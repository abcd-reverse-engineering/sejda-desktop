package org.sejda.model.pro.nup;

import org.sejda.model.FriendlyNamed;

public enum PageOrder implements FriendlyNamed {
   HORIZONTAL("horizontal"),
   VERTICAL("vertical");

   private final String displayName;

   private PageOrder(String displayName) {
      this.displayName = displayName;
   }

   public String getFriendlyName() {
      return this.displayName;
   }

   // $FF: synthetic method
   private static PageOrder[] $values() {
      return new PageOrder[]{HORIZONTAL, VERTICAL};
   }
}
