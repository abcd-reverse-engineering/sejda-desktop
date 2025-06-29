package org.sejda.sambox.pdmodel;

import java.io.IOException;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.common.PDNameTreeNode;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.PDDestination;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/PDDestinationNameTreeNode.class */
public class PDDestinationNameTreeNode extends PDNameTreeNode<PDPageDestination> {
    public PDDestinationNameTreeNode() {
    }

    public PDDestinationNameTreeNode(COSDictionary dic) {
        super(dic);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.sejda.sambox.pdmodel.common.PDNameTreeNode
    public PDPageDestination convertCOSToPD(COSBase base) throws IOException {
        if (base instanceof COSDictionary) {
            return (PDPageDestination) PDDestination.create(((COSDictionary) base).getDictionaryObject(COSName.D));
        }
        return (PDPageDestination) PDDestination.create(base);
    }

    @Override // org.sejda.sambox.pdmodel.common.PDNameTreeNode
    protected PDNameTreeNode<PDPageDestination> createChildNode(COSDictionary dic) {
        return new PDDestinationNameTreeNode(dic);
    }
}
