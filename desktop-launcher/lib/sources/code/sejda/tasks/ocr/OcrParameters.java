package code.sejda.tasks.ocr;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;
import scala.Option;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;

/* compiled from: OcrParameters.scala */
@ScalaSignature(bytes = "\u0006\u0005M4A!\u0004\b\u0001/!AQ\u0005\u0001BC\u0002\u0013\u0005a\u0005\u0003\u00059\u0001\t\u0005\t\u0015!\u0003(\u0011!I\u0004A!b\u0001\n\u0003Q\u0004\u0002\u0003%\u0001\u0005\u0003\u0005\u000b\u0011B\u001e\t\u000b%\u0003A\u0011\u0001&\t\u000b9\u0003A\u0011I(\t\u000ba\u0003A\u0011I-\b\u000fus\u0011\u0011!E\u0001=\u001a9QBDA\u0001\u0012\u0003y\u0006\"B%\n\t\u0003\u0019\u0007b\u00023\n#\u0003%\t!\u001a\u0005\ba&\t\n\u0011\"\u0001r\u00055y5M\u001d)be\u0006lW\r^3sg*\u0011q\u0002E\u0001\u0004_\u000e\u0014(BA\t\u0013\u0003\u0015!\u0018m]6t\u0015\t\u0019B#A\u0003tK*$\u0017MC\u0001\u0016\u0003\u0011\u0019w\u000eZ3\u0004\u0001M\u0011\u0001\u0001\u0007\t\u00033\rj\u0011A\u0007\u0006\u00037q\tAAY1tK*\u0011QDH\u0001\na\u0006\u0014\u0018-\\3uKJT!a\b\u0011\u0002\u000b5|G-\u001a7\u000b\u0005M\t#\"\u0001\u0012\u0002\u0007=\u0014x-\u0003\u0002%5\tIS*\u001e7uSBdW\r\u00153g'>,(oY3Nk2$\u0018\u000e\u001d7f\u001fV$\b/\u001e;QCJ\fW.\u001a;feN\f\u0001\u0002\\1oOV\fw-Z\u000b\u0002OA\u0019\u0001fK\u0017\u000e\u0003%R\u0011AK\u0001\u0006g\u000e\fG.Y\u0005\u0003Y%\u0012aa\u00149uS>t\u0007C\u0001\u00186\u001d\ty3\u0007\u0005\u00021S5\t\u0011G\u0003\u00023-\u00051AH]8pizJ!\u0001N\u0015\u0002\rA\u0013X\rZ3g\u0013\t1tG\u0001\u0004TiJLgn\u001a\u0006\u0003i%\n\u0011\u0002\\1oOV\fw-\u001a\u0011\u0002\u001b=,H\u000f];u\r>\u0014X.\u0019;t+\u0005Y\u0004c\u0001\u001fB\t:\u0011Qh\u0010\b\u0003ayJ\u0011AK\u0005\u0003\u0001&\nq\u0001]1dW\u0006<W-\u0003\u0002C\u0007\n\u00191+Z9\u000b\u0005\u0001K\u0003CA#G\u001b\u0005q\u0011BA$\u000f\u00051yU\u000f\u001e9vi\u001a{'/\\1u\u00039yW\u000f\u001e9vi\u001a{'/\\1ug\u0002\na\u0001P5oSRtDcA&M\u001bB\u0011Q\t\u0001\u0005\bK\u0015\u0001\n\u00111\u0001(\u0011\u001dIT\u0001%AA\u0002m\na!Z9vC2\u001cHC\u0001)T!\tA\u0013+\u0003\u0002SS\t9!i\\8mK\u0006t\u0007\"\u0002+\u0007\u0001\u0004)\u0016!B8uQ\u0016\u0014\bC\u0001\u0015W\u0013\t9\u0016FA\u0002B]f\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u00025B\u0011\u0001fW\u0005\u00039&\u00121!\u00138u\u00035y5M\u001d)be\u0006lW\r^3sgB\u0011Q)C\n\u0003\u0013\u0001\u0004\"\u0001K1\n\u0005\tL#AB!osJ+g\rF\u0001_\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%cU\taM\u000b\u0002(O.\n\u0001\u000e\u0005\u0002j]6\t!N\u0003\u0002lY\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0003[&\n!\"\u00198o_R\fG/[8o\u0013\ty'NA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\f1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\u0012T#\u0001:+\u0005m:\u0007")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/ocr/OcrParameters.class */
public class OcrParameters extends MultiplePdfSourceMultipleOutputParameters {
    private final Option<String> language;
    private final Seq<OutputFormat> outputFormats;

    public Option<String> language() {
        return this.language;
    }

    public OcrParameters(final Option<String> language, final Seq<OutputFormat> outputFormats) {
        this.language = language;
        this.outputFormats = outputFormats;
    }

    public Seq<OutputFormat> outputFormats() {
        return this.outputFormats;
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(final Object other) {
        if (other instanceof OcrParameters) {
            OcrParameters ocrParameters = (OcrParameters) other;
            return new EqualsBuilder().appendSuper(super.equals(ocrParameters)).append(language(), ocrParameters.language()).append(outputFormats(), ocrParameters.outputFormats()).isEquals();
        }
        return false;
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(language()).append(outputFormats()).toHashCode();
    }
}
