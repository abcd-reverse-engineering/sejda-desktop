package code.util.encryption;

import java.security.SecureRandom;
import scala.reflect.ScalaSignature;

/* compiled from: TwoWayEncryptionUtils.scala */
@ScalaSignature(bytes = "\u0006\u0005I<Q!\u0004\b\t\u0002U1Qa\u0006\b\t\u0002aAQaH\u0001\u0005\u0002\u0001B\u0001\"I\u0001\t\u0006\u0004%\tA\t\u0005\u0006W\u0005!\t\u0001\f\u0005\u0006q\u0005!\t!\u000f\u0005\u0006\u0013\u0006!\tA\u0013\u0005\u0006\u001b\u0006!\tA\u0014\u0005\u0006-\u0006!\ta\u0016\u0005\u00065\u0006!\ta\u0017\u0005\u0006A\u0006!\t!\u0019\u0005\u0006O\u0006!\t\u0001\u001b\u0005\u0006[\u0006!\tA\\\u0001\u0016)^|w+Y=F]\u000e\u0014\u0018\u0010\u001d;j_:,F/\u001b7t\u0015\ty\u0001#\u0001\u0006f]\u000e\u0014\u0018\u0010\u001d;j_:T!!\u0005\n\u0002\tU$\u0018\u000e\u001c\u0006\u0002'\u0005!1m\u001c3f\u0007\u0001\u0001\"AF\u0001\u000e\u00039\u0011Q\u0003V<p/\u0006LXI\\2ssB$\u0018n\u001c8Vi&d7o\u0005\u0002\u00023A\u0011!$H\u0007\u00027)\tA$A\u0003tG\u0006d\u0017-\u0003\u0002\u001f7\t1\u0011I\\=SK\u001a\fa\u0001P5oSRtD#A\u000b\u0002\rI\u000be\nR(N+\u0005\u0019\u0003C\u0001\u0013*\u001b\u0005)#B\u0001\u0014(\u0003!\u0019XmY;sSRL(\"\u0001\u0015\u0002\t)\fg/Y\u0005\u0003U\u0015\u0012AbU3dkJ,'+\u00198e_6\faA]1oI>lGCA\u00174!\rQb\u0006M\u0005\u0003_m\u0011Q!\u0011:sCf\u0004\"AG\u0019\n\u0005IZ\"\u0001\u0002\"zi\u0016DQ\u0001\u000e\u0003A\u0002U\nAAY5ugB\u0011!DN\u0005\u0003om\u00111!\u00138u\u0003\u0019\u0001(m\u001b3geQ\u0019QFO$\t\u000bm*\u0001\u0019\u0001\u001f\u0002\rM,7M]3u!\tiDI\u0004\u0002?\u0005B\u0011qhG\u0007\u0002\u0001*\u0011\u0011\tF\u0001\u0007yI|w\u000e\u001e \n\u0005\r[\u0012A\u0002)sK\u0012,g-\u0003\u0002F\r\n11\u000b\u001e:j]\u001eT!aQ\u000e\t\u000b!+\u0001\u0019\u0001\u001f\u0002\tM\fG\u000e^\u0001\u0004[\u0012,DCA\u0017L\u0011\u0015ae\u00011\u0001=\u0003\u00151\u0018\r\\;f\u0003\u0015!x\u000eS3y)\tyE\u000b\u0005\u0002Q'6\t\u0011K\u0003\u0002SO\u0005!A.\u00198h\u0013\t)\u0015\u000bC\u0003V\u000f\u0001\u0007Q&A\u0003csR,7/A\u0004ge>l\u0007*\u001a=\u0015\u00055B\u0006\"B-\t\u0001\u0004a\u0014a\u00015fq\u00069QM\\2ssB$H\u0003B(]=~CQ!X\u0005A\u0002q\nQ\u0001\u001d7bS:DQaO\u0005A\u0002qBQ\u0001S\u0005A\u0002q\n!\"\u001a8def\u0004HOU1x)\u0011i#mY3\t\u000buS\u0001\u0019A\u0017\t\u000b\u0011T\u0001\u0019A\u0017\u0002\u0007-,\u0017\u0010C\u0003g\u0015\u0001\u0007Q&\u0001\u0002jm\u00069A-Z2ssB$H\u0003\u0002\u001fjW2DQA[\u0006A\u0002q\n\u0011\"\u001a8def\u0004H/\u001a3\t\u000bmZ\u0001\u0019\u0001\u001f\t\u000b![\u0001\u0019\u0001\u001f\u0002\u0015\u0011,7M]=qiJ\u000bw\u000f\u0006\u0003._B\f\b\"\u00026\r\u0001\u0004i\u0003\"\u00023\r\u0001\u0004i\u0003\"\u00024\r\u0001\u0004i\u0003")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/encryption/TwoWayEncryptionUtils.class */
public final class TwoWayEncryptionUtils {
    public static byte[] decryptRaw(final byte[] encrypted, final byte[] key, final byte[] iv) {
        return TwoWayEncryptionUtils$.MODULE$.decryptRaw(encrypted, key, iv);
    }

    public static String decrypt(final String encrypted, final String secret, final String salt) {
        return TwoWayEncryptionUtils$.MODULE$.decrypt(encrypted, secret, salt);
    }

    public static byte[] encryptRaw(final byte[] plain, final byte[] key, final byte[] iv) {
        return TwoWayEncryptionUtils$.MODULE$.encryptRaw(plain, key, iv);
    }

    public static String encrypt(final String plain, final String secret, final String salt) {
        return TwoWayEncryptionUtils$.MODULE$.encrypt(plain, secret, salt);
    }

    public static byte[] fromHex(final String hex) {
        return TwoWayEncryptionUtils$.MODULE$.fromHex(hex);
    }

    public static String toHex(final byte[] bytes) {
        return TwoWayEncryptionUtils$.MODULE$.toHex(bytes);
    }

    public static byte[] md5(final String value) {
        return TwoWayEncryptionUtils$.MODULE$.md5(value);
    }

    public static byte[] pbkdf2(final String secret, final String salt) {
        return TwoWayEncryptionUtils$.MODULE$.pbkdf2(secret, salt);
    }

    public static byte[] random(final int bits) {
        return TwoWayEncryptionUtils$.MODULE$.random(bits);
    }

    public static SecureRandom RANDOM() {
        return TwoWayEncryptionUtils$.MODULE$.RANDOM();
    }
}
