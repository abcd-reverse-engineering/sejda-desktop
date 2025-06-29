package code.sejda.tasks.common.image;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.collection.immutable.Seq;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: PageImageRedactor.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/image/ImageRedactionResults$.class */
public final class ImageRedactionResults$ extends AbstractFunction1<Seq<ImageRedaction>, ImageRedactionResults> implements Serializable {
    public static final ImageRedactionResults$ MODULE$ = new ImageRedactionResults$();

    public final String toString() {
        return "ImageRedactionResults";
    }

    public ImageRedactionResults apply(final Seq<ImageRedaction> notFound) {
        return new ImageRedactionResults(notFound);
    }

    public Option<Seq<ImageRedaction>> unapply(final ImageRedactionResults x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(x$0.notFound());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ImageRedactionResults$.class);
    }

    private ImageRedactionResults$() {
    }
}
