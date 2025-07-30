package org.sejda.model.pro.watermark;

import org.sejda.model.FriendlyNamed;

public enum Location implements FriendlyNamed {
   BEHIND("behind"),
   OVER("over");

   private final String displayName;

   private Location(String displayName) {
      this.displayName = displayName;
   }

   public String getFriendlyName() {
      return this.displayName;
   }

   // $FF: synthetic method
   private static Location[] $values() {
      return new Location[]{BEHIND, OVER};
   }
}
