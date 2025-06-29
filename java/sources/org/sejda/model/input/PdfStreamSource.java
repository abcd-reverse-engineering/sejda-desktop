package org.sejda.model.input;

import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import org.sejda.io.SeekableSource;
import org.sejda.io.SeekableSources;
import org.sejda.model.exception.TaskIOException;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/input/PdfStreamSource.class */
public final class PdfStreamSource extends AbstractPdfSource<InputStream> {

    @NotNull
    private final InputStream stream;

    private PdfStreamSource(InputStream stream, String name, String password) {
        super(name, password);
        this.stream = stream;
    }

    @Override // org.sejda.model.input.TaskSource
    public InputStream getSource() {
        return this.stream;
    }

    @Override // org.sejda.model.input.PdfSource
    public <T> T open(PdfSourceOpener<T> opener) throws TaskIOException {
        return opener.open(this);
    }

    @Override // org.sejda.model.input.AbstractTaskSource
    public SeekableSource initializeSeekableSource() throws IOException {
        return SeekableSources.onTempFileSeekableSourceFrom(getEncryptionAtRestPolicy().decrypt(this.stream), getName());
    }

    public static PdfStreamSource newInstanceNoPassword(InputStream stream, String name) {
        return newInstanceWithPassword(stream, name, null);
    }

    public static PdfStreamSource newInstanceWithPassword(InputStream stream, String name, String password) {
        if (stream == null) {
            throw new IllegalArgumentException("A not null stream instance and a not blank name are expected.");
        }
        return new PdfStreamSource(stream, name, password);
    }
}
