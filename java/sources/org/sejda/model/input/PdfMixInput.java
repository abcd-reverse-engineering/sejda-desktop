package org.sejda.model.input;

import jakarta.validation.constraints.Min;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.sejda.model.validation.constraint.NoIntersections;

@NoIntersections
/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/input/PdfMixInput.class */
public class PdfMixInput extends PdfMergeInput {
    private boolean reverse;

    @Min(1)
    private int step;
    private boolean repeatForever;

    public PdfMixInput(PdfSource<?> source, boolean reverse, int step) {
        super(source);
        this.reverse = false;
        this.step = 1;
        this.repeatForever = false;
        this.reverse = reverse;
        this.step = step;
    }

    public PdfMixInput(PdfSource<?> source) {
        super(source);
        this.reverse = false;
        this.step = 1;
        this.repeatForever = false;
    }

    public boolean isReverse() {
        return this.reverse;
    }

    public int getStep() {
        return this.step;
    }

    public boolean isRepeatForever() {
        return this.repeatForever;
    }

    public void setRepeatForever(boolean repeatForever) {
        this.repeatForever = repeatForever;
    }

    @Override // org.sejda.model.input.PdfMergeInput
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString()).append(this.reverse).append(this.step).toString();
    }

    @Override // org.sejda.model.input.PdfMergeInput
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.reverse).append(this.step).append(this.repeatForever).toHashCode();
    }

    @Override // org.sejda.model.input.PdfMergeInput
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PdfMixInput)) {
            return false;
        }
        PdfMixInput input = (PdfMixInput) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.reverse, input.reverse).append(this.step, input.step).append(this.repeatForever, input.repeatForever).isEquals();
    }
}
