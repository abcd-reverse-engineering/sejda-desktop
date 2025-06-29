package org.sejda.sambox.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/util/Hex.class */
public final class Hex {
    private static final Logger LOG = LoggerFactory.getLogger(Hex.class);
    private static final byte[] HEX_BYTES = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70};
    private static final char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private Hex() {
    }

    public static String getString(byte b) {
        char[] chars = {HEX_CHARS[getHighNibble(b)], HEX_CHARS[getLowNibble(b)]};
        return new String(chars);
    }

    public static String getString(byte[] bytes) {
        StringBuilder string = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            string.append(HEX_CHARS[getHighNibble(b)]).append(HEX_CHARS[getLowNibble(b)]);
        }
        return string.toString();
    }

    public static byte[] getBytes(byte b) {
        return new byte[]{HEX_BYTES[getHighNibble(b)], HEX_BYTES[getLowNibble(b)]};
    }

    public static byte[] getBytes(byte[] bytes) {
        byte[] asciiBytes = new byte[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            asciiBytes[i * 2] = HEX_BYTES[getHighNibble(bytes[i])];
            asciiBytes[(i * 2) + 1] = HEX_BYTES[getLowNibble(bytes[i])];
        }
        return asciiBytes;
    }

    public static char[] getChars(short num) {
        char[] hex = {HEX_CHARS[(num >> 12) & 15], HEX_CHARS[(num >> 8) & 15], HEX_CHARS[(num >> 4) & 15], HEX_CHARS[num & 15]};
        return hex;
    }

    public static char[] getCharsUTF16BE(String text) {
        char[] hex = new char[text.length() * 4];
        int charIdx = 0;
        for (int stringIdx = 0; stringIdx < text.length(); stringIdx++) {
            char c = text.charAt(stringIdx);
            int i = charIdx;
            int charIdx2 = charIdx + 1;
            hex[i] = HEX_CHARS[(c >> '\f') & 15];
            int charIdx3 = charIdx2 + 1;
            hex[charIdx2] = HEX_CHARS[(c >> '\b') & 15];
            int charIdx4 = charIdx3 + 1;
            hex[charIdx3] = HEX_CHARS[(c >> 4) & 15];
            charIdx = charIdx4 + 1;
            hex[charIdx4] = HEX_CHARS[c & 15];
        }
        return hex;
    }

    public static void writeHexByte(byte b, OutputStream output) throws IOException {
        output.write(HEX_BYTES[getHighNibble(b)]);
        output.write(HEX_BYTES[getLowNibble(b)]);
    }

    public static void writeHexBytes(byte[] bytes, OutputStream output) throws IOException {
        for (byte b : bytes) {
            writeHexByte(b, output);
        }
    }

    private static int getHighNibble(byte b) {
        return (b & 240) >> 4;
    }

    private static int getLowNibble(byte b) {
        return b & 15;
    }

    public static byte[] decodeHex(String s) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = 0;
        while (i < s.length() - 1) {
            if (s.charAt(i) == '\n' || s.charAt(i) == '\r') {
                i++;
            } else {
                String hexByte = s.substring(i, i + 2);
                try {
                    baos.write(Integer.parseInt(hexByte, 16));
                    i += 2;
                } catch (NumberFormatException ex) {
                    LOG.error("Can't parse " + hexByte + ", aborting decode", ex);
                }
            }
        }
        return baos.toByteArray();
    }
}
