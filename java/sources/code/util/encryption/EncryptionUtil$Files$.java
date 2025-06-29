package code.util.encryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import org.apache.commons.io.IOUtils;

/* compiled from: EncryptionUtil.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/encryption/EncryptionUtil$Files$.class */
public class EncryptionUtil$Files$ {
    public static final EncryptionUtil$Files$ MODULE$ = new EncryptionUtil$Files$();

    public void encrypt(final File source, final File dest, final Cipher cipher) {
        encrypt(new FileInputStream(source), dest, cipher);
    }

    public void encrypt(final InputStream in, final File dest, final Cipher cipher) {
        FileOutputStream out = new FileOutputStream(dest);
        CipherOutputStream encryptedOut = new CipherOutputStream(out, cipher);
        try {
            IOUtils.copy(in, encryptedOut);
        } finally {
            IOUtils.closeQuietly(encryptedOut);
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(in);
        }
    }

    public void decrypt(final File source, final File dest, final Cipher cipher) {
        FileInputStream encryptedIn = new FileInputStream(source);
        CipherInputStream in = new CipherInputStream(encryptedIn, cipher);
        FileOutputStream out = new FileOutputStream(dest);
        try {
            IOUtils.copy(in, out);
        } finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(encryptedIn);
        }
    }

    public InputStream decryptedStream(final File source, final Cipher cipher) {
        FileInputStream encryptedIn = new FileInputStream(source);
        return new CipherInputStream(encryptedIn, cipher);
    }

    public Cipher getCipher(final String key, final int mode) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(mode, EncryptionUtil$.MODULE$.code$util$encryption$EncryptionUtil$$keyToSpec(key));
        return cipher;
    }

    public CipherInputStream wrap(final InputStream in, final Cipher cipher) {
        return new CipherInputStream(in, cipher);
    }

    public CipherOutputStream wrap(final OutputStream out, final Cipher cipher) {
        return new CipherOutputStream(out, cipher);
    }
}
