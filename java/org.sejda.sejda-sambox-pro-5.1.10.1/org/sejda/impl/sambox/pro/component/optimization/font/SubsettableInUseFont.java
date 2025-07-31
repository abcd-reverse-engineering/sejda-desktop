package org.sejda.impl.sambox.pro.component.optimization.font;

import java.io.IOException;
import java.util.Optional;
import org.sejda.impl.sambox.component.optimization.InUseDictionary;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/optimization/font/SubsettableInUseFont.class */
public abstract class SubsettableInUseFont extends InUseDictionary {
    private static final Logger LOG = LoggerFactory.getLogger(SubsettableInUseFont.class);
    private final BytesToGlyphIDLookup bytesToGlyphIdsLookup;
    private final FontSubsettingContext subsettingContext;
    final FontFileKey fontFileKey;

    public abstract void subset();

    SubsettableInUseFont(FontFileKey fontFileKey, COSDictionary existingFont, BytesToGlyphIDLookup bytesToGlyphIdsLookup, FontSubsettingContext subsettingContext) {
        super(existingFont);
        this.bytesToGlyphIdsLookup = bytesToGlyphIdsLookup;
        this.subsettingContext = subsettingContext;
        this.fontFileKey = fontFileKey;
    }

    public void addToSubset(byte[] bytes) {
        Optional.ofNullable(this.subsettingContext.subsetterFor(this.fontFileKey)).ifPresent(subsetter -> {
            try {
                subsetter.putAll(this.bytesToGlyphIdsLookup.convert(bytes));
            } catch (IOException e) {
                LOG.warn("Unable to convert content stream bytes to glyph ids, font won't be subsetted", e);
                this.subsettingContext.invalidateSubsetter(this.fontFileKey);
            }
        });
    }

    public COSStream getSubsettedStream() {
        return this.subsettingContext.getSubsettedStreamFor(this.fontFileKey);
    }
}
