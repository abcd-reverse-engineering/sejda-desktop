package org.sejda.sambox.pdmodel.interactive.form;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.ParserConfigurationException;
import org.sejda.commons.util.IOUtils;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.util.XMLUtil;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/form/PDXFAResource.class */
public final class PDXFAResource implements COSObjectable {
    private static final int BUFFER_SIZE = 1024;
    private COSBase xfa;

    public PDXFAResource(COSBase xfaBase) {
        this.xfa = xfaBase;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSBase getCOSObject() {
        return this.xfa;
    }

    public byte[] getBytes() throws IOException {
        InputStream is = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                COSBase cOSObject = getCOSObject();
                if (cOSObject instanceof COSArray) {
                    COSArray cosArray = (COSArray) cOSObject;
                    byte[] xfaBytes = new byte[BUFFER_SIZE];
                    for (int i = 1; i < cosArray.size(); i += 2) {
                        COSBase cosObj = cosArray.getObject(i);
                        if (cosObj instanceof COSStream) {
                            is = ((COSStream) cosObj).getUnfilteredStream();
                            while (true) {
                                int nRead = is.read(xfaBytes, 0, xfaBytes.length);
                                if (nRead == -1) {
                                    break;
                                }
                                baos.write(xfaBytes, 0, nRead);
                            }
                            baos.flush();
                        }
                    }
                } else if (this.xfa.getCOSObject() instanceof COSStream) {
                    byte[] xfaBytes2 = new byte[BUFFER_SIZE];
                    is = ((COSStream) this.xfa.getCOSObject()).getUnfilteredStream();
                    while (true) {
                        int nRead2 = is.read(xfaBytes2, 0, xfaBytes2.length);
                        if (nRead2 == -1) {
                            break;
                        }
                        baos.write(xfaBytes2, 0, nRead2);
                    }
                    baos.flush();
                }
                byte[] byteArray = baos.toByteArray();
                baos.close();
                IOUtils.close(is);
                return byteArray;
            } finally {
            }
        } catch (Throwable th) {
            IOUtils.close(is);
            throw th;
        }
    }

    public Document getDocument() throws ParserConfigurationException, SAXException, IOException {
        return XMLUtil.parse(new ByteArrayInputStream(getBytes()), true);
    }
}
