package code.util.pdf;

import com.github.jaiimageio.plugins.tiff.TIFFImageWriteParam;
import java.io.File;
import java.util.Locale;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageOutputStream;
import org.sejda.model.SejdaFileExtensions;
import scala.collection.IterableOnceOps;
import scala.collection.JavaConverters$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

/* compiled from: PdfTiffConverter.scala */
@ScalaSignature(bytes = "\u0006\u0005E4A\u0001E\t\u00011!Aq\u0004\u0001B\u0001B\u0003%\u0001\u0005C\u0003)\u0001\u0011\u0005\u0011\u0006C\u0004.\u0001\u0001\u0007I\u0011\u0001\u0018\t\u000fI\u0002\u0001\u0019!C\u0001g!1\u0011\b\u0001Q!\n=BqA\u000f\u0001C\u0002\u0013\u00051\b\u0003\u0004E\u0001\u0001\u0006I\u0001\u0010\u0005\b\u000b\u0002\u0011\r\u0011\"\u0001G\u0011\u0019)\u0006\u0001)A\u0005\u000f\"9a\u000b\u0001b\u0001\n\u00039\u0006B\u00020\u0001A\u0003%\u0001\fC\u0004`\u0001\t\u0007I\u0011\u00011\t\r\u001d\u0004\u0001\u0015!\u0003b\u0011\u0015A\u0007\u0001\"\u0001j\u0011\u0015y\u0007\u0001\"\u0001q\u0005-!\u0016N\u001a4Ck&dG-\u001a:\u000b\u0005I\u0019\u0012a\u00019eM*\u0011A#F\u0001\u0005kRLGNC\u0001\u0017\u0003\u0011\u0019w\u000eZ3\u0004\u0001M\u0011\u0001!\u0007\t\u00035ui\u0011a\u0007\u0006\u00029\u0005)1oY1mC&\u0011ad\u0007\u0002\u0007\u0003:L(+\u001a4\u0002\u0015=,H\u000f];u)&4g\r\u0005\u0002\"M5\t!E\u0003\u0002$I\u0005\u0011\u0011n\u001c\u0006\u0002K\u0005!!.\u0019<b\u0013\t9#E\u0001\u0003GS2,\u0017A\u0002\u001fj]&$h\b\u0006\u0002+YA\u00111\u0006A\u0007\u0002#!)qD\u0001a\u0001A\u0005)\u0011N\u001c3fqV\tq\u0006\u0005\u0002\u001ba%\u0011\u0011g\u0007\u0002\u0004\u0013:$\u0018!C5oI\u0016Dx\fJ3r)\t!t\u0007\u0005\u0002\u001bk%\u0011ag\u0007\u0002\u0005+:LG\u000fC\u00049\t\u0005\u0005\t\u0019A\u0018\u0002\u0007a$\u0013'\u0001\u0004j]\u0012,\u0007\u0010I\u0001\u0007oJLG/\u001a:\u0016\u0003q\u0002\"!\u0010\"\u000e\u0003yR!a\u0010!\u0002\u000f%l\u0017mZ3j_*\t\u0011)A\u0003kCZ\f\u00070\u0003\u0002D}\tY\u0011*\\1hK^\u0013\u0018\u000e^3s\u0003\u001d9(/\u001b;fe\u0002\na\u0002^5gM^\u0013\u0018\u000e^3QCJ\fW.F\u0001H!\tA5+D\u0001J\u0015\tQ5*\u0001\u0003uS\u001a4'B\u0001'N\u0003\u001d\u0001H.^4j]NT!AT(\u0002\u0015)\f\u0017.[7bO\u0016LwN\u0003\u0002Q#\u00061q-\u001b;ik\nT\u0011AU\u0001\u0004G>l\u0017B\u0001+J\u0005M!\u0016J\u0012$J[\u0006<Wm\u0016:ji\u0016\u0004\u0016M]1n\u0003=!\u0018N\u001a4Xe&$X\rU1sC6\u0004\u0013AD:ue\u0016\fW.T3uC\u0012\fG/Y\u000b\u00021B\u0011\u0011\fX\u0007\u00025*\u00111LP\u0001\t[\u0016$\u0018\rZ1uC&\u0011QL\u0017\u0002\f\u0013&{U*\u001a;bI\u0006$\u0018-A\btiJ,\u0017-\\'fi\u0006$\u0017\r^1!\u0003\rIwn]\u000b\u0002CB\u0011!-Z\u0007\u0002G*\u0011AMP\u0001\u0007gR\u0014X-Y7\n\u0005\u0019\u001c'!E%nC\u001e,w*\u001e;qkR\u001cFO]3b[\u0006!\u0011n\\:!\u0003\u00159(/\u001b;f)\t!$\u000eC\u0003l\u001d\u0001\u0007A.A\u0003j[\u0006<W\r\u0005\u0002>[&\u0011aN\u0010\u0002\t\u0013&{\u0015*\\1hK\u0006)1\r\\8tKR\tA\u0007")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/pdf/TiffBuilder.class */
public class TiffBuilder {
    private int index = 0;
    private final ImageWriter writer = (ImageWriter) ((IterableOnceOps) JavaConverters$.MODULE$.asScalaIteratorConverter(ImageIO.getImageWritersByFormatName(SejdaFileExtensions.TIFF_EXTENSION)).asScala()).find(x$1 -> {
        return BoxesRunTime.boxToBoolean($anonfun$writer$1(x$1));
    }).getOrElse(() -> {
        throw new RuntimeException("Could not find JAI writer for TIFF images");
    });
    private final TIFFImageWriteParam tiffWriteParam = new TIFFImageWriteParam(Locale.US);
    private final IIOMetadata streamMetadata;
    private final ImageOutputStream ios;

    public TiffBuilder(final File outputTiff) {
        tiffWriteParam().setCompressionMode(0);
        this.streamMetadata = writer().getDefaultStreamMetadata(tiffWriteParam());
        this.ios = ImageIO.createImageOutputStream(outputTiff);
        writer().setOutput(ios());
    }

    public int index() {
        return this.index;
    }

    public void index_$eq(final int x$1) {
        this.index = x$1;
    }

    public ImageWriter writer() {
        return this.writer;
    }

    public static final /* synthetic */ boolean $anonfun$writer$1(final ImageWriter x$1) {
        return x$1.getClass().getCanonicalName().contains("jai");
    }

    public TIFFImageWriteParam tiffWriteParam() {
        return this.tiffWriteParam;
    }

    public IIOMetadata streamMetadata() {
        return this.streamMetadata;
    }

    public ImageOutputStream ios() {
        return this.ios;
    }

    public void write(final IIOImage image) {
        if (index() == 0) {
            writer().write(streamMetadata(), image, tiffWriteParam());
        } else {
            writer().writeInsert(index(), image, tiffWriteParam());
        }
        index_$eq(index() + 1);
    }

    public void close() {
        ios().close();
        writer().dispose();
    }
}
