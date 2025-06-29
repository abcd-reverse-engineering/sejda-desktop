package org.sejda.sambox.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.util.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/filter/ASCIIHexFilter.class */
final class ASCIIHexFilter extends Filter {
    private static final Logger LOG = LoggerFactory.getLogger(ASCIIHexFilter.class);
    private static final int[] REVERSE_HEX = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15};

    ASCIIHexFilter() {
    }

    @Override // org.sejda.sambox.filter.Filter
    public DecodeResult decode(InputStream encoded, OutputStream decoded, COSDictionary parameters, int index) throws IOException {
        int value;
        while (true) {
            try {
                int i = encoded.read();
                int firstByte = i;
                if (i == -1) {
                    break;
                }
                while (isWhitespace(firstByte)) {
                    firstByte = encoded.read();
                }
                if (firstByte == -1 || isEOD(firstByte)) {
                    break;
                }
                if (REVERSE_HEX[firstByte] == -1) {
                    LOG.error("Invalid hex, int: " + firstByte + " char: " + ((char) firstByte));
                }
                value = REVERSE_HEX[firstByte] * 16;
                int secondByte = encoded.read();
                if (secondByte == -1 || isEOD(secondByte)) {
                    break;
                }
                if (REVERSE_HEX[secondByte] == -1) {
                    LOG.error("Invalid hex, int: " + secondByte + " char: " + ((char) secondByte));
                }
                decoded.write(value + REVERSE_HEX[secondByte]);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new IOException("Illegal character in ASCIIHexFilter", e);
            }
        }
        decoded.write(value);
        decoded.flush();
        return new DecodeResult(parameters);
    }

    private boolean isWhitespace(int c) {
        return c == 0 || c == 9 || c == 10 || c == 12 || c == 13 || c == 32;
    }

    private boolean isEOD(int c) {
        return c == 62;
    }

    @Override // org.sejda.sambox.filter.Filter
    public void encode(InputStream input, OutputStream encoded, COSDictionary parameters) throws IOException {
        while (true) {
            int byteRead = input.read();
            if (byteRead != -1) {
                encoded.write(Hex.getBytes((byte) byteRead));
            } else {
                encoded.flush();
                return;
            }
        }
    }
}
