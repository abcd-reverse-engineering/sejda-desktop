package com.sejda.pdf2html;

import com.sejda.pdf2html.LocalPDFTextStripper;
import com.sejda.pdf2html.pojo.Image;
import com.sejda.pdf2html.sambox.HeaderFooterRemover;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.PDPageTree;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.text.TextPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: com.sejda.pdf2html.pdf2html-0.0.72.jar:com/sejda/pdf2html/PDFText2HTML.class */
public class PDFText2HTML extends LocalPDFTextStripper {
    private static final float DELTA = 2.0f;
    private static final int MAX_FONT_SIZE = 200;
    protected final StatisticAnalyzer statisticParser;
    protected final HeaderFooterRemover headerFooterRemover;
    protected double maxLeftMargin;
    protected double minRightMargin;
    protected float minBoxMean;
    protected float maxBoxMean;
    protected PDPageTree allPages;
    protected PDPage currentPage;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    protected ImageExtractor imageExtractor = null;
    protected List<Image> pageImages = new ArrayList();
    private String align = null;
    private String lineSpacing = null;
    private float maxLineSpacing = 5.0f;
    private boolean startP = false;
    private boolean indentStartP = false;
    private boolean endP = false;
    private boolean inParagraph = false;
    private String lastStyle = null;
    private float prevLineY = -1.0f;
    private boolean pageBreak = false;
    private StringBuilder pendingAfterParagraphOutput = new StringBuilder();

    public PDFText2HTML(StatisticAnalyzer statisticParser, HeaderFooterRemover headerFooterRemover) throws IOException {
        this.statisticParser = statisticParser;
        this.headerFooterRemover = headerFooterRemover;
        setPageStart("");
        setPageEnd("");
        setArticleStart("");
        setArticleEnd("");
        setLineSeparator(this.LINE_SEPARATOR);
        setParagraphStart("");
        setParagraphEnd(this.LINE_SEPARATOR);
    }

    public void setImageExtractor(ImageExtractor imageExtractor) {
        this.imageExtractor = imageExtractor;
    }

    @Override // org.sejda.sambox.tools.SamboxPDFText2HTML
    protected void writeHeader() throws IOException {
        StringBuilder buf = new StringBuilder();
        buf.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
        buf.append("<html xmlns=\"http://www.w3.org/1999/xhtml\"><head>");
        buf.append("<title>" + escape(getTitle()) + "</title>\n");
        buf.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-16\"></meta>\n");
        String author = getAuthor();
        if (author != null && !author.isEmpty()) {
            buf.append("<meta name=\"Author\" content=\"");
            buf.append(escape(author));
            buf.append("\"></meta>");
        }
        buf.append("</head>\n");
        buf.append("<body>\n");
        this.output.write(buf.toString());
    }

    @Override // org.sejda.sambox.tools.SamboxPDFText2HTML
    protected String getTitle() {
        String titleGuess = this.document.getDocumentInformation().getTitle();
        if (titleGuess != null) {
            titleGuess = titleGuess.replace((char) 0, ' ');
        }
        if (titleGuess != null && titleGuess.length() > 0) {
            return titleGuess;
        }
        return "";
    }

    protected String getAuthor() {
        String authorGuess = this.document.getDocumentInformation().getAuthor();
        if (authorGuess != null) {
            authorGuess = authorGuess.replace((char) 0, ' ');
        }
        if (authorGuess != null && authorGuess.length() > 0) {
            return authorGuess;
        }
        return "";
    }

    @Override // org.sejda.sambox.text.PDFTextStripper
    public void writeText(PDDocument doc, Writer outputStream) throws IOException {
        float marginDelta = getAverangeFontSize() * DELTA;
        this.maxLeftMargin = getAverangeLeftMargin() + marginDelta;
        this.minRightMargin = this.statisticParser.getAverangeRightMargin() - marginDelta;
        super.writeText(doc, outputStream);
    }

    @Override // org.sejda.sambox.text.PDFTextStripper
    protected void startPage(PDPage page) throws IOException {
        this.LOGGER.debug("Starting page " + getCurrentPageNo());
        PDRectangle currentMediaBox = page.getMediaBox();
        float mediaBoxWidth = currentMediaBox.getWidth();
        float boxMean = mediaBoxWidth / DELTA;
        this.minBoxMean = boxMean - (getAverangeFontSize() * DELTA);
        this.maxBoxMean = boxMean + (getAverangeFontSize() * DELTA);
        this.prevLineY = -1.0f;
        if (this.imageExtractor != null) {
            this.pageImages = this.imageExtractor.pageImages(page);
        }
        this.headerFooterRemover.startPage(page);
        this.currentPage = page;
    }

    @Override // org.sejda.sambox.text.PDFTextStripper
    protected void writePageStart() throws IOException {
        if (this.currentPage != null) {
            writePageAnchor(this.currentPage);
        }
        super.writePageStart();
    }

    protected void writePageAnchor(PDPage page) throws IOException {
        if (this.allPages == null) {
            this.allPages = this.document.getDocumentCatalog().getPages();
        }
        int pageNumber = this.allPages.indexOf(page) + 1;
        String anchor = String.format("<a name=\"#page%d\"></a>", Integer.valueOf(pageNumber));
        if (this.inParagraph) {
            this.pendingAfterParagraphOutput.append(anchor);
        } else {
            this.output.write(anchor);
        }
    }

    protected void printImagesUntilLine(List<TextPosition> line) throws IOException {
        TextPosition start = getFirstTrimmed(line);
        float y = start.getYDirAdj();
        Iterator<Image> it = this.pageImages.iterator();
        List<Image> images = new ArrayList<>();
        while (it.hasNext()) {
            Image image = it.next();
            if (image.getY() <= y) {
                images.add(image);
                it.remove();
                this.prevLineY = Math.max(this.prevLineY, image.getY());
            }
        }
        printImages(images);
    }

    private void printImages(List<Image> images) throws IOException {
        images.sort((o1, o2) -> {
            return -new Float(o1.getY()).compareTo(Float.valueOf(o2.getY()));
        });
        float prevImageY = 1.0f;
        boolean divOpened = false;
        StringBuilder sb = new StringBuilder();
        for (Image image : images) {
            if (image.hasSrc()) {
                boolean sameLine = Math.abs(prevImageY - image.getY()) < ((float) 5);
                if (!sameLine) {
                    if (divOpened) {
                        sb.append("</div>");
                    }
                    sb.append("<div>");
                    divOpened = true;
                }
                sb.append("<img alt=\"Image\" ");
                sb.append("src=\"");
                sb.append(image.getSrc());
                sb.append("\" ");
                sb.append(" width=\"").append(image.getWidth()).append(OperatorName.SHOW_TEXT_LINE_AND_SPACE);
                sb.append(" height=\"").append(image.getHeight()).append(OperatorName.SHOW_TEXT_LINE_AND_SPACE);
                sb.append(" style=\"padding: 5px; ");
                if (this.pageBreak) {
                    addPageBreak(sb);
                }
                sb.append(OperatorName.SHOW_TEXT_LINE_AND_SPACE);
                sb.append("/>");
                prevImageY = image.getY();
            }
        }
        if (divOpened) {
            sb.append("</div>");
        }
        this.output.write(sb.toString());
    }

    @Override // org.sejda.sambox.text.PDFTextStripper
    protected void writePageEnd() throws IOException {
        printImages(this.pageImages);
        this.pageImages = new ArrayList();
        super.writePageEnd();
    }

    @Override // com.sejda.pdf2html.LocalPDFTextStripper
    protected void writeStringBefore(TextPosition text, String c, String normalized) throws IOException {
        String style;
        if (text.getUnicode() == null) {
            style = this.lastStyle;
        } else {
            style = parseStyle(text);
        }
        if (this.lastStyle == null || !this.lastStyle.equals(style)) {
            if (this.lastStyle != null) {
                this.output.write("</span>");
            }
            if (style != null) {
                this.output.write("<span style=\"" + style + "\">");
            }
            this.lastStyle = style;
        }
    }

    private String parseStyle(TextPosition text) {
        StringBuilder sb = new StringBuilder();
        int fontSizes = parseFont(text);
        boolean isRelevantSize = true;
        if (this.startP || this.inParagraph) {
            isRelevantSize = fontSizes < 70 || fontSizes > 130;
        }
        if (fontSizes > 0 && isRelevantSize) {
            sb.append("font-size: ");
            sb.append(fontSizes);
            sb.append("%;");
        }
        if (this.statisticParser.isBold(text.getFont().getFontDescriptor())) {
            sb.append("font-weight: bold;");
        }
        if (this.statisticParser.isItalic(text)) {
            sb.append("font-style: italic;");
        }
        if (sb.length() > 0) {
            return sb.toString();
        }
        return null;
    }

    private int parseFont(TextPosition text) {
        int fontSize = -1;
        if (!(text instanceof LocalPDFTextStripper.WordSeparator) && text.getFontSizeInPt() != getAverangeFontSize()) {
            fontSize = Math.min(Math.round((text.getFontSizeInPt() * 100.0f) / getAverangeFontSize()), MAX_FONT_SIZE);
        }
        return fontSize;
    }

    @Override // com.sejda.pdf2html.LocalPDFTextStripper
    protected void writeLineStart(List<TextPosition> line) throws IOException {
        this.align = null;
        this.lineSpacing = null;
        this.endP = false;
        printImagesUntilLine(line);
        super.writeLineStart(line);
        parseAlign(line);
        parseLineSpace(line);
        String tag = writeStartTag();
        if (tag != null) {
            this.output.append((CharSequence) tag);
        }
    }

    protected String lineToString(List<TextPosition> line) {
        StringBuffer sb = new StringBuffer();
        for (TextPosition tp : line) {
            sb.append(tp.getUnicode());
        }
        return sb.toString();
    }

    @Override // com.sejda.pdf2html.LocalPDFTextStripper
    protected void writeLineEnd(List<TextPosition> line) throws IOException {
        super.writeLineEnd(line);
        if (this.lastStyle != null) {
            this.output.append((CharSequence) "</span>");
            this.lastStyle = null;
        }
        String tag = writeEndTag();
        if (tag != null) {
            this.output.append((CharSequence) tag);
        }
    }

    protected String writeStartTag() throws IOException {
        if (this.align != null) {
            StringBuilder sb = new StringBuilder();
            if (this.startP || this.inParagraph) {
                sb.append("</p>");
                this.inParagraph = false;
                sb.append(this.pendingAfterParagraphOutput.toString());
                this.pendingAfterParagraphOutput = new StringBuilder();
                this.startP = false;
            }
            sb.append("<div style=\"");
            if (this.lineSpacing != null) {
                sb.append("margin-top: ");
                sb.append(this.lineSpacing);
                sb.append(";");
            }
            addPageBreak(sb);
            if (this.align != null) {
                sb.append("text-align: ");
                sb.append("left");
                sb.append(";");
            }
            sb.append("\">");
            return sb.toString();
        }
        if (!this.startP || this.lineSpacing != null || this.indentStartP) {
            this.startP = true;
            StringBuilder sb2 = new StringBuilder();
            if (this.inParagraph) {
                sb2.append("</p>");
                sb2.append(this.pendingAfterParagraphOutput.toString());
                this.pendingAfterParagraphOutput = new StringBuilder();
            }
            sb2.append("<p");
            if (this.pageBreak || this.lineSpacing != null) {
                sb2.append(" style=\"");
                addPageBreak(sb2);
                if (this.lineSpacing != null) {
                    sb2.append("margin-top: ");
                    sb2.append(this.lineSpacing);
                    sb2.append(";");
                }
                sb2.append(OperatorName.SHOW_TEXT_LINE_AND_SPACE);
            }
            sb2.append(">");
            if (sb2.toString().startsWith("</p><p><a name=\"#page") || sb2.toString().startsWith("<p><a name=\"#page")) {
                this.startP = false;
                return null;
            }
            this.inParagraph = true;
            this.indentStartP = false;
            return sb2.toString();
        }
        return null;
    }

    private void addPageBreak(StringBuilder sb) {
        if (this.pageBreak) {
            sb.append("page-break-before: always;");
            this.pageBreak = false;
        }
    }

    protected String writeEndTag() throws IOException {
        if (this.align != null) {
            return "</div>";
        }
        if (this.endP && this.startP) {
            this.startP = false;
            this.inParagraph = false;
            this.pendingAfterParagraphOutput = new StringBuilder();
            return "</p>" + this.pendingAfterParagraphOutput.toString();
        }
        return null;
    }

    protected void parseLineSpace(List<TextPosition> line) {
        float lineY = getFirstTrimmed(line).getY();
        if (this.prevLineY >= 0.0f && lineY - this.prevLineY > getAverangeLineSpacing()) {
            float perc = (lineY - this.prevLineY) / getAverangeLineSpacing();
            if (perc > this.maxLineSpacing) {
                perc = this.maxLineSpacing;
            }
            if (perc > 0.2f) {
                int pixels = Math.round(perc * 12.0f);
                if (Math.abs(pixels - 12) < 2) {
                    this.lineSpacing = null;
                } else {
                    this.lineSpacing = Math.round(perc * 12.0f) + "px";
                }
            }
        }
        this.prevLineY = lineY;
    }

    private float getAverangeLeftMargin() {
        return this.statisticParser.getAverangeLeftMargin();
    }

    private float getAverangeLeftMarginPerPage(int page) {
        return this.statisticParser.getAverangeLeftMarginPerPage(page);
    }

    private float getAverangeFontSize() {
        return this.statisticParser.getAverangeFontSize();
    }

    private float getAverangeLineSpacing() {
        return this.statisticParser.getAverangeLineSpacing();
    }

    private float getAverangeLastLine() {
        return this.statisticParser.getAverangeLastLine();
    }

    @Override // org.sejda.sambox.text.PDFTextStripper
    protected void endPage(PDPage page) throws IOException {
        if (this.prevLineY > -1.0f && getAverangeLastLine() - this.prevLineY > getAverangeFontSize()) {
            this.pageBreak = true;
        }
    }

    protected void parseAlign(List<TextPosition> line) {
        if (line.size() < 1) {
            return;
        }
        TextPosition firstText = getFirstTrimmed(line);
        float start = firstText.getX();
        if (start == -1.0f || firstText.getUnicode().trim().isEmpty()) {
            return;
        }
        TextPosition lastText = getLastTrimmed(line);
        float end = lastText.getX() + lastText.getWidth();
        if (end == -1.0f || lastText.getUnicode().trim().isEmpty()) {
            return;
        }
        if (start > this.maxLeftMargin && end < this.minRightMargin) {
            float lineMean = (end + start) / DELTA;
            if (lineMean > this.minBoxMean && lineMean < this.maxBoxMean) {
                this.align = "center";
            } else if (end > this.minRightMargin) {
                this.align = "right";
            }
        }
        if (this.align == null && start >= getAverangeLeftMarginPerPage(getCurrentPageNo()) + 2) {
            this.indentStartP = true;
        }
    }

    @Override // org.sejda.sambox.tools.SamboxPDFText2HTML, org.sejda.sambox.text.PDFTextStripper
    protected void startArticle(boolean isltr) throws IOException {
    }

    @Override // org.sejda.sambox.tools.SamboxPDFText2HTML, org.sejda.sambox.text.PDFTextStripper
    protected void endArticle() throws IOException {
    }

    @Override // org.sejda.sambox.tools.SamboxPDFText2HTML, org.sejda.sambox.text.PDFTextStripper
    public void endDocument(PDDocument pdf) throws IOException {
        if (this.inParagraph) {
            this.output.write("</p>");
            this.output.write(this.pendingAfterParagraphOutput.toString());
            this.pendingAfterParagraphOutput = new StringBuilder();
        }
        super.endDocument(pdf);
    }

    @Override // com.sejda.pdf2html.LocalPDFTextStripper
    protected boolean isLineEmpty(List<TextPosition> line) {
        return this.headerFooterRemover.belongsToHeaderOrFooter(line) || super.isLineEmpty(line);
    }
}
