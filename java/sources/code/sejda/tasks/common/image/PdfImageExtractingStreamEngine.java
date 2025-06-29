package code.sejda.tasks.common.image;

import code.util.Loggable;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import org.sejda.sambox.contentstream.PDFStreamEngine;
import org.sejda.sambox.contentstream.operator.MissingOperandException;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.contentstream.operator.OperatorProcessor;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.graphics.PDXObject;
import org.sejda.sambox.pdmodel.graphics.form.PDFormXObject;
import org.sejda.sambox.pdmodel.graphics.form.PDTransparencyGroup;
import org.sejda.sambox.pdmodel.graphics.image.PDImageXObject;
import org.slf4j.Logger;
import scala.Function2;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

/* compiled from: PdfImageExtractingStreamEngine.scala */
@ScalaSignature(bytes = "\u0006\u0005Q3A\u0001C\u0005\u0001)!Aa\u0005\u0001B\u0001B\u0003%q\u0005\u0003\u0005.\u0001\t\u0005\t\u0015!\u0003/\u0011\u0015\u0019\u0005\u0001\"\u0001E\u0011\u001dI\u0005\u00011A\u0005\u0002)Cqa\u0013\u0001A\u0002\u0013\u0005A\n\u0003\u0004P\u0001\u0001\u0006K!\u0010\u0005\u0006!\u0002!\t!\u0015\u0002\u001f!\u00124\u0017*\\1hK\u0016CHO]1di&twm\u0015;sK\u0006lWI\\4j]\u0016T!AC\u0006\u0002\u000b%l\u0017mZ3\u000b\u00051i\u0011AB2p[6|gN\u0003\u0002\u000f\u001f\u0005)A/Y:lg*\u0011\u0001#E\u0001\u0006g\u0016TG-\u0019\u0006\u0002%\u0005!1m\u001c3f\u0007\u0001\u00192\u0001A\u000b!!\t1b$D\u0001\u0018\u0015\tA\u0012$A\u0007d_:$XM\u001c;tiJ,\u0017-\u001c\u0006\u00035m\taa]1nE>D(B\u0001\t\u001d\u0015\u0005i\u0012aA8sO&\u0011qd\u0006\u0002\u0010!\u001235\u000b\u001e:fC6,enZ5oKB\u0011\u0011\u0005J\u0007\u0002E)\u00111%E\u0001\u0005kRLG.\u0003\u0002&E\tAAj\\4hC\ndW-A\u0002e_\u000e\u0004\"\u0001K\u0016\u000e\u0003%R!AK\r\u0002\u000fA$Wn\u001c3fY&\u0011A&\u000b\u0002\u000b!\u0012#unY;nK:$\u0018\u0001D5nC\u001e,\u0007*\u00198eY\u0016\u0014\b#B\u00183iu\u0002U\"\u0001\u0019\u000b\u0003E\nQa]2bY\u0006L!a\r\u0019\u0003\u0013\u0019+hn\u0019;j_:\u0014\u0004CA\u001b<\u001b\u00051$B\u0001\u00068\u0015\tA\u0014(A\u0002boRT\u0011AO\u0001\u0005U\u00064\u0018-\u0003\u0002=m\ti!)\u001e4gKJ,G-S7bO\u0016\u0004\"a\f \n\u0005}\u0002$aA%oiB\u0011q&Q\u0005\u0003\u0005B\u0012A!\u00168ji\u00061A(\u001b8jiz\"2!R$I!\t1\u0005!D\u0001\n\u0011\u001513\u00011\u0001(\u0011\u0015i3\u00011\u0001/\u00039\u0019WO\u001d:f]R\u0004\u0016mZ3Ok6,\u0012!P\u0001\u0013GV\u0014(/\u001a8u!\u0006<WMT;n?\u0012*\u0017\u000f\u0006\u0002A\u001b\"9a*BA\u0001\u0002\u0004i\u0014a\u0001=%c\u0005y1-\u001e:sK:$\b+Y4f\u001dVl\u0007%A\u0006qe>\u001cWm]:QC\u001e,GC\u0001!S\u0011\u0015\u0019v\u00011\u0001>\u0003\u001d\u0001\u0018mZ3Ok6\u0004")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/image/PdfImageExtractingStreamEngine.class */
public class PdfImageExtractingStreamEngine extends PDFStreamEngine implements Loggable {
    private final PDDocument doc;
    public final Function2<BufferedImage, Object, BoxedUnit> code$sejda$tasks$common$image$PdfImageExtractingStreamEngine$$imageHandler;
    private int currentPageNum;
    private transient Logger logger;
    private volatile transient boolean bitmap$trans$0;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v8, types: [code.sejda.tasks.common.image.PdfImageExtractingStreamEngine] */
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

    public PdfImageExtractingStreamEngine(final PDDocument doc, final Function2<BufferedImage, Object, BoxedUnit> imageHandler) {
        this.doc = doc;
        this.code$sejda$tasks$common$image$PdfImageExtractingStreamEngine$$imageHandler = imageHandler;
        Loggable.$init$(this);
        this.currentPageNum = -1;
        addOperator(new OperatorProcessor(this) { // from class: code.sejda.tasks.common.image.PdfImageExtractingStreamEngine$$anon$1
            private final /* synthetic */ PdfImageExtractingStreamEngine $outer;

            {
                if (this == null) {
                    throw null;
                }
                this.$outer = this;
            }

            @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
            public void process(final Operator operator, final List<COSBase> arguments) throws IOException {
                if (arguments.isEmpty()) {
                    throw new MissingOperandException(operator, arguments);
                }
                COSBase cOSBase = arguments.get(0);
                if (cOSBase instanceof COSName) {
                    PDXObject xObject = getContext().getResources().getXObject((COSName) cOSBase);
                    if (xObject instanceof PDImageXObject) {
                    } else if (xObject instanceof PDFormXObject) {
                        PDFormXObject pDFormXObject = (PDFormXObject) xObject;
                        try {
                            getContext().increaseLevel();
                            if (getContext().getLevel() > 25) {
                                this.$outer.logger().error("recursion is too deep, skipping form XObject");
                                return;
                            }
                            if (pDFormXObject instanceof PDTransparencyGroup) {
                                getContext().showTransparencyGroup((PDTransparencyGroup) pDFormXObject);
                                BoxedUnit boxedUnit = BoxedUnit.UNIT;
                            } else {
                                getContext().showForm(pDFormXObject);
                                BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
                            }
                            BoxedUnit boxedUnit3 = BoxedUnit.UNIT;
                        } finally {
                            getContext().decreaseLevel();
                        }
                    } else {
                        BoxedUnit boxedUnit4 = BoxedUnit.UNIT;
                    }
                    BoxedUnit boxedUnit5 = BoxedUnit.UNIT;
                    return;
                }
                BoxedUnit boxedUnit6 = BoxedUnit.UNIT;
            }

            @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
            public String getName() {
                return OperatorName.DRAW_OBJECT;
            }
        });
    }

    public int currentPageNum() {
        return this.currentPageNum;
    }

    public void currentPageNum_$eq(final int x$1) {
        this.currentPageNum = x$1;
    }

    public void processPage(final int pageNum) {
        logger().debug(new StringBuilder(29).append("Extracting images from page: ").append(pageNum).toString());
        PDPage page = this.doc.getPage(pageNum - 1);
        currentPageNum_$eq(pageNum);
        processPage(page);
    }
}
