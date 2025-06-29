package org.sejda.sambox.pdmodel.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSStream;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/common/PDMetadata.class */
public class PDMetadata extends PDStream {
    public PDMetadata() {
        getCOSObject().setName(COSName.TYPE, "Metadata");
        getCOSObject().setName(COSName.SUBTYPE, "XML");
    }

    public PDMetadata(InputStream str) throws IOException {
        super(str);
        getCOSObject().setName(COSName.TYPE, "Metadata");
        getCOSObject().setName(COSName.SUBTYPE, "XML");
    }

    public PDMetadata(COSStream str) {
        super(str);
    }

    public InputStream exportXMPMetadata() throws IOException {
        return createInputStream();
    }

    public void importXMPMetadata(byte[] xmp) throws IOException {
        OutputStream os = createOutputStream();
        try {
            os.write(xmp);
            if (os != null) {
                os.close();
            }
        } catch (Throwable th) {
            if (os != null) {
                try {
                    os.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }
}
