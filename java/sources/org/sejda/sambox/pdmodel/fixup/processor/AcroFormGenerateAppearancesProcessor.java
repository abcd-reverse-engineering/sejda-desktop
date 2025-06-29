package org.sejda.sambox.pdmodel.fixup.processor;

import java.io.IOException;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.interactive.form.PDAcroForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/fixup/processor/AcroFormGenerateAppearancesProcessor.class */
public class AcroFormGenerateAppearancesProcessor extends AbstractProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(AcroFormGenerateAppearancesProcessor.class);

    public AcroFormGenerateAppearancesProcessor(PDDocument document) {
        super(document);
    }

    @Override // org.sejda.sambox.pdmodel.fixup.processor.PDDocumentProcessor
    public void process() {
        PDAcroForm acroForm = this.document.getDocumentCatalog().getAcroForm(null);
        if (acroForm != null && acroForm.isNeedAppearances()) {
            try {
                LOG.debug("trying to generate appearance streams for fields as NeedAppearances is true()");
                acroForm.refreshAppearances();
                acroForm.setNeedAppearances(false);
            } catch (IOException | IllegalArgumentException ioe) {
                LOG.debug("couldn't generate appearance stream for some fields - check output");
                LOG.debug(ioe.getMessage());
            }
        }
    }
}
