package org.sejda.sambox.pdmodel.graphics.image;

import java.awt.Paint;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.pdmodel.graphics.color.PDColorSpace;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/image/PDImage.class */
public interface PDImage extends COSObjectable {
    BufferedImage getImage() throws IOException;

    WritableRaster getRawRaster() throws IOException;

    BufferedImage getRawImage() throws IOException;

    BufferedImage getStencilImage(Paint paint) throws IOException;

    InputStream createInputStream() throws IOException;

    ByteBuffer asByteBuffer() throws IOException;

    boolean isEmpty() throws IOException;

    boolean isStencil();

    void setStencil(boolean z);

    int getBitsPerComponent();

    void setBitsPerComponent(int i);

    PDColorSpace getColorSpace() throws IOException;

    void setColorSpace(PDColorSpace pDColorSpace);

    int getHeight();

    void setHeight(int i);

    int getWidth();

    void setWidth(int i);

    void setDecode(COSArray cOSArray);

    COSArray getDecode();

    boolean getInterpolate();

    void setInterpolate(boolean z);
}
