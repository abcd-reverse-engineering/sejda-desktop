package org.sejda.sambox.pdmodel.documentinterchange.logicalstructure;

import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.common.PDDictionaryWrapper;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/documentinterchange/logicalstructure/PDUserProperty.class */
public class PDUserProperty extends PDDictionaryWrapper {
    private final PDUserAttributeObject userAttributeObject;

    public PDUserProperty(PDUserAttributeObject userAttributeObject) {
        this.userAttributeObject = userAttributeObject;
    }

    public PDUserProperty(COSDictionary dictionary, PDUserAttributeObject userAttributeObject) {
        super(dictionary);
        this.userAttributeObject = userAttributeObject;
    }

    public String getName() {
        return getCOSObject().getNameAsString(COSName.N);
    }

    public void setName(String name) {
        potentiallyNotifyChanged(getName(), name);
        getCOSObject().setName(COSName.N, name);
    }

    public COSBase getValue() {
        return getCOSObject().getDictionaryObject(COSName.V);
    }

    public void setValue(COSBase value) {
        potentiallyNotifyChanged(getValue(), value);
        getCOSObject().setItem(COSName.V, value);
    }

    public String getFormattedValue() {
        return getCOSObject().getString(COSName.F);
    }

    public void setFormattedValue(String formattedValue) {
        potentiallyNotifyChanged(getFormattedValue(), formattedValue);
        getCOSObject().setString(COSName.F, formattedValue);
    }

    public boolean isHidden() {
        return getCOSObject().getBoolean(COSName.H, false);
    }

    public void setHidden(boolean hidden) {
        potentiallyNotifyChanged(Boolean.valueOf(isHidden()), Boolean.valueOf(hidden));
        getCOSObject().setBoolean(COSName.H, hidden);
    }

    public String toString() {
        return "Name=" + getName() + ", Value=" + getValue() + ", FormattedValue=" + getFormattedValue() + ", Hidden=" + isHidden();
    }

    private void potentiallyNotifyChanged(Object oldEntry, Object newEntry) {
        if (isEntryChanged(oldEntry, newEntry)) {
            this.userAttributeObject.userPropertyChanged(this);
        }
    }

    private boolean isEntryChanged(Object oldEntry, Object newEntry) {
        return oldEntry == null ? newEntry != null : !oldEntry.equals(newEntry);
    }

    @Override // org.sejda.sambox.pdmodel.common.PDDictionaryWrapper
    public int hashCode() {
        int result = super.hashCode();
        return (31 * result) + (this.userAttributeObject == null ? 0 : this.userAttributeObject.hashCode());
    }

    @Override // org.sejda.sambox.pdmodel.common.PDDictionaryWrapper
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj) || getClass() != obj.getClass()) {
            return false;
        }
        PDUserProperty other = (PDUserProperty) obj;
        if (this.userAttributeObject == null) {
            return other.userAttributeObject == null;
        }
        return this.userAttributeObject.equals(other.userAttributeObject);
    }
}
