package org.sejda.sambox.pdmodel.interactive.annotation;

import java.util.HashMap;
import java.util.Map;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSNull;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.pdmodel.common.COSDictionaryMap;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/annotation/PDAppearanceEntry.class */
public class PDAppearanceEntry implements COSObjectable {
    private COSBase entry;

    public PDAppearanceEntry(COSBase entry) {
        this.entry = entry;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSBase getCOSObject() {
        return this.entry;
    }

    public boolean isSubDictionary() {
        return !(this.entry instanceof COSStream) && (this.entry instanceof COSDictionary);
    }

    public boolean isStream() {
        return this.entry instanceof COSStream;
    }

    public PDAppearanceStream getAppearanceStream() {
        if (!isStream()) {
            throw new IllegalStateException("Expecting a stream, but got: " + this.entry);
        }
        return new PDAppearanceStream((COSStream) this.entry);
    }

    public Map<COSName, PDAppearanceStream> getSubDictionary() {
        if (!isSubDictionary()) {
            throw new IllegalStateException("Expecting a sub-dictionary, but got: " + this.entry);
        }
        COSDictionary dict = (COSDictionary) this.entry;
        Map<COSName, PDAppearanceStream> map = new HashMap<>();
        for (COSName name : dict.keySet()) {
            COSBase value = dict.getDictionaryObject(name);
            if (value instanceof COSStream) {
                map.put(name, new PDAppearanceStream((COSStream) value));
            } else if ((value instanceof COSNull) || value == null) {
                map.put(name, null);
            }
        }
        return new COSDictionaryMap(map, dict);
    }
}
