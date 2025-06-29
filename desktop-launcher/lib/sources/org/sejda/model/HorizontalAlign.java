package org.sejda.model;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/HorizontalAlign.class */
public enum HorizontalAlign implements FriendlyNamed {
    CENTER("center") { // from class: org.sejda.model.HorizontalAlign.1
        @Override // org.sejda.model.HorizontalAlign
        public float position(float pageWidth, float labelWidth, float margin) {
            return (pageWidth - labelWidth) / 2.0f;
        }
    },
    RIGHT("right") { // from class: org.sejda.model.HorizontalAlign.2
        @Override // org.sejda.model.HorizontalAlign
        public float position(float pageWidth, float labelWidth, float margin) {
            return (pageWidth - labelWidth) - margin;
        }
    },
    LEFT("left") { // from class: org.sejda.model.HorizontalAlign.3
        @Override // org.sejda.model.HorizontalAlign
        public float position(float pageWidth, float labelWidth, float margin) {
            return margin;
        }
    };

    private final String displayName;

    public abstract float position(float pageWidth, float stringWidth, float margin);

    HorizontalAlign(String displayName) {
        this.displayName = displayName;
    }

    @Override // org.sejda.model.FriendlyNamed
    public String getFriendlyName() {
        return this.displayName;
    }
}
