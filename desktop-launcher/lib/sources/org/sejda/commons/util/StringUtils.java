package org.sejda.commons.util;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/* loaded from: org.sejda.sejda-commons-3.0.0.jar:org/sejda/commons/util/StringUtils.class */
public class StringUtils {
    private StringUtils() {
    }

    public static String normalizeWhitespace(String in) {
        String result = in.replaceAll("[\\n\\t\\r]", "").replaceAll("\\p{Z}", " ").replaceAll("\\s", " ");
        return result.replace((char) 160, ' ');
    }

    public static Set<Character> difference(String s1, String s2) {
        return (Set) s1.codePoints().filter(c -> {
            return s2.indexOf(c) < 0;
        }).mapToObj(c2 -> {
            return Character.valueOf((char) c2);
        }).collect(Collectors.toSet());
    }

    public static String asUnicodes(String in) {
        if (Objects.nonNull(in)) {
            StringBuilder result = new StringBuilder();
            int iCharCount = 0;
            while (true) {
                int offset = iCharCount;
                if (offset < in.length()) {
                    int codepoint = in.codePointAt(offset);
                    result.append("\\U+").append(Integer.toHexString(codepoint).toUpperCase());
                    iCharCount = offset + Character.charCount(codepoint);
                } else {
                    return result.toString();
                }
            }
        } else {
            return null;
        }
    }

    public static boolean isEmpty(CharSequence cs) {
        return Objects.isNull(cs) || cs.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    public static String normalizeLineEndings(String in) {
        return in.replaceAll("\\r\\n", "\n");
    }
}
