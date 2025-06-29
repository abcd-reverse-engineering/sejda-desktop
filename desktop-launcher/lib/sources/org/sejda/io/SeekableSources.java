package org.sejda.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.Objects;
import org.sejda.commons.util.IOUtils;

/* loaded from: org.sejda.sejda-io-4.0.0.M4.jar:org/sejda/io/SeekableSources.class */
public final class SeekableSources {
    public static final String MAPPED_SIZE_THRESHOLD_PROPERTY = "org.sejda.io.mapped.size.threshold";
    public static final String DISABLE_MEMORY_MAPPED_PROPERTY = "org.sejda.io.mapped.disabled";
    public static final String INPUT_BUFFER_SIZE_PROPERTY = "org.sejda.io.buffered.input.size";
    public static final String MEMORY_MAPPED_PAGE_SIZE_PROPERTY = "org.sejda.io.memory.mapped.page.size";
    private static final long MB_16 = 16777216;

    private SeekableSources() {
    }

    public static SeekableSource seekableSourceFrom(File file) throws IOException {
        Objects.requireNonNull(file);
        return seekableSourceFrom(file.toPath());
    }

    public static SeekableSource seekableSourceFrom(Path path) throws IOException {
        Objects.requireNonNull(path);
        if (!"32".equals(System.getProperty("sun.arch.data.model")) && !Boolean.getBoolean(DISABLE_MEMORY_MAPPED_PROPERTY) && Files.size(path) > Long.getLong(MAPPED_SIZE_THRESHOLD_PROPERTY, MB_16).longValue()) {
            return new BufferedSeekableSource(new MemoryMappedSeekableSource(path));
        }
        return new BufferedSeekableSource(new FileChannelSeekableSource(path));
    }

    public static SeekableSource inMemorySeekableSourceFrom(InputStream stream) throws IOException {
        Objects.requireNonNull(stream);
        return new ByteArraySeekableSource(IOUtils.toByteArray(stream));
    }

    public static SeekableSource inMemorySeekableSourceFrom(byte[] bytes) {
        Objects.requireNonNull(bytes);
        return new ByteArraySeekableSource(bytes);
    }

    public static SeekableSource onTempFileSeekableSourceFrom(InputStream stream) throws IOException {
        return onTempFileSeekableSourceFrom(stream, "SejdaIO");
    }

    public static SeekableSource onTempFileSeekableSourceFrom(InputStream stream, String filenameHint) throws IOException {
        Objects.requireNonNull(stream);
        final Path tempDir = Files.createTempDirectory("SejdaIODir", new FileAttribute[0]);
        if (tempDir.toFile().listFiles().length > 0) {
            throw new RuntimeException("Temp dir collision: " + String.valueOf(tempDir.toAbsolutePath()));
        }
        final Path temp = tempDir.resolve(filenameHint);
        if (Files.exists(temp, new LinkOption[0])) {
            throw new RuntimeException("Temp file collision: " + String.valueOf(temp.toAbsolutePath()));
        }
        tempDir.toFile().deleteOnExit();
        temp.toFile().deleteOnExit();
        Files.copy(stream, temp, StandardCopyOption.REPLACE_EXISTING);
        return new BufferedSeekableSource(new FileChannelSeekableSource(temp) { // from class: org.sejda.io.SeekableSources.1
            @Override // org.sejda.io.FileChannelSeekableSource, org.sejda.io.BaseSeekableSource, java.nio.channels.Channel, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                super.close();
                Files.deleteIfExists(temp);
                Files.deleteIfExists(tempDir);
            }
        });
    }

    public static OffsettableSeekableSource asOffsettable(SeekableSource source) {
        Objects.requireNonNull(source);
        return new OffsettableSeekableSourceImpl(source);
    }
}
