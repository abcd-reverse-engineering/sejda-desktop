package org.sejda.sambox.cos;

import java.io.IOException;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/cos/COSNull.class */
public final class COSNull extends COSBase {
    public static final COSNull NULL = new COSNull();

    private COSNull() {
    }

    @Override // org.sejda.sambox.cos.COSBase
    public void accept(COSVisitor visitor) throws IOException {
        visitor.visit(this);
    }

    public String toString() {
        return "COSNull";
    }

    @Override // org.sejda.sambox.cos.COSBase
    public void idIfAbsent(IndirectCOSObjectIdentifier id) {
    }
}
