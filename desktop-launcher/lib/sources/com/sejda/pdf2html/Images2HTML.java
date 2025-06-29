package com.sejda.pdf2html;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.io.Closeables;
import com.sejda.pdf2html.pojo.Image;
import com.sejda.pdf2html.pojo.LazyImageProps;
import com.sejda.pdf2html.pojo.PdxObjectBackedImage;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Callable;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.imgscalr.Scalr;
import org.sejda.sambox.contentstream.PDFStreamEngine;
import org.sejda.sambox.contentstream.operator.DrawObject;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.state.Concatenate;
import org.sejda.sambox.contentstream.operator.state.Restore;
import org.sejda.sambox.contentstream.operator.state.Save;
import org.sejda.sambox.contentstream.operator.state.SetGraphicsStateParameters;
import org.sejda.sambox.contentstream.operator.state.SetMatrix;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.PDResources;
import org.sejda.sambox.pdmodel.graphics.PDXObject;
import org.sejda.sambox.pdmodel.graphics.form.PDFormXObject;
import org.sejda.sambox.pdmodel.graphics.image.PDImageXObject;
import org.sejda.sambox.util.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: com.sejda.pdf2html.pdf2html-0.0.72.jar:com/sejda/pdf2html/Images2HTML.class */
public class Images2HTML extends PDFStreamEngine implements ImageExtractor {
    private static final String INVOKE_OPERATOR = "Do";
    private final File storageFolder;
    private final int maxImagesPerDocument;
    private final int maxPagesPerDocument;
    private final boolean resizeImages;
    private final int maxWidth;
    private final int maxHeight;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private int count = 0;
    private Map<PDPage, List<Image>> images = new HashMap();
    private Multiset<Position> imagePositions = HashMultiset.create();

    public Images2HTML(File storageFolder, int maxImagesPerDocument, int maxPagesPerDocument, boolean resizeImages, int maxWidth, int maxHeight) throws IOException {
        this.maxImagesPerDocument = maxImagesPerDocument;
        this.maxPagesPerDocument = maxPagesPerDocument;
        this.storageFolder = storageFolder;
        this.resizeImages = resizeImages;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        addOperator(new Concatenate());
        addOperator(new DrawObject());
        addOperator(new SetGraphicsStateParameters());
        addOperator(new Save());
        addOperator(new Restore());
        addOperator(new SetMatrix());
    }

    @Override // com.sejda.pdf2html.ImageExtractor
    public Images2HTML processDocument(PDDocument document) throws Exception {
        int currentPage = 1;
        Iterator<PDPage> it = document.getDocumentCatalog().getPages().iterator();
        while (it.hasNext()) {
            PDPage page = it.next();
            if (document.getPages().indexOf(page) <= this.maxPagesPerDocument) {
                processPage(page);
                this.LOGGER.debug("On page " + currentPage + " found " + pageImages(page).size() + " images");
                currentPage++;
            }
        }
        int pageCount = document.getDocumentCatalog().getPages().getCount();
        int removedCount = 0;
        for (Position k : this.imagePositions) {
            if (this.imagePositions.count(k) > pageCount * 0.3d) {
                for (PDPage page2 : this.images.keySet()) {
                    List<Image> pageImages = this.images.get(page2);
                    Iterator<Image> it2 = pageImages.iterator();
                    while (it2.hasNext()) {
                        Image i = it2.next();
                        Position p = new Position(i.getX(), i.getY());
                        if (p.equals(k)) {
                            it2.remove();
                            removedCount++;
                        }
                    }
                    this.images.put(page2, pageImages);
                }
            }
        }
        if (removedCount > 0) {
            this.LOGGER.debug("Removed " + removedCount + " images as OCR background suspects");
        }
        this.LOGGER.debug("Found a total of " + (this.count - removedCount) + " images that will be included in the HTML result");
        return this;
    }

    private boolean shouldProcessImage() {
        return this.count < this.maxImagesPerDocument;
    }

    @Override // com.sejda.pdf2html.ImageExtractor
    public List<Image> pageImages(PDPage page) {
        List<Image> result = this.images.get(page);
        if (result == null) {
            result = new ArrayList();
        }
        return result;
    }

    public List<Image> allImages() {
        List<Image> all = new ArrayList<>();
        Collection<List<Image>> collectionValues = this.images.values();
        Objects.requireNonNull(all);
        collectionValues.forEach((v1) -> {
            r1.addAll(v1);
        });
        return all;
    }

    @Override // org.sejda.sambox.contentstream.PDFStreamEngine
    protected void processOperator(Operator operator, List arguments) throws IOException {
        String operation = operator.getName();
        if ("Do".equals(operation)) {
            COSName objectName = (COSName) arguments.get(0);
            try {
                processXObjectImage(objectName);
                return;
            } catch (Exception e) {
                this.LOGGER.warn("Failed to process image from PDF file: " + e.getMessage());
                return;
            }
        }
        super.processOperator(operator, (List<COSBase>) arguments);
    }

    private void processXObjectImage(COSName objectName) throws IOException {
        PDResources resources = getResources();
        if (!getResources().isImageXObject(objectName)) {
            if (getResources().isFormXObject(objectName)) {
                this.count++;
                if (shouldProcessImage()) {
                    PDXObject xobject = getResources().getXObject(objectName);
                    PDFormXObject form = (PDFormXObject) xobject;
                    showForm(form);
                    return;
                }
                return;
            }
            return;
        }
        this.count++;
        if (shouldProcessImage()) {
            PDPage page = getCurrentPage();
            Matrix ctmNew = getGraphicsState().getCurrentTransformationMatrix();
            float x = ctmNew.getTranslateX();
            float y = ctmNew.getTranslateY();
            Callable<LazyImageProps> imageProducer = () -> {
                try {
                    long start = System.currentTimeMillis();
                    PDXObject xobject2 = resources.getXObject(objectName);
                    PDImageXObject image = (PDImageXObject) xobject2;
                    BufferedImage resizedImage = image.getImage();
                    if (this.resizeImages) {
                        resizedImage = resize(resizedImage);
                    }
                    String format = resizedImage.getColorModel().hasAlpha() ? "png" : "jpg";
                    File outFile = new File(this.storageFolder, UUID.randomUUID().toString() + "." + format);
                    FileOutputStream out = new FileOutputStream(outFile);
                    try {
                        ImageIO.write(resizedImage, format, out);
                        Closeables.close(out, true);
                        long elapsed = (System.currentTimeMillis() - start) / 1000;
                        if (elapsed > 2) {
                            this.LOGGER.debug("Done producing image (" + elapsed + "s)");
                        }
                        Optional<File> src = Optional.of(outFile);
                        int width = resizedImage.getWidth();
                        int height = resizedImage.getHeight();
                        return new LazyImageProps(src, width, height);
                    } catch (Throwable th) {
                        Closeables.close(out, true);
                        throw th;
                    }
                } catch (Exception e) {
                    this.LOGGER.warn("Failed to process image " + String.valueOf(objectName), e);
                    return new LazyImageProps(Optional.empty(), 0, 0);
                }
            };
            PdxObjectBackedImage entry = new PdxObjectBackedImage(imageProducer, x, y, objectName.getName());
            if (!this.images.containsKey(page)) {
                this.images.put(page, new ArrayList());
            }
            this.images.get(page).add(entry);
            this.imagePositions.add(new Position(entry.getX(), entry.getY()));
        }
    }

    private BufferedImage resize(BufferedImage image) throws IOException {
        boolean shouldResize = image.getWidth() > this.maxWidth || image.getHeight() > this.maxHeight;
        if (shouldResize) {
            return Scalr.resize(image, Scalr.Method.QUALITY, this.maxWidth, this.maxHeight, new BufferedImageOp[0]);
        }
        return image;
    }

    /* loaded from: com.sejda.pdf2html.pdf2html-0.0.72.jar:com/sejda/pdf2html/Images2HTML$Position.class */
    static class Position {
        public final int x;
        public final int y;

        public Position(float x, float y) {
            this.x = (int) x;
            this.y = (int) y;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Position position = (Position) o;
            return new EqualsBuilder().append(this.x, position.x).append(this.y, position.y).isEquals();
        }

        public int hashCode() {
            return new HashCodeBuilder(17, 37).append(this.x).append(this.y).toHashCode();
        }
    }
}
