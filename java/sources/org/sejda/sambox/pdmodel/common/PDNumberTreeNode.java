package org.sejda.sambox.pdmodel.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSArrayList;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSInteger;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSNull;
import org.sejda.sambox.cos.COSObjectable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/common/PDNumberTreeNode.class */
public class PDNumberTreeNode implements COSObjectable {
    private static final Logger LOG = LoggerFactory.getLogger(PDNumberTreeNode.class);
    private final COSDictionary node;
    private Class<? extends COSObjectable> valueType;

    public PDNumberTreeNode(Class<? extends COSObjectable> valueClass) {
        this.valueType = null;
        this.node = new COSDictionary();
        this.valueType = valueClass;
    }

    public PDNumberTreeNode(COSDictionary dict, Class<? extends COSObjectable> valueClass) {
        this.valueType = null;
        this.node = dict;
        this.valueType = valueClass;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSDictionary getCOSObject() {
        return this.node;
    }

    public List<PDNumberTreeNode> getKids() {
        COSArray kids = (COSArray) this.node.getDictionaryObject(COSName.KIDS, COSArray.class);
        if (Objects.nonNull(kids)) {
            List<PDNumberTreeNode> pdObjects = new ArrayList<>();
            for (int i = 0; i < kids.size(); i++) {
                pdObjects.add(createChildNode((COSDictionary) kids.getObject(i)));
            }
            return new COSArrayList(pdObjects, kids);
        }
        return null;
    }

    public void setKids(List<? extends PDNumberTreeNode> kids) {
        if (kids != null && !kids.isEmpty()) {
            PDNumberTreeNode firstKid = kids.get(0);
            PDNumberTreeNode lastKid = kids.get(kids.size() - 1);
            Integer lowerLimit = firstKid.getLowerLimit();
            setLowerLimit(lowerLimit);
            Integer upperLimit = lastKid.getUpperLimit();
            setUpperLimit(upperLimit);
        } else if (this.node.getDictionaryObject(COSName.NUMS) == null) {
            this.node.removeItem(COSName.LIMITS);
        }
        this.node.setItem(COSName.KIDS, (COSBase) COSArrayList.converterToCOSArray(kids));
    }

    public Object getValue(Integer index) throws IOException {
        Map<Integer, COSObjectable> numbers = getNumbers();
        if (Objects.nonNull(numbers)) {
            return numbers.get(index);
        }
        Object retval = null;
        List<PDNumberTreeNode> kids = getKids();
        if (Objects.nonNull(kids)) {
            for (int i = 0; i < kids.size() && retval == null; i++) {
                PDNumberTreeNode childNode = kids.get(i);
                if (childNode.getLowerLimit().compareTo(index) <= 0 && childNode.getUpperLimit().compareTo(index) >= 0) {
                    retval = childNode.getValue(index);
                }
            }
        } else {
            LOG.warn("NumberTreeNode does not have \"nums\" nor \"kids\" objects.");
        }
        return retval;
    }

    public Map<Integer, COSObjectable> getNumbers() throws IOException {
        Map<Integer, COSObjectable> indices = null;
        COSArray numbersArray = (COSArray) this.node.getDictionaryObject(COSName.NUMS, COSArray.class);
        if (Objects.nonNull(numbersArray)) {
            Map<Integer, COSObjectable> indices2 = new HashMap<>();
            if (numbersArray.size() % 2 != 0) {
                LOG.warn("Numbers array has odd size: " + numbersArray.size());
            }
            for (int i = 0; i + 1 < numbersArray.size(); i += 2) {
                COSBase base = numbersArray.getObject(i);
                if (!(base instanceof COSInteger)) {
                    LOG.error("page labels ignored, index {} should be a number, but is {}", Integer.valueOf(i), base);
                    return null;
                }
                COSInteger key = (COSInteger) base;
                COSBase cosValue = numbersArray.getObject(i + 1);
                indices2.put(Integer.valueOf(key.intValue()), cosValue == null ? null : convertCOSToPD(cosValue));
            }
            indices = Collections.unmodifiableMap(indices2);
        }
        return indices;
    }

    protected COSObjectable convertCOSToPD(COSBase base) throws IOException {
        try {
            return this.valueType.getConstructor(base.getClass()).newInstance(base);
        } catch (Throwable t) {
            throw new IOException("Error while trying to create value in number tree", t);
        }
    }

    protected PDNumberTreeNode createChildNode(COSDictionary dic) {
        return new PDNumberTreeNode(dic, this.valueType);
    }

    public void setNumbers(Map<Integer, ? extends COSObjectable> numbers) {
        if (numbers == null) {
            this.node.setItem(COSName.NUMS, (COSObjectable) null);
            this.node.setItem(COSName.LIMITS, (COSObjectable) null);
            return;
        }
        List<Integer> keys = new ArrayList<>(numbers.keySet());
        Collections.sort(keys);
        COSArray array = new COSArray();
        for (Integer key : keys) {
            array.add((COSBase) COSInteger.get(key.intValue()));
            COSObjectable obj = numbers.get(key);
            array.add(obj == null ? COSNull.NULL : obj);
        }
        Integer lower = null;
        Integer upper = null;
        if (!keys.isEmpty()) {
            lower = keys.get(0);
            upper = keys.get(keys.size() - 1);
        }
        setUpperLimit(upper);
        setLowerLimit(lower);
        this.node.setItem(COSName.NUMS, (COSBase) array);
    }

    public Integer getUpperLimit() {
        COSArray arr = (COSArray) this.node.getDictionaryObject(COSName.LIMITS, COSArray.class);
        if (arr != null && arr.get(1) != null) {
            return Integer.valueOf(arr.getInt(1));
        }
        return null;
    }

    private void setUpperLimit(Integer upper) {
        COSArray arr = (COSArray) this.node.getDictionaryObject(COSName.LIMITS, COSArray.class);
        if (arr == null) {
            arr = new COSArray();
            arr.add((COSBase) null);
            arr.add((COSBase) null);
            this.node.setItem(COSName.LIMITS, (COSBase) arr);
        }
        if (upper != null) {
            arr.set(1, (COSBase) COSInteger.get(upper.intValue()));
        } else {
            arr.set(1, (COSBase) null);
        }
    }

    public Integer getLowerLimit() {
        COSArray arr = (COSArray) this.node.getDictionaryObject(COSName.LIMITS, COSArray.class);
        if (arr != null && arr.get(0) != null) {
            return Integer.valueOf(arr.getInt(0));
        }
        return null;
    }

    private void setLowerLimit(Integer lower) {
        COSArray arr = (COSArray) this.node.getDictionaryObject(COSName.LIMITS, COSArray.class);
        if (arr == null) {
            arr = new COSArray();
            arr.add((COSBase) null);
            arr.add((COSBase) null);
            this.node.setItem(COSName.LIMITS, (COSBase) arr);
        }
        if (lower != null) {
            arr.set(0, (COSBase) COSInteger.get(lower.intValue()));
        } else {
            arr.set(0, (COSBase) null);
        }
    }
}
