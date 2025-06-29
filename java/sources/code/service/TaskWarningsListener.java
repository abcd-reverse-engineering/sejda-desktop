package code.service;

import org.sejda.model.notification.EventListener;
import org.sejda.model.notification.event.TaskExecutionWarningEvent;
import scala.collection.immutable.Nil$;
import scala.collection.mutable.LinkedHashSet;
import scala.collection.mutable.LinkedHashSet$;
import scala.reflect.ScalaSignature;

/* compiled from: InJvmTaskExecutor.scala */
@ScalaSignature(bytes = "\u0006\u0005)3A!\u0002\u0004\u0001\u0017!)a\u0005\u0001C\u0001O!9!\u0006\u0001b\u0001\n\u0003Y\u0003BB!\u0001A\u0003%A\u0006C\u0003C\u0001\u0011\u00053I\u0001\u000bUCN\\w+\u0019:oS:<7\u000fT5ti\u0016tWM\u001d\u0006\u0003\u000f!\tqa]3sm&\u001cWMC\u0001\n\u0003\u0011\u0019w\u000eZ3\u0004\u0001M\u0019\u0001\u0001\u0004\u000b\u0011\u00055\u0011R\"\u0001\b\u000b\u0005=\u0001\u0012\u0001\u00027b]\u001eT\u0011!E\u0001\u0005U\u00064\u0018-\u0003\u0002\u0014\u001d\t1qJ\u00196fGR\u00042!\u0006\u0010!\u001b\u00051\"BA\f\u0019\u00031qw\u000e^5gS\u000e\fG/[8o\u0015\tI\"$A\u0003n_\u0012,GN\u0003\u0002\u001c9\u0005)1/\u001a6eC*\tQ$A\u0002pe\u001eL!a\b\f\u0003\u001b\u00153XM\u001c;MSN$XM\\3s!\t\tC%D\u0001#\u0015\t\u0019c#A\u0003fm\u0016tG/\u0003\u0002&E\tIB+Y:l\u000bb,7-\u001e;j_:<\u0016M\u001d8j]\u001e,e/\u001a8u\u0003\u0019a\u0014N\\5u}Q\t\u0001\u0006\u0005\u0002*\u00015\ta!\u0001\u0005xCJt\u0017N\\4t+\u0005a\u0003cA\u00175m5\taF\u0003\u00020a\u00059Q.\u001e;bE2,'BA\u00193\u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u0002g\u0005)1oY1mC&\u0011QG\f\u0002\u000e\u0019&t7.\u001a3ICND7+\u001a;\u0011\u0005]rdB\u0001\u001d=!\tI$'D\u0001;\u0015\tY$\"\u0001\u0004=e>|GOP\u0005\u0003{I\na\u0001\u0015:fI\u00164\u0017BA A\u0005\u0019\u0019FO]5oO*\u0011QHM\u0001\no\u0006\u0014h.\u001b8hg\u0002\nqa\u001c8Fm\u0016tG\u000f\u0006\u0002E\u0011B\u0011QIR\u0007\u0002e%\u0011qI\r\u0002\u0005+:LG\u000fC\u0003J\t\u0001\u0007\u0001%A\u0001f\u0001")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/service/TaskWarningsListener.class */
public class TaskWarningsListener implements EventListener<TaskExecutionWarningEvent> {
    private final LinkedHashSet<String> warnings = (LinkedHashSet) LinkedHashSet$.MODULE$.apply(Nil$.MODULE$);

    public LinkedHashSet<String> warnings() {
        return this.warnings;
    }

    @Override // org.sejda.model.notification.EventListener
    public void onEvent(final TaskExecutionWarningEvent e) {
        warnings().$plus$eq(e.getWarning());
    }
}
