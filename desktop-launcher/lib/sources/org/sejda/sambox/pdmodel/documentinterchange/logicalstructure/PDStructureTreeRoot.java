package org.sejda.sambox.pdmodel.documentinterchange.logicalstructure;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.PDStructureElementNameTreeNode;
import org.sejda.sambox.pdmodel.common.COSDictionaryMap;
import org.sejda.sambox.pdmodel.common.PDNameTreeNode;
import org.sejda.sambox.pdmodel.common.PDNumberTreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/documentinterchange/logicalstructure/PDStructureTreeRoot.class */
public class PDStructureTreeRoot extends PDStructureNode {
    private static final Logger LOG = LoggerFactory.getLogger(PDStructureTreeRoot.class);
    private static final String TYPE = "StructTreeRoot";

    public PDStructureTreeRoot() {
        super(TYPE);
    }

    public PDStructureTreeRoot(COSDictionary dic) {
        super(dic);
    }

    @Deprecated
    public COSArray getKArray() {
        COSBase k = getCOSObject().getDictionaryObject(COSName.K);
        if (k instanceof COSDictionary) {
            COSDictionary kdict = (COSDictionary) k;
            COSBase k2 = kdict.getDictionaryObject(COSName.K);
            if (k2 instanceof COSArray) {
                return (COSArray) k2;
            }
            return null;
        }
        if (k instanceof COSArray) {
            return (COSArray) k;
        }
        return null;
    }

    public COSBase getK() {
        return getCOSObject().getDictionaryObject(COSName.K);
    }

    public void setK(COSBase k) {
        getCOSObject().setItem(COSName.K, k);
    }

    public PDNameTreeNode<PDStructureElement> getIDTree() {
        COSBase base = getCOSObject().getDictionaryObject(COSName.ID_TREE);
        if (base instanceof COSDictionary) {
            return new PDStructureElementNameTreeNode((COSDictionary) base);
        }
        return null;
    }

    public void setIDTree(PDNameTreeNode<PDStructureElement> idTree) {
        getCOSObject().setItem(COSName.ID_TREE, idTree);
    }

    public PDNumberTreeNode getParentTree() {
        COSBase base = getCOSObject().getDictionaryObject(COSName.PARENT_TREE);
        if (base instanceof COSDictionary) {
            return new PDNumberTreeNode((COSDictionary) base, PDParentTreeValue.class);
        }
        return null;
    }

    public void setParentTree(PDNumberTreeNode parentTree) {
        getCOSObject().setItem(COSName.PARENT_TREE, parentTree);
    }

    public int getParentTreeNextKey() {
        return getCOSObject().getInt(COSName.PARENT_TREE_NEXT_KEY);
    }

    public void setParentTreeNextKey(int parentTreeNextkey) {
        getCOSObject().setInt(COSName.PARENT_TREE_NEXT_KEY, parentTreeNextkey);
    }

    public Map<String, Object> getRoleMap() {
        COSBase rm = getCOSObject().getDictionaryObject(COSName.ROLE_MAP);
        if (rm instanceof COSDictionary) {
            try {
                return COSDictionaryMap.convertBasicTypesToMap((COSDictionary) rm);
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return new HashMap();
    }

    public void setRoleMap(Map<String, String> roleMap) {
        COSDictionary rmDic = new COSDictionary();
        for (Map.Entry<String, String> entry : roleMap.entrySet()) {
            rmDic.setName(entry.getKey(), entry.getValue());
        }
        getCOSObject().setItem(COSName.ROLE_MAP, (COSBase) rmDic);
    }
}
