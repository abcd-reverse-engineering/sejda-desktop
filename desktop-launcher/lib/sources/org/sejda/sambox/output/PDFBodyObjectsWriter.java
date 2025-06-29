package org.sejda.sambox.output;

import java.io.Closeable;
import java.io.IOException;
import org.sejda.sambox.cos.IndirectCOSObjectReference;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/output/PDFBodyObjectsWriter.class */
public interface PDFBodyObjectsWriter extends Closeable {
    void writeObject(IndirectCOSObjectReference indirectCOSObjectReference) throws IOException;

    void onWriteCompletion() throws IOException;
}
