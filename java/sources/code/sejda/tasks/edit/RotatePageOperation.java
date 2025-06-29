package code.sejda.tasks.edit;

import java.io.Serializable;
import org.sejda.model.rotation.Rotation;
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
@ScalaSignature(bytes = "\u0006\u0005\u0005\rd\u0001B\r\u001b\u0001\u000eB\u0001\"\u000f\u0001\u0003\u0016\u0004%\tA\u000f\u0005\t}\u0001\u0011\t\u0012)A\u0005w!Aq\b\u0001BK\u0002\u0013\u0005\u0001\t\u0003\u0005L\u0001\tE\t\u0015!\u0003B\u0011\u0015a\u0005\u0001\"\u0001N\u0011\u001d\u0011\u0006!!A\u0005\u0002MCqA\u0016\u0001\u0012\u0002\u0013\u0005q\u000bC\u0004c\u0001E\u0005I\u0011A2\t\u000f\u0015\u0004\u0011\u0011!C!M\"9q\u000eAA\u0001\n\u0003Q\u0004b\u00029\u0001\u0003\u0003%\t!\u001d\u0005\bo\u0002\t\t\u0011\"\u0011y\u0011!y\b!!A\u0005\u0002\u0005\u0005\u0001\"CA\u0006\u0001\u0005\u0005I\u0011IA\u0007\u0011%\t\t\u0002AA\u0001\n\u0003\n\u0019\u0002C\u0005\u0002\u0016\u0001\t\t\u0011\"\u0011\u0002\u0018!I\u0011\u0011\u0004\u0001\u0002\u0002\u0013\u0005\u00131D\u0004\n\u0003?Q\u0012\u0011!E\u0001\u0003C1\u0001\"\u0007\u000e\u0002\u0002#\u0005\u00111\u0005\u0005\u0007\u0019N!\t!a\u000f\t\u0013\u0005U1#!A\u0005F\u0005]\u0001\"CA\u001f'\u0005\u0005I\u0011QA \u0011%\t)eEA\u0001\n\u0003\u000b9\u0005C\u0005\u0002ZM\t\t\u0011\"\u0003\u0002\\\t\u0019\"k\u001c;bi\u0016\u0004\u0016mZ3Pa\u0016\u0014\u0018\r^5p]*\u00111\u0004H\u0001\u0005K\u0012LGO\u0003\u0002\u001e=\u0005)A/Y:lg*\u0011q\u0004I\u0001\u0006g\u0016TG-\u0019\u0006\u0002C\u0005!1m\u001c3f\u0007\u0001\u0019B\u0001\u0001\u0013+[A\u0011Q\u0005K\u0007\u0002M)\tq%A\u0003tG\u0006d\u0017-\u0003\u0002*M\t1\u0011I\\=SK\u001a\u0004\"!J\u0016\n\u000512#a\u0002)s_\u0012,8\r\u001e\t\u0003]Yr!a\f\u001b\u000f\u0005A\u001aT\"A\u0019\u000b\u0005I\u0012\u0013A\u0002\u001fs_>$h(C\u0001(\u0013\t)d%A\u0004qC\u000e\\\u0017mZ3\n\u0005]B$\u0001D*fe&\fG.\u001b>bE2,'BA\u001b'\u0003)\u0001\u0018mZ3Ok6\u0014WM]\u000b\u0002wA\u0011Q\u0005P\u0005\u0003{\u0019\u00121!\u00138u\u0003-\u0001\u0018mZ3Ok6\u0014WM\u001d\u0011\u0002\u0011I|G/\u0019;j_:,\u0012!\u0011\t\u0003\u0005&k\u0011a\u0011\u0006\u0003\u007f\u0011S!!\u0012$\u0002\u000b5|G-\u001a7\u000b\u0005}9%\"\u0001%\u0002\u0007=\u0014x-\u0003\u0002K\u0007\nA!k\u001c;bi&|g.A\u0005s_R\fG/[8oA\u00051A(\u001b8jiz\"2A\u0014)R!\ty\u0005!D\u0001\u001b\u0011\u0015IT\u00011\u0001<\u0011\u0015yT\u00011\u0001B\u0003\u0011\u0019w\u000e]=\u0015\u00079#V\u000bC\u0004:\rA\u0005\t\u0019A\u001e\t\u000f}2\u0001\u0013!a\u0001\u0003\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\nT#\u0001-+\u0005mJ6&\u0001.\u0011\u0005m\u0003W\"\u0001/\u000b\u0005us\u0016!C;oG\",7m[3e\u0015\tyf%\u0001\u0006b]:|G/\u0019;j_:L!!\u0019/\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0016\u0003\u0011T#!Q-\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u00059\u0007C\u00015n\u001b\u0005I'B\u00016l\u0003\u0011a\u0017M\\4\u000b\u00031\fAA[1wC&\u0011a.\u001b\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0011!/\u001e\t\u0003KML!\u0001\u001e\u0014\u0003\u0007\u0005s\u0017\u0010C\u0004w\u0017\u0005\u0005\t\u0019A\u001e\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\u0005I\bc\u0001>~e6\t1P\u0003\u0002}M\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u0005y\\(\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$B!a\u0001\u0002\nA\u0019Q%!\u0002\n\u0007\u0005\u001daEA\u0004C_>dW-\u00198\t\u000fYl\u0011\u0011!a\u0001e\u0006\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\r9\u0017q\u0002\u0005\bm:\t\t\u00111\u0001<\u0003!A\u0017m\u001d5D_\u0012,G#A\u001e\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012aZ\u0001\u0007KF,\u0018\r\\:\u0015\t\u0005\r\u0011Q\u0004\u0005\bmF\t\t\u00111\u0001s\u0003M\u0011v\u000e^1uKB\u000bw-Z(qKJ\fG/[8o!\ty5cE\u0003\u0014\u0003K\t\t\u0004E\u0004\u0002(\u000552(\u0011(\u000e\u0005\u0005%\"bAA\u0016M\u00059!/\u001e8uS6,\u0017\u0002BA\u0018\u0003S\u0011\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c83!\u0011\t\u0019$!\u000f\u000e\u0005\u0005U\"bAA\u001cW\u0006\u0011\u0011n\\\u0005\u0004o\u0005UBCAA\u0011\u0003\u0015\t\u0007\u000f\u001d7z)\u0015q\u0015\u0011IA\"\u0011\u0015Id\u00031\u0001<\u0011\u0015yd\u00031\u0001B\u0003\u001d)h.\u00199qYf$B!!\u0013\u0002VA)Q%a\u0013\u0002P%\u0019\u0011Q\n\u0014\u0003\r=\u0003H/[8o!\u0015)\u0013\u0011K\u001eB\u0013\r\t\u0019F\n\u0002\u0007)V\u0004H.\u001a\u001a\t\u0011\u0005]s#!AA\u00029\u000b1\u0001\u001f\u00131\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\ti\u0006E\u0002i\u0003?J1!!\u0019j\u0005\u0019y%M[3di\u0002")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/RotatePageOperation.class */
public class RotatePageOperation implements Product, Serializable {
    private final int pageNumber;
    private final Rotation rotation;

    public static Option<Tuple2<Object, Rotation>> unapply(final RotatePageOperation x$0) {
        return RotatePageOperation$.MODULE$.unapply(x$0);
    }

    public static RotatePageOperation apply(final int pageNumber, final Rotation rotation) {
        return RotatePageOperation$.MODULE$.apply(pageNumber, rotation);
    }

    public static Function1<Tuple2<Object, Rotation>, RotatePageOperation> tupled() {
        return RotatePageOperation$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<Rotation, RotatePageOperation>> curried() {
        return RotatePageOperation$.MODULE$.curried();
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public int pageNumber() {
        return this.pageNumber;
    }

    public Rotation rotation() {
        return this.rotation;
    }

    public RotatePageOperation copy(final int pageNumber, final Rotation rotation) {
        return new RotatePageOperation(pageNumber, rotation);
    }

    public int copy$default$1() {
        return pageNumber();
    }

    public Rotation copy$default$2() {
        return rotation();
    }

    public String productPrefix() {
        return "RotatePageOperation";
    }

    public int productArity() {
        return 2;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return BoxesRunTime.boxToInteger(pageNumber());
            case 1:
                return rotation();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof RotatePageOperation;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "pageNumber";
            case 1:
                return "rotation";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return Statics.finalizeHash(Statics.mix(Statics.mix(Statics.mix(-889275714, productPrefix().hashCode()), pageNumber()), Statics.anyHash(rotation())), 2);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof RotatePageOperation) {
                RotatePageOperation rotatePageOperation = (RotatePageOperation) x$1;
                if (pageNumber() == rotatePageOperation.pageNumber()) {
                    Rotation rotation = rotation();
                    Rotation rotation2 = rotatePageOperation.rotation();
                    if (rotation != null ? rotation.equals(rotation2) : rotation2 == null) {
                        if (rotatePageOperation.canEqual(this)) {
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    public RotatePageOperation(final int pageNumber, final Rotation rotation) {
        this.pageNumber = pageNumber;
        this.rotation = rotation;
        Product.$init$(this);
    }
}
