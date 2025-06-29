package code.sejda.tasks.rename;

import code.sejda.tasks.common.AcroFormsHelper;
import code.util.Loggable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.io.MultipleOutputWriter;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.model.FileOutput;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.impl.sambox.component.PdfTextExtractorByArea;
import org.sejda.model.encryption.EncryptionAtRestPolicy;
import org.sejda.model.exception.TaskException;
import org.sejda.model.input.PdfSource;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.slf4j.Logger;
import scala.$less$colon$less$;
import scala.MatchError;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Tuple2;
import scala.collection.IterableOnceOps;
import scala.collection.JavaConverters$;
import scala.collection.StringOps$;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Map;
import scala.collection.mutable.Map$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.IntRef;
import scala.runtime.ObjectRef;

/* compiled from: RenameByTextTask.scala */
@ScalaSignature(bytes = "\u0006\u0005}4AAC\u0006\u0001)!)!\u0006\u0001C\u0001W!)Q\u0006\u0001C\u0001]!)!\t\u0001C\u0001\u0007\")!\u000e\u0001C!W\"QA\u000e\u0001I\u0001\u0002\u0003\u0005I\u0011A7\b\u000bQ\\\u0001\u0012A;\u0007\u000b)Y\u0001\u0012\u0001<\t\u000b):A\u0011\u0001>\t\u000bm<A\u0011\u0001?\u0003!I+g.Y7f\u0005f$V\r\u001f;UCN\\'B\u0001\u0007\u000e\u0003\u0019\u0011XM\\1nK*\u0011abD\u0001\u0006i\u0006\u001c8n\u001d\u0006\u0003!E\tQa]3kI\u0006T\u0011AE\u0001\u0005G>$Wm\u0001\u0001\u0014\u0007\u0001)B\u0005E\u0002\u0017=\u0001j\u0011a\u0006\u0006\u00031e\tA\u0001^1tW*\u0011!dG\u0001\u0006[>$W\r\u001c\u0006\u0003!qQ\u0011!H\u0001\u0004_J<\u0017BA\u0010\u0018\u0005!\u0011\u0015m]3UCN\\\u0007CA\u0011#\u001b\u0005Y\u0011BA\u0012\f\u0005Y\u0011VM\\1nK\nKH+\u001a=u!\u0006\u0014\u0018-\\3uKJ\u001c\bCA\u0013)\u001b\u00051#BA\u0014\u0012\u0003\u0011)H/\u001b7\n\u0005%2#\u0001\u0003'pO\u001e\f'\r\\3\u0002\rqJg.\u001b;?)\u0005a\u0003CA\u0011\u0001\u0003\u001d)\u00070Z2vi\u0016$\"aL\u001b\u0011\u0005A\u001aT\"A\u0019\u000b\u0003I\nQa]2bY\u0006L!\u0001N\u0019\u0003\tUs\u0017\u000e\u001e\u0005\u0006m\t\u0001\r\u0001I\u0001\u000ba\u0006\u0014\u0018-\\3uKJ\u001c\bf\u0001\u00029\u0003B\u0019\u0001'O\u001e\n\u0005i\n$A\u0002;ie><8\u000f\u0005\u0002=\u007f5\tQH\u0003\u0002?3\u0005IQ\r_2faRLwN\\\u0005\u0003\u0001v\u0012Q\u0002V1tW\u0016C8-\u001a9uS>t7%A\u001e\u0002/\u0011,G/\u001a:nS:,G+\u001a=u\u0003J,\u0017MV1mk\u0016\u001cHc\u0001#S9B!Q\tT(P\u001d\t1%\n\u0005\u0002Hc5\t\u0001J\u0003\u0002J'\u00051AH]8pizJ!aS\u0019\u0002\rA\u0013X\rZ3g\u0013\tieJA\u0002NCBT!aS\u0019\u0011\u0005\u0015\u0003\u0016BA)O\u0005\u0019\u0019FO]5oO\")1k\u0001a\u0001)\u0006\u0019Am\\2\u0011\u0005USV\"\u0001,\u000b\u0005]C\u0016a\u00029e[>$W\r\u001c\u0006\u00033n\taa]1nE>D\u0018BA.W\u0005)\u0001F\tR8dk6,g\u000e\u001e\u0005\u0006;\u000e\u0001\rAX\u0001\u0006CJ,\u0017m\u001d\t\u0004?\u0012<gB\u00011c\u001d\t9\u0015-C\u00013\u0013\t\u0019\u0017'A\u0004qC\u000e\\\u0017mZ3\n\u0005\u00154'aA*fc*\u00111-\r\t\u0003C!L!![\u0006\u0003\u0015I+g.Y7f\u0003J,\u0017-A\u0003bMR,'\u000fF\u00010\u0003i\u0001(o\u001c;fGR,G\rJ3yK\u000e,H/[8o\u0007>tG/\u001a=u)\tq'\u000fF\u0001p!\t1\u0002/\u0003\u0002r/\t!B+Y:l\u000bb,7-\u001e;j_:\u001cuN\u001c;fqRDqa]\u0003\u0002\u0002\u0003\u0007A&A\u0002yIE\n\u0001CU3oC6,')\u001f+fqR$\u0016m]6\u0011\u0005\u0005:1CA\u0004x!\t\u0001\u00040\u0003\u0002zc\t1\u0011I\\=SK\u001a$\u0012!^\u0001\u0011G>tg/\u001a:u)\u0016DHOV1mk\u0016$\"aT?\t\u000byL\u0001\u0019A(\u0002\u0005%t\u0007")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/rename/RenameByTextTask.class */
public class RenameByTextTask extends BaseTask<RenameByTextParameters> implements Loggable {
    private transient Logger logger;
    private volatile transient boolean bitmap$trans$0;

    public static String convertTextValue(final String in) {
        return RenameByTextTask$.MODULE$.convertTextValue(in);
    }

    @Override // org.sejda.model.task.Task
    public void after() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v8, types: [code.sejda.tasks.rename.RenameByTextTask] */
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

    public RenameByTextTask() {
        Loggable.$init$(this);
    }

    @Override // org.sejda.model.task.Task
    public void execute(final RenameByTextParameters parameters) throws TaskException {
        int totalSteps = parameters.getSourceList().size();
        IntRef currentStep = IntRef.create(0);
        MultipleOutputWriter outputWriter = OutputWriters.newMultipleOutputWriter(parameters.getExistingOutputPolicy(), executionContext());
        DefaultPdfSourceOpener opener = new DefaultPdfSourceOpener(executionContext());
        Map filenamesCount = (Map) Map$.MODULE$.apply(Nil$.MODULE$);
        ((IterableOnceOps) JavaConverters$.MODULE$.asScalaBufferConverter(parameters.getSourceList()).asScala()).foreach(source -> {
            $anonfun$execute$1(this, currentStep, opener, parameters, filenamesCount, outputWriter, totalSteps, source);
            return BoxedUnit.UNIT;
        });
        parameters.getOutput().accept(outputWriter);
    }

    public static final /* synthetic */ void $anonfun$execute$1(final RenameByTextTask $this, final IntRef currentStep$1, final DefaultPdfSourceOpener opener$1, final RenameByTextParameters parameters$1, final Map filenamesCount$1, final MultipleOutputWriter outputWriter$1, final int totalSteps$1, final PdfSource source) throws IOException, TaskException {
        $this.logger().debug(new StringBuilder(9).append("Renaming ").append(source).toString());
        currentStep$1.elem++;
        PDDocumentHandler docHandler = (PDDocumentHandler) source.open(opener$1);
        PDDocument doc = docHandler.getUnderlyingPDDocument();
        AcroFormsHelper acroFormsHelper = new AcroFormsHelper(doc);
        acroFormsHelper.flatten();
        scala.collection.immutable.Map values = $this.determineTextAreaValues(doc, parameters$1.areas());
        ObjectRef newFilename = ObjectRef.create(parameters$1.renamePattern());
        values.foreach(x0$1 -> {
            $anonfun$execute$2(newFilename, x0$1);
            return BoxedUnit.UNIT;
        });
        String sourceBaseName = FilenameUtils.getBaseName(source.getName());
        newFilename.elem = StringOps$.MODULE$.replaceAllLiterally$extension(Predef$.MODULE$.augmentString((String) newFilename.elem), "[BASENAME]", sourceBaseName);
        newFilename.elem = StringOps$.MODULE$.replaceAllLiterally$extension(Predef$.MODULE$.augmentString((String) newFilename.elem), "[FILENUMBER]", Integer.toString(currentStep$1.elem));
        newFilename.elem = StringOps$.MODULE$.replaceAllLiterally$extension(Predef$.MODULE$.augmentString((String) newFilename.elem), "[FILENUMBER#]", Integer.toString(currentStep$1.elem));
        newFilename.elem = StringOps$.MODULE$.replaceAllLiterally$extension(Predef$.MODULE$.augmentString((String) newFilename.elem), "[FILENUMBER##]", StringUtils.leftPad(Integer.toString(currentStep$1.elem), 2, '0'));
        newFilename.elem = StringOps$.MODULE$.replaceAllLiterally$extension(Predef$.MODULE$.augmentString((String) newFilename.elem), "[FILENUMBER###]", StringUtils.leftPad(Integer.toString(currentStep$1.elem), 3, '0'));
        newFilename.elem = IOUtils.toSafeFilename((String) newFilename.elem);
        int existingCount = BoxesRunTime.unboxToInt(filenamesCount$1.getOrElse((String) newFilename.elem, () -> {
            return 0;
        }));
        if (existingCount > 0) {
            newFilename.elem = new StringBuilder(2).append((String) newFilename.elem).append("(").append(existingCount).append(")").toString();
            filenamesCount$1.put((String) newFilename.elem, BoxesRunTime.boxToInteger(existingCount + 1));
        } else {
            filenamesCount$1.put((String) newFilename.elem, BoxesRunTime.boxToInteger(1));
        }
        newFilename.elem = new StringBuilder(4).append((String) newFilename.elem).append(".pdf").toString();
        docHandler.close();
        File outputFile = IOUtils.createTemporaryBuffer();
        EncryptionAtRestPolicy encryptionAtRestSecurity = parameters$1.getOutput().getEncryptionAtRestPolicy();
        OutputStream out = encryptionAtRestSecurity.encrypt(new FileOutputStream(outputFile));
        if (source.getSeekableSource().size() == 0) {
            throw new TaskException("Could not re-read the source after closing first time");
        }
        InputStream in = source.getSeekableSource().asNewInputStream();
        try {
            org.apache.commons.io.IOUtils.copyLarge(in, out);
            org.apache.commons.io.IOUtils.closeQuietly(in);
            org.apache.commons.io.IOUtils.closeQuietly(out);
            outputWriter$1.addOutput(FileOutput.file(outputFile).name((String) newFilename.elem));
            ApplicationEventsNotifier.notifyEvent($this.protected$executionContext($this).notifiableTaskMetadata()).stepsCompleted(currentStep$1.elem).outOf(totalSteps$1);
        } catch (Throwable th) {
            org.apache.commons.io.IOUtils.closeQuietly(in);
            org.apache.commons.io.IOUtils.closeQuietly(out);
            throw th;
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    public static final /* synthetic */ void $anonfun$execute$2(final ObjectRef newFilename$1, final Tuple2 x0$1) throws MatchError {
        if (x0$1 != null) {
            String name = (String) x0$1._1();
            String value = (String) x0$1._2();
            newFilename$1.elem = StringOps$.MODULE$.replaceAllLiterally$extension(Predef$.MODULE$.augmentString((String) newFilename$1.elem), new StringBuilder(2).append("[").append(name).append("]").toString(), RenameByTextTask$.MODULE$.convertTextValue(value));
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
            return;
        }
        throw new MatchError(x0$1);
    }

    public /* synthetic */ TaskExecutionContext protected$executionContext(final RenameByTextTask x$1) {
        return x$1.executionContext();
    }

    public scala.collection.immutable.Map<String, String> determineTextAreaValues(final PDDocument doc, final Seq<RenameArea> areas) {
        return ((IterableOnceOps) areas.map(area -> {
            PDPage page = doc.getPage(area.pageNum() - 1);
            String text = new PdfTextExtractorByArea().extractTextFromArea(page, area.asBox().asRectangle());
            return Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(area.name()), org.sejda.commons.util.StringUtils.normalizeWhitespace(StringUtils.strip((String) StringUtils.defaultIfBlank(text, ""), "")).trim());
        })).toMap($less$colon$less$.MODULE$.refl());
    }
}
