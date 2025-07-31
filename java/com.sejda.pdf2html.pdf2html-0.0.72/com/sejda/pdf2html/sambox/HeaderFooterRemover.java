package com.sejda.pdf2html.sambox;

import com.sejda.pdf2html.LocalPDFTextStripper;
import com.sejda.pdf2html.util.LevenshteinMetric$;
import java.util.List;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.text.TextPosition;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.collection.BuildFrom$;
import scala.collection.IterableOnceOps;
import scala.collection.IterableOps;
import scala.collection.StringOps$;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Stream;
import scala.collection.mutable.Buffer;
import scala.collection.mutable.Buffer$;
import scala.math.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.util.Random$;
import scala.util.control.NonFatal$;

/* compiled from: HeaderFooterRemover.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005mb\u0001B\u000b\u0017\u0001}AQ\u0001\u000b\u0001\u0005\u0002%Bqa\u000b\u0001A\u0002\u0013\u0005A\u0006C\u00044\u0001\u0001\u0007I\u0011\u0001\u001b\t\ri\u0002\u0001\u0015)\u0003.\u0011\u001dY\u0004\u00011A\u0005\u00021Bq\u0001\u0010\u0001A\u0002\u0013\u0005Q\b\u0003\u0004@\u0001\u0001\u0006K!\f\u0005\b\u0001\u0002\u0001\r\u0011\"\u0001B\u0011\u001d)\u0006\u00011A\u0005\u0002YCa\u0001\u0017\u0001!B\u0013\u0011\u0005bB-\u0001\u0001\u0004%\t!\u0011\u0005\b5\u0002\u0001\r\u0011\"\u0001\\\u0011\u0019i\u0006\u0001)Q\u0005\u0005\")a\f\u0001C!?\")A\u000e\u0001C![\"9\u00111\u0002\u0001\u0005\u0002\u00055\u0001bBA\t\u0001\u0011\u0005\u00111\u0003\u0005\b\u00033\u0001A\u0011AA\u000e\u0011\u001d\t\t\u0004\u0001C\u0001\u0003gAq!a\u000e\u0001\t\u0003\tIDA\nIK\u0006$WM\u001d$p_R,'OU3n_Z,'O\u0003\u0002\u00181\u000511/Y7c_bT!!\u0007\u000e\u0002\u0011A$gM\r5u[2T!a\u0007\u000f\u0002\u000bM,'\u000eZ1\u000b\u0003u\t1aY8n\u0007\u0001\u00192\u0001\u0001\u0011%!\t\t#%D\u0001\u0019\u0013\t\u0019\u0003D\u0001\u000bM_\u000e\fG\u000e\u0015#G)\u0016DHo\u0015;sSB\u0004XM\u001d\t\u0003K\u0019j\u0011AF\u0005\u0003OY\u0011a\u0002\u00153g\t>\u001c\u0017I\\1msj,'/\u0001\u0004=S:LGO\u0010\u000b\u0002UA\u0011Q\u0005A\u0001\bQ\u0016\fG-\u001a:Z+\u0005i\u0003C\u0001\u00182\u001b\u0005y#\"\u0001\u0019\u0002\u000bM\u001c\u0017\r\\1\n\u0005Iz#!\u0002$m_\u0006$\u0018a\u00035fC\u0012,'/W0%KF$\"!\u000e\u001d\u0011\u000592\u0014BA\u001c0\u0005\u0011)f.\u001b;\t\u000fe\u001a\u0011\u0011!a\u0001[\u0005\u0019\u0001\u0010J\u0019\u0002\u0011!,\u0017\rZ3s3\u0002\nqAZ8pi\u0016\u0014\u0018,A\u0006g_>$XM]-`I\u0015\fHCA\u001b?\u0011\u001dId!!AA\u00025\n\u0001BZ8pi\u0016\u0014\u0018\fI\u0001\riJ\f\u0017N\\3e\u0019&tWm]\u000b\u0002\u0005B\u00191\t\u0013&\u000e\u0003\u0011S!!\u0012$\u0002\u000f5,H/\u00192mK*\u0011qiL\u0001\u000bG>dG.Z2uS>t\u0017BA%E\u0005\u0019\u0011UO\u001a4feB\u00111J\u0015\b\u0003\u0019B\u0003\"!T\u0018\u000e\u00039S!a\u0014\u0010\u0002\rq\u0012xn\u001c;?\u0013\t\tv&\u0001\u0004Qe\u0016$WMZ\u0005\u0003'R\u0013aa\u0015;sS:<'BA)0\u0003A!(/Y5oK\u0012d\u0015N\\3t?\u0012*\u0017\u000f\u0006\u00026/\"9\u0011(CA\u0001\u0002\u0004\u0011\u0015!\u0004;sC&tW\r\u001a'j]\u0016\u001c\b%A\u0004sK\u000e,g\u000e^:\u0002\u0017I,7-\u001a8ug~#S-\u001d\u000b\u0003kqCq!\u000f\u0007\u0002\u0002\u0003\u0007!)\u0001\u0005sK\u000e,g\u000e^:!\u0003%\u0019H/\u0019:u!\u0006<W\r\u0006\u00026A\")\u0011M\u0004a\u0001E\u0006!\u0001/Y4f!\t\u0019'.D\u0001e\u0015\t)g-A\u0004qI6|G-\u001a7\u000b\u0005]9'BA\u000ei\u0015\u0005I\u0017aA8sO&\u00111\u000e\u001a\u0002\u0007!\u0012\u0003\u0016mZ3\u0002\u0013]\u0014\u0018\u000e^3MS:,G#B\u001bo}\u0006\u001d\u0001\"B8\u0010\u0001\u0004\u0001\u0018a\u00027j]\u0016\u001cV-\u001d\t\u0004cZDX\"\u0001:\u000b\u0005M$\u0018\u0001B;uS2T\u0011!^\u0001\u0005U\u00064\u0018-\u0003\u0002xe\n!A*[:u!\tIH0D\u0001{\u0015\tYh-\u0001\u0003uKb$\u0018BA?{\u00051!V\r\u001f;Q_NLG/[8o\u0011\u0019yx\u00021\u0001\u0002\u0002\u0005i\u0011n\u001d*uY\u0012{W.\u001b8b]R\u00042ALA\u0002\u0013\r\t)a\f\u0002\b\u0005>|G.Z1o\u0011\u001d\tIa\u0004a\u0001\u0003\u0003\ta\u0001[1t%Rd\u0017a\u00062fY>twm\u001d+p\u0011\u0016\fG-\u001a:Pe\u001a{w\u000e^3s)\u0011\t\t!a\u0004\t\u000b=\u0004\u0002\u0019\u00019\u0002\u0017\r|G\u000e\\3di2Kg.\u001a\u000b\u0004\u0005\u0006U\u0001BBA\f#\u0001\u0007!*\u0001\u0003mS:,\u0017A\u00057fm\u0016t7\u000f\u001b;fS:\u001cu.\u001c9be\u0016$b!!\b\u0002*\u00055\u0002#\u0002\u0018\u0002 \u0005\r\u0012bAA\u0011_\t1q\n\u001d;j_:\u00042ALA\u0013\u0013\r\t9c\f\u0002\u0004\u0013:$\bBBA\u0016%\u0001\u0007!*\u0001\u0002tc!1\u0011q\u0006\nA\u0002)\u000b!a\u001d\u001a\u0002\rM\u001cwN]3t)\u0011\t\t!!\u000e\t\r\u0005]1\u00031\u0001K\u0003\u0011\u0019\u0018N_3\u0016\u0005\u0005\r\u0002")
/* loaded from: com.sejda.pdf2html.pdf2html-0.0.72.jar:com/sejda/pdf2html/sambox/HeaderFooterRemover.class */
public class HeaderFooterRemover extends LocalPDFTextStripper implements PdfDocAnalyzer {
    private float headerY;
    private float footerY;
    private Buffer<String> trainedLines;
    private Buffer<String> recents;

    @Override // com.sejda.pdf2html.sambox.PdfDocAnalyzer
    public PdfDocAnalyzer train(final PDDocument doc) {
        return train(doc);
    }

    @Override // com.sejda.pdf2html.sambox.PdfDocAnalyzer
    public String lineAsString(final List<TextPosition> line) {
        return lineAsString(line);
    }

    public HeaderFooterRemover() {
        PdfDocAnalyzer.$init$(this);
        this.headerY = 0.0f;
        this.footerY = 0.0f;
        this.trainedLines = Buffer$.MODULE$.apply(Nil$.MODULE$);
        this.recents = Buffer$.MODULE$.apply(Nil$.MODULE$);
    }

    public float headerY() {
        return this.headerY;
    }

    public void headerY_$eq(final float x$1) {
        this.headerY = x$1;
    }

    public float footerY() {
        return this.footerY;
    }

    public void footerY_$eq(final float x$1) {
        this.footerY = x$1;
    }

    public Buffer<String> trainedLines() {
        return this.trainedLines;
    }

    public void trainedLines_$eq(final Buffer<String> x$1) {
        this.trainedLines = x$1;
    }

    public Buffer<String> recents() {
        return this.recents;
    }

    public void recents_$eq(final Buffer<String> x$1) {
        this.recents = x$1;
    }

    public void startPage(final PDPage page) {
        PDRectangle mediaBox = page.getMediaBox();
        footerY_$eq(mediaBox.getLowerLeftY() + 50);
        headerY_$eq(mediaBox.getUpperRightY() - 100);
    }

    @Override // com.sejda.pdf2html.LocalPDFTextStripper
    public void writeLine(final List<TextPosition> lineSeq, final boolean isRtlDominant, final boolean hasRtl) {
        if (PdfLine$.MODULE$.lineToPdfLine(lineSeq).inHeaderArea(headerY()) || PdfLine$.MODULE$.lineToPdfLine(lineSeq).inFooterArea(footerY())) {
            collectLine(PdfLine$.MODULE$.lineToPdfLine(lineSeq).asString());
        }
    }

    public boolean belongsToHeaderOrFooter(final List<TextPosition> lineSeq) {
        if (lineSeq == null || lineSeq.size() == 0) {
            return true;
        }
        if (PdfLine$.MODULE$.lineToPdfLine(lineSeq).inHeaderArea(headerY()) || PdfLine$.MODULE$.lineToPdfLine(lineSeq).inFooterArea(footerY())) {
            String line = PdfLine$.MODULE$.lineToPdfLine(lineSeq).asString();
            if (recents().contains(line)) {
                return true;
            }
            recents().$plus$eq(line);
            recents().drop(package$.MODULE$.min(recents().size() - 6, 0));
            return scores(line);
        }
        return false;
    }

    public Buffer<String> collectLine(final String line) {
        return trainedLines().$plus$eq(line);
    }

    public Option<Object> levenshteinCompare(final String s1, final String s2) {
        try {
            return LevenshteinMetric$.MODULE$.compare(s1, s2);
        } catch (Throwable th) {
            if (!NonFatal$.MODULE$.apply(th) && !(th instanceof StackOverflowError)) {
                throw th;
            }
            return None$.MODULE$;
        }
    }

    public boolean scores(final String line) {
        Stream goodMatches = ((IterableOnceOps) ((IterableOps) Random$.MODULE$.shuffle(trainedLines(), BuildFrom$.MODULE$.buildFromIterableOps())).take(40)).toStream().map(previous -> {
            return BoxesRunTime.boxToInteger($anonfun$scores$1(this, line, previous));
        }).filter(x$1 -> {
            return x$1 < package$.MODULE$.min(10, StringOps$.MODULE$.size$extension(Predef$.MODULE$.augmentString(line)));
        }).take(3);
        boolean remove = goodMatches.size() >= 2;
        return remove;
    }

    public static final /* synthetic */ int $anonfun$scores$1(final HeaderFooterRemover $this, final String line$1, final String previous) {
        return BoxesRunTime.unboxToInt($this.levenshteinCompare(previous, line$1).getOrElse(() -> {
            return 100;
        }));
    }

    public int size() {
        return trainedLines().size();
    }
}
