package org.sejda.sambox.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.Bidi;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/util/BidiUtils.class */
public final class BidiUtils {
    private static final Logger LOG = LoggerFactory.getLogger(BidiUtils.class);
    private static Map<Character, Character> MIRRORING_CHAR_MAP = new HashMap();

    static {
        InputStream stream = BidiUtils.class.getResourceAsStream("/org/sejda/sambox/resources/text/BidiMirroring.txt");
        if (Objects.nonNull(stream)) {
            parseBidiFile(stream);
        } else {
            LOG.warn("Could not find 'BidiMirroring.txt', mirroring char map will be empty");
        }
    }

    private BidiUtils() {
    }

    public static String visualToLogical(String text) {
        if (!CharUtils.isBlank(text)) {
            Bidi bidi = new Bidi(text, -2);
            if (!bidi.isLeftToRight()) {
                int runCount = bidi.getRunCount();
                byte[] levels = new byte[runCount];
                Integer[] runs = new Integer[runCount];
                for (int i = 0; i < runCount; i++) {
                    levels[i] = (byte) bidi.getRunLevel(i);
                    runs[i] = Integer.valueOf(i);
                }
                Bidi.reorderVisually(levels, 0, runs, 0, runCount);
                StringBuilder result = new StringBuilder();
                for (int i2 = 0; i2 < runCount; i2++) {
                    int index = runs[i2].intValue();
                    int start = bidi.getRunStart(index);
                    int end = bidi.getRunLimit(index);
                    if ((levels[index] & 1) != 0) {
                        while (true) {
                            end--;
                            if (end >= start) {
                                char character = text.charAt(end);
                                if (Character.isMirrored(text.codePointAt(end)) && MIRRORING_CHAR_MAP.containsKey(Character.valueOf(character))) {
                                    result.append(MIRRORING_CHAR_MAP.get(Character.valueOf(character)));
                                } else {
                                    result.append(character);
                                }
                            }
                        }
                    } else {
                        result.append((CharSequence) text, start, end);
                    }
                }
                return result.toString();
            }
        }
        return text;
    }

    private static void parseBidiFile(InputStream inputStream) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            try {
                reader.lines().map(l -> {
                    int comment = l.indexOf(35);
                    if (comment != -1) {
                        return l.substring(0, comment);
                    }
                    return l;
                }).filter(l2 -> {
                    return !CharUtils.isBlank(l2);
                }).filter(l3 -> {
                    return l3.length() > 1;
                }).forEach(l4 -> {
                    String[] tokens = l4.split(";");
                    if (tokens.length == 2) {
                        MIRRORING_CHAR_MAP.put(Character.valueOf((char) Integer.parseInt(tokens[0].trim(), 16)), Character.valueOf((char) Integer.parseInt(tokens[1].trim(), 16)));
                    }
                });
                reader.close();
            } finally {
            }
        } catch (IOException e) {
            LOG.warn("An error occurred while parsing BidiMirroring.txt", e);
        }
    }
}
