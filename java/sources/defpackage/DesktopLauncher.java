package defpackage;

import code.limits.Upgrade;
import code.model.TaskResult;
import code.service.TaskParamsProvider;
import code.util.pdf.PdfDetails;
import java.io.File;
import net.liftweb.json.JsonAST;
import org.sejda.core.service.DefaultTaskExecutionService;
import org.sejda.model.output.TaskOutput;
import org.sejda.model.parameter.base.TaskParameters;
import scala.Function0;
import scala.Option;
import scala.reflect.ScalaSignature;
import scala.runtime.AbstractFunction0;
import scala.runtime.BoxedUnit;

/* compiled from: DesktopLauncher.scala */
@ScalaSignature(bytes = "\u0006\u00055<Q!\u0003\u0006\t\u000251Qa\u0004\u0006\t\u0002AAQ\u0001K\u0001\u0005\u0002%BqAK\u0001C\u0002\u0013\u00051\u0006\u0003\u00040\u0003\u0001\u0006I\u0001\f\u0005\u0006a\u0005!\t%\r\u0005\u0006k\u0005!\tE\u000e\u0005\u0006C\u0006!\tE\u0019\u0005\u0006U\u0006!\te[\u0001\u0010\t\u0016\u001c8\u000e^8q\u0019\u0006,hn\u00195fe*\t1\"A\u0004=K6\u0004H/\u001f \u0004\u0001A\u0011a\"A\u0007\u0002\u0015\tyA)Z:li>\u0004H*Y;oG\",'oE\u0003\u0002#]Q\"\u0005\u0005\u0002\u0013+5\t1CC\u0001\u0015\u0003\u0015\u00198-\u00197b\u0013\t12C\u0001\u0004B]f\u0014VM\u001a\t\u0003%aI!!G\n\u0003\u0007\u0005\u0003\b\u000f\u0005\u0002\u001cA5\tAD\u0003\u0002\u001e=\u000591/\u001a:wS\u000e,'\"A\u0010\u0002\t\r|G-Z\u0005\u0003Cq\u0011\u0011#\u00138Km6$\u0016m]6Fq\u0016\u001cW\u000f^8s!\t\u0019c%D\u0001%\u0015\t)c$\u0001\u0003vi&d\u0017BA\u0014%\u0005!aunZ4bE2,\u0017A\u0002\u001fj]&$h\bF\u0001\u000e\u0003%\u0019H/\u0019:u)&lW-F\u0001-!\t\u0011R&\u0003\u0002/'\t1\u0011I\\=WC2\f!b\u001d;beR$\u0016.\\3!\u0003-!\u0018m]6D_:$X\r\u001f;\u0016\u0003I\u0002\"AE\u001a\n\u0005Q\u001a\"aA%oi\u0006i!-\u001a4pe\u0016,\u00050Z2vi\u0016$2a\u000e\u001eR!\t\u0011\u0002(\u0003\u0002:'\t!QK\\5u\u0011\u0015Yd\u00011\u0001=\u0003\u0011Q7o\u001c8\u0011\u0005ureB\u0001 L\u001d\ty\u0014J\u0004\u0002A\r:\u0011\u0011\tR\u0007\u0002\u0005*\u00111\tD\u0001\u0007yI|w\u000e\u001e \n\u0003\u0015\u000b1A\\3u\u0013\t9\u0005*A\u0004mS\u001a$x/\u001a2\u000b\u0003\u0015K!a\u000f&\u000b\u0005\u001dC\u0015B\u0001'N\u0003\u001d\u0001\u0018mY6bO\u0016T!a\u000f&\n\u0005=\u0003&A\u0002&WC2,XM\u0003\u0002M\u001b\")!K\u0002a\u0001'\u00061\u0001/\u0019:b[N\u0004\"\u0001V0\u000e\u0003US!AV,\u0002\t\t\f7/\u001a\u0006\u00031f\u000b\u0011\u0002]1sC6,G/\u001a:\u000b\u0005i[\u0016!B7pI\u0016d'B\u0001/^\u0003\u0015\u0019XM\u001b3b\u0015\u0005q\u0016aA8sO&\u0011\u0001-\u0016\u0002\u000f)\u0006\u001c8\u000eU1sC6,G/\u001a:t\u00031\tg\r^3s\u000bb,7-\u001e;f)\t94\rC\u0003e\u000f\u0001\u0007Q-\u0001\u0004sKN,H\u000e\u001e\t\u0003M\"l\u0011a\u001a\u0006\u00035zI!![4\u0003\u0015Q\u000b7o\u001b*fgVdG/\u0001\u0007u_J+7/\u001e7u\u0015N|g\u000e\u0006\u0002=Y\")A\r\u0003a\u0001K\u0002")
/* loaded from: com.sejda.desktop-launcher-1.0.0.jar:DesktopLauncher.class */
public final class DesktopLauncher {
    public static JsonAST.JValue toResultJson(final TaskResult result) {
        return DesktopLauncher$.MODULE$.toResultJson(result);
    }

    public static void afterExecute(final TaskResult result) {
        DesktopLauncher$.MODULE$.afterExecute(result);
    }

    public static void beforeExecute(final JsonAST.JValue json, final TaskParameters params) {
        DesktopLauncher$.MODULE$.beforeExecute(json, params);
    }

    public static int taskContext() {
        return DesktopLauncher$.MODULE$.taskContext();
    }

    public static Object startTime() {
        return DesktopLauncher$.MODULE$.startTime();
    }

    public static void launch() {
        DesktopLauncher$.MODULE$.launch();
    }

    public static Option<String> determineRemark(final TaskParameters params, final File result) {
        return DesktopLauncher$.MODULE$.determineRemark(params, result);
    }

    public static Option<PdfDetails> resultPdfDetails(final File result) {
        return DesktopLauncher$.MODULE$.resultPdfDetails(result);
    }

    public static Option<Upgrade> determineIfUpgradeRequired(final TaskParameters params, final File result) {
        return DesktopLauncher$.MODULE$.determineIfUpgradeRequired(params, result);
    }

    public static File processResult(final TaskOutput in) {
        return DesktopLauncher$.MODULE$.processResult(in);
    }

    public static void boot() {
        DesktopLauncher$.MODULE$.boot();
    }

    public static TaskParamsProvider taskParser() {
        return DesktopLauncher$.MODULE$.taskParser();
    }

    public static DefaultTaskExecutionService tasksService() {
        return DesktopLauncher$.MODULE$.tasksService();
    }

    public static void main(final String[] args) {
        DesktopLauncher$.MODULE$.main(args);
    }

    public static void delayedInit(final Function0<BoxedUnit> body2) {
        DesktopLauncher$.MODULE$.delayedInit(body2);
    }

    public static long executionStart() {
        return DesktopLauncher$.MODULE$.executionStart();
    }

    /* compiled from: DesktopLauncher.scala */
    /* loaded from: com.sejda.desktop-launcher-1.0.0.jar:DesktopLauncher$delayedInit$body.class */
    public final class body extends AbstractFunction0 {
        private final DesktopLauncher$ $outer;

        public final Object apply() {
            this.$outer.delayedEndpoint$DesktopLauncher$1();
            return BoxedUnit.UNIT;
        }

        public body(final DesktopLauncher$ $outer) {
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
        }
    }
}
