package org.sejda.sambox.printing;

import java.awt.RenderingHints;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.common.PDRectangle;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/printing/PDFPageable.class */
public final class PDFPageable extends Book {
    private final PDDocument document;
    private final int numberOfPages;
    private final boolean showPageBorder;
    private final float dpi;
    private final Orientation orientation;
    private boolean subsamplingAllowed;
    private RenderingHints renderingHints;

    public PDFPageable(PDDocument document) {
        this(document, Orientation.AUTO, false, 0.0f);
    }

    public PDFPageable(PDDocument document, Orientation orientation) {
        this(document, orientation, false, 0.0f);
    }

    public PDFPageable(PDDocument document, Orientation orientation, boolean showPageBorder) {
        this(document, orientation, showPageBorder, 0.0f);
    }

    public PDFPageable(PDDocument document, Orientation orientation, boolean showPageBorder, float dpi) {
        this.subsamplingAllowed = false;
        this.renderingHints = null;
        this.document = document;
        this.orientation = orientation;
        this.showPageBorder = showPageBorder;
        this.dpi = dpi;
        this.numberOfPages = document.getNumberOfPages();
    }

    public RenderingHints getRenderingHints() {
        return this.renderingHints;
    }

    public void setRenderingHints(RenderingHints renderingHints) {
        this.renderingHints = renderingHints;
    }

    public boolean isSubsamplingAllowed() {
        return this.subsamplingAllowed;
    }

    public void setSubsamplingAllowed(boolean subsamplingAllowed) {
        this.subsamplingAllowed = subsamplingAllowed;
    }

    public int getNumberOfPages() {
        return this.numberOfPages;
    }

    public PageFormat getPageFormat(int pageIndex) {
        Paper paper;
        boolean isLandscape;
        PDPage page = this.document.getPage(pageIndex);
        PDRectangle mediaBox = PDFPrintable.getRotatedMediaBox(page);
        PDRectangle cropBox = PDFPrintable.getRotatedCropBox(page);
        if (mediaBox.getWidth() > mediaBox.getHeight()) {
            paper = new Paper();
            paper.setSize(mediaBox.getHeight(), mediaBox.getWidth());
            paper.setImageableArea(cropBox.getLowerLeftY(), cropBox.getLowerLeftX(), cropBox.getHeight(), cropBox.getWidth());
            isLandscape = true;
        } else {
            paper = new Paper();
            paper.setSize(mediaBox.getWidth(), mediaBox.getHeight());
            paper.setImageableArea(cropBox.getLowerLeftX(), cropBox.getLowerLeftY(), cropBox.getWidth(), cropBox.getHeight());
            isLandscape = false;
        }
        PageFormat format = new PageFormat();
        format.setPaper(paper);
        switch (this.orientation) {
            case AUTO:
                format.setOrientation(isLandscape ? 0 : 1);
                break;
            case LANDSCAPE:
                format.setOrientation(0);
                break;
            case PORTRAIT:
                format.setOrientation(1);
                break;
        }
        return format;
    }

    public Printable getPrintable(int i) {
        if (i >= this.numberOfPages) {
            throw new IndexOutOfBoundsException(i + " >= " + this.numberOfPages);
        }
        PDFPrintable printable = new PDFPrintable(this.document, Scaling.ACTUAL_SIZE, this.showPageBorder, this.dpi);
        printable.setSubsamplingAllowed(this.subsamplingAllowed);
        printable.setRenderingHints(this.renderingHints);
        return printable;
    }
}
