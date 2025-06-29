package org.sejda.impl.sambox.pro.component.crop;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.model.RectangularBox;
import org.sejda.sambox.rendering.ImageType;
import org.sejda.sambox.rendering.PDFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/crop/AutoCrop.class */
public class AutoCrop {
    private final PDDocumentHandler documentHandler;
    private static final Logger LOG = LoggerFactory.getLogger(AutoCrop.class);

    public AutoCrop(PDDocumentHandler documentHandler) {
        this.documentHandler = documentHandler;
    }

    public RectangularBox getCropArea() throws IOException {
        Map<Integer, RectangularBox> cropAreas = getIndividualCropAreas();
        if (cropAreas.isEmpty()) {
            return null;
        }
        int top = 0;
        int bottom = Integer.MAX_VALUE;
        int left = Integer.MAX_VALUE;
        int right = 0;
        for (RectangularBox box : cropAreas.values()) {
            top = Math.max(top, box.getTop());
            left = Math.min(left, box.getLeft());
            bottom = Math.min(bottom, box.getBottom());
            right = Math.max(right, box.getRight());
        }
        LOG.debug("Auto-crop: detected crop box: {}, {}, {}, {}", new Object[]{Integer.valueOf(bottom), Integer.valueOf(left), Integer.valueOf(top), Integer.valueOf(right)});
        return RectangularBox.newInstance(bottom, left, top, right);
    }

    public Map<Integer, RectangularBox> getIndividualCropAreas() throws IOException {
        Map<Integer, RectangularBox> result = new HashMap<>();
        for (int pageIndex = 0; pageIndex < this.documentHandler.getNumberOfPages(); pageIndex++) {
            int pageNum = pageIndex + 1;
            PDFRenderer pdfRenderer = new PDFRenderer(this.documentHandler.getUnderlyingPDDocument());
            BufferedImage img = pdfRenderer.renderImage(pageIndex, 1.0f, ImageType.RGB);
            int imgHeight = img.getHeight();
            int imgWidth = img.getWidth();
            int top = scanY(true, img);
            int bottom = scanY(false, img);
            int left = scanX(true, img);
            int right = scanX(false, img);
            if (left == 0 && bottom == 0 && top == imgHeight && right == imgWidth) {
                LOG.debug("Auto-crop: page: {} needs no cropping", Integer.valueOf(pageNum));
            } else {
                LOG.debug("Auto-crop: page: {} detected crop box: {}, {}, {}, {} width: {} height: {}", new Object[]{Integer.valueOf(pageNum), Integer.valueOf(bottom), Integer.valueOf(left), Integer.valueOf(top), Integer.valueOf(right), Integer.valueOf(imgWidth), Integer.valueOf(imgHeight)});
                result.put(Integer.valueOf(pageNum), RectangularBox.newInstance(bottom, left, top, right));
            }
        }
        return result;
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0073, code lost:
    
        if (r5 == false) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x007b, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:?, code lost:
    
        return r0;
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0042  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int scanY(boolean r5, java.awt.image.BufferedImage r6) {
        /*
            r4 = this;
            r0 = r5
            if (r0 == 0) goto L8
            r0 = 1
            goto L9
        L8:
            r0 = -1
        L9:
            r7 = r0
            r0 = r6
            int r0 = r0.getHeight()
            r8 = r0
            r0 = r6
            int r0 = r0.getWidth()
            r9 = r0
            r0 = r5
            if (r0 == 0) goto L1e
            r0 = 0
            goto L22
        L1e:
            r0 = r8
            r1 = 1
            int r0 = r0 - r1
        L22:
            r10 = r0
        L24:
            r0 = r5
            if (r0 == 0) goto L32
            r0 = r10
            r1 = r8
            if (r0 >= r1) goto L72
            goto L38
        L32:
            r0 = r10
            r1 = -1
            if (r0 <= r1) goto L72
        L38:
            r0 = 0
            r11 = r0
        L3b:
            r0 = r11
            r1 = r9
            if (r0 >= r1) goto L69
            r0 = r6
            r1 = r11
            r2 = r10
            int r0 = r0.getRGB(r1, r2)
            java.awt.Color r1 = java.awt.Color.WHITE
            int r1 = r1.getRGB()
            if (r0 == r1) goto L63
            r0 = r8
            r1 = r10
            int r0 = r0 - r1
            r1 = r5
            if (r1 == 0) goto L60
            r1 = 0
            goto L61
        L60:
            r1 = -1
        L61:
            int r0 = r0 + r1
            return r0
        L63:
            int r11 = r11 + 1
            goto L3b
        L69:
            r0 = r10
            r1 = r7
            int r0 = r0 + r1
            r10 = r0
            goto L24
        L72:
            r0 = r5
            if (r0 == 0) goto L7b
            r0 = r8
            goto L7c
        L7b:
            r0 = 0
        L7c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.sejda.impl.sambox.pro.component.crop.AutoCrop.scanY(boolean, java.awt.image.BufferedImage):int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0070, code lost:
    
        if (r5 == false) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0073, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0079, code lost:
    
        return r0;
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0042  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int scanX(boolean r5, java.awt.image.BufferedImage r6) {
        /*
            r4 = this;
            r0 = r5
            if (r0 == 0) goto L8
            r0 = 1
            goto L9
        L8:
            r0 = -1
        L9:
            r7 = r0
            r0 = r6
            int r0 = r0.getHeight()
            r8 = r0
            r0 = r6
            int r0 = r0.getWidth()
            r9 = r0
            r0 = r5
            if (r0 == 0) goto L1e
            r0 = 0
            goto L22
        L1e:
            r0 = r9
            r1 = 1
            int r0 = r0 - r1
        L22:
            r10 = r0
        L24:
            r0 = r5
            if (r0 == 0) goto L32
            r0 = r10
            r1 = r9
            if (r0 >= r1) goto L6f
            goto L38
        L32:
            r0 = r10
            r1 = -1
            if (r0 <= r1) goto L6f
        L38:
            r0 = 0
            r11 = r0
        L3b:
            r0 = r11
            r1 = r8
            if (r0 >= r1) goto L66
            r0 = r6
            r1 = r10
            r2 = r11
            int r0 = r0.getRGB(r1, r2)
            java.awt.Color r1 = java.awt.Color.WHITE
            int r1 = r1.getRGB()
            if (r0 == r1) goto L60
            r0 = r10
            r1 = r5
            if (r1 == 0) goto L5d
            r1 = 0
            goto L5e
        L5d:
            r1 = 1
        L5e:
            int r0 = r0 + r1
            return r0
        L60:
            int r11 = r11 + 1
            goto L3b
        L66:
            r0 = r10
            r1 = r7
            int r0 = r0 + r1
            r10 = r0
            goto L24
        L6f:
            r0 = r5
            if (r0 == 0) goto L77
            r0 = 0
            goto L79
        L77:
            r0 = r9
        L79:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.sejda.impl.sambox.pro.component.crop.AutoCrop.scanX(boolean, java.awt.image.BufferedImage):int");
    }
}
