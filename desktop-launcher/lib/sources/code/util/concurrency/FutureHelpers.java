package code.util.concurrency;

import scala.Function0;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

/* compiled from: FutureHelpers.scala */
@ScalaSignature(bytes = "\u0006\u00055;QAB\u0004\t\u000291Q\u0001E\u0004\t\u0002EAQ\u0001G\u0001\u0005\u0002eAqAG\u0001C\u0002\u0013%1\u0004\u0003\u0004$\u0003\u0001\u0006I\u0001\b\u0005\u0006I\u0005!\t!J\u0001\u000e\rV$XO]3IK2\u0004XM]:\u000b\u0005!I\u0011aC2p]\u000e,(O]3oGfT!AC\u0006\u0002\tU$\u0018\u000e\u001c\u0006\u0002\u0019\u0005!1m\u001c3f\u0007\u0001\u0001\"aD\u0001\u000e\u0003\u001d\u0011QBR;ukJ,\u0007*\u001a7qKJ\u001c8CA\u0001\u0013!\t\u0019b#D\u0001\u0015\u0015\u0005)\u0012!B:dC2\f\u0017BA\f\u0015\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\u0012AD\u0001\u0006i&lWM]\u000b\u00029A\u0011Q$I\u0007\u0002=)\u0011!b\b\u0006\u0002A\u0005!!.\u0019<b\u0013\t\u0011cDA\u0003US6,'/\u0001\u0004uS6,'\u000fI\u0001\u0012MV$XO]3XSRDG+[7f_V$XC\u0001\u00141)\u00119c\b\u0011%\u0015\u0005!J\u0004cA\u0015-]5\t!F\u0003\u0002,)\u0005Q1m\u001c8dkJ\u0014XM\u001c;\n\u00055R#A\u0002$viV\u0014X\r\u0005\u00020a1\u0001A!B\u0019\u0006\u0005\u0004\u0011$!\u0001+\u0012\u0005M2\u0004CA\n5\u0013\t)DCA\u0004O_RD\u0017N\\4\u0011\u0005M9\u0014B\u0001\u001d\u0015\u0005\r\te.\u001f\u0005\u0006u\u0015\u0001\u001daO\u0001\u0003K\u000e\u0004\"!\u000b\u001f\n\u0005uR#\u0001E#yK\u000e,H/[8o\u0007>tG/\u001a=u\u0011\u0015yT\u00011\u0001)\u0003\u00191W\u000f^;sK\")\u0011)\u0002a\u0001\u0005\u00069A/[7f_V$\bCA\"G\u001b\u0005!%BA#+\u0003!!WO]1uS>t\u0017BA$E\u000591\u0015N\\5uK\u0012+(/\u0019;j_:Da!S\u0003\u0005\u0002\u0004Q\u0015AF8o)&lWm\\;u'V\u001c7-Z:t%\u0016\u001cX\u000f\u001c;\u0011\u0007MYe&\u0003\u0002M)\tAAHY=oC6,g\b")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/concurrency/FutureHelpers.class */
public final class FutureHelpers {
    public static <T> Future<T> futureWithTimeout(final Future<T> future, final FiniteDuration timeout, final Function0<T> onTimeoutSuccessResult, final ExecutionContext ec) {
        return FutureHelpers$.MODULE$.futureWithTimeout(future, timeout, onTimeoutSuccessResult, ec);
    }
}
