package code.sejda.tasks.common.image;

import java.io.Serializable;
import org.sejda.sambox.pdmodel.PDPage;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple3;
import scala.runtime.AbstractFunction3;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: PageImageRedactor.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/image/ImageRedaction$.class */
public final class ImageRedaction$ extends AbstractFunction3<PDPage, Object, String, ImageRedaction> implements Serializable {
    public static final ImageRedaction$ MODULE$ = new ImageRedaction$();

    public final String toString() {
        return "ImageRedaction";
    }

    public ImageRedaction apply(final PDPage page, final int pageNum, final String objId) {
        return new ImageRedaction(page, pageNum, objId);
    }

    public Option<Tuple3<PDPage, Object, String>> unapply(final ImageRedaction x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple3(x$0.page(), BoxesRunTime.boxToInteger(x$0.pageNum()), x$0.objId()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ImageRedaction$.class);
    }

    public /* bridge */ /* synthetic */ Object apply(final Object v1, final Object v2, final Object v3) {
        return apply((PDPage) v1, BoxesRunTime.unboxToInt(v2), (String) v3);
    }

    private ImageRedaction$() {
    }
}
