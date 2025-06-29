package org.sejda.sambox.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.sejda.commons.util.IOUtils;
import org.sejda.sambox.cos.COSDictionary;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/filter/IdentityFilter.class */
final class IdentityFilter extends Filter {
    IdentityFilter() {
    }

    @Override // org.sejda.sambox.filter.Filter
    public DecodeResult decode(InputStream encoded, OutputStream decoded, COSDictionary parameters, int index) throws IOException {
        IOUtils.copy(encoded, decoded);
        decoded.flush();
        return new DecodeResult(parameters);
    }

    @Override // org.sejda.sambox.filter.Filter
    public void encode(InputStream input, OutputStream encoded, COSDictionary parameters) throws IOException {
        IOUtils.copy(input, encoded);
        encoded.flush();
    }
}
