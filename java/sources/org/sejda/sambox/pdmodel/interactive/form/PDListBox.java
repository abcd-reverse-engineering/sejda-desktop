package org.sejda.sambox.pdmodel.interactive.form;

import java.io.IOException;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/form/PDListBox.class */
public final class PDListBox extends PDChoice {
    public PDListBox(PDAcroForm acroForm) {
        super(acroForm);
    }

    PDListBox(PDAcroForm acroForm, COSDictionary field, PDNonTerminalField parent) {
        super(acroForm, field, parent);
    }

    public int getTopIndex() {
        return getCOSObject().getInt(COSName.TI, 0);
    }

    public void setTopIndex(Integer topIndex) {
        if (topIndex != null) {
            getCOSObject().setInt(COSName.TI, topIndex.intValue());
        } else {
            getCOSObject().removeItem(COSName.TI);
        }
    }

    @Override // org.sejda.sambox.pdmodel.interactive.form.PDChoice, org.sejda.sambox.pdmodel.interactive.form.PDTerminalField
    void constructAppearances() throws IOException {
        AppearanceGeneratorHelper apHelper = new AppearanceGeneratorHelper(this);
        apHelper.setAppearanceValue("");
    }
}
