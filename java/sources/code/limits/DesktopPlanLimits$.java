package code.limits;

import code.util.DurationHelpers$;
import code.util.Loggable;
import code.util.ratelimit.DiskPersistentMeter;
import code.util.ratelimit.MeterRateLimitReached;
import code.util.ratelimit.RateLimit;
import java.io.File;
import net.liftweb.json.JsonAST;
import org.sejda.model.input.TaskSource;
import org.sejda.model.parameter.base.TaskParameters;
import org.slf4j.Logger;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.collection.immutable.$colon;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.runtime.BoxesRunTime;

/* compiled from: DesktopPlanLimits.scala */
/* loaded from: com.sejda.desktop-launcher-1.0.0.jar:code/limits/DesktopPlanLimits$.class */
public final class DesktopPlanLimits$ implements PlanLimits, Loggable {
    public static final DesktopPlanLimits$ MODULE$ = new DesktopPlanLimits$();
    private static List<DiskPersistentMeter> meters;
    private static transient Logger logger;
    private static int MB;
    private static double INCREASE_DUE_TO_ENCRYPTION;
    private static volatile boolean bitmap$0;
    private static volatile transient boolean bitmap$trans$0;

    static {
        PlanLimits.$init$(MODULE$);
        Loggable.$init$(MODULE$);
    }

    @Override // code.limits.PlanLimits
    public boolean hasFileCountLargerThanFreeUserQuota(final TaskParameters params) {
        return hasFileCountLargerThanFreeUserQuota(params);
    }

    @Override // code.limits.PlanLimits
    public boolean hasFileLargerThanFreeUserQuota(final TaskParameters params, final long maxSizeForFreeInMb) {
        return hasFileLargerThanFreeUserQuota(params, maxSizeForFreeInMb);
    }

    @Override // code.limits.PlanLimits
    public boolean hasImageFileLargerThanFreeUserQuota(final TaskParameters params, final long maxSizeForFreeInMb) {
        return hasImageFileLargerThanFreeUserQuota(params, maxSizeForFreeInMb);
    }

    @Override // code.limits.PlanLimits
    public boolean hasFileWithLargePageCount(final TaskParameters params) {
        return hasFileWithLargePageCount(params);
    }

    @Override // code.limits.PlanLimits
    public boolean isBatchFiles(final TaskParameters params) {
        return isBatchFiles(params);
    }

    @Override // code.limits.PlanLimits
    public Option<String> isLargePageCount_TaskSpecific_Blocking(final TaskParameters params) {
        return isLargePageCount_TaskSpecific_Blocking(params);
    }

    @Override // code.limits.PlanLimits
    public Option<String> isLargePageCount_TaskSpecific(final TaskParameters params) {
        return isLargePageCount_TaskSpecific(params);
    }

    @Override // code.limits.PlanLimits
    public Seq<File> inputFiles(final TaskParameters params) {
        return inputFiles(params);
    }

    @Override // code.limits.PlanLimits
    public Seq<TaskSource<?>> sources(final TaskParameters params) {
        return sources(params);
    }

    @Override // code.limits.PlanLimits
    public Seq<Object> pageCounts(final TaskParameters params) {
        return pageCounts(params);
    }

    @Override // code.limits.PlanLimits
    public boolean isLargeFileCount_ForRenameByText(final TaskParameters params) {
        return isLargeFileCount_ForRenameByText(params);
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

    @Override // code.limits.PlanLimits
    public int MB() {
        return MB;
    }

    @Override // code.limits.PlanLimits
    public double INCREASE_DUE_TO_ENCRYPTION() {
        return INCREASE_DUE_TO_ENCRYPTION;
    }

    @Override // code.limits.PlanLimits
    public void code$limits$PlanLimits$_setter_$MB_$eq(final int x$1) {
        MB = x$1;
    }

    @Override // code.limits.PlanLimits
    public void code$limits$PlanLimits$_setter_$INCREASE_DUE_TO_ENCRYPTION_$eq(final double x$1) {
        INCREASE_DUE_TO_ENCRYPTION = x$1;
    }

    private DesktopPlanLimits$() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v6 */
    private List<DiskPersistentMeter> meters$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (!bitmap$0) {
                meters = new $colon.colon(new DiskPersistentMeter(".sdpl-system-config4", "", new RateLimit(3, DurationHelpers$.MODULE$.perDay())), Nil$.MODULE$);
                r0 = 1;
                bitmap$0 = true;
            }
        }
        return meters;
    }

    public List<DiskPersistentMeter> meters() {
        return !bitmap$0 ? meters$lzycompute() : meters;
    }

    public Option<Upgrade> of(final JsonAST.JValue source, final TaskParameters params) {
        if (LicenseInformation$.MODULE$.fromJson(source).key().isDefined()) {
            return None$.MODULE$;
        }
        if (hasFileLargerThanFreeUserQuota(params, 50L)) {
            return new Some(new Upgrade("file-size", Upgrade$.MODULE$.apply$default$2()));
        }
        if (hasImageFileLargerThanFreeUserQuota(params, 5L)) {
            return new Some(new Upgrade("image-file-size", Upgrade$.MODULE$.apply$default$2()));
        }
        if (hasFileCountLargerThanFreeUserQuota(params)) {
            return new Some(new Upgrade("files-per-task", Upgrade$.MODULE$.apply$default$2()));
        }
        if (hasFileWithLargePageCount(params)) {
            return new Some(new Upgrade("pages", Upgrade$.MODULE$.apply$default$2()));
        }
        if (isLargeFileCount_ForRenameByText(params)) {
            return new Some(new Upgrade("file-count-per-rename", Upgrade$.MODULE$.apply$default$2()));
        }
        if (isBatchFiles(params)) {
            return new Some(new Upgrade("batch-files", Upgrade$.MODULE$.apply$default$2()));
        }
        Some someIsLargePageCount_TaskSpecific = isLargePageCount_TaskSpecific(params);
        if (someIsLargePageCount_TaskSpecific instanceof Some) {
            String reason = (String) someIsLargePageCount_TaskSpecific.value();
            return new Some(new Upgrade(reason, Upgrade$.MODULE$.apply$default$2()));
        }
        try {
            meters().foreach(meter -> {
                return BoxesRunTime.boxToInteger($anonfun$of$1(meter));
            });
            UntrustedLocalClock$.MODULE$.assertAccurateAsync(UntrustedLocalClock$.MODULE$.assertAccurateAsync$default$1());
            return None$.MODULE$;
        } catch (MeterRateLimitReached e) {
            return new Some(new Upgrade("tasks-per-hour", new Some(BoxesRunTime.boxToLong(e.meter().untilResetSeconds()))));
        }
    }

    public static final /* synthetic */ int $anonfun$of$1(final DiskPersistentMeter meter) {
        return meter.tick(meter.tick$default$1());
    }
}
