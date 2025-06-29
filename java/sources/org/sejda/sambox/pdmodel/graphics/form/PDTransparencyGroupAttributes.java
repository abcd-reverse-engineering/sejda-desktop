package org.sejda.sambox.pdmodel.graphics.form;

import java.io.IOException;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.PDResources;
import org.sejda.sambox.pdmodel.common.PDDictionaryWrapper;
import org.sejda.sambox.pdmodel.graphics.color.PDColorSpace;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/form/PDTransparencyGroupAttributes.class */
public final class PDTransparencyGroupAttributes extends PDDictionaryWrapper {
    private PDColorSpace colorSpace;

    public PDTransparencyGroupAttributes() {
        getCOSObject().setItem(COSName.TYPE, (COSBase) COSName.GROUP);
        getCOSObject().setItem(COSName.S, (COSBase) COSName.TRANSPARENCY);
    }

    public PDTransparencyGroupAttributes(COSDictionary dictionary) {
        super(dictionary);
    }

    public PDColorSpace getColorSpace() throws IOException {
        return getColorSpace(null);
    }

    public PDColorSpace getColorSpace(PDResources resources) throws IOException {
        COSBase dictionaryObject;
        if (this.colorSpace == null && getCOSObject().containsKey(COSName.CS) && (dictionaryObject = getCOSObject().getDictionaryObject(COSName.CS)) != null) {
            this.colorSpace = PDColorSpace.create(dictionaryObject, resources);
        }
        return this.colorSpace;
    }

    public boolean isIsolated() {
        return getCOSObject().getBoolean(COSName.I, false);
    }

    public void setIsolated() {
        getCOSObject().setBoolean(COSName.I, true);
    }

    public boolean isKnockout() {
        return getCOSObject().getBoolean(COSName.K, false);
    }

    public void setKnockout() {
        getCOSObject().setBoolean(COSName.K, true);
    }
}
