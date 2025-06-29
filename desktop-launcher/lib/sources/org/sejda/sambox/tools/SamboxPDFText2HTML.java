package org.sejda.sambox.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringEscapeUtils;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.font.PDFontDescriptor;
import org.sejda.sambox.text.PDFTextStripper;
import org.sejda.sambox.text.TextPosition;

/* loaded from: com.sejda.pdf2html.pdf2html-0.0.72.jar:org/sejda/sambox/tools/SamboxPDFText2HTML.class */
public class SamboxPDFText2HTML extends PDFTextStripper {
    private static final int INITIAL_PDF_TO_HTML_BYTES = 8192;
    private boolean onFirstPage = true;
    private final FontState fontState = new FontState();

    public SamboxPDFText2HTML() throws IOException {
        setLineSeparator(this.LINE_SEPARATOR);
        setParagraphStart("<p>");
        setParagraphEnd("</p>" + this.LINE_SEPARATOR);
        setPageStart("<div style=\"page-break-before:always; page-break-after:always\">");
        setPageEnd("</div>" + this.LINE_SEPARATOR);
        setArticleStart(this.LINE_SEPARATOR);
        setArticleEnd(this.LINE_SEPARATOR);
    }

    protected void writeHeader() throws IOException {
        StringBuilder buf = new StringBuilder(INITIAL_PDF_TO_HTML_BYTES);
        buf.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\n\"http://www.w3.org/TR/html4/loose.dtd\">\n");
        buf.append("<html><head>");
        buf.append("<title>").append(escape(getTitle())).append("</title>\n");
        buf.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-16\">\n");
        buf.append("</head>\n");
        buf.append("<body>\n");
        super.writeString(buf.toString());
    }

    @Override // org.sejda.sambox.text.PDFTextStripper
    protected void writePage() throws IOException {
        if (this.onFirstPage) {
            writeHeader();
            this.onFirstPage = false;
        }
        super.writePage();
    }

    @Override // org.sejda.sambox.text.PDFTextStripper
    public void endDocument(PDDocument document) throws IOException {
        super.writeString("</body></html>");
    }

    protected String getTitle() {
        String titleGuess = this.document.getDocumentInformation().getTitle();
        if (titleGuess != null && titleGuess.length() > 0) {
            return titleGuess;
        }
        Iterator<List<TextPosition>> textIter = getCharactersByArticle().iterator();
        float lastFontSize = -1.0f;
        StringBuilder titleText = new StringBuilder();
        while (textIter.hasNext()) {
            for (TextPosition position : textIter.next()) {
                float currentFontSize = position.getFontSize();
                if (currentFontSize != lastFontSize || titleText.length() > 64) {
                    if (titleText.length() > 0) {
                        return titleText.toString();
                    }
                    lastFontSize = currentFontSize;
                }
                if (currentFontSize > 13.0f) {
                    titleText.append(position.getUnicode());
                }
            }
        }
        return "";
    }

    @Override // org.sejda.sambox.text.PDFTextStripper
    protected void startArticle(boolean isLTR) throws IOException {
        if (isLTR) {
            super.writeString("<div>");
        } else {
            super.writeString("<div dir=\"RTL\">");
        }
    }

    @Override // org.sejda.sambox.text.PDFTextStripper
    protected void endArticle() throws IOException {
        super.endArticle();
        super.writeString("</div>");
    }

    @Override // org.sejda.sambox.text.PDFTextStripper
    protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
        super.writeString(this.fontState.push(text, textPositions));
    }

    @Override // org.sejda.sambox.text.PDFTextStripper
    protected void writeString(String chars) throws IOException {
        super.writeString(escape(chars));
    }

    @Override // org.sejda.sambox.text.PDFTextStripper
    protected void writeParagraphEnd() throws IOException {
        super.writeString(this.fontState.clear());
        super.writeParagraphEnd();
    }

    public static String escape(String chars) {
        StringBuilder builder = new StringBuilder(chars.length());
        for (int i = 0; i < chars.length(); i++) {
            appendEscaped(builder, chars.charAt(i));
        }
        return builder.toString();
    }

    private static void appendEscaped(StringBuilder builder, char character) {
        if (character < ' ' || character > '~') {
            builder.append(StringEscapeUtils.ESCAPE_HTML4.translate(String.valueOf(character)));
            return;
        }
        switch (character) {
            case '\"':
                builder.append("&quot;");
                break;
            case '&':
                builder.append("&amp;");
                break;
            case '<':
                builder.append("&lt;");
                break;
            case '>':
                builder.append("&gt;");
                break;
            default:
                builder.append(String.valueOf(character));
                break;
        }
    }

    /* loaded from: com.sejda.pdf2html.pdf2html-0.0.72.jar:org/sejda/sambox/tools/SamboxPDFText2HTML$FontState.class */
    private static class FontState {
        private final List<String> stateList = new ArrayList();
        private final Set<String> stateSet = new HashSet();

        private FontState() {
        }

        public String push(String text, List<TextPosition> textPositions) {
            StringBuilder buffer = new StringBuilder();
            if (text.length() == textPositions.size()) {
                for (int i = 0; i < text.length(); i++) {
                    push(buffer, text.charAt(i), textPositions.get(i));
                }
            } else if (!text.isEmpty()) {
                if (textPositions.isEmpty()) {
                    return text;
                }
                push(buffer, text.charAt(0), textPositions.get(0));
                buffer.append(SamboxPDFText2HTML.escape(text.substring(1)));
            }
            return buffer.toString();
        }

        public String clear() {
            StringBuilder buffer = new StringBuilder();
            closeUntil(buffer, null);
            this.stateList.clear();
            this.stateSet.clear();
            return buffer.toString();
        }

        protected String push(StringBuilder buffer, char character, TextPosition textPosition) {
            boolean bold = false;
            boolean italics = false;
            PDFontDescriptor descriptor = textPosition.getFont().getFontDescriptor();
            if (descriptor != null) {
                bold = isBold(descriptor);
                italics = isItalic(descriptor);
            }
            buffer.append(bold ? open(OperatorName.CLOSE_FILL_NON_ZERO_AND_STROKE) : close(OperatorName.CLOSE_FILL_NON_ZERO_AND_STROKE));
            buffer.append(italics ? open(OperatorName.SET_FLATNESS) : close(OperatorName.SET_FLATNESS));
            SamboxPDFText2HTML.appendEscaped(buffer, character);
            return buffer.toString();
        }

        private String open(String tag) {
            if (this.stateSet.contains(tag)) {
                return "";
            }
            this.stateList.add(tag);
            this.stateSet.add(tag);
            return openTag(tag);
        }

        private String close(String tag) {
            if (!this.stateSet.contains(tag)) {
                return "";
            }
            StringBuilder tagsBuilder = new StringBuilder();
            int index = closeUntil(tagsBuilder, tag);
            this.stateList.remove(index);
            this.stateSet.remove(tag);
            while (index < this.stateList.size()) {
                tagsBuilder.append(openTag(this.stateList.get(index)));
                index++;
            }
            return tagsBuilder.toString();
        }

        private int closeUntil(StringBuilder tagsBuilder, String endTag) {
            int i = this.stateList.size();
            while (true) {
                int i2 = i;
                i--;
                if (i2 > 0) {
                    String tag = this.stateList.get(i);
                    tagsBuilder.append(closeTag(tag));
                    if (endTag != null && tag.equals(endTag)) {
                        return i;
                    }
                } else {
                    return -1;
                }
            }
        }

        private String openTag(String tag) {
            return "<" + tag + ">";
        }

        private String closeTag(String tag) {
            return "</" + tag + ">";
        }

        private boolean isBold(PDFontDescriptor descriptor) {
            if (descriptor.isForceBold()) {
                return true;
            }
            return descriptor.getFontName().contains("Bold");
        }

        private boolean isItalic(PDFontDescriptor descriptor) {
            if (descriptor.isItalic()) {
                return true;
            }
            return descriptor.getFontName().contains("Italic");
        }
    }
}
