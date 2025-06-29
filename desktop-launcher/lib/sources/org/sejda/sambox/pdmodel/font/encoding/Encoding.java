package org.sejda.sambox.pdmodel.font.encoding;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/encoding/Encoding.class */
public abstract class Encoding implements COSObjectable {
    protected static final int CHAR_CODE = 0;
    protected static final int CHAR_NAME = 1;
    protected final Map<Integer, String> codeToName = new HashMap(250);
    protected final Map<String, Integer> inverted = new HashMap(250);

    public abstract String getEncodingName();

    public static Encoding getInstance(COSName name) {
        if (COSName.STANDARD_ENCODING.equals(name)) {
            return StandardEncoding.INSTANCE;
        }
        if (COSName.WIN_ANSI_ENCODING.equals(name)) {
            return WinAnsiEncoding.INSTANCE;
        }
        if (COSName.MAC_ROMAN_ENCODING.equals(name)) {
            return MacRomanEncoding.INSTANCE;
        }
        if (COSName.MAC_EXPERT_ENCODING.equals(name)) {
            return MacExpertEncoding.INSTANCE;
        }
        return null;
    }

    public Map<Integer, String> getCodeToNameMap() {
        return Collections.unmodifiableMap(this.codeToName);
    }

    public Map<String, Integer> getNameToCodeMap() {
        return Collections.unmodifiableMap(this.inverted);
    }

    protected void add(int code2, String name) {
        this.codeToName.put(Integer.valueOf(code2), name);
        if (!this.inverted.containsKey(name)) {
            this.inverted.put(name, Integer.valueOf(code2));
        }
    }

    protected void overwrite(int code2, String name) {
        Integer oldCode;
        String oldName = this.codeToName.get(Integer.valueOf(code2));
        if (oldName != null && (oldCode = this.inverted.get(oldName)) != null && oldCode.intValue() == code2) {
            this.inverted.remove(oldName);
        }
        this.inverted.put(name, Integer.valueOf(code2));
        this.codeToName.put(Integer.valueOf(code2), name);
    }

    public boolean contains(String name) {
        return this.inverted.containsKey(name);
    }

    public boolean contains(int code2) {
        return this.codeToName.containsKey(Integer.valueOf(code2));
    }

    public String getName(int code2) {
        String name = this.codeToName.get(Integer.valueOf(code2));
        if (name != null) {
            return name;
        }
        return ".notdef";
    }
}
