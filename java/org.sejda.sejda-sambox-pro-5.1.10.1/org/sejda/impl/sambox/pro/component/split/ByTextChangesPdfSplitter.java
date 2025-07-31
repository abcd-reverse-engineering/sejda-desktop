package org.sejda.impl.sambox.pro.component.split;

import org.sejda.core.support.prefix.model.NameGenerationRequest;
import org.sejda.impl.sambox.component.split.AbstractPdfSplitter;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.pro.parameter.SplitByTextContentParameters;
import org.sejda.model.split.NextOutputStrategy;
import org.sejda.sambox.pdmodel.PDDocument;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/split/ByTextChangesPdfSplitter.class */
public class ByTextChangesPdfSplitter extends AbstractPdfSplitter<SplitByTextContentParameters> {
    private SplitByTextChangesOutputStrategy outputStrategy;

    public ByTextChangesPdfSplitter(PDDocument document, SplitByTextContentParameters parameters, boolean optimize, SplitByTextChangesOutputStrategy outputStrategy) throws TaskIOException {
        super(document, parameters, optimize, parameters.discardOutline());
        this.outputStrategy = outputStrategy;
    }

    public NameGenerationRequest enrichNameGenerationRequest(NameGenerationRequest request) {
        return request.text(this.outputStrategy.getTextByPage(request.getPage().intValue()));
    }

    public NextOutputStrategy nextOutputStrategy() {
        return this.outputStrategy;
    }
}
