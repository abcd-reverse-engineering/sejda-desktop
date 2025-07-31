package org.sejda.impl.sambox.pro.component;

import java.util.Collections;
import org.sejda.impl.sambox.component.PdfScaler;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.scale.Margins;
import org.sejda.model.scale.ScaleType;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/PdfResizer.class */
public class PdfResizer {
    private PdfScaler scaler = new PdfScaler(ScaleType.PAGE);
    private static final Logger LOG = LoggerFactory.getLogger(PdfResizer.class);

    public void changePageSize(PDDocument doc, Iterable<PDPage> pages, PDRectangle desiredPageSize) throws TaskIOException {
        for (PDPage page : pages) {
            changePageSize(doc, page, desiredPageSize);
        }
    }

    public void changePageSize(PDDocument doc, PDPage page, PDRectangle desiredPageSize) throws TaskIOException {
        PDRectangle currentPageSize = page.getCropBox().rotate(page.getRotation());
        LOG.debug("Current page size: {}", currentPageSize);
        LOG.debug("Desired page size: {}", desiredPageSize);
        double scale = getScalingFactorMatchWidthOrHeight(desiredPageSize, currentPageSize);
        this.scaler.scale(doc, Collections.singletonList(page), scale);
        PDRectangle scaledPageSize = page.getCropBox().rotate(page.getRotation());
        PDRectangle normalizedScaledPageSize = scaledPageSize;
        boolean mismatchingOrientation = PdfScaler.isLandscape(scaledPageSize) != PdfScaler.isLandscape(desiredPageSize);
        if (mismatchingOrientation) {
            normalizedScaledPageSize = scaledPageSize.rotate();
        }
        double widthDiff = desiredPageSize.getWidth() - normalizedScaledPageSize.getWidth();
        double heightDiff = desiredPageSize.getHeight() - normalizedScaledPageSize.getHeight();
        if (widthDiff < 1.0d) {
            widthDiff = 0.0d;
        }
        if (heightDiff < 1.0d) {
            heightDiff = 0.0d;
        }
        if (widthDiff > 0.0d || heightDiff > 0.0d) {
            double top = Margins.pointsToInches(heightDiff) / 2.0d;
            double left = Margins.pointsToInches(widthDiff) / 2.0d;
            Margins margins = new Margins(top, left, top, left);
            if (mismatchingOrientation) {
                margins = margins.rotate();
            }
            PdfScaler.margin(doc, Collections.singleton(page), margins);
        }
    }

    private double getScalingFactorMatchWidthOrHeight(PDRectangle targetBox, PDRectangle pageBox) {
        PDRectangle normalizedOrientationTargetBox = targetBox;
        if (PdfScaler.isLandscape(targetBox) != PdfScaler.isLandscape(pageBox)) {
            normalizedOrientationTargetBox = targetBox.rotate();
        }
        float widthFactor = normalizedOrientationTargetBox.getWidth() / pageBox.getWidth();
        float heightFactor = normalizedOrientationTargetBox.getHeight() / pageBox.getHeight();
        float factor = Math.min(widthFactor, heightFactor);
        return factor;
    }
}
