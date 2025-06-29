package org.sejda.impl.sambox.component.split;

import java.io.File;
import org.apache.commons.lang3.StringUtils;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.io.MultipleOutputWriter;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.model.FileOutput;
import org.sejda.core.support.prefix.NameGenerator;
import org.sejda.core.support.prefix.model.NameGenerationRequest;
import org.sejda.core.support.util.HumanReadableSize;
import org.sejda.impl.sambox.component.PagesExtractor;
import org.sejda.model.exception.TaskException;
import org.sejda.model.input.PdfSource;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;
import org.sejda.model.split.NextOutputStrategy;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.sejda.sambox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/split/AbstractPdfSplitter.class */
public abstract class AbstractPdfSplitter<T extends MultiplePdfSourceMultipleOutputParameters> {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractPdfSplitter.class);
    private PDDocument document;
    private T parameters;
    private int totalPages;
    private MultipleOutputWriter outputWriter;
    private boolean optimize;
    private boolean discardOutline;

    public abstract NameGenerationRequest enrichNameGenerationRequest(NameGenerationRequest nameGenerationRequest);

    public abstract NextOutputStrategy nextOutputStrategy();

    public AbstractPdfSplitter(PDDocument document, T parameters, boolean optimize, boolean discardOutline) {
        this.optimize = false;
        this.discardOutline = false;
        this.document = document;
        this.parameters = parameters;
        this.totalPages = document.getNumberOfPages();
        this.optimize = optimize;
        this.discardOutline = discardOutline;
    }

    public void split(TaskExecutionContext executionContext, String outputPrefix, PdfSource<?> source) throws TaskException {
        nextOutputStrategy().ensureIsValid();
        this.outputWriter = OutputWriters.newMultipleOutputWriter(this.parameters.getExistingOutputPolicy(), executionContext);
        NameGenerator nameGen = NameGenerator.nameGenerator(outputPrefix);
        PagesExtractor extractor = supplyPagesExtractor(this.document);
        File tmpFile = null;
        for (int page = 1; page <= this.totalPages; page++) {
            try {
                if (nextOutputStrategy().isOpening(Integer.valueOf(page))) {
                    LOG.debug("Starting split at page {} of the original document", Integer.valueOf(page));
                    onOpen(page);
                    tmpFile = IOUtils.createTemporaryBuffer(this.parameters.getOutput());
                    LOG.debug("Created output temporary buffer {}", tmpFile);
                    int fileNumber = executionContext.incrementAndGetOutputDocumentsCounter();
                    String outName = this.parameters.getSpecificResultFilename(fileNumber);
                    if (StringUtils.isBlank(outName)) {
                        outName = nameGen.generate(enrichNameGenerationRequest(NameGenerationRequest.nameRequest().page(page).originalName(source.getName()).fileNumber(fileNumber)));
                    }
                    this.outputWriter.addOutput(FileOutput.file(tmpFile).name(outName));
                }
                LOG.trace("Retaining page {} of the original document", Integer.valueOf(page));
                onRetain(page);
                extractor.retain(page, executionContext);
                ApplicationEventsNotifier.notifyEvent(executionContext.notifiableTaskMetadata()).stepsCompleted(page).outOf(this.totalPages);
                if (nextOutputStrategy().isClosing(Integer.valueOf(page)) || page == this.totalPages) {
                    onClose(page);
                    extractor.setVersion(this.parameters.getVersion());
                    extractor.setCompress(this.parameters.isCompress());
                    if (this.optimize) {
                        extractor.optimize();
                    }
                    extractor.save(tmpFile, this.discardOutline, this.parameters.getOutput().getEncryptionAtRestPolicy());
                    extractor.reset();
                    LOG.debug("Ending split at page {} of the original document, generated document size is {}", Integer.valueOf(page), HumanReadableSize.toString(tmpFile.length()));
                }
            } catch (Throwable th) {
                if (extractor != null) {
                    try {
                        extractor.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        if (extractor != null) {
            extractor.close();
        }
        this.parameters.getOutput().accept(this.outputWriter);
    }

    protected void onOpen(int page) throws TaskException {
    }

    protected void onRetain(int page) throws TaskException {
    }

    protected void onClose(int page) throws TaskException {
    }

    protected PagesExtractor supplyPagesExtractor(PDDocument document) {
        return new PagesExtractor(document);
    }
}
