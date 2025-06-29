package org.sejda.model.input;

import jakarta.validation.constraints.NotEmpty;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/input/AbstractSource.class */
public abstract class AbstractSource<T> extends AbstractTaskSource<T> implements Source<T> {

    @NotEmpty
    private final String name;

    public AbstractSource(String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("A not blank name are expected.");
        }
        this.name = name;
    }

    @Override // org.sejda.model.input.TaskSource
    public String getName() {
        return this.name;
    }

    @Override // org.sejda.model.input.AbstractTaskSource
    public String toString() {
        return new ToStringBuilder(this).append(this.name).toString();
    }
}
