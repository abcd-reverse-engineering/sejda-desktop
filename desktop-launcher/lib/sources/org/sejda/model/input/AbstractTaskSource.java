package org.sejda.model.input;

import java.io.IOException;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.sejda.io.SeekableSource;
import org.sejda.model.encryption.EncryptionAtRestPolicy;
import org.sejda.model.encryption.NoEncryptionAtRest;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/input/AbstractTaskSource.class */
public abstract class AbstractTaskSource<T> implements TaskSource<T> {
    private EncryptionAtRestPolicy encryptionAtRestPolicy = NoEncryptionAtRest.INSTANCE;

    abstract SeekableSource initializeSeekableSource() throws IOException;

    @Override // org.sejda.model.input.TaskSource
    public EncryptionAtRestPolicy getEncryptionAtRestPolicy() {
        return this.encryptionAtRestPolicy;
    }

    @Override // org.sejda.model.input.TaskSource
    public void setEncryptionAtRestPolicy(EncryptionAtRestPolicy encryptionAtRestPolicy) {
        this.encryptionAtRestPolicy = encryptionAtRestPolicy;
    }

    @Override // org.sejda.model.input.TaskSource
    public SeekableSource getSeekableSource() throws IOException {
        return initializeSeekableSource();
    }

    public String toString() {
        return new ToStringBuilder(this).append(this.encryptionAtRestPolicy).toString();
    }
}
