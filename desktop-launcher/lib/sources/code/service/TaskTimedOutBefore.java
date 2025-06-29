package code.service;

import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: exceptions.scala */
@ScalaSignature(bytes = "\u0006\u0005i4AAE\nA1!)a\u0006\u0001C\u0001_!9!\u0007AA\u0001\n\u0003y\u0003bB\u001a\u0001\u0003\u0003%\t\u0005\u000e\u0005\b{\u0001\t\t\u0011\"\u0001?\u0011\u001d\u0011\u0005!!A\u0005\u0002\rCq!\u0013\u0001\u0002\u0002\u0013\u0005#\nC\u0004R\u0001\u0005\u0005I\u0011\u0001*\t\u000f]\u0003\u0011\u0011!C!1\"9!\fAA\u0001\n\u0003Z\u0006b\u0002/\u0001\u0003\u0003%\t%X\u0004\b?N\t\t\u0011#\u0001a\r\u001d\u00112#!A\t\u0002\u0005DQA\f\u0007\u0005\u00025DqA\u001c\u0007\u0002\u0002\u0013\u0015s\u000eC\u0004q\u0019\u0005\u0005I\u0011Q\u0018\t\u000fEd\u0011\u0011!CAe\"9Q\u000fDA\u0001\n\u00131(A\u0005+bg.$\u0016.\\3e\u001fV$()\u001a4pe\u0016T!\u0001F\u000b\u0002\u000fM,'O^5dK*\ta#\u0001\u0003d_\u0012,7\u0001A\n\u0005\u0001e93\u0006\u0005\u0002\u001bI9\u00111$\t\b\u00039}i\u0011!\b\u0006\u0003=]\ta\u0001\u0010:p_Rt\u0014\"\u0001\u0011\u0002\u000bM\u001c\u0017\r\\1\n\u0005\t\u001a\u0013a\u00029bG.\fw-\u001a\u0006\u0002A%\u0011QE\n\u0002\u0011%VtG/[7f\u000bb\u001cW\r\u001d;j_:T!AI\u0012\u0011\u0005!JS\"A\u0012\n\u0005)\u001a#a\u0002)s_\u0012,8\r\u001e\t\u000351J!!\f\u0014\u0003\u0019M+'/[1mSj\f'\r\\3\u0002\rqJg.\u001b;?)\u0005\u0001\u0004CA\u0019\u0001\u001b\u0005\u0019\u0012\u0001B2paf\fQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DX#A\u001b\u0011\u0005YZT\"A\u001c\u000b\u0005aJ\u0014\u0001\u00027b]\u001eT\u0011AO\u0001\u0005U\u00064\u0018-\u0003\u0002=o\t11\u000b\u001e:j]\u001e\fA\u0002\u001d:pIV\u001cG/\u0011:jif,\u0012a\u0010\t\u0003Q\u0001K!!Q\u0012\u0003\u0007%sG/\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\u0005\u0011;\u0005C\u0001\u0015F\u0013\t15EA\u0002B]fDq\u0001S\u0003\u0002\u0002\u0003\u0007q(A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0002\u0017B\u0019Aj\u0014#\u000e\u00035S!AT\u0012\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0002Q\u001b\nA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\t\u0019f\u000b\u0005\u0002))&\u0011Qk\t\u0002\b\u0005>|G.Z1o\u0011\u001dAu!!AA\u0002\u0011\u000b!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR\u0011Q'\u0017\u0005\b\u0011\"\t\t\u00111\u0001@\u0003!A\u0017m\u001d5D_\u0012,G#A \u0002\r\u0015\fX/\u00197t)\t\u0019f\fC\u0004I\u0015\u0005\u0005\t\u0019\u0001#\u0002%Q\u000b7o\u001b+j[\u0016$w*\u001e;CK\u001a|'/\u001a\t\u0003c1\u00192\u0001\u00042i!\r\u0019g\rM\u0007\u0002I*\u0011QmI\u0001\beVtG/[7f\u0013\t9GMA\tBEN$(/Y2u\rVt7\r^5p]B\u0002\"!\u001b7\u000e\u0003)T!a[\u001d\u0002\u0005%|\u0017BA\u0017k)\u0005\u0001\u0017\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003U\nQ!\u00199qYf\fq!\u001e8baBd\u0017\u0010\u0006\u0002Tg\"9A\u000fEA\u0001\u0002\u0004\u0001\u0014a\u0001=%a\u0005aqO]5uKJ+\u0007\u000f\\1dKR\tq\u000f\u0005\u00027q&\u0011\u0011p\u000e\u0002\u0007\u001f\nTWm\u0019;")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/service/TaskTimedOutBefore.class */
public class TaskTimedOutBefore extends RuntimeException implements Product {
    public static boolean unapply(final TaskTimedOutBefore x$0) {
        return TaskTimedOutBefore$.MODULE$.unapply(x$0);
    }

    public static TaskTimedOutBefore apply() {
        return TaskTimedOutBefore$.MODULE$.m124apply();
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public TaskTimedOutBefore copy() {
        return new TaskTimedOutBefore();
    }

    public String productPrefix() {
        return "TaskTimedOutBefore";
    }

    public int productArity() {
        return 0;
    }

    public Object productElement(final int x$1) {
        return Statics.ioobe(x$1);
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof TaskTimedOutBefore;
    }

    public String productElementName(final int x$1) {
        return (String) Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    public boolean equals(final Object x$1) {
        return (x$1 instanceof TaskTimedOutBefore) && ((TaskTimedOutBefore) x$1).canEqual(this);
    }

    public TaskTimedOutBefore() {
        Product.$init$(this);
    }
}
