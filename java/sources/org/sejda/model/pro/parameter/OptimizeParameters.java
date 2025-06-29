package org.sejda.model.pro.parameter;

import jakarta.validation.constraints.NotEmpty;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.commons.collection.NullSafeSet;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;
import org.sejda.model.pro.optimization.Optimization;

/* loaded from: org.sejda.sejda-model-pro-5.1.10.1.jar:org/sejda/model/pro/parameter/OptimizeParameters.class */
public class OptimizeParameters extends MultiplePdfSourceMultipleOutputParameters {
    private float imageQuality = 0.65f;
    private int imageDpi = 72;
    private int imageMinBytesSize = 0;

    @NotEmpty
    private Set<Optimization> optimizations = new NullSafeSet();
    private boolean failIfOutputSizeNotReduced = false;
    private boolean performVisualComparisonChecks = false;

    public float getImageQuality() {
        return this.imageQuality;
    }

    public int getImageDpi() {
        return this.imageDpi;
    }

    public void setImageDpi(int imageDpi) {
        this.imageDpi = imageDpi;
    }

    public void setImageQuality(float imageQuality) {
        this.imageQuality = imageQuality;
    }

    public Set<Optimization> getOptimizations() {
        return this.optimizations;
    }

    public void setOptimizations(Set<Optimization> optimizations) {
        this.optimizations = optimizations;
    }

    public void addOptimization(Optimization optimization) {
        this.optimizations.add(optimization);
    }

    public int getImageMinBytesSize() {
        return this.imageMinBytesSize;
    }

    public void setImageMinBytesSize(int imageMinBytesSize) {
        this.imageMinBytesSize = imageMinBytesSize;
    }

    public boolean isFailIfOutputSizeNotReduced() {
        return this.failIfOutputSizeNotReduced;
    }

    public void setFailIfOutputSizeNotReduced(boolean failIfOutputSizeNotReduced) {
        this.failIfOutputSizeNotReduced = failIfOutputSizeNotReduced;
    }

    public boolean isPerformVisualComparisonChecks() {
        return this.performVisualComparisonChecks;
    }

    public void setPerformVisualComparisonChecks(boolean performVisualComparisonChecks) {
        this.performVisualComparisonChecks = performVisualComparisonChecks;
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.optimizations).append(this.imageQuality).append(this.imageMinBytesSize).append(this.failIfOutputSizeNotReduced).append(this.performVisualComparisonChecks).toHashCode();
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof OptimizeParameters)) {
            return false;
        }
        OptimizeParameters parameter = (OptimizeParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(getOptimizations(), parameter.getOptimizations()).append(getImageQuality(), parameter.getImageQuality()).append(getImageDpi(), parameter.getImageDpi()).append(getImageMinBytesSize(), parameter.getImageMinBytesSize()).append(isFailIfOutputSizeNotReduced(), parameter.isFailIfOutputSizeNotReduced()).append(isPerformVisualComparisonChecks(), parameter.isPerformVisualComparisonChecks()).isEquals();
    }
}
