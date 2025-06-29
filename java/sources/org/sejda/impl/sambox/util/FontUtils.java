package org.sejda.impl.sambox.util;

import java.awt.geom.GeneralPath;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.apache.commons.lang3.StringUtils;
import org.apache.fontbox.ttf.TrueTypeFont;
import org.sejda.commons.util.IOUtils;
import org.sejda.commons.util.RequireUtils;
import org.sejda.impl.sambox.component.TextWithFont;
import org.sejda.impl.sambox.component.font.FallbackFontsProvider;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.exception.UnsupportedTextException;
import org.sejda.model.pdf.StandardType1Font;
import org.sejda.model.pdf.font.FontResource;
import org.sejda.model.pdf.font.Type0FontsProvider;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.filter.TIFFExtension;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.font.FontMappers;
import org.sejda.sambox.pdmodel.font.FontMapping;
import org.sejda.sambox.pdmodel.font.PDCIDFont;
import org.sejda.sambox.pdmodel.font.PDFont;
import org.sejda.sambox.pdmodel.font.PDFontDescriptor;
import org.sejda.sambox.pdmodel.font.PDSimpleFont;
import org.sejda.sambox.pdmodel.font.PDType0Font;
import org.sejda.sambox.pdmodel.font.PDType1Font;
import org.sejda.sambox.pdmodel.font.PDType3Font;
import org.sejda.sambox.pdmodel.font.PDVectorFont;
import org.sejda.sambox.util.BidiUtils;
import org.sejda.sambox.util.CharUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/util/FontUtils.class */
public final class FontUtils {
    private static final Logger LOG = LoggerFactory.getLogger(FontUtils.class);
    public static final FontResource[] TYPE0FONTS = (FontResource[]) StreamSupport.stream(ServiceLoader.load(Type0FontsProvider.class).spliterator(), false).flatMap(p -> {
        return p.getFonts().stream();
    }).sorted(Comparator.comparingInt((v0) -> {
        return v0.priority();
    })).toArray(x$0 -> {
        return new FontResource[x$0];
    });
    public static final List<FallbackFontsProvider> FALLBACK_FONTS_PROVIDERS = (List) StreamSupport.stream(ServiceLoader.load(FallbackFontsProvider.class).spliterator(), false).sorted(Comparator.comparingInt((v0) -> {
        return v0.getPriority();
    })).collect(Collectors.toList());
    private static Map<PDDocument, Map<String, PDFont>> loadedFontCache = new HashMap();
    public static final String REMARK_FROM_SEJDA_FONT_RESOURCE = "FromSejdaFontResource";

    private FontUtils() {
    }

    /* renamed from: org.sejda.impl.sambox.util.FontUtils$1, reason: invalid class name */
    /* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/util/FontUtils$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$sejda$model$pdf$StandardType1Font = new int[StandardType1Font.values().length];

        static {
            try {
                $SwitchMap$org$sejda$model$pdf$StandardType1Font[StandardType1Font.TIMES_ROMAN.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$sejda$model$pdf$StandardType1Font[StandardType1Font.TIMES_BOLD.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$sejda$model$pdf$StandardType1Font[StandardType1Font.TIMES_ITALIC.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$org$sejda$model$pdf$StandardType1Font[StandardType1Font.TIMES_BOLD_ITALIC.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$org$sejda$model$pdf$StandardType1Font[StandardType1Font.HELVETICA.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$org$sejda$model$pdf$StandardType1Font[StandardType1Font.HELVETICA_BOLD.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$org$sejda$model$pdf$StandardType1Font[StandardType1Font.HELVETICA_OBLIQUE.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$org$sejda$model$pdf$StandardType1Font[StandardType1Font.HELVETICA_BOLD_OBLIQUE.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$org$sejda$model$pdf$StandardType1Font[StandardType1Font.CURIER.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$org$sejda$model$pdf$StandardType1Font[StandardType1Font.CURIER_BOLD.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$org$sejda$model$pdf$StandardType1Font[StandardType1Font.CURIER_OBLIQUE.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$org$sejda$model$pdf$StandardType1Font[StandardType1Font.CURIER_BOLD_OBLIQUE.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$org$sejda$model$pdf$StandardType1Font[StandardType1Font.SYMBOL.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$org$sejda$model$pdf$StandardType1Font[StandardType1Font.ZAPFDINGBATS.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
        }
    }

    public static PDType1Font getStandardType1Font(StandardType1Font st1Font) {
        switch (AnonymousClass1.$SwitchMap$org$sejda$model$pdf$StandardType1Font[st1Font.ordinal()]) {
            case 1:
                return PDType1Font.TIMES_ROMAN();
            case 2:
                return PDType1Font.TIMES_BOLD();
            case 3:
                return PDType1Font.TIMES_ITALIC();
            case 4:
                return PDType1Font.TIMES_BOLD_ITALIC();
            case 5:
                return PDType1Font.HELVETICA();
            case 6:
                return PDType1Font.HELVETICA_BOLD();
            case 7:
                return PDType1Font.HELVETICA_OBLIQUE();
            case 8:
                return PDType1Font.HELVETICA_BOLD_OBLIQUE();
            case 9:
                return PDType1Font.COURIER();
            case 10:
                return PDType1Font.COURIER_BOLD();
            case 11:
                return PDType1Font.COURIER_OBLIQUE();
            case 12:
                return PDType1Font.COURIER_BOLD_OBLIQUE();
            case CharUtils.ASCII_CARRIAGE_RETURN /* 13 */:
                return PDType1Font.SYMBOL();
            case TIFFExtension.JPEG_PROC_LOSSLESS /* 14 */:
                return PDType1Font.ZAPF_DINGBATS();
            default:
                throw new IncompatibleClassChangeError();
        }
    }

    public static PDFont fontOrFallback(String text, PDFont font, PDDocument document) throws IOException {
        if (!canDisplay(text, font)) {
            PDFont fallback = findFontFor(document, text, isBold(font), font);
            String fallbackName = fallback == null ? null : fallback.getName();
            LOG.debug("Text '{}' cannot be written with font {}, using fallback {}", new Object[]{text, font.getName(), fallbackName});
            return fallback;
        }
        return font;
    }

    public static void clearLoadedFontCache() {
        loadedFontCache.clear();
    }

    public static void clearLoadedFontCache(PDDocument document) {
        loadedFontCache.remove(document);
    }

    public static PDFont loadFont(PDDocument document, FontResource font) throws IOException {
        if (!loadedFontCache.containsKey(document)) {
            loadedFontCache.put(document, new HashMap());
        }
        Map<String, PDFont> docCache = loadedFontCache.get(document);
        if (docCache.containsKey(font.getResource())) {
            return docCache.get(font.getResource());
        }
        try {
            InputStream in = font.getFontStream();
            try {
                PDType0Font loaded = PDType0Font.load(document, in);
                loaded.getTransientMetadata().put(REMARK_FROM_SEJDA_FONT_RESOURCE, "true");
                LOG.trace("Loaded font {}", loaded.getName());
                docCache.put(font.getResource(), loaded);
                if (in != null) {
                    in.close();
                }
                return loaded;
            } finally {
            }
        } catch (IOException e) {
            LOG.warn("Failed to load font " + String.valueOf(font), e);
            return null;
        }
    }

    public static PDFont findFontFor(PDDocument document, String text) {
        return findFontFor(document, text, false, null);
    }

    public static PDFont findFontFor(PDDocument document, String text, boolean bold, PDFont originalFont) throws IOException {
        for (FallbackFontsProvider provider : FALLBACK_FONTS_PROVIDERS) {
            PDFont fallback = provider.findFallbackFont(originalFont, document, text, bold);
            if (fallback != null) {
                LOG.debug("Found suitable font {} to display '{}', via provider {}", new Object[]{fallback.getName(), text, provider.getClass().getName()});
                return fallback;
            }
        }
        PDFont firstPartialMatch = null;
        for (FontResource font : TYPE0FONTS) {
            PDFont loaded = loadFont(document, font);
            if (canDisplay(text, loaded)) {
                firstPartialMatch = loaded;
                LOG.debug("Found suitable font {} to display '{}'", loaded, text);
                if (isBold(loaded) == bold) {
                    return loaded;
                }
            }
        }
        return firstPartialMatch;
    }

    public static boolean isOnlyWhitespace(String text) {
        return text.replaceAll("\\p{Zs}", "").length() == 0;
    }

    public static String removeWhitespace(String text) {
        return text.replaceAll("\\p{Zs}", "").replaceAll("\\r\\n", "").replaceAll("\\n", "");
    }

    public static boolean canDisplaySpace(PDFont font) {
        try {
            byte[] encoded = font.encode(" ");
            if (font.getStringWidth(" ") <= 0.0f) {
                return false;
            }
            return areEncodeDecodeSame(font, " ", encoded);
        } catch (IOException | IllegalArgumentException | NullPointerException | UnsupportedOperationException e) {
            return false;
        }
    }

    public static boolean canDisplay(String text, PDFont font) {
        return canDisplayString(removeWhitespace(text), font);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean canDisplayString(String text, PDFont pDFont) {
        if (pDFont == 0) {
            return false;
        }
        try {
            byte[] encoded = pDFont.encode(text);
            int[] cid2gid = null;
            if (pDFont instanceof PDType0Font) {
                PDType0Font type0Font = (PDType0Font) pDFont;
                try {
                    cid2gid = readCIDToGIDMap(type0Font.getDescendantFont());
                } catch (Exception e) {
                    LOG.warn("Exception reading CIDToGIDMap: " + e.getMessage());
                }
            }
            if (pDFont instanceof PDVectorFont) {
                PDVectorFont vectorFont = (PDVectorFont) pDFont;
                InputStream in = new ByteArrayInputStream(encoded);
                while (in.available() > 0) {
                    int code2 = pDFont.readCode(in);
                    GeneralPath path = vectorFont.getPath(code2);
                    if (path == null || path.getBounds2D().getWidth() == 0.0d) {
                        return false;
                    }
                    if (cid2gid != null && code2 < cid2gid.length && cid2gid[code2] == 0) {
                        return false;
                    }
                }
            }
            if (!"true".equals(pDFont.getTransientMetadata().get(REMARK_FROM_SEJDA_FONT_RESOURCE))) {
                return areEncodeDecodeSame(pDFont, text, encoded);
            }
            return true;
        } catch (IOException | IllegalArgumentException | NullPointerException | UnsupportedOperationException e2) {
            return false;
        }
    }

    private static int[] readCIDToGIDMap(PDCIDFont font) throws IOException {
        int[] cid2gid = null;
        COSDictionary dict = font.getCOSObject();
        COSBase map = dict.getDictionaryObject(COSName.CID_TO_GID_MAP);
        if (map instanceof COSStream) {
            COSStream stream = (COSStream) map;
            InputStream in = stream.getUnfilteredStream();
            byte[] mapAsBytes = IOUtils.toByteArray(in);
            IOUtils.closeQuietly(in);
            int numberOfInts = mapAsBytes.length / 2;
            cid2gid = new int[numberOfInts];
            int offset = 0;
            for (int index = 0; index < numberOfInts; index++) {
                int gid = ((mapAsBytes[offset] & 255) << 8) | (mapAsBytes[offset + 1] & 255);
                cid2gid[index] = gid;
                offset += 2;
            }
        }
        return cid2gid;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static double calculateBBoxHeight(String text, PDFont pDFont) {
        RequireUtils.requireNotNullArg(pDFont, "Font cannot be null");
        double maxHeight = 0.0d;
        try {
            InputStream in = new ByteArrayInputStream(pDFont.encode(text));
            while (in.available() > 0) {
                int code2 = pDFont.readCode(in);
                if (pDFont instanceof PDType3Font) {
                    maxHeight = Math.max(maxHeight, ((Double) Optional.ofNullable(((PDType3Font) pDFont).getCharProc(code2)).map((v0) -> {
                        return v0.getGlyphBBox();
                    }).map((v0) -> {
                        return v0.toGeneralPath();
                    }).map(p -> {
                        return Double.valueOf(p.getBounds2D().getHeight());
                    }).orElse(Double.valueOf(0.0d))).doubleValue());
                } else if (pDFont instanceof PDVectorFont) {
                    maxHeight = Math.max(maxHeight, ((Double) Optional.ofNullable(((PDVectorFont) pDFont).getPath(code2)).map(p2 -> {
                        return Double.valueOf(p2.getBounds2D().getHeight());
                    }).orElse(Double.valueOf(0.0d))).doubleValue());
                } else if (pDFont instanceof PDSimpleFont) {
                    PDSimpleFont simpleFont = (PDSimpleFont) pDFont;
                    String name = (String) Optional.ofNullable(simpleFont.getEncoding()).map(e -> {
                        return e.getName(code2);
                    }).orElse(null);
                    if (Objects.nonNull(name)) {
                        maxHeight = Math.max(maxHeight, simpleFont.getPath(name).getBounds2D().getHeight());
                    }
                }
            }
        } catch (IOException e2) {
            LOG.warn("An error occurred while calculating the highest glyph bbox", e2);
        }
        return maxHeight;
    }

    public static boolean isBold(PDFont font) {
        if (font.getName() == null) {
            return false;
        }
        String lowercasedName = font.getName().toLowerCase();
        return lowercasedName.contains("bold");
    }

    public static boolean isItalic(PDFont font) {
        if (font.getName() == null) {
            return false;
        }
        String lowercasedName = font.getName().toLowerCase();
        return lowercasedName.contains("italic") || lowercasedName.contains("oblique");
    }

    /* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/util/FontUtils$FontSubsetting.class */
    public static class FontSubsetting {
        public final String fontName;
        public final boolean isSubset;
        public final PDFont subsetFont;

        public FontSubsetting(PDFont subsetFont) {
            this.subsetFont = subsetFont;
            String fontName = StringUtils.trimToEmpty(subsetFont.getName());
            String[] fontNameFragments = fontName.split("\\+");
            if (fontNameFragments.length == 2 && fontNameFragments[0].length() == 6) {
                this.isSubset = true;
                this.fontName = fontNameFragments[1];
            } else {
                this.isSubset = false;
                this.fontName = null;
            }
        }

        public PDFont loadOriginalOrSimilar(PDDocument document) {
            PDFont original = loadOriginal(document);
            if (original == null) {
                return loadSimilar(document);
            }
            return original;
        }

        public PDFont loadOriginal(PDDocument document) {
            String lookupName = this.fontName.replace("-", " ");
            FontUtils.LOG.debug("Searching the system for a font matching name '{}'", lookupName);
            FontMapping<TrueTypeFont> fontMapping = FontMappers.instance().getTrueTypeFont(lookupName, null);
            if (fontMapping != null && fontMapping.getFont() != null && !fontMapping.isFallback()) {
                TrueTypeFont mappedFont = fontMapping.getFont();
                try {
                    FontUtils.LOG.debug("Original font available on the system: {}", this.fontName);
                    return PDType0Font.load(document, mappedFont.getOriginalData());
                } catch (IOException ioe) {
                    FontUtils.LOG.warn("Failed to load font from system", ioe);
                    try {
                        mappedFont.close();
                        return null;
                    } catch (IOException e) {
                        FontUtils.LOG.warn("Failed closing font", e);
                        return null;
                    }
                }
            }
            return null;
        }

        public PDFont loadSimilar(PDDocument document) {
            String lookupName = this.fontName.replace("-", " ");
            PDFontDescriptor descriptor = new PDFontDescriptor(new COSDictionary());
            descriptor.setFontName(this.fontName.split("-")[0]);
            descriptor.setForceBold(FontUtils.isBold(this.subsetFont));
            descriptor.setItalic(FontUtils.isItalic(this.subsetFont));
            FontUtils.LOG.debug("Searching the system for a font matching name '{}' and description [name:{}, bold:{}, italic:{}]", new Object[]{lookupName, descriptor.getFontName(), Boolean.valueOf(descriptor.isForceBold()), Boolean.valueOf(descriptor.isItalic())});
            FontMapping<TrueTypeFont> fontMapping = FontMappers.instance().getTrueTypeFont(lookupName, descriptor);
            if (fontMapping != null && fontMapping.getFont() != null) {
                TrueTypeFont mappedFont = fontMapping.getFont();
                try {
                    if (fontMapping.isFallback()) {
                        FontUtils.LOG.debug("Fallback font available on the system: {} (for {})", mappedFont.getName(), this.fontName);
                    } else {
                        FontUtils.LOG.debug("Original font available on the system: {}", this.fontName);
                    }
                    return PDType0Font.load(document, mappedFont.getOriginalData());
                } catch (IOException ioe) {
                    FontUtils.LOG.warn("Failed to load font from system", ioe);
                    try {
                        mappedFont.close();
                        return null;
                    } catch (Exception e) {
                        FontUtils.LOG.warn("Failed closing font", e);
                        return null;
                    }
                }
            }
            return null;
        }
    }

    public static List<String> wrapLines(String rawLabel, PDFont font, float fontSize, double maxWidth, PDDocument document) throws TaskIOException, IOException {
        List<String> lines = new ArrayList<>();
        String label = org.sejda.commons.util.StringUtils.normalizeWhitespace(rawLabel);
        StringBuilder currentString = new StringBuilder();
        double currentWidth = 0.0d;
        List<TextWithFont> resolvedStringsToFonts = resolveFonts(label, font, document);
        for (TextWithFont stringAndFont : resolvedStringsToFonts) {
            try {
                PDFont resolvedFont = stringAndFont.getFont();
                String resolvedLabel = stringAndFont.getText();
                if (Objects.isNull(resolvedFont)) {
                    throw new UnsupportedTextException("Unable to find suitable font for string \"" + resolvedLabel + "\"", resolvedLabel);
                }
                String[] words = BidiUtils.visualToLogical(resolvedLabel).split("(?<=\\b)");
                for (String word : words) {
                    double textWidth = getSimpleStringWidth(word, resolvedFont, fontSize);
                    if (textWidth > maxWidth || word.length() > 10) {
                        Iterator<Integer> codePointIterator = word.codePoints().iterator();
                        while (codePointIterator.hasNext()) {
                            int codePoint = codePointIterator.next().intValue();
                            String ch = new String(Character.toChars(codePoint));
                            double chWidth = getSimpleStringWidth(ch, resolvedFont, fontSize);
                            if (currentWidth + chWidth > maxWidth) {
                                currentString.append("-");
                                lines.add(currentString.toString().trim());
                                currentString = new StringBuilder();
                                currentWidth = 0.0d;
                            }
                            currentWidth += chWidth;
                            currentString.append(ch);
                        }
                    } else {
                        if (currentWidth + textWidth > maxWidth) {
                            lines.add(currentString.toString().trim());
                            currentString = new StringBuilder();
                            currentWidth = 0.0d;
                        }
                        currentWidth += textWidth;
                        currentString.append(word);
                    }
                }
            } catch (IOException e) {
                throw new TaskIOException(e);
            }
        }
        if (!currentString.toString().isEmpty()) {
            lines.add(currentString.toString().trim());
        }
        return lines;
    }

    public static double getSimpleStringWidth(String text, PDFont font, double fontSize) throws IOException {
        double textWidth = (font.getStringWidth(text) / 1000.0f) * fontSize;
        if (textWidth == 0.0d) {
            textWidth = (font.getAverageFontWidth() / 1000.0f) * fontSize;
        }
        return textWidth;
    }

    public static List<TextWithFont> resolveFonts(String label, PDFont font, PDDocument document) throws IOException {
        PDFont currentFont = font;
        StringBuilder currentString = new StringBuilder();
        List<TextWithFont> result = new ArrayList<>();
        Iterator<Integer> codePointIterator = label.codePoints().iterator();
        while (codePointIterator.hasNext()) {
            int codePoint = codePointIterator.next().intValue();
            String s = new String(Character.toChars(codePoint));
            PDFont f = fontOrFallback(s, font, document);
            if (s.equals(" ")) {
                if (!canDisplaySpace(f)) {
                    f = getStandardType1Font(StandardType1Font.HELVETICA);
                }
                if (f != currentFont) {
                    if (currentString.length() > 0) {
                        result.add(new TextWithFont(currentString.toString(), currentFont));
                    }
                    result.add(new TextWithFont(" ", f));
                    currentString = new StringBuilder();
                    currentFont = f;
                } else {
                    currentString.append(s);
                }
            } else if (currentFont == f) {
                currentString.append(s);
            } else {
                if (currentString.length() > 0) {
                    result.add(new TextWithFont(currentString.toString(), currentFont));
                }
                currentString = new StringBuilder(s);
                currentFont = f;
            }
        }
        result.add(new TextWithFont(currentString.toString(), currentFont));
        for (TextWithFont each : result) {
            LOG.trace("Will write '{}' with {}", each.getText(), each.getFont());
        }
        return result;
    }

    public static List<String> resolveTextFragments(String input, PDFont font) {
        List<String> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();
        if (org.sejda.core.support.util.StringUtils.isRtl(input)) {
            result.add(input);
            return result;
        }
        for (int codePoint : input.codePoints().toArray()) {
            try {
                if (font.getWidth(codePoint) == 0.0f) {
                    if (current.size() > 0) {
                        StringBuilder s = new StringBuilder();
                        Stream map = current.stream().map((v0) -> {
                            return Character.toChars(v0);
                        });
                        Objects.requireNonNull(s);
                        map.forEach(s::append);
                        result.add(s.toString());
                    }
                    result.add(new String(Character.toChars(codePoint)));
                    current = new ArrayList<>();
                } else {
                    current.add(Integer.valueOf(codePoint));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (current.size() > 0) {
            StringBuilder s2 = new StringBuilder();
            Stream map2 = current.stream().map((v0) -> {
                return Character.toChars(v0);
            });
            Objects.requireNonNull(s2);
            map2.forEach(s2::append);
            result.add(s2.toString());
        }
        return result;
    }

    public static String removeUnsupportedCharacters(String text, PDDocument doc) {
        return replaceUnsupportedCharacters(text, doc, "");
    }

    public static String replaceUnsupportedCharacters(String text, PDDocument doc, String replacement) throws IOException {
        List<TextWithFont> resolved = resolveFonts(text, PDType1Font.HELVETICA(), doc);
        Set<String> unsupported = new HashSet<>();
        resolved.forEach(tf -> {
            if (tf.getFont() == null) {
                unsupported.add(tf.getText());
            }
        });
        List<String> unsupportedSortedByLength = new ArrayList<>(unsupported);
        unsupportedSortedByLength.sort((o1, o2) -> {
            return Integer.valueOf(o2.length()).compareTo(Integer.valueOf(o1.length()));
        });
        String result = text;
        for (String s : unsupportedSortedByLength) {
            result = result.replaceAll(Pattern.quote(s), replacement);
        }
        return result;
    }

    public static boolean areEncodeDecodeSame(PDFont font, String text) throws IOException {
        return areEncodeDecodeSame(font, text, font.encode(text));
    }

    private static boolean areEncodeDecodeSame(PDFont font, String text, byte[] encoded) throws IOException {
        String decoded = decode(font, encoded);
        boolean result = org.sejda.core.support.util.StringUtils.equalsNormalized(text, decoded);
        if (!result) {
            LOG.info("Font " + font.getName() + " cannot encode/decode text: '" + text + "', decoded was: '" + decoded + "' " + org.sejda.commons.util.StringUtils.asUnicodes(text) + " " + org.sejda.commons.util.StringUtils.asUnicodes(decoded));
        }
        return result;
    }

    public static void assertEncodeDecodeSame(PDFont font, String text) throws IOException {
        if (!areEncodeDecodeSame(font, text)) {
            throw new IllegalStateException("Font " + font.getName() + " cannot encode/decode text: " + text);
        }
    }

    public static String decode(PDFont font, byte[] bytes) throws IOException {
        InputStream in = new ByteArrayInputStream(bytes);
        StringBuilder result = new StringBuilder();
        while (in.available() > 0) {
            int code2 = font.readCode(in);
            result.append(font.toUnicode(code2));
        }
        return result.toString();
    }
}
