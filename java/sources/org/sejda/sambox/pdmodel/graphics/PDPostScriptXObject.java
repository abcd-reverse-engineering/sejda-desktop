package org.sejda.sambox.pdmodel.graphics;

import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSStream;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/PDPostScriptXObject.class */
public class PDPostScriptXObject extends PDXObject {
    public PDPostScriptXObject(COSStream stream) {
        super(stream, COSName.PS);
    }
}
