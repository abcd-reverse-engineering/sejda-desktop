package code.util.zip;

import code.util.StringHelpers$;
import code.util.encryption.FileEncryption;
import com.google.common.io.ByteStreams;
import com.google.common.io.Closeables;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.Tuple2;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

/* compiled from: Zipper.scala */
@ScalaSignature(bytes = "\u0006\u000514AAB\u0004\u0001\u001d!AQ\u0003\u0001B\u0001B\u0003%a\u0003C\u0003\u001c\u0001\u0011\u0005A\u0004C\u0003!\u0001\u0011\u0005\u0011\u0005C\u0003>\u0001\u0011\u0005a\bC\u0003Q\u0001\u0011\u0005\u0011K\u0001\u0004[SB\u0004XM\u001d\u0006\u0003\u0011%\t1A_5q\u0015\tQ1\"\u0001\u0003vi&d'\"\u0001\u0007\u0002\t\r|G-Z\u0002\u0001'\t\u0001q\u0002\u0005\u0002\u0011'5\t\u0011CC\u0001\u0013\u0003\u0015\u00198-\u00197b\u0013\t!\u0012C\u0001\u0004B]f\u0014VMZ\u0001\u000bK:\u001c'/\u001f9uS>t\u0007CA\f\u001a\u001b\u0005A\"BA\u000b\n\u0013\tQ\u0002D\u0001\bGS2,WI\\2ssB$\u0018n\u001c8\u0002\rqJg.\u001b;?)\tir\u0004\u0005\u0002\u001f\u00015\tq\u0001C\u0003\u0016\u0005\u0001\u0007a#\u0001\u0004{SB\fE\u000e\u001c\u000b\u0004E)B\u0004CA\u0012)\u001b\u0005!#BA\u0013'\u0003\tIwNC\u0001(\u0003\u0011Q\u0017M^1\n\u0005%\"#\u0001\u0002$jY\u0016DQaK\u0002A\u00021\nQAZ5mKN\u00042!L\u001b#\u001d\tq3G\u0004\u00020e5\t\u0001G\u0003\u00022\u001b\u00051AH]8pizJ\u0011AE\u0005\u0003iE\tq\u0001]1dW\u0006<W-\u0003\u00027o\t\u00191+Z9\u000b\u0005Q\n\u0002\"B\u001d\u0004\u0001\u0004Q\u0014A\u0005;f[B4u\u000e\u001c3feB\u0013xN^5eKJ\u00042\u0001E\u001e#\u0013\ta\u0014CA\u0005Gk:\u001cG/[8oa\u00059!0\u001b9qS:<GCA O)\t\u0011\u0003\tC\u0003B\t\u0001\u0007!)\u0001\u0003gk:\u001c\u0007\u0003\u0002\tD\u000b.K!\u0001R\t\u0003\u0013\u0019+hn\u0019;j_:\f\u0004C\u0001$J\u001b\u00059%B\u0001\u0005I\u0015\tQa%\u0003\u0002K\u000f\ny!,\u001b9PkR\u0004X\u000f^*ue\u0016\fW\u000e\u0005\u0002\u0011\u0019&\u0011Q*\u0005\u0002\u0004\u0003:L\b\"B(\u0005\u0001\u0004\u0011\u0013\u0001\u00024jY\u0016\fQ!^:j]\u001e,\"A\u0015,\u0015\u0005M[GC\u0001+]!\t)f\u000b\u0004\u0001\u0005\u000b]+!\u0019\u0001-\u0003\u0003Q\u000b\"!W&\u0011\u0005AQ\u0016BA.\u0012\u0005\u001dqu\u000e\u001e5j]\u001eDQ!Q\u0003A\u0002u\u0003R\u0001\u00050aGRK!aX\t\u0003\u0013\u0019+hn\u0019;j_:\u0014\u0004CA\u0012b\u0013\t\u0011GEA\u0006J]B,Ho\u0015;sK\u0006l\u0007C\u00013i\u001d\t)g\r\u0005\u00020#%\u0011q-E\u0001\u0007!J,G-\u001a4\n\u0005%T'AB*ue&twM\u0003\u0002h#!)q*\u0002a\u0001E\u0001")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/zip/Zipper.class */
public class Zipper {
    private final FileEncryption encryption;

    public Zipper(final FileEncryption encryption) {
        this.encryption = encryption;
    }

    public File zipAll(final Seq<File> files, final Function0<File> tempFolderProvider) {
        return zipping(new File((File) tempFolderProvider.apply(), new StringBuilder(10).append("sejda-").append(StringHelpers$.MODULE$.randomString(6)).append(".zip").toString()), zip -> {
            return (Seq) files.map(file -> {
                $anonfun$zipAll$2(this, zip, file);
                return BoxedUnit.UNIT;
            });
        });
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    public static final /* synthetic */ void $anonfun$zipAll$3(final ZipOutputStream zip$1, final InputStream x0$1, final String x1$1) throws MatchError, IOException {
        Tuple2 tuple2 = new Tuple2(x0$1, x1$1);
        if (tuple2 == null) {
            throw new MatchError(tuple2);
        }
        InputStream in = (InputStream) tuple2._1();
        String name = (String) tuple2._2();
        zip$1.putNextEntry(new ZipEntry(name));
        ByteStreams.copy(in, zip$1);
        zip$1.closeEntry();
        BoxedUnit boxedUnit = BoxedUnit.UNIT;
    }

    public static final /* synthetic */ void $anonfun$zipAll$2(final Zipper $this, final ZipOutputStream zip$1, final File file) {
        $this.using(file, (x0$1, x1$1) -> {
            $anonfun$zipAll$3(zip$1, x0$1, x1$1);
            return BoxedUnit.UNIT;
        });
    }

    public File zipping(final File file, final Function1<ZipOutputStream, Object> func) throws IOException {
        OutputStream fos = this.encryption.encryptedFileOut(file);
        ZipOutputStream zip = new ZipOutputStream(fos);
        zip.setLevel(0);
        try {
            func.apply(zip);
            return file;
        } finally {
            zip.finish();
            Closeables.close(fos, true);
            Closeables.close(zip, true);
        }
    }

    public <T> T using(File file, Function2<InputStream, String, T> function2) {
        InputStream inputStreamDecryptedFileIn = this.encryption.decryptedFileIn(file);
        try {
            return (T) function2.apply(inputStreamDecryptedFileIn, file.getName());
        } finally {
            Closeables.closeQuietly(inputStreamDecryptedFileIn);
        }
    }
}
