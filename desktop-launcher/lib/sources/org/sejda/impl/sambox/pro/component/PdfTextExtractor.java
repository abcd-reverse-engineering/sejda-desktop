package org.sejda.impl.sambox.pro.component;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.Objects;
import org.sejda.commons.util.IOUtils;
import org.sejda.commons.util.StringUtils;
import org.sejda.impl.sambox.component.PdfVisibleTextStripper;
import org.sejda.model.exception.TaskException;
import org.sejda.model.exception.TaskExecutionException;
import org.sejda.model.exception.TaskIOException;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/PdfTextExtractor.class */
public class PdfTextExtractor implements Closeable {
    private static final Logger LOG = LoggerFactory.getLogger(PdfTextExtractor.class);
    private PdfVisibleTextStripper textStripper;
    private OutputStream outputStream;
    private Charset encoding;
    private StringWriter contentsWriter = new StringWriter();

    public PdfTextExtractor(Charset encoding, OutputStream outputStream) throws TaskException {
        try {
            this.encoding = encoding;
            this.outputStream = outputStream;
            this.textStripper = new PdfVisibleTextStripper(this.contentsWriter);
            this.textStripper.setLineSeparator("\r\n");
            this.textStripper.setPageEnd("\r\n");
        } catch (IOException e) {
            throw new TaskExecutionException("An error occurred creating a file writer", e);
        }
    }

    void extract(PDPage page) {
        if (Objects.nonNull(page) && page.hasContents()) {
            try {
                this.textStripper.extract(page);
                return;
            } catch (TaskIOException e) {
                LOG.warn("Skipping page, an error occurred extracting text.", e);
                return;
            }
        }
        LOG.warn("Skipping null or no content page");
    }

    public void extract(PDDocument document) {
        document.getPages().forEach(this::extract);
        end();
    }

    public boolean wasEmptyOutput() {
        String[] lines = this.contentsWriter.getBuffer().toString().split("\n");
        for (String line : lines) {
            String normalizedLine = StringUtils.normalizeWhitespace(line);
            if (org.apache.commons.lang3.StringUtils.isNotBlank(normalizedLine.trim())) {
                return false;
            }
        }
        return true;
    }

    public void end() {
        OutputStreamWriter writer = new OutputStreamWriter(this.outputStream, this.encoding);
        try {
            try {
                writer.write(this.contentsWriter.getBuffer().toString());
                IOUtils.closeQuietly(writer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (Throwable th) {
            IOUtils.closeQuietly(writer);
            throw th;
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        IOUtils.closeQuietly(this.textStripper);
    }
}
