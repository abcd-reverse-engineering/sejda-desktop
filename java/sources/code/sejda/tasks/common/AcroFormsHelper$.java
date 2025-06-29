package code.sejda.tasks.common;

import org.sejda.sambox.cos.COSObjectable;
import scala.util.control.NonFatal$;

/* compiled from: AcroFormsHelper.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/AcroFormsHelper$.class */
public final class AcroFormsHelper$ {
    public static final AcroFormsHelper$ MODULE$ = new AcroFormsHelper$();

    private AcroFormsHelper$() {
    }

    public String getObjId(final COSObjectable o) {
        try {
            return new StringBuilder(1).append(o.getCOSObject().id().objectIdentifier.objectNumber()).append("R").toString();
        } catch (Throwable th) {
            if (NonFatal$.MODULE$.apply(th)) {
                return "";
            }
            throw th;
        }
    }
}
