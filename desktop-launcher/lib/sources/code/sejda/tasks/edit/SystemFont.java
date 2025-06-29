package code.sejda.tasks.edit;

import code.sejda.tasks.common.SystemFonts$;
import code.util.Loggable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import org.apache.fontbox.ttf.OTFParser;
import org.apache.fontbox.ttf.OpenTypeFont;
import org.apache.fontbox.ttf.TrueTypeCollection;
import org.apache.fontbox.ttf.TrueTypeFont;
import org.sejda.model.pdf.font.FontResource;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.font.FontUtils;
import org.sejda.sambox.pdmodel.font.PDFont;
import org.sejda.sambox.pdmodel.font.PDType0Font;
import org.slf4j.Logger;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Option$;
import scala.Product;
import scala.Some;
import scala.Tuple4;
import scala.collection.Iterator;
import scala.collection.immutable.$colon;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.control.NonFatal$;

/* compiled from: EditParameters.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005-h\u0001\u0002\u0014(\u0001BB\u0001B\u0018\u0001\u0003\u0016\u0004%\ta\u0018\u0005\tQ\u0002\u0011\t\u0012)A\u0005A\"A\u0011\u000e\u0001BK\u0002\u0013\u0005q\f\u0003\u0005k\u0001\tE\t\u0015!\u0003a\u0011!Y\u0007A!f\u0001\n\u0003y\u0006\u0002\u00037\u0001\u0005#\u0005\u000b\u0011\u00021\t\u00115\u0004!Q3A\u0005\u0002}C\u0001B\u001c\u0001\u0003\u0012\u0003\u0006I\u0001\u0019\u0005\u0006_\u0002!\t\u0001\u001d\u0005\u0006o\u0002!\t\u0005\u001f\u0005\u0006s\u0002!\tE\u001f\u0005\u000b\u0003\u0007\u0001\u0001R1A\u0005\u0002\u0005\u0015\u0001bBA\u0006\u0001\u0011\u0005\u0011Q\u0002\u0005\b\u0003+\u0001A\u0011AA\u0007\u0011\u001d\t9\u0002\u0001C\u0001\u00033Aq!a\u0010\u0001\t\u0013\t\t\u0005C\u0005\u0002F\u0001\t\t\u0011\"\u0001\u0002H!I\u0011\u0011\u000b\u0001\u0012\u0002\u0013\u0005\u00111\u000b\u0005\n\u0003S\u0002\u0011\u0013!C\u0001\u0003'B\u0011\"a\u001b\u0001#\u0003%\t!a\u0015\t\u0013\u00055\u0004!%A\u0005\u0002\u0005M\u0003\"CA8\u0001\u0005\u0005I\u0011IA\u0003\u0011%\t\t\bAA\u0001\n\u0003\t\u0019\bC\u0005\u0002|\u0001\t\t\u0011\"\u0001\u0002~!I\u0011\u0011\u0012\u0001\u0002\u0002\u0013\u0005\u00131\u0012\u0005\n\u00033\u0003\u0011\u0011!C\u0001\u00037C\u0011\"a(\u0001\u0003\u0003%\t%!)\t\u0013\u0005\u0015\u0006!!A\u0005B\u0005\u001d\u0006\"CAU\u0001\u0005\u0005I\u0011IAV\u0011%\ti\u000bAA\u0001\n\u0003\nykB\u0005\u00024\u001e\n\t\u0011#\u0001\u00026\u001aAaeJA\u0001\u0012\u0003\t9\f\u0003\u0004pA\u0011\u0005\u0011\u0011\u001a\u0005\n\u0003S\u0003\u0013\u0011!C#\u0003WC\u0011\"a3!\u0003\u0003%\t)!4\t\u0013\u0005]\u0007%!A\u0005\u0002\u0006e\u0007\"CAtA\u0005\u0005I\u0011BAu\u0005)\u0019\u0016p\u001d;f[\u001a{g\u000e\u001e\u0006\u0003Q%\nA!\u001a3ji*\u0011!fK\u0001\u0006i\u0006\u001c8n\u001d\u0006\u0003Y5\nQa]3kI\u0006T\u0011AL\u0001\u0005G>$Wm\u0001\u0001\u0014\r\u0001\t\u0014H\u0012'S!\t\u0011t'D\u00014\u0015\t!T'\u0001\u0003mC:<'\"\u0001\u001c\u0002\t)\fg/Y\u0005\u0003qM\u0012aa\u00142kK\u000e$\bC\u0001\u001eE\u001b\u0005Y$B\u0001\u001f>\u0003\u00111wN\u001c;\u000b\u0005yz\u0014a\u00019eM*\u0011\u0001)Q\u0001\u0006[>$W\r\u001c\u0006\u0003Y\tS\u0011aQ\u0001\u0004_J<\u0017BA#<\u000511uN\u001c;SKN|WO]2f!\t9%*D\u0001I\u0015\tIU&\u0001\u0003vi&d\u0017BA&I\u0005!aunZ4bE2,\u0007CA'Q\u001b\u0005q%\"A(\u0002\u000bM\u001c\u0017\r\\1\n\u0005Es%a\u0002)s_\u0012,8\r\u001e\t\u0003'ns!\u0001V-\u000f\u0005UCV\"\u0001,\u000b\u0005]{\u0013A\u0002\u001fs_>$h(C\u0001P\u0013\tQf*A\u0004qC\u000e\\\u0017mZ3\n\u0005qk&\u0001D*fe&\fG.\u001b>bE2,'B\u0001.O\u0003\u0011\u0001\u0018\r\u001e5\u0016\u0003\u0001\u0004\"!Y3\u000f\u0005\t\u001c\u0007CA+O\u0013\t!g*\u0001\u0004Qe\u0016$WMZ\u0005\u0003M\u001e\u0014aa\u0015;sS:<'B\u00013O\u0003\u0015\u0001\u0018\r\u001e5!\u00039\u0001xn\u001d;tGJL\u0007\u000f\u001e(b[\u0016\fq\u0002]8tiN\u001c'/\u001b9u\u001d\u0006lW\rI\u0001\u0007M\u0006l\u0017\u000e\\=\u0002\u000f\u0019\fW.\u001b7zA\u0005)1\u000f^=mK\u000611\u000f^=mK\u0002\na\u0001P5oSRtD#B9tiV4\bC\u0001:\u0001\u001b\u00059\u0003\"\u00020\n\u0001\u0004\u0001\u0007\"B5\n\u0001\u0004\u0001\u0007\"B6\n\u0001\u0004\u0001\u0007\"B7\n\u0001\u0004\u0001\u0017aC4fiJ+7o\\;sG\u0016$\u0012\u0001Y\u0001\u000eO\u0016$hi\u001c8u'R\u0014X-Y7\u0015\u0003m\u0004\"\u0001`@\u000e\u0003uT!A`\u001b\u0002\u0005%|\u0017bAA\u0001{\nY\u0011J\u001c9viN#(/Z1n\u00039\u0019H/\u001f7f\u0019><XM]\"bg\u0016,\"!a\u0002\u0011\u0007I\nI!\u0003\u0002gg\u0005!!m\u001c7e+\t\ty\u0001E\u0002N\u0003#I1!a\u0005O\u0005\u001d\u0011un\u001c7fC:\fa!\u001b;bY&\u001c\u0017\u0001\u00027pC\u0012$B!a\u0007\u00024A)Q*!\b\u0002\"%\u0019\u0011q\u0004(\u0003\r=\u0003H/[8o!\u0011\t\u0019#a\f\u000e\u0005\u0005\u0015\"b\u0001\u001f\u0002()!\u0011\u0011FA\u0016\u0003\u001d\u0001H-\\8eK2T1!!\fB\u0003\u0019\u0019\u0018-\u001c2pq&!\u0011\u0011GA\u0013\u0005\u0019\u0001FIR8oi\"9\u0011QG\bA\u0002\u0005]\u0012a\u00013pGB!\u0011\u0011HA\u001e\u001b\t\t9#\u0003\u0003\u0002>\u0005\u001d\"A\u0003)E\t>\u001cW/\\3oi\u0006)q\f\\8bIR!\u00111DA\"\u0011\u001d\t)\u0004\u0005a\u0001\u0003o\tAaY8qsRI\u0011/!\u0013\u0002L\u00055\u0013q\n\u0005\b=F\u0001\n\u00111\u0001a\u0011\u001dI\u0017\u0003%AA\u0002\u0001Dqa[\t\u0011\u0002\u0003\u0007\u0001\rC\u0004n#A\u0005\t\u0019\u00011\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\u0011\u0011Q\u000b\u0016\u0004A\u0006]3FAA-!\u0011\tY&!\u001a\u000e\u0005\u0005u#\u0002BA0\u0003C\n\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0007\u0005\rd*\u0001\u0006b]:|G/\u0019;j_:LA!a\u001a\u0002^\t\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%e\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\u001a\u0014AD2paf$C-\u001a4bk2$H\u0005N\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0005\u0005U\u0004cA'\u0002x%\u0019\u0011\u0011\u0010(\u0003\u0007%sG/\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\t\u0005}\u0014Q\u0011\t\u0004\u001b\u0006\u0005\u0015bAAB\u001d\n\u0019\u0011I\\=\t\u0013\u0005\u001d\u0005$!AA\u0002\u0005U\u0014a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0002\u000eB1\u0011qRAK\u0003\u007fj!!!%\u000b\u0007\u0005Me*\u0001\u0006d_2dWm\u0019;j_:LA!a&\u0002\u0012\nA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\u0011\ty!!(\t\u0013\u0005\u001d%$!AA\u0002\u0005}\u0014A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$B!a\u0002\u0002$\"I\u0011qQ\u000e\u0002\u0002\u0003\u0007\u0011QO\u0001\tQ\u0006\u001c\bnQ8eKR\u0011\u0011QO\u0001\ti>\u001cFO]5oOR\u0011\u0011qA\u0001\u0007KF,\u0018\r\\:\u0015\t\u0005=\u0011\u0011\u0017\u0005\n\u0003\u000fs\u0012\u0011!a\u0001\u0003\u007f\n!bU=ti\u0016lgi\u001c8u!\t\u0011\beE\u0003!\u0003s\u000b)\rE\u0005\u0002<\u0006\u0005\u0007\r\u00191ac6\u0011\u0011Q\u0018\u0006\u0004\u0003\u007fs\u0015a\u0002:v]RLW.Z\u0005\u0005\u0003\u0007\fiLA\tBEN$(/Y2u\rVt7\r^5p]R\u00022\u0001`Ad\u0013\taV\u0010\u0006\u0002\u00026\u0006)\u0011\r\u001d9msRI\u0011/a4\u0002R\u0006M\u0017Q\u001b\u0005\u0006=\u000e\u0002\r\u0001\u0019\u0005\u0006S\u000e\u0002\r\u0001\u0019\u0005\u0006W\u000e\u0002\r\u0001\u0019\u0005\u0006[\u000e\u0002\r\u0001Y\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\tY.a9\u0011\u000b5\u000bi\"!8\u0011\u000f5\u000by\u000e\u00191aA&\u0019\u0011\u0011\u001d(\u0003\rQ+\b\u000f\\35\u0011!\t)\u000fJA\u0001\u0002\u0004\t\u0018a\u0001=%a\u0005aqO]5uKJ+\u0007\u000f\\1dKR\t\u0011\u0007")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/SystemFont.class */
public class SystemFont implements FontResource, Loggable, Product, Serializable {
    private String styleLowerCase;
    private final String path;
    private final String postscriptName;
    private final String family;
    private final String style;
    private transient Logger logger;
    private volatile boolean bitmap$0;
    private volatile transient boolean bitmap$trans$0;

    public static Option<Tuple4<String, String, String, String>> unapply(final SystemFont x$0) {
        return SystemFont$.MODULE$.unapply(x$0);
    }

    public static SystemFont apply(final String path, final String postscriptName, final String family, final String style) {
        return SystemFont$.MODULE$.apply(path, postscriptName, family, style);
    }

    public static Function1<Tuple4<String, String, String, String>, SystemFont> tupled() {
        return SystemFont$.MODULE$.tupled();
    }

    public static Function1<String, Function1<String, Function1<String, Function1<String, SystemFont>>>> curried() {
        return SystemFont$.MODULE$.curried();
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override // org.sejda.model.pdf.font.FontResource
    public Integer priority() {
        return super.priority();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v8, types: [code.sejda.tasks.edit.SystemFont] */
    private Logger logger$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (!this.bitmap$trans$0) {
                this.logger = logger();
                r0 = this;
                r0.bitmap$trans$0 = true;
            }
        }
        return this.logger;
    }

    @Override // code.util.Loggable
    public Logger logger() {
        return !this.bitmap$trans$0 ? logger$lzycompute() : this.logger;
    }

    public String path() {
        return this.path;
    }

    public String postscriptName() {
        return this.postscriptName;
    }

    public String family() {
        return this.family;
    }

    public String style() {
        return this.style;
    }

    public SystemFont copy(final String path, final String postscriptName, final String family, final String style) {
        return new SystemFont(path, postscriptName, family, style);
    }

    public String copy$default$1() {
        return path();
    }

    public String copy$default$2() {
        return postscriptName();
    }

    public String copy$default$3() {
        return family();
    }

    public String copy$default$4() {
        return style();
    }

    public String productPrefix() {
        return "SystemFont";
    }

    public int productArity() {
        return 4;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return path();
            case 1:
                return postscriptName();
            case 2:
                return family();
            case 3:
                return style();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof SystemFont;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "path";
            case 1:
                return "postscriptName";
            case 2:
                return "family";
            case 3:
                return "style";
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
            if (x$1 instanceof SystemFont) {
                SystemFont systemFont = (SystemFont) x$1;
                String strPath = path();
                String strPath2 = systemFont.path();
                if (strPath != null ? strPath.equals(strPath2) : strPath2 == null) {
                    String strPostscriptName = postscriptName();
                    String strPostscriptName2 = systemFont.postscriptName();
                    if (strPostscriptName != null ? strPostscriptName.equals(strPostscriptName2) : strPostscriptName2 == null) {
                        String strFamily = family();
                        String strFamily2 = systemFont.family();
                        if (strFamily != null ? strFamily.equals(strFamily2) : strFamily2 == null) {
                            String strStyle = style();
                            String strStyle2 = systemFont.style();
                            if (strStyle != null ? strStyle.equals(strStyle2) : strStyle2 == null) {
                                if (systemFont.canEqual(this)) {
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

    public SystemFont(final String path, final String postscriptName, final String family, final String style) {
        this.path = path;
        this.postscriptName = postscriptName;
        this.family = family;
        this.style = style;
        Loggable.$init$(this);
        Product.$init$(this);
    }

    @Override // org.sejda.model.pdf.font.FontResource
    public String getResource() {
        return path();
    }

    @Override // org.sejda.model.pdf.font.FontResource
    public InputStream getFontStream() {
        return new FileInputStream(new File(path()));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v8, types: [code.sejda.tasks.edit.SystemFont] */
    private String styleLowerCase$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (!this.bitmap$0) {
                this.styleLowerCase = style().toLowerCase();
                r0 = this;
                r0.bitmap$0 = true;
            }
        }
        return this.styleLowerCase;
    }

    public String styleLowerCase() {
        return !this.bitmap$0 ? styleLowerCase$lzycompute() : this.styleLowerCase;
    }

    public boolean bold() {
        String strStyle = style();
        if (strStyle != null ? !strStyle.equals("Bold") : "Bold" != 0) {
            String strStyle2 = style();
            if (strStyle2 != null ? !strStyle2.equals("Black") : "Black" != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean italic() {
        return new $colon.colon("italic", new $colon.colon("oblique", new $colon.colon("slated", Nil$.MODULE$))).exists(x$1 -> {
            return BoxesRunTime.boxToBoolean($anonfun$italic$1(this, x$1));
        });
    }

    public static final /* synthetic */ boolean $anonfun$italic$1(final SystemFont $this, final CharSequence x$1) {
        return $this.styleLowerCase().contains(x$1);
    }

    public Option<PDFont> load(final PDDocument doc) {
        try {
            return _load(doc);
        } catch (Throwable th) {
            if (!NonFatal$.MODULE$.apply(th)) {
                throw th;
            }
            logger().warn(new StringBuilder(16).append("Could not load: ").append(this).toString(), th);
            if (!th.getMessage().contains("does not permit embedding")) {
                BoxedUnit boxedUnit = BoxedUnit.UNIT;
            } else {
                SystemFonts$.MODULE$.addFailureReason(family(), "Font does not permit embedding");
            }
            return None$.MODULE$;
        }
    }

    private Option<PDFont> _load(final PDDocument doc) throws IOException {
        String lowercasedPath = path().toLowerCase();
        if (lowercasedPath.endsWith(".ttf")) {
            File file = new File(path());
            return Option$.MODULE$.apply(PDType0Font.load(doc, file));
        }
        if (!lowercasedPath.endsWith(".ttc") && !lowercasedPath.endsWith(".otc")) {
            if (!lowercasedPath.endsWith(".otf")) {
                throw new RuntimeException(new StringBuilder(18).append("Cannot load font: ").append(this).toString());
            }
            OpenTypeFont otf = new OTFParser(false, false).parse(getFontStream());
            return new Some(PDType0Font.load(doc, (TrueTypeFont) otf, false));
        }
        TrueTypeCollection ttc = new TrueTypeCollection(getFontStream());
        TrueTypeFont ttf = ttc.getFontByName(postscriptName());
        if (ttf == null) {
            logger().warn(new StringBuilder(39).append("Could not find font: '").append(postscriptName()).append("' in collection: ").append(path()).toString());
        }
        boolean subset = FontUtils.isSubsettingPermitted(ttf);
        return new Some(PDType0Font.load(doc, ttf, subset));
    }
}
