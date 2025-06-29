package org.sejda.model.pdf.encryption;

import org.sejda.model.FriendlyNamed;
import org.sejda.sambox.pdmodel.interactive.action.PDWindowsLaunchParams;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/pdf/encryption/PdfAccessPermission.class */
public enum PdfAccessPermission implements FriendlyNamed {
    PRINT(PDWindowsLaunchParams.OPERATION_PRINT, 4),
    MODIFY("modify", 8),
    COPY_AND_EXTRACT("copy", 16),
    ANNOTATION("modifyannotations", 32),
    FILL_FORMS("fill", PDAnnotation.FLAG_TOGGLE_NO_VIEW),
    EXTRACTION_FOR_DISABLES("screenreaders", 512),
    ASSEMBLE("assembly", 1024),
    DEGRADATED_PRINT("degradedprinting", 2048);

    private final String displayName;
    public final int bits;

    PdfAccessPermission(String displayName, int bits) {
        this.displayName = displayName;
        this.bits = bits;
    }

    @Override // org.sejda.model.FriendlyNamed
    public String getFriendlyName() {
        return this.displayName;
    }
}
