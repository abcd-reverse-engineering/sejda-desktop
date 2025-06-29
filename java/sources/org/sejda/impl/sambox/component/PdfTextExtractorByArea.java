package org.sejda.impl.sambox.component;

import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.sejda.commons.util.StringUtils;
import org.sejda.model.exception.TaskIOException;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.text.PDFTextStripperByArea;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/PdfTextExtractorByArea.class */
public class PdfTextExtractorByArea {
    private static final int GUESSTIMATE_HEADER_FOOTER_HEIGHT = 40;

    public String extractFooterText(PDPage page) throws TaskIOException {
        return extractTextFromArea(page, getFooterAreaRectangle(page));
    }

    public String extractHeaderText(PDPage page) throws TaskIOException {
        return extractTextFromArea(page, getHeaderAreaRectangle(page));
    }

    public String extractAddedText(PDPage page, Point2D position) throws TaskIOException {
        return extractTextFromArea(page, getAddedTextAreaRectangle(page, position));
    }

    private Rectangle getAddedTextAreaRectangle(PDPage page, Point2D position) {
        PDRectangle pageSize = page.getCropBox().rotate(page.getRotation());
        int pageHeight = (int) pageSize.getHeight();
        int pageWidth = (int) pageSize.getWidth();
        return new Rectangle((int) position.getX(), pageHeight - ((int) position.getY()), pageWidth, 12);
    }

    private Rectangle getFooterAreaRectangle(PDPage page) {
        PDRectangle pageSize = page.getCropBox().rotate(page.getRotation());
        int pageHeight = (int) pageSize.getHeight();
        int pageWidth = (int) pageSize.getWidth();
        return new Rectangle(0, pageHeight - 40, pageWidth, 40);
    }

    private Rectangle getHeaderAreaRectangle(PDPage page) {
        PDRectangle pageSize = page.getCropBox().rotate(page.getRotation());
        int pageWidth = (int) pageSize.getWidth();
        return new Rectangle(0, 0, pageWidth, 40);
    }

    public String extractTextFromArea(PDPage page, Rectangle2D area) throws TaskIOException {
        try {
            PDFTextStripperByArea stripper = new PDFTextStripperByArea();
            stripper.setSortByPosition(true);
            stripper.addRegion("area1", area);
            stripper.extractRegions(page);
            String result = stripper.getTextForRegion("area1");
            return StringUtils.normalizeWhitespace(org.apache.commons.lang3.StringUtils.strip((String) org.apache.commons.lang3.StringUtils.defaultIfBlank(result, ""))).trim();
        } catch (IOException e) {
            throw new TaskIOException("An error occurred extracting text from page.", e);
        }
    }

    public List<String> extractTextFromAreas(PDPage page, List<Rectangle> areas) throws TaskIOException {
        List<String> results = new ArrayList<>(areas.size());
        try {
            PDFTextStripperByArea stripper = new PDFTextStripperByArea();
            stripper.setSortByPosition(true);
            for (int i = 0; i < areas.size(); i++) {
                stripper.addRegion("area" + i, (Rectangle2D) areas.get(i));
            }
            stripper.extractRegions(page);
            for (int i2 = 0; i2 < areas.size(); i2++) {
                String text = stripper.getTextForRegion("area" + i2);
                String result = (String) org.apache.commons.lang3.StringUtils.defaultIfBlank(text, "");
                results.add(StringUtils.normalizeWhitespace(org.apache.commons.lang3.StringUtils.strip(result)).trim());
            }
            return results;
        } catch (IOException e) {
            throw new TaskIOException("An error occurred extracting text from page.", e);
        }
    }
}
