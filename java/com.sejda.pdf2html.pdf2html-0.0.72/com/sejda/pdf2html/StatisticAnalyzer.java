package com.sejda.pdf2html;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.font.PDFont;
import org.sejda.sambox.pdmodel.font.PDFontDescriptor;
import org.sejda.sambox.text.TextPosition;

/* loaded from: com.sejda.pdf2html.pdf2html-0.0.72.jar:com/sejda/pdf2html/StatisticAnalyzer.class */
public class StatisticAnalyzer extends LocalPDFTextStripper {
    private static final int FLAG_FIXED_PITCH = 1;
    private static final int FLAG_SERIF = 2;
    private static final int FLAG_SYMBOLIC = 4;
    private static final int FLAG_SCRIPT = 8;
    private static final int FLAG_NON_SYMBOLIC = 32;
    private static final int FLAG_ITALIC = 64;
    private static final int FLAG_ALL_CAP = 65536;
    private static final int FLAG_SMALL_CAP = 131072;
    private static final int FLAG_FORCE_BOLD = 262144;
    private float averangeLeftMargin;
    private float averangeRightMargin;
    public static final double italicScaleThreshold = 1.0d;
    private int pages = 0;
    private float lines = 0.0f;
    private Multiset<Float> leftMargin = HashMultiset.create();
    private Map<Integer, Multiset<Float>> leftMarginPerPage = new HashMap();
    private Multiset<Float> rightMargin = HashMultiset.create();
    private float averangeLine = 0.0f;
    private Multiset<Float> linesFontSize = HashMultiset.create();
    private Multiset<Float> lineSpacing = HashMultiset.create();
    private float averangeLineSpacing = 0.0f;
    private Multiset<Float> lastLine = HashMultiset.create();
    private Multiset<Float> fontWeight = HashMultiset.create();
    private float averangeLastLine = 0.0f;
    private float averangeFontSize = 0.0f;
    private float averangeFontWeight = 0.0f;
    private float prevLineY = -1.0f;

    protected void startPage(PDPage page) throws IOException {
        this.pages += FLAG_FIXED_PITCH;
        this.prevLineY = -1.0f;
    }

    @Override // com.sejda.pdf2html.LocalPDFTextStripper
    protected void writeLineStart(List<TextPosition> line) {
        if (isLineEmpty(line)) {
            return;
        }
        this.lines += 1.0f;
        float lineY = getFirstTrimmed(line).getY();
        if (this.prevLineY >= 0.0f) {
            incrementOrAdd(this.lineSpacing, lineY - this.prevLineY);
        }
        this.prevLineY = lineY;
        float start = getFirstTrimmed(line).getX();
        incrementOrAdd(this.leftMargin, start);
        if (!this.leftMarginPerPage.containsKey(Integer.valueOf(getCurrentPageNo()))) {
            this.leftMarginPerPage.put(Integer.valueOf(getCurrentPageNo()), HashMultiset.create());
        }
        incrementOrAdd(this.leftMarginPerPage.get(Integer.valueOf(getCurrentPageNo())), start);
        TextPosition lastTrimmed = getLastTrimmed(line);
        float end = lastTrimmed.getX() + lastTrimmed.getWidth();
        incrementOrAdd(this.rightMargin, end);
        for (TextPosition t : line) {
            PDFont font = t.getFont();
            if (font != null && font.getFontDescriptor() != null) {
                incrementOrAdd(this.fontWeight, font.getFontDescriptor().getFontWeight());
            }
            Float fontSize = Float.valueOf(t.getFontSizeInPt());
            if (fontSize.floatValue() > 0.0f) {
                incrementOrAdd(this.linesFontSize, fontSize.floatValue());
            }
        }
    }

    protected void endPage(PDPage page) throws IOException {
        if (this.prevLineY >= 0.0f) {
            incrementOrAdd(this.lastLine, this.prevLineY);
        }
    }

    private void incrementOrAdd(Multiset<Float> multiset, float key) {
        multiset.add(Float.valueOf(key));
    }

    @Override // org.sejda.sambox.tools.SamboxPDFText2HTML
    public void endDocument(PDDocument pdf) throws IOException {
        this.averangeLine = this.lines / this.pages;
        this.averangeLeftMargin = findMax(this.leftMargin);
        this.averangeRightMargin = findMax(this.rightMargin);
        this.averangeFontSize = findMax(this.linesFontSize);
        this.averangeLineSpacing = findMax(this.lineSpacing);
        this.averangeLastLine = findMax(this.lastLine);
        this.averangeFontWeight = findMax(this.fontWeight);
    }

    private float findMax(Multiset<Float> multiset) {
        float actual = 0.0f;
        int max = -1;
        for (Float k : multiset) {
            int count = multiset.count(k);
            if (count > max) {
                max = count;
                actual = k.floatValue();
            }
        }
        return actual;
    }

    public float getPages() {
        return this.pages;
    }

    public float getLines() {
        return this.lines;
    }

    public float getAverangeLines() {
        return this.averangeLine;
    }

    public float getAverangeLeftMargin() {
        return this.averangeLeftMargin;
    }

    public float getAverangeLeftMarginPerPage(int page) {
        if (this.leftMarginPerPage.containsKey(Integer.valueOf(page))) {
            return findMax(this.leftMarginPerPage.get(Integer.valueOf(page)));
        }
        return 0.0f;
    }

    public float getAverangeRightMargin() {
        return this.averangeRightMargin;
    }

    public float getAverangeFontSize() {
        return this.averangeFontSize;
    }

    public float getAverangeLineSpacing() {
        return this.averangeLineSpacing;
    }

    public float getAverangeLastLine() {
        return this.averangeLastLine;
    }

    public float getAverangeFontWeight() {
        return this.averangeFontWeight;
    }

    protected void startArticle() throws IOException {
    }

    @Override // org.sejda.sambox.tools.SamboxPDFText2HTML
    protected void startArticle(boolean isltr) throws IOException {
    }

    @Override // org.sejda.sambox.tools.SamboxPDFText2HTML
    protected void endArticle() throws IOException {
    }

    protected void writeCharacters(TextPosition text) throws IOException {
    }

    @Override // org.sejda.sambox.tools.SamboxPDFText2HTML
    protected void writeHeader() throws IOException {
    }

    protected void writeLineSeparator() throws IOException {
    }

    protected void writePageEnd() throws IOException {
    }

    protected void writePageStart() throws IOException {
    }

    @Override // org.sejda.sambox.tools.SamboxPDFText2HTML
    protected void writeParagraphEnd() throws IOException {
    }

    protected void writeParagraphSeparator() throws IOException {
    }

    protected void writeParagraphStart() throws IOException {
    }

    @Override // org.sejda.sambox.tools.SamboxPDFText2HTML
    protected void writeString(String chars) throws IOException {
    }

    protected void writeWordSeparator() throws IOException {
    }

    public boolean isItalic(PDFontDescriptor descriptor) {
        if (descriptor == null) {
            return false;
        }
        if (descriptor.getItalicAngle() != 0.0f || (descriptor.getFlags() & FLAG_ITALIC) == FLAG_ITALIC) {
            return true;
        }
        if (descriptor.getFontName() != null && descriptor.getFontName().indexOf("Italic") > -1) {
            return true;
        }
        return false;
    }

    public boolean isBold(PDFontDescriptor descriptor) {
        if (descriptor == null) {
            return false;
        }
        if (descriptor.getFontWeight() > this.averangeFontWeight || (descriptor.getFlags() & FLAG_FORCE_BOLD) == FLAG_FORCE_BOLD) {
            return true;
        }
        if (descriptor.getFontName() != null && descriptor.getFontName().indexOf("Bold") > -1) {
            return true;
        }
        return false;
    }

    public boolean isItalic(TextPosition text) {
        if (isItalic(text.getFont().getFontDescriptor())) {
            return true;
        }
        return false;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Pages=").append(this.pages);
        builder.append("\nlines=").append(this.lines);
        builder.append("\naverangeLineSpacing=").append(this.averangeLineSpacing).append(" #lineSpacing=").append(this.lineSpacing.size()).append('x').append(this.lineSpacing.count(Float.valueOf(this.averangeLineSpacing)));
        builder.append("\naverangeLastLine=").append(this.averangeLastLine).append(" #lastLine=").append(this.lastLine.size()).append('x').append(this.lastLine.count(Float.valueOf(this.averangeLastLine)));
        builder.append("\naverangeLine=").append(this.averangeLine);
        builder.append("\naverangeLeftMargin=").append(this.averangeLeftMargin).append(", #leftMargin=").append(this.leftMargin.size()).append('x').append(this.leftMargin.count(Float.valueOf(this.averangeLeftMargin)));
        builder.append("\naverangeRightMargin=").append(this.averangeRightMargin).append(" #rightMargin=").append(this.rightMargin.size()).append('x').append(this.rightMargin.count(Float.valueOf(this.averangeRightMargin)));
        builder.append("\naverangeFontSize=").append(this.averangeFontSize).append(" #linesFontSize=").append(this.linesFontSize.size()).append('x').append(this.linesFontSize.count(Float.valueOf(this.averangeFontSize)));
        return builder.toString();
    }
}
