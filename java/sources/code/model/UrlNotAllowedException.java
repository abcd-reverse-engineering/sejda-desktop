package code.model;

import scala.reflect.ScalaSignature;

/* compiled from: exceptions.scala */
@ScalaSignature(bytes = "\u0006\u0005%2A\u0001B\u0003\u0001\u0015!A\u0011\u0004\u0001B\u0001B\u0003%!\u0004\u0003\u0005#\u0001\t\u0005\t\u0015!\u0003\u001b\u0011\u0015\u0019\u0003\u0001\"\u0001%\u0005Y)&\u000f\u001c(pi\u0006cGn\\<fI\u0016C8-\u001a9uS>t'B\u0001\u0004\b\u0003\u0015iw\u000eZ3m\u0015\u0005A\u0011\u0001B2pI\u0016\u001c\u0001a\u0005\u0002\u0001\u0017A\u0011AB\u0006\b\u0003\u001bMq!AD\t\u000e\u0003=Q!\u0001E\u0005\u0002\rq\u0012xn\u001c;?\u0013\u0005\u0011\u0012!B:dC2\f\u0017B\u0001\u000b\u0016\u0003\u001d\u0001\u0018mY6bO\u0016T\u0011AE\u0005\u0003/a\u0011\u0001CU;oi&lW-\u0012=dKB$\u0018n\u001c8\u000b\u0005Q)\u0012aA7tOB\u00111d\b\b\u00039u\u0001\"AD\u000b\n\u0005y)\u0012A\u0002)sK\u0012,g-\u0003\u0002!C\t11\u000b\u001e:j]\u001eT!AH\u000b\u0002\u0007U\u0014H.\u0001\u0004=S:LGO\u0010\u000b\u0004K\u001dB\u0003C\u0001\u0014\u0001\u001b\u0005)\u0001\"B\r\u0004\u0001\u0004Q\u0002\"\u0002\u0012\u0004\u0001\u0004Q\u0002")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/model/UrlNotAllowedException.class */
public class UrlNotAllowedException extends RuntimeException {
    public UrlNotAllowedException(final String msg, final String url) {
        super(new StringBuilder(2).append(msg).append(": ").append(url).toString());
    }
}
