package org.sejda.sambox.pdmodel.fixup;

import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.fixup.processor.AcroFormDefaultsProcessor;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/fixup/AcroFormDefaultSamboxFixup.class */
public class AcroFormDefaultSamboxFixup extends AbstractFixup {
    public AcroFormDefaultSamboxFixup(PDDocument document) {
        super(document);
    }

    @Override // org.sejda.sambox.pdmodel.fixup.PDDocumentFixup
    public void apply() {
        new AcroFormDefaultsProcessor(this.document).process();
    }
}
