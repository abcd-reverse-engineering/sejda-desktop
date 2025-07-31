//
// Source code recreated by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

//decompiled from DesktopLauncher.class
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
import scala.runtime.BoxedUnit;

@ScalaSignature(
    bytes = "\u0006\u00055<Q!\u0003\u0006\t\u000251Qa\u0004\u0006\t\u0002AAQ\u0001K\u0001\u0005\u0002%BqAK\u0001C\u0002\u0013\u00051\u0006\u0003\u00040\u0003\u0001\u0006I\u0001\f\u0005\u0006a\u0005!\t%\r\u0005\u0006k\u0005!\tE\u000e\u0005\u0006C\u0006!\tE\u0019\u0005\u0006U\u0006!\te[\u0001\u0010\t\u0016\u001c8\u000e^8q\u0019\u0006,hn\u00195fe*\t1\"A\u0004=K6\u0004H/\u001f \u0004\u0001A\u0011a\"A\u0007\u0002\u0015\tyA)Z:li>\u0004H*Y;oG\",'oE\u0003\u0002#]Q\"\u0005\u0005\u0002\u0013+5\t1CC\u0001\u0015\u0003\u0015\u00198-\u00197b\u0013\t12C\u0001\u0004B]f\u0014VM\u001a\t\u0003%aI!!G\n\u0003\u0007\u0005\u0003\b\u000f\u0005\u0002\u001cA5\tAD\u0003\u0002\u001e=\u000591/\u001a:wS\u000e,'\"A\u0010\u0002\t\r|G-Z\u0005\u0003Cq\u0011\u0011#\u00138Km6$\u0016m]6Fq\u0016\u001cW\u000f^8s!\t\u0019c%D\u0001%\u0015\t)c$\u0001\u0003vi&d\u0017BA\u0014%\u0005!aunZ4bE2,\u0017A\u0002\u001fj]&$h\bF\u0001\u000e\u0003%\u0019H/\u0019:u)&lW-F\u0001-!\t\u0011R&\u0003\u0002/'\t1\u0011I\\=WC2\f!b\u001d;beR$\u0016.\\3!\u0003-!\u0018m]6D_:$X\r\u001f;\u0016\u0003I\u0002\"AE\u001a\n\u0005Q\u001a\"aA%oi\u0006i!-\u001a4pe\u0016,\u00050Z2vi\u0016$2a\u000e\u001eR!\t\u0011\u0002(\u0003\u0002:'\t!QK\\5u\u0011\u0015Yd\u00011\u0001=\u0003\u0011Q7o\u001c8\u0011\u0005ureB\u0001 L\u001d\ty\u0014J\u0004\u0002A\r:\u0011\u0011\tR\u0007\u0002\u0005*\u00111\tD\u0001\u0007yI|w\u000e\u001e \n\u0003\u0015\u000b1A\\3u\u0013\t9\u0005*A\u0004mS\u001a$x/\u001a2\u000b\u0003\u0015K!a\u000f&\u000b\u0005\u001dC\u0015B\u0001'N\u0003\u001d\u0001\u0018mY6bO\u0016T!a\u000f&\n\u0005=\u0003&A\u0002&WC2,XM\u0003\u0002M\u001b\")!K\u0002a\u0001'\u00061\u0001/\u0019:b[N\u0004\"\u0001V0\u000e\u0003US!AV,\u0002\t\t\f7/\u001a\u0006\u00031f\u000b\u0011\u0002]1sC6,G/\u001a:\u000b\u0005i[\u0016!B7pI\u0016d'B\u0001/^\u0003\u0015\u0019XM\u001b3b\u0015\u0005q\u0016aA8sO&\u0011\u0001-\u0016\u0002\u000f)\u0006\u001c8\u000eU1sC6,G/\u001a:t\u00031\tg\r^3s\u000bb,7-\u001e;f)\t94\rC\u0003e\u000f\u0001\u0007Q-\u0001\u0004sKN,H\u000e\u001e\t\u0003M\"l\u0011a\u001a\u0006\u00035zI!![4\u0003\u0015Q\u000b7o\u001b*fgVdG/\u0001\u0007u_J+7/\u001e7u\u0015N|g\u000e\u0006\u0002=Y\")A\r\u0003a\u0001K\u0002"
)
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

    /** @deprecated */
    public static void delayedInit(final Function0<BoxedUnit> body) {
        DesktopLauncher$.MODULE$.delayedInit(body);
    }

    public static long executionStart() {
        return DesktopLauncher$.MODULE$.executionStart();
    }
}

//decompiled from DesktopLauncher$.class
import code.limits.QuotaUpdate;
import code.limits.Upgrade;
import code.limits.UpgradeRequiredException;
import code.model.TaskResult;
import code.model.TaskContext.;
import code.service.InJvmTaskExecutor;
import code.service.TaskParamsProvider;
import code.util.Loggable;
import code.util.pdf.PdfDetails;
import code.util.ratelimit.DiskPersistentMeter;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import java.io.File;
import java.lang.invoke.SerializedLambda;
import net.liftweb.json.JsonAST;
import org.sejda.core.notification.context.GlobalNotificationContext;
import org.sejda.core.service.DefaultTaskExecutionService;
import org.sejda.model.notification.EventListener;
import org.sejda.model.notification.event.AbstractNotificationEvent;
import org.sejda.model.notification.event.PercentageOfWorkDoneChangedEvent;
import org.sejda.model.output.TaskOutput;
import org.sejda.model.parameter.base.TaskParameters;
import org.slf4j.Logger;
import scala.App;
import scala.Enumeration;
import scala.Function0;
import scala.Option;
import scala.Some;
import scala.collection.IterableOnceOps;
import scala.collection.mutable.ListBuffer;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;
import scala.runtime.java8.JFunction0;

public final class DesktopLauncher$ implements App, InJvmTaskExecutor {
    public static final DesktopLauncher$ MODULE$ = new DesktopLauncher$();
    private static Object startTime;
    private static DefaultTaskExecutionService tasksService;
    private static long code$service$InJvmTaskExecutor$$originalSize;
    private static transient Logger logger;
    private static long executionStart;
    private static String[] scala$App$$_args;
    private static ListBuffer<Function0<BoxedUnit>> scala$App$$initCode;
    private static transient volatile boolean bitmap$trans$0;

    static {
        App.$init$(MODULE$);
        Loggable.$init$(MODULE$);
        InJvmTaskExecutor.$init$(MODULE$);
        MODULE$.delayedInit(new DesktopLauncher$delayedInit$body(MODULE$));
        Statics.releaseFence();
    }

    public TaskParamsProvider taskParser() {
        return InJvmTaskExecutor.taskParser$(this);
    }

    public void boot() {
        InJvmTaskExecutor.boot$(this);
    }

    public File processResult(final TaskOutput in) {
        return InJvmTaskExecutor.processResult$(this, in);
    }

    public Option<Upgrade> determineIfUpgradeRequired(final TaskParameters params, final File result) {
        return InJvmTaskExecutor.determineIfUpgradeRequired$(this, params, result);
    }

    public Option<PdfDetails> resultPdfDetails(final File result) {
        return InJvmTaskExecutor.resultPdfDetails$(this, result);
    }

    public Option<String> determineRemark(final TaskParameters params, final File result) {
        return InJvmTaskExecutor.determineRemark$(this, params, result);
    }

    public void launch() {
        InJvmTaskExecutor.launch$(this);
    }

    public final String[] args() {
        return App.args$(this);
    }

    /** @deprecated */
    public void delayedInit(final Function0<BoxedUnit> body) {
        App.delayedInit$(this, body);
    }

    public final void main(final String[] args) {
        App.main$(this, args);
    }

    public DefaultTaskExecutionService tasksService() {
        return tasksService;
    }

    public long code$service$InJvmTaskExecutor$$originalSize() {
        return code$service$InJvmTaskExecutor$$originalSize;
    }

    public void code$service$InJvmTaskExecutor$$originalSize_$eq(final long x$1) {
        code$service$InJvmTaskExecutor$$originalSize = x$1;
    }

    public void code$service$InJvmTaskExecutor$_setter_$tasksService_$eq(final DefaultTaskExecutionService x$1) {
        tasksService = x$1;
    }

    private Logger logger$lzycompute() {
        synchronized(this){}

        try {
            if (!bitmap$trans$0) {
                logger = Loggable.logger$(this);
                bitmap$trans$0 = true;
            }
        } catch (Throwable var3) {
            throw var3;
        }

        return logger;
    }

    public Logger logger() {
        return !bitmap$trans$0 ? this.logger$lzycompute() : logger;
    }

    public final long executionStart() {
        return executionStart;
    }

    public String[] scala$App$$_args() {
        return scala$App$$_args;
    }

    public void scala$App$$_args_$eq(final String[] x$1) {
        scala$App$$_args = x$1;
    }

    public ListBuffer<Function0<BoxedUnit>> scala$App$$initCode() {
        return scala$App$$initCode;
    }

    public final void scala$App$_setter_$executionStart_$eq(final long x$1) {
        executionStart = x$1;
    }

    public final void scala$App$_setter_$scala$App$$initCode_$eq(final ListBuffer<Function0<BoxedUnit>> x$1) {
        scala$App$$initCode = x$1;
    }

    public Object startTime() {
        return startTime;
    }

    public int taskContext() {
        return .MODULE$.desktop();
    }

    public void beforeExecute(final JsonAST.JValue json, final TaskParameters params) {
        InJvmTaskExecutor.beforeExecute$(this, json, params);
        Option var4 = code.limits.DesktopPlanLimits..MODULE$.of(json, params);
        if (var4 instanceof Some) {
            Some var5 = (Some)var4;
            Upgrade upgrade = (Upgrade)var5.value();
            File out = new File(System.getProperty("task.out"));
            UpgradeRequiredException ex = new UpgradeRequiredException(upgrade);
            TaskResult taskResult = code.model.TaskResult..MODULE$.failureKnownReason((new StringBuilder(18)).append("Upgrade required: ").append(upgrade.reason()).toString(), ex, code.model.TaskResult..MODULE$.failureKnownReason$default$3(), code.model.TaskResult..MODULE$.failureKnownReason$default$4());
            JsonAST.JValue json = net.liftweb.json.JsonAST.JValue..MODULE$.MergeSyntax(taskResult.toJson()).merge(upgrade.toJsonExt(), net.liftweb.json.JsonAST.JValue..MODULE$.jjj());
            String s = net.liftweb.json.package..MODULE$.compactRender(json);
            Files.write(s, out, Charsets.UTF_8);
            this.logger().error(ex.getMessage(), ex);
            throw ex;
        } else {
            BoxedUnit var10000 = BoxedUnit.UNIT;
        }
    }

    public void afterExecute(final TaskResult result) {
        label21: {
            Enumeration.Value var10000 = result.status();
            Enumeration.Value var2 = code.model.TaskStatus..MODULE$.Failed();
            if (var10000 == null) {
                if (var2 != null) {
                    break label21;
                }
            } else if (!var10000.equals(var2)) {
                break label21;
            }

            code.limits.DesktopPlanLimits..MODULE$.meters().foreach((meterx) -> BoxesRunTime.boxToInteger($anonfun$afterExecute$1(meterx)));
        }

        try {
            DiskPersistentMeter meter = (DiskPersistentMeter)code.limits.DesktopPlanLimits..MODULE$.meters().head();
            result.quotaUpdate_$eq(new Some(new QuotaUpdate(meter.remainingRequests(), (long)meter.untilResetSeconds())));
        } catch (Throwable var5) {
        }

    }

    public JsonAST.JValue toResultJson(final TaskResult result) {
        return net.liftweb.json.JsonAST.JValue..MODULE$.MergeSyntax(result.toJson()).merge(result.toJsonExt(), net.liftweb.json.JsonAST.JValue..MODULE$.jjj());
    }

    // $FF: synthetic method
    public static final long $anonfun$startTime$1(final String s) {
        return System.currentTimeMillis() - scala.collection.StringOps..MODULE$.toLong$extension(scala.Predef..MODULE$.augmentString(s));
    }

    // $FF: synthetic method
    public static final int $anonfun$afterExecute$1(final DiskPersistentMeter meter) {
        return meter.untick();
    }

    public final void delayedEndpoint$DesktopLauncher$1() {
        startTime = scala.Option..MODULE$.apply(System.getProperty("sejda.jvm.startup")).map((s) -> BoxesRunTime.boxToLong($anonfun$startTime$1(s))).getOrElse((JFunction0.mcI.sp)() -> 0);
        this.logger().info((new StringBuilder(48)).append("JVM startup time: ").append(this.startTime()).append(" ms, vendor: ").append(System.getProperty("java.vendor")).append(" ").append(System.getProperty("java.version")).append(", max memory: ").append(Runtime.getRuntime().maxMemory() / 1000L / 1000L).append("MB").toString());
        code.util.pdf.PdfLibraryUtils..MODULE$.isSejdaDesktop_$eq(true);
        Option var2 = scala.collection.ArrayOps..MODULE$.headOption$extension(scala.Predef..MODULE$.refArrayOps((Object[])this.args()));
        if (var2 instanceof Some) {
            Some var3 = (Some)var2;
            String argsFile = (String)var3.value();
            ((IterableOnceOps)scala.collection.JavaConverters..MODULE$.asScalaBufferConverter(Files.readLines(new File(argsFile), Charsets.UTF_8)).asScala()).foreach((line) -> {
                int idx = line.indexOf("=");
                if (idx > 0) {
                    String name = line.substring(0, idx);
                    String value = line.substring(idx + 1);
                    return System.setProperty(name, value);
                } else {
                    return BoxedUnit.UNIT;
                }
            });
            BoxedUnit var10000 = BoxedUnit.UNIT;
        } else {
            BoxedUnit var5 = BoxedUnit.UNIT;
        }

        GlobalNotificationContext.getContext().addListener(new EventListener<PercentageOfWorkDoneChangedEvent>() {
            private transient Logger logger;
            private transient volatile boolean bitmap$trans$0;

            private Logger logger$lzycompute() {
                synchronized(this){}

                try {
                    if (!this.bitmap$trans$0) {
                        this.logger = Loggable.logger$(this);
                        this.bitmap$trans$0 = true;
                    }
                } catch (Throwable var3) {
                    throw var3;
                }

                return this.logger;
            }

            public Logger logger() {
                return !this.bitmap$trans$0 ? this.logger$lzycompute() : this.logger;
            }

            public void onEvent(final PercentageOfWorkDoneChangedEvent e) {
                if (!e.isUndetermined()) {
                    this.logger().info((new StringBuilder(21)).append("Task progress: ").append(e.getPercentage().toPlainString()).append("% done").toString());
                }
            }

            // $FF: synthetic method
            // $FF: bridge method
            public void onEvent(final AbstractNotificationEvent event) {
                this.onEvent((PercentageOfWorkDoneChangedEvent)event);
            }

            public {
                Loggable.$init$(this);
            }
        });
        this.launch();
    }

    private DesktopLauncher$() {
    }

    // $FF: synthetic method
    private static Object $deserializeLambda$(SerializedLambda var0) {
        return Class.lambdaDeserialize<invokedynamic>(var0);
    }
}

//decompiled from DesktopLauncher$delayedInit$body.class
import scala.runtime.AbstractFunction0;
import scala.runtime.BoxedUnit;

public final class DesktopLauncher$delayedInit$body extends AbstractFunction0 {
    private final DesktopLauncher$ $outer;

    public final Object apply() {
        this.$outer.delayedEndpoint$DesktopLauncher$1();
        return BoxedUnit.UNIT;
    }

    public DesktopLauncher$delayedInit$body(final DesktopLauncher$ $outer) {
        if ($outer == null) {
            throw null;
        } else {
            this.$outer = $outer;
            super();
        }
    }
}
