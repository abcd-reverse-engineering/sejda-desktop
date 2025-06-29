package org.sejda.sambox.xref;

import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.common.PDDictionaryWrapper;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/xref/FileTrailer.class */
public class FileTrailer extends PDDictionaryWrapper {
    private long xrefOffset;
    private String fallbackScanStatus;

    public FileTrailer() {
        this.xrefOffset = -1L;
        this.fallbackScanStatus = null;
    }

    public FileTrailer(COSDictionary trailerDictionary) {
        super(trailerDictionary);
        this.xrefOffset = -1L;
        this.fallbackScanStatus = null;
    }

    public long xrefOffset() {
        return this.xrefOffset;
    }

    public void xrefOffset(long offset) {
        this.xrefOffset = offset;
    }

    public boolean isXrefStream() {
        return COSName.XREF.equals(getCOSObject().getCOSName(COSName.TYPE));
    }

    public String getFallbackScanStatus() {
        return this.fallbackScanStatus;
    }

    public void setFallbackScanStatus(String fallbackScanStatus) {
        this.fallbackScanStatus = fallbackScanStatus;
    }
}
