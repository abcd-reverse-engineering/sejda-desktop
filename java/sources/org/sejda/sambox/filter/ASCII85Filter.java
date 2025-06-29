package org.sejda.sambox.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.sejda.commons.util.IOUtils;
import org.sejda.sambox.cos.COSDictionary;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/filter/ASCII85Filter.class */
final class ASCII85Filter extends Filter {
    ASCII85Filter() {
    }

    @Override // org.sejda.sambox.filter.Filter
    public DecodeResult decode(InputStream encoded, OutputStream decoded, COSDictionary parameters, int index) throws IOException {
        ASCII85InputStream is = new ASCII85InputStream(encoded);
        try {
            IOUtils.copy(is, decoded);
            is.close();
            decoded.flush();
            return new DecodeResult(parameters);
        } catch (Throwable th) {
            try {
                is.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // org.sejda.sambox.filter.Filter
    public void encode(InputStream input, OutputStream encoded, COSDictionary parameters) throws IOException {
        ASCII85OutputStream os = new ASCII85OutputStream(encoded);
        try {
            IOUtils.copy(input, os);
            os.close();
            encoded.flush();
        } catch (Throwable th) {
            try {
                os.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}
