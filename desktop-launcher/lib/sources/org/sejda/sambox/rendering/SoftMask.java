package org.sejda.sambox.rendering;

import java.awt.Color;
import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.IOException;
import org.sejda.sambox.pdmodel.common.function.PDFunction;
import org.sejda.sambox.pdmodel.common.function.PDFunctionTypeIdentity;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/rendering/SoftMask.class */
class SoftMask implements Paint {
    private static final ColorModel ARGB_COLOR_MODEL = new BufferedImage(1, 1, 2).getColorModel();
    private final Paint paint;
    private final BufferedImage mask;
    private final Rectangle2D bboxDevice;
    private int bc;
    private final PDFunction transferFunction;

    SoftMask(Paint paint, BufferedImage mask, Rectangle2D bboxDevice, PDColor backdropColor, PDFunction transferFunction) {
        this.bc = 0;
        this.paint = paint;
        this.mask = mask;
        this.bboxDevice = bboxDevice;
        if (transferFunction instanceof PDFunctionTypeIdentity) {
            this.transferFunction = null;
        } else {
            this.transferFunction = transferFunction;
        }
        if (backdropColor != null) {
            try {
                Color color = new Color(backdropColor.toRGB());
                this.bc = (((299 * color.getRed()) + (587 * color.getGreen())) + (114 * color.getBlue())) / 1000;
            } catch (IOException e) {
            }
        }
    }

    public PaintContext createContext(ColorModel cm, Rectangle deviceBounds, Rectangle2D userBounds, AffineTransform xform, RenderingHints hints) {
        PaintContext ctx = this.paint.createContext(cm, deviceBounds, userBounds, xform, hints);
        return new SoftPaintContext(ctx);
    }

    public int getTransparency() {
        return 3;
    }

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/rendering/SoftMask$SoftPaintContext.class */
    private class SoftPaintContext implements PaintContext {
        private final PaintContext context;

        SoftPaintContext(PaintContext context) {
            this.context = context;
        }

        public ColorModel getColorModel() {
            return SoftMask.ARGB_COLOR_MODEL;
        }

        public Raster getRaster(int x1, int y1, int w, int h) {
            Raster raster = this.context.getRaster(x1, y1, w, h);
            ColorModel rasterCM = this.context.getColorModel();
            float[] input = null;
            Float[] map = null;
            if (SoftMask.this.transferFunction != null) {
                map = new Float[PDAnnotation.FLAG_TOGGLE_NO_VIEW];
                input = new float[1];
            }
            WritableRaster output = getColorModel().createCompatibleWritableRaster(w, h);
            int x12 = x1 - ((int) SoftMask.this.bboxDevice.getX());
            int y12 = y1 - ((int) SoftMask.this.bboxDevice.getY());
            int[] gray = new int[4];
            Object pixelInput = null;
            int[] pixelOutput = new int[4];
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    pixelInput = raster.getDataElements(x, y, pixelInput);
                    pixelOutput[0] = rasterCM.getRed(pixelInput);
                    pixelOutput[1] = rasterCM.getGreen(pixelInput);
                    pixelOutput[2] = rasterCM.getBlue(pixelInput);
                    pixelOutput[3] = rasterCM.getAlpha(pixelInput);
                    gray[0] = 0;
                    if (x12 + x >= 0 && y12 + y >= 0 && x12 + x < SoftMask.this.mask.getWidth() && y12 + y < SoftMask.this.mask.getHeight()) {
                        SoftMask.this.mask.getRaster().getPixel(x12 + x, y12 + y, gray);
                        int g = gray[0];
                        if (SoftMask.this.transferFunction != null) {
                            try {
                                if (map[g] != null) {
                                    pixelOutput[3] = Math.round(pixelOutput[3] * map[g].floatValue());
                                } else {
                                    input[0] = g / 255.0f;
                                    float f = SoftMask.this.transferFunction.eval(input)[0];
                                    map[g] = Float.valueOf(f);
                                    pixelOutput[3] = Math.round(pixelOutput[3] * f);
                                }
                            } catch (IOException e) {
                                pixelOutput[3] = Math.round(pixelOutput[3] * (SoftMask.this.bc / 255.0f));
                            }
                        } else {
                            pixelOutput[3] = Math.round(pixelOutput[3] * (g / 255.0f));
                        }
                    } else {
                        pixelOutput[3] = Math.round(pixelOutput[3] * (SoftMask.this.bc / 255.0f));
                    }
                    output.setPixel(x, y, pixelOutput);
                }
            }
            return output;
        }

        public void dispose() {
            this.context.dispose();
        }
    }
}
