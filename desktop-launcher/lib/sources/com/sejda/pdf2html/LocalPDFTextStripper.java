package com.sejda.pdf2html;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.PDPageTree;
import org.sejda.sambox.text.TextPosition;
import org.sejda.sambox.text.TextPositionComparator;
import org.sejda.sambox.tools.SamboxPDFText2HTML;
import org.sejda.sambox.util.Matrix;

/* loaded from: com.sejda.pdf2html.pdf2html-0.0.72.jar:com/sejda/pdf2html/LocalPDFTextStripper.class */
public class LocalPDFTextStripper extends SamboxPDFText2HTML {
    private static final float ENDOFLASTTEXTX_RESET_VALUE = -1.0f;
    private static final float MAXYFORLINE_RESET_VALUE = -3.4028235E38f;
    private static final float EXPECTEDSTARTOFNEXTWORDX_RESET_VALUE = -3.4028235E38f;
    private static final float MAXHEIGHTFORLINE_RESET_VALUE = -1.0f;
    private static final float MINYTOPFORLINE_RESET_VALUE = Float.MAX_VALUE;
    private static final float LASTWORDSPACING_RESET_VALUE = -1.0f;
    private boolean onFirstPage = true;
    private boolean isOCRProduced = false;
    private int maxPages = Integer.MAX_VALUE;
    private PDPageTree pages;

    public void setOCRProduced(boolean isOCR) {
        this.isOCRProduced = isOCR;
    }

    public void setMaxPages(int maxPages) {
        this.maxPages = maxPages;
    }

    @Override // org.sejda.sambox.text.PDFTextStripper
    protected void processPages(PDPageTree pages) throws IOException {
        this.pages = pages;
        super.processPages(pages);
    }

    @Override // org.sejda.sambox.text.PDFTextStripper, org.sejda.sambox.text.PDFTextStreamEngine, org.sejda.sambox.contentstream.PDFStreamEngine
    public void processPage(PDPage page) throws IOException {
        if (this.pages.indexOf(page) < this.maxPages) {
            super.processPage(page);
        }
    }

    @Override // org.sejda.sambox.tools.SamboxPDFText2HTML, org.sejda.sambox.text.PDFTextStripper
    protected void writePage() throws IOException {
        float positionX;
        float positionY;
        float positionWidth;
        float positionHeight;
        float deltaSpace;
        float averageCharWidth;
        if (this.onFirstPage) {
            writeHeader();
            this.onFirstPage = false;
        }
        float maxYForLine = -3.4028235E38f;
        float minYTopForLine = Float.MAX_VALUE;
        float endOfLastTextX = -1.0f;
        float lastWordSpacing = -1.0f;
        float maxHeightForLine = -1.0f;
        PositionWrapper lastPosition = null;
        boolean startOfPage = true;
        if (this.charactersByArticle.size() > 0) {
            writePageStart();
        }
        for (int i = 0; i < this.charactersByArticle.size(); i++) {
            List<TextPosition> textList = this.charactersByArticle.get(i);
            if (getSortByPosition()) {
                TextPositionComparator comparator = new TextPositionComparator();
                Collections.sort(textList, comparator);
            }
            Iterator<TextPosition> textIter = textList.iterator();
            int ltrCnt = 0;
            int rtlCnt = 0;
            while (textIter.hasNext()) {
                String stringValue = textIter.next().getUnicode();
                for (int a = 0; a < stringValue.length(); a++) {
                    byte dir = Character.getDirectionality(stringValue.charAt(a));
                    if (dir == 0 || dir == 14 || dir == 15) {
                        ltrCnt++;
                    } else if (dir == 1 || dir == 2 || dir == 16 || dir == 17) {
                        rtlCnt++;
                    }
                }
            }
            boolean isRtlDominant = rtlCnt > ltrCnt;
            startArticle(!isRtlDominant);
            boolean startOfArticle = true;
            boolean hasRtl = rtlCnt > 0;
            List<TextPosition> line = new ArrayList<>();
            Iterator<TextPosition> textIter2 = textList.iterator();
            float f = -1.0f;
            while (true) {
                float previousAveCharWidth = f;
                if (!textIter2.hasNext()) {
                    break;
                }
                TextPosition position = textIter2.next();
                PositionWrapper current = new PositionWrapper(position);
                String characterValue = position.getUnicode();
                if (lastPosition != null && (position.getFont() != lastPosition.getTextPosition().getFont() || position.getFontSizeInPt() != lastPosition.getTextPosition().getFontSizeInPt())) {
                    previousAveCharWidth = -1.0f;
                }
                if (getSortByPosition()) {
                    positionX = position.getXDirAdj();
                    positionY = position.getYDirAdj();
                    positionWidth = position.getWidthDirAdj();
                    positionHeight = position.getHeightDir();
                } else {
                    positionX = position.getX();
                    positionY = position.getY();
                    positionWidth = position.getWidth();
                    positionHeight = position.getHeight();
                }
                int wordCharCount = position.getIndividualWidths().length;
                float wordSpacing = position.getWidthOfSpace();
                if (wordSpacing == 0.0f || wordSpacing == Float.NaN) {
                    deltaSpace = Float.MAX_VALUE;
                } else if (lastWordSpacing < 0.0f) {
                    deltaSpace = wordSpacing * getSpacingTolerance();
                } else {
                    deltaSpace = ((wordSpacing + lastWordSpacing) / 2.0f) * getSpacingTolerance();
                }
                if (previousAveCharWidth < 0.0f) {
                    averageCharWidth = positionWidth / wordCharCount;
                } else {
                    averageCharWidth = (previousAveCharWidth + (positionWidth / wordCharCount)) / 2.0f;
                }
                float deltaCharWidth = averageCharWidth * getAverageCharTolerance();
                float expectedStartOfNextWordX = -3.4028235E38f;
                if (endOfLastTextX != -1.0f) {
                    if (deltaCharWidth > deltaSpace) {
                        expectedStartOfNextWordX = endOfLastTextX + deltaSpace;
                    } else {
                        expectedStartOfNextWordX = endOfLastTextX + deltaCharWidth;
                    }
                }
                if (lastPosition != null) {
                    if (startOfArticle) {
                        lastPosition.setArticleStart();
                        startOfArticle = false;
                    }
                    if (!overlap(positionY, positionHeight, maxYForLine, maxHeightForLine)) {
                        writeLine(isRtlDominant, hasRtl, line);
                        line.clear();
                        writeLineSeparator();
                        expectedStartOfNextWordX = -3.4028235E38f;
                        maxYForLine = -3.4028235E38f;
                        maxHeightForLine = -1.0f;
                        minYTopForLine = Float.MAX_VALUE;
                    }
                    boolean isNextWordExpected = expectedStartOfNextWordX < positionX;
                    if (this.isOCRProduced) {
                        float nextWordPositionDelta = positionX - expectedStartOfNextWordX;
                        isNextWordExpected = nextWordPositionDelta > expectedStartOfNextWordX / 50.0f;
                    }
                    if (expectedStartOfNextWordX != -3.4028235E38f && isNextWordExpected && lastPosition.getTextPosition().getUnicode() != null && !lastPosition.getTextPosition().getUnicode().endsWith(" ")) {
                        line.add(WordSeparator.getSeparator());
                    }
                }
                if (positionY >= maxYForLine) {
                    maxYForLine = positionY;
                }
                endOfLastTextX = positionX + positionWidth;
                if (characterValue != null) {
                    if (startOfPage && lastPosition == null) {
                        writeParagraphStart();
                    }
                    line.add(position);
                }
                maxHeightForLine = Math.max(maxHeightForLine, positionHeight);
                minYTopForLine = Math.min(minYTopForLine, positionY - positionHeight);
                lastPosition = current;
                if (startOfPage) {
                    lastPosition.setParagraphStart();
                    lastPosition.setLineStart();
                    startOfPage = false;
                }
                lastWordSpacing = wordSpacing;
                f = averageCharWidth;
            }
            if (line.size() > 0) {
                writeLine(isRtlDominant, hasRtl, line);
            }
            endArticle();
        }
        writePageEnd();
    }

    protected void writeLine(boolean isRtlDominant, boolean hasRtl, List<TextPosition> line) throws IOException {
        if (isLineEmpty(line)) {
            return;
        }
        writeLineStart(line);
        this.output.flush();
        writeLine(line, isRtlDominant, hasRtl);
        this.output.flush();
        writeLineEnd(line);
        this.output.flush();
    }

    protected void writeLine(List<TextPosition> line, boolean isRtlDominant, boolean hasRtl) throws IOException {
        for (TextPosition text : line) {
            if (text instanceof WordSeparator) {
                writeWordSeparator();
            } else {
                String c = text.getUnicode();
                writeStringBefore(text, c, c);
                writeString(c);
                writeStringAfter(text, c, c);
            }
        }
    }

    protected void writeStringAfter(TextPosition text, String c, String normalized) throws IOException {
    }

    protected void writeStringBefore(TextPosition text, String c, String normalized) throws IOException {
    }

    protected void writeLineStart(List<TextPosition> line) throws IOException {
    }

    protected void writeLineEnd(List<TextPosition> line) throws IOException {
    }

    /* loaded from: com.sejda.pdf2html.pdf2html-0.0.72.jar:com/sejda/pdf2html/LocalPDFTextStripper$WordSeparator.class */
    public static final class WordSeparator extends TextPosition {
        private static final WordSeparator separator = new WordSeparator();

        private WordSeparator() {
            super(0, 0.0f, 0.0f, new Matrix(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f), 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, "", new int[0], null, 0.0f, 0);
        }

        public static WordSeparator getSeparator() {
            return separator;
        }
    }

    protected boolean overlap(float y1, float height1, float y2, float height2) {
        return within(y1, y2, 0.1f) || (y2 <= y1 && y2 >= y1 - height1) || (y1 <= y2 && y1 >= y2 - height2);
    }

    protected boolean within(float first, float second, float variance) {
        return second < first + variance && second > first - variance;
    }

    protected static TextPosition getFirstTrimmed(List<TextPosition> line) {
        String c;
        for (int i = 0; i < line.size(); i++) {
            if (line.get(i) != null && (c = line.get(i).getUnicode()) != null && c.trim().length() > 0) {
                return line.get(i);
            }
        }
        return line.get(0);
    }

    protected static TextPosition getLastTrimmed(List<TextPosition> line) {
        String c;
        for (int i = line.size() - 1; i >= 0; i--) {
            if (line.get(i) != null && (c = line.get(i).getUnicode()) != null && c.trim().length() > 0) {
                return line.get(i);
            }
        }
        return line.get(line.size() - 1);
    }

    protected boolean isLineEmpty(List<TextPosition> line) {
        return line.isEmpty() || getFirstTrimmed(line).getUnicode().trim().isEmpty();
    }
}
