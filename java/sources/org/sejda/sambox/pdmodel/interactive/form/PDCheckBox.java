package org.sejda.sambox.pdmodel.interactive.form;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAppearanceDictionary;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAppearanceEntry;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAppearanceStream;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/form/PDCheckBox.class */
public final class PDCheckBox extends PDButton {
    public PDCheckBox(PDAcroForm acroForm) {
        super(acroForm);
    }

    PDCheckBox(PDAcroForm acroForm, COSDictionary field, PDNonTerminalField parent) {
        super(acroForm, field, parent);
    }

    public boolean isChecked() {
        if (COSName.Off.getName().equals(getValue())) {
            return false;
        }
        return getOnValues().contains(getValue()) || getOnValue().equals(getValue());
    }

    public void check() throws IOException {
        setValue(getOnValue());
    }

    public void unCheck() throws IOException {
        setValue(COSName.Off.getName());
    }

    public String getOnValue() {
        PDAppearanceEntry normalAppearance;
        PDAnnotationWidget widget = getWidgets().get(0);
        PDAppearanceDictionary apDictionary = widget.getAppearance();
        if (apDictionary != null && (normalAppearance = apDictionary.getNormalAppearance()) != null && normalAppearance.isSubDictionary()) {
            Map<COSName, PDAppearanceStream> subDict = normalAppearance.getSubDictionary();
            Set<COSName> entries = subDict.keySet();
            for (COSName entry : entries) {
                if (COSName.Off.compareTo(entry) != 0) {
                    return entry.getName();
                }
            }
            return "";
        }
        return "";
    }
}
