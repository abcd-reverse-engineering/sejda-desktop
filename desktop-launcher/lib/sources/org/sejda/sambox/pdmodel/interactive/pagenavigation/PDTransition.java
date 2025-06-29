package org.sejda.sambox.pdmodel.interactive.pagenavigation;

import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSBoolean;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSFloat;
import org.sejda.sambox.cos.COSInteger;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.common.PDDictionaryWrapper;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/pagenavigation/PDTransition.class */
public final class PDTransition extends PDDictionaryWrapper {
    public PDTransition() {
        this(PDTransitionStyle.R);
    }

    public PDTransition(PDTransitionStyle style) {
        getCOSObject().setName(COSName.TYPE, COSName.TRANS.getName());
        getCOSObject().setName(COSName.S, style.name());
    }

    public PDTransition(COSDictionary dictionary) {
        super(dictionary);
    }

    public String getStyle() {
        return getCOSObject().getNameAsString(COSName.S, PDTransitionStyle.R.name());
    }

    public String getDimension() {
        return getCOSObject().getNameAsString(COSName.DM, PDTransitionDimension.H.name());
    }

    public void setDimension(PDTransitionDimension dimension) {
        getCOSObject().setName(COSName.DM, dimension.name());
    }

    public String getMotion() {
        return getCOSObject().getNameAsString(COSName.M, PDTransitionMotion.I.name());
    }

    public void setMotion(PDTransitionMotion motion) {
        getCOSObject().setName(COSName.M, motion.name());
    }

    public COSBase getDirection() {
        COSBase item = getCOSObject().getItem(COSName.DI);
        if (item == null) {
            return COSInteger.ZERO;
        }
        return item;
    }

    public void setDirection(PDTransitionDirection direction) {
        getCOSObject().setItem(COSName.DI, direction.getCOSBase());
    }

    public float getDuration() {
        return getCOSObject().getFloat(COSName.D, 1.0f);
    }

    public void setDuration(float duration) {
        getCOSObject().setItem(COSName.D, (COSBase) new COSFloat(duration));
    }

    public float getFlyScale() {
        return getCOSObject().getFloat(COSName.SS, 1.0f);
    }

    public void setFlyScale(float scale) {
        getCOSObject().setItem(COSName.SS, (COSBase) new COSFloat(scale));
    }

    public boolean isFlyAreaOpaque() {
        return getCOSObject().getBoolean(COSName.B, false);
    }

    public void setFlyAreaOpaque(boolean opaque) {
        getCOSObject().setItem(COSName.B, (COSBase) COSBoolean.valueOf(opaque));
    }
}
