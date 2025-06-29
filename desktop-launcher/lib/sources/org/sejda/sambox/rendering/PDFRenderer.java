package org.sejda.sambox.rendering;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.Map;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.PDPageTree;
import org.sejda.sambox.pdmodel.PDResources;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.graphics.blend.BlendMode;
import org.sejda.sambox.pdmodel.graphics.optionalcontent.PDOptionalContentGroup;
import org.sejda.sambox.pdmodel.graphics.optionalcontent.PDOptionalContentProperties;
import org.sejda.sambox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.sejda.sambox.pdmodel.interactive.annotation.AnnotationFilter;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/rendering/PDFRenderer.class */
public class PDFRenderer {
    protected final PDDocument document;
    private RenderDestination defaultDestination;
    private BufferedImage pageImage;
    private final PDPageTree pageTree;
    private AnnotationFilter annotationFilter = annotation -> {
        return true;
    };
    private boolean subsamplingAllowed = false;
    private RenderingHints renderingHints = null;

    public PDFRenderer(PDDocument document) {
        this.document = document;
        this.pageTree = document.getPages();
    }

    public AnnotationFilter getAnnotationsFilter() {
        return this.annotationFilter;
    }

    public void setAnnotationsFilter(AnnotationFilter annotationsFilter) {
        this.annotationFilter = annotationsFilter;
    }

    public boolean isSubsamplingAllowed() {
        return this.subsamplingAllowed;
    }

    public void setSubsamplingAllowed(boolean subsamplingAllowed) {
        this.subsamplingAllowed = subsamplingAllowed;
    }

    public RenderDestination getDefaultDestination() {
        return this.defaultDestination;
    }

    public void setDefaultDestination(RenderDestination defaultDestination) {
        this.defaultDestination = defaultDestination;
    }

    public RenderingHints getRenderingHints() {
        return this.renderingHints;
    }

    public void setRenderingHints(RenderingHints renderingHints) {
        this.renderingHints = renderingHints;
    }

    public BufferedImage renderImage(int pageIndex) throws IOException {
        return renderImage(pageIndex, 1.0f);
    }

    public BufferedImage renderImage(int pageIndex, float scale) throws IOException {
        return renderImage(pageIndex, scale, ImageType.RGB);
    }

    public BufferedImage renderImageWithDPI(int pageIndex, float dpi) throws IOException {
        return renderImage(pageIndex, dpi / 72.0f, ImageType.RGB);
    }

    public BufferedImage renderImageWithDPI(int pageIndex, float dpi, ImageType imageType) throws IOException {
        return renderImage(pageIndex, dpi / 72.0f, imageType);
    }

    public BufferedImage renderImage(int pageIndex, float scale, ImageType imageType) throws IOException {
        return renderImage(pageIndex, scale, imageType, this.defaultDestination == null ? RenderDestination.EXPORT : this.defaultDestination);
    }

    public BufferedImage renderImage(int pageIndex, float scale, ImageType imageType, RenderDestination destination) throws IOException {
        int bimType;
        BufferedImage image;
        PDPage page = this.pageTree.get(pageIndex);
        PDRectangle cropBox = page.getCropBox();
        float widthPt = cropBox.getWidth();
        float heightPt = cropBox.getHeight();
        int widthPx = (int) Math.max(Math.floor(widthPt * scale), 1.0d);
        int heightPx = (int) Math.max(Math.floor(heightPt * scale), 1.0d);
        if (widthPx * heightPx > 2147483647L) {
            throw new IOException("Maximum size of image exceeded (w * h * scale ^ 2) = " + widthPt + " * " + heightPt + " * " + scale + " ^ 2 > 2147483647");
        }
        int rotationAngle = page.getRotation();
        if (imageType != ImageType.ARGB && hasBlendMode(page)) {
            bimType = 2;
        } else {
            bimType = imageType.toBufferedImageType();
        }
        if (rotationAngle == 90 || rotationAngle == 270) {
            image = new BufferedImage(heightPx, widthPx, bimType);
        } else {
            image = new BufferedImage(widthPx, heightPx, bimType);
        }
        this.pageImage = image;
        Graphics2D g = image.createGraphics();
        if (image.getType() == 2) {
            g.setBackground(new Color(0, 0, 0, 0));
        } else {
            g.setBackground(Color.WHITE);
        }
        g.clearRect(0, 0, image.getWidth(), image.getHeight());
        transform(g, page.getRotation(), cropBox, scale, scale);
        RenderingHints actualRenderingHints = this.renderingHints == null ? createDefaultRenderingHints(g) : this.renderingHints;
        PageDrawerParameters parameters = new PageDrawerParameters(this, page, this.subsamplingAllowed, destination, actualRenderingHints);
        PageDrawer drawer = createPageDrawer(parameters);
        drawer.drawPage(g, cropBox);
        g.dispose();
        if (image.getType() != imageType.toBufferedImageType()) {
            BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), imageType.toBufferedImageType());
            Graphics2D dstGraphics = newImage.createGraphics();
            dstGraphics.setBackground(Color.WHITE);
            dstGraphics.clearRect(0, 0, image.getWidth(), image.getHeight());
            dstGraphics.drawImage(image, 0, 0, (ImageObserver) null);
            dstGraphics.dispose();
            image = newImage;
        }
        return image;
    }

    public void renderPageToGraphics(int pageIndex, Graphics2D graphics) throws IOException {
        renderPageToGraphics(pageIndex, graphics, 1.0f);
    }

    public void renderPageToGraphics(int pageIndex, Graphics2D graphics, float scale) throws IOException {
        renderPageToGraphics(pageIndex, graphics, scale, scale);
    }

    public void renderPageToGraphics(int pageIndex, Graphics2D graphics, float scaleX, float scaleY) throws IOException {
        renderPageToGraphics(pageIndex, graphics, scaleX, scaleY, this.defaultDestination == null ? RenderDestination.VIEW : this.defaultDestination);
    }

    public void renderPageToGraphics(int pageIndex, Graphics2D graphics, float scaleX, float scaleY, RenderDestination destination) throws IOException {
        PDPage page = this.pageTree.get(pageIndex);
        PDRectangle cropBox = page.getCropBox();
        transform(graphics, page.getRotation(), cropBox, scaleX, scaleY);
        graphics.clearRect(0, 0, (int) cropBox.getWidth(), (int) cropBox.getHeight());
        RenderingHints actualRenderingHints = this.renderingHints == null ? createDefaultRenderingHints(graphics) : this.renderingHints;
        PageDrawerParameters parameters = new PageDrawerParameters(this, page, this.subsamplingAllowed, destination, actualRenderingHints);
        PageDrawer drawer = createPageDrawer(parameters);
        drawer.drawPage(graphics, cropBox);
    }

    public boolean isGroupEnabled(PDOptionalContentGroup group) {
        PDOptionalContentProperties ocProperties = this.document.getDocumentCatalog().getOCProperties();
        return ocProperties == null || ocProperties.isGroupEnabled(group);
    }

    private void transform(Graphics2D graphics, int rotationAngle, PDRectangle cropBox, float scaleX, float scaleY) {
        graphics.scale(scaleX, scaleY);
        if (rotationAngle != 0) {
            float translateX = 0.0f;
            float translateY = 0.0f;
            switch (rotationAngle) {
                case 90:
                    translateX = cropBox.getHeight();
                    break;
                case 180:
                    translateX = cropBox.getWidth();
                    translateY = cropBox.getHeight();
                    break;
                case 270:
                    translateY = cropBox.getWidth();
                    break;
            }
            graphics.translate(translateX, translateY);
            graphics.rotate(Math.toRadians(rotationAngle));
        }
    }

    private boolean isBitonal(Graphics2D graphics) {
        GraphicsDevice device;
        DisplayMode displayMode;
        GraphicsConfiguration deviceConfiguration = graphics.getDeviceConfiguration();
        return (deviceConfiguration == null || (device = deviceConfiguration.getDevice()) == null || (displayMode = device.getDisplayMode()) == null || displayMode.getBitDepth() != 1) ? false : true;
    }

    private RenderingHints createDefaultRenderingHints(Graphics2D graphics) {
        Object obj;
        Object obj2;
        boolean isBitonal = isBitonal(graphics);
        RenderingHints r = new RenderingHints((Map) null);
        RenderingHints.Key key = RenderingHints.KEY_INTERPOLATION;
        if (isBitonal) {
            obj = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
        } else {
            obj = RenderingHints.VALUE_INTERPOLATION_BICUBIC;
        }
        r.put(key, obj);
        r.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        RenderingHints.Key key2 = RenderingHints.KEY_ANTIALIASING;
        if (isBitonal) {
            obj2 = RenderingHints.VALUE_ANTIALIAS_OFF;
        } else {
            obj2 = RenderingHints.VALUE_ANTIALIAS_ON;
        }
        r.put(key2, obj2);
        return r;
    }

    protected PageDrawer createPageDrawer(PageDrawerParameters parameters) throws IOException {
        PageDrawer pageDrawer = new PageDrawer(parameters);
        pageDrawer.setAnnotationFilter(this.annotationFilter);
        return pageDrawer;
    }

    private boolean hasBlendMode(PDPage page) {
        PDResources resources = page.getResources();
        if (resources == null) {
            return false;
        }
        for (COSName name : resources.getExtGStateNames()) {
            PDExtendedGraphicsState extGState = resources.getExtGState(name);
            if (extGState != null) {
                BlendMode blendMode = extGState.getBlendMode();
                if (blendMode != BlendMode.NORMAL) {
                    return true;
                }
            }
        }
        return false;
    }

    BufferedImage getPageImage() {
        return this.pageImage;
    }
}
