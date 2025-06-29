package org.sejda.sambox.pdmodel;

import java.awt.Point;
import java.awt.image.Raster;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.apache.fontbox.ttf.TrueTypeFont;
import org.sejda.commons.util.IOUtils;
import org.sejda.commons.util.RequireUtils;
import org.sejda.io.CountingWritableByteChannel;
import org.sejda.io.SeekableSources;
import org.sejda.sambox.SAMBox;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSDocument;
import org.sejda.sambox.cos.COSInteger;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSNumber;
import org.sejda.sambox.cos.COSString;
import org.sejda.sambox.cos.DirectCOSObject;
import org.sejda.sambox.encryption.EncryptionContext;
import org.sejda.sambox.encryption.MessageDigests;
import org.sejda.sambox.encryption.StandardSecurity;
import org.sejda.sambox.input.PDFParser;
import org.sejda.sambox.output.PDDocumentWriter;
import org.sejda.sambox.output.WriteOption;
import org.sejda.sambox.pdmodel.documentinterchange.taggedpdf.PDLayoutAttributeObject;
import org.sejda.sambox.pdmodel.encryption.AccessPermission;
import org.sejda.sambox.pdmodel.encryption.PDEncryption;
import org.sejda.sambox.pdmodel.encryption.SecurityHandler;
import org.sejda.sambox.pdmodel.font.Subsettable;
import org.sejda.sambox.pdmodel.graphics.color.PDDeviceRGB;
import org.sejda.sambox.util.SpecVersionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/PDDocument.class */
public class PDDocument implements Closeable {
    private static final Logger LOG = LoggerFactory.getLogger(PDDocument.class);
    private final COSDocument document;
    private PDDocumentCatalog documentCatalog;
    private SecurityHandler securityHandler;
    private boolean open;
    private OnClose onClose;
    private OnBeforeWrite onBeforeWrite;
    private ResourceCache resourceCache;
    private final Set<Subsettable> fontsToSubset;

    static {
        try {
            PDDeviceRGB.INSTANCE.toRGBImage(Raster.createBandedRaster(0, 1, 1, 3, new Point(0, 0)));
        } catch (IOException e) {
            LOG.warn("This shouldn't happen", e);
        }
        try {
            COSNumber.get(PDLayoutAttributeObject.GLYPH_ORIENTATION_VERTICAL_ZERO_DEGREES);
            COSNumber.get("1");
        } catch (IOException e2) {
        }
    }

    public PDDocument() {
        this.open = true;
        this.onClose = () -> {
            LOG.debug("Closing document");
        };
        this.onBeforeWrite = () -> {
            LOG.trace("About to write document");
        };
        this.resourceCache = new DefaultResourceCache();
        this.fontsToSubset = new HashSet();
        this.document = new COSDocument();
        this.document.getCatalog().setItem(COSName.VERSION, (COSBase) COSName.getPDFName(SpecVersionUtils.V1_4));
        COSDictionary pages = new COSDictionary();
        this.document.getCatalog().setItem(COSName.PAGES, (COSBase) pages);
        pages.setItem(COSName.TYPE, (COSBase) COSName.PAGES);
        pages.setItem(COSName.KIDS, (COSBase) new COSArray());
        pages.setItem(COSName.COUNT, (COSBase) COSInteger.ZERO);
    }

    public PDDocument(COSDocument document) {
        this(document, null);
    }

    public PDDocument(COSDocument document, SecurityHandler securityHandler) {
        this.open = true;
        this.onClose = () -> {
            LOG.debug("Closing document");
        };
        this.onBeforeWrite = () -> {
            LOG.trace("About to write document");
        };
        this.resourceCache = new DefaultResourceCache();
        this.fontsToSubset = new HashSet();
        this.document = document;
        this.securityHandler = securityHandler;
    }

    public void addPage(PDPage page) throws IllegalStateException {
        requireOpen();
        getPages().add(page);
    }

    public void removePage(PDPage page) throws IllegalStateException {
        requireOpen();
        getPages().remove(page);
    }

    public void removePage(int pageNumber) throws IllegalStateException {
        requireOpen();
        getPages().remove(pageNumber);
    }

    public COSDocument getDocument() {
        return this.document;
    }

    public PDDocumentInformation getDocumentInformation() {
        COSDictionary infoDic = (COSDictionary) this.document.getTrailer().getCOSObject().getDictionaryObject(COSName.INFO, COSDictionary.class);
        if (infoDic == null) {
            infoDic = new COSDictionary();
            this.document.getTrailer().getCOSObject().setItem(COSName.INFO, (COSBase) infoDic);
        }
        return new PDDocumentInformation(infoDic);
    }

    public void setDocumentInformation(PDDocumentInformation documentInformation) throws IllegalStateException {
        requireOpen();
        this.document.getTrailer().getCOSObject().setItem(COSName.INFO, (COSBase) documentInformation.getCOSObject());
    }

    public PDDocumentCatalog getDocumentCatalog() {
        if (this.documentCatalog == null) {
            this.documentCatalog = new PDDocumentCatalog(this, this.document.getCatalog());
        }
        return this.documentCatalog;
    }

    public boolean isEncrypted() {
        return this.document.isEncrypted();
    }

    public PDEncryption getEncryption() {
        if (isEncrypted()) {
            return new PDEncryption(this.document.getEncryptionDictionary());
        }
        return new PDEncryption();
    }

    public void registerTrueTypeFontForClosing(TrueTypeFont ttf) throws IllegalStateException {
        addOnCloseAction(() -> {
            IOUtils.closeQuietly(ttf);
        });
    }

    public Set<Subsettable> getFontsToSubset() {
        return this.fontsToSubset;
    }

    public PDPage getPage(int pageIndex) {
        return getDocumentCatalog().getPages().get(pageIndex);
    }

    public PDPageTree getPages() {
        return getDocumentCatalog().getPages();
    }

    public int getNumberOfPages() {
        return getDocumentCatalog().getPages().getCount();
    }

    public AccessPermission getCurrentAccessPermission() {
        return (AccessPermission) Optional.ofNullable(this.securityHandler).map((v0) -> {
            return v0.getCurrentAccessPermission();
        }).orElseGet(AccessPermission::getOwnerAccessPermission);
    }

    public SecurityHandler getSecurityHandler() {
        return this.securityHandler;
    }

    public String getVersion() {
        String headerVersion = getDocument().getHeaderVersion();
        if (SpecVersionUtils.isAtLeast(headerVersion, SpecVersionUtils.V1_4)) {
            return (String) Optional.ofNullable(getDocumentCatalog().getVersion()).filter(catalogVersion -> {
                return catalogVersion.compareTo(headerVersion) > 0;
            }).orElse(headerVersion);
        }
        return headerVersion;
    }

    public void setVersion(String newVersion) throws IllegalStateException {
        requireOpen();
        RequireUtils.requireNotBlank(newVersion, "Spec version cannot be blank");
        int compare = getVersion().compareTo(newVersion);
        if (compare > 0) {
            LOG.info("Spec version downgrade not allowed");
        } else if (compare < 0) {
            if (SpecVersionUtils.isAtLeast(newVersion, SpecVersionUtils.V1_4)) {
                getDocumentCatalog().setVersion(newVersion);
            }
            getDocument().setHeaderVersion(newVersion);
        }
    }

    public void requireMinVersion(String version) throws IllegalStateException {
        if (!SpecVersionUtils.isAtLeast(getVersion(), version)) {
            LOG.debug("Minimum spec version required is {}", version);
            setVersion(version);
        }
    }

    @Deprecated
    public void setOnCloseAction(OnClose onClose) throws IllegalStateException {
        addOnCloseAction(onClose);
    }

    public void addOnCloseAction(OnClose onClose) throws IllegalStateException {
        requireOpen();
        this.onClose = onClose.andThen(this.onClose);
    }

    public void setOnBeforeWriteAction(OnBeforeWrite onBeforeWrite) throws IllegalStateException {
        requireOpen();
        this.onBeforeWrite = onBeforeWrite.andThen(this.onBeforeWrite);
    }

    private void requireOpen() throws IllegalStateException {
        if (!isOpen()) {
            throw new IllegalStateException("The document is closed");
        }
    }

    private void generateFileIdentifier(byte[] md5Update, Optional<EncryptionContext> encContext) {
        COSString id = generateFileIdentifier(md5Update);
        encContext.ifPresent(c -> {
            c.documentId(id.getBytes());
        });
        DirectCOSObject directId = DirectCOSObject.asDirectObject(id);
        getDocument().getTrailer().getCOSObject().setItem(COSName.ID, (COSBase) DirectCOSObject.asDirectObject(new COSArray(directId, directId)));
    }

    public COSString generateFileIdentifier(byte[] md5Update) {
        MessageDigest md5 = MessageDigests.md5();
        md5.update(Long.toString(System.currentTimeMillis()).getBytes(StandardCharsets.ISO_8859_1));
        md5.update(md5Update);
        Optional.ofNullable((COSDictionary) getDocument().getTrailer().getCOSObject().getDictionaryObject(COSName.INFO, COSDictionary.class)).ifPresent(d -> {
            for (COSBase current : d.getValues()) {
                md5.update(current.toString().getBytes(StandardCharsets.ISO_8859_1));
            }
        });
        COSString retVal = COSString.newInstance(md5.digest());
        retVal.setForceHexForm(true);
        retVal.encryptable(false);
        return retVal;
    }

    public void writeTo(File file, WriteOption... options) throws IllegalStateException, IOException {
        writeTo(CountingWritableByteChannel.from(file), (StandardSecurity) null, options);
    }

    public void writeTo(String filename, WriteOption... options) throws IllegalStateException, IOException {
        writeTo(CountingWritableByteChannel.from(filename), (StandardSecurity) null, options);
    }

    public void writeTo(WritableByteChannel channel, WriteOption... options) throws IllegalStateException, IOException {
        writeTo(CountingWritableByteChannel.from(channel), (StandardSecurity) null, options);
    }

    public void writeTo(OutputStream out, WriteOption... options) throws IllegalStateException, IOException {
        writeTo(CountingWritableByteChannel.from(out), (StandardSecurity) null, options);
    }

    public void writeTo(File file, StandardSecurity security, WriteOption... options) throws IllegalStateException, IOException {
        writeTo(CountingWritableByteChannel.from(file), security, options);
    }

    public void writeTo(String filename, StandardSecurity security, WriteOption... options) throws IllegalStateException, IOException {
        writeTo(CountingWritableByteChannel.from(filename), security, options);
    }

    public void writeTo(WritableByteChannel channel, StandardSecurity security, WriteOption... options) throws IllegalStateException, IOException {
        writeTo(CountingWritableByteChannel.from(channel), security, options);
    }

    public void writeTo(OutputStream out, StandardSecurity security, WriteOption... options) throws IllegalStateException, IOException {
        writeTo(CountingWritableByteChannel.from(out), security, options);
    }

    private void writeTo(CountingWritableByteChannel output, StandardSecurity security, WriteOption... options) throws IllegalStateException, IOException {
        requireOpen();
        if (Arrays.stream(options).noneMatch(i -> {
            return i == WriteOption.NO_METADATA_PRODUCER_MODIFIED_DATE_UPDATE;
        })) {
            getDocumentInformation().setProducer(SAMBox.PRODUCER);
            getDocumentInformation().setModificationDate(Calendar.getInstance());
        }
        for (Subsettable font : this.fontsToSubset) {
            try {
                font.subset();
            } catch (Exception e) {
                LOG.warn("Exception occurred while subsetting font: " + font, e);
            }
        }
        this.fontsToSubset.clear();
        Optional<EncryptionContext> encryptionContext = Optional.ofNullable((EncryptionContext) Optional.ofNullable(security).map(EncryptionContext::new).orElse(null));
        generateFileIdentifier(output.toString().getBytes(StandardCharsets.ISO_8859_1), encryptionContext);
        try {
            PDDocumentWriter writer = new PDDocumentWriter(output, encryptionContext, options);
            try {
                this.onBeforeWrite.onBeforeWrite();
                writer.write(this);
                writer.close();
            } finally {
            }
        } finally {
            IOUtils.close(this);
        }
    }

    public boolean isOpen() {
        return this.open;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (isOpen()) {
            this.onClose.onClose();
            this.resourceCache.clear();
            this.open = false;
        }
    }

    @FunctionalInterface
    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/PDDocument$OnClose.class */
    public interface OnClose {
        void onClose() throws IOException;

        default OnClose andThen(OnClose after) {
            Objects.requireNonNull(after);
            return () -> {
                onClose();
                after.onClose();
            };
        }
    }

    @FunctionalInterface
    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/PDDocument$OnBeforeWrite.class */
    public interface OnBeforeWrite {
        void onBeforeWrite() throws IOException;

        default OnBeforeWrite andThen(OnBeforeWrite after) {
            Objects.requireNonNull(after);
            return () -> {
                onBeforeWrite();
                after.onBeforeWrite();
            };
        }
    }

    public ResourceCache getResourceCache() {
        return this.resourceCache;
    }

    public static PDDocument load(File file) throws IOException {
        return PDFParser.parse(SeekableSources.seekableSourceFrom(file));
    }

    public static PDDocument load(String path) throws IOException {
        return load(new File(path));
    }

    public void save(String path) throws IllegalStateException, IOException {
        writeTo(new File(path), WriteOption.COMPRESS_STREAMS);
    }

    public boolean hasParseErrors() {
        return this.document.getTrailer().getFallbackScanStatus() != null;
    }

    public void assertNumberOfPagesIsAccurate() {
        getPages().iterator();
    }
}
