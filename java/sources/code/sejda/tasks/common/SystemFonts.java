package code.sejda.tasks.common;

import code.sejda.tasks.edit.SystemFont;
import scala.Option;
import scala.collection.immutable.Map;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.mutable.HashMap;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

/* compiled from: SystemFonts.scala */
@ScalaSignature(bytes = "\u0006\u0005m4AAD\b\u00011!Aq\u0004\u0001B\u0001B\u0003%\u0001\u0005C\u00033\u0001\u0011\u00051\u0007C\u00048\u0001\t\u0007I\u0011\u0001\u001d\t\r-\u0003\u0001\u0015!\u0003:\u0011\u0015a\u0005\u0001\"\u0001N\u0011\u001dA\u0006!%A\u0005\u0002e;Q\u0001Z\b\t\u0002\u00154QAD\b\t\u0002\u0019DQA\r\u0005\u0005\u0002\u001dDq\u0001\u001b\u0005C\u0002\u0013\u0005\u0011\u000e\u0003\u0004q\u0011\u0001\u0006IA\u001b\u0005\u0006c\"!\tA\u001d\u0005\u0006q\"!\t!\u001f\u0002\f'f\u001cH/Z7G_:$8O\u0003\u0002\u0011#\u000511m\\7n_:T!AE\n\u0002\u000bQ\f7o[:\u000b\u0005Q)\u0012!B:fU\u0012\f'\"\u0001\f\u0002\t\r|G-Z\u0002\u0001'\t\u0001\u0011\u0004\u0005\u0002\u001b;5\t1DC\u0001\u001d\u0003\u0015\u00198-\u00197b\u0013\tq2D\u0001\u0004B]f\u0014VMZ\u0001\u0006M>tGo\u001d\t\u0004C%bcB\u0001\u0012(\u001d\t\u0019c%D\u0001%\u0015\t)s#\u0001\u0004=e>|GOP\u0005\u00029%\u0011\u0001fG\u0001\ba\u0006\u001c7.Y4f\u0013\tQ3FA\u0002TKFT!\u0001K\u000e\u0011\u00055\u0002T\"\u0001\u0018\u000b\u0005=\n\u0012\u0001B3eSRL!!\r\u0018\u0003\u0015MK8\u000f^3n\r>tG/\u0001\u0004=S:LGO\u0010\u000b\u0003iY\u0002\"!\u000e\u0001\u000e\u0003=AQa\b\u0002A\u0002\u0001\n\u0001BY=GC6LG._\u000b\u0002sA!!hP!J\u001b\u0005Y$B\u0001\u001f>\u0003%IW.\\;uC\ndWM\u0003\u0002?7\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u0005\u0001[$aA'baB\u0011!I\u0012\b\u0003\u0007\u0012\u0003\"aI\u000e\n\u0005\u0015[\u0012A\u0002)sK\u0012,g-\u0003\u0002H\u0011\n11\u000b\u001e:j]\u001eT!!R\u000e\u0011\u0007iRE&\u0003\u0002+w\u0005I!-\u001f$b[&d\u0017\u0010I\u0001\u0005M&tG\rF\u0002O#N\u00032AG(-\u0013\t\u00016D\u0001\u0004PaRLwN\u001c\u0005\u0006%\u0016\u0001\r!Q\u0001\u0005]\u0006lW\rC\u0004U\u000bA\u0005\t\u0019A+\u0002\t\t|G\u000e\u001a\t\u00035YK!aV\u000e\u0003\u000f\t{w\u000e\\3b]\u0006qa-\u001b8eI\u0011,g-Y;mi\u0012\u0012T#\u0001.+\u0005U[6&\u0001/\u0011\u0005u\u0013W\"\u00010\u000b\u0005}\u0003\u0017!C;oG\",7m[3e\u0015\t\t7$\u0001\u0006b]:|G/\u0019;j_:L!a\u00190\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-A\u0006TsN$X-\u001c$p]R\u001c\bCA\u001b\t'\tA\u0011\u0004F\u0001f\u0003Q\u0019\u0017m\u00195fI\u001a\u000b\u0017\u000e\\;sKJ+\u0017m]8ogV\t!\u000e\u0005\u0003l]\u0006\u000bU\"\u00017\u000b\u00055l\u0014aB7vi\u0006\u0014G.Z\u0005\u0003_2\u0014q\u0001S1tQ6\u000b\u0007/A\u000bdC\u000eDW\r\u001a$bS2,(/\u001a*fCN|gn\u001d\u0011\u0002!\u0005$GMR1jYV\u0014XMU3bg>tGcA:umB\u0019!dT!\t\u000bUd\u0001\u0019A!\u0002\u0007-,\u0017\u0010C\u0003x\u0019\u0001\u0007\u0011)\u0001\u0004sK\u0006\u001cxN\\\u0001\u0011O\u0016$h)Y5mkJ,'+Z1t_:$\"a\u001d>\t\u000bUl\u0001\u0019A!")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/SystemFonts.class */
public class SystemFonts {
    private final Map<String, Seq<SystemFont>> byFamily;

    public static Option<String> getFailureReason(final String key) {
        return SystemFonts$.MODULE$.getFailureReason(key);
    }

    public static Option<String> addFailureReason(final String key, final String reason) {
        return SystemFonts$.MODULE$.addFailureReason(key, reason);
    }

    public static HashMap<String, String> cachedFailureReasons() {
        return SystemFonts$.MODULE$.cachedFailureReasons();
    }

    public SystemFonts(final Seq<SystemFont> fonts) {
        this.byFamily = fonts.groupBy(x$1 -> {
            return x$1.family();
        });
    }

    public Map<String, Seq<SystemFont>> byFamily() {
        return this.byFamily;
    }

    public boolean find$default$2() {
        return false;
    }

    public Option<SystemFont> find(final String name, final boolean bold) {
        Seq fonts = (Seq) byFamily().getOrElse(name, () -> {
            return Nil$.MODULE$;
        });
        Seq notItalic = (Seq) fonts.filter(x$2 -> {
            return BoxesRunTime.boxToBoolean($anonfun$find$2(x$2));
        });
        if (bold) {
            return notItalic.find(x$3 -> {
                return BoxesRunTime.boxToBoolean(x$3.bold());
            }).orElse(() -> {
                return notItalic.find(x$4 -> {
                    return BoxesRunTime.boxToBoolean($anonfun$find$5(x$4));
                });
            }).orElse(() -> {
                return fonts.headOption();
            });
        }
        return notItalic.find(x$5 -> {
            return BoxesRunTime.boxToBoolean($anonfun$find$7(x$5));
        }).orElse(() -> {
            return notItalic.find(x$6 -> {
                return BoxesRunTime.boxToBoolean($anonfun$find$9(x$6));
            });
        }).orElse(() -> {
            return notItalic.headOption();
        }).orElse(() -> {
            return fonts.headOption();
        });
    }

    public static final /* synthetic */ boolean $anonfun$find$2(final SystemFont x$2) {
        return !x$2.italic();
    }

    public static final /* synthetic */ boolean $anonfun$find$5(final SystemFont x$4) {
        String strStyle = x$4.style();
        return strStyle != null ? strStyle.equals("Semibold") : "Semibold" == 0;
    }

    public static final /* synthetic */ boolean $anonfun$find$7(final SystemFont x$5) {
        String strStyle = x$5.style();
        return strStyle != null ? strStyle.equals("Regular") : "Regular" == 0;
    }

    public static final /* synthetic */ boolean $anonfun$find$9(final SystemFont x$6) {
        String strStyle = x$6.style();
        return strStyle != null ? strStyle.equals("Medium") : "Medium" == 0;
    }
}
