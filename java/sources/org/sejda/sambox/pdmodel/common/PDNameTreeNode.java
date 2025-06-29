package org.sejda.sambox.pdmodel.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSArrayList;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.cos.COSString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/common/PDNameTreeNode.class */
public abstract class PDNameTreeNode<T extends COSObjectable> implements COSObjectable {
    private static final Logger LOG = LoggerFactory.getLogger(PDNameTreeNode.class);
    private final COSDictionary node;
    private PDNameTreeNode<T> parent;

    protected abstract T convertCOSToPD(COSBase cOSBase) throws IOException;

    protected abstract PDNameTreeNode<T> createChildNode(COSDictionary cOSDictionary);

    protected PDNameTreeNode() {
        this.node = new COSDictionary();
    }

    protected PDNameTreeNode(COSDictionary dict) {
        this.node = dict;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSDictionary getCOSObject() {
        return this.node;
    }

    public PDNameTreeNode<T> getParent() {
        return this.parent;
    }

    public void setParent(PDNameTreeNode<T> parentNode) {
        this.parent = parentNode;
        calculateLimits();
    }

    public boolean isRootNode() {
        return this.parent == null;
    }

    public List<PDNameTreeNode<T>> getKids() {
        COSArray kids;
        if (Objects.nonNull(this.node) && (kids = (COSArray) this.node.getDictionaryObject(COSName.KIDS, COSArray.class)) != null) {
            List<PDNameTreeNode<T>> pdObjects = new ArrayList<>();
            for (int i = 0; i < kids.size(); i++) {
                pdObjects.add(createChildNode((COSDictionary) kids.getObject(i)));
            }
            return new COSArrayList(pdObjects, kids);
        }
        return null;
    }

    public void setKids(List<? extends PDNameTreeNode<T>> kids) {
        if (kids != null && !kids.isEmpty()) {
            for (PDNameTreeNode<T> kidsNode : kids) {
                kidsNode.setParent(this);
            }
            this.node.setItem(COSName.KIDS, (COSBase) COSArrayList.converterToCOSArray(kids));
            if (isRootNode()) {
                this.node.removeItem(COSName.NAMES);
            }
        } else {
            this.node.removeItem(COSName.KIDS);
            this.node.removeItem(COSName.LIMITS);
        }
        calculateLimits();
    }

    private void calculateLimits() {
        if (isRootNode()) {
            this.node.removeItem(COSName.LIMITS);
            return;
        }
        List<PDNameTreeNode<T>> kids = getKids();
        if (kids != null && !kids.isEmpty()) {
            PDNameTreeNode<T> firstKid = kids.get(0);
            PDNameTreeNode<T> lastKid = kids.get(kids.size() - 1);
            String lowerLimit = firstKid.getLowerLimit();
            setLowerLimit(lowerLimit);
            String upperLimit = lastKid.getUpperLimit();
            setUpperLimit(upperLimit);
            return;
        }
        try {
            Map<String, T> names = getNames();
            if (names != null && names.size() > 0) {
                Set<String> strings = names.keySet();
                String[] keys = (String[]) strings.toArray(new String[0]);
                String lowerLimit2 = keys[0];
                setLowerLimit(lowerLimit2);
                String upperLimit2 = keys[keys.length - 1];
                setUpperLimit(upperLimit2);
            } else {
                this.node.removeItem(COSName.LIMITS);
            }
        } catch (IOException exception) {
            this.node.removeItem(COSName.LIMITS);
            LOG.error("Error while calculating the Limits of a PageNameTreeNode:", exception);
        }
    }

    public T getValue(String str) {
        try {
            Map<String, T> names = getNames();
            if (Objects.isNull(names)) {
                List<PDNameTreeNode<T>> kids = getKids();
                if (kids != null) {
                    for (PDNameTreeNode<T> pDNameTreeNode : kids) {
                        if (pDNameTreeNode.couldContain(str)) {
                            T t = (T) pDNameTreeNode.getValue(str);
                            if (Objects.nonNull(t)) {
                                return t;
                            }
                        }
                    }
                } else {
                    LOG.warn("NameTreeNode does not have \"names\" nor \"kids\" objects.");
                }
                return null;
            }
            return names.get(str);
        } catch (IOException | ClassCastException e) {
            LOG.warn("NameTreeNode couldn't get the names map", e);
            return null;
        }
    }

    private boolean couldContain(String name) {
        if (!Objects.isNull(this.node) && !Objects.isNull(getLowerLimit()) && !Objects.isNull(getUpperLimit())) {
            return getLowerLimit().compareTo(name) <= 0 && getUpperLimit().compareTo(name) >= 0;
        }
        LOG.warn("Missing required name tree node Limits array");
        return false;
    }

    public Map<String, T> getNames() throws IOException {
        COSArray namesArray = (COSArray) this.node.getDictionaryObject(COSName.NAMES, COSArray.class);
        if (namesArray != null) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            if (namesArray.size() % 2 != 0) {
                LOG.warn("Names array has odd size: {}", Integer.valueOf(namesArray.size()));
            }
            for (int i = 0; i < namesArray.size(); i += 2) {
                COSBase base = namesArray.getObject(i);
                if (i + 1 >= namesArray.size()) {
                    LOG.warn("Found key without value in NAMES array: {}, at index: {}", base, Integer.valueOf(i));
                } else {
                    COSString key = (COSString) base;
                    COSBase cosValue = namesArray.getObject(i + 1);
                    COSObjectable cOSObjectableConvertCOSToPD = null;
                    try {
                        cOSObjectableConvertCOSToPD = convertCOSToPD(cosValue);
                    } catch (ClassCastException ex) {
                        LOG.warn("Skipping, could not convert COS to PD: " + cosValue, ex);
                    }
                    linkedHashMap.put(key.getString(), cOSObjectableConvertCOSToPD);
                }
            }
            return Collections.unmodifiableMap(linkedHashMap);
        }
        return null;
    }

    public void setNames(Map<String, T> names) {
        if (names == null) {
            this.node.setItem(COSName.NAMES, (COSObjectable) null);
            this.node.setItem(COSName.LIMITS, (COSObjectable) null);
            return;
        }
        COSArray array = new COSArray();
        List<String> keys = new ArrayList<>(names.keySet());
        Collections.sort(keys);
        for (String key : keys) {
            array.add((COSBase) COSString.parseLiteral(key));
            array.add((COSObjectable) names.get(key));
        }
        this.node.setItem(COSName.NAMES, (COSBase) array);
        calculateLimits();
    }

    public String getUpperLimit() {
        return (String) Optional.ofNullable((COSArray) this.node.getDictionaryObject(COSName.LIMITS, COSArray.class)).map(a -> {
            return a.getString(1);
        }).orElse(null);
    }

    private void setUpperLimit(String upper) {
        COSArray arr = (COSArray) this.node.getDictionaryObject(COSName.LIMITS, COSArray.class);
        if (arr == null) {
            arr = new COSArray(null, null);
            this.node.setItem(COSName.LIMITS, (COSBase) arr);
        }
        arr.setString(1, upper);
    }

    public String getLowerLimit() {
        return (String) Optional.ofNullable((COSArray) this.node.getDictionaryObject(COSName.LIMITS, COSArray.class)).map(a -> {
            return a.getString(0);
        }).orElse(null);
    }

    private void setLowerLimit(String lower) {
        COSArray arr = (COSArray) this.node.getDictionaryObject(COSName.LIMITS, COSArray.class);
        if (arr == null) {
            arr = new COSArray(null, null);
            this.node.setItem(COSName.LIMITS, (COSBase) arr);
        }
        arr.setString(0, lower);
    }
}
