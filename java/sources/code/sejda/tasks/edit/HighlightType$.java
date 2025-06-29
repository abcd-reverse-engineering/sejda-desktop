package code.sejda.tasks.edit;

import scala.None$;
import scala.Option;
import scala.Some;

/* compiled from: EditParameters.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/HighlightType$.class */
public final class HighlightType$ {
    public static final HighlightType$ MODULE$ = new HighlightType$();

    private HighlightType$() {
    }

    public Option<HighlightType> fromString(final String s) {
        String lowerCase = s.toLowerCase();
        switch (lowerCase == null ? 0 : lowerCase.hashCode()) {
            case -1026963764:
                if ("underline".equals(lowerCase)) {
                    return new Some(HighlightType$Underline$.MODULE$);
                }
                break;
            case -972521773:
                if ("strikethrough".equals(lowerCase)) {
                    return new Some(HighlightType$Strikethrough$.MODULE$);
                }
                break;
            case -681210700:
                if ("highlight".equals(lowerCase)) {
                    return new Some(HighlightType$Highlight$.MODULE$);
                }
                break;
        }
        return None$.MODULE$;
    }
}
