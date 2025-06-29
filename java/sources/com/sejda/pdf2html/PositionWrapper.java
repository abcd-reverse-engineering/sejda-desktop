package com.sejda.pdf2html;

import org.sejda.sambox.text.TextPosition;

/* loaded from: com.sejda.pdf2html.pdf2html-0.0.72.jar:com/sejda/pdf2html/PositionWrapper.class */
public class PositionWrapper {
    private boolean isLineStart = false;
    private boolean isParagraphStart = false;
    private boolean isPageBreak = false;
    private boolean isHangingIndent = false;
    private boolean isArticleStart = false;
    private TextPosition position;

    public TextPosition getTextPosition() {
        return this.position;
    }

    public boolean isLineStart() {
        return this.isLineStart;
    }

    public void setLineStart() {
        this.isLineStart = true;
    }

    public boolean isParagraphStart() {
        return this.isParagraphStart;
    }

    public void setParagraphStart() {
        this.isParagraphStart = true;
    }

    public void setLineStart(boolean isLineStart) {
        this.isLineStart = isLineStart;
    }

    public void setParagraphStart(boolean isParagraphStart) {
        this.isParagraphStart = isParagraphStart;
    }

    public void setPageBreak(boolean isPageBreak) {
        this.isPageBreak = isPageBreak;
    }

    public void setHangingIndent(boolean isHangingIndent) {
        this.isHangingIndent = isHangingIndent;
    }

    public void setArticleStart(boolean isArticleStart) {
        this.isArticleStart = isArticleStart;
    }

    public boolean isArticleStart() {
        return this.isArticleStart;
    }

    public void setArticleStart() {
        this.isArticleStart = true;
    }

    public boolean isPageBreak() {
        return this.isPageBreak;
    }

    public void setPageBreak() {
        this.isPageBreak = true;
    }

    public boolean isHangingIndent() {
        return this.isHangingIndent;
    }

    public void setHangingIndent() {
        this.isHangingIndent = true;
    }

    public PositionWrapper(TextPosition position) {
        this.position = null;
        this.position = position;
    }
}
