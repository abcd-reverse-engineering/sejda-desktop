package code.sejda.tasks.doc;

import code.util.ImplicitJavaConversions$;
import code.util.StringHelpers$;
import com.google.common.io.Files;
import com.sejda.pdf2html.sambox.PdfText2Html$;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.io.MultipleOutputWriter;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.model.FileOutput;
import org.sejda.core.support.prefix.NameGenerator;
import org.sejda.core.support.prefix.model.NameGenerationRequest;
import org.sejda.io.SeekableSource;
import org.sejda.model.exception.TaskException;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.input.PdfSource;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Option;
import scala.Option$;
import scala.Predef$;
import scala.Some;
import scala.Tuple2;
import scala.collection.ArrayOps$;
import scala.collection.StringOps$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.IntRef;
import scala.runtime.ScalaRunTime$;

/* compiled from: PdfToLegibleDocTask.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005Ef\u0001\u0002\u000b\u0016\u0001yAQA\f\u0001\u0005\u0002=Bq!\r\u0001C\u0002\u0013\u0005!\u0007\u0003\u0004:\u0001\u0001\u0006Ia\r\u0005\u0006u\u0001!\ta\u000f\u0005\u0006\u001f\u0002!I\u0001\u0015\u0005\b\u0003\u0007\u0001A\u0011BA\u0003\u0011%\tY\u0001\u0001b\u0001\n\u0003\ti\u0001\u0003\u0005\u0002\u0016\u0001\u0001\u000b\u0011BA\b\u0011\u001d\t9\u0002\u0001C\u0005\u00033Aq!a\n\u0001\t\u0013\tI\u0003C\u0004\u0002\\\u0001!I!!\u0018\t\u000f\u0005\r\u0004\u0001\"\u0003\u0002f!9\u0011Q\u000f\u0001\u0005\n\u0005]\u0004bBA@\u0001\u0011%\u0011\u0011\u0011\u0005\b\u0003\u0017\u0003A\u0011BAG\u0011\u001d\t\t\n\u0001C\u0005\u0003'Cq!a&\u0001\t\u0013\tI\nC\u0004\u0002\u001e\u0002!\t%a(\t\u0019\u0005\u0005\u0006\u0001%A\u0001\u0002\u0003%\t!a)\u0003'A#g\rV8MK\u001eL'\r\\3E_\u000e$\u0016m]6\u000b\u0005Y9\u0012a\u00013pG*\u0011\u0001$G\u0001\u0006i\u0006\u001c8n\u001d\u0006\u00035m\tQa]3kI\u0006T\u0011\u0001H\u0001\u0005G>$Wm\u0001\u0001\u0014\u0005\u0001y\u0002c\u0001\u0011)U5\t\u0011E\u0003\u0002#G\u0005!A/Y:l\u0015\t!S%A\u0003n_\u0012,GN\u0003\u0002\u001bM)\tq%A\u0002pe\u001eL!!K\u0011\u0003\u0011\t\u000b7/\u001a+bg.\u0004\"a\u000b\u0017\u000e\u0003UI!!L\u000b\u0003%A#g\rV8E_\u000e\u0004\u0016M]1nKR,'o]\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003A\u0002\"a\u000b\u0001\u0002\r1|wmZ3s+\u0005\u0019\u0004C\u0001\u001b8\u001b\u0005)$B\u0001\u001c'\u0003\u0015\u0019HN\u001a\u001bk\u0013\tATG\u0001\u0004M_\u001e<WM]\u0001\bY><w-\u001a:!\u0003\u001d)\u00070Z2vi\u0016$\"\u0001\u0010\"\u0011\u0005u\u0002U\"\u0001 \u000b\u0003}\nQa]2bY\u0006L!!\u0011 \u0003\tUs\u0017\u000e\u001e\u0005\u0006\u0007\u0012\u0001\rAK\u0001\u000ba\u0006\u0014\u0018-\\3uKJ\u001c\bf\u0001\u0003F\u001dB\u0019QH\u0012%\n\u0005\u001ds$A\u0002;ie><8\u000f\u0005\u0002J\u00196\t!J\u0003\u0002LG\u0005IQ\r_2faRLwN\\\u0005\u0003\u001b*\u0013Q\u0002V1tW\u0016C8-\u001a9uS>t7%\u0001%\u0002\u001b\r|gN^3siR{\u0007\n^7m)\u0015\t\u0016,\\?��!\t\u0011v+D\u0001T\u0015\t!V+\u0001\u0002j_*\ta+\u0001\u0003kCZ\f\u0017B\u0001-T\u0005\u00111\u0015\u000e\\3\t\u000bi+\u0001\u0019A.\u0002\rM|WO]2fa\taF\rE\u0002^A\nl\u0011A\u0018\u0006\u0003?\u000e\nQ!\u001b8qkRL!!\u00190\u0003\u0013A#gmU8ve\u000e,\u0007CA2e\u0019\u0001!\u0011\"Z-\u0002\u0002\u0003\u0005)\u0011\u00014\u0003\u0007}#\u0013'\u0005\u0002hUB\u0011Q\b[\u0005\u0003Sz\u0012qAT8uQ&tw\r\u0005\u0002>W&\u0011AN\u0010\u0002\u0004\u0003:L\b\"\u00028\u0006\u0001\u0004y\u0017a\u00019xIB\u0019Q\b\u001d:\n\u0005Et$AB(qi&|g\u000e\u0005\u0002tu:\u0011A\u000f\u001f\t\u0003kzj\u0011A\u001e\u0006\u0003ov\ta\u0001\u0010:p_Rt\u0014BA=?\u0003\u0019\u0001&/\u001a3fM&\u00111\u0010 \u0002\u0007'R\u0014\u0018N\\4\u000b\u0005et\u0004\"\u0002@\u0006\u0001\u0004Q\u0013A\u00029be\u0006l7\u000f\u0003\u0004\u0002\u0002\u0015\u0001\r!U\u0001\n_V$\b/\u001e;ESJ\f\u0011CZ1jY&3\u0007\n^7m\u0013N,U\u000e\u001d;z)\ra\u0014q\u0001\u0005\u0007\u0003\u00131\u0001\u0019A)\u0002\u0011!$X\u000e\u001c$jY\u0016\f1\u0002Z8d\r>tGoU5{KV\u0011\u0011q\u0002\t\u0004{\u0005E\u0011bAA\n}\t\u0019\u0011J\u001c;\u0002\u0019\u0011|7MR8oiNK'0\u001a\u0011\u0002\u0019\r|gN^3siR{Gi\\2\u0015\u000bq\nY\"!\b\t\u000biK\u0001\u0019A)\t\u000f\u0005}\u0011\u00021\u0001\u0002\"\u0005\u0019q.\u001e;\u0011\u0007I\u000b\u0019#C\u0002\u0002&M\u0013AbT;uaV$8\u000b\u001e:fC6\f\u0001b\u001e:ji\u0016$\u0016m\u001a\u000b\u0006y\u0005-\u0012q\b\u0005\b\u0003[Q\u0001\u0019AA\u0018\u0003\u0011qw\u000eZ3\u0011\t\u0005E\u00121H\u0007\u0003\u0003gQA!!\u000e\u00028\u0005)an\u001c3fg*\u0019\u0011\u0011\b\u0014\u0002\u000b)\u001cx.\u001e9\n\t\u0005u\u00121\u0007\u0002\u0005\u001d>$W\rC\u0004\u0002B)\u0001\r!a\u0011\u0002\u0013A\f'/Y4sCBD\u0007\u0003BA#\u0003/j!!a\u0012\u000b\t\u0005%\u00131J\u0001\nkN,'/\\8eK2TA!!\u0014\u0002P\u0005!\u0001p\u001e9g\u0015\u0011\t\t&a\u0015\u0002\u0007A|\u0017NC\u0002\u0002V\u0019\na!\u00199bG\",\u0017\u0002BA-\u0003\u000f\u0012Q\u0002W,Q\rB\u000b'/Y4sCBD\u0017aC5nC\u001e,G+\u001f9f\u001f\u001a$B!a\u0004\u0002`!1\u0011\u0011M\u0006A\u0002E\u000bAAZ5mK\u0006i\u0001/\u0019:tK\u000e\u001b8o\u0015;zY\u0016$Ra\\A4\u0003cBq!!\u001b\r\u0001\u0004\tY'\u0001\u0003fY\u0016l\u0007\u0003BA\u0019\u0003[JA!a\u001c\u00024\t9Q\t\\3nK:$\bBBA:\u0019\u0001\u0007!/\u0001\u0005qe>\u0004h*Y7f\u0003=\u0001\u0018M]:f!b\u001c5o]*us2,GCBA=\u0003w\ni\b\u0005\u0003>a\u0006=\u0001bBA5\u001b\u0001\u0007\u00111\u000e\u0005\u0007\u0003gj\u0001\u0019\u0001:\u0002\r%\u001c(i\u001c7e)\u0011\t\u0019)!#\u0011\u0007u\n))C\u0002\u0002\bz\u0012qAQ8pY\u0016\fg\u000eC\u0004\u0002j9\u0001\r!a\u001b\u0002\u0011%\u001c\u0018\n^1mS\u000e$B!a!\u0002\u0010\"9\u0011\u0011N\bA\u0002\u0005-\u0014a\u00044p]R\u001c\u0016N_3QKJ\u001cWM\u001c;\u0015\t\u0005e\u0014Q\u0013\u0005\b\u0003S\u0002\u0002\u0019AA6\u0003-1wN\u001c;TSj,gi\u001c:\u0015\t\u0005=\u00111\u0014\u0005\b\u0003S\n\u0002\u0019AA6\u0003\u0015\tg\r^3s)\u0005a\u0014A\u00079s_R,7\r^3eI\u0015DXmY;uS>t7i\u001c8uKb$H\u0003BAS\u0003[#\"!a*\u0011\u0007\u0001\nI+C\u0002\u0002,\u0006\u0012A\u0003V1tW\u0016CXmY;uS>t7i\u001c8uKb$\b\u0002CAX'\u0005\u0005\t\u0019\u0001\u0019\u0002\u0007a$\u0013\u0007")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/doc/PdfToLegibleDocTask.class */
public class PdfToLegibleDocTask extends BaseTask<PdfToDocParameters> {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final int docFontSize = 9;

    @Override // org.sejda.model.task.Task
    public void after() {
    }

    public Logger logger() {
        return this.logger;
    }

    @Override // org.sejda.model.task.Task
    public void execute(final PdfToDocParameters parameters) throws TaskException {
        int totalSteps = parameters.getSourceList().size();
        IntRef currentStep = IntRef.create(0);
        MultipleOutputWriter outputWriter = OutputWriters.newMultipleOutputWriter(parameters.getExistingOutputPolicy(), executionContext());
        ImplicitJavaConversions$.MODULE$.javaListToScalaSeq(parameters.getSourceList()).foreach(source -> {
            $anonfun$execute$1(this, currentStep, parameters, outputWriter, totalSteps, source);
            return BoxedUnit.UNIT;
        });
        parameters.getOutput().accept(outputWriter);
    }

    public static final /* synthetic */ void $anonfun$execute$1(final PdfToLegibleDocTask $this, final IntRef currentStep$1, final PdfToDocParameters parameters$1, final MultipleOutputWriter outputWriter$1, final int totalSteps$1, final PdfSource source) throws TaskIOException, IOException {
        $this.logger().debug("Processing {}", source);
        currentStep$1.elem++;
        File tmpDocFile = IOUtils.createTemporaryBuffer(".docx");
        OutputStream tmpOut = parameters$1.getOutput().getEncryptionAtRestPolicy().encrypt(new FileOutputStream(tmpDocFile));
        File tmpHtmlDir = Files.createTempDir();
        File htmlFile = $this.convertToHtml(source, Option$.MODULE$.apply(source.getPassword()), parameters$1, tmpHtmlDir);
        try {
            $this.failIfHtmlIsEmpty(htmlFile);
            $this.convertToDoc(htmlFile, tmpOut);
            FileUtils.deleteQuietly(tmpHtmlDir);
            org.apache.commons.io.IOUtils.closeQuietly(tmpOut);
            String outName = NameGenerator.nameGenerator(parameters$1.getOutputPrefix()).generate(NameGenerationRequest.nameRequest("docx").originalName(source.getName()).fileNumber(currentStep$1.elem));
            outputWriter$1.addOutput(FileOutput.file(tmpDocFile).name(outName));
            ApplicationEventsNotifier.notifyEvent($this.protected$executionContext($this).notifiableTaskMetadata()).stepsCompleted(currentStep$1.elem).outOf(totalSteps$1);
        } catch (Throwable th) {
            FileUtils.deleteQuietly(tmpHtmlDir);
            org.apache.commons.io.IOUtils.closeQuietly(tmpOut);
            throw th;
        }
    }

    public /* synthetic */ TaskExecutionContext protected$executionContext(final PdfToLegibleDocTask x$1) {
        return x$1.executionContext();
    }

    private File convertToHtml(final PdfSource<?> source, final Option<String> pwd, final PdfToDocParameters params, final File outputDir) {
        logger().debug(new StringBuilder(36).append("Starting pdf2html conversion of ").append(source.getName()).append(" ...").toString());
        String outputFilename = new StringBuilder(5).append(FilenameUtils.getBaseName(source.getName())).append(".html").toString();
        File outputFile = new File(outputDir, outputFilename);
        try {
            SeekableSource x$1 = source.getSeekableSource();
            boolean x$7 = params.resizeImages();
            boolean x$8 = PdfText2Html$.MODULE$.convert$default$3();
            int x$9 = PdfText2Html$.MODULE$.convert$default$6();
            boolean x$10 = PdfText2Html$.MODULE$.convert$default$7();
            Option x$11 = PdfText2Html$.MODULE$.convert$default$8();
            boolean x$12 = PdfText2Html$.MODULE$.convert$default$9();
            PdfText2Html$.MODULE$.convert(x$1, outputFile, x$8, Integer.MAX_VALUE, x$7, x$9, x$10, x$11, x$12, 700, 700, pwd);
            return outputFile;
        } finally {
            logger().debug(new StringBuilder(37).append("Completed pdf2html conversion of ").append(source).append(" to ").append(outputFile).toString());
        }
    }

    private void failIfHtmlIsEmpty(final File htmlFile) throws TaskException {
        String text = Jsoup.parse(htmlFile, "UTF-8").body().text().trim();
        if (text.isEmpty()) {
            throw new TaskException("Could not extract PDF contents");
        }
    }

    public int docFontSize() {
        return this.docFontSize;
    }

    private void convertToDoc(final File source, final OutputStream out) {
        Document sourceDoc = Jsoup.parse(source, "utf-8");
        XWPFDocument document = new XWPFDocument();
        document.getProperties().getCoreProperties().setTitle(StringHelpers$.MODULE$.removeInvalidXMLChars(sourceDoc.title()));
        ImplicitJavaConversions$.MODULE$.javaListToScalaSeq(sourceDoc.select("p,div")).foreach(elem -> {
            $anonfun$convertToDoc$1(this, document, elem);
            return BoxedUnit.UNIT;
        });
        document.write(out);
        document.close();
    }

    public static final /* synthetic */ void $anonfun$convertToDoc$1(final PdfToLegibleDocTask $this, final XWPFDocument document$1, final Element elem) {
        XWPFParagraph paragraph = document$1.createParagraph();
        $this.writeTag(elem, paragraph);
        Some pxCssStyle = $this.parsePxCssStyle(elem, "margin-top");
        if (pxCssStyle instanceof Some) {
            int marginTop = BoxesRunTime.unboxToInt(pxCssStyle.value());
            paragraph.setSpacingBefore(marginTop);
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
            return;
        }
        BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
    }

    private void writeTag(final Node node, final XWPFParagraph paragraph) {
        ImplicitJavaConversions$.MODULE$.javaListToScalaSeq(node.childNodes()).foreach(x0$1 -> {
            boolean z = false;
            Element element = null;
            if (x0$1 instanceof TextNode) {
                XWPFRun run = paragraph.createRun();
                run.setText(((TextNode) x0$1).getWholeText());
                run.setFontSize(this.docFontSize());
                return BoxedUnit.UNIT;
            }
            if (x0$1 instanceof Element) {
                z = true;
                element = (Element) x0$1;
                if (element.childNodeSize() == 1) {
                    XWPFRun run2 = paragraph.createRun();
                    run2.setText(element.text());
                    run2.setBold(this.isBold(element));
                    run2.setItalic(this.isItalic(element));
                    run2.setFontSize(this.fontSizeFor(element));
                    return BoxedUnit.UNIT;
                }
            }
            if (z && element.childNodeSize() == 0) {
                String lowerCase = element.tagName().toLowerCase();
                switch (lowerCase == null ? 0 : lowerCase.hashCode()) {
                    case 3152:
                        if ("br".equals(lowerCase)) {
                            XWPFRun run3 = paragraph.createRun();
                            run3.addBreak();
                            return BoxedUnit.UNIT;
                        }
                        break;
                    case 104387:
                        if ("img".equals(lowerCase)) {
                            XWPFRun run4 = paragraph.createRun();
                            File imageFile = new File(element.attr("src"));
                            FileInputStream imageIn = new FileInputStream(imageFile);
                            Tuple2.mcII.sp spVar = new Tuple2.mcII.sp(StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(element.attr("width"))), StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(element.attr("height"))));
                            int _type = this.imageTypeOf(imageFile);
                            return run4.addPicture(imageIn, _type, imageFile.getName(), Units.pixelToEMU(spVar._1$mcI$sp()), Units.pixelToEMU(spVar._2$mcI$sp()));
                        }
                        break;
                }
                return BoxedUnit.UNIT;
            }
            if (z) {
                this.writeTag(element, paragraph);
                return BoxedUnit.UNIT;
            }
            return BoxedUnit.UNIT;
        });
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:6:0x0014. Please report as an issue. */
    private int imageTypeOf(final File file) {
        String lowerCase = file.getName().toLowerCase();
        switch (lowerCase == null ? 0 : lowerCase.hashCode()) {
        }
        return lowerCase.endsWith(".png") ? org.apache.poi.xwpf.usermodel.Document.PICTURE_TYPE_PNG : org.apache.poi.xwpf.usermodel.Document.PICTURE_TYPE_JPEG;
    }

    private Option<String> parseCssStyle(final Element elem, final String propName) {
        return ArrayOps$.MODULE$.find$extension(Predef$.MODULE$.refArrayOps((Object[]) ArrayOps$.MODULE$.map$extension(Predef$.MODULE$.refArrayOps(((String) Option$.MODULE$.apply(elem.attr("style")).getOrElse(() -> {
            return "";
        })).split(";")), x$1 -> {
            return x$1.trim().split(":");
        }, ClassTag$.MODULE$.apply(ScalaRunTime$.MODULE$.arrayClass(String.class)))), pair -> {
            return BoxesRunTime.boxToBoolean($anonfun$parseCssStyle$3(propName, pair));
        }).flatMap(x$2 -> {
            return (Option) Predef$.MODULE$.wrapRefArray(x$2).lift().apply(BoxesRunTime.boxToInteger(1));
        }).map(x$3 -> {
            return x$3.trim();
        });
    }

    public static final /* synthetic */ boolean $anonfun$parseCssStyle$3(final String propName$1, final String[] pair) {
        String str = pair[0];
        return str != null ? str.equals(propName$1) : propName$1 == null;
    }

    private Option<Object> parsePxCssStyle(final Element elem, final String propName) {
        return parseCssStyle(elem, propName).map(x$4 -> {
            return StringOps$.MODULE$.replaceAllLiterally$extension(Predef$.MODULE$.augmentString(x$4), "px", "");
        }).map(x$5 -> {
            return BoxesRunTime.boxToInteger($anonfun$parsePxCssStyle$2(x$5));
        });
    }

    public static final /* synthetic */ int $anonfun$parsePxCssStyle$2(final String x$5) {
        return StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(x$5.trim()));
    }

    private boolean isBold(final Element elem) {
        Option<String> cssStyle = parseCssStyle(elem, "font-weight");
        Some some = new Some("bold");
        return cssStyle != null ? cssStyle.equals(some) : some == null;
    }

    private boolean isItalic(final Element elem) {
        Option<String> cssStyle = parseCssStyle(elem, "font-style");
        Some some = new Some("italic");
        return cssStyle != null ? cssStyle.equals(some) : some == null;
    }

    private Option<Object> fontSizePercent(final Element elem) {
        return parseCssStyle(elem, "font-size").filter(x$6 -> {
            return BoxesRunTime.boxToBoolean($anonfun$fontSizePercent$1(x$6));
        }).map(x$7 -> {
            return StringOps$.MODULE$.dropRight$extension(Predef$.MODULE$.augmentString(x$7), 1);
        }).map(x$8 -> {
            return BoxesRunTime.boxToInteger($anonfun$fontSizePercent$3(x$8));
        });
    }

    public static final /* synthetic */ boolean $anonfun$fontSizePercent$1(final String x$6) {
        return x$6.endsWith("%");
    }

    public static final /* synthetic */ int $anonfun$fontSizePercent$3(final String x$8) {
        return StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(x$8.trim()));
    }

    private int fontSizeFor(final Element elem) {
        return (int) ((BoxesRunTime.unboxToInt(fontSizePercent(elem).getOrElse(() -> {
            return 100;
        })) / 100.0d) * docFontSize());
    }
}
