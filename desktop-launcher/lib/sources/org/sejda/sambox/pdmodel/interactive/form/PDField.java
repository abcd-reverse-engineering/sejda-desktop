package org.sejda.sambox.pdmodel.interactive.form;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.sejda.commons.util.RequireUtils;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.common.PDDictionaryWrapper;
import org.sejda.sambox.pdmodel.interactive.action.PDFormFieldAdditionalActions;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationWidget;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/form/PDField.class */
public abstract class PDField extends PDDictionaryWrapper {
    private static final int FLAG_READ_ONLY = 1;
    private static final int FLAG_REQUIRED = 2;
    private static final int FLAG_NO_EXPORT = 4;
    private final PDAcroForm acroForm;
    private final PDNonTerminalField parent;

    public abstract String getFieldType();

    public abstract String getValueAsString();

    public abstract void setValue(String str) throws IOException;

    public abstract List<PDAnnotationWidget> getWidgets();

    public abstract int getFieldFlags();

    public abstract boolean isTerminal();

    PDField(PDAcroForm acroForm) {
        this(acroForm, new COSDictionary(), null);
    }

    PDField(PDAcroForm acroForm, COSDictionary dictionary, PDNonTerminalField parent) {
        super(dictionary);
        this.acroForm = acroForm;
        this.parent = parent;
    }

    static PDField fromDictionary(PDAcroForm form, COSDictionary field, PDNonTerminalField parent) {
        return PDFieldFactory.createField(form, field, parent);
    }

    protected COSBase getInheritableAttribute(COSName key) {
        if (getCOSObject().containsKey(key)) {
            return getCOSObject().getDictionaryObject(key);
        }
        if (this.parent != null) {
            return this.parent.getInheritableAttribute(key);
        }
        return this.acroForm.getCOSObject().getDictionaryObject(key);
    }

    public void setReadonly(boolean readonly) {
        getCOSObject().setFlag(COSName.FF, 1, readonly);
    }

    public boolean isReadonly() {
        return getCOSObject().getFlag(COSName.FF, 1);
    }

    public void setRequired(boolean required) {
        getCOSObject().setFlag(COSName.FF, 2, required);
    }

    public boolean isRequired() {
        return getCOSObject().getFlag(COSName.FF, 2);
    }

    public void setNoExport(boolean noExport) {
        getCOSObject().setFlag(COSName.FF, 4, noExport);
    }

    public boolean isNoExport() {
        return getCOSObject().getFlag(COSName.FF, 4);
    }

    public void setFieldFlags(int flags) {
        getCOSObject().setInt(COSName.FF, flags);
    }

    public PDFormFieldAdditionalActions getActions() {
        return (PDFormFieldAdditionalActions) Optional.ofNullable((COSDictionary) getCOSObject().getDictionaryObject(COSName.AA, COSDictionary.class)).map(PDFormFieldAdditionalActions::new).orElse(null);
    }

    public PDNonTerminalField getParent() {
        return this.parent;
    }

    PDField findKid(String[] name, int nameIndex) {
        PDField retval = null;
        COSArray kids = (COSArray) getCOSObject().getDictionaryObject(COSName.KIDS);
        if (kids != null) {
            for (int i = 0; retval == null && i < kids.size(); i++) {
                COSDictionary kidDictionary = (COSDictionary) kids.getObject(i);
                if (name[nameIndex].equals(kidDictionary.getString(COSName.T))) {
                    retval = fromDictionary(this.acroForm, kidDictionary, (PDNonTerminalField) this);
                    if (name.length > nameIndex + 1) {
                        retval = retval.findKid(name, nameIndex + 1);
                    }
                }
            }
        }
        return retval;
    }

    public PDAcroForm getAcroForm() {
        return this.acroForm;
    }

    public String getPartialName() {
        return getCOSObject().getString(COSName.T);
    }

    public void setPartialName(String name) {
        RequireUtils.requireArg(!name.contains("."), "A field partial name shall not contain a period character: " + name);
        getCOSObject().setString(COSName.T, name);
    }

    public String getFullyQualifiedName() {
        String finalName = getPartialName();
        String parentName = this.parent != null ? this.parent.getFullyQualifiedName() : null;
        if (parentName != null) {
            if (finalName != null) {
                finalName = parentName + "." + finalName;
            } else {
                finalName = parentName;
            }
        }
        return finalName;
    }

    public String getAlternateFieldName() {
        return getCOSObject().getString(COSName.TU);
    }

    public void setAlternateFieldName(String alternateFieldName) {
        getCOSObject().setString(COSName.TU, alternateFieldName);
    }

    public String getMappingName() {
        return getCOSObject().getString(COSName.TM);
    }

    public void setMappingName(String mappingName) {
        getCOSObject().setString(COSName.TM, mappingName);
    }

    public String toString() {
        return getFullyQualifiedName() + "{type: " + getClass().getSimpleName() + " value: " + getInheritableAttribute(COSName.V) + "}";
    }
}
