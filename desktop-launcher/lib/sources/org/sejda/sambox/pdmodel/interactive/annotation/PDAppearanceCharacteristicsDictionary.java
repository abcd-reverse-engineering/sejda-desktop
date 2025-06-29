package org.sejda.sambox.pdmodel.interactive.annotation;

import java.util.Objects;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.pdmodel.common.PDDictionaryWrapper;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.pdmodel.graphics.color.PDDeviceCMYK;
import org.sejda.sambox.pdmodel.graphics.color.PDDeviceGray;
import org.sejda.sambox.pdmodel.graphics.color.PDDeviceRGB;
import org.sejda.sambox.pdmodel.graphics.form.PDFormXObject;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/annotation/PDAppearanceCharacteristicsDictionary.class */
public class PDAppearanceCharacteristicsDictionary extends PDDictionaryWrapper {
    public PDAppearanceCharacteristicsDictionary(COSDictionary dict) {
        super(dict);
    }

    public int getRotation() {
        return getCOSObject().getInt(COSName.R, 0);
    }

    public void setRotation(int rotation) {
        getCOSObject().setInt(COSName.R, rotation);
    }

    public PDColor getBorderColour() {
        return getColor(COSName.BC);
    }

    public void setBorderColour(PDColor c) {
        getCOSObject().setItem(COSName.BC, (COSBase) c.toComponentsCOSArray());
    }

    public PDColor getBackground() {
        return getColor(COSName.BG);
    }

    public void setBackground(PDColor c) {
        getCOSObject().setItem(COSName.BG, (COSBase) c.toComponentsCOSArray());
    }

    public String getNormalCaption() {
        return getCOSObject().getString(COSName.CA);
    }

    public void setNormalCaption(String caption) {
        getCOSObject().setString(COSName.CA, caption);
    }

    public String getRolloverCaption() {
        return getCOSObject().getString("RC");
    }

    public void setRolloverCaption(String caption) {
        getCOSObject().setString(COSName.RC, caption);
    }

    public String getAlternateCaption() {
        return getCOSObject().getString(COSName.AC);
    }

    public void setAlternateCaption(String caption) {
        getCOSObject().setString(COSName.AC, caption);
    }

    public PDFormXObject getNormalIcon() {
        COSStream i = (COSStream) getCOSObject().getDictionaryObject(COSName.I, COSStream.class);
        if (Objects.nonNull(i)) {
            return new PDFormXObject(i);
        }
        return null;
    }

    public PDFormXObject getRolloverIcon() {
        COSStream i = (COSStream) getCOSObject().getDictionaryObject(COSName.RI, COSStream.class);
        if (Objects.nonNull(i)) {
            return new PDFormXObject(i);
        }
        return null;
    }

    public PDFormXObject getAlternateIcon() {
        COSStream i = (COSStream) getCOSObject().getDictionaryObject(COSName.IX, COSStream.class);
        if (Objects.nonNull(i)) {
            return new PDFormXObject(i);
        }
        return null;
    }

    private PDColor getColor(COSName itemName) {
        COSArray c = (COSArray) getCOSObject().getDictionaryObject(itemName, COSArray.class);
        if (Objects.nonNull(c)) {
            switch (c.size()) {
                case 1:
                    return new PDColor(c, PDDeviceGray.INSTANCE);
                case 2:
                default:
                    return null;
                case 3:
                    return new PDColor(c, PDDeviceRGB.INSTANCE);
                case 4:
                    return new PDColor(c, PDDeviceCMYK.INSTANCE);
            }
        }
        return null;
    }
}
