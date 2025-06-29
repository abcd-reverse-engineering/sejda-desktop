package org.sejda.sambox.pdmodel.font.encoding;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSNumber;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/encoding/DictionaryEncoding.class */
public class DictionaryEncoding extends Encoding {
    private final COSDictionary encoding;
    private final Encoding baseEncoding;
    private final Map<Integer, String> differences;

    public DictionaryEncoding(COSName baseEncoding, COSArray differences) {
        this.differences = new HashMap();
        this.encoding = new COSDictionary();
        this.encoding.setItem(COSName.NAME, (COSBase) COSName.ENCODING);
        this.encoding.setItem(COSName.DIFFERENCES, (COSBase) differences);
        if (baseEncoding != COSName.STANDARD_ENCODING) {
            this.encoding.setItem(COSName.BASE_ENCODING, (COSBase) baseEncoding);
            this.baseEncoding = Encoding.getInstance(baseEncoding);
        } else {
            this.baseEncoding = Encoding.getInstance(baseEncoding);
        }
        if (this.baseEncoding == null) {
            throw new IllegalArgumentException("Invalid encoding: " + baseEncoding);
        }
        this.codeToName.putAll(this.baseEncoding.codeToName);
        this.inverted.putAll(this.baseEncoding.inverted);
        applyDifferences();
    }

    public DictionaryEncoding(COSDictionary fontEncoding) {
        this.differences = new HashMap();
        this.encoding = fontEncoding;
        this.baseEncoding = null;
        applyDifferences();
    }

    public DictionaryEncoding(COSDictionary fontEncoding, boolean isNonSymbolic, Encoding builtIn) {
        this.differences = new HashMap();
        this.encoding = fontEncoding;
        Encoding base = null;
        boolean hasBaseEncoding = this.encoding.containsKey(COSName.BASE_ENCODING);
        if (hasBaseEncoding) {
            COSName name = this.encoding.getCOSName(COSName.BASE_ENCODING);
            base = Encoding.getInstance(name);
        }
        if (base == null) {
            if (isNonSymbolic) {
                base = StandardEncoding.INSTANCE;
            } else if (builtIn != null) {
                base = builtIn;
            } else {
                throw new IllegalArgumentException("Symbolic fonts must have a built-in encoding");
            }
        }
        this.baseEncoding = base;
        this.codeToName.putAll(this.baseEncoding.codeToName);
        this.inverted.putAll(this.baseEncoding.inverted);
        applyDifferences();
    }

    private void applyDifferences() {
        COSArray differences = (COSArray) Optional.ofNullable(this.encoding).map(e -> {
            return (COSArray) e.getDictionaryObject(COSName.DIFFERENCES, COSArray.class);
        }).orElse(null);
        if (Objects.nonNull(differences)) {
            int currentIndex = -1;
            for (int i = 0; i < differences.size(); i++) {
                COSBase next = differences.getObject(i);
                if (next instanceof COSNumber) {
                    currentIndex = ((COSNumber) next).intValue();
                } else if (next instanceof COSName) {
                    COSName name = (COSName) next;
                    overwrite(currentIndex, name.getName());
                    this.differences.put(Integer.valueOf(currentIndex), name.getName());
                    currentIndex++;
                }
            }
        }
    }

    public Encoding getBaseEncoding() {
        return this.baseEncoding;
    }

    public Map<Integer, String> getDifferences() {
        return this.differences;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSBase getCOSObject() {
        return this.encoding;
    }

    @Override // org.sejda.sambox.pdmodel.font.encoding.Encoding
    public String getEncodingName() {
        if (this.baseEncoding == null) {
            return "differences";
        }
        return this.baseEncoding.getEncodingName() + " with differences";
    }
}
