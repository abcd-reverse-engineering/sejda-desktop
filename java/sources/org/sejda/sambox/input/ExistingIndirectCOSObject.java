package org.sejda.sambox.input;

import java.io.IOException;
import java.util.Optional;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSNull;
import org.sejda.sambox.cos.COSObjectKey;
import org.sejda.sambox.cos.COSVisitor;
import org.sejda.sambox.cos.DisposableCOSObject;
import org.sejda.sambox.cos.IndirectCOSObjectIdentifier;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/input/ExistingIndirectCOSObject.class */
public class ExistingIndirectCOSObject extends COSBase implements DisposableCOSObject {
    private final IndirectCOSObjectIdentifier id;
    private final IndirectObjectsProvider provider;

    ExistingIndirectCOSObject(long objectNumber, int generationNumber, IndirectObjectsProvider provider) {
        this.id = new IndirectCOSObjectIdentifier(new COSObjectKey(objectNumber, generationNumber), provider.id());
        this.provider = provider;
    }

    @Override // org.sejda.sambox.cos.COSBase, org.sejda.sambox.cos.COSObjectable
    public COSBase getCOSObject() {
        COSBase baseObject = (COSBase) Optional.ofNullable(this.provider.get(this.id.objectIdentifier)).orElse(COSNull.NULL);
        baseObject.idIfAbsent(this.id);
        return baseObject;
    }

    @Override // org.sejda.sambox.cos.DisposableCOSObject
    public void releaseCOSObject() {
        this.provider.release(this.id.objectIdentifier);
    }

    @Override // org.sejda.sambox.cos.COSBase
    public void accept(COSVisitor visitor) throws IOException {
        getCOSObject().accept(visitor);
    }

    @Override // org.sejda.sambox.cos.COSBase
    public IndirectCOSObjectIdentifier id() {
        return this.id;
    }

    public String toString() {
        return String.format("%s[%s]", super.toString(), this.id.toString());
    }
}
