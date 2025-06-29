package org.sejda.fonts;

import java.io.InputStream;
import org.sejda.model.pdf.font.FontResource;

/* loaded from: org.sejda.sejda-fonts-5.1.10.jar:org/sejda/fonts/UnicodeType0Font.class */
public enum UnicodeType0Font implements FontResource {
    NOTO_SANS_MERGED_REGULAR("/fonts/sans/NotoSansMerged-Regular.ttf"),
    NOTO_SANS_MERGED_BOLD("/fonts/sans/NotoSansMerged-Bold.ttf"),
    NOTO_SANS_BOLD("/fonts/sans/NotoSans-Bold.ttf");

    private final String resource;

    UnicodeType0Font(String resource) {
        this.resource = resource;
    }

    @Override // org.sejda.model.pdf.font.FontResource
    public String getResource() {
        return this.resource;
    }

    @Override // org.sejda.model.pdf.font.FontResource
    public InputStream getFontStream() {
        return getClass().getResourceAsStream(this.resource);
    }

    @Override // org.sejda.model.pdf.font.FontResource
    public Integer priority() {
        return Integer.MIN_VALUE;
    }
}
