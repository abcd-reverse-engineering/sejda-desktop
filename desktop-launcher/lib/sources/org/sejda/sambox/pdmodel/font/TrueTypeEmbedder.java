package org.sejda.sambox.pdmodel.font;

import java.awt.geom.GeneralPath;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.apache.fontbox.ttf.CmapLookup;
import org.apache.fontbox.ttf.HeaderTable;
import org.apache.fontbox.ttf.HorizontalHeaderTable;
import org.apache.fontbox.ttf.OS2WindowsMetricsTable;
import org.apache.fontbox.ttf.PostScriptTable;
import org.apache.fontbox.ttf.TTFParser;
import org.apache.fontbox.ttf.TTFSubsetter;
import org.apache.fontbox.ttf.TrueTypeFont;
import org.sejda.commons.util.IOUtils;
import org.sejda.commons.util.RequireUtils;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.common.PDStream;
import org.sejda.sambox.pdmodel.documentinterchange.taggedpdf.StandardStructureTypes;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/TrueTypeEmbedder.class */
abstract class TrueTypeEmbedder implements Subsetter {
    private static final int ITALIC = 1;
    private static final int OBLIQUE = 512;
    protected TrueTypeFont ttf;
    protected PDFontDescriptor fontDescriptor;
    protected final CmapLookup cmapLookup;
    private final Set<Integer> subsetCodePoints = new HashSet();
    private final boolean embedSubset;

    protected abstract void buildSubset(InputStream inputStream, String str, Map<Integer, Integer> map) throws IOException;

    TrueTypeEmbedder(COSDictionary dict, TrueTypeFont ttf, boolean embedSubset) throws IOException {
        this.embedSubset = embedSubset;
        this.ttf = ttf;
        this.fontDescriptor = createFontDescriptor(ttf);
        if (!FontUtils.isEmbeddingPermitted(ttf)) {
            throw new IOException("This font does not permit embedding");
        }
        if (!embedSubset) {
            InputStream is = ttf.getOriginalData();
            byte[] b = new byte[4];
            is.mark(b.length);
            if (is.read(b) == b.length && new String(b).equals("ttcf")) {
                is.close();
                throw new IOException("Full embedding of TrueType font collections not supported");
            }
            if (is.markSupported()) {
                is.reset();
            } else {
                is.close();
                is = ttf.getOriginalData();
            }
            PDStream stream = new PDStream(is, COSName.FLATE_DECODE);
            stream.getCOSObject().setLong(COSName.LENGTH1, ttf.getOriginalDataSize());
            this.fontDescriptor.setFontFile2(stream);
        }
        dict.setName(COSName.BASE_FONT, ttf.getName());
        this.cmapLookup = ttf.getUnicodeCmapLookup();
    }

    public void buildFontFile2(InputStream ttfStream) throws IOException {
        PDStream stream = new PDStream(ttfStream, COSName.FLATE_DECODE);
        InputStream input = null;
        try {
            input = stream.createInputStream();
            this.ttf = new TTFParser().parseEmbedded(input);
            if (!FontUtils.isEmbeddingPermitted(this.ttf)) {
                throw new IOException("This font does not permit embedding");
            }
            if (this.fontDescriptor == null) {
                this.fontDescriptor = createFontDescriptor(this.ttf);
            }
            stream.getCOSObject().setLong(COSName.LENGTH1, this.ttf.getOriginalDataSize());
            this.fontDescriptor.setFontFile2(stream);
        } finally {
            IOUtils.closeQuietly(input);
        }
    }

    private PDFontDescriptor createFontDescriptor(TrueTypeFont ttf) throws IOException {
        String ttfName = ttf.getName();
        OS2WindowsMetricsTable os2 = ttf.getOS2Windows();
        RequireUtils.requireIOCondition(Objects.nonNull(os2), "os2 table is missing in font " + ttfName);
        PostScriptTable post = ttf.getPostScript();
        RequireUtils.requireIOCondition(Objects.nonNull(post), "post table is missing in font " + ttfName);
        PDFontDescriptor fd = new PDFontDescriptor();
        fd.setFontName(ttfName);
        HorizontalHeaderTable hhea = ttf.getHorizontalHeader();
        fd.setFixedPitch(post.getIsFixedPitch() > 0 || hhea.getNumberOfHMetrics() == 1);
        int fsSelection = os2.getFsSelection();
        fd.setItalic((fsSelection & 513) != 0);
        switch (os2.getFamilyClass()) {
            case 1:
            case 3:
            case 4:
            case 5:
            case 7:
                fd.setSerif(true);
                break;
            case 10:
                fd.setScript(true);
                break;
        }
        fd.setFontWeight(os2.getWeightClass());
        fd.setSymbolic(true);
        fd.setNonSymbolic(false);
        fd.setItalicAngle(post.getItalicAngle());
        HeaderTable header = ttf.getHeader();
        PDRectangle rect = new PDRectangle();
        float scaling = 1000.0f / header.getUnitsPerEm();
        rect.setLowerLeftX(header.getXMin() * scaling);
        rect.setLowerLeftY(header.getYMin() * scaling);
        rect.setUpperRightX(header.getXMax() * scaling);
        rect.setUpperRightY(header.getYMax() * scaling);
        fd.setFontBoundingBox(rect);
        fd.setAscent(hhea.getAscender() * scaling);
        fd.setDescent(hhea.getDescender() * scaling);
        if (os2.getVersion() >= 1.2d) {
            fd.setCapHeight(os2.getCapHeight() * scaling);
            fd.setXHeight(os2.getHeight() * scaling);
        } else {
            GeneralPath capHPath = ttf.getPath(StandardStructureTypes.H);
            if (capHPath != null) {
                fd.setCapHeight(Math.round(capHPath.getBounds2D().getMaxY()) * scaling);
            } else {
                fd.setCapHeight((os2.getTypoAscender() + os2.getTypoDescender()) * scaling);
            }
            GeneralPath xPath = ttf.getPath("x");
            if (xPath != null) {
                fd.setXHeight(Math.round(xPath.getBounds2D().getMaxY()) * scaling);
            } else {
                fd.setXHeight((os2.getTypoAscender() / 2.0f) * scaling);
            }
        }
        fd.setStemV(fd.getFontBoundingBox().getWidth() * 0.13f);
        return fd;
    }

    @Deprecated
    public TrueTypeFont getTrueTypeFont() {
        return this.ttf;
    }

    public PDFontDescriptor getFontDescriptor() {
        return this.fontDescriptor;
    }

    @Override // org.sejda.sambox.pdmodel.font.Subsetter
    public void addToSubset(int codePoint) {
        this.subsetCodePoints.add(Integer.valueOf(codePoint));
    }

    @Override // org.sejda.sambox.pdmodel.font.Subsetter
    public void subset() throws IOException {
        if (!FontUtils.isSubsettingPermitted(this.ttf)) {
            throw new IOException("This font does not permit subsetting");
        }
        if (!this.embedSubset) {
            throw new IllegalStateException("Subsetting is disabled");
        }
        TTFSubsetter subsetter = new TTFSubsetter(this.ttf, Arrays.asList("head", "hhea", "loca", "maxp", "cvt ", "prep", "glyf", "hmtx", "fpgm", "gasp"));
        subsetter.addAll(this.subsetCodePoints);
        Map<Integer, Integer> gidToCid = subsetter.getGIDMap();
        String tag = FontUtils.getTag(gidToCid);
        subsetter.setPrefix(tag);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        subsetter.writeToStream(out);
        buildSubset(new ByteArrayInputStream(out.toByteArray()), tag, gidToCid);
        this.ttf.close();
    }

    public boolean needsSubset() {
        return this.embedSubset;
    }
}
