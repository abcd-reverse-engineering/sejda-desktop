package org.sejda.core.support.util;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/support/util/RuntimeUtils.class */
public class RuntimeUtils {
    private RuntimeUtils() {
    }

    public static int getPercentageMemoryUsed() {
        long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();
        return (int) Math.round((usedMemory * 100.0d) / maxMemory);
    }
}
