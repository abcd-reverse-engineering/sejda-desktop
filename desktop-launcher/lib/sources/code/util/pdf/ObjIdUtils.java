package code.util.pdf;

import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectKey;
import org.sejda.sambox.cos.COSObjectable;
import scala.collection.immutable.Set;
import scala.reflect.ScalaSignature;

/* compiled from: ObjIdUtils.scala */
@ScalaSignature(bytes = "\u0006\u0005)<Q!\u0003\u0006\t\u0002E1Qa\u0005\u0006\t\u0002QAQaG\u0001\u0005\u0002qAQ!H\u0001\u0005\u0002yAQ!H\u0001\u0005\u0002aBQAP\u0001\u0005\u0002}BQ!Q\u0001\u0005\u0002\tCq!V\u0001\u0012\u0002\u0013\u0005a\u000bC\u0003b\u0003\u0011\u0005!-\u0001\u0006PE*LE-\u0016;jYNT!a\u0003\u0007\u0002\u0007A$gM\u0003\u0002\u000e\u001d\u0005!Q\u000f^5m\u0015\u0005y\u0011\u0001B2pI\u0016\u001c\u0001\u0001\u0005\u0002\u0013\u00035\t!B\u0001\u0006PE*LE-\u0016;jYN\u001c\"!A\u000b\u0011\u0005YIR\"A\f\u000b\u0003a\tQa]2bY\u0006L!AG\f\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\t\u0011#A\u0004pE*LEm\u00144\u0015\u0005}Q\u0003C\u0001\u0011(\u001d\t\tS\u0005\u0005\u0002#/5\t1E\u0003\u0002%!\u00051AH]8pizJ!AJ\f\u0002\rA\u0013X\rZ3g\u0013\tA\u0013F\u0001\u0004TiJLgn\u001a\u0006\u0003M]AQaK\u0002A\u00021\n\u0011a\u001c\t\u0003[Yj\u0011A\f\u0006\u0003_A\n1aY8t\u0015\t\t$'\u0001\u0004tC6\u0014w\u000e\u001f\u0006\u0003gQ\nQa]3kI\u0006T\u0011!N\u0001\u0004_J<\u0017BA\u001c/\u00055\u0019ujU(cU\u0016\u001cG/\u00192mKR\u0011q$\u000f\u0005\u0006u\u0011\u0001\raO\u0001\u0006S\u0012,g\u000e\u001e\t\u0003[qJ!!\u0010\u0018\u0003\u0019\r{5k\u00142kK\u000e$8*Z=\u0002\u001d=\u0014'.\u00133PM>\u0013X)\u001c9usR\u0011q\u0004\u0011\u0005\u0006W\u0015\u0001\r\u0001L\u0001\u0018O\u0016$\u0018J\u001c5fe&$\u0018M\u00197f\u0003R$(/\u001b2vi\u0016$Ba\u0011$L!B\u0011Q\u0006R\u0005\u0003\u000b:\u0012qaQ(T\u0005\u0006\u001cX\rC\u0003H\r\u0001\u0007\u0001*\u0001\u0003o_\u0012,\u0007CA\u0017J\u0013\tQeFA\u0007D\u001fN#\u0015n\u0019;j_:\f'/\u001f\u0005\u0006\u0019\u001a\u0001\r!T\u0001\u0004W\u0016L\bCA\u0017O\u0013\tyeFA\u0004D\u001fNs\u0015-\\3\t\u000fE3\u0001\u0013!a\u0001%\u0006ia/[:ji\u0016$wJ\u00196JIN\u00042\u0001I* \u0013\t!\u0016FA\u0002TKR\f\u0011eZ3u\u0013:DWM]5uC\ndW-\u0011;ue&\u0014W\u000f^3%I\u00164\u0017-\u001e7uIM*\u0012a\u0016\u0016\u0003%b[\u0013!\u0017\t\u00035~k\u0011a\u0017\u0006\u00039v\u000b\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005y;\u0012AC1o]>$\u0018\r^5p]&\u0011\u0001m\u0017\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017A\u00035bm\u0016\u001c\u0016-\\3JIR\u00191M\u001a5\u0011\u0005Y!\u0017BA3\u0018\u0005\u001d\u0011un\u001c7fC:DQa\u001a\u0005A\u00021\n\u0011!\u0019\u0005\u0006S\"\u0001\r\u0001L\u0001\u0002E\u0002")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/pdf/ObjIdUtils.class */
public final class ObjIdUtils {
    public static boolean haveSameId(final COSObjectable a, final COSObjectable b) {
        return ObjIdUtils$.MODULE$.haveSameId(a, b);
    }

    public static COSBase getInheritableAttribute(final COSDictionary node, final COSName key, final Set<String> visitedObjIds) {
        return ObjIdUtils$.MODULE$.getInheritableAttribute(node, key, visitedObjIds);
    }

    public static String objIdOfOrEmpty(final COSObjectable o) {
        return ObjIdUtils$.MODULE$.objIdOfOrEmpty(o);
    }

    public static String objIdOf(final COSObjectKey ident) {
        return ObjIdUtils$.MODULE$.objIdOf(ident);
    }

    public static String objIdOf(final COSObjectable o) {
        return ObjIdUtils$.MODULE$.objIdOf(o);
    }
}
