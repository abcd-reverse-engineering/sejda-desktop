package code.model;

import java.io.Serializable;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: exceptions.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/model/TemporaryFailureDownloadException$.class */
public final class TemporaryFailureDownloadException$ implements Serializable {
    public static final TemporaryFailureDownloadException$ MODULE$ = new TemporaryFailureDownloadException$();

    public Throwable $lessinit$greater$default$2() {
        return null;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(TemporaryFailureDownloadException$.class);
    }

    private TemporaryFailureDownloadException$() {
    }
}
