package org.sejda.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import org.sejda.commons.util.IOUtils;
import org.sejda.commons.util.RequireUtils;

/* loaded from: org.sejda.sejda-io-4.0.0.M4.jar:org/sejda/io/OffsettableSeekableSourceImpl.class */
class OffsettableSeekableSourceImpl implements OffsettableSeekableSource {
    private final SeekableSource wrapped;
    private long offset = 0;

    public OffsettableSeekableSourceImpl(SeekableSource wrapped) {
        RequireUtils.requireNotNullArg(wrapped, "Input decorated SeekableSource cannot be null");
        this.wrapped = wrapped;
    }

    @Override // org.sejda.io.SeekableSource
    public String id() {
        return this.wrapped.id();
    }

    @Override // java.nio.channels.Channel
    public boolean isOpen() {
        return this.wrapped.isOpen();
    }

    @Override // org.sejda.io.SeekableSource
    public void requireOpen() throws IOException {
        this.wrapped.requireOpen();
    }

    @Override // org.sejda.io.SeekableSource
    public long size() {
        return this.wrapped.size() - this.offset;
    }

    @Override // org.sejda.io.OffsettableSeekableSource
    public void offset(long offset) throws IOException {
        RequireUtils.requireArg(offset >= 0, "Cannot set a negative offset");
        RequireUtils.requireArg(this.wrapped.size() - offset >= 0, "Invalid offset bigger then the wrapped source size");
        this.offset = offset;
        this.wrapped.position(this.wrapped.position() + offset);
    }

    @Override // org.sejda.io.SeekableSource
    public long position() throws IOException {
        return this.wrapped.position() - this.offset;
    }

    @Override // org.sejda.io.SeekableSource
    public SeekableSource position(long position) throws IOException {
        return this.wrapped.position(position + this.offset);
    }

    @Override // org.sejda.io.SeekableSource
    public int read() throws IOException {
        return this.wrapped.read();
    }

    @Override // org.sejda.io.SeekableSource
    public SeekableSource view(long startingPosition, long length) throws IOException {
        return this.wrapped.view(startingPosition + this.offset, length);
    }

    @Override // java.nio.channels.ReadableByteChannel
    public int read(ByteBuffer dst) throws IOException {
        return this.wrapped.read(dst);
    }

    @Override // java.nio.channels.Channel, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        IOUtils.close(this.wrapped);
    }
}
