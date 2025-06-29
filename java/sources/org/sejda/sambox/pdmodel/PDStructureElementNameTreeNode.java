package org.sejda.sambox.pdmodel;

import java.io.IOException;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.pdmodel.common.PDNameTreeNode;
import org.sejda.sambox.pdmodel.documentinterchange.logicalstructure.PDStructureElement;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/PDStructureElementNameTreeNode.class */
public class PDStructureElementNameTreeNode extends PDNameTreeNode<PDStructureElement> {
    public PDStructureElementNameTreeNode() {
    }

    public PDStructureElementNameTreeNode(COSDictionary dic) {
        super(dic);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.sejda.sambox.pdmodel.common.PDNameTreeNode
    public PDStructureElement convertCOSToPD(COSBase base) throws IOException {
        return new PDStructureElement((COSDictionary) base);
    }

    @Override // org.sejda.sambox.pdmodel.common.PDNameTreeNode
    protected PDNameTreeNode<PDStructureElement> createChildNode(COSDictionary dic) {
        return new PDStructureElementNameTreeNode(dic);
    }
}
