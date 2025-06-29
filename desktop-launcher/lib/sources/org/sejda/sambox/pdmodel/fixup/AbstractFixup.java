package org.sejda.sambox.pdmodel.fixup;

import org.sejda.sambox.pdmodel.PDDocument;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/fixup/AbstractFixup.class */
public abstract class AbstractFixup implements PDDocumentFixup {
    protected PDDocument document;

    protected AbstractFixup(PDDocument document) {
        this.document = document;
    }
}
