package code.sejda.tasks.rename;

import scala.Predef$;
import scala.collection.StringOps$;

/* compiled from: RenameByTextTask.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/rename/RenameByTextTask$.class */
public final class RenameByTextTask$ {
    public static final RenameByTextTask$ MODULE$ = new RenameByTextTask$();

    private RenameByTextTask$() {
    }

    public String convertTextValue(final String in) {
        return StringOps$.MODULE$.replaceAllLiterally$extension(Predef$.MODULE$.augmentString(StringOps$.MODULE$.replaceAllLiterally$extension(Predef$.MODULE$.augmentString(in), "/", ".")), "\\", ".");
    }
}
