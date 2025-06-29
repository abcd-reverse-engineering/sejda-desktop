package org.sejda.model.input;

import java.io.IOException;
import org.sejda.io.SeekableSource;
import org.sejda.model.encryption.EncryptionAtRestPolicy;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/input/TaskSource.class */
public interface TaskSource<T> {
    T getSource();

    String getName();

    SeekableSource getSeekableSource() throws IOException;

    void setEncryptionAtRestPolicy(EncryptionAtRestPolicy policy);

    EncryptionAtRestPolicy getEncryptionAtRestPolicy();
}
