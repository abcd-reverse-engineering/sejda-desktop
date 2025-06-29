package org.sejda.sambox.pdmodel.documentinterchange.logicalstructure;

import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.common.PDDictionaryWrapper;
import org.sejda.sambox.pdmodel.documentinterchange.taggedpdf.PDExportFormatAttributeObject;
import org.sejda.sambox.pdmodel.documentinterchange.taggedpdf.PDLayoutAttributeObject;
import org.sejda.sambox.pdmodel.documentinterchange.taggedpdf.PDListAttributeObject;
import org.sejda.sambox.pdmodel.documentinterchange.taggedpdf.PDPrintFieldAttributeObject;
import org.sejda.sambox.pdmodel.documentinterchange.taggedpdf.PDTableAttributeObject;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/documentinterchange/logicalstructure/PDAttributeObject.class */
public abstract class PDAttributeObject extends PDDictionaryWrapper {
    private PDStructureElement structureElement;

    public PDAttributeObject() {
    }

    public PDAttributeObject(COSDictionary dictionary) {
        super(dictionary);
    }

    public static PDAttributeObject create(COSDictionary dictionary) {
        String owner = dictionary.getNameAsString(COSName.O);
        if (PDUserAttributeObject.OWNER_USER_PROPERTIES.equals(owner)) {
            return new PDUserAttributeObject(dictionary);
        }
        if (PDListAttributeObject.OWNER_LIST.equals(owner)) {
            return new PDListAttributeObject(dictionary);
        }
        if (PDPrintFieldAttributeObject.OWNER_PRINT_FIELD.equals(owner)) {
            return new PDPrintFieldAttributeObject(dictionary);
        }
        if ("Table".equals(owner)) {
            return new PDTableAttributeObject(dictionary);
        }
        if (PDLayoutAttributeObject.OWNER_LAYOUT.equals(owner)) {
            return new PDLayoutAttributeObject(dictionary);
        }
        if (PDExportFormatAttributeObject.OWNER_XML_1_00.equals(owner) || PDExportFormatAttributeObject.OWNER_HTML_3_20.equals(owner) || PDExportFormatAttributeObject.OWNER_HTML_4_01.equals(owner) || PDExportFormatAttributeObject.OWNER_OEB_1_00.equals(owner) || PDExportFormatAttributeObject.OWNER_RTF_1_05.equals(owner) || PDExportFormatAttributeObject.OWNER_CSS_1_00.equals(owner) || PDExportFormatAttributeObject.OWNER_CSS_2_00.equals(owner)) {
            return new PDExportFormatAttributeObject(dictionary);
        }
        return new PDDefaultAttributeObject(dictionary);
    }

    private PDStructureElement getStructureElement() {
        return this.structureElement;
    }

    protected void setStructureElement(PDStructureElement structureElement) {
        this.structureElement = structureElement;
    }

    public String getOwner() {
        return getCOSObject().getNameAsString(COSName.O);
    }

    protected void setOwner(String owner) {
        getCOSObject().setName(COSName.O, owner);
    }

    public boolean isEmpty() {
        return getCOSObject().size() == 1 && getOwner() != null;
    }

    protected void potentiallyNotifyChanged(COSBase oldBase, COSBase newBase) {
        if (isValueChanged(oldBase, newBase)) {
            notifyChanged();
        }
    }

    private boolean isValueChanged(COSBase oldValue, COSBase newValue) {
        return oldValue == null ? newValue != null : !oldValue.equals(newValue);
    }

    protected void notifyChanged() {
        if (getStructureElement() != null) {
            getStructureElement().attributeChanged(this);
        }
    }

    public String toString() {
        return "O=" + getOwner();
    }

    protected static String arrayToString(Object[] array) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(array[i]);
        }
        return sb.append(']').toString();
    }

    protected static String arrayToString(float[] array) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(array[i]);
        }
        return sb.append(']').toString();
    }
}
