package org.sejda.sambox.pdmodel.interactive.annotation;

import java.util.Optional;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDAppearanceHandler;
import org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDHighlightAppearanceHandler;
import org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDSquigglyAppearanceHandler;
import org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDStrikeoutAppearanceHandler;
import org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDUnderlineAppearanceHandler;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/annotation/PDAnnotationTextMarkup.class */
public class PDAnnotationTextMarkup extends PDAnnotationMarkup {
    private PDAppearanceHandler customAppearanceHandler;
    public static final String SUB_TYPE_HIGHLIGHT = "Highlight";
    public static final String SUB_TYPE_UNDERLINE = "Underline";
    public static final String SUB_TYPE_SQUIGGLY = "Squiggly";
    public static final String SUB_TYPE_STRIKEOUT = "StrikeOut";

    public PDAnnotationTextMarkup(String subType) {
        setSubtype(subType);
        setQuadPoints(new float[0]);
    }

    public PDAnnotationTextMarkup(COSDictionary field) {
        super(field);
    }

    public void setQuadPoints(float[] quadPoints) {
        COSArray newQuadPoints = new COSArray();
        newQuadPoints.setFloatArray(quadPoints);
        getCOSObject().setItem(COSName.QUADPOINTS, (COSBase) newQuadPoints);
    }

    public float[] getQuadPoints() {
        return (float[]) Optional.ofNullable((COSArray) getCOSObject().getDictionaryObject(COSName.QUADPOINTS, COSArray.class)).map((v0) -> {
            return v0.toFloatArray();
        }).orElse(null);
    }

    public void setSubtype(String subType) {
        getCOSObject().setName(COSName.SUBTYPE, subType);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation
    public String getSubtype() {
        return getCOSObject().getNameAsString(COSName.SUBTYPE);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup
    public void setCustomAppearanceHandler(PDAppearanceHandler appearanceHandler) {
        this.customAppearanceHandler = appearanceHandler;
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup, org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation
    public void constructAppearances() {
        if (this.customAppearanceHandler == null) {
            PDAppearanceHandler appearanceHandler = null;
            if (SUB_TYPE_HIGHLIGHT.equals(getSubtype())) {
                appearanceHandler = new PDHighlightAppearanceHandler(this);
            } else if (SUB_TYPE_SQUIGGLY.equals(getSubtype())) {
                appearanceHandler = new PDSquigglyAppearanceHandler(this);
            } else if (SUB_TYPE_STRIKEOUT.equals(getSubtype())) {
                appearanceHandler = new PDStrikeoutAppearanceHandler(this);
            } else if ("Underline".equals(getSubtype())) {
                appearanceHandler = new PDUnderlineAppearanceHandler(this);
            }
            if (appearanceHandler != null) {
                appearanceHandler.generateAppearanceStreams();
                return;
            }
            return;
        }
        this.customAppearanceHandler.generateAppearanceStreams();
    }
}
