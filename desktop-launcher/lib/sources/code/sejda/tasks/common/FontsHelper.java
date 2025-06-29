package code.sejda.tasks.common;

import scala.reflect.ScalaSignature;

/* compiled from: FontsHelper.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0011;Qa\u0002\u0005\t\u0002E1Qa\u0005\u0005\t\u0002QAQ!I\u0001\u0005\u0002\tBQaI\u0001\u0005\u0002\u0011BQAM\u0001\u0005\u0002MBQ\u0001P\u0001\u0005\u0002uBQ\u0001Q\u0001\u0005\u0002\u0005\u000b1BR8oiNDU\r\u001c9fe*\u0011\u0011BC\u0001\u0007G>lWn\u001c8\u000b\u0005-a\u0011!\u0002;bg.\u001c(BA\u0007\u000f\u0003\u0015\u0019XM\u001b3b\u0015\u0005y\u0011\u0001B2pI\u0016\u001c\u0001\u0001\u0005\u0002\u0013\u00035\t\u0001BA\u0006G_:$8\u000fS3ma\u0016\u00148cA\u0001\u00167A\u0011a#G\u0007\u0002/)\t\u0001$A\u0003tG\u0006d\u0017-\u0003\u0002\u001b/\t1\u0011I\\=SK\u001a\u0004\"\u0001H\u0010\u000e\u0003uQ!A\b\b\u0002\tU$\u0018\u000e\\\u0005\u0003Au\u0011\u0001\u0002T8hO\u0006\u0014G.Z\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003E\tQ\"\u0019:bE&\u001c7\u000b[1qS:<GCA\u00131!\t1SF\u0004\u0002(WA\u0011\u0001fF\u0007\u0002S)\u0011!\u0006E\u0001\u0007yI|w\u000e\u001e \n\u00051:\u0012A\u0002)sK\u0012,g-\u0003\u0002/_\t11\u000b\u001e:j]\u001eT!\u0001L\f\t\u000bE\u001a\u0001\u0019A\u0013\u0002\tQ,\u0007\u0010^\u0001\u0010m&\u001cX/\u00197U_2{w-[2bYR\u0011Ag\u000f\t\u0003kij\u0011A\u000e\u0006\u0003oa\nA\u0001\\1oO*\t\u0011(\u0001\u0003kCZ\f\u0017B\u0001\u00187\u0011\u0015\tD\u00011\u0001&\u0003u1\u0017-\\5ms:\u000bW.Z,ji\"|W\u000f^*vEN,G\u000f\u0015:fM&DHC\u0001\u001b?\u0011\u0015yT\u00011\u0001&\u0003\u0005\u0019\u0018!\u00048pe6\fG.\u001b>f\u001d\u0006lW\r\u0006\u0002&\u0005\")1I\u0002a\u0001K\u0005!a.Y7f\u0001")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/FontsHelper.class */
public final class FontsHelper {
    public static String normalizeName(final String name) {
        return FontsHelper$.MODULE$.normalizeName(name);
    }

    public static String familyNameWithoutSubsetPrefix(final String s) {
        return FontsHelper$.MODULE$.familyNameWithoutSubsetPrefix(s);
    }

    public static String visualToLogical(final String text) {
        return FontsHelper$.MODULE$.visualToLogical(text);
    }

    public static String arabicShaping(final String text) {
        return FontsHelper$.MODULE$.arabicShaping(text);
    }
}
