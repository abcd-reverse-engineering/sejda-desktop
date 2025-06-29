package com.sejda.pdf2html.sambox;

import com.google.common.io.ByteStreams;
import com.google.common.io.Closeables;
import com.sejda.pdf2html.LocalPDFTextStripper;
import java.io.OutputStreamWriter;
import java.util.List;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.text.PDFTextStripper;
import org.sejda.sambox.text.TextPosition;
import scala.collection.IterableOnceOps;
import scala.collection.IterableOps;
import scala.collection.JavaConverters$;
import scala.reflect.ScalaSignature;

/* compiled from: PdfDocAnalyzer.scala */
@ScalaSignature(bytes = "\u0006\u0005\u001d3q\u0001B\u0003\u0011\u0002\u0007\u0005a\u0002C\u0003\u0014\u0001\u0011\u0005A\u0003C\u0003\u001c\u0001\u0011\u0005A\u0004C\u0003+\u0001\u0011\u00051F\u0001\bQI\u001a$unY!oC2L(0\u001a:\u000b\u0005\u00199\u0011AB:b[\n|\u0007P\u0003\u0002\t\u0013\u0005A\u0001\u000f\u001a43QRlGN\u0003\u0002\u000b\u0017\u0005)1/\u001a6eC*\tA\"A\u0002d_6\u001c\u0001a\u0005\u0002\u0001\u001fA\u0011\u0001#E\u0007\u0002\u000f%\u0011!c\u0002\u0002\u0015\u0019>\u001c\u0017\r\u001c)E\rR+\u0007\u0010^*ue&\u0004\b/\u001a:\u0002\r\u0011Jg.\u001b;%)\u0005)\u0002C\u0001\f\u001a\u001b\u00059\"\"\u0001\r\u0002\u000bM\u001c\u0017\r\\1\n\u0005i9\"\u0001B+oSR\fQ\u0001\u001e:bS:$\"!\b\u0010\u000e\u0003\u0001AQa\b\u0002A\u0002\u0001\n1\u0001Z8d!\t\t\u0003&D\u0001#\u0015\t\u0019C%A\u0004qI6|G-\u001a7\u000b\u0005\u0019)#B\u0001\u0006'\u0015\u00059\u0013aA8sO&\u0011\u0011F\t\u0002\u000b!\u0012#unY;nK:$\u0018\u0001\u00047j]\u0016\f5o\u0015;sS:<GC\u0001\u00178!\tiCG\u0004\u0002/eA\u0011qfF\u0007\u0002a)\u0011\u0011'D\u0001\u0007yI|w\u000e\u001e \n\u0005M:\u0012A\u0002)sK\u0012,g-\u0003\u00026m\t11\u000b\u001e:j]\u001eT!aM\f\t\u000ba\u001a\u0001\u0019A\u001d\u0002\t1Lg.\u001a\t\u0004u}\nU\"A\u001e\u000b\u0005qj\u0014\u0001B;uS2T\u0011AP\u0001\u0005U\u00064\u0018-\u0003\u0002Aw\t!A*[:u!\t\u0011U)D\u0001D\u0015\t!E%\u0001\u0003uKb$\u0018B\u0001$D\u00051!V\r\u001f;Q_NLG/[8o\u0001")
/* loaded from: com.sejda.pdf2html.pdf2html-0.0.72.jar:com/sejda/pdf2html/sambox/PdfDocAnalyzer.class */
public interface PdfDocAnalyzer {
    static void $init$(final PdfDocAnalyzer $this) {
    }

    /* JADX WARN: Multi-variable type inference failed */
    default PdfDocAnalyzer train(final PDDocument doc) {
        OutputStreamWriter out = new OutputStreamWriter(ByteStreams.nullOutputStream());
        try {
            ((PDFTextStripper) this).writeText(doc, out);
            return this;
        } finally {
            Closeables.close(out, true);
        }
    }

    default String lineAsString(final List<TextPosition> line) {
        return ((IterableOnceOps) ((IterableOps) JavaConverters$.MODULE$.asScalaBufferConverter(line).asScala()).map(x0$1 -> {
            return x0$1 instanceof LocalPDFTextStripper.WordSeparator ? " " : x0$1.getUnicode();
        })).mkString("");
    }
}
