package org.sejda.io;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import org.sejda.commons.util.IOUtils;
import org.sejda.commons.util.RequireUtils;

/* loaded from: org.sejda.sejda-io-4.0.0.M4.jar:org/sejda/io/FileChannelSeekableSource.class */
public class FileChannelSeekableSource extends BaseSeekableSource {
    private final FileChannel channel;
    private Path path;
    private final long size;
    private final ThreadBoundCopiesSupplier<FileChannelSeekableSource> localCopiesSupplier;

    public FileChannelSeekableSource(Path path) {
        super((String) Optional.ofNullable(path).map((v0) -> {
            return v0.toAbsolutePath();
        }).map((v0) -> {
            return v0.toString();
        }).orElseThrow(() -> {
            return new IllegalArgumentException("Input path cannot be null");
        }));
        this.localCopiesSupplier = new ThreadBoundCopiesSupplier<>(() -> {
            return new FileChannelSeekableSource(this.path);
        });
        try {
            this.channel = FileChannel.open(path, StandardOpenOption.READ);
            this.size = this.channel.size();
            this.path = path;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public FileChannelSeekableSource(File file) {
        this((Path) Optional.ofNullable(file).map((v0) -> {
            return v0.toPath();
        }).orElseThrow(() -> {
            return new IllegalArgumentException("Input file cannot be null");
        }));
    }

    @Override // org.sejda.io.SeekableSource
    public long position() throws IOException {
        return this.channel.position();
    }

    @Override // org.sejda.io.SeekableSource
    public SeekableSource position(long newPosition) throws IOException {
        RequireUtils.requireArg(newPosition >= 0, "Cannot set position to a negative value");
        this.channel.position(newPosition);
        return this;
    }

    @Override // org.sejda.io.SeekableSource
    public long size() {
        return this.size;
    }

    @Override // org.sejda.io.BaseSeekableSource, java.nio.channels.Channel, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        super.close();
        IOUtils.close(this.localCopiesSupplier);
        IOUtils.close(this.channel);
    }

    @Override // java.nio.channels.ReadableByteChannel
    public int read(ByteBuffer dst) throws IOException {
        requireOpen();
        return this.channel.read(dst);
    }

    @Override // org.sejda.io.SeekableSource
    public int read() throws IOException {
        requireOpen();
        ByteBuffer buffer = ByteBuffer.allocate(1);
        if (this.channel.read(buffer) > 0) {
            buffer.flip();
            return buffer.get() & 255;
        }
        return -1;
    }

    @Override // org.sejda.io.SeekableSource
    public SeekableSource view(long startingPosition, long length) throws IOException {
        requireOpen();
        return new SeekableSourceView(this.localCopiesSupplier, id(), startingPosition, length);
    }
}
