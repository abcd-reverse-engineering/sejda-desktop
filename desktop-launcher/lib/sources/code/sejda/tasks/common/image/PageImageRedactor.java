package code.sejda.tasks.common.image;

import java.io.IOException;
import org.sejda.sambox.pdmodel.PDPage;
import scala.MatchError;
import scala.Tuple2;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Map;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ObjectRef;

/* compiled from: PageImageRedactor.scala */
@ScalaSignature(bytes = "\u0006\u0005a2A!\u0002\u0004\u0001#!)\u0001\u0004\u0001C\u00013!9A\u0004\u0001b\u0001\n\u0003i\u0002BB\u0011\u0001A\u0003%a\u0004C\u0003#\u0001\u0011\u00051EA\tQC\u001e,\u0017*\\1hKJ+G-Y2u_JT!a\u0002\u0005\u0002\u000b%l\u0017mZ3\u000b\u0005%Q\u0011AB2p[6|gN\u0003\u0002\f\u0019\u0005)A/Y:lg*\u0011QBD\u0001\u0006g\u0016TG-\u0019\u0006\u0002\u001f\u0005!1m\u001c3f\u0007\u0001\u0019\"\u0001\u0001\n\u0011\u0005M1R\"\u0001\u000b\u000b\u0003U\tQa]2bY\u0006L!a\u0006\u000b\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\t!\u0004\u0005\u0002\u001c\u00015\ta!\u0001\u0004f]\u001eLg.Z\u000b\u0002=A\u00111dH\u0005\u0003A\u0019\u0011Q\u0004\u00153g\u00136\fw-\u001a*fI\u0006\u001cG/\u001b8h'R\u0014X-Y7F]\u001eLg.Z\u0001\bK:<\u0017N\\3!\u0003\u0019\u0011X\rZ1diR\u0011Ae\n\t\u00037\u0015J!A\n\u0004\u0003+%k\u0017mZ3SK\u0012\f7\r^5p]J+7/\u001e7ug\")\u0001\u0006\u0002a\u0001S\u0005)\u0011\u000e^3ngB\u0019!FM\u001b\u000f\u0005-\u0002dB\u0001\u00170\u001b\u0005i#B\u0001\u0018\u0011\u0003\u0019a$o\\8u}%\tQ#\u0003\u00022)\u00059\u0001/Y2lC\u001e,\u0017BA\u001a5\u0005\r\u0019V-\u001d\u0006\u0003cQ\u0001\"a\u0007\u001c\n\u0005]2!AD%nC\u001e,'+\u001a3bGRLwN\u001c")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/image/PageImageRedactor.class */
public class PageImageRedactor {
    private final PdfImageRedactingStreamEngine engine = new PdfImageRedactingStreamEngine();

    public PdfImageRedactingStreamEngine engine() {
        return this.engine;
    }

    public ImageRedactionResults redact(final Seq<ImageRedaction> items) {
        ObjectRef notRedacted = ObjectRef.create(Nil$.MODULE$);
        items.groupBy(x$1 -> {
            return x$1.page();
        }).foreach(x0$1 -> {
            $anonfun$redact$2(this, notRedacted, x0$1);
            return BoxedUnit.UNIT;
        });
        return new ImageRedactionResults((List) notRedacted.elem);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    public static final /* synthetic */ void $anonfun$redact$2(final PageImageRedactor $this, final ObjectRef notRedacted$1, final Tuple2 x0$1) throws MatchError, IOException {
        if (x0$1 != null) {
            PDPage page = (PDPage) x0$1._1();
            Seq items = (Seq) x0$1._2();
            Map results = $this.engine().redact(page, (Seq) items.map(x$2 -> {
                return x$2.objId();
            }));
            items.foreach(item -> {
                $anonfun$redact$4(results, notRedacted$1, item);
                return BoxedUnit.UNIT;
            });
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
            return;
        }
        throw new MatchError(x0$1);
    }

    public static final /* synthetic */ void $anonfun$redact$4(final Map results$1, final ObjectRef notRedacted$1, final ImageRedaction item) {
        if (BoxesRunTime.unboxToInt(results$1.apply(item.objId())) <= 0) {
            notRedacted$1.elem = ((List) notRedacted$1.elem).$colon$colon(item);
        }
    }
}
