package org.sejda.sambox.util;

import java.util.regex.Pattern;
import org.sejda.commons.util.RequireUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/util/SpecVersionUtils.class */
public class SpecVersionUtils {
    public static final String PDF_HEADER = "%PDF-";
    public static final String V1_0 = "1.0";
    public static final String V1_1 = "1.1";
    public static final String V1_2 = "1.2";
    public static final String V1_3 = "1.3";
    public static final String V1_4 = "1.4";
    public static final String V1_5 = "1.5";
    public static final String V1_6 = "1.6";
    public static final String V1_7 = "1.7";
    public static final String V2_0 = "2.0";
    private static final Logger LOG = LoggerFactory.getLogger(SpecVersionUtils.class);
    private static final Pattern VERSION_PATTERN = Pattern.compile("^(\\d)\\.(\\d)$");

    private SpecVersionUtils() {
    }

    public static String parseHeaderString(String header) {
        String versionNumberPart = header.toUpperCase().replaceAll(Pattern.quote(PDF_HEADER), "").trim();
        if (versionNumberPart.length() > 3) {
            versionNumberPart = versionNumberPart.substring(0, 3);
        }
        String version = sanitizeVersion(versionNumberPart);
        if (!VERSION_PATTERN.matcher(version).matches()) {
            LOG.warn("Invalid header version {}, falling back to {}", version, V1_6);
            return V1_6;
        }
        return version;
    }

    private static String sanitizeVersion(String version) {
        return version.replace(',', '.');
    }

    public static boolean isAtLeast(String version, String atLeast) {
        RequireUtils.requireNotNullArg(version, "Cannot compare a null version");
        RequireUtils.requireNotNullArg(atLeast, "Cannot compare a null version");
        return version.compareTo(atLeast) >= 0;
    }
}
