package org.sejda.sambox.pdmodel;

import java.io.IOException;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.pdmodel.common.PDNameTreeNode;
import org.sejda.sambox.pdmodel.interactive.action.PDActionFactory;
import org.sejda.sambox.pdmodel.interactive.action.PDActionJavaScript;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/PDJavascriptNameTreeNode.class */
public class PDJavascriptNameTreeNode extends PDNameTreeNode<PDActionJavaScript> {
    public PDJavascriptNameTreeNode() {
    }

    public PDJavascriptNameTreeNode(COSDictionary dic) {
        super(dic);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.sejda.sambox.pdmodel.common.PDNameTreeNode
    public PDActionJavaScript convertCOSToPD(COSBase base) throws IOException {
        if (!(base instanceof COSDictionary)) {
            throw new IOException("Error creating Javascript object, expected a COSDictionary and not " + base);
        }
        return (PDActionJavaScript) PDActionFactory.createAction((COSDictionary) base);
    }

    @Override // org.sejda.sambox.pdmodel.common.PDNameTreeNode
    protected PDNameTreeNode<PDActionJavaScript> createChildNode(COSDictionary dic) {
        return new PDJavascriptNameTreeNode(dic);
    }
}
