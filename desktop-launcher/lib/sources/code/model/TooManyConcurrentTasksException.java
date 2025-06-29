package code.model;

import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple4;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: exceptions.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005\u001de\u0001\u0002\u0011\"\u0001\u001aB\u0001\u0002\u0010\u0001\u0003\u0016\u0004%\t!\u0010\u0005\t\r\u0002\u0011\t\u0012)A\u0005}!Aq\t\u0001BK\u0002\u0013\u0005\u0001\n\u0003\u0005M\u0001\tE\t\u0015!\u0003J\u0011!i\u0005A!f\u0001\n\u0003A\u0005\u0002\u0003(\u0001\u0005#\u0005\u000b\u0011B%\t\u0011=\u0003!Q3A\u0005\u0002uB\u0001\u0002\u0015\u0001\u0003\u0012\u0003\u0006IA\u0010\u0005\u0006#\u0002!\tA\u0015\u0005\b3\u0002\t\t\u0011\"\u0001[\u0011\u001dy\u0006!%A\u0005\u0002\u0001Dqa\u001b\u0001\u0012\u0002\u0013\u0005A\u000eC\u0004o\u0001E\u0005I\u0011\u00017\t\u000f=\u0004\u0011\u0013!C\u0001A\"9\u0001\u000fAA\u0001\n\u0003\n\bbB=\u0001\u0003\u0003%\tA\u001f\u0005\b}\u0002\t\t\u0011\"\u0001��\u0011%\tY\u0001AA\u0001\n\u0003\ni\u0001C\u0005\u0002\u001c\u0001\t\t\u0011\"\u0001\u0002\u001e!I\u0011q\u0005\u0001\u0002\u0002\u0013\u0005\u0013\u0011\u0006\u0005\n\u0003[\u0001\u0011\u0011!C!\u0003_A\u0011\"!\r\u0001\u0003\u0003%\t%a\r\b\u0013\u0005]\u0012%!A\t\u0002\u0005eb\u0001\u0003\u0011\"\u0003\u0003E\t!a\u000f\t\rECB\u0011AA*\u0011%\t)\u0006GA\u0001\n\u000b\n9\u0006C\u0005\u0002Za\t\t\u0011\"!\u0002\\!A\u0011Q\r\r\u0012\u0002\u0013\u0005\u0001\rC\u0005\u0002ha\t\t\u0011\"!\u0002j!A\u00111\u0010\r\u0012\u0002\u0013\u0005\u0001\rC\u0005\u0002~a\t\t\u0011\"\u0003\u0002��\tyBk\\8NC:L8i\u001c8dkJ\u0014XM\u001c;UCN\\7/\u0012=dKB$\u0018n\u001c8\u000b\u0005\t\u001a\u0013!B7pI\u0016d'\"\u0001\u0013\u0002\t\r|G-Z\u0002\u0001'\u0011\u0001q%N\u001d\u0011\u0005!\u0012dBA\u00150\u001d\tQS&D\u0001,\u0015\taS%\u0001\u0004=e>|GOP\u0005\u0002]\u0005)1oY1mC&\u0011\u0001'M\u0001\ba\u0006\u001c7.Y4f\u0015\u0005q\u0013BA\u001a5\u0005A\u0011VO\u001c;j[\u0016,\u0005pY3qi&|gN\u0003\u00021cA\u0011agN\u0007\u0002c%\u0011\u0001(\r\u0002\b!J|G-^2u!\tA#(\u0003\u0002<i\ta1+\u001a:jC2L'0\u00192mK\u000611\r\\5f]R,\u0012A\u0010\t\u0003\u007f\rs!\u0001Q!\u0011\u0005)\n\u0014B\u0001\"2\u0003\u0019\u0001&/\u001a3fM&\u0011A)\u0012\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005\t\u000b\u0014aB2mS\u0016tG\u000fI\u0001\u0006kN\fw-Z\u000b\u0002\u0013B\u0011aGS\u0005\u0003\u0017F\u0012A\u0001T8oO\u00061Qo]1hK\u0002\nQ\u0001\\5nSR\fa\u0001\\5nSR\u0004\u0013!\u00023fEV<\u0017A\u00023fEV<\u0007%\u0001\u0004=S:LGO\u0010\u000b\u0006'V3v\u000b\u0017\t\u0003)\u0002i\u0011!\t\u0005\u0006y%\u0001\rA\u0010\u0005\u0006\u000f&\u0001\r!\u0013\u0005\u0006\u001b&\u0001\r!\u0013\u0005\b\u001f&\u0001\n\u00111\u0001?\u0003\u0011\u0019w\u000e]=\u0015\u000bM[F,\u00180\t\u000fqR\u0001\u0013!a\u0001}!9qI\u0003I\u0001\u0002\u0004I\u0005bB'\u000b!\u0003\u0005\r!\u0013\u0005\b\u001f*\u0001\n\u00111\u0001?\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\u0012!\u0019\u0016\u0003}\t\\\u0013a\u0019\t\u0003I&l\u0011!\u001a\u0006\u0003M\u001e\f\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005!\f\u0014AC1o]>$\u0018\r^5p]&\u0011!.\u001a\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017AD2paf$C-\u001a4bk2$HEM\u000b\u0002[*\u0012\u0011JY\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00134\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIQ\nQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DX#\u0001:\u0011\u0005MDX\"\u0001;\u000b\u0005U4\u0018\u0001\u00027b]\u001eT\u0011a^\u0001\u0005U\u00064\u0018-\u0003\u0002Ei\u0006a\u0001O]8ek\u000e$\u0018I]5usV\t1\u0010\u0005\u00027y&\u0011Q0\r\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0005\u0003\u0003\t9\u0001E\u00027\u0003\u0007I1!!\u00022\u0005\r\te.\u001f\u0005\t\u0003\u0013\t\u0012\u0011!a\u0001w\u0006\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\"!a\u0004\u0011\r\u0005E\u0011qCA\u0001\u001b\t\t\u0019BC\u0002\u0002\u0016E\n!bY8mY\u0016\u001cG/[8o\u0013\u0011\tI\"a\u0005\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0003?\t)\u0003E\u00027\u0003CI1!a\t2\u0005\u001d\u0011un\u001c7fC:D\u0011\"!\u0003\u0014\u0003\u0003\u0005\r!!\u0001\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0004e\u0006-\u0002\u0002CA\u0005)\u0005\u0005\t\u0019A>\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012a_\u0001\u0007KF,\u0018\r\\:\u0015\t\u0005}\u0011Q\u0007\u0005\n\u0003\u00131\u0012\u0011!a\u0001\u0003\u0003\tq\u0004V8p\u001b\u0006t\u0017pQ8oGV\u0014(/\u001a8u)\u0006\u001c8n]#yG\u0016\u0004H/[8o!\t!\u0006dE\u0003\u0019\u0003{\tI\u0005E\u0005\u0002@\u0005\u0015c(S%?'6\u0011\u0011\u0011\t\u0006\u0004\u0003\u0007\n\u0014a\u0002:v]RLW.Z\u0005\u0005\u0003\u000f\n\tEA\tBEN$(/Y2u\rVt7\r^5p]R\u0002B!a\u0013\u0002R5\u0011\u0011Q\n\u0006\u0004\u0003\u001f2\u0018AA5p\u0013\rY\u0014Q\n\u000b\u0003\u0003s\t\u0001\u0002^8TiJLgn\u001a\u000b\u0002e\u0006)\u0011\r\u001d9msRI1+!\u0018\u0002`\u0005\u0005\u00141\r\u0005\u0006ym\u0001\rA\u0010\u0005\u0006\u000fn\u0001\r!\u0013\u0005\u0006\u001bn\u0001\r!\u0013\u0005\b\u001fn\u0001\n\u00111\u0001?\u0003=\t\u0007\u000f\u001d7zI\u0011,g-Y;mi\u0012\"\u0014aB;oCB\u0004H.\u001f\u000b\u0005\u0003W\n9\bE\u00037\u0003[\n\t(C\u0002\u0002pE\u0012aa\u00149uS>t\u0007c\u0002\u001c\u0002tyJ\u0015JP\u0005\u0004\u0003k\n$A\u0002+va2,G\u0007\u0003\u0005\u0002zu\t\t\u00111\u0001T\u0003\rAH\u0005M\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000f\n\u001b\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\u0005\u0005\u0005cA:\u0002\u0004&\u0019\u0011Q\u0011;\u0003\r=\u0013'.Z2u\u0001")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/model/TooManyConcurrentTasksException.class */
public class TooManyConcurrentTasksException extends RuntimeException implements Product {
    private final String client;
    private final long usage;
    private final long limit;
    private final String debug;

    public static Option<Tuple4<String, Object, Object, String>> unapply(final TooManyConcurrentTasksException x$0) {
        return TooManyConcurrentTasksException$.MODULE$.unapply(x$0);
    }

    public static TooManyConcurrentTasksException apply(final String client, final long usage, final long limit, final String debug) {
        return TooManyConcurrentTasksException$.MODULE$.apply(client, usage, limit, debug);
    }

    public static Function1<Tuple4<String, Object, Object, String>, TooManyConcurrentTasksException> tupled() {
        return TooManyConcurrentTasksException$.MODULE$.tupled();
    }

    public static Function1<String, Function1<Object, Function1<Object, Function1<String, TooManyConcurrentTasksException>>>> curried() {
        return TooManyConcurrentTasksException$.MODULE$.curried();
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String client() {
        return this.client;
    }

    public long usage() {
        return this.usage;
    }

    public long limit() {
        return this.limit;
    }

    public String debug() {
        return this.debug;
    }

    public TooManyConcurrentTasksException copy(final String client, final long usage, final long limit, final String debug) {
        return new TooManyConcurrentTasksException(client, usage, limit, debug);
    }

    public String copy$default$1() {
        return client();
    }

    public long copy$default$2() {
        return usage();
    }

    public long copy$default$3() {
        return limit();
    }

    public String copy$default$4() {
        return debug();
    }

    public String productPrefix() {
        return "TooManyConcurrentTasksException";
    }

    public int productArity() {
        return 4;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return client();
            case 1:
                return BoxesRunTime.boxToLong(usage());
            case 2:
                return BoxesRunTime.boxToLong(limit());
            case 3:
                return debug();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof TooManyConcurrentTasksException;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "client";
            case 1:
                return "usage";
            case 2:
                return "limit";
            case 3:
                return "debug";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return Statics.finalizeHash(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(-889275714, productPrefix().hashCode()), Statics.anyHash(client())), Statics.longHash(usage())), Statics.longHash(limit())), Statics.anyHash(debug())), 4);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof TooManyConcurrentTasksException) {
                TooManyConcurrentTasksException tooManyConcurrentTasksException = (TooManyConcurrentTasksException) x$1;
                if (usage() == tooManyConcurrentTasksException.usage() && limit() == tooManyConcurrentTasksException.limit()) {
                    String strClient = client();
                    String strClient2 = tooManyConcurrentTasksException.client();
                    if (strClient != null ? strClient.equals(strClient2) : strClient2 == null) {
                        String strDebug = debug();
                        String strDebug2 = tooManyConcurrentTasksException.debug();
                        if (strDebug != null ? strDebug.equals(strDebug2) : strDebug2 == null) {
                            if (tooManyConcurrentTasksException.canEqual(this)) {
                            }
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TooManyConcurrentTasksException(final String client, final long usage, final long limit, final String debug) {
        super(new StringBuilder(32).append("Too many concurrent tasks for ").append(client).append(": ").append(usage).toString());
        this.client = client;
        this.usage = usage;
        this.limit = limit;
        this.debug = debug;
        Product.$init$(this);
    }
}
