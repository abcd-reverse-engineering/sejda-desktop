package code.service;

import java.io.File;
import org.sejda.model.notification.EventListener;
import org.sejda.model.notification.event.TaskExecutionCompletedEvent;
import scala.collection.IterableOnce;
import scala.collection.JavaConverters$;
import scala.collection.immutable.Nil$;
import scala.collection.mutable.Buffer;
import scala.collection.mutable.Buffer$;
import scala.reflect.ScalaSignature;

/* compiled from: InJvmTaskExecutor.scala */
@ScalaSignature(bytes = "\u0006\u0005\u00153A!\u0002\u0004\u0001\u0017!)a\u0005\u0001C\u0001O!9!\u0006\u0001b\u0001\n\u0003Y\u0003B\u0002\u001f\u0001A\u0003%A\u0006C\u0003>\u0001\u0011\u0005cH\u0001\u0010UCN\\W\t_3dkRLwN\\\"p[BdW\r^3e\u0019&\u001cH/\u001a8fe*\u0011q\u0001C\u0001\bg\u0016\u0014h/[2f\u0015\u0005I\u0011\u0001B2pI\u0016\u001c\u0001aE\u0002\u0001\u0019Q\u0001\"!\u0004\n\u000e\u00039Q!a\u0004\t\u0002\t1\fgn\u001a\u0006\u0002#\u0005!!.\u0019<b\u0013\t\u0019bB\u0001\u0004PE*,7\r\u001e\t\u0004+y\u0001S\"\u0001\f\u000b\u0005]A\u0012\u0001\u00048pi&4\u0017nY1uS>t'BA\r\u001b\u0003\u0015iw\u000eZ3m\u0015\tYB$A\u0003tK*$\u0017MC\u0001\u001e\u0003\ry'oZ\u0005\u0003?Y\u0011Q\"\u0012<f]Rd\u0015n\u001d;f]\u0016\u0014\bCA\u0011%\u001b\u0005\u0011#BA\u0012\u0017\u0003\u0015)g/\u001a8u\u0013\t)#EA\u000eUCN\\W\t_3dkRLwN\\\"p[BdW\r^3e\u000bZ,g\u000e^\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003!\u0002\"!\u000b\u0001\u000e\u0003\u0019\t1\u0002^1tW>+H\u000f];ugV\tA\u0006E\u0002.iYj\u0011A\f\u0006\u0003_A\nq!\\;uC\ndWM\u0003\u00022e\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\u000b\u0003M\nQa]2bY\u0006L!!\u000e\u0018\u0003\r\t+hMZ3s!\t9$(D\u00019\u0015\tI\u0004#\u0001\u0002j_&\u00111\b\u000f\u0002\u0005\r&dW-\u0001\u0007uCN\\w*\u001e;qkR\u001c\b%A\u0004p]\u00163XM\u001c;\u0015\u0005}\u001a\u0005C\u0001!B\u001b\u0005\u0011\u0014B\u0001\"3\u0005\u0011)f.\u001b;\t\u000b\u0011#\u0001\u0019\u0001\u0011\u0002\u0003\u0015\u0004")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/service/TaskExecutionCompletedListener.class */
public class TaskExecutionCompletedListener implements EventListener<TaskExecutionCompletedEvent> {
    private final Buffer<File> taskOutputs = Buffer$.MODULE$.apply(Nil$.MODULE$);

    public Buffer<File> taskOutputs() {
        return this.taskOutputs;
    }

    @Override // org.sejda.model.notification.EventListener
    public void onEvent(final TaskExecutionCompletedEvent e) {
        taskOutputs().$plus$plus$eq((IterableOnce) JavaConverters$.MODULE$.asScalaBufferConverter(e.getNotifiableTaskMetadata().taskOutput()).asScala());
    }
}
