package org.sejda.sambox.pdmodel.interactive.form;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSArrayList;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSString;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAppearanceDictionary;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAppearanceEntry;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAppearanceStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/form/PDButton.class */
public abstract class PDButton extends PDTerminalField {
    private static final Logger LOG = LoggerFactory.getLogger(PDButton.class);
    static final int FLAG_RADIO = 32768;
    static final int FLAG_PUSHBUTTON = 65536;
    static final int FLAG_RADIOS_IN_UNISON = 33554432;
    private boolean ignoreExportOptions;

    public PDButton(PDAcroForm acroForm) {
        super(acroForm);
        this.ignoreExportOptions = false;
        getCOSObject().setItem(COSName.FT, (COSBase) COSName.BTN);
    }

    PDButton(PDAcroForm acroForm, COSDictionary field, PDNonTerminalField parent) {
        super(acroForm, field, parent);
        this.ignoreExportOptions = false;
    }

    public boolean isPushButton() {
        return getCOSObject().getFlag(COSName.FF, FLAG_PUSHBUTTON);
    }

    @Deprecated
    public void setPushButton(boolean pushbutton) {
        getCOSObject().setFlag(COSName.FF, FLAG_PUSHBUTTON, pushbutton);
        if (pushbutton) {
            setRadioButton(false);
        }
    }

    public boolean isRadioButton() {
        return getCOSObject().getFlag(COSName.FF, FLAG_RADIO);
    }

    @Deprecated
    public void setRadioButton(boolean radiobutton) {
        getCOSObject().setFlag(COSName.FF, FLAG_RADIO, radiobutton);
        if (radiobutton) {
            setPushButton(false);
        }
    }

    public String getValue() throws NumberFormatException {
        COSBase value = getInheritableAttribute(COSName.V);
        if (value instanceof COSName) {
            String stringValue = ((COSName) value).getName();
            if (this.ignoreExportOptions) {
                return stringValue;
            }
            List<String> exportValues = getExportValues();
            if (!exportValues.isEmpty()) {
                try {
                    int idx = Integer.parseInt(stringValue, 10);
                    if (idx >= 0 && idx < exportValues.size()) {
                        return exportValues.get(idx);
                    }
                } catch (NumberFormatException e) {
                    return stringValue;
                }
            }
            return stringValue;
        }
        return "Off";
    }

    @Override // org.sejda.sambox.pdmodel.interactive.form.PDField
    public void setValue(String value) throws IOException {
        checkValue(value);
        boolean hasExportValues = getExportValues().size() > 0;
        if (hasExportValues && !this.ignoreExportOptions) {
            updateByOption(value);
        } else {
            updateByValue(value);
        }
        applyChange();
    }

    public void setValue(int index) throws IOException {
        if (getExportValues().isEmpty() || index < 0 || index >= getExportValues().size()) {
            throw new IllegalArgumentException("index '" + index + "' is not a valid index for the field " + getFullyQualifiedName() + ", valid indices are from 0 to " + (getExportValues().size() - 1));
        }
        updateByValue(String.valueOf(index));
        applyChange();
    }

    public String getDefaultValue() {
        COSBase value = getInheritableAttribute(COSName.DV);
        if (value instanceof COSName) {
            return ((COSName) value).getName();
        }
        return "";
    }

    public void setDefaultValue(String value) {
        checkValue(value);
        getCOSObject().setName(COSName.DV, value);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.form.PDField
    public String getValueAsString() {
        return getValue();
    }

    public List<String> getExportValues() {
        COSBase value = getInheritableAttribute(COSName.OPT);
        if (value instanceof COSString) {
            List<String> array = new ArrayList<>();
            array.add(((COSString) value).getString());
            return array;
        }
        if (value instanceof COSArray) {
            return COSArrayList.convertCOSStringCOSArrayToList((COSArray) value);
        }
        return Collections.emptyList();
    }

    public void setExportValues(List<String> values) {
        if (values != null && !values.isEmpty()) {
            COSArray cosValues = COSArrayList.convertStringListToCOSStringCOSArray(values);
            getCOSObject().setItem(COSName.OPT, (COSBase) cosValues);
        } else {
            getCOSObject().removeItem(COSName.OPT);
        }
    }

    @Override // org.sejda.sambox.pdmodel.interactive.form.PDTerminalField
    void constructAppearances() throws NumberFormatException, IOException {
        List<String> exportValues = getExportValues();
        if (exportValues.size() > 0 && !this.ignoreExportOptions) {
            try {
                int optionsIndex = Integer.parseInt(getValue());
                if (optionsIndex < exportValues.size()) {
                    updateByOption(exportValues.get(optionsIndex));
                }
                return;
            } catch (NumberFormatException e) {
                return;
            }
        }
        updateByValue(getValue());
    }

    public Set<String> getOnValues() {
        Set<String> onValues = new HashSet<>();
        if (!this.ignoreExportOptions) {
            onValues.addAll(getExportValues());
        }
        onValues.addAll(getNormalAppearanceValues());
        return onValues;
    }

    public List<String> getNormalAppearanceValues() {
        List<String> values = new ArrayList<>();
        List<PDAnnotationWidget> widgets = getWidgets();
        for (PDAnnotationWidget widget : widgets) {
            String value = getOnValueForWidget(widget);
            if (value != null) {
                values.add(value);
            }
        }
        return values;
    }

    private String getOnValue(int index) {
        List<PDAnnotationWidget> widgets = getWidgets();
        if (index < widgets.size()) {
            return getOnValueForWidget(widgets.get(index));
        }
        return null;
    }

    public String getOnValueForWidget(PDAnnotationWidget widget) {
        PDAppearanceEntry normalAppearance;
        PDAppearanceDictionary apDictionary = widget.getAppearance();
        if (apDictionary != null && (normalAppearance = apDictionary.getNormalAppearance()) != null && normalAppearance.isSubDictionary()) {
            Map<COSName, PDAppearanceStream> subDictionary = normalAppearance.getSubDictionary();
            SortedSet<COSName> entries = new TreeSet<>(subDictionary.keySet());
            for (COSName entry : entries) {
                if (COSName.Off.compareTo(entry) != 0) {
                    return entry.getName();
                }
            }
            return null;
        }
        return null;
    }

    void checkValue(String value) {
        Set<String> onValues = getOnValues();
        if (!onValues.isEmpty() && COSName.Off.getName().compareTo(value) != 0 && !onValues.contains(value)) {
            throw new IllegalArgumentException("value '" + value + "' is not a valid option for the field " + getFullyQualifiedName() + ", valid values are: " + onValues + " and " + COSName.Off.getName());
        }
    }

    private void updateByValue(String value) {
        getCOSObject().setName(COSName.V, value);
        for (PDAnnotationWidget widget : getWidgets()) {
            PDAppearanceDictionary appearance = widget.getAppearance();
            PDAppearanceEntry normalAppearance = null;
            Set<String> entries = new HashSet<>();
            if (appearance != null) {
                normalAppearance = widget.getAppearance().getNormalAppearance();
                if (normalAppearance != null && normalAppearance.isSubDictionary()) {
                    entries = (Set) normalAppearance.getSubDictionary().keySet().stream().map((v0) -> {
                        return v0.getName();
                    }).collect(Collectors.toSet());
                    if (entries.contains(value)) {
                        widget.setAppearanceState(value);
                    } else {
                        widget.setAppearanceState(COSName.Off.getName());
                    }
                }
            }
            if (entries.isEmpty() || (entries.size() == 1 && entries.contains("Off"))) {
                if (this instanceof PDCheckBox) {
                    PDCheckBox checkBox = (PDCheckBox) this;
                    if (checkBox.getOnValue().equals(value)) {
                        widget.setAppearanceState(value);
                    } else {
                        widget.setAppearanceState(COSName.Off.getName());
                    }
                }
                if (normalAppearance != null && !normalAppearance.isSubDictionary() && ((this instanceof PDRadioButton) || (this instanceof PDCheckBox))) {
                    throw new RuntimeException("Check/radio has a single normal appearance, might look the same when checked or not");
                }
            }
        }
    }

    private void updateByOption(String value) throws IOException {
        List<PDAnnotationWidget> widgets = getWidgets();
        List<String> options = getExportValues();
        if (widgets.size() != options.size()) {
            throw new IllegalArgumentException("The number of options doesn't match the number of widgets");
        }
        if (value.equals(COSName.Off.getName())) {
            updateByValue(value);
            return;
        }
        int optionsIndex = options.indexOf(value);
        if (optionsIndex != -1) {
            updateByValue(getOnValue(optionsIndex));
        }
    }

    public boolean isIgnoreExportOptions() {
        return this.ignoreExportOptions;
    }

    public void setIgnoreExportOptions(boolean ignoreExportOptions) {
        this.ignoreExportOptions = ignoreExportOptions;
    }
}
