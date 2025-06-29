package org.sejda.impl.sambox.pro.component.optimization.font;

import java.io.IOException;
import java.util.Objects;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/optimization/font/Type0SubsettableInUseFont.class */
public class Type0SubsettableInUseFont extends SubsettableInUseFont {
    private static final Logger LOG = LoggerFactory.getLogger(Type0SubsettableInUseFont.class);
    private final COSDictionary descendant;

    Type0SubsettableInUseFont(FontFileKey fontFileKey, COSDictionary existingFont, COSDictionary descendant, FontSubsettingContext fontSubsettingContext) throws IOException {
        super(fontFileKey, existingFont, new CIDType2BytesToGlyphIdLookup(existingFont, descendant), fontSubsettingContext);
        this.descendant = descendant;
    }

    @Override // org.sejda.impl.sambox.pro.component.optimization.font.SubsettableInUseFont
    public void subset() {
        COSStream subsettedStream = getSubsettedStream();
        if (Objects.nonNull(subsettedStream)) {
            COSDictionary descriptor = ((COSDictionary) this.descendant.getDictionaryObject(COSName.FONT_DESC, COSDictionary.class)).duplicate();
            try {
                subsettedStream.setLong(COSName.LENGTH1, subsettedStream.getUnfilteredLength());
                descriptor.setItem(COSName.FONT_FILE2, (COSBase) subsettedStream);
                descriptor.setString(COSName.FONT_NAME, this.fontFileKey.subsetName());
                this.descendant.setName(COSName.BASE_FONT, this.fontFileKey.subsetName());
                setName(COSName.BASE_FONT, this.fontFileKey.subsetName());
                removeItem(COSName.NAME);
                this.descendant.removeItem(COSName.NAME);
                this.descendant.setItem(COSName.FONT_DESC, (COSBase) descriptor);
            } catch (IOException e) {
                LOG.warn("Unexpected error while setting subsetted file stream", e);
            }
        }
    }
}
