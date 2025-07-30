package org.sejda.model.pro.split;

import org.sejda.model.FriendlyNamed;

public enum SplitDownTheMiddleMode implements FriendlyNamed {
   HORIZONTAL("horizontal"),
   VERTICAL("vertical"),
   AUTO("auto");

   private final String displayName;

   private SplitDownTheMiddleMode(String displayName) {
      this.displayName = displayName;
   }

   public String getFriendlyName() {
      return this.displayName;
   }

   // $FF: synthetic method
   private static SplitDownTheMiddleMode[] $values() {
      return new SplitDownTheMiddleMode[]{HORIZONTAL, VERTICAL, AUTO};
   }
}
