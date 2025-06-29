package org.sejda.sambox.xref;

import java.util.Locale;
import org.sejda.sambox.cos.COSObjectKey;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/xref/XrefEntry.class */
public class XrefEntry {
    private static final String XREFTABLE_ENTRY_FORMAT = "%010d %05d %c\r\n";
    public static final XrefEntry DEFAULT_FREE_ENTRY = freeEntry(0, 65535);
    public static final long UNKNOWN_OFFSET = -1;
    private XrefType type;
    private COSObjectKey key;
    private long byteOffset;

    XrefEntry(XrefType type, long objectNumber, long byteOffset, int generationNumber) {
        this.type = type;
        this.key = new COSObjectKey(objectNumber, generationNumber);
        this.byteOffset = byteOffset;
    }

    public XrefType getType() {
        return this.type;
    }

    public long getByteOffset() {
        return this.byteOffset;
    }

    public void setByteOffset(long byteOffset) {
        this.byteOffset = byteOffset;
    }

    public long getObjectNumber() {
        return this.key.objectNumber();
    }

    public int getGenerationNumber() {
        return this.key.generation();
    }

    public boolean isUnknownOffset() {
        return this.byteOffset <= -1;
    }

    public COSObjectKey key() {
        return this.key;
    }

    public boolean owns(XrefEntry entry) {
        return entry != null && entry.getType() == XrefType.COMPRESSED && this.key.objectNumber() == ((CompressedXrefEntry) entry).getObjectStreamNumber();
    }

    public String toString() {
        return String.format("%s offset=%d, %s", this.type.toString(), Long.valueOf(this.byteOffset), this.key.toString());
    }

    public String toXrefTableEntry() {
        switch (this.type) {
            case IN_USE:
                return String.format(Locale.US, XREFTABLE_ENTRY_FORMAT, Long.valueOf(getByteOffset()), Integer.valueOf(getGenerationNumber()), 'n');
            case FREE:
                return String.format(Locale.US, XREFTABLE_ENTRY_FORMAT, Long.valueOf(getObjectNumber()), Integer.valueOf(getGenerationNumber()), 'f');
            default:
                throw new IllegalArgumentException("Only in_use and free entries can be written to an xref table");
        }
    }

    public byte[] toXrefStreamEntry(int secondFieldLength, int thirdFieldLength) {
        byte[] retVal = new byte[1 + secondFieldLength + thirdFieldLength];
        if (this.type == XrefType.FREE) {
            retVal[0] = 0;
            copyBytesTo(this.key.objectNumber(), secondFieldLength, retVal, 1);
            copyBytesTo(this.key.generation(), thirdFieldLength, retVal, 1 + secondFieldLength);
            return retVal;
        }
        retVal[0] = 1;
        copyBytesTo(this.byteOffset, secondFieldLength, retVal, 1);
        copyBytesTo(this.key.generation(), thirdFieldLength, retVal, 1 + secondFieldLength);
        return retVal;
    }

    protected void copyBytesTo(long data, int length, byte[] destination, int destinationIndex) {
        for (int i = 0; i < length; i++) {
            destination[((length + destinationIndex) - i) - 1] = (byte) (data & 255);
            data >>= 8;
        }
    }

    public static XrefEntry inUseEntry(long objectNumber, long byteOffset, int generationNumber) {
        return new XrefEntry(XrefType.IN_USE, objectNumber, byteOffset, generationNumber);
    }

    public static XrefEntry unknownOffsetEntry(long objectNumber, int generationNumber) {
        return new XrefEntry(XrefType.IN_USE, objectNumber, -1L, generationNumber);
    }

    public static XrefEntry freeEntry(long objectNumber, int generationNumber) {
        return new XrefEntry(XrefType.FREE, objectNumber, -1L, generationNumber);
    }
}
