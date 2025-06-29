package org.sejda.sambox.pdmodel.interactive.action;

import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/action/PDPageAdditionalActions.class */
public class PDPageAdditionalActions implements COSObjectable {
    private final COSDictionary actions;

    public PDPageAdditionalActions() {
        this.actions = new COSDictionary();
    }

    public PDPageAdditionalActions(COSDictionary a) {
        this.actions = a;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSDictionary getCOSObject() {
        return this.actions;
    }

    public PDAction getO() {
        COSDictionary o = (COSDictionary) this.actions.getDictionaryObject(COSName.O);
        if (o != null) {
            return PDActionFactory.createAction(o);
        }
        return null;
    }

    public void setO(PDAction o) {
        this.actions.setItem(COSName.O, o);
    }

    public PDAction getC() {
        COSDictionary c = (COSDictionary) this.actions.getDictionaryObject(COSName.C);
        if (c != null) {
            return PDActionFactory.createAction(c);
        }
        return null;
    }

    public void setC(PDAction c) {
        this.actions.setItem(COSName.C, c);
    }
}
