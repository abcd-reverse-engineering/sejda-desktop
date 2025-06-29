package org.sejda.sambox.pdmodel.interactive.annotation;

import java.util.Objects;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/annotation/PDAnnotationPopup.class */
public class PDAnnotationPopup extends PDAnnotation {
    public static final String SUB_TYPE = "Popup";

    public PDAnnotationPopup() {
        getCOSObject().setName(COSName.SUBTYPE, SUB_TYPE);
    }

    public PDAnnotationPopup(COSDictionary field) {
        super(field);
    }

    public void setOpen(boolean open) {
        getCOSObject().setBoolean("Open", open);
    }

    public boolean getOpen() {
        return getCOSObject().getBoolean("Open", false);
    }

    public void setParent(PDAnnotationMarkup annot) {
        getCOSObject().setItem(COSName.PARENT, (COSBase) annot.getCOSObject());
    }

    public PDAnnotationMarkup getParent() {
        COSDictionary parent = (COSDictionary) getCOSObject().getDictionaryObject(COSName.PARENT, COSDictionary.class);
        if (Objects.nonNull(parent)) {
            return (PDAnnotationMarkup) PDAnnotation.createAnnotation(parent, PDAnnotationMarkup.class);
        }
        return null;
    }
}
