package code.sejda.tasks.annotation;

import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationRubberStamp;

/* compiled from: DeleteAnnotationsParameters.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/annotation/AnnotationType$Stamp$.class */
public class AnnotationType$Stamp$ implements AnnotationType {
    public static final AnnotationType$Stamp$ MODULE$ = new AnnotationType$Stamp$();

    public String toString() {
        return "stamp";
    }

    @Override // code.sejda.tasks.annotation.AnnotationType
    public boolean matches(final PDAnnotation a) {
        if (a instanceof PDAnnotationRubberStamp) {
            String subtype = a.getSubtype();
            if (subtype != null ? subtype.equals(PDAnnotationRubberStamp.SUB_TYPE) : PDAnnotationRubberStamp.SUB_TYPE == 0) {
                return true;
            }
        }
        return false;
    }
}
