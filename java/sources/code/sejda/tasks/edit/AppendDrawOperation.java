package code.sejda.tasks.edit;

import java.awt.Color;
import java.io.Serializable;
import org.sejda.model.RectangularBox;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple6;
import scala.collection.Iterator;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: EditParameters.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005mg\u0001B\u0013'\u0001>B\u0001\"\u0012\u0001\u0003\u0016\u0004%\tA\u0012\u0005\t\u0015\u0002\u0011\t\u0012)A\u0005\u000f\"A1\n\u0001BK\u0002\u0013\u0005A\n\u0003\u0005W\u0001\tE\t\u0015!\u0003N\u0011!9\u0006A!f\u0001\n\u0003A\u0006\u0002C1\u0001\u0005#\u0005\u000b\u0011B-\t\u0011\t\u0004!Q3A\u0005\u0002\rD\u0001B\u001b\u0001\u0003\u0012\u0003\u0006I\u0001\u001a\u0005\tW\u0002\u0011)\u001a!C\u0001Y\"Aa\u000e\u0001B\tB\u0003%Q\u000e\u0003\u0005p\u0001\tU\r\u0011\"\u0001q\u0011!\t\bA!E!\u0002\u00139\u0007\"\u0002:\u0001\t\u0003\u0019\bb\u0002?\u0001\u0003\u0003%\t! \u0005\n\u0003\u0013\u0001\u0011\u0013!C\u0001\u0003\u0017A\u0011\"!\t\u0001#\u0003%\t!a\t\t\u0013\u0005\u001d\u0002!%A\u0005\u0002\u0005%\u0002\"CA\u0017\u0001E\u0005I\u0011AA\u0018\u0011%\t\u0019\u0004AI\u0001\n\u0003\t)\u0004C\u0005\u0002:\u0001\t\n\u0011\"\u0001\u0002<!I\u0011q\b\u0001\u0002\u0002\u0013\u0005\u0013\u0011\t\u0005\t\u0003\u001f\u0002\u0011\u0011!C\u0001\r\"I\u0011\u0011\u000b\u0001\u0002\u0002\u0013\u0005\u00111\u000b\u0005\n\u0003?\u0002\u0011\u0011!C!\u0003CB\u0011\"a\u001c\u0001\u0003\u0003%\t!!\u001d\t\u0013\u0005m\u0004!!A\u0005B\u0005u\u0004\"CAA\u0001\u0005\u0005I\u0011IAB\u0011%\t)\tAA\u0001\n\u0003\n9\tC\u0005\u0002\n\u0002\t\t\u0011\"\u0011\u0002\f\u001eI\u0011q\u0012\u0014\u0002\u0002#\u0005\u0011\u0011\u0013\u0004\tK\u0019\n\t\u0011#\u0001\u0002\u0014\"1!o\bC\u0001\u0003WC\u0011\"!\" \u0003\u0003%)%a\"\t\u0013\u00055v$!A\u0005\u0002\u0006=\u0006\"CA_?\u0005\u0005I\u0011QA`\u0011%\t\tnHA\u0001\n\u0013\t\u0019NA\nBaB,g\u000e\u001a#sC^|\u0005/\u001a:bi&|gN\u0003\u0002(Q\u0005!Q\rZ5u\u0015\tI#&A\u0003uCN\\7O\u0003\u0002,Y\u0005)1/\u001a6eC*\tQ&\u0001\u0003d_\u0012,7\u0001A\n\u0005\u0001A2\u0014\b\u0005\u00022i5\t!GC\u00014\u0003\u0015\u00198-\u00197b\u0013\t)$G\u0001\u0004B]f\u0014VM\u001a\t\u0003c]J!\u0001\u000f\u001a\u0003\u000fA\u0013x\u000eZ;diB\u0011!H\u0011\b\u0003w\u0001s!\u0001P \u000e\u0003uR!A\u0010\u0018\u0002\rq\u0012xn\u001c;?\u0013\u0005\u0019\u0014BA!3\u0003\u001d\u0001\u0018mY6bO\u0016L!a\u0011#\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005\u0005\u0013\u0014A\u00039bO\u0016tU/\u001c2feV\tq\t\u0005\u00022\u0011&\u0011\u0011J\r\u0002\u0004\u0013:$\u0018a\u00039bO\u0016tU/\u001c2fe\u0002\n1BY8v]\u0012Lgn\u001a\"pqV\tQ\n\u0005\u0002O)6\tqJ\u0003\u0002Q#\u0006)Qn\u001c3fY*\u00111F\u0015\u0006\u0002'\u0006\u0019qN]4\n\u0005U{%A\u0004*fGR\fgnZ;mCJ\u0014u\u000e_\u0001\rE>,h\u000eZ5oO\n{\u0007\u0010I\u0001\u0006G>dwN]\u000b\u00023B\u0011!lX\u0007\u00027*\u0011A,X\u0001\u0004C^$(\"\u00010\u0002\t)\fg/Y\u0005\u0003An\u0013QaQ8m_J\faaY8m_J\u0004\u0013aB5oW2L7\u000f^\u000b\u0002IB\u0019!(Z4\n\u0005\u0019$%aA*fcB\u0011\u0011\u0007[\u0005\u0003SJ\u0012a\u0001R8vE2,\u0017\u0001C5oW2L7\u000f\u001e\u0011\u0002\u0015\u0005\u0004\b/Z1sC:\u001cW-F\u0001n!\rQT\rZ\u0001\fCB\u0004X-\u0019:b]\u000e,\u0007%\u0001\u0005mS:,7+\u001b>f+\u00059\u0017!\u00037j]\u0016\u001c\u0016N_3!\u0003\u0019a\u0014N\\5u}Q9AO^<ysj\\\bCA;\u0001\u001b\u00051\u0003\"B#\u000e\u0001\u00049\u0005\"B&\u000e\u0001\u0004i\u0005\"B,\u000e\u0001\u0004I\u0006\"\u00022\u000e\u0001\u0004!\u0007\"B6\u000e\u0001\u0004i\u0007\"B8\u000e\u0001\u00049\u0017\u0001B2paf$2\u0002\u001e@��\u0003\u0003\t\u0019!!\u0002\u0002\b!9QI\u0004I\u0001\u0002\u00049\u0005bB&\u000f!\u0003\u0005\r!\u0014\u0005\b/:\u0001\n\u00111\u0001Z\u0011\u001d\u0011g\u0002%AA\u0002\u0011Dqa\u001b\b\u0011\u0002\u0003\u0007Q\u000eC\u0004p\u001dA\u0005\t\u0019A4\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\u0011\u0011Q\u0002\u0016\u0004\u000f\u0006=1FAA\t!\u0011\t\u0019\"!\b\u000e\u0005\u0005U!\u0002BA\f\u00033\t\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0007\u0005m!'\u0001\u0006b]:|G/\u0019;j_:LA!a\b\u0002\u0016\t\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%eU\u0011\u0011Q\u0005\u0016\u0004\u001b\u0006=\u0011AD2paf$C-\u001a4bk2$HeM\u000b\u0003\u0003WQ3!WA\b\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIQ*\"!!\r+\u0007\u0011\fy!\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001b\u0016\u0005\u0005]\"fA7\u0002\u0010\u0005q1m\u001c9zI\u0011,g-Y;mi\u00122TCAA\u001fU\r9\u0017qB\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\u0005\r\u0003\u0003BA#\u0003\u0017j!!a\u0012\u000b\u0007\u0005%S,\u0001\u0003mC:<\u0017\u0002BA'\u0003\u000f\u0012aa\u0015;sS:<\u0017\u0001\u00049s_\u0012,8\r^!sSRL\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0005\u0003+\nY\u0006E\u00022\u0003/J1!!\u00173\u0005\r\te.\u001f\u0005\t\u0003;:\u0012\u0011!a\u0001\u000f\u0006\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\"!a\u0019\u0011\r\u0005\u0015\u00141NA+\u001b\t\t9GC\u0002\u0002jI\n!bY8mY\u0016\u001cG/[8o\u0013\u0011\ti'a\u001a\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0003g\nI\bE\u00022\u0003kJ1!a\u001e3\u0005\u001d\u0011un\u001c7fC:D\u0011\"!\u0018\u001a\u0003\u0003\u0005\r!!\u0016\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0005\u0003\u0007\ny\b\u0003\u0005\u0002^i\t\t\u00111\u0001H\u0003!A\u0017m\u001d5D_\u0012,G#A$\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!a\u0011\u0002\r\u0015\fX/\u00197t)\u0011\t\u0019(!$\t\u0013\u0005uS$!AA\u0002\u0005U\u0013aE!qa\u0016tG\r\u0012:bo>\u0003XM]1uS>t\u0007CA; '\u0015y\u0012QSAQ!-\t9*!(H\u001bf#Wn\u001a;\u000e\u0005\u0005e%bAANe\u00059!/\u001e8uS6,\u0017\u0002BAP\u00033\u0013\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c87!\u0011\t\u0019+!+\u000e\u0005\u0005\u0015&bAAT;\u0006\u0011\u0011n\\\u0005\u0004\u0007\u0006\u0015FCAAI\u0003\u0015\t\u0007\u000f\u001d7z)5!\u0018\u0011WAZ\u0003k\u000b9,!/\u0002<\")QI\ta\u0001\u000f\")1J\ta\u0001\u001b\")qK\ta\u00013\")!M\ta\u0001I\")1N\ta\u0001[\")qN\ta\u0001O\u00069QO\\1qa2LH\u0003BAa\u0003\u001b\u0004R!MAb\u0003\u000fL1!!23\u0005\u0019y\u0005\u000f^5p]BI\u0011'!3H\u001bf#WnZ\u0005\u0004\u0003\u0017\u0014$A\u0002+va2,g\u0007\u0003\u0005\u0002P\u000e\n\t\u00111\u0001u\u0003\rAH\u0005M\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003+\u0004B!!\u0012\u0002X&!\u0011\u0011\\A$\u0005\u0019y%M[3di\u0002")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/AppendDrawOperation.class */
public class AppendDrawOperation implements Product, Serializable {
    private final int pageNumber;
    private final RectangularBox boundingBox;
    private final Color color;
    private final Seq<Object> inklist;
    private final Seq<Seq<Object>> appearance;
    private final double lineSize;

    public static Option<Tuple6<Object, RectangularBox, Color, Seq<Object>, Seq<Seq<Object>>, Object>> unapply(final AppendDrawOperation x$0) {
        return AppendDrawOperation$.MODULE$.unapply(x$0);
    }

    public static AppendDrawOperation apply(final int pageNumber, final RectangularBox boundingBox, final Color color, final Seq<Object> inklist, final Seq<Seq<Object>> appearance, final double lineSize) {
        return AppendDrawOperation$.MODULE$.apply(pageNumber, boundingBox, color, inklist, appearance, lineSize);
    }

    public static Function1<Tuple6<Object, RectangularBox, Color, Seq<Object>, Seq<Seq<Object>>, Object>, AppendDrawOperation> tupled() {
        return AppendDrawOperation$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<RectangularBox, Function1<Color, Function1<Seq<Object>, Function1<Seq<Seq<Object>>, Function1<Object, AppendDrawOperation>>>>>> curried() {
        return AppendDrawOperation$.MODULE$.curried();
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public int pageNumber() {
        return this.pageNumber;
    }

    public RectangularBox boundingBox() {
        return this.boundingBox;
    }

    public Color color() {
        return this.color;
    }

    public Seq<Object> inklist() {
        return this.inklist;
    }

    public Seq<Seq<Object>> appearance() {
        return this.appearance;
    }

    public double lineSize() {
        return this.lineSize;
    }

    public AppendDrawOperation copy(final int pageNumber, final RectangularBox boundingBox, final Color color, final Seq<Object> inklist, final Seq<Seq<Object>> appearance, final double lineSize) {
        return new AppendDrawOperation(pageNumber, boundingBox, color, inklist, appearance, lineSize);
    }

    public int copy$default$1() {
        return pageNumber();
    }

    public RectangularBox copy$default$2() {
        return boundingBox();
    }

    public Color copy$default$3() {
        return color();
    }

    public Seq<Object> copy$default$4() {
        return inklist();
    }

    public Seq<Seq<Object>> copy$default$5() {
        return appearance();
    }

    public double copy$default$6() {
        return lineSize();
    }

    public String productPrefix() {
        return "AppendDrawOperation";
    }

    public int productArity() {
        return 6;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return BoxesRunTime.boxToInteger(pageNumber());
            case 1:
                return boundingBox();
            case 2:
                return color();
            case 3:
                return inklist();
            case 4:
                return appearance();
            case 5:
                return BoxesRunTime.boxToDouble(lineSize());
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof AppendDrawOperation;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "pageNumber";
            case 1:
                return "boundingBox";
            case 2:
                return "color";
            case 3:
                return "inklist";
            case 4:
                return "appearance";
            case 5:
                return "lineSize";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return Statics.finalizeHash(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(-889275714, productPrefix().hashCode()), pageNumber()), Statics.anyHash(boundingBox())), Statics.anyHash(color())), Statics.anyHash(inklist())), Statics.anyHash(appearance())), Statics.doubleHash(lineSize())), 6);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof AppendDrawOperation) {
                AppendDrawOperation appendDrawOperation = (AppendDrawOperation) x$1;
                if (pageNumber() == appendDrawOperation.pageNumber() && lineSize() == appendDrawOperation.lineSize()) {
                    RectangularBox rectangularBoxBoundingBox = boundingBox();
                    RectangularBox rectangularBoxBoundingBox2 = appendDrawOperation.boundingBox();
                    if (rectangularBoxBoundingBox != null ? rectangularBoxBoundingBox.equals(rectangularBoxBoundingBox2) : rectangularBoxBoundingBox2 == null) {
                        Color color = color();
                        Color color2 = appendDrawOperation.color();
                        if (color != null ? color.equals(color2) : color2 == null) {
                            Seq<Object> seqInklist = inklist();
                            Seq<Object> seqInklist2 = appendDrawOperation.inklist();
                            if (seqInklist != null ? seqInklist.equals(seqInklist2) : seqInklist2 == null) {
                                Seq<Seq<Object>> seqAppearance = appearance();
                                Seq<Seq<Object>> seqAppearance2 = appendDrawOperation.appearance();
                                if (seqAppearance != null ? seqAppearance.equals(seqAppearance2) : seqAppearance2 == null) {
                                    if (appendDrawOperation.canEqual(this)) {
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

    public AppendDrawOperation(final int pageNumber, final RectangularBox boundingBox, final Color color, final Seq<Object> inklist, final Seq<Seq<Object>> appearance, final double lineSize) {
        this.pageNumber = pageNumber;
        this.boundingBox = boundingBox;
        this.color = color;
        this.inklist = inklist;
        this.appearance = appearance;
        this.lineSize = lineSize;
        Product.$init$(this);
    }
}
