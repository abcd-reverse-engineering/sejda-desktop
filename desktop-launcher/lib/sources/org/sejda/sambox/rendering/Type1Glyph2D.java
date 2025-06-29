package org.sejda.sambox.rendering;

import java.awt.geom.GeneralPath;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.sejda.sambox.pdmodel.font.PDSimpleFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/rendering/Type1Glyph2D.class */
final class Type1Glyph2D implements Glyph2D {
    private static final Logger LOG = LoggerFactory.getLogger(Type1Glyph2D.class);
    private final Map<Integer, GeneralPath> cache = new HashMap();
    private final PDSimpleFont font;

    Type1Glyph2D(PDSimpleFont font) {
        this.font = font;
    }

    @Override // org.sejda.sambox.rendering.Glyph2D
    public GeneralPath getPathForCharacterCode(int code2) throws NumberFormatException {
        GeneralPath path = this.cache.get(Integer.valueOf(code2));
        if (path == null) {
            try {
                String name = this.font.getEncoding().getName(code2);
                if (!this.font.hasGlyph(name)) {
                    LOG.warn("No glyph for code " + code2 + " (" + name + ") in font " + this.font.getName());
                    if (code2 == 10 && this.font.isStandard14()) {
                        GeneralPath path2 = new GeneralPath();
                        this.cache.put(Integer.valueOf(code2), path2);
                        return path2;
                    }
                    String unicodes = this.font.getGlyphList().toUnicode(name);
                    if (unicodes != null && unicodes.length() == 1) {
                        String uniName = getUniNameOfCodePoint(unicodes.codePointAt(0));
                        if (this.font.hasGlyph(uniName)) {
                            name = uniName;
                        }
                    }
                }
                GeneralPath path3 = this.font.getPath(name);
                if (path3 == null) {
                    path3 = this.font.getPath(".notdef");
                }
                this.cache.put(Integer.valueOf(code2), path3);
                return path3;
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

    private static String getUniNameOfCodePoint(int codePoint) {
        String hex = Integer.toString(codePoint, 16).toUpperCase(Locale.US);
        switch (hex.length()) {
            case 1:
                return "uni000" + hex;
            case 2:
                return "uni00" + hex;
            case 3:
                return "uni0" + hex;
            default:
                return "uni" + hex;
        }
    }
}
