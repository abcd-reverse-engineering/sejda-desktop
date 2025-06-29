package org.sejda.sambox.cos;

import java.io.IOException;
import java.util.Optional;
import org.sejda.sambox.xref.XrefEntry;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/cos/IndirectCOSObjectReference.class */
public class IndirectCOSObjectReference extends COSBase implements DisposableCOSObject {
    private COSBase baseObject;
    private XrefEntry xrefEntry;

    public IndirectCOSObjectReference(long objectNumber, int generationNumber, COSBase baseObject) {
        this.xrefEntry = XrefEntry.unknownOffsetEntry(objectNumber, generationNumber);
        this.baseObject = baseObject;
    }

    @Override // org.sejda.sambox.cos.COSBase
    public void accept(COSVisitor visitor) throws IOException {
        visitor.visit(this);
    }

    public XrefEntry xrefEntry() {
        return this.xrefEntry;
    }

    public void setValue(COSBase baseObject) {
        this.baseObject = baseObject;
    }

    @Override // org.sejda.sambox.cos.COSBase, org.sejda.sambox.cos.COSObjectable
    public COSBase getCOSObject() {
        return (COSBase) Optional.ofNullable(this.baseObject).orElse(COSNull.NULL);
    }

    @Override // org.sejda.sambox.cos.DisposableCOSObject
    public void releaseCOSObject() {
        if (this.baseObject instanceof DisposableCOSObject) {
            ((DisposableCOSObject) this.baseObject).releaseCOSObject();
        }
        this.baseObject = null;
    }

    public String toString() {
        long jObjectNumber = xrefEntry().key().objectNumber();
        xrefEntry().key().generation();
        return jObjectNumber + " " + jObjectNumber + " R";
    }
}
