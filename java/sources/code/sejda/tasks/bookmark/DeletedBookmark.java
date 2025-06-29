package code.sejda.tasks.bookmark;

import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: EditBookmarksParameters.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005\u0005c\u0001\u0002\f\u0018\u0001\u0002B\u0001B\u000e\u0001\u0003\u0016\u0004%\ta\u000e\u0005\t\u0001\u0002\u0011\t\u0012)A\u0005q!)\u0011\t\u0001C\u0001\u0005\"9a\tAA\u0001\n\u00039\u0005bB%\u0001#\u0003%\tA\u0013\u0005\b+\u0002\t\t\u0011\"\u0011W\u0011\u001dq\u0006!!A\u0005\u0002}Cqa\u0019\u0001\u0002\u0002\u0013\u0005A\rC\u0004k\u0001\u0005\u0005I\u0011I6\t\u000fI\u0004\u0011\u0011!C\u0001g\"9\u0001\u0010AA\u0001\n\u0003J\bbB>\u0001\u0003\u0003%\t\u0005 \u0005\b{\u0002\t\t\u0011\"\u0011\u007f\u0011!y\b!!A\u0005B\u0005\u0005q!CA\u0003/\u0005\u0005\t\u0012AA\u0004\r!1r#!A\t\u0002\u0005%\u0001BB!\u0011\t\u0003\t\t\u0003C\u0004~!\u0005\u0005IQ\t@\t\u0013\u0005\r\u0002#!A\u0005\u0002\u0006\u0015\u0002\"CA\u0015!\u0005\u0005I\u0011QA\u0016\u0011%\t9\u0004EA\u0001\n\u0013\tIDA\bEK2,G/\u001a3C_>\\W.\u0019:l\u0015\tA\u0012$\u0001\u0005c_>\\W.\u0019:l\u0015\tQ2$A\u0003uCN\\7O\u0003\u0002\u001d;\u0005)1/\u001a6eC*\ta$\u0001\u0003d_\u0012,7\u0001A\n\u0005\u0001\u0005:#\u0006\u0005\u0002#K5\t1EC\u0001%\u0003\u0015\u00198-\u00197b\u0013\t13E\u0001\u0004B]f\u0014VM\u001a\t\u0003E!J!!K\u0012\u0003\u000fA\u0013x\u000eZ;diB\u00111f\r\b\u0003YEr!!\f\u0019\u000e\u00039R!aL\u0010\u0002\rq\u0012xn\u001c;?\u0013\u0005!\u0013B\u0001\u001a$\u0003\u001d\u0001\u0018mY6bO\u0016L!\u0001N\u001b\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005I\u001a\u0013AA5e+\u0005A\u0004CA\u001d>\u001d\tQ4\b\u0005\u0002.G%\u0011AhI\u0001\u0007!J,G-\u001a4\n\u0005yz$AB*ue&twM\u0003\u0002=G\u0005\u0019\u0011\u000e\u001a\u0011\u0002\rqJg.\u001b;?)\t\u0019U\t\u0005\u0002E\u00015\tq\u0003C\u00037\u0007\u0001\u0007\u0001(\u0001\u0003d_BLHCA\"I\u0011\u001d1D\u0001%AA\u0002a\nabY8qs\u0012\"WMZ1vYR$\u0013'F\u0001LU\tADjK\u0001N!\tq5+D\u0001P\u0015\t\u0001\u0016+A\u0005v]\u000eDWmY6fI*\u0011!kI\u0001\u000bC:tw\u000e^1uS>t\u0017B\u0001+P\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0003]\u0003\"\u0001W/\u000e\u0003eS!AW.\u0002\t1\fgn\u001a\u0006\u00029\u0006!!.\u0019<b\u0013\tq\u0014,\u0001\u0007qe>$Wo\u0019;Be&$\u00180F\u0001a!\t\u0011\u0013-\u0003\u0002cG\t\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0011Q\r\u001b\t\u0003E\u0019L!aZ\u0012\u0003\u0007\u0005s\u0017\u0010C\u0004j\u0011\u0005\u0005\t\u0019\u00011\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\u0005a\u0007cA7qK6\taN\u0003\u0002pG\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u0005Et'\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$\"\u0001^<\u0011\u0005\t*\u0018B\u0001<$\u0005\u001d\u0011un\u001c7fC:Dq!\u001b\u0006\u0002\u0002\u0003\u0007Q-\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,GCA,{\u0011\u001dI7\"!AA\u0002\u0001\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002A\u0006AAo\\*ue&tw\rF\u0001X\u0003\u0019)\u0017/^1mgR\u0019A/a\u0001\t\u000f%t\u0011\u0011!a\u0001K\u0006yA)\u001a7fi\u0016$'i\\8l[\u0006\u00148\u000e\u0005\u0002E!M)\u0001#a\u0003\u0002\u0018A1\u0011QBA\nq\rk!!a\u0004\u000b\u0007\u0005E1%A\u0004sk:$\u0018.\\3\n\t\u0005U\u0011q\u0002\u0002\u0012\u0003\n\u001cHO]1di\u001a+hn\u0019;j_:\f\u0004\u0003BA\r\u0003?i!!a\u0007\u000b\u0007\u0005u1,\u0001\u0002j_&\u0019A'a\u0007\u0015\u0005\u0005\u001d\u0011!B1qa2LHcA\"\u0002(!)ag\u0005a\u0001q\u00059QO\\1qa2LH\u0003BA\u0017\u0003g\u0001BAIA\u0018q%\u0019\u0011\u0011G\u0012\u0003\r=\u0003H/[8o\u0011!\t)\u0004FA\u0001\u0002\u0004\u0019\u0015a\u0001=%a\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u00111\b\t\u00041\u0006u\u0012bAA 3\n1qJ\u00196fGR\u0004")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/bookmark/DeletedBookmark.class */
public class DeletedBookmark implements Product, Serializable {
    private final String id;

    public static Option<String> unapply(final DeletedBookmark x$0) {
        return DeletedBookmark$.MODULE$.unapply(x$0);
    }

    public static DeletedBookmark apply(final String id) {
        return DeletedBookmark$.MODULE$.apply(id);
    }

    public static <A> Function1<String, A> andThen(final Function1<DeletedBookmark, A> g) {
        return DeletedBookmark$.MODULE$.andThen(g);
    }

    public static <A> Function1<A, DeletedBookmark> compose(final Function1<A, String> g) {
        return DeletedBookmark$.MODULE$.compose(g);
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String id() {
        return this.id;
    }

    public DeletedBookmark copy(final String id) {
        return new DeletedBookmark(id);
    }

    public String copy$default$1() {
        return id();
    }

    public String productPrefix() {
        return "DeletedBookmark";
    }

    public int productArity() {
        return 1;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return id();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof DeletedBookmark;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "id";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof DeletedBookmark) {
                DeletedBookmark deletedBookmark = (DeletedBookmark) x$1;
                String strId = id();
                String strId2 = deletedBookmark.id();
                if (strId != null ? strId.equals(strId2) : strId2 == null) {
                    if (deletedBookmark.canEqual(this)) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    public DeletedBookmark(final String id) {
        this.id = id;
        Product.$init$(this);
    }
}
