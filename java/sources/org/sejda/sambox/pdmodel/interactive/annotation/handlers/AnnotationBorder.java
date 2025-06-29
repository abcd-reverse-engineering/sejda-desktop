package org.sejda.sambox.pdmodel.interactive.annotation.handlers;

import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSNumber;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.pdmodel.interactive.annotation.PDBorderStyleDictionary;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/annotation/handlers/AnnotationBorder.class */
class AnnotationBorder {
    float[] dashArray = null;
    boolean underline = false;
    float width = 0.0f;

    AnnotationBorder() {
    }

    static AnnotationBorder getAnnotationBorder(PDAnnotation annotation, PDBorderStyleDictionary borderStyle) {
        AnnotationBorder ab = new AnnotationBorder();
        if (borderStyle == null) {
            COSArray border = annotation.getBorder();
            if (border.size() >= 3 && (border.getObject(2) instanceof COSNumber)) {
                ab.width = ((COSNumber) border.getObject(2)).floatValue();
            }
            if (border.size() > 3) {
                COSBase base3 = border.getObject(3);
                if (base3 instanceof COSArray) {
                    ab.dashArray = ((COSArray) base3).toFloatArray();
                }
            }
        } else {
            ab.width = borderStyle.getWidth();
            if (borderStyle.getStyle().equals("D")) {
                ab.dashArray = borderStyle.getDashStyle().getDashArray();
            }
            if (borderStyle.getStyle().equals(PDBorderStyleDictionary.STYLE_UNDERLINE)) {
                ab.underline = true;
            }
        }
        if (ab.dashArray != null) {
            boolean allZero = true;
            float[] fArr = ab.dashArray;
            int length = fArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                float f = fArr[i];
                if (Float.compare(f, 0.0f) == 0) {
                    i++;
                } else {
                    allZero = false;
                    break;
                }
            }
            if (allZero) {
                ab.dashArray = null;
            }
        }
        return ab;
    }
}
