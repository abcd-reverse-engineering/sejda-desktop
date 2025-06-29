package code.sejda.tasks.common;

import java.io.InputStream;
import java.io.Serializable;
import org.sejda.impl.sambox.util.FontUtils;
import org.sejda.model.pdf.font.FontResource;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.font.PDFont;
import scala.Option;
import scala.Predef$;
import scala.Product;
import scala.collection.Iterator;
import scala.collection.StringOps$;
import scala.collection.immutable.Map;
import scala.collection.immutable.Set;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.Try$;

/* compiled from: GoogleFonts.scala */
@ScalaSignature(bytes = "\u0006\u0005\t%u!\u0002!B\u0011\u0003Qe!\u0002'B\u0011\u0003i\u0005\"\u0002+\u0002\t\u0003)f\u0001\u0002,\u0002\u0001^C\u0001\u0002`\u0002\u0003\u0016\u0004%\t! \u0005\n\u0003\u001b\u0019!\u0011#Q\u0001\nyD\u0011\"a\u0004\u0004\u0005+\u0007I\u0011A?\t\u0013\u0005E1A!E!\u0002\u0013q\bB\u0002+\u0004\t\u0003\t\u0019\u0002C\u0004\u0002\u001e\r!\t%a\b\t\u000f\u000552\u0001\"\u0011\u00020!9\u0011\u0011G\u0002\u0005\u0002\u0005M\u0002\"CA-\u0007\u0005\u0005I\u0011AA.\u0011%\t\tgAI\u0001\n\u0003\t\u0019\u0007C\u0005\u0002z\r\t\n\u0011\"\u0001\u0002d!I\u00111P\u0002\u0002\u0002\u0013\u0005\u0013Q\u0010\u0005\n\u0003\u0007\u001b\u0011\u0011!C\u0001\u0003\u000bC\u0011\"!$\u0004\u0003\u0003%\t!a$\t\u0013\u0005m5!!A\u0005B\u0005u\u0005\"CAV\u0007\u0005\u0005I\u0011AAW\u0011%\t9lAA\u0001\n\u0003\nI\fC\u0005\u0002>\u000e\t\t\u0011\"\u0011\u0002@\"I\u0011\u0011Y\u0002\u0002\u0002\u0013\u0005\u00131\u0019\u0005\n\u0003\u000b\u001c\u0011\u0011!C!\u0003\u000f<\u0011\"a3\u0002\u0003\u0003E\t!!4\u0007\u0011Y\u000b\u0011\u0011!E\u0001\u0003\u001fDa\u0001V\r\u0005\u0002\u0005\u0005\b\"CAa3\u0005\u0005IQIAb\u0011%\t\u0019/GA\u0001\n\u0003\u000b)\u000fC\u0005\u0002lf\t\t\u0011\"!\u0002n\"I\u00111`\r\u0002\u0002\u0013%\u0011Q \u0004\u0007\u0003\u007f\f\u0001I!\u0001\t\u0011q|\"Q3A\u0005\u0002uD\u0011\"!\u0004 \u0005#\u0005\u000b\u0011\u0002@\t\rQ{B\u0011\u0001B\u0002\u0011\u001d\t\td\bC\u0001\u0005\u0013A\u0011B!\u0005 #\u0003%\tAa\u0005\t\u000f\u0005=q\u0004\"\u0001\u0003\u0018!I\u0011\u0011L\u0010\u0002\u0002\u0013\u0005!q\u0004\u0005\n\u0003Cz\u0012\u0013!C\u0001\u0003GB\u0011\"a\u001f \u0003\u0003%\t%! \t\u0013\u0005\ru$!A\u0005\u0002\u0005\u0015\u0005\"CAG?\u0005\u0005I\u0011\u0001B\u0012\u0011%\tYjHA\u0001\n\u0003\ni\nC\u0005\u0002,~\t\t\u0011\"\u0001\u0003(!I\u0011qW\u0010\u0002\u0002\u0013\u0005#1\u0006\u0005\n\u0003{{\u0012\u0011!C!\u0003\u007fC\u0011\"!1 \u0003\u0003%\t%a1\t\u0013\u0005\u0015w$!A\u0005B\t=r!\u0003B\u001a\u0003\u0005\u0005\t\u0012\u0001B\u001b\r%\ty0AA\u0001\u0012\u0003\u00119\u0004\u0003\u0004Ue\u0011\u0005!q\b\u0005\n\u0003\u0003\u0014\u0014\u0011!C#\u0003\u0007D\u0011\"a93\u0003\u0003%\tI!\u0011\t\u0013\u0005-('!A\u0005\u0002\n\u0015\u0003\"CA~e\u0005\u0005I\u0011BA\u007f\u0011)\u0011Y%\u0001EC\u0002\u0013\u0005!Q\n\u0005\b\u00057\nA\u0011\u0001B/\u0011\u001d\u0011\u0019'\u0001C\u0001\u0005KBqA!\u001b\u0002\t\u0003\u0011Y\u0007C\u0004\u0003p\u0005!\tA!\u001d\t\u0013\t]\u0014A1A\u0005\u0002\te\u0004\u0002\u0003BA\u0003\u0001\u0006IAa\u001f\t\u000f\t\r\u0015\u0001\"\u0001\u0003\u0006\u0006Yqi\\8hY\u00164uN\u001c;t\u0015\t\u00115)\u0001\u0004d_6lwN\u001c\u0006\u0003\t\u0016\u000bQ\u0001^1tWNT!AR$\u0002\u000bM,'\u000eZ1\u000b\u0003!\u000bAaY8eK\u000e\u0001\u0001CA&\u0002\u001b\u0005\t%aC$p_\u001edWMR8oiN\u001c\"!\u0001(\u0011\u0005=\u0013V\"\u0001)\u000b\u0003E\u000bQa]2bY\u0006L!a\u0015)\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\t!JA\tH_><G.\u001a$p]R4\u0016M]5b]R\u001cRa\u0001-a[B\u0004\"!\u00170\u000e\u0003iS!a\u0017/\u0002\t1\fgn\u001a\u0006\u0002;\u0006!!.\u0019<b\u0013\ty&L\u0001\u0004PE*,7\r\u001e\t\u0003C.l\u0011A\u0019\u0006\u0003G\u0012\fAAZ8oi*\u0011QMZ\u0001\u0004a\u00124'BA4i\u0003\u0015iw\u000eZ3m\u0015\t1\u0015NC\u0001k\u0003\ry'oZ\u0005\u0003Y\n\u0014ABR8oiJ+7o\\;sG\u0016\u0004\"a\u00148\n\u0005=\u0004&a\u0002)s_\u0012,8\r\u001e\t\u0003cft!A]<\u000f\u0005M4X\"\u0001;\u000b\u0005UL\u0015A\u0002\u001fs_>$h(C\u0001R\u0013\tA\b+A\u0004qC\u000e\\\u0017mZ3\n\u0005i\\(\u0001D*fe&\fG.\u001b>bE2,'B\u0001=Q\u0003\u0011q\u0017-\\3\u0016\u0003y\u00042a`A\u0004\u001d\u0011\t\t!a\u0001\u0011\u0005M\u0004\u0016bAA\u0003!\u00061\u0001K]3eK\u001aLA!!\u0003\u0002\f\t11\u000b\u001e:j]\u001eT1!!\u0002Q\u0003\u0015q\u0017-\\3!\u0003\u001d1\u0018M]5b]R\f\u0001B^1sS\u0006tG\u000f\t\u000b\u0007\u0003+\tI\"a\u0007\u0011\u0007\u0005]1!D\u0001\u0002\u0011\u0015a\b\u00021\u0001\u007f\u0011\u0019\ty\u0001\u0003a\u0001}\u0006iq-\u001a;G_:$8\u000b\u001e:fC6$\"!!\t\u0011\t\u0005\r\u0012\u0011F\u0007\u0003\u0003KQ1!a\n]\u0003\tIw.\u0003\u0003\u0002,\u0005\u0015\"aC%oaV$8\u000b\u001e:fC6\f1bZ3u%\u0016\u001cx.\u001e:dKR\ta0\u0001\u0005m_\u0006$gi\u001c8u)\u0011\t)$!\u0014\u0011\u000b=\u000b9$a\u000f\n\u0007\u0005e\u0002K\u0001\u0004PaRLwN\u001c\t\u0005\u0003{\tI%\u0004\u0002\u0002@)\u00191-!\u0011\u000b\t\u0005\r\u0013QI\u0001\ba\u0012lw\u000eZ3m\u0015\r\t9\u0005[\u0001\u0007g\u0006l'm\u001c=\n\t\u0005-\u0013q\b\u0002\u0007!\u00123uN\u001c;\t\u000f\u0005=3\u00021\u0001\u0002R\u0005\u0019Am\\2\u0011\t\u0005M\u0013QK\u0007\u0003\u0003\u0003JA!a\u0016\u0002B\tQ\u0001\u000b\u0012#pGVlWM\u001c;\u0002\t\r|\u0007/\u001f\u000b\u0007\u0003+\ti&a\u0018\t\u000fqd\u0001\u0013!a\u0001}\"A\u0011q\u0002\u0007\u0011\u0002\u0003\u0007a0\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0005\u0005\u0015$f\u0001@\u0002h-\u0012\u0011\u0011\u000e\t\u0005\u0003W\n)(\u0004\u0002\u0002n)!\u0011qNA9\u0003%)hn\u00195fG.,GMC\u0002\u0002tA\u000b!\"\u00198o_R\fG/[8o\u0013\u0011\t9(!\u001c\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\ty\bE\u0002Z\u0003\u0003K1!!\u0003[\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\t\t9\tE\u0002P\u0003\u0013K1!a#Q\u0005\rIe\u000e^\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\u0011\t\t*a&\u0011\u0007=\u000b\u0019*C\u0002\u0002\u0016B\u00131!\u00118z\u0011%\tI*EA\u0001\u0002\u0004\t9)A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0003\u0003?\u0003b!!)\u0002(\u0006EUBAAR\u0015\r\t)\u000bU\u0001\u000bG>dG.Z2uS>t\u0017\u0002BAU\u0003G\u0013\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR!\u0011qVA[!\ry\u0015\u0011W\u0005\u0004\u0003g\u0003&a\u0002\"p_2,\u0017M\u001c\u0005\n\u00033\u001b\u0012\u0011!a\u0001\u0003#\u000b!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!\u0011qPA^\u0011%\tI\nFA\u0001\u0002\u0004\t9)\u0001\u0005iCND7i\u001c3f)\t\t9)\u0001\u0005u_N#(/\u001b8h)\t\ty(\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0003_\u000bI\rC\u0005\u0002\u001a^\t\t\u00111\u0001\u0002\u0012\u0006\tri\\8hY\u00164uN\u001c;WCJL\u0017M\u001c;\u0011\u0007\u0005]\u0011dE\u0003\u001a\u0003#\fi\u000e\u0005\u0005\u0002T\u0006egP`A\u000b\u001b\t\t)NC\u0002\u0002XB\u000bqA];oi&lW-\u0003\u0003\u0002\\\u0006U'!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8oeA!\u00111EAp\u0013\rQ\u0018Q\u0005\u000b\u0003\u0003\u001b\fQ!\u00199qYf$b!!\u0006\u0002h\u0006%\b\"\u0002?\u001d\u0001\u0004q\bBBA\b9\u0001\u0007a0A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\u0005=\u0018q\u001f\t\u0006\u001f\u0006]\u0012\u0011\u001f\t\u0006\u001f\u0006MhP`\u0005\u0004\u0003k\u0004&A\u0002+va2,'\u0007C\u0005\u0002zv\t\t\u00111\u0001\u0002\u0016\u0005\u0019\u0001\u0010\n\u0019\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0003a\u0013!bR8pO2,gi\u001c8u'\u0011yb*\u001c9\u0015\t\t\u0015!q\u0001\t\u0004\u0003/y\u0002\"\u0002?#\u0001\u0004qHCBA\u001b\u0005\u0017\u0011i\u0001C\u0004\u0002P\r\u0002\r!!\u0015\t\u0013\t=1\u0005%AA\u0002\u0005=\u0016\u0001\u00022pY\u0012\f!\u0003\\8bI\u001a{g\u000e\u001e\u0013eK\u001a\fW\u000f\u001c;%eU\u0011!Q\u0003\u0016\u0005\u0003_\u000b9\u0007\u0006\u0004\u00026\te!1\u0004\u0005\b\u0003\u001f*\u0003\u0019AA)\u0011\u0019\u0011i\"\na\u0001}\u0006\ta\u000f\u0006\u0003\u0003\u0006\t\u0005\u0002b\u0002?'!\u0003\u0005\rA \u000b\u0005\u0003#\u0013)\u0003C\u0005\u0002\u001a*\n\t\u00111\u0001\u0002\bR!\u0011q\u0016B\u0015\u0011%\tI\nLA\u0001\u0002\u0004\t\t\n\u0006\u0003\u0002��\t5\u0002\"CAM[\u0005\u0005\t\u0019AAD)\u0011\tyK!\r\t\u0013\u0005e\u0005'!AA\u0002\u0005E\u0015AC$p_\u001edWMR8oiB\u0019\u0011q\u0003\u001a\u0014\u000bI\u0012I$!8\u0011\u000f\u0005M'1\b@\u0003\u0006%!!QHAk\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|g.\r\u000b\u0003\u0005k!BA!\u0002\u0003D!)A0\u000ea\u0001}R!!q\tB%!\u0011y\u0015q\u0007@\t\u0013\u0005eh'!AA\u0002\t\u0015\u0011!\u00024p]R\u001cXC\u0001B(!\u0019\u0011\tFa\u0016\u0003\u00065\u0011!1\u000b\u0006\u0005\u0005+\n\u0019+A\u0005j[6,H/\u00192mK&!!\u0011\fB*\u0005\r\u0019V\r^\u0001\u0005M&tG\r\u0006\u0003\u0003`\t\u0005\u0004#B(\u00028\t\u0015\u0001\"\u0002?:\u0001\u0004q\u0018aA4fiR!!Q\u0001B4\u0011\u0015a(\b1\u0001\u007f\u0003\u0019)\u00070[:ugR!\u0011q\u0016B7\u0011\u0015a8\b1\u0001\u007f\u0003A1\u0017N\u001c3Cs:{'/\\1mSj,G\r\u0006\u0003\u0003`\tM\u0004B\u0002B;y\u0001\u0007a0\u0001\bo_Jl\u0017\r\\5{K\u0012t\u0015-\\3\u0002\u00115\f\u0007\u000f]5oON,\"Aa\u001f\u0011\u0011\tE#QPA@\u0003\u007fJAAa \u0003T\t\u0019Q*\u00199\u0002\u00135\f\u0007\u000f]5oON\u0004\u0013!\u00044j]\u0012\u0014\u00150T1qa&tw\r\u0006\u0003\u0003`\t\u001d\u0005B\u0002B;\u007f\u0001\u0007a\u0010")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/GoogleFonts.class */
public final class GoogleFonts {
    public static Option<GoogleFont> findByMapping(final String normalizedName) {
        return GoogleFonts$.MODULE$.findByMapping(normalizedName);
    }

    public static Map<String, String> mappings() {
        return GoogleFonts$.MODULE$.mappings();
    }

    public static Option<GoogleFont> findByNormalized(final String normalizedName) {
        return GoogleFonts$.MODULE$.findByNormalized(normalizedName);
    }

    public static boolean exists(final String name) {
        return GoogleFonts$.MODULE$.exists(name);
    }

    public static GoogleFont get(final String name) {
        return GoogleFonts$.MODULE$.get(name);
    }

    public static Option<GoogleFont> find(final String name) {
        return GoogleFonts$.MODULE$.find(name);
    }

    public static Set<GoogleFont> fonts() {
        return GoogleFonts$.MODULE$.fonts();
    }

    /* compiled from: GoogleFonts.scala */
    /* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/GoogleFonts$GoogleFontVariant.class */
    public static class GoogleFontVariant implements FontResource, Product, Serializable {
        private final String name;
        private final String variant;

        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override // org.sejda.model.pdf.font.FontResource
        public Integer priority() {
            return super.priority();
        }

        public String name() {
            return this.name;
        }

        public String variant() {
            return this.variant;
        }

        public GoogleFontVariant copy(final String name, final String variant) {
            return new GoogleFontVariant(name, variant);
        }

        public String copy$default$1() {
            return name();
        }

        public String copy$default$2() {
            return variant();
        }

        public String productPrefix() {
            return "GoogleFontVariant";
        }

        public int productArity() {
            return 2;
        }

        public Object productElement(final int x$1) {
            switch (x$1) {
                case 0:
                    return name();
                case 1:
                    return variant();
                default:
                    return Statics.ioobe(x$1);
            }
        }

        public Iterator<Object> productIterator() {
            return ScalaRunTime$.MODULE$.typedProductIterator(this);
        }

        public boolean canEqual(final Object x$1) {
            return x$1 instanceof GoogleFontVariant;
        }

        public String productElementName(final int x$1) {
            switch (x$1) {
                case 0:
                    return "name";
                case 1:
                    return "variant";
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
                if (x$1 instanceof GoogleFontVariant) {
                    GoogleFontVariant googleFontVariant = (GoogleFontVariant) x$1;
                    String strName = name();
                    String strName2 = googleFontVariant.name();
                    if (strName != null ? strName.equals(strName2) : strName2 == null) {
                        String strVariant = variant();
                        String strVariant2 = googleFontVariant.variant();
                        if (strVariant != null ? strVariant.equals(strVariant2) : strVariant2 == null) {
                            if (googleFontVariant.canEqual(this)) {
                            }
                        }
                    }
                }
                return false;
            }
            return true;
        }

        public GoogleFontVariant(final String name, final String variant) {
            this.name = name;
            this.variant = variant;
            Product.$init$(this);
        }

        @Override // org.sejda.model.pdf.font.FontResource
        public InputStream getFontStream() {
            return getClass().getResourceAsStream(getResource());
        }

        @Override // org.sejda.model.pdf.font.FontResource
        public String getResource() {
            return new StringBuilder(13).append("/fonts/").append(StringOps$.MODULE$.replaceAllLiterally$extension(Predef$.MODULE$.augmentString(name()), " ", "_")).append("/").append(StringOps$.MODULE$.replaceAllLiterally$extension(Predef$.MODULE$.augmentString(name()), " ", "")).append("-").append(variant()).append(".ttf").toString();
        }

        public Option<PDFont> loadFont(final PDDocument doc) {
            return Try$.MODULE$.apply(() -> {
                return FontUtils.loadFont(doc, this);
            }).toOption().filter(x$1 -> {
                return BoxesRunTime.boxToBoolean($anonfun$loadFont$2(x$1));
            });
        }

        public static final /* synthetic */ boolean $anonfun$loadFont$2(final PDFont x$1) {
            return x$1 != null;
        }
    }

    /* compiled from: GoogleFonts.scala */
    /* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/GoogleFonts$GoogleFont.class */
    public static class GoogleFont implements Product, Serializable {
        private final String name;

        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public String name() {
            return this.name;
        }

        public GoogleFont copy(final String name) {
            return new GoogleFont(name);
        }

        public String copy$default$1() {
            return name();
        }

        public String productPrefix() {
            return "GoogleFont";
        }

        public int productArity() {
            return 1;
        }

        public Object productElement(final int x$1) {
            switch (x$1) {
                case 0:
                    return name();
                default:
                    return Statics.ioobe(x$1);
            }
        }

        public Iterator<Object> productIterator() {
            return ScalaRunTime$.MODULE$.typedProductIterator(this);
        }

        public boolean canEqual(final Object x$1) {
            return x$1 instanceof GoogleFont;
        }

        public String productElementName(final int x$1) {
            switch (x$1) {
                case 0:
                    return "name";
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
                if (x$1 instanceof GoogleFont) {
                    GoogleFont googleFont = (GoogleFont) x$1;
                    String strName = name();
                    String strName2 = googleFont.name();
                    if (strName != null ? strName.equals(strName2) : strName2 == null) {
                        if (googleFont.canEqual(this)) {
                        }
                    }
                }
                return false;
            }
            return true;
        }

        public GoogleFont(final String name) {
            this.name = name;
            Product.$init$(this);
        }

        public boolean loadFont$default$2() {
            return false;
        }

        public Option<PDFont> loadFont(final PDDocument doc, final boolean bold) {
            return bold ? variant(doc, "Bold").orElse(() -> {
                return this.variant(doc, "Regular");
            }) : variant(doc, "Regular");
        }

        public Option<PDFont> variant(final PDDocument doc, final String v) {
            return new GoogleFontVariant(name(), v).loadFont(doc);
        }
    }
}
