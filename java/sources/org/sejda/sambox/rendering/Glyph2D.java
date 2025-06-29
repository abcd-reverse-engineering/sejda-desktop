package org.sejda.sambox.rendering;

import java.awt.geom.GeneralPath;
import java.io.IOException;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/rendering/Glyph2D.class */
interface Glyph2D {
    GeneralPath getPathForCharacterCode(int i) throws IOException;

    void dispose();
}
