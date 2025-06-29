package org.sejda.sambox.cos;

import java.io.IOException;
import org.sejda.commons.util.RequireUtils;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/cos/COSNumber.class */
public abstract class COSNumber extends COSBase {
    public abstract float floatValue();

    public abstract double doubleValue();

    public abstract int intValue();

    public abstract long longValue();

    private static boolean isNumber(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        int len = s.length();
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (c != 'E' && c != 'e' && c != '+' && c != '-' && c != '.' && !Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static COSNumber get(String number) throws IOException {
        RequireUtils.requireNotNullArg(number, "Number cannot be null");
        RequireUtils.requireArg(isNumber(number), "Invalid number " + number);
        if (number.length() == 1) {
            char digit = number.charAt(0);
            if ('0' <= digit && digit <= '9') {
                return COSInteger.get(digit - 48);
            }
            return COSInteger.ZERO;
        }
        if (isFloat(number)) {
            return new COSFloat(number);
        }
        try {
            return COSInteger.get(Long.parseLong(number));
        } catch (NumberFormatException e) {
            return COSInteger.ZERO;
        }
    }

    private static boolean isFloat(String number) {
        int length = number.length();
        for (int i = 0; i < length; i++) {
            char digit = number.charAt(i);
            if (digit == '.' || digit == 'e') {
                return true;
            }
        }
        return false;
    }

    @Override // org.sejda.sambox.cos.COSBase
    public void idIfAbsent(IndirectCOSObjectIdentifier id) {
    }
}
