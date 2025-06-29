package code.sejda.tasks.edit;

import scala.None$;
import scala.Option;
import scala.Some;

/* compiled from: EditParameters.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/FormFieldType$.class */
public final class FormFieldType$ {
    public static final FormFieldType$ MODULE$ = new FormFieldType$();

    private FormFieldType$() {
    }

    public Option<FormFieldType> fromString(final String s) {
        String lowerCase = s.toLowerCase();
        switch (lowerCase == null ? 0 : lowerCase.hashCode()) {
            case -432061423:
                if ("dropdown".equals(lowerCase)) {
                    return new Some(FormFieldType$Dropdown$.MODULE$);
                }
                break;
            case 3556653:
                if ("text".equals(lowerCase)) {
                    return new Some(FormFieldType$Text$.MODULE$);
                }
                break;
            case 108270587:
                if ("radio".equals(lowerCase)) {
                    return new Some(FormFieldType$Radio$.MODULE$);
                }
                break;
            case 1073584312:
                if ("signature".equals(lowerCase)) {
                    return new Some(FormFieldType$SignatureBox$.MODULE$);
                }
                break;
            case 1536891843:
                if ("checkbox".equals(lowerCase)) {
                    return new Some(FormFieldType$Checkbox$.MODULE$);
                }
                break;
        }
        return None$.MODULE$;
    }
}
