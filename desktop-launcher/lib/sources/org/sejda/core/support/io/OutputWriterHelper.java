package org.sejda.core.support.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.sejda.model.output.ExistingOutputPolicy;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/support/io/OutputWriterHelper.class */
final class OutputWriterHelper {
    private static final Logger LOG = LoggerFactory.getLogger(OutputWriterHelper.class);

    private OutputWriterHelper() {
    }

    static void moveToFile(Map<String, File> files, File outputFile, ExistingOutputPolicy existingOutputPolicy, TaskExecutionContext executionContext) throws IOException {
        if (outputFile.exists() && !outputFile.isFile()) {
            throw new IOException(String.format("Wrong output destination %s, must be a file.", outputFile));
        }
        if (files.size() != 1) {
            throw new IOException(String.format("Wrong files map size %d, must be 1 to copy to the selected destination %s", Integer.valueOf(files.size()), outputFile));
        }
        for (Map.Entry<String, File> entry : files.entrySet()) {
            moveFile(entry.getValue(), outputFile, (ExistingOutputPolicy) Optional.of(existingOutputPolicy).filter(p -> {
                return p != ExistingOutputPolicy.SKIP;
            }).orElseGet(() -> {
                LOG.debug("Cannot use {} output policy for single output, replaced with {}", ExistingOutputPolicy.SKIP, ExistingOutputPolicy.FAIL);
                return ExistingOutputPolicy.FAIL;
            }), executionContext);
        }
    }

    static void moveToDirectory(Map<String, File> files, File outputDirectory, ExistingOutputPolicy existingOutputPolicy, TaskExecutionContext executionContext) throws IOException {
        if (!outputDirectory.exists() && !outputDirectory.mkdirs()) {
            throw new IOException(String.format("Unable to make destination directory tree %s.", outputDirectory));
        }
        if (!outputDirectory.isDirectory()) {
            throw new IOException(String.format("Wrong output destination %s, must be a directory.", outputDirectory));
        }
        for (Map.Entry<String, File> entry : files.entrySet()) {
            if (StringUtils.isBlank(entry.getKey())) {
                throw new IOException(String.format("Unable to move %s to the output directory, no output name specified.", entry.getValue()));
            }
            moveFile(entry.getValue(), new File(outputDirectory, finalName(entry.getKey(), files.size())), existingOutputPolicy, executionContext);
        }
    }

    static void moveFile(File input, File output, ExistingOutputPolicy existingOutputPolicy, TaskExecutionContext executionContext) throws IOException {
        if (output.exists()) {
            switch (existingOutputPolicy) {
                case OVERWRITE:
                    LOG.debug("Moving {} to {}.", input.getAbsolutePath(), output.getAbsolutePath());
                    Files.move(input.toPath(), output.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    executionContext.notifiableTaskMetadata().addTaskOutput(output);
                    return;
                case RENAME:
                    File newNamedOutput = IOUtils.findNewNameThatDoesNotExist(output);
                    LOG.debug("Output exists {}, will use new name {}.", output, newNamedOutput);
                    doMoveFile(input, newNamedOutput);
                    executionContext.notifiableTaskMetadata().addTaskOutput(newNamedOutput);
                    return;
                case SKIP:
                    executionContext.notifiableTaskMetadata().addSkippedOutput(output);
                    LOG.info("Skipping already existing output file {}", output);
                    return;
                default:
                    throw new IOException(String.format("Unable to write %s to the already existing file destination %s. (policy is %s)", input, output, existingOutputPolicy));
            }
        }
        LOG.debug("Moving {} to {}.", input, output);
        doMoveFile(input, output);
        executionContext.notifiableTaskMetadata().addTaskOutput(output);
    }

    private static void doMoveFile(File input, File output) throws IOException {
        try {
            FileUtils.moveFile(input, output);
        } catch (IOException ex) {
            if (ex.getMessage().contains("Failed to delete original file")) {
                LOG.warn(ex.getMessage());
                input.deleteOnExit();
                return;
            }
            throw ex;
        }
    }

    private static String finalName(String filename, int totalFilesNumber) {
        return IOUtils.shortenFilename(filename.replace("[TOTAL_FILESNUMBER]", Integer.toString(totalFilesNumber)));
    }

    /* JADX WARN: Finally extract failed */
    static void copyToStreamZipped(Map<String, File> files, OutputStream out) throws IOException {
        ZipOutputStream zipOut = new ZipOutputStream(out);
        try {
            for (Map.Entry<String, File> entry : files.entrySet()) {
                if (StringUtils.isBlank(entry.getKey())) {
                    throw new IOException(String.format("Unable to copy %s to the output stream, no output name specified.", entry.getValue()));
                }
                try {
                    FileInputStream input = new FileInputStream(entry.getValue());
                    try {
                        zipOut.putNextEntry(new ZipEntry(entry.getKey()));
                        LOG.debug("Copying {} to zip stream {}.", entry.getValue(), entry.getKey());
                        org.sejda.commons.util.IOUtils.copy(input, zipOut);
                        input.close();
                        delete(entry.getValue());
                    } finally {
                    }
                } catch (Throwable th) {
                    delete(entry.getValue());
                    throw th;
                }
            }
            zipOut.close();
        } catch (Throwable th2) {
            try {
                zipOut.close();
            } catch (Throwable th3) {
                th2.addSuppressed(th3);
            }
            throw th2;
        }
    }

    static void copyToStream(File file, OutputStream out) throws IOException {
        try {
            InputStream in = new FileInputStream(file);
            try {
                org.sejda.commons.util.IOUtils.copy(in, out);
                in.close();
            } finally {
            }
        } finally {
            delete(file);
        }
    }

    private static void delete(File file) {
        if (!file.delete()) {
            LOG.warn("Unable to delete temporary file {}", file);
        }
    }
}
