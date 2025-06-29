package org.sejda.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import org.sejda.commons.util.IOUtils;
import org.sejda.commons.util.RequireUtils;

/* loaded from: org.sejda.sejda-io-4.0.0.M4.jar:org/sejda/io/BufferedSeekableSource.class */
public class BufferedSeekableSource implements SeekableSource {
    private final ByteBuffer buffer = ByteBuffer.allocate(Integer.getInteger(SeekableSources.INPUT_BUFFER_SIZE_PROPERTY, 8192).intValue());
    private final SeekableSource wrapped;
    private long position;
    private final long size;

    public BufferedSeekableSource(SeekableSource wrapped) {
        RequireUtils.requireNotNullArg(wrapped, "Input decorated SeekableSource cannot be null");
        this.wrapped = wrapped;
        this.size = wrapped.size();
        this.buffer.limit(0);
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
    public String id() {
        return this.wrapped.id();
    }

    @Override // org.sejda.io.SeekableSource
    public long position() {
        return this.position;
    }

    @Override // org.sejda.io.SeekableSource
    public SeekableSource position(long newPosition) throws IOException {
        RequireUtils.requireArg(newPosition >= 0, "Cannot set position to a negative value");
        if (newPosition != this.position) {
            long newBufPosition = (newPosition - this.position) + this.buffer.position();
            if (newBufPosition >= 0 && newBufPosition < this.buffer.limit()) {
                this.buffer.position((int) newBufPosition);
            } else {
                this.buffer.limit(0);
                this.wrapped.position(newPosition);
            }
            this.position = Math.min(newPosition, this.size);
        }
        return this;
    }

    @Override // org.sejda.io.SeekableSource
    public long size() {
        return this.size;
    }

    @Override // java.nio.channels.Channel, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        IOUtils.close(this.wrapped);
        this.buffer.clear();
        this.buffer.limit(0);
    }

    @Override // java.nio.channels.ReadableByteChannel
    public int read(ByteBuffer dst) throws IOException {
        requireOpen();
        this.buffer.limit(0);
        this.wrapped.position(this.position);
        int read = this.wrapped.read(dst);
        if (read > 0) {
            this.position += read;
        }
        return read;
    }

    @Override // org.sejda.io.SeekableSource
    public int read() throws IOException {
        requireOpen();
        if (ensureBuffer() > 0) {
            this.position++;
            return this.buffer.get() & 255;
        }
        return -1;
    }

    private int ensureBuffer() throws IOException {
        if (!this.buffer.hasRemaining()) {
            this.buffer.clear();
            this.wrapped.read(this.buffer);
            this.buffer.flip();
        }
        return this.buffer.remaining();
    }

    protected SeekableSource wrapped() {
        return this.wrapped;
    }

    @Override // org.sejda.io.SeekableSource
    public SeekableSource view(long startingPosition, long length) throws IOException {
        requireOpen();
        return new BufferedSeekableSource(this.wrapped.view(startingPosition, length));
    }
}
