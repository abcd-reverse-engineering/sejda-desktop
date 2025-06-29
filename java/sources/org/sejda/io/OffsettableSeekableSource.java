package org.sejda.io;

import java.io.IOException;

/* loaded from: org.sejda.sejda-io-4.0.0.M4.jar:org/sejda/io/OffsettableSeekableSource.class */
public interface OffsettableSeekableSource extends SeekableSource {
    void offset(long j) throws IOException;
}
