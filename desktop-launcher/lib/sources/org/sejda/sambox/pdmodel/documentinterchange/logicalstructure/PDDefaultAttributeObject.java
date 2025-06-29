package org.sejda.sambox.pdmodel.documentinterchange.logicalstructure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/documentinterchange/logicalstructure/PDDefaultAttributeObject.class */
public class PDDefaultAttributeObject extends PDAttributeObject {
    public PDDefaultAttributeObject() {
    }

    public PDDefaultAttributeObject(COSDictionary dictionary) {
        super(dictionary);
    }

    public List<String> getAttributeNames() {
        List<String> attrNames = new ArrayList<>();
        for (Map.Entry<COSName, COSBase> entry : getCOSObject().entrySet()) {
            COSName key = entry.getKey();
            if (!COSName.O.equals(key)) {
                attrNames.add(key.getName());
            }
        }
        return attrNames;
    }

    public COSBase getAttributeValue(String attrName) {
        return getCOSObject().getDictionaryObject(attrName);
    }

    protected COSBase getAttributeValue(String attrName, COSBase defaultValue) {
        COSBase value = getCOSObject().getDictionaryObject(attrName);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public void setAttribute(String attrName, COSBase attrValue) {
        COSBase old = getAttributeValue(attrName);
        getCOSObject().setItem(COSName.getPDFName(attrName), attrValue);
        potentiallyNotifyChanged(old, attrValue);
    }

    @Override // org.sejda.sambox.pdmodel.documentinterchange.logicalstructure.PDAttributeObject
    public String toString() {
        StringBuilder sb = new StringBuilder().append(super.toString()).append(", attributes={");
        Iterator<String> it = getAttributeNames().iterator();
        while (it.hasNext()) {
            String name = it.next();
            sb.append(name).append('=').append(getAttributeValue(name));
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        return sb.append('}').toString();
    }
}
