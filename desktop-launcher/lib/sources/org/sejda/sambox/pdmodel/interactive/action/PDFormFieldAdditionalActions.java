package org.sejda.sambox.pdmodel.interactive.action;

import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.pdmodel.interactive.annotation.PDBorderEffectDictionary;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/action/PDFormFieldAdditionalActions.class */
public class PDFormFieldAdditionalActions implements COSObjectable {
    private final COSDictionary actions;

    public PDFormFieldAdditionalActions() {
        this.actions = new COSDictionary();
    }

    public PDFormFieldAdditionalActions(COSDictionary a) {
        this.actions = a;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSDictionary getCOSObject() {
        return this.actions;
    }

    public PDAction getK() {
        COSDictionary k = (COSDictionary) this.actions.getDictionaryObject(OperatorName.STROKING_COLOR_CMYK);
        PDAction retval = null;
        if (k != null) {
            retval = PDActionFactory.createAction(k);
        }
        return retval;
    }

    public void setK(PDAction k) {
        this.actions.setItem(OperatorName.STROKING_COLOR_CMYK, k);
    }

    public PDAction getF() {
        COSBase f = this.actions.getDictionaryObject("F");
        PDAction retval = null;
        if (f instanceof COSDictionary) {
            retval = PDActionFactory.createAction((COSDictionary) f);
        }
        return retval;
    }

    public void setF(PDAction f) {
        this.actions.setItem("F", f);
    }

    public PDAction getV() {
        COSDictionary v = (COSDictionary) this.actions.getDictionaryObject("V");
        PDAction retval = null;
        if (v != null) {
            retval = PDActionFactory.createAction(v);
        }
        return retval;
    }

    public void setV(PDAction v) {
        this.actions.setItem("V", v);
    }

    public PDAction getC() {
        COSDictionary c = (COSDictionary) this.actions.getDictionaryObject(PDBorderEffectDictionary.STYLE_CLOUDY);
        PDAction retval = null;
        if (c != null) {
            retval = PDActionFactory.createAction(c);
        }
        return retval;
    }

    public void setC(PDAction c) {
        this.actions.setItem(PDBorderEffectDictionary.STYLE_CLOUDY, c);
    }
}
