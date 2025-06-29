package code.util.encryption;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import org.sejda.model.encryption.EncryptionAtRestPolicy;
import scala.reflect.ScalaSignature;

/* compiled from: FileEncryption.scala */
@ScalaSignature(bytes = "\u0006\u000593qa\u0002\u0005\u0011\u0002G\u0005r\u0002C\u0003\u0017\u0001\u0019\u0005q\u0003C\u0003$\u0001\u0019\u0005A\u0005C\u0003$\u0001\u0019\u0005A\u0007C\u0003;\u0001\u0019\u00051\bC\u0003@\u0001\u0019\u0005\u0001\tC\u0003G\u0001\u0019\u0005qI\u0001\bGS2,WI\\2ssB$\u0018n\u001c8\u000b\u0005%Q\u0011AC3oGJL\b\u000f^5p]*\u00111\u0002D\u0001\u0005kRLGNC\u0001\u000e\u0003\u0011\u0019w\u000eZ3\u0004\u0001M\u0011\u0001\u0001\u0005\t\u0003#Qi\u0011A\u0005\u0006\u0002'\u0005)1oY1mC&\u0011QC\u0005\u0002\u0007\u0003:L(+\u001a4\u0002\rA|G.[2z+\u0005A\u0002CA\r\"\u001b\u0005Q\"BA\u0005\u001c\u0015\taR$A\u0003n_\u0012,GN\u0003\u0002\u001f?\u0005)1/\u001a6eC*\t\u0001%A\u0002pe\u001eL!A\t\u000e\u0003-\u0015s7M]=qi&|g.\u0011;SKN$\bk\u001c7jGf\fq!\u001a8def\u0004H\u000fF\u0002&QI\u0002\"!\u0005\u0014\n\u0005\u001d\u0012\"\u0001B+oSRDQ!\u000b\u0002A\u0002)\naa]8ve\u000e,\u0007CA\u00161\u001b\u0005a#BA\u0017/\u0003\tIwNC\u00010\u0003\u0011Q\u0017M^1\n\u0005Eb#\u0001\u0002$jY\u0016DQa\r\u0002A\u0002)\n1\u0002Z3ti&t\u0017\r^5p]R\u0019Q%N\u001d\t\u000b%\u001a\u0001\u0019\u0001\u001c\u0011\u0005-:\u0014B\u0001\u001d-\u0005-Ie\u000e];u'R\u0014X-Y7\t\u000bM\u001a\u0001\u0019\u0001\u0016\u0002\u000f\u0011,7M]=qiR\u0019Q\u0005P\u001f\t\u000b%\"\u0001\u0019\u0001\u0016\t\u000by\"\u0001\u0019\u0001\u0016\u0002\t\u0011,7\u000f^\u0001\u0011K:\u001c'/\u001f9uK\u00124\u0015\u000e\\3PkR$\"!\u0011#\u0011\u0005-\u0012\u0015BA\"-\u00051yU\u000f\u001e9viN#(/Z1n\u0011\u0015)U\u00011\u0001+\u0003\u00111\u0017\u000e\\3\u0002\u001f\u0011,7M]=qi\u0016$g)\u001b7f\u0013:$\"A\u000e%\t\u000b%2\u0001\u0019\u0001\u0016*\u0007\u0001QE*\u0003\u0002L\u0011\ti1*Z=F]\u000e\u0014\u0018\u0010\u001d;j_:T!!\u0014\u0005\u0002!9{g)\u001b7f\u000b:\u001c'/\u001f9uS>t\u0007")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/encryption/FileEncryption.class */
public interface FileEncryption {
    EncryptionAtRestPolicy policy();

    void encrypt(final File source, final File destination);

    void encrypt(final InputStream source, final File destination);

    void decrypt(final File source, final File dest);

    OutputStream encryptedFileOut(final File file);

    InputStream decryptedFileIn(final File source);
}
