package org.sejda.sambox.pdmodel.font;

import org.apache.fontbox.FontBoxFont;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/FontInfo.class */
public abstract class FontInfo {
    public abstract String getPostScriptName();

    public abstract FontFormat getFormat();

    public abstract CIDSystemInfo getCIDSystemInfo();

    public abstract FontBoxFont getFont();

    public abstract int getFamilyClass();

    public abstract int getWeightClass();

    public abstract int getCodePageRange1();

    public abstract int getCodePageRange2();

    public abstract int getMacStyle();

    public abstract PDPanoseClassification getPanose();

    final int getWeightClassAsPanose() {
        int usWeightClass = getWeightClass();
        switch (usWeightClass) {
            case -1:
                break;
            case 0:
                break;
            case 100:
                break;
            case 200:
                break;
            case 300:
                break;
            case 400:
                break;
            case 500:
                break;
            case 600:
                break;
            case 700:
                break;
            case 800:
                break;
            case 900:
                break;
        }
        return 0;
    }

    final long getCodePageRange() {
        long range1 = getCodePageRange1() & 4294967295L;
        long range2 = getCodePageRange2() & 4294967295L;
        return (range2 << 32) | range1;
    }

    public String toString() {
        return getPostScriptName() + " (" + getFormat() + ", mac: 0x" + Integer.toHexString(getMacStyle()) + ", os/2: 0x" + Integer.toHexString(getFamilyClass()) + ", cid: " + getCIDSystemInfo() + ")";
    }
}
