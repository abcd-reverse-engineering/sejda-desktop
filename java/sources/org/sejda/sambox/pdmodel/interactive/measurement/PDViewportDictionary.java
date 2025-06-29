package org.sejda.sambox.pdmodel.interactive.measurement;

import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.pdmodel.common.PDRectangle;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/measurement/PDViewportDictionary.class */
public class PDViewportDictionary implements COSObjectable {
    public static final String TYPE = "Viewport";
    private final COSDictionary viewportDictionary;

    public PDViewportDictionary() {
        this.viewportDictionary = new COSDictionary();
    }

    public PDViewportDictionary(COSDictionary dictionary) {
        this.viewportDictionary = dictionary;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSDictionary getCOSObject() {
        return this.viewportDictionary;
    }

    public String getType() {
        return TYPE;
    }

    public PDRectangle getBBox() {
        COSBase bbox = getCOSObject().getDictionaryObject(COSName.BBOX);
        if (bbox instanceof COSArray) {
            return new PDRectangle((COSArray) bbox);
        }
        return null;
    }

    public void setBBox(PDRectangle rectangle) {
        getCOSObject().setItem(COSName.BBOX, rectangle);
    }

    public String getName() {
        return getCOSObject().getNameAsString(COSName.NAME);
    }

    public void setName(String name) {
        getCOSObject().setName(COSName.NAME, name);
    }

    public PDMeasureDictionary getMeasure() {
        COSBase base = getCOSObject().getDictionaryObject(COSName.MEASURE);
        if (base instanceof COSDictionary) {
            return new PDMeasureDictionary((COSDictionary) base);
        }
        return null;
    }

    public void setMeasure(PDMeasureDictionary measure) {
        getCOSObject().setItem(COSName.MEASURE, measure);
    }
}
