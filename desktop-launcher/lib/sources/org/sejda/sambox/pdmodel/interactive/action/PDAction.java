package org.sejda.sambox.pdmodel.interactive.action;

import java.util.ArrayList;
import java.util.List;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSArrayList;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.common.PDDestinationOrAction;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/action/PDAction.class */
public abstract class PDAction implements PDDestinationOrAction {
    public static final String TYPE = "Action";
    protected COSDictionary action;

    public PDAction() {
        this.action = new COSDictionary();
        setType(TYPE);
    }

    public PDAction(COSDictionary a) {
        this.action = a;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSDictionary getCOSObject() {
        return this.action;
    }

    public String getType() {
        return this.action.getNameAsString(COSName.TYPE);
    }

    public final void setType(String type) {
        this.action.setName(COSName.TYPE, type);
    }

    public String getSubType() {
        return this.action.getNameAsString(COSName.S);
    }

    public void setSubType(String s) {
        this.action.setName(COSName.S, s);
    }

    public List<PDAction> getNext() {
        List<PDAction> retval = null;
        COSBase next = this.action.getDictionaryObject(COSName.NEXT);
        if (next instanceof COSDictionary) {
            PDAction pdAction = PDActionFactory.createAction((COSDictionary) next);
            retval = new COSArrayList<>(pdAction, next, this.action, COSName.NEXT);
        } else if (next instanceof COSArray) {
            COSArray array = (COSArray) next;
            List<PDAction> actions = new ArrayList<>();
            for (int i = 0; i < array.size(); i++) {
                actions.add(PDActionFactory.createAction((COSDictionary) array.getObject(i)));
            }
            retval = new COSArrayList<>(actions, array);
        }
        return retval;
    }

    public void setNext(List<?> next) {
        this.action.setItem(COSName.NEXT, (COSBase) COSArrayList.converterToCOSArray(next));
    }
}
