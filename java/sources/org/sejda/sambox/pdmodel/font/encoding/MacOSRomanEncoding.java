package org.sejda.sambox.pdmodel.font.encoding;

import org.sejda.sambox.cos.COSName;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/encoding/MacOSRomanEncoding.class */
public class MacOSRomanEncoding extends MacRomanEncoding {
    private static final Object[][] MAC_OS_ROMAN_ENCODING_TABLE = {new Object[]{255, "notequal"}, new Object[]{260, "infinity"}, new Object[]{262, "lessequal"}, new Object[]{263, "greaterequal"}, new Object[]{266, "partialdiff"}, new Object[]{267, "summation"}, new Object[]{270, "product"}, new Object[]{271, "pi"}, new Object[]{272, "integral"}, new Object[]{275, "Omega"}, new Object[]{303, "radical"}, new Object[]{305, "approxequal"}, new Object[]{306, "Delta"}, new Object[]{327, "lozenge"}, new Object[]{333, "Euro"}, new Object[]{360, "apple"}};
    public static final MacOSRomanEncoding INSTANCE = new MacOSRomanEncoding();

    public MacOSRomanEncoding() {
        for (Object[] encodingEntry : MAC_OS_ROMAN_ENCODING_TABLE) {
            add(((Integer) encodingEntry[0]).intValue(), encodingEntry[1].toString());
        }
    }

    @Override // org.sejda.sambox.pdmodel.font.encoding.MacRomanEncoding, org.sejda.sambox.cos.COSObjectable
    public COSName getCOSObject() {
        return null;
    }
}
