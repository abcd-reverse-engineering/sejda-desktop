package org.sejda.model.parameter.base;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.output.SingleOrMultipleTaskOutput;
import org.sejda.model.util.IOUtils;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/base/MultiplePdfSourceMultipleOutputParameters.class */
public class MultiplePdfSourceMultipleOutputParameters extends MultiplePdfSourceParameters implements SingleOrMultipleOutputTaskParameters {

    @Valid
    @NotNull
    private SingleOrMultipleTaskOutput output;
    private String outputPrefix = "";
    private final List<String> specificResultFilenames = new ArrayList();

    @Override // org.sejda.model.parameter.base.PrefixableTaskParameters
    public String getOutputPrefix() {
        return this.outputPrefix;
    }

    @Override // org.sejda.model.parameter.base.PrefixableTaskParameters
    public void setOutputPrefix(String outputPrefix) {
        this.outputPrefix = outputPrefix;
    }

    @Override // org.sejda.model.parameter.base.TaskParameters, org.sejda.model.parameter.base.SingleOutputTaskParameters
    public SingleOrMultipleTaskOutput getOutput() {
        return this.output;
    }

    @Override // org.sejda.model.parameter.base.SingleOrMultipleOutputTaskParameters
    public void setOutput(SingleOrMultipleTaskOutput output) {
        this.output = output;
    }

    public void addSpecificResultFilename(String filename) {
        this.specificResultFilenames.add(filename);
    }

    public void addSpecificResultFilenames(Collection<String> filenames) {
        this.specificResultFilenames.addAll(filenames);
    }

    public List<String> getSpecificResultFilenames() {
        return Collections.unmodifiableList(this.specificResultFilenames);
    }

    public String getSpecificResultFilename(int fileNumber) {
        return getSpecificResultFilename(fileNumber, ".pdf");
    }

    public String getSpecificResultFilename(int fileNumber, String expectedExtension) {
        if (this.specificResultFilenames.size() >= fileNumber) {
            return (String) Optional.ofNullable(this.specificResultFilenames.get(fileNumber - 1)).filter((v0) -> {
                return StringUtils.isNotBlank(v0);
            }).map(IOUtils::toSafeFilename).map(n -> {
                String ext = StringUtils.defaultString(expectedExtension).toLowerCase();
                if (!n.toLowerCase().endsWith(ext)) {
                    return n + ext;
                }
                return n;
            }).map(IOUtils::shortenFilename).orElse(null);
        }
        return null;
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.outputPrefix).append(this.output).append(this.specificResultFilenames).toHashCode();
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MultiplePdfSourceMultipleOutputParameters)) {
            return false;
        }
        MultiplePdfSourceMultipleOutputParameters parameter = (MultiplePdfSourceMultipleOutputParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.outputPrefix, parameter.outputPrefix).append(this.output, parameter.output).append(this.specificResultFilenames, parameter.specificResultFilenames).isEquals();
    }
}
