package org.sejda.sambox.pdmodel.encryption;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESFastEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.Arrays;
import org.sejda.commons.util.RequireUtils;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/encryption/StandardSecurityHandler.class */
public final class StandardSecurityHandler extends SecurityHandler {
    public static final String FILTER = "Standard";
    private static final Logger LOG = LoggerFactory.getLogger(StandardSecurityHandler.class);
    public static final Class<?> PROTECTION_POLICY_CLASS = StandardProtectionPolicy.class;
    private static final byte[] ENCRYPT_PADDING = {40, -65, 78, 94, 78, 117, -118, 65, 100, 0, 78, 86, -1, -6, 1, 8, 46, 46, 0, -74, -48, 104, 62, Byte.MIN_VALUE, 47, 12, -87, -2, 100, 83, 105, 122};
    private static final String[] HASHES_2B = {"SHA-256", "SHA-384", "SHA-512"};

    @Override // org.sejda.sambox.pdmodel.encryption.SecurityHandler
    public void prepareForDecryption(PDEncryption encryption, COSArray documentIDArray, DecryptionMaterial decryptionMaterial) throws IOException {
        PDCryptFilterDictionary stdCryptFilterDictionary;
        int newLength;
        int newLength2;
        byte[] computedPassword;
        if (!(decryptionMaterial instanceof StandardDecryptionMaterial)) {
            throw new IOException("Decryption material is not compatible with the document");
        }
        StandardDecryptionMaterial material = (StandardDecryptionMaterial) decryptionMaterial;
        if (encryption.getVersion() >= 4) {
            setStreamFilterName(encryption.getStreamFilterName());
            setStringFilterName(encryption.getStringFilterName());
        }
        setDecryptMetadata(encryption.isEncryptMetaData());
        String password = material.getPassword();
        if (password == null) {
            password = "";
        }
        int dicPermissions = encryption.getPermissions();
        int dicRevision = encryption.getRevision();
        int dicLength = encryption.getVersion() == 1 ? 5 : encryption.getLength() / 8;
        if ((encryption.getVersion() == 4 || encryption.getVersion() == 5) && (stdCryptFilterDictionary = encryption.getStdCryptFilterDictionary()) != null) {
            COSName cryptFilterMethod = stdCryptFilterDictionary.getCryptFilterMethod();
            if (COSName.AESV2.equals(cryptFilterMethod)) {
                dicLength = 16;
                setAES(true);
                if (encryption.getCOSObject().containsKey(COSName.LENGTH) && (newLength2 = encryption.getLength() / 8) < 16) {
                    LOG.warn("Using {} bytes key length instead of {} in AESV2 encryption", Integer.valueOf(newLength2), 16);
                    dicLength = newLength2;
                }
            }
            if (COSName.AESV3.equals(cryptFilterMethod)) {
                dicLength = 32;
                setAES(true);
                if (encryption.getCOSObject().containsKey(COSName.LENGTH) && (newLength = encryption.getLength() / 8) < 32) {
                    LOG.warn("Using {} bytes key length instead of {} in AESV3 encryption", Integer.valueOf(newLength), 32);
                    dicLength = newLength;
                }
            }
        }
        byte[] documentIDBytes = getDocumentIDBytes(documentIDArray);
        boolean encryptMetadata = encryption.isEncryptMetaData();
        byte[] userKey = encryption.getUserKey();
        byte[] ownerKey = encryption.getOwnerKey();
        byte[] ue = null;
        byte[] oe = null;
        Charset passwordCharset = StandardCharsets.ISO_8859_1;
        if (dicRevision == 6 || dicRevision == 5) {
            passwordCharset = StandardCharsets.UTF_8;
            ue = encryption.getUserEncryptionKey();
            oe = encryption.getOwnerEncryptionKey();
        }
        if (dicRevision == 6) {
            password = SaslPrep.saslPrepQuery(password);
        }
        if (isOwnerPassword(password.getBytes(passwordCharset), userKey, ownerKey, dicPermissions, documentIDBytes, dicRevision, dicLength, encryptMetadata)) {
            AccessPermission currentAccessPermission = AccessPermission.getOwnerAccessPermission();
            setCurrentAccessPermission(currentAccessPermission);
            if (dicRevision == 6 || dicRevision == 5) {
                computedPassword = password.getBytes(passwordCharset);
            } else {
                computedPassword = getUserPassword(password.getBytes(passwordCharset), ownerKey, dicRevision, dicLength);
            }
            setEncryptionKey(computeEncryptedKey(computedPassword, ownerKey, userKey, oe, ue, dicPermissions, documentIDBytes, dicRevision, dicLength, encryptMetadata, true));
        } else if (isUserPassword(password.getBytes(passwordCharset), userKey, ownerKey, dicPermissions, documentIDBytes, dicRevision, dicLength, encryptMetadata)) {
            AccessPermission currentAccessPermission2 = new AccessPermission(dicPermissions);
            setCurrentAccessPermission(currentAccessPermission2);
            setEncryptionKey(computeEncryptedKey(password.getBytes(passwordCharset), ownerKey, userKey, oe, ue, dicPermissions, documentIDBytes, dicRevision, dicLength, encryptMetadata, false));
        } else {
            throw new InvalidPasswordException("Cannot decrypt PDF, the password is incorrect");
        }
        if (dicRevision == 6 || dicRevision == 5) {
            validatePerms(encryption, dicPermissions, encryptMetadata);
        }
    }

    private byte[] getDocumentIDBytes(COSArray documentIDArray) {
        byte[] documentIDBytes;
        if (documentIDArray != null && documentIDArray.size() >= 1) {
            COSString id = (COSString) documentIDArray.getObject(0);
            documentIDBytes = id.getBytes();
        } else {
            documentIDBytes = new byte[0];
        }
        return documentIDBytes;
    }

    private void validatePerms(PDEncryption encryption, int dicPermissions, boolean encryptMetadata) throws IOException {
        try {
            BufferedBlockCipher cipher = new BufferedBlockCipher(new AESFastEngine());
            cipher.init(false, new KeyParameter(getEncryptionKey()));
            byte[] buf = new byte[cipher.getOutputSize(encryption.getPerms().length)];
            int len = cipher.processBytes(encryption.getPerms(), 0, encryption.getPerms().length, buf, 0);
            byte[] perms = Arrays.copyOf(buf, len + cipher.doFinal(buf, len));
            if (perms[9] != 97 || perms[10] != 100 || perms[11] != 98) {
                LOG.warn("Verification of permissions failed (constant)");
            }
            int permsP = (perms[0] & 255) | ((perms[1] & 255) << 8) | ((perms[2] & 255) << 16) | ((perms[3] & 255) << 24);
            if (permsP != dicPermissions) {
                LOG.warn("Verification of permissions failed (" + String.format("%08X", Integer.valueOf(permsP)) + " != " + String.format("%08X", Integer.valueOf(dicPermissions)) + ")");
            }
            if ((encryptMetadata && perms[8] != 84) || (!encryptMetadata && perms[8] != 70)) {
                LOG.warn("Verification of permissions failed (EncryptMetadata)");
            }
        } catch (DataLengthException | IllegalStateException | InvalidCipherTextException e) {
            throw new IOException((Throwable) e);
        }
    }

    public boolean isOwnerPassword(byte[] ownerPassword, byte[] user, byte[] owner, int permissions, byte[] id, int encRevision, int length, boolean encryptMetadata) throws IOException {
        byte[] hash;
        if (encRevision == 6 || encRevision == 5) {
            byte[] truncatedOwnerPassword = truncate127(ownerPassword);
            byte[] oHash = new byte[32];
            byte[] oValidationSalt = new byte[8];
            RequireUtils.requireIOCondition(owner.length >= 40, "Owner password is too short");
            System.arraycopy(owner, 0, oHash, 0, 32);
            System.arraycopy(owner, 32, oValidationSalt, 0, 8);
            if (encRevision == 5) {
                hash = computeSHA256(truncatedOwnerPassword, oValidationSalt, user);
            } else {
                hash = computeHash2A(truncatedOwnerPassword, oValidationSalt, user);
            }
            return java.util.Arrays.equals(hash, oHash);
        }
        byte[] userPassword = getUserPassword(ownerPassword, owner, encRevision, length);
        return isUserPassword(userPassword, user, owner, permissions, id, encRevision, length, encryptMetadata);
    }

    public byte[] getUserPassword(byte[] ownerPassword, byte[] owner, int encRevision, int length) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] rc4Key = computeRC4key(ownerPassword, encRevision, length);
        if (encRevision == 2) {
            decryptDataRC4(rc4Key, owner, result);
        } else if (encRevision == 3 || encRevision == 4) {
            byte[] iterationKey = new byte[rc4Key.length];
            byte[] otemp = new byte[owner.length];
            System.arraycopy(owner, 0, otemp, 0, owner.length);
            for (int i = 19; i >= 0; i--) {
                System.arraycopy(rc4Key, 0, iterationKey, 0, rc4Key.length);
                for (int j = 0; j < iterationKey.length; j++) {
                    iterationKey[j] = (byte) (iterationKey[j] ^ ((byte) i));
                }
                result.reset();
                decryptDataRC4(iterationKey, otemp, result);
                otemp = result.toByteArray();
            }
        }
        return result.toByteArray();
    }

    public byte[] computeEncryptedKey(byte[] password, byte[] o, byte[] u, byte[] oe, byte[] ue, int permissions, byte[] id, int encRevision, int length, boolean encryptMetadata, boolean isOwnerPassword) throws IOException {
        if (encRevision == 6 || encRevision == 5) {
            return computeEncryptedKeyRev56(password, isOwnerPassword, o, u, oe, ue, encRevision);
        }
        return computeEncryptedKeyRev234(password, o, permissions, id, encryptMetadata, length, encRevision);
    }

    private byte[] computeEncryptedKeyRev234(byte[] password, byte[] o, int permissions, byte[] id, boolean encryptMetadata, int length, int encRevision) {
        byte[] padded = truncateOrPad(password);
        MessageDigest md = MessageDigests.getMD5();
        md.update(padded);
        md.update(o);
        md.update((byte) permissions);
        md.update((byte) (permissions >>> 8));
        md.update((byte) (permissions >>> 16));
        md.update((byte) (permissions >>> 24));
        md.update(id);
        if (encRevision == 4 && !encryptMetadata) {
            md.update(new byte[]{-1, -1, -1, -1});
        }
        byte[] digest = md.digest();
        if (encRevision == 3 || encRevision == 4) {
            for (int i = 0; i < 50; i++) {
                md.update(digest, 0, length);
                digest = md.digest();
            }
        }
        byte[] result = new byte[length];
        System.arraycopy(digest, 0, result, 0, length);
        return result;
    }

    private byte[] computeEncryptedKeyRev56(byte[] password, boolean isOwnerPassword, byte[] o, byte[] u, byte[] oe, byte[] ue, int encRevision) throws IOException {
        byte[] hash;
        byte[] fileKeyEnc;
        if (isOwnerPassword) {
            RequireUtils.requireIOCondition(Objects.nonNull(oe), "/Encrypt/OE entry is missing");
            byte[] oKeySalt = new byte[8];
            System.arraycopy(o, 40, oKeySalt, 0, 8);
            if (encRevision == 5) {
                hash = computeSHA256(password, oKeySalt, u);
            } else {
                hash = computeHash2A(password, oKeySalt, u);
            }
            fileKeyEnc = oe;
        } else {
            RequireUtils.requireIOCondition(Objects.nonNull(ue), "/Encrypt/UE entry is missing");
            byte[] uKeySalt = new byte[8];
            System.arraycopy(u, 40, uKeySalt, 0, 8);
            if (encRevision == 5) {
                hash = computeSHA256(password, uKeySalt, null);
            } else {
                hash = computeHash2A(password, uKeySalt, null);
            }
            fileKeyEnc = ue;
        }
        try {
            BufferedBlockCipher cipher = new BufferedBlockCipher(new CBCBlockCipher(new AESFastEngine()));
            cipher.init(false, new KeyParameter(hash));
            byte[] buf = new byte[cipher.getOutputSize(fileKeyEnc.length)];
            int len = cipher.processBytes(fileKeyEnc, 0, fileKeyEnc.length, buf, 0);
            return Arrays.copyOf(buf, len + cipher.doFinal(buf, len));
        } catch (DataLengthException | IllegalStateException | InvalidCipherTextException e) {
            throw new IOException((Throwable) e);
        }
    }

    public byte[] computeUserPassword(byte[] password, byte[] owner, int permissions, byte[] id, int encRevision, int length, boolean encryptMetadata) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] encKey = computeEncryptedKey(password, owner, null, null, null, permissions, id, encRevision, length, encryptMetadata, true);
        if (encRevision == 2) {
            decryptDataRC4(encKey, ENCRYPT_PADDING, result);
        } else if (encRevision == 3 || encRevision == 4) {
            MessageDigest md = MessageDigests.getMD5();
            md.update(ENCRYPT_PADDING);
            md.update(id);
            result.write(md.digest());
            byte[] iterationKey = new byte[encKey.length];
            for (int i = 0; i < 20; i++) {
                System.arraycopy(encKey, 0, iterationKey, 0, iterationKey.length);
                for (int j = 0; j < iterationKey.length; j++) {
                    iterationKey[j] = (byte) (iterationKey[j] ^ i);
                }
                ByteArrayInputStream input = new ByteArrayInputStream(result.toByteArray());
                result.reset();
                decryptDataRC4(iterationKey, input, result);
            }
            byte[] finalResult = new byte[32];
            System.arraycopy(result.toByteArray(), 0, finalResult, 0, 16);
            System.arraycopy(ENCRYPT_PADDING, 0, finalResult, 16, 16);
            result.reset();
            result.write(finalResult);
        }
        return result.toByteArray();
    }

    public byte[] computeOwnerPassword(byte[] ownerPassword, byte[] userPassword, int encRevision, int length) throws IOException {
        if (encRevision == 2 && length != 5) {
            throw new IOException("Expected length=5 actual=" + length);
        }
        byte[] rc4Key = computeRC4key(ownerPassword, encRevision, length);
        byte[] paddedUser = truncateOrPad(userPassword);
        ByteArrayOutputStream encrypted = new ByteArrayOutputStream();
        decryptDataRC4(rc4Key, new ByteArrayInputStream(paddedUser), encrypted);
        if (encRevision == 3 || encRevision == 4) {
            byte[] iterationKey = new byte[rc4Key.length];
            for (int i = 1; i < 20; i++) {
                System.arraycopy(rc4Key, 0, iterationKey, 0, rc4Key.length);
                for (int j = 0; j < iterationKey.length; j++) {
                    iterationKey[j] = (byte) (iterationKey[j] ^ ((byte) i));
                }
                ByteArrayInputStream input = new ByteArrayInputStream(encrypted.toByteArray());
                encrypted.reset();
                decryptDataRC4(iterationKey, input, encrypted);
            }
        }
        return encrypted.toByteArray();
    }

    private byte[] computeRC4key(byte[] ownerPassword, int encRevision, int length) {
        MessageDigest md = MessageDigests.getMD5();
        byte[] digest = md.digest(truncateOrPad(ownerPassword));
        if (encRevision == 3 || encRevision == 4) {
            for (int i = 0; i < 50; i++) {
                md.update(digest, 0, length);
                digest = md.digest();
            }
        }
        byte[] rc4Key = new byte[length];
        System.arraycopy(digest, 0, rc4Key, 0, length);
        return rc4Key;
    }

    private byte[] truncateOrPad(byte[] password) {
        byte[] padded = new byte[ENCRYPT_PADDING.length];
        int bytesBeforePad = Math.min(password.length, padded.length);
        System.arraycopy(password, 0, padded, 0, bytesBeforePad);
        System.arraycopy(ENCRYPT_PADDING, 0, padded, bytesBeforePad, ENCRYPT_PADDING.length - bytesBeforePad);
        return padded;
    }

    public boolean isUserPassword(byte[] password, byte[] user, byte[] owner, int permissions, byte[] id, int encRevision, int length, boolean encryptMetadata) throws IOException {
        byte[] hash;
        if (encRevision == 2) {
            byte[] passwordBytes = computeUserPassword(password, owner, permissions, id, encRevision, length, encryptMetadata);
            return java.util.Arrays.equals(user, passwordBytes);
        }
        if (encRevision == 3 || encRevision == 4) {
            byte[] passwordBytes2 = computeUserPassword(password, owner, permissions, id, encRevision, length, encryptMetadata);
            return java.util.Arrays.equals(java.util.Arrays.copyOf(user, 16), java.util.Arrays.copyOf(passwordBytes2, 16));
        }
        if (encRevision == 6 || encRevision == 5) {
            byte[] truncatedPassword = truncate127(password);
            byte[] uHash = new byte[32];
            byte[] uValidationSalt = new byte[8];
            System.arraycopy(user, 0, uHash, 0, 32);
            System.arraycopy(user, 32, uValidationSalt, 0, 8);
            if (encRevision == 5) {
                hash = computeSHA256(truncatedPassword, uValidationSalt, null);
            } else {
                hash = computeHash2A(truncatedPassword, uValidationSalt, null);
            }
            return java.util.Arrays.equals(hash, uHash);
        }
        throw new IOException("Unknown Encryption Revision " + encRevision);
    }

    public boolean isUserPassword(String password, byte[] user, byte[] owner, int permissions, byte[] id, int encRevision, int length, boolean encryptMetadata) throws IOException {
        if (encRevision == 6 || encRevision == 5) {
            return isUserPassword(password.getBytes(StandardCharsets.UTF_8), user, owner, permissions, id, encRevision, length, encryptMetadata);
        }
        return isUserPassword(password.getBytes(StandardCharsets.ISO_8859_1), user, owner, permissions, id, encRevision, length, encryptMetadata);
    }

    public boolean isOwnerPassword(String password, byte[] user, byte[] owner, int permissions, byte[] id, int encRevision, int length, boolean encryptMetadata) throws IOException {
        return isOwnerPassword(password.getBytes(StandardCharsets.ISO_8859_1), user, owner, permissions, id, encRevision, length, encryptMetadata);
    }

    private byte[] computeHash2A(byte[] password, byte[] salt, byte[] u) throws IOException {
        byte[] userKey;
        if (u == null) {
            userKey = new byte[0];
        } else {
            if (u.length < 48) {
                throw new IOException("Bad U length");
            }
            if (u.length > 48) {
                userKey = new byte[48];
                System.arraycopy(u, 0, userKey, 0, 48);
            } else {
                userKey = u;
            }
        }
        byte[] truncatedPassword = truncate127(password);
        byte[] input = concat(truncatedPassword, salt, userKey);
        return computeHash2B(input, truncatedPassword, userKey);
    }

    private static byte[] computeHash2B(byte[] input, byte[] password, byte[] userKey) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException, IOException, InvalidAlgorithmParameterException {
        byte[] k1;
        try {
            MessageDigest md = MessageDigests.getSHA256();
            byte[] k = md.digest(input);
            byte[] e = null;
            int round = 0;
            while (true) {
                if (round >= 64 && (e[e.length - 1] & 255) <= round - 32) {
                    break;
                }
                if (userKey != null && userKey.length >= 48) {
                    k1 = new byte[64 * (password.length + k.length + 48)];
                } else {
                    k1 = new byte[64 * (password.length + k.length)];
                }
                int pos = 0;
                for (int i = 0; i < 64; i++) {
                    System.arraycopy(password, 0, k1, pos, password.length);
                    int pos2 = pos + password.length;
                    System.arraycopy(k, 0, k1, pos2, k.length);
                    pos = pos2 + k.length;
                    if (userKey != null && userKey.length >= 48) {
                        System.arraycopy(userKey, 0, k1, pos, 48);
                        pos += 48;
                    }
                }
                byte[] kFirst = new byte[16];
                byte[] kSecond = new byte[16];
                System.arraycopy(k, 0, kFirst, 0, 16);
                System.arraycopy(k, 16, kSecond, 0, 16);
                Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
                SecretKeySpec keySpec = new SecretKeySpec(kFirst, "AES");
                IvParameterSpec ivSpec = new IvParameterSpec(kSecond);
                cipher.init(1, keySpec, ivSpec);
                e = cipher.doFinal(k1);
                byte[] eFirst = new byte[16];
                System.arraycopy(e, 0, eFirst, 0, 16);
                BigInteger bi = new BigInteger(1, eFirst);
                BigInteger remainder = bi.mod(new BigInteger("3"));
                String nextHash = HASHES_2B[remainder.intValue()];
                MessageDigest md2 = MessageDigest.getInstance(nextHash);
                k = md2.digest(e);
                round++;
            }
            if (k.length > 32) {
                byte[] kTrunc = new byte[32];
                System.arraycopy(k, 0, kTrunc, 0, 32);
                return kTrunc;
            }
            return k;
        } catch (GeneralSecurityException e2) {
            throw new IOException(e2);
        }
    }

    private static byte[] computeSHA256(byte[] input, byte[] password, byte[] userKey) {
        MessageDigest md = MessageDigests.getSHA256();
        md.update(input);
        md.update(password);
        return userKey == null ? md.digest() : md.digest(userKey);
    }

    private static byte[] concat(byte[] a, byte[] b, byte[] c) {
        byte[] o = new byte[a.length + b.length + c.length];
        System.arraycopy(a, 0, o, 0, a.length);
        System.arraycopy(b, 0, o, a.length, b.length);
        System.arraycopy(c, 0, o, a.length + b.length, c.length);
        return o;
    }

    private static byte[] truncate127(byte[] in) {
        if (in.length <= 127) {
            return in;
        }
        byte[] trunc = new byte[127];
        System.arraycopy(in, 0, trunc, 0, 127);
        return trunc;
    }
}
