package org.sejda.impl.sambox.pro;

import java.io.File;
import java.util.Iterator;
import java.util.Optional;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.io.MultipleOutputWriter;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.model.FileOutput;
import org.sejda.core.support.prefix.NameGenerator;
import org.sejda.core.support.prefix.model.NameGenerationRequest;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.impl.sambox.pro.component.optimization.DocumentOptimizer;
import org.sejda.impl.sambox.pro.component.optimization.PagesOptimizer;
import org.sejda.impl.sambox.pro.util.PixelCompareUtils;
import org.sejda.model.exception.TaskException;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.input.PdfFileSource;
import org.sejda.model.input.PdfSource;
import org.sejda.model.input.PdfSourceOpener;
import org.sejda.model.pro.optimization.FileAlreadyWellOptimizedException;
import org.sejda.model.pro.parameter.OptimizeParameters;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.sejda.sambox.pdmodel.PDPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/OptimizeTask.class */
public class OptimizeTask extends BaseTask<OptimizeParameters> {
    private static final Logger LOG = LoggerFactory.getLogger(OptimizeTask.class);
    private int totalSteps;
    private PDDocumentHandler documentHandler = null;
    private MultipleOutputWriter outputWriter;
    private DocumentOptimizer documentOptimizer;
    private PagesOptimizer pagesOptimizer;
    private PdfSourceOpener<PDDocumentHandler> documentLoader;

    public void before(OptimizeParameters parameters, TaskExecutionContext executionContext) throws TaskException {
        super.before(parameters, executionContext);
        this.totalSteps = parameters.getSourceList().size();
        this.documentLoader = new DefaultPdfSourceOpener(executionContext);
        this.outputWriter = OutputWriters.newMultipleOutputWriter(parameters.getExistingOutputPolicy(), executionContext);
    }

    public void execute(OptimizeParameters parameters) throws TaskIOException, FileAlreadyWellOptimizedException, TaskException {
        for (PdfSource<?> source : parameters.getSourceList()) {
            int fileNumber = executionContext().incrementAndGetOutputDocumentsCounter();
            LOG.debug("Opening {}", source);
            executionContext().notifiableTaskMetadata().setCurrentSource(source);
            this.documentHandler = (PDDocumentHandler) source.open(this.documentLoader);
            this.documentHandler.setCreatorOnPDDocument();
            File tmpFile = IOUtils.createTemporaryBuffer(parameters.getOutput());
            LOG.debug("Created output on temporary buffer {}", tmpFile);
            this.documentOptimizer = new DocumentOptimizer(parameters.getOptimizations());
            this.pagesOptimizer = new PagesOptimizer(parameters);
            LOG.debug("Starting optimization");
            int pageNum = 0;
            Iterator it = this.documentHandler.getPages().iterator();
            while (it.hasNext()) {
                PDPage page = (PDPage) it.next();
                pageNum++;
                try {
                    this.pagesOptimizer.accept(page);
                } catch (Exception e) {
                    ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).taskWarning(String.format("Page %d was skipped, could not be optimized", Integer.valueOf(pageNum)), e);
                }
            }
            this.pagesOptimizer.optimizationContext.fontSubsettingContext().subset();
            this.pagesOptimizer.optimizationContext.fontSubsettingContext().subsettableFonts().forEach((v0) -> {
                v0.subset();
            });
            this.documentOptimizer.accept(this.documentHandler.getUnderlyingPDDocument());
            this.documentHandler.setVersionOnPDDocument(parameters.getVersion());
            this.documentHandler.setCompress(parameters.isCompress());
            this.documentHandler.savePDDocument(tmpFile, parameters.getOutput().getEncryptionAtRestPolicy());
            if (parameters.isFailIfOutputSizeNotReduced() && parameters.getSourceList().size() == 1) {
                assertSizeHasReduced(source, tmpFile);
            }
            String outName = (String) Optional.ofNullable(parameters.getSpecificResultFilename(fileNumber)).orElseGet(() -> {
                return NameGenerator.nameGenerator(parameters.getOutputPrefix()).generate(NameGenerationRequest.nameRequest().originalName(source.getName()).fileNumber(fileNumber));
            });
            this.outputWriter.addOutput(FileOutput.file(tmpFile).name(outName));
            org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
            if (parameters.isPerformVisualComparisonChecks()) {
                performVisualChecks(source, tmpFile, parameters);
            }
            ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).stepsCompleted(fileNumber).outOf(this.totalSteps);
        }
        executionContext().notifiableTaskMetadata().clearCurrentSource();
        parameters.getOutput().accept(this.outputWriter);
        LOG.debug("Input documents optimized and written to {}", parameters.getOutput());
    }

    private void performVisualChecks(PdfSource<?> source, File tmpFile, OptimizeParameters parameters) throws TaskIOException {
        PDDocumentHandler resultHandler = null;
        try {
            LOG.info("Performing visual comparison checks");
            this.documentHandler = (PDDocumentHandler) source.open(this.documentLoader);
            PdfFileSource resultSource = PdfFileSource.newInstanceNoPassword(tmpFile);
            resultSource.setEncryptionAtRestPolicy(parameters.getOutput().getEncryptionAtRestPolicy());
            resultHandler = (PDDocumentHandler) resultSource.open(this.documentLoader);
            new PixelCompareUtils().assertSimilar(resultHandler.getUnderlyingPDDocument(), this.documentHandler.getUnderlyingPDDocument());
            LOG.info("Done performing visual comparison checks");
            org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
            org.sejda.commons.util.IOUtils.closeQuietly(resultHandler);
        } catch (Throwable th) {
            org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
            org.sejda.commons.util.IOUtils.closeQuietly(resultHandler);
            throw th;
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.sejda.model.pro.optimization.FileAlreadyWellOptimizedException */
    private void assertSizeHasReduced(PdfSource<?> source, File tmpFile) throws FileAlreadyWellOptimizedException {
        Object source2 = source.getSource();
        if (source2 instanceof File) {
            File sourceFile = (File) source2;
            Long fromKb = Long.valueOf(sourceFile.length() / 1024);
            Long toKb = Long.valueOf(tmpFile.length() / 1024);
            LOG.info("Source has {} Kb, compressed has {} Kb", fromKb, toKb);
            if (toKb.longValue() >= fromKb.longValue()) {
                throw new FileAlreadyWellOptimizedException();
            }
        }
    }

    public void after() {
        org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
    }
}
