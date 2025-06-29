package org.sejda.sambox.pdmodel.documentinterchange.logicalstructure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSArrayList;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSInteger;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/documentinterchange/logicalstructure/PDStructureNode.class */
public abstract class PDStructureNode implements COSObjectable {
    private final COSDictionary dictionary;

    protected PDStructureNode(String type) {
        this.dictionary = new COSDictionary();
        this.dictionary.setName(COSName.TYPE, type);
    }

    protected PDStructureNode(COSDictionary dictionary) {
        this.dictionary = dictionary;
    }

    public static PDStructureNode create(COSDictionary node) {
        String type = node.getNameAsString(COSName.TYPE);
        if ("StructTreeRoot".equals(type)) {
            return new PDStructureTreeRoot(node);
        }
        if (type == null || PDStructureElement.TYPE.equals(type)) {
            return new PDStructureElement(node);
        }
        throw new IllegalArgumentException("Dictionary must not include a Type entry with a value that is neither StructTreeRoot nor StructElem.");
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSDictionary getCOSObject() {
        return this.dictionary;
    }

    public String getType() {
        return getCOSObject().getNameAsString(COSName.TYPE);
    }

    public List<Object> getKids() {
        List<Object> kidObjects = new ArrayList<>();
        COSArray k = (COSArray) getCOSObject().getDictionaryObject(COSName.K, COSArray.class);
        if (Objects.nonNull(k)) {
            Iterator<COSBase> it = k.iterator();
            while (it.hasNext()) {
                COSBase kid = it.next();
                Object kidObject = createObject(kid);
                if (kidObject != null) {
                    kidObjects.add(kidObject);
                }
            }
        } else {
            Object kidObject2 = createObject(k);
            if (kidObject2 != null) {
                kidObjects.add(kidObject2);
            }
        }
        return kidObjects;
    }

    public void setKids(List<Object> kids) {
        getCOSObject().setItem(COSName.K, (COSBase) COSArrayList.converterToCOSArray(kids));
    }

    public void appendKid(PDStructureElement structureElement) {
        appendObjectableKid(structureElement);
        structureElement.setParent(this);
    }

    protected void appendObjectableKid(COSObjectable objectable) {
        if (objectable == null) {
            return;
        }
        appendKid(objectable.getCOSObject());
    }

    protected void appendKid(COSBase object) {
        if (object == null) {
            return;
        }
        COSBase k = getCOSObject().getDictionaryObject(COSName.K);
        if (k == null) {
            getCOSObject().setItem(COSName.K, object);
            return;
        }
        if (k instanceof COSArray) {
            ((COSArray) k).add(object);
            return;
        }
        COSArray array = new COSArray();
        array.add(k);
        array.add(object);
        getCOSObject().setItem(COSName.K, (COSBase) array);
    }

    public void insertBefore(PDStructureElement newKid, Object refKid) {
        insertObjectableBefore(newKid, refKid);
    }

    protected void insertObjectableBefore(COSObjectable newKid, Object refKid) {
        if (newKid == null) {
            return;
        }
        insertBefore(newKid.getCOSObject(), refKid);
    }

    protected void insertBefore(COSBase newKid, Object refKid) {
        COSBase k;
        if (newKid == null || refKid == null || (k = getCOSObject().getDictionaryObject(COSName.K)) == null) {
            return;
        }
        COSBase refKidBase = null;
        if (refKid instanceof COSObjectable) {
            refKidBase = ((COSObjectable) refKid).getCOSObject();
        }
        if (k instanceof COSArray) {
            COSArray array = (COSArray) k;
            int refIndex = array.indexOfObject(refKidBase);
            array.add(refIndex, newKid.getCOSObject());
            return;
        }
        boolean onlyKid = k.equals(refKidBase);
        if (onlyKid) {
            COSArray array2 = new COSArray();
            array2.add(newKid);
            array2.add(refKidBase);
            getCOSObject().setItem(COSName.K, (COSBase) array2);
        }
    }

    public boolean removeKid(PDStructureElement structureElement) {
        boolean removed = removeObjectableKid(structureElement);
        if (removed) {
            structureElement.setParent(null);
        }
        return removed;
    }

    protected boolean removeObjectableKid(COSObjectable objectable) {
        if (objectable == null) {
            return false;
        }
        return removeKid(objectable.getCOSObject());
    }

    protected boolean removeKid(COSBase object) {
        COSBase k;
        if (object == null || (k = getCOSObject().getDictionaryObject(COSName.K)) == null) {
            return false;
        }
        if (k instanceof COSArray) {
            COSArray array = (COSArray) k;
            boolean removed = array.removeObject(object);
            if (array.size() == 1) {
                getCOSObject().setItem(COSName.K, array.getObject(0));
            }
            return removed;
        }
        boolean onlyKid = k.equals(object.getCOSObject());
        if (onlyKid) {
            getCOSObject().removeItem(COSName.K);
            return true;
        }
        return false;
    }

    protected Object createObject(COSBase kid) {
        COSBase direct = kid.getCOSObject();
        COSDictionary kidDic = null;
        if (direct instanceof COSDictionary) {
            kidDic = (COSDictionary) direct;
        }
        if (kidDic != null) {
            return createObjectFromDic(kidDic);
        }
        if (kid instanceof COSInteger) {
            COSInteger mcid = (COSInteger) kid;
            return Integer.valueOf(mcid.intValue());
        }
        return null;
    }

    private COSObjectable createObjectFromDic(COSDictionary kidDic) {
        String type = kidDic.getNameAsString(COSName.TYPE);
        if (type == null || PDStructureElement.TYPE.equals(type)) {
            return new PDStructureElement(kidDic);
        }
        if (PDObjectReference.TYPE.equals(type)) {
            return new PDObjectReference(kidDic);
        }
        if (PDMarkedContentReference.TYPE.equals(type)) {
            return new PDMarkedContentReference(kidDic);
        }
        return null;
    }
}
