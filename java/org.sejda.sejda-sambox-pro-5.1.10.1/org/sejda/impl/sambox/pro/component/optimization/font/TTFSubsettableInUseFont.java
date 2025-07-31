package org.sejda.impl.sambox.pro.component.optimization.font;

import java.io.IOException;
import java.util.Objects;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/optimization/font/TTFSubsettableInUseFont.class */
public class TTFSubsettableInUseFont extends SubsettableInUseFont {
    private static final Logger LOG = LoggerFactory.getLogger(TTFSubsettableInUseFont.class);

    TTFSubsettableInUseFont(FontFileKey fontFileKey, COSDictionary existingFont, TrueTypeBytesToGlyphIDLookup bytesToGlyphIdsLookup, FontSubsettingContext fontSubsettingContext) {
        super(fontFileKey, existingFont, bytesToGlyphIdsLookup, fontSubsettingContext);
    }

    @Override // org.sejda.impl.sambox.pro.component.optimization.font.SubsettableInUseFont
    public void subset() {
        COSStream subsettedStream = getSubsettedStream();
        if (Objects.nonNull(subsettedStream)) {
            COSDictionary descriptor = getDictionaryObject(COSName.FONT_DESC, COSDictionary.class).duplicate();
            try {
                subsettedStream.setLong(COSName.LENGTH1, subsettedStream.getUnfilteredLength());
                descriptor.setItem(COSName.FONT_FILE2, subsettedStream);
                descriptor.setString(COSName.FONT_NAME, this.fontFileKey.subsetName());
                setName(COSName.BASE_FONT, this.fontFileKey.subsetName());
                removeItem(COSName.NAME);
                setItem(COSName.FONT_DESC, descriptor);
            } catch (IOException e) {
                LOG.warn("Unexpected error while setting subsetted file stream", e);
            }
        }
    }
}
