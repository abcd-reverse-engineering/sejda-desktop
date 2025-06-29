package org.sejda.impl.sambox.pro;

import java.io.File;
import java.util.Optional;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.io.MultipleOutputWriter;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.model.FileOutput;
import org.sejda.core.support.prefix.NameGenerator;
import org.sejda.core.support.prefix.model.NameGenerationRequest;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.impl.sambox.pro.component.PdfWatermarker;
import org.sejda.model.exception.TaskException;
import org.sejda.model.input.PdfSource;
import org.sejda.model.input.PdfSourceOpener;
import org.sejda.model.pdf.encryption.PdfAccessPermission;
import org.sejda.model.pro.parameter.WatermarkParameters;
import org.sejda.model.pro.watermark.Watermark;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.sejda.sambox.pdmodel.PageNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/WatermarkTask.class */
public class WatermarkTask extends BaseTask<WatermarkParameters> {
    private static final Logger LOG = LoggerFactory.getLogger(WatermarkTask.class);
    private int totalSteps;
    private PDDocumentHandler documentHandler = null;
    private MultipleOutputWriter outputWriter;
    private PdfSourceOpener<PDDocumentHandler> documentLoader;

    @Override // org.sejda.model.task.BaseTask, org.sejda.model.task.Task
    public void before(WatermarkParameters parameters, TaskExecutionContext executionContext) throws TaskException {
        super.before((WatermarkTask) parameters, executionContext);
        this.totalSteps = parameters.getSourceList().size() * parameters.getWatermarks().size();
        this.documentLoader = new DefaultPdfSourceOpener(executionContext);
        this.outputWriter = OutputWriters.newMultipleOutputWriter(parameters.getExistingOutputPolicy(), executionContext);
    }

    @Override // org.sejda.model.task.Task
    public void execute(WatermarkParameters parameters) throws TaskException {
        for (PdfSource<?> source : parameters.getSourceList()) {
            for (Watermark watermark : parameters.getWatermarks()) {
                int fileNumber = executionContext().incrementAndGetOutputDocumentsCounter();
                try {
                    LOG.debug("Opening {}", source);
                    executionContext().notifiableTaskMetadata().setCurrentSource(source);
                    this.documentHandler = (PDDocumentHandler) source.open(this.documentLoader);
                    this.documentHandler.getPermissions().ensurePermission(PdfAccessPermission.MODIFY);
                    this.documentHandler.setCreatorOnPDDocument();
                    String watermarkName = watermark.getName();
                    LOG.debug("Applying watermark {} {}", watermarkName, watermark);
                    File tmpFile = IOUtils.createTemporaryBuffer(parameters.getOutput());
                    LOG.debug("Created output on temporary buffer {}", tmpFile);
                    PdfWatermarker watermarker = new PdfWatermarker(watermark, this.documentHandler.getUnderlyingPDDocument());
                    for (Integer page : parameters.getPages(this.documentHandler.getNumberOfPages())) {
                        try {
                            watermarker.mark(this.documentHandler.getPage(page.intValue()));
                        } catch (PageNotFoundException e) {
                            executionContext().assertTaskIsLenient(e);
                            ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).taskWarning(String.format("Page %d was skipped, could not be watermarked", page), e);
                        }
                    }
                    this.documentHandler.setVersionOnPDDocument(parameters.getVersion());
                    this.documentHandler.setCompress(parameters.isCompress());
                    this.documentHandler.savePDDocument(tmpFile, parameters.getOutput().getEncryptionAtRestPolicy());
                    String outName = (String) Optional.ofNullable(parameters.getSpecificResultFilename(fileNumber)).orElseGet(() -> {
                        return NameGenerator.nameGenerator(parameters.getOutputPrefix()).generate(NameGenerationRequest.nameRequest().text(watermarkName).originalName(source.getName()).fileNumber(fileNumber));
                    });
                    this.outputWriter.addOutput(FileOutput.file(tmpFile).name(outName));
                    org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
                    ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).stepsCompleted(fileNumber).outOf(this.totalSteps);
                } catch (Throwable th) {
                    org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
                    throw th;
                }
            }
        }
        executionContext().notifiableTaskMetadata().clearCurrentSource();
        parameters.getOutput().accept(this.outputWriter);
        LOG.debug("Input documents watermarked and written to {}", parameters.getOutput());
    }

    @Override // org.sejda.model.task.Task
    public void after() {
        org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
    }
}
