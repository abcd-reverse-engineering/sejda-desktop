package org.sejda.impl.sambox.pro;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Optional;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.io.MultipleOutputWriter;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.model.FileOutput;
import org.sejda.core.support.prefix.NameGenerator;
import org.sejda.core.support.prefix.model.NameGenerationRequest;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.impl.sambox.pro.component.PdfTextExtractor;
import org.sejda.model.exception.TaskException;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.input.PdfSource;
import org.sejda.model.input.PdfSourceOpener;
import org.sejda.model.pdf.encryption.PdfAccessPermission;
import org.sejda.model.pro.parameter.ExtractTextParameters;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/ExtractTextTask.class */
public class ExtractTextTask extends BaseTask<ExtractTextParameters> {
    private static final Logger LOG = LoggerFactory.getLogger(ExtractTextTask.class);
    private int totalSteps;
    private PDDocumentHandler documentHandler = null;
    private MultipleOutputWriter outputWriter;
    private PdfSourceOpener<PDDocumentHandler> documentLoader;

    public void before(ExtractTextParameters parameters, TaskExecutionContext executionContext) throws TaskException {
        super.before(parameters, executionContext);
        this.totalSteps = parameters.getSourceList().size();
        this.documentLoader = new DefaultPdfSourceOpener(executionContext);
        this.outputWriter = OutputWriters.newMultipleOutputWriter(parameters.getExistingOutputPolicy(), executionContext);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.sejda.model.exception.TaskException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: org.sejda.model.exception.TaskIOException */
    public void execute(ExtractTextParameters parameters) throws TaskIOException, TaskException {
        int actualExtractions = 0;
        for (PdfSource<?> source : parameters.getSourceList()) {
            int fileNumber = executionContext().incrementAndGetOutputDocumentsCounter();
            LOG.debug("Opening {}", source);
            executionContext().notifiableTaskMetadata().setCurrentSource(source);
            this.documentHandler = (PDDocumentHandler) source.open(this.documentLoader);
            this.documentHandler.getPermissions().ensurePermission(PdfAccessPermission.COPY_AND_EXTRACT);
            File tmpFile = IOUtils.createTemporaryBuffer(parameters.getOutput());
            try {
                OutputStream tmpOut = parameters.getOutput().getEncryptionAtRestPolicy().encrypt(new FileOutputStream(tmpFile));
                LOG.debug("Created output on temporary buffer {}", tmpFile);
                PdfTextExtractor textExtractor = new PdfTextExtractor(Charset.forName(parameters.getTextEncoding()), tmpOut);
                try {
                    textExtractor.extract(this.documentHandler.getUnderlyingPDDocument());
                    if (textExtractor.wasEmptyOutput()) {
                        ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).taskWarning(String.format("Could not extract any text from %s", source.getName()));
                    } else {
                        String outName = (String) Optional.ofNullable(parameters.getSpecificResultFilename(fileNumber, ".txt")).orElseGet(() -> {
                            return NameGenerator.nameGenerator(parameters.getOutputPrefix()).generate(NameGenerationRequest.nameRequest("txt").originalName(source.getName()).fileNumber(fileNumber));
                        });
                        this.outputWriter.addOutput(FileOutput.file(tmpFile).name(outName));
                        actualExtractions++;
                    }
                    textExtractor.close();
                    org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
                    ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).stepsCompleted(fileNumber).outOf(this.totalSteps);
                } finally {
                }
            } catch (IOException ex) {
                throw new TaskIOException(ex);
            }
        }
        executionContext().notifiableTaskMetadata().clearCurrentSource();
        if (actualExtractions == 0) {
            throw new TaskException("No text could be extracted");
        }
        parameters.getOutput().accept(this.outputWriter);
        LOG.debug("Text extracted from input documents and written to {}", parameters.getOutput());
    }

    public void after() {
        org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
    }
}
