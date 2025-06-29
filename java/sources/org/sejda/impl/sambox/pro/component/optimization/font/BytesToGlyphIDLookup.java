package org.sejda.impl.sambox.pro.component.optimization.font;

import java.io.IOException;
import java.util.Map;

@FunctionalInterface
/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/optimization/font/BytesToGlyphIDLookup.class */
interface BytesToGlyphIDLookup {
    Map<Integer, Integer> convert(byte[] bArr) throws IOException;
}
