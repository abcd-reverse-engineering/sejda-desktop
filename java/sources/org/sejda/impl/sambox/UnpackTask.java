package org.sejda.impl.sambox;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import org.apache.commons.io.FileUtils;
import org.sejda.commons.util.IOUtils;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.io.MultipleOutputWriter;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.model.FileOutput;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.model.exception.TaskException;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.input.PdfSource;
import org.sejda.model.input.PdfSourceOpener;
import org.sejda.model.parameter.UnpackParameters;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.sambox.pdmodel.PDEmbeddedFilesNameTreeNode;
import org.sejda.sambox.pdmodel.common.PDNameTreeNode;
import org.sejda.sambox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationFileAttachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/UnpackTask.class */
public class UnpackTask extends BaseTask<UnpackParameters> {
    private static final Logger LOG = LoggerFactory.getLogger(UnpackTask.class);
    private int totalSteps;
    private PDDocumentHandler sourceDocumentHandler = null;
    private MultipleOutputWriter outputWriter;
    private PdfSourceOpener<PDDocumentHandler> documentLoader;

    @Override // org.sejda.model.task.BaseTask, org.sejda.model.task.Task
    public void before(UnpackParameters parameters, TaskExecutionContext executionContext) throws TaskException {
        super.before((UnpackTask) parameters, executionContext);
        this.totalSteps = parameters.getSourceList().size();
        this.documentLoader = new DefaultPdfSourceOpener(executionContext);
        this.outputWriter = OutputWriters.newMultipleOutputWriter(parameters.getExistingOutputPolicy(), executionContext);
    }

    @Override // org.sejda.model.task.Task
    public void execute(UnpackParameters parameters) throws TaskException {
        int currentStep = 0;
        for (PdfSource<?> source : parameters.getSourceList()) {
            currentStep++;
            try {
                LOG.debug("Opening {}", source);
                executionContext().notifiableTaskMetadata().setCurrentSource(source);
                this.sourceDocumentHandler = (PDDocumentHandler) source.open(this.documentLoader);
                Map<String, PDComplexFileSpecification> names = new HashMap<>();
                PDEmbeddedFilesNameTreeNode ef = (PDEmbeddedFilesNameTreeNode) Optional.ofNullable(this.sourceDocumentHandler.getUnderlyingPDDocument().getDocumentCatalog().getNames()).map((v0) -> {
                    return v0.getEmbeddedFiles();
                }).orElse(null);
                collectNamesVisitingTree(ef, names);
                Stream.concat(names.values().stream(), this.sourceDocumentHandler.getPages().stream().flatMap(p -> {
                    return p.getAnnotations().stream();
                }).filter(a -> {
                    return a instanceof PDAnnotationFileAttachment;
                }).map(a2 -> {
                    return (PDAnnotationFileAttachment) a2;
                }).map((v0) -> {
                    return v0.getFile();
                }).filter(f -> {
                    return f instanceof PDComplexFileSpecification;
                }).map(f2 -> {
                    return (PDComplexFileSpecification) f2;
                })).forEach(this::unpack);
                IOUtils.closeQuietly(this.sourceDocumentHandler);
                ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).stepsCompleted(currentStep).outOf(this.totalSteps);
            } catch (Throwable th) {
                IOUtils.closeQuietly(this.sourceDocumentHandler);
                throw th;
            }
        }
        executionContext().notifiableTaskMetadata().clearCurrentSource();
        parameters.getOutput().accept(this.outputWriter);
        LOG.debug("Attachments unpacked and written to {}", parameters.getOutput());
    }

    private void unpack(PDComplexFileSpecification file) {
        Optional.ofNullable(file.getBestEmbeddedFile()).ifPresent(e -> {
            try {
                File tmpFile = org.sejda.model.util.IOUtils.createTemporaryBuffer();
                LOG.debug("Created output temporary buffer {}", tmpFile);
                InputStream is = e.createInputStream();
                try {
                    FileUtils.copyInputStreamToFile(is, tmpFile);
                    LOG.debug("Attachment '{}' unpacked to temporary buffer", file.getFilename());
                    if (is != null) {
                        is.close();
                    }
                    this.outputWriter.addOutput(FileOutput.file(tmpFile).name(file.getFilename()));
                } finally {
                }
            } catch (IOException | TaskIOException ioe) {
                LOG.error("Unable to extract file", ioe);
            }
        });
    }

    private void collectNamesVisitingTree(PDNameTreeNode<PDComplexFileSpecification> node, Map<String, PDComplexFileSpecification> names) throws TaskIOException {
        try {
            if (Objects.nonNull(node)) {
                Map<? extends String, ? extends PDComplexFileSpecification> names2 = node.getNames();
                if (names2 != null) {
                    names.putAll(names2);
                } else {
                    Iterator it = node.getKids().iterator();
                    while (it.hasNext()) {
                        PDNameTreeNode<PDComplexFileSpecification> currNode = (PDNameTreeNode) it.next();
                        collectNamesVisitingTree(currNode, names);
                    }
                }
            }
        } catch (IOException ioe) {
            throw new TaskIOException(ioe);
        }
    }

    @Override // org.sejda.model.task.Task
    public void after() {
        IOUtils.closeQuietly(this.sourceDocumentHandler);
    }
}
