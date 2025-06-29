package org.sejda.sambox.pdmodel.interactive.form;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.sejda.sambox.cos.COSDictionary;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/form/PDPushButton.class */
public class PDPushButton extends PDButton {
    public PDPushButton(PDAcroForm acroForm) {
        super(acroForm);
        setPushButton(true);
    }

    PDPushButton(PDAcroForm acroForm, COSDictionary field, PDNonTerminalField parent) {
        super(acroForm, field, parent);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.form.PDButton
    public List<String> getExportValues() {
        return Collections.emptyList();
    }

    @Override // org.sejda.sambox.pdmodel.interactive.form.PDButton
    public void setExportValues(List<String> values) {
        if (values != null && !values.isEmpty()) {
            throw new IllegalArgumentException("A PDPushButton shall not use the Opt entry in the field dictionary");
        }
    }

    @Override // org.sejda.sambox.pdmodel.interactive.form.PDButton
    public String getValue() {
        return "";
    }

    @Override // org.sejda.sambox.pdmodel.interactive.form.PDButton
    public String getDefaultValue() {
        return "";
    }

    @Override // org.sejda.sambox.pdmodel.interactive.form.PDButton, org.sejda.sambox.pdmodel.interactive.form.PDField
    public String getValueAsString() {
        return getValue();
    }

    @Override // org.sejda.sambox.pdmodel.interactive.form.PDButton
    public Set<String> getOnValues() {
        return Collections.emptySet();
    }

    @Override // org.sejda.sambox.pdmodel.interactive.form.PDButton, org.sejda.sambox.pdmodel.interactive.form.PDTerminalField
    void constructAppearances() throws IOException {
    }
}
