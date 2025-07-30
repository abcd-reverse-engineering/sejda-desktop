package org.sejda.model.pro.pdf.encryption;

import org.sejda.model.FriendlyNamed;
import org.sejda.model.pdf.MinRequiredVersion;
import org.sejda.model.pdf.PdfVersion;

public enum PdfEncryption implements MinRequiredVersion, FriendlyNamed {
   STANDARD_ENC_128("rc4_128", PdfVersion.VERSION_1_2),
   AES_ENC_128("aes_128", PdfVersion.VERSION_1_6),
   AES_ENC_256("aes_256", PdfVersion.VERSION_1_7);

   private final PdfVersion minVersion;
   private final String displayName;

   private PdfEncryption(String displayName, PdfVersion minVersion) {
      this.displayName = displayName;
      this.minVersion = minVersion;
   }

   public PdfVersion getMinVersion() {
      return this.minVersion;
   }

   public String getFriendlyName() {
      return this.displayName;
   }

   // $FF: synthetic method
   private static PdfEncryption[] $values() {
      return new PdfEncryption[]{STANDARD_ENC_128, AES_ENC_128, AES_ENC_256};
   }
}
