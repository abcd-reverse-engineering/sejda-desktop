package code.sejda.tasks.bookmark;

import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple5;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: EditBookmarksParameters.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005Me\u0001\u0002\u0012$\u00012B\u0001B\u0011\u0001\u0003\u0016\u0004%\ta\u0011\u0005\t\u0019\u0002\u0011\t\u0012)A\u0005\t\"AQ\n\u0001BK\u0002\u0013\u00051\t\u0003\u0005O\u0001\tE\t\u0015!\u0003E\u0011!y\u0005A!f\u0001\n\u0003\u0019\u0005\u0002\u0003)\u0001\u0005#\u0005\u000b\u0011\u0002#\t\u0011E\u0003!Q3A\u0005\u0002IC\u0001B\u0016\u0001\u0003\u0012\u0003\u0006Ia\u0015\u0005\t/\u0002\u0011)\u001a!C\u0001%\"A\u0001\f\u0001B\tB\u0003%1\u000bC\u0003Z\u0001\u0011\u0005!\fC\u0004c\u0001\u0005\u0005I\u0011A2\t\u000f%\u0004\u0011\u0013!C\u0001U\"9Q\u000fAI\u0001\n\u0003Q\u0007b\u0002<\u0001#\u0003%\tA\u001b\u0005\bo\u0002\t\n\u0011\"\u0001y\u0011\u001dQ\b!%A\u0005\u0002aDqa\u001f\u0001\u0002\u0002\u0013\u0005C\u0010\u0003\u0005\u0002\n\u0001\t\t\u0011\"\u0001S\u0011%\tY\u0001AA\u0001\n\u0003\ti\u0001C\u0005\u0002\u001a\u0001\t\t\u0011\"\u0011\u0002\u001c!I\u0011\u0011\u0006\u0001\u0002\u0002\u0013\u0005\u00111\u0006\u0005\n\u0003k\u0001\u0011\u0011!C!\u0003oA\u0011\"a\u000f\u0001\u0003\u0003%\t%!\u0010\t\u0013\u0005}\u0002!!A\u0005B\u0005\u0005\u0003\"CA\"\u0001\u0005\u0005I\u0011IA#\u000f%\tIeIA\u0001\u0012\u0003\tYE\u0002\u0005#G\u0005\u0005\t\u0012AA'\u0011\u0019IF\u0004\"\u0001\u0002f!I\u0011q\b\u000f\u0002\u0002\u0013\u0015\u0013\u0011\t\u0005\n\u0003Ob\u0012\u0011!CA\u0003SB\u0011\"!\u001e\u001d\u0003\u0003%\t)a\u001e\t\u0013\u0005%E$!A\u0005\n\u0005-%!D!eI\u0016$'i\\8l[\u0006\u00148N\u0003\u0002%K\u0005A!m\\8l[\u0006\u00148N\u0003\u0002'O\u0005)A/Y:lg*\u0011\u0001&K\u0001\u0006g\u0016TG-\u0019\u0006\u0002U\u0005!1m\u001c3f\u0007\u0001\u0019B\u0001A\u00174mA\u0011a&M\u0007\u0002_)\t\u0001'A\u0003tG\u0006d\u0017-\u0003\u00023_\t1\u0011I\\=SK\u001a\u0004\"A\f\u001b\n\u0005Uz#a\u0002)s_\u0012,8\r\u001e\t\u0003o}r!\u0001O\u001f\u000f\u0005ebT\"\u0001\u001e\u000b\u0005mZ\u0013A\u0002\u001fs_>$h(C\u00011\u0013\tqt&A\u0004qC\u000e\\\u0017mZ3\n\u0005\u0001\u000b%\u0001D*fe&\fG.\u001b>bE2,'B\u0001 0\u0003\u0015!X\u000e]%e+\u0005!\u0005CA#J\u001d\t1u\t\u0005\u0002:_%\u0011\u0001jL\u0001\u0007!J,G-\u001a4\n\u0005)[%AB*ue&twM\u0003\u0002I_\u00051A/\u001c9JI\u0002\n\u0001\u0002]1sK:$\u0018\nZ\u0001\na\u0006\u0014XM\u001c;JI\u0002\nQ\u0001^5uY\u0016\fa\u0001^5uY\u0016\u0004\u0013\u0001\u00029bO\u0016,\u0012a\u0015\t\u0003]QK!!V\u0018\u0003\u0007%sG/A\u0003qC\u001e,\u0007%A\u0003j]\u0012,\u00070\u0001\u0004j]\u0012,\u0007\u0010I\u0001\u0007y%t\u0017\u000e\u001e \u0015\rmkfl\u00181b!\ta\u0006!D\u0001$\u0011\u0015\u00115\u00021\u0001E\u0011\u0015i5\u00021\u0001E\u0011\u0015y5\u00021\u0001E\u0011\u0015\t6\u00021\u0001T\u0011\u001596\u00021\u0001T\u0003\u0011\u0019w\u000e]=\u0015\rm#WMZ4i\u0011\u001d\u0011E\u0002%AA\u0002\u0011Cq!\u0014\u0007\u0011\u0002\u0003\u0007A\tC\u0004P\u0019A\u0005\t\u0019\u0001#\t\u000fEc\u0001\u0013!a\u0001'\"9q\u000b\u0004I\u0001\u0002\u0004\u0019\u0016AD2paf$C-\u001a4bk2$H%M\u000b\u0002W*\u0012A\t\\\u0016\u0002[B\u0011an]\u0007\u0002_*\u0011\u0001/]\u0001\nk:\u001c\u0007.Z2lK\u0012T!A]\u0018\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002u_\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%e\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\u001a\u0014AD2paf$C-\u001a4bk2$H\u0005N\u000b\u0002s*\u00121\u000b\\\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00136\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\tQ\u0010E\u0002\u007f\u0003\u000fi\u0011a \u0006\u0005\u0003\u0003\t\u0019!\u0001\u0003mC:<'BAA\u0003\u0003\u0011Q\u0017M^1\n\u0005){\u0018\u0001\u00049s_\u0012,8\r^!sSRL\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0005\u0003\u001f\t)\u0002E\u0002/\u0003#I1!a\u00050\u0005\r\te.\u001f\u0005\t\u0003/!\u0012\u0011!a\u0001'\u0006\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\"!!\b\u0011\r\u0005}\u0011QEA\b\u001b\t\t\tCC\u0002\u0002$=\n!bY8mY\u0016\u001cG/[8o\u0013\u0011\t9#!\t\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0003[\t\u0019\u0004E\u0002/\u0003_I1!!\r0\u0005\u001d\u0011un\u001c7fC:D\u0011\"a\u0006\u0017\u0003\u0003\u0005\r!a\u0004\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0004{\u0006e\u0002\u0002CA\f/\u0005\u0005\t\u0019A*\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012aU\u0001\ti>\u001cFO]5oOR\tQ0\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0003[\t9\u0005C\u0005\u0002\u0018i\t\t\u00111\u0001\u0002\u0010\u0005i\u0011\t\u001a3fI\n{wn[7be.\u0004\"\u0001\u0018\u000f\u0014\u000bq\ty%a\u0017\u0011\u0015\u0005E\u0013q\u000b#E\tN\u001b6,\u0004\u0002\u0002T)\u0019\u0011QK\u0018\u0002\u000fI,h\u000e^5nK&!\u0011\u0011LA*\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|g.\u000e\t\u0005\u0003;\n\u0019'\u0004\u0002\u0002`)!\u0011\u0011MA\u0002\u0003\tIw.C\u0002A\u0003?\"\"!a\u0013\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u0017m\u000bY'!\u001c\u0002p\u0005E\u00141\u000f\u0005\u0006\u0005~\u0001\r\u0001\u0012\u0005\u0006\u001b~\u0001\r\u0001\u0012\u0005\u0006\u001f~\u0001\r\u0001\u0012\u0005\u0006#~\u0001\ra\u0015\u0005\u0006/~\u0001\raU\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\tI(!\"\u0011\u000b9\nY(a \n\u0007\u0005utF\u0001\u0004PaRLwN\u001c\t\t]\u0005\u0005E\t\u0012#T'&\u0019\u00111Q\u0018\u0003\rQ+\b\u000f\\36\u0011!\t9\tIA\u0001\u0002\u0004Y\u0016a\u0001=%a\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u0011Q\u0012\t\u0004}\u0006=\u0015bAAI\u007f\n1qJ\u00196fGR\u0004")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/bookmark/AddedBookmark.class */
public class AddedBookmark implements Product, Serializable {
    private final String tmpId;
    private final String parentId;
    private final String title;
    private final int page;
    private final int index;

    public static Option<Tuple5<String, String, String, Object, Object>> unapply(final AddedBookmark x$0) {
        return AddedBookmark$.MODULE$.unapply(x$0);
    }

    public static AddedBookmark apply(final String tmpId, final String parentId, final String title, final int page, final int index) {
        return AddedBookmark$.MODULE$.apply(tmpId, parentId, title, page, index);
    }

    public static Function1<Tuple5<String, String, String, Object, Object>, AddedBookmark> tupled() {
        return AddedBookmark$.MODULE$.tupled();
    }

    public static Function1<String, Function1<String, Function1<String, Function1<Object, Function1<Object, AddedBookmark>>>>> curried() {
        return AddedBookmark$.MODULE$.curried();
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String tmpId() {
        return this.tmpId;
    }

    public String parentId() {
        return this.parentId;
    }

    public String title() {
        return this.title;
    }

    public int page() {
        return this.page;
    }

    public int index() {
        return this.index;
    }

    public AddedBookmark copy(final String tmpId, final String parentId, final String title, final int page, final int index) {
        return new AddedBookmark(tmpId, parentId, title, page, index);
    }

    public String copy$default$1() {
        return tmpId();
    }

    public String copy$default$2() {
        return parentId();
    }

    public String copy$default$3() {
        return title();
    }

    public int copy$default$4() {
        return page();
    }

    public int copy$default$5() {
        return index();
    }

    public String productPrefix() {
        return "AddedBookmark";
    }

    public int productArity() {
        return 5;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return tmpId();
            case 1:
                return parentId();
            case 2:
                return title();
            case 3:
                return BoxesRunTime.boxToInteger(page());
            case 4:
                return BoxesRunTime.boxToInteger(index());
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof AddedBookmark;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "tmpId";
            case 1:
                return "parentId";
            case 2:
                return "title";
            case 3:
                return "page";
            case 4:
                return "index";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return Statics.finalizeHash(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(-889275714, productPrefix().hashCode()), Statics.anyHash(tmpId())), Statics.anyHash(parentId())), Statics.anyHash(title())), page()), index()), 5);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof AddedBookmark) {
                AddedBookmark addedBookmark = (AddedBookmark) x$1;
                if (page() == addedBookmark.page() && index() == addedBookmark.index()) {
                    String strTmpId = tmpId();
                    String strTmpId2 = addedBookmark.tmpId();
                    if (strTmpId != null ? strTmpId.equals(strTmpId2) : strTmpId2 == null) {
                        String strParentId = parentId();
                        String strParentId2 = addedBookmark.parentId();
                        if (strParentId != null ? strParentId.equals(strParentId2) : strParentId2 == null) {
                            String strTitle = title();
                            String strTitle2 = addedBookmark.title();
                            if (strTitle != null ? strTitle.equals(strTitle2) : strTitle2 == null) {
                                if (addedBookmark.canEqual(this)) {
                                }
                            }
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    public AddedBookmark(final String tmpId, final String parentId, final String title, final int page, final int index) {
        this.tmpId = tmpId;
        this.parentId = parentId;
        this.title = title;
        this.page = page;
        this.index = index;
        Product.$init$(this);
    }
}
