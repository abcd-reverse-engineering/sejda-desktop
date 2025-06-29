package org.sejda.model.output;

import java.io.IOException;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/output/TaskOutputDispatcher.class */
public interface TaskOutputDispatcher {
    void dispatch(FileTaskOutput output) throws IOException;

    void dispatch(DirectoryTaskOutput output) throws IOException;

    void dispatch(FileOrDirectoryTaskOutput output) throws IOException;
}
