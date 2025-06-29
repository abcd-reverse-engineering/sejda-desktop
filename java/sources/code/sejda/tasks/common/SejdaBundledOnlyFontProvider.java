package code.sejda.tasks.common;

import java.io.File;
import java.util.List;
import scala.collection.JavaConverters$;
import scala.reflect.ScalaSignature;

/* compiled from: SejdaBundledOnlyFontProvider.scala */
@ScalaSignature(bytes = "\u0006\u0005)2Aa\u0001\u0003\u0001\u001b!)\u0001\u0004\u0001C\u00013!)1\u0004\u0001C!9\ta2+\u001a6eC\n+h\u000e\u001a7fI>sG.\u001f$p]R\u0004&o\u001c<jI\u0016\u0014(BA\u0003\u0007\u0003\u0019\u0019w.\\7p]*\u0011q\u0001C\u0001\u0006i\u0006\u001c8n\u001d\u0006\u0003\u0013)\tQa]3kI\u0006T\u0011aC\u0001\u0005G>$Wm\u0001\u0001\u0014\u0007\u0001q!\u0003\u0005\u0002\u0010!5\tA!\u0003\u0002\u0012\t\t\u0001\")Y:f\r>tG\u000f\u0015:pm&$WM\u001d\t\u0003'Yi\u0011\u0001\u0006\u0006\u0003+)\tA!\u001e;jY&\u0011q\u0003\u0006\u0002\t\u0019><w-\u00192mK\u00061A(\u001b8jiz\"\u0012A\u0007\t\u0003\u001f\u0001\tQBZ5oI\u001a{g\u000e\u001e$jY\u0016\u001cH#A\u000f\u0011\u0007y\u0011C%D\u0001 \u0015\t)\u0002EC\u0001\"\u0003\u0011Q\u0017M^1\n\u0005\rz\"\u0001\u0002'jgR\u0004\"!\n\u0015\u000e\u0003\u0019R!a\n\u0011\u0002\u0005%|\u0017BA\u0015'\u0005\u00111\u0015\u000e\\3")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/SejdaBundledOnlyFontProvider.class */
public class SejdaBundledOnlyFontProvider extends BaseFontProvider {
    @Override // org.sejda.sambox.pdmodel.font.FileSystemFontProvider
    public List<File> findFontFiles() {
        return (List) JavaConverters$.MODULE$.seqAsJavaListConverter(bundledFontFiles()).asJava();
    }
}
