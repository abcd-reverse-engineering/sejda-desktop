package org.sejda.sambox.pdmodel.font;

import java.io.IOException;
import org.apache.fontbox.util.BoundingBox;
import org.sejda.sambox.util.Matrix;
import org.sejda.sambox.util.Vector;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/PDFontLike.class */
public interface PDFontLike {
    String getName();

    PDFontDescriptor getFontDescriptor();

    Matrix getFontMatrix();

    BoundingBox getBoundingBox() throws IOException;

    Vector getPositionVector(int i);

    @Deprecated
    float getHeight(int i) throws IOException;

    float getWidth(int i) throws IOException;

    boolean hasExplicitWidth(int i) throws IOException;

    float getWidthFromFont(int i) throws IOException;

    boolean isEmbedded();

    boolean isDamaged();

    boolean isOriginalEmbeddedMissing();

    boolean isMappingFallbackUsed();

    float getAverageFontWidth();
}
