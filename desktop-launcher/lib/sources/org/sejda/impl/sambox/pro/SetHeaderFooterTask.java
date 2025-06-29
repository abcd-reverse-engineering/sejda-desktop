package org.sejda.impl.sambox.pro;

import java.io.File;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.sejda.commons.util.StringUtils;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.io.MultipleOutputWriter;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.model.FileOutput;
import org.sejda.core.support.prefix.NameGenerator;
import org.sejda.core.support.prefix.model.NameGenerationRequest;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.impl.sambox.component.PdfScaler;
import org.sejda.impl.sambox.pro.component.SetHeaderFooterWriter;
import org.sejda.impl.sambox.util.FontUtils;
import org.sejda.model.exception.TaskException;
import org.sejda.model.input.PdfSource;
import org.sejda.model.input.PdfSourceOpener;
import org.sejda.model.pdf.encryption.PdfAccessPermission;
import org.sejda.model.pro.parameter.SetHeaderFooterParameters;
import org.sejda.model.scale.ScaleType;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/SetHeaderFooterTask.class */
public class SetHeaderFooterTask extends BaseTask<SetHeaderFooterParameters> {
    private static final Logger LOG = LoggerFactory.getLogger(SetHeaderFooterTask.class);
    private int totalSteps;
    private PDDocumentHandler documentHandler = null;
    private MultipleOutputWriter outputWriter;
    private PdfSourceOpener<PDDocumentHandler> documentLoader;

    @Override // org.sejda.model.task.BaseTask, org.sejda.model.task.Task
    public void before(SetHeaderFooterParameters parameters, TaskExecutionContext executionContext) throws TaskException {
        super.before((SetHeaderFooterTask) parameters, executionContext);
        this.totalSteps = parameters.getSourceList().size();
        this.documentLoader = new DefaultPdfSourceOpener(executionContext);
        this.outputWriter = OutputWriters.newMultipleOutputWriter(parameters.getExistingOutputPolicy(), executionContext);
    }

    @Override // org.sejda.model.task.Task
    public void execute(SetHeaderFooterParameters parameters) throws IllegalStateException, TaskException {
        for (PdfSource<?> source : parameters.getSourceList()) {
            int fileNumber = executionContext().incrementAndGetOutputDocumentsCounter();
            LOG.debug("Opening {}", source);
            executionContext().notifiableTaskMetadata().setCurrentSource(source);
            this.documentHandler = (PDDocumentHandler) source.open(this.documentLoader);
            this.documentHandler.getPermissions().ensurePermission(PdfAccessPermission.MODIFY);
            this.documentHandler.setCreatorOnPDDocument();
            File tmpFile = IOUtils.createTemporaryBuffer(parameters.getOutput());
            LOG.debug("Created output on temporary buffer {}", tmpFile);
            this.documentHandler.setVersionOnPDDocument(parameters.getVersion());
            this.documentHandler.setCompress(parameters.isCompress());
            if (parameters.isAddMargins()) {
                new PdfScaler(ScaleType.CONTENT).scale(this.documentHandler.getUnderlyingPDDocument(), 0.9d);
            }
            String originalValue = parameters.getPattern();
            String value = FontUtils.removeUnsupportedCharacters(parameters.getPattern(), this.documentHandler.getUnderlyingPDDocument());
            if (!value.equals(originalValue)) {
                Set<Character> unsupportedChars = StringUtils.difference(originalValue, value);
                String displayUnsupportedChars = org.apache.commons.lang3.StringUtils.join((Iterable) unsupportedChars.stream().map(c -> {
                    return StringUtils.asUnicodes(c.toString());
                }).collect(Collectors.toList()), ",");
                ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).taskWarning(String.format("Unsupported characters (%s) were removed: '%s'", displayUnsupportedChars, org.apache.commons.lang3.StringUtils.abbreviate(originalValue, 20)));
            }
            SetHeaderFooterWriter footerWriter = new SetHeaderFooterWriter(this.documentHandler);
            try {
                int currentFileCounter = (fileNumber + parameters.getFileCountStartFrom().intValue()) - 1;
                String outName = (String) Optional.ofNullable(parameters.getSpecificResultFilename(fileNumber)).orElseGet(() -> {
                    return NameGenerator.nameGenerator(parameters.getOutputPrefix()).generate(NameGenerationRequest.nameRequest().originalName(source.getName()).fileNumber(currentFileCounter));
                });
                footerWriter.write(value, parameters, currentFileCounter, outName, executionContext());
                this.documentHandler.savePDDocument(tmpFile, parameters.getOutput().getEncryptionAtRestPolicy());
                this.outputWriter.addOutput(FileOutput.file(tmpFile).name(outName));
                footerWriter.close();
                ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).stepsCompleted(fileNumber).outOf(this.totalSteps);
            } catch (Throwable th) {
                try {
                    footerWriter.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        executionContext().notifiableTaskMetadata().clearCurrentSource();
        parameters.getOutput().accept(this.outputWriter);
    }

    @Override // org.sejda.model.task.Task
    public void after() {
        org.sejda.commons.util.IOUtils.closeQuietly(this.documentHandler);
    }
}
