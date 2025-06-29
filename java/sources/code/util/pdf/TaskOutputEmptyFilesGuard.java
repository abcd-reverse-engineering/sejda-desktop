package code.util.pdf;

import code.util.Loggable;
import java.io.File;
import org.sejda.model.output.DirectoryTaskOutput;
import org.sejda.model.output.FileOrDirectoryTaskOutput;
import org.sejda.model.output.FileTaskOutput;
import org.sejda.model.output.TaskOutputDispatcher;
import org.slf4j.Logger;
import scala.Predef$;
import scala.collection.ArrayOps$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

/* compiled from: TaskOutputEmptyFilesGuard.scala */
@ScalaSignature(bytes = "\u0006\u0005A3Aa\u0002\u0005\u0001\u001f!)\u0001\u0006\u0001C\u0001S!)A\u0006\u0001C![!)A\u0006\u0001C!q!)A\u0006\u0001C!{!)!\t\u0001C\u0005\u0007\")A\n\u0001C\u0005\u001b\nIB+Y:l\u001fV$\b/\u001e;F[B$\u0018PR5mKN<U/\u0019:e\u0015\tI!\"A\u0002qI\u001aT!a\u0003\u0007\u0002\tU$\u0018\u000e\u001c\u0006\u0002\u001b\u0005!1m\u001c3f\u0007\u0001\u0019B\u0001\u0001\t\u0019IA\u0011\u0011CF\u0007\u0002%)\u00111\u0003F\u0001\u0005Y\u0006twMC\u0001\u0016\u0003\u0011Q\u0017M^1\n\u0005]\u0011\"AB(cU\u0016\u001cG\u000f\u0005\u0002\u001aE5\t!D\u0003\u0002\u001c9\u00051q.\u001e;qkRT!!\b\u0010\u0002\u000b5|G-\u001a7\u000b\u0005}\u0001\u0013!B:fU\u0012\f'\"A\u0011\u0002\u0007=\u0014x-\u0003\u0002$5\t!B+Y:l\u001fV$\b/\u001e;ESN\u0004\u0018\r^2iKJ\u0004\"!\n\u0014\u000e\u0003)I!a\n\u0006\u0003\u00111{wmZ1cY\u0016\fa\u0001P5oSRtD#\u0001\u0016\u0011\u0005-\u0002Q\"\u0001\u0005\u0002\u0011\u0011L7\u000f]1uG\"$\"A\f\u001b\u0011\u0005=\u0012T\"\u0001\u0019\u000b\u0003E\nQa]2bY\u0006L!a\r\u0019\u0003\tUs\u0017\u000e\u001e\u0005\u00067\t\u0001\r!\u000e\t\u00033YJ!a\u000e\u000e\u0003\u001d\u0019KG.\u001a+bg.|U\u000f\u001e9viR\u0011a&\u000f\u0005\u00067\r\u0001\rA\u000f\t\u00033mJ!\u0001\u0010\u000e\u0003'\u0011K'/Z2u_JLH+Y:l\u001fV$\b/\u001e;\u0015\u00059r\u0004\"B\u000e\u0005\u0001\u0004y\u0004CA\rA\u0013\t\t%DA\rGS2,wJ\u001d#je\u0016\u001cGo\u001c:z)\u0006\u001c8nT;uaV$\u0018a\u00039s_\u000e,7o\u001d$jY\u0016$\"A\f#\t\u000b\u0015+\u0001\u0019\u0001$\u0002\u0003\u0019\u0004\"a\u0012&\u000e\u0003!S!!\u0013\u000b\u0002\u0005%|\u0017BA&I\u0005\u00111\u0015\u000e\\3\u0002\u001bA\u0014xnY3tg\u001a{G\u000eZ3s)\tqc\nC\u0003P\r\u0001\u0007a)A\u0001e\u0001")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/pdf/TaskOutputEmptyFilesGuard.class */
public class TaskOutputEmptyFilesGuard implements TaskOutputDispatcher, Loggable {
    private transient Logger logger;
    private volatile transient boolean bitmap$trans$0;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v8, types: [code.util.pdf.TaskOutputEmptyFilesGuard] */
    private Logger logger$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (!this.bitmap$trans$0) {
                this.logger = logger();
                r0 = this;
                r0.bitmap$trans$0 = true;
            }
        }
        return this.logger;
    }

    @Override // code.util.Loggable
    public Logger logger() {
        return !this.bitmap$trans$0 ? logger$lzycompute() : this.logger;
    }

    public TaskOutputEmptyFilesGuard() {
        Loggable.$init$(this);
    }

    @Override // org.sejda.model.output.TaskOutputDispatcher
    public void dispatch(final FileTaskOutput output) {
        processFile(output.getDestination());
    }

    @Override // org.sejda.model.output.TaskOutputDispatcher
    public void dispatch(final DirectoryTaskOutput output) {
        processFolder(output.getDestination());
    }

    @Override // org.sejda.model.output.TaskOutputDispatcher
    public void dispatch(final FileOrDirectoryTaskOutput output) {
        if (output.getDestination().isDirectory()) {
            processFolder(output.getDestination());
        } else {
            processFile(output.getDestination());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processFile(final File f) {
        if (f.length() == 0 && !f.getName().endsWith(".tmp")) {
            throw new RuntimeException(new StringBuilder(29).append("Detected 0-size output file: ").append(f).toString());
        }
    }

    private void processFolder(final File d) {
        ArrayOps$.MODULE$.foreach$extension(Predef$.MODULE$.refArrayOps(d.listFiles()), f -> {
            this.processFile(f);
            return BoxedUnit.UNIT;
        });
    }
}
