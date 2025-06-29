package code.limits;

import code.util.ratelimit.DiskPersistentMeter;
import net.liftweb.json.JsonAST;
import org.sejda.model.parameter.base.TaskParameters;
import scala.Option;
import scala.collection.immutable.List;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;

/* compiled from: DesktopPlanLimits.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0001<Q!\u0002\u0004\t\u0002-1Q!\u0004\u0004\t\u00029AQAH\u0001\u0005\u0002}A\u0001\u0002I\u0001\t\u0006\u0004%\t!\t\u0005\u0006a\u0005!\t!M\u0001\u0012\t\u0016\u001c8\u000e^8q!2\fg\u000eT5nSR\u001c(BA\u0004\t\u0003\u0019a\u0017.\\5ug*\t\u0011\"\u0001\u0003d_\u0012,7\u0001\u0001\t\u0003\u0019\u0005i\u0011A\u0002\u0002\u0012\t\u0016\u001c8\u000e^8q!2\fg\u000eT5nSR\u001c8\u0003B\u0001\u0010+a\u0001\"\u0001E\n\u000e\u0003EQ\u0011AE\u0001\u0006g\u000e\fG.Y\u0005\u0003)E\u0011a!\u00118z%\u00164\u0007C\u0001\u0007\u0017\u0013\t9bA\u0001\u0006QY\u0006tG*[7jiN\u0004\"!\u0007\u000f\u000e\u0003iQ!a\u0007\u0005\u0002\tU$\u0018\u000e\\\u0005\u0003;i\u0011\u0001\u0002T8hO\u0006\u0014G.Z\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003-\ta!\\3uKJ\u001cX#\u0001\u0012\u0011\u0007\rB#&D\u0001%\u0015\t)c%A\u0005j[6,H/\u00192mK*\u0011q%E\u0001\u000bG>dG.Z2uS>t\u0017BA\u0015%\u0005\u0011a\u0015n\u001d;\u0011\u0005-rS\"\u0001\u0017\u000b\u00055R\u0012!\u0003:bi\u0016d\u0017.\\5u\u0013\tyCFA\nESN\\\u0007+\u001a:tSN$XM\u001c;NKR,'/\u0001\u0002pMR\u0019!\u0007\u000f)\u0011\u0007A\u0019T'\u0003\u00025#\t1q\n\u001d;j_:\u0004\"\u0001\u0004\u001c\n\u0005]2!aB+qOJ\fG-\u001a\u0005\u0006s\u0011\u0001\rAO\u0001\u0007g>,(oY3\u0011\u0005mjeB\u0001\u001fK\u001d\titI\u0004\u0002?\t:\u0011qHQ\u0007\u0002\u0001*\u0011\u0011IC\u0001\u0007yI|w\u000e\u001e \n\u0003\r\u000b1A\\3u\u0013\t)e)A\u0004mS\u001a$x/\u001a2\u000b\u0003\rK!\u0001S%\u0002\t)\u001cxN\u001c\u0006\u0003\u000b\u001aK!a\u0013'\u0002\u000fA\f7m[1hK*\u0011\u0001*S\u0005\u0003\u001d>\u0013aA\u0013,bYV,'BA&M\u0011\u0015\tF\u00011\u0001S\u0003\u0019\u0001\u0018M]1ngB\u00111KX\u0007\u0002)*\u0011QKV\u0001\u0005E\u0006\u001cXM\u0003\u0002X1\u0006I\u0001/\u0019:b[\u0016$XM\u001d\u0006\u00033j\u000bQ!\\8eK2T!a\u0017/\u0002\u000bM,'\u000eZ1\u000b\u0003u\u000b1a\u001c:h\u0013\tyFK\u0001\bUCN\\\u0007+\u0019:b[\u0016$XM]:")
/* loaded from: com.sejda.desktop-launcher-1.0.0.jar:code/limits/DesktopPlanLimits.class */
public final class DesktopPlanLimits {
    public static Option<Upgrade> of(final JsonAST.JValue source, final TaskParameters params) {
        return DesktopPlanLimits$.MODULE$.of(source, params);
    }

    public static List<DiskPersistentMeter> meters() {
        return DesktopPlanLimits$.MODULE$.meters();
    }

    public static boolean isLargeFileCount_ForRenameByText(final TaskParameters params) {
        return DesktopPlanLimits$.MODULE$.isLargeFileCount_ForRenameByText(params);
    }

    public static Seq<Object> pageCounts(final TaskParameters params) {
        return DesktopPlanLimits$.MODULE$.pageCounts(params);
    }

    public static double INCREASE_DUE_TO_ENCRYPTION() {
        return DesktopPlanLimits$.MODULE$.INCREASE_DUE_TO_ENCRYPTION();
    }

    public static int MB() {
        return DesktopPlanLimits$.MODULE$.MB();
    }
}
