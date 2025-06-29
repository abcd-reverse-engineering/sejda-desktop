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
@ScalaSignature(bytes = "\u0006\u0005\u0005uc\u0001B\r\u001b\u0001\u000eB\u0001\"\u000f\u0001\u0003\u0016\u0004%\tA\u000f\u0005\t}\u0001\u0011\t\u0012)A\u0005w!Aq\b\u0001BK\u0002\u0013\u0005\u0001\t\u0003\u0005J\u0001\tE\t\u0015!\u0003B\u0011\u0015Q\u0005\u0001\"\u0001L\u0011\u001d\u0001\u0006!!A\u0005\u0002ECq\u0001\u0016\u0001\u0012\u0002\u0013\u0005Q\u000bC\u0004a\u0001E\u0005I\u0011A1\t\u000f\r\u0004\u0011\u0011!C!I\"9A\u000eAA\u0001\n\u0003Q\u0004bB7\u0001\u0003\u0003%\tA\u001c\u0005\bi\u0002\t\t\u0011\"\u0011v\u0011\u001da\b!!A\u0005\u0002uD\u0011\"!\u0002\u0001\u0003\u0003%\t%a\u0002\t\u0013\u0005-\u0001!!A\u0005B\u00055\u0001\"CA\b\u0001\u0005\u0005I\u0011IA\t\u0011%\t\u0019\u0002AA\u0001\n\u0003\n)bB\u0005\u0002\u001ai\t\t\u0011#\u0001\u0002\u001c\u0019A\u0011DGA\u0001\u0012\u0003\ti\u0002\u0003\u0004K'\u0011\u0005\u0011Q\u0007\u0005\n\u0003\u001f\u0019\u0012\u0011!C#\u0003#A\u0011\"a\u000e\u0014\u0003\u0003%\t)!\u000f\t\u0013\u0005}2#!A\u0005\u0002\u0006\u0005\u0003\"CA*'\u0005\u0005I\u0011BA+\u0005Q!U\r\\3uK&k\u0017mZ3Pa\u0016\u0014\u0018\r^5p]*\u00111\u0004H\u0001\u0005K\u0012LGO\u0003\u0002\u001e=\u0005)A/Y:lg*\u0011q\u0004I\u0001\u0006g\u0016TG-\u0019\u0006\u0002C\u0005!1m\u001c3f\u0007\u0001\u0019B\u0001\u0001\u0013+[A\u0011Q\u0005K\u0007\u0002M)\tq%A\u0003tG\u0006d\u0017-\u0003\u0002*M\t1\u0011I\\=SK\u001a\u0004\"!J\u0016\n\u000512#a\u0002)s_\u0012,8\r\u001e\t\u0003]Yr!a\f\u001b\u000f\u0005A\u001aT\"A\u0019\u000b\u0005I\u0012\u0013A\u0002\u001fs_>$h(C\u0001(\u0013\t)d%A\u0004qC\u000e\\\u0017mZ3\n\u0005]B$\u0001D*fe&\fG.\u001b>bE2,'BA\u001b'\u0003)\u0001\u0018mZ3Ok6\u0014WM]\u000b\u0002wA\u0011Q\u0005P\u0005\u0003{\u0019\u00121!\u00138u\u0003-\u0001\u0018mZ3Ok6\u0014WM\u001d\u0011\u0002\u000b=\u0014'.\u00133\u0016\u0003\u0005\u0003\"A\u0011$\u000f\u0005\r#\u0005C\u0001\u0019'\u0013\t)e%\u0001\u0004Qe\u0016$WMZ\u0005\u0003\u000f\"\u0013aa\u0015;sS:<'BA#'\u0003\u0019y'M[%eA\u00051A(\u001b8jiz\"2\u0001\u0014(P!\ti\u0005!D\u0001\u001b\u0011\u0015IT\u00011\u0001<\u0011\u0015yT\u00011\u0001B\u0003\u0011\u0019w\u000e]=\u0015\u00071\u00136\u000bC\u0004:\rA\u0005\t\u0019A\u001e\t\u000f}2\u0001\u0013!a\u0001\u0003\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\nT#\u0001,+\u0005m:6&\u0001-\u0011\u0005esV\"\u0001.\u000b\u0005mc\u0016!C;oG\",7m[3e\u0015\tif%\u0001\u0006b]:|G/\u0019;j_:L!a\u0018.\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0016\u0003\tT#!Q,\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u0005)\u0007C\u00014l\u001b\u00059'B\u00015j\u0003\u0011a\u0017M\\4\u000b\u0003)\fAA[1wC&\u0011qiZ\u0001\raJ|G-^2u\u0003JLG/_\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\ty'\u000f\u0005\u0002&a&\u0011\u0011O\n\u0002\u0004\u0003:L\bbB:\f\u0003\u0003\u0005\raO\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0003Y\u00042a\u001e>p\u001b\u0005A(BA='\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0003wb\u0014\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR\u0019a0a\u0001\u0011\u0005\u0015z\u0018bAA\u0001M\t9!i\\8mK\u0006t\u0007bB:\u000e\u0003\u0003\u0005\ra\\\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\rF\u0002f\u0003\u0013Aqa\u001d\b\u0002\u0002\u0003\u00071(\u0001\u0005iCND7i\u001c3f)\u0005Y\u0014\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003\u0015\fa!Z9vC2\u001cHc\u0001@\u0002\u0018!91/EA\u0001\u0002\u0004y\u0017\u0001\u0006#fY\u0016$X-S7bO\u0016|\u0005/\u001a:bi&|g\u000e\u0005\u0002N'M)1#a\b\u0002,A9\u0011\u0011EA\u0014w\u0005cUBAA\u0012\u0015\r\t)CJ\u0001\beVtG/[7f\u0013\u0011\tI#a\t\u0003#\u0005\u00137\u000f\u001e:bGR4UO\\2uS>t'\u0007\u0005\u0003\u0002.\u0005MRBAA\u0018\u0015\r\t\t$[\u0001\u0003S>L1aNA\u0018)\t\tY\"A\u0003baBd\u0017\u0010F\u0003M\u0003w\ti\u0004C\u0003:-\u0001\u00071\bC\u0003@-\u0001\u0007\u0011)A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\u0005\r\u0013q\n\t\u0006K\u0005\u0015\u0013\u0011J\u0005\u0004\u0003\u000f2#AB(qi&|g\u000eE\u0003&\u0003\u0017Z\u0014)C\u0002\u0002N\u0019\u0012a\u0001V;qY\u0016\u0014\u0004\u0002CA)/\u0005\u0005\t\u0019\u0001'\u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0002XA\u0019a-!\u0017\n\u0007\u0005msM\u0001\u0004PE*,7\r\u001e")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/DeleteImageOperation.class */
public class DeleteImageOperation implements Product, Serializable {
    private final int pageNumber;
    private final String objId;

    public static Option<Tuple2<Object, String>> unapply(final DeleteImageOperation x$0) {
        return DeleteImageOperation$.MODULE$.unapply(x$0);
    }

    public static DeleteImageOperation apply(final int pageNumber, final String objId) {
        return DeleteImageOperation$.MODULE$.apply(pageNumber, objId);
    }

    public static Function1<Tuple2<Object, String>, DeleteImageOperation> tupled() {
        return DeleteImageOperation$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<String, DeleteImageOperation>> curried() {
        return DeleteImageOperation$.MODULE$.curried();
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public int pageNumber() {
        return this.pageNumber;
    }

    public String objId() {
        return this.objId;
    }

    public DeleteImageOperation copy(final int pageNumber, final String objId) {
        return new DeleteImageOperation(pageNumber, objId);
    }

    public int copy$default$1() {
        return pageNumber();
    }

    public String copy$default$2() {
        return objId();
    }

    public String productPrefix() {
        return "DeleteImageOperation";
    }

    public int productArity() {
        return 2;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return BoxesRunTime.boxToInteger(pageNumber());
            case 1:
                return objId();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof DeleteImageOperation;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "pageNumber";
            case 1:
                return "objId";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return Statics.finalizeHash(Statics.mix(Statics.mix(Statics.mix(-889275714, productPrefix().hashCode()), pageNumber()), Statics.anyHash(objId())), 2);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof DeleteImageOperation) {
                DeleteImageOperation deleteImageOperation = (DeleteImageOperation) x$1;
                if (pageNumber() == deleteImageOperation.pageNumber()) {
                    String strObjId = objId();
                    String strObjId2 = deleteImageOperation.objId();
                    if (strObjId != null ? strObjId.equals(strObjId2) : strObjId2 == null) {
                        if (deleteImageOperation.canEqual(this)) {
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    public DeleteImageOperation(final int pageNumber, final String objId) {
        this.pageNumber = pageNumber;
        this.objId = objId;
        Product.$init$(this);
    }
}
