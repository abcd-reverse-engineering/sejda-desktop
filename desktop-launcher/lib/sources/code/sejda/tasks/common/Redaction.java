package code.sejda.tasks.common;

import java.io.Serializable;
import org.sejda.model.TopLeftRectangularBox;
import org.sejda.sambox.pdmodel.PDPage;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple5;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: PageTextRedactor.scala */
@ScalaSignature(bytes = "\u0006\u0005\u00055g\u0001\u0002\u0012$\u00012B\u0001B\u0011\u0001\u0003\u0016\u0004%\ta\u0011\u0005\t\u001f\u0002\u0011\t\u0012)A\u0005\t\"A\u0001\u000b\u0001BK\u0002\u0013\u0005\u0011\u000b\u0003\u0005V\u0001\tE\t\u0015!\u0003S\u0011!1\u0006A!f\u0001\n\u00039\u0006\u0002\u00031\u0001\u0005#\u0005\u000b\u0011\u0002-\t\u0011\u0005\u0004!Q3A\u0005\u0002\tD\u0001\"\u001b\u0001\u0003\u0012\u0003\u0006Ia\u0019\u0005\tU\u0002\u0011)\u001a!C\u0001W\"A\u0001\u000f\u0001B\tB\u0003%A\u000eC\u0003r\u0001\u0011\u0005!\u000fC\u0004z\u0001\u0005\u0005I\u0011\u0001>\t\u0013\u0005\u0005\u0001!%A\u0005\u0002\u0005\r\u0001\"CA\r\u0001E\u0005I\u0011AA\u000e\u0011%\ty\u0002AI\u0001\n\u0003\t\t\u0003C\u0005\u0002&\u0001\t\n\u0011\"\u0001\u0002(!I\u00111\u0006\u0001\u0012\u0002\u0013\u0005\u0011Q\u0006\u0005\n\u0003c\u0001\u0011\u0011!C!\u0003gA\u0001\"a\u0011\u0001\u0003\u0003%\t!\u0015\u0005\n\u0003\u000b\u0002\u0011\u0011!C\u0001\u0003\u000fB\u0011\"a\u0015\u0001\u0003\u0003%\t%!\u0016\t\u0013\u0005\r\u0004!!A\u0005\u0002\u0005\u0015\u0004\"CA8\u0001\u0005\u0005I\u0011IA9\u0011%\t)\bAA\u0001\n\u0003\n9\bC\u0005\u0002z\u0001\t\t\u0011\"\u0011\u0002|!I\u0011Q\u0010\u0001\u0002\u0002\u0013\u0005\u0013qP\u0004\n\u0003\u0007\u001b\u0013\u0011!E\u0001\u0003\u000b3\u0001BI\u0012\u0002\u0002#\u0005\u0011q\u0011\u0005\u0007cr!\t!a(\t\u0013\u0005eD$!A\u0005F\u0005m\u0004\"CAQ9\u0005\u0005I\u0011QAR\u0011%\ty\u000bHA\u0001\n\u0003\u000b\t\fC\u0005\u0002Dr\t\t\u0011\"\u0003\u0002F\nI!+\u001a3bGRLwN\u001c\u0006\u0003I\u0015\naaY8n[>t'B\u0001\u0014(\u0003\u0015!\u0018m]6t\u0015\tA\u0013&A\u0003tK*$\u0017MC\u0001+\u0003\u0011\u0019w\u000eZ3\u0004\u0001M!\u0001!L\u001a7!\tq\u0013'D\u00010\u0015\u0005\u0001\u0014!B:dC2\f\u0017B\u0001\u001a0\u0005\u0019\te.\u001f*fMB\u0011a\u0006N\u0005\u0003k=\u0012q\u0001\u0015:pIV\u001cG\u000f\u0005\u00028\u007f9\u0011\u0001(\u0010\b\u0003sqj\u0011A\u000f\u0006\u0003w-\na\u0001\u0010:p_Rt\u0014\"\u0001\u0019\n\u0005yz\u0013a\u00029bG.\fw-Z\u0005\u0003\u0001\u0006\u0013AbU3sS\u0006d\u0017N_1cY\u0016T!AP\u0018\u0002\tA\fw-Z\u000b\u0002\tB\u0011Q)T\u0007\u0002\r*\u0011q\tS\u0001\ba\u0012lw\u000eZ3m\u0015\tI%*\u0001\u0004tC6\u0014w\u000e\u001f\u0006\u0003Q-S\u0011\u0001T\u0001\u0004_J<\u0017B\u0001(G\u0005\u0019\u0001F\tU1hK\u0006)\u0001/Y4fA\u0005Q\u0001/Y4f\u001dVl'-\u001a:\u0016\u0003I\u0003\"AL*\n\u0005Q{#aA%oi\u0006Y\u0001/Y4f\u001dVl'-\u001a:!\u00031y'/[4j]\u0006dG+\u001a=u+\u0005A\u0006CA-^\u001d\tQ6\f\u0005\u0002:_%\u0011AlL\u0001\u0007!J,G-\u001a4\n\u0005y{&AB*ue&twM\u0003\u0002]_\u0005iqN]5hS:\fG\u000eV3yi\u0002\n1BY8v]\u0012Lgn\u001a\"pqV\t1\r\u0005\u0002eO6\tQM\u0003\u0002g\u0015\u0006)Qn\u001c3fY&\u0011\u0001.\u001a\u0002\u0016)>\u0004H*\u001a4u%\u0016\u001cG/\u00198hk2\f'OQ8y\u00031\u0011w.\u001e8eS:<'i\u001c=!\u0003-\u0011X\r\u001d7bG\u0016lWM\u001c;\u0016\u00031\u0004\"!\u001c8\u000e\u0003\rJ!a\\\u0012\u0003\u0017I+\u0007\u000f\\1dK6,g\u000e^\u0001\re\u0016\u0004H.Y2f[\u0016tG\u000fI\u0001\u0007y%t\u0017\u000e\u001e \u0015\rM$XO^<y!\ti\u0007\u0001C\u0003C\u0017\u0001\u0007A\tC\u0003Q\u0017\u0001\u0007!\u000bC\u0003W\u0017\u0001\u0007\u0001\fC\u0003b\u0017\u0001\u00071\rC\u0003k\u0017\u0001\u0007A.\u0001\u0003d_BLHCB:|yvtx\u0010C\u0004C\u0019A\u0005\t\u0019\u0001#\t\u000fAc\u0001\u0013!a\u0001%\"9a\u000b\u0004I\u0001\u0002\u0004A\u0006bB1\r!\u0003\u0005\ra\u0019\u0005\bU2\u0001\n\u00111\u0001m\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\"!!\u0002+\u0007\u0011\u000b9a\u000b\u0002\u0002\nA!\u00111BA\u000b\u001b\t\tiA\u0003\u0003\u0002\u0010\u0005E\u0011!C;oG\",7m[3e\u0015\r\t\u0019bL\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BA\f\u0003\u001b\u0011\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII*\"!!\b+\u0007I\u000b9!\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001a\u0016\u0005\u0005\r\"f\u0001-\u0002\b\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\"TCAA\u0015U\r\u0019\u0017qA\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00136+\t\tyCK\u0002m\u0003\u000f\tQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DXCAA\u001b!\u0011\t9$!\u0011\u000e\u0005\u0005e\"\u0002BA\u001e\u0003{\tA\u0001\\1oO*\u0011\u0011qH\u0001\u0005U\u00064\u0018-C\u0002_\u0003s\tA\u0002\u001d:pIV\u001cG/\u0011:jif\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0003\u0002J\u0005=\u0003c\u0001\u0018\u0002L%\u0019\u0011QJ\u0018\u0003\u0007\u0005s\u0017\u0010\u0003\u0005\u0002RQ\t\t\u00111\u0001S\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u0011q\u000b\t\u0007\u00033\ny&!\u0013\u000e\u0005\u0005m#bAA/_\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\t\u0005\u0005\u00141\f\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0003\u0002h\u00055\u0004c\u0001\u0018\u0002j%\u0019\u00111N\u0018\u0003\u000f\t{w\u000e\\3b]\"I\u0011\u0011\u000b\f\u0002\u0002\u0003\u0007\u0011\u0011J\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\r\u0006\u0003\u00026\u0005M\u0004\u0002CA)/\u0005\u0005\t\u0019\u0001*\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012AU\u0001\ti>\u001cFO]5oOR\u0011\u0011QG\u0001\u0007KF,\u0018\r\\:\u0015\t\u0005\u001d\u0014\u0011\u0011\u0005\n\u0003#R\u0012\u0011!a\u0001\u0003\u0013\n\u0011BU3eC\u000e$\u0018n\u001c8\u0011\u00055d2#\u0002\u000f\u0002\n\u0006U\u0005CCAF\u0003##%\u000bW2mg6\u0011\u0011Q\u0012\u0006\u0004\u0003\u001f{\u0013a\u0002:v]RLW.Z\u0005\u0005\u0003'\u000biIA\tBEN$(/Y2u\rVt7\r^5p]V\u0002B!a&\u0002\u001e6\u0011\u0011\u0011\u0014\u0006\u0005\u00037\u000bi$\u0001\u0002j_&\u0019\u0001)!'\u0015\u0005\u0005\u0015\u0015!B1qa2LHcC:\u0002&\u0006\u001d\u0016\u0011VAV\u0003[CQAQ\u0010A\u0002\u0011CQ\u0001U\u0010A\u0002ICQAV\u0010A\u0002aCQ!Y\u0010A\u0002\rDQA[\u0010A\u00021\fq!\u001e8baBd\u0017\u0010\u0006\u0003\u00024\u0006}\u0006#\u0002\u0018\u00026\u0006e\u0016bAA\\_\t1q\n\u001d;j_:\u0004\u0002BLA^\tJC6\r\\\u0005\u0004\u0003{{#A\u0002+va2,W\u0007\u0003\u0005\u0002B\u0002\n\t\u00111\u0001t\u0003\rAH\u0005M\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003\u000f\u0004B!a\u000e\u0002J&!\u00111ZA\u001d\u0005\u0019y%M[3di\u0002")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/Redaction.class */
public class Redaction implements Product, Serializable {
    private final PDPage page;
    private final int pageNumber;
    private final String originalText;
    private final TopLeftRectangularBox boundingBox;
    private final Replacement replacement;

    public static Option<Tuple5<PDPage, Object, String, TopLeftRectangularBox, Replacement>> unapply(final Redaction x$0) {
        return Redaction$.MODULE$.unapply(x$0);
    }

    public static Redaction apply(final PDPage page, final int pageNumber, final String originalText, final TopLeftRectangularBox boundingBox, final Replacement replacement) {
        return Redaction$.MODULE$.apply(page, pageNumber, originalText, boundingBox, replacement);
    }

    public static Function1<Tuple5<PDPage, Object, String, TopLeftRectangularBox, Replacement>, Redaction> tupled() {
        return Redaction$.MODULE$.tupled();
    }

    public static Function1<PDPage, Function1<Object, Function1<String, Function1<TopLeftRectangularBox, Function1<Replacement, Redaction>>>>> curried() {
        return Redaction$.MODULE$.curried();
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public PDPage page() {
        return this.page;
    }

    public Redaction copy(final PDPage page, final int pageNumber, final String originalText, final TopLeftRectangularBox boundingBox, final Replacement replacement) {
        return new Redaction(page, pageNumber, originalText, boundingBox, replacement);
    }

    public PDPage copy$default$1() {
        return page();
    }

    public String productPrefix() {
        return "Redaction";
    }

    public int productArity() {
        return 5;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return page();
            case 1:
                return BoxesRunTime.boxToInteger(pageNumber());
            case 2:
                return originalText();
            case 3:
                return boundingBox();
            case 4:
                return replacement();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof Redaction;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "page";
            case 1:
                return "pageNumber";
            case 2:
                return "originalText";
            case 3:
                return "boundingBox";
            case 4:
                return "replacement";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return Statics.finalizeHash(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(-889275714, productPrefix().hashCode()), Statics.anyHash(page())), pageNumber()), Statics.anyHash(originalText())), Statics.anyHash(boundingBox())), Statics.anyHash(replacement())), 5);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof Redaction) {
                Redaction redaction = (Redaction) x$1;
                if (pageNumber() == redaction.pageNumber()) {
                    PDPage pDPagePage = page();
                    PDPage pDPagePage2 = redaction.page();
                    if (pDPagePage != null ? pDPagePage.equals(pDPagePage2) : pDPagePage2 == null) {
                        String strOriginalText = originalText();
                        String strOriginalText2 = redaction.originalText();
                        if (strOriginalText != null ? strOriginalText.equals(strOriginalText2) : strOriginalText2 == null) {
                            TopLeftRectangularBox topLeftRectangularBoxBoundingBox = boundingBox();
                            TopLeftRectangularBox topLeftRectangularBoxBoundingBox2 = redaction.boundingBox();
                            if (topLeftRectangularBoxBoundingBox != null ? topLeftRectangularBoxBoundingBox.equals(topLeftRectangularBoxBoundingBox2) : topLeftRectangularBoxBoundingBox2 == null) {
                                Replacement replacement = replacement();
                                Replacement replacement2 = redaction.replacement();
                                if (replacement != null ? replacement.equals(replacement2) : replacement2 == null) {
                                    if (redaction.canEqual(this)) {
                                    }
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

    public Redaction(final PDPage page, final int pageNumber, final String originalText, final TopLeftRectangularBox boundingBox, final Replacement replacement) {
        this.page = page;
        this.pageNumber = pageNumber;
        this.originalText = originalText;
        this.boundingBox = boundingBox;
        this.replacement = replacement;
        Product.$init$(this);
    }

    public int pageNumber() {
        return this.pageNumber;
    }

    public int copy$default$2() {
        return pageNumber();
    }

    public String originalText() {
        return this.originalText;
    }

    public String copy$default$3() {
        return originalText();
    }

    public TopLeftRectangularBox boundingBox() {
        return this.boundingBox;
    }

    public TopLeftRectangularBox copy$default$4() {
        return boundingBox();
    }

    public Replacement replacement() {
        return this.replacement;
    }

    public Replacement copy$default$5() {
        return replacement();
    }
}
