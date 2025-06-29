package org.sejda.sambox.pdmodel;

import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.pdmodel.common.PDNameTreeNode;
import org.sejda.sambox.pdmodel.common.filespecification.PDComplexFileSpecification;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/PDEmbeddedFilesNameTreeNode.class */
public class PDEmbeddedFilesNameTreeNode extends PDNameTreeNode<PDComplexFileSpecification> {
    public PDEmbeddedFilesNameTreeNode() {
    }

    public PDEmbeddedFilesNameTreeNode(COSDictionary dic) {
        super(dic);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.sejda.sambox.pdmodel.common.PDNameTreeNode
    public PDComplexFileSpecification convertCOSToPD(COSBase base) {
        return new PDComplexFileSpecification((COSDictionary) base);
    }

    @Override // org.sejda.sambox.pdmodel.common.PDNameTreeNode
    protected PDNameTreeNode<PDComplexFileSpecification> createChildNode(COSDictionary dic) {
        return new PDEmbeddedFilesNameTreeNode(dic);
    }
}
