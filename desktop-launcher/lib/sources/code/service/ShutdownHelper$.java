package code.service;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import org.joda.time.DateTime;
import scala.Int$;

/* compiled from: InJvmTaskExecutor.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/service/ShutdownHelper$.class */
public final class ShutdownHelper$ {
    public static final ShutdownHelper$ MODULE$ = new ShutdownHelper$();
    private static final int interval = 2000;

    private ShutdownHelper$() {
    }

    public int interval() {
        return interval;
    }

    public void start(final File kill) {
        TimerTask task = new ShutdownHelper$$anon$2(kill);
        new Timer(true).schedule(task, interval(), Int$.MODULE$.int2long(interval()));
    }

    public void trigger(final File kill) throws InterruptedException {
        Files.write(DateTime.now().toString(), kill, Charsets.UTF_8);
        Thread.sleep(interval() + 1000);
    }
}
