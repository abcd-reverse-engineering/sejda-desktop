package code.util;

import java.lang.Thread;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import scala.Predef$;
import scala.collection.ArrayOps$;
import scala.collection.mutable.StringBuilder;

/* compiled from: ThreadUtils.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/ThreadUtils$.class */
public final class ThreadUtils$ {
    public static final ThreadUtils$ MODULE$ = new ThreadUtils$();

    private ThreadUtils$() {
    }

    public String dump() {
        StringBuilder dump = new StringBuilder();
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(threadMXBean.getAllThreadIds(), 500);
        ArrayOps$.MODULE$.foreach$extension(Predef$.MODULE$.refArrayOps(threadInfos), threadInfo -> {
            dump.append('\"');
            dump.append(threadInfo.getThreadName());
            dump.append("\" ");
            Thread.State state = threadInfo.getThreadState();
            dump.append("\n   java.lang.Thread.State: ");
            dump.append(state);
            StackTraceElement[] stackTraceElements = threadInfo.getStackTrace();
            ArrayOps$.MODULE$.foreach$extension(Predef$.MODULE$.refArrayOps(stackTraceElements), stackTraceElement -> {
                dump.append("\n        at ");
                return dump.append(stackTraceElement);
            });
            return dump.append("\n\n");
        });
        return dump.toString();
    }

    public ThreadFactory namedThreadFactory(final String name) {
        return new ThreadFactory(name) { // from class: code.util.ThreadUtils$$anon$1
            private final AtomicInteger count = new AtomicInteger();
            private final String name$1;

            {
                this.name$1 = name;
            }

            private AtomicInteger count() {
                return this.count;
            }

            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(final Runnable r) {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                thread.setName(new StringBuilder(1).append(this.name$1).append("-").append(count().incrementAndGet()).toString());
                return thread;
            }
        };
    }

    public ExecutorService newFixedDaemonThreadPool(final int count, final String name) {
        return Executors.newFixedThreadPool(count, namedThreadFactory(name));
    }
}
