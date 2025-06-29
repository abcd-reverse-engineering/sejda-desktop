package code.model;

import scala.Enumeration;
import scala.Enumeration$ValueOrdering$;
import scala.Enumeration$ValueSet$;
import scala.reflect.ScalaSignature;

/* compiled from: Task.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0019;QAD\b\t\u0002Q1QAF\b\t\u0002]AQAH\u0001\u0005\u0002})AAF\u0001\u0001A!9A%\u0001b\u0001\n\u0003)\u0003B\u0002\u0014\u0002A\u0003%\u0001\u0005C\u0004(\u0003\t\u0007I\u0011A\u0013\t\r!\n\u0001\u0015!\u0003!\u0011\u001dI\u0013A1A\u0005\u0002\u0015BaAK\u0001!\u0002\u0013\u0001\u0003bB\u0016\u0002\u0005\u0004%\t!\n\u0005\u0007Y\u0005\u0001\u000b\u0011\u0002\u0011\t\u000b5\nA\u0011\u0001\u0018\t\u000fq\n\u0011\u0011!C\u0005{\u0005QA+Y:l'R\fG/^:\u000b\u0005A\t\u0012!B7pI\u0016d'\"\u0001\n\u0002\t\r|G-Z\u0002\u0001!\t)\u0012!D\u0001\u0010\u0005)!\u0016m]6Ti\u0006$Xo]\n\u0003\u0003a\u0001\"!\u0007\u000f\u000e\u0003iQ\u0011aG\u0001\u0006g\u000e\fG.Y\u0005\u0003;i\u00111\"\u00128v[\u0016\u0014\u0018\r^5p]\u00061A(\u001b8jiz\"\u0012\u0001\u0006\t\u0003C\tj\u0011!A\u0005\u0003Gq\u0011QAV1mk\u0016\fa!U;fk\u0016$W#\u0001\u0011\u0002\u000fE+X-^3eA\u0005Q\u0001K]8dKN\u001c\u0018N\\4\u0002\u0017A\u0013xnY3tg&tw\rI\u0001\n\u0007>l\u0007\u000f\\3uK\u0012\f!bQ8na2,G/\u001a3!\u0003\u00191\u0015-\u001b7fI\u00069a)Y5mK\u0012\u0004\u0013A\u00034s_6\u001cFO]5oOR\u0011\u0001e\f\u0005\u0006a1\u0001\r!M\u0001\u0002gB\u0011!'\u000f\b\u0003g]\u0002\"\u0001\u000e\u000e\u000e\u0003UR!AN\n\u0002\rq\u0012xn\u001c;?\u0013\tA$$\u0001\u0004Qe\u0016$WMZ\u0005\u0003um\u0012aa\u0015;sS:<'B\u0001\u001d\u001b\u000319(/\u001b;f%\u0016\u0004H.Y2f)\u0005q\u0004CA E\u001b\u0005\u0001%BA!C\u0003\u0011a\u0017M\\4\u000b\u0003\r\u000bAA[1wC&\u0011Q\t\u0011\u0002\u0007\u001f\nTWm\u0019;")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/model/TaskStatus.class */
public final class TaskStatus {
    public static Enumeration.Value fromString(final String s) {
        return TaskStatus$.MODULE$.fromString(s);
    }

    public static Enumeration.Value Failed() {
        return TaskStatus$.MODULE$.Failed();
    }

    public static Enumeration.Value Completed() {
        return TaskStatus$.MODULE$.Completed();
    }

    public static Enumeration.Value Processing() {
        return TaskStatus$.MODULE$.Processing();
    }

    public static Enumeration.Value Queued() {
        return TaskStatus$.MODULE$.Queued();
    }

    public static Enumeration$ValueSet$ ValueSet() {
        return TaskStatus$.MODULE$.ValueSet();
    }

    public static Enumeration$ValueOrdering$ ValueOrdering() {
        return TaskStatus$.MODULE$.ValueOrdering();
    }

    public static Enumeration.Value withName(final String s) {
        return TaskStatus$.MODULE$.withName(s);
    }

    public static Enumeration.Value apply(final int x) {
        return TaskStatus$.MODULE$.apply(x);
    }

    public static int maxId() {
        return TaskStatus$.MODULE$.maxId();
    }

    public static Enumeration.ValueSet values() {
        return TaskStatus$.MODULE$.values();
    }

    public static String toString() {
        return TaskStatus$.MODULE$.toString();
    }
}
