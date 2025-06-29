package code.sejda.tasks.ocr;

import java.io.IOException;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.model.input.PdfSource;
import org.sejda.sambox.pdmodel.PDDocument;
import scala.reflect.ScalaSignature;

/* compiled from: TesseractUtils.scala */
@ScalaSignature(bytes = "\u0006\u0005y3AAC\u0006\u0001)!A1\u0004\u0001B\u0001B\u0003%A\u0004C\u00034\u0001\u0011\u0005A\u0007C\u0004=\u0001\u0001\u0007I\u0011A\u001f\t\u000f!\u0003\u0001\u0019!C\u0001\u0013\"1q\n\u0001Q!\nyBQ\u0001\u0015\u0001\u0005\nECQA\u0015\u0001\u0005\u0002MCQ\u0001\u0016\u0001\u0005\u0002MCQ!\u0016\u0001\u0005\u0002Y\u0013\u0011DU3pa\u0016t\u0017M\u00197f\t>\u001cW/\\3oi\"\u000bg\u000e\u001a7fe*\u0011A\"D\u0001\u0004_\u000e\u0014(B\u0001\b\u0010\u0003\u0015!\u0018m]6t\u0015\t\u0001\u0012#A\u0003tK*$\u0017MC\u0001\u0013\u0003\u0011\u0019w\u000eZ3\u0004\u0001M\u0011\u0001!\u0006\t\u0003-ei\u0011a\u0006\u0006\u00021\u0005)1oY1mC&\u0011!d\u0006\u0002\u0007\u0003:L(+\u001a4\u0002\rM|WO]2fa\ti\"\u0006E\u0002\u001fM!j\u0011a\b\u0006\u0003A\u0005\nQ!\u001b8qkRT!AI\u0012\u0002\u000b5|G-\u001a7\u000b\u0005A!#\"A\u0013\u0002\u0007=\u0014x-\u0003\u0002(?\tI\u0001\u000b\u001a4T_V\u00148-\u001a\t\u0003S)b\u0001\u0001B\u0005,\u0003\u0005\u0005\t\u0011!B\u0001Y\t\u0019q\f\n\u001a\u0012\u00055\u0002\u0004C\u0001\f/\u0013\tysCA\u0004O_RD\u0017N\\4\u0011\u0005Y\t\u0014B\u0001\u001a\u0018\u0005\r\te._\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005U:\u0004C\u0001\u001c\u0001\u001b\u0005Y\u0001\"B\u000e\u0003\u0001\u0004A\u0004GA\u001d<!\rqbE\u000f\t\u0003Sm\"\u0011bK\u001c\u0002\u0002\u0003\u0005)\u0011\u0001\u0017\u0002\u0015\u0011|7\rS1oI2,'/F\u0001?!\tyd)D\u0001A\u0015\t\t%)A\u0005d_6\u0004xN\\3oi*\u00111\tR\u0001\u0007g\u0006l'm\u001c=\u000b\u0005\u0015\u001b\u0013\u0001B5na2L!a\u0012!\u0003#A#Ei\\2v[\u0016tG\u000fS1oI2,'/\u0001\be_\u000eD\u0015M\u001c3mKJ|F%Z9\u0015\u0005)k\u0005C\u0001\fL\u0013\tauC\u0001\u0003V]&$\bb\u0002(\u0005\u0003\u0003\u0005\rAP\u0001\u0004q\u0012\n\u0014a\u00033pG\"\u000bg\u000e\u001a7fe\u0002\nAa\u001c9f]R\ta(\u0001\u0004sK>\u0004XM\u001c\u000b\u0002\u0015\u0006)1\r\\8tK\u0006\u0019Am\\2\u0016\u0003]\u0003\"\u0001\u0017/\u000e\u0003eS!AW.\u0002\u000fA$Wn\u001c3fY*\u00111iI\u0005\u0003;f\u0013!\u0002\u0015#E_\u000e,X.\u001a8u\u0001")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/ocr/ReopenableDocumentHandler.class */
public class ReopenableDocumentHandler {
    private final PdfSource<?> source;
    private PDDocumentHandler docHandler = open();

    public ReopenableDocumentHandler(final PdfSource<?> source) {
        this.source = source;
    }

    public PDDocumentHandler docHandler() {
        return this.docHandler;
    }

    public void docHandler_$eq(final PDDocumentHandler x$1) {
        this.docHandler = x$1;
    }

    private PDDocumentHandler open() {
        return (PDDocumentHandler) this.source.open(new DefaultPdfSourceOpener());
    }

    public void reopen() throws IOException {
        docHandler().close();
        docHandler_$eq(open());
    }

    public void close() throws IOException {
        docHandler().close();
    }

    public PDDocument doc() {
        return docHandler().getUnderlyingPDDocument();
    }
}
