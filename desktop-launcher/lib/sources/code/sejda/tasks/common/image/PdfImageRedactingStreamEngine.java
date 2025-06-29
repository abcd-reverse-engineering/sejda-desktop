package code.sejda.tasks.common.image;

import code.sejda.tasks.common.RedactedStream;
import code.util.Loggable;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.List;
import org.sejda.sambox.contentstream.PDFStreamEngine;
import org.sejda.sambox.contentstream.operator.DrawObject;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.cos.IndirectCOSObjectIdentifier;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.PDResources;
import org.sejda.sambox.pdmodel.graphics.form.PDFormXObject;
import org.sejda.sambox.pdmodel.graphics.form.PDTransparencyGroup;
import org.slf4j.Logger;
import scala.Option$;
import scala.Some;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.mutable.HashSet;
import scala.collection.mutable.Map;
import scala.collection.mutable.Map$;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;

/* compiled from: PdfImageRedactingStreamEngine.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005eh\u0001B\u000b\u0017\u0001\u0005BQa\r\u0001\u0005\u0002QBqa\u000e\u0001A\u0002\u0013%\u0001\bC\u0004>\u0001\u0001\u0007I\u0011\u0002 \t\r\u001d\u0003\u0001\u0015)\u0003:\u0011\u001dA\u0005\u00011A\u0005\n%CqA\u0018\u0001A\u0002\u0013%q\f\u0003\u0004b\u0001\u0001\u0006KA\u0013\u0005\bE\u0002\u0011\r\u0011\"\u0003d\u0011\u0019y\u0007\u0001)A\u0005I\"9\u0001\u000f\u0001b\u0001\n\u0013\t\bBB>\u0001A\u0003%!\u000fC\u0004}\u0001\t\u0007I\u0011B?\t\u000f\u0005E\u0001\u0001)A\u0005}\"9\u00111\u0003\u0001\u0005\u0002\u0005U\u0001bBA\u0015\u0001\u0011\u0005\u00131\u0006\u0005\b\u0003g\u0002A\u0011IA;\u0011\u001d\t)\n\u0001C!\u0003/Cq!!,\u0001\t\u0013\ty\u000bC\u0004\u0002@\u0002!\t%!1\t\u000f\u00055\b\u0001\"\u0011\u0002p\ni\u0002\u000b\u001a4J[\u0006<WMU3eC\u000e$\u0018N\\4TiJ,\u0017-\\#oO&tWM\u0003\u0002\u00181\u0005)\u0011.\\1hK*\u0011\u0011DG\u0001\u0007G>lWn\u001c8\u000b\u0005ma\u0012!\u0002;bg.\u001c(BA\u000f\u001f\u0003\u0015\u0019XM\u001b3b\u0015\u0005y\u0012\u0001B2pI\u0016\u001c\u0001aE\u0002\u0001E5\u0002\"aI\u0016\u000e\u0003\u0011R!!\n\u0014\u0002\u001b\r|g\u000e^3oiN$(/Z1n\u0015\t9\u0003&\u0001\u0004tC6\u0014w\u000e\u001f\u0006\u0003;%R\u0011AK\u0001\u0004_J<\u0017B\u0001\u0017%\u0005=\u0001FIR*ue\u0016\fW.\u00128hS:,\u0007C\u0001\u00182\u001b\u0005y#B\u0001\u0019\u001f\u0003\u0011)H/\u001b7\n\u0005Iz#\u0001\u0003'pO\u001e\f'\r\\3\u0002\rqJg.\u001b;?)\u0005)\u0004C\u0001\u001c\u0001\u001b\u00051\u0012A\u00044jYR,'/\u001a3TiJ,\u0017-\\\u000b\u0002sA\u0011!hO\u0007\u00021%\u0011A\b\u0007\u0002\u000f%\u0016$\u0017m\u0019;fIN#(/Z1n\u0003I1\u0017\u000e\u001c;fe\u0016$7\u000b\u001e:fC6|F%Z9\u0015\u0005}*\u0005C\u0001!D\u001b\u0005\t%\"\u0001\"\u0002\u000bM\u001c\u0017\r\\1\n\u0005\u0011\u000b%\u0001B+oSRDqAR\u0002\u0002\u0002\u0003\u0007\u0011(A\u0002yIE\nqBZ5mi\u0016\u0014X\rZ*ue\u0016\fW\u000eI\u0001\u000f_\nT\u0017\nZ:U_J+G-Y2u+\u0005Q\u0005cA&T-:\u0011A*\u0015\b\u0003\u001bBk\u0011A\u0014\u0006\u0003\u001f\u0002\na\u0001\u0010:p_Rt\u0014\"\u0001\"\n\u0005I\u000b\u0015a\u00029bG.\fw-Z\u0005\u0003)V\u00131aU3r\u0015\t\u0011\u0016\t\u0005\u0002X7:\u0011\u0001,\u0017\t\u0003\u001b\u0006K!AW!\u0002\rA\u0013X\rZ3g\u0013\taVL\u0001\u0004TiJLgn\u001a\u0006\u00035\u0006\u000b!c\u001c2k\u0013\u0012\u001cHk\u001c*fI\u0006\u001cGo\u0018\u0013fcR\u0011q\b\u0019\u0005\b\r\u001a\t\t\u00111\u0001K\u0003=y'M[%egR{'+\u001a3bGR\u0004\u0013!\u0005:fI\u0006\u001cG/\u001a3J[\u0006<Wm]'baV\tA\r\u0005\u0003fUZcW\"\u00014\u000b\u0005\u001dD\u0017aB7vi\u0006\u0014G.\u001a\u0006\u0003S\u0006\u000b!bY8mY\u0016\u001cG/[8o\u0013\tYgMA\u0002NCB\u0004\"\u0001Q7\n\u00059\f%aA%oi\u0006\u0011\"/\u001a3bGR,G-S7bO\u0016\u001cX*\u00199!\u0003Y\u0011X\rZ1di\u0016$gi\u001c:n1>\u0013'.Z2u\u0013\u0012\u001cX#\u0001:\u0011\u0007\u0015\u001cX/\u0003\u0002uM\n9\u0001*Y:i'\u0016$\bC\u0001<z\u001b\u00059(B\u0001='\u0003\r\u0019wn]\u0005\u0003u^\u00141$\u00138eSJ,7\r^\"P'>\u0013'.Z2u\u0013\u0012,g\u000e^5gS\u0016\u0014\u0018a\u0006:fI\u0006\u001cG/\u001a3G_Jl\u0007l\u00142kK\u000e$\u0018\nZ:!\u0003M!'/Y<PE*,7\r\u001e(b[\u0016\u001cF/Y2l+\u0005q\b#B@\u0002\b\u0005-QBAA\u0001\u0015\r\u0001\u00141\u0001\u0006\u0003\u0003\u000b\tAA[1wC&!\u0011\u0011BA\u0001\u0005)\t%O]1z\t\u0016\fX/\u001a\t\u0004m\u00065\u0011bAA\bo\n91iT*OC6,\u0017\u0001\u00063sC^|%M[3di:\u000bW.Z*uC\u000e\\\u0007%\u0001\u0004sK\u0012\f7\r\u001e\u000b\u0006I\u0006]\u0011q\u0005\u0005\b\u00033q\u0001\u0019AA\u000e\u0003\u0011\u0001\u0018mZ3\u0011\t\u0005u\u00111E\u0007\u0003\u0003?Q1!!\t'\u0003\u001d\u0001H-\\8eK2LA!!\n\u0002 \t1\u0001\u000b\u0012)bO\u0016DQ\u0001\u0013\bA\u0002)\u000b1\u0002\u001d:pG\u0016\u001c8\u000fU1hKR\u0019q(!\f\t\u000f\u0005eq\u00021\u0001\u0002\u001c!*q\"!\r\u0002DA)\u0001)a\r\u00028%\u0019\u0011QG!\u0003\rQD'o\\<t!\u0011\tI$a\u0010\u000e\u0005\u0005m\"\u0002BA\u001f\u0003\u0007\t!![8\n\t\u0005\u0005\u00131\b\u0002\f\u0013>+\u0005pY3qi&|g.\r\u0004\u001f-\u0006\u0015\u0013\u0011O\u0019\nG\u0005\u001d\u0013qJA4\u0003#*B!!\u0013\u0002LU\ta\u000bB\u0004\u0002N\u0001\u0012\r!a\u0016\u0003\u0003QKA!!\u0015\u0002T\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIER1!!\u0016B\u0003\u0019!\bN]8xgF!\u0011\u0011LA0!\r\u0001\u00151L\u0005\u0004\u0003;\n%a\u0002(pi\"Lgn\u001a\t\u0005\u0003C\n\u0019G\u0004\u0002A#&\u0019\u0011QM+\u0003\u0013QC'o\\<bE2,\u0017'C\u0012\u0002j\u0005-\u0014QNA+\u001d\r\u0001\u00151N\u0005\u0004\u0003+\n\u0015'\u0002\u0012A\u0003\u0006=$!B:dC2\f\u0017g\u0001\u0014\u00028\u0005A1\u000f[8x\r>\u0014X\u000eF\u0002@\u0003oBq!!\u001f\u0011\u0001\u0004\tY(\u0001\u0003g_Jl\u0007\u0003BA?\u0003\u000bk!!a \u000b\t\u0005e\u0014\u0011\u0011\u0006\u0005\u0003\u0007\u000by\"\u0001\u0005he\u0006\u0004\b.[2t\u0013\u0011\t9)a \u0003\u001bA#ei\u001c:n1>\u0013'.Z2uQ\u0015\u0001\u0012\u0011GAFc\u0019qb+!$\u0002\u0014FJ1%a\u0012\u0002P\u0005=\u0015\u0011K\u0019\nG\u0005%\u00141NAI\u0003+\nTA\t!B\u0003_\n4AJA\u001c\u0003U\u0019\bn\\<Ue\u0006t7\u000f]1sK:\u001c\u0017p\u0012:pkB$2aPAM\u0011\u001d\tI(\u0005a\u0001\u00037\u0003B!! \u0002\u001e&!\u0011qTA@\u0005M\u0001F\t\u0016:b]N\u0004\u0018M]3oGf<%o\\;qQ\u0015\t\u0012\u0011GARc\u0019qb+!*\u0002,FJ1%a\u0012\u0002P\u0005\u001d\u0016\u0011K\u0019\nG\u0005%\u00141NAU\u0003+\nTA\t!B\u0003_\n4AJA\u001c\u0003)\u0011X\rZ1di\u001a{'/\u001c\u000b\u0004\u007f\u0005E\u0006bBA=%\u0001\u0007\u00111\u0010\u0015\u0006%\u0005E\u0012QW\u0019\u0007=Y\u000b9,!02\u0013\r\n9%a\u0014\u0002:\u0006E\u0013'C\u0012\u0002j\u0005-\u00141XA+c\u0015\u0011\u0003)QA8c\r1\u0013qG\u0001\u0010aJ|7-Z:t\u001fB,'/\u0019;peR)q(a1\u0002R\"9\u0011QY\nA\u0002\u0005\u001d\u0017\u0001C8qKJ\fGo\u001c:\u0011\t\u0005%\u0017QZ\u0007\u0003\u0003\u0017T1!!2%\u0013\u0011\ty-a3\u0003\u0011=\u0003XM]1u_JDq!a5\u0014\u0001\u0004\t).\u0001\u0005pa\u0016\u0014\u0018M\u001c3t!\u0015y\u0018q[An\u0013\u0011\tI.!\u0001\u0003\t1K7\u000f\u001e\t\u0004m\u0006u\u0017bAApo\n91iT*CCN,\u0007&B\n\u00022\u0005\r\u0018G\u0002\u0010W\u0003K\fY/M\u0005$\u0003\u000f\ny%a:\u0002REJ1%!\u001b\u0002l\u0005%\u0018QK\u0019\u0006E\u0001\u000b\u0015qN\u0019\u0004M\u0005]\u0012!E8qKJ\fGo\u001c:Fq\u000e,\u0007\u000f^5p]R9q(!=\u0002t\u0006U\bbBAc)\u0001\u0007\u0011q\u0019\u0005\b\u0003'$\u0002\u0019AAk\u0011\u001d\t9\u0010\u0006a\u0001\u0003o\t!!\u001a=")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/image/PdfImageRedactingStreamEngine.class */
public class PdfImageRedactingStreamEngine extends PDFStreamEngine implements Loggable {
    private RedactedStream filteredStream;
    private Seq<String> objIdsToRedact;
    private final Map<String, Object> redactedImagesMap;
    private final HashSet<IndirectCOSObjectIdentifier> redactedFormXObjectIds;
    private final ArrayDeque<COSName> drawObjectNameStack;
    private transient Logger logger;
    private volatile transient boolean bitmap$trans$0;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v8, types: [code.sejda.tasks.common.image.PdfImageRedactingStreamEngine] */
    private Logger logger$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (!this.bitmap$trans$0) {
                this.logger = logger();
                r0 = this;
                r0.bitmap$trans$0 = true;
            }
        }
        return this.logger;
    }

    @Override // code.util.Loggable
    public Logger logger() {
        return !this.bitmap$trans$0 ? logger$lzycompute() : this.logger;
    }

    public PdfImageRedactingStreamEngine() {
        Loggable.$init$(this);
        this.filteredStream = new RedactedStream();
        this.objIdsToRedact = package$.MODULE$.Seq().empty();
        this.redactedImagesMap = ((Map) Map$.MODULE$.apply(Nil$.MODULE$)).withDefaultValue(BoxesRunTime.boxToInteger(0));
        this.redactedFormXObjectIds = new HashSet<>();
        this.drawObjectNameStack = new ArrayDeque<>();
        addOperator(new DrawObject());
    }

    private RedactedStream filteredStream() {
        return this.filteredStream;
    }

    private void filteredStream_$eq(final RedactedStream x$1) {
        this.filteredStream = x$1;
    }

    private Seq<String> objIdsToRedact() {
        return this.objIdsToRedact;
    }

    private void objIdsToRedact_$eq(final Seq<String> x$1) {
        this.objIdsToRedact = x$1;
    }

    private Map<String, Object> redactedImagesMap() {
        return this.redactedImagesMap;
    }

    private HashSet<IndirectCOSObjectIdentifier> redactedFormXObjectIds() {
        return this.redactedFormXObjectIds;
    }

    private ArrayDeque<COSName> drawObjectNameStack() {
        return this.drawObjectNameStack;
    }

    public Map<String, Object> redact(final PDPage page, final Seq<String> objIdsToRedact) throws IOException {
        objIdsToRedact_$eq(objIdsToRedact);
        redactedImagesMap().clear();
        filteredStream_$eq(new RedactedStream());
        processPage(page);
        return redactedImagesMap();
    }

    @Override // org.sejda.sambox.contentstream.PDFStreamEngine
    public void processPage(final PDPage page) throws IOException {
        super.processPage(page);
        filteredStream().close();
        COSArray array = new COSArray();
        array.add((COSObjectable) filteredStream().getStream());
        page.getCOSObject().setItem(COSName.CONTENTS, (COSBase) array);
    }

    @Override // org.sejda.sambox.contentstream.PDFStreamEngine
    public void showForm(final PDFormXObject form) throws IOException {
        redactForm(form);
    }

    @Override // org.sejda.sambox.contentstream.PDFStreamEngine
    public void showTransparencyGroup(final PDTransparencyGroup form) throws IOException {
        redactForm(form);
    }

    private void redactForm(final PDFormXObject form) throws IOException {
        PDFormXObject pDFormXObject;
        RedactedStream tmpFilteredStream = filteredStream();
        filteredStream_$eq(new RedactedStream());
        try {
            PDResources resources = getResources();
            COSName formName = drawObjectNameStack().peek();
            if (!resources.isFormXObject(formName)) {
                resources = getCurrentPage().getResources();
            }
            if (!resources.isFormXObject(formName)) {
                logger().warn(new StringBuilder(35).append("Could not find form in resources: ").append(formName).append(" ").append(form).toString());
                throw new RuntimeException("Could not find form in page resources");
            }
            logger().debug(new StringBuilder(23).append("Processing FormXObject ").append(formName).toString());
            super.showForm(form);
            filteredStream().close();
            if (!filteredStream().getRedacted()) {
                BoxedUnit boxedUnit = BoxedUnit.UNIT;
            } else {
                logger().debug(new StringBuilder(34).append("Redaction occurred in FormXObject ").append(formName).toString());
                if (form instanceof PDTransparencyGroup) {
                    PDTransparencyGroup newForm = new PDTransparencyGroup(filteredStream().getStream());
                    newForm.setGroup(((PDTransparencyGroup) form).getGroup());
                    pDFormXObject = newForm;
                } else {
                    pDFormXObject = new PDFormXObject(filteredStream().getStream().getCOSObject());
                }
                PDFormXObject newForm2 = pDFormXObject;
                newForm2.setMatrix(form.getMatrix().createAffineTransform());
                newForm2.setFormType(form.getFormType());
                newForm2.setBBox(form.getBBox());
                newForm2.setResources(form.getResources());
                newForm2.setStructParents(form.getStructParents());
                logger().debug(new StringBuilder(42).append("Updating FormXObject ").append(formName).append(" with redacted stream").toString());
                resources.put(formName, newForm2);
                Some someApply = Option$.MODULE$.apply(form.getCOSObject().getCOSObject().id());
                if (someApply instanceof Some) {
                    IndirectCOSObjectIdentifier cosId = (IndirectCOSObjectIdentifier) someApply.value();
                    BoxesRunTime.boxToBoolean(redactedFormXObjectIds().add(cosId));
                } else {
                    BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
                }
            }
            logger().debug(new StringBuilder(28).append("Done processing FormXObject ").append(form).toString());
        } finally {
            filteredStream_$eq(tmpFilteredStream);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x0175  */
    @Override // org.sejda.sambox.contentstream.PDFStreamEngine
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void processOperator(final org.sejda.sambox.contentstream.operator.Operator r6, final java.util.List<org.sejda.sambox.cos.COSBase> r7) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 492
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: code.sejda.tasks.common.image.PdfImageRedactingStreamEngine.processOperator(org.sejda.sambox.contentstream.operator.Operator, java.util.List):void");
    }

    @Override // org.sejda.sambox.contentstream.PDFStreamEngine
    public void operatorException(final Operator operator, final List<COSBase> operands, final IOException ex) throws IOException {
        super.operatorException(operator, operands, ex);
        throw ex;
    }
}
