package code.sejda.tasks.common;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import scala.collection.JavaConverters$;
import scala.reflect.ScalaSignature;

/* compiled from: SejdaBundledPlusFileSystemFontProvider.scala */
@ScalaSignature(bytes = "\u0006\u0005)2Aa\u0001\u0003\u0001\u001b!)\u0001\u0004\u0001C\u00013!)1\u0004\u0001C!9\t13+\u001a6eC\n+h\u000e\u001a7fIBcWo\u001d$jY\u0016\u001c\u0016p\u001d;f[\u001a{g\u000e\u001e)s_ZLG-\u001a:\u000b\u0005\u00151\u0011AB2p[6|gN\u0003\u0002\b\u0011\u0005)A/Y:lg*\u0011\u0011BC\u0001\u0006g\u0016TG-\u0019\u0006\u0002\u0017\u0005!1m\u001c3f\u0007\u0001\u00192\u0001\u0001\b\u0013!\ty\u0001#D\u0001\u0005\u0013\t\tBA\u0001\tCCN,gi\u001c8u!J|g/\u001b3feB\u00111CF\u0007\u0002))\u0011QCC\u0001\u0005kRLG.\u0003\u0002\u0018)\tAAj\\4hC\ndW-\u0001\u0004=S:LGO\u0010\u000b\u00025A\u0011q\u0002A\u0001\u000eM&tGMR8oi\u001aKG.Z:\u0015\u0003u\u00012A\b\u0012%\u001b\u0005y\"BA\u000b!\u0015\u0005\t\u0013\u0001\u00026bm\u0006L!aI\u0010\u0003\t1K7\u000f\u001e\t\u0003K!j\u0011A\n\u0006\u0003O\u0001\n!![8\n\u0005%2#\u0001\u0002$jY\u0016\u0004")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/SejdaBundledPlusFileSystemFontProvider.class */
public class SejdaBundledPlusFileSystemFontProvider extends BaseFontProvider {
    @Override // org.sejda.sambox.pdmodel.font.FileSystemFontProvider
    public List<File> findFontFiles() {
        ArrayList result = new ArrayList();
        result.addAll((Collection) JavaConverters$.MODULE$.seqAsJavaListConverter(bundledFontFiles()).asJava());
        result.addAll(super.findFontFiles());
        return result;
    }
}
