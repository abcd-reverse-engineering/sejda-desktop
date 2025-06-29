package org.sejda.model.parameter;

import jakarta.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.parameter.base.SinglePdfSourceSingleOutputParameters;
import org.sejda.model.pdf.transition.PdfPageTransition;
import org.sejda.model.validation.constraint.HasTransitions;
import org.sejda.model.validation.constraint.SingleOutputAllowedExtensions;

@SingleOutputAllowedExtensions
@HasTransitions
/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/SetPagesTransitionParameters.class */
public class SetPagesTransitionParameters extends SinglePdfSourceSingleOutputParameters {

    @Valid
    private PdfPageTransition defaultTransition;

    @Valid
    private final Map<Integer, PdfPageTransition> transitions = new HashMap();
    private boolean fullScreen = false;

    public SetPagesTransitionParameters() {
    }

    public SetPagesTransitionParameters(PdfPageTransition defaultTransition) {
        this.defaultTransition = defaultTransition;
    }

    public boolean isFullScreen() {
        return this.fullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
    }

    public PdfPageTransition putTransition(Integer page, PdfPageTransition transition) {
        return this.transitions.put(page, transition);
    }

    public void clearTransitions() {
        this.transitions.clear();
    }

    public Map<Integer, PdfPageTransition> getTransitions() {
        return Collections.unmodifiableMap(this.transitions);
    }

    public PdfPageTransition getDefaultTransition() {
        return this.defaultTransition;
    }

    public PdfPageTransition getOrDefault(int page) {
        return this.transitions.getOrDefault(Integer.valueOf(page), this.defaultTransition);
    }

    @Override // org.sejda.model.parameter.base.SinglePdfSourceSingleOutputParameters, org.sejda.model.parameter.base.SinglePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.transitions).append(this.defaultTransition).append(this.fullScreen).toHashCode();
    }

    @Override // org.sejda.model.parameter.base.SinglePdfSourceSingleOutputParameters, org.sejda.model.parameter.base.SinglePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SetPagesTransitionParameters)) {
            return false;
        }
        SetPagesTransitionParameters parameter = (SetPagesTransitionParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.transitions, parameter.getTransitions()).append(this.defaultTransition, parameter.getDefaultTransition()).append(this.fullScreen, parameter.isFullScreen()).isEquals();
    }
}
