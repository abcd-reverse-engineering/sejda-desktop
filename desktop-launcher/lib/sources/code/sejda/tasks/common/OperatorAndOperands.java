package code.sejda.tasks.common;

import java.io.Serializable;
import java.util.List;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.cos.COSBase;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: OperatorAndOperands.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005\u0005e\u0001B\r\u001b\u0001\u000eB\u0001\"\u000f\u0001\u0003\u0016\u0004%\tA\u000f\u0005\t\u000f\u0002\u0011\t\u0012)A\u0005w!A\u0001\n\u0001BK\u0002\u0013\u0005\u0011\n\u0003\u0005T\u0001\tE\t\u0015!\u0003K\u0011\u0015!\u0006\u0001\"\u0001V\u0011\u001dQ\u0006!!A\u0005\u0002mCqA\u0018\u0001\u0012\u0002\u0013\u0005q\fC\u0004k\u0001E\u0005I\u0011A6\t\u000f5\u0004\u0011\u0011!C!]\"9q\u000fAA\u0001\n\u0003A\bb\u0002?\u0001\u0003\u0003%\t! \u0005\n\u0003\u000f\u0001\u0011\u0011!C!\u0003\u0013A\u0011\"a\u0006\u0001\u0003\u0003%\t!!\u0007\t\u0013\u0005\r\u0002!!A\u0005B\u0005\u0015\u0002\"CA\u0015\u0001\u0005\u0005I\u0011IA\u0016\u0011%\ti\u0003AA\u0001\n\u0003\ny\u0003C\u0005\u00022\u0001\t\t\u0011\"\u0011\u00024\u001d9\u0011q\u0007\u000e\t\u0002\u0005ebAB\r\u001b\u0011\u0003\tY\u0004\u0003\u0004U'\u0011\u0005\u0011q\t\u0005\b\u0003\u0013\u001aB\u0011AA&\u0011%\tIeEA\u0001\n\u0003\u000bi\u0006C\u0005\u0002dM\t\t\u0011\"!\u0002f!I\u0011qO\n\u0002\u0002\u0013%\u0011\u0011\u0010\u0002\u0014\u001fB,'/\u0019;pe\u0006sGm\u00149fe\u0006tGm\u001d\u0006\u00037q\taaY8n[>t'BA\u000f\u001f\u0003\u0015!\u0018m]6t\u0015\ty\u0002%A\u0003tK*$\u0017MC\u0001\"\u0003\u0011\u0019w\u000eZ3\u0004\u0001M!\u0001\u0001\n\u0016.!\t)\u0003&D\u0001'\u0015\u00059\u0013!B:dC2\f\u0017BA\u0015'\u0005\u0019\te.\u001f*fMB\u0011QeK\u0005\u0003Y\u0019\u0012q\u0001\u0015:pIV\u001cG\u000f\u0005\u0002/m9\u0011q\u0006\u000e\b\u0003aMj\u0011!\r\u0006\u0003e\t\na\u0001\u0010:p_Rt\u0014\"A\u0014\n\u0005U2\u0013a\u00029bG.\fw-Z\u0005\u0003oa\u0012AbU3sS\u0006d\u0017N_1cY\u0016T!!\u000e\u0014\u0002\u0011=\u0004XM]1u_J,\u0012a\u000f\t\u0003y\u0015k\u0011!\u0010\u0006\u0003syR!a\u0010!\u0002\u001b\r|g\u000e^3oiN$(/Z1n\u0015\t\t%)\u0001\u0004tC6\u0014w\u000e\u001f\u0006\u0003?\rS\u0011\u0001R\u0001\u0004_J<\u0017B\u0001$>\u0005!y\u0005/\u001a:bi>\u0014\u0018!C8qKJ\fGo\u001c:!\u0003!y\u0007/\u001a:b]\u0012\u001cX#\u0001&\u0011\u00079ZU*\u0003\u0002Mq\t\u00191+Z9\u0011\u00059\u000bV\"A(\u000b\u0005A\u0003\u0015aA2pg&\u0011!k\u0014\u0002\b\u0007>\u001b&)Y:f\u0003%y\u0007/\u001a:b]\u0012\u001c\b%\u0001\u0004=S:LGO\u0010\u000b\u0004-bK\u0006CA,\u0001\u001b\u0005Q\u0002\"B\u001d\u0006\u0001\u0004Y\u0004\"\u0002%\u0006\u0001\u0004Q\u0015\u0001B2paf$2A\u0016/^\u0011\u001dId\u0001%AA\u0002mBq\u0001\u0013\u0004\u0011\u0002\u0003\u0007!*\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0003\u0001T#aO1,\u0003\t\u0004\"a\u00195\u000e\u0003\u0011T!!\u001a4\u0002\u0013Ut7\r[3dW\u0016$'BA4'\u0003)\tgN\\8uCRLwN\\\u0005\u0003S\u0012\u0014\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII*\u0012\u0001\u001c\u0016\u0003\u0015\u0006\fQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DX#A8\u0011\u0005A,X\"A9\u000b\u0005I\u001c\u0018\u0001\u00027b]\u001eT\u0011\u0001^\u0001\u0005U\u00064\u0018-\u0003\u0002wc\n11\u000b\u001e:j]\u001e\fA\u0002\u001d:pIV\u001cG/\u0011:jif,\u0012!\u001f\t\u0003KiL!a\u001f\u0014\u0003\u0007%sG/\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\u0007y\f\u0019\u0001\u0005\u0002&\u007f&\u0019\u0011\u0011\u0001\u0014\u0003\u0007\u0005s\u0017\u0010\u0003\u0005\u0002\u0006-\t\t\u00111\u0001z\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u00111\u0002\t\u0006\u0003\u001b\t\u0019B`\u0007\u0003\u0003\u001fQ1!!\u0005'\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003+\tyA\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BA\u000e\u0003C\u00012!JA\u000f\u0013\r\tyB\n\u0002\b\u0005>|G.Z1o\u0011!\t)!DA\u0001\u0002\u0004q\u0018A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$2a\\A\u0014\u0011!\t)ADA\u0001\u0002\u0004I\u0018\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003e\f\u0001\u0002^8TiJLgn\u001a\u000b\u0002_\u00061Q-];bYN$B!a\u0007\u00026!A\u0011QA\t\u0002\u0002\u0003\u0007a0A\nPa\u0016\u0014\u0018\r^8s\u0003:$w\n]3sC:$7\u000f\u0005\u0002X'M!1\u0003JA\u001f!\u0011\ty$!\u0012\u000e\u0005\u0005\u0005#bAA\"g\u0006\u0011\u0011n\\\u0005\u0004o\u0005\u0005CCAA\u001d\u0003\u0015\t\u0007\u000f\u001d7z)\u00151\u0016QJA(\u0011\u0015IT\u00031\u0001<\u0011\u0019AU\u00031\u0001\u0002RA)\u00111KA-\u001b6\u0011\u0011Q\u000b\u0006\u0004\u0003/\u001a\u0018\u0001B;uS2LA!a\u0017\u0002V\t!A*[:u)\u00151\u0016qLA1\u0011\u0015Id\u00031\u0001<\u0011\u0015Ae\u00031\u0001K\u0003\u001d)h.\u00199qYf$B!a\u001a\u0002tA)Q%!\u001b\u0002n%\u0019\u00111\u000e\u0014\u0003\r=\u0003H/[8o!\u0015)\u0013qN\u001eK\u0013\r\t\tH\n\u0002\u0007)V\u0004H.\u001a\u001a\t\u0011\u0005Ut#!AA\u0002Y\u000b1\u0001\u001f\u00131\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\tY\bE\u0002q\u0003{J1!a r\u0005\u0019y%M[3di\u0002")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/OperatorAndOperands.class */
public class OperatorAndOperands implements Product, Serializable {
    private final Operator operator;
    private final Seq<COSBase> operands;

    public static Option<Tuple2<Operator, Seq<COSBase>>> unapply(final OperatorAndOperands x$0) {
        return OperatorAndOperands$.MODULE$.unapply(x$0);
    }

    public static OperatorAndOperands apply(final Operator operator, final Seq<COSBase> operands) {
        return OperatorAndOperands$.MODULE$.apply(operator, operands);
    }

    public static OperatorAndOperands apply(final Operator operator, final List<COSBase> operands) {
        return OperatorAndOperands$.MODULE$.apply(operator, operands);
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Operator operator() {
        return this.operator;
    }

    public Seq<COSBase> operands() {
        return this.operands;
    }

    public OperatorAndOperands copy(final Operator operator, final Seq<COSBase> operands) {
        return new OperatorAndOperands(operator, operands);
    }

    public Operator copy$default$1() {
        return operator();
    }

    public Seq<COSBase> copy$default$2() {
        return operands();
    }

    public String productPrefix() {
        return "OperatorAndOperands";
    }

    public int productArity() {
        return 2;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return operator();
            case 1:
                return operands();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof OperatorAndOperands;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "operator";
            case 1:
                return "operands";
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
            if (x$1 instanceof OperatorAndOperands) {
                OperatorAndOperands operatorAndOperands = (OperatorAndOperands) x$1;
                Operator operator = operator();
                Operator operator2 = operatorAndOperands.operator();
                if (operator != null ? operator.equals(operator2) : operator2 == null) {
                    Seq<COSBase> seqOperands = operands();
                    Seq<COSBase> seqOperands2 = operatorAndOperands.operands();
                    if (seqOperands != null ? seqOperands.equals(seqOperands2) : seqOperands2 == null) {
                        if (operatorAndOperands.canEqual(this)) {
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    public OperatorAndOperands(final Operator operator, final Seq<COSBase> operands) {
        this.operator = operator;
        this.operands = operands;
        Product.$init$(this);
    }
}
