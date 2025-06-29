package org.sejda.sambox.pdmodel.fixup.processor;

import org.sejda.sambox.pdmodel.PDDocument;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/fixup/processor/AbstractProcessor.class */
public abstract class AbstractProcessor implements PDDocumentProcessor {
    protected PDDocument document;

    protected AbstractProcessor(PDDocument document) {
        this.document = document;
    }
}
