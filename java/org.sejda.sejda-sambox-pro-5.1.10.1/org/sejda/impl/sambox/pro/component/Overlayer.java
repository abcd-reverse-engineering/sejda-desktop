package org.sejda.impl.sambox.pro.component;

import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.sejda.commons.FastByteArrayOutputStream;
import org.sejda.io.SeekableSource;
import org.sejda.io.SeekableSources;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.input.ExistingIndirectCOSObject;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.PDResources;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.graphics.form.PDFormXObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/Overlayer.class */
public class Overlayer {
    private static final Logger LOG = LoggerFactory.getLogger(Overlayer.class);
    private Map<Integer, LayoutPage> specificPageOverlayPage = new HashMap();
    private Position position = Position.BACKGROUND;

    /* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/Overlayer$Position.class */
    public enum Position {
        FOREGROUND,
        BACKGROUND
    }

    public PDDocument overlayDocuments(PDDocument base, PDDocument overlay, int baseStartPage) throws IOException {
        this.position = Position.FOREGROUND;
        int basePageNum = baseStartPage;
        int overlayPageNum = 0;
        while (overlayPageNum < overlay.getNumberOfPages()) {
            this.specificPageOverlayPage.put(Integer.valueOf(basePageNum), getLayoutPage(overlay.getPage(overlayPageNum)));
            overlayPageNum++;
            basePageNum++;
        }
        processPages(base);
        return base;
    }

    /* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/Overlayer$LayoutPage.class */
    private static final class LayoutPage {
        private final PDRectangle overlayMediaBox;
        private final COSStream overlayContentStream;
        private final COSDictionary overlayResources;

        private LayoutPage(PDRectangle mediaBox, COSStream contentStream, COSDictionary resources) {
            this.overlayMediaBox = mediaBox;
            this.overlayContentStream = contentStream;
            this.overlayResources = resources;
        }
    }

    private LayoutPage getLayoutPage(PDPage page) throws IOException {
        COSBase contents = page.getCOSObject().getDictionaryObject(COSName.CONTENTS);
        PDResources resources = page.getResources();
        if (resources == null) {
            resources = new PDResources();
        }
        return new LayoutPage(page.getMediaBox(), createCombinedContentStream(contents), resources.getCOSObject());
    }

    private COSStream createCombinedContentStream(COSBase contents) throws IOException {
        List<COSStream> contentStreams = createContentStreamList(contents);
        FastByteArrayOutputStream baos = new FastByteArrayOutputStream();
        for (COSStream contentStream : contentStreams) {
            InputStream in = contentStream.getUnfilteredStream();
            try {
                IOUtils.copy(in, baos);
                if (in != null) {
                    in.close();
                }
            } catch (Throwable th) {
                if (in != null) {
                    try {
                        in.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        SeekableSource source = SeekableSources.inMemorySeekableSourceFrom(baos.getInputStream());
        COSDictionary dictionary = new COSDictionary();
        COSStream concatenatedStreams = new COSStream(dictionary, source, 0L, baos.size());
        concatenatedStreams.setFilters(COSName.FLATE_DECODE);
        return concatenatedStreams;
    }

    private List<COSStream> createContentStreamList(COSBase contents) throws IOException {
        List<COSStream> contentStreams = new ArrayList<>();
        if (contents == null) {
            return contentStreams;
        }
        if (contents instanceof ExistingIndirectCOSObject) {
            contents = contents.getCOSObject();
        }
        if (contents instanceof COSStream) {
            contentStreams.add((COSStream) contents);
        } else if (contents instanceof COSArray) {
            Iterator it = ((COSArray) contents).iterator();
            while (it.hasNext()) {
                COSBase item = (COSBase) it.next();
                contentStreams.addAll(createContentStreamList(item));
            }
        } else {
            throw new IOException("Unknown content type: " + contents.getClass().getName());
        }
        return contentStreams;
    }

    private void processPages(PDDocument document) throws IOException {
        for (int pageNum = 0; pageNum < document.getNumberOfPages(); pageNum++) {
            LayoutPage layoutPage = this.specificPageOverlayPage.get(Integer.valueOf(pageNum));
            if (layoutPage != null) {
                PDPage page = document.getPage(pageNum);
                COSDictionary pageDictionary = page.getCOSObject();
                COSBase originalContent = pageDictionary.getDictionaryObject(COSName.CONTENTS);
                COSArray newContentArray = new COSArray();
                LOG.debug("Overlaying page {}", Integer.valueOf(pageNum));
                switch (this.position) {
                    case FOREGROUND:
                        newContentArray.add(createStream("q\n"));
                        addOriginalContent(originalContent, newContentArray);
                        newContentArray.add(createStream("Q\n"));
                        overlayPage(page, layoutPage, newContentArray);
                        break;
                    case BACKGROUND:
                        overlayPage(page, layoutPage, newContentArray);
                        addOriginalContent(originalContent, newContentArray);
                        break;
                    default:
                        throw new IOException("Unknown type of position:" + this.position);
                }
                pageDictionary.setItem(COSName.CONTENTS, newContentArray);
            }
        }
    }

    private void addOriginalContent(COSBase contents, COSArray contentArray) throws IOException {
        if (contents == null) {
            return;
        }
        if (contents instanceof COSStream) {
            contentArray.add(contents);
        } else {
            if (contents instanceof COSArray) {
                contentArray.addAll((COSArray) contents);
                return;
            }
            throw new IOException("Unknown content type: " + contents.getClass().getName());
        }
    }

    private void overlayPage(PDPage page, LayoutPage layoutPage, COSArray array) throws IOException {
        PDResources resources = page.getResources();
        if (resources == null) {
            PDResources resources2 = new PDResources();
            page.setResources(resources2);
        }
        COSName xObjectId = createOverlayXObject(page, layoutPage, layoutPage.overlayContentStream);
        array.add(createOverlayStream(page, layoutPage, xObjectId));
    }

    private COSName createOverlayXObject(PDPage page, LayoutPage layoutPage, COSStream contentStream) {
        PDFormXObject xobjForm = new PDFormXObject(contentStream);
        xobjForm.setResources(new PDResources(layoutPage.overlayResources));
        xobjForm.setFormType(1);
        xobjForm.setBBox(layoutPage.overlayMediaBox.createRetranslatedRectangle());
        xobjForm.setMatrix(new AffineTransform());
        PDResources resources = page.getResources();
        return resources.add(xobjForm, "OL");
    }

    private COSStream createOverlayStream(PDPage page, LayoutPage layoutPage, COSName xObjectId) throws IOException {
        StringBuilder overlayStream = new StringBuilder();
        overlayStream.append("q\nq\n");
        AffineTransform at = calculateAffineTransform(page, layoutPage.overlayMediaBox);
        double[] flatmatrix = new double[6];
        at.getMatrix(flatmatrix);
        for (double v : flatmatrix) {
            overlayStream.append(float2String((float) v));
            overlayStream.append(" ");
        }
        overlayStream.append(" cm\n/");
        overlayStream.append(xObjectId.getName());
        overlayStream.append(" Do Q\nQ\n");
        return createStream(overlayStream.toString());
    }

    private AffineTransform calculateAffineTransform(PDPage page, PDRectangle overlayMediaBox) {
        AffineTransform at = new AffineTransform();
        PDRectangle pageMediaBox = page.getMediaBox();
        PDRectangle pageCropBox = page.getCropBox();
        float deltaLeftX = pageCropBox.getLowerLeftX() - pageMediaBox.getLowerLeftX();
        float deltaLeftY = pageCropBox.getLowerLeftY() - pageMediaBox.getLowerLeftY();
        float deltaRightX = pageMediaBox.getUpperRightX() - pageCropBox.getUpperRightX();
        float deltaRightY = pageMediaBox.getUpperRightY() - pageCropBox.getUpperRightY();
        at.rotate((page.getRotation() * 3.141592653589793d) / 180.0d);
        switch (page.getRotation()) {
            case 0:
                at.translate(deltaLeftX, deltaLeftY);
                break;
            case 90:
                at.translate(deltaLeftY, (-pageMediaBox.getWidth()) + deltaRightX);
                break;
            case 180:
                at.translate(deltaRightX - pageMediaBox.getWidth(), deltaRightY - pageMediaBox.getHeight());
                break;
            case 270:
                at.translate(deltaRightY - pageMediaBox.getHeight(), deltaLeftX);
                break;
        }
        return at;
    }

    private String float2String(float floatValue) {
        BigDecimal value = new BigDecimal(String.valueOf(floatValue));
        String stringValue = value.toPlainString();
        if (stringValue.indexOf(46) > -1 && !stringValue.endsWith(".0")) {
            while (stringValue.endsWith("0") && !stringValue.endsWith(".0")) {
                stringValue = stringValue.substring(0, stringValue.length() - 1);
            }
        }
        return stringValue;
    }

    private COSStream createStream(String content) throws IOException {
        byte[] bytes = content.getBytes(StandardCharsets.ISO_8859_1);
        SeekableSource source = SeekableSources.inMemorySeekableSourceFrom(bytes);
        COSStream stream = new COSStream(new COSDictionary(), source, 0L, bytes.length);
        stream.setFilters(COSName.FLATE_DECODE);
        return stream;
    }
}
