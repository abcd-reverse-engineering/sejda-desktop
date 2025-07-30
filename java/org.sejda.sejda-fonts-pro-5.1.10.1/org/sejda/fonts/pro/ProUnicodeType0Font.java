package org.sejda.fonts.pro;

import java.io.InputStream;
import org.sejda.model.pdf.font.FontResource;

public enum ProUnicodeType0Font implements FontResource {
   NOTO_SANS_CJK_REGULAR("/fonts/pro/sans/NotoSansCJKtc-Regular.ttf"),
   NOTO_SANS_ARMENIAN_REGULAR("/fonts/pro/sans/NotoSansArmenian-Regular.ttf");

   private final String resource;

   private ProUnicodeType0Font(String resource) {
      this.resource = resource;
   }

   public String getResource() {
      return this.resource;
   }

   public InputStream getFontStream() {
      return this.getClass().getResourceAsStream(this.resource);
   }

   // $FF: synthetic method
   private static ProUnicodeType0Font[] $values() {
      return new ProUnicodeType0Font[]{NOTO_SANS_CJK_REGULAR, NOTO_SANS_ARMENIAN_REGULAR};
   }
}
