package code.sejda.tasks.ocr;

import scala.None$;
import scala.Option;
import scala.collection.immutable.Seq;
import scala.package$;

/* compiled from: OcrParameters.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/ocr/OcrParameters$.class */
public final class OcrParameters$ {
    public static final OcrParameters$ MODULE$ = new OcrParameters$();

    public Option<String> $lessinit$greater$default$1() {
        return None$.MODULE$;
    }

    private OcrParameters$() {
    }

    public Seq<OutputFormat> $lessinit$greater$default$2() {
        return package$.MODULE$.Seq().empty();
    }
}
