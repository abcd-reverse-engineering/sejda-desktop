package org.sejda.sambox.pdmodel.common.filespecification;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import org.sejda.model.pdf.PdfMetadataFields;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.pdmodel.common.PDStream;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/common/filespecification/PDEmbeddedFile.class */
public class PDEmbeddedFile extends PDStream {
    public PDEmbeddedFile(COSStream str) {
        super(str);
    }

    public PDEmbeddedFile(InputStream str) throws IOException {
        super(str);
        getCOSObject().setItem(COSName.TYPE, (COSBase) COSName.EMBEDDED_FILE);
    }

    public PDEmbeddedFile(InputStream input, COSName filter) throws IOException {
        super(input, filter);
        getCOSObject().setItem(COSName.TYPE, (COSBase) COSName.EMBEDDED_FILE);
    }

    public void setSubtype(String mimeType) {
        getCOSObject().setName(COSName.SUBTYPE, mimeType);
    }

    public String getSubtype() {
        return getCOSObject().getNameAsString(COSName.SUBTYPE);
    }

    public int getSize() {
        return getCOSObject().getEmbeddedInt("Params", "Size");
    }

    public void setSize(long size) {
        getCOSObject().setEmbeddedInt("Params", "Size", size);
    }

    public Calendar getCreationDate() {
        return getCOSObject().getEmbeddedDate("Params", "CreationDate");
    }

    public void setCreationDate(Calendar creation) {
        getCOSObject().setEmbeddedDate("Params", "CreationDate", creation);
    }

    public Calendar getModDate() {
        return getCOSObject().getEmbeddedDate("Params", "ModDate");
    }

    public void setModDate(Calendar mod) {
        getCOSObject().setEmbeddedDate("Params", "ModDate", mod);
    }

    public String getCheckSum() {
        return getCOSObject().getEmbeddedString("Params", "CheckSum");
    }

    public void setCheckSum(String checksum) {
        getCOSObject().setEmbeddedString("Params", "CheckSum", checksum);
    }

    public String getMacSubtype() {
        String retval = null;
        COSDictionary params = (COSDictionary) getCOSObject().getDictionaryObject(COSName.PARAMS);
        if (params != null) {
            retval = params.getEmbeddedString("Mac", "Subtype");
        }
        return retval;
    }

    public void setMacSubtype(String macSubtype) {
        COSDictionary params = (COSDictionary) getCOSObject().getDictionaryObject(COSName.PARAMS);
        if (params == null && macSubtype != null) {
            params = new COSDictionary();
            getCOSObject().setItem(COSName.PARAMS, (COSBase) params);
        }
        if (params != null) {
            params.setEmbeddedString("Mac", "Subtype", macSubtype);
        }
    }

    public String getMacCreator() {
        String retval = null;
        COSDictionary params = (COSDictionary) getCOSObject().getDictionaryObject(COSName.PARAMS);
        if (params != null) {
            retval = params.getEmbeddedString("Mac", PdfMetadataFields.CREATOR);
        }
        return retval;
    }

    public void setMacCreator(String macCreator) {
        COSDictionary params = (COSDictionary) getCOSObject().getDictionaryObject(COSName.PARAMS);
        if (params == null && macCreator != null) {
            params = new COSDictionary();
            getCOSObject().setItem(COSName.PARAMS, (COSBase) params);
        }
        if (params != null) {
            params.setEmbeddedString("Mac", PdfMetadataFields.CREATOR, macCreator);
        }
    }

    public String getMacResFork() {
        String retval = null;
        COSDictionary params = (COSDictionary) getCOSObject().getDictionaryObject(COSName.PARAMS);
        if (params != null) {
            retval = params.getEmbeddedString("Mac", "ResFork");
        }
        return retval;
    }

    public void setMacResFork(String macResFork) {
        COSDictionary params = (COSDictionary) getCOSObject().getDictionaryObject(COSName.PARAMS);
        if (params == null && macResFork != null) {
            params = new COSDictionary();
            getCOSObject().setItem(COSName.PARAMS, (COSBase) params);
        }
        if (params != null) {
            params.setEmbeddedString("Mac", "ResFork", macResFork);
        }
    }
}
