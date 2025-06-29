package code.sejda.tasks.edit;

import scala.None$;
import scala.Option;
import scala.Some;

/* compiled from: EditParameters.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/FormFieldAlign$.class */
public final class FormFieldAlign$ {
    public static final FormFieldAlign$ MODULE$ = new FormFieldAlign$();

    private FormFieldAlign$() {
    }

    public Option<FormFieldAlign> fromString(final String s) {
        String lowerCase = s.toLowerCase();
        switch (lowerCase == null ? 0 : lowerCase.hashCode()) {
            case -1364013995:
                if ("center".equals(lowerCase)) {
                    return new Some(FormFieldAlign$Center$.MODULE$);
                }
                break;
            case 3317767:
                if ("left".equals(lowerCase)) {
                    return new Some(FormFieldAlign$Left$.MODULE$);
                }
                break;
            case 108511772:
                if ("right".equals(lowerCase)) {
                    return new Some(FormFieldAlign$Right$.MODULE$);
                }
                break;
        }
        return None$.MODULE$;
    }
}
