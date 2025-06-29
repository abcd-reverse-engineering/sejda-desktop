package org.sejda.model.pdf.font;

import java.io.InputStream;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/pdf/font/FontResource.class */
public interface FontResource {
    String getResource();

    InputStream getFontStream();

    default Integer priority() {
        return 0;
    }
}
