package code.sejda.tasks.common.prefix;

import org.apache.commons.lang3.StringUtils;
import org.sejda.core.support.prefix.model.PrefixTransformationContext;
import org.sejda.core.support.prefix.processor.PrefixProcessor;
import org.sejda.model.util.IOUtils;
import scala.Option$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;

/* compiled from: SimpleTextPrefixProcessor.scala */
@ScalaSignature(bytes = "\u0006\u0005\u00013A\u0001B\u0003\u0001!!)q\u0005\u0001C\u0001Q!)1\u0006\u0001C!Y!)1\b\u0001C!y\tI2+[7qY\u0016$V\r\u001f;Qe\u00164\u0017\u000e\u001f)s_\u000e,7o]8s\u0015\t1q!\u0001\u0004qe\u00164\u0017\u000e\u001f\u0006\u0003\u0011%\taaY8n[>t'B\u0001\u0006\f\u0003\u0015!\u0018m]6t\u0015\taQ\"A\u0003tK*$\u0017MC\u0001\u000f\u0003\u0011\u0019w\u000eZ3\u0004\u0001M\u0019\u0001!E\r\u0011\u0005I9R\"A\n\u000b\u0005Q)\u0012\u0001\u00027b]\u001eT\u0011AF\u0001\u0005U\u00064\u0018-\u0003\u0002\u0019'\t1qJ\u00196fGR\u0004\"AG\u0013\u000e\u0003mQ!\u0001H\u000f\u0002\u0013A\u0014xnY3tg>\u0014(B\u0001\u0004\u001f\u0015\ty\u0002%A\u0004tkB\u0004xN\u001d;\u000b\u0005\u0005\u0012\u0013\u0001B2pe\u0016T!\u0001D\u0012\u000b\u0003\u0011\n1a\u001c:h\u0013\t13DA\bQe\u00164\u0017\u000e\u001f)s_\u000e,7o]8s\u0003\u0019a\u0014N\\5u}Q\t\u0011\u0006\u0005\u0002+\u00015\tQ!\u0001\u0004bG\u000e,\u0007\u000f\u001e\u000b\u0003[M\u0002\"AL\u0019\u000e\u0003=R\u0011\u0001M\u0001\u0006g\u000e\fG.Y\u0005\u0003e=\u0012A!\u00168ji\")AG\u0001a\u0001k\u000591m\u001c8uKb$\bC\u0001\u001c:\u001b\u00059$B\u0001\u001d\u001e\u0003\u0015iw\u000eZ3m\u0013\tQtGA\u000eQe\u00164\u0017\u000e\u001f+sC:\u001chm\u001c:nCRLwN\\\"p]R,\u0007\u0010^\u0001\u0006_J$WM\u001d\u000b\u0002{A\u0011aFP\u0005\u0003\u007f=\u00121!\u00138u\u0001")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/prefix/SimpleTextPrefixProcessor.class */
public class SimpleTextPrefixProcessor implements PrefixProcessor {
    @Override // java.util.function.Consumer
    public void accept(final PrefixTransformationContext context) {
        if (context.currentPrefix().contains("[TEXT]")) {
            Option$.MODULE$.apply(context.request()).map(x$1 -> {
                return x$1.getText();
            }).map(input -> {
                return IOUtils.toSafeFilename(input);
            }).filter(x$12 -> {
                return BoxesRunTime.boxToBoolean(StringUtils.isNotBlank(x$12));
            }).map(o -> {
                return context.currentPrefix().replace("[TEXT]", o);
            }).foreach(t -> {
                $anonfun$accept$5(context, t);
                return BoxedUnit.UNIT;
            });
        }
    }

    public static final /* synthetic */ void $anonfun$accept$5(final PrefixTransformationContext context$1, final String t) {
        context$1.currentPrefix(t);
        context$1.uniqueNames(true);
    }

    @Override // org.sejda.core.support.prefix.processor.PrefixProcessor
    public int order() {
        return -1;
    }
}
