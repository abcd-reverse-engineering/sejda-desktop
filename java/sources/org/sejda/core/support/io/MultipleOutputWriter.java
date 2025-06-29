package org.sejda.core.support.io;

import org.sejda.core.support.io.model.PopulatedFileOutput;
import org.sejda.model.output.TaskOutputDispatcher;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/support/io/MultipleOutputWriter.class */
public interface MultipleOutputWriter extends TaskOutputDispatcher {
    void addOutput(PopulatedFileOutput populatedFileOutput);
}
