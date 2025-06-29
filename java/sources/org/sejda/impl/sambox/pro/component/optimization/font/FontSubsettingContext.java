package org.sejda.impl.sambox.pro.component.optimization.font;

import java.io.IOException;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import org.apache.commons.lang3.StringUtils;
import org.apache.fontbox.ttf.TTFParser;
import org.apache.fontbox.ttf.TrueTypeFont;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.cos.IndirectCOSObjectIdentifier;
import org.sejda.sambox.pdmodel.font.BaseTTFSubsetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/optimization/font/FontSubsettingContext.class */
public class FontSubsettingContext {
    private static final Logger LOG = LoggerFactory.getLogger(FontSubsettingContext.class);
    private final Map<IndirectCOSObjectIdentifier, SubsettableInUseFont> subsettables = new HashMap();
    private final Map<FontFileKey, Function<String, COSStream>> subsetters = new HashMap();
    private final Map<FontFileKey, COSStream> subsetted = new HashMap();
    private final Deque<SubsettableInUseFont> fontStack = new LinkedList();

    void maybeSetCurrentSubsettableFont(COSDictionary fontDictionary, COSName fontName, COSDictionary resources) {
        maybeSetCurrentSubsettableFont(fontDictionary, subFont -> {
            resources.setItem(fontName, (COSBase) subFont);
        });
    }

    public void maybeSetCurrentSubsettableFont(COSDictionary fontDictionary, Consumer<SubsettableInUseFont> onSuccess) {
        this.fontStack.push((SubsettableInUseFont) Optional.ofNullable(fontDictionary.id()).map(id -> {
            return this.subsettables.computeIfAbsent(id, k -> {
                COSName subType = fontDictionary.getCOSName(COSName.SUBTYPE);
                if (!isSubsetFontName(fontDictionary.getNameAsString(COSName.BASE_FONT))) {
                    if (COSName.TRUE_TYPE.equals(subType)) {
                        return trueTypeSubsettableFont(fontDictionary, onSuccess);
                    }
                    if (COSName.TYPE0.equals(subType)) {
                        return type0SubsettableFont(fontDictionary, onSuccess);
                    }
                    return null;
                }
                return null;
            });
        }).orElse(null));
    }

    public void saveState() {
        this.fontStack.push(currentSubsettableFont().orElse(null));
    }

    public void restoreState() {
        if (!this.fontStack.isEmpty()) {
            this.fontStack.pop();
        } else {
            LOG.warn("Unable to restore empty state");
        }
    }

    private SubsettableInUseFont trueTypeSubsettableFont(COSDictionary fontDictionary, Consumer<SubsettableInUseFont> onSuccess) {
        try {
            COSStream ttfFontFile = (COSStream) Optional.ofNullable((COSDictionary) fontDictionary.getDictionaryObject(COSName.FONT_DESC, COSDictionary.class)).map(d -> {
                return (COSStream) d.getDictionaryObject(COSName.FONT_FILE2, COSStream.class);
            }).orElse(null);
            if (Objects.nonNull(ttfFontFile)) {
                LOG.trace("Found a not subsetted TrueType font with id '{}'", fontDictionary.id());
                TrueTypeFont font = new TTFParser(true, true).parse(ttfFontFile.getUnfilteredStream());
                String subsetFontName = getPrefixFor(ttfFontFile) + fontDictionary.getNameAsString(COSName.BASE_FONT);
                FontFileKey fontFileKey = FontFileKey.keyOf(ttfFontFile, COSName.TRUE_TYPE, subsetFontName);
                TTFSubsettableInUseFont subsettableFont = new TTFSubsettableInUseFont(fontFileKey, fontDictionary, new TrueTypeBytesToGlyphIDLookup(fontDictionary, font), this);
                this.subsetters.putIfAbsent(fontFileKey, new ExistingTTFSubsetter(fontDictionary, ttfFontFile, font));
                onSuccess.accept(subsettableFont);
                return subsettableFont;
            }
            return null;
        } catch (Exception e) {
            LOG.warn("Unable to create subsettable TrueType font", e);
            return null;
        }
    }

    private SubsettableInUseFont type0SubsettableFont(COSDictionary fontDictionary, Consumer<SubsettableInUseFont> onSuccess) {
        return (SubsettableInUseFont) Optional.ofNullable((COSArray) fontDictionary.getDictionaryObject(COSName.DESCENDANT_FONTS, COSArray.class)).filter(a -> {
            return !a.isEmpty();
        }).map(a2 -> {
            return (COSDictionary) a2.getObject(0, COSDictionary.class);
        }).map(descendant -> {
            if (!isSubsetFontName(descendant.getNameAsString(COSName.BASE_FONT)) && COSName.CID_FONT_TYPE2.equals(descendant.getCOSName(COSName.SUBTYPE))) {
                COSStream ttfFontFile = (COSStream) Optional.ofNullable((COSDictionary) descendant.getDictionaryObject(COSName.FONT_DESC, COSDictionary.class)).map(d -> {
                    return (COSStream) d.getDictionaryObject(COSName.FONT_FILE2, COSStream.class);
                }).orElse(null);
                if (Objects.nonNull(ttfFontFile)) {
                    LOG.trace("Found a not subsetted CID subtype 2 font with id '{}'", fontDictionary.id());
                    try {
                        String subsetFontName = getPrefixFor(ttfFontFile) + descendant.getNameAsString(COSName.BASE_FONT);
                        FontFileKey fontFileKey = FontFileKey.keyOf(ttfFontFile, COSName.TYPE0, subsetFontName);
                        Type0SubsettableInUseFont subsettableFont = new Type0SubsettableInUseFont(fontFileKey, fontDictionary, descendant, this);
                        this.subsetters.putIfAbsent(fontFileKey, new ExistingType0CIDType2Subsetter(fontDictionary, ttfFontFile));
                        onSuccess.accept(subsettableFont);
                        return subsettableFont;
                    } catch (IOException e) {
                        LOG.warn("Unable to create subsettable CID subtype 2 font from a not subsetted type 0 font with id " + fontDictionary.id(), e);
                        return null;
                    }
                }
                return null;
            }
            return null;
        }).orElse(null);
    }

    public Optional<SubsettableInUseFont> currentSubsettableFont() {
        return Optional.ofNullable(this.fontStack.peek());
    }

    public Collection<SubsettableInUseFont> subsettableFonts() {
        return this.subsettables.values();
    }

    public void subset() {
        if (!this.subsetters.isEmpty()) {
            LOG.info("Subsetting {} font streams", Integer.valueOf(this.subsetters.size()));
            for (Map.Entry<FontFileKey, Function<String, COSStream>> subsetter : this.subsetters.entrySet()) {
                LOG.trace("Subsetting expected font {}", subsetter.getKey());
                this.subsetted.put(subsetter.getKey(), subsetter.getValue().apply(subsetter.getKey().subsetName()));
            }
        }
    }

    COSStream getSubsettedStreamFor(FontFileKey fontFileKey) {
        return this.subsetted.get(fontFileKey);
    }

    BaseTTFSubsetter subsetterFor(FontFileKey fontFileKey) {
        Function<String, COSStream> subsetter = this.subsetters.get(fontFileKey);
        if (subsetter instanceof BaseTTFSubsetter) {
            BaseTTFSubsetter base = (BaseTTFSubsetter) subsetter;
            return base;
        }
        return null;
    }

    void invalidateSubsetter(FontFileKey fontFileKey) {
        this.subsetters.put(fontFileKey, n -> {
            return null;
        });
    }

    private static boolean isSubsetFontName(String name) {
        String[] nameFragments = (String[]) Optional.ofNullable(name).map((v0) -> {
            return v0.trim();
        }).map(s -> {
            return s.split("\\+");
        }).orElseGet(() -> {
            return new String[0];
        });
        return nameFragments.length == 2 && nameFragments[0].length() == 6;
    }

    static String getPrefixFor(COSStream fontStream) {
        return ((String) Optional.ofNullable(fontStream.id()).map(id -> {
            return id.objectIdentifier;
        }).map((v0) -> {
            return v0.objectNumber();
        }).map(objectNumber -> {
            StringBuilder sb = new StringBuilder();
            long current = objectNumber.longValue();
            do {
                long div = current / 26;
                int mod = (int) (current % 26);
                sb.append((char) (65 + mod));
                current = div;
                if (current == 0) {
                    break;
                }
            } while (sb.length() < 6);
            return StringUtils.leftPad(sb.toString(), 6, 'A');
        }).orElseGet(FontSubsettingContext::randomName)) + "+";
    }

    private static String randomName() {
        StringBuilder sb = new StringBuilder();
        new Random().ints(6L, 0, 26).forEach(i -> {
            sb.append((char) (i + 65));
        });
        return sb.toString();
    }
}
