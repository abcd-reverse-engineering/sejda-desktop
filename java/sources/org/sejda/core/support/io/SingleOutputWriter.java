package org.sejda.core.support.io;

import java.io.File;
import org.sejda.model.output.TaskOutputDispatcher;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/support/io/SingleOutputWriter.class */
public interface SingleOutputWriter extends TaskOutputDispatcher {
    void taskOutput(File file);
}
