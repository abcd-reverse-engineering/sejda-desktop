package org.sejda.model.pro.parameter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.awt.Color;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.HorizontalAlign;
import org.sejda.model.VerticalAlign;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;
import org.sejda.model.pdf.StandardType1Font;
import org.sejda.model.pdf.page.PageRange;
import org.sejda.model.pdf.page.PageRangeSelection;
import org.sejda.model.pdf.page.PagesSelection;
import org.sejda.model.pdf.page.PredefinedSetOfPages;
import org.sejda.model.pro.pdf.numbering.BatesSequence;

public class SetHeaderFooterParameters extends MultiplePdfSourceMultipleOutputParameters implements PageRangeSelection, PagesSelection {
   private @NotNull @Valid Set<PageRange> pageRanges = new HashSet();
   private @NotNull PredefinedSetOfPages predefinedSetOfPages;
   private StandardType1Font font;
   private HorizontalAlign horizontalAlign;
   private VerticalAlign verticalAlign;
   private @Positive double fontSize;
   private @NotNull String pattern;
   private Integer pageCountStartFrom;
   private BatesSequence batesSequence;
   private @NotNull Color color;
   private int fileCountStartFrom;
   private boolean addMargins;

   public SetHeaderFooterParameters() {
      this.predefinedSetOfPages = PredefinedSetOfPages.NONE;
      this.font = StandardType1Font.HELVETICA;
      this.horizontalAlign = HorizontalAlign.CENTER;
      this.verticalAlign = VerticalAlign.BOTTOM;
      this.fontSize = 10.0;
      this.color = Color.BLACK;
      this.fileCountStartFrom = 1;
      this.addMargins = false;
   }

   public StandardType1Font getFont() {
      return this.font;
   }

   public void setFont(StandardType1Font font) {
      this.font = font;
   }

   public HorizontalAlign getHorizontalAlign() {
      return this.horizontalAlign;
   }

   public void setHorizontalAlign(HorizontalAlign align) {
      this.horizontalAlign = align;
   }

   public VerticalAlign getVerticalAlign() {
      return this.verticalAlign;
   }

   public void setVerticalAlign(VerticalAlign verticalAlign) {
      this.verticalAlign = verticalAlign;
   }

   public double getFontSize() {
      return this.fontSize;
   }

   public String getPattern() {
      return this.pattern;
   }

   public void setPattern(String pattern) {
      this.pattern = pattern;
   }

   public Integer getPageCountStartFrom() {
      return this.pageCountStartFrom;
   }

   public void setPageCountStartFrom(int pageCountStartFrom) {
      this.pageCountStartFrom = pageCountStartFrom;
   }

   public BatesSequence getBatesSequence() {
      return this.batesSequence;
   }

   public void setBatesSequence(BatesSequence batesSequence) {
      this.batesSequence = batesSequence;
   }

   public void setFontSize(double fontSize) {
      this.fontSize = fontSize;
   }

   public Color getColor() {
      return this.color;
   }

   public void setColor(Color color) {
      this.color = color;
   }

   public Integer getFileCountStartFrom() {
      return this.fileCountStartFrom;
   }

   public void setFileCountStartFrom(Integer fileCountStartFrom) {
      this.fileCountStartFrom = fileCountStartFrom;
   }

   public boolean isAddMargins() {
      return this.addMargins;
   }

   public void setAddMargins(boolean addMargins) {
      this.addMargins = addMargins;
   }

   public PredefinedSetOfPages getPredefinedSetOfPages() {
      return this.predefinedSetOfPages;
   }

   public void setPredefinedSetOfPages(PredefinedSetOfPages predefinedSetOfPages) {
      this.predefinedSetOfPages = predefinedSetOfPages;
   }

   public void addPageRange(PageRange range) {
      this.pageRanges.add(range);
   }

   public void addAllPageRanges(Collection<PageRange> ranges) {
      ranges.forEach(this::addPageRange);
   }

   public Set<PageRange> getPageSelection() {
      return Collections.unmodifiableSet(this.pageRanges);
   }

   public Set<PageRange> getPageRanges() {
      return this.pageRanges;
   }

   public SortedSet<Integer> getPages(int upperLimit) {
      if (this.predefinedSetOfPages != PredefinedSetOfPages.NONE) {
         return this.predefinedSetOfPages.getPages(upperLimit);
      } else {
         SortedSet<Integer> retSet = new TreeSet();
         Iterator var3 = this.getPageSelection().iterator();

         while(var3.hasNext()) {
            PageRange range = (PageRange)var3.next();
            retSet.addAll(range.getPages(upperLimit));
         }

         return retSet;
      }
   }

   public int hashCode() {
      return (new HashCodeBuilder()).appendSuper(super.hashCode()).append(this.font).append(this.horizontalAlign).append(this.verticalAlign).append(this.fontSize).append(this.pageRanges).append(this.pattern).append(this.batesSequence).append(this.pageCountStartFrom).append(this.color).append(this.fileCountStartFrom).append(this.addMargins).append(this.predefinedSetOfPages).toHashCode();
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (other instanceof SetHeaderFooterParameters) {
         SetHeaderFooterParameters parameter = (SetHeaderFooterParameters)other;
         return (new EqualsBuilder()).appendSuper(super.equals(other)).append(this.getFont(), parameter.getFont()).append(this.getHorizontalAlign(), parameter.getHorizontalAlign()).append(this.getVerticalAlign(), parameter.getVerticalAlign()).append(this.getBatesSequence(), parameter.getBatesSequence()).append(this.getPageCountStartFrom(), parameter.getPageCountStartFrom()).append(this.getFontSize(), parameter.getFontSize()).append(this.getPageRanges(), parameter.getPageRanges()).append(this.getPattern(), parameter.getPattern()).append(this.getColor(), parameter.getColor()).append(this.getFileCountStartFrom(), parameter.getFileCountStartFrom()).append(this.isAddMargins(), parameter.isAddMargins()).append(this.getPredefinedSetOfPages(), parameter.getPredefinedSetOfPages()).isEquals();
      } else {
         return false;
      }
   }
}
