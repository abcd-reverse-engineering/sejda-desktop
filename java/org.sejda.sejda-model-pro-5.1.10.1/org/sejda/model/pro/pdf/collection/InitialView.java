package org.sejda.model.pro.pdf.collection;

import org.sejda.model.FriendlyNamed;

public enum InitialView implements FriendlyNamed {
   DETAILS("details", "D"),
   TILES("tiles", "T"),
   HIDDEN("hidden", "H");

   private final String displayName;
   public final String value;

   private InitialView(String displayName, String value) {
      this.displayName = displayName;
      this.value = value;
   }

   public String getFriendlyName() {
      return this.displayName;
   }

   // $FF: synthetic method
   private static InitialView[] $values() {
      return new InitialView[]{DETAILS, TILES, HIDDEN};
   }
}
