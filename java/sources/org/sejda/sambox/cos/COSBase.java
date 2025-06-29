package org.sejda.sambox.cos;

import java.io.IOException;
import java.util.Objects;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/cos/COSBase.class */
public abstract class COSBase implements COSObjectable {
    private IndirectCOSObjectIdentifier id;

    public abstract void accept(COSVisitor cOSVisitor) throws IOException;

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSBase getCOSObject() {
        return this;
    }

    public IndirectCOSObjectIdentifier id() {
        return this.id;
    }

    public void idIfAbsent(IndirectCOSObjectIdentifier id) {
        if (!hasId() && Objects.nonNull(id)) {
            this.id = id;
        }
    }

    public boolean hasId() {
        return Objects.nonNull(id());
    }
}
