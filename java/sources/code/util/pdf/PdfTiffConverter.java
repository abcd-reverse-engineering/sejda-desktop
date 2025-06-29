package code.util.pdf;

import code.sejda.tasks.ocr.ReopenableDocumentHandler;
import java.io.File;
import scala.reflect.ScalaSignature;

/* compiled from: PdfTiffConverter.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0001;Q\u0001B\u0003\t\u000211QAD\u0003\t\u0002=AQAG\u0001\u0005\u0002mAQ\u0001H\u0001\u0005\u0002u\t\u0001\u0003\u00153g)&4gmQ8om\u0016\u0014H/\u001a:\u000b\u0005\u00199\u0011a\u00019eM*\u0011\u0001\"C\u0001\u0005kRLGNC\u0001\u000b\u0003\u0011\u0019w\u000eZ3\u0004\u0001A\u0011Q\"A\u0007\u0002\u000b\t\u0001\u0002\u000b\u001a4US\u001a47i\u001c8wKJ$XM]\n\u0004\u0003A1\u0002CA\t\u0015\u001b\u0005\u0011\"\"A\n\u0002\u000bM\u001c\u0017\r\\1\n\u0005U\u0011\"AB!osJ+g\r\u0005\u0002\u001815\tq!\u0003\u0002\u001a\u000f\tAAj\\4hC\ndW-\u0001\u0004=S:LGO\u0010\u000b\u0002\u0019\u000591m\u001c8wKJ$HC\u0002\u0010'e]bd\b\u0005\u0002 I5\t\u0001E\u0003\u0002\"E\u0005\u0011\u0011n\u001c\u0006\u0002G\u0005!!.\u0019<b\u0013\t)\u0003E\u0001\u0003GS2,\u0007\"B\u0014\u0004\u0001\u0004A\u0013A\u00033pG\"\u000bg\u000e\u001a7feB\u0011\u0011\u0006M\u0007\u0002U)\u00111\u0006L\u0001\u0004_\u000e\u0014(BA\u0017/\u0003\u0015!\u0018m]6t\u0015\ty\u0013\"A\u0003tK*$\u0017-\u0003\u00022U\tI\"+Z8qK:\f'\r\\3E_\u000e,X.\u001a8u\u0011\u0006tG\r\\3s\u0011\u0015\u00194\u00011\u00015\u0003\r!\u0007/\u001b\t\u0003#UJ!A\u000e\n\u0003\u0007%sG\u000fC\u00039\u0007\u0001\u0007\u0011(\u0001\u0006sK:$WM\u001d+fqR\u0004\"!\u0005\u001e\n\u0005m\u0012\"a\u0002\"p_2,\u0017M\u001c\u0005\u0006{\r\u0001\r\u0001N\u0001\ngR\f'\u000f\u001e)bO\u0016DQaP\u0002A\u0002Q\nq!\u001a8e!\u0006<W\r")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/pdf/PdfTiffConverter.class */
public final class PdfTiffConverter {
    public static File convert(final ReopenableDocumentHandler docHandler, final int dpi, final boolean renderText, final int startPage, final int endPage) {
        return PdfTiffConverter$.MODULE$.convert(docHandler, dpi, renderText, startPage, endPage);
    }
}
