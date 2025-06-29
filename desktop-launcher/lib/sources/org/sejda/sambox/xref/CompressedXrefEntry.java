package org.sejda.sambox.xref;

import org.sejda.commons.util.RequireUtils;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/xref/CompressedXrefEntry.class */
public final class CompressedXrefEntry extends XrefEntry {
    private final long objectStreamNumber;
    private final long index;

    private CompressedXrefEntry(XrefType type, long objectNumber, long byteOffset, int generationNumber, long objectStreamNumber, long index) {
        super(type, objectNumber, byteOffset, generationNumber);
        RequireUtils.requireArg(objectStreamNumber >= 0, "Containing object stream number cannot be negative");
        this.objectStreamNumber = objectStreamNumber;
        this.index = index;
    }

    public long getObjectStreamNumber() {
        return this.objectStreamNumber;
    }

    @Override // org.sejda.sambox.xref.XrefEntry
    public byte[] toXrefStreamEntry(int secondFieldLength, int thirdFieldLength) {
        byte[] retVal = new byte[1 + secondFieldLength + thirdFieldLength];
        retVal[0] = 2;
        copyBytesTo(getObjectStreamNumber(), secondFieldLength, retVal, 1);
        copyBytesTo(this.index, thirdFieldLength, retVal, 1 + secondFieldLength);
        return retVal;
    }

    @Override // org.sejda.sambox.xref.XrefEntry
    public String toString() {
        return String.format("%s offset=%d objectStreamNumber=%d, %s", getType().toString(), Long.valueOf(getByteOffset()), Long.valueOf(this.objectStreamNumber), key().toString());
    }

    public static CompressedXrefEntry compressedEntry(long objectNumber, long objectStreamNumber, long index) {
        return new CompressedXrefEntry(XrefType.COMPRESSED, objectNumber, -1L, 0, objectStreamNumber, index);
    }
}
