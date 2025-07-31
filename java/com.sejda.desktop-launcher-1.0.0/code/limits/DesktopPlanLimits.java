//
// Source code recreated by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

//decompiled from DesktopPlanLimits.class
package code.limits;

import code.util.ratelimit.DiskPersistentMeter;
import net.liftweb.json.JsonAST;
import org.sejda.model.parameter.base.TaskParameters;
import scala.Option;
import scala.collection.immutable.List;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;

@ScalaSignature(
    bytes = "\u0006\u0005\u0001<Q!\u0002\u0004\t\u0002-1Q!\u0004\u0004\t\u00029AQAH\u0001\u0005\u0002}A\u0001\u0002I\u0001\t\u0006\u0004%\t!\t\u0005\u0006a\u0005!\t!M\u0001\u0012\t\u0016\u001c8\u000e^8q!2\fg\u000eT5nSR\u001c(BA\u0004\t\u0003\u0019a\u0017.\\5ug*\t\u0011\"\u0001\u0003d_\u0012,7\u0001\u0001\t\u0003\u0019\u0005i\u0011A\u0002\u0002\u0012\t\u0016\u001c8\u000e^8q!2\fg\u000eT5nSR\u001c8\u0003B\u0001\u0010+a\u0001\"\u0001E\n\u000e\u0003EQ\u0011AE\u0001\u0006g\u000e\fG.Y\u0005\u0003)E\u0011a!\u00118z%\u00164\u0007C\u0001\u0007\u0017\u0013\t9bA\u0001\u0006QY\u0006tG*[7jiN\u0004\"!\u0007\u000f\u000e\u0003iQ!a\u0007\u0005\u0002\tU$\u0018\u000e\\\u0005\u0003;i\u0011\u0001\u0002T8hO\u0006\u0014G.Z\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003-\ta!\\3uKJ\u001cX#\u0001\u0012\u0011\u0007\rB#&D\u0001%\u0015\t)c%A\u0005j[6,H/\u00192mK*\u0011q%E\u0001\u000bG>dG.Z2uS>t\u0017BA\u0015%\u0005\u0011a\u0015n\u001d;\u0011\u0005-rS\"\u0001\u0017\u000b\u00055R\u0012!\u0003:bi\u0016d\u0017.\\5u\u0013\tyCFA\nESN\\\u0007+\u001a:tSN$XM\u001c;NKR,'/\u0001\u0002pMR\u0019!\u0007\u000f)\u0011\u0007A\u0019T'\u0003\u00025#\t1q\n\u001d;j_:\u0004\"\u0001\u0004\u001c\n\u0005]2!aB+qOJ\fG-\u001a\u0005\u0006s\u0011\u0001\rAO\u0001\u0007g>,(oY3\u0011\u0005mjeB\u0001\u001fK\u001d\titI\u0004\u0002?\t:\u0011qHQ\u0007\u0002\u0001*\u0011\u0011IC\u0001\u0007yI|w\u000e\u001e \n\u0003\r\u000b1A\\3u\u0013\t)e)A\u0004mS\u001a$x/\u001a2\u000b\u0003\rK!\u0001S%\u0002\t)\u001cxN\u001c\u0006\u0003\u000b\u001aK!a\u0013'\u0002\u000fA\f7m[1hK*\u0011\u0001*S\u0005\u0003\u001d>\u0013aA\u0013,bYV,'BA&M\u0011\u0015\tF\u00011\u0001S\u0003\u0019\u0001\u0018M]1ngB\u00111KX\u0007\u0002)*\u0011QKV\u0001\u0005E\u0006\u001cXM\u0003\u0002X1\u0006I\u0001/\u0019:b[\u0016$XM\u001d\u0006\u00033j\u000bQ!\\8eK2T!a\u0017/\u0002\u000bM,'\u000eZ1\u000b\u0003u\u000b1a\u001c:h\u0013\tyFK\u0001\bUCN\\\u0007+\u0019:b[\u0016$XM]:"
)
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

//decompiled from DesktopPlanLimits$.class
package code.limits;

import code.util.Loggable;
import code.util.ratelimit.DiskPersistentMeter;
import code.util.ratelimit.MeterRateLimitReached;
import code.util.ratelimit.RateLimit;
import java.io.File;
import java.lang.invoke.SerializedLambda;
import net.liftweb.json.JsonAST;
import org.sejda.model.input.TaskSource;
import org.sejda.model.parameter.base.TaskParameters;
import org.slf4j.Logger;
import scala.Option;
import scala.Some;
import scala.collection.immutable.;
import scala.collection.immutable.List;
import scala.collection.immutable.Seq;
import scala.runtime.BoxesRunTime;

public final class DesktopPlanLimits$ implements PlanLimits, Loggable {
    public static final DesktopPlanLimits$ MODULE$ = new DesktopPlanLimits$();
    private static List<DiskPersistentMeter> meters;
    private static transient Logger logger;
    private static int MB;
    private static double INCREASE_DUE_TO_ENCRYPTION;
    private static volatile boolean bitmap$0;
    private static transient volatile boolean bitmap$trans$0;

    static {
        PlanLimits.$init$(MODULE$);
        Loggable.$init$(MODULE$);
    }

    public boolean hasFileCountLargerThanFreeUserQuota(final TaskParameters params) {
        return PlanLimits.hasFileCountLargerThanFreeUserQuota$(this, params);
    }

    public boolean hasFileLargerThanFreeUserQuota(final TaskParameters params, final long maxSizeForFreeInMb) {
        return PlanLimits.hasFileLargerThanFreeUserQuota$(this, params, maxSizeForFreeInMb);
    }

    public boolean hasImageFileLargerThanFreeUserQuota(final TaskParameters params, final long maxSizeForFreeInMb) {
        return PlanLimits.hasImageFileLargerThanFreeUserQuota$(this, params, maxSizeForFreeInMb);
    }

    public boolean hasFileWithLargePageCount(final TaskParameters params) {
        return PlanLimits.hasFileWithLargePageCount$(this, params);
    }

    public boolean isBatchFiles(final TaskParameters params) {
        return PlanLimits.isBatchFiles$(this, params);
    }

    public Option<String> isLargePageCount_TaskSpecific_Blocking(final TaskParameters params) {
        return PlanLimits.isLargePageCount_TaskSpecific_Blocking$(this, params);
    }

    public Option<String> isLargePageCount_TaskSpecific(final TaskParameters params) {
        return PlanLimits.isLargePageCount_TaskSpecific$(this, params);
    }

    public Seq<File> inputFiles(final TaskParameters params) {
        return PlanLimits.inputFiles$(this, params);
    }

    public Seq<TaskSource<?>> sources(final TaskParameters params) {
        return PlanLimits.sources$(this, params);
    }

    public Seq<Object> pageCounts(final TaskParameters params) {
        return PlanLimits.pageCounts$(this, params);
    }

    public boolean isLargeFileCount_ForRenameByText(final TaskParameters params) {
        return PlanLimits.isLargeFileCount_ForRenameByText$(this, params);
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

    public int MB() {
        return MB;
    }

    public double INCREASE_DUE_TO_ENCRYPTION() {
        return INCREASE_DUE_TO_ENCRYPTION;
    }

    public void code$limits$PlanLimits$_setter_$MB_$eq(final int x$1) {
        MB = x$1;
    }

    public void code$limits$PlanLimits$_setter_$INCREASE_DUE_TO_ENCRYPTION_$eq(final double x$1) {
        INCREASE_DUE_TO_ENCRYPTION = x$1;
    }

    private List<DiskPersistentMeter> meters$lzycompute() {
        synchronized(this){}

        try {
            if (!bitmap$0) {
                meters = new .colon.colon(new DiskPersistentMeter(".sdpl-system-config4", "", new RateLimit(3, code.util.DurationHelpers..MODULE$.perDay())), scala.collection.immutable.Nil..MODULE$);
                bitmap$0 = true;
            }
        } catch (Throwable var3) {
            throw var3;
        }

        return meters;
    }

    public List<DiskPersistentMeter> meters() {
        return !bitmap$0 ? this.meters$lzycompute() : meters;
    }

    public Option<Upgrade> of(final JsonAST.JValue source, final TaskParameters params) {
        if (code.limits.LicenseInformation..MODULE$.fromJson(source).key().isDefined()) {
            return scala.None..MODULE$;
        } else if (this.hasFileLargerThanFreeUserQuota(params, 50L)) {
            return new Some(new Upgrade("file-size", code.limits.Upgrade..MODULE$.apply$default$2()));
        } else if (this.hasImageFileLargerThanFreeUserQuota(params, 5L)) {
            return new Some(new Upgrade("image-file-size", code.limits.Upgrade..MODULE$.apply$default$2()));
        } else if (this.hasFileCountLargerThanFreeUserQuota(params)) {
            return new Some(new Upgrade("files-per-task", code.limits.Upgrade..MODULE$.apply$default$2()));
        } else if (this.hasFileWithLargePageCount(params)) {
            return new Some(new Upgrade("pages", code.limits.Upgrade..MODULE$.apply$default$2()));
        } else if (this.isLargeFileCount_ForRenameByText(params)) {
            return new Some(new Upgrade("file-count-per-rename", code.limits.Upgrade..MODULE$.apply$default$2()));
        } else if (this.isBatchFiles(params)) {
            return new Some(new Upgrade("batch-files", code.limits.Upgrade..MODULE$.apply$default$2()));
        } else {
            Option var6 = this.isLargePageCount_TaskSpecific(params);
            if (var6 instanceof Some) {
                Some var7 = (Some)var6;
                String reason = (String)var7.value();
                return new Some(new Upgrade(reason, code.limits.Upgrade..MODULE$.apply$default$2()));
            } else {
                Object var10000;
                try {
                    this.meters().foreach((meter) -> BoxesRunTime.boxToInteger($anonfun$of$1(meter)));
                    code.limits.UntrustedLocalClock..MODULE$.assertAccurateAsync(code.limits.UntrustedLocalClock..MODULE$.assertAccurateAsync$default$1());
                    var10000 = scala.None..MODULE$;
                } catch (MeterRateLimitReached var10) {
                    var10000 = new Some(new Upgrade("tasks-per-hour", new Some(BoxesRunTime.boxToLong((long)var10.meter().untilResetSeconds()))));
                }

                return (Option<Upgrade>)var10000;
            }
        }
    }

    // $FF: synthetic method
    public static final int $anonfun$of$1(final DiskPersistentMeter meter) {
        return meter.tick(meter.tick$default$1());
    }

    private DesktopPlanLimits$() {
    }

    // $FF: synthetic method
    private static Object $deserializeLambda$(SerializedLambda var0) {
        return var0.lambdaDeserialize<invokedynamic>(var0);
    }
}
