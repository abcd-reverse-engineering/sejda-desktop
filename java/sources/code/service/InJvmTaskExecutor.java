package code.service;

import code.limits.Upgrade;
import code.model.TaskResult;
import code.model.TaskResult$;
import code.util.Loggable;
import code.util.pdf.PdfDetails;
import code.util.pdf.PdfDetails$;
import code.util.pdf.PdfLibraryUtils$;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import java.io.File;
import net.liftweb.json.JsonAST;
import net.liftweb.json.package$;
import org.sejda.core.notification.context.GlobalNotificationContext;
import org.sejda.core.service.DefaultTaskExecutionService;
import org.sejda.core.support.util.HumanReadableSize;
import org.sejda.model.input.PdfFileSource;
import org.sejda.model.notification.EventListener;
import org.sejda.model.notification.event.AbstractNotificationEvent;
import org.sejda.model.notification.event.TaskExecutionFailedEvent;
import org.sejda.model.output.TaskOutput;
import org.sejda.model.parameter.base.TaskParameters;
import org.sejda.model.pro.parameter.OptimizeParameters;
import org.sejda.model.pro.parameter.RepairParameters;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.Nothing$;

/* compiled from: InJvmTaskExecutor.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005\u0015eaB\n\u0015!\u0003\r\t!\u0007\u0005\u0006M\u0001!\ta\n\u0005\bW\u0001\u0011\r\u0011\"\u0001-\u0011\u0015A\u0004\u0001\"\u0001:\u0011\u0015q\u0004A\"\u0001@\u0011\u0015\u0019\u0005\u0001\"\u0001(\u0011\u0015!\u0005\u0001\"\u0001F\u0011\u001dA\u0006\u00011A\u0005\neCq!\u0018\u0001A\u0002\u0013%a\fC\u0003b\u0001\u0011\u0005!\rC\u0004\u0002\n\u0001!\t!a\u0003\t\u000f\u0005m\u0001A\"\u0001\u0002\u001e!9\u0011\u0011\u0005\u0001\u0005\u0002\u0005\r\u0002bBA\u001e\u0001\u0011\u0005\u0011Q\b\u0005\b\u0003\u001f\u0002A\u0011AA)\u0011\u0019\t)\u0007\u0001C\u0001O!9\u0011q\r\u0001\u0005\n\u0005%\u0004bBA9\u0001\u0011%\u00111\u000f\u0005\b\u0003w\u0002A\u0011BA?\u0005EIeN\u0013<n)\u0006\u001c8.\u0012=fGV$xN\u001d\u0006\u0003+Y\tqa]3sm&\u001cWMC\u0001\u0018\u0003\u0011\u0019w\u000eZ3\u0004\u0001M\u0019\u0001A\u0007\u0011\u0011\u0005mqR\"\u0001\u000f\u000b\u0003u\tQa]2bY\u0006L!a\b\u000f\u0003\r\u0005s\u0017PU3g!\t\tC%D\u0001#\u0015\t\u0019c#\u0001\u0003vi&d\u0017BA\u0013#\u0005!aunZ4bE2,\u0017A\u0002\u0013j]&$H\u0005F\u0001)!\tY\u0012&\u0003\u0002+9\t!QK\\5u\u00031!\u0018m]6t'\u0016\u0014h/[2f+\u0005i\u0003C\u0001\u00187\u001b\u0005y#BA\u000b1\u0015\t\t$'\u0001\u0003d_J,'BA\u001a5\u0003\u0015\u0019XM\u001b3b\u0015\u0005)\u0014aA8sO&\u0011qg\f\u0002\u001c\t\u00164\u0017-\u001e7u)\u0006\u001c8.\u0012=fGV$\u0018n\u001c8TKJ4\u0018nY3\u0002\u0015Q\f7o\u001b)beN,'/F\u0001;!\tYD(D\u0001\u0015\u0013\tiDC\u0001\nUCN\\\u0007+\u0019:b[N\u0004&o\u001c<jI\u0016\u0014\u0018a\u0003;bg.\u001cuN\u001c;fqR,\u0012\u0001\u0011\t\u00037\u0005K!A\u0011\u000f\u0003\u0007%sG/\u0001\u0003c_>$\u0018!\u00049s_\u000e,7o\u001d*fgVdG\u000f\u0006\u0002G\u001dB\u0011q\tT\u0007\u0002\u0011*\u0011\u0011JS\u0001\u0003S>T\u0011aS\u0001\u0005U\u00064\u0018-\u0003\u0002N\u0011\n!a)\u001b7f\u0011\u0015ye\u00011\u0001Q\u0003\tIg\u000e\u0005\u0002R-6\t!K\u0003\u0002T)\u00061q.\u001e;qkRT!!\u0016\u001a\u0002\u000b5|G-\u001a7\n\u0005]\u0013&A\u0003+bg.|U\u000f\u001e9vi\u0006aqN]5hS:\fGnU5{KV\t!\f\u0005\u0002\u001c7&\u0011A\f\b\u0002\u0005\u0019>tw-\u0001\tpe&<\u0017N\\1m'&TXm\u0018\u0013fcR\u0011\u0001f\u0018\u0005\bA\"\t\t\u00111\u0001[\u0003\rAH%M\u0001\u000eE\u00164wN]3Fq\u0016\u001cW\u000f^3\u0015\u0007!\u001a'\u0010C\u0003e\u0013\u0001\u0007Q-\u0001\u0003kg>t\u0007C\u00014x\u001d\t9GO\u0004\u0002ie:\u0011\u0011n\u001c\b\u0003U6l\u0011a\u001b\u0006\u0003Yb\ta\u0001\u0010:p_Rt\u0014\"\u00018\u0002\u00079,G/\u0003\u0002qc\u00069A.\u001b4uo\u0016\u0014'\"\u00018\n\u0005\u0011\u001c(B\u00019r\u0013\t)h/A\u0004qC\u000e\\\u0017mZ3\u000b\u0005\u0011\u001c\u0018B\u0001=z\u0005\u0019Qe+\u00197vK*\u0011QO\u001e\u0005\u0006w&\u0001\r\u0001`\u0001\u0007a\u0006\u0014\u0018-\\:\u0011\u0007u\f)!D\u0001\u007f\u0015\ry\u0018\u0011A\u0001\u0005E\u0006\u001cXMC\u0002\u0002\u0004Q\u000b\u0011\u0002]1sC6,G/\u001a:\n\u0007\u0005\u001daP\u0001\bUCN\\\u0007+\u0019:b[\u0016$XM]:\u0002\u0019\u00054G/\u001a:Fq\u0016\u001cW\u000f^3\u0015\u0007!\ni\u0001C\u0004\u0002\u0010)\u0001\r!!\u0005\u0002\rI,7/\u001e7u!\u0011\t\u0019\"a\u0006\u000e\u0005\u0005U!BA+\u0017\u0013\u0011\tI\"!\u0006\u0003\u0015Q\u000b7o\u001b*fgVdG/\u0001\u0007u_J+7/\u001e7u\u0015N|g\u000eF\u0002f\u0003?Aq!a\u0004\f\u0001\u0004\t\t\"\u0001\u000eeKR,'/\\5oK&3W\u000b]4sC\u0012,'+Z9vSJ,G\r\u0006\u0004\u0002&\u0005]\u0012\u0011\b\t\u00067\u0005\u001d\u00121F\u0005\u0004\u0003Sa\"AB(qi&|g\u000e\u0005\u0003\u0002.\u0005MRBAA\u0018\u0015\r\t\tDF\u0001\u0007Y&l\u0017\u000e^:\n\t\u0005U\u0012q\u0006\u0002\b+B<'/\u00193f\u0011\u0015YH\u00021\u0001}\u0011\u0019\ty\u0001\u0004a\u0001\r\u0006\u0001\"/Z:vYR\u0004FM\u001a#fi\u0006LGn\u001d\u000b\u0005\u0003\u007f\ti\u0005E\u0003\u001c\u0003O\t\t\u0005\u0005\u0003\u0002D\u0005%SBAA#\u0015\r\t9EI\u0001\u0004a\u00124\u0017\u0002BA&\u0003\u000b\u0012!\u0002\u00153g\t\u0016$\u0018-\u001b7t\u0011\u0019\ty!\u0004a\u0001\r\u0006yA-\u001a;fe6Lg.\u001a*f[\u0006\u00148\u000e\u0006\u0004\u0002T\u0005\u0005\u00141\r\t\u00067\u0005\u001d\u0012Q\u000b\t\u0005\u0003/\ni&\u0004\u0002\u0002Z)\u0019\u00111\f&\u0002\t1\fgnZ\u0005\u0005\u0003?\nIF\u0001\u0004TiJLgn\u001a\u0005\u0006w:\u0001\r\u0001 \u0005\u0007\u0003\u001fq\u0001\u0019\u0001$\u0002\r1\fWO\\2i\u0003-9(/\u001b;f%\u0016\u001cX\u000f\u001c;\u0015\u000b!\nY'!\u001c\t\u000f\u0005=\u0001\u00031\u0001\u0002\u0012!1\u0011q\u000e\tA\u0002\u0019\u000b1a\\;u\u0003a\u0019\u0017\r\u001d;ve\u0016,\u00050Z2vi&|gnV1s]&twm\u001d\u000b\u0003\u0003k\u00022aOA<\u0013\r\tI\b\u0006\u0002\u0015)\u0006\u001c8nV1s]&twm\u001d'jgR,g.\u001a:\u00023\r\f\u0007\u000f^;sK\u0016CXmY;uS>t7i\\7qY\u0016$X\r\u001a\u000b\u0003\u0003\u007f\u00022aOAA\u0013\r\t\u0019\t\u0006\u0002\u001f)\u0006\u001c8.\u0012=fGV$\u0018n\u001c8D_6\u0004H.\u001a;fI2K7\u000f^3oKJ\u0004")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/service/InJvmTaskExecutor.class */
public interface InJvmTaskExecutor extends Loggable {
    void code$service$InJvmTaskExecutor$_setter_$tasksService_$eq(final DefaultTaskExecutionService x$1);

    DefaultTaskExecutionService tasksService();

    int taskContext();

    long code$service$InJvmTaskExecutor$$originalSize();

    void code$service$InJvmTaskExecutor$$originalSize_$eq(final long x$1);

    default void afterExecute(final TaskResult result) {
    }

    JsonAST.JValue toResultJson(final TaskResult result);

    static void $init$(final InJvmTaskExecutor $this) {
        $this.code$service$InJvmTaskExecutor$_setter_$tasksService_$eq(new DefaultTaskExecutionService());
        final InJvmTaskExecutor inJvmTaskExecutor = null;
        GlobalNotificationContext.getContext().addListener(new EventListener<TaskExecutionFailedEvent>(inJvmTaskExecutor) { // from class: code.service.InJvmTaskExecutor$$anon$1
            /* JADX INFO: Thrown type has an unknown type hierarchy: scala.runtime.Nothing$ */
            @Override // org.sejda.model.notification.EventListener
            public /* bridge */ /* synthetic */ void onEvent(final AbstractNotificationEvent event) throws Nothing$ {
                throw onEvent((TaskExecutionFailedEvent) event);
            }

            public Nothing$ onEvent(final TaskExecutionFailedEvent event) throws Exception {
                throw event.getFailingCause();
            }
        });
        $this.code$service$InJvmTaskExecutor$$originalSize_$eq(0L);
    }

    default TaskParamsProvider taskParser() {
        return DefaultTaskParamsProvider$.MODULE$;
    }

    default void boot() {
        PdfLibraryUtils$.MODULE$.configure();
    }

    default File processResult(final TaskOutput in) {
        return in.getDestination();
    }

    default void beforeExecute(final JsonAST.JValue json, final TaskParameters params) {
        PdfLibraryUtils$.MODULE$.configurePerTask(params);
        if (params instanceof OptimizeParameters) {
            OptimizeParameters optimizeParameters = (OptimizeParameters) params;
            if (optimizeParameters.getSourceList().size() == 1) {
                code$service$InJvmTaskExecutor$$originalSize_$eq(((File) optimizeParameters.getSourceList().get(0).getSource()).length());
                BoxedUnit boxedUnit = BoxedUnit.UNIT;
                return;
            }
        }
        BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
    }

    default Option<Upgrade> determineIfUpgradeRequired(final TaskParameters params, final File result) {
        return None$.MODULE$;
    }

    default Option<PdfDetails> resultPdfDetails(final File result) {
        return PdfDetails$.MODULE$.parseCached(PdfFileSource.newInstanceNoPassword(result));
    }

    default Option<String> determineRemark(final TaskParameters params, final File result) {
        if ((params instanceof RepairParameters) && ((RepairParameters) params).getSourceList().size() == 1) {
            return resultPdfDetails(result).map(details -> {
                return new StringBuilder(44).append("We recovered ").append(details.pages()).append(" pages from your original file.").toString();
            });
        }
        if ((params instanceof OptimizeParameters) && ((OptimizeParameters) params).getSourceList().size() == 1 && code$service$InJvmTaskExecutor$$originalSize() > 0) {
            long t = result.length();
            String to = HumanReadableSize.toString(t, true);
            int percentage = 100 - ((int) Math.ceil((t * 100) / code$service$InJvmTaskExecutor$$originalSize()));
            if (percentage > 1) {
                return new Some(new StringBuilder(36).append("We compressed your file to ").append(to).append(" (").append(percentage).append("% less)").toString());
            }
            return new Some("Your PDF file is already very well compressed");
        }
        return None$.MODULE$;
    }

    default void launch() {
        boot();
        File in = new File(System.getProperty("task.in"));
        File out = new File(System.getProperty("task.out"));
        File kill = new File(System.getProperty("task.kill"));
        ShutdownHelper$.MODULE$.start(kill);
        JsonAST.JValue jsonIn = package$.MODULE$.parse(Files.toString(in, Charsets.UTF_8));
        try {
            TaskParameters params = taskParser().parse(jsonIn);
            beforeExecute(jsonIn, params);
            TaskResult result = execute$1(in, params);
            afterExecute(result);
            writeResult(result, out);
        } catch (Throwable e) {
            writeResult(handleFailure$1(e, in), out);
            throw e;
        }
    }

    private default TaskResult handleFailure$1(final Throwable e, final File in$1) {
        logger().warn(new StringBuilder(12).append("Task ").append(in$1).append(" failed").toString(), e);
        return TaskResult$.MODULE$.knownFailureReasons(e, taskContext());
    }

    private default TaskResult execute$1(final File in$1, final TaskParameters params$1) {
        try {
            logger().debug(new StringBuilder(16).append("Task ").append(in$1).append(" starting: ").append(params$1).toString());
            TaskWarningsListener warnListener = captureExecutionWarnings();
            TaskExecutionCompletedListener completionListener = captureExecutionCompleted();
            tasksService().execute(params$1);
            logger().debug(new StringBuilder(33).append("Task ").append(in$1).append(" executed, processing output").toString());
            File result = processResult(params$1.getOutput());
            logger().debug(new StringBuilder(30).append("Task ").append(in$1).append(" completed with ").append(warnListener.warnings().size()).append(" warnings").toString());
            Option upgrade = determineIfUpgradeRequired(params$1, result);
            Option remark = determineRemark(params$1, result);
            return TaskResult$.MODULE$.success(result, warnListener.warnings().toSeq(), upgrade, remark, completionListener.taskOutputs().toSeq());
        } catch (Throwable e) {
            return handleFailure$1(e, in$1);
        }
    }

    private default void writeResult(final TaskResult result, final File out) {
        String jsonOut = package$.MODULE$.compactRender(toResultJson(result));
        logger().debug(new StringBuilder(34).append("Task completed, writing out file:\n").append(jsonOut).toString());
        Files.write(jsonOut, out, Charsets.UTF_8);
    }

    private default TaskWarningsListener captureExecutionWarnings() {
        TaskWarningsListener listener = new TaskWarningsListener();
        GlobalNotificationContext.getContext().addListener(listener);
        return listener;
    }

    private default TaskExecutionCompletedListener captureExecutionCompleted() {
        TaskExecutionCompletedListener listener = new TaskExecutionCompletedListener();
        GlobalNotificationContext.getContext().addListener(listener);
        return listener;
    }
}
