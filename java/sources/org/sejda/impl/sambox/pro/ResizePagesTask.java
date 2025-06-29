package org.sejda.impl.sambox.pro;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.io.MultipleOutputWriter;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.model.FileOutput;
import org.sejda.core.support.prefix.NameGenerator;
import org.sejda.core.support.prefix.model.NameGenerationRequest;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.impl.sambox.component.PdfScaler;
import org.sejda.impl.sambox.pro.component.PdfResizer;
import org.sejda.model.PageSize;
import org.sejda.model.exception.TaskException;
import org.sejda.model.input.PdfSource;
import org.sejda.model.input.PdfSourceOpener;
import org.sejda.model.pdf.encryption.PdfAccessPermission;
import org.sejda.model.pro.parameter.ResizePagesParameters;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/ResizePagesTask.class */
public class ResizePagesTask extends BaseTask<ResizePagesParameters> {
    private static final Logger LOG = LoggerFactory.getLogger(ResizePagesTask.class);
    private int totalSteps;
    private PDDocumentHandler documentHandler = null;
    private MultipleOutputWriter outputWriter;
    private PdfSourceOpener<PDDocumentHandler> documentLoader;

    @Override // org.sejda.model.task.BaseTask, org.sejda.model.task.Task
    public void before(ResizePagesParameters parameters, TaskExecutionContext executionContext) throws TaskException {
        super.before((ResizePagesTask) parameters, executionContext);
        this.totalSteps = parameters.getSourceList().size();
        this.documentLoader = new DefaultPdfSourceOpener(executionContext);
        this.outputWriter = OutputWriters.newMultipleOutputWriter(parameters.getExistingOutputPolicy(), executionContext);
    }

    @Override // org.sejda.model.task.Task
    public void execute(ResizePagesParameters parameters) throws TaskException {
        for (PdfSource<?> source : parameters.getSourceList()) {
            int fileNumber = executionContext().incrementAndGetOutputDocumentsCounter();
            LOG.debug("Opening {}", source);
            executionContext().notifiableTaskMetadata().setCurrentSource(source);
            try {
                this.documentHandler = (PDDocumentHandler) source.open(this.documentLoader);
                this.documentHandler.getPermissions().ensurePermission(PdfAccessPermission.MODIFY);
                this.documentHandler.setCreatorOnPDDocument();
                File tmpFile = IOUtils.createTemporaryBuffer(parameters.getOutput());
                LOG.debug("Created output on temporary buffer {}", tmpFile);
                Collection<PDPage> pages = new ArrayList<>();
                if (parameters.getPageSelection().isEmpty()) {
                    Iterator<PDPage> it = this.documentHandler.getPages().iterator();
                    while (it.hasNext()) {
                        PDPage p = it.next();
                        pages.add(p);
                    }
                } else {
                    Iterator<Integer> it2 = parameters.getPages(this.documentHandler.getNumberOfPages()).iterator();
                    while (it2.hasNext()) {
                        int pageNumber = it2.next().intValue();
                        pages.add(this.documentHandler.getPage(pageNumber));
                    }
                }
                if (Objects.nonNull(parameters.getMargins())) {
                    LOG.debug("Adding margins of {} (inches) to {} pages", parameters.getMargins(), Integer.valueOf(pages.size()));
                    PdfScaler.margin(this.documentHandler.getUnderlyingPDDocument(), pages, parameters.getMargins());
                } else {
                    PageSize pageSize = parameters.getPageSize();
                    if (pageSize != null) {
                        PDRectangle desiredPageSize = new PDRectangle(pageSize.getWidth(), pageSize.getHeight());
                        LOG.debug("Resizing {} pages to match {}", Integer.valueOf(pages.size()), desiredPageSize);
                        new PdfResizer().changePageSize(this.documentHandler.getUnderlyingPDDocument(), pages, desiredPageSize);
                    }
                }
                this.documentHandler.setVersionOnPDDocument(parameters.getVersion());
                this.documentHandler.setCompress(parameters.isCompress());
                this.documentHandler.savePDDocument(tmpFile, parameters.getOutput().getEncryptionAtRestPolicy());
                String outName = (String) Optional.ofNullable(parameters.getSpecificResultFilename(fileNumber)).orElseGet(() -> {
                    return NameGenerator.nameGenerator(parameters.getOutputPrefix()).generate(NameGenerationRequest.nameRequest().originalName(source.getName()).fileNumber(fileNumber));
                });
                this.outputWriter.addOutput(FileOutput.file(tmpFile).name(outName));
                org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
                ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).stepsCompleted(fileNumber).outOf(this.totalSteps);
            } catch (Throwable th) {
                org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
                throw th;
            }
        }
        executionContext().notifiableTaskMetadata().clearCurrentSource();
        parameters.getOutput().accept(this.outputWriter);
        LOG.debug("Input documents scaled and written to {}", parameters.getOutput());
    }

    @Override // org.sejda.model.task.Task
    public void after() {
        org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
    }
}
