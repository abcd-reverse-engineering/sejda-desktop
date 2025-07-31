package org.sejda.impl.sambox.pro;

import java.io.File;
import java.util.Optional;
import java.util.stream.Collectors;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.io.MultipleOutputWriter;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.model.FileOutput;
import org.sejda.core.support.prefix.NameGenerator;
import org.sejda.core.support.prefix.model.NameGenerationRequest;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.impl.sambox.pro.util.EncryptionUtils;
import org.sejda.model.exception.TaskException;
import org.sejda.model.input.PdfSource;
import org.sejda.model.input.PdfSourceOpener;
import org.sejda.model.pro.parameter.EncryptParameters;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.sejda.sambox.encryption.StandardSecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/EncryptTask.class */
public class EncryptTask extends BaseTask<EncryptParameters> {
    private static final Logger LOG = LoggerFactory.getLogger(EncryptTask.class);
    private int totalSteps;
    private PDDocumentHandler documentHandler = null;
    private MultipleOutputWriter outputWriter;
    private PdfSourceOpener<PDDocumentHandler> documentLoader;
    private StandardSecurity security;

    public void before(EncryptParameters parameters, TaskExecutionContext executionContext) throws TaskException {
        super.before(parameters, executionContext);
        this.totalSteps = parameters.getSourceList().size();
        this.documentLoader = new DefaultPdfSourceOpener(executionContext);
        this.outputWriter = OutputWriters.newMultipleOutputWriter(parameters.getExistingOutputPolicy(), executionContext);
        this.security = EncryptionUtils.securityFromParams(parameters);
    }

    public void execute(EncryptParameters parameters) throws TaskException {
        for (PdfSource<?> source : parameters.getSourceList()) {
            int fileNumber = executionContext().incrementAndGetOutputDocumentsCounter();
            LOG.debug("Opening {}", source);
            executionContext().notifiableTaskMetadata().setCurrentSource(source);
            try {
                this.documentHandler = (PDDocumentHandler) source.open(this.documentLoader);
                this.documentHandler.setCreatorOnPDDocument();
                File tmpFile = IOUtils.createTemporaryBuffer(parameters.getOutput());
                LOG.debug("Created output on temporary buffer {}", tmpFile);
                this.documentHandler.setVersionOnPDDocument(parameters.getVersion());
                this.documentHandler.setCompress(parameters.isCompress());
                this.documentHandler.savePDDocument(tmpFile, this.security, parameters.getOutput().getEncryptionAtRestPolicy());
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
        LOG.debug("Input documents encrypted using {}, standard security handler revision {} and written to {}", new Object[]{this.security.encryption, Integer.valueOf(this.security.encryption.revision.revisionNumber), parameters.getOutput()});
        LOG.debug("Permissions: {}", parameters.getPermissions().stream().map((v0) -> {
            return v0.getFriendlyName();
        }).collect(Collectors.joining(", ")));
    }

    public void after() {
        org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
    }
}
