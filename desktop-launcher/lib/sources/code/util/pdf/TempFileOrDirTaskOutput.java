package code.util.pdf;

import java.io.File;
import java.io.Serializable;
import org.sejda.model.output.FileOrDirectoryTaskOutput;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: TempDirTaskOutput.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005]b\u0001B\n\u0015\u0001nA\u0001B\u000f\u0001\u0003\u0016\u0004%\ta\u000f\u0005\t\t\u0002\u0011\t\u0012)A\u0005y!)Q\t\u0001C\u0001\r\"9!\nAA\u0001\n\u0003Y\u0005bB'\u0001#\u0003%\tA\u0014\u0005\b3\u0002\t\t\u0011\"\u0011[\u0011\u001d\t\u0007!!A\u0005\u0002\tDqA\u001a\u0001\u0002\u0002\u0013\u0005q\rC\u0004n\u0001\u0005\u0005I\u0011\t8\t\u000fU\u0004\u0011\u0011!C\u0001m\"91\u0010AA\u0001\n\u0003bxa\u0002@\u0015\u0003\u0003E\ta \u0004\t'Q\t\t\u0011#\u0001\u0002\u0002!1Q)\u0004C\u0001\u0003'A\u0011\"!\u0006\u000e\u0003\u0003%)%a\u0006\t\u0013\u0005eQ\"!A\u0005\u0002\u0006m\u0001\"CA\u0010\u001b\u0005\u0005I\u0011QA\u0011\u0011%\ti#DA\u0001\n\u0013\tyCA\fUK6\u0004h)\u001b7f\u001fJ$\u0015N\u001d+bg.|U\u000f\u001e9vi*\u0011QCF\u0001\u0004a\u00124'BA\f\u0019\u0003\u0011)H/\u001b7\u000b\u0003e\tAaY8eK\u000e\u00011\u0003\u0002\u0001\u001dQ9\u0002\"!\b\u0014\u000e\u0003yQ!a\b\u0011\u0002\r=,H\u000f];u\u0015\t\t#%A\u0003n_\u0012,GN\u0003\u0002$I\u0005)1/\u001a6eC*\tQ%A\u0002pe\u001eL!a\n\u0010\u00033\u0019KG.Z(s\t&\u0014Xm\u0019;pef$\u0016m]6PkR\u0004X\u000f\u001e\t\u0003S1j\u0011A\u000b\u0006\u0002W\u0005)1oY1mC&\u0011QF\u000b\u0002\b!J|G-^2u!\tysG\u0004\u00021k9\u0011\u0011\u0007N\u0007\u0002e)\u00111GG\u0001\u0007yI|w\u000e\u001e \n\u0003-J!A\u000e\u0016\u0002\u000fA\f7m[1hK&\u0011\u0001(\u000f\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0003m)\n1\u0002Z3ti&t\u0017\r^5p]V\tA\b\u0005\u0002>\u00056\taH\u0003\u0002@\u0001\u0006\u0011\u0011n\u001c\u0006\u0002\u0003\u0006!!.\u0019<b\u0013\t\u0019eH\u0001\u0003GS2,\u0017\u0001\u00043fgRLg.\u0019;j_:\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0002H\u0013B\u0011\u0001\nA\u0007\u0002)!)!h\u0001a\u0001y\u0005!1m\u001c9z)\t9E\nC\u0004;\tA\u0005\t\u0019\u0001\u001f\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\tqJ\u000b\u0002=!.\n\u0011\u000b\u0005\u0002S/6\t1K\u0003\u0002U+\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0003-*\n!\"\u00198o_R\fG/[8o\u0013\tA6KA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DX#A.\u0011\u0005q{V\"A/\u000b\u0005y\u0003\u0015\u0001\u00027b]\u001eL!\u0001Y/\u0003\rM#(/\u001b8h\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\u0005\u0019\u0007CA\u0015e\u0013\t)'FA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0002iWB\u0011\u0011&[\u0005\u0003U*\u00121!\u00118z\u0011\u001da\u0007\"!AA\u0002\r\f1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014X#A8\u0011\u0007A\u001c\b.D\u0001r\u0015\t\u0011(&\u0001\u0006d_2dWm\u0019;j_:L!\u0001^9\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0003oj\u0004\"!\u000b=\n\u0005eT#a\u0002\"p_2,\u0017M\u001c\u0005\bY*\t\t\u00111\u0001i\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\u0005mk\bb\u00027\f\u0003\u0003\u0005\raY\u0001\u0018)\u0016l\u0007OR5mK>\u0013H)\u001b:UCN\\w*\u001e;qkR\u0004\"\u0001S\u0007\u0014\u000b5\t\u0019!a\u0004\u0011\r\u0005\u0015\u00111\u0002\u001fH\u001b\t\t9AC\u0002\u0002\n)\nqA];oi&lW-\u0003\u0003\u0002\u000e\u0005\u001d!!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8ocA\u0019Q(!\u0005\n\u0005arD#A@\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012aW\u0001\u0006CB\u0004H.\u001f\u000b\u0004\u000f\u0006u\u0001\"\u0002\u001e\u0011\u0001\u0004a\u0014aB;oCB\u0004H.\u001f\u000b\u0005\u0003G\tI\u0003\u0005\u0003*\u0003Ka\u0014bAA\u0014U\t1q\n\u001d;j_:D\u0001\"a\u000b\u0012\u0003\u0003\u0005\raR\u0001\u0004q\u0012\u0002\u0014\u0001D<sSR,'+\u001a9mC\u000e,GCAA\u0019!\ra\u00161G\u0005\u0004\u0003ki&AB(cU\u0016\u001cG\u000f")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/pdf/TempFileOrDirTaskOutput.class */
public class TempFileOrDirTaskOutput extends FileOrDirectoryTaskOutput implements Product, Serializable {
    private final File destination;

    public static Option<File> unapply(final TempFileOrDirTaskOutput x$0) {
        return TempFileOrDirTaskOutput$.MODULE$.unapply(x$0);
    }

    public static TempFileOrDirTaskOutput apply(final File destination) {
        return TempFileOrDirTaskOutput$.MODULE$.apply(destination);
    }

    public static <A> Function1<File, A> andThen(final Function1<TempFileOrDirTaskOutput, A> g) {
        return TempFileOrDirTaskOutput$.MODULE$.andThen(g);
    }

    public static <A> Function1<A, TempFileOrDirTaskOutput> compose(final Function1<A, File> g) {
        return TempFileOrDirTaskOutput$.MODULE$.compose(g);
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public File destination() {
        return this.destination;
    }

    public TempFileOrDirTaskOutput copy(final File destination) {
        return new TempFileOrDirTaskOutput(destination);
    }

    public File copy$default$1() {
        return destination();
    }

    public String productPrefix() {
        return "TempFileOrDirTaskOutput";
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
        return x$1 instanceof TempFileOrDirTaskOutput;
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
    public TempFileOrDirTaskOutput(final File destination) {
        super(destination);
        this.destination = destination;
        Product.$init$(this);
    }
}
