package org.sejda.impl.sambox.pro.component.optimization.images;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.imageio.ImageIO;
import org.sejda.commons.util.IOUtils;
import org.sejda.commons.util.RequireUtils;
import org.sejda.core.support.util.HumanReadableSize;
import org.sejda.impl.sambox.component.ReadOnlyFilteredCOSStream;
import org.sejda.impl.sambox.pro.component.grayscale.GrayscaleUtils;
import org.sejda.impl.sambox.pro.component.optimization.ContentStreamOptimizationOperator;
import org.sejda.model.pro.optimization.Optimization;
import org.sejda.sambox.contentstream.operator.MissingOperandException;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.cos.IndirectCOSObjectIdentifier;
import org.sejda.sambox.encryption.MessageDigests;
import org.sejda.sambox.pdmodel.MissingResourceException;
import org.sejda.sambox.pdmodel.graphics.PDXObject;
import org.sejda.sambox.pdmodel.graphics.form.PDFormXObject;
import org.sejda.sambox.pdmodel.graphics.image.JPEGFactory;
import org.sejda.sambox.pdmodel.graphics.image.PDImageXObject;
import org.sejda.sambox.util.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/optimization/images/XObjectsOptimizerOperator.class */
public class XObjectsOptimizerOperator extends ContentStreamOptimizationOperator {
    private static final Logger LOG = LoggerFactory.getLogger(XObjectsOptimizerOperator.class);

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.sejda.sambox.contentstream.operator.MissingOperandException */
    public void process(Operator operator, List<COSBase> operands) throws MissingOperandException, IOException {
        if (operands.isEmpty()) {
            throw new MissingOperandException(operator, operands);
        }
        COSBase operand = operands.get(0);
        if (operand instanceof COSName) {
            COSName objectName = (COSName) operand;
            COSBase existing = (COSBase) Optional.ofNullable(getContext().getResources().getCOSObject().getDictionaryObject(COSName.XOBJECT, COSDictionary.class)).map(d -> {
                return d.getDictionaryObject(objectName);
            }).orElseThrow(() -> {
                return new MissingResourceException("Missing XObject: " + objectName.getName());
            });
            if (existing instanceof COSStream) {
                COSStream stream = (COSStream) existing;
                try {
                    processStream(objectName, stream);
                    return;
                } catch (OutOfMemoryError ex) {
                    LOG.warn("Failed to optimize stream, skipping and continuing with next.", ex);
                    return;
                }
            }
            LOG.warn("Unexpected type {} for xObject {}", existing.getClass(), objectName.getName());
        }
    }

    private void processStream(COSName objectName, COSStream stream) throws IOException {
        if (!(stream instanceof ReadOnlyFilteredCOSStream)) {
            hit(objectName, stream);
            String subtype = stream.getNameAsString(COSName.SUBTYPE);
            LOG.trace("Hit image with name {} and type {}", objectName.getName(), subtype);
            if (COSName.IMAGE.getName().equals(subtype)) {
                removeMetadataIfNeeded(stream);
                removeAlternatesIfNeeded(stream);
                boolean grayscale = optimizationContext().parameters().getOptimizations().contains(Optimization.GRAYSCALE_IMAGES);
                boolean compress = optimizationContext().parameters().getOptimizations().contains(Optimization.COMPRESS_IMAGES);
                if (grayscale || compress) {
                    if (stream.hasFilter(COSName.JBIG2_DECODE)) {
                        LOG.debug("Skipping JBIG2 encoded image {}", objectName.getName());
                    } else {
                        boolean willCompress = stream.getFilteredLength() > ((long) optimizationContext().parameters().getImageMinBytesSize()) && compress;
                        boolean willConvertToGrayscale = !GrayscaleUtils.isGray((COSDictionary) stream) && grayscale;
                        if (willCompress || willConvertToGrayscale) {
                            tryOptimize(objectName, stream, grayscale, willCompress);
                        }
                    }
                }
            } else if (COSName.FORM.getName().equals(subtype)) {
                PDFormXObject pDFormXObjectCreateXObject = PDXObject.createXObject(stream.getCOSObject(), getContext().getResources());
                removeMetadataIfNeeded(pDFormXObjectCreateXObject.getCOSObject());
                removePieceInfoIfNeeded(pDFormXObjectCreateXObject.getCOSObject());
                getContext().showForm(pDFormXObjectCreateXObject);
            } else {
                LOG.info("Found image of unknown subtype {}", subtype);
            }
            stream.unDecode();
            LOG.trace("Used memory: {} Mb", Long.valueOf(((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000) / 1000));
        }
    }

    private void tryOptimize(COSName objectName, COSStream stream, boolean grayscale, boolean willCompress) {
        try {
            long unfilteredSize = stream.getFilteredLength();
            PDImageXObject image = createXObject(stream);
            boolean willConvertToGrayscale = !GrayscaleUtils.isGray(image) && grayscale;
            if (willCompress || willConvertToGrayscale) {
                Matrix ctmNew = getContext().getGraphicsState().getCurrentTransformationMatrix();
                float displayWidth = ctmNew.getScalingFactorX();
                float displayHeight = ctmNew.getScalingFactorY();
                int width = image.getWidth();
                int height = image.getHeight();
                double scaleWidth = displayWidth / width;
                double scaleHeight = displayHeight / height;
                double scaleWidthRounded = Math.round(scaleWidth * 1000.0d) / 1000.0f;
                double scaleHeightRounded = Math.round(scaleHeight * 1000.0d) / 1000.0f;
                if (scaleWidthRounded != scaleHeightRounded) {
                    if (scaleWidthRounded > scaleHeightRounded) {
                        displayHeight = ((float) scaleWidth) * height;
                    } else if (scaleWidthRounded < scaleHeightRounded) {
                        displayWidth = ((float) scaleHeight) * width;
                    }
                    LOG.debug("Image {} has width/height scaled with different factors, making sure we keep original aspect ratio: {}x{} instead of {}x{}", new Object[]{objectName.getName(), Float.valueOf(displayWidth), Float.valueOf(displayHeight), Float.valueOf(displayWidth), Float.valueOf(displayHeight)});
                }
                if (Optional.ofNullable(image.getCOSObject().getDictionaryObject(COSName.SMASK, COSStream.class)).map(sm -> {
                    return sm.getDictionaryObject(COSName.MATTE, COSArray.class);
                }).isPresent()) {
                    LOG.debug("Found image {} {}x{} (displayed as {}x{}, has smask with matte and won't be resized) with size {}", new Object[]{objectName.getName(), Integer.valueOf(image.getWidth()), Integer.valueOf(image.getHeight()), Float.valueOf(displayWidth), Float.valueOf(displayHeight), HumanReadableSize.toString(unfilteredSize)});
                    optimize(objectName, image, stream.id(), image.getWidth(), image.getHeight(), willConvertToGrayscale);
                } else {
                    int newDisplayHeight = Math.abs(Math.round((displayHeight / 72.0f) * optimizationContext().parameters().getImageDpi()));
                    int newDisplayWidth = (int) Math.abs(Math.round((newDisplayHeight * width) / height));
                    LOG.debug("Found image {} {}x{} (displayed as {}x{}, to be resized to {}x{}) with size {}", new Object[]{objectName.getName(), Integer.valueOf(image.getWidth()), Integer.valueOf(image.getHeight()), Float.valueOf(displayWidth), Float.valueOf(displayHeight), Integer.valueOf(newDisplayWidth), Integer.valueOf(newDisplayHeight), HumanReadableSize.toString(unfilteredSize)});
                    optimize(objectName, image, stream.id(), newDisplayWidth, newDisplayHeight, willConvertToGrayscale);
                }
            }
        } catch (IOException | OutOfMemoryError | RuntimeException ex) {
            LOG.warn("Failed to optimize image, skipping and continuing with next.", ex);
        }
    }

    private void optimize(COSName objectName, PDImageXObject image, IndirectCOSObjectIdentifier id, int displayWidth, int displayHeight, boolean convertToGray) throws IOException {
        LOG.debug("Optimizing image {} {} with dimensions {}x{}. Convert to grayscale={}", new Object[]{objectName.getName(), id.toString(), Integer.valueOf(image.getWidth()), Integer.valueOf(image.getHeight()), Boolean.valueOf(convertToGray)});
        ReadOnlyFilteredCOSStream optimizedImage = optimizationContext().optimizedImagesById().get(id);
        if (Objects.isNull(optimizedImage)) {
            String hash = hashOf(image);
            LOG.trace("Image with id: {} has hash: {}", id, hash);
            ReadOnlyFilteredCOSStream previousImage = null;
            if (hash != null) {
                previousImage = optimizationContext().optimizedImagesByHash().get(hash);
            }
            if (previousImage != null) {
                LOG.trace("Found previous image: {} with same hash: {}", previousImage, hash);
                optimizedImage = previousImage;
            } else {
                BufferedImage imageWithoutMasks = image.getImageWithoutMasks();
                File tmpImageFile = ImageOptimizer.optimize(imageWithoutMasks, optimizationContext().parameters().getImageQuality(), optimizationContext().parameters().getImageDpi(), displayWidth, displayHeight, convertToGray);
                imageWithoutMasks.flush();
                optimizedImage = ReadOnlyFilteredCOSStream.readOnly(image.getCOSObject());
                double sizeRate = (tmpImageFile.length() * 100.0d) / image.getCOSObject().getFilteredLength();
                if (sizeRate < 100.0d || convertToGray) {
                    LOG.debug(String.format("Compressed image to %.2f%% of original size: %d", Double.valueOf(sizeRate), Long.valueOf(tmpImageFile.length())));
                    optimizedImage = createFromJpegFile(tmpImageFile, image);
                    optimizationContext().optimizedImagesById().put(id, optimizedImage);
                }
                if (hash != null) {
                    optimizationContext().optimizedImagesByHash().put(hash, optimizedImage);
                }
            }
        } else {
            LOG.debug(String.format("Skipping already visited image with id %s", id));
        }
        replaceHitXObject(objectName, optimizedImage);
    }

    private String hashOf(PDImageXObject image) throws IOException {
        if (image.hasSoftMask() || image.hasMask()) {
            LOG.trace("Image with masks, skipping hash based reuse mechanism");
            return null;
        }
        COSName deviceColorSpace = image.getCOSObject().getDictionaryObject(COSName.COLORSPACE, COSName.class);
        if (Objects.isNull(deviceColorSpace)) {
            LOG.trace("Image with CIE-based or special colour space, skipping hash based reuse mechanism");
            return null;
        }
        String imageProperties = image.getWidth() + image.getHeight() + image.getBitsPerComponent() + image.getInterpolate() + deviceColorSpace.getName() + image.isStencil();
        MessageDigest md5Digest = MessageDigests.md5();
        md5Digest.update(imageProperties.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(md5Digest.digest(IOUtils.toByteArray(image.createInputStream())));
    }

    private void removeMetadataIfNeeded(COSStream cosObject) {
        if (optimizationContext().parameters().getOptimizations().contains(Optimization.DISCARD_METADATA)) {
            cosObject.removeItem(COSName.METADATA);
        }
    }

    private void removePieceInfoIfNeeded(COSStream cosObject) {
        if (optimizationContext().parameters().getOptimizations().contains(Optimization.DISCARD_PIECE_INFO)) {
            cosObject.removeItem(COSName.PIECE_INFO);
        }
    }

    private void removeAlternatesIfNeeded(COSStream cosObject) {
        if (optimizationContext().parameters().getOptimizations().contains(Optimization.DISCARD_ALTERNATE_IMAGES)) {
            cosObject.removeItem(COSName.getPDFName("Alternates"));
        }
    }

    private PDImageXObject createXObject(COSStream stream) throws IOException {
        long start = System.currentTimeMillis();
        PDImageXObject pDImageXObjectCreateXObject = PDXObject.createXObject(stream, getContext().getResources());
        long elapsed = System.currentTimeMillis() - start;
        if (elapsed > 500) {
            LOG.trace("Loading PDXObject took {}ms", Long.valueOf(elapsed));
        }
        return pDImageXObjectCreateXObject;
    }

    private void replaceHitXObject(COSName objectName, ReadOnlyFilteredCOSStream xObject) {
        xobjectResources().setItem(objectName, xObject);
    }

    private void hit(COSName objectName, COSStream xObject) throws IOException {
        COSDictionary xobjects = xobjectResources();
        if (!(xobjects.getItem(objectName) instanceof ReadOnlyFilteredCOSStream)) {
            xobjects.setItem(objectName, ReadOnlyFilteredCOSStream.readOnly(xObject));
        }
    }

    private COSDictionary xobjectResources() {
        COSDictionary resources = getContext().getResources().getCOSObject();
        return (COSDictionary) Optional.ofNullable(resources.getDictionaryObject(COSName.XOBJECT, COSDictionary.class)).orElseGet(() -> {
            COSDictionary ret = new COSDictionary();
            resources.setItem(COSName.XOBJECT, ret);
            return ret;
        });
    }

    public String getName() {
        return "Do";
    }

    private static ReadOnlyFilteredCOSStream createFromJpegFile(File file, PDImageXObject original) throws IOException {
        BufferedImage awtImage = ImageIO.read(file);
        RequireUtils.requireIOCondition(Objects.nonNull(awtImage), "Cannot read image");
        if (awtImage.getColorModel().hasAlpha()) {
            throw new UnsupportedOperationException("alpha channel not implemented");
        }
        ReadOnlyFilteredCOSStream stream = ReadOnlyFilteredCOSStream.readOnlyJpegImage(file, awtImage.getWidth(), awtImage.getHeight(), awtImage.getColorModel().getComponentSize(0), JPEGFactory.getColorSpaceFromAWT(awtImage));
        stream.setItem(COSName.OC, original.getCOSObject().getDictionaryObject(COSName.OC));
        stream.setItem(COSName.SMASK, original.getCOSObject().getDictionaryObject(COSName.SMASK, COSStream.class));
        stream.setItem(COSName.MASK, original.getCOSObject().getDictionaryObject(COSName.MASK, COSStream.class));
        return stream;
    }
}
