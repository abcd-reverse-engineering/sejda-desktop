package code.model;

import code.sejda.tasks.html.HtmlToPdfParameters;
import code.sejda.tasks.html.HtmlToPdfParameters$;
import code.util.JsonExtract$;
import code.util.Loggable;
import java.io.File;
import net.liftweb.json.JsonAST;
import net.liftweb.json.JsonDSL$;
import net.liftweb.json.package$;
import org.joda.time.DateTime;
import org.sejda.model.parameter.base.TaskParameters;
import org.slf4j.Logger;
import scala.Enumeration;
import scala.Function0;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Some;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;

/* compiled from: Task.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005\rh\u0001B\u0015+\u0001=B\u0001\u0002\u0010\u0001\u0003\u0006\u0004%\t!\u0010\u0005\t\u0013\u0002\u0011\t\u0011)A\u0005}!A!\n\u0001BC\u0002\u0013\u00051\n\u0003\u0005Z\u0001\t\u0005\t\u0015!\u0003M\u0011!Q\u0006A!b\u0001\n\u0003Y\u0006\u0002C8\u0001\u0005\u0003\u0005\u000b\u0011\u0002/\t\u000bA\u0004A\u0011A9\t\u000f]\u0004\u0001\u0019!C\u0001q\"9A\u0010\u0001a\u0001\n\u0003i\bbBA\u0004\u0001\u0001\u0006K!\u001f\u0005\b\u0003#\u0001A\u0011AA\n\u0011\u001d\tY\u0003\u0001C\u0001\u0003[Aq!!\u0012\u0001\t\u0003\t9\u0005C\u0004\u0002L\u0001!\t!a\u0012\t\r\u00055\u0003\u0001\"\u0001>\u0011\u001d\ty\u0005\u0001C\u0001\u0003#Bq!!\u0017\u0001\t\u0003\tY\u0006C\u0005\u0002p\u0001\u0011\r\u0011\"\u0001\u0002r!A\u00111\u0011\u0001!\u0002\u0013\t\u0019\bC\u0005\u0002\u0006\u0002\u0001\r\u0011\"\u0001\u0002\b\"I\u00111\u0012\u0001A\u0002\u0013\u0005\u0011Q\u0012\u0005\t\u0003#\u0003\u0001\u0015)\u0003\u0002\n\"9\u00111\u0013\u0001\u0005\u0002\u0005U\u0005bBAL\u0001\u0011\u0005\u0011Q\u0013\u0005\b\u00033\u0003A\u0011AAK\u0011%\tY\n\u0001a\u0001\n\u0003\ti\nC\u0005\u0002&\u0002\u0001\r\u0011\"\u0001\u0002(\"A\u00111\u0016\u0001!B\u0013\ty\nC\u0004\u00020\u0002!\t!!&\t\u000f\u0005E\u0006\u0001\"\u0001\u00024\"9\u00111\u0018\u0001\u0005\u0002\u0005u\u0006bBAa\u0001\u0011\u0005\u0011\u0011\u000b\u0005\b\u0003\u0007\u0004A\u0011AA)\u0011\u0019\t)\r\u0001C\u00017\"9\u0011q\u0019\u0001\u0005\u0002\u0005M\u0006bBAe\u0001\u0011\u0005\u00111\u001a\u0005\b\u0003\u001b\u0004A\u0011AAf\u0011\u001d\ty\r\u0001C\u0001\u0003#Dq!a5\u0001\t\u0003\t9\u0005C\u0004\u0002V\u0002!\t!a6\u0003\tQ\u000b7o\u001b\u0006\u0003W1\nQ!\\8eK2T\u0011!L\u0001\u0005G>$Wm\u0001\u0001\u0014\u0007\u0001\u0001d\u0007\u0005\u00022i5\t!GC\u00014\u0003\u0015\u00198-\u00197b\u0013\t)$G\u0001\u0004B]f\u0014VM\u001a\t\u0003oij\u0011\u0001\u000f\u0006\u0003s1\nA!\u001e;jY&\u00111\b\u000f\u0002\t\u0019><w-\u00192mK\u0006\u0011\u0011\u000eZ\u000b\u0002}A\u0011qH\u0012\b\u0003\u0001\u0012\u0003\"!\u0011\u001a\u000e\u0003\tS!a\u0011\u0018\u0002\rq\u0012xn\u001c;?\u0013\t)%'\u0001\u0004Qe\u0016$WMZ\u0005\u0003\u000f\"\u0013aa\u0015;sS:<'BA#3\u0003\rIG\rI\u0001\u0007a\u0006\u0014\u0018-\\:\u0016\u00031\u0003\"!T,\u000e\u00039S!a\u0014)\u0002\t\t\f7/\u001a\u0006\u0003#J\u000b\u0011\u0002]1sC6,G/\u001a:\u000b\u0005-\u001a&B\u0001+V\u0003\u0015\u0019XM\u001b3b\u0015\u00051\u0016aA8sO&\u0011\u0001L\u0014\u0002\u000f)\u0006\u001c8\u000eU1sC6,G/\u001a:t\u0003\u001d\u0001\u0018M]1ng\u0002\naa]8ve\u000e,W#\u0001/\u0011\u0005ucgB\u00010j\u001d\tyfM\u0004\u0002aG:\u0011\u0011)Y\u0005\u0002E\u0006\u0019a.\u001a;\n\u0005\u0011,\u0017a\u00027jMR<XM\u0019\u0006\u0002E&\u0011q\r[\u0001\u0005UN|gN\u0003\u0002eK&\u0011!n[\u0001\b\u0015N|g.Q*U\u0015\t9\u0007.\u0003\u0002n]\n1!JV1mk\u0016T!A[6\u0002\u000fM|WO]2fA\u00051A(\u001b8jiz\"BA\u001d;vmB\u00111\u000fA\u0007\u0002U!)Ah\u0002a\u0001}!)!j\u0002a\u0001\u0019\")!l\u0002a\u00019\u0006QA/Y:l%\u0016\u001cX\u000f\u001c;\u0016\u0003e\u0004\"a\u001d>\n\u0005mT#A\u0003+bg.\u0014Vm];mi\u0006qA/Y:l%\u0016\u001cX\u000f\u001c;`I\u0015\fHc\u0001@\u0002\u0004A\u0011\u0011g`\u0005\u0004\u0003\u0003\u0011$\u0001B+oSRD\u0001\"!\u0002\n\u0003\u0003\u0005\r!_\u0001\u0004q\u0012\n\u0014a\u0003;bg.\u0014Vm];mi\u0002B3ACA\u0006!\r\t\u0014QB\u0005\u0004\u0003\u001f\u0011$\u0001\u0003<pY\u0006$\u0018\u000e\\3\u0002\rM$\u0018\r^;t+\t\t)\u0002\u0005\u0003\u0002\u0018\u0005\u0015b\u0002BA\r\u0003CqA!a\u0007\u0002 9\u0019\u0011)!\b\n\u00035J!a\u000b\u0017\n\u0007\u0005\r\"&\u0001\u0006UCN\\7\u000b^1ukNLA!a\n\u0002*\tQA+Y:l'R\fG/^:\u000b\u0007\u0005\r\"&\u0001\u0004sKN,H\u000e^\u000b\u0003\u0003_\u0001R!MA\u0019\u0003kI1!a\r3\u0005\u0019y\u0005\u000f^5p]B!\u0011qGA!\u001b\t\tID\u0003\u0003\u0002<\u0005u\u0012AA5p\u0015\t\ty$\u0001\u0003kCZ\f\u0017\u0002BA\"\u0003s\u0011AAR5mK\u0006ia-Y5mkJ,'+Z1t_:,\"!!\u0013\u0011\tE\n\tDP\u0001\u0012M\u0006LG.\u001e:f%\u0016\f7o\u001c8D_\u0012,\u0017\u0001\u00044bS2,(/Z*uC\u000e\\\u0017\u0001\u0003;j[\u0016$w*\u001e;\u0016\u0005\u0005M\u0003cA\u0019\u0002V%\u0019\u0011q\u000b\u001a\u0003\u000f\t{w\u000e\\3b]\u0006Aq/\u0019:oS:<7/\u0006\u0002\u0002^A)\u0011qLA5}9!\u0011\u0011MA3\u001d\r\t\u00151M\u0005\u0002g%\u0019\u0011q\r\u001a\u0002\u000fA\f7m[1hK&!\u00111NA7\u0005\r\u0019V-\u001d\u0006\u0004\u0003O\u0012\u0014aC3ocV,W/\u001a#bi\u0016,\"!a\u001d\u0011\t\u0005U\u0014qP\u0007\u0003\u0003oRA!!\u001f\u0002|\u0005!A/[7f\u0015\r\ti(V\u0001\u0005U>$\u0017-\u0003\u0003\u0002\u0002\u0006]$\u0001\u0003#bi\u0016$\u0016.\\3\u0002\u0019\u0015t\u0017/^3vK\u0012\u000bG/\u001a\u0011\u0002#A\u0014xnY3tg&tw\rR1uK>\u0003H/\u0006\u0002\u0002\nB)\u0011'!\r\u0002t\u0005)\u0002O]8dKN\u001c\u0018N\\4ECR,w\n\u001d;`I\u0015\fHc\u0001@\u0002\u0010\"I\u0011QA\u000b\u0002\u0002\u0003\u0007\u0011\u0011R\u0001\u0013aJ|7-Z:tS:<G)\u0019;f\u001fB$\b%A\u0005p]\u001a\u000b\u0017\u000e\\;sKR\ta0A\u0005p]N+8mY3tg\u000611-\u00198dK2\f\u0001\u0002Z8DC:\u001cW\r\\\u000b\u0003\u0003?\u0003B!MAQ}&\u0019\u00111\u0015\u001a\u0003\u0013\u0019+hn\u0019;j_:\u0004\u0014\u0001\u00043p\u0007\u0006t7-\u001a7`I\u0015\fHc\u0001@\u0002*\"I\u0011QA\u000e\u0002\u0002\u0003\u0007\u0011qT\u0001\nI>\u001c\u0015M\\2fY\u0002B3\u0001HA\u0006\u0003)\u0001(o\\2fgNLgnZ\u0001\u0012aJ|7-Z:tS:<7+Z2p]\u0012\u001cHCAA[!\r\t\u0014qW\u0005\u0004\u0003s\u0013$\u0001\u0002'p]\u001e\faB]3dK&4X\r\u001a*fgVdG\u000fF\u0002\u007f\u0003\u007fCQa^\u0010A\u0002e\f\u0011bY8na2,G/\u001a3\u0002\r\u0019\f\u0017\u000e\\3e\u0003\u0019!xNS:p]\u0006!RM\\9vKV,GMR8s\u0013:\u001cVmY8oIN\f!B[:p]N{WO]2f)\u0005q\u0014!B0usB,\u0017AC5t\r&dGnU5h]R\u0011\u00111K\u0001\u0007e\u0016l\u0017M]6\u0002\u001fM\u0004XmY5gS\u000e$U\r^1jYN,\"!!7\u0011\t\u0005m\u0017\u0011]\u0007\u0003\u0003;TA!a8\u0002>\u0005!A.\u00198h\u0013\r9\u0015Q\u001c")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/model/Task.class */
public class Task implements Loggable {
    private final String id;
    private final TaskParameters params;
    private final JsonAST.JValue source;
    private volatile TaskResult taskResult;
    private final DateTime enqueueDate;
    private Option<DateTime> processingDateOpt;
    private volatile Function0<BoxedUnit> doCancel;
    private transient Logger logger;
    private volatile transient boolean bitmap$trans$0;

    public void onFailure() {
    }

    public void onSuccess() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v8, types: [code.model.Task] */
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

    public String id() {
        return this.id;
    }

    public TaskParameters params() {
        return this.params;
    }

    public JsonAST.JValue source() {
        return this.source;
    }

    public Task(final String id, final TaskParameters params, final JsonAST.JValue source) {
        this.id = id;
        this.params = params;
        this.source = source;
        Loggable.$init$(this);
        this.taskResult = TaskResult$.MODULE$.queued();
        this.enqueueDate = new DateTime();
        this.processingDateOpt = None$.MODULE$;
        this.doCancel = () -> {
            this.receivedResult(TaskResult$.MODULE$.failureCancelled());
        };
    }

    public TaskResult taskResult() {
        return this.taskResult;
    }

    public void taskResult_$eq(final TaskResult x$1) {
        this.taskResult = x$1;
    }

    public Enumeration.Value status() {
        return taskResult().status();
    }

    public Option<File> result() {
        return taskResult().result();
    }

    public Option<String> failureReason() {
        return taskResult().failureReason();
    }

    public Option<String> failureReasonCode() {
        return taskResult().failureReasonCode();
    }

    public String failureStack() {
        return (String) taskResult().failureStack().getOrElse(() -> {
            return "";
        });
    }

    public boolean timedOut() {
        return taskResult().timedOut();
    }

    public Seq<String> warnings() {
        return taskResult().warnings();
    }

    public DateTime enqueueDate() {
        return this.enqueueDate;
    }

    public Option<DateTime> processingDateOpt() {
        return this.processingDateOpt;
    }

    public void processingDateOpt_$eq(final Option<DateTime> x$1) {
        this.processingDateOpt = x$1;
    }

    public void cancel() {
        Enumeration.Value valueStatus = status();
        Enumeration.Value valueQueued = TaskStatus$.MODULE$.Queued();
        if (valueStatus != null ? !valueStatus.equals(valueQueued) : valueQueued != null) {
            Enumeration.Value valueStatus2 = status();
            Enumeration.Value valueProcessing = TaskStatus$.MODULE$.Processing();
            if (valueStatus2 == null) {
                if (valueProcessing != null) {
                    return;
                }
            } else if (!valueStatus2.equals(valueProcessing)) {
                return;
            }
        }
        logger().info(new StringBuilder(16).append("Cancelling task ").append(id()).toString());
        doCancel().apply$mcV$sp();
    }

    public Function0<BoxedUnit> doCancel() {
        return this.doCancel;
    }

    public void doCancel_$eq(final Function0<BoxedUnit> x$1) {
        this.doCancel = x$1;
    }

    public void processing() {
        taskResult_$eq(TaskResult$.MODULE$.processing());
        processingDateOpt_$eq(new Some(DateTime.now()));
    }

    public long processingSeconds() {
        Some someProcessingDateOpt = processingDateOpt();
        if (!(someProcessingDateOpt instanceof Some)) {
            return 0L;
        }
        DateTime processingDate = (DateTime) someProcessingDateOpt.value();
        return (DateTime.now().getMillis() - processingDate.getMillis()) / 1000;
    }

    public void receivedResult(final TaskResult taskResult) {
        logger().debug(new StringBuilder(36).append("Received task results, id: ").append(id()).append(" result: ").append(taskResult).toString());
        taskResult_$eq(taskResult);
        Enumeration.Value valueStatus = status();
        Enumeration.Value valueFailed = TaskStatus$.MODULE$.Failed();
        if (valueFailed != null ? valueFailed.equals(valueStatus) : valueStatus == null) {
            onFailure();
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
            return;
        }
        Enumeration.Value valueCompleted = TaskStatus$.MODULE$.Completed();
        if (valueCompleted != null ? valueCompleted.equals(valueStatus) : valueStatus == null) {
            onSuccess();
            BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
        } else {
            BoxedUnit boxedUnit3 = BoxedUnit.UNIT;
        }
    }

    public boolean completed() {
        Enumeration.Value valueStatus = status();
        Enumeration.Value valueCompleted = TaskStatus$.MODULE$.Completed();
        return valueStatus != null ? valueStatus.equals(valueCompleted) : valueCompleted == null;
    }

    public boolean failed() {
        Enumeration.Value valueStatus = status();
        Enumeration.Value valueFailed = TaskStatus$.MODULE$.Failed();
        return valueStatus != null ? valueStatus.equals(valueFailed) : valueFailed == null;
    }

    public JsonAST.JValue toJson() {
        JsonDSL$.MODULE$.pair2Assoc(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("status"), status().toString()), x -> {
            return JsonDSL$.MODULE$.string2jvalue(x);
        }).$tilde(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("remark"), remark()), opt -> {
            return JsonDSL$.MODULE$.option2jvalue(opt, x2 -> {
                return JsonDSL$.MODULE$.string2jvalue(x2);
            });
        });
        return JsonDSL$.MODULE$.jobject2assoc(JsonDSL$.MODULE$.jobject2assoc(JsonDSL$.MODULE$.jobject2assoc(JsonDSL$.MODULE$.pair2Assoc(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("failureReason"), failureReason()), opt2 -> {
            return JsonDSL$.MODULE$.option2jvalue(opt2, x2 -> {
                return JsonDSL$.MODULE$.string2jvalue(x2);
            });
        }).$tilde(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("failureReasonCode"), failureReasonCode()), opt3 -> {
            return JsonDSL$.MODULE$.option2jvalue(opt3, x2 -> {
                return JsonDSL$.MODULE$.string2jvalue(x2);
            });
        })).$tilde(JsonDSL$.MODULE$.pair2jvalue(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("resultPath"), result().map(x$1 -> {
            return x$1.getAbsolutePath();
        })), opt4 -> {
            return JsonDSL$.MODULE$.option2jvalue(opt4, x2 -> {
                return JsonDSL$.MODULE$.string2jvalue(x2);
            });
        }))).$tilde(JsonDSL$.MODULE$.pair2jvalue(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("resultFilename"), result().map(x$2 -> {
            return x$2.getName();
        })), opt5 -> {
            return JsonDSL$.MODULE$.option2jvalue(opt5, x2 -> {
                return JsonDSL$.MODULE$.string2jvalue(x2);
            });
        }))).$tilde(JsonDSL$.MODULE$.pair2jvalue(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("resultFileSize"), result().map(x$3 -> {
            return BoxesRunTime.boxToLong(x$3.length());
        })), opt6 -> {
            return JsonDSL$.MODULE$.option2jvalue(opt6, x2 -> {
                return $anonfun$toJson$16(BoxesRunTime.unboxToLong(x2));
            });
        }));
    }

    public static final /* synthetic */ JsonAST.JInt $anonfun$toJson$16(final long x) {
        return JsonDSL$.MODULE$.long2jvalue(x);
    }

    public long enqueuedForInSeconds() {
        return (DateTime.now().getMillis() - enqueueDate().getMillis()) / 1000;
    }

    public String jsonSource() {
        return package$.MODULE$.compactRender(source());
    }

    public String _type() {
        return JsonExtract$.MODULE$.toStringOr(source().$bslash("type"), "unknown");
    }

    public boolean isFillSign() {
        String str_type = _type();
        return str_type != null ? str_type.equals("fillSign") : "fillSign" == 0;
    }

    public Option<String> remark() {
        return taskResult().remark();
    }

    public String specificDetails() {
        TaskParameters taskParametersParams = params();
        if (!(taskParametersParams instanceof HtmlToPdfParameters)) {
            return "";
        }
        HtmlToPdfParameters htmlToPdfParameters = (HtmlToPdfParameters) taskParametersParams;
        return new StringBuilder(20).append("urls: ").append(htmlToPdfParameters.urls().size()).append(" [").append(htmlToPdfParameters.urls().mkString(", ")).append("] htmlCode: ").append(htmlToPdfParameters.htmlCode().map(s -> {
            return HtmlToPdfParameters$.MODULE$.redactedHtmlCode(s);
        })).toString();
    }
}
