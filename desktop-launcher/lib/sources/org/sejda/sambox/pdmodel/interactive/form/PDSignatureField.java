package org.sejda.sambox.pdmodel.interactive.form;

import java.util.Optional;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/form/PDSignatureField.class */
public class PDSignatureField extends PDTerminalField {
    private static final Logger LOG = LoggerFactory.getLogger(PDSignatureField.class);

    public PDSignatureField(PDAcroForm acroForm) {
        super(acroForm);
        getCOSObject().setItem(COSName.FT, (COSBase) COSName.SIG);
        PDAnnotationWidget firstWidget = getWidgets().get(0);
        firstWidget.setLocked(true);
        firstWidget.setPrinted(true);
    }

    PDSignatureField(PDAcroForm acroForm, COSDictionary field, PDNonTerminalField parent) {
        super(acroForm, field, parent);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.form.PDField
    public String getValueAsString() {
        return (String) Optional.ofNullable(getCOSObject().getDictionaryObject(COSName.V)).map((v0) -> {
            return v0.toString();
        }).orElse("");
    }

    @Override // org.sejda.sambox.pdmodel.interactive.form.PDTerminalField
    void constructAppearances() {
        PDAnnotationWidget widget = getWidgets().get(0);
        if (widget != null && widget.getRectangle() != null) {
            if ((widget.getRectangle().getHeight() == 0.0f && widget.getRectangle().getWidth() == 0.0f) || widget.isNoView() || widget.isHidden()) {
                return;
            }
            LOG.warn("Signature field appearance not implemented");
        }
    }

    @Override // org.sejda.sambox.pdmodel.interactive.form.PDField
    public void setValue(String value) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Signature fields don't support setting the value as String - use setValue(PDSignature value) instead");
    }
}
