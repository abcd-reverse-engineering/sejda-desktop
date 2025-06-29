package org.sejda.sambox.text;

import java.awt.geom.Rectangle2D;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.sejda.sambox.pdmodel.font.PDFont;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.pdmodel.graphics.state.RenderingMode;
import org.sejda.sambox.util.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/text/TextPosition.class */
public class TextPosition {
    private static final Logger LOG = LoggerFactory.getLogger(TextPosition.class);
    private static final Map<Integer, String> DIACRITICS = createDiacritics();
    private final Matrix textMatrix;
    private final float endX;
    private final float endY;
    private final float maxHeight;
    private final int rotation;
    private final float x;
    private final float y;
    private final float pageHeight;
    private final float pageWidth;
    private final float widthOfSpace;
    private final int[] charCodes;
    private final PDFont font;
    private final float fontSize;
    private final int fontSizePt;
    private final PDColor color;
    private final RenderingMode renderingMode;
    private float[] widths;
    private String unicode;
    private float direction;

    public TextPosition(int pageRotation, float pageWidth, float pageHeight, Matrix textMatrix, float endX, float endY, float maxHeight, float individualWidth, float spaceWidth, String unicode, int[] charCodes, PDFont font, float fontSize, int fontSizeInPt) {
        this(pageRotation, pageWidth, pageHeight, textMatrix, endX, endY, maxHeight, individualWidth, spaceWidth, unicode, charCodes, font, fontSize, fontSizeInPt, null, null);
    }

    public TextPosition(int pageRotation, float pageWidth, float pageHeight, Matrix textMatrix, float endX, float endY, float maxHeight, float individualWidth, float spaceWidth, String unicode, int[] charCodes, PDFont font, float fontSize, int fontSizeInPt, PDColor color, RenderingMode renderingMode) {
        this.direction = -1.0f;
        this.textMatrix = textMatrix;
        this.endX = endX;
        this.endY = endY;
        this.rotation = pageRotation;
        this.maxHeight = maxHeight;
        this.pageHeight = pageHeight;
        this.pageWidth = pageWidth;
        this.widths = new float[]{individualWidth};
        this.widthOfSpace = spaceWidth;
        this.unicode = unicode;
        this.charCodes = charCodes;
        this.font = font;
        this.fontSize = fontSize;
        this.fontSizePt = fontSizeInPt;
        this.color = color;
        this.renderingMode = renderingMode;
        this.x = getXRot(this.rotation);
        if (this.rotation == 0 || this.rotation == 180) {
            this.y = this.pageHeight - getYLowerLeftRot(this.rotation);
        } else {
            this.y = this.pageWidth - getYLowerLeftRot(this.rotation);
        }
    }

    private static Map<Integer, String> createDiacritics() {
        Map<Integer, String> map = new HashMap<>(31);
        map.put(96, "̀");
        map.put(715, "̀");
        map.put(39, "́");
        map.put(697, "́");
        map.put(714, "́");
        map.put(94, "̂");
        map.put(710, "̂");
        map.put(126, "̃");
        map.put(713, "̄");
        map.put(176, "̊");
        map.put(698, "̋");
        map.put(711, "̌");
        map.put(712, "̍");
        map.put(34, "̎");
        map.put(699, "̒");
        map.put(700, "̓");
        map.put(1158, "̓");
        map.put(1370, "̓");
        map.put(701, "̔");
        map.put(1157, "̔");
        map.put(1369, "̔");
        map.put(724, "̝");
        map.put(725, "̞");
        map.put(726, "̟");
        map.put(727, "̠");
        map.put(690, "̡");
        map.put(716, "̩");
        map.put(695, "̫");
        map.put(717, "̱");
        map.put(95, "̲");
        map.put(8270, "͙");
        return map;
    }

    public String getUnicode() {
        return this.unicode;
    }

    public String getVisuallyOrderedUnicode() {
        String text = getUnicode();
        int length = text.length();
        int i = 0;
        while (true) {
            int index = i;
            if (index < length) {
                int codePoint = text.codePointAt(index);
                int nextIndex = index + Character.charCount(codePoint);
                byte directionality = Character.getDirectionality(codePoint);
                if ((directionality == 2 || directionality == 1) && (index != 0 || nextIndex < length)) {
                    break;
                }
                i = nextIndex;
            } else {
                return text;
            }
        }
        return new StringBuilder(text).reverse().toString();
    }

    public int[] getCharacterCodes() {
        return this.charCodes;
    }

    public Matrix getTextMatrix() {
        return this.textMatrix;
    }

    public float getDir() {
        if (this.direction < 0.0f) {
            float a = this.textMatrix.getScaleY();
            float b = this.textMatrix.getShearY();
            float c = this.textMatrix.getShearX();
            float d = this.textMatrix.getScaleX();
            if (a > 0.0f && Math.abs(b) < d && Math.abs(c) < a && d > 0.0f) {
                this.direction = 0.0f;
            } else if (a < 0.0f && Math.abs(b) < Math.abs(d) && Math.abs(c) < Math.abs(a) && d < 0.0f) {
                this.direction = 180.0f;
            } else if (Math.abs(a) < Math.abs(c) && b > 0.0f && c < 0.0f && Math.abs(d) < b) {
                this.direction = 90.0f;
            } else if (Math.abs(a) < c && b < 0.0f && c > 0.0f && Math.abs(d) < Math.abs(b)) {
                this.direction = 270.0f;
            } else {
                this.direction = 0.0f;
            }
        }
        return this.direction;
    }

    private float getXRot(float rotation) {
        if (rotation == 0.0f) {
            return this.textMatrix.getTranslateX();
        }
        if (rotation == 90.0f) {
            return this.textMatrix.getTranslateY();
        }
        if (rotation == 180.0f) {
            return this.pageWidth - this.textMatrix.getTranslateX();
        }
        if (rotation == 270.0f) {
            return this.pageHeight - this.textMatrix.getTranslateY();
        }
        return 0.0f;
    }

    public float getX() {
        return this.x;
    }

    public float getXDirAdj() {
        return getXRot(getDir());
    }

    private float getYLowerLeftRot(float rotation) {
        if (rotation == 0.0f) {
            return this.textMatrix.getTranslateY();
        }
        if (rotation == 90.0f) {
            return this.pageWidth - this.textMatrix.getTranslateX();
        }
        if (rotation == 180.0f) {
            return this.pageHeight - this.textMatrix.getTranslateY();
        }
        if (rotation == 270.0f) {
            return this.textMatrix.getTranslateX();
        }
        return 0.0f;
    }

    public float getY() {
        return this.y;
    }

    public float getYDirAdj() {
        float dir = getDir();
        if (dir == 0.0f || dir == 180.0f) {
            return this.pageHeight - getYLowerLeftRot(dir);
        }
        return this.pageWidth - getYLowerLeftRot(dir);
    }

    private float getWidthRot(float rotation) {
        if (rotation == 90.0f || rotation == 270.0f) {
            return Math.abs(this.endY - this.textMatrix.getTranslateY());
        }
        return Math.abs(this.endX - this.textMatrix.getTranslateX());
    }

    public float getWidth() {
        return getWidthRot(this.rotation);
    }

    public float getWidthDirAdj() {
        return getWidthRot(getDir());
    }

    public float getHeight() {
        return this.maxHeight;
    }

    public float getHeightDir() {
        return this.maxHeight;
    }

    public float getFontSize() {
        return this.fontSize;
    }

    public float getFontSizeInPt() {
        return this.fontSizePt;
    }

    public PDFont getFont() {
        return this.font;
    }

    public float getWidthOfSpace() {
        return this.widthOfSpace;
    }

    public float getXScale() {
        return this.textMatrix.getScalingFactorX();
    }

    public float getYScale() {
        return this.textMatrix.getScalingFactorY();
    }

    public float[] getIndividualWidths() {
        return this.widths;
    }

    public boolean contains(TextPosition tp2) {
        double thisXstart = getXDirAdj();
        double thisWidth = getWidthDirAdj();
        double thisXend = thisXstart + thisWidth;
        double tp2Xstart = tp2.getXDirAdj();
        double tp2Xend = tp2Xstart + tp2.getWidthDirAdj();
        if (tp2Xend <= thisXstart || tp2Xstart >= thisXend) {
            return false;
        }
        double thisYstart = getYDirAdj();
        double tp2Ystart = tp2.getYDirAdj();
        if (tp2Ystart + tp2.getHeightDir() < thisYstart || tp2Ystart > thisYstart + getHeightDir()) {
            return false;
        }
        if (tp2Xstart > thisXstart && tp2Xend > thisXend) {
            double overlap = thisXend - tp2Xstart;
            double overlapPercent = overlap / thisWidth;
            return overlapPercent > 0.15d;
        }
        if (tp2Xstart < thisXstart && tp2Xend < thisXend) {
            double overlap2 = tp2Xend - thisXstart;
            double overlapPercent2 = overlap2 / thisWidth;
            return overlapPercent2 > 0.15d;
        }
        return true;
    }

    public void mergeDiacritic(TextPosition diacritic) {
        if (diacritic.getUnicode().length() > 1) {
            return;
        }
        float diacXStart = diacritic.getXDirAdj();
        float diacXEnd = diacXStart + diacritic.widths[0];
        float currCharXStart = getXDirAdj();
        int strLen = this.unicode.length();
        boolean wasAdded = false;
        for (int i = 0; i < strLen && !wasAdded; i++) {
            if (i >= this.widths.length) {
                LOG.info("diacritic " + diacritic.getUnicode() + " on ligature " + this.unicode + " is not supported yet and is ignored (PDFBOX-2831)");
                return;
            }
            float currCharXEnd = currCharXStart + this.widths[i];
            if (diacXStart < currCharXStart && diacXEnd <= currCharXEnd) {
                if (i == 0) {
                    insertDiacritic(i, diacritic);
                } else {
                    float distanceOverlapping1 = diacXEnd - currCharXStart;
                    float percentage1 = distanceOverlapping1 / this.widths[i];
                    float distanceOverlapping2 = currCharXStart - diacXStart;
                    float percentage2 = distanceOverlapping2 / this.widths[i - 1];
                    if (percentage1 >= percentage2) {
                        insertDiacritic(i, diacritic);
                    } else {
                        insertDiacritic(i - 1, diacritic);
                    }
                }
                wasAdded = true;
            } else if (diacXStart < currCharXStart) {
                insertDiacritic(i, diacritic);
                wasAdded = true;
            } else if (diacXEnd <= currCharXEnd) {
                insertDiacritic(i, diacritic);
                wasAdded = true;
            } else if (i == strLen - 1) {
                insertDiacritic(i, diacritic);
                wasAdded = true;
            }
            currCharXStart += this.widths[i];
        }
    }

    private void insertDiacritic(int i, TextPosition diacritic) {
        StringBuilder sb = new StringBuilder();
        sb.append((CharSequence) this.unicode, 0, i);
        float[] widths2 = new float[this.widths.length + 1];
        System.arraycopy(this.widths, 0, widths2, 0, i);
        sb.append(this.unicode.charAt(i));
        widths2[i] = this.widths[i];
        sb.append(combineDiacritic(diacritic.getUnicode()));
        widths2[i + 1] = 0.0f;
        sb.append(this.unicode.substring(i + 1));
        System.arraycopy(this.widths, i + 1, widths2, i + 2, (this.widths.length - i) - 1);
        this.unicode = sb.toString();
        this.widths = widths2;
    }

    private String combineDiacritic(String str) {
        int codePoint = str.codePointAt(0);
        if (DIACRITICS.containsKey(Integer.valueOf(codePoint))) {
            return DIACRITICS.get(Integer.valueOf(codePoint));
        }
        return Normalizer.normalize(str, Normalizer.Form.NFKC).trim();
    }

    public boolean isDiacritic() {
        String text = getUnicode();
        if (text.length() != 1 || "ー".equals(text)) {
            return false;
        }
        int type = Character.getType(text.charAt(0));
        return type == 6 || type == 27 || type == 4;
    }

    public boolean isVisible() {
        Rectangle2D.Float rectangle = new Rectangle2D.Float(0.0f, 0.0f, this.pageWidth, this.pageHeight);
        if (this.rotation == 90 || this.rotation == 270) {
            rectangle = new Rectangle2D.Float(0.0f, 0.0f, this.pageHeight, this.pageWidth);
        }
        return rectangle.contains(getX(), getY());
    }

    public String toString() {
        return getUnicode();
    }

    public float getEndX() {
        return this.endX;
    }

    public float getEndY() {
        return this.endY;
    }

    public int getRotation() {
        return this.rotation;
    }

    public float getPageHeight() {
        return this.pageHeight;
    }

    public float getPageWidth() {
        return this.pageWidth;
    }

    public PDColor getColor() {
        return this.color;
    }

    public RenderingMode getRenderingMode() {
        return this.renderingMode;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TextPosition)) {
            return false;
        }
        TextPosition that = (TextPosition) o;
        if (Float.compare(that.endX, this.endX) != 0 || Float.compare(that.endY, this.endY) != 0 || Float.compare(that.maxHeight, this.maxHeight) != 0 || this.rotation != that.rotation || Float.compare(that.x, this.x) != 0 || Float.compare(that.y, this.y) != 0 || Float.compare(that.pageHeight, this.pageHeight) != 0 || Float.compare(that.pageWidth, this.pageWidth) != 0 || Float.compare(that.widthOfSpace, this.widthOfSpace) != 0 || Float.compare(that.fontSize, this.fontSize) != 0 || this.fontSizePt != that.fontSizePt || !Objects.equals(this.textMatrix, that.textMatrix) || !Arrays.equals(this.charCodes, that.charCodes) || !Objects.equals(this.renderingMode, that.renderingMode) || !Objects.equals(this.color, that.color)) {
            return false;
        }
        return Objects.equals(this.font, that.font);
    }

    public int hashCode() {
        int result = this.textMatrix != null ? this.textMatrix.hashCode() : 0;
        return (31 * ((31 * ((31 * ((31 * ((31 * ((31 * ((31 * ((31 * ((31 * ((31 * ((31 * ((31 * ((31 * ((31 * ((31 * result) + Float.floatToIntBits(this.endX))) + Float.floatToIntBits(this.endY))) + Float.floatToIntBits(this.maxHeight))) + this.rotation)) + Float.floatToIntBits(this.x))) + Float.floatToIntBits(this.y))) + Float.floatToIntBits(this.pageHeight))) + Float.floatToIntBits(this.pageWidth))) + Float.floatToIntBits(this.widthOfSpace))) + Arrays.hashCode(this.charCodes))) + (this.font != null ? this.font.hashCode() : 0))) + Float.floatToIntBits(this.fontSize))) + this.fontSizePt)) + (this.color != null ? this.color.hashCode() : 0))) + (this.renderingMode != null ? this.renderingMode.hashCode() : 0);
    }
}
