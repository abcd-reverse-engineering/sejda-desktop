package org.sejda.sambox.cos;

import java.io.IOException;
import java.util.Optional;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/cos/DirectCOSObject.class */
public final class DirectCOSObject extends COSBase {
    private final COSBase baseObject;

    private DirectCOSObject(COSBase wrapped) {
        this.baseObject = wrapped;
    }

    @Override // org.sejda.sambox.cos.COSBase, org.sejda.sambox.cos.COSObjectable
    public COSBase getCOSObject() {
        return this.baseObject;
    }

    @Override // org.sejda.sambox.cos.COSBase
    public void accept(COSVisitor visitor) throws IOException {
        this.baseObject.accept(visitor);
    }

    public static DirectCOSObject asDirectObject(COSObjectable wrapped) {
        return new DirectCOSObject(((COSObjectable) Optional.ofNullable(wrapped).orElse(COSNull.NULL)).getCOSObject());
    }
}
