package org.sejda.sambox.rendering;

import java.awt.Paint;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.pdmodel.graphics.color.PDColorSpace;
import org.sejda.sambox.pdmodel.graphics.pattern.PDTilingPattern;
import org.sejda.sambox.util.Matrix;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/rendering/TilingPaintFactory.class */
class TilingPaintFactory {
    private final PageDrawer drawer;
    private final Map<TilingPaintParameter, WeakReference<Paint>> weakCache = new WeakHashMap();

    TilingPaintFactory(PageDrawer drawer) {
        this.drawer = drawer;
    }

    Paint create(PDTilingPattern pattern, PDColorSpace colorSpace, PDColor color, AffineTransform xform) throws IOException {
        Paint paint = null;
        TilingPaintParameter tilingPaintParameter = new TilingPaintParameter(this.drawer.getInitialMatrix(), pattern.getCOSObject(), colorSpace, color, xform);
        WeakReference<Paint> weakRef = this.weakCache.get(tilingPaintParameter);
        if (weakRef != null) {
            paint = weakRef.get();
        }
        if (paint == null) {
            paint = new TilingPaint(this.drawer, pattern, colorSpace, color, xform);
            this.weakCache.put(tilingPaintParameter, new WeakReference<>(paint));
        }
        return paint;
    }

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/rendering/TilingPaintFactory$TilingPaintParameter.class */
    private static class TilingPaintParameter {
        private final Matrix matrix;
        private final COSDictionary patternDict;
        private final PDColorSpace colorSpace;
        private final PDColor color;
        private final AffineTransform xform;

        private TilingPaintParameter(Matrix matrix, COSDictionary patternDict, PDColorSpace colorSpace, PDColor color, AffineTransform xform) {
            this.matrix = matrix.m587clone();
            this.patternDict = patternDict;
            this.colorSpace = colorSpace;
            this.color = color;
            this.xform = xform;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            TilingPaintParameter other = (TilingPaintParameter) obj;
            if (!Objects.equals(this.matrix, other.matrix) || !Objects.equals(this.patternDict, other.patternDict) || !Objects.equals(this.colorSpace, other.colorSpace)) {
                return false;
            }
            if (this.color == null && other.color != null) {
                return false;
            }
            if (this.color != null && other.color == null) {
                return false;
            }
            if (this.color != null && this.color.getColorSpace() != other.color.getColorSpace()) {
                return false;
            }
            try {
                if (this.color != null && other.color != null && this.color != other.color) {
                    if (this.color.toRGB() != other.color.toRGB()) {
                        return false;
                    }
                }
                return Objects.equals(this.xform, other.xform);
            } catch (IOException e) {
                return false;
            }
        }

        public int hashCode() {
            int hash = (23 * 7) + (this.matrix != null ? this.matrix.hashCode() : 0);
            return (23 * ((23 * ((23 * ((23 * hash) + (this.patternDict != null ? this.patternDict.hashCode() : 0))) + (this.colorSpace != null ? this.colorSpace.hashCode() : 0))) + (this.color != null ? this.color.hashCode() : 0))) + (this.xform != null ? this.xform.hashCode() : 0);
        }

        public String toString() {
            return "TilingPaintParameter{matrix=" + this.matrix + ", pattern=" + this.patternDict + ", colorSpace=" + this.colorSpace + ", color=" + this.color + ", xform=" + this.xform + "}";
        }
    }
}
