package org.sejda.sambox.pdmodel.font;

import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.fontbox.EncodedFont;
import org.apache.fontbox.FontBoxFont;
import org.apache.fontbox.type1.DamagedFontException;
import org.apache.fontbox.type1.Type1Font;
import org.apache.fontbox.util.BoundingBox;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.filter.TIFFExtension;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.common.PDStream;
import org.sejda.sambox.pdmodel.font.encoding.Encoding;
import org.sejda.sambox.pdmodel.font.encoding.StandardEncoding;
import org.sejda.sambox.pdmodel.font.encoding.SymbolEncoding;
import org.sejda.sambox.pdmodel.font.encoding.Type1Encoding;
import org.sejda.sambox.pdmodel.font.encoding.WinAnsiEncoding;
import org.sejda.sambox.pdmodel.font.encoding.ZapfDingbatsEncoding;
import org.sejda.sambox.util.CharUtils;
import org.sejda.sambox.util.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/PDType1Font.class */
public class PDType1Font extends PDSimpleFont {
    private static final Logger LOG = LoggerFactory.getLogger(PDType1Font.class);
    private static final Map<String, String> ALT_NAMES = new HashMap();
    private static final int PFB_START_MARKER = 128;
    private static Map<StandardFont, PDType1Font> cache;
    private final Type1Font type1font;
    private final FontBoxFont genericFont;
    private final boolean isEmbedded;
    private final boolean isDamaged;
    private boolean isOriginalEmbeddedMissing;
    private boolean isMappingFallbackUsed;
    private Matrix fontMatrix;
    private final AffineTransform fontMatrixTransform;
    private BoundingBox fontBBox;
    private final Map<Integer, byte[]> codeToBytesMap;

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/PDType1Font$StandardFont.class */
    public enum StandardFont {
        TIMES_ROMAN,
        TIMES_BOLD,
        TIMES_ITALIC,
        TIMES_BOLD_ITALIC,
        HELVETICA,
        HELVETICA_BOLD,
        HELVETICA_OBLIQUE,
        HELVETICA_BOLD_OBLIQUE,
        COURIER,
        COURIER_BOLD,
        COURIER_OBLIQUE,
        COURIER_BOLD_OBLIQUE,
        SYMBOL,
        ZAPF_DINGBATS
    }

    static {
        ALT_NAMES.put("ff", "f_f");
        ALT_NAMES.put("ffi", "f_f_i");
        ALT_NAMES.put("ffl", "f_f_l");
        ALT_NAMES.put("fi", "f_i");
        ALT_NAMES.put("fl", "f_l");
        ALT_NAMES.put("st", "s_t");
        ALT_NAMES.put("IJ", "I_J");
        ALT_NAMES.put("ij", "i_j");
        ALT_NAMES.put("ellipsis", "elipsis");
        cache = new ConcurrentHashMap();
    }

    protected static PDType1Font getStandardFont(StandardFont key) {
        return cache.computeIfAbsent(key, standardFont -> {
            return loadStandardFont(key);
        });
    }

    /* renamed from: org.sejda.sambox.pdmodel.font.PDType1Font$1, reason: invalid class name */
    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/PDType1Font$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$sejda$sambox$pdmodel$font$PDType1Font$StandardFont = new int[StandardFont.values().length];

        static {
            try {
                $SwitchMap$org$sejda$sambox$pdmodel$font$PDType1Font$StandardFont[StandardFont.TIMES_ROMAN.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$sejda$sambox$pdmodel$font$PDType1Font$StandardFont[StandardFont.TIMES_BOLD.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$sejda$sambox$pdmodel$font$PDType1Font$StandardFont[StandardFont.TIMES_ITALIC.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$org$sejda$sambox$pdmodel$font$PDType1Font$StandardFont[StandardFont.TIMES_BOLD_ITALIC.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$org$sejda$sambox$pdmodel$font$PDType1Font$StandardFont[StandardFont.HELVETICA.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$org$sejda$sambox$pdmodel$font$PDType1Font$StandardFont[StandardFont.HELVETICA_BOLD.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$org$sejda$sambox$pdmodel$font$PDType1Font$StandardFont[StandardFont.HELVETICA_OBLIQUE.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$org$sejda$sambox$pdmodel$font$PDType1Font$StandardFont[StandardFont.HELVETICA_BOLD_OBLIQUE.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$org$sejda$sambox$pdmodel$font$PDType1Font$StandardFont[StandardFont.COURIER.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$org$sejda$sambox$pdmodel$font$PDType1Font$StandardFont[StandardFont.COURIER_BOLD.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$org$sejda$sambox$pdmodel$font$PDType1Font$StandardFont[StandardFont.COURIER_OBLIQUE.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$org$sejda$sambox$pdmodel$font$PDType1Font$StandardFont[StandardFont.COURIER_BOLD_OBLIQUE.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$org$sejda$sambox$pdmodel$font$PDType1Font$StandardFont[StandardFont.SYMBOL.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$org$sejda$sambox$pdmodel$font$PDType1Font$StandardFont[StandardFont.ZAPF_DINGBATS.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PDType1Font loadStandardFont(StandardFont font) {
        switch (AnonymousClass1.$SwitchMap$org$sejda$sambox$pdmodel$font$PDType1Font$StandardFont[font.ordinal()]) {
            case 1:
                return new PDType1Font("Times-Roman");
            case 2:
                return new PDType1Font("Times-Bold");
            case 3:
                return new PDType1Font("Times-Italic");
            case 4:
                return new PDType1Font("Times-BoldItalic");
            case 5:
                return new PDType1Font("Helvetica");
            case 6:
                return new PDType1Font("Helvetica-Bold");
            case 7:
                return new PDType1Font("Helvetica-Oblique");
            case 8:
                return new PDType1Font("Helvetica-BoldOblique");
            case 9:
                return new PDType1Font("Courier");
            case 10:
                return new PDType1Font("Courier-Bold");
            case 11:
                return new PDType1Font("Courier-Oblique");
            case 12:
                return new PDType1Font("Courier-BoldOblique");
            case CharUtils.ASCII_CARRIAGE_RETURN /* 13 */:
                return new PDType1Font("Symbol");
            case TIFFExtension.JPEG_PROC_LOSSLESS /* 14 */:
                return new PDType1Font("ZapfDingbats");
            default:
                throw new IncompatibleClassChangeError();
        }
    }

    public static PDType1Font TIMES_ROMAN() {
        return getStandardFont(StandardFont.TIMES_ROMAN);
    }

    public static PDType1Font TIMES_BOLD() {
        return getStandardFont(StandardFont.TIMES_BOLD);
    }

    public static PDType1Font TIMES_ITALIC() {
        return getStandardFont(StandardFont.TIMES_ITALIC);
    }

    public static PDType1Font TIMES_BOLD_ITALIC() {
        return getStandardFont(StandardFont.TIMES_BOLD_ITALIC);
    }

    public static PDType1Font HELVETICA() {
        return getStandardFont(StandardFont.HELVETICA);
    }

    public static PDType1Font HELVETICA_BOLD() {
        return getStandardFont(StandardFont.HELVETICA_BOLD);
    }

    public static PDType1Font HELVETICA_OBLIQUE() {
        return getStandardFont(StandardFont.HELVETICA_OBLIQUE);
    }

    public static PDType1Font HELVETICA_BOLD_OBLIQUE() {
        return getStandardFont(StandardFont.HELVETICA_BOLD_OBLIQUE);
    }

    public static PDType1Font COURIER() {
        return getStandardFont(StandardFont.COURIER);
    }

    public static PDType1Font COURIER_BOLD() {
        return getStandardFont(StandardFont.COURIER_BOLD);
    }

    public static PDType1Font COURIER_OBLIQUE() {
        return getStandardFont(StandardFont.COURIER_OBLIQUE);
    }

    public static PDType1Font COURIER_BOLD_OBLIQUE() {
        return getStandardFont(StandardFont.COURIER_BOLD_OBLIQUE);
    }

    public static PDType1Font SYMBOL() {
        return getStandardFont(StandardFont.SYMBOL);
    }

    public static PDType1Font ZAPF_DINGBATS() {
        return getStandardFont(StandardFont.ZAPF_DINGBATS);
    }

    private PDType1Font(String baseFont) {
        String fontName;
        super(baseFont);
        this.isOriginalEmbeddedMissing = false;
        this.isMappingFallbackUsed = false;
        this.dict.setItem(COSName.SUBTYPE, (COSBase) COSName.TYPE1);
        this.dict.setName(COSName.BASE_FONT, baseFont);
        if ("ZapfDingbats".equals(baseFont)) {
            this.encoding = ZapfDingbatsEncoding.INSTANCE;
        } else if ("Symbol".equals(baseFont)) {
            this.encoding = SymbolEncoding.INSTANCE;
        } else {
            this.encoding = WinAnsiEncoding.INSTANCE;
            this.dict.setItem(COSName.ENCODING, (COSBase) COSName.WIN_ANSI_ENCODING);
        }
        this.codeToBytesMap = new ConcurrentHashMap();
        this.type1font = null;
        FontMapping<FontBoxFont> mapping = FontMappers.instance().getFontBoxFont(getBaseFont(), getFontDescriptor());
        this.genericFont = mapping.getFont();
        if (mapping.isFallback()) {
            try {
                fontName = this.genericFont.getName();
            } catch (IOException e) {
                fontName = "?";
            }
            LOG.warn("Using fallback font " + fontName + " for base font " + getBaseFont());
            this.isMappingFallbackUsed = true;
        }
        this.isEmbedded = false;
        this.isDamaged = false;
        this.fontMatrixTransform = new AffineTransform();
    }

    public PDType1Font(PDDocument doc, InputStream pfbIn) throws IOException {
        this(doc, pfbIn, null);
    }

    public PDType1Font(PDDocument doc, InputStream pfbIn, Encoding encoding) throws IOException {
        this.isOriginalEmbeddedMissing = false;
        this.isMappingFallbackUsed = false;
        PDType1FontEmbedder embedder = new PDType1FontEmbedder(doc, this.dict, pfbIn, encoding);
        Optional optionalOfNullable = Optional.ofNullable(encoding);
        Objects.requireNonNull(embedder);
        this.encoding = (Encoding) optionalOfNullable.orElseGet(embedder::getFontEncoding);
        this.glyphList = embedder.getGlyphList();
        this.type1font = embedder.getType1Font();
        this.genericFont = embedder.getType1Font();
        this.isEmbedded = true;
        this.isDamaged = false;
        this.fontMatrixTransform = new AffineTransform();
        this.codeToBytesMap = new HashMap();
    }

    public PDType1Font(COSDictionary fontDictionary) throws IOException {
        super(fontDictionary);
        this.isOriginalEmbeddedMissing = false;
        this.isMappingFallbackUsed = false;
        this.codeToBytesMap = new HashMap();
        PDFontDescriptor fd = getFontDescriptor();
        Type1Font t1 = null;
        boolean fontIsDamaged = false;
        if (fd != null) {
            PDStream fontFile3 = fd.getFontFile3();
            if (fontFile3 != null) {
                throw new FontFileMismatchException("Use PDType1CFont for FontFile3");
            }
            PDStream fontFile = fd.getFontFile();
            if (fontFile != null) {
                try {
                    COSStream stream = fontFile.getCOSObject();
                    int length1 = stream.getInt(COSName.LENGTH1);
                    int length2 = stream.getInt(COSName.LENGTH2);
                    byte[] bytes = fontFile.toByteArray();
                    if (bytes.length <= 0) {
                        throw new IOException("Font data unavailable");
                    }
                    int length12 = repairLength1(bytes, length1);
                    int length22 = repairLength2(bytes, length12, length2);
                    if ((bytes[0] & 255) == 128) {
                        t1 = Type1Font.createWithPFB(bytes);
                    } else {
                        if (length12 < 0 || length12 > length12 + length22) {
                            throw new IOException("Invalid length data, actual length: " + bytes.length + ", /Length1: " + length12 + ", /Length2: " + length22);
                        }
                        byte[] segment1 = Arrays.copyOfRange(bytes, 0, length12);
                        byte[] segment2 = Arrays.copyOfRange(bytes, length12, length12 + length22);
                        if (length12 > 0 && length22 > 0) {
                            t1 = Type1Font.createWithSegments(segment1, segment2);
                        }
                    }
                } catch (DamagedFontException e) {
                    LOG.warn("Can't read damaged embedded Type1 font " + fd.getFontName());
                    fontIsDamaged = true;
                } catch (IOException e2) {
                    LOG.error("Can't read the embedded Type1 font " + fd.getFontName(), e2);
                    fontIsDamaged = true;
                }
            }
        }
        this.isEmbedded = t1 != null;
        this.isDamaged = fontIsDamaged;
        this.type1font = t1;
        if (this.type1font != null) {
            this.genericFont = this.type1font;
        } else {
            this.isOriginalEmbeddedMissing = true;
            FontMapping<FontBoxFont> mapping = FontMappers.instance().getFontBoxFont(getBaseFont(), fd);
            this.genericFont = mapping.getFont();
            if (mapping.isFallback()) {
                LOG.warn("Using fallback font " + this.genericFont.getName() + " for " + getBaseFont());
                this.isMappingFallbackUsed = true;
            }
        }
        readEncoding();
        this.fontMatrixTransform = getFontMatrix().createAffineTransform();
        this.fontMatrixTransform.scale(1000.0d, 1000.0d);
    }

    private int repairLength1(byte[] bytes, int length1) {
        int offset = Math.max(0, length1 - 4);
        if (offset <= 0 || offset > bytes.length - 4) {
            offset = bytes.length - 4;
        }
        int offset2 = findBinaryOffsetAfterExec(bytes, offset);
        if (offset2 == 0 && length1 > 0) {
            offset2 = findBinaryOffsetAfterExec(bytes, bytes.length - 4);
        }
        if (length1 - offset2 != 0 && offset2 > 0) {
            if (LOG.isWarnEnabled()) {
                LOG.warn("Ignored invalid Length1 " + length1 + " for Type 1 font " + getName());
            }
            return offset2;
        }
        return length1;
    }

    private static int findBinaryOffsetAfterExec(byte[] bytes, int startOffset) {
        int offset = startOffset;
        while (true) {
            if (offset <= 0) {
                break;
            }
            if (bytes[offset + 0] == 101 && bytes[offset + 1] == 120 && bytes[offset + 2] == 101 && bytes[offset + 3] == 99) {
                offset += 4;
                while (offset < bytes.length && (bytes[offset] == 13 || bytes[offset] == 10 || bytes[offset] == 32 || bytes[offset] == 9)) {
                    offset++;
                }
            } else {
                offset--;
            }
        }
        return offset;
    }

    private int repairLength2(byte[] bytes, int length1, int length2) {
        if (length2 < 0 || length2 > bytes.length - length1) {
            LOG.warn("Ignored invalid Length2 " + length2 + " for Type 1 font " + getName());
            return bytes.length - length1;
        }
        return length2;
    }

    public final String getBaseFont() {
        return this.dict.getNameAsString(COSName.BASE_FONT);
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public float getHeight(int code2) throws IOException {
        if (getStandard14AFM() != null) {
            String afmName = getEncoding().getName(code2);
            return getStandard14AFM().getCharacterHeight(afmName);
        }
        String name = codeToName(code2);
        return (float) this.genericFont.getPath(name).getBounds().getHeight();
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFont
    protected byte[] encode(int unicode) throws NumberFormatException, IOException {
        byte[] bytes = this.codeToBytesMap.get(Integer.valueOf(unicode));
        if (bytes != null) {
            return bytes;
        }
        String name = getGlyphList().codePointToName(unicode);
        if (isStandard14()) {
            if (!this.encoding.contains(name)) {
                throw new IllegalArgumentException(String.format("U+%04X ('%s') is not available in this font %s encoding: %s", Integer.valueOf(unicode), name, getName(), this.encoding.getEncodingName()));
            }
            if (".notdef".equals(name)) {
                throw new IllegalArgumentException(String.format("No glyph for U+%04X in this font %s", Integer.valueOf(unicode), getName()));
            }
        } else {
            if (!this.encoding.contains(name)) {
                throw new IllegalArgumentException(String.format("U+%04X ('%s') is not available in this font %s (generic: %s) encoding: %s", Integer.valueOf(unicode), name, getName(), this.genericFont.getName(), this.encoding.getEncodingName()));
            }
            String nameInFont = getNameInFont(name);
            if (nameInFont.equals(".notdef") || !this.genericFont.hasGlyph(nameInFont)) {
                throw new IllegalArgumentException(String.format("No glyph for U+%04X in font %s (generic: %s)", Integer.valueOf(unicode), getName(), this.genericFont.getName()));
            }
        }
        Map<String, Integer> inverted = this.encoding.getNameToCodeMap();
        int code2 = inverted.get(name).intValue();
        if (code2 < 0) {
            throw new IllegalArgumentException(String.format("U+%04X ('%s') is not available in this font %s (generic: %s), encoding: %s", Integer.valueOf(unicode), name, getName(), this.genericFont.getName(), this.encoding.getEncodingName()));
        }
        byte[] bytes2 = {(byte) code2};
        this.codeToBytesMap.put(Integer.valueOf(unicode), bytes2);
        return bytes2;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public float getWidthFromFont(int code2) throws IOException {
        String name = codeToName(code2);
        if (!this.isEmbedded && ".notdef".equals(name)) {
            return 250.0f;
        }
        float width = this.genericFont.getWidth(name);
        Point2D.Float r0 = new Point2D.Float(width, 0.0f);
        this.fontMatrixTransform.transform(r0, r0);
        return (float) r0.getX();
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public boolean isEmbedded() {
        return this.isEmbedded;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFont, org.sejda.sambox.pdmodel.font.PDFontLike
    public float getAverageFontWidth() {
        if (getStandard14AFM() != null) {
            return getStandard14AFM().getAverageCharacterWidth();
        }
        return super.getAverageFontWidth();
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFont
    public int readCode(InputStream in) throws IOException {
        return in.read();
    }

    @Override // org.sejda.sambox.pdmodel.font.PDSimpleFont
    protected Encoding readEncodingFromFont() throws IOException {
        if (!isEmbedded() && getStandard14AFM() != null) {
            return new Type1Encoding(getStandard14AFM());
        }
        if (this.genericFont instanceof EncodedFont) {
            return Type1Encoding.fromFontBox(this.genericFont.getEncoding());
        }
        return StandardEncoding.INSTANCE;
    }

    public Type1Font getType1Font() {
        return this.type1font;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDSimpleFont
    public FontBoxFont getFontBoxFont() {
        return this.genericFont;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public String getName() {
        return getBaseFont();
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public BoundingBox getBoundingBox() throws IOException {
        if (this.fontBBox == null) {
            this.fontBBox = generateBoundingBox();
        }
        return this.fontBBox;
    }

    private BoundingBox generateBoundingBox() throws IOException {
        if (getFontDescriptor() != null) {
            PDRectangle bbox = getFontDescriptor().getFontBoundingBox();
            if (Objects.nonNull(bbox) && (bbox.getLowerLeftX() != 0.0f || bbox.getLowerLeftY() != 0.0f || bbox.getUpperRightX() != 0.0f || bbox.getUpperRightY() != 0.0f)) {
                return new BoundingBox(bbox.getLowerLeftX(), bbox.getLowerLeftY(), bbox.getUpperRightX(), bbox.getUpperRightY());
            }
        }
        return this.genericFont.getFontBBox();
    }

    public String codeToName(int code2) throws IOException {
        String name = (String) Optional.ofNullable(getEncoding()).map(e -> {
            return e.getName(code2);
        }).orElse(".notdef");
        return getNameInFont(name);
    }

    private String getNameInFont(String name) throws NumberFormatException, IOException {
        Integer code2;
        if (isEmbedded() || this.genericFont.hasGlyph(name)) {
            return name;
        }
        String altName = ALT_NAMES.get(name);
        if (altName != null && !name.equals(".notdef") && this.genericFont.hasGlyph(altName)) {
            return altName;
        }
        String unicodes = getGlyphList().toUnicode(name);
        if (unicodes != null && unicodes.length() == 1) {
            String uniName = UniUtil.getUniNameOfCodePoint(unicodes.codePointAt(0));
            if (this.genericFont.hasGlyph(uniName)) {
                return uniName;
            }
            if ("SymbolMT".equals(this.genericFont.getName()) && (code2 = SymbolEncoding.INSTANCE.getNameToCodeMap().get(name)) != null) {
                String uniName2 = UniUtil.getUniNameOfCodePoint(code2.intValue() + 61440);
                if (this.genericFont.hasGlyph(uniName2)) {
                    return uniName2;
                }
                return ".notdef";
            }
            return ".notdef";
        }
        return ".notdef";
    }

    @Override // org.sejda.sambox.pdmodel.font.PDSimpleFont
    public GeneralPath getPath(String name) throws IOException {
        if (name.equals(".notdef") && !this.isEmbedded) {
            return new GeneralPath();
        }
        return this.genericFont.getPath(getNameInFont(name));
    }

    @Override // org.sejda.sambox.pdmodel.font.PDSimpleFont
    public boolean hasGlyph(String name) throws IOException {
        return this.genericFont.hasGlyph(getNameInFont(name));
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFont, org.sejda.sambox.pdmodel.font.PDFontLike
    public final Matrix getFontMatrix() {
        if (this.fontMatrix == null) {
            List<Number> numbers = null;
            try {
                numbers = this.genericFont.getFontMatrix();
            } catch (IOException e) {
                this.fontMatrix = DEFAULT_FONT_MATRIX;
            }
            if (numbers != null && numbers.size() == 6) {
                this.fontMatrix = new Matrix(numbers.get(0).floatValue(), numbers.get(1).floatValue(), numbers.get(2).floatValue(), numbers.get(3).floatValue(), numbers.get(4).floatValue(), numbers.get(5).floatValue());
            } else {
                return super.getFontMatrix();
            }
        }
        return this.fontMatrix;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public boolean isDamaged() {
        return this.isDamaged;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public boolean isOriginalEmbeddedMissing() {
        return this.isOriginalEmbeddedMissing;
    }

    @Override // org.sejda.sambox.pdmodel.font.PDFontLike
    public boolean isMappingFallbackUsed() {
        return this.isMappingFallbackUsed;
    }
}
