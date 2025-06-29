package org.sejda.sambox.pdmodel.graphics.form;

import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;
import org.sejda.sambox.contentstream.PDContentStream;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSFloat;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.pdmodel.PDResources;
import org.sejda.sambox.pdmodel.ResourceCache;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.common.PDStream;
import org.sejda.sambox.pdmodel.documentinterchange.markedcontent.PDPropertyList;
import org.sejda.sambox.pdmodel.graphics.PDXObject;
import org.sejda.sambox.util.Matrix;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/form/PDFormXObject.class */
public class PDFormXObject extends PDXObject implements PDContentStream {
    private PDTransparencyGroupAttributes group;
    private final ResourceCache cache;
    private PDResources resources;

    public PDFormXObject(PDStream stream) {
        super(stream, COSName.FORM);
        this.cache = null;
    }

    public PDFormXObject(COSStream stream) {
        super(stream, COSName.FORM);
        this.cache = null;
    }

    public PDFormXObject(COSStream stream, ResourceCache cache) {
        super(stream, COSName.FORM);
        this.cache = cache;
    }

    public PDFormXObject() {
        super(COSName.FORM);
        this.cache = null;
    }

    public int getFormType() {
        return getCOSObject().getInt(COSName.FORMTYPE, 1);
    }

    public void setFormType(int formType) {
        getCOSObject().setInt(COSName.FORMTYPE, formType);
    }

    public PDTransparencyGroupAttributes getGroup() {
        if (Objects.isNull(this.group)) {
            this.group = (PDTransparencyGroupAttributes) Optional.ofNullable((COSDictionary) getCOSObject().getDictionaryObject(COSName.GROUP, COSDictionary.class)).map(PDTransparencyGroupAttributes::new).orElse(null);
        }
        return this.group;
    }

    public void setGroup(PDTransparencyGroupAttributes group) {
        if (Objects.isNull(group)) {
            getCOSObject().removeItem(COSName.GROUP);
            this.group = null;
        } else {
            getCOSObject().setItem(COSName.GROUP, (COSBase) group.getCOSObject());
            this.group = group;
        }
    }

    public PDStream getContentStream() {
        return new PDStream(getCOSObject());
    }

    @Override // org.sejda.sambox.contentstream.PDContentStream
    public InputStream getContents() throws IOException {
        return getCOSObject().getUnfilteredStream();
    }

    @Override // org.sejda.sambox.contentstream.PDContentStream
    public PDResources getResources() {
        if (this.resources == null) {
            COSDictionary resourcesDict = (COSDictionary) getCOSObject().getDictionaryObject(COSName.RESOURCES, COSDictionary.class);
            if (resourcesDict != null) {
                this.resources = new PDResources(resourcesDict, this.cache);
            } else if (getCOSObject().containsKey(COSName.RESOURCES)) {
                this.resources = new PDResources();
            }
        }
        return this.resources;
    }

    public void setResources(PDResources resources) {
        this.resources = resources;
        getCOSObject().setItem(COSName.RESOURCES, resources);
    }

    @Override // org.sejda.sambox.contentstream.PDContentStream
    public PDRectangle getBBox() {
        PDRectangle retval = null;
        COSArray array = (COSArray) getCOSObject().getDictionaryObject(COSName.BBOX);
        if (array != null && array.size() >= 4) {
            retval = new PDRectangle(array);
        }
        return retval;
    }

    public void setBBox(PDRectangle bbox) {
        if (bbox == null) {
            getCOSObject().removeItem(COSName.BBOX);
        } else {
            getCOSObject().setItem(COSName.BBOX, (COSBase) bbox.getCOSObject());
        }
    }

    @Override // org.sejda.sambox.contentstream.PDContentStream
    public Matrix getMatrix() {
        return Matrix.createMatrix(getCOSObject().getDictionaryObject(COSName.MATRIX));
    }

    public void setMatrix(AffineTransform transform) {
        COSArray matrix = new COSArray();
        double[] values = new double[6];
        transform.getMatrix(values);
        for (double v : values) {
            matrix.add((COSBase) new COSFloat((float) v));
        }
        getCOSObject().setItem(COSName.MATRIX, (COSBase) matrix);
    }

    public int getStructParents() {
        return getCOSObject().getInt(COSName.STRUCT_PARENTS);
    }

    public void setStructParents(int structParent) {
        getCOSObject().setInt(COSName.STRUCT_PARENTS, structParent);
    }

    public PDPropertyList getOptionalContent() {
        return (PDPropertyList) Optional.ofNullable((COSDictionary) getCOSObject().getDictionaryObject(COSName.OC, COSDictionary.class)).map(PDPropertyList::create).orElse(null);
    }

    public void setOptionalContent(PDPropertyList oc) {
        getCOSObject().setItem(COSName.OC, oc);
    }
}
