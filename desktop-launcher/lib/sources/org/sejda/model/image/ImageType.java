package org.sejda.model.image;

import java.util.EnumSet;
import java.util.Set;
import org.sejda.model.SejdaFileExtensions;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/image/ImageType.class */
public enum ImageType {
    PNG("image/png", "png", false),
    JPEG("image/jpeg", "jpg", false),
    GIF("image/gif", "gif", true),
    TIFF("image/tiff", SejdaFileExtensions.TIF_EXTENSION, true);

    private final String mimeType;
    private final String extension;
    private final boolean supportMultiImage;

    ImageType(String mimeType, String extension, boolean supportMultiImage) {
        this.mimeType = mimeType;
        this.extension = extension;
        this.supportMultiImage = supportMultiImage;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public boolean isSupportMultiImage() {
        return this.supportMultiImage;
    }

    public String getExtension() {
        return this.extension;
    }

    public static Set<ImageType> valuesSupportingMultipleImage() {
        Set<ImageType> retSet = EnumSet.noneOf(ImageType.class);
        for (ImageType current : values()) {
            if (current.isSupportMultiImage()) {
                retSet.add(current);
            }
        }
        return retSet;
    }
}
