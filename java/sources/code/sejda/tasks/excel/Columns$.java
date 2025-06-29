package code.sejda.tasks.excel;

/* compiled from: TableDetector.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/excel/Columns$.class */
public final class Columns$ {
    public static final Columns$ MODULE$ = new Columns$();
    private static int i = 0;

    private Columns$() {
    }

    public int i() {
        return i;
    }

    public void i_$eq(final int x$1) {
        i = x$1;
    }

    public int nextIndex() {
        i_$eq(i() + 1);
        return i();
    }
}
