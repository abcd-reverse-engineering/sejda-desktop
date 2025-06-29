package code.sejda.tasks.common;

import org.sejda.sambox.pdmodel.interactive.annotation.AnnotationFilter;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationText;
import scala.reflect.ScalaSignature;

/* compiled from: AnnotationFilters.scala */
@ScalaSignature(bytes = "\u0006\u0005U2Aa\u0001\u0003\u0001\u001b!)Q\u0005\u0001C\u0001M!)\u0011\u0006\u0001C!U\t\u0011cj\u001c+fqR\u001cF/[2ls:{G/Z:B]:|G/\u0019;j_:\u001ch)\u001b7uKJT!!\u0002\u0004\u0002\r\r|W.\\8o\u0015\t9\u0001\"A\u0003uCN\\7O\u0003\u0002\n\u0015\u0005)1/\u001a6eC*\t1\"\u0001\u0003d_\u0012,7\u0001A\n\u0004\u000191\u0002CA\b\u0015\u001b\u0005\u0001\"BA\t\u0013\u0003\u0011a\u0017M\\4\u000b\u0003M\tAA[1wC&\u0011Q\u0003\u0005\u0002\u0007\u001f\nTWm\u0019;\u0011\u0005]\u0019S\"\u0001\r\u000b\u0005eQ\u0012AC1o]>$\u0018\r^5p]*\u00111\u0004H\u0001\fS:$XM]1di&4XM\u0003\u0002\u001e=\u00059\u0001\u000fZ7pI\u0016d'BA\u0010!\u0003\u0019\u0019\u0018-\u001c2pq*\u0011\u0011\"\t\u0006\u0002E\u0005\u0019qN]4\n\u0005\u0011B\"\u0001E!o]>$\u0018\r^5p]\u001aKG\u000e^3s\u0003\u0019a\u0014N\\5u}Q\tq\u0005\u0005\u0002)\u00015\tA!\u0001\u0004bG\u000e,\u0007\u000f\u001e\u000b\u0003WE\u0002\"\u0001L\u0018\u000e\u00035R\u0011AL\u0001\u0006g\u000e\fG.Y\u0005\u0003a5\u0012qAQ8pY\u0016\fg\u000eC\u0003\u001a\u0005\u0001\u0007!\u0007\u0005\u0002\u0018g%\u0011A\u0007\u0007\u0002\r!\u0012\u000beN\\8uCRLwN\u001c")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/NoTextStickyNotesAnnotationsFilter.class */
public class NoTextStickyNotesAnnotationsFilter implements AnnotationFilter {
    @Override // org.sejda.sambox.pdmodel.interactive.annotation.AnnotationFilter
    public boolean accept(final PDAnnotation annotation) {
        return ((annotation instanceof PDAnnotationText) && ((PDAnnotationText) annotation).getOpen()) ? false : true;
    }
}
