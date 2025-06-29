package org.sejda.core;

import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/Sejda.class */
public final class Sejda {
    public static final String UNETHICAL_READ_PROPERTY_NAME = "sejda.unethical.read";
    public static final String USER_CONFIG_FILE_PROPERTY_NAME = "sejda.config.file";
    public static final String PERFORM_SCHEMA_VALIDATION_PROPERTY_NAME = "sejda.perform.schema.validation";
    public static final String PERFORM_MEMORY_OPTIMIZATIONS_PROPERTY_NAME = "sejda.perform.memory.optimizations";
    public static final String PERFORM_EAGER_ASSERTIONS_PROPERTY_NAME = "sejda.perform.eager.assertions";
    private static final Logger LOG = LoggerFactory.getLogger(Sejda.class);
    public static final String VERSION = new SejdaVersionLoader().getSejdaVersion();
    public static String CREATOR = "Sejda " + VERSION + " (www.sejda.org)";

    private Sejda() {
    }

    /* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/Sejda$SejdaVersionLoader.class */
    private static final class SejdaVersionLoader {
        private static final String SEJDA_PROPERTIES = "/sejda.properties";
        private String sejdaVersion;

        private SejdaVersionLoader() throws IOException {
            this.sejdaVersion = "";
            Properties props = new Properties();
            try {
                props.load(SejdaVersionLoader.class.getResourceAsStream(SEJDA_PROPERTIES));
                this.sejdaVersion = props.getProperty("sejda.version", "UNKNOWN");
            } catch (IOException e) {
                Sejda.LOG.warn("Unable to determine version of Sejda.", e);
            }
        }

        String getSejdaVersion() {
            return this.sejdaVersion;
        }
    }
}
