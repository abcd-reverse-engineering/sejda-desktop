package org.sejda.core.support.util;

import java.util.Locale;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/support/util/HumanReadableSize.class */
public class HumanReadableSize {
    public static final long KB = 1000;
    public static final long MB = 1000000;

    private HumanReadableSize() {
    }

    public static String toString(long size) {
        return toString(size, false);
    }

    public static String toString(long size, boolean roundUp) {
        String format = roundUp ? "%.0f" : "%.2f";
        String unit = "bytes";
        String unitSize = String.format("%.0f", Float.valueOf(size));
        if (size > MB) {
            unit = "MB";
            unitSize = String.format(format, Float.valueOf(size / 1000000.0f));
        } else if (size > 1000) {
            unit = "KB";
            unitSize = String.format(format, Float.valueOf(size / 1000.0f));
        }
        return String.format(Locale.US, "%s %s", unitSize, unit);
    }
}
