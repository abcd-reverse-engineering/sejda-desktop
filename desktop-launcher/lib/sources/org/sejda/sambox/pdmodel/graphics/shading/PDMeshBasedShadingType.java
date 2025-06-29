package org.sejda.sambox.pdmodel.graphics.shading;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.MemoryCacheImageInputStream;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.pdmodel.common.PDRange;
import org.sejda.sambox.util.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/shading/PDMeshBasedShadingType.class */
abstract class PDMeshBasedShadingType extends PDShadingType4 {
    private static final Logger LOG = LoggerFactory.getLogger(PDMeshBasedShadingType.class);

    abstract Patch generatePatch(Point2D[] point2DArr, float[][] fArr);

    @Override // org.sejda.sambox.pdmodel.graphics.shading.PDShadingType4, org.sejda.sambox.pdmodel.graphics.shading.PDTriangleBasedShadingType, org.sejda.sambox.pdmodel.graphics.shading.PDShading
    public abstract Rectangle2D getBounds(AffineTransform affineTransform, Matrix matrix) throws IOException;

    PDMeshBasedShadingType(COSDictionary shadingDictionary) {
        super(shadingDictionary);
    }

    final List<Patch> collectPatches(AffineTransform xform, Matrix matrix, int controlPoints) throws IOException {
        Patch current;
        COSDictionary dict = getCOSObject();
        if (!(dict instanceof COSStream)) {
            return Collections.emptyList();
        }
        COSStream cosStream = (COSStream) dict;
        PDRange rangeX = getDecodeForParameter(0);
        PDRange rangeY = getDecodeForParameter(1);
        if (rangeX == null || rangeY == null || Float.compare(rangeX.getMin(), rangeX.getMax()) == 0 || Float.compare(rangeY.getMin(), rangeY.getMax()) == 0) {
            return Collections.emptyList();
        }
        int bitsPerFlag = getBitsPerFlag();
        PDRange[] colRange = new PDRange[getNumberOfColorComponents()];
        for (int i = 0; i < colRange.length; i++) {
            colRange[i] = getDecodeForParameter(2 + i);
            if (colRange[i] == null) {
                throw new IOException("Range missing in shading /Decode entry");
            }
        }
        List<Patch> list = new ArrayList<>();
        long maxSrcCoord = ((long) Math.pow(2.0d, getBitsPerCoordinate())) - 1;
        long maxSrcColor = ((long) Math.pow(2.0d, getBitsPerComponent())) - 1;
        MemoryCacheImageInputStream memoryCacheImageInputStream = new MemoryCacheImageInputStream(cosStream.getUnfilteredStream());
        try {
            Point2D[] implicitEdge = new Point2D[4];
            float[][] implicitCornerColor = new float[2][colRange.length];
            try {
                byte flag = (byte) (memoryCacheImageInputStream.readBits(bitsPerFlag) & 3);
                boolean eof = false;
                while (!eof) {
                    try {
                        boolean isFree = flag == 0;
                        current = readPatch(memoryCacheImageInputStream, isFree, implicitEdge, implicitCornerColor, maxSrcCoord, maxSrcColor, rangeX, rangeY, colRange, matrix, xform, controlPoints);
                    } catch (EOFException e) {
                        eof = true;
                    }
                    if (current != null) {
                        list.add(current);
                        flag = (byte) (memoryCacheImageInputStream.readBits(bitsPerFlag) & 3);
                        switch (flag) {
                            case 0:
                                break;
                            case 1:
                                implicitEdge = current.getFlag1Edge();
                                implicitCornerColor = current.getFlag1Color();
                                break;
                            case 2:
                                implicitEdge = current.getFlag2Edge();
                                implicitCornerColor = current.getFlag2Color();
                                break;
                            case 3:
                                implicitEdge = current.getFlag3Edge();
                                implicitCornerColor = current.getFlag3Color();
                                break;
                            default:
                                LOG.warn("bad flag: " + flag);
                                break;
                        }
                    } else {
                        memoryCacheImageInputStream.close();
                        return list;
                    }
                }
                memoryCacheImageInputStream.close();
                return list;
            } catch (EOFException ex) {
                LOG.error("Unexpected error", ex);
                memoryCacheImageInputStream.close();
                return list;
            }
        } catch (Throwable th) {
            try {
                memoryCacheImageInputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    protected Patch readPatch(ImageInputStream input, boolean isFree, Point2D[] implicitEdge, float[][] implicitCornerColor, long maxSrcCoord, long maxSrcColor, PDRange rangeX, PDRange rangeY, PDRange[] colRange, Matrix matrix, AffineTransform xform, int controlPoints) throws IOException {
        int numberOfColorComponents = getNumberOfColorComponents();
        float[][] color = new float[4][numberOfColorComponents];
        Point2D[] points = new Point2D[controlPoints];
        int pStart = 4;
        int cStart = 2;
        if (isFree) {
            pStart = 0;
            cStart = 0;
        } else {
            points[0] = implicitEdge[0];
            points[1] = implicitEdge[1];
            points[2] = implicitEdge[2];
            points[3] = implicitEdge[3];
            for (int i = 0; i < numberOfColorComponents; i++) {
                color[0][i] = implicitCornerColor[0][i];
                color[1][i] = implicitCornerColor[1][i];
            }
        }
        for (int i2 = pStart; i2 < controlPoints; i2++) {
            try {
                long x = input.readBits(getBitsPerCoordinate());
                long y = input.readBits(getBitsPerCoordinate());
                float px = interpolate(x, maxSrcCoord, rangeX.getMin(), rangeX.getMax());
                float py = interpolate(y, maxSrcCoord, rangeY.getMin(), rangeY.getMax());
                Point2D.Float floatTransformPoint = matrix.transformPoint(px, py);
                xform.transform(floatTransformPoint, floatTransformPoint);
                points[i2] = floatTransformPoint;
            } catch (EOFException ex) {
                LOG.debug("EOF", ex);
                return null;
            }
        }
        for (int i3 = cStart; i3 < 4; i3++) {
            for (int j = 0; j < numberOfColorComponents; j++) {
                long c = input.readBits(getBitsPerComponent());
                color[i3][j] = interpolate(c, maxSrcColor, colRange[j].getMin(), colRange[j].getMax());
            }
        }
        return generatePatch(points, color);
    }

    Rectangle2D getBounds(AffineTransform xform, Matrix matrix, int controlPoints) throws IOException {
        Rectangle2D.Double r15 = null;
        for (Patch patch : collectPatches(xform, matrix, controlPoints)) {
            for (ShadedTriangle shadedTriangle : patch.listOfTriangles) {
                if (r15 == null) {
                    r15 = new Rectangle2D.Double(shadedTriangle.corner[0].getX(), shadedTriangle.corner[0].getY(), 0.0d, 0.0d);
                }
                r15.add(shadedTriangle.corner[0]);
                r15.add(shadedTriangle.corner[1]);
                r15.add(shadedTriangle.corner[2]);
            }
        }
        return r15;
    }
}
