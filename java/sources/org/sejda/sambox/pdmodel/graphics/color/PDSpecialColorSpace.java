package org.sejda.sambox.pdmodel.graphics.color;

import org.sejda.sambox.cos.COSBase;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/color/PDSpecialColorSpace.class */
public abstract class PDSpecialColorSpace extends PDColorSpace {
    @Override // org.sejda.sambox.pdmodel.graphics.color.PDColorSpace, org.sejda.sambox.cos.COSObjectable
    public COSBase getCOSObject() {
        return this.array;
    }
}
