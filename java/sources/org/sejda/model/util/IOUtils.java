package org.sejda.model.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.Objects;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.exception.TaskOutputVisitException;
import org.sejda.model.output.DirectoryTaskOutput;
import org.sejda.model.output.FileOrDirectoryTaskOutput;
import org.sejda.model.output.FileTaskOutput;
import org.sejda.model.output.TaskOutput;
import org.sejda.model.output.TaskOutputDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/util/IOUtils.class */
public final class IOUtils {
    public static final String TMP_BUFFER_PREFIX_PROPERTY_NAME = "sejda.tmp.buffer.prefix";
    private static final Logger LOG = LoggerFactory.getLogger(IOUtils.class);
    static final String BUFFER_NAME = "sejdaTmp";
    private static final int TEMP_DIR_ATTEMPTS = 1000;

    private IOUtils() {
    }

    public static File createTemporaryBuffer(TaskOutput taskOut) throws TaskIOException {
        TmpBufferLocationFinder bufferLocationFinder = new TmpBufferLocationFinder();
        try {
            taskOut.accept(bufferLocationFinder);
            File buffer = tmpFile(bufferLocationFinder.bufferLocation).toFile();
            buffer.deleteOnExit();
            return buffer;
        } catch (IOException | InvalidPathException | TaskOutputVisitException e) {
            try {
                return createTemporaryBuffer();
            } catch (TaskIOException ex) {
                throw new TaskIOException("Unable to create temporary buffer", ex);
            }
        }
    }

    private static Path tmpFile(Path location) throws IOException {
        String prefix = (SystemUtils.IS_OS_WINDOWS ? "" : ".") + System.getProperty(TMP_BUFFER_PREFIX_PROPERTY_NAME, BUFFER_NAME);
        return Files.createTempFile(location, prefix, null, new FileAttribute[0]);
    }

    public static File createTemporaryBuffer() throws TaskIOException {
        return createTemporaryBuffer(".tmp");
    }

    public static File createTemporaryBuffer(String extension) throws TaskIOException, IOException {
        try {
            File buffer = File.createTempFile(System.getProperty(TMP_BUFFER_PREFIX_PROPERTY_NAME, BUFFER_NAME), extension);
            buffer.deleteOnExit();
            return buffer;
        } catch (IOException e) {
            throw new TaskIOException("Unable to create temporary buffer", e);
        }
    }

    public static File createTemporaryBufferWithName(String filename) throws TaskIOException, IOException {
        try {
            File tmpDir = createTemporaryFolder();
            File buffer = new File(tmpDir, filename);
            boolean created = buffer.createNewFile();
            if (!created) {
                throw new IOException("Could not create new file: " + buffer.getAbsolutePath());
            }
            buffer.deleteOnExit();
            return buffer;
        } catch (IOException | IllegalStateException e) {
            throw new TaskIOException("Unable to create temporary buffer", e);
        }
    }

    public static File createTemporaryFolder() {
        File baseDir = SystemUtils.getJavaIoTmpDir();
        String baseName = System.getProperty(TMP_BUFFER_PREFIX_PROPERTY_NAME, BUFFER_NAME) + System.currentTimeMillis() + "-";
        for (int counter = 0; counter < TEMP_DIR_ATTEMPTS; counter++) {
            File tempDir = new File(baseDir, baseName + counter);
            if (tempDir.mkdir()) {
                tempDir.deleteOnExit();
                return tempDir;
            }
        }
        throw new IllegalStateException("Failed to create directory within 1000 attempts (tried " + baseName + "0 to " + baseName + "999)");
    }

    public static File findNewNameThatDoesNotExist(File output) throws IOException {
        File newNamedOutput;
        int count = 1;
        String basename = FilenameUtils.getBaseName(output.getName());
        String extension = FilenameUtils.getExtension(output.getName());
        do {
            String newName = String.format("%s(%d).%s", basename, Integer.valueOf(count), extension);
            if (isFilenameTooLong(newName)) {
                String extra = String.format("(%d)", Integer.valueOf(count));
                String newBasename = basename.substring(0, basename.length() - extra.length());
                newName = String.format("%s(%d).%s", newBasename, Integer.valueOf(count), extension);
            }
            newNamedOutput = new File(output.getParent(), newName);
            count++;
            if (count >= 100) {
                break;
            }
        } while (newNamedOutput.exists());
        if (newNamedOutput.exists()) {
            LOG.warn("Unable to generate a new filename that does not exist, path was {}", output);
            throw new IOException(String.format("Unable to generate a new filename that does not exist, path was %s", output));
        }
        return newNamedOutput;
    }

    private static boolean isFilenameTooLong(String name) {
        return !name.equals(shortenFilename(name));
    }

    public static String shortenFilename(String name) {
        if (SystemUtils.IS_OS_WINDOWS || SystemUtils.IS_OS_MAC) {
            return shortenFilenameCharLength(name, 255);
        }
        return shortenFilenameBytesLength(name, 254, StandardCharsets.UTF_8);
    }

    public static String toSafeFilename(String input) {
        return StringUtils.defaultString(input).replaceAll("\\s|\\h", " ").replaceAll("[��\f\t\n\r\\\\/:*?\\\"<>|]", "").trim();
    }

    public static String toStrictFilename(String input) {
        String safe = ((String) StringUtils.defaultIfBlank(input, "")).replaceAll("[^A-Za-z0-9_ .-]", "");
        if (safe.length() > 251) {
            safe = safe.substring(0, 251);
        }
        return safe;
    }

    static String shortenFilenameCharLength(String input, int maxCharLength) {
        if (input.length() > maxCharLength) {
            String baseName = FilenameUtils.getBaseName(input);
            String ext = FilenameUtils.getExtension(input);
            return String.format("%s.%s", baseName.substring(0, (maxCharLength - 1) - ext.length()), ext);
        }
        return input;
    }

    static String shortenFilenameBytesLength(String input, int maxBytesLength, Charset charset) {
        if (input.getBytes(charset).length > maxBytesLength) {
            String baseName = FilenameUtils.getBaseName(input);
            String ext = FilenameUtils.getExtension(input);
            String baseName2 = baseName.substring(0, baseName.length() - 1);
            String str = String.format("%s.%s", baseName2, ext);
            while (true) {
                String shorterFilename = str;
                if (shorterFilename.getBytes(charset).length > maxBytesLength) {
                    baseName2 = baseName2.substring(0, baseName2.length() - 1);
                    str = String.format("%s.%s", baseName2, ext);
                } else {
                    return shorterFilename;
                }
            }
        } else {
            return input;
        }
    }

    /* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/util/IOUtils$TmpBufferLocationFinder.class */
    private static class TmpBufferLocationFinder implements TaskOutputDispatcher {
        private Path bufferLocation = Paths.get(SystemUtils.JAVA_IO_TMPDIR, new String[0]);

        private TmpBufferLocationFinder() {
        }

        @Override // org.sejda.model.output.TaskOutputDispatcher
        public void dispatch(FileTaskOutput output) {
            Path dest = output.getDestination().toPath().getParent();
            if (Objects.nonNull(dest) && Files.exists(dest, new LinkOption[0])) {
                this.bufferLocation = dest;
            }
        }

        @Override // org.sejda.model.output.TaskOutputDispatcher
        public void dispatch(DirectoryTaskOutput output) {
            Path dest = output.getDestination().toPath();
            if (Files.exists(dest, new LinkOption[0])) {
                this.bufferLocation = dest;
            }
        }

        @Override // org.sejda.model.output.TaskOutputDispatcher
        public void dispatch(FileOrDirectoryTaskOutput output) {
            Path dest = output.getDestination().toPath();
            if (Files.exists(dest, new LinkOption[0])) {
                if (Files.isDirectory(dest, new LinkOption[0])) {
                    this.bufferLocation = dest;
                } else {
                    this.bufferLocation = dest.getParent();
                }
            }
        }
    }
}
