package org.sejda.sambox.pdmodel.interactive.pagenavigation;

import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSInteger;
import org.sejda.sambox.cos.COSName;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/pagenavigation/PDTransitionDirection.class */
public enum PDTransitionDirection {
    LEFT_TO_RIGHT(0),
    BOTTOM_TO_TOP(90),
    RIGHT_TO_LEFT(180),
    TOP_TO_BOTTOM(270),
    TOP_LEFT_TO_BOTTOM_RIGHT(315),
    NONE(0) { // from class: org.sejda.sambox.pdmodel.interactive.pagenavigation.PDTransitionDirection.1
        @Override // org.sejda.sambox.pdmodel.interactive.pagenavigation.PDTransitionDirection
        public COSBase getCOSBase() {
            return COSName.NONE;
        }
    };

    private final int degrees;

    PDTransitionDirection(int degrees) {
        this.degrees = degrees;
    }

    public COSBase getCOSBase() {
        return COSInteger.get(this.degrees);
    }
}
