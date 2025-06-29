package code.util.encryption;

import scala.reflect.ScalaSignature;

/* compiled from: HashingUtil.scala */
@ScalaSignature(bytes = "\u0006\u0005]:QAB\u0004\t\u000291Q\u0001E\u0004\t\u0002EAQ\u0001G\u0001\u0005\u0002eAQAG\u0001\u0005\u0002mAQAG\u0001\u0005\u0002-BQ!L\u0001\u0005\n9\n1\u0002S1tQ&tw-\u0016;jY*\u0011\u0001\"C\u0001\u000bK:\u001c'/\u001f9uS>t'B\u0001\u0006\f\u0003\u0011)H/\u001b7\u000b\u00031\tAaY8eK\u000e\u0001\u0001CA\b\u0002\u001b\u00059!a\u0003%bg\"LgnZ+uS2\u001c\"!\u0001\n\u0011\u0005M1R\"\u0001\u000b\u000b\u0003U\tQa]2bY\u0006L!a\u0006\u000b\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\ta\"\u0001\u0004tQ\u0006\u0014TG\u000e\u000b\u00049\u001dJ\u0003CA\u000f%\u001d\tq\"\u0005\u0005\u0002 )5\t\u0001E\u0003\u0002\"\u001b\u00051AH]8pizJ!a\t\u000b\u0002\rA\u0013X\rZ3g\u0013\t)cE\u0001\u0004TiJLgn\u001a\u0006\u0003GQAQ\u0001K\u0002A\u0002q\tA\u0001Z1uC\")!f\u0001a\u00019\u000511/Z2sKR$\"\u0001\b\u0017\t\u000b!\"\u0001\u0019\u0001\u000f\u0002\u0017Q|\u0007*\u001a=TiJLgn\u001a\u000b\u00039=BQ\u0001M\u0003A\u0002E\nQAY=uKN\u00042a\u0005\u001a5\u0013\t\u0019DCA\u0003BeJ\f\u0017\u0010\u0005\u0002\u0014k%\u0011a\u0007\u0006\u0002\u0005\u0005f$X\r")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/encryption/HashingUtil.class */
public final class HashingUtil {
    public static String sha256(final String data) {
        return HashingUtil$.MODULE$.sha256(data);
    }

    public static String sha256(final String data, final String secret) {
        return HashingUtil$.MODULE$.sha256(data, secret);
    }
}
