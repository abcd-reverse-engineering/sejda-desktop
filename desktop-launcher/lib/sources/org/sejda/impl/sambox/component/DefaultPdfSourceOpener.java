package org.sejda.impl.sambox.component;

import java.io.IOException;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.exception.TaskWrongPasswordException;
import org.sejda.model.input.PdfFileSource;
import org.sejda.model.input.PdfSource;
import org.sejda.model.input.PdfSourceOpener;
import org.sejda.model.input.PdfStreamSource;
import org.sejda.model.input.PdfURLSource;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.sambox.input.PDFParser;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.encryption.InvalidPasswordException;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/DefaultPdfSourceOpener.class */
public class DefaultPdfSourceOpener implements PdfSourceOpener<PDDocumentHandler> {
    private static final String WRONG_PWD_MESSAGE = "Unable to open '%s' due to a wrong password.";
    private static final String ERROR_MESSAGE = "An error occurred opening the source: %s.";
    private static final String WARNING_PARSE_ERRORS_MESSAGE = "Errors were detected when reading document: %s. Please verify the results carefully.";
    private final TaskExecutionContext taskExecutionContext;

    public DefaultPdfSourceOpener() {
        this.taskExecutionContext = null;
    }

    public DefaultPdfSourceOpener(TaskExecutionContext taskExecutionContext) {
        this.taskExecutionContext = taskExecutionContext;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.sejda.model.input.PdfSourceOpener
    public PDDocumentHandler open(PdfURLSource source) throws TaskIOException {
        return openGeneric(source);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.sejda.model.input.PdfSourceOpener
    public PDDocumentHandler open(PdfFileSource source) throws TaskIOException {
        return openGeneric(source);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.sejda.model.input.PdfSourceOpener
    public PDDocumentHandler open(PdfStreamSource source) throws TaskIOException {
        return openGeneric(source);
    }

    private PDDocumentHandler openGeneric(PdfSource<?> source) throws TaskIOException {
        try {
            PDDocument document = PDFParser.parse(source.getSeekableSource(), source.getPassword());
            if (document.hasParseErrors() && this.taskExecutionContext != null) {
                ApplicationEventsNotifier.notifyEvent(this.taskExecutionContext.notifiableTaskMetadata()).taskWarningOnce(String.format(WARNING_PARSE_ERRORS_MESSAGE, source.getName()));
            }
            return new PDDocumentHandler(document);
        } catch (InvalidPasswordException ipe) {
            throw new TaskWrongPasswordException(String.format(WRONG_PWD_MESSAGE, source.getName()), ipe);
        } catch (IOException e) {
            throw new TaskIOException(String.format(ERROR_MESSAGE, source), e);
        }
    }
}
