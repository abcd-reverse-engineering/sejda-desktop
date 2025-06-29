package org.sejda.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import org.sejda.commons.util.RequireUtils;

/* loaded from: org.sejda.sejda-io-4.0.0.M4.jar:org/sejda/io/SeekableSourceInputStream.class */
class SeekableSourceInputStream extends InputStream {
    private final SeekableSource wrapped;

    SeekableSourceInputStream(SeekableSource wrapped) {
        RequireUtils.requireNotNullArg(wrapped, "Cannot decorate a null instance");
        this.wrapped = wrapped;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        return getSource().read();
    }

    @Override // java.io.InputStream
    public int read(byte[] b) throws IOException {
        if (available() > 0) {
            return getSource().read(ByteBuffer.wrap(b, 0, Math.min(b.length, available())));
        }
        return -1;
    }

    @Override // java.io.InputStream
    public int read(byte[] b, int offset, int length) throws IOException {
        if (available() > 0) {
            return getSource().read(ByteBuffer.wrap(b, Math.min(b.length, offset), Math.min(length, Math.min(b.length - offset, available()))));
        }
        return -1;
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        SeekableSource source = getSource();
        return (int) Math.max(0L, source.size() - source.position());
    }

    @Override // java.io.InputStream
    public long skip(long offset) throws IOException {
        SeekableSource source = getSource();
        long start = source.position();
        return source.forward(Math.min(offset, available())).position() - start;
    }

    private SeekableSource getSource() {
        RequireUtils.requireState(this.wrapped.isOpen(), "The SeekableSource has been closed");
        return this.wrapped;
    }
}
