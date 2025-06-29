package code.sejda.tasks.excel;

import org.sejda.model.TopLeftRectangularBox;
import scala.reflect.ScalaSignature;

/* compiled from: TableDetector.scala */
@ScalaSignature(bytes = "\u0006\u0005}2qAC\u0006\u0011\u0002\u0007\u0005A\u0003C\u0003\u001c\u0001\u0011\u0005A\u0004C\u0003!\u0001\u0019\u0005\u0011\u0005C\u0003&\u0001\u0019\u0005\u0011\u0005C\u0003'\u0001\u0019\u0005\u0011\u0005C\u0003(\u0001\u0019\u0005\u0011\u0005C\u0003)\u0001\u0011\u0005\u0011\u0005C\u0003*\u0001\u0011\u0005\u0011\u0005C\u0003+\u0001\u0011\u00051\u0006C\u00036\u0001\u0011\u0005aGA\rU_BdUM\u001a;SK\u000e$\u0018M\\4vY\u0006\u0014(i\u001c=MS.,'B\u0001\u0007\u000e\u0003\u0015)\u0007pY3m\u0015\tqq\"A\u0003uCN\\7O\u0003\u0002\u0011#\u0005)1/\u001a6eC*\t!#\u0001\u0003d_\u0012,7\u0001A\n\u0003\u0001U\u0001\"AF\r\u000e\u0003]Q\u0011\u0001G\u0001\u0006g\u000e\fG.Y\u0005\u00035]\u0011a!\u00118z%\u00164\u0017A\u0002\u0013j]&$H\u0005F\u0001\u001e!\t1b$\u0003\u0002 /\t!QK\\5u\u0003\r!x\u000e]\u000b\u0002EA\u0011acI\u0005\u0003I]\u0011QA\u00127pCR\faAY8ui>l\u0017!\u0002:jO\"$\u0018\u0001\u00027fMR\fQa^5ei\"\fa\u0001[3jO\"$\u0018aF1t)>\u0004H*\u001a4u%\u0016\u001cG/\u00198hk2\f'OQ8y)\u0005a\u0003CA\u00174\u001b\u0005q#BA\u00181\u0003\u0015iw\u000eZ3m\u0015\t\u0001\u0012GC\u00013\u0003\ry'oZ\u0005\u0003i9\u0012Q\u0003V8q\u0019\u00164GOU3di\u0006tw-\u001e7be\n{\u00070A\u0006u_N#(/\u001b8h\u0005>DH#A\u001c\u0011\u0005ajT\"A\u001d\u000b\u0005iZ\u0014\u0001\u00027b]\u001eT\u0011\u0001P\u0001\u0005U\u00064\u0018-\u0003\u0002?s\t11\u000b\u001e:j]\u001e\u0004")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/excel/TopLeftRectangularBoxLike.class */
public interface TopLeftRectangularBoxLike {
    float top();

    float bottom();

    float right();

    float left();

    static void $init$(final TopLeftRectangularBoxLike $this) {
    }

    default float width() {
        return right() - left();
    }

    default float height() {
        return bottom() - top();
    }

    default TopLeftRectangularBox asTopLeftRectangularBox() {
        float width = right() - left();
        float height = top() - bottom();
        return new TopLeftRectangularBox((int) left(), (int) top(), (int) width, (int) height);
    }

    default String toStringBox() {
        return new StringBuilder(5).append("[").append(top()).append(" ").append(bottom()).append(" ").append(left()).append(" ").append(right()).append("]").toString();
    }
}
