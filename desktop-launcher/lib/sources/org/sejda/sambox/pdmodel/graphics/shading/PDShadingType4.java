package org.sejda.sambox.pdmodel.graphics.shading;

import java.awt.Paint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.imageio.stream.MemoryCacheImageInputStream;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.pdmodel.common.PDRange;
import org.sejda.sambox.util.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/shading/PDShadingType4.class */
public class PDShadingType4 extends PDTriangleBasedShadingType {
    private static Logger LOG = LoggerFactory.getLogger(PDShadingType4.class);

    @Override // org.sejda.sambox.pdmodel.graphics.shading.PDTriangleBasedShadingType, org.sejda.sambox.pdmodel.graphics.shading.PDShading
    public /* bridge */ /* synthetic */ Rectangle2D getBounds(AffineTransform affineTransform, Matrix matrix) throws IOException {
        return super.getBounds(affineTransform, matrix);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.shading.PDTriangleBasedShadingType
    public /* bridge */ /* synthetic */ PDRange getDecodeForParameter(int i) {
        return super.getDecodeForParameter(i);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.shading.PDTriangleBasedShadingType
    public /* bridge */ /* synthetic */ void setDecodeValues(COSArray cOSArray) {
        super.setDecodeValues(cOSArray);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.shading.PDTriangleBasedShadingType
    public /* bridge */ /* synthetic */ int getNumberOfColorComponents() throws IOException {
        return super.getNumberOfColorComponents();
    }

    @Override // org.sejda.sambox.pdmodel.graphics.shading.PDTriangleBasedShadingType
    public /* bridge */ /* synthetic */ void setBitsPerCoordinate(int i) {
        super.setBitsPerCoordinate(i);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.shading.PDTriangleBasedShadingType
    public /* bridge */ /* synthetic */ int getBitsPerCoordinate() {
        return super.getBitsPerCoordinate();
    }

    @Override // org.sejda.sambox.pdmodel.graphics.shading.PDTriangleBasedShadingType
    public /* bridge */ /* synthetic */ void setBitsPerComponent(int i) {
        super.setBitsPerComponent(i);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.shading.PDTriangleBasedShadingType
    public /* bridge */ /* synthetic */ int getBitsPerComponent() {
        return super.getBitsPerComponent();
    }

    public PDShadingType4(COSDictionary shadingDictionary) {
        super(shadingDictionary);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.shading.PDShading
    public int getShadingType() {
        return 4;
    }

    public int getBitsPerFlag() {
        return getCOSObject().getInt(COSName.BITS_PER_FLAG, -1);
    }

    public void setBitsPerFlag(int bitsPerFlag) {
        getCOSObject().setInt(COSName.BITS_PER_FLAG, bitsPerFlag);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.shading.PDShading
    public Paint toPaint(Matrix matrix) {
        return new Type4ShadingPaint(this, matrix);
    }

    /* JADX WARN: Type inference failed for: r0v61, types: [float[], float[][]] */
    /* JADX WARN: Type inference failed for: r0v88, types: [float[], float[][]] */
    @Override // org.sejda.sambox.pdmodel.graphics.shading.PDTriangleBasedShadingType
    List<ShadedTriangle> collectTriangles(AffineTransform xform, Matrix matrix) throws IOException {
        int bitsPerFlag = getBitsPerFlag();
        COSDictionary dict = getCOSObject();
        if (!(dict instanceof COSStream)) {
            return Collections.emptyList();
        }
        COSStream stream = (COSStream) dict;
        PDRange rangeX = getDecodeForParameter(0);
        PDRange rangeY = getDecodeForParameter(1);
        if (rangeX == null || rangeY == null || Float.compare(rangeX.getMin(), rangeX.getMax()) == 0 || Float.compare(rangeY.getMin(), rangeY.getMax()) == 0) {
            return Collections.emptyList();
        }
        PDRange[] colRange = new PDRange[getNumberOfColorComponents()];
        for (int i = 0; i < colRange.length; i++) {
            colRange[i] = getDecodeForParameter(2 + i);
            if (colRange[i] == null) {
                throw new IOException("Range missing in shading /Decode entry");
            }
        }
        List<ShadedTriangle> list = new ArrayList<>();
        long maxSrcCoord = ((long) Math.pow(2.0d, getBitsPerCoordinate())) - 1;
        long maxSrcColor = ((long) Math.pow(2.0d, getBitsPerComponent())) - 1;
        MemoryCacheImageInputStream memoryCacheImageInputStream = new MemoryCacheImageInputStream(stream.getUnfilteredStream());
        byte flag = 0;
        try {
            try {
                flag = (byte) (memoryCacheImageInputStream.readBits(bitsPerFlag) & 3);
            } catch (EOFException ex) {
                LOG.error("Error reading image stream", ex);
            }
            boolean eof = false;
            while (!eof) {
                try {
                } catch (EOFException e) {
                    eof = true;
                }
                switch (flag) {
                    case 0:
                        Vertex p0 = readVertex(memoryCacheImageInputStream, maxSrcCoord, maxSrcColor, rangeX, rangeY, colRange, matrix, xform);
                        flag = (byte) (memoryCacheImageInputStream.readBits(bitsPerFlag) & 3);
                        if (flag != 0) {
                            LOG.error("bad triangle: " + flag);
                        }
                        Vertex p1 = readVertex(memoryCacheImageInputStream, maxSrcCoord, maxSrcColor, rangeX, rangeY, colRange, matrix, xform);
                        memoryCacheImageInputStream.readBits(bitsPerFlag);
                        if (flag != 0) {
                            LOG.error("bad triangle: " + flag);
                        }
                        Vertex p2 = readVertex(memoryCacheImageInputStream, maxSrcCoord, maxSrcColor, rangeX, rangeY, colRange, matrix, xform);
                        list.add(new ShadedTriangle(new Point2D[]{p0.point, p1.point, p2.point}, new float[]{p0.color, p1.color, p2.color}));
                        flag = (byte) (memoryCacheImageInputStream.readBits(bitsPerFlag) & 3);
                    case 1:
                    case 2:
                        int lastIndex = list.size() - 1;
                        if (lastIndex < 0) {
                            LOG.error("broken data stream: " + list.size());
                        } else {
                            ShadedTriangle preTri = list.get(lastIndex);
                            Vertex p22 = readVertex(memoryCacheImageInputStream, maxSrcCoord, maxSrcColor, rangeX, rangeY, colRange, matrix, xform);
                            Point2D[] ps = new Point2D[3];
                            ps[0] = flag == 1 ? preTri.corner[1] : preTri.corner[0];
                            ps[1] = preTri.corner[2];
                            ps[2] = p22.point;
                            ?? r0 = new float[3];
                            r0[0] = flag == 1 ? preTri.color[1] : preTri.color[0];
                            r0[1] = preTri.color[2];
                            r0[2] = p22.color;
                            list.add(new ShadedTriangle(ps, r0));
                            flag = (byte) (memoryCacheImageInputStream.readBits(bitsPerFlag) & 3);
                        }
                    default:
                        LOG.warn("bad flag: " + flag);
                }
            }
            memoryCacheImageInputStream.close();
            return list;
        } catch (Throwable th) {
            try {
                memoryCacheImageInputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}
