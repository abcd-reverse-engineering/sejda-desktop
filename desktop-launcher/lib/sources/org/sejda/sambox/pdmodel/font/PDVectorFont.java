package org.sejda.sambox.pdmodel.font;

import java.awt.geom.GeneralPath;
import java.io.IOException;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/PDVectorFont.class */
public interface PDVectorFont {
    GeneralPath getPath(int i) throws IOException;

    boolean hasGlyph(int i) throws IOException;
}
