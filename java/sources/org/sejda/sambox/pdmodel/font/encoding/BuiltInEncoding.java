package org.sejda.sambox.pdmodel.font.encoding;

import java.util.Map;
import org.sejda.sambox.cos.COSBase;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/encoding/BuiltInEncoding.class */
public class BuiltInEncoding extends Encoding {
    public BuiltInEncoding(Map<Integer, String> codeToName) {
        for (Map.Entry<Integer, String> entry : codeToName.entrySet()) {
            add(entry.getKey().intValue(), entry.getValue());
        }
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSBase getCOSObject() {
        throw new UnsupportedOperationException("Built-in encodings cannot be serialized");
    }

    @Override // org.sejda.sambox.pdmodel.font.encoding.Encoding
    public String getEncodingName() {
        return "built-in (TTF)";
    }
}
