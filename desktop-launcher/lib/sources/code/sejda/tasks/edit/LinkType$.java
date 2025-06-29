package code.sejda.tasks.edit;

import scala.None$;
import scala.Option;
import scala.Some;

/* compiled from: EditParameters.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/LinkType$.class */
public final class LinkType$ {
    public static final LinkType$ MODULE$ = new LinkType$();

    private LinkType$() {
    }

    public Option<LinkType> fromString(final String s) {
        String lowerCase = s.toLowerCase();
        switch (lowerCase == null ? 0 : lowerCase.hashCode()) {
            case 116076:
                if ("uri".equals(lowerCase)) {
                    return new Some(LinkType$URI$.MODULE$);
                }
                break;
            case 3433103:
                if ("page".equals(lowerCase)) {
                    return new Some(LinkType$Page$.MODULE$);
                }
                break;
        }
        return None$.MODULE$;
    }
}
