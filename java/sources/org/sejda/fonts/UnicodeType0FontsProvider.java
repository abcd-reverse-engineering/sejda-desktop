package org.sejda.fonts;

import java.util.List;
import org.sejda.model.pdf.font.FontResource;
import org.sejda.model.pdf.font.Type0FontsProvider;

/* loaded from: org.sejda.sejda-fonts-5.1.10.jar:org/sejda/fonts/UnicodeType0FontsProvider.class */
public class UnicodeType0FontsProvider implements Type0FontsProvider {
    @Override // org.sejda.model.pdf.font.Type0FontsProvider
    public List<FontResource> getFonts() {
        return List.of(UnicodeType0Font.NOTO_SANS_MERGED_REGULAR, UnicodeType0Font.NOTO_SANS_MERGED_BOLD, UnicodeType0Font.NOTO_SANS_BOLD);
    }
}
