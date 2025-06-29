package code.sejda.tasks.flatten;

import scala.None$;
import scala.Option;
import scala.Some;

/* compiled from: FlattenParameters.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/flatten/FlattenMode$.class */
public final class FlattenMode$ {
    public static final FlattenMode$ MODULE$ = new FlattenMode$();

    private FlattenMode$() {
    }

    public Option<FlattenMode> fromString(final String s) {
        String lowerCase = s.toLowerCase();
        switch (lowerCase == null ? 0 : lowerCase.hashCode()) {
            case 96673:
                if ("all".equals(lowerCase)) {
                    return new Some(FlattenMode$All$.MODULE$);
                }
                break;
            case 299522051:
                if ("onlyforms".equals(lowerCase)) {
                    return new Some(FlattenMode$OnlyForms$.MODULE$);
                }
                break;
        }
        return None$.MODULE$;
    }
}
