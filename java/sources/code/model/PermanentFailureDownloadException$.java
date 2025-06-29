package code.model;

import java.io.Serializable;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: exceptions.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/model/PermanentFailureDownloadException$.class */
public final class PermanentFailureDownloadException$ implements Serializable {
    public static final PermanentFailureDownloadException$ MODULE$ = new PermanentFailureDownloadException$();

    public Throwable $lessinit$greater$default$2() {
        return null;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(PermanentFailureDownloadException$.class);
    }

    private PermanentFailureDownloadException$() {
    }
}
