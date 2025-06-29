package org.sejda.model.util;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/util/RomanNumbersUtils.class */
public final class RomanNumbersUtils {
    private RomanNumbersUtils() {
    }

    public static String toRoman(long toConvert) {
        if (toConvert < 0) {
            throw new IllegalArgumentException();
        }
        if (toConvert == 0) {
            return "nulla";
        }
        long n = toConvert;
        StringBuilder buf = new StringBuilder();
        Numeral[] values = Numeral.values();
        for (int i = values.length - 1; i >= 0; i--) {
            while (n >= values[i].weight) {
                buf.append(values[i]);
                n -= values[i].weight;
            }
        }
        return buf.toString();
    }

    /* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/util/RomanNumbersUtils$Numeral.class */
    private enum Numeral {
        I(1),
        IV(4),
        V(5),
        IX(9),
        X(10),
        XL(40),
        L(50),
        XC(90),
        C(100),
        CD(400),
        D(500),
        CM(900),
        M(1000);

        private final int weight;

        Numeral(int weight) {
            this.weight = weight;
        }
    }
}
