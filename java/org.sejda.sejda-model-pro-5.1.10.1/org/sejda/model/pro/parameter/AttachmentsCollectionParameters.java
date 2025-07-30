package org.sejda.model.pro.parameter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.input.Source;
import org.sejda.model.output.SingleTaskOutput;
import org.sejda.model.parameter.base.AbstractPdfOutputParameters;
import org.sejda.model.parameter.base.MultipleSourceTaskParameter;
import org.sejda.model.parameter.base.SingleOutputTaskParameters;
import org.sejda.model.pro.pdf.collection.InitialView;
import org.sejda.model.validation.constraint.SingleOutputAllowedExtensions;

@SingleOutputAllowedExtensions
public class AttachmentsCollectionParameters extends AbstractPdfOutputParameters implements SingleOutputTaskParameters, MultipleSourceTaskParameter {
   private @NotNull InitialView initialView;
   private final @NotEmpty @Valid List<Source<?>> sourceList;
   private @Valid @NotNull SingleTaskOutput output;

   public AttachmentsCollectionParameters() {
      this.initialView = InitialView.TILES;
      this.sourceList = new ArrayList();
   }

   public void addSources(Collection<? extends Source<?>> inputs) {
      this.sourceList.addAll(inputs);
   }

   public void addSource(Source<?> input) {
      this.sourceList.add(input);
   }

   public List<Source<?>> getSourceList() {
      return Collections.unmodifiableList(this.sourceList);
   }

   public InitialView getInitialView() {
      return this.initialView;
   }

   public void setInitialView(InitialView initialView) {
      this.initialView = initialView;
   }

   public SingleTaskOutput getOutput() {
      return this.output;
   }

   public void setOutput(SingleTaskOutput output) {
      this.output = output;
   }

   public int hashCode() {
      return (new HashCodeBuilder()).appendSuper(super.hashCode()).append(this.initialView).append(this.output).append(this.sourceList).toHashCode();
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (other instanceof AttachmentsCollectionParameters) {
         AttachmentsCollectionParameters parameter = (AttachmentsCollectionParameters)other;
         return (new EqualsBuilder()).appendSuper(super.equals(other)).append(this.initialView, parameter.getInitialView()).append(this.output, parameter.output).append(this.sourceList, parameter.getSourceList()).isEquals();
      } else {
         return false;
      }
   }
}
