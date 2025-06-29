package org.sejda.sambox.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.sejda.sambox.cos.COSDictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/filter/RunLengthDecodeFilter.class */
final class RunLengthDecodeFilter extends Filter {
    private static final Logger LOG = LoggerFactory.getLogger(RunLengthDecodeFilter.class);
    private static final int RUN_LENGTH_EOD = 128;

    RunLengthDecodeFilter() {
    }

    @Override // org.sejda.sambox.filter.Filter
    public DecodeResult decode(InputStream encoded, OutputStream decoded, COSDictionary parameters, int index) throws IOException {
        int compressedRead;
        byte[] buffer = new byte[128];
        while (true) {
            int dupAmount = encoded.read();
            if (dupAmount == -1 || dupAmount == 128) {
                break;
            }
            if (dupAmount <= 127) {
                int i = dupAmount + 1;
                while (true) {
                    int amountToCopy = i;
                    if (amountToCopy <= 0 || (compressedRead = encoded.read(buffer, 0, amountToCopy)) == -1) {
                        break;
                    }
                    decoded.write(buffer, 0, compressedRead);
                    i = amountToCopy - compressedRead;
                }
            } else {
                int dupByte = encoded.read();
                if (dupByte == -1) {
                    break;
                }
                for (int i2 = 0; i2 < 257 - dupAmount; i2++) {
                    decoded.write(dupByte);
                }
            }
        }
        return new DecodeResult(parameters);
    }

    @Override // org.sejda.sambox.filter.Filter
    public void encode(InputStream input, OutputStream encoded, COSDictionary parameters) {
        LOG.warn("RunLengthDecodeFilter.encode is not implemented yet, skipping this stream.");
    }
}
