package org.sejda.sambox.pdmodel.interactive.annotation;

import java.util.Optional;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSFloat;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDAppearanceHandler;
import org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDCircleAppearanceHandler;
import org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDSquareAppearanceHandler;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/annotation/PDAnnotationSquareCircle.class */
public class PDAnnotationSquareCircle extends PDAnnotationMarkup {
    public static final String SUB_TYPE_SQUARE = "Square";
    public static final String SUB_TYPE_CIRCLE = "Circle";
    private PDAppearanceHandler customAppearanceHandler;

    public PDAnnotationSquareCircle(String subType) {
        setSubtype(subType);
    }

    public PDAnnotationSquareCircle(COSDictionary field) {
        super(field);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup
    public void setInteriorColor(PDColor ic) {
        getCOSObject().setItem(COSName.IC, (COSBase) ic.toComponentsCOSArray());
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup
    public PDColor getInteriorColor() {
        return getColor(COSName.IC);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup
    public void setBorderEffect(PDBorderEffectDictionary be) {
        getCOSObject().setItem(COSName.BE, be);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup
    public PDBorderEffectDictionary getBorderEffect() {
        return (PDBorderEffectDictionary) Optional.ofNullable((COSDictionary) getCOSObject().getDictionaryObject(COSName.BE, COSDictionary.class)).map(PDBorderEffectDictionary::new).orElse(null);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup
    public void setRectDifference(PDRectangle rd) {
        getCOSObject().setItem(COSName.RD, rd);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup
    public PDRectangle getRectDifference() {
        return (PDRectangle) Optional.ofNullable((COSArray) getCOSObject().getDictionaryObject(COSName.RD, COSArray.class)).map(PDRectangle::new).orElse(null);
    }

    public void setSubtype(String subType) {
        getCOSObject().setName(COSName.SUBTYPE, subType);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation
    public String getSubtype() {
        return getCOSObject().getNameAsString(COSName.SUBTYPE);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup
    public void setBorderStyle(PDBorderStyleDictionary bs) {
        getCOSObject().setItem(COSName.BS, bs);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup
    public PDBorderStyleDictionary getBorderStyle() {
        return (PDBorderStyleDictionary) Optional.ofNullable((COSDictionary) getCOSObject().getDictionaryObject(COSName.BS, COSDictionary.class)).map(PDBorderStyleDictionary::new).orElse(null);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup
    public void setRectDifferences(float difference) {
        setRectDifferences(difference, difference, difference, difference);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup
    public void setRectDifferences(float differenceLeft, float differenceTop, float differenceRight, float differenceBottom) {
        COSArray margins = new COSArray();
        margins.add((COSBase) new COSFloat(differenceLeft));
        margins.add((COSBase) new COSFloat(differenceTop));
        margins.add((COSBase) new COSFloat(differenceRight));
        margins.add((COSBase) new COSFloat(differenceBottom));
        getCOSObject().setItem(COSName.RD, (COSBase) margins);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup
    public float[] getRectDifferences() {
        COSBase margin = getCOSObject().getItem(COSName.RD);
        if (margin instanceof COSArray) {
            return ((COSArray) margin).toFloatArray();
        }
        return new float[0];
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup
    public void setCustomAppearanceHandler(PDAppearanceHandler appearanceHandler) {
        this.customAppearanceHandler = appearanceHandler;
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup, org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation
    public void constructAppearances() {
        if (this.customAppearanceHandler == null) {
            if ("Circle".equals(getSubtype())) {
                PDCircleAppearanceHandler appearanceHandler = new PDCircleAppearanceHandler(this);
                appearanceHandler.generateAppearanceStreams();
                return;
            } else {
                if ("Square".equals(getSubtype())) {
                    PDSquareAppearanceHandler appearanceHandler2 = new PDSquareAppearanceHandler(this);
                    appearanceHandler2.generateAppearanceStreams();
                    return;
                }
                return;
            }
        }
        this.customAppearanceHandler.generateAppearanceStreams();
    }
}
