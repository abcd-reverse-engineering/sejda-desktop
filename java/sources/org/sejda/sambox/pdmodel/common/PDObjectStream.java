package org.sejda.sambox.pdmodel.common;

import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.pdmodel.PDDocument;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/common/PDObjectStream.class */
public class PDObjectStream extends PDStream {
    public PDObjectStream(COSStream str) {
        super(str);
    }

    public static PDObjectStream createStream(PDDocument document) {
        COSStream cosStream = new COSStream();
        PDObjectStream strm = new PDObjectStream(cosStream);
        strm.getCOSObject().setItem(COSName.TYPE, (COSBase) COSName.OBJ_STM);
        return strm;
    }

    public String getType() {
        return getCOSObject().getNameAsString(COSName.TYPE);
    }

    public int getNumberOfObjects() {
        return getCOSObject().getInt(COSName.N, 0);
    }

    public void setNumberOfObjects(int n) {
        getCOSObject().setInt(COSName.N, n);
    }

    public int getFirstByteOffset() {
        return getCOSObject().getInt(COSName.FIRST, 0);
    }

    public void setFirstByteOffset(int n) {
        getCOSObject().setInt(COSName.FIRST, n);
    }

    public PDObjectStream getExtends() {
        PDObjectStream retval = null;
        COSStream stream = (COSStream) getCOSObject().getDictionaryObject(COSName.EXTENDS);
        if (stream != null) {
            retval = new PDObjectStream(stream);
        }
        return retval;
    }

    public void setExtends(PDObjectStream stream) {
        getCOSObject().setItem(COSName.EXTENDS, stream);
    }
}
