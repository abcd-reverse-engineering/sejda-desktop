package code.model;

import java.io.Serializable;
import scala.runtime.AbstractFunction0;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: exceptions.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/model/CaptchaRequiredException$.class */
public final class CaptchaRequiredException$ extends AbstractFunction0<CaptchaRequiredException> implements Serializable {
    public static final CaptchaRequiredException$ MODULE$ = new CaptchaRequiredException$();

    public final String toString() {
        return "CaptchaRequiredException";
    }

    /* renamed from: apply, reason: merged with bridge method [inline-methods] */
    public CaptchaRequiredException m8apply() {
        return new CaptchaRequiredException();
    }

    public boolean unapply(final CaptchaRequiredException x$0) {
        return x$0 != null;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(CaptchaRequiredException$.class);
    }

    private CaptchaRequiredException$() {
    }
}
