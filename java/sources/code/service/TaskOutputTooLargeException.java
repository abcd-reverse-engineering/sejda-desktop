package code.service;

import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: exceptions.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005=b\u0001B\u000b\u0017\u0001nA\u0001\"\r\u0001\u0003\u0016\u0004%\tA\r\u0005\tm\u0001\u0011\t\u0012)A\u0005g!)q\u0007\u0001C\u0001q!9A\bAA\u0001\n\u0003i\u0004bB \u0001#\u0003%\t\u0001\u0011\u0005\b\u0017\u0002\t\t\u0011\"\u0011M\u0011\u001d)\u0006!!A\u0005\u0002YCqA\u0017\u0001\u0002\u0002\u0013\u00051\fC\u0004b\u0001\u0005\u0005I\u0011\t2\t\u000f%\u0004\u0011\u0011!C\u0001U\"9q\u000eAA\u0001\n\u0003\u0002\bb\u0002:\u0001\u0003\u0003%\te\u001d\u0005\bi\u0002\t\t\u0011\"\u0011v\u000f\u001d9h#!A\t\u0002a4q!\u0006\f\u0002\u0002#\u0005\u0011\u0010\u0003\u00048\u001f\u0011\u0005\u00111\u0002\u0005\n\u0003\u001by\u0011\u0011!C#\u0003\u001fA\u0011\"!\u0005\u0010\u0003\u0003%\t)a\u0005\t\u0013\u0005]q\"!A\u0005\u0002\u0006e\u0001\"CA\u0013\u001f\u0005\u0005I\u0011BA\u0014\u0005m!\u0016m]6PkR\u0004X\u000f\u001e+p_2\u000b'oZ3Fq\u000e,\u0007\u000f^5p]*\u0011q\u0003G\u0001\bg\u0016\u0014h/[2f\u0015\u0005I\u0012\u0001B2pI\u0016\u001c\u0001a\u0005\u0003\u00019)r\u0003CA\u000f(\u001d\tqBE\u0004\u0002 E5\t\u0001E\u0003\u0002\"5\u00051AH]8pizJ\u0011aI\u0001\u0006g\u000e\fG.Y\u0005\u0003K\u0019\nq\u0001]1dW\u0006<WMC\u0001$\u0013\tA\u0013F\u0001\tSk:$\u0018.\\3Fq\u000e,\u0007\u000f^5p]*\u0011QE\n\t\u0003W1j\u0011AJ\u0005\u0003[\u0019\u0012q\u0001\u0015:pIV\u001cG\u000f\u0005\u0002\u001e_%\u0011\u0001'\u000b\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.Z\u0001\u0006Ef$Xm]\u000b\u0002gA\u00111\u0006N\u0005\u0003k\u0019\u0012A\u0001T8oO\u00061!-\u001f;fg\u0002\na\u0001P5oSRtDCA\u001d<!\tQ\u0004!D\u0001\u0017\u0011\u0015\t4\u00011\u00014\u0003\u0011\u0019w\u000e]=\u0015\u0005er\u0004bB\u0019\u0005!\u0003\u0005\raM\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\u0005\t%FA\u001aCW\u0005\u0019\u0005C\u0001#J\u001b\u0005)%B\u0001$H\u0003%)hn\u00195fG.,GM\u0003\u0002IM\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005)+%!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006i\u0001O]8ek\u000e$\bK]3gSb,\u0012!\u0014\t\u0003\u001dNk\u0011a\u0014\u0006\u0003!F\u000bA\u0001\\1oO*\t!+\u0001\u0003kCZ\f\u0017B\u0001+P\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5usV\tq\u000b\u0005\u0002,1&\u0011\u0011L\n\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u00039~\u0003\"aK/\n\u0005y3#aA!os\"9\u0001\rCA\u0001\u0002\u00049\u0016a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/F\u0001d!\r!w\rX\u0007\u0002K*\u0011aMJ\u0001\u000bG>dG.Z2uS>t\u0017B\u00015f\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\u0005-t\u0007CA\u0016m\u0013\tigEA\u0004C_>dW-\u00198\t\u000f\u0001T\u0011\u0011!a\u00019\u0006\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\ti\u0015\u000fC\u0004a\u0017\u0005\u0005\t\u0019A,\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012aV\u0001\u0007KF,\u0018\r\\:\u0015\u0005-4\bb\u00021\u000e\u0003\u0003\u0005\r\u0001X\u0001\u001c)\u0006\u001c8nT;uaV$Hk\\8MCJ<W-\u0012=dKB$\u0018n\u001c8\u0011\u0005iz1\u0003B\b{\u0003\u0003\u0001Ba\u001f@4s5\tAP\u0003\u0002~M\u00059!/\u001e8uS6,\u0017BA@}\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|g.\r\t\u0005\u0003\u0007\tI!\u0004\u0002\u0002\u0006)\u0019\u0011qA)\u0002\u0005%|\u0017b\u0001\u0019\u0002\u0006Q\t\u00010\u0001\u0005u_N#(/\u001b8h)\u0005i\u0015!B1qa2LHcA\u001d\u0002\u0016!)\u0011G\u0005a\u0001g\u00059QO\\1qa2LH\u0003BA\u000e\u0003C\u0001BaKA\u000fg%\u0019\u0011q\u0004\u0014\u0003\r=\u0003H/[8o\u0011!\t\u0019cEA\u0001\u0002\u0004I\u0014a\u0001=%a\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u0011\u0011\u0006\t\u0004\u001d\u0006-\u0012bAA\u0017\u001f\n1qJ\u00196fGR\u0004")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/service/TaskOutputTooLargeException.class */
public class TaskOutputTooLargeException extends RuntimeException implements Product {
    private final long bytes;

    public static Option<Object> unapply(final TaskOutputTooLargeException x$0) {
        return TaskOutputTooLargeException$.MODULE$.unapply(x$0);
    }

    public static TaskOutputTooLargeException apply(final long bytes) {
        return TaskOutputTooLargeException$.MODULE$.apply(bytes);
    }

    public static <A> Function1<Object, A> andThen(final Function1<TaskOutputTooLargeException, A> g) {
        return TaskOutputTooLargeException$.MODULE$.andThen(g);
    }

    public static <A> Function1<A, TaskOutputTooLargeException> compose(final Function1<A, Object> g) {
        return TaskOutputTooLargeException$.MODULE$.compose(g);
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public long bytes() {
        return this.bytes;
    }

    public TaskOutputTooLargeException copy(final long bytes) {
        return new TaskOutputTooLargeException(bytes);
    }

    public long copy$default$1() {
        return bytes();
    }

    public String productPrefix() {
        return "TaskOutputTooLargeException";
    }

    public int productArity() {
        return 1;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return BoxesRunTime.boxToLong(bytes());
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof TaskOutputTooLargeException;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "bytes";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return Statics.finalizeHash(Statics.mix(Statics.mix(-889275714, productPrefix().hashCode()), Statics.longHash(bytes())), 1);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof TaskOutputTooLargeException) {
                TaskOutputTooLargeException taskOutputTooLargeException = (TaskOutputTooLargeException) x$1;
                if (bytes() != taskOutputTooLargeException.bytes() || !taskOutputTooLargeException.canEqual(this)) {
                }
            }
            return false;
        }
        return true;
    }

    public TaskOutputTooLargeException(final long bytes) {
        this.bytes = bytes;
        Product.$init$(this);
    }
}
