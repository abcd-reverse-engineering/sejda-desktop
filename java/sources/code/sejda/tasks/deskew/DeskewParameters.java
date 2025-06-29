package code.sejda.tasks.deskew;

import code.sejda.tasks.common.param.WithPageSelectionParams;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;
import org.sejda.model.pdf.page.PageRange;
import scala.collection.immutable.Map;
import scala.collection.mutable.HashMap;
import scala.collection.mutable.Set;
import scala.reflect.ScalaSignature;

/* compiled from: DeskewParameters.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005Ma\u0001\u0002\n\u0014\u0001qA\u0001B\r\u0001\u0003\u0006\u0004%\ta\r\u0005\tu\u0001\u0011\t\u0011)A\u0005i!A1\b\u0001BC\u0002\u0013\u00051\u0007\u0003\u0005=\u0001\t\u0005\t\u0015!\u00035\u0011!i\u0004A!b\u0001\n\u0003q\u0004\u0002C(\u0001\u0005\u0003\u0005\u000b\u0011B \t\u0011A\u0003!Q1A\u0005\u0002EC\u0001\u0002\u0017\u0001\u0003\u0002\u0003\u0006IA\u0015\u0005\u00063\u0002!\tA\u0017\u0005\u0006C\u0002!\tAY\u0004\b_N\t\t\u0011#\u0001q\r\u001d\u00112#!A\t\u0002EDQ!\u0017\u0007\u0005\u0002UDqA\u001e\u0007\u0012\u0002\u0013\u0005q\u000f\u0003\u0005\u0002\u00061\t\n\u0011\"\u0001x\u0011%\t9\u0001DI\u0001\n\u0003\tI\u0001C\u0005\u0002\u000e1\t\n\u0011\"\u0001\u0002\u0010\t\u0001B)Z:lK^\u0004\u0016M]1nKR,'o\u001d\u0006\u0003)U\ta\u0001Z3tW\u0016<(B\u0001\f\u0018\u0003\u0015!\u0018m]6t\u0015\tA\u0012$A\u0003tK*$\u0017MC\u0001\u001b\u0003\u0011\u0019w\u000eZ3\u0004\u0001M\u0019\u0001!\b\u0016\u0011\u0005yAS\"A\u0010\u000b\u0005\u0001\n\u0013\u0001\u00022bg\u0016T!AI\u0012\u0002\u0013A\f'/Y7fi\u0016\u0014(B\u0001\u0013&\u0003\u0015iw\u000eZ3m\u0015\tAbEC\u0001(\u0003\ry'oZ\u0005\u0003S}\u0011\u0011&T;mi&\u0004H.\u001a)eMN{WO]2f\u001bVdG/\u001b9mK>+H\u000f];u!\u0006\u0014\u0018-\\3uKJ\u001c\bCA\u00161\u001b\u0005a#BA\u0017/\u0003\u0015\u0001\u0018M]1n\u0015\tyS#\u0001\u0004d_6lwN\\\u0005\u0003c1\u0012qcV5uQB\u000bw-Z*fY\u0016\u001cG/[8o!\u0006\u0014\u0018-\\:\u0002\u00195Lg\u000e\u00165sKNDw\u000e\u001c3\u0016\u0003Q\u0002\"!\u000e\u001d\u000e\u0003YR\u0011aN\u0001\u0006g\u000e\fG.Y\u0005\u0003sY\u0012a\u0001R8vE2,\u0017!D7j]RC'/Z:i_2$\u0007%\u0001\u0007nCb$\u0006N]3tQ>dG-A\u0007nCb$\u0006N]3tQ>dG\rI\u0001\u000ea\u0006<WmU3mK\u000e$\u0018n\u001c8\u0016\u0003}\u00022\u0001Q#H\u001b\u0005\t%B\u0001\"D\u0003\u001diW\u000f^1cY\u0016T!\u0001\u0012\u001c\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0002G\u0003\n\u00191+\u001a;\u0011\u0005!kU\"A%\u000b\u0005)[\u0015\u0001\u00029bO\u0016T!\u0001T\u0012\u0002\u0007A$g-\u0003\u0002O\u0013\nI\u0001+Y4f%\u0006tw-Z\u0001\u000fa\u0006<WmU3mK\u000e$\u0018n\u001c8!\u0003\u0019\tgn\u001a7fgV\t!\u000b\u0005\u0003A'V#\u0014B\u0001+B\u0005\u001dA\u0015m\u001d5NCB\u0004\"!\u000e,\n\u0005]3$aA%oi\u00069\u0011M\\4mKN\u0004\u0013A\u0002\u001fj]&$h\bF\u0003\\;z{\u0006\r\u0005\u0002]\u00015\t1\u0003C\u00043\u0013A\u0005\t\u0019\u0001\u001b\t\u000fmJ\u0001\u0013!a\u0001i!9Q(\u0003I\u0001\u0002\u0004y\u0004b\u0002)\n!\u0003\u0005\rAU\u0001\ng\u0016$\u0018I\\4mKN$\"AU2\t\u000bAS\u0001\u0019\u00013\u0011\t\u0015dW\u000b\u000e\b\u0003M*\u0004\"a\u001a\u001c\u000e\u0003!T!![\u000e\u0002\rq\u0012xn\u001c;?\u0013\tYg'\u0001\u0004Qe\u0016$WMZ\u0005\u0003[:\u00141!T1q\u0015\tYg'\u0001\tEKN\\Wm\u001e)be\u0006lW\r^3sgB\u0011A\fD\n\u0003\u0019I\u0004\"!N:\n\u0005Q4$AB!osJ+g\rF\u0001q\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%cU\t\u0001P\u000b\u00025s.\n!\u0010E\u0002|\u0003\u0003i\u0011\u0001 \u0006\u0003{z\f\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005}4\u0014AC1o]>$\u0018\r^5p]&\u0019\u00111\u0001?\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$HEM\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000fJ\u001a\u0016\u0005\u0005-!FA z\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%iU\u0011\u0011\u0011\u0003\u0016\u0003%f\u0004")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/deskew/DeskewParameters.class */
public class DeskewParameters extends MultiplePdfSourceMultipleOutputParameters implements WithPageSelectionParams {
    private final double minThreshold;
    private final double maxThreshold;
    private final Set<PageRange> pageSelection;
    private final HashMap<Object, Object> angles;

    @Override // code.sejda.tasks.common.param.WithPageSelectionParams
    public scala.collection.immutable.Set<Object> getPages(final int totalNumberOfPage) {
        return getPages(totalNumberOfPage);
    }

    @Override // code.sejda.tasks.common.param.WithPageSelectionParams
    public boolean addPageRange(final PageRange range) {
        return addPageRange(range);
    }

    public double minThreshold() {
        return this.minThreshold;
    }

    public DeskewParameters(final double minThreshold, final double maxThreshold, final Set<PageRange> pageSelection, final HashMap<Object, Object> angles) {
        this.minThreshold = minThreshold;
        this.maxThreshold = maxThreshold;
        this.pageSelection = pageSelection;
        this.angles = angles;
        WithPageSelectionParams.$init$(this);
    }

    public double maxThreshold() {
        return this.maxThreshold;
    }

    @Override // code.sejda.tasks.common.param.WithPageSelectionParams
    public Set<PageRange> pageSelection() {
        return this.pageSelection;
    }

    public HashMap<Object, Object> angles() {
        return this.angles;
    }

    public HashMap<Object, Object> setAngles(final Map<Object, Object> angles) {
        return angles().addAll(angles);
    }
}
