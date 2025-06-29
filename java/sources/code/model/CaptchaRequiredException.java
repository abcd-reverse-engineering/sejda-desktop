package code.model;

import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: exceptions.scala */
@ScalaSignature(bytes = "\u0006\u0005i4AAE\nA1!)a\u0006\u0001C\u0001_!9!\u0007AA\u0001\n\u0003y\u0003bB\u001a\u0001\u0003\u0003%\t\u0005\u000e\u0005\b{\u0001\t\t\u0011\"\u0001?\u0011\u001d\u0011\u0005!!A\u0005\u0002\rCq!\u0013\u0001\u0002\u0002\u0013\u0005#\nC\u0004R\u0001\u0005\u0005I\u0011\u0001*\t\u000f]\u0003\u0011\u0011!C!1\"9!\fAA\u0001\n\u0003Z\u0006b\u0002/\u0001\u0003\u0003%\t%X\u0004\b?N\t\t\u0011#\u0001a\r\u001d\u00112#!A\t\u0002\u0005DQA\f\u0007\u0005\u00025DqA\u001c\u0007\u0002\u0002\u0013\u0015s\u000eC\u0004q\u0019\u0005\u0005I\u0011Q\u0018\t\u000fEd\u0011\u0011!CAe\"9Q\u000fDA\u0001\n\u00131(\u0001G\"baR\u001c\u0007.\u0019*fcVL'/\u001a3Fq\u000e,\u0007\u000f^5p]*\u0011A#F\u0001\u0006[>$W\r\u001c\u0006\u0002-\u0005!1m\u001c3f\u0007\u0001\u0019B\u0001A\r(WA\u0011!\u0004\n\b\u00037\u0005r!\u0001H\u0010\u000e\u0003uQ!AH\f\u0002\rq\u0012xn\u001c;?\u0013\u0005\u0001\u0013!B:dC2\f\u0017B\u0001\u0012$\u0003\u001d\u0001\u0018mY6bO\u0016T\u0011\u0001I\u0005\u0003K\u0019\u0012\u0001CU;oi&lW-\u0012=dKB$\u0018n\u001c8\u000b\u0005\t\u001a\u0003C\u0001\u0015*\u001b\u0005\u0019\u0013B\u0001\u0016$\u0005\u001d\u0001&o\u001c3vGR\u0004\"A\u0007\u0017\n\u000552#\u0001D*fe&\fG.\u001b>bE2,\u0017A\u0002\u001fj]&$h\bF\u00011!\t\t\u0004!D\u0001\u0014\u0003\u0011\u0019w\u000e]=\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u0005)\u0004C\u0001\u001c<\u001b\u00059$B\u0001\u001d:\u0003\u0011a\u0017M\\4\u000b\u0003i\nAA[1wC&\u0011Ah\u000e\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0003}\u0002\"\u0001\u000b!\n\u0005\u0005\u001b#aA%oi\u0006q\u0001O]8ek\u000e$X\t\\3nK:$HC\u0001#H!\tAS)\u0003\u0002GG\t\u0019\u0011I\\=\t\u000f!+\u0011\u0011!a\u0001\u007f\u0005\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\u0012a\u0013\t\u0004\u0019>#U\"A'\u000b\u00059\u001b\u0013AC2pY2,7\r^5p]&\u0011\u0001+\u0014\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0002T-B\u0011\u0001\u0006V\u0005\u0003+\u000e\u0012qAQ8pY\u0016\fg\u000eC\u0004I\u000f\u0005\u0005\t\u0019\u0001#\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0003keCq\u0001\u0013\u0005\u0002\u0002\u0003\u0007q(\u0001\u0005iCND7i\u001c3f)\u0005y\u0014AB3rk\u0006d7\u000f\u0006\u0002T=\"9\u0001JCA\u0001\u0002\u0004!\u0015\u0001G\"baR\u001c\u0007.\u0019*fcVL'/\u001a3Fq\u000e,\u0007\u000f^5p]B\u0011\u0011\u0007D\n\u0004\u0019\tD\u0007cA2ga5\tAM\u0003\u0002fG\u00059!/\u001e8uS6,\u0017BA4e\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|g\u000e\r\t\u0003S2l\u0011A\u001b\u0006\u0003Wf\n!![8\n\u00055RG#\u00011\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012!N\u0001\u0006CB\u0004H._\u0001\bk:\f\u0007\u000f\u001d7z)\t\u00196\u000fC\u0004u!\u0005\u0005\t\u0019\u0001\u0019\u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\rF\u0001x!\t1\u00040\u0003\u0002zo\t1qJ\u00196fGR\u0004")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/model/CaptchaRequiredException.class */
public class CaptchaRequiredException extends RuntimeException implements Product {
    public static boolean unapply(final CaptchaRequiredException x$0) {
        return CaptchaRequiredException$.MODULE$.unapply(x$0);
    }

    public static CaptchaRequiredException apply() {
        return CaptchaRequiredException$.MODULE$.m8apply();
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public CaptchaRequiredException copy() {
        return new CaptchaRequiredException();
    }

    public String productPrefix() {
        return "CaptchaRequiredException";
    }

    public int productArity() {
        return 0;
    }

    public Object productElement(final int x$1) {
        return Statics.ioobe(x$1);
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof CaptchaRequiredException;
    }

    public String productElementName(final int x$1) {
        return (String) Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    public boolean equals(final Object x$1) {
        return (x$1 instanceof CaptchaRequiredException) && ((CaptchaRequiredException) x$1).canEqual(this);
    }

    public CaptchaRequiredException() {
        super("Captcha challenge required");
        Product.$init$(this);
    }
}
