package org.sejda.impl.sambox.component.font;

import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.font.PDFont;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/font/FallbackFontsProvider.class */
public interface FallbackFontsProvider {
    PDFont findFallbackFont(PDFont pDFont, PDDocument pDDocument, String str, boolean z);

    default int getPriority() {
        return 0;
    }
}
