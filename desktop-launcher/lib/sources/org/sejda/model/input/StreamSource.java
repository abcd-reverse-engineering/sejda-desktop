package org.sejda.model.input;

import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import org.sejda.io.SeekableSource;
import org.sejda.io.SeekableSources;
import org.sejda.model.exception.TaskIOException;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/input/StreamSource.class */
public class StreamSource extends AbstractSource<InputStream> {

    @NotNull
    private final InputStream stream;

    private StreamSource(InputStream stream, String name) {
        super(name);
        this.stream = stream;
    }

    @Override // org.sejda.model.input.TaskSource
    public InputStream getSource() {
        return this.stream;
    }

    @Override // org.sejda.model.input.Source
    public <R> R dispatch(SourceDispatcher<R> dispatcher) throws TaskIOException {
        return dispatcher.dispatch(this);
    }

    @Override // org.sejda.model.input.AbstractTaskSource
    public SeekableSource initializeSeekableSource() throws IOException {
        return SeekableSources.onTempFileSeekableSourceFrom(getEncryptionAtRestPolicy().decrypt(this.stream), getName());
    }

    public static StreamSource newInstance(InputStream stream, String name) {
        if (stream == null) {
            throw new IllegalArgumentException("A not null stream instance and a not blank name are expected.");
        }
        return new StreamSource(stream, name);
    }
}
