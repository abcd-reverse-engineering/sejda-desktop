package code.sejda.tasks.edit;

import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: EditParameters.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005uc\u0001B\r\u001b\u0001\u000eB\u0001\"\u000f\u0001\u0003\u0016\u0004%\tA\u000f\u0005\t\u0007\u0002\u0011\t\u0012)A\u0005w!AA\t\u0001BK\u0002\u0013\u0005Q\t\u0003\u0005J\u0001\tE\t\u0015!\u0003G\u0011\u0015Q\u0005\u0001\"\u0001L\u0011\u001d\u0001\u0006!!A\u0005\u0002ECq\u0001\u0016\u0001\u0012\u0002\u0013\u0005Q\u000bC\u0004a\u0001E\u0005I\u0011A1\t\u000f\r\u0004\u0011\u0011!C!I\"9A\u000eAA\u0001\n\u0003)\u0005bB7\u0001\u0003\u0003%\tA\u001c\u0005\bi\u0002\t\t\u0011\"\u0011v\u0011\u001da\b!!A\u0005\u0002uD\u0011\"!\u0002\u0001\u0003\u0003%\t%a\u0002\t\u0013\u0005-\u0001!!A\u0005B\u00055\u0001\"CA\b\u0001\u0005\u0005I\u0011IA\t\u0011%\t\u0019\u0002AA\u0001\n\u0003\n)bB\u0005\u0002\u001ai\t\t\u0011#\u0001\u0002\u001c\u0019A\u0011DGA\u0001\u0012\u0003\ti\u0002\u0003\u0004K'\u0011\u0005\u0011Q\u0007\u0005\n\u0003\u001f\u0019\u0012\u0011!C#\u0003#A\u0011\"a\u000e\u0014\u0003\u0003%\t)!\u000f\t\u0013\u0005}2#!A\u0005\u0002\u0006\u0005\u0003\"CA*'\u0005\u0005I\u0011BA+\u0005a!U\r\\3uK\u001a{'/\u001c$jK2$w\n]3sCRLwN\u001c\u0006\u00037q\tA!\u001a3ji*\u0011QDH\u0001\u0006i\u0006\u001c8n\u001d\u0006\u0003?\u0001\nQa]3kI\u0006T\u0011!I\u0001\u0005G>$Wm\u0001\u0001\u0014\t\u0001!#&\f\t\u0003K!j\u0011A\n\u0006\u0002O\u0005)1oY1mC&\u0011\u0011F\n\u0002\u0007\u0003:L(+\u001a4\u0011\u0005\u0015Z\u0013B\u0001\u0017'\u0005\u001d\u0001&o\u001c3vGR\u0004\"A\f\u001c\u000f\u0005=\"dB\u0001\u00194\u001b\u0005\t$B\u0001\u001a#\u0003\u0019a$o\\8u}%\tq%\u0003\u00026M\u00059\u0001/Y2lC\u001e,\u0017BA\u001c9\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\t)d%A\u0003pE*LE-F\u0001<!\ta\u0004I\u0004\u0002>}A\u0011\u0001GJ\u0005\u0003\u007f\u0019\na\u0001\u0015:fI\u00164\u0017BA!C\u0005\u0019\u0019FO]5oO*\u0011qHJ\u0001\u0007_\nT\u0017\n\u001a\u0011\u0002\u0015A\fw-\u001a(v[\n,'/F\u0001G!\t)s)\u0003\u0002IM\t\u0019\u0011J\u001c;\u0002\u0017A\fw-\u001a(v[\n,'\u000fI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u00071su\n\u0005\u0002N\u00015\t!\u0004C\u0003:\u000b\u0001\u00071\bC\u0003E\u000b\u0001\u0007a)\u0001\u0003d_BLHc\u0001'S'\"9\u0011H\u0002I\u0001\u0002\u0004Y\u0004b\u0002#\u0007!\u0003\u0005\rAR\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\u00051&FA\u001eXW\u0005A\u0006CA-_\u001b\u0005Q&BA.]\u0003%)hn\u00195fG.,GM\u0003\u0002^M\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005}S&!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012T#\u00012+\u0005\u0019;\u0016!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070F\u0001f!\t17.D\u0001h\u0015\tA\u0017.\u0001\u0003mC:<'\"\u00016\u0002\t)\fg/Y\u0005\u0003\u0003\u001e\fA\u0002\u001d:pIV\u001cG/\u0011:jif\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0002peB\u0011Q\u0005]\u0005\u0003c\u001a\u00121!\u00118z\u0011\u001d\u00198\"!AA\u0002\u0019\u000b1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014X#\u0001<\u0011\u0007]Tx.D\u0001y\u0015\tIh%\u0001\u0006d_2dWm\u0019;j_:L!a\u001f=\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0004}\u0006\r\u0001CA\u0013��\u0013\r\t\tA\n\u0002\b\u0005>|G.Z1o\u0011\u001d\u0019X\"!AA\u0002=\f!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR\u0019Q-!\u0003\t\u000fMt\u0011\u0011!a\u0001\r\u0006A\u0001.Y:i\u0007>$W\rF\u0001G\u0003!!xn\u0015;sS:<G#A3\u0002\r\u0015\fX/\u00197t)\rq\u0018q\u0003\u0005\bgF\t\t\u00111\u0001p\u0003a!U\r\\3uK\u001a{'/\u001c$jK2$w\n]3sCRLwN\u001c\t\u0003\u001bN\u0019RaEA\u0010\u0003W\u0001r!!\t\u0002(m2E*\u0004\u0002\u0002$)\u0019\u0011Q\u0005\u0014\u0002\u000fI,h\u000e^5nK&!\u0011\u0011FA\u0012\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|gN\r\t\u0005\u0003[\t\u0019$\u0004\u0002\u00020)\u0019\u0011\u0011G5\u0002\u0005%|\u0017bA\u001c\u00020Q\u0011\u00111D\u0001\u0006CB\u0004H.\u001f\u000b\u0006\u0019\u0006m\u0012Q\b\u0005\u0006sY\u0001\ra\u000f\u0005\u0006\tZ\u0001\rAR\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\t\u0019%a\u0014\u0011\u000b\u0015\n)%!\u0013\n\u0007\u0005\u001dcE\u0001\u0004PaRLwN\u001c\t\u0006K\u0005-3HR\u0005\u0004\u0003\u001b2#A\u0002+va2,'\u0007\u0003\u0005\u0002R]\t\t\u00111\u0001M\u0003\rAH\u0005M\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003/\u00022AZA-\u0013\r\tYf\u001a\u0002\u0007\u001f\nTWm\u0019;")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/DeleteFormFieldOperation.class */
public class DeleteFormFieldOperation implements Product, Serializable {
    private final String objId;
    private final int pageNumber;

    public static Option<Tuple2<String, Object>> unapply(final DeleteFormFieldOperation x$0) {
        return DeleteFormFieldOperation$.MODULE$.unapply(x$0);
    }

    public static DeleteFormFieldOperation apply(final String objId, final int pageNumber) {
        return DeleteFormFieldOperation$.MODULE$.apply(objId, pageNumber);
    }

    public static Function1<Tuple2<String, Object>, DeleteFormFieldOperation> tupled() {
        return DeleteFormFieldOperation$.MODULE$.tupled();
    }

    public static Function1<String, Function1<Object, DeleteFormFieldOperation>> curried() {
        return DeleteFormFieldOperation$.MODULE$.curried();
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String objId() {
        return this.objId;
    }

    public int pageNumber() {
        return this.pageNumber;
    }

    public DeleteFormFieldOperation copy(final String objId, final int pageNumber) {
        return new DeleteFormFieldOperation(objId, pageNumber);
    }

    public String copy$default$1() {
        return objId();
    }

    public int copy$default$2() {
        return pageNumber();
    }

    public String productPrefix() {
        return "DeleteFormFieldOperation";
    }

    public int productArity() {
        return 2;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return objId();
            case 1:
                return BoxesRunTime.boxToInteger(pageNumber());
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof DeleteFormFieldOperation;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "objId";
            case 1:
                return "pageNumber";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return Statics.finalizeHash(Statics.mix(Statics.mix(Statics.mix(-889275714, productPrefix().hashCode()), Statics.anyHash(objId())), pageNumber()), 2);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof DeleteFormFieldOperation) {
                DeleteFormFieldOperation deleteFormFieldOperation = (DeleteFormFieldOperation) x$1;
                if (pageNumber() == deleteFormFieldOperation.pageNumber()) {
                    String strObjId = objId();
                    String strObjId2 = deleteFormFieldOperation.objId();
                    if (strObjId != null ? strObjId.equals(strObjId2) : strObjId2 == null) {
                        if (deleteFormFieldOperation.canEqual(this)) {
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    public DeleteFormFieldOperation(final String objId, final int pageNumber) {
        this.objId = objId;
        this.pageNumber = pageNumber;
        Product.$init$(this);
    }
}
