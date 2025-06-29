package code.util.pdf;

import org.sejda.model.parameter.base.TaskParameters;
import scala.reflect.ScalaSignature;

/* compiled from: PdfLibraryUtils.scala */
@ScalaSignature(bytes = "\u0006\u0005I;Qa\u0004\t\t\u0002]1Q!\u0007\t\t\u0002iAQ!J\u0001\u0005\u0002\u0019BqaJ\u0001A\u0002\u0013\u0005\u0001\u0006C\u0004-\u0003\u0001\u0007I\u0011A\u0017\t\rM\n\u0001\u0015)\u0003*\u0011\u001d!\u0014\u00011A\u0005\u0002!Bq!N\u0001A\u0002\u0013\u0005a\u0007\u0003\u00049\u0003\u0001\u0006K!\u000b\u0005\u0006s\u0005!\tA\u000f\u0005\tw\u0005A)\u0019!C\u0001y!)Q(\u0001C\u0001}!)q*\u0001C\u0005u!)\u0001+\u0001C\u0005u!)\u0011+\u0001C\u0005u\u0005y\u0001\u000b\u001a4MS\n\u0014\u0018M]=Vi&d7O\u0003\u0002\u0012%\u0005\u0019\u0001\u000f\u001a4\u000b\u0005M!\u0012\u0001B;uS2T\u0011!F\u0001\u0005G>$Wm\u0001\u0001\u0011\u0005a\tQ\"\u0001\t\u0003\u001fA#g\rT5ce\u0006\u0014\u00180\u0016;jYN\u001c2!A\u000e\"!\tar$D\u0001\u001e\u0015\u0005q\u0012!B:dC2\f\u0017B\u0001\u0011\u001e\u0005\u0019\te.\u001f*fMB\u0011!eI\u0007\u0002%%\u0011AE\u0005\u0002\t\u0019><w-\u00192mK\u00061A(\u001b8jiz\"\u0012aF\u0001\u000fSN\u001cVM\u001b3b\t\u0016\u001c8\u000e^8q+\u0005I\u0003C\u0001\u000f+\u0013\tYSDA\u0004C_>dW-\u00198\u0002%%\u001c8+\u001a6eC\u0012+7o\u001b;pa~#S-\u001d\u000b\u0003]E\u0002\"\u0001H\u0018\n\u0005Aj\"\u0001B+oSRDqA\r\u0003\u0002\u0002\u0003\u0007\u0011&A\u0002yIE\nq\"[:TK*$\u0017\rR3tWR|\u0007\u000fI\u0001\nSN$Vm\u001d;j]\u001e\fQ\"[:UKN$\u0018N\\4`I\u0015\fHC\u0001\u00188\u0011\u001d\u0011t!!AA\u0002%\n!\"[:UKN$\u0018N\\4!\u0003%\u0019wN\u001c4jOV\u0014X\rF\u0001/\u00039\u0019wN\u001c4jOV\u0014XmX8oG\u0016,\u0012AL\u0001\u0011G>tg-[4ve\u0016\u0004VM\u001d+bg.$\"AL \t\u000b\u0001[\u0001\u0019A!\u0002\rA\f'/Y7t!\t\u0011U*D\u0001D\u0015\t!U)\u0001\u0003cCN,'B\u0001$H\u0003%\u0001\u0018M]1nKR,'O\u0003\u0002I\u0013\u0006)Qn\u001c3fY*\u0011!jS\u0001\u0006g\u0016TG-\u0019\u0006\u0002\u0019\u0006\u0019qN]4\n\u00059\u001b%A\u0004+bg.\u0004\u0016M]1nKR,'o]\u0001\u000fG>tg-[4ve\u0016\u001cVM\u001b3b\u0003A\u0019wN\u001c4jOV\u0014X-S7bO\u0016Lu*\u0001\u0007d_:4\u0017nZ;sK*#7\u000e")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/pdf/PdfLibraryUtils.class */
public final class PdfLibraryUtils {
    public static void configurePerTask(final TaskParameters params) {
        PdfLibraryUtils$.MODULE$.configurePerTask(params);
    }

    public static void configure_once() {
        PdfLibraryUtils$.MODULE$.configure_once();
    }

    public static void configure() {
        PdfLibraryUtils$.MODULE$.configure();
    }

    public static boolean isTesting() {
        return PdfLibraryUtils$.MODULE$.isTesting();
    }

    public static boolean isSejdaDesktop() {
        return PdfLibraryUtils$.MODULE$.isSejdaDesktop();
    }
}
