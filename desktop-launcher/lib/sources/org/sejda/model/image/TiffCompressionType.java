package org.sejda.model.image;

import org.sejda.model.FriendlyNamed;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/image/TiffCompressionType.class */
public enum TiffCompressionType implements FriendlyNamed {
    NONE("none"),
    CCITT_GROUP_3_1D("ccitt_group_3_1d"),
    CCITT_GROUP_3_2D("ccitt_group_3_2d"),
    CCITT_GROUP_4("ccitt_group_4"),
    LZW("lzw"),
    ZLIB("zlib"),
    JPEG_TTN2("jpeg_ttn2"),
    PACKBITS("packbits"),
    DEFLATE("deflate");

    private final String displayName;

    TiffCompressionType(String displayName) {
        this.displayName = displayName;
    }

    @Override // org.sejda.model.FriendlyNamed
    public String getFriendlyName() {
        return this.displayName;
    }
}
