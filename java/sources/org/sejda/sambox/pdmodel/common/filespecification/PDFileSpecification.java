package org.sejda.sambox.pdmodel.common.filespecification;

import java.io.IOException;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.cos.COSString;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/common/filespecification/PDFileSpecification.class */
public interface PDFileSpecification extends COSObjectable {
    String getFile();

    void setFile(String str);

    static PDFileSpecification createFS(COSBase base) throws IOException {
        PDFileSpecification retval = null;
        if (base != null) {
            if (base instanceof COSString) {
                retval = new PDSimpleFileSpecification((COSString) base);
            } else if (base instanceof COSDictionary) {
                retval = new PDComplexFileSpecification((COSDictionary) base);
            } else {
                throw new IOException("Error: Unknown file specification " + base);
            }
        }
        return retval;
    }
}
