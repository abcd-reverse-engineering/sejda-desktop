package org.sejda.sambox.pdmodel.interactive.documentnavigation.destination;

import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSInteger;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSNumber;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.PDPageTree;
import org.sejda.sambox.util.Matrix;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/documentnavigation/destination/PDPageDestination.class */
public abstract class PDPageDestination extends PDDestination {
    protected final COSArray array;

    public abstract void transform(Matrix matrix);

    protected PDPageDestination() {
        this.array = new COSArray();
    }

    protected PDPageDestination(COSArray array) {
        this.array = array;
    }

    public PDPage getPage() {
        if (this.array.size() > 0) {
            COSBase page = this.array.getObject(0);
            if (page instanceof COSDictionary) {
                return new PDPage((COSDictionary) page);
            }
            return null;
        }
        return null;
    }

    public void setPage(PDPage page) {
        this.array.set(0, (COSObjectable) page);
    }

    public int getPageNumber() {
        if (this.array.size() > 0) {
            COSBase page = this.array.getObject(0);
            if (page instanceof COSNumber) {
                return ((COSNumber) page).intValue();
            }
            return -1;
        }
        return -1;
    }

    public int retrievePageNumber() {
        if (this.array.size() > 0) {
            COSBase page = this.array.getObject(0);
            if (page instanceof COSNumber) {
                return ((COSNumber) page).intValue();
            }
            if (page instanceof COSDictionary) {
                return indexOfPageTree((COSDictionary) page);
            }
            return -1;
        }
        return -1;
    }

    private int indexOfPageTree(COSDictionary pageDict) {
        COSDictionary parent;
        COSDictionary cOSDictionary = pageDict;
        while (true) {
            parent = cOSDictionary;
            if (!(parent.getDictionaryObject(COSName.PARENT, COSName.P) instanceof COSDictionary)) {
                break;
            }
            cOSDictionary = (COSDictionary) parent.getDictionaryObject(COSName.PARENT, COSName.P);
        }
        if (parent.containsKey(COSName.KIDS) && COSName.PAGES.equals(parent.getItem(COSName.TYPE))) {
            PDPageTree pages = new PDPageTree(parent);
            return pages.indexOf(new PDPage(pageDict));
        }
        return -1;
    }

    public void setPageNumber(int pageNumber) {
        this.array.set(0, (COSBase) COSInteger.get(pageNumber));
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSArray getCOSObject() {
        return this.array;
    }
}
