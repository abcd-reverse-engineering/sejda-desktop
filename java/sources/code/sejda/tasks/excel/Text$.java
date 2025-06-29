package code.sejda.tasks.excel;

import org.sejda.sambox.text.TextPosition;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.collection.immutable.Seq;

/* compiled from: TableDetector.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/excel/Text$.class */
public final class Text$ {
    public static final Text$ MODULE$ = new Text$();

    private Text$() {
    }

    public Option<Text> opt(final Seq<TextPosition> tps) {
        if (tps.nonEmpty()) {
            return new Some(apply(tps));
        }
        return None$.MODULE$;
    }

    public Text apply(final Seq<TextPosition> tps) {
        return new Text(tps);
    }
}
