package code.sejda.tasks.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.sejda.commons.util.IOUtils;
import org.sejda.io.CountingWritableByteChannel;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSNumber;
import org.sejda.sambox.cos.COSString;
import org.sejda.sambox.output.ContentStreamWriter;
import org.sejda.sambox.pdmodel.common.PDStream;
import org.sejda.sambox.pdmodel.graphics.state.RenderingMode;
import scala.collection.IterableOnceOps;
import scala.collection.JavaConverters$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

/* compiled from: RedactedStream.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005\rd\u0001B\n\u0015\u0001uAQ\u0001\n\u0001\u0005\u0002\u0015Bq\u0001\u000b\u0001C\u0002\u0013%\u0011\u0006\u0003\u00047\u0001\u0001\u0006IA\u000b\u0005\bo\u0001\u0011\r\u0011\"\u00039\u0011\u0019y\u0004\u0001)A\u0005s!9\u0001\t\u0001b\u0001\n\u0013\t\u0005BB'\u0001A\u0003%!\tC\u0004O\u0001\u0001\u0007I\u0011B(\t\u000fM\u0003\u0001\u0019!C\u0005)\"1!\f\u0001Q!\nACQa\u0017\u0001\u0005\u0002qCQ!\u001d\u0001\u0005\nIDQa\u001d\u0001\u0005\nIDQ\u0001\u001e\u0001\u0005\nIDa!a\u0013\u0001\t\u0003\u0011\bBBA-\u0001\u0011\u0005\u0011\u0006\u0003\u0004\u0002\\\u0001!\ta\u0014\u0005\b\u0003;\u0002A\u0011AA0\u00059\u0011V\rZ1di\u0016$7\u000b\u001e:fC6T!!\u0006\f\u0002\r\r|W.\\8o\u0015\t9\u0002$A\u0003uCN\\7O\u0003\u0002\u001a5\u0005)1/\u001a6eC*\t1$\u0001\u0003d_\u0012,7\u0001A\n\u0003\u0001y\u0001\"a\b\u0012\u000e\u0003\u0001R\u0011!I\u0001\u0006g\u000e\fG.Y\u0005\u0003G\u0001\u0012a!\u00118z%\u00164\u0017A\u0002\u001fj]&$h\bF\u0001'!\t9\u0003!D\u0001\u0015\u0003\u0019\u0019HO]3b[V\t!\u0006\u0005\u0002,i5\tAF\u0003\u0002\u0016[)\u0011afL\u0001\ba\u0012lw\u000eZ3m\u0015\t\u0001\u0014'\u0001\u0004tC6\u0014w\u000e\u001f\u0006\u00033IR\u0011aM\u0001\u0004_J<\u0017BA\u001b-\u0005!\u0001Fi\u0015;sK\u0006l\u0017aB:ue\u0016\fW\u000eI\u0001\rgR\u0014X-Y7Xe&$XM]\u000b\u0002sA\u0011!(P\u0007\u0002w)\u0011AhL\u0001\u0007_V$\b/\u001e;\n\u0005yZ$aE\"p]R,g\u000e^*ue\u0016\fWn\u0016:ji\u0016\u0014\u0018!D:ue\u0016\fWn\u0016:ji\u0016\u0014\b%\u0001\u0004ck\u001a4WM]\u000b\u0002\u0005B\u00191\t\u0013&\u000e\u0003\u0011S!!\u0012$\u0002\tU$\u0018\u000e\u001c\u0006\u0002\u000f\u0006!!.\u0019<b\u0013\tIEIA\u0005BeJ\f\u0017\u0010T5tiB\u0011qeS\u0005\u0003\u0019R\u00111c\u00149fe\u0006$xN]!oI>\u0003XM]1oIN\fqAY;gM\u0016\u0014\b%\u0001\u0005sK\u0012\f7\r^3e+\u0005\u0001\u0006CA\u0010R\u0013\t\u0011\u0006EA\u0004C_>dW-\u00198\u0002\u0019I,G-Y2uK\u0012|F%Z9\u0015\u0005UC\u0006CA\u0010W\u0013\t9\u0006E\u0001\u0003V]&$\bbB-\n\u0003\u0003\u0005\r\u0001U\u0001\u0004q\u0012\n\u0014!\u0003:fI\u0006\u001cG/\u001a3!\u0003\u00159(/\u001b;f)\r)VL\u001a\u0005\u0006=.\u0001\raX\u0001\t_B,'/\u0019;peB\u0011\u0001\rZ\u0007\u0002C*\u0011aL\u0019\u0006\u0003G>\nQbY8oi\u0016tGo\u001d;sK\u0006l\u0017BA3b\u0005!y\u0005/\u001a:bi>\u0014\b\"B4\f\u0001\u0004A\u0017\u0001C8qKJ\fg\u000eZ:\u0011\u0007\rK7.\u0003\u0002k\t\n!A*[:u!\taw.D\u0001n\u0015\tqw&A\u0002d_NL!\u0001]7\u0003\u000f\r{5KQ1tK\u0006Y\u0001o\\:u!J|7-Z:t)\u0005)\u0016\u0001\u00125b]\u0012dWm\u00142kK\u000e$(\t\\8dWN<\u0016\u000e\u001e5O_NCwn\u001e+fqR|\u0005/\u001a:bi>\u00148/\u00118e\u001d\u0016LG\u000f[3s\u00072L\u0007OU3oI\u0016\u0014\u0018N\\4N_\u0012,\u0017!\u00024mkND\u0007f\u0001\bw\u007fB\u0019qd^=\n\u0005a\u0004#A\u0002;ie><8\u000f\u0005\u0002{{6\t1P\u0003\u0002}\r\u0006\u0011\u0011n\\\u0005\u0003}n\u00141\"S(Fq\u000e,\u0007\u000f^5p]F:a$!\u0001\u0002\u0018\u0005%\u0003\u0003BA\u0002\u0003#qA!!\u0002\u0002\u000eA\u0019\u0011q\u0001\u0011\u000e\u0005\u0005%!bAA\u00069\u00051AH]8pizJ1!a\u0004!\u0003\u0019\u0001&/\u001a3fM&!\u00111CA\u000b\u0005\u0019\u0019FO]5oO*\u0019\u0011q\u0002\u00112\u0013\r\nI\"!\t\u0002@\u0005\rR\u0003BA\u000e\u0003;)\"!!\u0001\u0005\u000f\u0005}AD1\u0001\u0002*\t\tA+\u0003\u0003\u0002$\u0005\u0015\u0012a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$\u0013GC\u0002\u0002(\u0001\na\u0001\u001e5s_^\u001c\u0018\u0003BA\u0016\u0003c\u00012aHA\u0017\u0013\r\ty\u0003\t\u0002\b\u001d>$\b.\u001b8h!\u0011\t\u0019$!\u000f\u000f\u0007}\t)$C\u0002\u00028\u0001\nq\u0001]1dW\u0006<W-\u0003\u0003\u0002<\u0005u\"!\u0003+ie><\u0018M\u00197f\u0015\r\t9\u0004I\u0019\nG\u0005\u0005\u00131IA#\u0003Oq1aHA\"\u0013\r\t9\u0003I\u0019\u0006E}\u0001\u0013q\t\u0002\u0006g\u000e\fG.Y\u0019\u0003Me\fQa\u00197pg\u0016DCa\u0004<\u0002PE:a$!\u0001\u0002R\u0005]\u0013'C\u0012\u0002\u001a\u0005\u0005\u00121KA\u0012c%\u0019\u0013\u0011IA\"\u0003+\n9#M\u0003#?\u0001\n9%\r\u0002's\u0006Iq-\u001a;TiJ,\u0017-\\\u0001\fO\u0016$(+\u001a3bGR,G-A\u0006tKR\u0014V\rZ1di\u0016$GcA+\u0002b!)aJ\u0005a\u0001!\u0002")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/RedactedStream.class */
public class RedactedStream {
    private final PDStream stream = new PDStream();
    private final ContentStreamWriter streamWriter = new ContentStreamWriter(CountingWritableByteChannel.from(stream().createOutputStream(COSName.FLATE_DECODE)));
    private final ArrayList<OperatorAndOperands> buffer = new ArrayList<>(512);
    private boolean redacted = false;

    private PDStream stream() {
        return this.stream;
    }

    private ContentStreamWriter streamWriter() {
        return this.streamWriter;
    }

    private ArrayList<OperatorAndOperands> buffer() {
        return this.buffer;
    }

    private boolean redacted() {
        return this.redacted;
    }

    private void redacted_$eq(final boolean x$1) {
        this.redacted = x$1;
    }

    public void write(final Operator operator, final List<COSBase> operands) {
        buffer().add(OperatorAndOperands$.MODULE$.apply(operator, operands));
    }

    private void postProcess() {
        handleObjectBlocksWithNoShowTextOperatorsAndNeitherClipRenderingMode();
    }

    private void handleObjectBlocksWithNoShowTextOperatorsAndNeitherClipRenderingMode() {
        int startIdx = -1;
        boolean hasNoTextShowingOperators = true;
        RenderingMode renderingMode = null;
        int i = 0;
        while (i < buffer().size()) {
            OperatorAndOperands o = buffer().get(i);
            String name = o.operator().getName();
            switch (name == null ? 0 : name.hashCode()) {
                case 34:
                    if (OperatorName.SHOW_TEXT_LINE_AND_SPACE.equals(name)) {
                    }
                    break;
                case 39:
                    if (OperatorName.SHOW_TEXT_LINE.equals(name)) {
                    }
                    break;
                case 2130:
                    if (!OperatorName.BEGIN_TEXT.equals(name)) {
                        break;
                    } else {
                        startIdx = i;
                        hasNoTextShowingOperators = true;
                        break;
                    }
                case 2223:
                    if (!OperatorName.END_TEXT.equals(name)) {
                        break;
                    } else {
                        if (startIdx >= 0 && hasNoTextShowingOperators && renderingMode == RenderingMode.NEITHER_CLIP) {
                            buffer().add(i - 1, OperatorAndOperands$.MODULE$.apply(Operator.getOperator(OperatorName.SHOW_TEXT), Arrays.asList(COSString.parseLiteral(" "))));
                            i++;
                        }
                        startIdx = -1;
                        break;
                    }
                    break;
                case 2678:
                    if (OperatorName.SHOW_TEXT_ADJUSTED.equals(name)) {
                    }
                    break;
                case 2710:
                    if (OperatorName.SHOW_TEXT.equals(name) && startIdx > 0) {
                        hasNoTextShowingOperators = false;
                        break;
                    } else {
                        break;
                    }
                    break;
                case 2718:
                    if (!OperatorName.SET_TEXT_RENDERINGMODE.equals(name)) {
                        break;
                    } else {
                        try {
                            renderingMode = RenderingMode.fromInt(((COSNumber) o.operands().apply(0)).intValue());
                            break;
                        } catch (Exception e) {
                            break;
                        }
                    }
            }
            i++;
        }
    }

    private void flush() throws IOException {
        postProcess();
        ((IterableOnceOps) JavaConverters$.MODULE$.asScalaBufferConverter(buffer()).asScala()).foreach(o -> {
            $anonfun$flush$1(this, o);
            return BoxedUnit.UNIT;
        });
    }

    public static final /* synthetic */ void $anonfun$flush$1(final RedactedStream $this, final OperatorAndOperands o) throws IOException {
        $this.streamWriter().writeTokens(new ArrayList((Collection) JavaConverters$.MODULE$.seqAsJavaListConverter(o.operands()).asJava()));
        $this.streamWriter().writeTokens(o.operator());
    }

    public void close() throws IOException {
        flush();
        IOUtils.close(streamWriter());
    }

    public PDStream getStream() {
        return stream();
    }

    public boolean getRedacted() {
        return redacted();
    }

    public void setRedacted(final boolean redacted) {
        redacted_$eq(redacted);
    }
}
