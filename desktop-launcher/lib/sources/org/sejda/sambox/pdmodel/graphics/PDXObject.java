package org.sejda.sambox.pdmodel.graphics;

import java.io.IOException;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.pdmodel.PDResources;
import org.sejda.sambox.pdmodel.ResourceCache;
import org.sejda.sambox.pdmodel.common.PDStream;
import org.sejda.sambox.pdmodel.graphics.form.PDFormXObject;
import org.sejda.sambox.pdmodel.graphics.form.PDTransparencyGroup;
import org.sejda.sambox.pdmodel.graphics.image.PDImageXObject;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/PDXObject.class */
public class PDXObject implements COSObjectable {
    private PDStream stream;

    public static PDXObject createXObject(COSBase base, PDResources resources) throws IOException {
        if (base == null) {
            return null;
        }
        if (!(base instanceof COSStream)) {
            throw new IOException("Unexpected object type: " + base.getClass().getName());
        }
        COSStream stream = (COSStream) base;
        String subtype = stream.getNameAsString(COSName.SUBTYPE);
        if (COSName.IMAGE.getName().equals(subtype)) {
            return new PDImageXObject(new PDStream(stream), resources);
        }
        if (COSName.FORM.getName().equals(subtype)) {
            ResourceCache cache = resources != null ? resources.getResourceCache() : null;
            COSDictionary group = (COSDictionary) stream.getDictionaryObject(COSName.GROUP, COSDictionary.class);
            if (group != null && COSName.TRANSPARENCY.equals(group.getCOSName(COSName.S))) {
                return new PDTransparencyGroup(stream, cache);
            }
            return new PDFormXObject(stream, cache);
        }
        if (COSName.PS.getName().equals(subtype)) {
            return new PDPostScriptXObject(stream);
        }
        throw new IOException("Invalid XObject Subtype: " + subtype);
    }

    protected PDXObject(COSStream stream, COSName subtype) {
        this.stream = new PDStream(stream);
        stream.setName(COSName.TYPE, COSName.XOBJECT.getName());
        stream.setName(COSName.SUBTYPE, subtype.getName());
    }

    protected PDXObject(PDStream stream, COSName subtype) {
        this.stream = stream;
        stream.getCOSObject().setName(COSName.TYPE, COSName.XOBJECT.getName());
        stream.getCOSObject().setName(COSName.SUBTYPE, subtype.getName());
    }

    protected PDXObject(COSName subtype) {
        this.stream = new PDStream();
        this.stream.getCOSObject().setName(COSName.TYPE, COSName.XOBJECT.getName());
        this.stream.getCOSObject().setName(COSName.SUBTYPE, subtype.getName());
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public final COSStream getCOSObject() {
        return this.stream.getCOSObject();
    }

    public final PDStream getStream() {
        return this.stream;
    }

    public final void setStream(PDStream stream) {
        stream.getCOSObject().setName(COSName.TYPE, COSName.XOBJECT.getName());
        stream.getCOSObject().setName(COSName.SUBTYPE, this.stream.getCOSObject().getCOSName(COSName.SUBTYPE).getName());
        this.stream = stream;
    }
}
