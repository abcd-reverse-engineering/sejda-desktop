package org.sejda.sambox.cos;

import java.io.IOException;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/cos/COSBoolean.class */
public final class COSBoolean extends COSBase {
    public static final COSBoolean TRUE = new COSBoolean(true);
    public static final COSBoolean FALSE = new COSBoolean(false);
    private final boolean value;

    private COSBoolean(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return this.value;
    }

    public static COSBoolean valueOf(boolean value) {
        return value ? TRUE : FALSE;
    }

    public String toString() {
        return Boolean.toString(this.value);
    }

    @Override // org.sejda.sambox.cos.COSBase
    public void accept(COSVisitor visitor) throws IOException {
        visitor.visit(this);
    }

    @Override // org.sejda.sambox.cos.COSBase
    public void idIfAbsent(IndirectCOSObjectIdentifier id) {
    }
}
