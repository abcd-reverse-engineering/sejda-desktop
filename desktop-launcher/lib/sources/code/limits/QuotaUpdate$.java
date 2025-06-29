package code.limits;

import code.util.JsonExtract$;
import java.io.Serializable;
import net.liftweb.json.JsonAST;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.ModuleSerializationProxy;
import scala.util.Try$;

/* compiled from: PlanLimits.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/limits/QuotaUpdate$.class */
public final class QuotaUpdate$ implements Serializable {
    public static final QuotaUpdate$ MODULE$ = new QuotaUpdate$();

    public QuotaUpdate apply(final int remainingRequests, final long untilResetSeconds) {
        return new QuotaUpdate(remainingRequests, untilResetSeconds);
    }

    public Option<Tuple2<Object, Object>> unapply(final QuotaUpdate x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple2.mcIJ.sp(x$0.remainingRequests(), x$0.untilResetSeconds()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(QuotaUpdate$.class);
    }

    private QuotaUpdate$() {
    }

    public QuotaUpdate fromJson(final JsonAST.JValue json) {
        return new QuotaUpdate(JsonExtract$.MODULE$.toInt(json.$bslash("remainingRequests")), JsonExtract$.MODULE$.toLong(json.$bslash("untilResetSeconds")));
    }

    public Option<QuotaUpdate> fromJsonOpt(final JsonAST.JValue json) {
        return Try$.MODULE$.apply(() -> {
            return MODULE$.fromJson(json);
        }).toOption();
    }
}
