package org.sejda.io;

import java.io.IOException;
import org.sejda.commons.util.RequireUtils;

/* loaded from: org.sejda.sejda-io-4.0.0.M4.jar:org/sejda/io/BaseSeekableSource.class */
public abstract class BaseSeekableSource implements SeekableSource {
    private boolean open = true;
    private final String id;

    public BaseSeekableSource(String id) {
        RequireUtils.requireNotBlank(id, "SeekableSource id cannot be blank");
        this.id = id;
    }

    @Override // java.nio.channels.Channel
    public boolean isOpen() {
        return this.open;
    }

    @Override // java.nio.channels.Channel, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.open = false;
    }

    @Override // org.sejda.io.SeekableSource
    public void requireOpen() throws IOException {
        RequireUtils.requireState(isOpen(), "The SeekableSource has been closed");
    }

    @Override // org.sejda.io.SeekableSource
    public String id() {
        return this.id;
    }
}
