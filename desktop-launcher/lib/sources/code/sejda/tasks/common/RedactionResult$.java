package code.sejda.tasks.common;

import code.sejda.tasks.common.text.PdfTextRedactingStreamEngine;
import java.awt.Point;
import java.io.Serializable;
import org.sejda.sambox.pdmodel.font.PDFont;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.pdmodel.graphics.state.RenderingMode;
import scala.None$;
import scala.Option;
import scala.Option$;
import scala.Some;
import scala.Tuple7;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: PageTextRedactor.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/RedactionResult$.class */
public final class RedactionResult$ implements Serializable {
    public static final RedactionResult$ MODULE$ = new RedactionResult$();

    public RedactionResult apply(final String text, final Option<Point> position, final Option<PDFont> font, final Option<Object> fontSize, final Option<PDColor> color, final Option<RenderingMode> renderingMode, final boolean textFoundAndRedacted) {
        return new RedactionResult(text, position, font, fontSize, color, renderingMode, textFoundAndRedacted);
    }

    public Option<Tuple7<String, Option<Point>, Option<PDFont>, Option<Object>, Option<PDColor>, Option<RenderingMode>, Object>> unapply(final RedactionResult x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple7(x$0.text(), x$0.position(), x$0.font(), x$0.fontSize(), x$0.color(), x$0.renderingMode(), BoxesRunTime.boxToBoolean(x$0.textFoundAndRedacted())));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(RedactionResult$.class);
    }

    private RedactionResult$() {
    }

    public RedactionResult apply(final PdfTextRedactingStreamEngine engine) {
        boolean z;
        Option position = Option$.MODULE$.apply(engine.redactedTextPosition());
        Option font = Option$.MODULE$.apply(engine.redactedFont());
        if (position.isEmpty()) {
            z = false;
        } else {
            z = !font.exists(x$1 -> {
                return BoxesRunTime.boxToBoolean($anonfun$apply$1(x$1));
            }) || engine.getPartiallyRedactedOperators().isEmpty();
        }
        boolean textFoundAndRedacted = z;
        return new RedactionResult(engine.redactedString().toString(), position, font, Option$.MODULE$.apply(BoxesRunTime.boxToFloat(engine.redactedFontSize())).map(x$2 -> {
            return x$2;
        }), Option$.MODULE$.apply(engine.redactedFontColor()), Option$.MODULE$.apply(engine.redactedTextRenderingMode()), textFoundAndRedacted);
    }

    public static final /* synthetic */ boolean $anonfun$apply$1(final PDFont x$1) {
        String subType = x$1.getSubType();
        return subType != null ? subType.equals("Type3") : "Type3" == 0;
    }
}
