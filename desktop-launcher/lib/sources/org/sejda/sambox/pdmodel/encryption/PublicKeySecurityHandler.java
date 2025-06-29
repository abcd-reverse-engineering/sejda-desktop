package org.sejda.sambox.pdmodel.encryption;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.CMSEnvelopedData;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.KeyTransRecipientId;
import org.bouncycastle.cms.RecipientId;
import org.bouncycastle.cms.RecipientInformation;
import org.bouncycastle.cms.jcajce.JceKeyTransEnvelopedRecipient;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSString;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/encryption/PublicKeySecurityHandler.class */
public final class PublicKeySecurityHandler extends SecurityHandler {
    public static final String FILTER = "Adobe.PubSec";
    private PublicKeyProtectionPolicy policy;

    public PublicKeySecurityHandler() {
        this.policy = null;
    }

    public PublicKeySecurityHandler(PublicKeyProtectionPolicy p) {
        this.policy = null;
        this.policy = p;
        this.keyLength = this.policy.getEncryptionKeyLength();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.sejda.sambox.pdmodel.encryption.SecurityHandler
    public void prepareForDecryption(PDEncryption encryption, COSArray documentIDArray, DecryptionMaterial decryptionMaterial) throws IOException {
        byte[] mdResult;
        if (!(decryptionMaterial instanceof PublicKeyDecryptionMaterial)) {
            throw new IOException("Provided decryption material is not compatible with the document");
        }
        PublicKeyDecryptionMaterial material = (PublicKeyDecryptionMaterial) decryptionMaterial;
        PDCryptFilterDictionary defaultCryptFilterDictionary = encryption.getDefaultCryptFilterDictionary();
        if (defaultCryptFilterDictionary != null && defaultCryptFilterDictionary.getLength() != 0) {
            setKeyLength(defaultCryptFilterDictionary.getLength());
            setDecryptMetadata(defaultCryptFilterDictionary.isEncryptMetaData());
        } else if (encryption.getLength() != 0) {
            setKeyLength(encryption.getLength());
            setDecryptMetadata(encryption.isEncryptMetaData());
        }
        try {
            boolean foundRecipient = false;
            X509Certificate certificate = material.getCertificate();
            X509CertificateHolder materialCert = null;
            if (certificate != null) {
                materialCert = new X509CertificateHolder(certificate.getEncoded());
            }
            byte[] envelopedData = null;
            COSArray array = encryption.getCOSObject().getCOSArray(COSName.RECIPIENTS);
            if (array == null && defaultCryptFilterDictionary != null) {
                array = defaultCryptFilterDictionary.getCOSObject().getCOSArray(COSName.RECIPIENTS);
            }
            if (array == null) {
                throw new IOException("/Recipients entry is missing in encryption dictionary");
            }
            byte[] bArr = new byte[array.size()];
            int recipientFieldsLength = 0;
            StringBuilder extraInfo = new StringBuilder();
            for (int i = 0; i < array.size(); i++) {
                COSString recipientFieldString = (COSString) array.getObject(i);
                byte[] recipientBytes = recipientFieldString.getBytes();
                CMSEnvelopedData data = new CMSEnvelopedData(recipientBytes);
                Collection<RecipientInformation> recipCertificatesIt = data.getRecipientInfos().getRecipients();
                int j = 0;
                Iterator<RecipientInformation> it = recipCertificatesIt.iterator();
                while (true) {
                    if (it.hasNext()) {
                        RecipientInformation ri = it.next();
                        RecipientId rid = ri.getRID();
                        if (!foundRecipient && rid.match(materialCert)) {
                            foundRecipient = true;
                            PrivateKey privateKey = (PrivateKey) material.getPrivateKey();
                            envelopedData = ri.getContent(new JceKeyTransEnvelopedRecipient(privateKey));
                            break;
                        } else {
                            j++;
                            if (certificate != null) {
                                extraInfo.append('\n');
                                extraInfo.append(j);
                                extraInfo.append(": ");
                                if (rid instanceof KeyTransRecipientId) {
                                    appendCertInfo(extraInfo, (KeyTransRecipientId) rid, certificate, materialCert);
                                }
                            }
                        }
                    }
                }
                bArr[i] = recipientBytes;
                recipientFieldsLength += recipientBytes.length;
            }
            if (!foundRecipient || envelopedData == null) {
                throw new IOException("The certificate matches none of " + array.size() + " recipient entries" + extraInfo);
            }
            if (envelopedData.length != 24) {
                throw new IOException("The enveloped data does not contain 24 bytes");
            }
            byte[] accessBytes = new byte[4];
            System.arraycopy(envelopedData, 20, accessBytes, 0, 4);
            AccessPermission currentAccessPermission = new AccessPermission(accessBytes);
            currentAccessPermission.setReadOnly();
            setCurrentAccessPermission(currentAccessPermission);
            byte[] sha1Input = new byte[recipientFieldsLength + 20];
            System.arraycopy(envelopedData, 0, sha1Input, 0, 20);
            int sha1InputOffset = 20;
            for (Object[] objArr : bArr) {
                System.arraycopy(objArr, 0, sha1Input, sha1InputOffset, objArr.length);
                sha1InputOffset += objArr.length;
            }
            if (encryption.getVersion() == 4 || encryption.getVersion() == 5) {
                if (!isDecryptMetadata()) {
                    sha1Input = Arrays.copyOf(sha1Input, sha1Input.length + 4);
                    System.arraycopy(new byte[]{-1, -1, -1, -1}, 0, sha1Input, sha1Input.length - 4, 4);
                }
                if (encryption.getVersion() == 4) {
                    mdResult = MessageDigests.getSHA1().digest(sha1Input);
                } else {
                    mdResult = MessageDigests.getSHA256().digest(sha1Input);
                }
                if (defaultCryptFilterDictionary != null) {
                    COSName cryptFilterMethod = defaultCryptFilterDictionary.getCryptFilterMethod();
                    setAES(COSName.AESV2.equals(cryptFilterMethod) || COSName.AESV3.equals(cryptFilterMethod));
                }
            } else {
                mdResult = MessageDigests.getSHA1().digest(sha1Input);
            }
            setEncryptionKey(new byte[this.keyLength / 8]);
            System.arraycopy(mdResult, 0, getEncryptionKey(), 0, this.keyLength / 8);
        } catch (CMSException | KeyStoreException | CertificateEncodingException e) {
            throw new IOException((Throwable) e);
        }
    }

    private void appendCertInfo(StringBuilder extraInfo, KeyTransRecipientId ktRid, X509Certificate certificate, X509CertificateHolder materialCert) {
        BigInteger ridSerialNumber = ktRid.getSerialNumber();
        if (ridSerialNumber != null) {
            String certSerial = "unknown";
            BigInteger certSerialNumber = certificate.getSerialNumber();
            if (certSerialNumber != null) {
                certSerial = certSerialNumber.toString(16);
            }
            extraInfo.append("serial-#: rid ");
            extraInfo.append(ridSerialNumber.toString(16));
            extraInfo.append(" vs. cert ");
            extraInfo.append(certSerial);
            extraInfo.append(" issuer: rid '");
            extraInfo.append(ktRid.getIssuer());
            extraInfo.append("' vs. cert '");
            extraInfo.append((Object) (materialCert == null ? "null" : materialCert.getIssuer()));
            extraInfo.append("' ");
        }
    }
}
