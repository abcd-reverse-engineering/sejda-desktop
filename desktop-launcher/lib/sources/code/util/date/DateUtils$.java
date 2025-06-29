package code.util.date;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;

/* compiled from: DateUtils.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/date/DateUtils$.class */
public final class DateUtils$ {
    public static final DateUtils$ MODULE$ = new DateUtils$();
    private static final int secondsInADay = 86400;

    private DateUtils$() {
    }

    public int secondsInADay() {
        return secondsInADay;
    }

    public DateTime fromTimestamp(final long t) {
        return new DateTime(t * 1000);
    }

    public long toTimestamp(final DateTime d) {
        return d.getMillis() / 1000;
    }

    public Timestamp nowAsTimestamp() {
        return new Timestamp(new Date().getTime());
    }

    public long nowAsSeconds() {
        return DateTime.now().getMillis() / 1000;
    }

    public DateTime toDateTime(final Timestamp ts) {
        return new DateTime(ts.getTime());
    }

    public boolean sameDayAsToday(final Timestamp ts1) {
        return sameDay(new Date(ts1.getTime()), new Date());
    }

    public Date sameDay$default$2() {
        return new Date();
    }

    public boolean sameDay(final Date date1, final Date date2) {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String str = df.format(date1);
        String str2 = df.format(date2);
        return str != null ? str.equals(str2) : str2 == null;
    }

    public boolean isBeforeNow(final DateTime d) {
        return d.isBefore(DateTime.now());
    }

    public String humanFriendlyDate(final DateTime created) {
        return humanFriendlyDate(created.toDate());
    }

    public String humanFriendlyDate(final Date created) {
        Date today = new Date();
        long duration = today.getTime() - created.getTime();
        long minute = 1000 * 60;
        long hour = minute * 60;
        long day = hour * 24;
        if (duration < 1000 * 7) {
            return "right now";
        }
        if (duration < minute) {
            int n = (int) Math.floor(duration / 1000);
            return new StringBuilder(12).append(n).append(" seconds ago").toString();
        }
        if (duration < minute * 2) {
            return "about 1 minute ago";
        }
        if (duration < hour) {
            int n2 = (int) Math.floor(duration / minute);
            return new StringBuilder(12).append(n2).append(" minutes ago").toString();
        }
        if (duration < hour * 2) {
            return "about 1 hour ago";
        }
        if (duration < day) {
            int n3 = (int) Math.floor(duration / hour);
            return new StringBuilder(10).append(n3).append(" hours ago").toString();
        }
        if (duration > day && duration < day * 2) {
            return "yesterday";
        }
        if (duration < day * 365) {
            int n4 = (int) Math.floor(duration / day);
            return new StringBuilder(9).append(n4).append(" days ago").toString();
        }
        return "over a year ago";
    }

    public String humanFriendlyFutureDate(final DateTime future) {
        return humanFriendlyFutureDate(future.toDate());
    }

    public String humanFriendlyFutureDate(final Date future) {
        Date now = new Date();
        long duration = future.getTime() - now.getTime();
        long minute = 1000 * 60;
        long hour = minute * 60;
        long day = hour * 24;
        long j = day * 365;
        if (duration < 1000 * 7) {
            return "right now";
        }
        if (duration < minute) {
            int n = (int) Math.floor(duration / 1000);
            return new StringBuilder(11).append("in ").append(n).append(" seconds").toString();
        }
        if (duration < minute * 2) {
            return "in 1 minute";
        }
        if (duration < hour) {
            int n2 = (int) Math.floor(duration / minute);
            return new StringBuilder(11).append("in ").append(n2).append(" minutes").toString();
        }
        if (duration < hour * 2) {
            return "in 1 hour";
        }
        if (duration < day) {
            int n3 = (int) Math.floor(duration / hour);
            return new StringBuilder(9).append("in ").append(n3).append(" hours").toString();
        }
        if (duration > day && duration < day * 2) {
            return "tomorrow";
        }
        if (duration < day * 365) {
            int n4 = (int) Math.floor(duration / day);
            return new StringBuilder(8).append("in ").append(n4).append(" days").toString();
        }
        return "in a year";
    }

    public DateTime withCetTimezone(final long ts) {
        return withCetTimezone(new DateTime(ts));
    }

    public DateTime withCetTimezone(final DateTime dt) {
        return dt.withZone(DateTimeZone.forID("Europe/Amsterdam"));
    }

    public String fromString$default$2() {
        return "YYYY-MM-dd";
    }

    public DateTime fromString(final String input, final String pattern) {
        return DateTime.parse(input, DateTimeFormat.forPattern(pattern));
    }

    public DateTime fromString(final String input) {
        String pattern = input.contains(":") ? "YYYY-MM-dd HH:mm:ss" : "YYYY-MM-dd";
        return DateTime.parse(input, DateTimeFormat.forPattern(pattern));
    }

    public DateTime fromSeconds(final long s) {
        return new DateTime(new Date(s * 1000));
    }

    public DateTime currentHour() {
        return toHour(new DateTime());
    }

    public DateTime toHour(final DateTime dt) {
        return dt.withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
    }
}
