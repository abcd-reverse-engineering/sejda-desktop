package org.sejda.model.pro.parameter;

import org.sejda.model.FriendlyNamed;

public enum AutoCropMode implements FriendlyNamed {
   AUTOMATIC_CONSISTENT("automatic_consistent"),
   AUTOMATIC_MAXIMUM("automatic_maximum"),
   NONE("none");

   private final String displayName;

   private AutoCropMode(String displayName) {
      this.displayName = displayName;
   }

   public String getFriendlyName() {
      return this.displayName;
   }

   // $FF: synthetic method
   private static AutoCropMode[] $values() {
      return new AutoCropMode[]{AUTOMATIC_CONSISTENT, AUTOMATIC_MAXIMUM, NONE};
   }
}
