package org.sejda.io;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.WritableByteChannel;
import org.sejda.commons.util.IOUtils;
import org.sejda.commons.util.RequireUtils;

/* loaded from: org.sejda.sejda-io-4.0.0.M4.jar:org/sejda/io/CountingWritableByteChannel.class */
public class CountingWritableByteChannel implements WritableByteChannel {
    private long written = 0;
    private final WritableByteChannel wrapped;

    public CountingWritableByteChannel(WritableByteChannel wrapped) {
        RequireUtils.requireNotNullArg(wrapped, "Cannot decorate a null instance");
        this.wrapped = wrapped;
    }

    public long count() {
        return this.written;
    }

    @Override // java.nio.channels.WritableByteChannel
    public int write(ByteBuffer src) throws IOException {
        if (!isOpen()) {
            throw new ClosedChannelException();
        }
        int count = this.wrapped.write(src);
        this.written += count;
        return count;
    }

    @Override // java.nio.channels.Channel
    public boolean isOpen() {
        return this.wrapped.isOpen();
    }

    @Override // java.nio.channels.Channel, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        IOUtils.close(this.wrapped);
    }

    public static CountingWritableByteChannel from(WritableByteChannel channel) {
        return new CountingWritableByteChannel(channel);
    }

    public static CountingWritableByteChannel from(OutputStream stream) {
        return new CountingWritableByteChannel(Channels.newChannel(stream));
    }

    public static CountingWritableByteChannel from(File file) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        raf.setLength(0L);
        return new CountingWritableByteChannel(raf.getChannel());
    }

    public static CountingWritableByteChannel from(String file) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        raf.setLength(0L);
        return new CountingWritableByteChannel(raf.getChannel());
    }
}
