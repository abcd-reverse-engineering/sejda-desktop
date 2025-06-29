package org.sejda.impl.sambox;

import org.sejda.commons.util.IOUtils;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.impl.sambox.component.SamboxOutlineLevelsHandler;
import org.sejda.impl.sambox.component.split.PageDestinationsLevelPdfExtractor;
import org.sejda.model.exception.TaskException;
import org.sejda.model.input.PdfSource;
import org.sejda.model.input.PdfSourceOpener;
import org.sejda.model.outline.OutlineExtractPageDestinations;
import org.sejda.model.parameter.ExtractByOutlineParameters;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.sambox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/ExtractByOutlineTask.class */
public class ExtractByOutlineTask extends BaseTask<ExtractByOutlineParameters> {
    private static final Logger LOG = LoggerFactory.getLogger(ExtractByOutlineTask.class);
    private PDDocument document = null;
    private PdfSourceOpener<PDDocumentHandler> documentLoader;

    @Override // org.sejda.model.task.BaseTask, org.sejda.model.task.Task
    public void before(ExtractByOutlineParameters parameters, TaskExecutionContext executionContext) throws TaskException {
        super.before((ExtractByOutlineTask) parameters, executionContext);
        this.documentLoader = new DefaultPdfSourceOpener(executionContext);
    }

    @Override // org.sejda.model.task.Task
    public void execute(ExtractByOutlineParameters parameters) throws TaskException {
        for (PdfSource<?> source : parameters.getSourceList()) {
            LOG.debug("Opening {} ", source);
            executionContext().notifiableTaskMetadata().setCurrentSource(source);
            this.document = ((PDDocumentHandler) source.open(this.documentLoader)).getUnderlyingPDDocument();
            LOG.debug("Retrieving outline information for level {} and match regex {}", Integer.valueOf(parameters.getLevel()), parameters.getMatchingTitleRegEx());
            OutlineExtractPageDestinations pagesDestination = new SamboxOutlineLevelsHandler(this.document, parameters.getMatchingTitleRegEx()).getExtractPageDestinations(parameters.getLevel(), parameters.isIncludePageAfter());
            LOG.debug("Starting extraction by outline, level {} and match regex {}", Integer.valueOf(parameters.getLevel()), parameters.getMatchingTitleRegEx());
            new PageDestinationsLevelPdfExtractor(this.document, parameters, pagesDestination, source).extract(executionContext());
            IOUtils.closeQuietly(this.document);
        }
        executionContext().notifiableTaskMetadata().clearCurrentSource();
        LOG.debug("Extraction completed and outputs written to {}", parameters.getOutput());
    }

    @Override // org.sejda.model.task.Task
    public void after() {
        IOUtils.closeQuietly(this.document);
    }
}
