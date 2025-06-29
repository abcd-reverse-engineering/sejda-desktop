package org.sejda.sambox.pdmodel.font;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.fontbox.afm.FontMetrics;
import org.apache.fontbox.encoding.BuiltInEncoding;
import org.apache.fontbox.pfb.PfbParser;
import org.apache.fontbox.type1.Type1Font;
import org.apache.fontbox.util.BoundingBox;
import org.sejda.commons.util.IOUtils;
import org.sejda.sambox.cos.COSArrayList;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.common.PDStream;
import org.sejda.sambox.pdmodel.font.encoding.Encoding;
import org.sejda.sambox.pdmodel.font.encoding.GlyphList;
import org.sejda.sambox.pdmodel.font.encoding.Type1Encoding;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/PDType1FontEmbedder.class */
class PDType1FontEmbedder {
    private final Encoding fontEncoding;
    private final Type1Font type1;

    PDType1FontEmbedder(PDDocument doc, COSDictionary dict, InputStream pfbStream, Encoding encoding) throws IOException {
        dict.setItem(COSName.SUBTYPE, COSName.TYPE1);
        byte[] pfbBytes = IOUtils.toByteArray(pfbStream);
        PfbParser pfbParser = new PfbParser(pfbBytes);
        this.type1 = Type1Font.createWithPFB(pfbBytes);
        this.fontEncoding = (Encoding) Objects.requireNonNullElseGet(encoding, () -> {
            return Type1Encoding.fromFontBox(this.type1.getEncoding());
        });
        PDFontDescriptor fd = buildFontDescriptor(this.type1);
        PDStream fontStream = new PDStream(pfbParser.getInputStream(), COSName.FLATE_DECODE);
        fontStream.getCOSObject().setInt("Length", pfbParser.size());
        for (int i = 0; i < pfbParser.getLengths().length; i++) {
            fontStream.getCOSObject().setInt("Length" + (i + 1), pfbParser.getLengths()[i]);
        }
        fd.setFontFile(fontStream);
        dict.setItem(COSName.FONT_DESC, fd);
        dict.setName(COSName.BASE_FONT, this.type1.getName());
        List<Integer> widths = new ArrayList<>(PDAnnotation.FLAG_TOGGLE_NO_VIEW);
        for (int code2 = 0; code2 <= 255; code2++) {
            String name = this.fontEncoding.getName(code2);
            int width = Math.round(this.type1.getWidth(name));
            widths.add(Integer.valueOf(width));
        }
        dict.setInt(COSName.FIRST_CHAR, 0);
        dict.setInt(COSName.LAST_CHAR, 255);
        dict.setItem(COSName.WIDTHS, COSArrayList.converterToCOSArray(widths));
        dict.setItem(COSName.ENCODING, encoding);
    }

    static PDFontDescriptor buildFontDescriptor(Type1Font type1) {
        boolean isSymbolic = type1.getEncoding() instanceof BuiltInEncoding;
        PDFontDescriptor fd = new PDFontDescriptor();
        fd.setFontName(type1.getName());
        fd.setFontFamily(type1.getFamilyName());
        fd.setNonSymbolic(!isSymbolic);
        fd.setSymbolic(isSymbolic);
        BoundingBox fontBBox = type1.getFontBBox();
        fd.setFontBoundingBox(new PDRectangle(fontBBox));
        fd.setItalicAngle(type1.getItalicAngle());
        fd.setAscent(fontBBox.getUpperRightY());
        fd.setDescent(fontBBox.getLowerLeftY());
        fd.setCapHeight(((Number) type1.getBlueValues().get(2)).floatValue());
        fd.setStemV(0.0f);
        return fd;
    }

    static PDFontDescriptor buildFontDescriptor(FontMetrics metrics) {
        boolean isSymbolic = metrics.getEncodingScheme().equals("FontSpecific");
        PDFontDescriptor fd = new PDFontDescriptor();
        fd.setFontName(metrics.getFontName());
        fd.setFontFamily(metrics.getFamilyName());
        fd.setNonSymbolic(!isSymbolic);
        fd.setSymbolic(isSymbolic);
        fd.setFontBoundingBox(new PDRectangle(metrics.getFontBBox()));
        fd.setItalicAngle(metrics.getItalicAngle());
        fd.setAscent(metrics.getAscender());
        fd.setDescent(metrics.getDescender());
        fd.setCapHeight(metrics.getCapHeight());
        fd.setXHeight(metrics.getXHeight());
        fd.setAverageWidth(metrics.getAverageCharacterWidth());
        fd.setCharacterSet(metrics.getCharacterSet());
        fd.setStemV(0.0f);
        return fd;
    }

    public Encoding getFontEncoding() {
        return this.fontEncoding;
    }

    public GlyphList getGlyphList() {
        return GlyphList.getAdobeGlyphList();
    }

    public Type1Font getType1Font() {
        return this.type1;
    }
}
