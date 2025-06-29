package org.sejda.sambox.rendering;

import java.awt.RenderingHints;
import org.sejda.sambox.pdmodel.PDPage;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/rendering/PageDrawerParameters.class */
public final class PageDrawerParameters {
    private final PDFRenderer renderer;
    private final PDPage page;
    private final boolean subsamplingAllowed;
    private final RenderDestination destination;
    private final RenderingHints renderingHints;

    PageDrawerParameters(PDFRenderer renderer, PDPage page, boolean subsamplingAllowed, RenderDestination destination, RenderingHints renderingHints) {
        this.renderer = renderer;
        this.page = page;
        this.subsamplingAllowed = subsamplingAllowed;
        this.destination = destination;
        this.renderingHints = renderingHints;
    }

    public PDPage getPage() {
        return this.page;
    }

    PDFRenderer getRenderer() {
        return this.renderer;
    }

    public boolean isSubsamplingAllowed() {
        return this.subsamplingAllowed;
    }

    public RenderDestination getDestination() {
        return this.destination;
    }

    public RenderingHints getRenderingHints() {
        return this.renderingHints;
    }
}
