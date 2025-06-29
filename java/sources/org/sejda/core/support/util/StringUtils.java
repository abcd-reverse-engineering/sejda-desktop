package org.sejda.core.support.util;

import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.ArabicShapingException;
import java.text.Bidi;
import java.text.Normalizer;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/support/util/StringUtils.class */
public final class StringUtils {
    private StringUtils() {
    }

    public static String isolateRTLIfRequired(String s) {
        if (isRtl(s)) {
            return "\u2068" + s + "\u2069";
        }
        return s;
    }

    public static boolean isRtl(String string) {
        if (string == null) {
            return false;
        }
        Bidi bidi = new Bidi(string, -2);
        return bidi.isMixed() || bidi.isRightToLeft();
    }

    public static boolean equalsNormalized(String s1, String s2) {
        return Normalizer.normalize(s1, Normalizer.Form.NFKC).equals(Normalizer.normalize(s2, Normalizer.Form.NFKC));
    }

    public static String shapeArabicIf(String s) {
        if (isRtl(s)) {
            return shapeArabic(s);
        }
        return s;
    }

    public static String shapeArabic(String s) {
        try {
            com.ibm.icu.text.Bidi bidi = new com.ibm.icu.text.Bidi(new ArabicShaping(8).shape(s), 127);
            bidi.setReorderingMode(0);
            return bidi.writeReordered(2);
        } catch (ArabicShapingException e) {
            return s;
        }
    }
}
