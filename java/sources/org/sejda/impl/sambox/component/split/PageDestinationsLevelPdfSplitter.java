package org.sejda.impl.sambox.component.split;

import org.sejda.core.support.prefix.model.NameGenerationRequest;
import org.sejda.model.outline.OutlinePageDestinations;
import org.sejda.model.parameter.SplitByOutlineLevelParameters;
import org.sejda.model.split.NextOutputStrategy;
import org.sejda.model.split.PageDestinationsSplitPages;
import org.sejda.sambox.pdmodel.PDDocument;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/split/PageDestinationsLevelPdfSplitter.class */
public class PageDestinationsLevelPdfSplitter extends AbstractPdfSplitter<SplitByOutlineLevelParameters> {
    private PageDestinationsSplitPages splitPages;
    private OutlinePageDestinations outlineDestinations;

    public PageDestinationsLevelPdfSplitter(PDDocument document, SplitByOutlineLevelParameters parameters, OutlinePageDestinations outlineDestinations, boolean optimize) {
        super(document, parameters, optimize, parameters.discardOutline());
        this.splitPages = new PageDestinationsSplitPages(outlineDestinations);
        this.outlineDestinations = outlineDestinations;
    }

    @Override // org.sejda.impl.sambox.component.split.AbstractPdfSplitter
    public NameGenerationRequest enrichNameGenerationRequest(NameGenerationRequest request) {
        return request.bookmark(this.outlineDestinations.getTitle(request.getPage()));
    }

    @Override // org.sejda.impl.sambox.component.split.AbstractPdfSplitter
    public NextOutputStrategy nextOutputStrategy() {
        return this.splitPages;
    }
}
