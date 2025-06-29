package org.sejda.model.input;

import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URL;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.sejda.io.SeekableSource;
import org.sejda.io.SeekableSources;
import org.sejda.model.exception.TaskIOException;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/input/PdfURLSource.class */
public final class PdfURLSource extends AbstractPdfSource<URL> {

    @NotNull
    private final URL url;

    private PdfURLSource(URL url, String name, String password) {
        super(name, password);
        this.url = url;
    }

    @Override // org.sejda.model.input.TaskSource
    public URL getSource() {
        return this.url;
    }

    @Override // org.sejda.model.input.PdfSource
    public <T> T open(PdfSourceOpener<T> opener) throws TaskIOException {
        return opener.open(this);
    }

    @Override // org.sejda.model.input.AbstractTaskSource
    public SeekableSource initializeSeekableSource() throws IOException {
        return SeekableSources.onTempFileSeekableSourceFrom(getEncryptionAtRestPolicy().decrypt(this.url.openStream()), getName());
    }

    @Override // org.sejda.model.input.AbstractPdfSource, org.sejda.model.input.AbstractTaskSource
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString()).append(this.url).toString();
    }

    public static PdfURLSource newInstanceNoPassword(URL url, String name) {
        return newInstanceWithPassword(url, name, null);
    }

    public static PdfURLSource newInstanceWithPassword(URL url, String name, String password) {
        if (url == null) {
            throw new IllegalArgumentException("A not null url instance and a not blank name are expected.");
        }
        return new PdfURLSource(url, name, password);
    }
}
