package org.sejda.sambox.contentstream;

import java.io.IOException;
import java.io.InputStream;
import org.sejda.sambox.pdmodel.PDResources;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.util.Matrix;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/PDContentStream.class */
public interface PDContentStream {
    InputStream getContents() throws IOException;

    PDResources getResources();

    PDRectangle getBBox();

    Matrix getMatrix();
}
