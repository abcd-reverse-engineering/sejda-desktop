package code.util;

import scala.reflect.ScalaSignature;

/* compiled from: FormatHelpers.scala */
@ScalaSignature(bytes = "\u0006\u0005!;Q!\u0003\u0006\t\u0002=1Q!\u0005\u0006\t\u0002IAQ!G\u0001\u0005\u0002iAQaG\u0001\u0005\u0002qAQAM\u0001\u0005\u0002MBQ!N\u0001\u0005\u0002YBQ\u0001O\u0001\u0005\u0002eBQaO\u0001\u0005\nqBQ!Q\u0001\u0005\n\u0015\u000bQBR8s[\u0006$\b*\u001a7qKJ\u001c(BA\u0006\r\u0003\u0011)H/\u001b7\u000b\u00035\tAaY8eK\u000e\u0001\u0001C\u0001\t\u0002\u001b\u0005Q!!\u0004$pe6\fG\u000fS3ma\u0016\u00148o\u0005\u0002\u0002'A\u0011AcF\u0007\u0002+)\ta#A\u0003tG\u0006d\u0017-\u0003\u0002\u0019+\t1\u0011I\\=SK\u001a\fa\u0001P5oSRtD#A\b\u0002#\u0005dw/Y=t)^|G)Z2j[\u0006d7\u000f\u0006\u0002\u001eQA\u0011a$\n\b\u0003?\r\u0002\"\u0001I\u000b\u000e\u0003\u0005R!A\t\b\u0002\rq\u0012xn\u001c;?\u0013\t!S#\u0001\u0004Qe\u0016$WMZ\u0005\u0003M\u001d\u0012aa\u0015;sS:<'B\u0001\u0013\u0016\u0011\u0015I3\u00011\u0001+\u0003\u0005q\u0007CA\u00161\u001b\u0005a#BA\u0017/\u0003\u0011a\u0017M\\4\u000b\u0003=\nAA[1wC&\u0011\u0011\u0007\f\u0002\u0007\u001dVl'-\u001a:\u0002#Q<x\u000eR3dS6\fGn](s\u001d>tW\r\u0006\u0002\u001ei!)\u0011\u0006\u0002a\u0001U\u0005\u0001R.Y=cKR;x\u000eR3dS6\fGn\u001d\u000b\u0003;]BQ!K\u0003A\u0002)\nq\"\\1zE\u0016|e.\u001a#fG&l\u0017\r\u001c\u000b\u0003;iBQ!\u000b\u0004A\u0002)\nQ\"\\1zE\u0016$UmY5nC2\u001cHcA\u001f@\u0001B\u00111FP\u0005\u0003M1BQ!K\u0004A\u0002)BQ!Q\u0004A\u0002\t\u000b\u0001\u0002Z3dS6\fGn\u001d\t\u0003)\rK!\u0001R\u000b\u0003\u0007%sG\u000fF\u0002>\r\u001eCQ!\u000b\u0005A\u0002)BQ!\u0011\u0005A\u0002\t\u0003")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/FormatHelpers.class */
public final class FormatHelpers {
    public static String maybeOneDecimal(final Number n) {
        return FormatHelpers$.MODULE$.maybeOneDecimal(n);
    }

    public static String maybeTwoDecimals(final Number n) {
        return FormatHelpers$.MODULE$.maybeTwoDecimals(n);
    }

    public static String twoDecimalsOrNone(final Number n) {
        return FormatHelpers$.MODULE$.twoDecimalsOrNone(n);
    }

    public static String alwaysTwoDecimals(final Number n) {
        return FormatHelpers$.MODULE$.alwaysTwoDecimals(n);
    }
}
