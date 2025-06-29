package org.sejda.model.parameter;

import jakarta.validation.constraints.NotNull;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;
import org.sejda.model.pdf.MinRequiredVersion;
import org.sejda.model.pdf.PdfVersion;
import org.sejda.model.pdf.viewerpreference.PdfBooleanPreference;
import org.sejda.model.pdf.viewerpreference.PdfDirection;
import org.sejda.model.pdf.viewerpreference.PdfDuplex;
import org.sejda.model.pdf.viewerpreference.PdfNonFullScreenPageMode;
import org.sejda.model.pdf.viewerpreference.PdfPageLayout;
import org.sejda.model.pdf.viewerpreference.PdfPageMode;
import org.sejda.model.pdf.viewerpreference.PdfPrintScaling;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/ViewerPreferencesParameters.class */
public class ViewerPreferencesParameters extends MultiplePdfSourceMultipleOutputParameters {
    private PdfDuplex duplex;
    private PdfDirection direction;
    private PdfPrintScaling printScaling;

    @NotNull
    private PdfPageMode pageMode = PdfPageMode.USE_NONE;

    @NotNull
    private PdfPageLayout pageLayout = PdfPageLayout.SINGLE_PAGE;

    @NotNull
    private PdfNonFullScreenPageMode nfsMode = PdfNonFullScreenPageMode.USE_NONE;
    private Set<PdfBooleanPreference> enabledBooleanPreferences = EnumSet.noneOf(PdfBooleanPreference.class);

    public boolean addEnabledPreference(PdfBooleanPreference e) {
        return this.enabledBooleanPreferences.add(e);
    }

    public void clearEnabledPreferences() {
        this.enabledBooleanPreferences.clear();
    }

    public PdfPageMode getPageMode() {
        return this.pageMode;
    }

    public void setPageMode(PdfPageMode pageMode) {
        this.pageMode = pageMode;
    }

    public PdfPageLayout getPageLayout() {
        return this.pageLayout;
    }

    public void setPageLayout(PdfPageLayout pageLayout) {
        this.pageLayout = pageLayout;
    }

    public PdfNonFullScreenPageMode getNfsMode() {
        return this.nfsMode;
    }

    public void setNfsMode(PdfNonFullScreenPageMode nfsMode) {
        this.nfsMode = nfsMode;
    }

    public PdfDuplex getDuplex() {
        return this.duplex;
    }

    public void setDuplex(PdfDuplex duplex) {
        this.duplex = duplex;
    }

    public PdfDirection getDirection() {
        return this.direction;
    }

    public void setDirection(PdfDirection direction) {
        this.direction = direction;
    }

    public PdfPrintScaling getPrintScaling() {
        return this.printScaling;
    }

    public void setPrintScaling(PdfPrintScaling printScaling) {
        this.printScaling = printScaling;
    }

    public Set<PdfBooleanPreference> getEnabledPreferences() {
        return Collections.unmodifiableSet(this.enabledBooleanPreferences);
    }

    @Override // org.sejda.model.parameter.base.AbstractPdfOutputParameters
    public PdfVersion getMinRequiredPdfVersion() {
        return PdfVersion.getMax(super.getMinRequiredPdfVersion(), PdfVersion.getMax(this.printScaling, this.direction, this.duplex, this.pageLayout, this.pageMode), PdfVersion.getMax((MinRequiredVersion[]) this.enabledBooleanPreferences.toArray(new MinRequiredVersion[this.enabledBooleanPreferences.size()])));
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.printScaling).append(this.direction).append(this.duplex).append(this.pageLayout).append(this.pageMode).append(this.nfsMode).append(this.enabledBooleanPreferences).toHashCode();
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ViewerPreferencesParameters)) {
            return false;
        }
        ViewerPreferencesParameters parameter = (ViewerPreferencesParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.printScaling, parameter.getPrintScaling()).append(this.direction, parameter.getDirection()).append(this.duplex, parameter.getDuplex()).append(this.pageLayout, parameter.getPageLayout()).append(this.pageMode, parameter.getPageMode()).append(this.nfsMode, parameter.getNfsMode()).append(this.enabledBooleanPreferences, parameter.getEnabledPreferences()).isEquals();
    }
}
