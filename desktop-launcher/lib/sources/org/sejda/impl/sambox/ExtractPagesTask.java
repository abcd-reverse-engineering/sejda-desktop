package org.sejda.impl.sambox;

import java.io.File;
import java.util.Optional;
import java.util.Set;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.io.MultipleOutputWriter;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.model.FileOutput;
import org.sejda.core.support.prefix.NameGenerator;
import org.sejda.core.support.prefix.model.NameGenerationRequest;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.impl.sambox.component.PagesExtractor;
import org.sejda.impl.sambox.component.optimization.OptimizationRuler;
import org.sejda.model.exception.TaskException;
import org.sejda.model.input.PdfSource;
import org.sejda.model.input.PdfSourceOpener;
import org.sejda.model.parameter.ExtractPagesParameters;
import org.sejda.model.pdf.encryption.PdfAccessPermission;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/ExtractPagesTask.class */
public class ExtractPagesTask extends BaseTask<ExtractPagesParameters> {
    private static final Logger LOG = LoggerFactory.getLogger(ExtractPagesTask.class);
    private MultipleOutputWriter outputWriter;
    private PdfSourceOpener<PDDocumentHandler> documentLoader;
    private PDDocumentHandler sourceDocumentHandler;

    @Override // org.sejda.model.task.BaseTask, org.sejda.model.task.Task
    public void before(ExtractPagesParameters parameters, TaskExecutionContext executionContext) throws TaskException {
        super.before((ExtractPagesTask) parameters, executionContext);
        this.documentLoader = new DefaultPdfSourceOpener(executionContext);
        this.outputWriter = OutputWriters.newMultipleOutputWriter(parameters.getExistingOutputPolicy(), executionContext);
    }

    @Override // org.sejda.model.task.Task
    public void execute(ExtractPagesParameters parameters) throws TaskException {
        int currentStep = 0;
        int totalSteps = parameters.getSourceList().size();
        for (PdfSource<?> source : parameters.getSourceList()) {
            LOG.debug("Opening {}", source);
            executionContext().notifiableTaskMetadata().setCurrentSource(source);
            this.sourceDocumentHandler = (PDDocumentHandler) source.open(this.documentLoader);
            this.sourceDocumentHandler.getPermissions().ensurePermission(PdfAccessPermission.ASSEMBLE);
            Set<Integer> pages = parameters.getPages(this.sourceDocumentHandler.getNumberOfPages());
            if (pages == null || pages.isEmpty()) {
                executionContext().assertTaskIsLenient(noPagesErrorMessage(source, parameters));
                ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).taskWarning(noPagesErrorMessage(source, parameters));
            } else {
                LOG.debug("Extracting pages from {}, one file per range is '{}' ", source, Boolean.valueOf(parameters.isSeparateFileForEachRange()));
                PagesExtractor extractor = new PagesExtractor(this.sourceDocumentHandler.getUnderlyingPDDocument());
                try {
                    for (Set<Integer> pageSets : parameters.getPagesSets(this.sourceDocumentHandler.getNumberOfPages())) {
                        if (!pageSets.isEmpty()) {
                            File tmpFile = IOUtils.createTemporaryBuffer(parameters.getOutput());
                            LOG.debug("Created output temporary buffer {}", tmpFile);
                            int fileNumber = executionContext().incrementAndGetOutputDocumentsCounter();
                            String outName = (String) Optional.ofNullable(parameters.getSpecificResultFilename(fileNumber)).orElseGet(() -> {
                                return NameGenerator.nameGenerator(parameters.getOutputPrefix()).generate(NameGenerationRequest.nameRequest().originalName(source.getName()).fileNumber(fileNumber).page(((Integer) pageSets.iterator().next()).intValue()));
                            });
                            this.outputWriter.addOutput(FileOutput.file(tmpFile).name(outName));
                            LOG.trace("Extracting pages {}", pageSets);
                            extractor.retain(pageSets, executionContext());
                            if (new OptimizationRuler(parameters.getOptimizationPolicy()).apply(this.sourceDocumentHandler.getUnderlyingPDDocument()).booleanValue()) {
                                extractor.optimize();
                            }
                            extractor.setVersion(parameters.getVersion());
                            extractor.setCompress(parameters.isCompress());
                            extractor.save(tmpFile, parameters.discardOutline(), parameters.getOutput().getEncryptionAtRestPolicy());
                            extractor.reset();
                        }
                    }
                    extractor.close();
                } catch (Throwable th) {
                    try {
                        extractor.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            }
            executionContext().assertHasOutputDocuments("The task didn't generate any output file");
            org.sejda.commons.util.IOUtils.closeQuietly(this.sourceDocumentHandler);
            currentStep++;
            ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).stepsCompleted(currentStep).outOf(totalSteps);
        }
        executionContext().notifiableTaskMetadata().clearCurrentSource();
        parameters.getOutput().accept(this.outputWriter);
        LOG.debug("Pages extracted and written to {}", parameters.getOutput());
    }

    @Override // org.sejda.model.task.Task
    public void after() {
        closeResource();
    }

    private void closeResource() {
        org.sejda.commons.util.IOUtils.closeQuietly(this.sourceDocumentHandler);
    }

    private String noPagesErrorMessage(PdfSource<?> source, ExtractPagesParameters parameters) {
        if (parameters.isInvertSelection()) {
            return String.format("Document had all pages removed: %s", source.getName());
        }
        return String.format("No page has been selected for extraction from: %s", source.getName());
    }
}
