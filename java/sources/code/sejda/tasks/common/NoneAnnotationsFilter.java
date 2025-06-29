package code.sejda.tasks.common;

import org.sejda.sambox.pdmodel.interactive.annotation.AnnotationFilter;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import scala.reflect.ScalaSignature;

/* compiled from: AnnotationFilters.scala */
@ScalaSignature(bytes = "\u0006\u0005U2Aa\u0001\u0003\u0001\u001b!)Q\u0005\u0001C\u0001M!)\u0011\u0006\u0001C!U\t)bj\u001c8f\u0003:tw\u000e^1uS>t7OR5mi\u0016\u0014(BA\u0003\u0007\u0003\u0019\u0019w.\\7p]*\u0011q\u0001C\u0001\u0006i\u0006\u001c8n\u001d\u0006\u0003\u0013)\tQa]3kI\u0006T\u0011aC\u0001\u0005G>$Wm\u0001\u0001\u0014\u0007\u0001qa\u0003\u0005\u0002\u0010)5\t\u0001C\u0003\u0002\u0012%\u0005!A.\u00198h\u0015\u0005\u0019\u0012\u0001\u00026bm\u0006L!!\u0006\t\u0003\r=\u0013'.Z2u!\t92%D\u0001\u0019\u0015\tI\"$\u0001\u0006b]:|G/\u0019;j_:T!a\u0007\u000f\u0002\u0017%tG/\u001a:bGRLg/\u001a\u0006\u0003;y\tq\u0001\u001d3n_\u0012,GN\u0003\u0002 A\u000511/Y7c_bT!!C\u0011\u000b\u0003\t\n1a\u001c:h\u0013\t!\u0003D\u0001\tB]:|G/\u0019;j_:4\u0015\u000e\u001c;fe\u00061A(\u001b8jiz\"\u0012a\n\t\u0003Q\u0001i\u0011\u0001B\u0001\u0007C\u000e\u001cW\r\u001d;\u0015\u0005-\n\u0004C\u0001\u00170\u001b\u0005i#\"\u0001\u0018\u0002\u000bM\u001c\u0017\r\\1\n\u0005Aj#a\u0002\"p_2,\u0017M\u001c\u0005\u00063\t\u0001\rA\r\t\u0003/MJ!\u0001\u000e\r\u0003\u0019A#\u0015I\u001c8pi\u0006$\u0018n\u001c8")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/NoneAnnotationsFilter.class */
public class NoneAnnotationsFilter implements AnnotationFilter {
    @Override // org.sejda.sambox.pdmodel.interactive.annotation.AnnotationFilter
    public boolean accept(final PDAnnotation annotation) {
        return false;
    }
}
