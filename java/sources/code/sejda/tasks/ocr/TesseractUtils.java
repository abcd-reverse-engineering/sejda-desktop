package code.sejda.tasks.ocr;

import java.io.File;
import org.sejda.model.encryption.EncryptionAtRestPolicy;
import org.sejda.model.input.PdfSource;
import org.sejda.model.task.TaskExecutionContext;
import scala.Option;
import scala.Tuple2;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.Future;
import scala.reflect.ScalaSignature;

/* compiled from: TesseractUtils.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005mr!B\u0006\r\u0011\u0003)b!B\f\r\u0011\u0003A\u0002\"B\u0013\u0002\t\u00031\u0003bB\u0014\u0002\u0005\u0004%\u0019\u0001\u000b\u0005\u0007_\u0005\u0001\u000b\u0011B\u0015\t\u000fA\n!\u0019!C\u0001c!1Q'\u0001Q\u0001\nIBQAN\u0001\u0005\u0002]Bq!_\u0001\u0012\u0002\u0013\u0005!\u0010C\u0004\u0002\f\u0005!\t!!\u0004\t\u000f\u0005\u0005\u0012\u0001\"\u0001\u0002$\u0005qA+Z:tKJ\f7\r^+uS2\u001c(BA\u0007\u000f\u0003\ry7M\u001d\u0006\u0003\u001fA\tQ\u0001^1tWNT!!\u0005\n\u0002\u000bM,'\u000eZ1\u000b\u0003M\tAaY8eK\u000e\u0001\u0001C\u0001\f\u0002\u001b\u0005a!A\u0004+fgN,'/Y2u+RLGn]\n\u0004\u0003ey\u0002C\u0001\u000e\u001e\u001b\u0005Y\"\"\u0001\u000f\u0002\u000bM\u001c\u0017\r\\1\n\u0005yY\"AB!osJ+g\r\u0005\u0002!G5\t\u0011E\u0003\u0002#%\u0005!Q\u000f^5m\u0013\t!\u0013E\u0001\u0005M_\u001e<\u0017M\u00197f\u0003\u0019a\u0014N\\5u}Q\tQ#A\fgkR,(/Z:Fq\u0016\u001cW\u000f^5p]\u000e{g\u000e^3yiV\t\u0011\u0006\u0005\u0002+[5\t1F\u0003\u0002-7\u0005Q1m\u001c8dkJ\u0014XM\u001c;\n\u00059Z#\u0001G#yK\u000e,H/[8o\u0007>tG/\u001a=u\u000bb,7-\u001e;pe\u0006Ab-\u001e;ve\u0016\u001cX\t_3dkRLwN\\\"p]R,\u0007\u0010\u001e\u0011\u0002\u0013\t\fGo\u00195TSj,W#\u0001\u001a\u0011\u0005i\u0019\u0014B\u0001\u001b\u001c\u0005\rIe\u000e^\u0001\u000bE\u0006$8\r[*ju\u0016\u0004\u0013A\u00039fe\u001a|'/\\(deR)\u0001\bQ-jcB\u0011\u0011HP\u0007\u0002u)\u00111\bP\u0001\u0003S>T\u0011!P\u0001\u0005U\u00064\u0018-\u0003\u0002@u\t!a)\u001b7f\u0011\u0015\tu\u00011\u0001C\u0003\u0019\u0019x.\u001e:dKB\u00121\t\u0015\t\u0004\t2sU\"A#\u000b\u0005\u0019;\u0015!B5oaV$(B\u0001%J\u0003\u0015iw\u000eZ3m\u0015\t\t\"JC\u0001L\u0003\ry'oZ\u0005\u0003\u001b\u0016\u0013\u0011\u0002\u00153g'>,(oY3\u0011\u0005=\u0003F\u0002\u0001\u0003\n#\u0002\u000b\t\u0011!A\u0003\u0002I\u00131a\u0018\u00132#\t\u0019f\u000b\u0005\u0002\u001b)&\u0011Qk\u0007\u0002\b\u001d>$\b.\u001b8h!\tQr+\u0003\u0002Y7\t\u0019\u0011I\\=\t\u000fi;\u0001\u0013!a\u00017\u0006AA.\u00198hk\u0006<W\rE\u0002\u001b9zK!!X\u000e\u0003\r=\u0003H/[8o!\tyfM\u0004\u0002aIB\u0011\u0011mG\u0007\u0002E*\u00111\rF\u0001\u0007yI|w\u000e\u001e \n\u0005\u0015\\\u0012A\u0002)sK\u0012,g-\u0003\u0002hQ\n11\u000b\u001e:j]\u001eT!!Z\u000e\t\u000b)<\u0001\u0019A6\u0002!\u0015t7M]=qi&|g.\u0011;SKN$\bC\u00017p\u001b\u0005i'B\u00018H\u0003))gn\u0019:zaRLwN\\\u0005\u0003a6\u0014a#\u00128def\u0004H/[8o\u0003R\u0014Vm\u001d;Q_2L7-\u001f\u0005\u0006e\u001e\u0001\ra]\u0001\u0011Kb,7-\u001e;j_:\u001cuN\u001c;fqR\u0004\"\u0001^<\u000e\u0003UT!A^$\u0002\tQ\f7o[\u0005\u0003qV\u0014A\u0003V1tW\u0016CXmY;uS>t7i\u001c8uKb$\u0018\u0001\u00069fe\u001a|'/\\(de\u0012\"WMZ1vYR$#'F\u0001|U\tYFpK\u0001~!\rq\u0018qA\u0007\u0002\u007f*!\u0011\u0011AA\u0002\u0003%)hn\u00195fG.,GMC\u0002\u0002\u0006m\t!\"\u00198o_R\fG/[8o\u0013\r\tIa \u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017\u0001D2p]Z,'\u000f\u001e)bO\u0016\u001cHc\u0002\u001d\u0002\u0010\u0005e\u0011Q\u0004\u0005\b\u0003#I\u0001\u0019AA\n\u0003)!wn\u0019%b]\u0012dWM\u001d\t\u0004-\u0005U\u0011bAA\f\u0019\tI\"+Z8qK:\f'\r\\3E_\u000e,X.\u001a8u\u0011\u0006tG\r\\3s\u0011\u0019\tY\"\u0003a\u0001e\u0005I1\u000f^1siB\u000bw-\u001a\u0005\u0007\u0003?I\u0001\u0019\u0001\u001a\u0002\u000f\u0015tG\rU1hK\u0006Aqn\u0019:QC\u001e,7\u000f\u0006\u0006\u0002&\u0005E\u0012QGA\u001c\u0003s\u0001RAKA\u0014\u0003WI1!!\u000b,\u0005\u00191U\u000f^;sKB)!$!\f3q%\u0019\u0011qF\u000e\u0003\rQ+\b\u000f\\33\u0011\u0019\t\u0019D\u0003a\u0001q\u0005i\u0011N\u001c9viRKgM\u001a$jY\u0016Da!a\u0007\u000b\u0001\u0004\u0011\u0004BBA\u0010\u0015\u0001\u0007!\u0007C\u0003[\u0015\u0001\u00071\f")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/ocr/TesseractUtils.class */
public final class TesseractUtils {
    public static Future<Tuple2<Object, File>> ocrPages(final File inputTiffFile, final int startPage, final int endPage, final Option<String> language) {
        return TesseractUtils$.MODULE$.ocrPages(inputTiffFile, startPage, endPage, language);
    }

    public static File convertPages(final ReopenableDocumentHandler docHandler, final int startPage, final int endPage) {
        return TesseractUtils$.MODULE$.convertPages(docHandler, startPage, endPage);
    }

    public static File performOcr(final PdfSource<?> source, final Option<String> language, final EncryptionAtRestPolicy encryptionAtRest, final TaskExecutionContext executionContext) {
        return TesseractUtils$.MODULE$.performOcr(source, language, encryptionAtRest, executionContext);
    }

    public static int batchSize() {
        return TesseractUtils$.MODULE$.batchSize();
    }

    public static ExecutionContextExecutor futuresExecutionContext() {
        return TesseractUtils$.MODULE$.futuresExecutionContext();
    }
}
