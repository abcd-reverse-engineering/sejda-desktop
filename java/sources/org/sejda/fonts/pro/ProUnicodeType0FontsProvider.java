package org.sejda.fonts.pro;

import java.util.List;
import org.sejda.model.pdf.font.FontResource;
import org.sejda.model.pdf.font.Type0FontsProvider;

/* loaded from: org.sejda.sejda-fonts-pro-5.1.10.1.jar:org/sejda/fonts/pro/ProUnicodeType0FontsProvider.class */
public class ProUnicodeType0FontsProvider implements Type0FontsProvider {
    @Override // org.sejda.model.pdf.font.Type0FontsProvider
    public List<FontResource> getFonts() {
        return List.of(ProUnicodeType0Font.NOTO_SANS_ARMENIAN_REGULAR, ProUnicodeType0Font.NOTO_SANS_CJK_REGULAR);
    }
}
