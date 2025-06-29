package code.model;

/* compiled from: TaskContext.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/model/TaskContext$.class */
public final class TaskContext$ {
    public static final TaskContext$ MODULE$ = new TaskContext$();
    private static final int web = 1;
    private static final int api = 2;
    private static final int desktop = 3;

    private TaskContext$() {
    }

    public int web() {
        return web;
    }

    public int api() {
        return api;
    }

    public int desktop() {
        return desktop;
    }
}
