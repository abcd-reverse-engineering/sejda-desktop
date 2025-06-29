package org.sejda.sambox.filter;

import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.pdmodel.graphics.color.PDJPXColorSpace;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/filter/DecodeResult.class */
public final class DecodeResult {
    public static final DecodeResult DEFAULT = new DecodeResult(new COSDictionary());
    private final COSDictionary parameters;
    private PDJPXColorSpace colorSpace;

    DecodeResult(COSDictionary parameters) {
        this.parameters = parameters;
    }

    DecodeResult(COSDictionary parameters, PDJPXColorSpace colorSpace) {
        this.parameters = parameters;
        this.colorSpace = colorSpace;
    }

    public COSDictionary getParameters() {
        return this.parameters;
    }

    public PDJPXColorSpace getJPXColorSpace() {
        return this.colorSpace;
    }

    void setColorSpace(PDJPXColorSpace colorSpace) {
        this.colorSpace = colorSpace;
    }
}
