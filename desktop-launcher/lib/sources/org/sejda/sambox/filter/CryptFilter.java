package org.sejda.sambox.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/filter/CryptFilter.class */
final class CryptFilter extends Filter {
    CryptFilter() {
    }

    @Override // org.sejda.sambox.filter.Filter
    public DecodeResult decode(InputStream encoded, OutputStream decoded, COSDictionary parameters, int index) throws IOException {
        COSName encryptionName = (COSName) parameters.getDictionaryObject(COSName.NAME);
        if (encryptionName == null || encryptionName.equals(COSName.IDENTITY)) {
            Filter identityFilter = new IdentityFilter();
            identityFilter.decode(encoded, decoded, parameters, index);
            return new DecodeResult(parameters);
        }
        throw new IOException("Unsupported crypt filter " + encryptionName.getName());
    }

    @Override // org.sejda.sambox.filter.Filter
    public void encode(InputStream input, OutputStream encoded, COSDictionary parameters) throws IOException {
        COSName encryptionName = (COSName) parameters.getDictionaryObject(COSName.NAME);
        if (encryptionName == null || encryptionName.equals(COSName.IDENTITY)) {
            Filter identityFilter = new IdentityFilter();
            identityFilter.encode(input, encoded, parameters);
            return;
        }
        throw new IOException("Unsupported crypt filter " + encryptionName.getName());
    }
}
