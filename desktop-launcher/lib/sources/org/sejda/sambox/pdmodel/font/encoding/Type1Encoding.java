package org.sejda.sambox.pdmodel.font.encoding;

import java.util.Map;
import org.apache.fontbox.afm.CharMetric;
import org.apache.fontbox.afm.FontMetrics;
import org.sejda.sambox.cos.COSBase;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/encoding/Type1Encoding.class */
public class Type1Encoding extends Encoding {
    public static Type1Encoding fromFontBox(org.apache.fontbox.encoding.Encoding encoding) {
        Map<Integer, String> codeToName = encoding.getCodeToNameMap();
        Type1Encoding enc = new Type1Encoding();
        for (Map.Entry<Integer, String> entry : codeToName.entrySet()) {
            enc.add(entry.getKey().intValue(), entry.getValue());
        }
        return enc;
    }

    public Type1Encoding() {
    }

    public Type1Encoding(FontMetrics fontMetrics) {
        for (CharMetric nextMetric : fontMetrics.getCharMetrics()) {
            add(nextMetric.getCharacterCode(), nextMetric.getName());
        }
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSBase getCOSObject() {
        return null;
    }

    @Override // org.sejda.sambox.pdmodel.font.encoding.Encoding
    public String getEncodingName() {
        return "built-in (Type 1)";
    }
}
