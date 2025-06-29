package org.sejda.io;

import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

/* loaded from: org.sejda.sejda-io-4.0.0.M4.jar:org/sejda/io/DevNullWritableByteChannel.class */
public class DevNullWritableByteChannel implements WritableByteChannel {
    private boolean open = true;

    @Override // java.nio.channels.Channel
    public boolean isOpen() {
        return this.open;
    }

    @Override // java.nio.channels.Channel, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.open = false;
    }

    @Override // java.nio.channels.WritableByteChannel
    public int write(ByteBuffer src) {
        return src.remaining();
    }
}
