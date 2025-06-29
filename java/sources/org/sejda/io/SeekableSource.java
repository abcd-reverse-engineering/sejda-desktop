package org.sejda.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.ReadableByteChannel;

/* loaded from: org.sejda.sejda-io-4.0.0.M4.jar:org/sejda/io/SeekableSource.class */
public interface SeekableSource extends ReadableByteChannel {
    String id();

    long position() throws IOException;

    SeekableSource position(long j) throws IOException;

    long size();

    int read() throws IOException;

    SeekableSource view(long j, long j2) throws IOException;

    void requireOpen() throws IOException;

    default SeekableSource back(long offset) throws IOException {
        long newPosition = position() - offset;
        if (newPosition < 0 || newPosition > size()) {
            throw new IllegalArgumentException("Going back would move to " + newPosition + ", outside of source boundaries");
        }
        position(newPosition);
        return this;
    }

    default SeekableSource back() throws IOException {
        return back(1L);
    }

    default SeekableSource forward(long offset) throws IOException {
        return back(-offset);
    }

    default int peek() throws IOException {
        int val = read();
        if (val != -1) {
            back(1L);
        }
        return val;
    }

    default int peekBack() throws IOException {
        if (position() > 0) {
            back(1L);
            return read();
        }
        return -1;
    }

    default InputStream asInputStream() {
        return new SeekableSourceInputStream(this);
    }

    default void reset() {
        try {
            requireOpen();
            back(position());
        } catch (IOException e) {
            throw new RuntimeException("Failed to reset stream");
        }
    }

    default InputStream asNewInputStream() {
        reset();
        return new SeekableSourceInputStream(this);
    }
}
