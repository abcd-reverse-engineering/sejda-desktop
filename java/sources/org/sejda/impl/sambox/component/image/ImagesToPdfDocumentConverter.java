package org.sejda.impl.sambox.component.image;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import org.apache.commons.io.FilenameUtils;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.impl.sambox.component.PageImageWriter;
import org.sejda.model.PageOrientation;
import org.sejda.model.PageSize;
import org.sejda.model.encryption.EncryptionAtRestPolicy;
import org.sejda.model.exception.TaskException;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.input.ImageMergeInput;
import org.sejda.model.input.MergeInput;
import org.sejda.model.input.PdfFileSource;
import org.sejda.model.input.PdfMergeInput;
import org.sejda.model.input.Source;
import org.sejda.model.parameter.BaseMergeParameters;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.graphics.image.PDImageXObject;
import org.sejda.sambox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/image/ImagesToPdfDocumentConverter.class */
public class ImagesToPdfDocumentConverter {
    private static final Logger LOG = LoggerFactory.getLogger(ImagesToPdfDocumentConverter.class);
    private PDDocumentHandler documentHandler = new PDDocumentHandler();
    private PageImageWriter imageWriter;

    public ImagesToPdfDocumentConverter() {
        this.documentHandler.setCreatorOnPDDocument();
        this.imageWriter = new PageImageWriter(this.documentHandler.getUnderlyingPDDocument());
    }

    public List<PDPage> addPages(Source<?> source) throws TaskException {
        return addPages(source, null, PageOrientation.AUTO, 0.0f);
    }

    public List<PDPage> addPages(Source<?> source, PDRectangle pageSize, PageOrientation pageOrientation, float marginInches) throws TaskException {
        beforeImage(source);
        List<PDPage> results = new LinkedList<>();
        try {
            int numberOfImages = 1;
            if (supportsMultiPageImage(source)) {
                numberOfImages = getImagePageCount(source);
            }
            for (int imageNumber = 0; imageNumber < numberOfImages; imageNumber++) {
                PDImageXObject image = PageImageWriter.toPDXImageObject(source, imageNumber);
                PDRectangle mediaBox = pageSize;
                if (mediaBox == null) {
                    mediaBox = new PDRectangle(image.getWidth(), image.getHeight());
                }
                if (pageOrientation == PageOrientation.LANDSCAPE) {
                    mediaBox = new PDRectangle(mediaBox.getHeight(), mediaBox.getWidth());
                } else if (pageOrientation == PageOrientation.AUTO && image.getWidth() > image.getHeight() && image.getWidth() > mediaBox.getWidth()) {
                    LOG.debug("Switching to landscape, image dimensions are {}x{}", Integer.valueOf(image.getWidth()), Integer.valueOf(image.getHeight()));
                    mediaBox = new PDRectangle(mediaBox.getHeight(), mediaBox.getWidth());
                }
                PDPage page = this.documentHandler.addBlankPage(mediaBox);
                results.add(page);
                float width = image.getWidth();
                float height = image.getHeight();
                if (width > mediaBox.getWidth()) {
                    float targetWidth = mediaBox.getWidth();
                    LOG.debug("Scaling image down to fit by width {} vs {}", Float.valueOf(width), Float.valueOf(targetWidth));
                    float ratio = width / targetWidth;
                    width = targetWidth;
                    height = Math.round(height / ratio);
                }
                if (height > mediaBox.getHeight()) {
                    float targetHeight = mediaBox.getHeight();
                    LOG.debug("Scaling image down to fit by height {} vs {}", Float.valueOf(height), Float.valueOf(targetHeight));
                    float ratio2 = height / targetHeight;
                    height = targetHeight;
                    width = Math.round(width / ratio2);
                }
                if (marginInches > 0.0f) {
                    float newWidth = width - (marginInches * 72.0f);
                    float newHeight = (height * newWidth) / width;
                    width = newWidth;
                    height = newHeight;
                }
                float x = (mediaBox.getWidth() - width) / 2.0f;
                float y = (mediaBox.getHeight() - height) / 2.0f;
                this.imageWriter.append(page, image, (Point2D) new Point2D.Float(x, y), width, height, (PDExtendedGraphicsState) null, 0);
                afterImage(image);
            }
        } catch (TaskIOException e) {
            failedImage(source, e);
        }
        return results;
    }

    public void beforeImage(Source<?> source) throws TaskException {
    }

    public void afterImage(PDImageXObject image) throws TaskException {
    }

    public void failedImage(Source<?> source, TaskIOException e) throws TaskException {
        throw e;
    }

    public boolean supportsMultiPageImage(Source<?> source) {
        return true;
    }

    public static void convertImageMergeInputToPdf(BaseMergeParameters<MergeInput> parameters, TaskExecutionContext context) throws TaskException {
        ArrayList arrayList = new ArrayList();
        for (T input : parameters.getInputList()) {
            if (input instanceof ImageMergeInput) {
                ImageMergeInput image = (ImageMergeInput) input;
                context.notifiableTaskMetadata().setCurrentSource(image.getSource());
                arrayList.add(convertImagesToPdfMergeInput(image, context));
                context.notifiableTaskMetadata().clearCurrentSource();
            } else {
                arrayList.add(input);
            }
        }
        parameters.setInputList(arrayList);
    }

    public static PDRectangle toPDRectangle(PageSize pageSize) {
        return new PDRectangle(pageSize.getWidth(), pageSize.getHeight());
    }

    private static PdfMergeInput convertImagesToPdfMergeInput(ImageMergeInput image, final TaskExecutionContext context) throws IllegalStateException, IOException, TaskException {
        ImagesToPdfDocumentConverter converter = new ImagesToPdfDocumentConverter() { // from class: org.sejda.impl.sambox.component.image.ImagesToPdfDocumentConverter.1
            @Override // org.sejda.impl.sambox.component.image.ImagesToPdfDocumentConverter
            public void failedImage(Source<?> source, TaskIOException e) throws TaskException {
                context.assertTaskIsLenient(e);
                ApplicationEventsNotifier.notifyEvent(context.notifiableTaskMetadata()).taskWarning(String.format("Image %s was skipped, could not be processed", source.getName()), e);
            }
        };
        PDRectangle pageSize = null;
        if (image.getPageSize() != null) {
            pageSize = toPDRectangle(image.getPageSize());
        }
        if (image.isShouldPageSizeMatchImageSize()) {
            pageSize = null;
        }
        converter.addPages(image.getSource(), pageSize, image.getPageOrientation(), 0.0f);
        PDDocumentHandler converted = converter.getDocumentHandler();
        String basename = FilenameUtils.getBaseName(image.getSource().getName());
        String filename = String.format("%s.pdf", basename);
        File convertedTmpFile = IOUtils.createTemporaryBufferWithName(filename);
        converted.setDocumentTitle(basename);
        EncryptionAtRestPolicy encryptionAtRestPolicy = image.getSource().getEncryptionAtRestPolicy();
        converted.savePDDocument(convertedTmpFile, encryptionAtRestPolicy);
        PdfMergeInput input = new PdfMergeInput(PdfFileSource.newInstanceNoPassword(convertedTmpFile));
        input.getSource().setEncryptionAtRestPolicy(encryptionAtRestPolicy);
        return input;
    }

    public static int getImagePageCount(Source<?> source) {
        try {
            ImageInputStream is = ImageIO.createImageInputStream(source.getSeekableSource().asNewInputStream());
            try {
                Iterator<ImageReader> readers = ImageIO.getImageReaders(is);
                if (readers.hasNext()) {
                    ImageReader reader = readers.next();
                    reader.setInput(is);
                    try {
                        int numImages = reader.getNumImages(true);
                        reader.dispose();
                        if (is != null) {
                            is.close();
                        }
                        return numImages;
                    } catch (Throwable th) {
                        reader.dispose();
                        throw th;
                    }
                }
                if (is != null) {
                    is.close();
                }
                return 1;
            } finally {
            }
        } catch (IOException ex) {
            LOG.warn("Could not determine image page count: {}", source.getName(), ex);
            return 1;
        }
    }

    public PDDocumentHandler getDocumentHandler() {
        return this.documentHandler;
    }
}
