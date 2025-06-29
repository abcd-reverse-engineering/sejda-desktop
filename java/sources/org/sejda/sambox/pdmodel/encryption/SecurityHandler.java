package org.sejda.sambox.pdmodel.encryption;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.crypto.engines.AESFastEngine;
import org.bouncycastle.crypto.io.CipherInputStream;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.sejda.commons.FastByteArrayOutputStream;
import org.sejda.commons.util.IOUtils;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.cos.COSString;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/encryption/SecurityHandler.class */
public abstract class SecurityHandler {
    private static final short DEFAULT_KEY_LENGTH = 40;
    private byte[] encryptionKey;
    private boolean decryptMetadata;
    private boolean useAES;
    private COSName streamFilterName;
    private COSName stringFilterName;
    private static final Logger LOG = LoggerFactory.getLogger(SecurityHandler.class);
    private static final byte[] AES_SALT = {115, 65, 108, 84};
    protected short keyLength = 40;
    private final RC4Cipher rc4 = new RC4Cipher();
    private final Set<COSBase> objects = Collections.newSetFromMap(new IdentityHashMap());
    private AccessPermission currentAccessPermission = null;
    private final Map<Long, Boolean> logOnceCache = new HashMap();

    public abstract void prepareForDecryption(PDEncryption pDEncryption, COSArray cOSArray, DecryptionMaterial decryptionMaterial) throws IOException;

    protected void setDecryptMetadata(boolean decryptMetadata) {
        this.decryptMetadata = decryptMetadata;
    }

    public boolean isDecryptMetadata() {
        return this.decryptMetadata;
    }

    protected void setStringFilterName(COSName stringFilterName) {
        this.stringFilterName = stringFilterName;
    }

    protected void setStreamFilterName(COSName streamFilterName) {
        this.streamFilterName = streamFilterName;
    }

    private void decryptData(long objectNumber, long genNumber, InputStream data, OutputStream output) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidAlgorithmParameterException {
        if (this.useAES && this.encryptionKey.length == 32) {
            decryptDataAES256(data, output);
        } else {
            byte[] finalKey = calcFinalKey(objectNumber, genNumber);
            if (this.useAES) {
                decryptDataAESother(finalKey, data, output);
            } else {
                decryptDataRC4(finalKey, data, output);
            }
        }
        IOUtils.close(output);
    }

    private byte[] calcFinalKey(long objectNumber, long genNumber) {
        byte[] newKey = new byte[this.encryptionKey.length + 5];
        System.arraycopy(this.encryptionKey, 0, newKey, 0, this.encryptionKey.length);
        newKey[newKey.length - 5] = (byte) (objectNumber & 255);
        newKey[newKey.length - 4] = (byte) ((objectNumber >> 8) & 255);
        newKey[newKey.length - 3] = (byte) ((objectNumber >> 16) & 255);
        newKey[newKey.length - 2] = (byte) (genNumber & 255);
        newKey[newKey.length - 1] = (byte) ((genNumber >> 8) & 255);
        MessageDigest md = MessageDigests.getMD5();
        md.update(newKey);
        if (this.useAES) {
            md.update(AES_SALT);
        }
        byte[] digestedKey = md.digest();
        int length = Math.min(newKey.length, 16);
        byte[] finalKey = new byte[length];
        System.arraycopy(digestedKey, 0, finalKey, 0, length);
        return finalKey;
    }

    protected void decryptDataRC4(byte[] finalKey, InputStream input, OutputStream output) throws IOException {
        this.rc4.setKey(finalKey);
        this.rc4.write(input, output);
    }

    protected void decryptDataRC4(byte[] finalKey, byte[] input, OutputStream output) throws IOException {
        this.rc4.setKey(finalKey);
        this.rc4.write(input, output);
    }

    private void decryptDataAESother(byte[] finalKey, InputStream data, OutputStream output) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidAlgorithmParameterException {
        byte[] iv = new byte[16];
        int ivSize = data.read(iv);
        if (ivSize == -1) {
            return;
        }
        if (ivSize != iv.length) {
            throw new IOException("AES initialization vector not fully read: only " + ivSize + " bytes read instead of " + iv.length);
        }
        try {
            Cipher decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKey aesKey = new SecretKeySpec(finalKey, "AES");
            IvParameterSpec ips = new IvParameterSpec(iv);
            decryptCipher.init(2, aesKey, ips);
            byte[] buffer = new byte[PDAnnotation.FLAG_TOGGLE_NO_VIEW];
            while (true) {
                int n = data.read(buffer);
                if (n != -1) {
                    byte[] update = decryptCipher.update(buffer, 0, n);
                    if (update != null) {
                        output.write(update);
                    }
                } else {
                    output.write(decryptCipher.doFinal());
                    return;
                }
            }
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            throw new IOException(e);
        }
    }

    private void decryptDataAES256(InputStream data, OutputStream output) throws IOException {
        byte[] iv = new byte[16];
        int ivSize = data.read(iv);
        if (ivSize == -1) {
            return;
        }
        if (ivSize != iv.length) {
            throw new IOException("AES initialization vector not fully read: only " + ivSize + " bytes read instead of " + iv.length);
        }
        PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESFastEngine()));
        cipher.init(false, new ParametersWithIV(new KeyParameter(this.encryptionKey), iv));
        CipherInputStream cis = new CipherInputStream(data, cipher);
        try {
            IOUtils.copy(cis, output);
            cis.close();
        } catch (Throwable th) {
            try {
                cis.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public void decrypt(COSBase obj, long objNum, long genNum) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, InvalidAlgorithmParameterException {
        if (obj instanceof COSString) {
            if (this.objects.contains(obj)) {
                return;
            }
            this.objects.add(obj);
            decryptString((COSString) obj, objNum, genNum);
            return;
        }
        if (obj instanceof COSStream) {
            if (this.objects.contains(obj)) {
                return;
            }
            this.objects.add(obj);
            decryptStream((COSStream) obj, objNum, genNum);
            return;
        }
        if (obj instanceof COSDictionary) {
            decryptDictionary((COSDictionary) obj, objNum, genNum);
        } else if (obj instanceof COSArray) {
            decryptArray((COSArray) obj, objNum, genNum);
        }
    }

    public void decryptStream(COSStream stream, long objNum, long genNum) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidAlgorithmParameterException {
        if (!COSName.IDENTITY.equals(this.streamFilterName)) {
            COSBase type = stream.getCOSName(COSName.TYPE);
            if ((!this.decryptMetadata && COSName.METADATA.equals(type)) || COSName.XREF.equals(type)) {
                return;
            }
            if (COSName.METADATA.equals(type)) {
                byte[] buf = new byte[10];
                InputStream is = stream.getUnfilteredStream();
                try {
                    is.read(buf);
                    if (is != null) {
                        is.close();
                    }
                    if (Arrays.equals(buf, "<?xpacket ".getBytes(StandardCharsets.ISO_8859_1))) {
                        LOG.warn("Metadata is not encrypted, but was expected to be");
                        LOG.warn("Read PDF specification about EncryptMetadata (default value: true)");
                        return;
                    }
                } catch (Throwable th) {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            decryptDictionary(stream, objNum, genNum);
            byte[] encrypted = IOUtils.toByteArray(stream.getFilteredStream());
            ByteArrayInputStream encryptedStream = new ByteArrayInputStream(encrypted);
            try {
                OutputStream output = stream.createFilteredStream();
                try {
                    decryptData(objNum, genNum, encryptedStream, output);
                    if (output != null) {
                        output.close();
                    }
                } finally {
                }
            } catch (IOException e) {
                e.getMessage();
                logErrorOnce("Failed to decrypt COSStream object " + objNum + " " + this + ": " + genNum, objNum);
                throw e;
            }
        }
    }

    private void decryptDictionary(COSDictionary dictionary, long objNum, long genNum) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, InvalidAlgorithmParameterException {
        if (dictionary.getItem(COSName.CF) != null) {
            return;
        }
        COSBase type = dictionary.getDictionaryObject(COSName.TYPE);
        boolean isSignature = COSName.SIG.equals(type) || COSName.DOC_TIME_STAMP.equals(type) || ((dictionary.getDictionaryObject(COSName.CONTENTS) instanceof COSString) && (dictionary.getDictionaryObject(COSName.BYTERANGE) instanceof COSArray));
        for (Map.Entry<COSName, COSBase> entry : dictionary.entrySet()) {
            if (!isSignature || !COSName.CONTENTS.equals(entry.getKey())) {
                COSBase value = entry.getValue();
                if ((value instanceof COSString) || (value instanceof COSArray) || (value instanceof COSDictionary)) {
                    decrypt(value, objNum, genNum);
                }
            }
        }
    }

    private void decryptString(COSString string, long objNum, long genNum) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, InvalidAlgorithmParameterException {
        if (!COSName.IDENTITY.equals(this.stringFilterName)) {
            ByteArrayInputStream data = new ByteArrayInputStream(string.getBytes());
            FastByteArrayOutputStream outputStream = new FastByteArrayOutputStream();
            try {
                decryptData(objNum, genNum, data, outputStream);
                string.setValue(outputStream.toByteArray());
            } catch (IOException ex) {
                ex.getMessage();
                logErrorOnce("Failed to decrypt COSString object " + objNum + " " + this + ": " + genNum, objNum);
            }
        }
    }

    private void logErrorOnce(String message, long key) {
        if (this.logOnceCache.containsKey(Long.valueOf(key))) {
            return;
        }
        this.logOnceCache.put(Long.valueOf(key), null);
        LOG.error(message);
    }

    private void decryptArray(COSArray array, long objNum, long genNum) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, InvalidAlgorithmParameterException {
        for (int i = 0; i < array.size(); i++) {
            decrypt(array.get(i), objNum, genNum);
        }
    }

    public int getKeyLength() {
        return this.keyLength;
    }

    public void setKeyLength(int keyLen) {
        this.keyLength = (short) keyLen;
    }

    public void setCurrentAccessPermission(AccessPermission currentAccessPermission) {
        this.currentAccessPermission = currentAccessPermission;
    }

    public AccessPermission getCurrentAccessPermission() {
        return this.currentAccessPermission;
    }

    public boolean isAES() {
        return this.useAES;
    }

    public void setAES(boolean aesValue) {
        this.useAES = aesValue;
    }

    public byte[] getEncryptionKey() {
        return this.encryptionKey;
    }

    protected void setEncryptionKey(byte[] encryptionKey) {
        this.encryptionKey = encryptionKey;
    }
}
