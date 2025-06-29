package code.util.imageio;

import code.util.Loggable;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.WritableRaster;
import java.util.List;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import org.slf4j.Logger;
import org.w3c.dom.NodeList;

/* compiled from: ImageIOUtils.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/imageio/ImageIOUtils$.class */
public final class ImageIOUtils$ implements Loggable {
    public static final ImageIOUtils$ MODULE$ = new ImageIOUtils$();
    private static transient Logger logger;
    private static volatile transient boolean bitmap$trans$0;

    static {
        Loggable.$init$(MODULE$);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v7 */
    private Logger logger$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (!bitmap$trans$0) {
                logger = logger();
                r0 = 1;
                bitmap$trans$0 = true;
            }
        }
        return logger;
    }

    @Override // code.util.Loggable
    public Logger logger() {
        return !bitmap$trans$0 ? logger$lzycompute() : logger;
    }

    private ImageIOUtils$() {
    }

    public IIOImage toIIOImage(final BufferedImage image, final String format, final int dpi, final float quality) {
        ImageWriter writer = (ImageWriter) ImageIO.getImageWritersByFormatName(format).next();
        ImageWriteParam param = writer.getDefaultWriteParam();
        IIOMetadata metadata = writer.getDefaultImageMetadata(new ImageTypeSpecifier(image), param);
        if (metadata == null || metadata.isReadOnly() || !metadata.isStandardMetadataFormatSupported()) {
            throw new RuntimeException(new StringBuilder(30).append("Unsuitable writer found for ").append(format).append(": ").append(writer.getClass().getCanonicalName()).toString());
        }
        if (param.canWriteCompressed()) {
            param.setCompressionMode(2);
            param.setCompressionQuality(quality);
        }
        setDPI(metadata, dpi, format);
        return new IIOImage(image, (List) null, metadata);
    }

    private void setDPI(final IIOMetadata metadata, final int dpi, final String formatName) {
        IIOMetadataNode root = (IIOMetadataNode) metadata.getAsTree("javax_imageio_1.0");
        IIOMetadataNode dimension = getOrCreateChildNode(root, "Dimension");
        float res = "PNG".equals(formatName.toUpperCase()) ? dpi / 25.4f : 25.4f / dpi;
        IIOMetadataNode child = getOrCreateChildNode(dimension, "HorizontalPixelSize");
        child.setAttribute("value", Float.toString(res));
        IIOMetadataNode child2 = getOrCreateChildNode(dimension, "VerticalPixelSize");
        child2.setAttribute("value", Float.toString(res));
        metadata.mergeTree("javax_imageio_1.0", root);
    }

    private IIOMetadataNode getOrCreateChildNode(final IIOMetadataNode parentNode, final String name) {
        NodeList nodeList = parentNode.getElementsByTagName(name);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0);
        }
        IIOMetadataNode childNode = new IIOMetadataNode(name);
        parentNode.appendChild(childNode);
        return childNode;
    }

    public boolean isBlack(final BufferedImage image, final int x, final int y) {
        if (image.getType() == 12) {
            WritableRaster raster = image.getRaster();
            int pixelRGBValue = raster.getSample(x, y, 0);
            return pixelRGBValue == 0;
        }
        return isBlack(image, x, y, 140);
    }

    public boolean isBlack(final BufferedImage image, final int x, final int y, final int luminanceCutOff) {
        double luminance = 0.0d;
        if (x < 0 || y < 0 || x > image.getWidth() || y > image.getHeight()) {
            return false;
        }
        try {
            int pixelRGBValue = image.getRGB(x, y);
            int r = (pixelRGBValue >> 16) & 255;
            int g = (pixelRGBValue >> 8) & 255;
            int b = pixelRGBValue & 255;
            luminance = (r * 0.299d) + (g * 0.587d) + (b * 0.114d);
        } catch (Exception e) {
        }
        return luminance < ((double) luminanceCutOff);
    }

    public BufferedImage rotate(final BufferedImage image, final double angle, final int _cx, final int _cy) {
        int width = image.getWidth((ImageObserver) null);
        int height = image.getHeight((ImageObserver) null);
        int minX = 0;
        int minY = 0;
        int maxX = 0;
        int maxY = 0;
        int[] corners = {0, 0, width, 0, width, height, 0, height};
        double theta = Math.toRadians(angle);
        for (int i = 0; i < corners.length; i += 2) {
            int x = (int) (((Math.cos(theta) * (corners[i] - _cx)) - (Math.sin(theta) * (corners[i + 1] - _cy))) + _cx);
            int y = (int) ((Math.sin(theta) * (corners[i] - _cx)) + (Math.cos(theta) * (corners[i + 1] - _cy)) + _cy);
            if (x > maxX) {
                maxX = x;
            }
            if (x < minX) {
                minX = x;
            }
            if (y > maxY) {
                maxY = y;
            }
            if (y < minY) {
                minY = y;
            }
        }
        int cx = _cx - minX;
        int cy = _cy - minY;
        BufferedImage bi = new BufferedImage(maxX - minX, maxY - minY, image.getType());
        Graphics2D g2 = bi.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setBackground(Color.white);
        g2.fillRect(0, 0, bi.getWidth(), bi.getHeight());
        AffineTransform at = new AffineTransform();
        at.rotate(theta, cx, cy);
        g2.setTransform(at);
        g2.drawImage(image, -minX, -minY, (ImageObserver) null);
        g2.dispose();
        return bi;
    }
}
