package code.util.date;

import java.io.Serializable;
import java.util.Calendar;
import org.joda.time.DateTime;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple4;
import scala.collection.Iterator;
import scala.concurrent.duration.Duration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: DateUtils.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005\u0005f\u0001B\u0011#\u0001&B\u0001b\u0010\u0001\u0003\u0016\u0004%\t\u0001\u0011\u0005\t\t\u0002\u0011\t\u0012)A\u0005\u0003\"AQ\t\u0001BK\u0002\u0013\u0005\u0001\t\u0003\u0005G\u0001\tE\t\u0015!\u0003B\u0011!9\u0005A!f\u0001\n\u0003\u0001\u0005\u0002\u0003%\u0001\u0005#\u0005\u000b\u0011B!\t\u0011%\u0003!Q3A\u0005\u0002)C\u0001B\u0015\u0001\u0003\u0012\u0003\u0006Ia\u0013\u0005\u0006'\u0002!\t\u0001\u0016\u0005\u00067\u0002!\t\u0001\u0018\u0005\u0006A\u0002!\t!\u0019\u0005\b]\u0002\t\t\u0011\"\u0001p\u0011\u001d!\b!%A\u0005\u0002UD\u0001\"!\u0001\u0001#\u0003%\t!\u001e\u0005\t\u0003\u0007\u0001\u0011\u0013!C\u0001k\"I\u0011Q\u0001\u0001\u0012\u0002\u0013\u0005\u0011q\u0001\u0005\n\u0003\u0017\u0001\u0011\u0011!C!\u0003\u001bA\u0001\"a\b\u0001\u0003\u0003%\t\u0001\u0011\u0005\n\u0003C\u0001\u0011\u0011!C\u0001\u0003GA\u0011\"a\f\u0001\u0003\u0003%\t%!\r\t\u0013\u0005}\u0002!!A\u0005\u0002\u0005\u0005\u0003\"CA#\u0001\u0005\u0005I\u0011IA$\u0011%\tY\u0005AA\u0001\n\u0003\ni\u0005C\u0005\u0002P\u0001\t\t\u0011\"\u0011\u0002R!I\u00111\u000b\u0001\u0002\u0002\u0013\u0005\u0013QK\u0004\n\u00033\u0012\u0013\u0011!E\u0001\u000372\u0001\"\t\u0012\u0002\u0002#\u0005\u0011Q\f\u0005\u0007'n!\t!!\u001e\t\u0013\u0005=3$!A\u0005F\u0005E\u0003\"CA<7\u0005\u0005I\u0011QA=\u0011%\t\u0019iGA\u0001\n\u0003\u000b)\tC\u0005\u0002\u0018n\t\t\u0011\"\u0003\u0002\u001a\nQA+[7f/&tGm\\<\u000b\u0005\r\"\u0013\u0001\u00023bi\u0016T!!\n\u0014\u0002\tU$\u0018\u000e\u001c\u0006\u0002O\u0005!1m\u001c3f\u0007\u0001\u0019B\u0001\u0001\u00161gA\u00111FL\u0007\u0002Y)\tQ&A\u0003tG\u0006d\u0017-\u0003\u00020Y\t1\u0011I\\=SK\u001a\u0004\"aK\u0019\n\u0005Ib#a\u0002)s_\u0012,8\r\u001e\t\u0003iqr!!\u000e\u001e\u000f\u0005YJT\"A\u001c\u000b\u0005aB\u0013A\u0002\u001fs_>$h(C\u0001.\u0013\tYD&A\u0004qC\u000e\\\u0017mZ3\n\u0005ur$\u0001D*fe&\fG.\u001b>bE2,'BA\u001e-\u0003%!\u0017-_(g/\u0016,7.F\u0001B!\tY#)\u0003\u0002DY\t\u0019\u0011J\u001c;\u0002\u0015\u0011\f\u0017p\u00144XK\u0016\\\u0007%\u0001\u0005ge>l\u0007j\\;s\u0003%1'o\\7I_V\u0014\b%\u0001\u0006ge>lW*\u001b8vi\u0016\f1B\u001a:p[6Kg.\u001e;fA\u0005AA-\u001e:bi&|g.F\u0001L!\ta\u0005+D\u0001N\u0015\tIeJ\u0003\u0002PY\u0005Q1m\u001c8dkJ\u0014XM\u001c;\n\u0005Ek%\u0001\u0003#ve\u0006$\u0018n\u001c8\u0002\u0013\u0011,(/\u0019;j_:\u0004\u0013A\u0002\u001fj]&$h\bF\u0003V/bK&\f\u0005\u0002W\u00015\t!\u0005C\u0003@\u0013\u0001\u0007\u0011\tC\u0003F\u0013\u0001\u0007\u0011\tC\u0003H\u0013\u0001\u0007\u0011\tC\u0003J\u0013\u0001\u00071*A\u0006jg\u0006\u001bG/\u001b<f\u001d><H#A/\u0011\u0005-r\u0016BA0-\u0005\u001d\u0011un\u001c7fC:\f\u0001bY8oi\u0006Lgn\u001d\u000b\u0003;\nDQaY\u0006A\u0002\u0011\f!\u0001\u001a;\u0011\u0005\u0015dW\"\u00014\u000b\u0005\u001dD\u0017\u0001\u0002;j[\u0016T!!\u001b6\u0002\t)|G-\u0019\u0006\u0002W\u0006\u0019qN]4\n\u000554'\u0001\u0003#bi\u0016$\u0016.\\3\u0002\t\r|\u0007/\u001f\u000b\u0006+B\f(o\u001d\u0005\b\u007f1\u0001\n\u00111\u0001B\u0011\u001d)E\u0002%AA\u0002\u0005Cqa\u0012\u0007\u0011\u0002\u0003\u0007\u0011\tC\u0004J\u0019A\u0005\t\u0019A&\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\taO\u000b\u0002Bo.\n\u0001\u0010\u0005\u0002z}6\t!P\u0003\u0002|y\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0003{2\n!\"\u00198o_R\fG/[8o\u0013\ty(PA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fabY8qs\u0012\"WMZ1vYR$#'\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001a\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%iU\u0011\u0011\u0011\u0002\u0016\u0003\u0017^\fQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DXCAA\b!\u0011\t\t\"a\u0007\u000e\u0005\u0005M!\u0002BA\u000b\u0003/\tA\u0001\\1oO*\u0011\u0011\u0011D\u0001\u0005U\u00064\u0018-\u0003\u0003\u0002\u001e\u0005M!AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\t\u0005\u0015\u00121\u0006\t\u0004W\u0005\u001d\u0012bAA\u0015Y\t\u0019\u0011I\\=\t\u0011\u000552#!AA\u0002\u0005\u000b1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XCAA\u001a!\u0019\t)$a\u000f\u0002&5\u0011\u0011q\u0007\u0006\u0004\u0003sa\u0013AC2pY2,7\r^5p]&!\u0011QHA\u001c\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\u0007u\u000b\u0019\u0005C\u0005\u0002.U\t\t\u00111\u0001\u0002&\u0005\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\u0011\ty!!\u0013\t\u0011\u00055b#!AA\u0002\u0005\u000b\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002\u0003\u0006AAo\\*ue&tw\r\u0006\u0002\u0002\u0010\u00051Q-];bYN$2!XA,\u0011%\ti#GA\u0001\u0002\u0004\t)#\u0001\u0006US6,w+\u001b8e_^\u0004\"AV\u000e\u0014\u000bm\ty&a\u001b\u0011\u0013\u0005\u0005\u0014qM!B\u0003.+VBAA2\u0015\r\t)\u0007L\u0001\beVtG/[7f\u0013\u0011\tI'a\u0019\u0003#\u0005\u00137\u000f\u001e:bGR4UO\\2uS>tG\u0007\u0005\u0003\u0002n\u0005MTBAA8\u0015\u0011\t\t(a\u0006\u0002\u0005%|\u0017bA\u001f\u0002pQ\u0011\u00111L\u0001\u0006CB\u0004H.\u001f\u000b\n+\u0006m\u0014QPA@\u0003\u0003CQa\u0010\u0010A\u0002\u0005CQ!\u0012\u0010A\u0002\u0005CQa\u0012\u0010A\u0002\u0005CQ!\u0013\u0010A\u0002-\u000bq!\u001e8baBd\u0017\u0010\u0006\u0003\u0002\b\u0006M\u0005#B\u0016\u0002\n\u00065\u0015bAAFY\t1q\n\u001d;j_:\u0004raKAH\u0003\u0006\u000b5*C\u0002\u0002\u00122\u0012a\u0001V;qY\u0016$\u0004\u0002CAK?\u0005\u0005\t\u0019A+\u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0002\u001cB!\u0011\u0011CAO\u0013\u0011\ty*a\u0005\u0003\r=\u0013'.Z2u\u0001")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/date/TimeWindow.class */
public class TimeWindow implements Product, Serializable {
    private final int dayOfWeek;
    private final int fromHour;
    private final int fromMinute;
    private final Duration duration;

    public static Option<Tuple4<Object, Object, Object, Duration>> unapply(final TimeWindow x$0) {
        return TimeWindow$.MODULE$.unapply(x$0);
    }

    public static TimeWindow apply(final int dayOfWeek, final int fromHour, final int fromMinute, final Duration duration) {
        return TimeWindow$.MODULE$.apply(dayOfWeek, fromHour, fromMinute, duration);
    }

    public static Function1<Tuple4<Object, Object, Object, Duration>, TimeWindow> tupled() {
        return TimeWindow$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<Object, Function1<Object, Function1<Duration, TimeWindow>>>> curried() {
        return TimeWindow$.MODULE$.curried();
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public int dayOfWeek() {
        return this.dayOfWeek;
    }

    public int fromHour() {
        return this.fromHour;
    }

    public int fromMinute() {
        return this.fromMinute;
    }

    public Duration duration() {
        return this.duration;
    }

    public TimeWindow copy(final int dayOfWeek, final int fromHour, final int fromMinute, final Duration duration) {
        return new TimeWindow(dayOfWeek, fromHour, fromMinute, duration);
    }

    public int copy$default$1() {
        return dayOfWeek();
    }

    public int copy$default$2() {
        return fromHour();
    }

    public int copy$default$3() {
        return fromMinute();
    }

    public Duration copy$default$4() {
        return duration();
    }

    public String productPrefix() {
        return "TimeWindow";
    }

    public int productArity() {
        return 4;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return BoxesRunTime.boxToInteger(dayOfWeek());
            case 1:
                return BoxesRunTime.boxToInteger(fromHour());
            case 2:
                return BoxesRunTime.boxToInteger(fromMinute());
            case 3:
                return duration();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof TimeWindow;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "dayOfWeek";
            case 1:
                return "fromHour";
            case 2:
                return "fromMinute";
            case 3:
                return "duration";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return Statics.finalizeHash(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(-889275714, productPrefix().hashCode()), dayOfWeek()), fromHour()), fromMinute()), Statics.anyHash(duration())), 4);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof TimeWindow) {
                TimeWindow timeWindow = (TimeWindow) x$1;
                if (dayOfWeek() == timeWindow.dayOfWeek() && fromHour() == timeWindow.fromHour() && fromMinute() == timeWindow.fromMinute()) {
                    Duration duration = duration();
                    Duration duration2 = timeWindow.duration();
                    if (duration != null ? duration.equals(duration2) : duration2 == null) {
                        if (timeWindow.canEqual(this)) {
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    public TimeWindow(final int dayOfWeek, final int fromHour, final int fromMinute, final Duration duration) {
        this.dayOfWeek = dayOfWeek;
        this.fromHour = fromHour;
        this.fromMinute = fromMinute;
        this.duration = duration;
        Product.$init$(this);
    }

    public boolean isActiveNow() {
        return contains(DateTime.now());
    }

    public boolean contains(final DateTime dt) {
        Calendar c = Calendar.getInstance();
        c.setTime(dt.toDate());
        if (c.get(7) != dayOfWeek()) {
            return false;
        }
        DateTime startDt = dt.withHourOfDay(fromHour()).withMinuteOfHour(fromMinute()).withSecondOfMinute(0);
        DateTime endDt = startDt.plusSeconds((int) duration().toSeconds());
        return dt.isAfter(startDt) && dt.isBefore(endDt);
    }
}
