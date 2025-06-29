package org.sejda.impl.sambox.component;

import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.sejda.core.Sejda;
import org.sejda.impl.sambox.util.FontUtils;
import org.sejda.impl.sambox.util.PageLabelUtils;
import org.sejda.impl.sambox.util.ViewerPreferencesUtils;
import org.sejda.model.encryption.EncryptionAtRestPolicy;
import org.sejda.model.encryption.NoEncryptionAtRest;
import org.sejda.model.exception.TaskException;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.image.ImageColorType;
import org.sejda.model.pdf.PdfVersion;
import org.sejda.model.pdf.label.PdfPageLabel;
import org.sejda.model.pdf.viewerpreference.PdfPageLayout;
import org.sejda.model.pdf.viewerpreference.PdfPageMode;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.encryption.StandardSecurity;
import org.sejda.sambox.output.WriteOption;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDDocumentCatalog;
import org.sejda.sambox.pdmodel.PDDocumentInformation;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.PDPageTree;
import org.sejda.sambox.pdmodel.PageLayout;
import org.sejda.sambox.pdmodel.PageMode;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.sejda.sambox.pdmodel.interactive.form.PDAcroForm;
import org.sejda.sambox.pdmodel.interactive.viewerpreferences.PDViewerPreferences;
import org.sejda.sambox.rendering.ImageType;
import org.sejda.sambox.rendering.PDFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/PDDocumentHandler.class */
public class PDDocumentHandler implements Closeable {
    public static final String SAMBOX_USE_ASYNC_WRITER = "sejda.sambox.asyncwriter";
    private static final Logger LOG = LoggerFactory.getLogger(PDDocumentHandler.class);
    private static final WriteOption[] COMPRESSED_OPTS = {WriteOption.COMPRESS_STREAMS, WriteOption.OBJECT_STREAMS, WriteOption.XREF_STREAM};
    private PDDocument document;
    private PDDocumentAccessPermission permissions;
    private Set<WriteOption> writeOptions;
    private boolean updateProducerModifiedDate;

    public PDDocumentHandler(PDDocument document) {
        this.writeOptions = new HashSet();
        this.updateProducerModifiedDate = true;
        if (document == null) {
            throw new IllegalArgumentException("PDDocument cannot be null.");
        }
        if (Boolean.getBoolean(Sejda.PERFORM_EAGER_ASSERTIONS_PROPERTY_NAME)) {
            document.assertNumberOfPagesIsAccurate();
        }
        this.document = document;
        this.permissions = new PDDocumentAccessPermission(document);
    }

    public PDDocumentHandler() {
        this.writeOptions = new HashSet();
        this.updateProducerModifiedDate = true;
        this.document = new PDDocument();
        this.permissions = new PDDocumentAccessPermission(this.document);
        COSDictionary pieceInfo = new COSDictionary();
        COSDictionary pieceLastMod = new COSDictionary();
        pieceLastMod.setDate(COSName.LAST_MODIFIED, Calendar.getInstance());
        pieceInfo.setItem("sjda_", (COSBase) pieceLastMod);
        this.document.getDocumentCatalog().getCOSObject().setItem(COSName.PIECE_INFO, (COSBase) pieceInfo);
    }

    public void setCreatorOnPDDocument() {
        if (!Sejda.CREATOR.isEmpty()) {
            this.document.getDocumentInformation().setCreator(Sejda.CREATOR);
        }
    }

    public void setDocumentInformation(PDDocumentInformation info) throws IllegalStateException {
        this.document.setDocumentInformation(info);
    }

    public void setDocumentTitle(String title) throws IllegalStateException {
        PDDocumentInformation info = this.document.getDocumentInformation();
        info.setTitle(title);
        this.document.setDocumentInformation(info);
    }

    public PDDocumentAccessPermission getPermissions() {
        return this.permissions;
    }

    public void setPageLayoutOnDocument(PdfPageLayout layout) {
        setPageLayout(ViewerPreferencesUtils.getPageLayout(layout));
        LOG.trace("Page layout set to '{}'", layout);
    }

    public void setPageModeOnDocument(PdfPageMode mode) {
        setPageMode(ViewerPreferencesUtils.getPageMode(mode));
        LOG.trace("Page mode set to '{}'", mode);
    }

    public void setPageLabelsOnDocument(Map<Integer, PdfPageLabel> labels) {
        this.document.getDocumentCatalog().setPageLabels(PageLabelUtils.getLabels(labels, getNumberOfPages()));
        LOG.trace("Page labels set");
    }

    public void setVersionOnPDDocument(PdfVersion version) throws IllegalStateException {
        if (version != null) {
            this.document.setVersion(version.getVersionString());
            LOG.trace("Version set to '{}'", version);
        }
    }

    public void addWriteOption(WriteOption... opts) {
        this.writeOptions.addAll(Arrays.asList(opts));
    }

    public void removeWriteOption(WriteOption... opts) {
        for (WriteOption opt : opts) {
            this.writeOptions.remove(opt);
        }
    }

    public void setCompress(boolean compress) {
        if (compress) {
            addWriteOption(COMPRESSED_OPTS);
        } else {
            removeWriteOption(COMPRESSED_OPTS);
        }
    }

    public PDViewerPreferences getViewerPreferences() {
        PDViewerPreferences retVal = this.document.getDocumentCatalog().getViewerPreferences();
        if (retVal == null) {
            retVal = new PDViewerPreferences(new COSDictionary());
        }
        return retVal;
    }

    public void setViewerPreferences(PDViewerPreferences preferences) {
        this.document.getDocumentCatalog().setViewerPreferences(preferences);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.document.close();
        FontUtils.clearLoadedFontCache(this.document);
    }

    public void savePDDocument(File file) throws IllegalStateException, TaskException {
        savePDDocument(file, null, NoEncryptionAtRest.INSTANCE);
    }

    public void savePDDocument(File file, EncryptionAtRestPolicy encryptionAtRestSecurity) throws IllegalStateException, TaskException {
        savePDDocument(file, null, encryptionAtRestSecurity);
    }

    public void savePDDocument(File file, StandardSecurity security, EncryptionAtRestPolicy encryptionAtRestSecurity) throws IllegalStateException, TaskException {
        try {
            if (Boolean.getBoolean(SAMBOX_USE_ASYNC_WRITER)) {
                addWriteOption(WriteOption.ASYNC_BODY_WRITE);
            }
            if (!this.updateProducerModifiedDate) {
                addWriteOption(WriteOption.NO_METADATA_PRODUCER_MODIFIED_DATE_UPDATE);
            }
            if (encryptionAtRestSecurity instanceof NoEncryptionAtRest) {
                LOG.trace("Saving document to {} using options {}", file, this.writeOptions);
                this.document.writeTo(file, security, (WriteOption[]) this.writeOptions.toArray(x$0 -> {
                    return new WriteOption[x$0];
                }));
            } else {
                LOG.trace("Saving document to {} using options {}", file, this.writeOptions);
                this.document.writeTo(encryptionAtRestSecurity.encrypt(new FileOutputStream(file)), security, (WriteOption[]) this.writeOptions.toArray(x$02 -> {
                    return new WriteOption[x$02];
                }));
            }
        } catch (IOException e) {
            throw new TaskIOException("Unable to save to temporary file.", e);
        }
    }

    public int getNumberOfPages() {
        return this.document.getNumberOfPages();
    }

    public PDDocument getUnderlyingPDDocument() {
        return this.document;
    }

    public PDDocumentCatalog catalog() {
        return this.document.getDocumentCatalog();
    }

    public PDPage importPage(PDPage page) {
        PDPage imported = new PDPage(page.getCOSObject().duplicate());
        imported.setCropBox(page.getCropBox());
        imported.setMediaBox(page.getMediaBox());
        imported.setBleedBox(page.getBleedBox());
        imported.setResources(page.getResources());
        imported.setRotation(page.getRotation());
        imported.getCOSObject().removeItem(COSName.B);
        imported.sanitizeDictionary();
        return addPage(imported);
    }

    public PDPage addPage(PDPage page) throws IllegalStateException {
        this.document.addPage(page);
        return page;
    }

    public void removePage(int pageNumber) throws IllegalStateException {
        this.document.removePage(pageNumber - 1);
    }

    public void movePageToDocumentEnd(int oldPageNumber) throws IllegalStateException {
        if (oldPageNumber == this.document.getNumberOfPages()) {
            return;
        }
        PDPage page = getPage(oldPageNumber);
        this.document.addPage(page);
        this.document.removePage(oldPageNumber - 1);
    }

    public PDPage getPage(int pageNumber) {
        return this.document.getPage(pageNumber - 1);
    }

    public PDPageTree getPages() {
        return this.document.getPages();
    }

    public void initialiseBasedOn(PDDocument other) throws IllegalStateException {
        setDocumentInformation(other.getDocumentInformation());
        setViewerPreferences(other.getDocumentCatalog().getViewerPreferences());
        if (other.getDocumentCatalog().getCOSObject().containsKey(COSName.PAGE_LAYOUT)) {
            setPageLayout(other.getDocumentCatalog().getPageLayout());
        }
        if (other.getDocumentCatalog().getCOSObject().containsKey(COSName.PAGE_MODE)) {
            setPageMode(other.getDocumentCatalog().getPageMode());
        }
        this.document.getDocumentCatalog().setLanguage(other.getDocumentCatalog().getLanguage());
        setCreatorOnPDDocument();
    }

    public BufferedImage renderImage(int pageNumber, int dpi, ImageColorType type) throws TaskException {
        try {
            PDFRenderer pdfRenderer = new PDFRenderer(this.document);
            return pdfRenderer.renderImageWithDPI(pageNumber - 1, dpi, toSamboxImageType(type));
        } catch (IOException ex) {
            LOG.error("Failed to render page " + pageNumber, ex);
            throw new TaskException("Failed to render page " + pageNumber, ex);
        }
    }

    private ImageType toSamboxImageType(ImageColorType colorType) {
        for (ImageType type : ImageType.values()) {
            if (type.toBufferedImageType() == colorType.getBufferedImageType()) {
                return type;
            }
        }
        throw new RuntimeException("Could not find a suitable image type for color type:" + String.valueOf(colorType));
    }

    public void setDocumentOutline(PDDocumentOutline outline) {
        this.document.getDocumentCatalog().setDocumentOutline(outline);
    }

    public void setDocumentAcroForm(PDAcroForm acroForm) {
        this.document.getDocumentCatalog().setAcroForm(acroForm);
    }

    private void setPageMode(PageMode pageMode) {
        this.document.getDocumentCatalog().setPageMode(pageMode);
    }

    private void setPageLayout(PageLayout pageLayout) {
        this.document.getDocumentCatalog().setPageLayout(pageLayout);
    }

    public PDPage addBlankPageIfOdd(PDRectangle mediaBox) {
        if (this.document.getNumberOfPages() % 2 != 0) {
            return addBlankPage(mediaBox);
        }
        return null;
    }

    public PDPage addBlankPage(PDRectangle mediaBox) {
        LOG.debug("Adding blank page");
        return addPage(new PDPage((PDRectangle) Optional.ofNullable(mediaBox).orElse(PDRectangle.LETTER)));
    }

    public PDPage addBlankPageAfter(int pageNumber) {
        PDPage target = this.document.getPage(pageNumber - 1);
        PDPage result = new PDPage(target.getMediaBox().rotate(target.getRotation()));
        this.document.getPages().insertAfter(result, target);
        return result;
    }

    public PDPage addBlankPageBefore(int pageNumber) {
        PDPage target = this.document.getPage(pageNumber - 1);
        PDPage result = new PDPage(target.getMediaBox().rotate(target.getRotation()));
        this.document.getPages().insertBefore(result, target);
        return result;
    }

    public boolean isUpdateProducerModifiedDate() {
        return this.updateProducerModifiedDate;
    }

    public void setUpdateProducerModifiedDate(boolean updateProducerModifiedDate) {
        this.updateProducerModifiedDate = updateProducerModifiedDate;
    }
}
