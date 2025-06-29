package code.sejda.tasks.flatten;

import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;
import scala.reflect.ScalaSignature;

/* compiled from: FlattenParameters.scala */
@ScalaSignature(bytes = "\u0006\u0005)3AAC\u0006\u0001)!A!\u0005\u0001BC\u0002\u0013\u00051\u0005\u0003\u0005)\u0001\t\u0005\t\u0015!\u0003%\u0011!I\u0003A!b\u0001\n\u0003Q\u0003\u0002C\u0019\u0001\u0005\u0003\u0005\u000b\u0011B\u0016\t\u000bI\u0002A\u0011A\u001a\b\u000f]Z\u0011\u0011!E\u0001q\u00199!bCA\u0001\u0012\u0003I\u0004\"\u0002\u001a\b\t\u0003i\u0004b\u0002 \b#\u0003%\ta\u0010\u0002\u0012\r2\fG\u000f^3o!\u0006\u0014\u0018-\\3uKJ\u001c(B\u0001\u0007\u000e\u0003\u001d1G.\u0019;uK:T!AD\b\u0002\u000bQ\f7o[:\u000b\u0005A\t\u0012!B:fU\u0012\f'\"\u0001\n\u0002\t\r|G-Z\u0002\u0001'\t\u0001Q\u0003\u0005\u0002\u0017A5\tqC\u0003\u0002\u00193\u0005!!-Y:f\u0015\tQ2$A\u0005qCJ\fW.\u001a;fe*\u0011A$H\u0001\u0006[>$W\r\u001c\u0006\u0003!yQ\u0011aH\u0001\u0004_J<\u0017BA\u0011\u0018\u0005%jU\u000f\u001c;ja2,\u0007\u000b\u001a4T_V\u00148-Z'vYRL\u0007\u000f\\3PkR\u0004X\u000f\u001e)be\u0006lW\r^3sg\u0006!Qn\u001c3f+\u0005!\u0003CA\u0013'\u001b\u0005Y\u0011BA\u0014\f\u0005-1E.\u0019;uK:lu\u000eZ3\u0002\u000b5|G-\u001a\u0011\u0002\u0007\u0011\u0004\u0018.F\u0001,!\tas&D\u0001.\u0015\u0005q\u0013!B:dC2\f\u0017B\u0001\u0019.\u0005\rIe\u000e^\u0001\u0005IBL\u0007%\u0001\u0004=S:LGO\u0010\u000b\u0004iU2\u0004CA\u0013\u0001\u0011\u0015\u0011S\u00011\u0001%\u0011\u001dIS\u0001%AA\u0002-\n\u0011C\u00127biR,g\u000eU1sC6,G/\u001a:t!\t)sa\u0005\u0002\buA\u0011AfO\u0005\u0003y5\u0012a!\u00118z%\u00164G#\u0001\u001d\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00133+\u0005\u0001%FA\u0016BW\u0005\u0011\u0005CA\"I\u001b\u0005!%BA#G\u0003%)hn\u00195fG.,GM\u0003\u0002H[\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005%#%!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0002")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/flatten/FlattenParameters.class */
public class FlattenParameters extends MultiplePdfSourceMultipleOutputParameters {
    private final FlattenMode mode;
    private final int dpi;

    public FlattenMode mode() {
        return this.mode;
    }

    public FlattenParameters(final FlattenMode mode, final int dpi) {
        this.mode = mode;
        this.dpi = dpi;
    }

    public int dpi() {
        return this.dpi;
    }
}
