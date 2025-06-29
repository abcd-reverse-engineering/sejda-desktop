package org.sejda.impl.sambox;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.sejda.commons.LookupTable;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.io.MultipleOutputWriter;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.model.FileOutput;
import org.sejda.core.support.prefix.NameGenerator;
import org.sejda.core.support.prefix.model.NameGenerationRequest;
import org.sejda.impl.sambox.component.AnnotationsDistiller;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.impl.sambox.component.SignatureClipper;
import org.sejda.model.exception.TaskException;
import org.sejda.model.exception.TaskExecutionException;
import org.sejda.model.input.PdfSource;
import org.sejda.model.input.PdfSourceOpener;
import org.sejda.model.parameter.AddBackPagesParameters;
import org.sejda.model.pdf.encryption.PdfAccessPermission;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/AddBackPagesTask.class */
public class AddBackPagesTask extends BaseTask<AddBackPagesParameters> {
    private static final Logger LOG = LoggerFactory.getLogger(AddBackPagesTask.class);
    private int totalSteps;
    private PDDocumentHandler sourceDocumentHandler = null;
    private MultipleOutputWriter outputWriter;
    private PdfSourceOpener<PDDocumentHandler> documentLoader;
    private PDDocumentHandler backPagesSource;
    private PDDocumentHandler destinationDocument;

    @Override // org.sejda.model.task.BaseTask, org.sejda.model.task.Task
    public void before(AddBackPagesParameters parameters, TaskExecutionContext executionContext) throws TaskException {
        super.before((AddBackPagesTask) parameters, executionContext);
        this.totalSteps = parameters.getSourceList().size();
        this.documentLoader = new DefaultPdfSourceOpener(executionContext);
        this.outputWriter = OutputWriters.newMultipleOutputWriter(parameters.getExistingOutputPolicy(), executionContext);
    }

    @Override // org.sejda.model.task.Task
    public void execute(AddBackPagesParameters parameters) throws IllegalStateException, TaskException {
        LOG.debug("Opening back pages source {}", parameters.getBackPagesSource());
        executionContext().notifiableTaskMetadata().setCurrentSource(parameters.getBackPagesSource());
        this.backPagesSource = (PDDocumentHandler) parameters.getBackPagesSource().open(this.documentLoader);
        List<PDPage> back = parameters.getPages(this.backPagesSource.getNumberOfPages()).stream().map(p -> {
            return this.backPagesSource.getPage(p.intValue());
        }).toList();
        if (back.size() == 0) {
            throw new TaskExecutionException("No back page was selected");
        }
        LOG.debug("Retrieved {} back pages", Integer.valueOf(back.size()));
        for (PdfSource<?> source : parameters.getSourceList()) {
            int fileNumber = executionContext().incrementAndGetOutputDocumentsCounter();
            executionContext().notifiableTaskMetadata().setCurrentSource(source);
            this.destinationDocument = new PDDocumentHandler();
            this.destinationDocument.setCreatorOnPDDocument();
            this.destinationDocument.setVersionOnPDDocument(parameters.getVersion());
            this.destinationDocument.setCompress(parameters.isCompress());
            LOG.debug("Opening {}", source);
            this.sourceDocumentHandler = (PDDocumentHandler) source.open(this.documentLoader);
            this.sourceDocumentHandler.getPermissions().ensurePermission(PdfAccessPermission.ASSEMBLE);
            File tmpFile = IOUtils.createTemporaryBuffer(parameters.getOutput());
            LOG.debug("Created output on temporary buffer {}", tmpFile);
            int pageCounter = 0;
            LookupTable<PDPage> pagesLookup = new LookupTable<>();
            LOG.debug("Adding pages and back pages");
            Iterator<PDPage> it = this.sourceDocumentHandler.getPages().iterator();
            while (it.hasNext()) {
                PDPage current = it.next();
                pagesLookup.addLookupEntry(current, this.destinationDocument.importPage(current));
                pageCounter++;
                if (pageCounter % parameters.getStep() == 0) {
                    back.forEach(p2 -> {
                        this.destinationDocument.importPage(p2);
                    });
                }
            }
            LookupTable<PDAnnotation> annotationsLookup = new AnnotationsDistiller(this.sourceDocumentHandler.getUnderlyingPDDocument()).retainRelevantAnnotations(pagesLookup);
            SignatureClipper.clipSignatures(annotationsLookup.values());
            this.destinationDocument.savePDDocument(tmpFile, parameters.getOutput().getEncryptionAtRestPolicy());
            String outName = (String) Optional.ofNullable(parameters.getSpecificResultFilename(fileNumber)).orElseGet(() -> {
                return NameGenerator.nameGenerator(parameters.getOutputPrefix()).generate(NameGenerationRequest.nameRequest().originalName(source.getName()).fileNumber(fileNumber));
            });
            this.outputWriter.addOutput(FileOutput.file(tmpFile).name(outName));
            org.sejda.commons.util.IOUtils.closeQuietly(this.destinationDocument);
            org.sejda.commons.util.IOUtils.closeQuietly(this.sourceDocumentHandler);
            ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).stepsCompleted(fileNumber).outOf(this.totalSteps);
        }
        executionContext().notifiableTaskMetadata().clearCurrentSource();
        org.sejda.commons.util.IOUtils.closeQuietly(this.backPagesSource);
        parameters.getOutput().accept(this.outputWriter);
        LOG.debug("Back pages added after every {} pages to {} input documents and written to {}", new Object[]{Integer.valueOf(parameters.getStep()), Integer.valueOf(parameters.getSourceList().size()), parameters.getOutput()});
    }

    @Override // org.sejda.model.task.Task
    public void after() {
        org.sejda.commons.util.IOUtils.closeQuietly(this.backPagesSource);
        org.sejda.commons.util.IOUtils.closeQuietly(this.destinationDocument);
        org.sejda.commons.util.IOUtils.closeQuietly(this.sourceDocumentHandler);
    }
}
