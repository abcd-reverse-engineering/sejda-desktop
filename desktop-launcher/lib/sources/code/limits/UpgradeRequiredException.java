package code.limits;

import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: UpgradeRequiredException.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005=b\u0001B\u000b\u0017\u0001nA\u0001\"\r\u0001\u0003\u0016\u0004%\tA\r\u0005\to\u0001\u0011\t\u0012)A\u0005g!)\u0001\b\u0001C\u0001s!9A\bAA\u0001\n\u0003i\u0004bB \u0001#\u0003%\t\u0001\u0011\u0005\b\u0017\u0002\t\t\u0011\"\u0011M\u0011\u001d)\u0006!!A\u0005\u0002YCqA\u0017\u0001\u0002\u0002\u0013\u00051\fC\u0004b\u0001\u0005\u0005I\u0011\t2\t\u000f%\u0004\u0011\u0011!C\u0001U\"9q\u000eAA\u0001\n\u0003\u0002\bb\u0002:\u0001\u0003\u0003%\te\u001d\u0005\bi\u0002\t\t\u0011\"\u0011v\u000f\u001d9h#!A\t\u0002a4q!\u0006\f\u0002\u0002#\u0005\u0011\u0010\u0003\u00049\u001f\u0011\u0005\u00111\u0002\u0005\n\u0003\u001by\u0011\u0011!C#\u0003\u001fA\u0011\"!\u0005\u0010\u0003\u0003%\t)a\u0005\t\u0013\u0005]q\"!A\u0005\u0002\u0006e\u0001\"CA\u0013\u001f\u0005\u0005I\u0011BA\u0014\u0005a)\u0006o\u001a:bI\u0016\u0014V-];je\u0016$W\t_2faRLwN\u001c\u0006\u0003/a\ta\u0001\\5nSR\u001c(\"A\r\u0002\t\r|G-Z\u0002\u0001'\u0011\u0001AD\u000b\u0018\u0011\u0005u9cB\u0001\u0010%\u001d\ty\"%D\u0001!\u0015\t\t#$\u0001\u0004=e>|GOP\u0005\u0002G\u0005)1oY1mC&\u0011QEJ\u0001\ba\u0006\u001c7.Y4f\u0015\u0005\u0019\u0013B\u0001\u0015*\u0005A\u0011VO\u001c;j[\u0016,\u0005pY3qi&|gN\u0003\u0002&MA\u00111\u0006L\u0007\u0002M%\u0011QF\n\u0002\b!J|G-^2u!\tir&\u0003\u00021S\ta1+\u001a:jC2L'0\u00192mK\u00069Q\u000f]4sC\u0012,W#A\u001a\u0011\u0005Q*T\"\u0001\f\n\u0005Y2\"aB+qOJ\fG-Z\u0001\tkB<'/\u00193fA\u00051A(\u001b8jiz\"\"AO\u001e\u0011\u0005Q\u0002\u0001\"B\u0019\u0004\u0001\u0004\u0019\u0014\u0001B2paf$\"A\u000f \t\u000fE\"\u0001\u0013!a\u0001g\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\nT#A!+\u0005M\u00125&A\"\u0011\u0005\u0011KU\"A#\u000b\u0005\u0019;\u0015!C;oG\",7m[3e\u0015\tAe%\u0001\u0006b]:|G/\u0019;j_:L!AS#\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0002\u001bB\u0011ajU\u0007\u0002\u001f*\u0011\u0001+U\u0001\u0005Y\u0006twMC\u0001S\u0003\u0011Q\u0017M^1\n\u0005Q{%AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180F\u0001X!\tY\u0003,\u0003\u0002ZM\t\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0011Al\u0018\t\u0003WuK!A\u0018\u0014\u0003\u0007\u0005s\u0017\u0010C\u0004a\u0011\u0005\u0005\t\u0019A,\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\u0005\u0019\u0007c\u00013h96\tQM\u0003\u0002gM\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u0005!,'\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$\"a\u001b8\u0011\u0005-b\u0017BA7'\u0005\u001d\u0011un\u001c7fC:Dq\u0001\u0019\u0006\u0002\u0002\u0003\u0007A,\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,GCA'r\u0011\u001d\u00017\"!AA\u0002]\u000b\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002/\u00061Q-];bYN$\"a\u001b<\t\u000f\u0001l\u0011\u0011!a\u00019\u0006AR\u000b]4sC\u0012,'+Z9vSJ,G-\u0012=dKB$\u0018n\u001c8\u0011\u0005Qz1\u0003B\b{\u0003\u0003\u0001Ba\u001f@4u5\tAP\u0003\u0002~M\u00059!/\u001e8uS6,\u0017BA@}\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|g.\r\t\u0005\u0003\u0007\tI!\u0004\u0002\u0002\u0006)\u0019\u0011qA)\u0002\u0005%|\u0017b\u0001\u0019\u0002\u0006Q\t\u00010\u0001\u0005u_N#(/\u001b8h)\u0005i\u0015!B1qa2LHc\u0001\u001e\u0002\u0016!)\u0011G\u0005a\u0001g\u00059QO\\1qa2LH\u0003BA\u000e\u0003C\u0001BaKA\u000fg%\u0019\u0011q\u0004\u0014\u0003\r=\u0003H/[8o\u0011!\t\u0019cEA\u0001\u0002\u0004Q\u0014a\u0001=%a\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u0011\u0011\u0006\t\u0004\u001d\u0006-\u0012bAA\u0017\u001f\n1qJ\u00196fGR\u0004")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/limits/UpgradeRequiredException.class */
public class UpgradeRequiredException extends RuntimeException implements Product {
    private final Upgrade upgrade;

    public static Option<Upgrade> unapply(final UpgradeRequiredException x$0) {
        return UpgradeRequiredException$.MODULE$.unapply(x$0);
    }

    public static UpgradeRequiredException apply(final Upgrade upgrade) {
        return UpgradeRequiredException$.MODULE$.apply(upgrade);
    }

    public static <A> Function1<Upgrade, A> andThen(final Function1<UpgradeRequiredException, A> g) {
        return UpgradeRequiredException$.MODULE$.andThen(g);
    }

    public static <A> Function1<A, UpgradeRequiredException> compose(final Function1<A, Upgrade> g) {
        return UpgradeRequiredException$.MODULE$.compose(g);
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Upgrade upgrade() {
        return this.upgrade;
    }

    public UpgradeRequiredException copy(final Upgrade upgrade) {
        return new UpgradeRequiredException(upgrade);
    }

    public Upgrade copy$default$1() {
        return upgrade();
    }

    public String productPrefix() {
        return "UpgradeRequiredException";
    }

    public int productArity() {
        return 1;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return upgrade();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof UpgradeRequiredException;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "upgrade";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof UpgradeRequiredException) {
                UpgradeRequiredException upgradeRequiredException = (UpgradeRequiredException) x$1;
                Upgrade upgrade = upgrade();
                Upgrade upgrade2 = upgradeRequiredException.upgrade();
                if (upgrade != null ? upgrade.equals(upgrade2) : upgrade2 == null) {
                    if (upgradeRequiredException.canEqual(this)) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UpgradeRequiredException(final Upgrade upgrade) {
        super(new StringBuilder(26).append("Upgrade required. Reason: ").append(upgrade.reason()).toString());
        this.upgrade = upgrade;
        Product.$init$(this);
    }
}
