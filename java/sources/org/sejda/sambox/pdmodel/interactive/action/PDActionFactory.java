package org.sejda.sambox.pdmodel.interactive.action;

import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/action/PDActionFactory.class */
public final class PDActionFactory {
    private PDActionFactory() {
    }

    public static PDAction createAction(COSDictionary action) {
        if (action != null) {
            String type = action.getNameAsString(COSName.S);
            if (PDActionJavaScript.SUB_TYPE.equals(type)) {
                return new PDActionJavaScript(action);
            }
            if (PDActionGoTo.SUB_TYPE.equals(type)) {
                return new PDActionGoTo(action);
            }
            if (PDActionLaunch.SUB_TYPE.equals(type)) {
                return new PDActionLaunch(action);
            }
            if (PDActionRemoteGoTo.SUB_TYPE.equals(type)) {
                return new PDActionRemoteGoTo(action);
            }
            if (PDActionURI.SUB_TYPE.equals(type)) {
                return new PDActionURI(action);
            }
            if (PDActionNamed.SUB_TYPE.equals(type)) {
                return new PDActionNamed(action);
            }
            if ("Sound".equals(type)) {
                return new PDActionSound(action);
            }
            if (PDActionMovie.SUB_TYPE.equals(type)) {
                return new PDActionMovie(action);
            }
            if (PDActionImportData.SUB_TYPE.equals(type)) {
                return new PDActionImportData(action);
            }
            if (PDActionResetForm.SUB_TYPE.equals(type)) {
                return new PDActionResetForm(action);
            }
            if (PDActionHide.SUB_TYPE.equals(type)) {
                return new PDActionHide(action);
            }
            if (PDActionSubmitForm.SUB_TYPE.equals(type)) {
                return new PDActionSubmitForm(action);
            }
            if (PDActionThread.SUB_TYPE.equals(type)) {
                return new PDActionThread(action);
            }
            if (PDActionEmbeddedGoTo.SUB_TYPE.equals(type)) {
                return new PDActionEmbeddedGoTo(action);
            }
            return null;
        }
        return null;
    }
}
