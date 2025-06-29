package code.util.pdf;

import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.collection.immutable.Seq;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: PageRanges.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005mc\u0001B\f\u0019\u0001~A\u0001\"\u000e\u0001\u0003\u0016\u0004%\tA\u000e\u0005\t\t\u0002\u0011\t\u0012)A\u0005o!)Q\t\u0001C\u0001\r\")!\n\u0001C\u0001\u0017\"9q\u000bAA\u0001\n\u0003A\u0006b\u0002.\u0001#\u0003%\ta\u0017\u0005\bM\u0002\t\t\u0011\"\u0011h\u0011\u001d\u0001\b!!A\u0005\u0002EDqA\u001d\u0001\u0002\u0002\u0013\u00051\u000fC\u0004z\u0001\u0005\u0005I\u0011\t>\t\u0011}\u0004\u0011\u0011!C\u0001\u0003\u0003A\u0011\"a\u0003\u0001\u0003\u0003%\t%!\u0004\t\u0013\u0005E\u0001!!A\u0005B\u0005M\u0001\"CA\u000b\u0001\u0005\u0005I\u0011IA\f\u0011%\tI\u0002AA\u0001\n\u0003\nYbB\u0005\u0002 a\t\t\u0011#\u0001\u0002\"\u0019Aq\u0003GA\u0001\u0012\u0003\t\u0019\u0003\u0003\u0004F#\u0011\u0005\u00111\b\u0005\n\u0003+\t\u0012\u0011!C#\u0003/A\u0011\"!\u0010\u0012\u0003\u0003%\t)a\u0010\t\u0013\u0005\r\u0013#!A\u0005\u0002\u0006\u0015\u0003\"CA)#\u0005\u0005I\u0011BA*\u00051\u0001\u0016mZ3SC:<W-\u0012=u\u0015\tI\"$A\u0002qI\u001aT!a\u0007\u000f\u0002\tU$\u0018\u000e\u001c\u0006\u0002;\u0005!1m\u001c3f\u0007\u0001\u0019B\u0001\u0001\u0011'SA\u0011\u0011\u0005J\u0007\u0002E)\t1%A\u0003tG\u0006d\u0017-\u0003\u0002&E\t1\u0011I\\=SK\u001a\u0004\"!I\u0014\n\u0005!\u0012#a\u0002)s_\u0012,8\r\u001e\t\u0003UIr!a\u000b\u0019\u000f\u00051zS\"A\u0017\u000b\u00059r\u0012A\u0002\u001fs_>$h(C\u0001$\u0013\t\t$%A\u0004qC\u000e\\\u0017mZ3\n\u0005M\"$\u0001D*fe&\fG.\u001b>bE2,'BA\u0019#\u0003))h\u000eZ3sYfLgnZ\u000b\u0002oA\u0011\u0001HQ\u0007\u0002s)\u0011!hO\u0001\u0005a\u0006<WM\u0003\u0002\u001ay)\u0011QHP\u0001\u0006[>$W\r\u001c\u0006\u0003\u007f\u0001\u000bQa]3kI\u0006T\u0011!Q\u0001\u0004_J<\u0017BA\":\u0005%\u0001\u0016mZ3SC:<W-A\u0006v]\u0012,'\u000f\\=j]\u001e\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0002H\u0013B\u0011\u0001\nA\u0007\u00021!)Qg\u0001a\u0001o\u0005y1\u000f\u001d7ji\n{WO\u001c3be&,7/F\u0001M!\ri%\u000bV\u0007\u0002\u001d*\u0011q\nU\u0001\nS6lW\u000f^1cY\u0016T!!\u0015\u0012\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0002T\u001d\n\u00191+Z9\u0011\u0005\u0005*\u0016B\u0001,#\u0005\rIe\u000e^\u0001\u0005G>\u0004\u0018\u0010\u0006\u0002H3\"9Q'\u0002I\u0001\u0002\u00049\u0014AD2paf$C-\u001a4bk2$H%M\u000b\u00029*\u0012q'X\u0016\u0002=B\u0011q\fZ\u0007\u0002A*\u0011\u0011MY\u0001\nk:\u001c\u0007.Z2lK\u0012T!a\u0019\u0012\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002fA\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u0005A\u0007CA5o\u001b\u0005Q'BA6m\u0003\u0011a\u0017M\\4\u000b\u00035\fAA[1wC&\u0011qN\u001b\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0003Q\u000ba\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0002uoB\u0011\u0011%^\u0005\u0003m\n\u00121!\u00118z\u0011\u001dA\u0018\"!AA\u0002Q\u000b1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014X#A>\u0011\u0007qlH/D\u0001Q\u0013\tq\bK\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BA\u0002\u0003\u0013\u00012!IA\u0003\u0013\r\t9A\t\u0002\b\u0005>|G.Z1o\u0011\u001dA8\"!AA\u0002Q\f!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR\u0019\u0001.a\u0004\t\u000fad\u0011\u0011!a\u0001)\u0006A\u0001.Y:i\u0007>$W\rF\u0001U\u0003!!xn\u0015;sS:<G#\u00015\u0002\r\u0015\fX/\u00197t)\u0011\t\u0019!!\b\t\u000fa|\u0011\u0011!a\u0001i\u0006a\u0001+Y4f%\u0006tw-Z#yiB\u0011\u0001*E\n\u0006#\u0005\u0015\u0012\u0011\u0007\t\u0007\u0003O\ticN$\u000e\u0005\u0005%\"bAA\u0016E\u00059!/\u001e8uS6,\u0017\u0002BA\u0018\u0003S\u0011\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c82!\u0011\t\u0019$!\u000f\u000e\u0005\u0005U\"bAA\u001cY\u0006\u0011\u0011n\\\u0005\u0004g\u0005UBCAA\u0011\u0003\u0015\t\u0007\u000f\u001d7z)\r9\u0015\u0011\t\u0005\u0006kQ\u0001\raN\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\t9%!\u0014\u0011\t\u0005\nIeN\u0005\u0004\u0003\u0017\u0012#AB(qi&|g\u000e\u0003\u0005\u0002PU\t\t\u00111\u0001H\u0003\rAH\u0005M\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003+\u00022![A,\u0013\r\tIF\u001b\u0002\u0007\u001f\nTWm\u0019;")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/pdf/PageRangeExt.class */
public class PageRangeExt implements Product, Serializable {
    private final org.sejda.model.pdf.page.PageRange underlying;

    public static Option<org.sejda.model.pdf.page.PageRange> unapply(final PageRangeExt x$0) {
        return PageRangeExt$.MODULE$.unapply(x$0);
    }

    public static PageRangeExt apply(final org.sejda.model.pdf.page.PageRange underlying) {
        return PageRangeExt$.MODULE$.apply(underlying);
    }

    public static <A> Function1<org.sejda.model.pdf.page.PageRange, A> andThen(final Function1<PageRangeExt, A> g) {
        return PageRangeExt$.MODULE$.andThen(g);
    }

    public static <A> Function1<A, PageRangeExt> compose(final Function1<A, org.sejda.model.pdf.page.PageRange> g) {
        return PageRangeExt$.MODULE$.compose(g);
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public org.sejda.model.pdf.page.PageRange underlying() {
        return this.underlying;
    }

    public PageRangeExt copy(final org.sejda.model.pdf.page.PageRange underlying) {
        return new PageRangeExt(underlying);
    }

    public org.sejda.model.pdf.page.PageRange copy$default$1() {
        return underlying();
    }

    public String productPrefix() {
        return "PageRangeExt";
    }

    public int productArity() {
        return 1;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return underlying();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof PageRangeExt;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "underlying";
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
            if (x$1 instanceof PageRangeExt) {
                PageRangeExt pageRangeExt = (PageRangeExt) x$1;
                org.sejda.model.pdf.page.PageRange pageRangeUnderlying = underlying();
                org.sejda.model.pdf.page.PageRange pageRangeUnderlying2 = pageRangeExt.underlying();
                if (pageRangeUnderlying != null ? pageRangeUnderlying.equals(pageRangeUnderlying2) : pageRangeUnderlying2 == null) {
                    if (pageRangeExt.canEqual(this)) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    public PageRangeExt(final org.sejda.model.pdf.page.PageRange underlying) {
        this.underlying = underlying;
        Product.$init$(this);
    }

    public Seq<Object> splitBoundaries() {
        return underlying().isUnbounded() ? package$.MODULE$.Seq().apply(ScalaRunTime$.MODULE$.wrapIntArray(new int[]{underlying().getStart()})) : package$.MODULE$.Seq().apply(ScalaRunTime$.MODULE$.wrapIntArray(new int[]{underlying().getStart(), underlying().getEnd() + 1}));
    }
}
