package org.sejda.sambox.pdmodel.graphics.form;

import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.pdmodel.ResourceCache;
import org.sejda.sambox.pdmodel.common.PDStream;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/form/PDTransparencyGroup.class */
public class PDTransparencyGroup extends PDFormXObject {
    public PDTransparencyGroup(PDStream stream) {
        super(stream);
    }

    public PDTransparencyGroup(COSStream stream, ResourceCache cache) {
        super(stream, cache);
    }
}
