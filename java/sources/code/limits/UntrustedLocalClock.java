package code.limits;

import scala.reflect.ScalaSignature;

/* compiled from: UntrustedLocalClock.scala */
@ScalaSignature(bytes = "\u0006\u0005m;QAD\b\t\u0002Q1QAF\b\t\u0002]AQ\u0001J\u0001\u0005\u0002\u0015BqAJ\u0001C\u0002\u0013\u0005q\u0005\u0003\u0004,\u0003\u0001\u0006I\u0001\u000b\u0005\bY\u0005\u0011\r\u0011\"\u0001.\u0011\u0019\t\u0014\u0001)A\u0005]!)!'\u0001C\u0001g!9\u0011(AI\u0001\n\u0003Q\u0004\"B#\u0002\t\u00031\u0005b\u0002%\u0002#\u0003%\tA\u000f\u0005\u0006\u0013\u0006!IA\u0013\u0005\u0006\u0017\u0006!I\u0001\u0014\u0005\u00065\u0006!IAS\u0001\u0014+:$(/^:uK\u0012dunY1m\u00072|7m\u001b\u0006\u0003!E\ta\u0001\\5nSR\u001c(\"\u0001\n\u0002\t\r|G-Z\u0002\u0001!\t)\u0012!D\u0001\u0010\u0005M)f\u000e\u001e:vgR,G\rT8dC2\u001cEn\\2l'\r\t\u0001D\b\t\u00033qi\u0011A\u0007\u0006\u00027\u0005)1oY1mC&\u0011QD\u0007\u0002\u0007\u0003:L(+\u001a4\u0011\u0005}\u0011S\"\u0001\u0011\u000b\u0005\u0005\n\u0012\u0001B;uS2L!a\t\u0011\u0003\u00111{wmZ1cY\u0016\fa\u0001P5oSRtD#\u0001\u000b\u0002C\u0011,g-Y;mi\u0006cGn\\<fI\u0012KgMZ3sK:\u001cW-\u00138TK\u000e|g\u000eZ:\u0016\u0003!\u0002\"!G\u0015\n\u0005)R\"\u0001\u0002'p]\u001e\f!\u0005Z3gCVdG/\u00117m_^,G\rR5gM\u0016\u0014XM\\2f\u0013:\u001cVmY8oIN\u0004\u0013A\u00043fM\u0006,H\u000e\u001e+j[\u0016|W\u000f^\u000b\u0002]A\u0011\u0011dL\u0005\u0003ai\u00111!\u00138u\u0003=!WMZ1vYR$\u0016.\\3pkR\u0004\u0013AD1tg\u0016\u0014H/Q2dkJ\fG/\u001a\u000b\u0003i]\u0002\"!G\u001b\n\u0005YR\"\u0001B+oSRDq\u0001O\u0004\u0011\u0002\u0003\u0007\u0001&A\fbY2|w/\u001a3TK\u000e|g\u000e\u001a#jM\u001a,'/\u001a8dK\u0006A\u0012m]:feR\f5mY;sCR,G\u0005Z3gCVdG\u000fJ\u0019\u0016\u0003mR#\u0001\u000b\u001f,\u0003u\u0002\"AP\"\u000e\u0003}R!\u0001Q!\u0002\u0013Ut7\r[3dW\u0016$'B\u0001\"\u001b\u0003)\tgN\\8uCRLwN\\\u0005\u0003\t~\u0012\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u0003M\t7o]3si\u0006\u001b7-\u001e:bi\u0016\f5/\u001f8d)\t!t\tC\u00049\u0013A\u0005\t\u0019\u0001\u0015\u0002;\u0005\u001c8/\u001a:u\u0003\u000e\u001cWO]1uK\u0006\u001b\u0018P\\2%I\u00164\u0017-\u001e7uIE\nqbZ3u\u0013:$XM\u001d8fiRKW.\u001a\u000b\u0002Q\u0005\u0001rlZ3u\u0013:$XM\u001d8fiRKW.\u001a\u000b\u0003Q5CQA\u0014\u0007A\u0002=\u000baa]3sm\u0016\u0014\bC\u0001)X\u001d\t\tV\u000b\u0005\u0002S55\t1K\u0003\u0002U'\u00051AH]8pizJ!A\u0016\u000e\u0002\rA\u0013X\rZ3g\u0013\tA\u0016L\u0001\u0004TiJLgn\u001a\u0006\u0003-j\tAbZ3u\u0019>\u001c\u0017\r\u001c+j[\u0016\u0004")
/* loaded from: com.sejda.desktop-launcher-1.0.0.jar:code/limits/UntrustedLocalClock.class */
public final class UntrustedLocalClock {
    public static void assertAccurateAsync(final long allowedSecondDifference) {
        UntrustedLocalClock$.MODULE$.assertAccurateAsync(allowedSecondDifference);
    }

    public static void assertAccurate(final long allowedSecondDifference) {
        UntrustedLocalClock$.MODULE$.assertAccurate(allowedSecondDifference);
    }

    public static int defaultTimeout() {
        return UntrustedLocalClock$.MODULE$.defaultTimeout();
    }

    public static long defaultAllowedDifferenceInSeconds() {
        return UntrustedLocalClock$.MODULE$.defaultAllowedDifferenceInSeconds();
    }
}
