package org.sejda.impl.sambox.component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.Optional;
import java.util.zip.DeflaterInputStream;
import org.sejda.commons.util.RequireUtils;
import org.sejda.io.SeekableSource;
import org.sejda.model.exception.SejdaRuntimeException;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.input.FileSource;
import org.sejda.model.input.Source;
import org.sejda.model.input.SourceDispatcher;
import org.sejda.model.input.StreamSource;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.cos.IndirectCOSObjectIdentifier;
import org.sejda.sambox.pdmodel.graphics.color.PDColorSpace;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/ReadOnlyFilteredCOSStream.class */
public class ReadOnlyFilteredCOSStream extends COSStream {
    private InputStreamSupplier<InputStream> supplier;
    private final long length;
    private final COSDictionary wrapped;

    @FunctionalInterface
    /* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/ReadOnlyFilteredCOSStream$InputStreamSupplier.class */
    public interface InputStreamSupplier<T extends InputStream> {
        T get() throws IOException;
    }

    ReadOnlyFilteredCOSStream(COSDictionary existingDictionary, InputStream supplier, long length) {
        this(existingDictionary, (InputStreamSupplier<InputStream>) () -> {
            return supplier;
        }, length);
        RequireUtils.requireNotNullArg(supplier, "input stream cannot be null");
    }

    public ReadOnlyFilteredCOSStream(COSDictionary existingDictionary, InputStreamSupplier<InputStream> supplier, long length) {
        super((COSDictionary) Optional.ofNullable(existingDictionary).orElseThrow(() -> {
            return new IllegalArgumentException("wrapped dictionary cannot be null");
        }));
        RequireUtils.requireNotNullArg(supplier, "input stream provider cannot be null");
        this.supplier = supplier;
        this.length = length;
        this.wrapped = existingDictionary;
    }

    @Override // org.sejda.sambox.cos.COSStream
    protected InputStream doGetFilteredStream() throws IOException {
        return this.supplier.get();
    }

    @Override // org.sejda.sambox.cos.COSStream
    public long getFilteredLength() throws IOException {
        RequireUtils.requireIOCondition(this.length >= 0, "Filtered length cannot be requested");
        return this.length;
    }

    @Override // org.sejda.sambox.cos.COSStream
    public long getUnfilteredLength() throws IOException {
        throw new IOException("Unfiltered length cannot be requested");
    }

    @Override // org.sejda.sambox.cos.COSStream
    public InputStream getUnfilteredStream() throws IOException {
        throw new IOException("getUnfilteredStream  cannot be requested");
    }

    @Override // org.sejda.sambox.cos.COSStream
    public SeekableSource getUnfilteredSource() throws IOException {
        throw new IOException("getUnfilteredSource  cannot be requested");
    }

    @Override // org.sejda.sambox.cos.COSStream
    public OutputStream createFilteredStream() {
        throw new SejdaRuntimeException("createFilteredStream cannot be called on this stream");
    }

    @Override // org.sejda.sambox.cos.COSStream
    public OutputStream createFilteredStream(COSBase filters) {
        throw new SejdaRuntimeException("createFilteredStream cannot be called on this stream");
    }

    @Override // org.sejda.sambox.cos.COSStream
    public void setFilters(COSBase filters) {
        throw new SejdaRuntimeException("setFilters cannot be called on this stream");
    }

    @Override // org.sejda.sambox.cos.COSStream
    public boolean addCompression() {
        return false;
    }

    @Override // org.sejda.sambox.cos.COSStream, org.sejda.sambox.cos.Encryptable
    public boolean encryptable() {
        return true;
    }

    @Override // org.sejda.sambox.cos.COSStream, org.sejda.sambox.cos.Encryptable
    public void encryptable(boolean encryptable) {
    }

    @Override // org.sejda.sambox.cos.COSStream
    public OutputStream createUnfilteredStream() {
        throw new SejdaRuntimeException("createUnfilteredStream cannot be called on this stream");
    }

    @Override // org.sejda.sambox.cos.COSStream
    public boolean isEmpty() {
        return false;
    }

    @Override // org.sejda.sambox.cos.COSStream
    public boolean indirectLength() {
        return true;
    }

    @Override // org.sejda.sambox.cos.COSStream
    public void indirectLength(boolean indirectLength) {
    }

    @Override // org.sejda.sambox.cos.COSBase
    public IndirectCOSObjectIdentifier id() {
        return this.wrapped.id();
    }

    @Override // org.sejda.sambox.cos.COSBase
    public void idIfAbsent(IndirectCOSObjectIdentifier id) {
        this.wrapped.idIfAbsent(id);
    }

    @Override // org.sejda.sambox.cos.COSStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.supplier = () -> {
            return new ByteArrayInputStream(new byte[0]);
        };
    }

    public static ReadOnlyFilteredCOSStream readOnly(COSStream existing) throws IOException {
        RequireUtils.requireNotNullArg(existing, "input stream cannot be null");
        existing.setEncryptor(null);
        Objects.requireNonNull(existing);
        return new ReadOnlyFilteredCOSStream(existing, (InputStreamSupplier<InputStream>) existing::getFilteredStream, existing.getFilteredLength());
    }

    public static ReadOnlyFilteredCOSStream readOnlyJpegImage(File imageFile, int width, int height, int bitsPerComponent, PDColorSpace colorSpace) {
        RequireUtils.requireNotNullArg(imageFile, "input file cannot be null");
        RequireUtils.requireNotNullArg(colorSpace, "color space cannot be null");
        COSDictionary dictionary = new COSDictionary();
        dictionary.setItem(COSName.TYPE, (COSBase) COSName.XOBJECT);
        dictionary.setItem(COSName.SUBTYPE, (COSBase) COSName.IMAGE);
        dictionary.setItem(COSName.FILTER, (COSBase) COSName.DCT_DECODE);
        dictionary.setInt(COSName.BITS_PER_COMPONENT, bitsPerComponent);
        dictionary.setInt(COSName.HEIGHT, height);
        dictionary.setInt(COSName.WIDTH, width);
        Optional.of(colorSpace).map((v0) -> {
            return v0.getCOSObject();
        }).ifPresent(cs -> {
            dictionary.setItem(COSName.COLORSPACE, cs);
        });
        return new ReadOnlyFilteredCOSStream(dictionary, (InputStreamSupplier<InputStream>) () -> {
            return new FileInputStream(imageFile);
        }, imageFile.length());
    }

    public static final ReadOnlyFilteredCOSStream readOnlyEmbeddedFile(Source<?> source) throws TaskIOException {
        final COSDictionary dictionary = new COSDictionary();
        dictionary.setItem(COSName.FILTER, (COSBase) COSName.FLATE_DECODE);
        return (ReadOnlyFilteredCOSStream) source.dispatch(new SourceDispatcher<ReadOnlyFilteredCOSStream>() { // from class: org.sejda.impl.sambox.component.ReadOnlyFilteredCOSStream.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.sejda.model.input.SourceDispatcher
            public ReadOnlyFilteredCOSStream dispatch(FileSource source2) throws TaskIOException {
                try {
                    ReadOnlyFilteredCOSStream retVal = new ReadOnlyFilteredCOSStream(dictionary, new DeflaterInputStream(new FileInputStream(source2.getSource())), -1L);
                    retVal.setEmbeddedInt(COSName.PARAMS.getName(), COSName.SIZE, source2.getSource().length());
                    GregorianCalendar calendar = new GregorianCalendar();
                    calendar.setTimeInMillis(source2.getSource().lastModified());
                    retVal.setEmbeddedDate(COSName.PARAMS.getName(), COSName.MOD_DATE, calendar);
                    return retVal;
                } catch (FileNotFoundException e) {
                    throw new TaskIOException(e);
                }
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.sejda.model.input.SourceDispatcher
            public ReadOnlyFilteredCOSStream dispatch(StreamSource source2) {
                return new ReadOnlyFilteredCOSStream(dictionary, new DeflaterInputStream(source2.getSource()), -1L);
            }
        });
    }
}
