package org.sejda.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import org.sejda.commons.util.IOUtils;
import org.sejda.commons.util.RequireUtils;

/* loaded from: org.sejda.sejda-io-4.0.0.M4.jar:org/sejda/io/BufferedCountingChannelWriter.class */
public class BufferedCountingChannelWriter implements Closeable {
    public static final String OUTPUT_BUFFER_SIZE_PROPERTY = "org.sejda.io.buffered.output.size";
    private static final byte EOL = 10;
    private final CountingWritableByteChannel channel;
    private final ByteBuffer buffer = ByteBuffer.allocate(Integer.getInteger(OUTPUT_BUFFER_SIZE_PROPERTY, 4096).intValue());
    private boolean onNewLine = false;

    public BufferedCountingChannelWriter(CountingWritableByteChannel channel) {
        RequireUtils.requireNotNullArg(channel, "Cannot write to a null channel");
        this.channel = channel;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.channel.isOpen() && this.buffer.position() != 0) {
            flush();
        }
        IOUtils.close(this.channel);
    }

    public void writeEOL() throws IOException {
        if (!this.onNewLine) {
            write((byte) 10);
            this.onNewLine = true;
        }
    }

    public void write(String value) throws IOException {
        write(value.getBytes(StandardCharsets.ISO_8859_1));
    }

    public void write(byte[] bytes) throws IOException {
        for (byte aByte : bytes) {
            write(aByte);
        }
    }

    public void write(byte myByte) throws IOException {
        this.onNewLine = false;
        this.buffer.put(myByte);
        if (!this.buffer.hasRemaining()) {
            flush();
        }
    }

    public void write(InputStream stream) throws IOException {
        this.onNewLine = false;
        ReadableByteChannel readable = Channels.newChannel(stream);
        try {
            flush();
            while (readable.read(this.buffer) != -1) {
                flush();
            }
            if (readable != null) {
                readable.close();
            }
        } catch (Throwable th) {
            if (readable != null) {
                try {
                    readable.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public long offset() {
        return this.channel.count() + this.buffer.position();
    }

    public void flush() throws IOException {
        this.buffer.flip();
        this.channel.write(this.buffer);
        this.buffer.clear();
    }
}
