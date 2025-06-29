package org.sejda.sambox.pdmodel.documentinterchange.logicalstructure;

import java.io.IOException;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.pdmodel.common.PDDictionaryWrapper;
import org.sejda.sambox.pdmodel.graphics.PDXObject;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationUnknown;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/documentinterchange/logicalstructure/PDObjectReference.class */
public class PDObjectReference extends PDDictionaryWrapper {
    public static final String TYPE = "OBJR";

    public PDObjectReference() {
        getCOSObject().setName(COSName.TYPE, TYPE);
    }

    public PDObjectReference(COSDictionary dictionary) {
        super(dictionary);
    }

    public COSObjectable getReferencedObject() {
        PDXObject xobject;
        COSBase obj = getCOSObject().getDictionaryObject(COSName.OBJ);
        if (!(obj instanceof COSDictionary)) {
            return null;
        }
        try {
            if ((obj instanceof COSStream) && (xobject = PDXObject.createXObject(obj, null)) != null) {
                return xobject;
            }
            COSDictionary objDictionary = (COSDictionary) obj;
            PDAnnotation annotation = PDAnnotation.createAnnotation(obj);
            if (annotation instanceof PDAnnotationUnknown) {
                if (!COSName.ANNOT.equals(objDictionary.getDictionaryObject(COSName.TYPE))) {
                    return null;
                }
            }
            return annotation;
        } catch (IOException e) {
            return null;
        }
    }

    public void setReferencedObject(PDAnnotation annotation) {
        getCOSObject().setItem(COSName.OBJ, annotation);
    }

    public void setReferencedObject(PDXObject xobject) {
        getCOSObject().setItem(COSName.OBJ, xobject);
    }
}
