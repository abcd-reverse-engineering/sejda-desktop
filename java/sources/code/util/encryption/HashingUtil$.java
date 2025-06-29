package code.util.encryption;

import com.google.common.hash.Hashing;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import scala.Predef$;
import scala.collection.ArrayOps$;
import scala.collection.StringOps$;
import scala.collection.mutable.StringBuilder;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;

/* compiled from: HashingUtil.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/encryption/HashingUtil$.class */
public final class HashingUtil$ {
    public static final HashingUtil$ MODULE$ = new HashingUtil$();

    private HashingUtil$() {
    }

    public String sha256(final String data, final String secret) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);
        return toHexString(mac.doFinal(data.getBytes()));
    }

    public String sha256(final String data) {
        return Hashing.sha256().hashBytes(data.getBytes("UTF-8")).toString();
    }

    private String toHexString(final byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        ArrayOps$.MODULE$.foreach$extension(Predef$.MODULE$.byteArrayOps(bytes), b -> {
            return $anonfun$toHexString$1(sb, BoxesRunTime.unboxToByte(b));
        });
        return sb.toString();
    }

    public static final /* synthetic */ StringBuilder $anonfun$toHexString$1(final StringBuilder sb$1, final byte b) {
        return sb$1.append(StringOps$.MODULE$.format$extension(Predef$.MODULE$.augmentString("%02x"), ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{BoxesRunTime.boxToByte(b)})));
    }
}
