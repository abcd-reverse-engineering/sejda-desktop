package org.sejda.sambox.pdmodel.interactive.pagenavigation;

import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.sejda.sambox.pdmodel.interactive.measurement.PDNumberFormatDictionary;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/pagenavigation/PDThreadBead.class */
public class PDThreadBead implements COSObjectable {
    private final COSDictionary bead;

    public PDThreadBead(COSDictionary b) {
        this.bead = b;
    }

    public PDThreadBead() {
        this.bead = new COSDictionary();
        this.bead.setName("Type", "Bead");
        setNextBead(this);
        setPreviousBead(this);
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSDictionary getCOSObject() {
        return this.bead;
    }

    public PDThread getThread() {
        PDThread retval = null;
        COSDictionary dic = (COSDictionary) this.bead.getDictionaryObject(PDNumberFormatDictionary.FRACTIONAL_DISPLAY_TRUNCATE);
        if (dic != null) {
            retval = new PDThread(dic);
        }
        return retval;
    }

    public void setThread(PDThread thread) {
        this.bead.setItem(PDNumberFormatDictionary.FRACTIONAL_DISPLAY_TRUNCATE, thread);
    }

    public PDThreadBead getNextBead() {
        return new PDThreadBead((COSDictionary) this.bead.getDictionaryObject(PDAnnotationLink.HIGHLIGHT_MODE_NONE));
    }

    protected final void setNextBead(PDThreadBead next) {
        this.bead.setItem(PDAnnotationLink.HIGHLIGHT_MODE_NONE, next);
    }

    public PDThreadBead getPreviousBead() {
        return new PDThreadBead((COSDictionary) this.bead.getDictionaryObject("V"));
    }

    protected final void setPreviousBead(PDThreadBead previous) {
        this.bead.setItem("V", previous);
    }

    public void appendBead(PDThreadBead append) {
        PDThreadBead nextBead = getNextBead();
        nextBead.setPreviousBead(append);
        append.setNextBead(nextBead);
        setNextBead(append);
        append.setPreviousBead(this);
    }

    public PDPage getPage() {
        PDPage page = null;
        COSDictionary dic = (COSDictionary) this.bead.getDictionaryObject("P");
        if (dic != null) {
            page = new PDPage(dic);
        }
        return page;
    }

    public void setPage(PDPage page) {
        this.bead.setItem("P", page);
    }

    public PDRectangle getRectangle() {
        PDRectangle rect = null;
        COSArray array = (COSArray) this.bead.getDictionaryObject(COSName.R);
        if (array != null) {
            rect = new PDRectangle(array);
        }
        return rect;
    }

    public void setRectangle(PDRectangle rect) {
        this.bead.setItem(COSName.R, rect);
    }
}
