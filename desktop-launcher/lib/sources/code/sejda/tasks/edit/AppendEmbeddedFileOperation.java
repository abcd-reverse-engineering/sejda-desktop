package code.sejda.tasks.edit;

import java.io.Serializable;
import org.sejda.model.input.Source;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: EditParameters.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005\re\u0001\u0002\f\u0018\u0001\u0002B\u0001B\u000e\u0001\u0003\u0016\u0004%\ta\u000e\u0005\t\u0011\u0002\u0011\t\u0012)A\u0005q!)\u0001\u000b\u0001C\u0001#\"9\u0011\fAA\u0001\n\u0003Q\u0006b\u0002/\u0001#\u0003%\t!\u0018\u0005\bY\u0002\t\t\u0011\"\u0011n\u0011\u001d1\b!!A\u0005\u0002]Dqa\u001f\u0001\u0002\u0002\u0013\u0005A\u0010\u0003\u0005��\u0001\u0005\u0005I\u0011IA\u0001\u0011%\ty\u0001AA\u0001\n\u0003\t\t\u0002C\u0005\u0002\u001c\u0001\t\t\u0011\"\u0011\u0002\u001e!I\u0011\u0011\u0005\u0001\u0002\u0002\u0013\u0005\u00131\u0005\u0005\n\u0003K\u0001\u0011\u0011!C!\u0003OA\u0011\"!\u000b\u0001\u0003\u0003%\t%a\u000b\b\u0013\u0005=r#!A\t\u0002\u0005Eb\u0001\u0003\f\u0018\u0003\u0003E\t!a\r\t\rA\u0003B\u0011AA*\u0011%\t)\u0003EA\u0001\n\u000b\n9\u0003C\u0005\u0002VA\t\t\u0011\"!\u0002X!I\u00111\r\t\u0002\u0002\u0013\u0005\u0015Q\r\u0005\n\u0003s\u0002\u0012\u0011!C\u0005\u0003w\u00121$\u00119qK:$W)\u001c2fI\u0012,GMR5mK>\u0003XM]1uS>t'B\u0001\r\u001a\u0003\u0011)G-\u001b;\u000b\u0005iY\u0012!\u0002;bg.\u001c(B\u0001\u000f\u001e\u0003\u0015\u0019XM\u001b3b\u0015\u0005q\u0012\u0001B2pI\u0016\u001c\u0001a\u0005\u0003\u0001C\u001dR\u0003C\u0001\u0012&\u001b\u0005\u0019#\"\u0001\u0013\u0002\u000bM\u001c\u0017\r\\1\n\u0005\u0019\u001a#AB!osJ+g\r\u0005\u0002#Q%\u0011\u0011f\t\u0002\b!J|G-^2u!\tY3G\u0004\u0002-c9\u0011Q\u0006M\u0007\u0002])\u0011qfH\u0001\u0007yI|w\u000e\u001e \n\u0003\u0011J!AM\u0012\u0002\u000fA\f7m[1hK&\u0011A'\u000e\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0003e\r\nAAZ5mKV\t\u0001\b\r\u0002:\rB\u0019!H\u0011#\u000e\u0003mR!\u0001P\u001f\u0002\u000b%t\u0007/\u001e;\u000b\u0005yz\u0014!B7pI\u0016d'B\u0001\u000fA\u0015\u0005\t\u0015aA8sO&\u00111i\u000f\u0002\u0007'>,(oY3\u0011\u0005\u00153E\u0002\u0001\u0003\n\u000f\n\t\t\u0011!A\u0003\u0002%\u00131a\u0018\u00133\u0003\u00151\u0017\u000e\\3!#\tQU\n\u0005\u0002#\u0017&\u0011Aj\t\u0002\b\u001d>$\b.\u001b8h!\t\u0011c*\u0003\u0002PG\t\u0019\u0011I\\=\u0002\rqJg.\u001b;?)\t\u0011F\u000b\u0005\u0002T\u00015\tq\u0003C\u00037\u0007\u0001\u0007Q\u000b\r\u0002W1B\u0019!HQ,\u0011\u0005\u0015CF!C$U\u0003\u0003\u0005\tQ!\u0001J\u0003\u0011\u0019w\u000e]=\u0015\u0005I[\u0006b\u0002\u001c\u0005!\u0003\u0005\r!V\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\u0005q\u0006GA0cU\t\u00017\rE\u0002;\u0005\u0006\u0004\"!\u00122\u0005\u0013\u001d+\u0011\u0011!A\u0001\u0006\u0003I5&\u00013\u0011\u0005\u0015TW\"\u00014\u000b\u0005\u001dD\u0017!C;oG\",7m[3e\u0015\tI7%\u0001\u0006b]:|G/\u0019;j_:L!a\u001b4\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0002]B\u0011q\u000e^\u0007\u0002a*\u0011\u0011O]\u0001\u0005Y\u0006twMC\u0001t\u0003\u0011Q\u0017M^1\n\u0005U\u0004(AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180F\u0001y!\t\u0011\u00130\u0003\u0002{G\t\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0011Q* \u0005\b}\"\t\t\u00111\u0001y\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u00111\u0001\t\u0006\u0003\u000b\tY!T\u0007\u0003\u0003\u000fQ1!!\u0003$\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003\u001b\t9A\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BA\n\u00033\u00012AIA\u000b\u0013\r\t9b\t\u0002\b\u0005>|G.Z1o\u0011\u001dq(\"!AA\u00025\u000b!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR\u0019a.a\b\t\u000fy\\\u0011\u0011!a\u0001q\u0006A\u0001.Y:i\u0007>$W\rF\u0001y\u0003!!xn\u0015;sS:<G#\u00018\u0002\r\u0015\fX/\u00197t)\u0011\t\u0019\"!\f\t\u000fyt\u0011\u0011!a\u0001\u001b\u0006Y\u0012\t\u001d9f]\u0012,UNY3eI\u0016$g)\u001b7f\u001fB,'/\u0019;j_:\u0004\"a\u0015\t\u0014\u000bA\t)$!\u0013\u0011\u000f\u0005]\u0012QHA!%6\u0011\u0011\u0011\b\u0006\u0004\u0003w\u0019\u0013a\u0002:v]RLW.Z\u0005\u0005\u0003\u007f\tIDA\tBEN$(/Y2u\rVt7\r^5p]F\u0002D!a\u0011\u0002HA!!HQA#!\r)\u0015q\t\u0003\n\u000fB\t\t\u0011!A\u0003\u0002%\u0003B!a\u0013\u0002R5\u0011\u0011Q\n\u0006\u0004\u0003\u001f\u0012\u0018AA5p\u0013\r!\u0014Q\n\u000b\u0003\u0003c\tQ!\u00199qYf$2AUA-\u0011\u001914\u00031\u0001\u0002\\A\"\u0011QLA1!\u0011Q$)a\u0018\u0011\u0007\u0015\u000b\t\u0007\u0002\u0006H\u00033\n\t\u0011!A\u0003\u0002%\u000bq!\u001e8baBd\u0017\u0010\u0006\u0003\u0002h\u0005U\u0004#\u0002\u0012\u0002j\u00055\u0014bAA6G\t1q\n\u001d;j_:\u0004D!a\u001c\u0002tA!!HQA9!\r)\u00151\u000f\u0003\n\u000fR\t\t\u0011!A\u0003\u0002%C\u0001\"a\u001e\u0015\u0003\u0003\u0005\rAU\u0001\u0004q\u0012\u0002\u0014\u0001D<sSR,'+\u001a9mC\u000e,GCAA?!\ry\u0017qP\u0005\u0004\u0003\u0003\u0003(AB(cU\u0016\u001cG\u000f")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/AppendEmbeddedFileOperation.class */
public class AppendEmbeddedFileOperation implements Product, Serializable {
    private final Source<?> file;

    public static Option<Source<?>> unapply(final AppendEmbeddedFileOperation x$0) {
        return AppendEmbeddedFileOperation$.MODULE$.unapply(x$0);
    }

    public static AppendEmbeddedFileOperation apply(final Source<?> file) {
        return AppendEmbeddedFileOperation$.MODULE$.apply(file);
    }

    public static <A> Function1<Source<?>, A> andThen(final Function1<AppendEmbeddedFileOperation, A> g) {
        return AppendEmbeddedFileOperation$.MODULE$.andThen(g);
    }

    public static <A> Function1<A, AppendEmbeddedFileOperation> compose(final Function1<A, Source<?>> g) {
        return AppendEmbeddedFileOperation$.MODULE$.compose(g);
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Source<?> file() {
        return this.file;
    }

    public AppendEmbeddedFileOperation copy(final Source<?> file) {
        return new AppendEmbeddedFileOperation(file);
    }

    public Source<?> copy$default$1() {
        return file();
    }

    public String productPrefix() {
        return "AppendEmbeddedFileOperation";
    }

    public int productArity() {
        return 1;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return file();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof AppendEmbeddedFileOperation;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "file";
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
            if (x$1 instanceof AppendEmbeddedFileOperation) {
                AppendEmbeddedFileOperation appendEmbeddedFileOperation = (AppendEmbeddedFileOperation) x$1;
                Source<?> sourceFile = file();
                Source<?> sourceFile2 = appendEmbeddedFileOperation.file();
                if (sourceFile != null ? sourceFile.equals(sourceFile2) : sourceFile2 == null) {
                    if (appendEmbeddedFileOperation.canEqual(this)) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    public AppendEmbeddedFileOperation(final Source<?> file) {
        this.file = file;
        Product.$init$(this);
    }
}
