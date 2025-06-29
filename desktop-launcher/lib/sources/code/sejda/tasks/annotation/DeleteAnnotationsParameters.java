package code.sejda.tasks.annotation;

import code.sejda.tasks.common.param.WithPageSelectionParams;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;
import org.sejda.model.pdf.page.PageRange;
import scala.collection.immutable.Set;
import scala.reflect.ScalaSignature;

/* compiled from: DeleteAnnotationsParameters.scala */
@ScalaSignature(bytes = "\u0006\u0005!4AAC\u0006\u0001)!A!\u0006\u0001BC\u0002\u0013\u00051\u0006\u0003\u0005>\u0001\t\u0005\t\u0015!\u0003-\u0011!q\u0004A!b\u0001\n\u0003y\u0004\u0002C(\u0001\u0005\u0003\u0005\u000b\u0011\u0002!\t\u000bA\u0003A\u0011A)\b\u000fU[\u0011\u0011!E\u0001-\u001a9!bCA\u0001\u0012\u00039\u0006\"\u0002)\b\t\u0003a\u0006bB/\b#\u0003%\tA\u0018\u0002\u001c\t\u0016dW\r^3B]:|G/\u0019;j_:\u001c\b+\u0019:b[\u0016$XM]:\u000b\u00051i\u0011AC1o]>$\u0018\r^5p]*\u0011abD\u0001\u0006i\u0006\u001c8n\u001d\u0006\u0003!E\tQa]3kI\u0006T\u0011AE\u0001\u0005G>$Wm\u0001\u0001\u0014\u0007\u0001)\"\u0005\u0005\u0002\u0017A5\tqC\u0003\u0002\u00193\u0005!!-Y:f\u0015\tQ2$A\u0005qCJ\fW.\u001a;fe*\u0011A$H\u0001\u0006[>$W\r\u001c\u0006\u0003!yQ\u0011aH\u0001\u0004_J<\u0017BA\u0011\u0018\u0005%jU\u000f\u001c;ja2,\u0007\u000b\u001a4T_V\u00148-Z'vYRL\u0007\u000f\\3PkR\u0004X\u000f\u001e)be\u0006lW\r^3sgB\u00111\u0005K\u0007\u0002I)\u0011QEJ\u0001\u0006a\u0006\u0014\u0018-\u001c\u0006\u0003O5\taaY8n[>t\u0017BA\u0015%\u0005]9\u0016\u000e\u001e5QC\u001e,7+\u001a7fGRLwN\u001c)be\u0006l7/A\u0003usB,7/F\u0001-!\ric'\u000f\b\u0003]Q\u0002\"a\f\u001a\u000e\u0003AR!!M\n\u0002\rq\u0012xn\u001c;?\u0015\u0005\u0019\u0014!B:dC2\f\u0017BA\u001b3\u0003\u0019\u0001&/\u001a3fM&\u0011q\u0007\u000f\u0002\u0004'\u0016$(BA\u001b3!\tQ4(D\u0001\f\u0013\ta4B\u0001\bB]:|G/\u0019;j_:$\u0016\u0010]3\u0002\rQL\b/Z:!\u00035\u0001\u0018mZ3TK2,7\r^5p]V\t\u0001\tE\u0002B\r\u001ek\u0011A\u0011\u0006\u0003\u0007\u0012\u000bq!\\;uC\ndWM\u0003\u0002Fe\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u0005]\u0012\u0005C\u0001%N\u001b\u0005I%B\u0001&L\u0003\u0011\u0001\u0018mZ3\u000b\u00051[\u0012a\u00019eM&\u0011a*\u0013\u0002\n!\u0006<WMU1oO\u0016\fa\u0002]1hKN+G.Z2uS>t\u0007%\u0001\u0004=S:LGO\u0010\u000b\u0004%N#\u0006C\u0001\u001e\u0001\u0011\u0015QS\u00011\u0001-\u0011\u001dqT\u0001%AA\u0002\u0001\u000b1\u0004R3mKR,\u0017I\u001c8pi\u0006$\u0018n\u001c8t!\u0006\u0014\u0018-\\3uKJ\u001c\bC\u0001\u001e\b'\t9\u0001\f\u0005\u0002Z56\t!'\u0003\u0002\\e\t1\u0011I\\=SK\u001a$\u0012AV\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000f\n\u001a\u0016\u0003}S#\u0001\u00111,\u0003\u0005\u0004\"A\u00194\u000e\u0003\rT!\u0001Z3\u0002\u0013Ut7\r[3dW\u0016$'B\u0001\u00073\u0013\t97MA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\u0004")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/annotation/DeleteAnnotationsParameters.class */
public class DeleteAnnotationsParameters extends MultiplePdfSourceMultipleOutputParameters implements WithPageSelectionParams {
    private final Set<AnnotationType> types;
    private final scala.collection.mutable.Set<PageRange> pageSelection;

    @Override // code.sejda.tasks.common.param.WithPageSelectionParams
    public Set<Object> getPages(final int totalNumberOfPage) {
        return getPages(totalNumberOfPage);
    }

    @Override // code.sejda.tasks.common.param.WithPageSelectionParams
    public boolean addPageRange(final PageRange range) {
        return addPageRange(range);
    }

    public Set<AnnotationType> types() {
        return this.types;
    }

    public DeleteAnnotationsParameters(final Set<AnnotationType> types, final scala.collection.mutable.Set<PageRange> pageSelection) {
        this.types = types;
        this.pageSelection = pageSelection;
        WithPageSelectionParams.$init$(this);
    }

    @Override // code.sejda.tasks.common.param.WithPageSelectionParams
    public scala.collection.mutable.Set<PageRange> pageSelection() {
        return this.pageSelection;
    }
}
