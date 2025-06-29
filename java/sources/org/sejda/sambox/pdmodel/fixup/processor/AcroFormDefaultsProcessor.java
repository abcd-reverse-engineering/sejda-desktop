package org.sejda.sambox.pdmodel.fixup.processor;

import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDResources;
import org.sejda.sambox.pdmodel.font.PDType1Font;
import org.sejda.sambox.pdmodel.interactive.form.PDAcroForm;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/fixup/processor/AcroFormDefaultsProcessor.class */
public class AcroFormDefaultsProcessor extends AbstractProcessor {
    public AcroFormDefaultsProcessor(PDDocument document) {
        super(document);
    }

    @Override // org.sejda.sambox.pdmodel.fixup.processor.PDDocumentProcessor
    public void process() {
        PDAcroForm acroForm = this.document.getDocumentCatalog().getAcroForm(null);
        if (acroForm != null) {
            verifyOrCreateDefaults(acroForm);
        }
    }

    private void verifyOrCreateDefaults(PDAcroForm acroForm) {
        if (acroForm.getDefaultAppearance().length() == 0) {
            acroForm.setDefaultAppearance("/Helv 0 Tf 0 g ");
        }
        PDResources defaultResources = acroForm.getDefaultResources();
        if (defaultResources == null) {
            defaultResources = new PDResources();
            acroForm.setDefaultResources(defaultResources);
        }
        COSDictionary fontDict = (COSDictionary) defaultResources.getCOSObject().getDictionaryObject(COSName.FONT, COSDictionary.class);
        if (fontDict == null) {
            fontDict = new COSDictionary();
            defaultResources.getCOSObject().setItem(COSName.FONT, (COSBase) fontDict);
        }
        if (!fontDict.containsKey(COSName.HELV)) {
            defaultResources.put(COSName.HELV, PDType1Font.HELVETICA());
        }
        if (!fontDict.containsKey(COSName.ZA_DB)) {
            defaultResources.put(COSName.ZA_DB, PDType1Font.ZAPF_DINGBATS());
        }
    }
}
