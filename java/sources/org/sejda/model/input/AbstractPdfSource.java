package org.sejda.model.input;

import jakarta.validation.constraints.NotEmpty;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/input/AbstractPdfSource.class */
public abstract class AbstractPdfSource<T> extends AbstractTaskSource<T> implements PdfSource<T> {
    private String password;

    @NotEmpty
    private final String name;

    public AbstractPdfSource(String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("A not blank name are expected.");
        }
        this.name = name;
    }

    public AbstractPdfSource(String name, String password) {
        this(name);
        this.password = password;
    }

    @Override // org.sejda.model.input.PdfSource
    public String getPassword() {
        return this.password;
    }

    @Override // org.sejda.model.input.PdfSource
    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getPasswordBytes() {
        byte[] retVal = null;
        if (StringUtils.isNotEmpty(this.password)) {
            retVal = this.password.getBytes();
        }
        return retVal;
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
