package org.sejda.sambox.pdmodel.interactive.documentnavigation.outline;

import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/documentnavigation/outline/PDDocumentOutline.class */
public final class PDDocumentOutline extends PDOutlineNode {
    public PDDocumentOutline() {
        getCOSObject().setName(COSName.TYPE, COSName.OUTLINES.getName());
    }

    public PDDocumentOutline(COSDictionary dic) {
        super(dic);
        getCOSObject().setName(COSName.TYPE, COSName.OUTLINES.getName());
    }

    @Override // org.sejda.sambox.pdmodel.interactive.documentnavigation.outline.PDOutlineNode
    public boolean isNodeOpen() {
        return true;
    }

    @Override // org.sejda.sambox.pdmodel.interactive.documentnavigation.outline.PDOutlineNode
    public void openNode() {
    }

    @Override // org.sejda.sambox.pdmodel.interactive.documentnavigation.outline.PDOutlineNode
    public void closeNode() {
    }
}
