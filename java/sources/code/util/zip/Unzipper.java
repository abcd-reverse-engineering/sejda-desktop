package code.util.zip;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;

/* compiled from: Unzipper.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0019;Q!\u0002\u0004\t\u000251Qa\u0004\u0004\t\u0002AAQaF\u0001\u0005\u0002aAQ!G\u0001\u0005\u0002iAQ!O\u0001\u0005\u0002i\n\u0001\"\u00168{SB\u0004XM\u001d\u0006\u0003\u000f!\t1A_5q\u0015\tI!\"\u0001\u0003vi&d'\"A\u0006\u0002\t\r|G-Z\u0002\u0001!\tq\u0011!D\u0001\u0007\u0005!)fN_5qa\u0016\u00148CA\u0001\u0012!\t\u0011R#D\u0001\u0014\u0015\u0005!\u0012!B:dC2\f\u0017B\u0001\f\u0014\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\u0012!D\u0001\u0005Y&\u001cH\u000f\u0006\u0002\u001c_A\u0019A\u0004J\u0014\u000f\u0005u\u0011cB\u0001\u0010\"\u001b\u0005y\"B\u0001\u0011\r\u0003\u0019a$o\\8u}%\tA#\u0003\u0002$'\u00059\u0001/Y2lC\u001e,\u0017BA\u0013'\u0005\r\u0019V-\u001d\u0006\u0003GM\u0001\"\u0001\u000b\u0017\u000f\u0005%R\u0003C\u0001\u0010\u0014\u0013\tY3#\u0001\u0004Qe\u0016$WMZ\u0005\u0003[9\u0012aa\u0015;sS:<'BA\u0016\u0014\u0011\u0015\u00014\u00011\u00012\u0003\tIg\u000e\u0005\u00023o5\t1G\u0003\u00025k\u0005\u0011\u0011n\u001c\u0006\u0002m\u0005!!.\u0019<b\u0013\tA4GA\u0006J]B,Ho\u0015;sK\u0006l\u0017!B;ou&\u0004H\u0003B\u001e?\u007f\u0005\u0003\"A\u0005\u001f\n\u0005u\u001a\"\u0001B+oSRDQ\u0001\r\u0003A\u0002EBQ\u0001\u0011\u0003A\u0002\u001d\n\u0011\"\u001a8ueft\u0015-\\3\t\u000b\t#\u0001\u0019A\"\u0002\u000f=,HOR5mKB\u0011!\u0007R\u0005\u0003\u000bN\u0012AAR5mK\u0002")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/zip/Unzipper.class */
public final class Unzipper {
    public static void unzip(final InputStream in, final String entryName, final File outFile) throws IOException {
        Unzipper$.MODULE$.unzip(in, entryName, outFile);
    }

    public static Seq<String> list(final InputStream in) {
        return Unzipper$.MODULE$.list(in);
    }
}
