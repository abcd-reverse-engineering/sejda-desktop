package org.sejda.sambox.pdmodel.documentinterchange.logicalstructure;

import java.util.ArrayList;
import java.util.List;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/documentinterchange/logicalstructure/PDUserAttributeObject.class */
public class PDUserAttributeObject extends PDAttributeObject {
    public static final String OWNER_USER_PROPERTIES = "UserProperties";

    public PDUserAttributeObject() {
        setOwner(OWNER_USER_PROPERTIES);
    }

    public PDUserAttributeObject(COSDictionary dictionary) {
        super(dictionary);
    }

    public List<PDUserProperty> getOwnerUserProperties() {
        COSArray p = (COSArray) getCOSObject().getDictionaryObject(COSName.P);
        List<PDUserProperty> properties = new ArrayList<>(p.size());
        for (int i = 0; i < p.size(); i++) {
            properties.add(new PDUserProperty((COSDictionary) p.getObject(i), this));
        }
        return properties;
    }

    public void setUserProperties(List<PDUserProperty> userProperties) {
        COSArray p = new COSArray();
        for (PDUserProperty userProperty : userProperties) {
            p.add((COSObjectable) userProperty);
        }
        getCOSObject().setItem(COSName.P, (COSBase) p);
    }

    public void addUserProperty(PDUserProperty userProperty) {
        COSArray p = (COSArray) getCOSObject().getDictionaryObject(COSName.P);
        p.add((COSObjectable) userProperty);
        notifyChanged();
    }

    public void removeUserProperty(PDUserProperty userProperty) {
        if (userProperty == null) {
            return;
        }
        COSArray p = (COSArray) getCOSObject().getDictionaryObject(COSName.P);
        p.remove(userProperty.getCOSObject());
        notifyChanged();
    }

    public void userPropertyChanged(PDUserProperty userProperty) {
    }

    @Override // org.sejda.sambox.pdmodel.documentinterchange.logicalstructure.PDAttributeObject
    public String toString() {
        return super.toString() + ", userProperties=" + getOwnerUserProperties();
    }
}
