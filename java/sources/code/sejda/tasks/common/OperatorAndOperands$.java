package code.sejda.tasks.common;

import java.io.Serializable;
import java.util.List;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.cos.COSBase;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.collection.IterableOnceOps;
import scala.collection.JavaConverters$;
import scala.collection.immutable.Seq;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: OperatorAndOperands.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/OperatorAndOperands$.class */
public final class OperatorAndOperands$ implements Serializable {
    public static final OperatorAndOperands$ MODULE$ = new OperatorAndOperands$();

    public OperatorAndOperands apply(final Operator operator, final Seq<COSBase> operands) {
        return new OperatorAndOperands(operator, operands);
    }

    public Option<Tuple2<Operator, Seq<COSBase>>> unapply(final OperatorAndOperands x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple2(x$0.operator(), x$0.operands()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(OperatorAndOperands$.class);
    }

    private OperatorAndOperands$() {
    }

    public OperatorAndOperands apply(final Operator operator, final List<COSBase> operands) {
        return new OperatorAndOperands(operator, ((IterableOnceOps) JavaConverters$.MODULE$.asScalaBufferConverter(operands).asScala()).toList());
    }
}
