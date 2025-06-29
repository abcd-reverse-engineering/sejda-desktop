package org.sejda.sambox.pdmodel.interactive.measurement;

import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationLink;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/measurement/PDRectlinearMeasureDictionary.class */
public class PDRectlinearMeasureDictionary extends PDMeasureDictionary {
    public static final String SUBTYPE = "RL";

    public PDRectlinearMeasureDictionary() {
        setSubtype(SUBTYPE);
    }

    public PDRectlinearMeasureDictionary(COSDictionary dictionary) {
        super(dictionary);
    }

    public String getScaleRatio() {
        return getCOSObject().getString(COSName.R);
    }

    public void setScaleRatio(String scaleRatio) {
        getCOSObject().setString(COSName.R, scaleRatio);
    }

    public PDNumberFormatDictionary[] getChangeXs() {
        COSArray x = (COSArray) getCOSObject().getDictionaryObject("X");
        if (x != null) {
            PDNumberFormatDictionary[] retval = new PDNumberFormatDictionary[x.size()];
            for (int i = 0; i < x.size(); i++) {
                COSDictionary dic = (COSDictionary) x.get(i);
                retval[i] = new PDNumberFormatDictionary(dic);
            }
            return retval;
        }
        return null;
    }

    public void setChangeXs(PDNumberFormatDictionary[] changeXs) {
        COSArray array = new COSArray();
        for (PDNumberFormatDictionary changeX : changeXs) {
            array.add((COSObjectable) changeX);
        }
        getCOSObject().setItem("X", (COSBase) array);
    }

    public PDNumberFormatDictionary[] getChangeYs() {
        COSArray y = (COSArray) getCOSObject().getDictionaryObject("Y");
        if (y != null) {
            PDNumberFormatDictionary[] retval = new PDNumberFormatDictionary[y.size()];
            for (int i = 0; i < y.size(); i++) {
                COSDictionary dic = (COSDictionary) y.get(i);
                retval[i] = new PDNumberFormatDictionary(dic);
            }
            return retval;
        }
        return null;
    }

    public void setChangeYs(PDNumberFormatDictionary[] changeYs) {
        COSArray array = new COSArray();
        for (PDNumberFormatDictionary changeY : changeYs) {
            array.add((COSObjectable) changeY);
        }
        getCOSObject().setItem("Y", (COSBase) array);
    }

    public PDNumberFormatDictionary[] getDistances() {
        COSArray d = (COSArray) getCOSObject().getDictionaryObject("D", COSArray.class);
        if (d != null) {
            PDNumberFormatDictionary[] retval = new PDNumberFormatDictionary[d.size()];
            for (int i = 0; i < d.size(); i++) {
                COSDictionary dic = (COSDictionary) d.get(i);
                retval[i] = new PDNumberFormatDictionary(dic);
            }
            return retval;
        }
        return null;
    }

    public void setDistances(PDNumberFormatDictionary[] distances) {
        COSArray array = new COSArray();
        for (PDNumberFormatDictionary distance : distances) {
            array.add((COSObjectable) distance);
        }
        getCOSObject().setItem("D", (COSBase) array);
    }

    public PDNumberFormatDictionary[] getAreas() {
        COSArray a = (COSArray) getCOSObject().getDictionaryObject(COSName.A, COSArray.class);
        if (a != null) {
            PDNumberFormatDictionary[] retval = new PDNumberFormatDictionary[a.size()];
            for (int i = 0; i < a.size(); i++) {
                COSDictionary dic = (COSDictionary) a.get(i);
                retval[i] = new PDNumberFormatDictionary(dic);
            }
            return retval;
        }
        return null;
    }

    public void setAreas(PDNumberFormatDictionary[] areas) {
        COSArray array = new COSArray();
        for (PDNumberFormatDictionary area : areas) {
            array.add((COSObjectable) area);
        }
        getCOSObject().setItem(COSName.A, (COSBase) array);
    }

    public PDNumberFormatDictionary[] getAngles() {
        COSArray t = (COSArray) getCOSObject().getDictionaryObject(PDNumberFormatDictionary.FRACTIONAL_DISPLAY_TRUNCATE, COSArray.class);
        if (t != null) {
            PDNumberFormatDictionary[] retval = new PDNumberFormatDictionary[t.size()];
            for (int i = 0; i < t.size(); i++) {
                COSDictionary dic = (COSDictionary) t.get(i);
                retval[i] = new PDNumberFormatDictionary(dic);
            }
            return retval;
        }
        return null;
    }

    public void setAngles(PDNumberFormatDictionary[] angles) {
        COSArray array = new COSArray();
        for (PDNumberFormatDictionary angle : angles) {
            array.add((COSObjectable) angle);
        }
        getCOSObject().setItem(PDNumberFormatDictionary.FRACTIONAL_DISPLAY_TRUNCATE, (COSBase) array);
    }

    public PDNumberFormatDictionary[] getLineSloaps() {
        COSArray s = (COSArray) getCOSObject().getDictionaryObject("S");
        if (s != null) {
            PDNumberFormatDictionary[] retval = new PDNumberFormatDictionary[s.size()];
            for (int i = 0; i < s.size(); i++) {
                COSDictionary dic = (COSDictionary) s.get(i);
                retval[i] = new PDNumberFormatDictionary(dic);
            }
            return retval;
        }
        return null;
    }

    public void setLineSloaps(PDNumberFormatDictionary[] lineSloaps) {
        COSArray array = new COSArray();
        for (PDNumberFormatDictionary lineSloap : lineSloaps) {
            array.add((COSObjectable) lineSloap);
        }
        getCOSObject().setItem("S", (COSBase) array);
    }

    public float[] getCoordSystemOrigin() {
        COSArray o = (COSArray) getCOSObject().getDictionaryObject(PDAnnotationLink.HIGHLIGHT_MODE_OUTLINE);
        if (o != null) {
            return o.toFloatArray();
        }
        return null;
    }

    public void setCoordSystemOrigin(float[] coordSystemOrigin) {
        COSArray array = new COSArray();
        array.setFloatArray(coordSystemOrigin);
        getCOSObject().setItem(PDAnnotationLink.HIGHLIGHT_MODE_OUTLINE, (COSBase) array);
    }

    public float getCYX() {
        return getCOSObject().getFloat("CYX");
    }

    public void setCYX(float cyx) {
        getCOSObject().setFloat("CYX", cyx);
    }
}
