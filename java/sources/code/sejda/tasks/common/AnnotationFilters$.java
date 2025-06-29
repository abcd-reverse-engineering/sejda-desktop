package code.sejda.tasks.common;

/* compiled from: AnnotationFilters.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/AnnotationFilters$.class */
public final class AnnotationFilters$ {
    public static final AnnotationFilters$ MODULE$ = new AnnotationFilters$();
    private static final NoTextStickyNotesAnnotationsFilter NoStickyNotesPopups = new NoTextStickyNotesAnnotationsFilter();
    private static final NoneAnnotationsFilter None = new NoneAnnotationsFilter();

    private AnnotationFilters$() {
    }

    public NoTextStickyNotesAnnotationsFilter NoStickyNotesPopups() {
        return NoStickyNotesPopups;
    }

    public NoneAnnotationsFilter None() {
        return None;
    }
}
