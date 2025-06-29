package org.sejda.sambox.rendering;

import java.awt.geom.GeneralPath;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.sejda.sambox.pdmodel.font.PDCIDFontType0;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/rendering/CIDType0Glyph2D.class */
final class CIDType0Glyph2D implements Glyph2D {
    private static final Logger LOG = LoggerFactory.getLogger(CIDType0Glyph2D.class);
    private final Map<Integer, GeneralPath> cache = new HashMap();
    private final PDCIDFontType0 font;
    private final String fontName;

    CIDType0Glyph2D(PDCIDFontType0 font) {
        this.font = font;
        this.fontName = font.getBaseFont();
    }

    @Override // org.sejda.sambox.rendering.Glyph2D
    public GeneralPath getPathForCharacterCode(int code2) {
        GeneralPath path = this.cache.get(Integer.valueOf(code2));
        if (path == null) {
            try {
                if (!this.font.hasGlyph(code2)) {
                    int cid = this.font.getParent().codeToCID(code2);
                    String cidHex = String.format("%04x", Integer.valueOf(cid));
                    LOG.warn("No glyph for " + code2 + " (CID " + cidHex + ") in font " + this.fontName);
                }
                GeneralPath path2 = this.font.getPath(code2);
                this.cache.put(Integer.valueOf(code2), path2);
                return path2;
            } catch (IOException e) {
                LOG.error("Glyph rendering failed", e);
                path = new GeneralPath();
            }
        }
        return path;
    }

    @Override // org.sejda.sambox.rendering.Glyph2D
    public void dispose() {
        this.cache.clear();
    }
}
