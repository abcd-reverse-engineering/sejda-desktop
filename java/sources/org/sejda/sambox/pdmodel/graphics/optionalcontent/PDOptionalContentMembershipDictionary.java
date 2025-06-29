package org.sejda.sambox.pdmodel.graphics.optionalcontent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.pdmodel.documentinterchange.markedcontent.PDPropertyList;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/optionalcontent/PDOptionalContentMembershipDictionary.class */
public class PDOptionalContentMembershipDictionary extends PDPropertyList {
    public PDOptionalContentMembershipDictionary() {
        this.dict.setItem(COSName.TYPE, (COSBase) COSName.OCMD);
    }

    public PDOptionalContentMembershipDictionary(COSDictionary dict) {
        super(dict);
        if (!dict.getItem(COSName.TYPE).equals(COSName.OCMD)) {
            throw new IllegalArgumentException("Provided dictionary is not of type '" + COSName.OCMD + "'");
        }
    }

    public List<PDPropertyList> getOCGs() {
        COSBase base = this.dict.getDictionaryObject(COSName.OCGS);
        if (base instanceof COSDictionary) {
            return Collections.singletonList(PDPropertyList.create((COSDictionary) base));
        }
        if (base instanceof COSArray) {
            COSArray ar = (COSArray) base;
            List<PDPropertyList> list = new ArrayList<>();
            for (int i = 0; i < ar.size(); i++) {
                COSBase elem = ar.getObject(i);
                if (elem instanceof COSDictionary) {
                    list.add(PDPropertyList.create((COSDictionary) elem));
                }
            }
            return list;
        }
        return Collections.emptyList();
    }

    public void setOCGs(List<PDPropertyList> ocgs) {
        COSArray ar = new COSArray();
        for (PDPropertyList prop : ocgs) {
            ar.add((COSObjectable) prop);
        }
        this.dict.setItem(COSName.OCGS, (COSBase) ar);
    }

    public COSName getVisibilityPolicy() {
        return this.dict.getCOSName(COSName.P, COSName.ANY_ON);
    }

    public void setVisibilityPolicy(COSName visibilityPolicy) {
        this.dict.setItem(COSName.P, (COSBase) visibilityPolicy);
    }
}
