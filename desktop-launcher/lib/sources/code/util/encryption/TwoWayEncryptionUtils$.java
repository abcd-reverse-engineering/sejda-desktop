package code.util.encryption;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;

/* compiled from: TwoWayEncryptionUtils.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/encryption/TwoWayEncryptionUtils$.class */
public final class TwoWayEncryptionUtils$ {
    public static final TwoWayEncryptionUtils$ MODULE$ = new TwoWayEncryptionUtils$();
    private static SecureRandom RANDOM;
    private static volatile boolean bitmap$0;

    private TwoWayEncryptionUtils$() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v6 */
    private SecureRandom RANDOM$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (!bitmap$0) {
                RANDOM = new SecureRandom();
                r0 = 1;
                bitmap$0 = true;
            }
        }
        return RANDOM;
    }

    public SecureRandom RANDOM() {
        return !bitmap$0 ? RANDOM$lzycompute() : RANDOM;
    }

    public byte[] random(final int bits) {
        if (bits < 8 || bits % 8 != 0) {
            throw new IllegalArgumentException(new StringBuilder(31).append(bits).append(" does not match even byte count").toString());
        }
        byte[] bytes = new byte[bits / 8];
        RANDOM().nextBytes(bytes);
        return bytes;
    }

    public byte[] pbkdf2(final String secret, final String salt) throws NoSuchAlgorithmException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(secret.toCharArray(), salt.getBytes("UTF-8"), 4096, PDAnnotation.FLAG_LOCKED);
        return factory.generateSecret(spec).getEncoded();
    }

    public byte[] md5(final String value) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return md.digest(value.getBytes("UTF-8"));
    }

    public String toHex(final byte[] bytes) {
        return new String(Hex.encodeHex(bytes)).toLowerCase();
    }

    public byte[] fromHex(final String hex) {
        return Hex.decodeHex(hex.toCharArray());
    }

    public String encrypt(final String plain, final String secret, final String salt) throws NoSuchAlgorithmException {
        byte[] key = pbkdf2(secret, salt);
        byte[] iv = md5(salt);
        return toHex(encryptRaw(plain.getBytes("UTF-8"), key, iv));
    }

    public byte[] encryptRaw(final byte[] plain, final byte[] key, final byte[] iv) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        SecretKeySpec secret = new SecretKeySpec(key, "AES");
        IvParameterSpec ips = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, secret, ips);
        return cipher.doFinal(plain);
    }

    public String decrypt(final String encrypted, final String secret, final String salt) throws NoSuchAlgorithmException {
        byte[] key = pbkdf2(secret, salt);
        byte[] iv = md5(salt);
        return new String(decryptRaw(fromHex(encrypted), key, iv));
    }

    public byte[] decryptRaw(final byte[] encrypted, final byte[] key, final byte[] iv) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        SecretKeySpec secret = new SecretKeySpec(key, "AES");
        IvParameterSpec ips = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(2, secret, ips);
        return cipher.doFinal(encrypted);
    }
}
