package org.sejda.model.pro;

import org.sejda.model.FriendlyNamed;
import org.sejda.model.PageSize;

/* loaded from: org.sejda.sejda-model-pro-5.1.10.1.jar:org/sejda/model/pro/PaperSize.class */
public enum PaperSize implements FriendlyNamed {
    A0(PageSize.A0),
    A1(PageSize.A1),
    A2(PageSize.A2),
    A3(PageSize.A3),
    A4(PageSize.A4),
    A5(PageSize.A5),
    A6(PageSize.A6),
    EXECUTIVE(PageSize.EXECUTIVE),
    LEDGER(PageSize.LEDGER),
    LEGAL(PageSize.LEGAL),
    LETTER(PageSize.LETTER),
    TABLOID(PageSize.TABLOID);

    private final PageSize size;

    PaperSize(PageSize size) {
        this.size = size;
    }

    @Override // org.sejda.model.FriendlyNamed
    public String getFriendlyName() {
        return this.size.getName();
    }

    public PageSize getSize() {
        return this.size;
    }
}
