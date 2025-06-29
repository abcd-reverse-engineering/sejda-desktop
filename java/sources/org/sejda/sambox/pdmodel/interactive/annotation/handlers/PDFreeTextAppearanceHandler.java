package org.sejda.sambox.pdmodel.interactive.annotation.handlers;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.fontbox.util.Charsets;
import org.sejda.io.SeekableSources;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSNumber;
import org.sejda.sambox.input.ContentStreamParser;
import org.sejda.sambox.pdmodel.PDAppearanceContentStream;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDResources;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.font.PDFont;
import org.sejda.sambox.pdmodel.font.PDType1Font;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.pdmodel.graphics.color.PDDeviceCMYK;
import org.sejda.sambox.pdmodel.graphics.color.PDDeviceGray;
import org.sejda.sambox.pdmodel.graphics.color.PDDeviceRGB;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAppearanceStream;
import org.sejda.sambox.pdmodel.interactive.annotation.PDBorderEffectDictionary;
import org.sejda.sambox.pdmodel.interactive.annotation.layout.AppearanceStyle;
import org.sejda.sambox.pdmodel.interactive.annotation.layout.PlainText;
import org.sejda.sambox.pdmodel.interactive.annotation.layout.PlainTextFormatter;
import org.sejda.sambox.pdmodel.interactive.form.PDAcroForm;
import org.sejda.sambox.util.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/annotation/handlers/PDFreeTextAppearanceHandler.class */
public class PDFreeTextAppearanceHandler extends PDAbstractAppearanceHandler {
    private static final Logger LOG = LoggerFactory.getLogger(PDFreeTextAppearanceHandler.class);
    private static final Pattern COLOR_PATTERN = Pattern.compile(".*color\\:\\s*\\#([0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F]).*");
    private float fontSize;
    private COSName fontName;
    private PDDocument document;

    public PDFreeTextAppearanceHandler(PDAnnotation annotation) {
        this(annotation, null);
    }

    public PDFreeTextAppearanceHandler(PDAnnotation annotation, PDDocument document) {
        super(annotation);
        this.fontSize = 10.0f;
        this.fontName = COSName.HELV;
        this.document = document;
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDAppearanceHandler
    public void generateNormalAppearance() {
        PDRectangle borderBox;
        float xOffset;
        float yOffset;
        float clipY;
        PDAcroForm acroForm;
        PDResources defaultResources;
        PDFont defaultResourcesFont;
        PDAnnotationMarkup annotation = (PDAnnotationMarkup) getAnnotation();
        float[] pathsArray = new float[0];
        if (PDAnnotationMarkup.IT_FREE_TEXT_CALLOUT.equals(annotation.getIntent())) {
            pathsArray = annotation.getCallout();
            if (pathsArray == null || (pathsArray.length != 4 && pathsArray.length != 6)) {
                pathsArray = new float[0];
            }
        }
        AnnotationBorder ab = AnnotationBorder.getAnnotationBorder(annotation, annotation.getBorderStyle());
        try {
            PDAppearanceContentStream cs = getNormalAppearanceAsContentStream(true);
            try {
                boolean hasBackground = cs.setNonStrokingColorOnDemand(annotation.getColor());
                setOpacity(cs, annotation.getConstantOpacity());
                PDColor strokingColor = extractNonStrokingColor(annotation);
                boolean hasStroke = cs.setStrokingColorOnDemand(strokingColor);
                PDColor textColor = strokingColor;
                String defaultStyleString = annotation.getDefaultStyleString();
                if (defaultStyleString != null) {
                    Matcher m = COLOR_PATTERN.matcher(defaultStyleString);
                    if (m.find()) {
                        int color = Integer.parseInt(m.group(1), 16);
                        float r = ((color >> 16) & 255) / 255.0f;
                        float g = ((color >> 8) & 255) / 255.0f;
                        float b = (color & 255) / 255.0f;
                        textColor = new PDColor(new float[]{r, g, b}, PDDeviceRGB.INSTANCE);
                    }
                }
                if (ab.dashArray != null) {
                    cs.setLineDashPattern(ab.dashArray, 0.0f);
                }
                cs.setLineWidth(ab.width);
                for (int i = 0; i < pathsArray.length / 2; i++) {
                    float x = pathsArray[i * 2];
                    float y = pathsArray[(i * 2) + 1];
                    if (i == 0) {
                        if (SHORT_STYLES.contains(annotation.getLineEndingStyle())) {
                            float x1 = pathsArray[2];
                            float y1 = pathsArray[3];
                            float len = (float) Math.sqrt(Math.pow(x - x1, 2.0d) + Math.pow(y - y1, 2.0d));
                            if (Float.compare(len, 0.0f) != 0) {
                                x += ((x1 - x) / len) * ab.width;
                                y += ((y1 - y) / len) * ab.width;
                            }
                        }
                        cs.moveTo(x, y);
                    } else {
                        cs.lineTo(x, y);
                    }
                }
                if (pathsArray.length > 0) {
                    cs.stroke();
                }
                if (PDAnnotationMarkup.IT_FREE_TEXT_CALLOUT.equals(annotation.getIntent()) && !"None".equals(annotation.getLineEndingStyle()) && pathsArray.length >= 4) {
                    float x2 = pathsArray[2];
                    float y2 = pathsArray[3];
                    float x12 = pathsArray[0];
                    float y12 = pathsArray[1];
                    cs.saveGraphicsState();
                    if (ANGLED_STYLES.contains(annotation.getLineEndingStyle())) {
                        double angle = Math.atan2(y2 - y12, x2 - x12);
                        cs.transform(Matrix.getRotateInstance(angle, x12, y12));
                    } else {
                        cs.transform(Matrix.getTranslateInstance(x12, y12));
                    }
                    drawStyle(annotation.getLineEndingStyle(), cs, 0.0f, 0.0f, ab.width, hasStroke, hasBackground, false);
                    cs.restoreGraphicsState();
                }
                PDBorderEffectDictionary borderEffect = annotation.getBorderEffect();
                if (borderEffect != null && borderEffect.getStyle().equals(PDBorderEffectDictionary.STYLE_CLOUDY)) {
                    borderBox = applyRectDifferences(getRectangle(), annotation.getRectDifferences());
                    CloudyBorder cloudyBorder = new CloudyBorder(cs, borderEffect.getIntensity(), ab.width, getRectangle());
                    cloudyBorder.createCloudyRectangle(annotation.getRectDifference());
                    annotation.setRectangle(cloudyBorder.getRectangle());
                    annotation.setRectDifference(cloudyBorder.getRectDifference());
                    PDAppearanceStream appearanceStream = annotation.getNormalAppearanceStream();
                    appearanceStream.setBBox(cloudyBorder.getBBox());
                    appearanceStream.setMatrix(cloudyBorder.getMatrix());
                } else {
                    borderBox = applyRectDifferences(getRectangle(), annotation.getRectDifferences());
                    annotation.getNormalAppearanceStream().setBBox(borderBox);
                    PDRectangle paddedRectangle = getPaddedRectangle(borderBox, ab.width / 2.0f);
                    cs.addRect(paddedRectangle.getLowerLeftX(), paddedRectangle.getLowerLeftY(), paddedRectangle.getWidth(), paddedRectangle.getHeight());
                }
                cs.drawShape(ab.width, hasStroke, hasBackground);
                int rotation = annotation.getCOSObject().getInt(COSName.ROTATE, 0);
                cs.transform(Matrix.getRotateInstance(Math.toRadians(rotation), 0.0f, 0.0f));
                float width = (rotation == 90 || rotation == 270) ? borderBox.getHeight() : borderBox.getWidth();
                PDFont font = PDType1Font.HELVETICA();
                float clipWidth = width - (ab.width * 4.0f);
                float clipHeight = (rotation == 90 || rotation == 270) ? borderBox.getWidth() - (ab.width * 4.0f) : borderBox.getHeight() - (ab.width * 4.0f);
                extractFontDetails(annotation);
                if (this.document != null && (acroForm = this.document.getDocumentCatalog().getAcroForm()) != null && (defaultResources = acroForm.getDefaultResources()) != null && (defaultResourcesFont = defaultResources.getFont(this.fontName)) != null) {
                    font = defaultResourcesFont;
                }
                switch (rotation) {
                    case 0:
                    default:
                        xOffset = borderBox.getLowerLeftX() + (ab.width * 2.0f);
                        yOffset = (borderBox.getUpperRightY() - (ab.width * 2.0f)) - (0.7896f * this.fontSize);
                        clipY = borderBox.getLowerLeftY() + (ab.width * 2.0f);
                        break;
                    case 90:
                        xOffset = borderBox.getLowerLeftY() + (ab.width * 2.0f);
                        yOffset = ((-borderBox.getLowerLeftX()) - (ab.width * 2.0f)) - (0.7896f * this.fontSize);
                        clipY = (-borderBox.getUpperRightX()) + (ab.width * 2.0f);
                        break;
                    case 180:
                        xOffset = (-borderBox.getUpperRightX()) + (ab.width * 2.0f);
                        yOffset = ((-borderBox.getLowerLeftY()) - (ab.width * 2.0f)) - (0.7896f * this.fontSize);
                        clipY = (-borderBox.getUpperRightY()) + (ab.width * 2.0f);
                        break;
                    case 270:
                        xOffset = (-borderBox.getUpperRightY()) + (ab.width * 2.0f);
                        yOffset = (borderBox.getUpperRightX() - (ab.width * 2.0f)) - (0.7896f * this.fontSize);
                        clipY = borderBox.getLowerLeftX() + (ab.width * 2.0f);
                        break;
                }
                cs.addRect(xOffset, clipY, clipWidth, clipHeight);
                cs.clip();
                if (annotation.getContents() != null) {
                    cs.beginText();
                    cs.setFont(font, this.fontSize);
                    cs.setNonStrokingColor(textColor.getComponents());
                    AppearanceStyle appearanceStyle = new AppearanceStyle();
                    appearanceStyle.setFont(font);
                    appearanceStyle.setFontSize(this.fontSize);
                    String textContents = (String) Optional.ofNullable(annotation.getContents()).orElse("");
                    PlainTextFormatter formatter = new PlainTextFormatter.Builder(cs).style(appearanceStyle).text(new PlainText(textContents)).width(width - (ab.width * 4.0f)).wrapLines(true).initialOffset(xOffset, yOffset).build();
                    try {
                        try {
                            formatter.format();
                            cs.endText();
                        } catch (IllegalArgumentException ex) {
                            throw new IOException(ex);
                        }
                    } catch (Throwable th) {
                        cs.endText();
                        throw th;
                    }
                }
                if (pathsArray.length > 0) {
                    PDRectangle rect = getRectangle();
                    float minX = Float.MAX_VALUE;
                    float minY = Float.MAX_VALUE;
                    float maxX = Float.MIN_VALUE;
                    float maxY = Float.MIN_VALUE;
                    for (int i2 = 0; i2 < pathsArray.length / 2; i2++) {
                        float x3 = pathsArray[i2 * 2];
                        float y3 = pathsArray[(i2 * 2) + 1];
                        minX = Math.min(minX, x3);
                        minY = Math.min(minY, y3);
                        maxX = Math.max(maxX, x3);
                        maxY = Math.max(maxY, y3);
                    }
                    rect.setLowerLeftX(Math.min(minX - (ab.width * 10.0f), rect.getLowerLeftX()));
                    rect.setLowerLeftY(Math.min(minY - (ab.width * 10.0f), rect.getLowerLeftY()));
                    rect.setUpperRightX(Math.max(maxX + (ab.width * 10.0f), rect.getUpperRightX()));
                    rect.setUpperRightY(Math.max(maxY + (ab.width * 10.0f), rect.getUpperRightY()));
                    annotation.setRectangle(rect);
                    annotation.getNormalAppearanceStream().setBBox(getRectangle());
                }
                if (cs != null) {
                    cs.close();
                }
            } finally {
            }
        } catch (IOException e) {
            LOG.error("Error writing to the content stream", e);
        }
    }

    private PDColor extractNonStrokingColor(PDAnnotationMarkup annotation) {
        PDColor strokingColor = new PDColor(new float[]{0.0f}, PDDeviceGray.INSTANCE);
        String defaultAppearance = annotation.getDefaultAppearance();
        if (defaultAppearance == null) {
            return strokingColor;
        }
        try {
            ContentStreamParser parser = new ContentStreamParser(SeekableSources.inMemorySeekableSourceFrom(defaultAppearance.getBytes(Charsets.US_ASCII)));
            try {
                COSArray arguments = new COSArray();
                COSArray colors = null;
                Operator graphicOp = null;
                while (true) {
                    Object token = parser.nextParsedToken();
                    if (token == null) {
                        break;
                    }
                    if (token instanceof Operator) {
                        Operator op = (Operator) token;
                        String name = op.getName();
                        if (OperatorName.NON_STROKING_GRAY.equals(name) || OperatorName.NON_STROKING_RGB.equals(name) || OperatorName.NON_STROKING_CMYK.equals(name)) {
                            graphicOp = op;
                            colors = arguments;
                        }
                        arguments = new COSArray();
                    } else {
                        arguments.add(((COSBase) token).getCOSObject());
                    }
                }
                if (graphicOp != null) {
                    String graphicOpName = graphicOp.getName();
                    if (OperatorName.NON_STROKING_GRAY.equals(graphicOpName)) {
                        strokingColor = new PDColor(colors, PDDeviceGray.INSTANCE);
                    } else if (OperatorName.NON_STROKING_RGB.equals(graphicOpName)) {
                        strokingColor = new PDColor(colors, PDDeviceRGB.INSTANCE);
                    } else if (OperatorName.NON_STROKING_CMYK.equals(graphicOpName)) {
                        strokingColor = new PDColor(colors, PDDeviceCMYK.INSTANCE);
                    }
                }
                parser.close();
            } finally {
            }
        } catch (IOException ex) {
            LOG.warn("Problem parsing /DA, will use default black", ex);
        }
        return strokingColor;
    }

    private void extractFontDetails(PDAnnotationMarkup annotation) {
        PDAcroForm pdAcroForm;
        String defaultAppearance = annotation.getDefaultAppearance();
        if (defaultAppearance == null && this.document != null && (pdAcroForm = this.document.getDocumentCatalog().getAcroForm()) != null) {
            defaultAppearance = pdAcroForm.getDefaultAppearance();
        }
        if (defaultAppearance == null) {
            return;
        }
        try {
            ContentStreamParser parser = new ContentStreamParser(SeekableSources.inMemorySeekableSourceFrom(defaultAppearance.getBytes(Charsets.US_ASCII)));
            try {
                COSArray arguments = new COSArray();
                COSArray fontArguments = new COSArray();
                while (true) {
                    Object token = parser.nextParsedToken();
                    if (token == null) {
                        break;
                    }
                    if (token instanceof Operator) {
                        Operator op = (Operator) token;
                        String name = op.getName();
                        if (OperatorName.SET_FONT_AND_SIZE.equals(name)) {
                            fontArguments = arguments;
                        }
                        arguments = new COSArray();
                    } else {
                        arguments.add(((COSBase) token).getCOSObject());
                    }
                }
                if (fontArguments.size() >= 2) {
                    COSBase base = fontArguments.get(0);
                    if (base instanceof COSName) {
                        this.fontName = (COSName) base;
                    }
                    COSBase base2 = fontArguments.get(1);
                    if (base2 instanceof COSNumber) {
                        this.fontSize = ((COSNumber) base2).floatValue();
                    }
                }
                parser.close();
            } finally {
            }
        } catch (IOException ex) {
            LOG.warn("Problem parsing /DA, will use default 'Helv 10'", ex);
        }
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDAppearanceHandler
    public void generateRolloverAppearance() {
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDAppearanceHandler
    public void generateDownAppearance() {
    }
}
