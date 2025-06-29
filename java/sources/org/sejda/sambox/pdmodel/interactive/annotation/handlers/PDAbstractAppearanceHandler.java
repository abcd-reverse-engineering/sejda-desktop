package org.sejda.sambox.pdmodel.interactive.annotation.handlers;

import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.Set;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.pdmodel.PDAppearanceContentStream;
import org.sejda.sambox.pdmodel.PDResources;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationLine;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationSquareCircle;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAppearanceDictionary;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAppearanceEntry;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAppearanceStream;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/annotation/handlers/PDAbstractAppearanceHandler.class */
public abstract class PDAbstractAppearanceHandler implements PDAppearanceHandler {
    private final PDAnnotation annotation;
    protected static final Set<String> SHORT_STYLES = createShortStyles();
    static final double ARROW_ANGLE = Math.toRadians(30.0d);
    protected static final Set<String> INTERIOR_COLOR_STYLES = createInteriorColorStyles();
    protected static final Set<String> ANGLED_STYLES = createAngledStyles();

    public PDAbstractAppearanceHandler(PDAnnotation annotation) {
        this.annotation = annotation;
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDAppearanceHandler
    public void generateAppearanceStreams() {
        generateNormalAppearance();
        generateRolloverAppearance();
        generateDownAppearance();
    }

    PDAnnotation getAnnotation() {
        return this.annotation;
    }

    PDColor getColor() {
        return this.annotation.getColor();
    }

    PDRectangle getRectangle() {
        return this.annotation.getRectangle();
    }

    PDAppearanceDictionary getAppearance() {
        PDAppearanceDictionary appearanceDictionary = this.annotation.getAppearance();
        if (appearanceDictionary == null) {
            appearanceDictionary = new PDAppearanceDictionary();
            this.annotation.setAppearance(appearanceDictionary);
        }
        return appearanceDictionary;
    }

    PDAppearanceContentStream getNormalAppearanceAsContentStream() throws IOException {
        return getNormalAppearanceAsContentStream(false);
    }

    PDAppearanceContentStream getNormalAppearanceAsContentStream(boolean compress) throws IOException {
        PDAppearanceEntry appearanceEntry = getNormalAppearance();
        return getAppearanceEntryAsContentStream(appearanceEntry, compress);
    }

    PDAppearanceEntry getDownAppearance() {
        PDAppearanceDictionary appearanceDictionary = getAppearance();
        PDAppearanceEntry downAppearanceEntry = appearanceDictionary.getDownAppearance();
        if (downAppearanceEntry.isSubDictionary()) {
            downAppearanceEntry = new PDAppearanceEntry(new COSStream());
            appearanceDictionary.setDownAppearance(downAppearanceEntry);
        }
        return downAppearanceEntry;
    }

    PDAppearanceEntry getRolloverAppearance() {
        PDAppearanceDictionary appearanceDictionary = getAppearance();
        PDAppearanceEntry rolloverAppearanceEntry = appearanceDictionary.getRolloverAppearance();
        if (rolloverAppearanceEntry.isSubDictionary()) {
            rolloverAppearanceEntry = new PDAppearanceEntry(new COSStream());
            appearanceDictionary.setRolloverAppearance(rolloverAppearanceEntry);
        }
        return rolloverAppearanceEntry;
    }

    PDRectangle getPaddedRectangle(PDRectangle rectangle, float padding) {
        return new PDRectangle(rectangle.getLowerLeftX() + padding, rectangle.getLowerLeftY() + padding, rectangle.getWidth() - (2.0f * padding), rectangle.getHeight() - (2.0f * padding));
    }

    PDRectangle addRectDifferences(PDRectangle rectangle, float[] differences) {
        if (differences == null || differences.length != 4) {
            return rectangle;
        }
        return new PDRectangle(rectangle.getLowerLeftX() - differences[0], rectangle.getLowerLeftY() - differences[1], rectangle.getWidth() + differences[0] + differences[2], rectangle.getHeight() + differences[1] + differences[3]);
    }

    PDRectangle applyRectDifferences(PDRectangle rectangle, float[] differences) {
        if (differences == null || differences.length != 4) {
            return rectangle;
        }
        return new PDRectangle(rectangle.getLowerLeftX() + differences[0], rectangle.getLowerLeftY() + differences[1], (rectangle.getWidth() - differences[0]) - differences[2], (rectangle.getHeight() - differences[1]) - differences[3]);
    }

    void setOpacity(PDAppearanceContentStream contentStream, float opacity) throws IOException {
        if (opacity < 1.0f) {
            PDExtendedGraphicsState gs = new PDExtendedGraphicsState();
            gs.setStrokingAlphaConstant(Float.valueOf(opacity));
            gs.setNonStrokingAlphaConstant(Float.valueOf(opacity));
            contentStream.setGraphicsStateParameters(gs);
        }
    }

    void drawStyle(String style, PDAppearanceContentStream cs, float x, float y, float width, boolean hasStroke, boolean hasBackground, boolean ending) throws IOException {
        int sign = ending ? -1 : 1;
        if (PDAnnotationLine.LE_OPEN_ARROW.equals(style) || PDAnnotationLine.LE_CLOSED_ARROW.equals(style)) {
            drawArrow(cs, x + (sign * width), y, sign * width * 9.0f);
        } else if (PDAnnotationLine.LE_BUTT.equals(style)) {
            cs.moveTo(x, y - (width * 3.0f));
            cs.lineTo(x, y + (width * 3.0f));
        } else if (PDAnnotationLine.LE_DIAMOND.equals(style)) {
            drawDiamond(cs, x, y, width * 3.0f);
        } else if ("Square".equals(style)) {
            cs.addRect(x - (width * 3.0f), y - (width * 3.0f), width * 6.0f, width * 6.0f);
        } else if ("Circle".equals(style)) {
            drawCircle(cs, x, y, width * 3.0f);
        } else if (PDAnnotationLine.LE_R_OPEN_ARROW.equals(style) || PDAnnotationLine.LE_R_CLOSED_ARROW.equals(style)) {
            drawArrow(cs, x + ((-sign) * width), y, (-sign) * width * 9.0f);
        } else if (PDAnnotationLine.LE_SLASH.equals(style)) {
            float width9 = width * 9.0f;
            cs.moveTo(x + ((float) (Math.cos(Math.toRadians(60.0d)) * width9)), y + ((float) (Math.sin(Math.toRadians(60.0d)) * width9)));
            cs.lineTo(x + ((float) (Math.cos(Math.toRadians(240.0d)) * width9)), y + ((float) (Math.sin(Math.toRadians(240.0d)) * width9)));
        }
        if (PDAnnotationLine.LE_R_CLOSED_ARROW.equals(style) || PDAnnotationLine.LE_CLOSED_ARROW.equals(style)) {
            cs.closePath();
        }
        cs.drawShape(width, hasStroke, INTERIOR_COLOR_STYLES.contains(style) && hasBackground);
    }

    void drawArrow(PDAppearanceContentStream cs, float x, float y, float len) throws IOException {
        float armX = x + ((float) (Math.cos(ARROW_ANGLE) * len));
        float armYdelta = (float) (Math.sin(ARROW_ANGLE) * len);
        cs.moveTo(armX, y + armYdelta);
        cs.lineTo(x, y);
        cs.lineTo(armX, y - armYdelta);
    }

    void drawDiamond(PDAppearanceContentStream cs, float x, float y, float r) throws IOException {
        cs.moveTo(x - r, y);
        cs.lineTo(x, y + r);
        cs.lineTo(x + r, y);
        cs.lineTo(x, y - r);
        cs.closePath();
    }

    void drawCircle(PDAppearanceContentStream cs, float x, float y, float r) throws IOException {
        float magic = r * 0.551784f;
        cs.moveTo(x, y + r);
        cs.curveTo(x + magic, y + r, x + r, y + magic, x + r, y);
        cs.curveTo(x + r, y - magic, x + magic, y - r, x, y - r);
        cs.curveTo(x - magic, y - r, x - r, y - magic, x - r, y);
        cs.curveTo(x - r, y + magic, x - magic, y + r, x, y + r);
        cs.closePath();
    }

    void drawCircle2(PDAppearanceContentStream cs, float x, float y, float r) throws IOException {
        float magic = r * 0.551784f;
        cs.moveTo(x, y + r);
        cs.curveTo(x - magic, y + r, x - r, y + magic, x - r, y);
        cs.curveTo(x - r, y - magic, x - magic, y - r, x, y - r);
        cs.curveTo(x + magic, y - r, x + r, y - magic, x + r, y);
        cs.curveTo(x + r, y + magic, x + magic, y + r, x, y + r);
        cs.closePath();
    }

    private static Set<String> createShortStyles() {
        return Set.of(PDAnnotationLine.LE_OPEN_ARROW, PDAnnotationLine.LE_CLOSED_ARROW, "Square", "Circle", PDAnnotationLine.LE_DIAMOND);
    }

    private static Set<String> createInteriorColorStyles() {
        return Set.of(PDAnnotationLine.LE_CLOSED_ARROW, "Circle", PDAnnotationLine.LE_DIAMOND, PDAnnotationLine.LE_R_CLOSED_ARROW, "Square");
    }

    private static Set<String> createAngledStyles() {
        return Set.of(PDAnnotationLine.LE_CLOSED_ARROW, PDAnnotationLine.LE_OPEN_ARROW, PDAnnotationLine.LE_R_CLOSED_ARROW, PDAnnotationLine.LE_R_OPEN_ARROW, PDAnnotationLine.LE_BUTT, PDAnnotationLine.LE_SLASH);
    }

    private PDAppearanceEntry getNormalAppearance() {
        PDAppearanceDictionary appearanceDictionary = getAppearance();
        PDAppearanceEntry normalAppearanceEntry = appearanceDictionary.getNormalAppearance();
        if (normalAppearanceEntry == null || normalAppearanceEntry.isSubDictionary()) {
            normalAppearanceEntry = new PDAppearanceEntry(new COSStream());
            appearanceDictionary.setNormalAppearance(normalAppearanceEntry);
        }
        return normalAppearanceEntry;
    }

    private PDAppearanceContentStream getAppearanceEntryAsContentStream(PDAppearanceEntry appearanceEntry, boolean compress) throws IOException {
        PDAppearanceStream appearanceStream = appearanceEntry.getAppearanceStream();
        setTransformationMatrix(appearanceStream);
        PDResources resources = appearanceStream.getResources();
        if (resources == null) {
            PDResources resources2 = new PDResources();
            appearanceStream.setResources(resources2);
        }
        return new PDAppearanceContentStream(appearanceStream, compress);
    }

    private void setTransformationMatrix(PDAppearanceStream appearanceStream) {
        PDRectangle bbox = getRectangle();
        if (bbox == null) {
            return;
        }
        appearanceStream.setBBox(bbox);
        AffineTransform transform = AffineTransform.getTranslateInstance(-bbox.getLowerLeftX(), -bbox.getLowerLeftY());
        appearanceStream.setMatrix(transform);
    }

    PDRectangle handleBorderBox(PDAnnotationSquareCircle annotation, float lineWidth) {
        PDRectangle borderBox;
        float[] rectDifferences = annotation.getRectDifferences();
        if (getRectangle() == null) {
            return null;
        }
        if (rectDifferences.length == 0) {
            borderBox = getPaddedRectangle(getRectangle(), lineWidth / 2.0f);
            annotation.setRectDifferences(lineWidth / 2.0f);
            annotation.setRectangle(addRectDifferences(getRectangle(), annotation.getRectDifferences()));
            PDRectangle rect = getRectangle();
            PDAppearanceStream appearanceStream = annotation.getNormalAppearanceStream();
            AffineTransform transform = AffineTransform.getTranslateInstance(-rect.getLowerLeftX(), -rect.getLowerLeftY());
            appearanceStream.setBBox(rect);
            appearanceStream.setMatrix(transform);
        } else {
            PDRectangle borderBox2 = applyRectDifferences(getRectangle(), rectDifferences);
            borderBox = getPaddedRectangle(borderBox2, lineWidth / 2.0f);
        }
        return borderBox;
    }
}
