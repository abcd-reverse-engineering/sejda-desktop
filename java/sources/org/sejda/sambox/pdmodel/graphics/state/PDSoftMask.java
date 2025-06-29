package org.sejda.sambox.pdmodel.graphics.state;

import java.io.IOException;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.pdmodel.common.function.PDFunction;
import org.sejda.sambox.pdmodel.graphics.PDXObject;
import org.sejda.sambox.pdmodel.graphics.form.PDTransparencyGroup;
import org.sejda.sambox.util.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/state/PDSoftMask.class */
public final class PDSoftMask implements COSObjectable {
    private static final Logger LOG = LoggerFactory.getLogger(PDSoftMask.class);
    private final COSDictionary dictionary;
    private COSName subType = null;
    private PDTransparencyGroup group = null;
    private COSArray backdropColor = null;
    private PDFunction transferFunction = null;
    private Matrix ctm;

    public static PDSoftMask create(COSBase dictionary) {
        if (dictionary instanceof COSName) {
            if (COSName.NONE.equals(dictionary)) {
                return null;
            }
            LOG.warn("Invalid SMask " + dictionary);
            return null;
        }
        if (dictionary instanceof COSDictionary) {
            return new PDSoftMask((COSDictionary) dictionary);
        }
        LOG.warn("Invalid SMask " + dictionary);
        return null;
    }

    public PDSoftMask(COSDictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSDictionary getCOSObject() {
        return this.dictionary;
    }

    public COSName getSubType() {
        if (this.subType == null) {
            this.subType = (COSName) getCOSObject().getDictionaryObject(COSName.S);
        }
        return this.subType;
    }

    public PDTransparencyGroup getGroup() throws IOException {
        COSBase cosGroup;
        if (this.group == null && (cosGroup = getCOSObject().getDictionaryObject(COSName.G)) != null) {
            PDXObject xObject = PDXObject.createXObject(cosGroup, null);
            if (xObject instanceof PDTransparencyGroup) {
                this.group = (PDTransparencyGroup) xObject;
            } else {
                LOG.warn("Expected PDTransparencyGroup but got: " + xObject);
            }
        }
        return this.group;
    }

    public COSArray getBackdropColor() {
        if (this.backdropColor == null) {
            this.backdropColor = (COSArray) getCOSObject().getDictionaryObject(COSName.BC, COSArray.class);
        }
        return this.backdropColor;
    }

    public PDFunction getTransferFunction() throws IOException {
        COSBase cosTF;
        if (this.transferFunction == null && (cosTF = getCOSObject().getDictionaryObject(COSName.TR)) != null) {
            this.transferFunction = PDFunction.create(cosTF);
        }
        return this.transferFunction;
    }

    void setInitialTransformationMatrix(Matrix ctm) {
        this.ctm = ctm;
    }

    public Matrix getInitialTransformationMatrix() {
        return this.ctm;
    }
}
