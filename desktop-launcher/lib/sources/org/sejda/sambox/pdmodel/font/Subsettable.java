package org.sejda.sambox.pdmodel.font;

import java.io.IOException;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/Subsettable.class */
public interface Subsettable {
    void addToSubset(int i);

    void subset() throws IOException;

    boolean willBeSubset();
}
