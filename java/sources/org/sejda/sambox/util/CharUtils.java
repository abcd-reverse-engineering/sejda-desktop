package org.sejda.sambox.util;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/util/CharUtils.class */
public final class CharUtils {
    public static final byte ASCII_LINE_FEED = 10;
    public static final byte ASCII_FORM_FEED = 12;
    public static final byte ASCII_CARRIAGE_RETURN = 13;
    public static final byte ASCII_BACKSPACE = 8;
    public static final byte ASCII_HORIZONTAL_TAB = 9;
    private static final byte ASCII_ZERO = 48;
    private static final byte ASCII_SEVEN = 55;
    private static final byte ASCII_NINE = 57;
    public static final byte ASCII_NULL = 0;
    public static final byte ASCII_SPACE = 32;
    private static final byte ASCII_UPPERCASE_A = 65;
    private static final byte ASCII_UPPERCASE_Z = 90;
    private static final byte ASCII_LOWERCASE_A = 97;
    private static final byte ASCII_LOWERCASE_Z = 122;

    private CharUtils() {
    }

    public static boolean isEndOfName(int ch) {
        return isWhitespace(ch) || ch == 62 || ch == 60 || ch == 91 || ch == 93 || ch == 47 || ch == 41 || ch == 40 || ch == 37;
    }

    public static boolean isEOF(int c) {
        return c == -1;
    }

    public static boolean isEOL(int c) {
        return isCarriageReturn(c) || isLineFeed(c);
    }

    public static boolean isLineFeed(int c) {
        return 10 == c;
    }

    public static boolean isCarriageReturn(int c) {
        return 13 == c;
    }

    public static boolean isWhitespace(int c) {
        return c == 0 || c == 9 || c == 12 || isEOL(c) || isSpace(c);
    }

    public static boolean isNul(int c) {
        return c == 0;
    }

    public static boolean isSpace(int c) {
        return 32 == c;
    }

    public static boolean isDigit(int c) {
        return c >= ASCII_ZERO && c <= ASCII_NINE;
    }

    public static boolean isLetter(int c) {
        return (c >= ASCII_UPPERCASE_A && c <= ASCII_UPPERCASE_Z) || (c >= ASCII_LOWERCASE_A && c <= ASCII_LOWERCASE_Z);
    }

    public static boolean isOctalDigit(int c) {
        return c >= ASCII_ZERO && c <= ASCII_SEVEN;
    }

    public static boolean isHexDigit(int c) {
        return isDigit(c) || (c >= ASCII_LOWERCASE_A && c <= 102) || (c >= ASCII_UPPERCASE_A && c <= 70);
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
