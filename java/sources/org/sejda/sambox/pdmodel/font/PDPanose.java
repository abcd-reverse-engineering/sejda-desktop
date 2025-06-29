package org.sejda.sambox.pdmodel.font;

import java.util.Arrays;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/PDPanose.class */
public class PDPanose {
    public static final int LENGTH = 12;
    private final byte[] bytes;

    public PDPanose(byte[] bytes) {
        this.bytes = bytes;
    }

    public int getFamilyClass() {
        return (this.bytes[0] << 8) | (this.bytes[1] & 255);
    }

    public PDPanoseClassification getPanose() {
        byte[] panose = Arrays.copyOfRange(this.bytes, 2, 12);
        return new PDPanoseClassification(panose);
    }
}
