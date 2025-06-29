package code.sejda.tasks.excel;

import code.util.Loggable;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;
import java.util.function.Consumer;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.sejda.commons.util.IOUtils;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.io.MultipleOutputWriter;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.model.FileOutput;
import org.sejda.core.support.prefix.NameGenerator;
import org.sejda.core.support.prefix.model.NameGenerationRequest;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.impl.sambox.component.excel.DataTable;
import org.sejda.impl.sambox.component.excel.DataTableUtils;
import org.sejda.model.encryption.EncryptionAtRestPolicy;
import org.sejda.model.exception.TaskException;
import org.sejda.model.input.PdfSource;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.sambox.pdmodel.PDDocument;
import org.slf4j.Logger;
import scala.Predef$;
import scala.collection.IterableOnceOps;
import scala.collection.JavaConverters$;
import scala.collection.immutable.List;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.IntRef;
import scala.runtime.NonLocalReturnControl;
import scala.runtime.ObjectRef;
import scala.runtime.RichInt$;

/* compiled from: PdfToExcelNextTask.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005ug\u0001\u0002\u000b\u0016\u0001yAQ\u0001\u000e\u0001\u0005\u0002UBqa\u000e\u0001A\u0002\u0013%\u0001\bC\u0004D\u0001\u0001\u0007I\u0011\u0002#\t\r5\u0003\u0001\u0015)\u0003:\u0011\u001dq\u0005\u00011A\u0005\naBqa\u0014\u0001A\u0002\u0013%\u0001\u000b\u0003\u0004S\u0001\u0001\u0006K!\u000f\u0005\b'\u0002\u0001\r\u0011\"\u0003U\u0011\u001dy\u0006\u00011A\u0005\n\u0001DaA\u0019\u0001!B\u0013)\u0006\"B2\u0001\t\u0003\"\u0007bBA\u001d\u0001\u0011\u0005\u00131\b\u0005\b\u0003\u0017\u0002A\u0011BA'\u0011\u001d\tY\u0007\u0001C\u0005\u0003[Bq!a\u001e\u0001\t\u0013\tI\bC\u0004\u0002(\u0002!I!!+\t\u000f\u0005u\u0006\u0001\"\u0003\u0002@\"9\u0011\u0011\u001b\u0001\u0005B\u0005M\u0007\u0002DAk\u0001A\u0005\t\u0011!A\u0005\u0002\u0005]'A\u0005)eMR{W\t_2fY:+\u0007\u0010\u001e+bg.T!AF\f\u0002\u000b\u0015D8-\u001a7\u000b\u0005aI\u0012!\u0002;bg.\u001c(B\u0001\u000e\u001c\u0003\u0015\u0019XM\u001b3b\u0015\u0005a\u0012\u0001B2pI\u0016\u001c\u0001aE\u0002\u0001?9\u00022\u0001\t\u0015+\u001b\u0005\t#B\u0001\u0012$\u0003\u0011!\u0018m]6\u000b\u0005\u0011*\u0013!B7pI\u0016d'B\u0001\u000e'\u0015\u00059\u0013aA8sO&\u0011\u0011&\t\u0002\t\u0005\u0006\u001cX\rV1tWB\u00111\u0006L\u0007\u0002+%\u0011Q&\u0006\u0002\u0019!\u00124Gk\\#yG\u0016dg*\u001a=u!\u0006\u0014\u0018-\\3uKJ\u001c\bCA\u00183\u001b\u0005\u0001$BA\u0019\u001c\u0003\u0011)H/\u001b7\n\u0005M\u0002$\u0001\u0003'pO\u001e\f'\r\\3\u0002\rqJg.\u001b;?)\u00051\u0004CA\u0016\u0001\u0003U\u0019x.\u001e:dK\u0012{7-^7f]RD\u0015M\u001c3mKJ,\u0012!\u000f\t\u0003u\u0005k\u0011a\u000f\u0006\u0003yu\n\u0011bY8na>tWM\u001c;\u000b\u0005yz\u0014AB:b[\n|\u0007P\u0003\u0002AK\u0005!\u0011.\u001c9m\u0013\t\u00115HA\tQ\t\u0012{7-^7f]RD\u0015M\u001c3mKJ\f\u0011d]8ve\u000e,Gi\\2v[\u0016tG\u000fS1oI2,'o\u0018\u0013fcR\u0011Qi\u0013\t\u0003\r&k\u0011a\u0012\u0006\u0002\u0011\u0006)1oY1mC&\u0011!j\u0012\u0002\u0005+:LG\u000fC\u0004M\u0007\u0005\u0005\t\u0019A\u001d\u0002\u0007a$\u0013'\u0001\ft_V\u00148-\u001a#pGVlWM\u001c;IC:$G.\u001a:!\u0003M!Wm\u001d;j]\u0006$\u0018n\u001c8E_\u000e,X.\u001a8u\u0003]!Wm\u001d;j]\u0006$\u0018n\u001c8E_\u000e,X.\u001a8u?\u0012*\u0017\u000f\u0006\u0002F#\"9AJBA\u0001\u0002\u0004I\u0014\u0001\u00063fgRLg.\u0019;j_:$unY;nK:$\b%\u0001\u0007pkR\u0004X\u000f^,sSR,'/F\u0001V!\t1V,D\u0001X\u0015\tA\u0016,\u0001\u0002j_*\u0011!lW\u0001\bgV\u0004\bo\u001c:u\u0015\taV%\u0001\u0003d_J,\u0017B\u00010X\u0005QiU\u000f\u001c;ja2,w*\u001e;qkR<&/\u001b;fe\u0006\u0001r.\u001e;qkR<&/\u001b;fe~#S-\u001d\u000b\u0003\u000b\u0006Dq\u0001T\u0005\u0002\u0002\u0003\u0007Q+A\u0007pkR\u0004X\u000f^,sSR,'\u000fI\u0001\u0007E\u00164wN]3\u0015\u0007\u0015+w\rC\u0003g\u0017\u0001\u0007!&\u0001\u0006qCJ\fW.\u001a;feNDQ\u0001[\u0006A\u0002%\f\u0001#\u001a=fGV$\u0018n\u001c8D_:$X\r\u001f;\u0011\u0005\u0001R\u0017BA6\"\u0005Q!\u0016m]6Fq\u0016\u001cW\u000f^5p]\u000e{g\u000e^3yi\"\u001a1\"\u001c<\u0011\u0007\u0019s\u0007/\u0003\u0002p\u000f\n1A\u000f\u001b:poN\u0004\"!\u001d;\u000e\u0003IT!a]\u0012\u0002\u0013\u0015D8-\u001a9uS>t\u0017BA;s\u00055!\u0016m]6Fq\u000e,\u0007\u000f^5p]F2ad^A\u0003\u0003o\u0001\"\u0001_@\u000f\u0005el\bC\u0001>H\u001b\u0005Y(B\u0001?\u001e\u0003\u0019a$o\\8u}%\u0011apR\u0001\u0007!J,G-\u001a4\n\t\u0005\u0005\u00111\u0001\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005y<\u0015'C\u0012\u0002\b\u0005=\u0011QFA\t+\u0011\tI!a\u0003\u0016\u0003]$q!!\u0004\u001e\u0005\u0004\t9BA\u0001U\u0013\u0011\t\t\"a\u0005\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00132\u0015\r\t)bR\u0001\u0007i\"\u0014xn^:\u0012\t\u0005e\u0011q\u0004\t\u0004\r\u0006m\u0011bAA\u000f\u000f\n9aj\u001c;iS:<\u0007\u0003BA\u0011\u0003Oq1ARA\u0012\u0013\r\t)cR\u0001\ba\u0006\u001c7.Y4f\u0013\u0011\tI#a\u000b\u0003\u0013QC'o\\<bE2,'bAA\u0013\u000fFJ1%a\f\u00022\u0005M\u0012Q\u0003\b\u0004\r\u0006E\u0012bAA\u000b\u000fF*!ER$\u00026\t)1oY1mCF\u0012a\u0005]\u0001\bKb,7-\u001e;f)\r)\u0015Q\b\u0005\u0006M2\u0001\rA\u000b\u0015\u0005\u00195\f\t%\r\u0004\u001fo\u0006\r\u0013\u0011J\u0019\nG\u0005\u001d\u0011qBA#\u0003#\t\u0014bIA\u0018\u0003c\t9%!\u00062\u000b\t2u)!\u000e2\u0005\u0019\u0002\u0018!B7fe\u001e,G\u0003BA(\u00033\u0002B!!\u0015\u0002V5\u0011\u00111\u000b\u0006\u0003-mJA!a\u0016\u0002T\tIA)\u0019;b)\u0006\u0014G.\u001a\u0005\b\u00037j\u0001\u0019AA/\u0003\u0019!\u0018M\u00197fgB1\u0011qLA4\u0003\u001fj!!!\u0019\u000b\u0007E\n\u0019G\u0003\u0002\u0002f\u0005!!.\u0019<b\u0013\u0011\tI'!\u0019\u0003\t1K7\u000f^\u0001\fQ\u0006\u001c8i\u001c8uK:$8\u000f\u0006\u0003\u0002p\u0005U\u0004c\u0001$\u0002r%\u0019\u00111O$\u0003\u000f\t{w\u000e\\3b]\"9\u00111\f\bA\u0002\u0005u\u0013!D<sSR,7i\u001d<GS2,7\u000f\u0006\u0004\u0002|\u0005\u001d\u00151\u0012\t\u0007\u0003?\n9'! \u0011\t\u0005}\u00141Q\u0007\u0003\u0003\u0003S1\u0001WA2\u0013\u0011\t))!!\u0003\t\u0019KG.\u001a\u0005\b\u0003\u0013{\u0001\u0019AA/\u0003)!\u0017\r^1UC\ndWm\u001d\u0005\b\u0003\u001b{\u0001\u0019AAH\u0003Y)gn\u0019:zaRLwN\\!u%\u0016\u001cH\u000fU8mS\u000eL\b\u0003BAI\u0003/k!!a%\u000b\u0007\u0005U5%\u0001\u0006f]\u000e\u0014\u0018\u0010\u001d;j_:LA!!'\u0002\u0014\n1RI\\2ssB$\u0018n\u001c8BiJ+7\u000f\u001e)pY&\u001c\u0017\u0010\u000b\u0003\u0010[\u0006u\u0015G\u0002\u0010x\u0003?\u000b)+M\u0005$\u0003\u000f\ty!!)\u0002\u0012EJ1%a\f\u00022\u0005\r\u0016QC\u0019\u0006E\u0019;\u0015QG\u0019\u0003MA\fAb\u001e:ji\u0016\u001c5O\u001e$jY\u0016$b!! \u0002,\u0006=\u0006bBAW!\u0001\u0007\u0011qJ\u0001\nI\u0006$\u0018\rV1cY\u0016Dq!!$\u0011\u0001\u0004\ty\t\u000b\u0003\u0011[\u0006M\u0016G\u0002\u0010x\u0003k\u000bY,M\u0005$\u0003\u000f\ty!a.\u0002\u0012EJ1%a\f\u00022\u0005e\u0016QC\u0019\u0006E\u0019;\u0015QG\u0019\u0003MA\fab\u001e:ji\u0016,\u0005pY3m\r&dW\r\u0006\u0004\u0002~\u0005\u0005\u00171\u0019\u0005\b\u0003\u0013\u000b\u0002\u0019AA/\u0011\u001d\ti)\u0005a\u0001\u0003\u001fCC!E7\u0002HF2ad^Ae\u0003\u001f\f\u0014bIA\u0004\u0003\u001f\tY-!\u00052\u0013\r\ny#!\r\u0002N\u0006U\u0011'\u0002\u0012G\u000f\u0006U\u0012G\u0001\u0014q\u0003\u0015\tg\r^3s)\u0005)\u0015A\u00079s_R,7\r^3eI\u0015DXmY;uS>t7i\u001c8uKb$H\u0003BAm\u00037$\u0012!\u001b\u0005\b\u0019N\t\t\u00111\u00017\u0001")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/excel/PdfToExcelNextTask.class */
public class PdfToExcelNextTask extends BaseTask<PdfToExcelNextParameters> implements Loggable {
    private PDDocumentHandler sourceDocumentHandler;
    private PDDocumentHandler destinationDocument;
    private MultipleOutputWriter outputWriter;
    private transient Logger logger;
    private volatile transient boolean bitmap$trans$0;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v8, types: [code.sejda.tasks.excel.PdfToExcelNextTask] */
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

    public PdfToExcelNextTask() {
        Loggable.$init$(this);
        this.sourceDocumentHandler = null;
        this.destinationDocument = null;
        this.outputWriter = null;
    }

    private PDDocumentHandler sourceDocumentHandler() {
        return this.sourceDocumentHandler;
    }

    private void sourceDocumentHandler_$eq(final PDDocumentHandler x$1) {
        this.sourceDocumentHandler = x$1;
    }

    private PDDocumentHandler destinationDocument() {
        return this.destinationDocument;
    }

    private void destinationDocument_$eq(final PDDocumentHandler x$1) {
        this.destinationDocument = x$1;
    }

    private MultipleOutputWriter outputWriter() {
        return this.outputWriter;
    }

    private void outputWriter_$eq(final MultipleOutputWriter x$1) {
        this.outputWriter = x$1;
    }

    @Override // org.sejda.model.task.BaseTask, org.sejda.model.task.Task
    public void before(final PdfToExcelNextParameters parameters, final TaskExecutionContext executionContext) throws TaskException {
        super.before((PdfToExcelNextTask) parameters, executionContext);
        outputWriter_$eq(OutputWriters.newMultipleOutputWriter(parameters.getExistingOutputPolicy(), executionContext));
    }

    @Override // org.sejda.model.task.Task
    public void execute(final PdfToExcelNextParameters parameters) throws TaskException {
        IntRef currentStep = IntRef.create(0);
        IntRef fileOutputNumber = IntRef.create(0);
        int totalSteps = parameters.getSourceList().size();
        ((IterableOnceOps) JavaConverters$.MODULE$.asScalaBufferConverter(parameters.getSourceList()).asScala()).foreach(source -> {
            $anonfun$execute$1(this, currentStep, parameters, fileOutputNumber, totalSteps, source);
            return BoxedUnit.UNIT;
        });
        parameters.getOutput().accept(outputWriter());
        logger().debug(new StringBuilder(39).append("Input documents cropped and written to ").append(parameters.getOutput()).toString());
    }

    public static final /* synthetic */ void $anonfun$execute$1(final PdfToExcelNextTask $this, final IntRef currentStep$1, final PdfToExcelNextParameters parameters$1, final IntRef fileOutputNumber$1, final int totalSteps$1, final PdfSource source) throws IllegalStateException, IOException, TaskException {
        currentStep$1.elem++;
        $this.logger().debug(new StringBuilder(8).append("Opening ").append(source).toString());
        $this.sourceDocumentHandler_$eq((PDDocumentHandler) source.open(new DefaultPdfSourceOpener($this.protected$executionContext($this))));
        $this.destinationDocument_$eq(new PDDocumentHandler());
        $this.destinationDocument().setVersionOnPDDocument(parameters$1.getVersion());
        $this.destinationDocument().initialiseBasedOn($this.sourceDocumentHandler().getUnderlyingPDDocument());
        $this.destinationDocument().setCompress(parameters$1.isCompress());
        int numberOfPages = $this.sourceDocumentHandler().getNumberOfPages();
        ObjectRef all = ObjectRef.create(new ArrayList());
        RichInt$.MODULE$.to$extension(Predef$.MODULE$.intWrapper(1), numberOfPages).foreach$mVc$sp(pageNumber -> {
            $this.logger().debug(new StringBuilder(28).append("Extracting tables from page ").append(pageNumber).toString());
            PDDocument doc = $this.sourceDocumentHandler().getUnderlyingPDDocument();
            List dataTables = new TableDetector().detect(pageNumber, doc);
            dataTables.foreach(dt -> {
                if (!dt.hasData()) {
                    return BoxedUnit.UNIT;
                }
                return BoxesRunTime.boxToBoolean(((java.util.List) all.elem).add(dt));
            });
        });
        all.elem = DataTableUtils.mergeComplementaryColumns((java.util.List<DataTable>) all.elem);
        if (parameters$1.mergeTablesSpanningMultiplePages()) {
            all.elem = DataTableUtils.mergeTablesSpanningMultiplePages((java.util.List) all.elem);
        }
        if (!$this.hasContents((java.util.List) all.elem)) {
            throw new TaskException("Scans are not supported");
        }
        if (parameters$1.singleSheet()) {
            all.elem = Collections.singletonList($this.merge((java.util.List) all.elem));
        }
        if (parameters$1.csvFormat()) {
            java.util.List tmpFiles = $this.writeCsvFiles((java.util.List) all.elem, parameters$1.getOutput().getEncryptionAtRestPolicy());
            ((IterableOnceOps) JavaConverters$.MODULE$.asScalaBufferConverter(tmpFiles).asScala()).foreach(tmpFile -> {
                $anonfun$execute$4($this, fileOutputNumber$1, parameters$1, source, tmpFile);
                return BoxedUnit.UNIT;
            });
        } else {
            File tmpFile2 = $this.writeExcelFile((java.util.List) all.elem, parameters$1.getOutput().getEncryptionAtRestPolicy());
            fileOutputNumber$1.elem++;
            String outName = NameGenerator.nameGenerator(parameters$1.getOutputPrefix()).generate(NameGenerationRequest.nameRequest("xlsx").originalName(source.getName()).fileNumber(fileOutputNumber$1.elem));
            $this.outputWriter().addOutput(FileOutput.file(tmpFile2).name(outName));
        }
        ApplicationEventsNotifier.notifyEvent($this.protected$executionContext($this).notifiableTaskMetadata()).stepsCompleted(currentStep$1.elem).outOf(totalSteps$1);
        IOUtils.closeQuietly($this.sourceDocumentHandler());
    }

    public /* synthetic */ TaskExecutionContext protected$executionContext(final PdfToExcelNextTask x$1) {
        return x$1.executionContext();
    }

    public static final /* synthetic */ void $anonfun$execute$4(final PdfToExcelNextTask $this, final IntRef fileOutputNumber$1, final PdfToExcelNextParameters parameters$1, final PdfSource source$1, final File tmpFile) {
        fileOutputNumber$1.elem++;
        String outName = NameGenerator.nameGenerator(parameters$1.getOutputPrefix()).generate(NameGenerationRequest.nameRequest("csv").originalName(source$1.getName()).fileNumber(fileOutputNumber$1.elem));
        $this.outputWriter().addOutput(FileOutput.file(tmpFile).name(outName));
    }

    private DataTable merge(final java.util.List<DataTable> tables) {
        final TreeSet pageNumbers = new TreeSet();
        final PdfToExcelNextTask pdfToExcelNextTask = null;
        tables.forEach(new Consumer<DataTable>(pdfToExcelNextTask, pageNumbers) { // from class: code.sejda.tasks.excel.PdfToExcelNextTask$$anon$1
            private final TreeSet pageNumbers$1;

            {
                this.pageNumbers$1 = pageNumbers;
            }

            @Override // java.util.function.Consumer
            public Consumer<DataTable> andThen(final Consumer<? super DataTable> x$1) {
                return super.andThen(x$1);
            }

            @Override // java.util.function.Consumer
            public void accept(final DataTable t) {
                this.pageNumbers$1.addAll(t.getPageNumbers());
            }
        });
        final DataTable result = new DataTable(pageNumbers);
        final PdfToExcelNextTask pdfToExcelNextTask2 = null;
        tables.forEach(new Consumer<DataTable>(pdfToExcelNextTask2, result) { // from class: code.sejda.tasks.excel.PdfToExcelNextTask$$anon$2
            private final DataTable result$1;

            {
                this.result$1 = result;
            }

            @Override // java.util.function.Consumer
            public Consumer<DataTable> andThen(final Consumer<? super DataTable> x$1) {
                return super.andThen(x$1);
            }

            @Override // java.util.function.Consumer
            public void accept(final DataTable t) {
                RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(0), t.getRowsCount()).foreach$mVc$sp(rowIndex -> {
                    this.result$1.addRow(t.getRow(rowIndex));
                });
            }
        });
        return result;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.runtime.NonLocalReturnControl */
    private boolean hasContents(final java.util.List<DataTable> tables) throws NonLocalReturnControl {
        Object obj = new Object();
        try {
            ((IterableOnceOps) JavaConverters$.MODULE$.asScalaBufferConverter(tables).asScala()).foreach(table -> {
                $anonfun$hasContents$1(obj, table);
                return BoxedUnit.UNIT;
            });
            return false;
        } catch (NonLocalReturnControl ex) {
            if (ex.key() == obj) {
                return ex.value$mcZ$sp();
            }
            throw ex;
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.runtime.NonLocalReturnControl$mcZ$sp */
    public static final /* synthetic */ void $anonfun$hasContents$1(final Object nonLocalReturnKey1$1, final DataTable table) throws NonLocalReturnControl.mcZ.sp {
        if (table.hasData()) {
            throw new NonLocalReturnControl.mcZ.sp(nonLocalReturnKey1$1, true);
        }
    }

    private java.util.List<File> writeCsvFiles(final java.util.List<DataTable> dataTables, final EncryptionAtRestPolicy encryptionAtRestPolicy) throws TaskException {
        java.util.List results = new ArrayList();
        RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(0), dataTables.size()).foreach(t -> {
            DataTable dataTable = (DataTable) dataTables.get(t);
            return results.add(this.writeCsvFile(dataTable, encryptionAtRestPolicy));
        });
        return results;
    }

    private File writeCsvFile(final DataTable dataTable, final EncryptionAtRestPolicy encryptionAtRestPolicy) throws TaskException {
        try {
            File tmpFile = org.sejda.model.util.IOUtils.createTemporaryBuffer(".csv");
            OutputStream tmpOut = encryptionAtRestPolicy.encrypt(new FileOutputStream(tmpFile));
            logger().debug("Created output temporary buffer {}, writing csv data", tmpFile);
            long start = System.currentTimeMillis();
            java.util.List data = dataTable.getData();
            CSVPrinter csvPrinter = new CSVPrinter(new BufferedWriter(new OutputStreamWriter(tmpOut)), CSVFormat.DEFAULT);
            try {
                RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(0), data.size()).foreach$mVc$sp(r -> {
                    java.util.List dataRow = (java.util.List) data.get(r);
                    csvPrinter.printRecord(dataRow);
                });
                csvPrinter.flush();
                tmpOut.flush();
                IOUtils.closeQuietly(csvPrinter);
                IOUtils.closeQuietly(tmpOut);
                logger().debug("Done writing data to csv file, took {} seconds", BoxesRunTime.boxToLong((System.currentTimeMillis() - start) / 1000));
                return tmpFile;
            } catch (Throwable th) {
                IOUtils.closeQuietly(csvPrinter);
                IOUtils.closeQuietly(tmpOut);
                throw th;
            }
        } catch (IOException ioe) {
            throw new TaskException("Could not save .csv file", ioe);
        }
    }

    private File writeExcelFile(final java.util.List<DataTable> dataTables, final EncryptionAtRestPolicy encryptionAtRestPolicy) throws IOException, TaskException {
        File tmpFile = org.sejda.model.util.IOUtils.createTemporaryBuffer(".xlsx");
        logger().debug("Created output temporary buffer {}, writing excel data", tmpFile);
        long start = System.currentTimeMillis();
        XSSFWorkbook xSSFWorkbook = new XSSFWorkbook();
        OutputStream fileOut = encryptionAtRestPolicy.encrypt(new FileOutputStream(tmpFile));
        try {
            try {
                RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(0), dataTables.size()).foreach$mVc$sp(t -> {
                    DataTable dataTable = (DataTable) dataTables.get(t);
                    java.util.List data = dataTable.getData();
                    Sheet sheet = xSSFWorkbook.createSheet(new StringBuilder(9).append("Table ").append(t + 1).append(" (").append(dataTable.getPagesAsString()).append(")").toString());
                    RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(0), data.size()).foreach$mVc$sp(r -> {
                        java.util.List dataRow = (java.util.List) data.get(r);
                        Row row = sheet.createRow(r);
                        RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(0), dataRow.size()).foreach$mVc$sp(i -> {
                            String stringValue = (String) dataRow.get(i);
                            row.createCell(i).setCellValue(stringValue);
                        });
                    });
                    RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(0), sheet.getRow(0).getPhysicalNumberOfCells()).foreach$mVc$sp(c -> {
                        try {
                            sheet.autoSizeColumn(c);
                        } catch (Exception e) {
                            this.logger().warn("Failed to autosize column", e);
                        }
                    });
                });
                xSSFWorkbook.write(fileOut);
                logger().debug("Done writing data to excel file, took {} seconds", BoxesRunTime.boxToLong((System.currentTimeMillis() - start) / 1000));
                return tmpFile;
            } catch (IOException ioe) {
                throw new TaskException("Could not save .xlsx file", ioe);
            }
        } finally {
            if (fileOut != null) {
                fileOut.close();
            }
        }
    }

    @Override // org.sejda.model.task.Task
    public void after() {
        IOUtils.closeQuietly(sourceDocumentHandler());
        IOUtils.closeQuietly(destinationDocument());
    }
}
