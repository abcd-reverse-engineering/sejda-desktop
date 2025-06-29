package org.sejda.sambox.pdmodel.interactive.annotation;

import java.util.Objects;
import java.util.Optional;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSFloat;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDAppearanceHandler;
import org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDLineAppearanceHandler;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/annotation/PDAnnotationLine.class */
public class PDAnnotationLine extends PDAnnotationMarkup {
    private PDAppearanceHandler customAppearanceHandler;
    public static final String IT_LINE_ARROW = "LineArrow";
    public static final String IT_LINE_DIMENSION = "LineDimension";
    public static final String LE_SQUARE = "Square";
    public static final String LE_CIRCLE = "Circle";
    public static final String LE_DIAMOND = "Diamond";
    public static final String LE_OPEN_ARROW = "OpenArrow";
    public static final String LE_CLOSED_ARROW = "ClosedArrow";
    public static final String LE_NONE = "None";
    public static final String LE_BUTT = "Butt";
    public static final String LE_R_OPEN_ARROW = "ROpenArrow";
    public static final String LE_R_CLOSED_ARROW = "RClosedArrow";
    public static final String LE_SLASH = "Slash";
    public static final String SUB_TYPE = "Line";

    public PDAnnotationLine() {
        getCOSObject().setName(COSName.SUBTYPE, SUB_TYPE);
        setLine(new float[]{0.0f, 0.0f, 0.0f, 0.0f});
    }

    public PDAnnotationLine(COSDictionary field) {
        super(field);
    }

    public void setLine(float[] l) {
        COSArray newL = new COSArray();
        newL.setFloatArray(l);
        getCOSObject().setItem(COSName.L, (COSBase) newL);
    }

    public float[] getLine() {
        return (float[]) Optional.ofNullable((COSArray) getCOSObject().getDictionaryObject(COSName.L, COSArray.class)).map((v0) -> {
            return v0.toFloatArray();
        }).orElse(null);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup
    public void setStartPointEndingStyle(String style) {
        if (style == null) {
            style = "None";
        }
        COSArray array = (COSArray) getCOSObject().getDictionaryObject(COSName.LE, COSArray.class);
        if (array == null || array.size() == 0) {
            COSArray array2 = new COSArray();
            array2.add((COSBase) COSName.getPDFName(style));
            array2.add((COSBase) COSName.getPDFName("None"));
            getCOSObject().setItem(COSName.LE, (COSBase) array2);
            return;
        }
        array.set(0, (COSBase) COSName.getPDFName(style));
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup
    public String getStartPointEndingStyle() {
        COSArray array = (COSArray) getCOSObject().getDictionaryObject(COSName.LE, COSArray.class);
        if (Objects.nonNull(array)) {
            return array.getName(0);
        }
        return "None";
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup
    public void setEndPointEndingStyle(String style) {
        if (style == null) {
            style = "None";
        }
        COSArray array = (COSArray) getCOSObject().getDictionaryObject(COSName.LE, COSArray.class);
        if (array == null || array.size() < 2) {
            COSArray array2 = new COSArray();
            array2.add((COSBase) COSName.getPDFName("None"));
            array2.add((COSBase) COSName.getPDFName(style));
            getCOSObject().setItem(COSName.LE, (COSBase) array2);
            return;
        }
        array.set(1, (COSBase) COSName.getPDFName(style));
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup
    public String getEndPointEndingStyle() {
        COSArray array = (COSArray) getCOSObject().getDictionaryObject(COSName.LE, COSArray.class);
        if (Objects.nonNull(array) && array.size() >= 2) {
            return array.getName(1);
        }
        return "None";
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup
    public void setInteriorColor(PDColor ic) {
        getCOSObject().setItem(COSName.IC, (COSBase) ic.toComponentsCOSArray());
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup
    public PDColor getInteriorColor() {
        return getColor(COSName.IC);
    }

    public void setCaption(boolean cap) {
        getCOSObject().setBoolean(COSName.CAP, cap);
    }

    public boolean getCaption() {
        return getCOSObject().getBoolean(COSName.CAP, false);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup
    public void setBorderStyle(PDBorderStyleDictionary bs) {
        getCOSObject().setItem(COSName.BS, bs);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup
    public PDBorderStyleDictionary getBorderStyle() {
        COSDictionary bs = (COSDictionary) getCOSObject().getDictionaryObject(COSName.BS, COSDictionary.class);
        if (Objects.nonNull(bs)) {
            return new PDBorderStyleDictionary(bs);
        }
        return null;
    }

    public float getLeaderLineLength() {
        return getCOSObject().getFloat(COSName.LL, 0.0f);
    }

    public void setLeaderLineLength(float leaderLineLength) {
        getCOSObject().setFloat(COSName.LL, leaderLineLength);
    }

    public float getLeaderLineExtensionLength() {
        return getCOSObject().getFloat(COSName.LLE, 0.0f);
    }

    public void setLeaderLineExtensionLength(float leaderLineExtensionLength) {
        getCOSObject().setFloat(COSName.LLE, leaderLineExtensionLength);
    }

    public float getLeaderLineOffsetLength() {
        return getCOSObject().getFloat(COSName.LLO, 0.0f);
    }

    public void setLeaderLineOffsetLength(float leaderLineOffsetLength) {
        getCOSObject().setFloat(COSName.LLO, leaderLineOffsetLength);
    }

    public String getCaptionPositioning() {
        return getCOSObject().getNameAsString(COSName.CP);
    }

    public void setCaptionPositioning(String captionPositioning) {
        getCOSObject().setName(COSName.CP, captionPositioning);
    }

    public void setCaptionHorizontalOffset(float offset) {
        COSArray array = (COSArray) getCOSObject().getDictionaryObject(COSName.CO);
        if (array == null) {
            COSArray array2 = new COSArray();
            array2.setFloatArray(new float[]{offset, 0.0f});
            getCOSObject().setItem(COSName.CO, (COSBase) array2);
            return;
        }
        array.set(0, (COSBase) new COSFloat(offset));
    }

    public float getCaptionHorizontalOffset() {
        COSArray array = (COSArray) getCOSObject().getDictionaryObject(COSName.CO, COSArray.class);
        if (Objects.nonNull(array)) {
            return array.toFloatArray()[0];
        }
        return 0.0f;
    }

    public void setCaptionVerticalOffset(float offset) {
        COSArray array = (COSArray) getCOSObject().getDictionaryObject(COSName.CO, COSArray.class);
        if (array == null) {
            COSArray array2 = new COSArray();
            array2.setFloatArray(new float[]{0.0f, offset});
            getCOSObject().setItem(COSName.CO, (COSBase) array2);
            return;
        }
        array.set(1, (COSBase) new COSFloat(offset));
    }

    public float getCaptionVerticalOffset() {
        COSArray array = (COSArray) getCOSObject().getDictionaryObject(COSName.CO, COSArray.class);
        if (array != null) {
            return array.toFloatArray()[1];
        }
        return 0.0f;
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup
    public void setCustomAppearanceHandler(PDAppearanceHandler appearanceHandler) {
        this.customAppearanceHandler = appearanceHandler;
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup, org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation
    public void constructAppearances() {
        if (this.customAppearanceHandler == null) {
            PDLineAppearanceHandler appearanceHandler = new PDLineAppearanceHandler(this);
            appearanceHandler.generateAppearanceStreams();
        } else {
            this.customAppearanceHandler.generateAppearanceStreams();
        }
    }
}
