package org.sejda.io;

import java.io.File;
import java.io.IOException;
import java.lang.foreign.Arena;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.sejda.commons.util.IOUtils;
import org.sejda.commons.util.RequireUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-io-4.0.0.M4.jar:org/sejda/io/MemoryMappedSeekableSource.class */
public class MemoryMappedSeekableSource extends BaseSeekableSource {
    private static final Logger LOG = LoggerFactory.getLogger(MemoryMappedSeekableSource.class);
    private static final long MB_256 = 268435456;
    private final long pageSize;
    private final List<ByteBuffer> pages;
    private final Arena arena;
    private final long size;
    private final ThreadBoundCopiesSupplier<MemoryMappedSeekableSource> localCopiesSupplier;
    private long position;

    public MemoryMappedSeekableSource(Path path) throws IOException {
        super((String) Optional.ofNullable(path).map((v0) -> {
            return v0.toAbsolutePath();
        }).map((v0) -> {
            return v0.toString();
        }).orElseThrow(() -> {
            return new IllegalArgumentException("Input path cannot be null");
        }));
        this.pageSize = Long.getLong(SeekableSources.MEMORY_MAPPED_PAGE_SIZE_PROPERTY, MB_256).longValue();
        this.pages = new ArrayList();
        this.localCopiesSupplier = new ThreadBoundCopiesSupplier<>(() -> {
            return new MemoryMappedSeekableSource(this);
        });
        FileChannel channel = FileChannel.open(path, StandardOpenOption.READ);
        try {
            this.size = channel.size();
            int zeroBasedPagesNumber = (int) (channel.size() / this.pageSize);
            this.arena = Arena.ofShared();
            for (int i = 0; i <= zeroBasedPagesNumber; i++) {
                if (i == zeroBasedPagesNumber) {
                    this.pages.add(i, channel.map(FileChannel.MapMode.READ_ONLY, i * this.pageSize, channel.size() - (i * this.pageSize), this.arena).asByteBuffer());
                } else {
                    this.pages.add(i, channel.map(FileChannel.MapMode.READ_ONLY, i * this.pageSize, this.pageSize, this.arena).asByteBuffer());
                }
            }
            LOG.debug("Created MemoryMappedSeekableSource with " + this.pages.size() + " pages");
            if (channel != null) {
                channel.close();
            }
        } catch (Throwable th) {
            if (channel != null) {
                try {
                    channel.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public MemoryMappedSeekableSource(File file) throws IOException {
        this((Path) Optional.ofNullable(file).map((v0) -> {
            return v0.toPath();
        }).orElseThrow(() -> {
            return new IllegalArgumentException("Input file cannot be null");
        }));
    }

    private MemoryMappedSeekableSource(MemoryMappedSeekableSource parent) {
        super(parent.id());
        this.pageSize = Long.getLong(SeekableSources.MEMORY_MAPPED_PAGE_SIZE_PROPERTY, MB_256).longValue();
        this.pages = new ArrayList();
        this.localCopiesSupplier = new ThreadBoundCopiesSupplier<>(() -> {
            return new MemoryMappedSeekableSource(this);
        });
        this.size = parent.size;
        for (ByteBuffer page : parent.pages) {
            this.pages.add(page.duplicate());
        }
        this.arena = null;
    }

    @Override // org.sejda.io.SeekableSource
    public long position() {
        return this.position;
    }

    @Override // org.sejda.io.SeekableSource
    public long size() {
        return this.size;
    }

    @Override // org.sejda.io.SeekableSource
    public SeekableSource position(long position) {
        RequireUtils.requireArg(position >= 0, "Cannot set position to a negative value");
        this.position = Math.min(position, this.size);
        return this;
    }

    @Override // java.nio.channels.ReadableByteChannel
    public int read(ByteBuffer dst) throws IOException {
        int read;
        requireOpen();
        int zeroBasedPagesNumber = (int) (position() / this.pageSize);
        ByteBuffer page = this.pages.get(zeroBasedPagesNumber);
        int relativePosition = (int) (position() - (zeroBasedPagesNumber * this.pageSize));
        if (relativePosition < page.limit()) {
            int page2 = readPage(dst, zeroBasedPagesNumber, relativePosition);
            while (true) {
                read = page2;
                if (!dst.hasRemaining()) {
                    break;
                }
                zeroBasedPagesNumber++;
                int readBytes = readPage(dst, zeroBasedPagesNumber, 0);
                if (readBytes == 0) {
                    break;
                }
                page2 = read + readBytes;
            }
            this.position += read;
            return read;
        }
        return -1;
    }

    private int readPage(ByteBuffer dst, int pageNumber, int bufferPosition) {
        if (pageNumber < this.pages.size()) {
            ByteBuffer page = this.pages.get(pageNumber);
            page.position(bufferPosition);
            if (page.hasRemaining()) {
                int toRead = Math.min(dst.remaining(), page.remaining());
                byte[] bufToRead = new byte[toRead];
                page.get(bufToRead);
                dst.put(bufToRead);
                return toRead;
            }
            return 0;
        }
        return 0;
    }

    @Override // org.sejda.io.SeekableSource
    public int read() throws IOException {
        requireOpen();
        int zeroBasedPagesNumber = (int) (position() / this.pageSize);
        ByteBuffer page = this.pages.get(zeroBasedPagesNumber);
        int relativePosition = (int) (position() - (zeroBasedPagesNumber * this.pageSize));
        if (relativePosition < page.limit()) {
            this.position++;
            return page.get(relativePosition);
        }
        return -1;
    }

    @Override // org.sejda.io.BaseSeekableSource, java.nio.channels.Channel, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        super.close();
        IOUtils.close(this.localCopiesSupplier);
        Optional.ofNullable(this.arena).filter(a -> {
            return a.scope().isAlive();
        }).ifPresent((v0) -> {
            v0.close();
        });
        this.pages.clear();
    }

    @Override // org.sejda.io.SeekableSource
    public SeekableSource view(long startingPosition, long length) throws IOException {
        requireOpen();
        return new SeekableSourceView(this.localCopiesSupplier, id(), startingPosition, length);
    }
}
