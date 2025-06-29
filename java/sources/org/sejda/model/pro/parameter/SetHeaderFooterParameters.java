package org.sejda.model.pro.parameter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.awt.Color;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
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

/* loaded from: org.sejda.sejda-model-pro-5.1.10.1.jar:org/sejda/model/pro/parameter/SetHeaderFooterParameters.class */
public class SetHeaderFooterParameters extends MultiplePdfSourceMultipleOutputParameters implements PageRangeSelection, PagesSelection {

    @NotNull
    private String pattern;
    private Integer pageCountStartFrom;
    private BatesSequence batesSequence;

    @NotNull
    @Valid
    private Set<PageRange> pageRanges = new HashSet();

    @NotNull
    private PredefinedSetOfPages predefinedSetOfPages = PredefinedSetOfPages.NONE;
    private StandardType1Font font = StandardType1Font.HELVETICA;
    private HorizontalAlign horizontalAlign = HorizontalAlign.CENTER;
    private VerticalAlign verticalAlign = VerticalAlign.BOTTOM;

    @Positive
    private double fontSize = 10.0d;

    @NotNull
    private Color color = Color.BLACK;
    private int fileCountStartFrom = 1;
    private boolean addMargins = false;

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
        this.pageCountStartFrom = Integer.valueOf(pageCountStartFrom);
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
        return Integer.valueOf(this.fileCountStartFrom);
    }

    public void setFileCountStartFrom(Integer fileCountStartFrom) {
        this.fileCountStartFrom = fileCountStartFrom.intValue();
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

    @Override // org.sejda.model.pdf.page.PageRangeSelection
    public Set<PageRange> getPageSelection() {
        return Collections.unmodifiableSet(this.pageRanges);
    }

    public Set<PageRange> getPageRanges() {
        return this.pageRanges;
    }

    @Override // org.sejda.model.pdf.page.PagesSelection
    public SortedSet<Integer> getPages(int upperLimit) {
        if (this.predefinedSetOfPages != PredefinedSetOfPages.NONE) {
            return this.predefinedSetOfPages.getPages(upperLimit);
        }
        SortedSet<Integer> retSet = new TreeSet<>();
        for (PageRange range : getPageSelection()) {
            retSet.addAll(range.getPages(upperLimit));
        }
        return retSet;
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.font).append(this.horizontalAlign).append(this.verticalAlign).append(this.fontSize).append(this.pageRanges).append(this.pattern).append(this.batesSequence).append(this.pageCountStartFrom).append(this.color).append(this.fileCountStartFrom).append(this.addMargins).append(this.predefinedSetOfPages).toHashCode();
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SetHeaderFooterParameters)) {
            return false;
        }
        SetHeaderFooterParameters parameter = (SetHeaderFooterParameters) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(getFont(), parameter.getFont()).append(getHorizontalAlign(), parameter.getHorizontalAlign()).append(getVerticalAlign(), parameter.getVerticalAlign()).append(getBatesSequence(), parameter.getBatesSequence()).append(getPageCountStartFrom(), parameter.getPageCountStartFrom()).append(getFontSize(), parameter.getFontSize()).append(getPageRanges(), parameter.getPageRanges()).append(getPattern(), parameter.getPattern()).append(getColor(), parameter.getColor()).append(getFileCountStartFrom(), parameter.getFileCountStartFrom()).append(isAddMargins(), parameter.isAddMargins()).append(getPredefinedSetOfPages(), parameter.getPredefinedSetOfPages()).isEquals();
    }
}
