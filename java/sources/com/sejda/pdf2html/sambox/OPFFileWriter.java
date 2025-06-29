package com.sejda.pdf2html.sambox;

import com.sejda.pdf2html.util.FileUtils$;
import java.io.File;
import java.io.IOException;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.util.SpecVersionUtils;
import scala.None$;
import scala.Option;
import scala.Option$;
import scala.Predef$;
import scala.Some;
import scala.collection.StringOps$;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.xml.Elem;
import scala.xml.NamespaceBinding;
import scala.xml.NodeBuffer;
import scala.xml.NodeSeq$;
import scala.xml.Null$;
import scala.xml.Text;
import scala.xml.TopScope$;
import scala.xml.UnprefixedAttribute;

/* compiled from: OpfFileWriter.scala */
@ScalaSignature(bytes = "\u0006\u0005\u00055q!B\b\u0011\u0011\u0003Ib!B\u000e\u0011\u0011\u0003a\u0002\"B\u0012\u0002\t\u0003!\u0003\"B\u0013\u0002\t\u00031\u0003b\u0002>\u0002#\u0003%\ta\u001f\u0004\u00057A\u0001\u0011\u0006\u0003\u0005+\u000b\t\u0005\t\u0015!\u0003,\u0011!)TA!A!\u0002\u00131\u0004\"B\u0012\u0006\t\u0003!\u0005\"B$\u0006\t\u0003A\u0005\"\u0002'\u0006\t\u0003i\u0005\"\u0002)\u0006\t\u0003\t\u0006\"B+\u0006\t\u00031\u0006\u0002C0\u0006\u0011\u000b\u0007I\u0011\u00011\t\u000b\u0005,A\u0011\u00012\u0002\u001b=\u0003fIR5mK^\u0013\u0018\u000e^3s\u0015\t\t\"#\u0001\u0004tC6\u0014w\u000e\u001f\u0006\u0003'Q\t\u0001\u0002\u001d3ge!$X\u000e\u001c\u0006\u0003+Y\tQa]3kI\u0006T\u0011aF\u0001\u0004G>l7\u0001\u0001\t\u00035\u0005i\u0011\u0001\u0005\u0002\u000e\u001fB3e)\u001b7f/JLG/\u001a:\u0014\u0005\u0005i\u0002C\u0001\u0010\"\u001b\u0005y\"\"\u0001\u0011\u0002\u000bM\u001c\u0017\r\\1\n\u0005\tz\"AB!osJ+g-\u0001\u0004=S:LGO\u0010\u000b\u00023\u0005)\u0011\r\u001d9msV\u0011q%\u001d\u000b\u0003Q=\u0004\"AG\u0003\u0014\u0005\u0015i\u0012a\u00013pGB\u0011AfM\u0007\u0002[)\u0011afL\u0001\ba\u0012lw\u000eZ3m\u0015\t\t\u0002G\u0003\u0002\u0016c)\t!'A\u0002pe\u001eL!\u0001N\u0017\u0003\u0015A#Ei\\2v[\u0016tG/A\u0007gC2d'-Y2l)&$H.\u001a\t\u0004=]J\u0014B\u0001\u001d \u0005\u0019y\u0005\u000f^5p]B\u0011!(\u0011\b\u0003w}\u0002\"\u0001P\u0010\u000e\u0003uR!A\u0010\r\u0002\rq\u0012xn\u001c;?\u0013\t\u0001u$\u0001\u0004Qe\u0016$WMZ\u0005\u0003\u0005\u000e\u0013aa\u0015;sS:<'B\u0001! )\rASI\u0012\u0005\u0006U!\u0001\ra\u000b\u0005\bk!\u0001\n\u00111\u00017\u0003\u0015\u0019Gn\\:f)\u0005I\u0005C\u0001\u0010K\u0013\tYuD\u0001\u0003V]&$\u0018!\u0002;p\u001fB$HC\u0001\u001cO\u0011\u0015y%\u00021\u0001:\u0003\u0005\u0019\u0018aB5g\u00052\fgn\u001b\u000b\u0004sI\u001b\u0006\"B(\f\u0001\u0004I\u0004\"\u0002+\f\u0001\u0004I\u0014a\u00023fM\u0006,H\u000e^\u0001\r]VdGnU1gKR\u0013\u0018.\u001c\u000b\u0003/z\u0003\"\u0001W/\u000e\u0003eS!AW.\u0002\t1\fgn\u001a\u0006\u00029\u0006!!.\u0019<b\u0013\t\u0011\u0015\fC\u0003P\u0019\u0001\u0007\u0011(\u0001\u0005mC:<W/Y4f+\u00059\u0016!B<sSR,G\u0003B%dW6DQ\u0001\u001a\bA\u0002\u0015\f1a\\;u!\t1\u0017.D\u0001h\u0015\tA7,\u0001\u0002j_&\u0011!n\u001a\u0002\u0005\r&dW\rC\u0003m\u001d\u0001\u0007Q-\u0001\u0005c_>\\g)\u001b7f\u0011\u0015qg\u00021\u0001f\u0003\u001d!xn\u0019$jY\u0016DQ\u0001]\u0002A\u0002\u0015\f!!\u001b8\u0005\u000bI\u001c!\u0019A:\u0003\u0003\u0005\u000b\"\u0001^<\u0011\u0005y)\u0018B\u0001< \u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"A\b=\n\u0005e|\"aA!os\u0006YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uII*\u0012\u0001 \u0016\u0003mu\\\u0013A \t\u0004\u007f\u0006%QBAA\u0001\u0015\u0011\t\u0019!!\u0002\u0002\u0013Ut7\r[3dW\u0016$'bAA\u0004?\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005-\u0011\u0011\u0001\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0007")
/* loaded from: com.sejda.pdf2html.pdf2html-0.0.72.jar:com/sejda/pdf2html/sambox/OPFFileWriter.class */
public class OPFFileWriter {
    private String language;
    private final PDDocument doc;
    private final Option<String> fallbackTitle;
    private volatile boolean bitmap$0;

    public static <A> OPFFileWriter apply(final File in) {
        return OPFFileWriter$.MODULE$.apply(in);
    }

    public OPFFileWriter(final PDDocument doc, final Option<String> fallbackTitle) {
        this.doc = doc;
        this.fallbackTitle = fallbackTitle;
    }

    public void close() throws IOException {
        this.doc.close();
    }

    public Option<String> toOpt(final String s) {
        return nullSafeTrim(s).isEmpty() ? None$.MODULE$ : new Some(s);
    }

    public String ifBlank(final String s, String str) {
        return nullSafeTrim(s).isEmpty() ? str : s;
    }

    public String nullSafeTrim(final String s) {
        return (String) Option$.MODULE$.apply(s).map(x$1 -> {
            return x$1.trim();
        }).getOrElse(() -> {
            return "";
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.sejda.pdf2html.sambox.OPFFileWriter] */
    private String language$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (!this.bitmap$0) {
                this.language = "en-us";
                r0 = this;
                r0.bitmap$0 = true;
            }
        }
        return this.language;
    }

    public String language() {
        return !this.bitmap$0 ? language$lzycompute() : this.language;
    }

    public void write(final File out, final File bookFile, final File tocFile) {
        Elem elemEmpty;
        Elem elemEmpty2;
        String title = (String) toOpt(this.doc.getDocumentInformation().getTitle()).orElse(() -> {
            return this.fallbackTitle;
        }).getOrElse(() -> {
            return "Unknown title";
        });
        String author = ifBlank(this.doc.getDocumentInformation().getAuthor(), "Unknown author");
        boolean hasToc = Option$.MODULE$.apply(tocFile).exists(x$2 -> {
            return BoxesRunTime.boxToBoolean(x$2.exists());
        });
        NamespaceBinding $tmpscope = new NamespaceBinding((String) null, "http://www.idpf.org/2007/opf", TopScope$.MODULE$);
        UnprefixedAttribute unprefixedAttribute = new UnprefixedAttribute("version", new Text(SpecVersionUtils.V2_0), new UnprefixedAttribute("unique-identifier", new Text("BookId"), Null$.MODULE$));
        NodeSeq$ nodeSeq$ = NodeSeq$.MODULE$;
        NodeBuffer $buf = new NodeBuffer();
        $buf.$amp$plus(new Text("\n        "));
        NamespaceBinding $tmpscope2 = new NamespaceBinding("opf", "http://www.idpf.org/2007/opf", new NamespaceBinding("dc", "http://purl.org/dc/elements/1.1/", $tmpscope));
        Null$ null$ = Null$.MODULE$;
        NodeSeq$ nodeSeq$2 = NodeSeq$.MODULE$;
        NodeBuffer $buf2 = new NodeBuffer();
        $buf2.$amp$plus(new Text("\n          "));
        Null$ null$2 = Null$.MODULE$;
        NodeSeq$ nodeSeq$3 = NodeSeq$.MODULE$;
        NodeBuffer $buf3 = new NodeBuffer();
        $buf3.$amp$plus(new Text("\n            "));
        $buf3.$amp$plus(title);
        $buf3.$amp$plus(new Text("\n          "));
        $buf2.$amp$plus(new Elem("dc", "title", null$2, $tmpscope2, false, nodeSeq$3.seqToNodeSeq($buf3)));
        $buf2.$amp$plus(new Text("\n          "));
        Null$ null$3 = Null$.MODULE$;
        NodeSeq$ nodeSeq$4 = NodeSeq$.MODULE$;
        NodeBuffer $buf4 = new NodeBuffer();
        $buf4.$amp$plus(new Text("\n            "));
        $buf4.$amp$plus(author);
        $buf4.$amp$plus(new Text("\n          "));
        $buf2.$amp$plus(new Elem("dc", "creator", null$3, $tmpscope2, false, nodeSeq$4.seqToNodeSeq($buf4)));
        $buf2.$amp$plus(new Text("\n          "));
        Null$ null$4 = Null$.MODULE$;
        NodeSeq$ nodeSeq$5 = NodeSeq$.MODULE$;
        NodeBuffer $buf5 = new NodeBuffer();
        $buf5.$amp$plus(language());
        $buf2.$amp$plus(new Elem("dc", "language", null$4, $tmpscope2, false, nodeSeq$5.seqToNodeSeq($buf5)));
        $buf2.$amp$plus(new Text("\n        "));
        $buf.$amp$plus(new Elem((String) null, "metadata", null$, $tmpscope2, false, nodeSeq$2.seqToNodeSeq($buf2)));
        $buf.$amp$plus(new Text("\n        "));
        Null$ null$5 = Null$.MODULE$;
        NodeSeq$ nodeSeq$6 = NodeSeq$.MODULE$;
        NodeBuffer $buf6 = new NodeBuffer();
        $buf6.$amp$plus(new Text("\n          "));
        if (hasToc) {
            elemEmpty = new Elem((String) null, "item", new UnprefixedAttribute("id", new Text("toc"), new UnprefixedAttribute("media-type", new Text("application/xhtml+xml"), new UnprefixedAttribute("href", tocFile.getName(), Null$.MODULE$))), $tmpscope, false, Nil$.MODULE$);
        } else {
            elemEmpty = NodeSeq$.MODULE$.Empty();
        }
        $buf6.$amp$plus(elemEmpty);
        $buf6.$amp$plus(new Text("\n          "));
        $buf6.$amp$plus(new Elem((String) null, "item", new UnprefixedAttribute("id", new Text("book"), new UnprefixedAttribute("media-type", new Text("application/xhtml+xml"), new UnprefixedAttribute("href", bookFile.getName(), Null$.MODULE$))), $tmpscope, false, Nil$.MODULE$));
        $buf6.$amp$plus(new Text("\n        "));
        $buf.$amp$plus(new Elem((String) null, "manifest", null$5, $tmpscope, false, nodeSeq$6.seqToNodeSeq($buf6)));
        $buf.$amp$plus(new Text("\n        "));
        Null$ null$6 = Null$.MODULE$;
        NodeSeq$ nodeSeq$7 = NodeSeq$.MODULE$;
        NodeBuffer $buf7 = new NodeBuffer();
        $buf7.$amp$plus(new Text("\n          "));
        $buf7.$amp$plus(new Elem((String) null, "itemref", new UnprefixedAttribute("idref", new Text("book"), Null$.MODULE$), $tmpscope, true, Nil$.MODULE$));
        $buf7.$amp$plus(new Text("\n        "));
        $buf.$amp$plus(new Elem((String) null, "spine", null$6, $tmpscope, false, nodeSeq$7.seqToNodeSeq($buf7)));
        $buf.$amp$plus(new Text("\n        "));
        Null$ null$7 = Null$.MODULE$;
        NodeSeq$ nodeSeq$8 = NodeSeq$.MODULE$;
        NodeBuffer $buf8 = new NodeBuffer();
        $buf8.$amp$plus(new Text("\n          "));
        if (hasToc) {
            elemEmpty2 = new Elem((String) null, "reference", new UnprefixedAttribute("type", new Text("toc"), new UnprefixedAttribute("title", new Text("Table of Contents"), new UnprefixedAttribute("href", tocFile.getName(), Null$.MODULE$))), $tmpscope, false, Nil$.MODULE$);
        } else {
            elemEmpty2 = NodeSeq$.MODULE$.Empty();
        }
        $buf8.$amp$plus(elemEmpty2);
        $buf8.$amp$plus(new Text("\n        "));
        $buf.$amp$plus(new Elem((String) null, "guide", null$7, $tmpscope, false, nodeSeq$8.seqToNodeSeq($buf8)));
        $buf.$amp$plus(new Text("\n      "));
        Elem contents = new Elem((String) null, "package", unprefixedAttribute, $tmpscope, false, nodeSeq$.seqToNodeSeq($buf));
        FileUtils$.MODULE$.write(out, StringOps$.MODULE$.$plus$plus$extension(Predef$.MODULE$.augmentString("<?xml version=\"1.0\" encoding=\"utf-8\"?>"), contents.toString()));
    }
}
