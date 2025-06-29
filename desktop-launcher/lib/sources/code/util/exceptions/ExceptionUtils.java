package code.util.exceptions;

import scala.Option;
import scala.collection.Iterable;
import scala.collection.immutable.Seq;
import scala.reflect.ClassTag;
import scala.reflect.ScalaSignature;
import scala.util.matching.Regex;

/* compiled from: ExceptionUtils.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005}r!B\u0007\u000f\u0011\u0003)b!B\f\u000f\u0011\u0003A\u0002\"B\u0010\u0002\t\u0003\u0001\u0003\"B\u0011\u0002\t\u0003\u0011\u0003\"B!\u0002\t\u0003\u0011\u0005\"B\u0011\u0002\t\u0003)\u0005\"B&\u0002\t\u0003a\u0005b\u0002)\u0002\u0005\u0004%\t!\u0015\u0005\u0007C\u0006\u0001\u000b\u0011\u0002*\t\u000b\t\fA\u0011A2\t\u000f\u0005m\u0011\u0001\"\u0001\u0002\u001e!9\u00111E\u0001\u0005\u0002\u0005\u0015\u0002bBA\u0015\u0003\u0011\u0005\u00111F\u0001\u000f\u000bb\u001cW\r\u001d;j_:,F/\u001b7t\u0015\ty\u0001#\u0001\u0006fq\u000e,\u0007\u000f^5p]NT!!\u0005\n\u0002\tU$\u0018\u000e\u001c\u0006\u0002'\u0005!1m\u001c3f\u0007\u0001\u0001\"AF\u0001\u000e\u00039\u0011a\"\u0012=dKB$\u0018n\u001c8Vi&d7o\u0005\u0002\u00023A\u0011!$H\u0007\u00027)\tA$A\u0003tG\u0006d\u0017-\u0003\u0002\u001f7\t1\u0011I\\=SK\u001a\fa\u0001P5oSRtD#A\u000b\u0002\u001f\r|g\u000e^1j]N\u001cFO]5oON$2a\t\u00145!\tQB%\u0003\u0002&7\t9!i\\8mK\u0006t\u0007\"B\u0014\u0004\u0001\u0004A\u0013!\u0003;ie><\u0018M\u00197f!\tI\u0013G\u0004\u0002+_9\u00111FL\u0007\u0002Y)\u0011Q\u0006F\u0001\u0007yI|w\u000e\u001e \n\u0003qI!\u0001M\u000e\u0002\u000fA\f7m[1hK&\u0011!g\r\u0002\n)\"\u0014xn^1cY\u0016T!\u0001M\u000e\t\u000bU\u001a\u0001\u0019\u0001\u001c\u0002\u001bM,\u0017M]2i'R\u0014\u0018N\\4t!\rQr'O\u0005\u0003qm\u0011!\u0002\u0010:fa\u0016\fG/\u001a3?!\tQdH\u0004\u0002<yA\u00111fG\u0005\u0003{m\ta\u0001\u0015:fI\u00164\u0017BA A\u0005\u0019\u0019FO]5oO*\u0011QhG\u0001\u0013G>tG/Y5og\u0006cGn\u0015;sS:<7\u000fF\u0002$\u0007\u0012CQa\n\u0003A\u0002!BQ!\u000e\u0003A\u0002Y\"2a\t$H\u0011\u00159S\u00011\u0001)\u0011\u0015)T\u00011\u0001I!\rI\u0013*O\u0005\u0003\u0015N\u0012\u0001\"\u0013;fe\u0006\u0014G.Z\u0001\u000fG>tG/Y5ogN#(/\u001b8h)\r\u0019SJ\u0014\u0005\u0006O\u0019\u0001\r\u0001\u000b\u0005\u0006\u001f\u001a\u0001\r!O\u0001\rg\u0016\f'o\u00195TiJLgnZ\u0001\u0010I&\u001c8NR;mYN#(/\u001b8hgV\t!\u000bE\u0002T1jk\u0011\u0001\u0016\u0006\u0003+Z\u000b\u0011\"[7nkR\f'\r\\3\u000b\u0005][\u0012AC2pY2,7\r^5p]&\u0011\u0011\f\u0016\u0002\u0004'\u0016\f\bCA.a\u001b\u0005a&BA/_\u0003\u0011a\u0017M\\4\u000b\u0003}\u000bAA[1wC&\u0011q\bX\u0001\u0011I&\u001c8NR;mYN#(/\u001b8hg\u0002\nqB]3hKbl\u0015\r^2i)f\u0004X\rZ\u000b\u0003Iz$R!ZA\b\u0003#!\"A\u001a;\u0011\u0007i9\u0017.\u0003\u0002i7\t1q\n\u001d;j_:\u0004\"A[9\u000f\u0005-|W\"\u00017\u000b\u00055t\u0017\u0001C7bi\u000eD\u0017N\\4\u000b\u0005EY\u0012B\u00019m\u0003\u0015\u0011VmZ3y\u0013\t\u00118OA\u0003NCR\u001c\u0007N\u0003\u0002qY\"9Q/CA\u0001\u0002\b1\u0018AC3wS\u0012,gnY3%cA\u0019qO\u001f?\u000e\u0003aT!!_\u000e\u0002\u000fI,g\r\\3di&\u00111\u0010\u001f\u0002\t\u00072\f7o\u001d+bOB\u0011QP \u0007\u0001\t\u0019y\u0018B1\u0001\u0002\u0002\t\tA+\u0005\u0003\u0002\u0004\u0005%\u0001c\u0001\u000e\u0002\u0006%\u0019\u0011qA\u000e\u0003\u000f9{G\u000f[5oOB\u0019\u0011&a\u0003\n\u0007\u000551GA\u0005Fq\u000e,\u0007\u000f^5p]\")q%\u0003a\u0001Q!9\u00111C\u0005A\u0002\u0005U\u0011!\u0002:fO\u0016D\bcA6\u0002\u0018%\u0019\u0011\u0011\u00047\u0003\u000bI+w-\u001a=\u0002\u0015I,w-\u001a=NCR\u001c\u0007\u000eF\u0003g\u0003?\t\t\u0003C\u0003(\u0015\u0001\u0007\u0001\u0006C\u0004\u0002\u0014)\u0001\r!!\u0006\u0002\u0011\u0005\u001c8\u000b\u001e:j]\u001e$2!OA\u0014\u0011\u001593\u00021\u0001)\u0003\u00111\u0017N\u001c3\u0016\t\u00055\u0012Q\u0007\u000b\u0005\u0003_\ti\u0004\u0006\u0003\u00022\u0005]\u0002\u0003\u0002\u000eh\u0003g\u00012!`A\u001b\t\u0019yHB1\u0001\u0002\u0002!I\u0011\u0011\b\u0007\u0002\u0002\u0003\u000f\u00111H\u0001\u000bKZLG-\u001a8dK\u0012\u0012\u0004\u0003B<{\u0003gAQa\n\u0007A\u0002!\u0002")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/exceptions/ExceptionUtils.class */
public final class ExceptionUtils {
    public static <T extends Exception> Option<T> find(final Throwable throwable, final ClassTag<T> evidence$2) {
        return ExceptionUtils$.MODULE$.find(throwable, evidence$2);
    }

    public static String asString(final Throwable throwable) {
        return ExceptionUtils$.MODULE$.asString(throwable);
    }

    public static Option<Regex.Match> regexMatch(final Throwable throwable, final Regex regex) {
        return ExceptionUtils$.MODULE$.regexMatch(throwable, regex);
    }

    public static <T extends Exception> Option<Regex.Match> regexMatchTyped(final Throwable throwable, final Regex regex, final ClassTag<T> evidence$1) {
        return ExceptionUtils$.MODULE$.regexMatchTyped(throwable, regex, evidence$1);
    }

    public static Seq<String> diskFullStrings() {
        return ExceptionUtils$.MODULE$.diskFullStrings();
    }

    public static boolean containsString(final Throwable throwable, final String searchString) {
        return ExceptionUtils$.MODULE$.containsString(throwable, searchString);
    }

    public static boolean containsStrings(final Throwable throwable, final Iterable<String> searchStrings) {
        return ExceptionUtils$.MODULE$.containsStrings(throwable, searchStrings);
    }

    public static boolean containsAllStrings(final Throwable throwable, final Seq<String> searchStrings) {
        return ExceptionUtils$.MODULE$.containsAllStrings(throwable, searchStrings);
    }

    public static boolean containsStrings(final Throwable throwable, final Seq<String> searchStrings) {
        return ExceptionUtils$.MODULE$.containsStrings(throwable, searchStrings);
    }
}
