package org.sejda.sambox.pdmodel.interactive.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSString;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/form/FieldUtils.class */
public final class FieldUtils {

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/form/FieldUtils$KeyValue.class */
    static class KeyValue {
        private final String key;
        private final String value;

        public KeyValue(String theKey, String theValue) {
            this.key = theKey;
            this.value = theValue;
        }

        public String getKey() {
            return this.key;
        }

        public String getValue() {
            return this.value;
        }

        public String toString() {
            return "(" + this.key + ", " + this.value + ")";
        }
    }

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/form/FieldUtils$KeyValueKeyComparator.class */
    static class KeyValueKeyComparator implements Serializable, Comparator<KeyValue> {
        private static final long serialVersionUID = 6715364290007167694L;

        KeyValueKeyComparator() {
        }

        @Override // java.util.Comparator
        public int compare(KeyValue o1, KeyValue o2) {
            return o1.key.compareTo(o2.key);
        }
    }

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/form/FieldUtils$KeyValueValueComparator.class */
    static class KeyValueValueComparator implements Serializable, Comparator<KeyValue> {
        private static final long serialVersionUID = -3984095679894798265L;

        KeyValueValueComparator() {
        }

        @Override // java.util.Comparator
        public int compare(KeyValue o1, KeyValue o2) {
            return o1.value.compareTo(o2.value);
        }
    }

    private FieldUtils() {
    }

    static List<KeyValue> toKeyValueList(List<String> key, List<String> value) {
        List<KeyValue> list = new ArrayList<>();
        for (int i = 0; i < key.size(); i++) {
            list.add(new KeyValue(key.get(i), value.get(i)));
        }
        return list;
    }

    static void sortByValue(List<KeyValue> pairs) {
        pairs.sort(new KeyValueValueComparator());
    }

    static void sortByKey(List<KeyValue> pairs) {
        pairs.sort(new KeyValueKeyComparator());
    }

    static List<String> getPairableItems(COSBase items, int pairIdx) {
        if (pairIdx < 0 || pairIdx > 1) {
            throw new IllegalArgumentException("Only 0 and 1 are allowed as an index into two-element arrays");
        }
        if (items instanceof COSString) {
            List<String> array = new ArrayList<>();
            array.add(((COSString) items).getString());
            return array;
        }
        if (items instanceof COSArray) {
            List<String> entryList = new ArrayList<>();
            Iterator<COSBase> it = ((COSArray) items).iterator();
            while (it.hasNext()) {
                COSBase entry = it.next();
                if (entry instanceof COSString) {
                    entryList.add(((COSString) entry).getString());
                } else if (entry instanceof COSArray) {
                    COSArray cosArray = (COSArray) entry;
                    if (cosArray.size() >= pairIdx + 1 && (cosArray.get(pairIdx) instanceof COSString)) {
                        entryList.add(((COSString) cosArray.get(pairIdx)).getString());
                    }
                }
            }
            return entryList;
        }
        return Collections.emptyList();
    }
}
