package org.sejda.sambox.pdmodel.encryption;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/encryption/PublicKeyProtectionPolicy.class */
public final class PublicKeyProtectionPolicy extends ProtectionPolicy {
    public final List<PublicKeyRecipient> recipients = new ArrayList();
    private X509Certificate decryptionCertificate;

    public void addRecipient(PublicKeyRecipient recipient) {
        this.recipients.add(recipient);
    }

    public boolean removeRecipient(PublicKeyRecipient recipient) {
        return this.recipients.remove(recipient);
    }

    public Iterator<PublicKeyRecipient> getRecipientsIterator() {
        return this.recipients.iterator();
    }

    public X509Certificate getDecryptionCertificate() {
        return this.decryptionCertificate;
    }

    public void setDecryptionCertificate(X509Certificate decryptionCertificate) {
        this.decryptionCertificate = decryptionCertificate;
    }

    public int getNumberOfRecipients() {
        return this.recipients.size();
    }
}
