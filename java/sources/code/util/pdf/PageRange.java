package code.util.pdf;

import scala.Option;
import scala.reflect.ScalaSignature;

/* compiled from: PageRanges.scala */
@ScalaSignature(bytes = "\u0006\u0005Y:Q\u0001B\u0003\t\u000211QAD\u0003\t\u0002=AQAF\u0001\u0005\u0002]AQ\u0001G\u0001\u0005\u0002e\t\u0011\u0002U1hKJ\u000bgnZ3\u000b\u0005\u00199\u0011a\u00019eM*\u0011\u0001\"C\u0001\u0005kRLGNC\u0001\u000b\u0003\u0011\u0019w\u000eZ3\u0004\u0001A\u0011Q\"A\u0007\u0002\u000b\tI\u0001+Y4f%\u0006tw-Z\n\u0003\u0003A\u0001\"!\u0005\u000b\u000e\u0003IQ\u0011aE\u0001\u0006g\u000e\fG.Y\u0005\u0003+I\u0011a!\u00118z%\u00164\u0017A\u0002\u001fj]&$h\bF\u0001\r\u0003\u001d)h.\u00199qYf$\"AG\u0015\u0011\u0007EYR$\u0003\u0002\u001d%\t1q\n\u001d;j_:\u0004\"A\b\u0015\u000e\u0003}Q!\u0001I\u0011\u0002\tA\fw-\u001a\u0006\u0003\r\tR!a\t\u0013\u0002\u000b5|G-\u001a7\u000b\u0005\u00152\u0013!B:fU\u0012\f'\"A\u0014\u0002\u0007=\u0014x-\u0003\u0002\u000f?!)!f\u0001a\u0001W\u0005\t1\u000f\u0005\u0002-g9\u0011Q&\r\t\u0003]Ii\u0011a\f\u0006\u0003a-\ta\u0001\u0010:p_Rt\u0014B\u0001\u001a\u0013\u0003\u0019\u0001&/\u001a3fM&\u0011A'\u000e\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005I\u0012\u0002")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/pdf/PageRange.class */
public final class PageRange {
    public static Option<org.sejda.model.pdf.page.PageRange> unapply(final String s) {
        return PageRange$.MODULE$.unapply(s);
    }
}
