package code.util.imageio;

import java.awt.image.BufferedImage;
import javax.imageio.IIOImage;
import scala.reflect.ScalaSignature;

/* compiled from: ImageIOUtils.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005\u0005q!B\u0005\u000b\u0011\u0003\tb!B\n\u000b\u0011\u0003!\u0002\"B\u0010\u0002\t\u0003\u0001\u0003\"B\u0011\u0002\t\u0003\u0011\u0003\"\u0002'\u0002\t\u0013i\u0005\"B.\u0002\t\u0013a\u0006\"\u00023\u0002\t\u0003)\u0007\"\u00023\u0002\t\u0003q\u0007\"\u0002;\u0002\t\u0003)\u0018\u0001D%nC\u001e,\u0017jT+uS2\u001c(BA\u0006\r\u0003\u001dIW.Y4fS>T!!\u0004\b\u0002\tU$\u0018\u000e\u001c\u0006\u0002\u001f\u0005!1m\u001c3f\u0007\u0001\u0001\"AE\u0001\u000e\u0003)\u0011A\"S7bO\u0016Lu*\u0016;jYN\u001c2!A\u000b\u001c!\t1\u0012$D\u0001\u0018\u0015\u0005A\u0012!B:dC2\f\u0017B\u0001\u000e\u0018\u0005\u0019\te.\u001f*fMB\u0011A$H\u0007\u0002\u0019%\u0011a\u0004\u0004\u0002\t\u0019><w-\u00192mK\u00061A(\u001b8jiz\"\u0012!E\u0001\u000bi>L\u0015jT%nC\u001e,G#B\u0012+k\t;\u0005C\u0001\u0013)\u001b\u0005)#BA\u0006'\u0015\u00059\u0013!\u00026bm\u0006D\u0018BA\u0015&\u0005!I\u0015jT%nC\u001e,\u0007\"B\u0016\u0004\u0001\u0004a\u0013!B5nC\u001e,\u0007CA\u00174\u001b\u0005q#BA\u00160\u0015\t\u0001\u0014'A\u0002boRT\u0011AM\u0001\u0005U\u00064\u0018-\u0003\u00025]\ti!)\u001e4gKJ,G-S7bO\u0016DQAN\u0002A\u0002]\naAZ8s[\u0006$\bC\u0001\u001d@\u001d\tIT\b\u0005\u0002;/5\t1H\u0003\u0002=!\u00051AH]8pizJ!AP\f\u0002\rA\u0013X\rZ3g\u0013\t\u0001\u0015I\u0001\u0004TiJLgn\u001a\u0006\u0003}]AQaQ\u0002A\u0002\u0011\u000b1\u0001\u001a9j!\t1R)\u0003\u0002G/\t\u0019\u0011J\u001c;\t\u000b!\u001b\u0001\u0019A%\u0002\u000fE,\u0018\r\\5usB\u0011aCS\u0005\u0003\u0017^\u0011QA\u00127pCR\faa]3u\tBKE\u0003\u0002(R1f\u0003\"AF(\n\u0005A;\"\u0001B+oSRDQA\u0015\u0003A\u0002M\u000b\u0001\"\\3uC\u0012\fG/\u0019\t\u0003)Zk\u0011!\u0016\u0006\u0003%\u0016J!aV+\u0003\u0017%Ku*T3uC\u0012\fG/\u0019\u0005\u0006\u0007\u0012\u0001\r\u0001\u0012\u0005\u00065\u0012\u0001\raN\u0001\u000bM>\u0014X.\u0019;OC6,\u0017\u0001F4fi>\u00138I]3bi\u0016\u001c\u0005.\u001b7e\u001d>$W\rF\u0002^A\n\u0004\"\u0001\u00160\n\u0005}+&aD%J\u001f6+G/\u00193bi\u0006tu\u000eZ3\t\u000b\u0005,\u0001\u0019A/\u0002\u0015A\f'/\u001a8u\u001d>$W\rC\u0003d\u000b\u0001\u0007q'\u0001\u0003oC6,\u0017aB5t\u00052\f7m\u001b\u000b\u0005M&TG\u000e\u0005\u0002\u0017O&\u0011\u0001n\u0006\u0002\b\u0005>|G.Z1o\u0011\u0015Yc\u00011\u0001-\u0011\u0015Yg\u00011\u0001E\u0003\u0005A\b\"B7\u0007\u0001\u0004!\u0015!A=\u0015\u000b\u0019|\u0007/\u001d:\t\u000b-:\u0001\u0019\u0001\u0017\t\u000b-<\u0001\u0019\u0001#\t\u000b5<\u0001\u0019\u0001#\t\u000bM<\u0001\u0019\u0001#\u0002\u001f1,X.\u001b8b]\u000e,7)\u001e;PM\u001a\faA]8uCR,G#\u0002\u0017wort\b\"B\u0016\t\u0001\u0004a\u0003\"\u0002=\t\u0001\u0004I\u0018!B1oO2,\u0007C\u0001\f{\u0013\tYxC\u0001\u0004E_V\u0014G.\u001a\u0005\u0006{\"\u0001\r\u0001R\u0001\u0004?\u000eD\b\"B@\t\u0001\u0004!\u0015aA0ds\u0002")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/imageio/ImageIOUtils.class */
public final class ImageIOUtils {
    public static BufferedImage rotate(final BufferedImage image, final double angle, final int _cx, final int _cy) {
        return ImageIOUtils$.MODULE$.rotate(image, angle, _cx, _cy);
    }

    public static boolean isBlack(final BufferedImage image, final int x, final int y, final int luminanceCutOff) {
        return ImageIOUtils$.MODULE$.isBlack(image, x, y, luminanceCutOff);
    }

    public static boolean isBlack(final BufferedImage image, final int x, final int y) {
        return ImageIOUtils$.MODULE$.isBlack(image, x, y);
    }

    public static IIOImage toIIOImage(final BufferedImage image, final String format, final int dpi, final float quality) {
        return ImageIOUtils$.MODULE$.toIIOImage(image, format, dpi, quality);
    }
}
