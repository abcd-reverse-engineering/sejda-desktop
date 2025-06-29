package code.model;

import scala.Option;
import scala.reflect.ScalaSignature;

/* compiled from: exceptions.scala */
@ScalaSignature(bytes = "\u0006\u0005\r4AAC\u0006\u0001!!Aq\u0004\u0001B\u0001B\u0003%\u0001\u0005\u0003\u0005)\u0001\t\u0005\t\u0015!\u0003*\u0011\u0015a\u0003\u0001\"\u0001.\u000f\u0015\u00114\u0002#\u00014\r\u0015Q1\u0002#\u00015\u0011\u0015aS\u0001\"\u0001B\u0011\u0015\u0011U\u0001\"\u0001D\u0011\u001dyU!%A\u0005\u0002ACqaW\u0003\u0002\u0002\u0013%ALA\tE_^tGn\\1e\u000bb\u001cW\r\u001d;j_:T!\u0001D\u0007\u0002\u000b5|G-\u001a7\u000b\u00039\tAaY8eK\u000e\u00011C\u0001\u0001\u0012!\t\u0011BD\u0004\u0002\u001439\u0011AcF\u0007\u0002+)\u0011acD\u0001\u0007yI|w\u000e\u001e \n\u0003a\tQa]2bY\u0006L!AG\u000e\u0002\u000fA\f7m[1hK*\t\u0001$\u0003\u0002\u001e=\t\u0001\"+\u001e8uS6,W\t_2faRLwN\u001c\u0006\u00035m\t1!\\:h!\t\tSE\u0004\u0002#GA\u0011AcG\u0005\u0003Im\ta\u0001\u0015:fI\u00164\u0017B\u0001\u0014(\u0005\u0019\u0019FO]5oO*\u0011AeG\u0001\u0006G\u0006,8/\u001a\t\u0003%)J!a\u000b\u0010\u0003\u0013QC'o\\<bE2,\u0017A\u0002\u001fj]&$h\bF\u0002/aE\u0002\"a\f\u0001\u000e\u0003-AQaH\u0002A\u0002\u0001Bq\u0001K\u0002\u0011\u0002\u0003\u0007\u0011&A\tE_^tGn\\1e\u000bb\u001cW\r\u001d;j_:\u0004\"aL\u0003\u0014\u0007\u0015)\u0014\b\u0005\u00027o5\t1$\u0003\u000297\t1\u0011I\\=SK\u001a\u0004\"AO \u000e\u0003mR!\u0001P\u001f\u0002\u0005%|'\"\u0001 \u0002\t)\fg/Y\u0005\u0003\u0001n\u0012AbU3sS\u0006d\u0017N_1cY\u0016$\u0012aM\u0001\u0006CB\u0004H.\u001f\u000b\u0005]\u0011+%\nC\u0003 \u000f\u0001\u0007\u0001\u0005C\u0003G\u000f\u0001\u0007q)\u0001\u0006ti\u0006$Xo]\"pI\u0016\u0004\"A\u000e%\n\u0005%[\"aA%oi\")1j\u0002a\u0001\u0019\u0006i\u0001O]8ys\u0016\u0013(o\u001c:PaR\u00042AN'!\u0013\tq5D\u0001\u0004PaRLwN\\\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000f\n\u001a\u0016\u0003ES#!\u000b*,\u0003M\u0003\"\u0001V-\u000e\u0003US!AV,\u0002\u0013Ut7\r[3dW\u0016$'B\u0001-\u001c\u0003)\tgN\\8uCRLwN\\\u0005\u00035V\u0013\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u000319(/\u001b;f%\u0016\u0004H.Y2f)\u0005i\u0006C\u00010b\u001b\u0005y&B\u00011>\u0003\u0011a\u0017M\\4\n\u0005\t|&AB(cU\u0016\u001cG\u000f")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/model/DownloadException.class */
public class DownloadException extends RuntimeException {
    public static DownloadException apply(final String msg, final int statusCode, final Option<String> proxyErrorOpt) {
        return DownloadException$.MODULE$.apply(msg, statusCode, proxyErrorOpt);
    }

    public DownloadException(final String msg, final Throwable cause) {
        super(msg, cause);
    }
}
