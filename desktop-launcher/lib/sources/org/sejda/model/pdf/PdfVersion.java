package org.sejda.model.pdf;

import java.util.Objects;
import java.util.Optional;
import org.sejda.model.FriendlyNamed;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/pdf/PdfVersion.class */
public enum PdfVersion implements FriendlyNamed {
    VERSION_1_0(1.0d, "%PDF-1.0"),
    VERSION_1_1(1.1d, "%PDF-1.1"),
    VERSION_1_2(1.2d, "%PDF-1.2"),
    VERSION_1_3(1.3d, "%PDF-1.3"),
    VERSION_1_4(1.4d, "%PDF-1.4"),
    VERSION_1_5(1.5d, "%PDF-1.5"),
    VERSION_1_6(1.6d, "%PDF-1.6"),
    VERSION_1_7(1.7d, "%PDF-1.7"),
    VERSION_2_0(2.0d, "%PDF-2.0");

    private final double version;
    private final String versionHeader;
    private final String displayName;

    PdfVersion(double version, String versionHeader) {
        this.displayName = String.valueOf(version);
        this.version = version;
        this.versionHeader = versionHeader;
    }

    @Override // org.sejda.model.FriendlyNamed
    public String getFriendlyName() {
        return this.displayName;
    }

    public double getVersion() {
        return this.version;
    }

    public String getVersionString() {
        return String.valueOf(this.version);
    }

    public String getVersionHeader() {
        return this.versionHeader;
    }

    public static PdfVersion getMax(PdfVersion... pdfVersions) {
        PdfVersion max = null;
        for (PdfVersion current : pdfVersions) {
            if (Objects.nonNull(current) && (Objects.isNull(max) || max.compareTo(current) < 0)) {
                max = current;
            }
        }
        return (PdfVersion) Optional.ofNullable(max).orElse(VERSION_1_0);
    }

    public static PdfVersion getMax(MinRequiredVersion... items) {
        PdfVersion max = null;
        for (MinRequiredVersion current : items) {
            if (Objects.nonNull(current) && (Objects.isNull(max) || max.compareTo(current.getMinVersion()) < 0)) {
                max = current.getMinVersion();
            }
        }
        return (PdfVersion) Optional.ofNullable(max).orElse(VERSION_1_0);
    }
}
