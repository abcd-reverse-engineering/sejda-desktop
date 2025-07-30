package org.sejda.fonts.pro;

import java.util.List;
import org.sejda.model.pdf.font.FontResource;
import org.sejda.model.pdf.font.Type0FontsProvider;

public class ProUnicodeType0FontsProvider implements Type0FontsProvider {
   public List<FontResource> getFonts() {
      return List.of(ProUnicodeType0Font.NOTO_SANS_ARMENIAN_REGULAR, ProUnicodeType0Font.NOTO_SANS_CJK_REGULAR);
   }
}
