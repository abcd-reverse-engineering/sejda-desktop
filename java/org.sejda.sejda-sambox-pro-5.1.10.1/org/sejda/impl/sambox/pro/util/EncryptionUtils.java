package org.sejda.impl.sambox.pro.util;

import java.util.Set;
import org.sejda.model.pdf.encryption.PdfAccessPermission;
import org.sejda.model.pro.parameter.EncryptParameters;
import org.sejda.model.pro.pdf.encryption.PdfEncryption;
import org.sejda.sambox.encryption.StandardSecurity;
import org.sejda.sambox.encryption.StandardSecurityEncryption;
import org.sejda.sambox.pdmodel.encryption.AccessPermission;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/util/EncryptionUtils.class */
public final class EncryptionUtils {
    private EncryptionUtils() {
    }

    public static StandardSecurity securityFromParams(EncryptParameters params) {
        return new StandardSecurity(params.getOwnerPassword(), params.getUserPassword(), getEncryptionFrom(params.getEncryptionAlgorithm()), getPermissionsFrom(params.getPermissions()), true);
    }

    private static AccessPermission getPermissionsFrom(Set<PdfAccessPermission> permissions) {
        int perm = -3584;
        for (PdfAccessPermission permission : permissions) {
            perm |= permission.bits;
        }
        return new AccessPermission(perm);
    }

    /* renamed from: org.sejda.impl.sambox.pro.util.EncryptionUtils$1, reason: invalid class name */
    /* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/util/EncryptionUtils$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$sejda$model$pro$pdf$encryption$PdfEncryption = new int[PdfEncryption.values().length];

        static {
            try {
                $SwitchMap$org$sejda$model$pro$pdf$encryption$PdfEncryption[PdfEncryption.STANDARD_ENC_128.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$sejda$model$pro$pdf$encryption$PdfEncryption[PdfEncryption.AES_ENC_128.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    private static StandardSecurityEncryption getEncryptionFrom(PdfEncryption encryptionAlgorithm) {
        switch (AnonymousClass1.$SwitchMap$org$sejda$model$pro$pdf$encryption$PdfEncryption[encryptionAlgorithm.ordinal()]) {
            case 1:
                return StandardSecurityEncryption.ARC4_128;
            case 2:
                return StandardSecurityEncryption.AES_128;
            default:
                return StandardSecurityEncryption.AES_256;
        }
    }
}
