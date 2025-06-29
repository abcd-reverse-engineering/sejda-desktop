package defpackage;

import code.limits.DesktopPlanLimits$;
import code.limits.QuotaUpdate;
import code.limits.Upgrade;
import code.limits.UpgradeRequiredException;
import code.model.TaskContext$;
import code.model.TaskResult;
import code.model.TaskResult$;
import code.model.TaskStatus$;
import code.service.InJvmTaskExecutor;
import code.service.TaskParamsProvider;
import code.util.Loggable;
import code.util.pdf.PdfDetails;
import code.util.pdf.PdfLibraryUtils$;
import code.util.ratelimit.DiskPersistentMeter;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import defpackage.DesktopLauncher;
import java.io.File;
import net.liftweb.json.JsonAST;
import net.liftweb.json.JsonAST$JValue$;
import net.liftweb.json.package$;
import org.sejda.core.notification.context.GlobalNotificationContext;
import org.sejda.core.service.DefaultTaskExecutionService;
import org.sejda.model.output.TaskOutput;
import org.sejda.model.parameter.base.TaskParameters;
import org.slf4j.Logger;
import scala.App;
import scala.Enumeration;
import scala.Function0;
import scala.Option;
import scala.Option$;
import scala.Predef$;
import scala.Some;
import scala.collection.ArrayOps$;
import scala.collection.IterableOnceOps;
import scala.collection.JavaConverters$;
import scala.collection.StringOps$;
import scala.collection.mutable.ListBuffer;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;

/* compiled from: DesktopLauncher.scala */
/* loaded from: com.sejda.desktop-launcher-1.0.0.jar:DesktopLauncher$.class */
public final class DesktopLauncher$ implements App, InJvmTaskExecutor {
    public static final DesktopLauncher$ MODULE$ = new DesktopLauncher$();
    private static Object startTime;
    private static DefaultTaskExecutionService tasksService;
    private static long code$service$InJvmTaskExecutor$$originalSize;
    private static transient Logger logger;
    private static long executionStart;
    private static String[] scala$App$$_args;
    private static ListBuffer<Function0<BoxedUnit>> scala$App$$initCode;
    private static volatile transient boolean bitmap$trans$0;

    static {
        App.$init$(MODULE$);
        Loggable.$init$(MODULE$);
        InJvmTaskExecutor.$init$((InJvmTaskExecutor) MODULE$);
        MODULE$.delayedInit(new DesktopLauncher.body(MODULE$));
        Statics.releaseFence();
    }

    @Override // code.service.InJvmTaskExecutor
    public TaskParamsProvider taskParser() {
        return taskParser();
    }

    @Override // code.service.InJvmTaskExecutor
    public void boot() {
        boot();
    }

    @Override // code.service.InJvmTaskExecutor
    public File processResult(final TaskOutput in) {
        return processResult(in);
    }

    @Override // code.service.InJvmTaskExecutor
    public Option<Upgrade> determineIfUpgradeRequired(final TaskParameters params, final File result) {
        return determineIfUpgradeRequired(params, result);
    }

    @Override // code.service.InJvmTaskExecutor
    public Option<PdfDetails> resultPdfDetails(final File result) {
        return resultPdfDetails(result);
    }

    @Override // code.service.InJvmTaskExecutor
    public Option<String> determineRemark(final TaskParameters params, final File result) {
        return determineRemark(params, result);
    }

    @Override // code.service.InJvmTaskExecutor
    public void launch() {
        launch();
    }

    public final String[] args() {
        return App.args$(this);
    }

    public void delayedInit(final Function0<BoxedUnit> body) {
        App.delayedInit$(this, body);
    }

    public final void main(final String[] args) {
        App.main$(this, args);
    }

    @Override // code.service.InJvmTaskExecutor
    public DefaultTaskExecutionService tasksService() {
        return tasksService;
    }

    @Override // code.service.InJvmTaskExecutor
    public long code$service$InJvmTaskExecutor$$originalSize() {
        return code$service$InJvmTaskExecutor$$originalSize;
    }

    @Override // code.service.InJvmTaskExecutor
    public void code$service$InJvmTaskExecutor$$originalSize_$eq(final long x$1) {
        code$service$InJvmTaskExecutor$$originalSize = x$1;
    }

    @Override // code.service.InJvmTaskExecutor
    public void code$service$InJvmTaskExecutor$_setter_$tasksService_$eq(final DefaultTaskExecutionService x$1) {
        tasksService = x$1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v7 */
    private Logger logger$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (!bitmap$trans$0) {
                logger = logger();
                r0 = 1;
                bitmap$trans$0 = true;
            }
        }
        return logger;
    }

    @Override // code.util.Loggable
    public Logger logger() {
        return !bitmap$trans$0 ? logger$lzycompute() : logger;
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

    private DesktopLauncher$() {
    }

    public Object startTime() {
        return startTime;
    }

    public static final /* synthetic */ long $anonfun$startTime$1(final String s) {
        return System.currentTimeMillis() - StringOps$.MODULE$.toLong$extension(Predef$.MODULE$.augmentString(s));
    }

    public final void delayedEndpoint$DesktopLauncher$1() {
        startTime = Option$.MODULE$.apply(System.getProperty("sejda.jvm.startup")).map(s -> {
            return BoxesRunTime.boxToLong($anonfun$startTime$1(s));
        }).getOrElse(() -> {
            return 0;
        });
        logger().info(new StringBuilder(48).append("JVM startup time: ").append(startTime()).append(" ms, vendor: ").append(System.getProperty("java.vendor")).append(" ").append(System.getProperty("java.version")).append(", max memory: ").append((Runtime.getRuntime().maxMemory() / 1000) / 1000).append("MB").toString());
        PdfLibraryUtils$.MODULE$.isSejdaDesktop_$eq(true);
        Some someHeadOption$extension = ArrayOps$.MODULE$.headOption$extension(Predef$.MODULE$.refArrayOps(args()));
        if (someHeadOption$extension instanceof Some) {
            String argsFile = (String) someHeadOption$extension.value();
            ((IterableOnceOps) JavaConverters$.MODULE$.asScalaBufferConverter(Files.readLines(new File(argsFile), Charsets.UTF_8)).asScala()).foreach(line -> {
                int idx = line.indexOf("=");
                if (idx <= 0) {
                    return BoxedUnit.UNIT;
                }
                String name = line.substring(0, idx);
                String value = line.substring(idx + 1);
                return System.setProperty(name, value);
            });
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
        } else {
            BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
        }
        GlobalNotificationContext.getContext().addListener(new DesktopLauncher$$anon$1());
        launch();
    }

    @Override // code.service.InJvmTaskExecutor
    public int taskContext() {
        return TaskContext$.MODULE$.desktop();
    }

    @Override // code.service.InJvmTaskExecutor
    public void beforeExecute(final JsonAST.JValue json, final TaskParameters params) {
        beforeExecute(json, params);
        Some someOf = DesktopPlanLimits$.MODULE$.of(json, params);
        if (someOf instanceof Some) {
            Upgrade upgrade = (Upgrade) someOf.value();
            File out = new File(System.getProperty("task.out"));
            UpgradeRequiredException ex = new UpgradeRequiredException(upgrade);
            TaskResult taskResult = TaskResult$.MODULE$.failureKnownReason(new StringBuilder(18).append("Upgrade required: ").append(upgrade.reason()).toString(), ex, TaskResult$.MODULE$.failureKnownReason$default$3(), TaskResult$.MODULE$.failureKnownReason$default$4());
            String s = package$.MODULE$.compactRender(JsonAST$JValue$.MODULE$.MergeSyntax(taskResult.toJson()).merge(upgrade.toJsonExt(), JsonAST$JValue$.MODULE$.jjj()));
            Files.write(s, out, Charsets.UTF_8);
            logger().error(ex.getMessage(), ex);
            throw ex;
        }
        BoxedUnit boxedUnit = BoxedUnit.UNIT;
    }

    @Override // code.service.InJvmTaskExecutor
    public void afterExecute(final TaskResult result) {
        Enumeration.Value valueStatus = result.status();
        Enumeration.Value valueFailed = TaskStatus$.MODULE$.Failed();
        if (valueStatus != null ? valueStatus.equals(valueFailed) : valueFailed == null) {
            DesktopPlanLimits$.MODULE$.meters().foreach(meter -> {
                return BoxesRunTime.boxToInteger(meter.untick());
            });
        }
        try {
            DiskPersistentMeter meter2 = (DiskPersistentMeter) DesktopPlanLimits$.MODULE$.meters().head();
            result.quotaUpdate_$eq(new Some(new QuotaUpdate(meter2.remainingRequests(), meter2.untilResetSeconds())));
        } catch (Throwable th) {
        }
    }

    @Override // code.service.InJvmTaskExecutor
    public JsonAST.JValue toResultJson(final TaskResult result) {
        return JsonAST$JValue$.MODULE$.MergeSyntax(result.toJson()).merge(result.toJsonExt(), JsonAST$JValue$.MODULE$.jjj());
    }
}
