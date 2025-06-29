package code.util.date;

import java.sql.Timestamp;
import java.util.Date;
import org.joda.time.DateTime;
import scala.reflect.ScalaSignature;

/* compiled from: DateUtils.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005]s!\u0002\u000e\u001c\u0011\u0003\u0011c!\u0002\u0013\u001c\u0011\u0003)\u0003\"\u0002\u0017\u0002\t\u0003i\u0003b\u0002\u0018\u0002\u0005\u0004%\ta\f\u0005\u0007g\u0005\u0001\u000b\u0011\u0002\u0019\t\u000bQ\nA\u0011A\u001b\t\u000b\u0015\u000bA\u0011\u0001$\t\u000b%\u000bA\u0011\u0001&\t\u000bM\u000bA\u0011\u0001+\t\u000bU\u000bA\u0011\u0001,\t\u000be\u000bA\u0011\u0001.\t\u000b\u0001\fA\u0011A1\t\u000f-\f\u0011\u0013!C\u0001Y\")q/\u0001C\u0001q\")!0\u0001C\u0001w\"1!0\u0001C\u0001\u0003'Aq!a\u0006\u0002\t\u0003\tI\u0002C\u0004\u0002\u0018\u0005!\t!a\b\t\u000f\u0005\r\u0012\u0001\"\u0001\u0002&!9\u00111E\u0001\u0005\u0002\u0005%\u0002bBA\u0018\u0003\u0011\u0005\u0011\u0011\u0007\u0005\n\u0003w\t\u0011\u0013!C\u0001\u0003{Aq!a\f\u0002\t\u0003\t\t\u0005C\u0004\u0002F\u0005!\t!a\u0012\t\u000f\u00055\u0013\u0001\"\u0001\u0002P!9\u0011\u0011K\u0001\u0005\u0002\u0005M\u0013!\u0003#bi\u0016,F/\u001b7t\u0015\taR$\u0001\u0003eCR,'B\u0001\u0010 \u0003\u0011)H/\u001b7\u000b\u0003\u0001\nAaY8eK\u000e\u0001\u0001CA\u0012\u0002\u001b\u0005Y\"!\u0003#bi\u0016,F/\u001b7t'\t\ta\u0005\u0005\u0002(U5\t\u0001FC\u0001*\u0003\u0015\u00198-\u00197b\u0013\tY\u0003F\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003\t\nQb]3d_:$7/\u00138B\t\u0006LX#\u0001\u0019\u0011\u0005\u001d\n\u0014B\u0001\u001a)\u0005\rIe\u000e^\u0001\u000fg\u0016\u001cwN\u001c3t\u0013:\fE)Y=!\u000351'o\\7US6,7\u000f^1naR\u0011a\u0007\u0011\t\u0003oyj\u0011\u0001\u000f\u0006\u0003si\nA\u0001^5nK*\u00111\bP\u0001\u0005U>$\u0017MC\u0001>\u0003\ry'oZ\u0005\u0003\u007fa\u0012\u0001\u0002R1uKRKW.\u001a\u0005\u0006\u0003\u0016\u0001\rAQ\u0001\u0002iB\u0011qeQ\u0005\u0003\t\"\u0012A\u0001T8oO\u0006YAo\u001c+j[\u0016\u001cH/Y7q)\t\u0011u\tC\u0003I\r\u0001\u0007a'A\u0001e\u00039qwn^!t)&lWm\u001d;b[B$\u0012a\u0013\t\u0003\u0019Fk\u0011!\u0014\u0006\u0003\u001d>\u000b1a]9m\u0015\u0005\u0001\u0016\u0001\u00026bm\u0006L!AU'\u0003\u0013QKW.Z:uC6\u0004\u0018\u0001\u00048po\u0006\u001b8+Z2p]\u0012\u001cH#\u0001\"\u0002\u0015Q|G)\u0019;f)&lW\r\u0006\u00027/\")\u0001,\u0003a\u0001\u0017\u0006\u0011Ao]\u0001\u000fg\u0006lW\rR1z\u0003N$v\u000eZ1z)\tYf\f\u0005\u0002(9&\u0011Q\f\u000b\u0002\b\u0005>|G.Z1o\u0011\u0015y&\u00021\u0001L\u0003\r!8/M\u0001\bg\u0006lW\rR1z)\rY&-\u001b\u0005\u0006G.\u0001\r\u0001Z\u0001\u0006I\u0006$X-\r\t\u0003K\u001el\u0011A\u001a\u0006\u0003==K!\u0001\u001b4\u0003\t\u0011\u000bG/\u001a\u0005\bU.\u0001\n\u00111\u0001e\u0003\u0015!\u0017\r^33\u0003E\u0019\u0018-\\3ECf$C-\u001a4bk2$HEM\u000b\u0002[*\u0012AM\\\u0016\u0002_B\u0011\u0001/^\u0007\u0002c*\u0011!o]\u0001\nk:\u001c\u0007.Z2lK\u0012T!\u0001\u001e\u0015\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002wc\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u0017%\u001c()\u001a4pe\u0016tun\u001e\u000b\u00037fDQ\u0001S\u0007A\u0002Y\n\u0011\u0003[;nC:4%/[3oI2LH)\u0019;f)\ra\u0018q\u0002\t\u0004{\u0006%ab\u0001@\u0002\u0006A\u0011q\u0010K\u0007\u0003\u0003\u0003Q1!a\u0001\"\u0003\u0019a$o\\8u}%\u0019\u0011q\u0001\u0015\u0002\rA\u0013X\rZ3g\u0013\u0011\tY!!\u0004\u0003\rM#(/\u001b8h\u0015\r\t9\u0001\u000b\u0005\u0007\u0003#q\u0001\u0019\u0001\u001c\u0002\u000f\r\u0014X-\u0019;fIR\u0019A0!\u0006\t\r\u0005Eq\u00021\u0001e\u0003]AW/\\1o\rJLWM\u001c3ms\u001a+H/\u001e:f\t\u0006$X\rF\u0002}\u00037Aa!!\b\u0011\u0001\u00041\u0014A\u00024viV\u0014X\rF\u0002}\u0003CAa!!\b\u0012\u0001\u0004!\u0017aD<ji\"\u001cU\r\u001e+j[\u0016TxN\\3\u0015\u0007Y\n9\u0003C\u0003Y%\u0001\u0007!\tF\u00027\u0003WAa!!\f\u0014\u0001\u00041\u0014A\u00013u\u0003)1'o\\7TiJLgn\u001a\u000b\u0006m\u0005M\u0012q\u0007\u0005\u0007\u0003k!\u0002\u0019\u0001?\u0002\u000b%t\u0007/\u001e;\t\u0011\u0005eB\u0003%AA\u0002q\fq\u0001]1ui\u0016\u0014h.\u0001\u000bge>l7\u000b\u001e:j]\u001e$C-\u001a4bk2$HEM\u000b\u0003\u0003\u007fQ#\u0001 8\u0015\u0007Y\n\u0019\u0005\u0003\u0004\u00026Y\u0001\r\u0001`\u0001\fMJ|WnU3d_:$7\u000fF\u00027\u0003\u0013Ba!a\u0013\u0018\u0001\u0004\u0011\u0015!A:\u0002\u0017\r,(O]3oi\"{WO\u001d\u000b\u0002m\u00051Ao\u001c%pkJ$2ANA+\u0011\u0019\ti#\u0007a\u0001m\u0001")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/date/DateUtils.class */
public final class DateUtils {
    public static DateTime toHour(final DateTime dt) {
        return DateUtils$.MODULE$.toHour(dt);
    }

    public static DateTime currentHour() {
        return DateUtils$.MODULE$.currentHour();
    }

    public static DateTime fromSeconds(final long s) {
        return DateUtils$.MODULE$.fromSeconds(s);
    }

    public static DateTime fromString(final String input) {
        return DateUtils$.MODULE$.fromString(input);
    }

    public static DateTime fromString(final String input, final String pattern) {
        return DateUtils$.MODULE$.fromString(input, pattern);
    }

    public static DateTime withCetTimezone(final DateTime dt) {
        return DateUtils$.MODULE$.withCetTimezone(dt);
    }

    public static DateTime withCetTimezone(final long ts) {
        return DateUtils$.MODULE$.withCetTimezone(ts);
    }

    public static String humanFriendlyFutureDate(final Date future) {
        return DateUtils$.MODULE$.humanFriendlyFutureDate(future);
    }

    public static String humanFriendlyFutureDate(final DateTime future) {
        return DateUtils$.MODULE$.humanFriendlyFutureDate(future);
    }

    public static String humanFriendlyDate(final Date created) {
        return DateUtils$.MODULE$.humanFriendlyDate(created);
    }

    public static String humanFriendlyDate(final DateTime created) {
        return DateUtils$.MODULE$.humanFriendlyDate(created);
    }

    public static boolean isBeforeNow(final DateTime d) {
        return DateUtils$.MODULE$.isBeforeNow(d);
    }

    public static boolean sameDay(final Date date1, final Date date2) {
        return DateUtils$.MODULE$.sameDay(date1, date2);
    }

    public static boolean sameDayAsToday(final Timestamp ts1) {
        return DateUtils$.MODULE$.sameDayAsToday(ts1);
    }

    public static DateTime toDateTime(final Timestamp ts) {
        return DateUtils$.MODULE$.toDateTime(ts);
    }

    public static long nowAsSeconds() {
        return DateUtils$.MODULE$.nowAsSeconds();
    }

    public static Timestamp nowAsTimestamp() {
        return DateUtils$.MODULE$.nowAsTimestamp();
    }

    public static long toTimestamp(final DateTime d) {
        return DateUtils$.MODULE$.toTimestamp(d);
    }

    public static DateTime fromTimestamp(final long t) {
        return DateUtils$.MODULE$.fromTimestamp(t);
    }

    public static int secondsInADay() {
        return DateUtils$.MODULE$.secondsInADay();
    }
}
