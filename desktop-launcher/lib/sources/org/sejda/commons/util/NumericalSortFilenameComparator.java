package org.sejda.commons.util;

import java.io.File;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: org.sejda.sejda-commons-3.0.0.jar:org/sejda/commons/util/NumericalSortFilenameComparator.class */
public class NumericalSortFilenameComparator implements Comparator<File> {
    private static final Pattern DIGITS_PATTERN = Pattern.compile("^(\\d*)(.*)(\\d*)$");
    private static final Comparator<String> BIG_INT_COMPARATOR = (a, b) -> {
        BigInteger bigA = toBigInteger(a);
        BigInteger bigB = toBigInteger(b);
        if (Objects.nonNull(bigA) && Objects.nonNull(bigB)) {
            return bigA.compareTo(bigB);
        }
        return 0;
    };
    private final Comparator<Matcher> digitsPatternMatcherComparator;
    private final Comparator<String> fallback;

    public NumericalSortFilenameComparator(Comparator<String> fallback) {
        this.digitsPatternMatcherComparator = Comparator.comparing(m -> {
            return m.group(1);
        }, BIG_INT_COMPARATOR).thenComparing(m2 -> {
            return m2.group(2);
        }, this::stringCompare).thenComparing(m3 -> {
            return m3.group(3);
        }, BIG_INT_COMPARATOR);
        this.fallback = (Comparator) Optional.ofNullable(fallback).orElse(Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER));
    }

    public NumericalSortFilenameComparator() {
        this(null);
    }

    @Override // java.util.Comparator
    public int compare(File a, File b) {
        Optional map = Optional.ofNullable(a).map(NumericalSortFilenameComparator::basename);
        Pattern pattern = DIGITS_PATTERN;
        Objects.requireNonNull(pattern);
        Matcher m1 = (Matcher) map.map((v1) -> {
            return r1.matcher(v1);
        }).filter((v0) -> {
            return v0.matches();
        }).orElse(null);
        Optional map2 = Optional.ofNullable(b).map(NumericalSortFilenameComparator::basename);
        Pattern pattern2 = DIGITS_PATTERN;
        Objects.requireNonNull(pattern2);
        Matcher m2 = (Matcher) map2.map((v1) -> {
            return r1.matcher(v1);
        }).filter((v0) -> {
            return v0.matches();
        }).orElse(null);
        if (Objects.nonNull(m1) && Objects.nonNull(m2) && (StringUtils.isEmpty(m1.group(1)) ^ StringUtils.isEmpty(m2.group(1)))) {
            return this.fallback.compare(name(a), name(b));
        }
        int retVal = Comparator.nullsLast(this.digitsPatternMatcherComparator).compare(m1, m2);
        if (retVal == 0) {
            return this.fallback.compare(name(a), name(b));
        }
        return retVal;
    }

    private int stringCompare(String a, String b) {
        if (StringUtils.isNotEmpty(a) && StringUtils.isNotEmpty(b)) {
            return this.fallback.compare(a, b);
        }
        return 0;
    }

    private static BigInteger toBigInteger(String value) {
        if (StringUtils.isNotEmpty(value)) {
            return new BigInteger(value);
        }
        return null;
    }

    private static String basename(File file) {
        if (Objects.nonNull(file)) {
            String filename = file.getName();
            int index = filename.lastIndexOf(46);
            if (index > 0) {
                return filename.substring(0, index);
            }
            if (StringUtils.isNotEmpty(filename)) {
                return filename;
            }
            return null;
        }
        return null;
    }

    private static String name(File file) {
        return (String) Optional.ofNullable(file).map((v0) -> {
            return v0.getName();
        }).orElse(null);
    }
}
