package com.sejda.pdf2html.sambox;

import com.sejda.pdf2html.util.FileUtils$;
import java.io.File;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.PDPageTree;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Option$;
import scala.Predef$;
import scala.Some;
import scala.collection.IterableOps;
import scala.collection.StringOps$;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.mutable.StringBuilder;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.xml.Elem;
import scala.xml.NamespaceBinding;
import scala.xml.NodeBuffer;
import scala.xml.NodeSeq$;
import scala.xml.Null$;
import scala.xml.PrefixedAttribute;
import scala.xml.Text;
import scala.xml.TopScope$;
import scala.xml.UnprefixedAttribute;

/* compiled from: PdfText2ToC.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005mr!\u0002\u000b\u0016\u0011\u0003qb!\u0002\u0011\u0016\u0011\u0003\t\u0003\"\u0002\u0015\u0002\t\u0003I\u0003\"\u0002\u0016\u0002\t\u0003Y\u0003\"CA\u0012\u0003E\u0005I\u0011AA\u0013\r\u0011\u0001S\u0003\u0001\u0018\t\u0011=*!\u0011!Q\u0001\nAB\u0001BO\u0003\u0003\u0002\u0003\u0006Ia\u000f\u0005\u0006Q\u0015!\t\u0001\u0014\u0005\u0006\u001f\u0016!\t\u0001\u0015\u0005\u0006=\u0016!\ta\u0018\u0005\u0006C\u0016!\tA\u0019\u0005\u0006G\u0016!\t\u0001\u001a\u0005\u0006Q\u0016!\t!\u001b\u0005\u0006U\u0016!\t!\u001b\u0005\bW\u0016\u0011\r\u0011\"\u0001m\u0011\u0019\u0001X\u0001)A\u0005[\"9\u0011/\u0002b\u0001\n\u0003\u0011\bB\u0002?\u0006A\u0003%1\u000fC\u0003~\u000b\u0011%a0A\u0006QI\u001a$V\r\u001f;3)>\u001c%B\u0001\f\u0018\u0003\u0019\u0019\u0018-\u001c2pq*\u0011\u0001$G\u0001\ta\u00124'\u0007\u001b;nY*\u0011!dG\u0001\u0006g\u0016TG-\u0019\u0006\u00029\u0005\u00191m\\7\u0004\u0001A\u0011q$A\u0007\u0002+\tY\u0001\u000b\u001a4UKb$(\u0007V8D'\t\t!\u0005\u0005\u0002$M5\tAEC\u0001&\u0003\u0015\u00198-\u00197b\u0013\t9CE\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003y\tQ!\u00199qYf,2\u0001LA\t)\u0015i\u0013\u0011BA\u0007!\tyRa\u0005\u0002\u0006E\u0005\u0019Am\\2\u0011\u0005EBT\"\u0001\u001a\u000b\u0005M\"\u0014a\u00029e[>$W\r\u001c\u0006\u0003-UR!A\u0007\u001c\u000b\u0003]\n1a\u001c:h\u0013\tI$G\u0001\u0006Q\t\u0012{7-^7f]R\fQ\"\u00198dQ>\u0014h)Y2u_JL\b\u0003B\u0012=}\u0005K!!\u0010\u0013\u0003\u0013\u0019+hn\u0019;j_:\f\u0004CA\u0012@\u0013\t\u0001EEA\u0002J]R\u0004\"AQ%\u000f\u0005\r;\u0005C\u0001#%\u001b\u0005)%B\u0001$\u001e\u0003\u0019a$o\\8u}%\u0011\u0001\nJ\u0001\u0007!J,G-\u001a4\n\u0005)[%AB*ue&twM\u0003\u0002IIQ\u0019Q&\u0014(\t\u000b=B\u0001\u0019\u0001\u0019\t\u000fiB\u0001\u0013!a\u0001w\u0005IqO]5uK\"#X\u000e\u001c\u000b\u0003#R\u0003\"a\t*\n\u0005M##\u0001B+oSRDQ!V\u0005A\u0002Y\u000bAAZ5mKB\u0011q\u000bX\u0007\u00021*\u0011\u0011LW\u0001\u0003S>T\u0011aW\u0001\u0005U\u00064\u0018-\u0003\u0002^1\n!a)\u001b7f\u0003!9(/\u001b;f\u001d\u000eDHCA)a\u0011\u0015)&\u00021\u0001W\u0003\u0015\u0019Gn\\:f)\u0005\t\u0016A\u00025bgR{7-F\u0001f!\t\u0019c-\u0003\u0002hI\t9!i\\8mK\u0006t\u0017A\u0002;p\u0011RlG.F\u0001B\u0003\u0015!xNT2y\u0003%y\u0016\r\u001c7QC\u001e,7/F\u0001n!\t\td.\u0003\u0002pe\tQ\u0001\u000b\u0012)bO\u0016$&/Z3\u0002\u0015}\u000bG\u000e\u001c)bO\u0016\u001c\b%A\u0004pkRd\u0017N\\3\u0016\u0003M\u0004\"\u0001\u001e>\u000e\u0003UT!!\u001d<\u000b\u0005]D\u0018A\u00053pGVlWM\u001c;oCZLw-\u0019;j_:T!!\u001f\u001a\u0002\u0017%tG/\u001a:bGRLg/Z\u0005\u0003wV\u0014\u0011\u0003\u0015#E_\u000e,X.\u001a8u\u001fV$H.\u001b8f\u0003!yW\u000f\u001e7j]\u0016\u0004\u0013aC0qC\u001e,g*^7cKJ$\"AP@\t\u000f\u0005\u00051\u00031\u0001\u0002\u0004\u0005!\u0001/Y4f!\r\t\u0014QA\u0005\u0004\u0003\u000f\u0011$A\u0002)E!\u0006<W\r\u0003\u0004\u0002\f\r\u0001\rAV\u0001\u0003S:Da!a\u0004\u0004\u0001\u0004\t\u0015\u0001\u00032p_.4\u0015\u000e\\3\u0005\u000f\u0005M1A1\u0001\u0002\u0016\t\t\u0011)\u0005\u0003\u0002\u0018\u0005u\u0001cA\u0012\u0002\u001a%\u0019\u00111\u0004\u0013\u0003\u000f9{G\u000f[5oOB\u00191%a\b\n\u0007\u0005\u0005BEA\u0002B]f\f1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\u0012TCAA\u0014U\rY\u0014\u0011F\u0016\u0003\u0003W\u0001B!!\f\u000285\u0011\u0011q\u0006\u0006\u0005\u0003c\t\u0019$A\u0005v]\u000eDWmY6fI*\u0019\u0011Q\u0007\u0013\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0002:\u0005=\"!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0002")
/* loaded from: com.sejda.pdf2html.pdf2html-0.0.72.jar:com/sejda/pdf2html/sambox/PdfText2ToC.class */
public class PdfText2ToC {
    private final PDDocument doc;
    private final Function1<Object, String> anchorFactory;
    private final PDPageTree _allPages;
    private final PDDocumentOutline outline;

    public static <A> PdfText2ToC apply(final File in, final String bookFile) {
        return PdfText2ToC$.MODULE$.apply(in, bookFile);
    }

    public PdfText2ToC(final PDDocument doc, final Function1<Object, String> anchorFactory) {
        this.doc = doc;
        this.anchorFactory = anchorFactory;
        this._allPages = doc.getDocumentCatalog().getPages();
        this.outline = doc.getDocumentCatalog().getDocumentOutline();
    }

    public void writeHtml(final File file) {
        if (hasToc()) {
            FileUtils$.MODULE$.write(file, toHtml());
        }
    }

    public void writeNcx(final File file) {
        if (hasToc()) {
            FileUtils$.MODULE$.write(file, toNcx());
        }
    }

    public void close() {
        this.doc.close();
    }

    public boolean hasToc() {
        return Option$.MODULE$.apply(outline()).isDefined();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    private final String _toHtmlList$1(final Option outlineItem) throws MatchError {
        Some some_toHtml$1 = _toHtml$1(outlineItem);
        if (some_toHtml$1 instanceof Some) {
            String contents = (String) some_toHtml$1.value();
            return new StringBuilder(9).append("<ul>").append(contents).append("</ul>").toString();
        }
        if (None$.MODULE$.equals(some_toHtml$1)) {
            return "";
        }
        throw new MatchError(some_toHtml$1);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    private final Option _toHtml$1(final Option outlineItem) throws MatchError {
        if (None$.MODULE$.equals(outlineItem)) {
            return None$.MODULE$;
        }
        if (outlineItem instanceof Some) {
            PDOutlineItem item = (PDOutlineItem) ((Some) outlineItem).value();
            try {
                int pageNumber = _pageNumber(item.findDestinationPage(this.doc));
                String title = item.getTitle();
                String anchor = (String) this.anchorFactory.apply(BoxesRunTime.boxToInteger(pageNumber));
                String listItem = new StringBuilder(25).append("<li><a href=\"").append(anchor).append("\">").append(title).append("</a></li>\n").toString();
                String children = _toHtmlList$1(Option$.MODULE$.apply(item.getFirstChild()));
                String restOfSiblings = (String) _toHtml$1(Option$.MODULE$.apply(item.getNextSibling())).getOrElse(() -> {
                    return "";
                });
                return new Some(new StringBuilder(listItem).append(children).append(restOfSiblings).toString());
            } catch (Exception e) {
                return None$.MODULE$;
            }
        }
        throw new MatchError(outlineItem);
    }

    public String toHtml() throws MatchError {
        String toc = _toHtmlList$1(Option$.MODULE$.apply(outline().getFirstChild()));
        return new StringBuilder(61).append("<html><body><a name=\"toc\">Table of contents</a>").append(toc).append("</body></html>").toString();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    private final Seq _navPoints$1(final Option outlineItem, final int counter) throws MatchError {
        if (None$.MODULE$.equals(outlineItem)) {
            return Nil$.MODULE$;
        }
        if (!(outlineItem instanceof Some)) {
            throw new MatchError(outlineItem);
        }
        PDOutlineItem item = (PDOutlineItem) ((Some) outlineItem).value();
        int pageNumber = _pageNumber(item.findDestinationPage(this.doc));
        String title = item.getTitle();
        String id = new StringBuilder(9).append("navPoint-").append(counter).toString();
        String playOrder = Integer.toString(counter);
        String anchor = (String) this.anchorFactory.apply(BoxesRunTime.boxToInteger(pageNumber));
        UnprefixedAttribute unprefixedAttribute = new UnprefixedAttribute("id", id, new UnprefixedAttribute("playOrder", playOrder, Null$.MODULE$));
        TopScope$ topScope$ = TopScope$.MODULE$;
        NodeSeq$ nodeSeq$ = NodeSeq$.MODULE$;
        NodeBuffer $buf = new NodeBuffer();
        $buf.$amp$plus(new Text("\n            "));
        Null$ null$ = Null$.MODULE$;
        TopScope$ topScope$2 = TopScope$.MODULE$;
        NodeSeq$ nodeSeq$2 = NodeSeq$.MODULE$;
        NodeBuffer $buf2 = new NodeBuffer();
        $buf2.$amp$plus(new Text("\n              "));
        Null$ null$2 = Null$.MODULE$;
        TopScope$ topScope$3 = TopScope$.MODULE$;
        NodeSeq$ nodeSeq$3 = NodeSeq$.MODULE$;
        NodeBuffer $buf3 = new NodeBuffer();
        $buf3.$amp$plus(new Text("\n                "));
        $buf3.$amp$plus(title);
        $buf3.$amp$plus(new Text("\n              "));
        $buf2.$amp$plus(new Elem((String) null, "text", null$2, topScope$3, false, nodeSeq$3.seqToNodeSeq($buf3)));
        $buf2.$amp$plus(new Text("\n            "));
        $buf.$amp$plus(new Elem((String) null, "navLabel", null$, topScope$2, false, nodeSeq$2.seqToNodeSeq($buf2)));
        $buf.$amp$plus(new Text("\n            "));
        $buf.$amp$plus(new Elem((String) null, "content", new UnprefixedAttribute("src", anchor, Null$.MODULE$), TopScope$.MODULE$, true, Nil$.MODULE$));
        $buf.$amp$plus(new Text("\n          "));
        Elem navPoint = new Elem((String) null, "navPoint", unprefixedAttribute, topScope$, false, nodeSeq$.seqToNodeSeq($buf));
        Seq children = _navPoints$1(Option$.MODULE$.apply(item.getFirstChild()), _navPoints$default$2$1());
        Seq restOfSiblings = _navPoints$1(Option$.MODULE$.apply(item.getNextSibling()), _navPoints$default$2$1());
        return (Seq) ((IterableOps) navPoint.$plus$plus(children)).$plus$plus(restOfSiblings);
    }

    private static final int _navPoints$default$2$1() {
        return 1;
    }

    public String toNcx() {
        String preamble = StringOps$.MODULE$.stripMargin$extension(Predef$.MODULE$.augmentString("\n        |<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n        |<!DOCTYPE ncx PUBLIC \"-//NISO//DTD ncx 2005-1//EN\" \"http://www.daisy.org/z3986/2005/ncx-2005-1.dtd\">\n        | "));
        NamespaceBinding $tmpscope = new NamespaceBinding((String) null, "http://www.daisy.org/z3986/2005/ncx/", TopScope$.MODULE$);
        UnprefixedAttribute unprefixedAttribute = new UnprefixedAttribute("version", new Text("2005-1"), new PrefixedAttribute("xml", "lang", new Text("en-US"), Null$.MODULE$));
        NodeSeq$ nodeSeq$ = NodeSeq$.MODULE$;
        NodeBuffer $buf = new NodeBuffer();
        $buf.$amp$plus(new Text("\n      "));
        Null$ null$ = Null$.MODULE$;
        NodeSeq$ nodeSeq$2 = NodeSeq$.MODULE$;
        NodeBuffer $buf2 = new NodeBuffer();
        $buf2.$amp$plus(new Text("\n        "));
        $buf2.$amp$plus(new Elem((String) null, "meta", new UnprefixedAttribute("name", new Text("dtb:uid"), new UnprefixedAttribute("content", new Text("toc"), Null$.MODULE$)), $tmpscope, true, Nil$.MODULE$));
        $buf2.$amp$plus(new Text("\n        "));
        $buf2.$amp$plus(new Elem((String) null, "meta", new UnprefixedAttribute("name", new Text("dtb:totalPageCount"), new UnprefixedAttribute("content", Integer.toString(_allPages().getCount()), Null$.MODULE$)), $tmpscope, true, Nil$.MODULE$));
        $buf2.$amp$plus(new Text("\n      "));
        $buf.$amp$plus(new Elem((String) null, "head", null$, $tmpscope, false, nodeSeq$2.seqToNodeSeq($buf2)));
        $buf.$amp$plus(new Text("\n      "));
        Null$ null$2 = Null$.MODULE$;
        NodeSeq$ nodeSeq$3 = NodeSeq$.MODULE$;
        NodeBuffer $buf3 = new NodeBuffer();
        $buf3.$amp$plus(new Text("\n        "));
        $buf3.$amp$plus(this.doc.getDocumentInformation().getTitle());
        $buf3.$amp$plus(new Text("\n      "));
        $buf.$amp$plus(new Elem((String) null, "docTitle", null$2, $tmpscope, false, nodeSeq$3.seqToNodeSeq($buf3)));
        $buf.$amp$plus(new Text("\n      "));
        Null$ null$3 = Null$.MODULE$;
        NodeSeq$ nodeSeq$4 = NodeSeq$.MODULE$;
        NodeBuffer $buf4 = new NodeBuffer();
        $buf4.$amp$plus(new Text("\n        "));
        $buf4.$amp$plus(this.doc.getDocumentInformation().getAuthor());
        $buf4.$amp$plus(new Text("\n      "));
        $buf.$amp$plus(new Elem((String) null, "docAuthor", null$3, $tmpscope, false, nodeSeq$4.seqToNodeSeq($buf4)));
        $buf.$amp$plus(new Text("\n      "));
        Null$ null$4 = Null$.MODULE$;
        NodeSeq$ nodeSeq$5 = NodeSeq$.MODULE$;
        NodeBuffer $buf5 = new NodeBuffer();
        $buf5.$amp$plus(new Text("\n        "));
        $buf5.$amp$plus(_navPoints$1(Option$.MODULE$.apply(outline().getFirstChild()), _navPoints$default$2$1()));
        $buf5.$amp$plus(new Text("\n      "));
        $buf.$amp$plus(new Elem((String) null, "navMap", null$4, $tmpscope, false, nodeSeq$5.seqToNodeSeq($buf5)));
        $buf.$amp$plus(new Text("\n    "));
        Elem content = new Elem((String) null, "ncx", unprefixedAttribute, $tmpscope, false, nodeSeq$.seqToNodeSeq($buf));
        return StringOps$.MODULE$.$plus$plus$extension(Predef$.MODULE$.augmentString(preamble), content.toString());
    }

    public PDPageTree _allPages() {
        return this._allPages;
    }

    public PDDocumentOutline outline() {
        return this.outline;
    }

    private int _pageNumber(final PDPage page) {
        return _allPages().indexOf(page) + 1;
    }
}
