package code.service;

import java.io.File;
import scala.reflect.ScalaSignature;

/* compiled from: InJvmTaskExecutor.scala */
@ScalaSignature(bytes = "\u0006\u0005E:Qa\u0002\u0005\t\u000251Qa\u0004\u0005\t\u0002AAQaF\u0001\u0005\u0002aAq!G\u0001C\u0002\u0013\u0005!\u0004\u0003\u0004\u001f\u0003\u0001\u0006Ia\u0007\u0005\u0006?\u0005!\t\u0001\t\u0005\u0006]\u0005!\taL\u0001\u000f'\",H\u000fZ8x]\"+G\u000e]3s\u0015\tI!\"A\u0004tKJ4\u0018nY3\u000b\u0003-\tAaY8eK\u000e\u0001\u0001C\u0001\b\u0002\u001b\u0005A!AD*ikR$wn\u001e8IK2\u0004XM]\n\u0003\u0003E\u0001\"AE\u000b\u000e\u0003MQ\u0011\u0001F\u0001\u0006g\u000e\fG.Y\u0005\u0003-M\u0011a!\u00118z%\u00164\u0017A\u0002\u001fj]&$h\bF\u0001\u000e\u0003!Ig\u000e^3sm\u0006dW#A\u000e\u0011\u0005Ia\u0012BA\u000f\u0014\u0005\rIe\u000e^\u0001\nS:$XM\u001d<bY\u0002\nQa\u001d;beR$\"!\t\u0013\u0011\u0005I\u0011\u0013BA\u0012\u0014\u0005\u0011)f.\u001b;\t\u000b\u0015*\u0001\u0019\u0001\u0014\u0002\t-LG\u000e\u001c\t\u0003O1j\u0011\u0001\u000b\u0006\u0003S)\n!![8\u000b\u0003-\nAA[1wC&\u0011Q\u0006\u000b\u0002\u0005\r&dW-A\u0004ue&<w-\u001a:\u0015\u0005\u0005\u0002\u0004\"B\u0013\u0007\u0001\u00041\u0003")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/service/ShutdownHelper.class */
public final class ShutdownHelper {
    public static void trigger(final File kill) throws InterruptedException {
        ShutdownHelper$.MODULE$.trigger(kill);
    }

    public static void start(final File kill) {
        ShutdownHelper$.MODULE$.start(kill);
    }

    public static int interval() {
        return ShutdownHelper$.MODULE$.interval();
    }
}
