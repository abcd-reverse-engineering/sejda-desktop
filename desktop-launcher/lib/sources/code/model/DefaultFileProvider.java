package code.model;

import java.io.File;
import org.sejda.model.encryption.NoEncryptionAtRest;
import org.sejda.model.input.FileSource;
import org.sejda.model.input.PdfSource;
import scala.Option;
import scala.reflect.ScalaSignature;

/* compiled from: FileProvider.scala */
@ScalaSignature(bytes = "\u0006\u0005=<Q\u0001D\u0007\t\u0002I1Q\u0001F\u0007\t\u0002UAQaH\u0001\u0005\u0002\u0001Bq!I\u0001C\u0002\u0013\u0005!\u0005\u0003\u0004,\u0003\u0001\u0006Ia\t\u0005\u0006Y\u0005!\t!\f\u0005\u0006\u0015\u0006!\ta\u0013\u0005\u0006!\u0006!\t!\u0015\u0005\u0006%\u0006!\ta\u0015\u0005\u0006-\u0006!\ta\u0016\u0005\u0006C\u0006!\tE\u0019\u0005\u0006Q\u0006!\t%[\u0001\u0014\t\u00164\u0017-\u001e7u\r&dW\r\u0015:pm&$WM\u001d\u0006\u0003\u001d=\tQ!\\8eK2T\u0011\u0001E\u0001\u0005G>$Wm\u0001\u0001\u0011\u0005M\tQ\"A\u0007\u0003'\u0011+g-Y;mi\u001aKG.\u001a)s_ZLG-\u001a:\u0014\u0007\u00051B\u0004\u0005\u0002\u001855\t\u0001DC\u0001\u001a\u0003\u0015\u00198-\u00197b\u0013\tY\u0002D\u0001\u0004B]f\u0014VM\u001a\t\u0003'uI!AH\u0007\u0003\u0019\u0019KG.\u001a)s_ZLG-\u001a:\u0002\rqJg.\u001b;?)\u0005\u0011\u0012AB:fGJ,G/F\u0001$!\t!\u0013&D\u0001&\u0015\t1s%\u0001\u0003mC:<'\"\u0001\u0015\u0002\t)\fg/Y\u0005\u0003U\u0015\u0012aa\u0015;sS:<\u0017aB:fGJ,G\u000fI\u0001\rO\u0016$\b\u000b\u001a4T_V\u00148-\u001a\u000b\u0003]}\u00022aL\u001c:\u001b\u0005\u0001$BA\u00193\u0003\u0015Ig\u000e];u\u0015\tq1G\u0003\u00025k\u0005)1/\u001a6eC*\ta'A\u0002pe\u001eL!\u0001\u000f\u0019\u0003\u0013A#gmU8ve\u000e,\u0007C\u0001\u001e>\u001b\u0005Y$B\u0001\u001f(\u0003\tIw.\u0003\u0002?w\t!a)\u001b7f\u0011\u0015\tT\u00011\u0001A!\t\t\u0005J\u0004\u0002C\rB\u00111\tG\u0007\u0002\t*\u0011Q)E\u0001\u0007yI|w\u000e\u001e \n\u0005\u001dC\u0012A\u0002)sK\u0012,g-\u0003\u0002+\u0013*\u0011q\tG\u0001\nO\u0016$8k\\;sG\u0016$\"\u0001T(\u0011\u0005=j\u0015B\u0001(1\u0005)1\u0015\u000e\\3T_V\u00148-\u001a\u0005\u0006c\u0019\u0001\r\u0001Q\u0001\rGJ,\u0017\r^3U[B$\u0015N\u001d\u000b\u0002s\u0005i1M]3bi\u0016$V\u000e\u001d$jY\u0016$\"!\u000f+\t\u000bUC\u0001\u0019\u0001!\u0002\t9\fW.Z\u0001\u0005g\u00064X\rF\u0002M1\u0002DQ!W\u0005A\u0002i\u000bQAY=uKN\u00042aF.^\u0013\ta\u0006DA\u0003BeJ\f\u0017\u0010\u0005\u0002\u0018=&\u0011q\f\u0007\u0002\u0005\u0005f$X\rC\u0003V\u0013\u0001\u0007\u0001)\u0001\u0006f]\u000e\u0014\u0018\u0010\u001d;j_:,\u0012a\u0019\t\u0003I\u001al\u0011!\u001a\u0006\u0003CJJ!aZ3\u0003%9{WI\\2ssB$\u0018n\u001c8BiJ+7\u000f^\u0001\u0012M&dWM\\1nK>\u0013H)\u001a4bk2$HC\u0001!k\u0011\u0015Y7\u00021\u0001m\u0003-1\u0017\u000e\\3oC6,w\n\u001d;\u0011\u0007]i\u0007)\u0003\u0002o1\t1q\n\u001d;j_:\u0004")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/model/DefaultFileProvider.class */
public final class DefaultFileProvider {
    public static String filenameOrDefault(final Option<String> filenameOpt) {
        return DefaultFileProvider$.MODULE$.filenameOrDefault(filenameOpt);
    }

    public static NoEncryptionAtRest encryption() {
        return DefaultFileProvider$.MODULE$.encryption();
    }

    public static FileSource save(final byte[] bytes, final String name) {
        return DefaultFileProvider$.MODULE$.save(bytes, name);
    }

    public static File createTmpFile(final String name) {
        return DefaultFileProvider$.MODULE$.createTmpFile(name);
    }

    public static File createTmpDir() {
        return DefaultFileProvider$.MODULE$.createTmpDir();
    }

    public static FileSource getSource(final String input) {
        return DefaultFileProvider$.MODULE$.getSource(input);
    }

    public static PdfSource<File> getPdfSource(final String input) {
        return DefaultFileProvider$.MODULE$.getPdfSource(input);
    }

    public static String secret() {
        return DefaultFileProvider$.MODULE$.secret();
    }
}
