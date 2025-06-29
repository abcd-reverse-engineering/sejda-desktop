package org.sejda.sambox.pdmodel.font;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import org.apache.fontbox.FontBoxFont;
import org.apache.fontbox.ttf.OpenTypeFont;
import org.apache.fontbox.ttf.TTFParser;
import org.apache.fontbox.ttf.TrueTypeFont;
import org.apache.fontbox.type1.Type1Font;
import org.sejda.commons.util.RequireUtils;
import org.sejda.sambox.SAMBox;
import org.sejda.sambox.pdmodel.documentinterchange.taggedpdf.StandardStructureTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/FontMapperImpl.class */
final class FontMapperImpl implements FontMapper {
    private final CompletableFuture<Map<String, FontInfo>> fontInfoByName;
    private final TrueTypeFont lastResortFont;
    private static final Logger LOG = LoggerFactory.getLogger(FontMapperImpl.class);
    private final Map<String, List<String>> substitutes;

    FontMapperImpl() {
        this(loadFontProvider());
    }

    FontMapperImpl(FontProvider fontProvider) {
        this.substitutes = new HashMap();
        this.fontInfoByName = CompletableFuture.supplyAsync(() -> {
            Map<String, FontInfo> map = new LinkedHashMap<>();
            fontProvider.getFontInfo().forEach(info -> {
                getPostScriptNames(info.getPostScriptName()).forEach(name -> {
                    map.put(name.toLowerCase(Locale.ENGLISH), info);
                });
            });
            return map;
        });
        addSubstitutes("Courier", new ArrayList(Arrays.asList("CourierNew", "CourierNewPSMT", "LiberationMono", "NimbusMonL-Regu")));
        addSubstitutes("Courier-Bold", new ArrayList(Arrays.asList("CourierNewPS-BoldMT", "CourierNew-Bold", "LiberationMono-Bold", "NimbusMonL-Bold")));
        addSubstitutes("Courier-Oblique", new ArrayList(Arrays.asList("CourierNewPS-ItalicMT", "CourierNew-Italic", "LiberationMono-Italic", "NimbusMonL-ReguObli")));
        addSubstitutes("Courier-BoldOblique", new ArrayList(Arrays.asList("CourierNewPS-BoldItalicMT", "CourierNew-BoldItalic", "LiberationMono-BoldItalic", "NimbusMonL-BoldObli")));
        addSubstitutes("Helvetica", new ArrayList(Arrays.asList("ArialMT", "Arial", "LiberationSans", "NimbusSanL-Regu")));
        addSubstitutes("Helvetica-Bold", new ArrayList(Arrays.asList("Arial-BoldMT", "Arial-Bold", "LiberationSans-Bold", "NimbusSanL-Bold")));
        addSubstitutes("Helvetica-Oblique", new ArrayList(Arrays.asList("Arial-ItalicMT", "Arial-Italic", "Helvetica-Italic", "LiberationSans-Italic", "NimbusSanL-ReguItal")));
        addSubstitutes("Helvetica-BoldOblique", new ArrayList(Arrays.asList("Arial-BoldItalicMT", "Helvetica-BoldItalic", "LiberationSans-BoldItalic", "NimbusSanL-BoldItal")));
        addSubstitutes("Times-Roman", new ArrayList(Arrays.asList("TimesNewRomanPSMT", "TimesNewRoman", "TimesNewRomanPS", "LiberationSerif", "NimbusRomNo9L-Regu")));
        addSubstitutes("Times-Bold", new ArrayList(Arrays.asList("TimesNewRomanPS-BoldMT", "TimesNewRomanPS-Bold", "TimesNewRoman-Bold", "LiberationSerif-Bold", "NimbusRomNo9L-Medi")));
        addSubstitutes("Times-Italic", new ArrayList(Arrays.asList("TimesNewRomanPS-ItalicMT", "TimesNewRomanPS-Italic", "TimesNewRoman-Italic", "LiberationSerif-Italic", "NimbusRomNo9L-ReguItal")));
        addSubstitutes("Times-BoldItalic", new ArrayList(Arrays.asList("TimesNewRomanPS-BoldItalicMT", "TimesNewRomanPS-BoldItalic", "TimesNewRoman-BoldItalic", "LiberationSerif-BoldItalic", "NimbusRomNo9L-MediItal")));
        addSubstitutes("Symbol", new ArrayList(Arrays.asList("Symbol", "SymbolMT", "StandardSymL", "ChromSymbolOTF")));
        addSubstitutes("ZapfDingbats", new ArrayList(Arrays.asList("ZapfDingbatsITCbyBT-Regular", "ZapfDingbatsITC", "Dingbats", "MS-Gothic", "ChromDingbatsOTF")));
        for (String baseName : Standard14Fonts.getNames()) {
            if (getSubstitutes(baseName).isEmpty()) {
                String mappedName = Standard14Fonts.getMappedFontName(baseName);
                addSubstitutes(baseName, copySubstitutes(mappedName.toLowerCase(Locale.ENGLISH)));
            }
        }
        this.lastResortFont = loadLastResortFont();
    }

    public TrueTypeFont getLastResortFont() {
        return this.lastResortFont;
    }

    public static TrueTypeFont loadLastResortFont() {
        try {
            InputStream stream = FontMapperImpl.class.getResourceAsStream("/org/sejda/sambox/resources/ttf/LiberationSans-Regular.ttf");
            RequireUtils.requireNotNullArg(stream, "Unable to load org/sejda/sambox/resources/ttf/LiberationSans-Regular.ttf");
            return new TTFParser().parse(new BufferedInputStream(stream));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static FontProvider loadFontProvider() {
        String configuredFontProvider = System.getProperty(SAMBox.FONT_PROVIDER_PROPERTY);
        if ("noop".equalsIgnoreCase(configuredFontProvider)) {
            return new NoopFontProvider();
        }
        if (configuredFontProvider != null && !configuredFontProvider.isEmpty()) {
            try {
                LOG.debug("Trying to use {} as font provider...", configuredFontProvider);
                return (FontProvider) Class.forName(configuredFontProvider).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            } catch (Exception ex) {
                LOG.error("Failed loading custom font provider", ex);
            }
        }
        return new FileSystemFontProvider();
    }

    private Set<String> getPostScriptNames(String postScriptName) {
        Set<String> names = new HashSet<>(2);
        names.add(postScriptName);
        names.add(postScriptName.replace("-", ""));
        return names;
    }

    private List<String> copySubstitutes(String postScriptName) {
        return new ArrayList(this.substitutes.get(postScriptName));
    }

    public void addSubstitute(String match, String replace) {
        String lowerCaseMatch = match.toLowerCase(Locale.ENGLISH);
        if (!this.substitutes.containsKey(lowerCaseMatch)) {
            this.substitutes.put(lowerCaseMatch, new ArrayList());
        }
        this.substitutes.get(lowerCaseMatch).add(replace);
    }

    private void addSubstitutes(String match, List<String> replacements) {
        this.substitutes.put(match.toLowerCase(Locale.ENGLISH), replacements);
    }

    private List<String> getSubstitutes(String postScriptName) {
        List<String> subs = this.substitutes.get(postScriptName.replace(" ", "").toLowerCase(Locale.ENGLISH));
        if (subs != null) {
            return subs;
        }
        return Collections.emptyList();
    }

    private String getFallbackFontName(PDFontDescriptor fontDescriptor) {
        String fontName;
        if (fontDescriptor != null) {
            boolean isBold = false;
            String name = fontDescriptor.getFontName();
            if (name != null) {
                String lower = fontDescriptor.getFontName().toLowerCase();
                isBold = lower.contains("bold") || lower.contains("black") || lower.contains("heavy");
            }
            if (fontDescriptor.isFixedPitch()) {
                fontName = "Courier";
                if (isBold && fontDescriptor.isItalic()) {
                    fontName = fontName + "-BoldOblique";
                } else if (isBold) {
                    fontName = fontName + "-Bold";
                } else if (fontDescriptor.isItalic()) {
                    fontName = fontName + "-Oblique";
                }
            } else if (fontDescriptor.isSerif()) {
                if (isBold && fontDescriptor.isItalic()) {
                    fontName = "Times" + "-BoldItalic";
                } else if (isBold) {
                    fontName = "Times" + "-Bold";
                } else if (fontDescriptor.isItalic()) {
                    fontName = "Times" + "-Italic";
                } else {
                    fontName = "Times" + "-Roman";
                }
            } else {
                fontName = "Helvetica";
                if (isBold && fontDescriptor.isItalic()) {
                    fontName = fontName + "-BoldOblique";
                } else if (isBold) {
                    fontName = fontName + "-Bold";
                } else if (fontDescriptor.isItalic()) {
                    fontName = fontName + "-Oblique";
                }
            }
        } else {
            fontName = "Times-Roman";
        }
        return fontName;
    }

    @Override // org.sejda.sambox.pdmodel.font.FontMapper
    public FontMapping<TrueTypeFont> getTrueTypeFont(String baseFont, PDFontDescriptor fontDescriptor) {
        TrueTypeFont ttf = findFont(FontFormat.TTF, baseFont);
        if (ttf != null) {
            return new FontMapping<>(ttf, false);
        }
        String fontName = getFallbackFontName(fontDescriptor);
        TrueTypeFont ttf2 = findFont(FontFormat.TTF, fontName);
        if (ttf2 == null) {
            ttf2 = this.lastResortFont;
        }
        return new FontMapping<>(ttf2, true);
    }

    @Override // org.sejda.sambox.pdmodel.font.FontMapper
    public FontMapping<FontBoxFont> getFontBoxFont(String baseFont, PDFontDescriptor fontDescriptor) {
        FontBoxFont font = findFontBoxFont(baseFont);
        if (font != null) {
            return new FontMapping<>(font, false);
        }
        String fallbackName = getFallbackFontName(fontDescriptor);
        TrueTypeFont trueTypeFontFindFontBoxFont = findFontBoxFont(fallbackName);
        if (trueTypeFontFindFontBoxFont == null) {
            trueTypeFontFindFontBoxFont = this.lastResortFont;
        }
        return new FontMapping<>(trueTypeFontFindFontBoxFont, true);
    }

    private FontBoxFont findFontBoxFont(String postScriptName) {
        Type1Font t1 = findFont(FontFormat.PFB, postScriptName);
        if (t1 != null) {
            return t1;
        }
        TrueTypeFont ttf = findFont(FontFormat.TTF, postScriptName);
        if (ttf != null) {
            return ttf;
        }
        return findFont(FontFormat.OTF, postScriptName);
    }

    private FontBoxFont findFont(FontFormat format, String postScriptName) {
        if (postScriptName == null) {
            return null;
        }
        FontInfo info = getFont(format, postScriptName);
        if (info != null) {
            return info.getFont();
        }
        FontInfo info2 = getFont(format, postScriptName.replace("-", ""));
        if (info2 != null) {
            return info2.getFont();
        }
        for (String substituteName : getSubstitutes(postScriptName)) {
            FontInfo info3 = getFont(format, substituteName);
            if (info3 != null) {
                return info3.getFont();
            }
        }
        FontInfo info4 = getFont(format, postScriptName.replaceAll(",", "-"));
        if (info4 != null) {
            return info4.getFont();
        }
        FontInfo info5 = getFont(format, postScriptName + "-Regular");
        if (info5 != null) {
            return info5.getFont();
        }
        return null;
    }

    private FontInfo getFont(FontFormat format, String postScriptName) {
        if (postScriptName.contains("+")) {
            postScriptName = postScriptName.substring(postScriptName.indexOf(43) + 1);
        }
        FontInfo info = this.fontInfoByName.join().get(postScriptName.toLowerCase(Locale.ENGLISH));
        if (info != null && info.getFormat() == format) {
            return info;
        }
        return null;
    }

    @Override // org.sejda.sambox.pdmodel.font.FontMapper
    public CIDFontMapping getCIDFont(String baseFont, PDFontDescriptor fontDescriptor, PDCIDSystemInfo cidSystemInfo) {
        OpenTypeFont otf1 = findFont(FontFormat.OTF, baseFont);
        if (otf1 != null) {
            return new CIDFontMapping(otf1, null, false);
        }
        TrueTypeFont ttf = findFont(FontFormat.TTF, baseFont);
        if (ttf != null) {
            return new CIDFontMapping(null, ttf, false);
        }
        if (cidSystemInfo != null) {
            String collection = cidSystemInfo.getRegistry() + "-" + cidSystemInfo.getOrdering();
            if (collection.equals("Adobe-GB1") || collection.equals("Adobe-CNS1") || collection.equals("Adobe-Japan1") || collection.equals("Adobe-Korea1")) {
                PriorityQueue<FontMatch> queue = getFontMatches(fontDescriptor, cidSystemInfo);
                FontMatch bestMatch = queue.poll();
                if (bestMatch != null) {
                    OpenTypeFont font = bestMatch.info.getFont();
                    if (font instanceof OpenTypeFont) {
                        return new CIDFontMapping(font, null, true);
                    }
                    if (font != null) {
                        return new CIDFontMapping(null, font, true);
                    }
                }
            }
        }
        return new CIDFontMapping(null, this.lastResortFont, true);
    }

    private PriorityQueue<FontMatch> getFontMatches(PDFontDescriptor fontDescriptor, PDCIDSystemInfo cidSystemInfo) {
        PriorityQueue<FontMatch> queue = new PriorityQueue<>(20);
        for (FontInfo info : this.fontInfoByName.join().values()) {
            if (cidSystemInfo == null || isCharSetMatch(cidSystemInfo, info)) {
                FontMatch match = new FontMatch(info);
                if (fontDescriptor.getPanose() != null && info.getPanose() != null) {
                    PDPanoseClassification panose = fontDescriptor.getPanose().getPanose();
                    if (panose.getFamilyKind() == info.getPanose().getFamilyKind()) {
                        if (panose.getFamilyKind() != 0 || ((!info.getPostScriptName().toLowerCase().contains("barcode") && !info.getPostScriptName().startsWith(StandardStructureTypes.CODE)) || probablyBarcodeFont(fontDescriptor))) {
                            if (panose.getSerifStyle() == info.getPanose().getSerifStyle()) {
                                match.score += 2.0d;
                            } else if (panose.getSerifStyle() >= 2 && panose.getSerifStyle() <= 5 && info.getPanose().getSerifStyle() >= 2 && info.getPanose().getSerifStyle() <= 5) {
                                match.score += 1.0d;
                            } else if (panose.getSerifStyle() >= 11 && panose.getSerifStyle() <= 13 && info.getPanose().getSerifStyle() >= 11 && info.getPanose().getSerifStyle() <= 13) {
                                match.score += 1.0d;
                            } else if (panose.getSerifStyle() != 0 && info.getPanose().getSerifStyle() != 0) {
                                match.score -= 1.0d;
                            }
                            int weight = info.getPanose().getWeight();
                            int weightClass = info.getWeightClassAsPanose();
                            if (Math.abs(weight - weightClass) > 2) {
                                weight = weightClass;
                            }
                            if (panose.getWeight() == weight) {
                                match.score += 2.0d;
                            } else if (panose.getWeight() > 1 && weight > 1) {
                                float dist = Math.abs(panose.getWeight() - weight);
                                match.score += 1.0d - (dist * 0.5d);
                            }
                        }
                    }
                    queue.add(match);
                } else {
                    if (fontDescriptor.getFontWeight() > 0.0f && info.getWeightClass() > 0) {
                        float dist2 = Math.abs(fontDescriptor.getFontWeight() - info.getWeightClass());
                        match.score += 1.0d - ((dist2 / 100.0f) * 0.5d);
                    }
                    queue.add(match);
                }
            }
        }
        return queue;
    }

    private static boolean probablyBarcodeFont(PDFontDescriptor fontDescriptor) {
        String ff = (String) Optional.ofNullable(fontDescriptor.getFontFamily()).orElse("");
        String fn = (String) Optional.ofNullable(fontDescriptor.getFontName()).orElse("");
        return ff.startsWith(StandardStructureTypes.CODE) || ff.toLowerCase().contains("barcode") || fn.startsWith(StandardStructureTypes.CODE) || fn.toLowerCase().contains("barcode");
    }

    private boolean isCharSetMatch(PDCIDSystemInfo cidSystemInfo, FontInfo info) {
        if (info.getCIDSystemInfo() != null) {
            return info.getCIDSystemInfo().getRegistry().equals(cidSystemInfo.getRegistry()) && info.getCIDSystemInfo().getOrdering().equals(cidSystemInfo.getOrdering());
        }
        long codePageRange = info.getCodePageRange();
        if ("MalgunGothic-Semilight".equals(info.getPostScriptName())) {
            codePageRange &= ((131072 | 262144) | 1048576) ^ (-1);
        }
        if (cidSystemInfo.getOrdering().equals("GB1") && (codePageRange & 262144) == 262144) {
            return true;
        }
        if (cidSystemInfo.getOrdering().equals("CNS1") && (codePageRange & 1048576) == 1048576) {
            return true;
        }
        if (cidSystemInfo.getOrdering().equals("Japan1") && (codePageRange & 131072) == 131072) {
            return true;
        }
        return cidSystemInfo.getOrdering().equals("Korea1") && ((codePageRange & 524288) == 524288 || (codePageRange & 2097152) == 2097152);
    }

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/FontMapperImpl$FontMatch.class */
    private static class FontMatch implements Comparable<FontMatch> {
        double score;
        final FontInfo info;

        FontMatch(FontInfo info) {
            this.info = info;
        }

        @Override // java.lang.Comparable
        public int compareTo(FontMatch match) {
            return Double.compare(match.score, this.score);
        }
    }
}
