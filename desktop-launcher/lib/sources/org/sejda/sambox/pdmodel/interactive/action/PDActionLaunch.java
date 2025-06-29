package org.sejda.sambox.pdmodel.interactive.action;

import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSBoolean;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.common.filespecification.FileSpecifications;
import org.sejda.sambox.pdmodel.common.filespecification.PDFileSpecification;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/action/PDActionLaunch.class */
public class PDActionLaunch extends PDAction {
    public static final String SUB_TYPE = "Launch";

    public PDActionLaunch() {
        setSubType(SUB_TYPE);
    }

    public PDActionLaunch(COSDictionary a) {
        super(a);
    }

    public PDFileSpecification getFile() {
        return FileSpecifications.fileSpecificationFor(getCOSObject().getDictionaryObject(COSName.F));
    }

    public void setFile(PDFileSpecification fs) {
        getCOSObject().setItem(COSName.F, fs);
    }

    public PDWindowsLaunchParams getWinLaunchParams() {
        COSDictionary win = (COSDictionary) this.action.getDictionaryObject("Win");
        PDWindowsLaunchParams retval = null;
        if (win != null) {
            retval = new PDWindowsLaunchParams(win);
        }
        return retval;
    }

    public void setWinLaunchParams(PDWindowsLaunchParams win) {
        this.action.setItem("Win", win);
    }

    public String getF() {
        return this.action.getString(COSName.F);
    }

    public void setF(String f) {
        this.action.setString(COSName.F, f);
    }

    public String getD() {
        return this.action.getString(COSName.D);
    }

    public void setD(String d) {
        this.action.setString(COSName.D, d);
    }

    public String getO() {
        return this.action.getString(COSName.O);
    }

    public void setO(String o) {
        this.action.setString(COSName.O, o);
    }

    public String getP() {
        return this.action.getString(COSName.P);
    }

    public void setP(String p) {
        this.action.setString(COSName.P, p);
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
}
