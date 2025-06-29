package org.sejda.sambox.pdmodel.graphics.optionalcontent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.pdmodel.common.PDDictionaryWrapper;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/optionalcontent/PDOptionalContentProperties.class */
public class PDOptionalContentProperties extends PDDictionaryWrapper {

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/optionalcontent/PDOptionalContentProperties$BaseState.class */
    public enum BaseState {
        ON(COSName.ON),
        OFF(COSName.OFF),
        UNCHANGED(COSName.UNCHANGED);

        private final COSName name;

        BaseState(COSName value) {
            this.name = value;
        }

        public COSName getName() {
            return this.name;
        }

        public static BaseState valueOf(COSName state) {
            if (state == null) {
                return ON;
            }
            return valueOf(state.getName().toUpperCase());
        }
    }

    public PDOptionalContentProperties() {
        getCOSObject().setItem(COSName.OCGS, (COSBase) new COSArray());
        COSDictionary d = new COSDictionary();
        d.setString(COSName.NAME, "Top");
        getCOSObject().setItem(COSName.D, (COSBase) d);
    }

    public PDOptionalContentProperties(COSDictionary props) {
        super(props);
    }

    private COSArray getOCGs() {
        COSArray ocgs = (COSArray) getCOSObject().getDictionaryObject(COSName.OCGS, COSArray.class);
        if (ocgs == null) {
            ocgs = new COSArray();
            getCOSObject().setItem(COSName.OCGS, (COSBase) ocgs);
        }
        return ocgs;
    }

    private COSDictionary getD() {
        COSDictionary d = (COSDictionary) getCOSObject().getDictionaryObject(COSName.D, COSDictionary.class);
        if (Objects.isNull(d)) {
            d = new COSDictionary();
            d.setString(COSName.NAME, "Top");
            getCOSObject().setItem(COSName.D, (COSBase) d);
        }
        return d;
    }

    public PDOptionalContentGroup getGroup(String name) {
        COSArray ocgs = getOCGs();
        Iterator<COSBase> it = ocgs.iterator();
        while (it.hasNext()) {
            COSBase o = it.next();
            COSDictionary ocg = toDictionary(o);
            String groupName = ocg.getString(COSName.NAME);
            if (groupName.equals(name)) {
                return new PDOptionalContentGroup(ocg);
            }
        }
        return null;
    }

    public void addGroup(PDOptionalContentGroup ocg) {
        COSArray ocgs = getOCGs();
        ocgs.add((COSBase) ocg.getCOSObject());
        COSArray order = (COSArray) getD().getDictionaryObject(COSName.ORDER);
        if (order == null) {
            order = new COSArray();
            getD().setItem(COSName.ORDER, (COSBase) order);
        }
        order.add((COSObjectable) ocg);
    }

    public Collection<PDOptionalContentGroup> getOptionalContentGroups() {
        Collection<PDOptionalContentGroup> coll = new ArrayList<>();
        COSArray ocgs = getOCGs();
        Iterator<COSBase> it = ocgs.iterator();
        while (it.hasNext()) {
            COSBase base = it.next();
            coll.add(new PDOptionalContentGroup(toDictionary(base)));
        }
        return coll;
    }

    public BaseState getBaseState() {
        COSDictionary d = getD();
        COSName name = (COSName) d.getItem(COSName.BASE_STATE);
        return BaseState.valueOf(name);
    }

    public void setBaseState(BaseState state) {
        COSDictionary d = getD();
        d.setItem(COSName.BASE_STATE, (COSBase) state.getName());
    }

    public String[] getGroupNames() {
        COSArray ocgs = (COSArray) getCOSObject().getDictionaryObject(COSName.OCGS, COSArray.class);
        if (ocgs == null) {
            return new String[0];
        }
        int size = ocgs.size();
        String[] groups = new String[size];
        for (int i = 0; i < size; i++) {
            COSBase obj = ocgs.get(i);
            COSDictionary ocg = toDictionary(obj);
            groups[i] = ocg.getString(COSName.NAME);
        }
        return groups;
    }

    public boolean hasGroup(String groupName) {
        String[] layers = getGroupNames();
        for (String layer : layers) {
            if (layer.equals(groupName)) {
                return true;
            }
        }
        return false;
    }

    public boolean isGroupEnabled(String groupName) {
        boolean result = false;
        COSArray ocgs = getOCGs();
        Iterator<COSBase> it = ocgs.iterator();
        while (it.hasNext()) {
            COSBase o = it.next();
            COSDictionary ocg = toDictionary(o);
            String name = ocg.getString(COSName.NAME);
            if (groupName.equals(name) && isGroupEnabled(new PDOptionalContentGroup(ocg))) {
                result = true;
            }
        }
        return result;
    }

    public boolean isGroupEnabled(PDOptionalContentGroup group) {
        BaseState baseState = getBaseState();
        boolean enabled = !baseState.equals(BaseState.OFF);
        if (group == null) {
            return enabled;
        }
        COSDictionary d = getD();
        COSBase base = d.getDictionaryObject(COSName.ON);
        if (base instanceof COSArray) {
            Iterator<COSBase> it = ((COSArray) base).iterator();
            while (it.hasNext()) {
                COSBase o = it.next();
                COSDictionary dictionary = toDictionary(o);
                if (dictionary == group.getCOSObject()) {
                    return true;
                }
            }
        }
        COSBase base2 = d.getDictionaryObject(COSName.OFF);
        if (base2 instanceof COSArray) {
            Iterator<COSBase> it2 = ((COSArray) base2).iterator();
            while (it2.hasNext()) {
                COSBase o2 = it2.next();
                COSDictionary dictionary2 = toDictionary(o2);
                if (dictionary2 == group.getCOSObject()) {
                    return false;
                }
            }
        }
        return enabled;
    }

    private COSDictionary toDictionary(COSBase o) {
        return (COSDictionary) o.getCOSObject();
    }

    public boolean setGroupEnabled(String groupName, boolean enable) {
        boolean result = false;
        COSArray ocgs = getOCGs();
        Iterator<COSBase> it = ocgs.iterator();
        while (it.hasNext()) {
            COSBase o = it.next();
            COSDictionary ocg = toDictionary(o);
            String name = ocg.getString(COSName.NAME);
            if (groupName.equals(name) && setGroupEnabled(new PDOptionalContentGroup(ocg), enable)) {
                result = true;
            }
        }
        return result;
    }

    public boolean setGroupEnabled(PDOptionalContentGroup group, boolean enable) {
        COSArray on;
        COSArray off;
        COSDictionary d = getD();
        COSBase base = d.getDictionaryObject(COSName.ON);
        if (!(base instanceof COSArray)) {
            on = new COSArray();
            d.setItem(COSName.ON, (COSBase) on);
        } else {
            on = (COSArray) base;
        }
        COSBase base2 = d.getDictionaryObject(COSName.OFF);
        if (!(base2 instanceof COSArray)) {
            off = new COSArray();
            d.setItem(COSName.OFF, (COSBase) off);
        } else {
            off = (COSArray) base2;
        }
        boolean found = false;
        if (enable) {
            Iterator<COSBase> it = off.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                COSBase o = it.next();
                COSDictionary groupDictionary = toDictionary(o);
                if (groupDictionary == group.getCOSObject()) {
                    off.remove(o);
                    on.add(o);
                    found = true;
                    break;
                }
            }
        } else {
            Iterator<COSBase> it2 = on.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                COSBase o2 = it2.next();
                COSDictionary groupDictionary2 = toDictionary(o2);
                if (groupDictionary2 == group.getCOSObject()) {
                    on.remove(o2);
                    off.add(o2);
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            if (enable) {
                on.add((COSBase) group.getCOSObject());
            } else {
                off.add((COSBase) group.getCOSObject());
            }
        }
        return found;
    }
}
