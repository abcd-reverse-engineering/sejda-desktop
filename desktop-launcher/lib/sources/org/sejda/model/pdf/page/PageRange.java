package org.sejda.model.pdf.page;

import jakarta.validation.constraints.Min;
import java.util.SortedSet;
import java.util.TreeSet;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.validation.constraint.EndGreaterThenOrEqualToStart;

@EndGreaterThenOrEqualToStart
/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/pdf/page/PageRange.class */
public class PageRange implements PagesSelection {
    private static final int UNBOUNDED_END = Integer.MAX_VALUE;

    @Min(1)
    private int start;

    @Min(1)
    private int end;

    PageRange() {
    }

    public PageRange(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public PageRange(int start) {
        this.start = start;
        this.end = UNBOUNDED_END;
    }

    public int getStart() {
        return this.start;
    }

    public int getEnd() {
        return this.end;
    }

    public boolean isUnbounded() {
        return this.end == UNBOUNDED_END;
    }

    public boolean intersects(PageRange range) {
        return (range.getStart() >= this.start && range.getStart() <= this.end) || (range.getEnd() >= this.start && range.getEnd() <= this.end);
    }

    @Override // org.sejda.model.pdf.page.PagesSelection
    public SortedSet<Integer> getPages(int totalNumberOfPage) {
        SortedSet<Integer> retSet = new TreeSet<>();
        for (int i = this.start; i <= totalNumberOfPage && i <= this.end; i++) {
            retSet.add(Integer.valueOf(i));
        }
        return retSet;
    }

    public boolean contains(int page) {
        return this.start <= page && this.end >= page;
    }

    public String toString() {
        if (isUnbounded()) {
            return String.format("%s-", Integer.valueOf(this.start));
        }
        if (this.start == this.end) {
            return String.format("%s", Integer.valueOf(this.start));
        }
        return String.format("%s-%s", Integer.valueOf(this.start), Integer.valueOf(this.end));
    }

    public int hashCode() {
        return new HashCodeBuilder().append(this.start).append(this.end).toHashCode();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PageRange)) {
            return false;
        }
        PageRange range = (PageRange) other;
        return new EqualsBuilder().append(this.start, range.getStart()).append(this.end, range.getEnd()).isEquals();
    }

    public static PageRange one(int page) {
        return new PageRange(page, page);
    }
}
