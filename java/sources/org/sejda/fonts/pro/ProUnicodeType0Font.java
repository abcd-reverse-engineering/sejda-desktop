package org.sejda.fonts.pro;

import java.io.InputStream;
import org.sejda.model.pdf.font.FontResource;

/* loaded from: org.sejda.sejda-fonts-pro-5.1.10.1.jar:org/sejda/fonts/pro/ProUnicodeType0Font.class */
public enum ProUnicodeType0Font implements FontResource {
    NOTO_SANS_CJK_REGULAR("/fonts/pro/sans/NotoSansCJKtc-Regular.ttf"),
    NOTO_SANS_ARMENIAN_REGULAR("/fonts/pro/sans/NotoSansArmenian-Regular.ttf");

    private final String resource;

    ProUnicodeType0Font(String resource) {
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
}
