package code.service;

import net.liftweb.json.JsonAST;
import org.sejda.model.parameter.base.TaskParameters;
import scala.reflect.ScalaSignature;

/* compiled from: InJvmTaskExecutor.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0001;Q\u0001B\u0003\t\u0002)1Q\u0001D\u0003\t\u00025AQaF\u0001\u0005\u0002aAQ!G\u0001\u0005Bi\t\u0011\u0004R3gCVdG\u000fV1tWB\u000b'/Y7t!J|g/\u001b3fe*\u0011aaB\u0001\bg\u0016\u0014h/[2f\u0015\u0005A\u0011\u0001B2pI\u0016\u001c\u0001\u0001\u0005\u0002\f\u00035\tQAA\rEK\u001a\fW\u000f\u001c;UCN\\\u0007+\u0019:b[N\u0004&o\u001c<jI\u0016\u00148cA\u0001\u000f)A\u0011qBE\u0007\u0002!)\t\u0011#A\u0003tG\u0006d\u0017-\u0003\u0002\u0014!\t1\u0011I\\=SK\u001a\u0004\"aC\u000b\n\u0005Y)!A\u0005+bg.\u0004\u0016M]1ngB\u0013xN^5eKJ\fa\u0001P5oSRtD#\u0001\u0006\u0002\u000bA\f'o]3\u0015\u0005mI\u0003C\u0001\u000f(\u001b\u0005i\"B\u0001\u0010 \u0003\u0011\u0011\u0017m]3\u000b\u0005\u0001\n\u0013!\u00039be\u0006lW\r^3s\u0015\t\u00113%A\u0003n_\u0012,GN\u0003\u0002%K\u0005)1/\u001a6eC*\ta%A\u0002pe\u001eL!\u0001K\u000f\u0003\u001dQ\u000b7o\u001b)be\u0006lW\r^3sg\")!f\u0001a\u0001W\u0005!!n]8o!\taSH\u0004\u0002.u9\u0011a\u0006\u000f\b\u0003_Ur!\u0001M\u001a\u000e\u0003ER!AM\u0005\u0002\rq\u0012xn\u001c;?\u0013\u0005!\u0014a\u00018fi&\u0011agN\u0001\bY&4Go^3c\u0015\u0005!\u0014B\u0001\u0016:\u0015\t1t'\u0003\u0002<y\u00059\u0001/Y2lC\u001e,'B\u0001\u0016:\u0013\tqtH\u0001\u0004K-\u0006dW/\u001a\u0006\u0003wq\u0002")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/service/DefaultTaskParamsProvider.class */
public final class DefaultTaskParamsProvider {
    public static TaskParameters parse(final JsonAST.JValue json) {
        return DefaultTaskParamsProvider$.MODULE$.parse(json);
    }
}
