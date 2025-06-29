package code.sejda.tasks.bookmark;

import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple3;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: EditBookmarksParameters.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005Md\u0001\u0002\u000f\u001e\u0001\u001aB\u0001\u0002\u0010\u0001\u0003\u0016\u0004%\t!\u0010\u0005\t\r\u0002\u0011\t\u0012)A\u0005}!Aq\t\u0001BK\u0002\u0013\u0005Q\b\u0003\u0005I\u0001\tE\t\u0015!\u0003?\u0011!I\u0005A!f\u0001\n\u0003Q\u0005\u0002C)\u0001\u0005#\u0005\u000b\u0011B&\t\u000bI\u0003A\u0011A*\t\u000fe\u0003\u0011\u0011!C\u00015\"9a\fAI\u0001\n\u0003y\u0006b\u00026\u0001#\u0003%\ta\u0018\u0005\bW\u0002\t\n\u0011\"\u0001m\u0011\u001dq\u0007!!A\u0005B=Dqa\u001e\u0001\u0002\u0002\u0013\u0005\u0001\u0010C\u0004z\u0001\u0005\u0005I\u0011\u0001>\t\u0013\u0005\u0005\u0001!!A\u0005B\u0005\r\u0001\"CA\t\u0001\u0005\u0005I\u0011AA\n\u0011%\ti\u0002AA\u0001\n\u0003\ny\u0002C\u0005\u0002$\u0001\t\t\u0011\"\u0011\u0002&!I\u0011q\u0005\u0001\u0002\u0002\u0013\u0005\u0013\u0011\u0006\u0005\n\u0003W\u0001\u0011\u0011!C!\u0003[9\u0011\"!\r\u001e\u0003\u0003E\t!a\r\u0007\u0011qi\u0012\u0011!E\u0001\u0003kAaA\u0015\f\u0005\u0002\u00055\u0003\"CA\u0014-\u0005\u0005IQIA\u0015\u0011%\tyEFA\u0001\n\u0003\u000b\t\u0006C\u0005\u0002ZY\t\t\u0011\"!\u0002\\!I\u0011\u0011\u000e\f\u0002\u0002\u0013%\u00111\u000e\u0002\u0010+B$\u0017\r^3e\u0005>|7.\\1sW*\u0011adH\u0001\tE>|7.\\1sW*\u0011\u0001%I\u0001\u0006i\u0006\u001c8n\u001d\u0006\u0003E\r\nQa]3kI\u0006T\u0011\u0001J\u0001\u0005G>$Wm\u0001\u0001\u0014\t\u00019S\u0006\r\t\u0003Q-j\u0011!\u000b\u0006\u0002U\u0005)1oY1mC&\u0011A&\u000b\u0002\u0007\u0003:L(+\u001a4\u0011\u0005!r\u0013BA\u0018*\u0005\u001d\u0001&o\u001c3vGR\u0004\"!M\u001d\u000f\u0005I:dBA\u001a7\u001b\u0005!$BA\u001b&\u0003\u0019a$o\\8u}%\t!&\u0003\u00029S\u00059\u0001/Y2lC\u001e,\u0017B\u0001\u001e<\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\tA\u0014&\u0001\u0002jIV\ta\b\u0005\u0002@\u0007:\u0011\u0001)\u0011\t\u0003g%J!AQ\u0015\u0002\rA\u0013X\rZ3g\u0013\t!UI\u0001\u0004TiJLgn\u001a\u0006\u0003\u0005&\n1!\u001b3!\u0003\u0015!\u0018\u000e\u001e7f\u0003\u0019!\u0018\u000e\u001e7fA\u0005!\u0001/Y4f+\u0005Y\u0005c\u0001\u0015M\u001d&\u0011Q*\u000b\u0002\u0007\u001fB$\u0018n\u001c8\u0011\u0005!z\u0015B\u0001)*\u0005\rIe\u000e^\u0001\u0006a\u0006<W\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015\tQ3v\u000b\u0017\t\u0003+\u0002i\u0011!\b\u0005\u0006y\u001d\u0001\rA\u0010\u0005\u0006\u000f\u001e\u0001\rA\u0010\u0005\u0006\u0013\u001e\u0001\raS\u0001\u0005G>\u0004\u0018\u0010\u0006\u0003U7rk\u0006b\u0002\u001f\t!\u0003\u0005\rA\u0010\u0005\b\u000f\"\u0001\n\u00111\u0001?\u0011\u001dI\u0005\u0002%AA\u0002-\u000babY8qs\u0012\"WMZ1vYR$\u0013'F\u0001aU\tq\u0014mK\u0001c!\t\u0019\u0007.D\u0001e\u0015\t)g-A\u0005v]\u000eDWmY6fI*\u0011q-K\u0001\u000bC:tw\u000e^1uS>t\u0017BA5e\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00133\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIM*\u0012!\u001c\u0016\u0003\u0017\u0006\fQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DX#\u00019\u0011\u0005E4X\"\u0001:\u000b\u0005M$\u0018\u0001\u00027b]\u001eT\u0011!^\u0001\u0005U\u00064\u0018-\u0003\u0002Ee\u0006a\u0001O]8ek\u000e$\u0018I]5usV\ta*\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\u0005mt\bC\u0001\u0015}\u0013\ti\u0018FA\u0002B]fDqa \b\u0002\u0002\u0003\u0007a*A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0003\u0003\u000b\u0001R!a\u0002\u0002\u000eml!!!\u0003\u000b\u0007\u0005-\u0011&\u0001\u0006d_2dWm\u0019;j_:LA!a\u0004\u0002\n\tA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\u0011\t)\"a\u0007\u0011\u0007!\n9\"C\u0002\u0002\u001a%\u0012qAQ8pY\u0016\fg\u000eC\u0004��!\u0005\u0005\t\u0019A>\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0004a\u0006\u0005\u0002bB@\u0012\u0003\u0003\u0005\rAT\u0001\tQ\u0006\u001c\bnQ8eKR\ta*\u0001\u0005u_N#(/\u001b8h)\u0005\u0001\u0018AB3rk\u0006d7\u000f\u0006\u0003\u0002\u0016\u0005=\u0002bB@\u0015\u0003\u0003\u0005\ra_\u0001\u0010+B$\u0017\r^3e\u0005>|7.\\1sWB\u0011QKF\n\u0006-\u0005]\u00121\t\t\t\u0003s\tyD\u0010 L)6\u0011\u00111\b\u0006\u0004\u0003{I\u0013a\u0002:v]RLW.Z\u0005\u0005\u0003\u0003\nYDA\tBEN$(/Y2u\rVt7\r^5p]N\u0002B!!\u0012\u0002L5\u0011\u0011q\t\u0006\u0004\u0003\u0013\"\u0018AA5p\u0013\rQ\u0014q\t\u000b\u0003\u0003g\tQ!\u00199qYf$r\u0001VA*\u0003+\n9\u0006C\u0003=3\u0001\u0007a\bC\u0003H3\u0001\u0007a\bC\u0003J3\u0001\u00071*A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\u0005u\u0013Q\r\t\u0005Q1\u000by\u0006\u0005\u0004)\u0003CrdhS\u0005\u0004\u0003GJ#A\u0002+va2,7\u0007\u0003\u0005\u0002hi\t\t\u00111\u0001U\u0003\rAH\u0005M\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003[\u00022!]A8\u0013\r\t\tH\u001d\u0002\u0007\u001f\nTWm\u0019;")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/bookmark/UpdatedBookmark.class */
public class UpdatedBookmark implements Product, Serializable {
    private final String id;
    private final String title;
    private final Option<Object> page;

    public static Option<Tuple3<String, String, Option<Object>>> unapply(final UpdatedBookmark x$0) {
        return UpdatedBookmark$.MODULE$.unapply(x$0);
    }

    public static UpdatedBookmark apply(final String id, final String title, final Option<Object> page) {
        return UpdatedBookmark$.MODULE$.apply(id, title, page);
    }

    public static Function1<Tuple3<String, String, Option<Object>>, UpdatedBookmark> tupled() {
        return UpdatedBookmark$.MODULE$.tupled();
    }

    public static Function1<String, Function1<String, Function1<Option<Object>, UpdatedBookmark>>> curried() {
        return UpdatedBookmark$.MODULE$.curried();
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String id() {
        return this.id;
    }

    public String title() {
        return this.title;
    }

    public Option<Object> page() {
        return this.page;
    }

    public UpdatedBookmark copy(final String id, final String title, final Option<Object> page) {
        return new UpdatedBookmark(id, title, page);
    }

    public String copy$default$1() {
        return id();
    }

    public String copy$default$2() {
        return title();
    }

    public Option<Object> copy$default$3() {
        return page();
    }

    public String productPrefix() {
        return "UpdatedBookmark";
    }

    public int productArity() {
        return 3;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return id();
            case 1:
                return title();
            case 2:
                return page();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof UpdatedBookmark;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "id";
            case 1:
                return "title";
            case 2:
                return "page";
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
            if (x$1 instanceof UpdatedBookmark) {
                UpdatedBookmark updatedBookmark = (UpdatedBookmark) x$1;
                String strId = id();
                String strId2 = updatedBookmark.id();
                if (strId != null ? strId.equals(strId2) : strId2 == null) {
                    String strTitle = title();
                    String strTitle2 = updatedBookmark.title();
                    if (strTitle != null ? strTitle.equals(strTitle2) : strTitle2 == null) {
                        Option<Object> optionPage = page();
                        Option<Object> optionPage2 = updatedBookmark.page();
                        if (optionPage != null ? optionPage.equals(optionPage2) : optionPage2 == null) {
                            if (updatedBookmark.canEqual(this)) {
                            }
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    public UpdatedBookmark(final String id, final String title, final Option<Object> page) {
        this.id = id;
        this.title = title;
        this.page = page;
        Product.$init$(this);
    }
}
