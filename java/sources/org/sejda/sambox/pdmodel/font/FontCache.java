package org.sejda.sambox.pdmodel.font;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.fontbox.FontBoxFont;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/FontCache.class */
public final class FontCache {
    private final Map<FontInfo, SoftReference<FontBoxFont>> cache = new ConcurrentHashMap();

    public void addFont(FontInfo info, FontBoxFont font) {
        this.cache.put(info, new SoftReference<>(font));
    }

    public FontBoxFont getFont(FontInfo info) {
        SoftReference<FontBoxFont> reference = this.cache.get(info);
        if (reference != null) {
            return reference.get();
        }
        return null;
    }
}
