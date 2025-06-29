package code.util.encryption;

import code.util.FileHelper$;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.sejda.model.encryption.EncryptionAtRestPolicy;
import org.sejda.model.encryption.NoEncryptionAtRest;

/* compiled from: FileEncryption.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/encryption/NoFileEncryption$.class */
public final class NoFileEncryption$ implements FileEncryption {
    public static final NoFileEncryption$ MODULE$ = new NoFileEncryption$();

    private NoFileEncryption$() {
    }

    @Override // code.util.encryption.FileEncryption
    public EncryptionAtRestPolicy policy() {
        return NoEncryptionAtRest.INSTANCE;
    }

    @Override // code.util.encryption.FileEncryption
    public void encrypt(final File source, final File dest) {
        FileHelper$.MODULE$.copy(source, dest);
    }

    @Override // code.util.encryption.FileEncryption
    public void encrypt(final InputStream source, final File dest) {
        FileHelper$.MODULE$.copy(source, dest);
    }

    @Override // code.util.encryption.FileEncryption
    public void decrypt(final File source, final File dest) {
        FileHelper$.MODULE$.copy(source, dest);
    }

    @Override // code.util.encryption.FileEncryption
    public OutputStream encryptedFileOut(final File file) {
        return new FileOutputStream(file);
    }

    @Override // code.util.encryption.FileEncryption
    public InputStream decryptedFileIn(final File source) {
        return new FileInputStream(source);
    }
}
