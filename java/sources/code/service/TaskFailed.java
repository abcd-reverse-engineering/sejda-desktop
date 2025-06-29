package code.service;

import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: exceptions.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005]b\u0001B\u000b\u0017\u0001nA\u0001\"\r\u0001\u0003\u0016\u0004%\tA\r\u0005\tw\u0001\u0011\t\u0012)A\u0005g!)A\b\u0001C\u0001{!9\u0011\tAA\u0001\n\u0003\u0011\u0005b\u0002#\u0001#\u0003%\t!\u0012\u0005\b!\u0002\t\t\u0011\"\u0011R\u0011\u001dI\u0006!!A\u0005\u0002iCqA\u0018\u0001\u0002\u0002\u0013\u0005q\fC\u0004f\u0001\u0005\u0005I\u0011\t4\t\u000f5\u0004\u0011\u0011!C\u0001]\"91\u000fAA\u0001\n\u0003\"\bb\u0002<\u0001\u0003\u0003%\te\u001e\u0005\bq\u0002\t\t\u0011\"\u0011z\u000f\u001dYh#!A\t\u0002q4q!\u0006\f\u0002\u0002#\u0005Q\u0010\u0003\u0004=\u001f\u0011\u0005\u00111\u0003\u0005\n\u0003+y\u0011\u0011!C#\u0003/A\u0011\"!\u0007\u0010\u0003\u0003%\t)a\u0007\t\u0013\u0005}q\"!A\u0005\u0002\u0006\u0005\u0002\"CA\u0017\u001f\u0005\u0005I\u0011BA\u0018\u0005)!\u0016m]6GC&dW\r\u001a\u0006\u0003/a\tqa]3sm&\u001cWMC\u0001\u001a\u0003\u0011\u0019w\u000eZ3\u0004\u0001M!\u0001\u0001\b\u0016/!\tirE\u0004\u0002\u001fI9\u0011qDI\u0007\u0002A)\u0011\u0011EG\u0001\u0007yI|w\u000e\u001e \n\u0003\r\nQa]2bY\u0006L!!\n\u0014\u0002\u000fA\f7m[1hK*\t1%\u0003\u0002)S\t\u0001\"+\u001e8uS6,W\t_2faRLwN\u001c\u0006\u0003K\u0019\u0002\"a\u000b\u0017\u000e\u0003\u0019J!!\f\u0014\u0003\u000fA\u0013x\u000eZ;diB\u0011QdL\u0005\u0003a%\u0012AbU3sS\u0006d\u0017N_1cY\u0016\f1!\\:h+\u0005\u0019\u0004C\u0001\u001b9\u001d\t)d\u0007\u0005\u0002 M%\u0011qGJ\u0001\u0007!J,G-\u001a4\n\u0005eR$AB*ue&twM\u0003\u00028M\u0005!Qn]4!\u0003\u0019a\u0014N\\5u}Q\u0011a\b\u0011\t\u0003\u007f\u0001i\u0011A\u0006\u0005\u0006c\r\u0001\raM\u0001\u0005G>\u0004\u0018\u0010\u0006\u0002?\u0007\"9\u0011\u0007\u0002I\u0001\u0002\u0004\u0019\u0014AD2paf$C-\u001a4bk2$H%M\u000b\u0002\r*\u00121gR\u0016\u0002\u0011B\u0011\u0011JT\u0007\u0002\u0015*\u00111\nT\u0001\nk:\u001c\u0007.Z2lK\u0012T!!\u0014\u0014\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002P\u0015\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u0005\u0011\u0006CA*Y\u001b\u0005!&BA+W\u0003\u0011a\u0017M\\4\u000b\u0003]\u000bAA[1wC&\u0011\u0011\bV\u0001\raJ|G-^2u\u0003JLG/_\u000b\u00027B\u00111\u0006X\u0005\u0003;\u001a\u00121!\u00138u\u00039\u0001(o\u001c3vGR,E.Z7f]R$\"\u0001Y2\u0011\u0005-\n\u0017B\u00012'\u0005\r\te.\u001f\u0005\bI\"\t\t\u00111\u0001\\\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\tq\rE\u0002iW\u0002l\u0011!\u001b\u0006\u0003U\u001a\n!bY8mY\u0016\u001cG/[8o\u0013\ta\u0017N\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dGCA8s!\tY\u0003/\u0003\u0002rM\t9!i\\8mK\u0006t\u0007b\u00023\u000b\u0003\u0003\u0005\r\u0001Y\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\r\u0006\u0002Sk\"9AmCA\u0001\u0002\u0004Y\u0016\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003m\u000ba!Z9vC2\u001cHCA8{\u0011\u001d!W\"!AA\u0002\u0001\f!\u0002V1tW\u001a\u000b\u0017\u000e\\3e!\tytb\u0005\u0003\u0010}\u0006%\u0001#B@\u0002\u0006MrTBAA\u0001\u0015\r\t\u0019AJ\u0001\beVtG/[7f\u0013\u0011\t9!!\u0001\u0003#\u0005\u00137\u000f\u001e:bGR4UO\\2uS>t\u0017\u0007\u0005\u0003\u0002\f\u0005EQBAA\u0007\u0015\r\tyAV\u0001\u0003S>L1\u0001MA\u0007)\u0005a\u0018\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003I\u000bQ!\u00199qYf$2APA\u000f\u0011\u0015\t$\u00031\u00014\u0003\u001d)h.\u00199qYf$B!a\t\u0002*A!1&!\n4\u0013\r\t9C\n\u0002\u0007\u001fB$\u0018n\u001c8\t\u0011\u0005-2#!AA\u0002y\n1\u0001\u001f\u00131\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\t\t\u0004E\u0002T\u0003gI1!!\u000eU\u0005\u0019y%M[3di\u0002")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/service/TaskFailed.class */
public class TaskFailed extends RuntimeException implements Product {
    private final String msg;

    public static Option<String> unapply(final TaskFailed x$0) {
        return TaskFailed$.MODULE$.unapply(x$0);
    }

    public static TaskFailed apply(final String msg) {
        return TaskFailed$.MODULE$.apply(msg);
    }

    public static <A> Function1<String, A> andThen(final Function1<TaskFailed, A> g) {
        return TaskFailed$.MODULE$.andThen(g);
    }

    public static <A> Function1<A, TaskFailed> compose(final Function1<A, String> g) {
        return TaskFailed$.MODULE$.compose(g);
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String msg() {
        return this.msg;
    }

    public TaskFailed copy(final String msg) {
        return new TaskFailed(msg);
    }

    public String copy$default$1() {
        return msg();
    }

    public String productPrefix() {
        return "TaskFailed";
    }

    public int productArity() {
        return 1;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return msg();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof TaskFailed;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "msg";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof TaskFailed) {
                TaskFailed taskFailed = (TaskFailed) x$1;
                String strMsg = msg();
                String strMsg2 = taskFailed.msg();
                if (strMsg != null ? strMsg.equals(strMsg2) : strMsg2 == null) {
                    if (taskFailed.canEqual(this)) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TaskFailed(final String msg) {
        super(msg);
        this.msg = msg;
        Product.$init$(this);
    }
}
