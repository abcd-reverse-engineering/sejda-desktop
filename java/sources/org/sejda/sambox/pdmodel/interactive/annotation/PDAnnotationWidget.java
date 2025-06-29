package org.sejda.sambox.pdmodel.interactive.annotation;

import java.util.Objects;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.interactive.action.PDAction;
import org.sejda.sambox.pdmodel.interactive.action.PDActionFactory;
import org.sejda.sambox.pdmodel.interactive.action.PDAnnotationAdditionalActions;
import org.sejda.sambox.pdmodel.interactive.measurement.PDNumberFormatDictionary;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/annotation/PDAnnotationWidget.class */
public class PDAnnotationWidget extends PDAnnotation {
    public PDAnnotationWidget() {
        getCOSObject().setName(COSName.SUBTYPE, COSName.WIDGET.getName());
    }

    public PDAnnotationWidget(COSDictionary field) {
        super(field);
        getCOSObject().setName(COSName.SUBTYPE, COSName.WIDGET.getName());
    }

    public String getHighlightingMode() {
        return getCOSObject().getNameAsString(COSName.H, "I");
    }

    public void setHighlightingMode(String highlightingMode) {
        if (highlightingMode == null || PDAnnotationLink.HIGHLIGHT_MODE_NONE.equals(highlightingMode) || "I".equals(highlightingMode) || PDAnnotationLink.HIGHLIGHT_MODE_OUTLINE.equals(highlightingMode) || "P".equals(highlightingMode) || PDNumberFormatDictionary.FRACTIONAL_DISPLAY_TRUNCATE.equals(highlightingMode)) {
            getCOSObject().setName(COSName.H, highlightingMode);
            return;
        }
        throw new IllegalArgumentException("Valid values for highlighting mode are 'N', 'N', 'O', 'P' or 'T'");
    }

    public PDAppearanceCharacteristicsDictionary getAppearanceCharacteristics() {
        COSDictionary mk = (COSDictionary) getCOSObject().getDictionaryObject(COSName.MK, COSDictionary.class);
        if (Objects.nonNull(mk)) {
            return new PDAppearanceCharacteristicsDictionary(mk);
        }
        return null;
    }

    public void setAppearanceCharacteristics(PDAppearanceCharacteristicsDictionary appearanceCharacteristics) {
        getCOSObject().setItem(COSName.MK, appearanceCharacteristics);
    }

    public PDAction getAction() {
        return PDActionFactory.createAction((COSDictionary) getCOSObject().getDictionaryObject(COSName.A));
    }

    public void setAction(PDAction action) {
        getCOSObject().setItem(COSName.A, action);
    }

    public PDAnnotationAdditionalActions getActions() {
        COSDictionary aa = (COSDictionary) getCOSObject().getDictionaryObject("AA");
        if (aa != null) {
            return new PDAnnotationAdditionalActions(aa);
        }
        return null;
    }

    public void setActions(PDAnnotationAdditionalActions actions) {
        getCOSObject().setItem("AA", actions);
    }

    public void setBorderStyle(PDBorderStyleDictionary bs) {
        getCOSObject().setItem("BS", bs);
    }

    public PDBorderStyleDictionary getBorderStyle() {
        COSDictionary bs = (COSDictionary) getCOSObject().getDictionaryObject(COSName.BS, COSDictionary.class);
        if (bs != null) {
            return new PDBorderStyleDictionary(bs);
        }
        return null;
    }
}
