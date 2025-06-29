package org.sejda.sambox.xref;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeSet;
import org.sejda.sambox.cos.COSObjectKey;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/xref/Xref.class */
public class Xref {
    private HashMap<COSObjectKey, XrefEntry> data = new HashMap<>();

    public XrefEntry addIfAbsent(XrefEntry entry) {
        return this.data.putIfAbsent(entry.key(), entry);
    }

    public XrefEntry add(XrefEntry entry) {
        return this.data.put(entry.key(), entry);
    }

    public XrefEntry get(COSObjectKey objectKey) {
        return this.data.get(objectKey);
    }

    public Collection<XrefEntry> values() {
        return Collections.unmodifiableCollection(this.data.values());
    }

    public COSObjectKey highestKey() {
        return (COSObjectKey) new TreeSet(this.data.keySet()).last();
    }

    public boolean contains(COSObjectKey objectKey) {
        return this.data.containsKey(objectKey);
    }
}
