package org.sejda.impl.sambox.component;

import java.awt.Color;
import java.io.IOException;
import org.sejda.impl.sambox.util.FontUtils;
import org.sejda.model.HorizontalAlign;
import org.sejda.model.VerticalAlign;
import org.sejda.model.exception.TaskException;
import org.sejda.model.exception.TaskIOException;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.font.PDFont;
import org.sejda.sambox.pdmodel.font.PDType1Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/FilenameFooterWriter.class */
public class FilenameFooterWriter {
    private boolean addFooter;
    private PageTextWriter writer;
    private PDDocument document;
    private static final Logger LOG = LoggerFactory.getLogger(FilenameFooterWriter.class);
    private static PDFont FONT = PDType1Font.HELVETICA();
    private static double FONT_SIZE = 10.0d;

    public FilenameFooterWriter(boolean addFooter, PDDocument document) {
        this.addFooter = false;
        this.writer = new PageTextWriter(document);
        this.document = document;
        this.addFooter = addFooter;
    }

    public void addFooter(PDPage page, String fileName, long pageNumber) throws TaskException {
        if (this.addFooter) {
            try {
                String truncatedFilename = truncateIfRequired(fileName, maxWidth(page, pageNumber));
                this.writer.write(page, HorizontalAlign.LEFT, VerticalAlign.BOTTOM, truncatedFilename, FONT, Double.valueOf(FONT_SIZE), Color.BLACK);
                this.writer.write(page, HorizontalAlign.RIGHT, VerticalAlign.BOTTOM, Long.toString(pageNumber), FONT, Double.valueOf(FONT_SIZE), Color.BLACK);
            } catch (IOException | TaskIOException e) {
                throw new TaskException("Unable to write the page footer", e);
            }
        }
    }

    private double maxWidth(PDPage page, long pageNumber) throws IOException {
        PDRectangle pageSize = page.getMediaBox().rotate(page.getRotation());
        return (pageSize.getWidth() - (2.0f * PageTextWriter.DEFAULT_MARGIN.floatValue())) - FontUtils.getSimpleStringWidth(Long.toString(pageNumber), FONT, FONT_SIZE);
    }

    private double stringWidth(String text) throws TaskIOException {
        return this.writer.getStringWidth(text, FONT, (float) FONT_SIZE);
    }

    private String truncateIfRequired(String original, double maxWidth) throws TaskIOException, IOException {
        int currentLength;
        String text = FontUtils.replaceUnsupportedCharacters(original, this.document, "#");
        if (stringWidth(text) <= maxWidth) {
            return text;
        }
        LOG.debug("Page filename footer needs truncating to fit available space");
        int length = text.length();
        while (true) {
            currentLength = length / 2;
            if (stringWidth(text.substring(0, currentLength)) <= maxWidth) {
                break;
            }
            length = currentLength;
        }
        int currentChunk = currentLength;
        while (currentChunk > 1) {
            currentChunk /= 2;
            if (stringWidth(text.substring(0, currentLength + currentChunk)) < maxWidth) {
                currentLength += currentChunk;
            }
        }
        return text.substring(0, currentLength);
    }
}
