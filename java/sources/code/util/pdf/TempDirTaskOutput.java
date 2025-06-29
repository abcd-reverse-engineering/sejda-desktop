package code.util.pdf;

import java.io.File;
import java.io.Serializable;
import org.sejda.model.output.DirectoryTaskOutput;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: TempDirTaskOutput.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005]b\u0001B\n\u0015\u0001nA\u0001B\u000f\u0001\u0003\u0016\u0004%\ta\u000f\u0005\t\t\u0002\u0011\t\u0012)A\u0005y!)Q\t\u0001C\u0001\r\"9!\nAA\u0001\n\u0003Y\u0005bB'\u0001#\u0003%\tA\u0014\u0005\b3\u0002\t\t\u0011\"\u0011[\u0011\u001d\t\u0007!!A\u0005\u0002\tDqA\u001a\u0001\u0002\u0002\u0013\u0005q\rC\u0004n\u0001\u0005\u0005I\u0011\t8\t\u000fU\u0004\u0011\u0011!C\u0001m\"91\u0010AA\u0001\n\u0003bxa\u0002@\u0015\u0003\u0003E\ta \u0004\t'Q\t\t\u0011#\u0001\u0002\u0002!1Q)\u0004C\u0001\u0003'A\u0011\"!\u0006\u000e\u0003\u0003%)%a\u0006\t\u0013\u0005eQ\"!A\u0005\u0002\u0006m\u0001\"CA\u0010\u001b\u0005\u0005I\u0011QA\u0011\u0011%\ti#DA\u0001\n\u0013\tyCA\tUK6\u0004H)\u001b:UCN\\w*\u001e;qkRT!!\u0006\f\u0002\u0007A$gM\u0003\u0002\u00181\u0005!Q\u000f^5m\u0015\u0005I\u0012\u0001B2pI\u0016\u001c\u0001a\u0005\u0003\u00019!r\u0003CA\u000f'\u001b\u0005q\"BA\u0010!\u0003\u0019yW\u000f\u001e9vi*\u0011\u0011EI\u0001\u0006[>$W\r\u001c\u0006\u0003G\u0011\nQa]3kI\u0006T\u0011!J\u0001\u0004_J<\u0017BA\u0014\u001f\u0005M!\u0015N]3di>\u0014\u0018\u0010V1tW>+H\u000f];u!\tIC&D\u0001+\u0015\u0005Y\u0013!B:dC2\f\u0017BA\u0017+\u0005\u001d\u0001&o\u001c3vGR\u0004\"aL\u001c\u000f\u0005A*dBA\u00195\u001b\u0005\u0011$BA\u001a\u001b\u0003\u0019a$o\\8u}%\t1&\u0003\u00027U\u00059\u0001/Y2lC\u001e,\u0017B\u0001\u001d:\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\t1$&A\u0006eKN$\u0018N\\1uS>tW#\u0001\u001f\u0011\u0005u\u0012U\"\u0001 \u000b\u0005}\u0002\u0015AA5p\u0015\u0005\t\u0015\u0001\u00026bm\u0006L!a\u0011 \u0003\t\u0019KG.Z\u0001\rI\u0016\u001cH/\u001b8bi&|g\u000eI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005\u001dK\u0005C\u0001%\u0001\u001b\u0005!\u0002\"\u0002\u001e\u0004\u0001\u0004a\u0014\u0001B2paf$\"a\u0012'\t\u000fi\"\u0001\u0013!a\u0001y\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\nT#A(+\u0005q\u00026&A)\u0011\u0005I;V\"A*\u000b\u0005Q+\u0016!C;oG\",7m[3e\u0015\t1&&\u0001\u0006b]:|G/\u0019;j_:L!\u0001W*\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u00027B\u0011AlX\u0007\u0002;*\u0011a\fQ\u0001\u0005Y\u0006tw-\u0003\u0002a;\n11\u000b\u001e:j]\u001e\fA\u0002\u001d:pIV\u001cG/\u0011:jif,\u0012a\u0019\t\u0003S\u0011L!!\u001a\u0016\u0003\u0007%sG/\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\u0005!\\\u0007CA\u0015j\u0013\tQ'FA\u0002B]fDq\u0001\u001c\u0005\u0002\u0002\u0003\u00071-A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0002_B\u0019\u0001o\u001d5\u000e\u0003ET!A\u001d\u0016\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0002uc\nA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\t9(\u0010\u0005\u0002*q&\u0011\u0011P\u000b\u0002\b\u0005>|G.Z1o\u0011\u001da'\"!AA\u0002!\f!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR\u00111, \u0005\bY.\t\t\u00111\u0001d\u0003E!V-\u001c9ESJ$\u0016m]6PkR\u0004X\u000f\u001e\t\u0003\u00116\u0019R!DA\u0002\u0003\u001f\u0001b!!\u0002\u0002\fq:UBAA\u0004\u0015\r\tIAK\u0001\beVtG/[7f\u0013\u0011\ti!a\u0002\u0003#\u0005\u00137\u000f\u001e:bGR4UO\\2uS>t\u0017\u0007E\u0002>\u0003#I!\u0001\u000f \u0015\u0003}\f\u0001\u0002^8TiJLgn\u001a\u000b\u00027\u0006)\u0011\r\u001d9msR\u0019q)!\b\t\u000bi\u0002\u0002\u0019\u0001\u001f\u0002\u000fUt\u0017\r\u001d9msR!\u00111EA\u0015!\u0011I\u0013Q\u0005\u001f\n\u0007\u0005\u001d\"F\u0001\u0004PaRLwN\u001c\u0005\t\u0003W\t\u0012\u0011!a\u0001\u000f\u0006\u0019\u0001\u0010\n\u0019\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\u0005E\u0002c\u0001/\u00024%\u0019\u0011QG/\u0003\r=\u0013'.Z2u\u0001")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/pdf/TempDirTaskOutput.class */
public class TempDirTaskOutput extends DirectoryTaskOutput implements Product, Serializable {
    private final File destination;

    public static Option<File> unapply(final TempDirTaskOutput x$0) {
        return TempDirTaskOutput$.MODULE$.unapply(x$0);
    }

    public static TempDirTaskOutput apply(final File destination) {
        return TempDirTaskOutput$.MODULE$.apply(destination);
    }

    public static <A> Function1<File, A> andThen(final Function1<TempDirTaskOutput, A> g) {
        return TempDirTaskOutput$.MODULE$.andThen(g);
    }

    public static <A> Function1<A, TempDirTaskOutput> compose(final Function1<A, File> g) {
        return TempDirTaskOutput$.MODULE$.compose(g);
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public File destination() {
        return this.destination;
    }

    public TempDirTaskOutput copy(final File destination) {
        return new TempDirTaskOutput(destination);
    }

    public File copy$default$1() {
        return destination();
    }

    public String productPrefix() {
        return "TempDirTaskOutput";
    }

    public int productArity() {
        return 1;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return destination();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof TempDirTaskOutput;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "destination";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TempDirTaskOutput(final File destination) {
        super(destination);
        this.destination = destination;
        Product.$init$(this);
    }
}
