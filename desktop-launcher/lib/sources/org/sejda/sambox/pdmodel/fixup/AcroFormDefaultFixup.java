package org.sejda.sambox.pdmodel.fixup;

import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.fixup.processor.AcroFormDefaultsProcessor;
import org.sejda.sambox.pdmodel.fixup.processor.AcroFormGenerateAppearancesProcessor;
import org.sejda.sambox.pdmodel.fixup.processor.AcroFormOrphanWidgetsProcessor;
import org.sejda.sambox.pdmodel.interactive.form.PDAcroForm;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/fixup/AcroFormDefaultFixup.class */
public class AcroFormDefaultFixup extends AbstractFixup {
    public AcroFormDefaultFixup(PDDocument document) {
        super(document);
    }

    @Override // org.sejda.sambox.pdmodel.fixup.PDDocumentFixup
    public void apply() {
        new AcroFormDefaultsProcessor(this.document).process();
        PDAcroForm acroForm = this.document.getDocumentCatalog().getAcroForm(null);
        if (acroForm != null && acroForm.isNeedAppearances()) {
            if (acroForm.getFields().isEmpty()) {
                new AcroFormOrphanWidgetsProcessor(this.document).process();
            }
            new AcroFormGenerateAppearancesProcessor(this.document).process();
        }
    }
}
