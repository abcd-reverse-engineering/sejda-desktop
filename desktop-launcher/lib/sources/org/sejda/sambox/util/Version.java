package org.sejda.sambox.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import org.sejda.sambox.SAMBox;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/util/Version.class */
public final class Version {
    private Version() {
    }

    public static String getVersion() throws IOException {
        try {
            InputStream stream = Version.class.getResourceAsStream(SAMBox.SAMBOX_PROPERTIES);
            try {
                if (Objects.nonNull(stream)) {
                    Properties properties = new Properties();
                    properties.load(new BufferedInputStream(stream));
                    String property = properties.getProperty("sambox.version", "");
                    if (stream != null) {
                        stream.close();
                    }
                    return property;
                }
                if (stream != null) {
                    stream.close();
                }
                return "";
            } finally {
            }
        } catch (IOException e) {
            return "";
        }
    }
}
