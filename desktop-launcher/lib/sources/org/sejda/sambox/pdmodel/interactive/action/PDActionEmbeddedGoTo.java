package org.sejda.sambox.pdmodel.interactive.action;

import java.io.IOException;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSBoolean;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSInteger;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.common.filespecification.PDFileSpecification;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.PDDestination;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/action/PDActionEmbeddedGoTo.class */
public class PDActionEmbeddedGoTo extends PDAction {
    public static final String SUB_TYPE = "GoToE";

    public PDActionEmbeddedGoTo() {
        setSubType(SUB_TYPE);
    }

    public PDActionEmbeddedGoTo(COSDictionary a) {
        super(a);
    }

    public PDDestination getDestination() throws IOException {
        return PDDestination.create(getCOSObject().getDictionaryObject(COSName.D));
    }

    public void setDestination(PDDestination d) {
        if (d instanceof PDPageDestination) {
            PDPageDestination pageDest = (PDPageDestination) d;
            COSArray destArray = pageDest.getCOSObject();
            if (destArray.size() >= 1) {
                COSBase page = destArray.getObject(0);
                if (!(page instanceof COSInteger)) {
                    throw new IllegalArgumentException("Destination of a GoToE action must be an integer");
                }
            }
        }
        getCOSObject().setItem(COSName.D, d);
    }

    public PDFileSpecification getFile() throws IOException {
        return PDFileSpecification.createFS(getCOSObject().getDictionaryObject(COSName.F));
    }

    public void setFile(PDFileSpecification fs) {
        getCOSObject().setItem(COSName.F, fs);
    }

    public OpenMode getOpenInNewWindow() {
        COSBase dictionaryObject = getCOSObject().getDictionaryObject(COSName.NEW_WINDOW);
        if (!(dictionaryObject instanceof COSBoolean)) {
            return OpenMode.USER_PREFERENCE;
        }
        COSBoolean b = (COSBoolean) dictionaryObject;
        return b.getValue() ? OpenMode.NEW_WINDOW : OpenMode.SAME_WINDOW;
    }

    public void setOpenInNewWindow(OpenMode value) {
        if (null == value) {
            getCOSObject().removeItem(COSName.NEW_WINDOW);
        }
        switch (value) {
            case USER_PREFERENCE:
                getCOSObject().removeItem(COSName.NEW_WINDOW);
                break;
            case SAME_WINDOW:
                getCOSObject().setBoolean(COSName.NEW_WINDOW, false);
                break;
            case NEW_WINDOW:
                getCOSObject().setBoolean(COSName.NEW_WINDOW, true);
                break;
        }
    }

    public PDTargetDirectory getTargetDirectory() {
        COSBase base = getCOSObject().getDictionaryObject(COSName.T);
        if (base instanceof COSDictionary) {
            return new PDTargetDirectory((COSDictionary) base);
        }
        return null;
    }

    public void setTargetDirectory(PDTargetDirectory targetDirectory) {
        getCOSObject().setItem(COSName.T, targetDirectory);
    }
}
