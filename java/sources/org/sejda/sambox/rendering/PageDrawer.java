package org.sejda.sambox.rendering;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.TexturePaint;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.ImageObserver;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.sejda.sambox.contentstream.PDFGraphicsStreamEngine;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.PDResources;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.common.function.PDFunction;
import org.sejda.sambox.pdmodel.documentinterchange.markedcontent.PDPropertyList;
import org.sejda.sambox.pdmodel.font.PDCIDFont;
import org.sejda.sambox.pdmodel.font.PDCIDFontType0;
import org.sejda.sambox.pdmodel.font.PDCIDFontType2;
import org.sejda.sambox.pdmodel.font.PDFont;
import org.sejda.sambox.pdmodel.font.PDTrueTypeFont;
import org.sejda.sambox.pdmodel.font.PDType0Font;
import org.sejda.sambox.pdmodel.font.PDType1CFont;
import org.sejda.sambox.pdmodel.font.PDType1Font;
import org.sejda.sambox.pdmodel.font.PDType3Font;
import org.sejda.sambox.pdmodel.graphics.PDLineDashPattern;
import org.sejda.sambox.pdmodel.graphics.PDXObject;
import org.sejda.sambox.pdmodel.graphics.blend.BlendMode;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.pdmodel.graphics.color.PDColorSpace;
import org.sejda.sambox.pdmodel.graphics.color.PDDeviceGray;
import org.sejda.sambox.pdmodel.graphics.color.PDDeviceRGB;
import org.sejda.sambox.pdmodel.graphics.color.PDICCBased;
import org.sejda.sambox.pdmodel.graphics.color.PDPattern;
import org.sejda.sambox.pdmodel.graphics.color.PDSeparation;
import org.sejda.sambox.pdmodel.graphics.form.PDFormXObject;
import org.sejda.sambox.pdmodel.graphics.form.PDTransparencyGroup;
import org.sejda.sambox.pdmodel.graphics.image.PDImage;
import org.sejda.sambox.pdmodel.graphics.image.PDImageXObject;
import org.sejda.sambox.pdmodel.graphics.optionalcontent.PDOptionalContentGroup;
import org.sejda.sambox.pdmodel.graphics.optionalcontent.PDOptionalContentMembershipDictionary;
import org.sejda.sambox.pdmodel.graphics.pattern.PDAbstractPattern;
import org.sejda.sambox.pdmodel.graphics.pattern.PDShadingPattern;
import org.sejda.sambox.pdmodel.graphics.pattern.PDTilingPattern;
import org.sejda.sambox.pdmodel.graphics.shading.PDShading;
import org.sejda.sambox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.sejda.sambox.pdmodel.graphics.state.PDGraphicsState;
import org.sejda.sambox.pdmodel.graphics.state.PDSoftMask;
import org.sejda.sambox.pdmodel.graphics.state.RenderingMode;
import org.sejda.sambox.pdmodel.interactive.annotation.AnnotationFilter;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationUnknown;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAppearanceDictionary;
import org.sejda.sambox.util.Matrix;
import org.sejda.sambox.util.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/rendering/PageDrawer.class */
public class PageDrawer extends PDFGraphicsStreamEngine {
    private static final Logger LOG = LoggerFactory.getLogger(PageDrawer.class);
    private final PDFRenderer renderer;
    private Graphics2D graphics;
    private AffineTransform xform;
    private float xformScalingFactorX;
    private float xformScalingFactorY;
    private PDRectangle pageSize;
    private boolean flipTG;
    private int clipWindingRule;
    private GeneralPath linePath;
    private List<Path2D> lastClips;
    private Shape initialClip;
    private List<Shape> textClippings;
    private final Map<PDFont, Glyph2D> fontGlyph2D;
    private final TilingPaintFactory tilingPaintFactory;
    private final Deque<TransparencyGroup> transparencyGroupStack;
    private int nestedHiddenOCGCount;
    private final RenderDestination destination;
    private final RenderingHints renderingHints;
    private LookupTable invTable;
    private AnnotationFilter annotationFilter;
    private boolean textContentRendered;
    private boolean imageContentRendered;

    public PageDrawer(PageDrawerParameters parameters) throws IOException {
        super(parameters.getPage());
        this.flipTG = false;
        this.clipWindingRule = -1;
        this.linePath = new GeneralPath();
        this.fontGlyph2D = new HashMap();
        this.tilingPaintFactory = new TilingPaintFactory(this);
        this.transparencyGroupStack = new ArrayDeque();
        this.invTable = null;
        this.annotationFilter = annotation -> {
            return true;
        };
        this.textContentRendered = true;
        this.imageContentRendered = true;
        this.renderer = parameters.getRenderer();
        this.destination = parameters.getDestination();
        this.renderingHints = parameters.getRenderingHints();
    }

    public AnnotationFilter getAnnotationFilter() {
        return this.annotationFilter;
    }

    public void setAnnotationFilter(AnnotationFilter annotationFilter) {
        this.annotationFilter = annotationFilter;
    }

    public final PDFRenderer getRenderer() {
        return this.renderer;
    }

    protected final Graphics2D getGraphics() {
        return this.graphics;
    }

    protected final GeneralPath getLinePath() {
        return this.linePath;
    }

    private void setRenderingHints() {
        this.graphics.addRenderingHints(this.renderingHints);
    }

    public void drawPage(Graphics g, PDRectangle pageSize) throws IOException {
        this.graphics = (Graphics2D) g;
        this.xform = this.graphics.getTransform();
        Matrix m = new Matrix(this.xform);
        this.xformScalingFactorX = Math.abs(m.getScalingFactorX());
        this.xformScalingFactorY = Math.abs(m.getScalingFactorY());
        this.initialClip = this.graphics.getClip();
        this.pageSize = pageSize;
        setRenderingHints();
        this.graphics.translate(0.0d, pageSize.getHeight());
        this.graphics.scale(1.0d, -1.0d);
        this.graphics.translate(-pageSize.getLowerLeftX(), -pageSize.getLowerLeftY());
        processPage(getPage());
        for (PDAnnotation annotation : getPage().getAnnotations(this.annotationFilter)) {
            showAnnotation(annotation);
        }
        this.graphics = null;
    }

    void drawTilingPattern(Graphics2D g, PDTilingPattern pattern, PDColorSpace colorSpace, PDColor color, Matrix patternMatrix) throws IOException {
        Graphics2D savedGraphics = this.graphics;
        this.graphics = g;
        GeneralPath savedLinePath = this.linePath;
        this.linePath = new GeneralPath();
        int savedClipWindingRule = this.clipWindingRule;
        this.clipWindingRule = -1;
        List<Path2D> savedLastClips = this.lastClips;
        this.lastClips = null;
        Shape savedInitialClip = this.initialClip;
        this.initialClip = null;
        boolean savedFlipTG = this.flipTG;
        this.flipTG = true;
        setRenderingHints();
        processTilingPattern(pattern, color, colorSpace, patternMatrix);
        this.flipTG = savedFlipTG;
        this.graphics = savedGraphics;
        this.linePath = savedLinePath;
        this.lastClips = savedLastClips;
        this.initialClip = savedInitialClip;
        this.clipWindingRule = savedClipWindingRule;
    }

    private float clampColor(float color) {
        if (color < 0.0f) {
            return 0.0f;
        }
        if (color > 1.0f) {
            return 1.0f;
        }
        return color;
    }

    protected Paint getPaint(PDColor color) throws IOException {
        PDColorSpace colorSpace = color.getColorSpace();
        if (colorSpace == null) {
            colorSpace = PDDeviceRGB.INSTANCE;
        }
        if ((colorSpace instanceof PDSeparation) && "None".equals(((PDSeparation) colorSpace).getColorantName())) {
            return new Color(0, 0, 0, 0);
        }
        if (!(colorSpace instanceof PDPattern)) {
            float[] rgb = colorSpace.toRGB(color.getComponents());
            return new Color(clampColor(rgb[0]), clampColor(rgb[1]), clampColor(rgb[2]));
        }
        PDPattern patternSpace = (PDPattern) colorSpace;
        PDAbstractPattern pattern = patternSpace.getPattern(color);
        if (pattern instanceof PDTilingPattern) {
            PDTilingPattern tilingPattern = (PDTilingPattern) pattern;
            if (tilingPattern.getPaintType() == 1) {
                return this.tilingPaintFactory.create(tilingPattern, null, null, this.xform);
            }
            return this.tilingPaintFactory.create(tilingPattern, patternSpace.getUnderlyingColorSpace(), color, this.xform);
        }
        PDShadingPattern shadingPattern = (PDShadingPattern) pattern;
        PDShading shading = shadingPattern.getShading();
        if (shading == null) {
            LOG.error("shadingPattern is null, will be filled with transparency");
            return new Color(0, 0, 0, 0);
        }
        return shading.toPaint(Matrix.concatenate(getInitialMatrix(), shadingPattern.getMatrix()));
    }

    protected final void setClip() {
        List<Path2D> clippingPaths = getGraphicsState().getCurrentClippingPaths();
        if (clippingPaths != this.lastClips) {
            transferClip(this.graphics);
            if (this.initialClip != null) {
            }
            this.lastClips = clippingPaths;
        }
    }

    protected void transferClip(Graphics2D graphics) {
        Area clippingPath = getGraphicsState().getCurrentClippingPath();
        if (clippingPath.getPathIterator((AffineTransform) null).isDone()) {
            graphics.setClip(new Rectangle());
        } else {
            graphics.setClip(clippingPath);
        }
    }

    @Override // org.sejda.sambox.contentstream.PDFStreamEngine
    public void beginText() throws IOException {
        setClip();
        beginTextClip();
    }

    @Override // org.sejda.sambox.contentstream.PDFStreamEngine
    public void endText() throws IOException {
        endTextClip();
    }

    private void beginTextClip() {
        this.textClippings = new ArrayList();
    }

    private void endTextClip() {
        PDGraphicsState state = getGraphicsState();
        RenderingMode renderingMode = state.getTextState().getRenderingMode();
        if (renderingMode.isClip() && !this.textClippings.isEmpty()) {
            GeneralPath path = new GeneralPath(1, this.textClippings.size());
            for (Shape shape : this.textClippings) {
                path.append(shape, false);
            }
            state.intersectClippingPath(path);
            this.textClippings = new ArrayList();
            this.lastClips = null;
        }
    }

    @Override // org.sejda.sambox.contentstream.PDFStreamEngine
    protected void showFontGlyph(Matrix textRenderingMatrix, PDFont font, int code2, Vector displacement) throws IOException {
        AffineTransform at = textRenderingMatrix.createAffineTransform();
        at.concatenate(font.getFontMatrix().createAffineTransform());
        Glyph2D glyph2D = createGlyph2D(font);
        drawGlyph2D(glyph2D, font, code2, displacement, at);
    }

    private void drawGlyph2D(Glyph2D glyph2D, PDFont font, int code2, Vector displacement, AffineTransform at) throws IOException {
        PDGraphicsState state = getGraphicsState();
        RenderingMode renderingMode = state.getTextState().getRenderingMode();
        GeneralPath path = glyph2D.getPathForCharacterCode(code2);
        if (path != null) {
            if (!font.isEmbedded() && !font.isVertical() && !font.isStandard14() && font.hasExplicitWidth(code2)) {
                float fontWidth = font.getWidthFromFont(code2);
                if (fontWidth > 0.0f && Math.abs(fontWidth - (displacement.getX() * 1000.0f)) > 1.0E-4d) {
                    float pdfWidth = displacement.getX() * 1000.0f;
                    at.scale(pdfWidth / fontWidth, 1.0d);
                }
            }
            Shape glyph = at.createTransformedShape(path);
            if (isContentRendered() && isTextContentRendered()) {
                if (renderingMode.isFill()) {
                    this.graphics.setComposite(state.getNonStrokingJavaComposite());
                    this.graphics.setPaint(getNonStrokingPaint());
                    setClip();
                    this.graphics.fill(glyph);
                }
                if (renderingMode.isStroke()) {
                    this.graphics.setComposite(state.getStrokingJavaComposite());
                    this.graphics.setPaint(getStrokingPaint());
                    this.graphics.setStroke(getStroke());
                    setClip();
                    this.graphics.draw(glyph);
                }
            }
            if (renderingMode.isClip()) {
                this.textClippings.add(glyph);
            }
        }
    }

    @Override // org.sejda.sambox.contentstream.PDFStreamEngine
    protected void showType3Glyph(Matrix textRenderingMatrix, PDType3Font font, int code2, Vector displacement) throws IOException {
        PDGraphicsState state = getGraphicsState();
        RenderingMode renderingMode = state.getTextState().getRenderingMode();
        if (!RenderingMode.NEITHER.equals(renderingMode)) {
            super.showType3Glyph(textRenderingMatrix, font, code2, displacement);
        }
    }

    private Glyph2D createGlyph2D(PDFont font) throws IOException {
        Glyph2D glyph2D = this.fontGlyph2D.get(font);
        if (glyph2D != null) {
            return glyph2D;
        }
        if (font instanceof PDTrueTypeFont) {
            PDTrueTypeFont ttfFont = (PDTrueTypeFont) font;
            glyph2D = new TTFGlyph2D(ttfFont);
        } else if (font instanceof PDType1Font) {
            PDType1Font pdType1Font = (PDType1Font) font;
            glyph2D = new Type1Glyph2D(pdType1Font);
        } else if (font instanceof PDType1CFont) {
            PDType1CFont type1CFont = (PDType1CFont) font;
            glyph2D = new Type1Glyph2D(type1CFont);
        } else if (font instanceof PDType0Font) {
            PDType0Font type0Font = (PDType0Font) font;
            if (type0Font.getDescendantFont() instanceof PDCIDFontType2) {
                glyph2D = new TTFGlyph2D(type0Font);
            } else {
                PDCIDFont descendantFont = type0Font.getDescendantFont();
                if (descendantFont instanceof PDCIDFontType0) {
                    PDCIDFontType0 cidType0Font = (PDCIDFontType0) descendantFont;
                    glyph2D = new CIDType0Glyph2D(cidType0Font);
                }
            }
        } else {
            throw new IllegalStateException("Bad font type: " + font.getClass().getSimpleName());
        }
        if (glyph2D != null) {
            this.fontGlyph2D.put(font, glyph2D);
        }
        if (glyph2D == null) {
            throw new UnsupportedOperationException("No font for " + font.getName());
        }
        return glyph2D;
    }

    @Override // org.sejda.sambox.contentstream.PDFGraphicsStreamEngine
    public void appendRectangle(Point2D p0, Point2D p1, Point2D p2, Point2D p3) {
        this.linePath.moveTo((float) p0.getX(), (float) p0.getY());
        this.linePath.lineTo((float) p1.getX(), (float) p1.getY());
        this.linePath.lineTo((float) p2.getX(), (float) p2.getY());
        this.linePath.lineTo((float) p3.getX(), (float) p3.getY());
        this.linePath.closePath();
    }

    private Paint applySoftMaskToPaint(Paint parentPaint, PDSoftMask softMask) throws IOException {
        COSArray backdropColorArray;
        if (softMask == null || softMask.getGroup() == null) {
            return parentPaint;
        }
        PDColor backdropColor = null;
        if (COSName.LUMINOSITY.equals(softMask.getSubType()) && (backdropColorArray = softMask.getBackdropColor()) != null) {
            PDTransparencyGroup form = softMask.getGroup();
            PDColorSpace colorSpace = form.getGroup().getColorSpace(form.getResources());
            if (colorSpace != null) {
                backdropColor = new PDColor(backdropColorArray, colorSpace);
            }
        }
        TransparencyGroup transparencyGroup = new TransparencyGroup(softMask.getGroup(), true, softMask.getInitialTransformationMatrix(), backdropColor);
        BufferedImage image = transparencyGroup.getImage();
        if (image == null) {
            return parentPaint;
        }
        BufferedImage gray = new BufferedImage(image.getWidth(), image.getHeight(), 10);
        if (COSName.ALPHA.equals(softMask.getSubType())) {
            gray.setData(image.getAlphaRaster());
        } else if (COSName.LUMINOSITY.equals(softMask.getSubType())) {
            Graphics g = gray.getGraphics();
            g.drawImage(image, 0, 0, (ImageObserver) null);
            g.dispose();
        } else {
            throw new IOException("Invalid soft mask subtype.");
        }
        BufferedImage gray2 = adjustImage(gray);
        Rectangle2D tpgBounds = transparencyGroup.getBounds();
        return new SoftMask(parentPaint, gray2, tpgBounds, backdropColor, softMask.getTransferFunction());
    }

    private BufferedImage adjustImage(BufferedImage gray) {
        AffineTransform at = new AffineTransform(this.xform);
        at.scale(1.0d / this.xformScalingFactorX, 1.0d / this.xformScalingFactorY);
        Rectangle originalBounds = new Rectangle(gray.getWidth(), gray.getHeight());
        Rectangle2D transformedBounds = at.createTransformedShape(originalBounds).getBounds2D();
        at.preConcatenate(AffineTransform.getTranslateInstance(-transformedBounds.getMinX(), -transformedBounds.getMinY()));
        int width = (int) Math.ceil(transformedBounds.getWidth());
        int height = (int) Math.ceil(transformedBounds.getHeight());
        if (width == gray.getWidth() && height == gray.getHeight() && at.isIdentity()) {
            return gray;
        }
        BufferedImage transformedGray = new BufferedImage(width, height, 10);
        Graphics2D g2 = transformedGray.getGraphics();
        g2.drawImage(gray, at, (ImageObserver) null);
        g2.dispose();
        return transformedGray;
    }

    private Paint getStrokingPaint() throws IOException {
        return applySoftMaskToPaint(getPaint(getGraphicsState().getStrokingColor()), getGraphicsState().getSoftMask());
    }

    protected final Paint getNonStrokingPaint() throws IOException {
        return applySoftMaskToPaint(getPaint(getGraphicsState().getNonStrokingColor()), getGraphicsState().getSoftMask());
    }

    private Stroke getStroke() {
        PDGraphicsState state = getGraphicsState();
        float lineWidth = transformWidth(state.getLineWidth());
        if (lineWidth < 0.25d) {
            lineWidth = 0.25f;
        }
        PDLineDashPattern dashPattern = state.getLineDashPattern();
        float[] dashArray = dashPattern.getDashArray();
        if (isAllZeroDash(dashArray)) {
            return p -> {
                return new Area();
            };
        }
        float phaseStart = dashPattern.getPhase();
        float[] dashArray2 = getDashArray(dashPattern);
        float phaseStart2 = transformWidth(phaseStart);
        int lineCap = Math.min(2, Math.max(0, state.getLineCap()));
        int lineJoin = Math.min(2, Math.max(0, state.getLineJoin()));
        float miterLimit = state.getMiterLimit();
        if (miterLimit < 1.0f) {
            LOG.warn("Miter limit must be >= 1, value " + miterLimit + " is ignored");
            miterLimit = 10.0f;
        }
        return new BasicStroke(lineWidth, lineCap, lineJoin, miterLimit, dashArray2, phaseStart2);
    }

    private boolean isAllZeroDash(float[] dashArray) {
        if (dashArray.length > 0) {
            for (float f : dashArray) {
                if (f != 0.0f) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private float[] getDashArray(PDLineDashPattern dashPattern) {
        float[] dashArray = dashPattern.getDashArray();
        int phase = dashPattern.getPhase();
        if (dashArray.length == 0 || Float.isInfinite(phase) || Float.isNaN(phase)) {
            return null;
        }
        for (int i = 0; i < dashArray.length; i++) {
            if (Float.isInfinite(dashArray[i]) || Float.isNaN(dashArray[i])) {
                return null;
            }
        }
        for (int i2 = 0; i2 < dashArray.length; i2++) {
            float w = transformWidth(dashArray[i2]);
            if (this.xformScalingFactorX < 0.5f) {
                dashArray[i2] = Math.max(w, 0.2f);
            } else {
                dashArray[i2] = Math.max(w, 0.062f);
            }
        }
        return dashArray;
    }

    @Override // org.sejda.sambox.contentstream.PDFGraphicsStreamEngine
    public void strokePath() throws IOException {
        if (isContentRendered()) {
            this.graphics.setComposite(getGraphicsState().getStrokingJavaComposite());
            this.graphics.setPaint(getStrokingPaint());
            this.graphics.setStroke(getStroke());
            setClip();
            this.graphics.draw(this.linePath);
        }
        this.linePath.reset();
    }

    @Override // org.sejda.sambox.contentstream.PDFGraphicsStreamEngine
    public void fillPath(int windingRule) throws IOException {
        Area area;
        PDGraphicsState graphicsState = getGraphicsState();
        this.graphics.setComposite(graphicsState.getNonStrokingJavaComposite());
        setClip();
        this.linePath.setWindingRule(windingRule);
        Rectangle2D bounds = this.linePath.getBounds2D();
        boolean noAntiAlias = isRectangular(this.linePath) && bounds.getWidth() > 1.0d && bounds.getHeight() > 1.0d;
        if (noAntiAlias) {
            this.graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        }
        if (graphicsState.getNonStrokingColorSpace() instanceof PDPattern) {
            Area area2 = new Area(this.linePath);
            Shape clip = this.graphics.getClip();
            if (clip != null) {
                area2.intersect(new Area(clip));
            }
            intersectShadingBBox(graphicsState.getNonStrokingColor(), area2);
            area = area2;
        } else {
            area = this.linePath;
        }
        if (isContentRendered() && !area.getPathIterator((AffineTransform) null).isDone()) {
            this.graphics.setPaint(getNonStrokingPaint());
            this.graphics.fill(area);
        }
        this.linePath.reset();
        if (noAntiAlias) {
            setRenderingHints();
        }
    }

    private void intersectShadingBBox(PDColor color, Area area) throws IOException {
        if (color.getColorSpace() instanceof PDPattern) {
            PDColorSpace colorSpace = color.getColorSpace();
            PDAbstractPattern pat = ((PDPattern) colorSpace).getPattern(color);
            if (pat instanceof PDShadingPattern) {
                PDShading shading = ((PDShadingPattern) pat).getShading();
                PDRectangle bbox = shading.getBBox();
                if (bbox != null) {
                    Matrix m = Matrix.concatenate(getInitialMatrix(), pat.getMatrix());
                    Area bboxArea = new Area(bbox.transform(m));
                    area.intersect(bboxArea);
                }
            }
        }
    }

    private boolean isRectangular(GeneralPath path) {
        PathIterator iter = path.getPathIterator((AffineTransform) null);
        double[] coords = new double[6];
        int count = 0;
        int[] xs = new int[4];
        int[] ys = new int[4];
        while (!iter.isDone()) {
            switch (iter.currentSegment(coords)) {
                case 0:
                    if (count == 0) {
                        xs[count] = (int) Math.floor(coords[0]);
                        ys[count] = (int) Math.floor(coords[1]);
                        count++;
                        break;
                    } else {
                        return false;
                    }
                case 1:
                    if (count < 4) {
                        xs[count] = (int) Math.floor(coords[0]);
                        ys[count] = (int) Math.floor(coords[1]);
                        count++;
                        break;
                    } else {
                        return false;
                    }
                case 3:
                    return false;
            }
            iter.next();
        }
        if (count == 4) {
            return xs[0] == xs[1] || xs[0] == xs[2] || ys[0] == ys[1] || ys[0] == ys[3];
        }
        return false;
    }

    @Override // org.sejda.sambox.contentstream.PDFGraphicsStreamEngine
    public void fillAndStrokePath(int windingRule) throws IOException {
        GeneralPath path = (GeneralPath) this.linePath.clone();
        fillPath(windingRule);
        this.linePath = path;
        strokePath();
    }

    @Override // org.sejda.sambox.contentstream.PDFGraphicsStreamEngine
    public void clip(int windingRule) {
        this.clipWindingRule = windingRule;
    }

    @Override // org.sejda.sambox.contentstream.PDFGraphicsStreamEngine
    public void moveTo(float x, float y) {
        this.linePath.moveTo(x, y);
    }

    @Override // org.sejda.sambox.contentstream.PDFGraphicsStreamEngine
    public void lineTo(float x, float y) {
        this.linePath.lineTo(x, y);
    }

    @Override // org.sejda.sambox.contentstream.PDFGraphicsStreamEngine
    public void curveTo(float x1, float y1, float x2, float y2, float x3, float y3) {
        this.linePath.curveTo(x1, y1, x2, y2, x3, y3);
    }

    @Override // org.sejda.sambox.contentstream.PDFGraphicsStreamEngine
    public Point2D getCurrentPoint() {
        return this.linePath.getCurrentPoint();
    }

    @Override // org.sejda.sambox.contentstream.PDFGraphicsStreamEngine
    public void closePath() {
        this.linePath.closePath();
    }

    @Override // org.sejda.sambox.contentstream.PDFGraphicsStreamEngine
    public void endPath() {
        if (this.clipWindingRule != -1) {
            this.linePath.setWindingRule(this.clipWindingRule);
            if (!this.linePath.getPathIterator((AffineTransform) null).isDone()) {
                getGraphicsState().intersectClippingPath(this.linePath);
            }
            this.lastClips = null;
            this.clipWindingRule = -1;
        }
        this.linePath.reset();
    }

    @Override // org.sejda.sambox.contentstream.PDFGraphicsStreamEngine
    public void drawImage(PDImage pdImage) throws IOException {
        if (((pdImage instanceof PDImageXObject) && isHiddenOCG(((PDImageXObject) pdImage).getOptionalContent())) || !isContentRendered() || !isImageContentRendered()) {
            return;
        }
        Matrix ctm = getGraphicsState().getCurrentTransformationMatrix();
        AffineTransform at = ctm.createAffineTransform();
        if (!pdImage.getInterpolate()) {
            BufferedImage bim = pdImage.getImage();
            Matrix m = new Matrix(at);
            boolean isScaledUp = bim.getWidth() < Math.abs(Math.round(m.getScalingFactorX())) || bim.getHeight() < Math.abs(Math.round(m.getScalingFactorY()));
            if (isScaledUp) {
                this.graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            }
        }
        this.graphics.setComposite(getGraphicsState().getNonStrokingJavaComposite());
        setClip();
        if (pdImage.isStencil()) {
            if (getGraphicsState().getNonStrokingColor().getColorSpace() instanceof PDPattern) {
                Paint paint = getNonStrokingPaint();
                Rectangle2D bounds = at.createTransformedShape(new Rectangle2D.Float(0.0f, 0.0f, 1.0f, 1.0f)).getBounds2D();
                int w = (int) Math.ceil(bounds.getWidth());
                int h = (int) Math.ceil(bounds.getHeight());
                BufferedImage renderedPaint = new BufferedImage(w, h, 2);
                Graphics2D g = renderedPaint.getGraphics();
                g.translate(-bounds.getMinX(), -bounds.getMinY());
                g.setPaint(paint);
                g.setRenderingHints(this.graphics.getRenderingHints());
                g.fill(bounds);
                g.dispose();
                BufferedImage mask = pdImage.getImage();
                AffineTransform imageTransform = new AffineTransform(at);
                imageTransform.scale(1.0d / mask.getWidth(), (-1.0d) / mask.getHeight());
                imageTransform.translate(0.0d, -mask.getHeight());
                AffineTransform full = new AffineTransform(g.getTransform());
                full.concatenate(imageTransform);
                Matrix m2 = new Matrix(full);
                double scaleX = Math.abs(m2.getScalingFactorX());
                double scaleY = Math.abs(m2.getScalingFactorY());
                boolean smallMask = mask.getWidth() <= 8 && mask.getHeight() <= 8;
                if (!smallMask) {
                    BufferedImage tmp = new BufferedImage(mask.getWidth(), mask.getHeight(), 1);
                    mask = new LookupOp(getInvLookupTable(), this.graphics.getRenderingHints()).filter(mask, tmp);
                }
                BufferedImage renderedMask = new BufferedImage(w, h, 1);
                Graphics2D g2 = renderedMask.getGraphics();
                g2.translate(-bounds.getMinX(), -bounds.getMinY());
                g2.setRenderingHints(this.graphics.getRenderingHints());
                if (smallMask) {
                    g2.drawImage(mask, imageTransform, (ImageObserver) null);
                } else {
                    while (scaleX < 0.25d) {
                        scaleX *= 2.0d;
                    }
                    while (scaleY < 0.25d) {
                        scaleY *= 2.0d;
                    }
                    int w2 = (int) Math.round(mask.getWidth() * scaleX);
                    int h2 = (int) Math.round(mask.getHeight() * scaleY);
                    if (w2 == 0 || h2 == 0) {
                        LOG.warn("Skipping mask image with 0 dimensions, width: " + w2 + ", height: " + h2);
                    } else {
                        Image scaledMask = mask.getScaledInstance(w2, h2, 4);
                        imageTransform.scale(1.0d / Math.abs(scaleX), 1.0d / Math.abs(scaleY));
                        g2.drawImage(scaledMask, imageTransform, (ImageObserver) null);
                    }
                }
                g2.dispose();
                int[] alphaPixel = null;
                int[] rasterPixel = null;
                WritableRaster raster = renderedPaint.getRaster();
                WritableRaster alpha = renderedMask.getRaster();
                for (int y = 0; y < h; y++) {
                    for (int x = 0; x < w; x++) {
                        alphaPixel = alpha.getPixel(x, y, alphaPixel);
                        rasterPixel = raster.getPixel(x, y, rasterPixel);
                        rasterPixel[3] = alphaPixel[0];
                        raster.setPixel(x, y, rasterPixel);
                    }
                }
                this.graphics.drawImage(renderedPaint, AffineTransform.getTranslateInstance(bounds.getMinX(), bounds.getMinY()), (ImageObserver) null);
            } else {
                BufferedImage image = pdImage.getStencilImage(getNonStrokingPaint());
                drawBufferedImage(image, at);
            }
        } else {
            drawBufferedImage(pdImage.getImage(), at);
        }
        if (!pdImage.getInterpolate()) {
            setRenderingHints();
        }
    }

    private LookupTable getInvLookupTable() {
        if (this.invTable == null) {
            byte[] inv = new byte[PDAnnotation.FLAG_TOGGLE_NO_VIEW];
            for (int i = 0; i < inv.length; i++) {
                inv[i] = (byte) (255 - i);
            }
            this.invTable = new ByteLookupTable(0, inv);
        }
        return this.invTable;
    }

    private void drawBufferedImage(BufferedImage image, AffineTransform at) throws IOException {
        AffineTransform originalTransform = this.graphics.getTransform();
        AffineTransform imageTransform = new AffineTransform(at);
        int width = image.getWidth();
        int height = image.getHeight();
        imageTransform.scale(1.0d / width, (-1.0d) / height);
        imageTransform.translate(0.0d, -height);
        PDSoftMask softMask = getGraphicsState().getSoftMask();
        if (softMask != null) {
            Rectangle2D.Float r0 = new Rectangle2D.Float(0.0f, 0.0f, width, height);
            Paint awtPaint = applySoftMaskToPaint(new TexturePaint(image, r0), softMask);
            this.graphics.setPaint(awtPaint);
            this.graphics.transform(imageTransform);
            this.graphics.fill(r0);
            this.graphics.setTransform(originalTransform);
            return;
        }
        COSBase transfer = getGraphicsState().getTransfer();
        if ((transfer instanceof COSArray) || (transfer instanceof COSDictionary)) {
            image = applyTransferFunction(image, transfer);
        }
        Matrix imageTransformMatrix = new Matrix(imageTransform);
        Matrix graphicsTransformMatrix = new Matrix(originalTransform);
        float scaleX = Math.abs(imageTransformMatrix.getScalingFactorX() * graphicsTransformMatrix.getScalingFactorX());
        float scaleY = Math.abs(imageTransformMatrix.getScalingFactorY() * graphicsTransformMatrix.getScalingFactorY());
        if ((scaleX < 0.5d || scaleY < 0.5d) && RenderingHints.VALUE_RENDER_QUALITY.equals(this.graphics.getRenderingHint(RenderingHints.KEY_RENDERING)) && RenderingHints.VALUE_INTERPOLATION_BICUBIC.equals(this.graphics.getRenderingHint(RenderingHints.KEY_INTERPOLATION))) {
            int w = Math.round(image.getWidth() * scaleX);
            int h = Math.round(image.getHeight() * scaleY);
            if (w < 1 || h < 1) {
                this.graphics.drawImage(image, imageTransform, (ImageObserver) null);
                return;
            }
            Image imageToDraw = image.getScaledInstance(w, h, 4);
            imageTransform.scale((1.0f / w) * image.getWidth(), (1.0f / h) * image.getHeight());
            imageTransform.preConcatenate(originalTransform);
            this.graphics.setTransform(new AffineTransform());
            this.graphics.drawImage(imageToDraw, imageTransform, (ImageObserver) null);
            this.graphics.setTransform(originalTransform);
            return;
        }
        this.graphics.drawImage(image, imageTransform, (ImageObserver) null);
    }

    private BufferedImage applyTransferFunction(BufferedImage image, COSBase transfer) throws IOException {
        BufferedImage bim;
        PDFunction rf;
        PDFunction gf;
        PDFunction bf;
        Integer[] rMap;
        Integer[] gMap;
        Integer[] bMap;
        int ro;
        int go;
        int bo;
        if (image.getColorModel().hasAlpha()) {
            bim = new BufferedImage(image.getWidth(), image.getHeight(), 2);
        } else {
            bim = new BufferedImage(image.getWidth(), image.getHeight(), 1);
        }
        if (transfer instanceof COSArray) {
            COSArray ar = (COSArray) transfer;
            rf = PDFunction.create(ar.getObject(0));
            gf = PDFunction.create(ar.getObject(1));
            bf = PDFunction.create(ar.getObject(2));
            rMap = new Integer[PDAnnotation.FLAG_TOGGLE_NO_VIEW];
            gMap = new Integer[PDAnnotation.FLAG_TOGGLE_NO_VIEW];
            bMap = new Integer[PDAnnotation.FLAG_TOGGLE_NO_VIEW];
        } else {
            rf = PDFunction.create(transfer);
            gf = rf;
            bf = rf;
            rMap = new Integer[PDAnnotation.FLAG_TOGGLE_NO_VIEW];
            gMap = rMap;
            bMap = rMap;
        }
        float[] input = new float[1];
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgb = image.getRGB(x, y);
                int ri = (rgb >> 16) & 255;
                int gi = (rgb >> 8) & 255;
                int bi = rgb & 255;
                if (rMap[ri] != null) {
                    ro = rMap[ri].intValue();
                } else {
                    input[0] = (ri & 255) / 255.0f;
                    ro = (int) (rf.eval(input)[0] * 255.0f);
                    rMap[ri] = Integer.valueOf(ro);
                }
                if (gMap[gi] != null) {
                    go = gMap[gi].intValue();
                } else {
                    input[0] = (gi & 255) / 255.0f;
                    go = (int) (gf.eval(input)[0] * 255.0f);
                    gMap[gi] = Integer.valueOf(go);
                }
                if (bMap[bi] != null) {
                    bo = bMap[bi].intValue();
                } else {
                    input[0] = (bi & 255) / 255.0f;
                    bo = (int) (bf.eval(input)[0] * 255.0f);
                    bMap[bi] = Integer.valueOf(bo);
                }
                bim.setRGB(x, y, (rgb & (-16777216)) | (ro << 16) | (go << 8) | bo);
            }
        }
        return bim;
    }

    @Override // org.sejda.sambox.contentstream.PDFGraphicsStreamEngine
    public void shadingFill(COSName shadingName) throws IOException {
        Area area;
        if (!isContentRendered()) {
            return;
        }
        PDShading shading = getResources().getShading(shadingName);
        if (shading == null) {
            LOG.error("shading " + shadingName + " does not exist in resources dictionary");
            return;
        }
        Matrix ctm = getGraphicsState().getCurrentTransformationMatrix();
        this.graphics.setComposite(getGraphicsState().getNonStrokingJavaComposite());
        Shape savedClip = this.graphics.getClip();
        this.graphics.setClip((Shape) null);
        this.lastClips = null;
        PDRectangle bbox = shading.getBBox();
        if (bbox != null) {
            area = new Area(bbox.transform(ctm));
            area.intersect(getGraphicsState().getCurrentClippingPath());
        } else {
            Rectangle2D bounds = shading.getBounds(new AffineTransform(), ctm);
            if (bounds != null) {
                bounds.add(new Point2D.Double(Math.floor(bounds.getMinX() - 1.0d), Math.floor(bounds.getMinY() - 1.0d)));
                bounds.add(new Point2D.Double(Math.ceil(bounds.getMaxX() + 1.0d), Math.ceil(bounds.getMaxY() + 1.0d)));
                area = new Area(bounds);
                area.intersect(getGraphicsState().getCurrentClippingPath());
            } else {
                area = getGraphicsState().getCurrentClippingPath();
            }
        }
        if (!area.isEmpty()) {
            Paint paint = shading.toPaint(ctm);
            this.graphics.setPaint(applySoftMaskToPaint(paint, getGraphicsState().getSoftMask()));
            this.graphics.fill(area);
        }
        this.graphics.setClip(savedClip);
    }

    @Override // org.sejda.sambox.contentstream.PDFStreamEngine
    public void showAnnotation(PDAnnotation annotation) throws IOException {
        this.lastClips = null;
        int deviceType = -1;
        GraphicsConfiguration graphicsConfiguration = this.graphics.getDeviceConfiguration();
        if (graphicsConfiguration != null && graphicsConfiguration.getDevice() != null) {
            deviceType = graphicsConfiguration.getDevice().getType();
        }
        if (deviceType == 1 && !annotation.isPrinted()) {
            return;
        }
        if ((deviceType == 0 && annotation.isNoView()) || annotation.isHidden()) {
            return;
        }
        if ((annotation.isInvisible() && (annotation instanceof PDAnnotationUnknown)) || isHiddenOCG(annotation.getOptionalContent())) {
            return;
        }
        PDAppearanceDictionary appearance = annotation.getAppearance();
        if (appearance == null || appearance.getNormalAppearance() == null) {
            annotation.constructAppearances();
        }
        if (annotation.isNoRotate() && getCurrentPage().getRotation() != 0) {
            PDRectangle rect = annotation.getRectangle();
            AffineTransform savedTransform = this.graphics.getTransform();
            this.graphics.rotate(Math.toRadians(getCurrentPage().getRotation()), rect.getLowerLeftX(), rect.getUpperRightY());
            super.showAnnotation(annotation);
            this.graphics.setTransform(savedTransform);
            return;
        }
        super.showAnnotation(annotation);
    }

    @Override // org.sejda.sambox.contentstream.PDFStreamEngine
    public void showForm(PDFormXObject form) throws IOException {
        if (!isHiddenOCG(form.getOptionalContent()) && isContentRendered()) {
            GeneralPath savedLinePath = this.linePath;
            this.linePath = new GeneralPath();
            super.showForm(form);
            this.linePath = savedLinePath;
        }
    }

    @Override // org.sejda.sambox.contentstream.PDFStreamEngine
    public void showTransparencyGroup(PDTransparencyGroup form) throws IOException {
        showTransparencyGroupOnGraphics(form, this.graphics);
    }

    protected void showTransparencyGroupOnGraphics(PDTransparencyGroup form, Graphics2D graphics) throws IOException {
        TransparencyGroup group;
        BufferedImage image;
        if (isHiddenOCG(form.getOptionalContent()) || !isContentRendered() || (image = (group = new TransparencyGroup(form, false, getGraphicsState().getCurrentTransformationMatrix(), null)).getImage()) == null) {
            return;
        }
        graphics.setComposite(getGraphicsState().getNonStrokingJavaComposite());
        setClip();
        AffineTransform savedTransform = graphics.getTransform();
        AffineTransform transform = new AffineTransform(this.xform);
        transform.scale(1.0d / this.xformScalingFactorX, 1.0d / this.xformScalingFactorY);
        graphics.setTransform(transform);
        PDRectangle bbox = group.getBBox();
        float x = bbox.getLowerLeftX() - this.pageSize.getLowerLeftX();
        float y = this.pageSize.getUpperRightY() - bbox.getUpperRightY();
        if (this.flipTG) {
            graphics.translate(0, image.getHeight());
            graphics.scale(1.0d, -1.0d);
        } else {
            graphics.translate(x * this.xformScalingFactorX, y * this.xformScalingFactorY);
        }
        PDSoftMask softMask = getGraphicsState().getSoftMask();
        if (softMask != null) {
            Paint awtPaint = applySoftMaskToPaint(new TexturePaint(image, new Rectangle2D.Float(0.0f, 0.0f, image.getWidth(), image.getHeight())), softMask);
            graphics.setPaint(awtPaint);
            graphics.fill(new Rectangle2D.Float(0.0f, 0.0f, bbox.getWidth() * this.xformScalingFactorX, bbox.getHeight() * this.xformScalingFactorY));
        } else {
            try {
                graphics.drawImage(image, (AffineTransform) null, (ImageObserver) null);
            } catch (InternalError ie) {
                LOG.error("Exception drawing image, see JDK-6689349, try rendering into a BufferedImage instead", ie);
            }
        }
        graphics.setTransform(savedTransform);
    }

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/rendering/PageDrawer$TransparencyGroup.class */
    private final class TransparencyGroup {
        private final BufferedImage image;
        private final PDRectangle bbox;
        private final int minX;
        private final int minY;
        private final int maxX;
        private final int maxY;
        private final int width;
        private final int height;

        private TransparencyGroup(PDTransparencyGroup form, boolean isSoftMask, Matrix ctm, PDColor backdropColor) throws IOException {
            Graphics2D savedGraphics = PageDrawer.this.graphics;
            List<Path2D> savedLastClips = PageDrawer.this.lastClips;
            Shape savedInitialClip = PageDrawer.this.initialClip;
            Matrix transform = Matrix.concatenate(ctm, form.getMatrix());
            PDRectangle formBBox = form.getBBox();
            if (formBBox == null) {
                PageDrawer.LOG.warn("transparency group ignored because BBox is null");
                formBBox = new PDRectangle();
            }
            GeneralPath transformedBox = formBBox.transform(transform);
            Area transformed = new Area(transformedBox);
            transformed.intersect(PageDrawer.this.getGraphicsState().getCurrentClippingPath());
            Rectangle2D clipRect = transformed.getBounds2D();
            if (clipRect.isEmpty()) {
                this.image = null;
                this.bbox = null;
                this.minX = 0;
                this.minY = 0;
                this.maxX = 0;
                this.maxY = 0;
                this.width = 0;
                this.height = 0;
                return;
            }
            this.bbox = new PDRectangle((float) clipRect.getX(), (float) clipRect.getY(), (float) clipRect.getWidth(), (float) clipRect.getHeight());
            AffineTransform xformOriginal = PageDrawer.this.xform;
            PageDrawer.this.xform = AffineTransform.getScaleInstance(PageDrawer.this.xformScalingFactorX, PageDrawer.this.xformScalingFactorY);
            Rectangle2D bounds = PageDrawer.this.xform.createTransformedShape(clipRect).getBounds2D();
            this.minX = (int) Math.floor(bounds.getMinX());
            this.minY = (int) Math.floor(bounds.getMinY());
            this.maxX = ((int) Math.floor(bounds.getMaxX())) + 1;
            this.maxY = ((int) Math.floor(bounds.getMaxY())) + 1;
            this.width = this.maxX - this.minX;
            this.height = this.maxY - this.minY;
            if (isGray(form.getGroup().getColorSpace(form.getResources()))) {
                this.image = create2ByteGrayAlphaImage(this.width, this.height);
            } else {
                this.image = new BufferedImage(this.width, this.height, 2);
            }
            boolean needsBackdrop = (isSoftMask || form.getGroup().isIsolated() || !PageDrawer.this.hasBlendMode(form, new HashSet())) ? false : true;
            BufferedImage backdropImage = null;
            int backdropX = 0;
            int backdropY = 0;
            if (needsBackdrop) {
                if (PageDrawer.this.transparencyGroupStack.isEmpty()) {
                    backdropImage = PageDrawer.this.renderer.getPageImage();
                    if (backdropImage == null) {
                        needsBackdrop = false;
                    } else {
                        backdropX = this.minX;
                        backdropY = backdropImage.getHeight() - this.maxY;
                    }
                } else {
                    TransparencyGroup parentGroup = PageDrawer.this.transparencyGroupStack.peek();
                    backdropImage = parentGroup.image;
                    backdropX = this.minX - parentGroup.minX;
                    backdropY = parentGroup.maxY - this.maxY;
                }
            }
            Graphics2D g = this.image.createGraphics();
            if (needsBackdrop) {
                g.drawImage(backdropImage, 0, 0, this.width, this.height, backdropX, backdropY, backdropX + this.width, backdropY + this.height, (ImageObserver) null);
                g = new GroupGraphics(this.image, g);
            }
            if (isSoftMask && backdropColor != null) {
                g.setBackground(new Color(backdropColor.toRGB()));
                g.clearRect(0, 0, this.width, this.height);
            }
            g.translate(0, this.image.getHeight());
            g.scale(1.0d, -1.0d);
            boolean savedFlipTG = PageDrawer.this.flipTG;
            PageDrawer.this.flipTG = false;
            g.transform(PageDrawer.this.xform);
            PDRectangle pageSizeOriginal = PageDrawer.this.pageSize;
            PageDrawer.this.pageSize = new PDRectangle(this.minX / PageDrawer.this.xformScalingFactorX, this.minY / PageDrawer.this.xformScalingFactorY, (float) (bounds.getWidth() / PageDrawer.this.xformScalingFactorX), (float) (bounds.getHeight() / PageDrawer.this.xformScalingFactorY));
            int clipWindingRuleOriginal = PageDrawer.this.clipWindingRule;
            PageDrawer.this.clipWindingRule = -1;
            GeneralPath linePathOriginal = PageDrawer.this.linePath;
            PageDrawer.this.linePath = new GeneralPath();
            g.translate(-clipRect.getX(), -clipRect.getY());
            PageDrawer.this.graphics = g;
            PageDrawer.this.setRenderingHints();
            try {
                if (isSoftMask) {
                    PageDrawer.this.processSoftMask(form);
                } else {
                    PageDrawer.this.transparencyGroupStack.push(this);
                    PageDrawer.this.processTransparencyGroup(form);
                    if (!PageDrawer.this.transparencyGroupStack.isEmpty()) {
                        PageDrawer.this.transparencyGroupStack.pop();
                    }
                }
                if (needsBackdrop) {
                    ((GroupGraphics) PageDrawer.this.graphics).removeBackdrop(backdropImage, backdropX, backdropY);
                }
            } finally {
                PageDrawer.this.flipTG = savedFlipTG;
                PageDrawer.this.lastClips = savedLastClips;
                PageDrawer.this.graphics.dispose();
                PageDrawer.this.graphics = savedGraphics;
                PageDrawer.this.initialClip = savedInitialClip;
                PageDrawer.this.clipWindingRule = clipWindingRuleOriginal;
                PageDrawer.this.linePath = linePathOriginal;
                PageDrawer.this.pageSize = pageSizeOriginal;
                PageDrawer.this.xform = xformOriginal;
            }
        }

        private BufferedImage create2ByteGrayAlphaImage(int width, int height) {
            int[] bandOffsets = {1, 0};
            int bands = bandOffsets.length;
            ComponentColorModel componentColorModel = new ComponentColorModel(ColorSpace.getInstance(1003), true, false, 3, 0);
            WritableRaster raster = Raster.createInterleavedRaster(new DataBufferByte(width * height * bands), width, height, width * bands, bands, bandOffsets, new Point(0, 0));
            return new BufferedImage(componentColorModel, raster, false, (Hashtable) null);
        }

        private boolean isGray(PDColorSpace colorSpace) {
            if (colorSpace instanceof PDDeviceGray) {
                return true;
            }
            if (colorSpace instanceof PDICCBased) {
                try {
                    return ((PDICCBased) colorSpace).getAlternateColorSpace() instanceof PDDeviceGray;
                } catch (IOException e) {
                    return false;
                }
            }
            return false;
        }

        BufferedImage getImage() {
            return this.image;
        }

        PDRectangle getBBox() {
            return this.bbox;
        }

        Rectangle2D getBounds() {
            Rectangle2D.Double r0 = new Rectangle2D.Double(this.minX - (PageDrawer.this.pageSize.getLowerLeftX() * PageDrawer.this.xformScalingFactorX), (((PageDrawer.this.pageSize.getLowerLeftY() + PageDrawer.this.pageSize.getHeight()) * PageDrawer.this.xformScalingFactorY) - this.minY) - this.height, this.width, this.height);
            AffineTransform adjustedTransform = new AffineTransform(PageDrawer.this.xform);
            adjustedTransform.scale(1.0d / PageDrawer.this.xformScalingFactorX, 1.0d / PageDrawer.this.xformScalingFactorY);
            return adjustedTransform.createTransformedShape(r0).getBounds2D();
        }
    }

    private boolean hasBlendMode(PDTransparencyGroup group, Set<COSBase> groupsDone) {
        PDXObject xObject;
        if (groupsDone.contains(group.getCOSObject())) {
            return false;
        }
        groupsDone.add(group.getCOSObject());
        PDResources resources = group.getResources();
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
        for (COSName name2 : resources.getXObjectNames()) {
            try {
                xObject = resources.getXObject(name2);
            } catch (IOException e) {
            }
            if ((xObject instanceof PDTransparencyGroup) && hasBlendMode((PDTransparencyGroup) xObject, groupsDone)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.sejda.sambox.contentstream.PDFStreamEngine
    public void beginMarkedContentSequence(COSName tag, COSDictionary properties) {
        if (this.nestedHiddenOCGCount > 0) {
            this.nestedHiddenOCGCount++;
        } else if (tag != null && getResources() != null && isHiddenOCG(getResources().getProperties(tag))) {
            this.nestedHiddenOCGCount = 1;
        }
    }

    @Override // org.sejda.sambox.contentstream.PDFStreamEngine
    public void endMarkedContentSequence() {
        if (this.nestedHiddenOCGCount > 0) {
            this.nestedHiddenOCGCount--;
        }
    }

    private boolean isContentRendered() {
        return this.nestedHiddenOCGCount <= 0;
    }

    private boolean isHiddenOCG(PDPropertyList propertyList) {
        if (propertyList instanceof PDOptionalContentGroup) {
            PDOptionalContentGroup group = (PDOptionalContentGroup) propertyList;
            PDOptionalContentGroup.RenderState printState = group.getRenderState(this.destination);
            if (printState == null) {
                return !getRenderer().isGroupEnabled(group);
            }
            if (PDOptionalContentGroup.RenderState.OFF.equals(printState)) {
                return true;
            }
            return false;
        }
        if (propertyList instanceof PDOptionalContentMembershipDictionary) {
            return isHiddenOCMD((PDOptionalContentMembershipDictionary) propertyList);
        }
        return false;
    }

    private boolean isHiddenOCMD(PDOptionalContentMembershipDictionary ocmd) {
        if (ocmd.getCOSObject().getCOSArray(COSName.VE) != null) {
            LOG.info("/VE entry ignored in Optional Content Membership Dictionary");
        }
        List<PDPropertyList> oCGs = ocmd.getOCGs();
        if (oCGs.isEmpty()) {
            return false;
        }
        List<Boolean> visibles = new ArrayList<>();
        for (PDPropertyList prop : oCGs) {
            visibles.add(Boolean.valueOf(!isHiddenOCG(prop)));
        }
        COSName visibilityPolicy = ocmd.getVisibilityPolicy();
        if (COSName.ANY_OFF.equals(visibilityPolicy)) {
            Iterator<Boolean> it = visibles.iterator();
            while (it.hasNext()) {
                boolean visible = it.next().booleanValue();
                if (!visible) {
                    return false;
                }
            }
            return true;
        }
        if (COSName.ALL_ON.equals(visibilityPolicy)) {
            Iterator<Boolean> it2 = visibles.iterator();
            while (it2.hasNext()) {
                boolean visible2 = it2.next().booleanValue();
                if (!visible2) {
                    return true;
                }
            }
            return false;
        }
        if (COSName.ALL_OFF.equals(visibilityPolicy)) {
            Iterator<Boolean> it3 = visibles.iterator();
            while (it3.hasNext()) {
                boolean visible3 = it3.next().booleanValue();
                if (visible3) {
                    return true;
                }
            }
            return false;
        }
        Iterator<Boolean> it4 = visibles.iterator();
        while (it4.hasNext()) {
            boolean visible4 = it4.next().booleanValue();
            if (visible4) {
                return false;
            }
        }
        return true;
    }

    public boolean isTextContentRendered() {
        return this.textContentRendered;
    }

    public void setTextContentRendered(boolean textContentRendered) {
        this.textContentRendered = textContentRendered;
    }

    public boolean isImageContentRendered() {
        return this.imageContentRendered;
    }

    public void setImageContentRendered(boolean imageContentRendered) {
        this.imageContentRendered = imageContentRendered;
    }
}
