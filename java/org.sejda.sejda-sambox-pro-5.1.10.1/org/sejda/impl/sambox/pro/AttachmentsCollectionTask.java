package org.sejda.impl.sambox.pro;

import java.io.File;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.SingleOutputWriter;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.impl.sambox.component.ReadOnlyFilteredCOSStream;
import org.sejda.impl.sambox.pro.component.AttachmentsSummaryCreator;
import org.sejda.model.exception.TaskException;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.input.Source;
import org.sejda.model.pdf.viewerpreference.PdfPageMode;
import org.sejda.model.pro.parameter.AttachmentsCollectionParameters;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSInteger;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.PDDocumentNameDictionary;
import org.sejda.sambox.pdmodel.PDEmbeddedFilesNameTreeNode;
import org.sejda.sambox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.sejda.sambox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationFileAttachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/AttachmentsCollectionTask.class */
public class AttachmentsCollectionTask extends BaseTask<AttachmentsCollectionParameters> {
    private static final Logger LOG = LoggerFactory.getLogger(AttachmentsCollectionTask.class);
    private static final COSName COLLECTION_ITEM_ORDER_FIELD = COSName.getPDFName("Sejda-Order");
    private int totalSteps;
    private SingleOutputWriter outputWriter;
    private PDDocumentHandler destinationDocument;
    private AttachmentsSummaryCreator tocCreator;

    public void before(AttachmentsCollectionParameters parameters, TaskExecutionContext executionContext) throws TaskException {
        super.before(parameters, executionContext);
        this.totalSteps = parameters.getSourceList().size();
        this.outputWriter = OutputWriters.newSingleOutputWriter(parameters.getExistingOutputPolicy(), executionContext);
    }

    public void execute(AttachmentsCollectionParameters parameters) throws TaskIOException, TaskException {
        int currentStep = 0;
        File tmpFile = IOUtils.createTemporaryBuffer(parameters.getOutput());
        this.outputWriter.taskOutput(tmpFile);
        LOG.debug("Temporary output set to {}", tmpFile);
        this.destinationDocument = new PDDocumentHandler();
        this.destinationDocument.setCreatorOnPDDocument();
        this.destinationDocument.setVersionOnPDDocument(parameters.getVersion());
        this.destinationDocument.getUnderlyingPDDocument().requireMinVersion("1.7");
        this.destinationDocument.setCompress(parameters.isCompress());
        this.destinationDocument.setPageModeOnDocument(PdfPageMode.USE_ATTACHMENTS);
        this.tocCreator = new AttachmentsSummaryCreator(this.destinationDocument.getUnderlyingPDDocument());
        PDEmbeddedFilesNameTreeNode embeddedFiles = new PDEmbeddedFilesNameTreeNode();
        Map<String, PDComplexFileSpecification> names = new HashMap<>();
        COSDictionary collection = new COSDictionary();
        collection.setName(COSName.getPDFName("View"), parameters.getInitialView().value);
        collection.setItem(COSName.TYPE, COSName.getPDFName("Collection"));
        collection.setItem(COSName.getPDFName("Sort"), createSortDictionary());
        LOG.trace("Added sort dictionary");
        collection.setItem(COSName.getPDFName("Schema"), createSchemaDictionary());
        LOG.trace("Added schema dictionary");
        for (Source<?> source : parameters.getSourceList()) {
            executionContext().notifiableTaskMetadata().setCurrentSource(source);
            PDComplexFileSpecification fileSpec = new PDComplexFileSpecification((COSDictionary) null);
            fileSpec.setFileUnicode(source.getName());
            fileSpec.setFile(source.getName());
            COSDictionary collectionItem = new COSDictionary();
            collectionItem.setInt(COLLECTION_ITEM_ORDER_FIELD, currentStep);
            fileSpec.setCollectionItem(collectionItem);
            PDEmbeddedFile embeddedFile = embeddedFileFromSource(source);
            fileSpec.setEmbeddedFileUnicode(embeddedFile);
            fileSpec.setEmbeddedFile(embeddedFile);
            names.put(currentStep + source.getName(), fileSpec);
            collection.putIfAbsent(COSName.D, currentStep + source.getName());
            this.tocCreator.appendItem(FilenameUtils.getName(source.getName()), attachmentAnnotation(fileSpec));
            currentStep++;
            ApplicationEventsNotifier.notifyEvent(executionContext().notifiableTaskMetadata()).stepsCompleted(currentStep).outOf(this.totalSteps);
            LOG.debug("Added embedded file from {}", source);
        }
        executionContext().notifiableTaskMetadata().clearCurrentSource();
        embeddedFiles.setNames(names);
        PDDocumentNameDictionary nameDictionary = new PDDocumentNameDictionary(this.destinationDocument.catalog());
        nameDictionary.setEmbeddedFiles(embeddedFiles);
        this.destinationDocument.catalog().setNames(nameDictionary);
        this.destinationDocument.catalog().getCOSObject().setItem(COSName.getPDFName("Collection"), collection);
        LOG.debug("Adding generated ToC");
        this.tocCreator.addToC();
        this.destinationDocument.savePDDocument(tmpFile, parameters.getOutput().getEncryptionAtRestPolicy());
        org.sejda.commons.util.IOUtils.closeQuietly(this.destinationDocument);
        parameters.getOutput().accept(this.outputWriter);
        LOG.debug("Created portfolio with {} files and written to {}", Integer.valueOf(parameters.getSourceList().size()), parameters.getOutput());
    }

    private COSDictionary createSortDictionary() {
        COSDictionary sortDictionary = new COSDictionary();
        sortDictionary.setItem(COSName.S, COLLECTION_ITEM_ORDER_FIELD);
        return sortDictionary;
    }

    private COSDictionary createSchemaDictionary() {
        COSDictionary schemaDictionary = new COSDictionary();
        COSDictionary fileDateFieldDictionary = new COSDictionary();
        fileDateFieldDictionary.setItem(COSName.SUBTYPE, COSName.F);
        fileDateFieldDictionary.setString(COSName.N, "File");
        fileDateFieldDictionary.setInt(COSName.O, 0);
        schemaDictionary.setItem(COSName.F, fileDateFieldDictionary);
        COSDictionary creationDateFieldDictionary = new COSDictionary();
        creationDateFieldDictionary.setItem(COSName.SUBTYPE, COSName.CREATION_DATE);
        creationDateFieldDictionary.setString(COSName.N, "Created");
        creationDateFieldDictionary.setInt(COSName.O, 1);
        schemaDictionary.setItem(COSName.CREATION_DATE, creationDateFieldDictionary);
        COSDictionary modDateFieldDictionary = new COSDictionary();
        modDateFieldDictionary.setItem(COSName.SUBTYPE, COSName.MOD_DATE);
        modDateFieldDictionary.setString(COSName.N, "Modified");
        modDateFieldDictionary.setInt(COSName.O, 2);
        schemaDictionary.setItem(COSName.MOD_DATE, modDateFieldDictionary);
        COSDictionary sizeDateFieldDictionary = new COSDictionary();
        sizeDateFieldDictionary.setItem(COSName.SUBTYPE, COSName.SIZE);
        sizeDateFieldDictionary.setString(COSName.N, "Size");
        sizeDateFieldDictionary.setInt(COSName.O, 3);
        schemaDictionary.setItem(COSName.SIZE, sizeDateFieldDictionary);
        COSDictionary sortFieldDictionary = new COSDictionary();
        sortFieldDictionary.setItem(COSName.SUBTYPE, COSName.N);
        sortFieldDictionary.setString(COSName.N, "Order");
        sortFieldDictionary.setInt(COSName.O, 4);
        schemaDictionary.setItem(COLLECTION_ITEM_ORDER_FIELD, sortFieldDictionary);
        return schemaDictionary;
    }

    private PDEmbeddedFile embeddedFileFromSource(Source<?> source) throws TaskIOException {
        PDEmbeddedFile embeddedFile = new PDEmbeddedFile(ReadOnlyFilteredCOSStream.readOnlyEmbeddedFile(source));
        embeddedFile.setCreationDate(new GregorianCalendar());
        return embeddedFile;
    }

    private PDAnnotationFileAttachment attachmentAnnotation(PDComplexFileSpecification fileSpec) {
        PDAnnotationFileAttachment attachmentAnnot = new PDAnnotationFileAttachment();
        attachmentAnnot.setFile(fileSpec);
        attachmentAnnot.setBorder(new COSArray(new COSBase[]{COSInteger.ZERO, COSInteger.ZERO, COSInteger.ZERO}));
        return attachmentAnnot;
    }

    public void after() {
        org.sejda.commons.util.IOUtils.closeQuietly(this.destinationDocument);
    }
}
