package code.sejda.tasks.excel;

import scala.reflect.ScalaSignature;

/* compiled from: TableDetector.scala */
@ScalaSignature(bytes = "\u0006\u0005Q2q\u0001B\u0003\u0011\u0002\u0007\u0005a\u0002C\u0003\u0016\u0001\u0011\u0005a\u0003C\u0003\u001b\u0001\u0011\u00051\u0004C\u0004)\u0001E\u0005I\u0011A\u0015\u0003\u000bU#\u0018\u000e\\:\u000b\u0005\u00199\u0011!B3yG\u0016d'B\u0001\u0005\n\u0003\u0015!\u0018m]6t\u0015\tQ1\"A\u0003tK*$\u0017MC\u0001\r\u0003\u0011\u0019w\u000eZ3\u0004\u0001M\u0011\u0001a\u0004\t\u0003!Mi\u0011!\u0005\u0006\u0002%\u0005)1oY1mC&\u0011A#\u0005\u0002\u0007\u0003:L(+\u001a4\u0002\r\u0011Jg.\u001b;%)\u00059\u0002C\u0001\t\u0019\u0013\tI\u0012C\u0001\u0003V]&$\u0018!D1sK\u0012+G\u000e^1FcV\fG\u000e\u0006\u0003\u001d?\u00112\u0003C\u0001\t\u001e\u0013\tq\u0012CA\u0004C_>dW-\u00198\t\u000b\u0001\u0012\u0001\u0019A\u0011\u0002\u0005Y\f\u0004C\u0001\t#\u0013\t\u0019\u0013CA\u0003GY>\fG\u000fC\u0003&\u0005\u0001\u0007\u0011%\u0001\u0002we!9qE\u0001I\u0001\u0002\u0004\t\u0013!\u00023fYR\f\u0017aF1sK\u0012+G\u000e^1FcV\fG\u000e\n3fM\u0006,H\u000e\u001e\u00134+\u0005Q#FA\u0011,W\u0005a\u0003CA\u00173\u001b\u0005q#BA\u00181\u0003%)hn\u00195fG.,GM\u0003\u00022#\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005Mr#!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0002")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/excel/Utils.class */
public interface Utils {
    static void $init$(final Utils $this) {
    }

    default boolean areDeltaEqual(final float v1, final float v2, final float delta) {
        return Math.abs(v1 - v2) < delta;
    }

    default float areDeltaEqual$default$3() {
        return 2.0f;
    }
}
