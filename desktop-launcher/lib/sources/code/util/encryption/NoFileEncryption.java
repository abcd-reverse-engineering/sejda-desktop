package code.util.encryption;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import org.sejda.model.encryption.EncryptionAtRestPolicy;
import scala.reflect.ScalaSignature;

/* compiled from: FileEncryption.scala */
@ScalaSignature(bytes = "\u0006\u0005I;Q!\u0003\u0006\t\u0002E1Qa\u0005\u0006\t\u0002QAQAH\u0001\u0005\u0002}AQ\u0001I\u0001\u0005B\u0005BQ!L\u0001\u0005B9BQ!L\u0001\u0005ByBQ\u0001R\u0001\u0005B\u0015CQ\u0001S\u0001\u0005B%CQaT\u0001\u0005BA\u000b\u0001CT8GS2,WI\\2ssB$\u0018n\u001c8\u000b\u0005-a\u0011AC3oGJL\b\u000f^5p]*\u0011QBD\u0001\u0005kRLGNC\u0001\u0010\u0003\u0011\u0019w\u000eZ3\u0004\u0001A\u0011!#A\u0007\u0002\u0015\t\u0001bj\u001c$jY\u0016,en\u0019:zaRLwN\\\n\u0004\u0003UY\u0002C\u0001\f\u001a\u001b\u00059\"\"\u0001\r\u0002\u000bM\u001c\u0017\r\\1\n\u0005i9\"AB!osJ+g\r\u0005\u0002\u00139%\u0011QD\u0003\u0002\u000f\r&dW-\u00128def\u0004H/[8o\u0003\u0019a\u0014N\\5u}Q\t\u0011#\u0001\u0004q_2L7-_\u000b\u0002EA\u00111eK\u0007\u0002I)\u00111\"\n\u0006\u0003M\u001d\nQ!\\8eK2T!\u0001K\u0015\u0002\u000bM,'\u000eZ1\u000b\u0003)\n1a\u001c:h\u0013\taCE\u0001\fF]\u000e\u0014\u0018\u0010\u001d;j_:\fEOU3tiB{G.[2z\u0003\u001d)gn\u0019:zaR$2a\f\u001a=!\t1\u0002'\u0003\u00022/\t!QK\\5u\u0011\u0015\u0019D\u00011\u00015\u0003\u0019\u0019x.\u001e:dKB\u0011QGO\u0007\u0002m)\u0011q\u0007O\u0001\u0003S>T\u0011!O\u0001\u0005U\u00064\u0018-\u0003\u0002<m\t!a)\u001b7f\u0011\u0015iD\u00011\u00015\u0003\u0011!Wm\u001d;\u0015\u0007=z4\tC\u00034\u000b\u0001\u0007\u0001\t\u0005\u00026\u0003&\u0011!I\u000e\u0002\f\u0013:\u0004X\u000f^*ue\u0016\fW\u000eC\u0003>\u000b\u0001\u0007A'A\u0004eK\u000e\u0014\u0018\u0010\u001d;\u0015\u0007=2u\tC\u00034\r\u0001\u0007A\u0007C\u0003>\r\u0001\u0007A'\u0001\tf]\u000e\u0014\u0018\u0010\u001d;fI\u001aKG.Z(viR\u0011!*\u0014\t\u0003k-K!\u0001\u0014\u001c\u0003\u0019=+H\u000f];u'R\u0014X-Y7\t\u000b9;\u0001\u0019\u0001\u001b\u0002\t\u0019LG.Z\u0001\u0010I\u0016\u001c'/\u001f9uK\u00124\u0015\u000e\\3J]R\u0011\u0001)\u0015\u0005\u0006g!\u0001\r\u0001\u000e")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/encryption/NoFileEncryption.class */
public final class NoFileEncryption {
    public static InputStream decryptedFileIn(final File source) {
        return NoFileEncryption$.MODULE$.decryptedFileIn(source);
    }

    public static OutputStream encryptedFileOut(final File file) {
        return NoFileEncryption$.MODULE$.encryptedFileOut(file);
    }

    public static void decrypt(final File source, final File dest) {
        NoFileEncryption$.MODULE$.decrypt(source, dest);
    }

    public static void encrypt(final InputStream source, final File dest) {
        NoFileEncryption$.MODULE$.encrypt(source, dest);
    }

    public static void encrypt(final File source, final File dest) {
        NoFileEncryption$.MODULE$.encrypt(source, dest);
    }

    public static EncryptionAtRestPolicy policy() {
        return NoFileEncryption$.MODULE$.policy();
    }
}
