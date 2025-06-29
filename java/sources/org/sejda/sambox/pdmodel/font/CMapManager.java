package org.sejda.sambox.pdmodel.font;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.fontbox.cmap.CMap;
import org.apache.fontbox.cmap.CMapParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/CMapManager.class */
public final class CMapManager {
    private static final Map<String, CMap> CMAP_CACHE = new ConcurrentHashMap();
    private static final Logger LOG = LoggerFactory.getLogger(CMapManager.class);

    private CMapManager() {
    }

    public static CMap getPredefinedCMap(String cMapName) throws IOException {
        CMap cmap = CMAP_CACHE.get(cMapName);
        if (cmap != null) {
            return cmap;
        }
        CMap targetCmap = new CMapParser().parsePredefined(cMapName);
        CMAP_CACHE.put(targetCmap.getName(), targetCmap);
        return targetCmap;
    }

    public static CMap parseCMap(InputStream cMapStream) throws IOException {
        if (cMapStream != null) {
            try {
                return new CMapParser(true).parse(cMapStream);
            } catch (IOException e) {
                LOG.warn("Failed to parse CMap for font", e);
                return null;
            }
        }
        return null;
    }
}
