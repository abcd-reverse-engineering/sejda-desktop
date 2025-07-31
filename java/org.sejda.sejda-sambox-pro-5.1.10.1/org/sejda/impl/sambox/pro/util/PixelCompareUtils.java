package org.sejda.impl.sambox.pro.util;

import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;
import java.awt.image.BufferedImage;
import java.io.IOException;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.resizers.configurations.ScalingMode;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.rendering.PDFRenderer;
import org.sejda.sambox.rendering.PageDrawer;
import org.sejda.sambox.rendering.PageDrawerParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/util/PixelCompareUtils.class */
public class PixelCompareUtils {
    private static final Logger LOG = LoggerFactory.getLogger(PixelCompareUtils.class);
    private final double percentSimilarityThreshold;
    private final boolean renderImagesInPdfPages;

    public PixelCompareUtils() {
        this(99.99d, false);
    }

    public PixelCompareUtils(double percentSimilarityThreshold, boolean renderImagesInPdfPages) {
        this.percentSimilarityThreshold = percentSimilarityThreshold;
        this.renderImagesInPdfPages = renderImagesInPdfPages;
    }

    public void assertSimilar(PDDocument actual, PDDocument expected) {
        assertSimilar(actual, expected, Integer.MAX_VALUE);
    }

    public void assertSimilar(PDDocument actual, PDDocument expected, int maxNumberOfPages) {
        try {
            if (actual.getNumberOfPages() != expected.getNumberOfPages()) {
                throw new VisualCompareException("Documents have different number of pages:" + actual.getNumberOfPages() + ", " + expected.getNumberOfPages());
            }
            int numOfPages = Math.min(actual.getNumberOfPages(), maxNumberOfPages);
            for (int i = 0; i < numOfPages; i++) {
                LOG.info("Comparing page " + (i + 1));
                BufferedImage p1 = resizeTo(takeScreenshotOf(actual, i, this.renderImagesInPdfPages), 1024);
                BufferedImage p2 = resizeTo(takeScreenshotOf(expected, i, this.renderImagesInPdfPages), 1024);
                ImageComparisonResult comparisonResult = new ImageComparison(p1, p2).compareImages();
                if (ImageComparisonState.MATCH != comparisonResult.getImageComparisonState()) {
                    double percentSimilarity = 100.0f - comparisonResult.getDifferencePercent();
                    if (percentSimilarity < this.percentSimilarityThreshold) {
                        throw new VisualCompareException("Page " + (i + 1) + " differs, similarity: " + percentSimilarity + "%");
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static BufferedImage takeScreenshotOf(PDDocument doc, int pageIndex, final boolean renderImages) throws IOException {
        PDFRenderer pdfRenderer = new PDFRenderer(doc) { // from class: org.sejda.impl.sambox.pro.util.PixelCompareUtils.1
            protected PageDrawer createPageDrawer(PageDrawerParameters parameters) throws IOException {
                PageDrawer pageDrawer = new PageDrawer(parameters);
                pageDrawer.setImageContentRendered(renderImages);
                return pageDrawer;
            }
        };
        return pdfRenderer.renderImageWithDPI(pageIndex, 150.0f);
    }

    private static BufferedImage resizeTo(BufferedImage image, int width) throws IOException {
        return Thumbnails.of(new BufferedImage[]{image}).scalingMode(ScalingMode.PROGRESSIVE_BILINEAR).width(width).asBufferedImage();
    }
}
