package org.sejda.core.support.prefix.processor;

import org.sejda.model.util.IOUtils;

@Deprecated
/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/support/prefix/processor/PrefixUtils.class */
public final class PrefixUtils {
    private PrefixUtils() {
    }

    public static String toSafeFilename(String input) {
        return IOUtils.toSafeFilename(input);
    }

    public static String toStrictFilename(String input) {
        return IOUtils.toStrictFilename(input);
    }
}
