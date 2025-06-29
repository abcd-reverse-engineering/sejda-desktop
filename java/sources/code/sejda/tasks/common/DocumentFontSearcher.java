package code.sejda.tasks.common;

import code.util.Loggable;
import code.util.StringHelpers$;
import code.util.pdf.ObjIdUtils$;
import java.io.Serializable;
import org.sejda.impl.sambox.util.FontUtils;
import org.sejda.sambox.contentstream.PDContentStream;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectKey;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.PDResources;
import org.sejda.sambox.pdmodel.font.PDFont;
import org.sejda.sambox.pdmodel.font.PDFontDescriptor;
import org.slf4j.Logger;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Option$;
import scala.Predef$;
import scala.Product;
import scala.Some;
import scala.collection.Iterable;
import scala.collection.IterableOnceOps;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.JavaConverters$;
import scala.collection.StringOps$;
import scala.collection.mutable.Set;
import scala.collection.mutable.Set$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.FloatRef;
import scala.runtime.IntRef;
import scala.runtime.NonLocalReturnControl;
import scala.runtime.ObjectRef;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.Try$;
import scala.util.control.NonFatal$;

/* compiled from: DocumentFontSearcher.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005eg\u0001\u0002\u0010 \u0001\"B\u0001\u0002\u0012\u0001\u0003\u0016\u0004%\t!\u0012\u0005\t#\u0002\u0011\t\u0012)A\u0005\r\")!\u000b\u0001C\u0001'\")q\u000b\u0001C\u00051\")q\u000b\u0001C\u0001s\"A!\u0010\u0001EC\u0002\u0013\u00051\u0010C\u0003}\u0001\u0011%Q\u0010C\u0004\u0002\u0006\u0001!I!a\u0002\t\u000f\u0005\u0005\u0002\u0001\"\u0001\u0002$!9\u0011q\u0005\u0001\u0005\u0002\u0005%\u0002\"CA\u001a\u0001E\u0005I\u0011AA\u001b\u0011%\tY\u0005AA\u0001\n\u0003\ti\u0005C\u0005\u0002R\u0001\t\n\u0011\"\u0001\u0002T!I\u0011q\u000b\u0001\u0002\u0002\u0013\u0005\u0013\u0011\f\u0005\n\u0003S\u0002\u0011\u0011!C\u0001\u0003WB\u0011\"!\u001c\u0001\u0003\u0003%\t!a\u001c\t\u0013\u0005m\u0004!!A\u0005B\u0005u\u0004\"CAD\u0001\u0005\u0005I\u0011AAE\u0011%\ti\tAA\u0001\n\u0003\ny\tC\u0005\u0002\u0014\u0002\t\t\u0011\"\u0011\u0002\u0016\"I\u0011q\u0013\u0001\u0002\u0002\u0013\u0005\u0013\u0011\u0014\u0005\n\u00037\u0003\u0011\u0011!C!\u0003;;\u0011\"!) \u0003\u0003E\t!a)\u0007\u0011yy\u0012\u0011!E\u0001\u0003KCaA\u0015\r\u0005\u0002\u0005u\u0006\"CAL1\u0005\u0005IQIAM\u0011%\ty\fGA\u0001\n\u0003\u000b\t\rC\u0005\u0002Fb\t\t\u0011\"!\u0002H\"I\u0011q\u001a\r\u0002\u0002\u0013%\u0011\u0011\u001b\u0002\u0015\t>\u001cW/\\3oi\u001a{g\u000e^*fCJ\u001c\u0007.\u001a:\u000b\u0005\u0001\n\u0013AB2p[6|gN\u0003\u0002#G\u0005)A/Y:lg*\u0011A%J\u0001\u0006g\u0016TG-\u0019\u0006\u0002M\u0005!1m\u001c3f\u0007\u0001\u0019R\u0001A\u00150ka\u0002\"AK\u0017\u000e\u0003-R\u0011\u0001L\u0001\u0006g\u000e\fG.Y\u0005\u0003]-\u0012a!\u00118z%\u00164\u0007C\u0001\u00194\u001b\u0005\t$B\u0001\u001a&\u0003\u0011)H/\u001b7\n\u0005Q\n$\u0001\u0003'pO\u001e\f'\r\\3\u0011\u0005)2\u0014BA\u001c,\u0005\u001d\u0001&o\u001c3vGR\u0004\"!O!\u000f\u0005izdBA\u001e?\u001b\u0005a$BA\u001f(\u0003\u0019a$o\\8u}%\tA&\u0003\u0002AW\u00059\u0001/Y2lC\u001e,\u0017B\u0001\"D\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\t\u00015&A\u0002e_\u000e,\u0012A\u0012\t\u0003\u000f>k\u0011\u0001\u0013\u0006\u0003\u0013*\u000bq\u0001\u001d3n_\u0012,GN\u0003\u0002L\u0019\u000611/Y7c_bT!\u0001J'\u000b\u00039\u000b1a\u001c:h\u0013\t\u0001\u0006J\u0001\u0006Q\t\u0012{7-^7f]R\fA\u0001Z8dA\u00051A(\u001b8jiz\"\"\u0001\u0016,\u0011\u0005U\u0003Q\"A\u0010\t\u000b\u0011\u001b\u0001\u0019\u0001$\u0002\u00131|\u0017\r\u001a$p]R\u001cHcA-cOB\u0019\u0011H\u0017/\n\u0005m\u001b%\u0001C%uKJ\f'\r\\3\u0011\u0005u\u0003W\"\u00010\u000b\u0005}C\u0015\u0001\u00024p]RL!!\u00190\u0003\rA#ei\u001c8u\u0011\u0015\u0019G\u00011\u0001e\u0003%\u0011Xm]8ve\u000e,7\u000f\u0005\u0002HK&\u0011a\r\u0013\u0002\f!\u0012\u0013Vm]8ve\u000e,7\u000fC\u0003i\t\u0001\u0007\u0011.\u0001\bbYJ,\u0017\rZ=WSNLG/\u001a3\u0011\u0007)|\u0017/D\u0001l\u0015\taW.A\u0004nkR\f'\r\\3\u000b\u00059\\\u0013AC2pY2,7\r^5p]&\u0011\u0001o\u001b\u0002\u0004'\u0016$\bC\u0001:w\u001d\t\u0019H\u000f\u0005\u0002<W%\u0011QoK\u0001\u0007!J,G-\u001a4\n\u0005]D(AB*ue&twM\u0003\u0002vWQ\t\u0011,A\u0003g_:$8/F\u0001Z\u0003eI7/T5tg&twm\u0014:jO&t\u0017\r\\#nE\u0016$G-\u001a3\u0015\u0007y\f\u0019\u0001\u0005\u0002+\u007f&\u0019\u0011\u0011A\u0016\u0003\u000f\t{w\u000e\\3b]\")ql\u0002a\u00019\u0006Aa-\u001b8e\r>tG\u000f\u0006\u0006\u0002\n\u0005=\u00111CA\u000b\u0003/\u0001BAKA\u00069&\u0019\u0011QB\u0016\u0003\r=\u0003H/[8o\u0011\u0019\t\t\u0002\u0003a\u0001c\u00061am\u001c8u\u0013\u0012DQa\u0019\u0005A\u0002\u0011DQ\u0001\u001b\u0005A\u0002%Dq!!\u0007\t\u0001\u0004\tY\"\u0001\u0006ti\u0006\u001c7\u000eR3qi\"\u00042AKA\u000f\u0013\r\tyb\u000b\u0002\u0004\u0013:$\u0018a\u00044j]\u00124uN\u001c;Cs>\u0013'.\u00133\u0015\t\u0005%\u0011Q\u0005\u0005\u0007\u0003#I\u0001\u0019A9\u0002!\u0019Lg\u000e\u001a\"fgRl\u0015\r^2iS:<GCBA\u0005\u0003W\ty\u0003\u0003\u0004\u0002.)\u0001\r!]\u0001\u000bM>tGOR1nS2L\b\u0002CA\u0019\u0015A\u0005\t\u0019A9\u0002\tQ,\u0007\u0010^\u0001\u001bM&tGMQ3ti6\u000bGo\u00195j]\u001e$C-\u001a4bk2$HEM\u000b\u0003\u0003oQ3!]A\u001dW\t\tY\u0004\u0005\u0003\u0002>\u0005\u001dSBAA \u0015\u0011\t\t%a\u0011\u0002\u0013Ut7\r[3dW\u0016$'bAA#W\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005%\u0013q\b\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017\u0001B2paf$2\u0001VA(\u0011\u001d!E\u0002%AA\u0002\u0019\u000babY8qs\u0012\"WMZ1vYR$\u0013'\u0006\u0002\u0002V)\u001aa)!\u000f\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\tY\u0006\u0005\u0003\u0002^\u0005\u001dTBAA0\u0015\u0011\t\t'a\u0019\u0002\t1\fgn\u001a\u0006\u0003\u0003K\nAA[1wC&\u0019q/a\u0018\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0005\u0005m\u0011A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0005\u0003c\n9\bE\u0002+\u0003gJ1!!\u001e,\u0005\r\te.\u001f\u0005\n\u0003s\u0002\u0012\u0011!a\u0001\u00037\t1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XCAA@!\u0019\t\t)a!\u0002r5\tQ.C\u0002\u0002\u00066\u0014\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR\u0019a0a#\t\u0013\u0005e$#!AA\u0002\u0005E\u0014A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$B!a\u0017\u0002\u0012\"I\u0011\u0011P\n\u0002\u0002\u0003\u0007\u00111D\u0001\tQ\u0006\u001c\bnQ8eKR\u0011\u00111D\u0001\ti>\u001cFO]5oOR\u0011\u00111L\u0001\u0007KF,\u0018\r\\:\u0015\u0007y\fy\nC\u0005\u0002zY\t\t\u00111\u0001\u0002r\u0005!Bi\\2v[\u0016tGOR8oiN+\u0017M]2iKJ\u0004\"!\u0016\r\u0014\u000ba\t9+a-\u0011\r\u0005%\u0016q\u0016$U\u001b\t\tYKC\u0002\u0002..\nqA];oi&lW-\u0003\u0003\u00022\u0006-&!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8ocA!\u0011QWA^\u001b\t\t9L\u0003\u0003\u0002:\u0006\r\u0014AA5p\u0013\r\u0011\u0015q\u0017\u000b\u0003\u0003G\u000bQ!\u00199qYf$2\u0001VAb\u0011\u0015!5\u00041\u0001G\u0003\u001d)h.\u00199qYf$B!!3\u0002LB!!&a\u0003G\u0011!\ti\rHA\u0001\u0002\u0004!\u0016a\u0001=%a\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u00111\u001b\t\u0005\u0003;\n).\u0003\u0003\u0002X\u0006}#AB(cU\u0016\u001cG\u000f")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/DocumentFontSearcher.class */
public class DocumentFontSearcher implements Loggable, Product, Serializable {
    private Iterable<PDFont> fonts;
    private final PDDocument doc;
    private transient Logger logger;
    private volatile boolean bitmap$0;
    private volatile transient boolean bitmap$trans$0;

    public static Option<PDDocument> unapply(final DocumentFontSearcher x$0) {
        return DocumentFontSearcher$.MODULE$.unapply(x$0);
    }

    public static DocumentFontSearcher apply(final PDDocument doc) {
        return DocumentFontSearcher$.MODULE$.apply(doc);
    }

    public static <A> Function1<PDDocument, A> andThen(final Function1<DocumentFontSearcher, A> g) {
        return DocumentFontSearcher$.MODULE$.andThen(g);
    }

    public static <A> Function1<A, DocumentFontSearcher> compose(final Function1<A, PDDocument> g) {
        return DocumentFontSearcher$.MODULE$.compose(g);
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v8, types: [code.sejda.tasks.common.DocumentFontSearcher] */
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

    public PDDocument doc() {
        return this.doc;
    }

    public DocumentFontSearcher copy(final PDDocument doc) {
        return new DocumentFontSearcher(doc);
    }

    public PDDocument copy$default$1() {
        return doc();
    }

    public String productPrefix() {
        return "DocumentFontSearcher";
    }

    public int productArity() {
        return 1;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return doc();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof DocumentFontSearcher;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "doc";
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
            if (x$1 instanceof DocumentFontSearcher) {
                DocumentFontSearcher documentFontSearcher = (DocumentFontSearcher) x$1;
                PDDocument pDDocumentDoc = doc();
                PDDocument pDDocumentDoc2 = documentFontSearcher.doc();
                if (pDDocumentDoc != null ? pDDocumentDoc.equals(pDDocumentDoc2) : pDDocumentDoc2 == null) {
                    if (documentFontSearcher.canEqual(this)) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    public DocumentFontSearcher(final PDDocument doc) {
        this.doc = doc;
        Loggable.$init$(this);
        Product.$init$(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Iterable<PDFont> loadFonts(final PDResources resources, final Set<String> alreadyVisited) {
        Iterable matching = (Iterable) ((IterableOps) JavaConverters$.MODULE$.iterableAsScalaIterableConverter(resources.getFontNames()).asScala()).flatMap(fontName -> {
            return Try$.MODULE$.apply(() -> {
                return resources.getFont(fontName);
            }).toOption();
        });
        Iterable matchingSubResources = (Iterable) ((IterableOps) JavaConverters$.MODULE$.iterableAsScalaIterableConverter(resources.getXObjectNames()).asScala()).flatMap(objectName -> {
            scala.collection.immutable.Set setEmpty;
            try {
                if (resources.isImageXObject(objectName)) {
                    setEmpty = Predef$.MODULE$.Set().empty();
                } else {
                    COSObjectable xObject = resources.getXObject(objectName);
                    if (xObject instanceof PDContentStream) {
                        PDResources res = ((PDContentStream) xObject).getResources();
                        Some someAsOpt = StringHelpers$.MODULE$.asOpt(ObjIdUtils$.MODULE$.objIdOfOrEmpty(res));
                        if (someAsOpt instanceof Some) {
                            String id = (String) someAsOpt.value();
                            if (alreadyVisited.contains(id)) {
                                setEmpty = Predef$.MODULE$.Set().empty();
                            } else {
                                alreadyVisited.add(id);
                                setEmpty = this.loadFonts(res, alreadyVisited);
                            }
                        } else {
                            if (!None$.MODULE$.equals(someAsOpt)) {
                                throw new MatchError(someAsOpt);
                            }
                            setEmpty = Predef$.MODULE$.Set().empty();
                        }
                    } else {
                        setEmpty = Predef$.MODULE$.Set().empty();
                    }
                }
                return setEmpty;
            } catch (Throwable th) {
                if (!NonFatal$.MODULE$.apply(th)) {
                    throw th;
                }
                this.logger().warn("Failure while searching font in XObject", th);
                return Predef$.MODULE$.Set().empty();
            }
        });
        return (Iterable) matching.$plus$plus(matchingSubResources);
    }

    public Iterable<PDFont> loadFonts() {
        return (Iterable) ((IterableOps) JavaConverters$.MODULE$.iterableAsScalaIterableConverter(doc().getPages()).asScala()).flatMap(page -> {
            return this.loadFonts(page.getResources(), (Set) Set$.MODULE$.empty());
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v8, types: [code.sejda.tasks.common.DocumentFontSearcher] */
    private Iterable<PDFont> fonts$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (!this.bitmap$0) {
                this.fonts = loadFonts();
                r0 = this;
                r0.bitmap$0 = true;
            }
        }
        return this.fonts;
    }

    public Iterable<PDFont> fonts() {
        return !this.bitmap$0 ? fonts$lzycompute() : this.fonts;
    }

    private boolean isMissingOriginalEmbedded(final PDFont font) {
        if (font != null) {
            return font.isOriginalEmbeddedMissing();
        }
        return false;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.runtime.NonLocalReturnControl */
    private Option<PDFont> findFont(final String fontId, final PDResources resources, final Set<String> alreadyVisited, final int stackDepth) throws NonLocalReturnControl {
        Object obj = new Object();
        try {
            if (stackDepth > 30) {
                return None$.MODULE$;
            }
            ((IterableOnceOps) JavaConverters$.MODULE$.iterableAsScalaIterableConverter(resources.getFontNames()).asScala()).foreach(fontName -> {
                $anonfun$findFont$1(resources, fontId, obj, fontName);
                return BoxedUnit.UNIT;
            });
            ((IterableOnceOps) JavaConverters$.MODULE$.iterableAsScalaIterableConverter(resources.getXObjectNames()).asScala()).foreach(objectName -> {
                $anonfun$findFont$2(this, resources, alreadyVisited, fontId, stackDepth, obj, objectName);
                return BoxedUnit.UNIT;
            });
            return None$.MODULE$;
        } catch (NonLocalReturnControl ex) {
            if (ex.key() == obj) {
                return (Option) ex.value();
            }
            throw ex;
        }
    }

    public static final /* synthetic */ void $anonfun$findFont$1(final PDResources resources$2, final String fontId$1, final Object nonLocalReturnKey1$1, final COSName fontName) {
        COSObjectKey key = resources$2.getIndirectKey(COSName.FONT, fontName);
        if (key != null) {
            String strObjIdOf = ObjIdUtils$.MODULE$.objIdOf(key);
            if (strObjIdOf == null) {
                if (fontId$1 != null) {
                    return;
                }
            } else if (!strObjIdOf.equals(fontId$1)) {
                return;
            }
            try {
                throw new NonLocalReturnControl(nonLocalReturnKey1$1, Option$.MODULE$.apply(resources$2.getFont(fontName)));
            } catch (Throwable th) {
                if (!NonFatal$.MODULE$.apply(th)) {
                    throw th;
                }
                BoxedUnit boxedUnit = BoxedUnit.UNIT;
            }
        }
    }

    public static final /* synthetic */ void $anonfun$findFont$2(final DocumentFontSearcher $this, final PDResources resources$2, final Set alreadyVisited$2, final String fontId$1, final int stackDepth$1, final Object nonLocalReturnKey1$1, final COSName objectName) {
        try {
            if (!resources$2.isImageXObject(objectName)) {
                COSObjectable xObject = resources$2.getXObject(objectName);
                if (xObject instanceof PDContentStream) {
                    PDResources res = ((PDContentStream) xObject).getResources();
                    String id = ObjIdUtils$.MODULE$.objIdOfOrEmpty(res);
                    if (!id.isEmpty() && alreadyVisited$2.contains(id)) {
                        BoxedUnit boxedUnit = BoxedUnit.UNIT;
                    } else {
                        if (StringOps$.MODULE$.nonEmpty$extension(Predef$.MODULE$.augmentString(id))) {
                            BoxesRunTime.boxToBoolean(alreadyVisited$2.add(id));
                        } else {
                            BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
                        }
                        Some someFindFont = $this.findFont(fontId$1, res, alreadyVisited$2, stackDepth$1 + 1);
                        if (someFindFont instanceof Some) {
                            PDFont font = (PDFont) someFindFont.value();
                            throw new NonLocalReturnControl(nonLocalReturnKey1$1, new Some(font));
                        }
                        BoxedUnit boxedUnit3 = BoxedUnit.UNIT;
                        BoxedUnit boxedUnit4 = BoxedUnit.UNIT;
                    }
                } else {
                    BoxedUnit boxedUnit5 = BoxedUnit.UNIT;
                }
            }
        } catch (Throwable th) {
            if (!NonFatal$.MODULE$.apply(th)) {
                throw th;
            }
            $this.logger().warn("Failure while searching font in XObject", th);
            BoxedUnit boxedUnit6 = BoxedUnit.UNIT;
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.runtime.NonLocalReturnControl */
    public Option<PDFont> findFontByObjId(final String fontId) throws NonLocalReturnControl {
        Object obj = new Object();
        try {
            if (fontId.isEmpty()) {
                return None$.MODULE$;
            }
            ((IterableOnceOps) JavaConverters$.MODULE$.iterableAsScalaIterableConverter(doc().getPages()).asScala()).foreach(page -> {
                $anonfun$findFontByObjId$1(this, fontId, obj, page);
                return BoxedUnit.UNIT;
            });
            logger().warn(new StringBuilder(15).append("No font by id: ").append(fontId).toString());
            return None$.MODULE$;
        } catch (NonLocalReturnControl ex) {
            if (ex.key() == obj) {
                return (Option) ex.value();
            }
            throw ex;
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.runtime.NonLocalReturnControl */
    public static final /* synthetic */ void $anonfun$findFontByObjId$1(final DocumentFontSearcher $this, final String fontId$2, final Object nonLocalReturnKey2$1, final PDPage page) throws NonLocalReturnControl {
        Some someFindFont = $this.findFont(fontId$2, page.getResources(), (Set) Set$.MODULE$.empty(), 1);
        if (someFindFont instanceof Some) {
            PDFont font = (PDFont) someFindFont.value();
            $this.logger().debug(new StringBuilder(17).append("Found ").append(font.getName()).append(" for objId ").append(fontId$2).toString());
            throw new NonLocalReturnControl(nonLocalReturnKey2$1, new Some(font));
        }
        BoxedUnit boxedUnit = BoxedUnit.UNIT;
    }

    public String findBestMatching$default$2() {
        return "";
    }

    public Option<PDFont> findBestMatching(final String fontFamily, final String text) {
        logger().debug(new StringBuilder(28).append("Finding best matching font: ").append(fontFamily).toString());
        ObjectRef bestMatch = ObjectRef.create(None$.MODULE$);
        IntRef bestMatchCount = IntRef.create(-1);
        FloatRef bestMatchWidth = FloatRef.create(-1.0f);
        scala.collection.immutable.Set matchingByName = ((IterableOnceOps) ((IterableOps) ((IterableOps) fonts().filter(x$1 -> {
            return BoxesRunTime.boxToBoolean($anonfun$findBestMatching$1(x$1));
        })).filter(font -> {
            return BoxesRunTime.boxToBoolean($anonfun$findBestMatching$2(fontFamily, font));
        })).filter(font2 -> {
            return BoxesRunTime.boxToBoolean($anonfun$findBestMatching$4(this, font2));
        })).toSet();
        if (matchingByName.size() == 1) {
            return matchingByName.headOption();
        }
        String textNoWhitespace = FontUtils.removeWhitespace(text);
        matchingByName.foreach(font3 -> {
            $anonfun$findBestMatching$5(text, textNoWhitespace, bestMatchCount, bestMatchWidth, bestMatch, font3);
            return BoxedUnit.UNIT;
        });
        return (Option) bestMatch.elem;
    }

    public static final /* synthetic */ boolean $anonfun$findBestMatching$1(final PDFont x$1) {
        return x$1 != null;
    }

    public static final /* synthetic */ boolean $anonfun$findBestMatching$3(final String fontFamily$1, final PDFontDescriptor x$2) {
        String fontName = x$2.getFontName();
        return fontName != null ? fontName.equals(fontFamily$1) : fontFamily$1 == null;
    }

    public static final /* synthetic */ boolean $anonfun$findBestMatching$2(final String fontFamily$1, final PDFont font) {
        return fontFamily$1.equalsIgnoreCase(font.getName()) || Option$.MODULE$.apply(font.getFontDescriptor()).exists(x$2 -> {
            return BoxesRunTime.boxToBoolean($anonfun$findBestMatching$3(fontFamily$1, x$2));
        });
    }

    public static final /* synthetic */ boolean $anonfun$findBestMatching$4(final DocumentFontSearcher $this, final PDFont font) {
        return !$this.isMissingOriginalEmbedded(font);
    }

    public static final /* synthetic */ void $anonfun$findBestMatching$5(final String text$1, final String textNoWhitespace$1, final IntRef bestMatchCount$1, final FloatRef bestMatchWidth$1, final ObjectRef bestMatch$1, final PDFont font) {
        IntRef supportedCount = IntRef.create(0);
        float supportedWidth = BoxesRunTime.unboxToFloat(Try$.MODULE$.apply(() -> {
            return font.getStringWidthLeniently(text$1);
        }).toOption().getOrElse(() -> {
            return -1.0f;
        }));
        StringOps$.MODULE$.foreach$extension(Predef$.MODULE$.augmentString(textNoWhitespace$1), obj -> {
            $anonfun$findBestMatching$8(font, supportedCount, BoxesRunTime.unboxToChar(obj));
            return BoxedUnit.UNIT;
        });
        if (bestMatchCount$1.elem < supportedCount.elem || (bestMatchCount$1.elem == supportedCount.elem && supportedWidth > bestMatchWidth$1.elem)) {
            bestMatch$1.elem = new Some(font);
            bestMatchCount$1.elem = supportedCount.elem;
            bestMatchWidth$1.elem = supportedWidth;
        }
    }

    public static final /* synthetic */ void $anonfun$findBestMatching$8(final PDFont font$1, final IntRef supportedCount$1, char c) {
        if (FontUtils.canDisplay(Character.toString(c), font$1)) {
            supportedCount$1.elem++;
        }
    }
}
