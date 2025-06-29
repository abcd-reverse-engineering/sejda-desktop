package org.sejda.model.output;

import java.io.File;
import org.sejda.model.encryption.EncryptionAtRestPolicy;
import org.sejda.model.exception.TaskOutputVisitException;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/output/TaskOutput.class */
public interface TaskOutput {
    File getDestination();

    void accept(TaskOutputDispatcher dispatcher) throws TaskOutputVisitException;

    EncryptionAtRestPolicy getEncryptionAtRestPolicy();

    void setEncryptionAtRestPolicy(EncryptionAtRestPolicy encryptionAtRestSecurity);
}
