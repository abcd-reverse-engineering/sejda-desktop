package org.sejda.model.parameter;

import jakarta.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.commons.collection.NullSafeSet;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/parameter/SetMetadataParameters.class */
public final class SetMetadataParameters extends MultiplePdfSourceMultipleOutputParameters {

    @NotEmpty
    private final Map<String, String> metadata = new HashMap();
    private final Set<String> toRemove = new NullSafeSet();
    private boolean updateCreatorProducerModifiedDate = true;
    private boolean removeAllMetadata = false;

    public Map<String, String> getMetadata() {
        return this.metadata;
    }

    public void putAll(Map<String, String> m) {
        this.metadata.putAll(m);
    }

    public void put(String key, String metadata) {
        this.metadata.put(key, metadata);
    }

    public void addFieldToRemove(String fieldName) {
        this.toRemove.add(fieldName);
    }

    public void addFieldsToRemove(Collection<String> fieldNames) {
        this.toRemove.addAll(fieldNames);
    }

    public Set<String> getToRemove() {
        return this.toRemove;
    }

    public boolean isUpdateProducerModifiedDate() {
        return this.updateCreatorProducerModifiedDate;
    }

    public void setUpdateCreatorProducerModifiedDate(Boolean updateCreatorProducerModifiedDate) {
        this.updateCreatorProducerModifiedDate = updateCreatorProducerModifiedDate.booleanValue();
    }

    public boolean isRemoveAllMetadata() {
        return this.removeAllMetadata;
    }

    public void setRemoveAllMetadata(Boolean removeAllMetadata) {
        this.removeAllMetadata = removeAllMetadata.booleanValue();
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.metadata).append(this.toRemove).append(this.updateCreatorProducerModifiedDate).append(this.removeAllMetadata).toHashCode();
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SetMetadataParameters)) {
            return false;
        }
        SetMetadataParameters parameter = (SetMetadataParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.metadata, parameter.metadata).append(this.toRemove, parameter.toRemove).append(this.updateCreatorProducerModifiedDate, parameter.updateCreatorProducerModifiedDate).append(this.removeAllMetadata, parameter.removeAllMetadata).isEquals();
    }
}
