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
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/limits/Upgrade$.class */
public final class Upgrade$ implements Serializable {
    public static final Upgrade$ MODULE$ = new Upgrade$();

    public Option<Object> $lessinit$greater$default$2() {
        return None$.MODULE$;
    }

    public Upgrade apply(final String reason, final Option<Object> untilResetSeconds) {
        return new Upgrade(reason, untilResetSeconds);
    }

    public Option<Object> apply$default$2() {
        return None$.MODULE$;
    }

    public Option<Tuple2<String, Option<Object>>> unapply(final Upgrade x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple2(x$0.reason(), x$0.untilResetSeconds()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Upgrade$.class);
    }

    private Upgrade$() {
    }

    public Upgrade fromJson(final JsonAST.JValue json) {
        return new Upgrade(JsonExtract$.MODULE$.toString(json.$bslash("reason")), JsonExtract$.MODULE$.toLongOption(json.$bslash("untilResetSeconds")));
    }

    public Option<Upgrade> fromJsonOpt(final JsonAST.JValue json) {
        return Try$.MODULE$.apply(() -> {
            return MODULE$.fromJson(json);
        }).toOption();
    }
}
