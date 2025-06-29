package code.model;

import java.io.Serializable;
import scala.Option;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: exceptions.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/model/DownloadException$.class */
public final class DownloadException$ implements Serializable {
    public static final DownloadException$ MODULE$ = new DownloadException$();

    public Throwable $lessinit$greater$default$2() {
        return null;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(DownloadException$.class);
    }

    private DownloadException$() {
    }

    public DownloadException apply(final String msg, final int statusCode, final Option<String> proxyErrorOpt) {
        String proxyError = (String) proxyErrorOpt.getOrElse(() -> {
            return "";
        });
        if (proxyError.contains("ERR_DNS_FAIL") || proxyError.contains("ERR_CONNECT_FAIL")) {
            return new PermanentFailureDownloadException(msg, PermanentFailureDownloadException$.MODULE$.$lessinit$greater$default$2());
        }
        if (statusCode == 404 || statusCode == 403 || statusCode == 401 || statusCode == 429 || statusCode == 460) {
            return new PermanentFailureDownloadException(msg, PermanentFailureDownloadException$.MODULE$.$lessinit$greater$default$2());
        }
        return new TemporaryFailureDownloadException(msg, TemporaryFailureDownloadException$.MODULE$.$lessinit$greater$default$2());
    }
}
