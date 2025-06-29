package org.sejda.sambox.cos;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.sejda.commons.FastByteArrayOutputStream;
import org.sejda.commons.util.IOUtils;
import org.sejda.commons.util.RequireUtils;
import org.sejda.io.SeekableSource;
import org.sejda.io.SeekableSources;
import org.sejda.sambox.filter.DecodeResult;
import org.sejda.sambox.filter.Filter;
import org.sejda.sambox.filter.FilterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/cos/COSStream.class */
public class COSStream extends COSDictionary implements Closeable, Encryptable {
    private static final Set<COSName> CAN_COMPRESS = Set.of(COSName.ASCII_HEX_DECODE, COSName.ASCII_HEX_DECODE_ABBREVIATION, COSName.ASCII85_DECODE, COSName.ASCII85_DECODE_ABBREVIATION);
    private static final Logger LOG = LoggerFactory.getLogger(COSStream.class);
    private LazySeekableSourceViewHolder existing;
    private byte[] filtered;
    private byte[] unfiltered;
    private DecodeResult decodeResult;
    private Function<InputStream, InputStream> encryptor;
    private boolean encryptable;
    private boolean indirectLength;

    public COSStream() {
        this.encryptable = true;
        this.indirectLength = false;
    }

    public COSStream(COSDictionary dictionary) {
        super(dictionary);
        this.encryptable = true;
        this.indirectLength = false;
    }

    public COSStream(COSDictionary dictionary, SeekableSource seekableSource, long startingPosition, long length) {
        super(dictionary);
        this.encryptable = true;
        this.indirectLength = false;
        this.existing = new LazySeekableSourceViewHolder(seekableSource, startingPosition, length);
        setLong(COSName.LENGTH, length);
    }

    public final InputStream getFilteredStream() throws IOException {
        if (Objects.nonNull(this.encryptor)) {
            return this.encryptor.apply(doGetFilteredStream());
        }
        return doGetFilteredStream();
    }

    protected InputStream doGetFilteredStream() throws IOException {
        if (Objects.nonNull(this.existing)) {
            return this.existing.get().asInputStream();
        }
        encodeIfRequired();
        if (Objects.nonNull(this.filtered)) {
            return new MyByteArrayInputStream(this.filtered);
        }
        return new MyByteArrayInputStream(this.unfiltered);
    }

    public SeekableSource getFilteredSource() throws IOException {
        if (this.existing != null) {
            return this.existing.get();
        }
        return SeekableSources.inMemorySeekableSourceFrom(getFilteredStream());
    }

    public long getFilteredLength() throws IOException {
        if (this.existing != null) {
            return this.existing.length;
        }
        encodeIfRequired();
        if (Objects.nonNull(this.filtered)) {
            return this.filtered.length;
        }
        return ((Integer) Optional.ofNullable(this.unfiltered).map(f -> {
            return Integer.valueOf(f.length);
        }).orElse(0)).intValue();
    }

    private void encodeIfRequired() throws IOException {
        if (getFilters() != null && this.filtered == null && this.unfiltered != null) {
            doEncode();
        }
    }

    public InputStream getUnfilteredStream() throws IOException {
        decodeIfRequired();
        if (this.unfiltered != null) {
            return new MyByteArrayInputStream(this.unfiltered);
        }
        return getStreamToDecode();
    }

    public SeekableSource getUnfilteredSource() throws IOException {
        decodeIfRequired();
        if (this.unfiltered != null) {
            return SeekableSources.inMemorySeekableSourceFrom(this.unfiltered);
        }
        if (this.existing != null) {
            return this.existing.get();
        }
        return SeekableSources.inMemorySeekableSourceFrom(this.filtered);
    }

    public ByteBuffer getUnfilteredByteBuffer() throws IOException {
        decodeIfRequired();
        if (this.unfiltered != null) {
            return ByteBuffer.wrap(this.unfiltered).asReadOnlyBuffer();
        }
        if (this.existing != null) {
            return ByteBuffer.wrap(IOUtils.toByteArray(this.existing.get().asInputStream()));
        }
        return ByteBuffer.wrap(this.filtered).asReadOnlyBuffer();
    }

    public long getUnfilteredLength() throws IOException {
        decodeIfRequired();
        if (Objects.nonNull(this.unfiltered)) {
            return this.unfiltered.length;
        }
        if (Objects.nonNull(this.existing)) {
            return this.existing.length;
        }
        return ((Integer) Optional.ofNullable(this.filtered).map(f -> {
            return Integer.valueOf(f.length);
        }).orElse(0)).intValue();
    }

    private void decodeIfRequired() throws IOException {
        if (Objects.nonNull(getFilters()) && Objects.isNull(this.unfiltered)) {
            doDecode();
        }
    }

    public DecodeResult getDecodeResult() throws IOException {
        decodeIfRequired();
        return (DecodeResult) Optional.ofNullable(this.decodeResult).orElse(DecodeResult.DEFAULT);
    }

    @Override // org.sejda.sambox.cos.COSDictionary, org.sejda.sambox.cos.COSBase
    public void accept(COSVisitor visitor) throws IOException {
        visitor.visit(this);
    }

    private void doDecode() throws IOException {
        COSBase filters = getFilters();
        if (Objects.nonNull(filters)) {
            if (filters instanceof COSName) {
                this.unfiltered = decode((COSName) filters, 0, getStreamToDecode());
            } else if (filters instanceof COSArray) {
                this.unfiltered = decodeChain((COSArray) filters, getStreamToDecode());
            } else {
                LOG.warn("Unknown filter type:" + filters);
            }
        }
    }

    private InputStream getStreamToDecode() throws IOException {
        if (this.existing != null) {
            return this.existing.get().asInputStream();
        }
        return new MyByteArrayInputStream(this.filtered);
    }

    private byte[] decodeChain(COSArray filters, InputStream startingFrom) throws IOException {
        if (filters.size() > 0) {
            byte[] tmpResult = new byte[0];
            InputStream input = startingFrom;
            for (int i = 0; i < filters.size(); i++) {
                COSName filterName = (COSName) filters.getObject(i, COSName.class);
                RequireUtils.requireIOCondition(Objects.nonNull(filterName), "Invalid filter type at index " + i);
                tmpResult = decode(filterName, i, input);
                input = new MyByteArrayInputStream(tmpResult);
            }
            return tmpResult;
        }
        return null;
    }

    private byte[] decode(COSName filterName, int filterIndex, InputStream toDecode) throws IOException {
        if (toDecode.available() > 0) {
            Filter filter = FilterFactory.INSTANCE.getFilter(filterName);
            MyByteArrayOutputStream out = new MyByteArrayOutputStream();
            try {
                this.decodeResult = filter.decode(toDecode, out, this, filterIndex);
                byte[] byteArray = out.toByteArray();
                out.close();
                return byteArray;
            } catch (Throwable th) {
                try {
                    out.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        return new byte[0];
    }

    private void doEncode() throws IOException {
        COSBase filters = getFilters();
        if (filters instanceof COSName) {
            this.filtered = encode((COSName) filters, new MyByteArrayInputStream(this.unfiltered));
        } else if (filters instanceof COSArray) {
            this.filtered = encodeChain((COSArray) filters, new MyByteArrayInputStream(this.unfiltered));
        }
    }

    private byte[] encode(COSName filterName, InputStream toEncode) throws IOException {
        Filter filter = FilterFactory.INSTANCE.getFilter(filterName);
        MyByteArrayOutputStream encoded = new MyByteArrayOutputStream();
        try {
            filter.encode(toEncode, encoded, this);
            byte[] byteArray = encoded.toByteArray();
            encoded.close();
            return byteArray;
        } catch (Throwable th) {
            try {
                encoded.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private byte[] encodeChain(COSArray filters, InputStream startingFrom) throws IOException {
        if (filters.size() > 0) {
            byte[] tmpResult = new byte[0];
            InputStream input = startingFrom;
            for (int i = filters.size() - 1; i >= 0; i--) {
                COSName filterName = (COSName) filters.getObject(i);
                tmpResult = encode(filterName, input);
                input = new MyByteArrayInputStream(tmpResult);
            }
            return tmpResult;
        }
        return null;
    }

    public COSBase getFilters() {
        return getDictionaryObject(COSName.FILTER);
    }

    public boolean hasFilter(COSName filter) {
        COSBase filters = getFilters();
        if (filters instanceof COSName) {
            return filters.equals(filter);
        }
        if (filters instanceof COSArray) {
            return ((COSArray) filters).contains(filter);
        }
        return false;
    }

    public void setEncryptor(Function<InputStream, InputStream> encryptor) {
        this.encryptor = encryptor;
    }

    public OutputStream createFilteredStream() {
        IOUtils.closeQuietly(this.existing);
        this.unfiltered = null;
        this.existing = null;
        this.filtered = null;
        return new MyByteArrayOutputStream(bytes -> {
            this.filtered = bytes;
        });
    }

    public OutputStream createFilteredStream(COSBase filters) {
        if (filters != null) {
            setItem(COSName.FILTER, filters);
        }
        return createUnfilteredStream();
    }

    public void setFilters(COSBase filters) throws IOException {
        if (this.unfiltered == null) {
            InputStream in = getUnfilteredStream();
            try {
                MyByteArrayOutputStream out = new MyByteArrayOutputStream(bytes -> {
                    this.unfiltered = bytes;
                });
                try {
                    in.transferTo(out);
                    out.close();
                    if (in != null) {
                        in.close();
                    }
                } finally {
                }
            } catch (Throwable th) {
                if (in != null) {
                    try {
                        in.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        setItem(COSName.FILTER, filters);
        IOUtils.closeQuietly(this.existing);
        this.existing = null;
        this.filtered = null;
    }

    public boolean addCompression() {
        if (canCompress()) {
            try {
                COSArray newFilters = new COSArray(COSName.FLATE_DECODE);
                COSBase filters = getFilters();
                if (filters instanceof COSName) {
                    newFilters.add(filters);
                    setFilters(newFilters);
                    return true;
                }
                if (filters instanceof COSArray) {
                    newFilters.addAll((COSArray) filters);
                    setFilters(newFilters);
                    return true;
                }
                setFilters(COSName.FLATE_DECODE);
                return true;
            } catch (IOException e) {
                LOG.warn("Unable to add FlateDecode filter to the stream", e);
                return false;
            }
        }
        return false;
    }

    private boolean canCompress() {
        if (getDictionaryObject(COSName.DECODE_PARMS, COSName.DP) != null) {
            return false;
        }
        COSBase filters = getFilters();
        if (filters instanceof COSName) {
            return CAN_COMPRESS.contains(filters);
        }
        if (filters instanceof COSArray) {
            COSArray filtersArray = (COSArray) filters;
            return CAN_COMPRESS.containsAll(filtersArray);
        }
        return true;
    }

    public boolean encryptable() {
        return this.encryptable;
    }

    public void encryptable(boolean encryptable) {
        this.encryptable = encryptable;
    }

    public OutputStream createUnfilteredStream() {
        this.filtered = null;
        IOUtils.closeQuietly(this.existing);
        this.existing = null;
        this.unfiltered = null;
        return new MyByteArrayOutputStream(bytes -> {
            this.unfiltered = bytes;
        });
    }

    public boolean isEmpty() throws IOException {
        if (Objects.nonNull(this.existing)) {
            return this.existing.get().size() <= 0;
        }
        return ((Boolean) Optional.ofNullable(this.filtered).map(f -> {
            return Boolean.valueOf(f.length <= 0);
        }).orElseGet(() -> {
            return (Boolean) Optional.ofNullable(this.unfiltered).map(u -> {
                return Boolean.valueOf(u.length <= 0);
            }).orElse(true);
        })).booleanValue();
    }

    public String asTextString() {
        try {
            return COSString.newInstance(IOUtils.toByteArray(getUnfilteredStream())).getString();
        } catch (IOException e) {
            LOG.warn("Unable to convert the COSStream to a text string", e);
            return "";
        }
    }

    public void close() throws IOException {
        IOUtils.closeQuietly(this.existing);
        this.existing = null;
        this.unfiltered = null;
        this.filtered = null;
    }

    public void unDecode() {
        if (Objects.nonNull(this.existing)) {
            this.unfiltered = null;
            this.filtered = null;
        }
        if (Objects.nonNull(this.filtered)) {
            this.unfiltered = null;
        }
    }

    public boolean indirectLength() {
        return this.indirectLength;
    }

    public void indirectLength(boolean indirectLength) {
        this.indirectLength = indirectLength;
    }

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/cos/COSStream$MyByteArrayOutputStream.class */
    static class MyByteArrayOutputStream extends FastByteArrayOutputStream {
        private final Optional<Consumer<byte[]>> onClose;

        MyByteArrayOutputStream() {
            this(null);
        }

        MyByteArrayOutputStream(Consumer<byte[]> onClose) {
            this.onClose = Optional.ofNullable(onClose);
        }

        @Override // org.sejda.commons.FastByteArrayOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            super.close();
            this.onClose.ifPresent(c -> {
                c.accept(toByteArray());
            });
        }
    }

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/cos/COSStream$MyByteArrayInputStream.class */
    static class MyByteArrayInputStream extends ByteArrayInputStream {
        MyByteArrayInputStream(byte[] bytes) {
            super((byte[]) Optional.ofNullable(bytes).orElse(new byte[0]));
        }
    }

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/cos/COSStream$LazySeekableSourceViewHolder.class */
    private static class LazySeekableSourceViewHolder implements Closeable {
        private WeakReference<SeekableSource> sourceRef;
        private final long length;
        private final Supplier<SeekableSource> supplier;
        private SeekableSource view;

        public LazySeekableSourceViewHolder(SeekableSource source, long startingPosition, long length) {
            this.supplier = () -> {
                try {
                    return ((SeekableSource) Optional.ofNullable(this.sourceRef.get()).filter((v0) -> {
                        return v0.isOpen();
                    }).orElseThrow(() -> {
                        return new IllegalStateException("The original SeekableSource has been closed");
                    })).view(startingPosition, length);
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            };
            this.sourceRef = new WeakReference<>(source);
            this.length = length;
        }

        SeekableSource get() throws IOException {
            if (this.view == null) {
                this.view = this.supplier.get();
            }
            this.view.position(0L);
            return this.view;
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            IOUtils.close(this.view);
            this.view = null;
        }
    }
}
