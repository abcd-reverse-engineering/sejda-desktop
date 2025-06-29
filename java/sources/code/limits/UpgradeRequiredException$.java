package code.limits;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: UpgradeRequiredException.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/limits/UpgradeRequiredException$.class */
public final class UpgradeRequiredException$ extends AbstractFunction1<Upgrade, UpgradeRequiredException> implements Serializable {
    public static final UpgradeRequiredException$ MODULE$ = new UpgradeRequiredException$();

    public final String toString() {
        return "UpgradeRequiredException";
    }

    public UpgradeRequiredException apply(final Upgrade upgrade) {
        return new UpgradeRequiredException(upgrade);
    }

    public Option<Upgrade> unapply(final UpgradeRequiredException x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(x$0.upgrade());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(UpgradeRequiredException$.class);
    }

    private UpgradeRequiredException$() {
    }
}
