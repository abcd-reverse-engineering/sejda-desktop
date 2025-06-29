package code.util.encryption;

import code.util.Loggable;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import scala.util.control.NonFatal$;

/* compiled from: EncryptionUtil.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/encryption/EncryptionUtil$.class */
public final class EncryptionUtil$ implements Loggable {
    public static final EncryptionUtil$ MODULE$ = new EncryptionUtil$();
    private static final String salt;
    private static transient Logger logger;
    private static volatile transient boolean bitmap$trans$0;

    static {
        Loggable.$init$(MODULE$);
        salt = "ldsi82734x76%%%%6((NBV@#$%bdnmsda&$$dsvdsytt6%%^&*(((*&^%$FVBnjhgfrstyus8duyhdudyt%$%^^^^^%$$$$%^^%$987";
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v7 */
    private Logger logger$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (!bitmap$trans$0) {
                logger = logger();
                r0 = 1;
                bitmap$trans$0 = true;
            }
        }
        return logger;
    }

    @Override // code.util.Loggable
    public Logger logger() {
        return !bitmap$trans$0 ? logger$lzycompute() : logger;
    }

    private EncryptionUtil$() {
    }

    public boolean encrypt$default$3() {
        return false;
    }

    public String encrypt(final String key, final String value, final boolean urlSafe) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(1, code$util$encryption$EncryptionUtil$$keyToSpec(key));
        byte[] encrypted = cipher.doFinal(value.getBytes("UTF-8"));
        if (urlSafe) {
            return Base64.encodeBase64URLSafeString(encrypted);
        }
        return Base64.encodeBase64String(encrypted);
    }

    public String decrypt(final String key, final String encryptedValue) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(2, code$util$encryption$EncryptionUtil$$keyToSpec(key));
            return new String(cipher.doFinal(Base64.decodeBase64(encryptedValue)));
        } catch (Throwable th) {
            if (!NonFatal$.MODULE$.apply(th)) {
                throw th;
            }
            logger().warn(new StringBuilder(33).append("Failed to decrypt '").append(encryptedValue).append("', error was: ").append(th.getMessage()).toString());
            throw th;
        }
    }

    public SecretKeySpec code$util$encryption$EncryptionUtil$$keyToSpec(final String key) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        byte[] keyBytes = new StringBuilder(0).append(salt()).append(key).toString().getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        return new SecretKeySpec(Arrays.copyOf(sha.digest(keyBytes), 16), "AES");
    }

    private String salt() {
        return salt;
    }
}
