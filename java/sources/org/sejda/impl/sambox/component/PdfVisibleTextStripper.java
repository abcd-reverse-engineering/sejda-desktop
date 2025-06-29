package org.sejda.impl.sambox.component;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import org.sejda.commons.util.IOUtils;
import org.sejda.model.exception.TaskIOException;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.common.PDStream;
import org.sejda.sambox.text.PDFTextStripper;
import org.sejda.sambox.text.TextPosition;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/PdfVisibleTextStripper.class */
public class PdfVisibleTextStripper extends PDFTextStripper implements Closeable {
    public PdfVisibleTextStripper(Writer outputWriter) throws IOException {
        setShouldSeparateByBeads(false);
        this.output = outputWriter;
    }

    @Override // org.sejda.sambox.text.PDFTextStripper, org.sejda.sambox.text.PDFTextStreamEngine
    protected void processTextPosition(TextPosition text) {
        if (text.isVisible()) {
            super.processTextPosition(text);
        }
    }

    public void extract(PDPage page) throws TaskIOException {
        try {
            setSortByPosition(true);
            setStartPage(getCurrentPageNo());
            setEndPage(getCurrentPageNo());
            if (page.hasContents()) {
                processPage(page);
            }
        } catch (IOException e) {
            throw new TaskIOException("An error occurred extracting text from page.", e);
        }
    }

    @Override // org.sejda.sambox.text.PDFTextStripper
    protected void endPage(PDPage page) {
        Iterator<PDStream> iter = page.getContentStreams();
        while (iter.hasNext()) {
            iter.next().getCOSObject().unDecode();
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        IOUtils.closeQuietly(this.output);
    }
}
