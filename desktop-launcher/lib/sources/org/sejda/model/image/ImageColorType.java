package org.sejda.model.image;

import java.awt.image.BufferedImage;
import org.sejda.model.FriendlyNamed;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/image/ImageColorType.class */
public enum ImageColorType implements FriendlyNamed {
    BLACK_AND_WHITE("black_and_white", 12),
    GRAY_SCALE("gray_scale", 10),
    COLOR_RGB("color_rgb", 1);

    private final int bufferedImageType;
    private final String displayName;

    ImageColorType(String displayName, int bufferedImageType) {
        this.displayName = displayName;
        this.bufferedImageType = bufferedImageType;
    }

    @Override // org.sejda.model.FriendlyNamed
    public String getFriendlyName() {
        return this.displayName;
    }

    public int getBufferedImageType() {
        return this.bufferedImageType;
    }

    public BufferedImage createBufferedImage(int width, int height) {
        return new BufferedImage(width, height, getBufferedImageType());
    }
}
