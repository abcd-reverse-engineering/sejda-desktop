package org.sejda.sambox.output;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import org.sejda.commons.util.RequireUtils;
import org.sejda.sambox.cos.IndirectCOSObjectReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/output/AsyncPDFBodyObjectsWriter.class */
class AsyncPDFBodyObjectsWriter implements PDFBodyObjectsWriter {
    private static final Logger LOG = LoggerFactory.getLogger(AsyncPDFBodyObjectsWriter.class);
    private final ExecutorService executor = Executors.newSingleThreadExecutor(target -> {
        return new Thread(null, target, "pdf-writer-thread", 0L);
    });
    private final AtomicReference<IOException> executionException = new AtomicReference<>();
    private final IndirectObjectsWriter writer;

    AsyncPDFBodyObjectsWriter(IndirectObjectsWriter writer) {
        RequireUtils.requireNotNullArg(writer, "Cannot write to a null writer");
        this.writer = writer;
    }

    @Override // org.sejda.sambox.output.PDFBodyObjectsWriter
    public void writeObject(IndirectCOSObjectReference ref) throws IOException {
        assertCanSubmitAsyncTask();
        this.executor.execute(() -> {
            try {
                if (this.executionException.get() == null) {
                    this.writer.writeObjectIfNotWritten(ref);
                }
            } catch (IOException e) {
                this.executionException.set(e);
            } catch (Exception e2) {
                this.executionException.set(new IOException(e2));
            }
        });
    }

    private void assertCanSubmitAsyncTask() throws IOException {
        IOException previous = this.executionException.get();
        if (previous != null) {
            this.executor.shutdownNow();
            throw previous;
        }
    }

    @Override // org.sejda.sambox.output.PDFBodyObjectsWriter
    public void onWriteCompletion() throws ExecutionException, InterruptedException, IOException {
        assertCanSubmitAsyncTask();
        try {
            this.executor.submit(() -> {
                IOException previous = this.executionException.get();
                if (previous != null) {
                    throw previous;
                }
                LOG.debug("Written document body");
                return null;
            }).get();
        } catch (InterruptedException e) {
            throw new IOException(e);
        } catch (ExecutionException e2) {
            throw new IOException(e2.getCause());
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.executor.shutdown();
    }
}
