package org.sejda.sambox.pdmodel.interactive.action;

import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/action/PDAdditionalActions.class */
public class PDAdditionalActions implements COSObjectable {
    private final COSDictionary actions;

    public PDAdditionalActions() {
        this.actions = new COSDictionary();
    }

    public PDAdditionalActions(COSDictionary a) {
        this.actions = a;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSDictionary getCOSObject() {
        return this.actions;
    }

    public PDAction getF() {
        return PDActionFactory.createAction((COSDictionary) this.actions.getDictionaryObject(COSName.F));
    }

    public void setF(PDAction action) {
        this.actions.setItem(COSName.F, action);
    }
}
