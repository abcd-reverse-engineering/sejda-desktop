package code.model;

import java.io.File;
import org.sejda.model.encryption.EncryptionAtRestPolicy;
import org.sejda.model.input.PdfSource;
import org.sejda.model.input.Source;
import scala.Option;
import scala.reflect.ScalaSignature;

/* compiled from: FileProvider.scala */
@ScalaSignature(bytes = "\u0006\u0005m3q\u0001C\u0005\u0011\u0002G\u0005a\u0002C\u0003\u0016\u0001\u0019\u0005a\u0003C\u00037\u0001\u0019\u0005q\u0007C\u0003=\u0001\u0019\u0005Q\bC\u0003?\u0001\u0019\u0005q\bC\u0003C\u0001\u0019\u00051\tC\u0003N\u0001\u0019\u0005a\nC\u0003U\u0001\u0019\u0005QK\u0001\u0007GS2,\u0007K]8wS\u0012,'O\u0003\u0002\u000b\u0017\u0005)Qn\u001c3fY*\tA\"\u0001\u0003d_\u0012,7\u0001A\n\u0003\u0001=\u0001\"\u0001E\n\u000e\u0003EQ\u0011AE\u0001\u0006g\u000e\fG.Y\u0005\u0003)E\u0011a!\u00118z%\u00164\u0017\u0001D4fiB#gmU8ve\u000e,GCA\f+!\rA\u0002EI\u0007\u00023)\u0011!dG\u0001\u0006S:\u0004X\u000f\u001e\u0006\u0003\u0015qQ!!\b\u0010\u0002\u000bM,'\u000eZ1\u000b\u0003}\t1a\u001c:h\u0013\t\t\u0013DA\u0005QI\u001a\u001cv.\u001e:dKB\u00111\u0005K\u0007\u0002I)\u0011QEJ\u0001\u0003S>T\u0011aJ\u0001\u0005U\u00064\u0018-\u0003\u0002*I\t!a)\u001b7f\u0011\u0015Q\u0012\u00011\u0001,!\ta3G\u0004\u0002.cA\u0011a&E\u0007\u0002_)\u0011\u0001'D\u0001\u0007yI|w\u000e\u001e \n\u0005I\n\u0012A\u0002)sK\u0012,g-\u0003\u00025k\t11\u000b\u001e:j]\u001eT!AM\t\u0002\u0013\u001d,GoU8ve\u000e,GC\u0001\u001d<!\rA\u0012HI\u0005\u0003ue\u0011aaU8ve\u000e,\u0007\"\u0002\u000e\u0003\u0001\u0004Y\u0013\u0001D2sK\u0006$X\rV7q\t&\u0014H#\u0001\u0012\u0002\u001b\r\u0014X-\u0019;f)6\u0004h)\u001b7f)\t\u0011\u0003\tC\u0003B\t\u0001\u00071&\u0001\u0003oC6,\u0017\u0001B:bm\u0016$2\u0001\u000f#M\u0011\u0015)U\u00011\u0001G\u0003\u0015\u0011\u0017\u0010^3t!\r\u0001r)S\u0005\u0003\u0011F\u0011Q!\u0011:sCf\u0004\"\u0001\u0005&\n\u0005-\u000b\"\u0001\u0002\"zi\u0016DQ!Q\u0003A\u0002-\n!\"\u001a8def\u0004H/[8o+\u0005y\u0005C\u0001)S\u001b\u0005\t&BA'\u001c\u0013\t\u0019\u0016K\u0001\fF]\u000e\u0014\u0018\u0010\u001d;j_:\fEOU3tiB{G.[2z\u0003E1\u0017\u000e\\3oC6,wJ\u001d#fM\u0006,H\u000e\u001e\u000b\u0003WYCQaV\u0004A\u0002a\u000b1BZ5mK:\fW.Z(qiB\u0019\u0001#W\u0016\n\u0005i\u000b\"AB(qi&|g\u000e")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/model/FileProvider.class */
public interface FileProvider {
    PdfSource<File> getPdfSource(final String input);

    Source<File> getSource(final String input);

    File createTmpDir();

    File createTmpFile(final String name);

    Source<File> save(final byte[] bytes, final String name);

    EncryptionAtRestPolicy encryption();

    String filenameOrDefault(final Option<String> filenameOpt);
}
