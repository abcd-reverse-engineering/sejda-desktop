package org.sejda.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.function.Supplier;
import org.sejda.commons.util.RequireUtils;

/* loaded from: org.sejda.sejda-io-4.0.0.M4.jar:org/sejda/io/SeekableSourceView.class */
class SeekableSourceView extends BaseSeekableSource {
    private final long startingPosition;
    private final long length;
    private long currentPosition;
    private final Supplier<? extends SeekableSource> supplier;

    public SeekableSourceView(Supplier<? extends SeekableSource> supplier, String id, long startingPosition, long length) {
        super(id);
        RequireUtils.requireArg(startingPosition >= 0, "Starting position cannot be negative");
        RequireUtils.requireArg(length > 0, "View length must be positive");
        RequireUtils.requireNotNullArg(supplier, "Input decorated SeekableSource cannot be null");
        this.startingPosition = startingPosition;
        this.currentPosition = 0L;
        SeekableSource wrapped = supplier.get();
        RequireUtils.requireArg(startingPosition < wrapped.size(), "Starting position cannot be higher then wrapped source size");
        this.length = Math.min(length, wrapped.size() - startingPosition);
        this.supplier = supplier;
    }

    @Override // org.sejda.io.SeekableSource
    public long position() {
        return this.currentPosition;
    }

    @Override // org.sejda.io.SeekableSource
    public SeekableSource position(long newPosition) throws IOException {
        SeekableSource wrapped = this.supplier.get();
        RequireUtils.requireArg(newPosition >= 0, "Cannot set position to a negative value");
        this.currentPosition = Math.min(this.length, newPosition);
        wrapped.position(this.startingPosition + this.currentPosition);
        return this;
    }

    @Override // org.sejda.io.SeekableSource
    public long size() {
        return this.length;
    }

    @Override // java.nio.channels.ReadableByteChannel
    public int read(ByteBuffer dst) throws IOException {
        SeekableSource wrapped = this.supplier.get();
        requireOpen();
        if (hasAvailable()) {
            wrapped.position(this.startingPosition + this.currentPosition);
            if (dst.remaining() > available()) {
                dst.limit(dst.position() + ((int) available()));
            }
            int read = wrapped.read(dst);
            if (read > 0) {
                this.currentPosition += read;
                return read;
            }
            return -1;
        }
        return -1;
    }

    @Override // org.sejda.io.SeekableSource
    public int read() throws IOException {
        requireOpen();
        SeekableSource wrapped = this.supplier.get();
        if (hasAvailable()) {
            wrapped.position(this.startingPosition + this.currentPosition);
            this.currentPosition++;
            return wrapped.read();
        }
        return -1;
    }

    private boolean hasAvailable() {
        return available() > 0;
    }

    private long available() {
        return this.length - this.currentPosition;
    }

    @Override // org.sejda.io.BaseSeekableSource, java.nio.channels.Channel, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        super.close();
        this.currentPosition = 0L;
    }

    @Override // org.sejda.io.BaseSeekableSource, org.sejda.io.SeekableSource
    public void requireOpen() throws IOException {
        super.requireOpen();
        SeekableSource wrapped = this.supplier.get();
        RequireUtils.requireState(wrapped.isOpen(), "The original SeekableSource has been closed");
    }

    @Override // org.sejda.io.SeekableSource
    public SeekableSource view(long startingPosition, long length) {
        throw new RuntimeException("Cannot create a view of a view");
    }
}
