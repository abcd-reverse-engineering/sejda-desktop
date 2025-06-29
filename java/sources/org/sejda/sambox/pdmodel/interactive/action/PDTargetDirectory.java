package org.sejda.sambox.pdmodel.interactive.action;

import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSInteger;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.cos.COSString;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.PDNamedDestination;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/action/PDTargetDirectory.class */
public class PDTargetDirectory implements COSObjectable {
    private final COSDictionary dict;

    public PDTargetDirectory() {
        this.dict = new COSDictionary();
    }

    public PDTargetDirectory(COSDictionary dictionary) {
        this.dict = dictionary;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSDictionary getCOSObject() {
        return this.dict;
    }

    public COSName getRelationship() {
        COSBase base = this.dict.getItem(COSName.R);
        if (base instanceof COSName) {
            return (COSName) base;
        }
        return null;
    }

    public void setRelationship(COSName relationship) {
        if (!COSName.P.equals(relationship) && !COSName.C.equals(relationship)) {
            throw new IllegalArgumentException("The only valid are P or C, not " + relationship.getName());
        }
        this.dict.setItem(COSName.R, (COSBase) relationship);
    }

    public String getFilename() {
        return this.dict.getString(COSName.N);
    }

    public void setFilename(String filename) {
        this.dict.setString(COSName.N, filename);
    }

    public PDTargetDirectory getTargetDirectory() {
        COSBase base = this.dict.getDictionaryObject(COSName.T);
        if (base instanceof COSDictionary) {
            return new PDTargetDirectory((COSDictionary) base);
        }
        return null;
    }

    public void setTargetDirectory(PDTargetDirectory targetDirectory) {
        this.dict.setItem(COSName.T, targetDirectory);
    }

    public int getPageNumber() {
        COSBase base = this.dict.getDictionaryObject(COSName.P);
        if (base instanceof COSInteger) {
            return ((COSInteger) base).intValue();
        }
        return -1;
    }

    public void setPageNumber(int pageNumber) {
        if (pageNumber < 0) {
            this.dict.removeItem(COSName.P);
        } else {
            this.dict.setInt(COSName.P, pageNumber);
        }
    }

    public PDNamedDestination getNamedDestination() {
        COSBase base = this.dict.getDictionaryObject(COSName.P);
        if (base instanceof COSString) {
            return new PDNamedDestination((COSString) base);
        }
        return null;
    }

    public void setNamedDestination(PDNamedDestination dest) {
        if (dest == null) {
            this.dict.removeItem(COSName.P);
        } else {
            this.dict.setItem(COSName.P, dest);
        }
    }

    public int getAnnotationIndex() {
        COSBase base = this.dict.getDictionaryObject(COSName.A);
        if (base instanceof COSInteger) {
            return ((COSInteger) base).intValue();
        }
        return -1;
    }

    public void setAnnotationIndex(int index) {
        if (index < 0) {
            this.dict.removeItem(COSName.A);
        } else {
            this.dict.setInt(COSName.A, index);
        }
    }

    public String getAnnotationName() {
        COSBase base = this.dict.getDictionaryObject(COSName.A);
        if (base instanceof COSString) {
            return ((COSString) base).getString();
        }
        return null;
    }

    public void setAnnotationName(String name) {
        this.dict.setString(COSName.A, name);
    }
}
