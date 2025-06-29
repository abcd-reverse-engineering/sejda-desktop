package org.sejda.sambox.pdmodel.interactive.annotation;

import java.util.Calendar;
import java.util.Optional;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSFloat;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.cos.COSString;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.documentinterchange.taggedpdf.StandardStructureTypes;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDAppearanceHandler;
import org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDCaretAppearanceHandler;
import org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDFileAttachmentAppearanceHandler;
import org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDFreeTextAppearanceHandler;
import org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDInkAppearanceHandler;
import org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDPolygonAppearanceHandler;
import org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDPolylineAppearanceHandler;
import org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDSoundAppearanceHandler;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/annotation/PDAnnotationMarkup.class */
public class PDAnnotationMarkup extends PDAnnotation {
    private PDAppearanceHandler customAppearanceHandler;
    public static final String SUB_TYPE_FREETEXT = "FreeText";
    public static final String SUB_TYPE_POLYGON = "Polygon";
    public static final String SUB_TYPE_POLYLINE = "PolyLine";
    public static final String SUB_TYPE_CARET = "Caret";
    public static final String SUB_TYPE_INK = "Ink";
    public static final String SUB_TYPE_SOUND = "Sound";
    public static final String IT_FREE_TEXT = "FreeText";
    public static final String IT_FREE_TEXT_CALLOUT = "FreeTextCallout";
    public static final String IT_FREE_TEXT_TYPE_WRITER = "FreeTextTypeWriter";
    public static final String RT_REPLY = "R";
    public static final String RT_GROUP = "Group";

    public PDAnnotationMarkup() {
    }

    public PDAnnotationMarkup(COSDictionary dict) {
        super(dict);
    }

    public String getTitlePopup() {
        return getCOSObject().getString(COSName.T);
    }

    public void setTitlePopup(String t) {
        getCOSObject().setString(COSName.T, t);
    }

    public PDAnnotationPopup getPopup() {
        return (PDAnnotationPopup) Optional.ofNullable((COSDictionary) getCOSObject().getDictionaryObject(COSName.POPUP, COSDictionary.class)).map(PDAnnotationPopup::new).orElse(null);
    }

    public void setPopup(PDAnnotationPopup popup) {
        getCOSObject().setItem(COSName.POPUP, popup);
    }

    public float getConstantOpacity() {
        return getCOSObject().getFloat(COSName.CA, 1.0f);
    }

    public void setConstantOpacity(float ca) {
        getCOSObject().setFloat(COSName.CA, ca);
    }

    public String getRichContents() {
        COSBase base = getCOSObject().getDictionaryObject(COSName.RC);
        if (base instanceof COSString) {
            return ((COSString) base).getString();
        }
        if (base instanceof COSStream) {
            return ((COSStream) base).asTextString();
        }
        return null;
    }

    public void setRichContents(String rc) {
        getCOSObject().setItem(COSName.RC, (COSBase) COSString.parseLiteral(rc));
    }

    public Calendar getCreationDate() {
        return getCOSObject().getDate(COSName.CREATION_DATE);
    }

    public void setCreationDate(Calendar creationDate) {
        getCOSObject().setDate(COSName.CREATION_DATE, creationDate);
    }

    public PDAnnotation getInReplyTo() {
        return (PDAnnotation) Optional.ofNullable((COSDictionary) getCOSObject().getDictionaryObject("IRT", COSDictionary.class)).map((v0) -> {
            return PDAnnotation.createAnnotation(v0);
        }).orElse(null);
    }

    public void setInReplyTo(PDAnnotation irt) {
        getCOSObject().setItem("IRT", irt);
    }

    public String getSubject() {
        return getCOSObject().getString(COSName.SUBJ);
    }

    public void setSubject(String subj) {
        getCOSObject().setString(COSName.SUBJ, subj);
    }

    public String getReplyType() {
        return getCOSObject().getNameAsString(StandardStructureTypes.RT, "R");
    }

    public void setReplyType(String rt) {
        getCOSObject().setName(StandardStructureTypes.RT, rt);
    }

    public String getIntent() {
        return getCOSObject().getNameAsString(COSName.IT);
    }

    public void setIntent(String it) {
        getCOSObject().setName(COSName.IT, it);
    }

    public PDExternalDataDictionary getExternalData() {
        COSBase exData = getCOSObject().getDictionaryObject("ExData");
        if (exData instanceof COSDictionary) {
            return new PDExternalDataDictionary((COSDictionary) exData);
        }
        return null;
    }

    public void setExternalData(PDExternalDataDictionary externalData) {
        getCOSObject().setItem("ExData", externalData);
    }

    public void setBorderStyle(PDBorderStyleDictionary bs) {
        getCOSObject().setItem(COSName.BS, bs);
    }

    public PDBorderStyleDictionary getBorderStyle() {
        COSDictionary bs = (COSDictionary) getCOSObject().getDictionaryObject(COSName.BS, COSDictionary.class);
        if (bs != null) {
            return new PDBorderStyleDictionary(bs);
        }
        return null;
    }

    public final void setLineEndingStyle(String style) {
        getCOSObject().setName(COSName.LE, style);
    }

    public String getLineEndingStyle() {
        return getCOSObject().getNameAsString(COSName.LE, "None");
    }

    public void setInteriorColor(PDColor ic) {
        getCOSObject().setItem(COSName.IC, (COSBase) ic.toCOSArray());
    }

    public PDColor getInteriorColor() {
        return getColor(COSName.IC);
    }

    public void setBorderEffect(PDBorderEffectDictionary be) {
        getCOSObject().setItem(COSName.BE, be);
    }

    public PDBorderEffectDictionary getBorderEffect() {
        COSDictionary be = (COSDictionary) getCOSObject().getDictionaryObject(COSName.BE);
        if (be != null) {
            return new PDBorderEffectDictionary(be);
        }
        return null;
    }

    public void setInkList(float[][] inkList) {
        if (inkList == null) {
            getCOSObject().removeItem(COSName.INKLIST);
            return;
        }
        COSArray array = new COSArray();
        for (float[] path : inkList) {
            COSArray innerArray = new COSArray();
            innerArray.setFloatArray(path);
            array.add((COSBase) innerArray);
        }
        getCOSObject().setItem(COSName.INKLIST, (COSBase) array);
    }

    /* JADX WARN: Type inference failed for: r0v11, types: [float[], float[][]] */
    public float[][] getInkList() {
        COSBase base = getCOSObject().getDictionaryObject(COSName.INKLIST);
        if (base instanceof COSArray) {
            COSArray array = (COSArray) base;
            ?? r0 = new float[array.size()];
            for (int i = 0; i < array.size(); i++) {
                COSBase base2 = array.getObject(i);
                if (base2 instanceof COSArray) {
                    r0[i] = ((COSArray) base2).toFloatArray();
                } else {
                    r0[i] = new float[0];
                }
            }
            return r0;
        }
        return new float[0][0];
    }

    public String getDefaultAppearance() {
        return getCOSObject().getString(COSName.DA);
    }

    public void setDefaultAppearance(String daValue) {
        getCOSObject().setString(COSName.DA, daValue);
    }

    public String getDefaultStyleString() {
        return getCOSObject().getString(COSName.DS);
    }

    public void setDefaultStyleString(String defaultStyleString) {
        getCOSObject().setString(COSName.DS, defaultStyleString);
    }

    public int getQ() {
        return getCOSObject().getInt(COSName.Q, 0);
    }

    public void setQ(int q) {
        getCOSObject().setInt(COSName.Q, q);
    }

    public void setRectDifference(PDRectangle rd) {
        getCOSObject().setItem(COSName.RD, rd);
    }

    public PDRectangle getRectDifference() {
        COSBase base = getCOSObject().getDictionaryObject(COSName.RD);
        if (base instanceof COSArray) {
            return new PDRectangle((COSArray) base);
        }
        return null;
    }

    public void setRectDifferences(float difference) {
        setRectDifferences(difference, difference, difference, difference);
    }

    public void setRectDifferences(float differenceLeft, float differenceTop, float differenceRight, float differenceBottom) {
        COSArray margins = new COSArray();
        margins.add((COSBase) new COSFloat(differenceLeft));
        margins.add((COSBase) new COSFloat(differenceTop));
        margins.add((COSBase) new COSFloat(differenceRight));
        margins.add((COSBase) new COSFloat(differenceBottom));
        getCOSObject().setItem(COSName.RD, (COSBase) margins);
    }

    public float[] getRectDifferences() {
        COSBase margin = getCOSObject().getItem(COSName.RD);
        if (margin instanceof COSArray) {
            return ((COSArray) margin).toFloatArray();
        }
        return new float[0];
    }

    public final void setCallout(float[] callout) {
        COSArray newCallout = new COSArray();
        newCallout.setFloatArray(callout);
        getCOSObject().setItem(COSName.CL, (COSBase) newCallout);
    }

    public float[] getCallout() {
        COSBase base = getCOSObject().getDictionaryObject(COSName.CL);
        if (base instanceof COSArray) {
            return ((COSArray) base).toFloatArray();
        }
        return null;
    }

    public void setStartPointEndingStyle(String style) {
        String actualStyle = style == null ? "None" : style;
        COSBase base = getCOSObject().getDictionaryObject(COSName.LE);
        if (!(base instanceof COSArray) || ((COSArray) base).size() == 0) {
            COSArray array = new COSArray();
            array.add((COSBase) COSName.getPDFName(actualStyle));
            array.add((COSBase) COSName.getPDFName("None"));
            getCOSObject().setItem(COSName.LE, (COSBase) array);
            return;
        }
        ((COSArray) base).set(0, (COSBase) COSName.getPDFName(actualStyle));
    }

    public String getStartPointEndingStyle() {
        COSBase base = getCOSObject().getDictionaryObject(COSName.LE);
        if ((base instanceof COSArray) && ((COSArray) base).size() >= 2) {
            return ((COSArray) base).getName(0, "None");
        }
        return "None";
    }

    public void setEndPointEndingStyle(String style) {
        String actualStyle = style == null ? "None" : style;
        COSBase base = getCOSObject().getDictionaryObject(COSName.LE);
        if (!(base instanceof COSArray) || ((COSArray) base).size() < 2) {
            COSArray array = new COSArray();
            array.add((COSBase) COSName.getPDFName("None"));
            array.add((COSBase) COSName.getPDFName(actualStyle));
            getCOSObject().setItem(COSName.LE, (COSBase) array);
            return;
        }
        ((COSArray) base).set(1, (COSBase) COSName.getPDFName(actualStyle));
    }

    public String getEndPointEndingStyle() {
        COSBase base = getCOSObject().getDictionaryObject(COSName.LE);
        if ((base instanceof COSArray) && ((COSArray) base).size() >= 2) {
            return ((COSArray) base).getName(1, "None");
        }
        return "None";
    }

    public float[] getVertices() {
        COSBase base = getCOSObject().getDictionaryObject(COSName.VERTICES);
        if (base instanceof COSArray) {
            return ((COSArray) base).toFloatArray();
        }
        return null;
    }

    public void setVertices(float[] points) {
        COSArray ar = new COSArray();
        ar.setFloatArray(points);
        getCOSObject().setItem(COSName.VERTICES, (COSBase) ar);
    }

    /* JADX WARN: Type inference failed for: r0v10, types: [float[], float[][]] */
    public float[][] getPath() {
        COSBase base = getCOSObject().getDictionaryObject(COSName.PATH);
        if (base instanceof COSArray) {
            COSArray array = (COSArray) base;
            ?? r0 = new float[array.size()];
            for (int i = 0; i < array.size(); i++) {
                COSBase base2 = array.getObject(i);
                if (base2 instanceof COSArray) {
                    r0[i] = ((COSArray) base2).toFloatArray();
                } else {
                    r0[i] = new float[0];
                }
            }
            return r0;
        }
        return null;
    }

    public void setCustomAppearanceHandler(PDAppearanceHandler appearanceHandler) {
        this.customAppearanceHandler = appearanceHandler;
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation
    public void constructAppearances() {
        if (this.customAppearanceHandler == null) {
            PDAppearanceHandler appearanceHandler = null;
            if (SUB_TYPE_CARET.equals(getSubtype())) {
                appearanceHandler = new PDCaretAppearanceHandler(this);
            } else if ("FreeText".equals(getSubtype())) {
                appearanceHandler = new PDFreeTextAppearanceHandler(this);
            } else if (SUB_TYPE_INK.equals(getSubtype())) {
                appearanceHandler = new PDInkAppearanceHandler(this);
            } else if (SUB_TYPE_POLYGON.equals(getSubtype())) {
                appearanceHandler = new PDPolygonAppearanceHandler(this);
            } else if (SUB_TYPE_POLYLINE.equals(getSubtype())) {
                appearanceHandler = new PDPolylineAppearanceHandler(this);
            } else if ("Sound".equals(getSubtype())) {
                appearanceHandler = new PDSoundAppearanceHandler(this);
            } else if (PDAnnotationFileAttachment.SUB_TYPE.equals(getSubtype())) {
                appearanceHandler = new PDFileAttachmentAppearanceHandler(this);
            }
            if (appearanceHandler != null) {
                appearanceHandler.generateAppearanceStreams();
                return;
            }
            return;
        }
        this.customAppearanceHandler.generateAppearanceStreams();
    }
}
