package org.sejda.sambox.pdmodel.interactive.documentnavigation.destination;

import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSString;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/documentnavigation/destination/PDNamedDestination.class */
public class PDNamedDestination extends PDDestination {
    private COSBase namedDestination;

    public PDNamedDestination(COSString dest) {
        this.namedDestination = dest;
    }

    public PDNamedDestination(COSName dest) {
        this.namedDestination = dest;
    }

    public PDNamedDestination() {
    }

    public PDNamedDestination(String dest) {
        this.namedDestination = COSString.parseLiteral(dest);
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSBase getCOSObject() {
        return this.namedDestination;
    }

    public String getNamedDestination() {
        String retval = null;
        if (this.namedDestination instanceof COSString) {
            retval = ((COSString) this.namedDestination).getString();
        } else if (this.namedDestination instanceof COSName) {
            retval = ((COSName) this.namedDestination).getName();
        }
        return retval;
    }

    public void setNamedDestination(String dest) {
        if (dest == null) {
            this.namedDestination = null;
        } else {
            this.namedDestination = COSString.parseLiteral(dest);
        }
    }
}
