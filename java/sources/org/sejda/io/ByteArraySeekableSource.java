package org.sejda.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Optional;
import java.util.UUID;
import org.sejda.commons.util.RequireUtils;

/* loaded from: org.sejda.sejda-io-4.0.0.M4.jar:org/sejda/io/ByteArraySeekableSource.class */
public class ByteArraySeekableSource extends BaseSeekableSource {
    private byte[] bytes;
    private long position;

    public ByteArraySeekableSource(byte[] bytes) {
        super((String) Optional.ofNullable(bytes).map(UUID::nameUUIDFromBytes).map((v0) -> {
            return v0.toString();
        }).orElseThrow(() -> {
            return new IllegalArgumentException("Input byte array cannot be null");
        }));
        this.bytes = bytes;
    }

    @Override // org.sejda.io.SeekableSource
    public long position() {
        return this.position;
    }

    @Override // org.sejda.io.SeekableSource
    public SeekableSource position(long position) {
        RequireUtils.requireArg(position >= 0, "Cannot set position to a negative value");
        this.position = Math.min(position, size());
        return this;
    }

    @Override // org.sejda.io.SeekableSource
    public long size() {
        return this.bytes.length;
    }

    @Override // java.nio.channels.ReadableByteChannel
    public int read(ByteBuffer dst) throws IOException {
        requireOpen();
        if (this.position < size()) {
            int toCopy = (int) Math.min(dst.remaining(), size() - this.position);
            dst.put(this.bytes, (int) this.position, toCopy);
            this.position += toCopy;
            return toCopy;
        }
        return -1;
    }

    @Override // org.sejda.io.SeekableSource
    public int read() throws IOException {
        requireOpen();
        if (this.position < size()) {
            byte[] bArr = this.bytes;
            long j = this.position;
            this.position = j + 1;
            return bArr[(int) j] & 255;
        }
        return -1;
    }

    @Override // org.sejda.io.BaseSeekableSource, java.nio.channels.Channel, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        super.close();
        this.bytes = new byte[0];
    }

    @Override // org.sejda.io.SeekableSource
    public SeekableSource view(long startingPosition, long length) throws IOException {
        requireOpen();
        return new SeekableSourceView(() -> {
            return new ByteArraySeekableSource(this.bytes);
        }, id(), startingPosition, length);
    }
}
