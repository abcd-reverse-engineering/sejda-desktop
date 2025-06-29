package org.sejda.commons.util;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Supplier;

/* loaded from: org.sejda.sejda-commons-3.0.0.jar:org/sejda/commons/util/RequireUtils.class */
public final class RequireUtils {
    private RequireUtils() {
    }

    public static void requireNotNullArg(Object arg, String exceptionMessage) {
        requireArg(Objects.nonNull(arg), exceptionMessage);
    }

    public static void requireArg(boolean condition, String exceptionMessage) {
        if (!condition) {
            throw new IllegalArgumentException(exceptionMessage);
        }
    }

    public static void requireNotBlank(String value, String exceptionMessage) {
        requireArg(value != null && value.trim().length() > 0, exceptionMessage);
    }

    public static void requireIOCondition(boolean condition, String exceptionMessage) throws IOException {
        if (!condition) {
            throw new IOException(exceptionMessage);
        }
    }

    public static void requireState(boolean condition, String exceptionMessage) {
        if (!condition) {
            throw new IllegalStateException(exceptionMessage);
        }
    }

    public static void requireNotNegative(int victim) {
        if (victim < 0) {
            throw new IllegalArgumentException("The given value cannot be negative");
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: E extends java.lang.Throwable */
    public static <E extends Throwable> void require(boolean condition, Supplier<? extends E> supplier) throws Throwable {
        if (!condition) {
            throw supplier.get();
        }
    }
}
