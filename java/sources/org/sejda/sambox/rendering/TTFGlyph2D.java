package org.sejda.sambox.rendering;

import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.fontbox.ttf.HeaderTable;
import org.apache.fontbox.ttf.TrueTypeFont;
import org.sejda.sambox.pdmodel.font.PDCIDFontType2;
import org.sejda.sambox.pdmodel.font.PDFont;
import org.sejda.sambox.pdmodel.font.PDTrueTypeFont;
import org.sejda.sambox.pdmodel.font.PDType0Font;
import org.sejda.sambox.pdmodel.font.PDVectorFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/rendering/TTFGlyph2D.class */
final class TTFGlyph2D implements Glyph2D {
    private static final Logger LOG = LoggerFactory.getLogger(TTFGlyph2D.class);
    private final PDFont font;
    private final TrueTypeFont ttf;
    private PDVectorFont vectorFont;
    private float scale;
    private boolean hasScaling;
    private final Map<Integer, GeneralPath> glyphs;
    private final boolean isCIDFont;

    TTFGlyph2D(PDTrueTypeFont ttfFont) throws IOException {
        this(ttfFont.getTrueTypeFont(), ttfFont, false);
        this.vectorFont = ttfFont;
    }

    TTFGlyph2D(PDType0Font type0Font) throws IOException {
        this(((PDCIDFontType2) type0Font.getDescendantFont()).getTrueTypeFont(), type0Font, true);
        this.vectorFont = type0Font;
    }

    private TTFGlyph2D(TrueTypeFont ttf, PDFont font, boolean isCIDFont) throws IOException {
        this.scale = 1.0f;
        this.glyphs = new HashMap();
        this.font = font;
        this.ttf = ttf;
        this.isCIDFont = isCIDFont;
        HeaderTable header = this.ttf.getHeader();
        if (header != null && header.getUnitsPerEm() != 1000) {
            this.scale = 1000.0f / header.getUnitsPerEm();
            this.hasScaling = true;
        }
    }

    @Override // org.sejda.sambox.rendering.Glyph2D
    public GeneralPath getPathForCharacterCode(int code2) throws IOException {
        int gid = getGIDForCharacterCode(code2);
        return getPathForGID(gid, code2);
    }

    private int getGIDForCharacterCode(int code2) throws IOException {
        if (this.isCIDFont) {
            return ((PDType0Font) this.font).codeToGID(code2);
        }
        return ((PDTrueTypeFont) this.font).codeToGID(code2);
    }

    public GeneralPath getPathForGID(int gid, int code2) throws IOException {
        if (gid == 0 && !this.isCIDFont && code2 == 10 && this.font.isStandard14()) {
            LOG.warn("No glyph for code " + code2 + " in font " + this.font.getName());
            return new GeneralPath();
        }
        GeneralPath glyphPath = this.glyphs.get(Integer.valueOf(gid));
        if (glyphPath == null) {
            if (gid == 0 || gid >= this.ttf.getMaximumProfile().getNumGlyphs()) {
                if (this.isCIDFont) {
                    int cid = ((PDType0Font) this.font).codeToCID(code2);
                    String cidHex = String.format("%04x", Integer.valueOf(cid));
                    LOG.warn("No glyph for code " + code2 + " (CID " + cidHex + ") in font " + this.font.getName());
                } else {
                    LOG.warn("No glyph for " + code2 + " in font " + this.font.getName());
                }
            }
            GeneralPath glyph = this.vectorFont.getPath(code2);
            if (gid == 0 && !this.font.isEmbedded() && !this.font.isStandard14()) {
                glyph = null;
            }
            if (glyph == null) {
                glyphPath = new GeneralPath();
                this.glyphs.put(Integer.valueOf(gid), glyphPath);
            } else {
                glyphPath = glyph;
                if (this.hasScaling) {
                    AffineTransform atScale = AffineTransform.getScaleInstance(this.scale, this.scale);
                    glyphPath = (GeneralPath) glyphPath.clone();
                    glyphPath.transform(atScale);
                }
                this.glyphs.put(Integer.valueOf(gid), glyphPath);
            }
        }
        return (GeneralPath) glyphPath.clone();
    }

    @Override // org.sejda.sambox.rendering.Glyph2D
    public void dispose() {
        this.glyphs.clear();
    }
}
