package org.sejda.sambox.pdmodel.font;

import java.io.IOException;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.ResourceCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/PDFontFactory.class */
public final class PDFontFactory {
    private static final Logger LOG = LoggerFactory.getLogger(PDFontFactory.class);

    private PDFontFactory() {
    }

    public static PDFont createFont(COSDictionary dictionary) throws IOException {
        return createFont(dictionary, null);
    }

    public static PDFont createFont(COSDictionary dictionary, ResourceCache resourceCache) throws IOException {
        COSName type = dictionary.getCOSName(COSName.TYPE, COSName.FONT);
        if (!COSName.FONT.equals(type)) {
            LOG.error("Expected 'Font' dictionary but found '" + type.getName() + "'");
        }
        COSName subType = dictionary.getCOSName(COSName.SUBTYPE);
        if (COSName.TYPE1.equals(subType)) {
            COSBase fd = dictionary.getDictionaryObject(COSName.FONT_DESC);
            if ((fd instanceof COSDictionary) && ((COSDictionary) fd).containsKey(COSName.FONT_FILE3)) {
                return new PDType1CFont(dictionary);
            }
            return new PDType1Font(dictionary);
        }
        if (COSName.MM_TYPE1.equals(subType)) {
            COSBase fd2 = dictionary.getDictionaryObject(COSName.FONT_DESC);
            if ((fd2 instanceof COSDictionary) && ((COSDictionary) fd2).containsKey(COSName.FONT_FILE3)) {
                return new PDType1CFont(dictionary);
            }
            return new PDMMType1Font(dictionary);
        }
        if (COSName.TRUE_TYPE.equals(subType)) {
            return new PDTrueTypeFont(dictionary);
        }
        if (COSName.TYPE3.equals(subType)) {
            return new PDType3Font(dictionary, resourceCache);
        }
        if (COSName.TYPE0.equals(subType)) {
            return new PDType0Font(dictionary);
        }
        if (COSName.CID_FONT_TYPE0.equals(subType)) {
            throw new IOException("Type 0 descendant font not allowed");
        }
        if (COSName.CID_FONT_TYPE2.equals(subType)) {
            throw new IOException("Type 2 descendant font not allowed");
        }
        LOG.warn("Invalid font subtype '" + subType + "'");
        try {
            return new PDType1Font(dictionary);
        } catch (FontFileMismatchException e) {
            return new PDType1CFont(dictionary);
        }
    }

    static PDCIDFont createDescendantFont(COSDictionary dictionary, PDType0Font parent) throws IOException {
        COSName type = dictionary.getCOSName(COSName.TYPE, COSName.FONT);
        if (!COSName.FONT.equals(type)) {
            throw new IOException("Expected 'Font' dictionary but found '" + type.getName() + "'");
        }
        COSName subType = dictionary.getCOSName(COSName.SUBTYPE);
        if (COSName.CID_FONT_TYPE0.equals(subType)) {
            return new PDCIDFontType0(dictionary, parent);
        }
        if (COSName.CID_FONT_TYPE2.equals(subType)) {
            return new PDCIDFontType2(dictionary, parent);
        }
        throw new IOException("Invalid font type: " + subType);
    }

    @Deprecated
    public static PDFont createDefaultFont() throws IOException {
        return PDType1Font.HELVETICA();
    }
}
