package org.sejda.impl.sambox.component;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/OutlineItem.class */
public class OutlineItem {
    public final String title;
    public final int page;
    public final int level;
    public final boolean xyzDestination;

    public OutlineItem(String title, int page, int level, boolean xyzDestination) {
        this.title = title;
        this.page = page;
        this.level = level;
        this.xyzDestination = xyzDestination;
    }
}
