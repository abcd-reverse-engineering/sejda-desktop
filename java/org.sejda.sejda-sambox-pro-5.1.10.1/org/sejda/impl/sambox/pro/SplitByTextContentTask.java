package org.sejda.impl.sambox.pro;

import org.sejda.commons.util.IOUtils;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.impl.sambox.component.ReloadablePDDocumentHandler;
import org.sejda.impl.sambox.component.optimization.OptimizationRuler;
import org.sejda.impl.sambox.component.split.AbstractPdfSplitter;
import org.sejda.impl.sambox.pro.component.split.ByTextChangesPdfSplitter;
import org.sejda.impl.sambox.pro.component.split.SplitByTextChangesOutputStrategy;
import org.sejda.model.exception.TaskException;
import org.sejda.model.input.PdfSource;
import org.sejda.model.input.PdfSourceOpener;
import org.sejda.model.pro.parameter.SplitByTextContentParameters;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.sambox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/SplitByTextContentTask.class */
public class SplitByTextContentTask extends BaseTask<SplitByTextContentParameters> {
    private static final Logger LOG = LoggerFactory.getLogger(SplitByTextContentTask.class);
    private PdfSourceOpener<PDDocumentHandler> documentLoader;
    private AbstractPdfSplitter<SplitByTextContentParameters> splitter;

    public void before(SplitByTextContentParameters parameters, TaskExecutionContext executionContext) throws TaskException {
        super.before(parameters, executionContext);
        this.documentLoader = new DefaultPdfSourceOpener(executionContext);
    }

    public void execute(SplitByTextContentParameters parameters) throws TaskException {
        for (PdfSource<?> source : parameters.getSourceList()) {
            LOG.debug("Opening {}", source);
            executionContext().notifiableTaskMetadata().setCurrentSource(source);
            ReloadablePDDocumentHandler reloadablePDDocumentHandler = null;
            try {
                reloadablePDDocumentHandler = new ReloadablePDDocumentHandler(source, this.documentLoader);
                SplitByTextChangesOutputStrategy splitByTextChangesOutputStrategy = new SplitByTextChangesOutputStrategy(reloadablePDDocumentHandler, parameters.getTextArea(), parameters.getStartsWith(), parameters.getEndsWith()) { // from class: org.sejda.impl.sambox.pro.SplitByTextContentTask.1
                };
                PDDocument document = reloadablePDDocumentHandler.getDocumentHandler().getUnderlyingPDDocument();
                this.splitter = new ByTextChangesPdfSplitter(document, parameters, new OptimizationRuler(parameters.getOptimizationPolicy()).apply(document).booleanValue(), splitByTextChangesOutputStrategy);
                LOG.debug("Starting to split by text content");
                this.splitter.split(executionContext(), parameters.getOutputPrefix(), source);
                IOUtils.closeQuietly(reloadablePDDocumentHandler.getDocumentHandler());
            } catch (Throwable th) {
                IOUtils.closeQuietly(reloadablePDDocumentHandler.getDocumentHandler());
                throw th;
            }
        }
        executionContext().notifiableTaskMetadata().clearCurrentSource();
        LOG.debug("Input documents split and written to {}", parameters.getOutput());
    }

    public void after() {
        this.splitter = null;
    }
}
