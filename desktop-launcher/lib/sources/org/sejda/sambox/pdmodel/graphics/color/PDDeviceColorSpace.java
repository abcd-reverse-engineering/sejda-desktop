package org.sejda.sambox.pdmodel.graphics.color;

import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSName;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/color/PDDeviceColorSpace.class */
public abstract class PDDeviceColorSpace extends PDColorSpace {
    public String toString() {
        return getName();
    }

    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace, org.sejda.sambox.cos.COSObjectable
    public COSBase getCOSObject() {
        return COSName.getPDFName(getName());
    }
}
