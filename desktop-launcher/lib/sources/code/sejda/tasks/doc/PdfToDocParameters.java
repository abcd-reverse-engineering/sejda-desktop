package code.sejda.tasks.doc;

import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;
import scala.reflect.ScalaSignature;

/* compiled from: PdfToDocParameters.scala */
@ScalaSignature(bytes = "\u0006\u0005)2A\u0001B\u0003\u0001\u001d!AA\u0004\u0001BC\u0002\u0013\u0005Q\u0004\u0003\u0005%\u0001\t\u0005\t\u0015!\u0003\u001f\u0011\u0015)\u0003\u0001\"\u0001'\u0005I\u0001FM\u001a+p\t>\u001c\u0007+\u0019:b[\u0016$XM]:\u000b\u0005\u00199\u0011a\u00013pG*\u0011\u0001\"C\u0001\u0006i\u0006\u001c8n\u001d\u0006\u0003\u0015-\tQa]3kI\u0006T\u0011\u0001D\u0001\u0005G>$Wm\u0001\u0001\u0014\u0005\u0001y\u0001C\u0001\t\u001b\u001b\u0005\t\"B\u0001\n\u0014\u0003\u0011\u0011\u0017m]3\u000b\u0005Q)\u0012!\u00039be\u0006lW\r^3s\u0015\t1r#A\u0003n_\u0012,GN\u0003\u0002\u000b1)\t\u0011$A\u0002pe\u001eL!aG\t\u0003S5+H\u000e^5qY\u0016\u0004FMZ*pkJ\u001cW-T;mi&\u0004H.Z(viB,H\u000fU1sC6,G/\u001a:t\u00031\u0011Xm]5{K&k\u0017mZ3t+\u0005q\u0002CA\u0010#\u001b\u0005\u0001#\"A\u0011\u0002\u000bM\u001c\u0017\r\\1\n\u0005\r\u0002#a\u0002\"p_2,\u0017M\\\u0001\u000ee\u0016\u001c\u0018N_3J[\u0006<Wm\u001d\u0011\u0002\rqJg.\u001b;?)\t9\u0013\u0006\u0005\u0002)\u00015\tQ\u0001C\u0003\u001d\u0007\u0001\u0007a\u0004")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/doc/PdfToDocParameters.class */
public class PdfToDocParameters extends MultiplePdfSourceMultipleOutputParameters {
    private final boolean resizeImages;

    public boolean resizeImages() {
        return this.resizeImages;
    }

    public PdfToDocParameters(final boolean resizeImages) {
        this.resizeImages = resizeImages;
    }
}
