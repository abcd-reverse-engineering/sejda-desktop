package code.sejda.tasks.common;

import java.awt.geom.Point2D;
import org.sejda.model.RectangularBox;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import scala.Tuple2;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;

/* compiled from: AnnotationsHelper.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005\rq!B\u0005\u000b\u0011\u0003\u0019b!B\u000b\u000b\u0011\u00031\u0002\"B\u000f\u0002\t\u0003q\u0002\"B\u0010\u0002\t\u0003\u0001\u0003\"B\u001b\u0002\t\u00031\u0004\"B#\u0002\t\u00031\u0005\"\u0002%\u0002\t\u0003I\u0005\"\u0002(\u0002\t\u0013y\u0005\"\u0002-\u0002\t\u0003I\u0016!E!o]>$\u0018\r^5p]NDU\r\u001c9fe*\u00111\u0002D\u0001\u0007G>lWn\u001c8\u000b\u00055q\u0011!\u0002;bg.\u001c(BA\b\u0011\u0003\u0015\u0019XM\u001b3b\u0015\u0005\t\u0012\u0001B2pI\u0016\u001c\u0001\u0001\u0005\u0002\u0015\u00035\t!BA\tB]:|G/\u0019;j_:\u001c\b*\u001a7qKJ\u001c\"!A\f\u0011\u0005aYR\"A\r\u000b\u0003i\tQa]2bY\u0006L!\u0001H\r\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\t1#A\u0004rk\u0006$7o\u00144\u0015\u0005\u0005:\u0003c\u0001\r#I%\u00111%\u0007\u0002\u0006\u0003J\u0014\u0018-\u001f\t\u00031\u0015J!AJ\r\u0003\u000b\u0019cw.\u0019;\t\u000b!\u001a\u0001\u0019A\u0015\u0002\u0011A|7/\u001b;j_:\u0004\"AK\u001a\u000e\u0003-R!a\u0003\u0017\u000b\u00055r\u0013a\u00029e[>$W\r\u001c\u0006\u0003_A\naa]1nE>D(BA\b2\u0015\u0005\u0011\u0014aA8sO&\u0011Ag\u000b\u0002\f!\u0012\u0013Vm\u0019;b]\u001edW-A\rc_VtG-\u001b8h\u0005>D\u0018\t\u001a6vgR,G\rV8QC\u001e,GcA\u001c>\u007fA\u0011\u0001hO\u0007\u0002s)\u0011!\bM\u0001\u0006[>$W\r\\\u0005\u0003ye\u0012aBU3di\u0006tw-\u001e7be\n{\u0007\u0010C\u0003?\t\u0001\u0007q'A\u0006c_VtG-\u001b8h\u0005>D\b\"\u0002!\u0005\u0001\u0004\t\u0015\u0001\u00029bO\u0016\u0004\"AQ\"\u000e\u00031J!\u0001\u0012\u0017\u0003\rA#\u0005+Y4f\u00035!x\u000e\u0015#SK\u000e$\u0018M\\4mKR\u0011\u0011f\u0012\u0005\u0006}\u0015\u0001\raN\u0001\u000f_Z,'\u000f\\1q!\u0016\u00148-\u001a8u)\r!#\n\u0014\u0005\u0006\u0017\u001a\u0001\r!K\u0001\u0007i\u0006\u0014x-\u001a;\t\u000b53\u0001\u0019A\u0015\u0002\u000b=$\b.\u001a:\u0002\u000fI|WO\u001c3VaR\u0011\u0001k\u0015\t\u00031EK!AU\r\u0003\u0007%sG\u000fC\u0003U\u000f\u0001\u0007Q+\u0001\u0002j]B\u0011\u0001DV\u0005\u0003/f\u0011a\u0001R8vE2,\u0017\u0001\b4j]\u0012\feN\\8uCRLwN\\:J]\n{WO\u001c3j]\u001e\u0014u\u000e\u001f\u000b\u00065F\u0014Xp \t\u00047\u000e4gB\u0001/b\u001d\ti\u0006-D\u0001_\u0015\ty&#\u0001\u0004=e>|GOP\u0005\u00025%\u0011!-G\u0001\ba\u0006\u001c7.Y4f\u0013\t!WMA\u0002TKFT!AY\r\u0011\ta9\u0017\u000eJ\u0005\u0003Qf\u0011a\u0001V;qY\u0016\u0014\u0004C\u00016p\u001b\u0005Y'B\u00017n\u0003)\tgN\\8uCRLwN\u001c\u0006\u0003]2\n1\"\u001b8uKJ\f7\r^5wK&\u0011\u0001o\u001b\u0002\r!\u0012\u000beN\\8uCRLwN\u001c\u0005\u0006\u0001\"\u0001\r!\u0011\u0005\u0006Q!\u0001\ra\u001d\t\u0003inl\u0011!\u001e\u0006\u0003m^\fAaZ3p[*\u0011\u00010_\u0001\u0004C^$(\"\u0001>\u0002\t)\fg/Y\u0005\u0003yV\u0014q\u0001U8j]R\u0014D\tC\u0003\u007f\u0011\u0001\u0007A%A\u0003xS\u0012$\b\u000e\u0003\u0004\u0002\u0002!\u0001\r\u0001J\u0001\u0007Q\u0016Lw\r\u001b;")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/AnnotationsHelper.class */
public final class AnnotationsHelper {
    public static Seq<Tuple2<PDAnnotation, Object>> findAnnotationsInBoundingBox(final PDPage page, final Point2D position, final float width, final float height) {
        return AnnotationsHelper$.MODULE$.findAnnotationsInBoundingBox(page, position, width, height);
    }

    public static float overlapPercent(final PDRectangle target, final PDRectangle other) {
        return AnnotationsHelper$.MODULE$.overlapPercent(target, other);
    }

    public static PDRectangle toPDRectangle(final RectangularBox boundingBox) {
        return AnnotationsHelper$.MODULE$.toPDRectangle(boundingBox);
    }

    public static RectangularBox boundingBoxAdjustedToPage(final RectangularBox boundingBox, final PDPage page) {
        return AnnotationsHelper$.MODULE$.boundingBoxAdjustedToPage(boundingBox, page);
    }

    public static float[] quadsOf(final PDRectangle position) {
        return AnnotationsHelper$.MODULE$.quadsOf(position);
    }
}
