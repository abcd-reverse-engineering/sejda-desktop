package code.util;

import java.util.LinkedHashSet;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;

/* compiled from: JavaConversions.scala */
@ScalaSignature(bytes = "\u0006\u0005e:Q\u0001B\u0003\t\u0002)1Q\u0001D\u0003\t\u00025AQ\u0001F\u0001\u0005\u0002UAQAF\u0001\u0005\u0002]\tqBS1wC\u000e{gN^3sg&|gn\u001d\u0006\u0003\r\u001d\tA!\u001e;jY*\t\u0001\"\u0001\u0003d_\u0012,7\u0001\u0001\t\u0003\u0017\u0005i\u0011!\u0002\u0002\u0010\u0015\u00064\u0018mQ8om\u0016\u00148/[8ogN\u0011\u0011A\u0004\t\u0003\u001fIi\u0011\u0001\u0005\u0006\u0002#\u0005)1oY1mC&\u00111\u0003\u0005\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u0005Q\u0011aE1t\u0015\u00064\u0018\rT5oW\u0016$\u0007*Y:i'\u0016$XC\u0001\r#)\tI2\u0006E\u0002\u001b=\u0001j\u0011a\u0007\u0006\u0003\rqQ\u0011!H\u0001\u0005U\u00064\u0018-\u0003\u0002 7\tiA*\u001b8lK\u0012D\u0015m\u001d5TKR\u0004\"!\t\u0012\r\u0001\u0011)1e\u0001b\u0001I\t\tA+\u0005\u0002&QA\u0011qBJ\u0005\u0003OA\u0011qAT8uQ&tw\r\u0005\u0002\u0010S%\u0011!\u0006\u0005\u0002\u0004\u0003:L\b\"\u0002\u0017\u0004\u0001\u0004i\u0013!A:\u0011\u000792\u0004E\u0004\u00020i9\u0011\u0001gM\u0007\u0002c)\u0011!'C\u0001\u0007yI|w\u000e\u001e \n\u0003EI!!\u000e\t\u0002\u000fA\f7m[1hK&\u0011q\u0007\u000f\u0002\u0004'\u0016\f(BA\u001b\u0011\u0001")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/JavaConversions.class */
public final class JavaConversions {
    public static <T> LinkedHashSet<T> asJavaLinkedHashSet(final Seq<T> s) {
        return JavaConversions$.MODULE$.asJavaLinkedHashSet(s);
    }
}
