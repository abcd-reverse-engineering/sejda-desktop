package code.sejda.tasks.common;

import scala.reflect.ScalaSignature;

/* compiled from: AnnotationFilters.scala */
@ScalaSignature(bytes = "\u0006\u0005%:Qa\u0002\u0005\t\u0002E1Qa\u0005\u0005\t\u0002QAQaG\u0001\u0005\u0002qAq!H\u0001C\u0002\u0013\u0005a\u0004\u0003\u0004#\u0003\u0001\u0006Ia\b\u0005\bG\u0005\u0011\r\u0011\"\u0001%\u0011\u0019A\u0013\u0001)A\u0005K\u0005\t\u0012I\u001c8pi\u0006$\u0018n\u001c8GS2$XM]:\u000b\u0005%Q\u0011AB2p[6|gN\u0003\u0002\f\u0019\u0005)A/Y:lg*\u0011QBD\u0001\u0006g\u0016TG-\u0019\u0006\u0002\u001f\u0005!1m\u001c3f\u0007\u0001\u0001\"AE\u0001\u000e\u0003!\u0011\u0011#\u00118o_R\fG/[8o\r&dG/\u001a:t'\t\tQ\u0003\u0005\u0002\u001735\tqCC\u0001\u0019\u0003\u0015\u00198-\u00197b\u0013\tQrC\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003E\t1CT8Ti&\u001c7.\u001f(pi\u0016\u001c\bk\u001c9vaN,\u0012a\b\t\u0003%\u0001J!!\t\u0005\u0003E9{G+\u001a=u'RL7m[=O_R,7/\u00118o_R\fG/[8og\u001aKG\u000e^3s\u0003Qqun\u0015;jG.Lhj\u001c;fgB{\u0007/\u001e9tA\u0005!aj\u001c8f+\u0005)\u0003C\u0001\n'\u0013\t9\u0003BA\u000bO_:,\u0017I\u001c8pi\u0006$\u0018n\u001c8t\r&dG/\u001a:\u0002\u000b9{g.\u001a\u0011")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/AnnotationFilters.class */
public final class AnnotationFilters {
    public static NoneAnnotationsFilter None() {
        return AnnotationFilters$.MODULE$.None();
    }

    public static NoTextStickyNotesAnnotationsFilter NoStickyNotesPopups() {
        return AnnotationFilters$.MODULE$.NoStickyNotesPopups();
    }
}
