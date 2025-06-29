package org.sejda.core.support.io;

import org.sejda.model.output.ExistingOutputPolicy;
import org.sejda.model.task.TaskExecutionContext;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/support/io/OutputWriters.class */
public final class OutputWriters {
    private OutputWriters() {
    }

    public static SingleOutputWriter newSingleOutputWriter(ExistingOutputPolicy policy, TaskExecutionContext executionContext) {
        return new DefaultSingleOutputWriter(policy, executionContext);
    }

    public static MultipleOutputWriter newMultipleOutputWriter(ExistingOutputPolicy policy, TaskExecutionContext executionContext) {
        return new DefaultMultipleOutputWriter(policy, executionContext);
    }
}
