package org.sejda.sambox.rendering;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/rendering/ImageType.class */
public enum ImageType {
    BINARY { // from class: org.sejda.sambox.rendering.ImageType.1
        @Override // org.sejda.sambox.rendering.ImageType
        public int toBufferedImageType() {
            return 12;
        }
    },
    GRAY { // from class: org.sejda.sambox.rendering.ImageType.2
        @Override // org.sejda.sambox.rendering.ImageType
        public int toBufferedImageType() {
            return 10;
        }
    },
    RGB { // from class: org.sejda.sambox.rendering.ImageType.3
        @Override // org.sejda.sambox.rendering.ImageType
        public int toBufferedImageType() {
            return 1;
        }
    },
    ARGB { // from class: org.sejda.sambox.rendering.ImageType.4
        @Override // org.sejda.sambox.rendering.ImageType
        public int toBufferedImageType() {
            return 2;
        }
    },
    BGR { // from class: org.sejda.sambox.rendering.ImageType.5
        @Override // org.sejda.sambox.rendering.ImageType
        public int toBufferedImageType() {
            return 5;
        }
    };

    public abstract int toBufferedImageType();
}
