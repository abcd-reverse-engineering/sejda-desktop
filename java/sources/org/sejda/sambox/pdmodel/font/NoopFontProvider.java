package org.sejda.sambox.pdmodel.font;

import java.util.ArrayList;
import java.util.List;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/NoopFontProvider.class */
public class NoopFontProvider extends FontProvider {
    @Override // org.sejda.sambox.pdmodel.font.FontProvider
    public List<? extends FontInfo> getFontInfo() {
        return new ArrayList();
    }
}
