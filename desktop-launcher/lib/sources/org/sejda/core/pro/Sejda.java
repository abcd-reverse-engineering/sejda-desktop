package org.sejda.core.pro;

import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-core-pro-5.1.10.1.jar:org/sejda/core/pro/Sejda.class */
public final class Sejda {
    private static final Logger LOG = LoggerFactory.getLogger(Sejda.class);
    public static final String VERSION = new SejdaVersionLoader().getSejdaVersion();
    public static String CREATOR = "Sejda Pro " + VERSION + " (sejda.org)";

    private Sejda() {
    }

    /* loaded from: org.sejda.sejda-core-pro-5.1.10.1.jar:org/sejda/core/pro/Sejda$SejdaVersionLoader.class */
    private static final class SejdaVersionLoader {
        private static final String SEJDA_PROPERTIES = "/sejda-pro.properties";
        private String sejdaVersion;

        private SejdaVersionLoader() throws IOException {
            this.sejdaVersion = "";
            Properties props = new Properties();
            try {
                props.load(SejdaVersionLoader.class.getResourceAsStream(SEJDA_PROPERTIES));
                this.sejdaVersion = props.getProperty("sejda.pro.version", "UNKNOWN");
            } catch (IOException e) {
                Sejda.LOG.warn("Unable to determine version of Sejda.", e);
            }
        }

        String getSejdaVersion() {
            return this.sejdaVersion;
        }
    }
}
