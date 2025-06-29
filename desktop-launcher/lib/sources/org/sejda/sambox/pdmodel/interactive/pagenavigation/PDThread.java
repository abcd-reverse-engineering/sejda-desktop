package org.sejda.sambox.pdmodel.interactive.pagenavigation;

import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.pdmodel.PDDocumentInformation;
import org.sejda.sambox.pdmodel.interactive.action.PDActionThread;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/pagenavigation/PDThread.class */
public class PDThread implements COSObjectable {
    private COSDictionary thread;

    public PDThread(COSDictionary t) {
        this.thread = t;
    }

    public PDThread() {
        this.thread = new COSDictionary();
        this.thread.setName("Type", PDActionThread.SUB_TYPE);
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSDictionary getCOSObject() {
        return this.thread;
    }

    public PDDocumentInformation getThreadInfo() {
        PDDocumentInformation retval = null;
        COSDictionary info = (COSDictionary) this.thread.getDictionaryObject("I");
        if (info != null) {
            retval = new PDDocumentInformation(info);
        }
        return retval;
    }

    public void setThreadInfo(PDDocumentInformation info) {
        this.thread.setItem("I", info);
    }

    public PDThreadBead getFirstBead() {
        PDThreadBead retval = null;
        COSDictionary bead = (COSDictionary) this.thread.getDictionaryObject("F");
        if (bead != null) {
            retval = new PDThreadBead(bead);
        }
        return retval;
    }

    public void setFirstBead(PDThreadBead bead) {
        if (bead != null) {
            bead.setThread(this);
        }
        this.thread.setItem("F", bead);
    }
}
