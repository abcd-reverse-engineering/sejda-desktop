package org.sejda.sambox.encryption;

import org.sejda.sambox.cos.COSObjectKey;
import org.sejda.sambox.cos.COSVisitor;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/encryption/GeneralEncryptionAlgorithm.class */
public interface GeneralEncryptionAlgorithm extends COSVisitor {
    void setCurrentCOSObjectKey(COSObjectKey cOSObjectKey);
}
