package org.sejda.impl.sambox.pro.component.optimization.font;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import org.apache.fontbox.cmap.CMap;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.pdmodel.font.CMapManager;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/optimization/font/CIDType2BytesToGlyphIdLookup.class */
public class CIDType2BytesToGlyphIdLookup implements BytesToGlyphIDLookup {
    private final CMap cMap;
    private final Function<Integer, Integer> codeToCid;
    private final Function<Integer, Integer> cidToGid;

    public CIDType2BytesToGlyphIdLookup(COSDictionary type0Parent, COSDictionary descendant) throws IOException {
        COSBase encoding = (COSBase) Objects.requireNonNull(type0Parent.getDictionaryObject(COSName.ENCODING));
        this.cMap = (CMap) Objects.requireNonNull(getCMap(encoding));
        this.cidToGid = cidToGidFunction(descendant);
        this.codeToCid = codeToCID(this.cMap);
    }

    @Override // org.sejda.impl.sambox.pro.component.optimization.font.BytesToGlyphIDLookup
    public Map<Integer, Integer> convert(byte[] input) throws IOException {
        Map<Integer, Integer> gidsMapping = new HashMap<>();
        ByteArrayInputStream in = new ByteArrayInputStream(input);
        while (in.available() > 0) {
            try {
                int code2 = this.cMap.readCode(in);
                gidsMapping.put(Integer.valueOf(code2), this.cidToGid.apply(this.codeToCid.apply(Integer.valueOf(code2))));
            } catch (Throwable th) {
                try {
                    in.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        in.close();
        return gidsMapping;
    }

    private static CMap getCMap(COSBase encoding) throws IOException {
        if (encoding instanceof COSName) {
            COSName name = (COSName) encoding;
            return CMapManager.getPredefinedCMap(name.getName());
        }
        if (encoding instanceof COSStream) {
            COSStream stream = (COSStream) encoding;
            InputStream input = stream.getUnfilteredStream();
            try {
                CMap cMap = CMapManager.parseCMap(input);
                if (input != null) {
                    input.close();
                }
                return cMap;
            } catch (Throwable th) {
                if (input != null) {
                    try {
                        input.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        return null;
    }

    public Function<Integer, Integer> codeToCID(CMap cMap) {
        return code2 -> {
            String unicode;
            if (!cMap.hasCIDMappings() && cMap.hasUnicodeMappings() && (unicode = cMap.toUnicode(code2.intValue())) != null) {
                return Integer.valueOf(unicode.codePointAt(0));
            }
            return Integer.valueOf(cMap.toCID(code2.intValue()));
        };
    }

    final Function<Integer, Integer> cidToGidFunction(COSDictionary descendant) throws IOException {
        COSStream map = (COSStream) descendant.getDictionaryObject(COSName.CID_TO_GID_MAP, COSStream.class);
        if (Objects.nonNull(map)) {
            ShortBuffer shorts = map.getUnfilteredByteBuffer().asShortBuffer();
            ArrayList<Integer> mapping = new ArrayList<>(shorts.remaining());
            while (shorts.hasRemaining()) {
                mapping.add(Integer.valueOf(shorts.get()));
            }
            return c -> {
                if (c.intValue() < mapping.size()) {
                    return (Integer) mapping.get(c.intValue());
                }
                return 0;
            };
        }
        return Function.identity();
    }
}
