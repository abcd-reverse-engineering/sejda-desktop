package org.sejda.impl.sambox.util;

import org.sejda.sambox.pdmodel.common.PDRectangle;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/util/RectangleUtils.class */
public class RectangleUtils {
    public static boolean intersect(PDRectangle r1, PDRectangle r2) {
        return r1.contains(r2.getLowerLeftX(), r2.getLowerLeftY()) || r1.contains(r2.getUpperRightX(), r2.getUpperRightY()) || r1.contains(r2.getLowerLeftX(), r2.getUpperRightY()) || r1.contains(r2.getUpperRightX(), r2.getLowerLeftY());
    }

    public static PDRectangle translate(float xOffset, float yOffset, PDRectangle oldRectangle) {
        return new PDRectangle(oldRectangle.getLowerLeftX() + xOffset, oldRectangle.getLowerLeftY() + yOffset, oldRectangle.getWidth(), oldRectangle.getHeight());
    }

    public static PDRectangle rotate(int degrees, PDRectangle oldRectangle, PDRectangle mediaBox) {
        while (degrees < 0) {
            degrees = 360 + degrees;
        }
        switch (degrees) {
            case 0:
                return oldRectangle;
            case 90:
                return new PDRectangle(oldRectangle.getLowerLeftY(), (mediaBox.getWidth() - oldRectangle.getLowerLeftX()) - oldRectangle.getWidth(), oldRectangle.getHeight(), oldRectangle.getWidth());
            case 180:
                return new PDRectangle((mediaBox.getWidth() - oldRectangle.getLowerLeftX()) - oldRectangle.getWidth(), (mediaBox.getHeight() - oldRectangle.getLowerLeftY()) - oldRectangle.getHeight(), oldRectangle.getWidth(), oldRectangle.getHeight());
            case 270:
                return new PDRectangle((mediaBox.getHeight() - oldRectangle.getLowerLeftY()) - oldRectangle.getHeight(), oldRectangle.getLowerLeftX(), oldRectangle.getHeight(), oldRectangle.getWidth());
            default:
                throw new RuntimeException("Cannot rotate rectangle by degrees: " + degrees);
        }
    }
}
