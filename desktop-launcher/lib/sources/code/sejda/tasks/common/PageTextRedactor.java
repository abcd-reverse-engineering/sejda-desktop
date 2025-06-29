package code.sejda.tasks.common;

import code.sejda.tasks.common.text.PdfTextRedactingStreamEngine;
import code.util.Loggable;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.IOException;
import org.sejda.impl.sambox.component.PageTextWriter;
import org.sejda.impl.sambox.util.FontUtils;
import org.sejda.model.TopLeftRectangularBox;
import org.sejda.model.exception.TaskIOException;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.font.PDFont;
import org.sejda.sambox.pdmodel.font.PDType1Font;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.pdmodel.graphics.color.PDDeviceRGB;
import org.sejda.sambox.pdmodel.graphics.state.RenderingMode;
import org.slf4j.Logger;
import scala.$less$colon$less$;
import scala.MatchError;
import scala.Option;
import scala.Predef$;
import scala.Tuple2;
import scala.collection.IterableOps;
import scala.collection.StringOps$;
import scala.collection.immutable.List;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;

/* compiled from: PageTextRedactor.scala */
@ScalaSignature(bytes = "\u0006\u0005\u00154Aa\u0002\u0005\u0001#!Aa\u0004\u0001B\u0001B\u0003%q\u0004C\u0003+\u0001\u0011\u00051\u0006\u0003\u00050\u0001!\u0015\r\u0011\"\u00011\u0011\u0015I\u0004\u0001\"\u0001;\u0011\u0015\u0019\u0006\u0001\"\u0003U\u0011\u0015\u0011\u0007\u0001\"\u0003d\u0005A\u0001\u0016mZ3UKb$(+\u001a3bGR|'O\u0003\u0002\n\u0015\u000511m\\7n_:T!a\u0003\u0007\u0002\u000bQ\f7o[:\u000b\u00055q\u0011!B:fU\u0012\f'\"A\b\u0002\t\r|G-Z\u0002\u0001'\r\u0001!\u0003\u0007\t\u0003'Yi\u0011\u0001\u0006\u0006\u0002+\u0005)1oY1mC&\u0011q\u0003\u0006\u0002\u0007\u0003:L(+\u001a4\u0011\u0005eaR\"\u0001\u000e\u000b\u0005mq\u0011\u0001B;uS2L!!\b\u000e\u0003\u00111{wmZ1cY\u0016\f1\u0001Z8d!\t\u0001\u0003&D\u0001\"\u0015\t\u00113%A\u0004qI6|G-\u001a7\u000b\u0005\u0011*\u0013AB:b[\n|\u0007P\u0003\u0002\u000eM)\tq%A\u0002pe\u001eL!!K\u0011\u0003\u0015A#Ei\\2v[\u0016tG/\u0001\u0004=S:LGO\u0010\u000b\u0003Y9\u0002\"!\f\u0001\u000e\u0003!AQA\b\u0002A\u0002}\tQA\u0011'B\u0007.+\u0012!\r\t\u0003e]j\u0011a\r\u0006\u0003iU\nQaY8m_JT!AN\u0011\u0002\u0011\u001d\u0014\u0018\r\u001d5jGNL!\u0001O\u001a\u0003\u000fA#5i\u001c7pe\u0006Y!/\u001a9mC\u000e,G+\u001a=u)\tY\u0004\u000bE\u0002=\t\u001es!!\u0010\"\u000f\u0005y\nU\"A \u000b\u0005\u0001\u0003\u0012A\u0002\u001fs_>$h(C\u0001\u0016\u0013\t\u0019E#A\u0004qC\u000e\\\u0017mZ3\n\u0005\u00153%aA*fc*\u00111\t\u0006\t\u0005'!SU*\u0003\u0002J)\t1A+\u001e9mKJ\u0002\"!L&\n\u00051C!!\u0003*fI\u0006\u001cG/[8o!\tic*\u0003\u0002P\u0011\ty!+\u001a3bGRLwN\u001c*fgVdG\u000fC\u0003R\t\u0001\u0007!+\u0001\u0006sK\u0012\f7\r^5p]N\u00042\u0001\u0010#K\u0003Y\t'/Z*b[\u0016<\u0016\u000e\u001e5TC6,G)Z:dK:$HcA+YAB\u00111CV\u0005\u0003/R\u0011qAQ8pY\u0016\fg\u000eC\u0003Z\u000b\u0001\u0007!,\u0001\u0002gcA\u00111LX\u0007\u00029*\u0011Q,I\u0001\u0005M>tG/\u0003\u0002`9\n1\u0001\u000b\u0012$p]RDQ!Y\u0003A\u0002i\u000b!A\u001a\u001a\u0002\u001f%\u001c8\u000b^1oI\u0006\u0014H\rV5nKN$\"!\u00163\t\u000bu3\u0001\u0019\u0001.")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/PageTextRedactor.class */
public class PageTextRedactor implements Loggable {
    private PDColor BLACK;
    private final PDDocument doc;
    private transient Logger logger;
    private volatile boolean bitmap$0;
    private volatile transient boolean bitmap$trans$0;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v8, types: [code.sejda.tasks.common.PageTextRedactor] */
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

    public PageTextRedactor(final PDDocument doc) {
        this.doc = doc;
        Loggable.$init$(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v8, types: [code.sejda.tasks.common.PageTextRedactor] */
    private PDColor BLACK$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (!this.bitmap$0) {
                this.BLACK = new PDColor(new float[]{0.0f, 0.0f, 0.0f}, PDDeviceRGB.INSTANCE);
                r0 = this;
                r0.bitmap$0 = true;
            }
        }
        return this.BLACK;
    }

    public PDColor BLACK() {
        return !this.bitmap$0 ? BLACK$lzycompute() : this.BLACK;
    }

    public Seq<Tuple2<Redaction, RedactionResult>> replaceText(final Seq<Redaction> redactions) {
        return (Seq) ((IterableOps) redactions.map(redaction -> {
            TopLeftRectangularBox boundingBox = redaction.boundingBox();
            PDPage page = redaction.page();
            PdfTextRedactingStreamEngine engine = new PdfTextRedactingStreamEngine(boundingBox);
            try {
                engine.processPage(page);
                if (engine.redactedTextPosition() != null) {
                }
                return new Tuple2(redaction, RedactionResult$.MODULE$.apply(engine));
            } catch (IOException e) {
                throw new TaskIOException(e);
            }
        })).map(x0$1 -> {
            if (x0$1 != null) {
                Redaction redaction2 = (Redaction) x0$1._1();
                RedactionResult redacted = (RedactionResult) x0$1._2();
                PDPage page = redaction2.page();
                Replacement replacement = redaction2.replacement();
                PDRectangle pageSize = page.getMediaBox().rotate(page.getRotation());
                String replacementText = replacement.text();
                if (StringOps$.MODULE$.nonEmpty$extension(Predef$.MODULE$.augmentString(replacementText.trim()))) {
                    double fontSize = BoxesRunTime.unboxToDouble(replacement.fontSize().orElse(() -> {
                        return redacted.fontSize();
                    }).getOrElse(() -> {
                        throw new RuntimeException("No font size provided");
                    }));
                    Point2D originalPosition = (Point2D) replacement.position().orElse(() -> {
                        return redacted.toPagePoint(pageSize);
                    }).getOrElse(() -> {
                        throw new RuntimeException("No position provided");
                    });
                    PDColor color = (PDColor) replacement.color().orElse(() -> {
                        return redacted.color();
                    }).getOrElse(() -> {
                        return this.BLACK();
                    });
                    RenderingMode renderingMode = (RenderingMode) redacted.renderingMode().getOrElse(() -> {
                        return RenderingMode.FILL;
                    });
                    List lines = Predef$.MODULE$.wrapRefArray(StringOps$.MODULE$.replaceAllLiterally$extension(Predef$.MODULE$.augmentString(replacementText), "\r\n", "\n").split("\\n")).toList();
                    ((List) lines.zipWithIndex()).foreach(x0$2 -> {
                        $anonfun$replaceText$10(this, replacement, redacted, fontSize, originalPosition, page, color, renderingMode, x0$2);
                        return BoxedUnit.UNIT;
                    });
                }
                return new Tuple2(redaction2, redacted);
            }
            throw new MatchError(x0$1);
        });
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    public static final /* synthetic */ void $anonfun$replaceText$10(final PageTextRedactor $this, final Replacement replacement$1, final RedactionResult redacted$1, final double fontSize$1, final Point2D originalPosition$1, final PDPage page$1, final PDColor color$1, final RenderingMode renderingMode$1, final Tuple2 x0$2) throws MatchError, TaskIOException, IOException {
        boolean zFauxItalic;
        if (x0$2 != null) {
            String text = (String) x0$2._1();
            int i = x0$2._2$mcI$sp();
            Option originalFontOpt = replacement$1.font().orElse(() -> {
                return redacted$1.font();
            }).filter(f -> {
                return BoxesRunTime.boxToBoolean($anonfun$replaceText$12(f));
            });
            boolean originalFontBold = originalFontOpt.exists(x$1 -> {
                return BoxesRunTime.boxToBoolean(FontUtils.isBold(x$1));
            });
            PDFont font = (PDFont) replacement$1.font().orElse(() -> {
                return redacted$1.font();
            }).getOrElse(() -> {
                return FontUtils.findFontFor($this.doc, replacement$1.text(), originalFontBold, (PDFont) originalFontOpt.orNull($less$colon$less$.MODULE$.refl()));
            });
            double lineHeight = BoxesRunTime.unboxToDouble(replacement$1.lineHeight().getOrElse(() -> {
                return (font.getBoundingBox().getHeight() / 1000) * fontSize$1;
            }));
            Point2D point = new Point((int) originalPosition$1.getX(), (int) ((originalPosition$1.getY() - (i * lineHeight)) + 1));
            if (FontUtils.isItalic(font)) {
                zFauxItalic = false;
            } else {
                zFauxItalic = replacement$1.fauxItalic();
            }
            boolean fauxItalic = zFauxItalic;
            new PageTextWriter($this.doc).write(page$1, point, text, font, Predef$.MODULE$.double2Double(fontSize$1), color$1, renderingMode$1, fauxItalic);
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
            return;
        }
        throw new MatchError(x0$2);
    }

    public static final /* synthetic */ boolean $anonfun$replaceText$12(final PDFont f) {
        return (f == null || f.getName() == null) ? false : true;
    }

    private boolean areSameWithSameDescent(final PDFont f1, final PDFont f2) {
        String name = f1.getName();
        String name2 = f2.getName();
        if (name != null ? name.equals(name2) : name2 == null) {
            if (f1.getFontDescriptor() != null && f2.getFontDescriptor() != null && f1.getFontDescriptor().getDescent() == f2.getFontDescriptor().getDescent()) {
                return true;
            }
        }
        return false;
    }

    private boolean isStandardTimes(final PDFont font) {
        PDType1Font pDType1FontTIMES_ROMAN = PDType1Font.TIMES_ROMAN();
        if (font != null ? !font.equals(pDType1FontTIMES_ROMAN) : pDType1FontTIMES_ROMAN != null) {
            PDType1Font pDType1FontTIMES_BOLD = PDType1Font.TIMES_BOLD();
            if (font != null ? !font.equals(pDType1FontTIMES_BOLD) : pDType1FontTIMES_BOLD != null) {
                PDType1Font pDType1FontTIMES_ITALIC = PDType1Font.TIMES_ITALIC();
                if (font != null ? !font.equals(pDType1FontTIMES_ITALIC) : pDType1FontTIMES_ITALIC != null) {
                    PDType1Font pDType1FontTIMES_BOLD_ITALIC = PDType1Font.TIMES_BOLD_ITALIC();
                    if (font != null ? !font.equals(pDType1FontTIMES_BOLD_ITALIC) : pDType1FontTIMES_BOLD_ITALIC != null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
