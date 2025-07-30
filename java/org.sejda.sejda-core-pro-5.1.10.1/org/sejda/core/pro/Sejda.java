package org.sejda.core.pro;

import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Sejda {
   private static final Logger LOG = LoggerFactory.getLogger(Sejda.class);
   public static final String VERSION = (new SejdaVersionLoader()).getSejdaVersion();
   public static String CREATOR;

   private Sejda() {
   }

   static {
      CREATOR = "Sejda Pro " + VERSION + " (sejda.org)";
   }

   private static final class SejdaVersionLoader {
      private static final String SEJDA_PROPERTIES = "/sejda-pro.properties";
      private String sejdaVersion = "";

      private SejdaVersionLoader() {
         Properties props = new Properties();

         try {
            props.load(SejdaVersionLoader.class.getResourceAsStream("/sejda-pro.properties"));
            this.sejdaVersion = props.getProperty("sejda.pro.version", "UNKNOWN");
         } catch (IOException var3) {
            Sejda.LOG.warn("Unable to determine version of Sejda.", var3);
         }

      }

      String getSejdaVersion() {
         return this.sejdaVersion;
      }
   }
}
