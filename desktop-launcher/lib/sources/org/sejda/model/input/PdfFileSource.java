package org.sejda.model.input;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.sejda.io.SeekableSource;
import org.sejda.io.SeekableSources;
import org.sejda.model.encryption.NoEncryptionAtRest;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.validation.constraint.PdfFile;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/input/PdfFileSource.class */
public class PdfFileSource extends AbstractPdfSource<File> {

    @PdfFile
    private final File file;

    private PdfFileSource(File file, String password) {
        super(file.getName(), password);
        this.file = file;
    }

    @Override // org.sejda.model.input.TaskSource
    public File getSource() {
        return this.file;
    }

    @Override // org.sejda.model.input.PdfSource
    public <T> T open(PdfSourceOpener<T> opener) throws TaskIOException {
        return opener.open(this);
    }

    @Override // org.sejda.model.input.AbstractTaskSource
    SeekableSource initializeSeekableSource() throws IOException {
        if (getEncryptionAtRestPolicy() instanceof NoEncryptionAtRest) {
            return SeekableSources.seekableSourceFrom(this.file);
        }
        return SeekableSources.onTempFileSeekableSourceFrom(getEncryptionAtRestPolicy().decrypt(new FileInputStream(this.file)), this.file.getName());
    }

    public static PdfFileSource newInstanceNoPassword(File file) {
        return newInstanceWithPassword(file, null);
    }

    public static PdfFileSource newInstanceWithPassword(File file, String password) {
        if (file == null || !file.isFile()) {
            throw new IllegalArgumentException("A not null File instance that isFile is expected. Path: " + String.valueOf(file));
        }
        return new PdfFileSource(file, password);
    }

    @Override // org.sejda.model.input.AbstractPdfSource, org.sejda.model.input.AbstractTaskSource
    public String toString() {
        return this.file.getAbsolutePath();
    }
}
