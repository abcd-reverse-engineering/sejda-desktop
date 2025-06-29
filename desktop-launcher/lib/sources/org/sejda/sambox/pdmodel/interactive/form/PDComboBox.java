package org.sejda.sambox.pdmodel.interactive.form;

import java.io.IOException;
import java.util.List;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/form/PDComboBox.class */
public final class PDComboBox extends PDChoice {
    private static final int FLAG_EDIT = 262144;

    public PDComboBox(PDAcroForm acroForm) {
        super(acroForm);
        setCombo(true);
    }

    PDComboBox(PDAcroForm acroForm, COSDictionary field, PDNonTerminalField parent) {
        super(acroForm, field, parent);
    }

    public boolean isEdit() {
        return getCOSObject().getFlag(COSName.FF, FLAG_EDIT);
    }

    public void setEdit(boolean edit) {
        getCOSObject().setFlag(COSName.FF, FLAG_EDIT, edit);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.form.PDChoice, org.sejda.sambox.pdmodel.interactive.form.PDTerminalField
    void constructAppearances() throws IOException {
        AppearanceGeneratorHelper apHelper = new AppearanceGeneratorHelper(this);
        List<String> values = getValue();
        if (!values.isEmpty()) {
            apHelper.setAppearanceValue(values.get(0));
        } else {
            apHelper.setAppearanceValue("");
        }
    }
}
