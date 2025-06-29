package org.sejda.sambox.cos;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/cos/COSInteger.class */
public final class COSInteger extends COSNumber {
    private static final Map<Long, COSInteger> CACHE = new ConcurrentHashMap();
    public static final COSInteger ZERO = get(0);
    public static final COSInteger ONE = get(1);
    public static final COSInteger TWO = get(2);
    public static final COSInteger THREE = get(3);
    private final long value;

    public static COSInteger get(long val) {
        COSInteger value = CACHE.get(Long.valueOf(val));
        if (value == null) {
            COSInteger newVal = new COSInteger(val);
            value = CACHE.putIfAbsent(Long.valueOf(val), newVal);
            if (value == null) {
                value = newVal;
            }
        }
        return value;
    }

    public COSInteger(long value) {
        this.value = value;
    }

    public boolean equals(Object o) {
        return (o instanceof COSInteger) && ((COSInteger) o).intValue() == intValue();
    }

    public int hashCode() {
        return Long.hashCode(this.value);
    }

    public String toString() {
        return Long.toString(this.value);
    }

    @Override // org.sejda.sambox.cos.COSNumber
    public float floatValue() {
        return this.value;
    }

    @Override // org.sejda.sambox.cos.COSNumber
    public double doubleValue() {
        return this.value;
    }

    @Override // org.sejda.sambox.cos.COSNumber
    public int intValue() {
        return (int) this.value;
    }

    @Override // org.sejda.sambox.cos.COSNumber
    public long longValue() {
        return this.value;
    }

    @Override // org.sejda.sambox.cos.COSBase
    public void accept(COSVisitor visitor) throws IOException {
        visitor.visit(this);
    }
}
