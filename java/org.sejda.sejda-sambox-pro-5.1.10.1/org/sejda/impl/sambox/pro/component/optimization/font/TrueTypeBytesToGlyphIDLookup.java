package org.sejda.impl.sambox.pro.component.optimization.font;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import org.apache.fontbox.ttf.TrueTypeFont;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.pdmodel.font.PDTrueTypeFont;
import org.sejda.sambox.pdmodel.font.encoding.GlyphList;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/optimization/font/TrueTypeBytesToGlyphIDLookup.class */
public class TrueTypeBytesToGlyphIDLookup implements BytesToGlyphIDLookup {
    private PDTrueTypeFont originalFont;

    public TrueTypeBytesToGlyphIDLookup(COSDictionary fontDictionary, TrueTypeFont ttf) throws IOException {
        this.originalFont = new PDTrueTypeFont(fontDictionary, ttf);
    }

    @Override // org.sejda.impl.sambox.pro.component.optimization.font.BytesToGlyphIDLookup
    public Map<Integer, Integer> convert(byte[] input) throws IOException {
        Map<Integer, Integer> gidsMapping = new HashMap<>();
        ByteBuffer buffer = ByteBuffer.wrap(input);
        while (buffer.remaining() > 0) {
            int code = Byte.toUnsignedInt(buffer.get());
            gidsMapping.put(Integer.valueOf(codeToUni(code)), Integer.valueOf(this.originalFont.codeToGID(code)));
        }
        return gidsMapping;
    }

    private int codeToUni(int code) {
        String name = this.originalFont.getEncoding().getName(code);
        if (".notdef".equals(name)) {
            return 0;
        }
        String unicode = GlyphList.getAdobeGlyphList().toUnicode(name);
        if (unicode != null) {
            return unicode.codePointAt(0);
        }
        return code;
    }
}
