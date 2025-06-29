package org.sejda.model.input;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.sejda.io.SeekableSource;
import org.sejda.io.SeekableSources;
import org.sejda.model.encryption.NoEncryptionAtRest;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.validation.constraint.ExistingFile;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/input/FileSource.class */
public class FileSource extends AbstractSource<File> {

    @ExistingFile
    private final File file;

    public FileSource(File file) {
        super(file.getName());
        this.file = file;
    }

    @Override // org.sejda.model.input.TaskSource
    public File getSource() {
        return this.file;
    }

    @Override // org.sejda.model.input.AbstractSource, org.sejda.model.input.AbstractTaskSource
    public String toString() {
        return this.file.getAbsolutePath();
    }

    @Override // org.sejda.model.input.Source
    public <R> R dispatch(SourceDispatcher<R> dispatcher) throws TaskIOException {
        return dispatcher.dispatch(this);
    }

    @Override // org.sejda.model.input.AbstractTaskSource
    public SeekableSource initializeSeekableSource() throws IOException {
        if (getEncryptionAtRestPolicy() instanceof NoEncryptionAtRest) {
            return SeekableSources.seekableSourceFrom(this.file);
        }
        return SeekableSources.onTempFileSeekableSourceFrom(getEncryptionAtRestPolicy().decrypt(new FileInputStream(this.file)), this.file.getName());
    }

    public static FileSource newInstance(File file) {
        if (file == null || !file.isFile()) {
            throw new IllegalArgumentException("A not null File instance that isFile is expected. Path: " + String.valueOf(file));
        }
        return new FileSource(file);
    }
}
