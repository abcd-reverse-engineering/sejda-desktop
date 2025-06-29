package org.sejda.impl.sambox.component.split;

import org.sejda.core.support.prefix.model.NameGenerationRequest;
import org.sejda.model.parameter.AbstractSplitByPageParameters;
import org.sejda.model.split.NextOutputStrategy;
import org.sejda.model.split.SplitPages;
import org.sejda.sambox.pdmodel.PDDocument;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/split/PagesPdfSplitter.class */
public class PagesPdfSplitter<T extends AbstractSplitByPageParameters> extends AbstractPdfSplitter<T> {
    private NextOutputStrategy splitPages;

    public PagesPdfSplitter(PDDocument document, T parameters, boolean optimize) {
        super(document, parameters, optimize, parameters.discardOutline());
        this.splitPages = new SplitPages(parameters.getPages(document.getNumberOfPages()));
    }

    @Override // org.sejda.impl.sambox.component.split.AbstractPdfSplitter
    public NextOutputStrategy nextOutputStrategy() {
        return this.splitPages;
    }

    @Override // org.sejda.impl.sambox.component.split.AbstractPdfSplitter
    public NameGenerationRequest enrichNameGenerationRequest(NameGenerationRequest request) {
        return request;
    }
}
