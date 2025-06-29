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

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/shading/PDShadingType5.class */
public class PDShadingType5 extends PDTriangleBasedShadingType {
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

    public PDShadingType5(COSDictionary shadingDictionary) {
        super(shadingDictionary);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.shading.PDShading
    public int getShadingType() {
        return 5;
    }

    public int getVerticesPerRow() {
        return getCOSObject().getInt(COSName.VERTICES_PER_ROW, -1);
    }

    public void setVerticesPerRow(int verticesPerRow) {
        getCOSObject().setInt(COSName.VERTICES_PER_ROW, verticesPerRow);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.shading.PDShading
    public Paint toPaint(Matrix matrix) {
        return new Type5ShadingPaint(this, matrix);
    }

    @Override // org.sejda.sambox.pdmodel.graphics.shading.PDTriangleBasedShadingType
    List<ShadedTriangle> collectTriangles(AffineTransform xform, Matrix matrix) throws IOException {
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
        int numPerRow = getVerticesPerRow();
        PDRange[] colRange = new PDRange[getNumberOfColorComponents()];
        for (int i = 0; i < colRange.length; i++) {
            colRange[i] = getDecodeForParameter(2 + i);
            if (colRange[i] == null) {
                throw new IOException("Range missing in shading /Decode entry");
            }
        }
        List<Vertex> vlist = new ArrayList<>();
        long maxSrcCoord = ((long) Math.pow(2.0d, getBitsPerCoordinate())) - 1;
        long maxSrcColor = ((long) Math.pow(2.0d, getBitsPerComponent())) - 1;
        MemoryCacheImageInputStream memoryCacheImageInputStream = new MemoryCacheImageInputStream(cosStream.getUnfilteredStream());
        boolean eof = false;
        while (!eof) {
            try {
                Vertex p = readVertex(memoryCacheImageInputStream, maxSrcCoord, maxSrcColor, rangeX, rangeY, colRange, matrix, xform);
                vlist.add(p);
            } catch (EOFException e) {
                eof = true;
            } catch (Throwable th) {
                try {
                    memoryCacheImageInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        memoryCacheImageInputStream.close();
        int rowNum = vlist.size() / numPerRow;
        if (rowNum < 2) {
            return Collections.emptyList();
        }
        Vertex[][] latticeArray = new Vertex[rowNum][numPerRow];
        for (int i2 = 0; i2 < rowNum; i2++) {
            for (int j = 0; j < numPerRow; j++) {
                latticeArray[i2][j] = vlist.get((i2 * numPerRow) + j);
            }
        }
        return createShadedTriangleList(rowNum, numPerRow, latticeArray);
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [float[], float[][]] */
    private List<ShadedTriangle> createShadedTriangleList(int rowNum, int numPerRow, Vertex[][] latticeArray) {
        Point2D[] ps = new Point2D[3];
        ?? r0 = new float[3];
        List<ShadedTriangle> list = new ArrayList<>();
        for (int i = 0; i < rowNum - 1; i++) {
            for (int j = 0; j < numPerRow - 1; j++) {
                ps[0] = latticeArray[i][j].point;
                ps[1] = latticeArray[i][j + 1].point;
                ps[2] = latticeArray[i + 1][j].point;
                r0[0] = latticeArray[i][j].color;
                r0[1] = latticeArray[i][j + 1].color;
                r0[2] = latticeArray[i + 1][j].color;
                list.add(new ShadedTriangle(ps, r0));
                ps[0] = latticeArray[i][j + 1].point;
                ps[1] = latticeArray[i + 1][j].point;
                ps[2] = latticeArray[i + 1][j + 1].point;
                r0[0] = latticeArray[i][j + 1].color;
                r0[1] = latticeArray[i + 1][j].color;
                r0[2] = latticeArray[i + 1][j + 1].color;
                list.add(new ShadedTriangle(ps, r0));
            }
        }
        return list;
    }
}
