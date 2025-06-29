package code.util.encoding;

import org.sejda.io.SeekableSource;
import scala.Option;
import scala.reflect.ScalaSignature;

/* compiled from: FileContentsHelper.scala */
@ScalaSignature(bytes = "\u0006\u0005u;QAC\u0006\t\u0002I1Q\u0001F\u0006\t\u0002UAQ\u0001H\u0001\u0005\u0002uAqAH\u0001C\u0002\u0013\u0005q\u0004\u0003\u0004)\u0003\u0001\u0006I\u0001\t\u0005\bS\u0005\u0011\r\u0011\"\u0001+\u0011\u0019q\u0013\u0001)A\u0005W!)q&\u0001C\u0001a!)!*\u0001C\u0001\u0017\")\u0011+\u0001C\u0005%\u0006\u0011b)\u001b7f\u0007>tG/\u001a8ug\"+G\u000e]3s\u0015\taQ\"\u0001\u0005f]\u000e|G-\u001b8h\u0015\tqq\"\u0001\u0003vi&d'\"\u0001\t\u0002\t\r|G-Z\u0002\u0001!\t\u0019\u0012!D\u0001\f\u0005I1\u0015\u000e\\3D_:$XM\u001c;t\u0011\u0016d\u0007/\u001a:\u0014\u0005\u00051\u0002CA\f\u001b\u001b\u0005A\"\"A\r\u0002\u000bM\u001c\u0017\r\\1\n\u0005mA\"AB!osJ+g-\u0001\u0004=S:LGO\u0010\u000b\u0002%\u00059!-Y:f+JLW#\u0001\u0011\u0011\u0005\u00052S\"\u0001\u0012\u000b\u0005\r\"\u0013\u0001\u00027b]\u001eT\u0011!J\u0001\u0005U\u00064\u0018-\u0003\u0002(E\t11\u000b\u001e:j]\u001e\f\u0001BY1tKV\u0013\u0018\u000eI\u0001\r[\u0006D\b+\u0019:tKNK'0Z\u000b\u0002WA\u0011q\u0003L\u0005\u0003[a\u00111!\u00138u\u00035i\u0017\r\u001f)beN,7+\u001b>fA\u0005qQM\\2pI&twm\u00144Ii6dGCA\u0019?!\r9\"\u0007N\u0005\u0003ga\u0011aa\u00149uS>t\u0007CA\u001b=\u001d\t1$\b\u0005\u0002815\t\u0001H\u0003\u0002:#\u00051AH]8pizJ!a\u000f\r\u0002\rA\u0013X\rZ3g\u0013\t9SH\u0003\u0002<1!)qh\u0002a\u0001\u0001\u0006q1/Z3lC\ndWmU8ve\u000e,\u0007CA!I\u001b\u0005\u0011%BA\"E\u0003\tIwN\u0003\u0002F\r\u0006)1/\u001a6eC*\tq)A\u0002pe\u001eL!!\u0013\"\u0003\u001dM+Wm[1cY\u0016\u001cv.\u001e:dK\u0006y\u0001.Y:Ii6d7i\u001c8uK:$8\u000fF\u0002M\u001fB\u0003\"aF'\n\u00059C\"a\u0002\"p_2,\u0017M\u001c\u0005\u0006\u007f!\u0001\r\u0001\u0011\u0005\u0006\u0019!\u0001\r\u0001N\u0001\u000ea\u0006\u00148/Z#oG>$\u0017N\\4\u0015\u0005E\u001a\u0006\"\u0002+\n\u0001\u0004)\u0016aB7fi\u0006$\u0016m\u001a\t\u0003-nk\u0011a\u0016\u0006\u00031f\u000bQA\\8eKNT!A\u0017$\u0002\u000b)\u001cx.\u001e9\n\u0005q;&aB#mK6,g\u000e\u001e")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/encoding/FileContentsHelper.class */
public final class FileContentsHelper {
    public static boolean hasHtmlContents(final SeekableSource seekableSource, final String encoding) {
        return FileContentsHelper$.MODULE$.hasHtmlContents(seekableSource, encoding);
    }

    public static Option<String> encodingOfHtml(final SeekableSource seekableSource) {
        return FileContentsHelper$.MODULE$.encodingOfHtml(seekableSource);
    }

    public static int maxParseSize() {
        return FileContentsHelper$.MODULE$.maxParseSize();
    }

    public static String baseUri() {
        return FileContentsHelper$.MODULE$.baseUri();
    }
}
