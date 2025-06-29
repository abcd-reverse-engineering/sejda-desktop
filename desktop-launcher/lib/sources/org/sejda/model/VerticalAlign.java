package org.sejda.model;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/VerticalAlign.class */
public enum VerticalAlign implements FriendlyNamed {
    TOP("top") { // from class: org.sejda.model.VerticalAlign.1
        @Override // org.sejda.model.VerticalAlign
        public float position(float pageHight, float margin, float fontSize) {
            return (pageHight - margin) - (fontSize / 2.0f);
        }
    },
    BOTTOM("bottom") { // from class: org.sejda.model.VerticalAlign.2
        @Override // org.sejda.model.VerticalAlign
        public float position(float pageHight, float margin, float fontSize) {
            return margin;
        }
    };

    private final String displayName;

    public abstract float position(float pageHeight, float margin, float fontSize);

    VerticalAlign(String displayName) {
        this.displayName = displayName;
    }

    @Override // org.sejda.model.FriendlyNamed
    public String getFriendlyName() {
        return this.displayName;
    }
}
