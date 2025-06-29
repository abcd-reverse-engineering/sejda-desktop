package code.sejda.tasks.edit;

import scala.MatchError;
import scala.Option;
import scala.reflect.ScalaSignature;

/* compiled from: EditParameters.scala */
@ScalaSignature(bytes = "\u0006\u0005I3q\u0001E\t\u0011\u0002\u0007\u0005\"\u0004C\u0003\"\u0001\u0011\u0005!\u0005C\u0003'\u0001\u0011\u0005seB\u0003R#!\u0005\u0001HB\u0003\u0011#!\u0005Q\u0007C\u00037\t\u0011\u0005qgB\u0003;\t!\u00051HB\u0003>\t!\u0005a\bC\u00037\u000f\u0011\u0005\u0001iB\u0003B\t!\u0005!IB\u00035\t!\u0005q\nC\u00037\u0015\u0011\u0005\u0001kB\u0003D\t!\u0005AIB\u0003F\t!\u0005a\tC\u00037\u001b\u0011\u0005q\tC\u0003I\t\u0011\u0005\u0011J\u0001\bG_Jlg)[3mI\u0006c\u0017n\u001a8\u000b\u0005I\u0019\u0012\u0001B3eSRT!\u0001F\u000b\u0002\u000bQ\f7o[:\u000b\u0005Y9\u0012!B:fU\u0012\f'\"\u0001\r\u0002\t\r|G-Z\u0002\u0001'\t\u00011\u0004\u0005\u0002\u001d?5\tQDC\u0001\u001f\u0003\u0015\u00198-\u00197b\u0013\t\u0001SD\u0001\u0004B]f\u0014VMZ\u0001\u0007I%t\u0017\u000e\u001e\u0013\u0015\u0003\r\u0002\"\u0001\b\u0013\n\u0005\u0015j\"\u0001B+oSR\f\u0001\u0002^8TiJLgn\u001a\u000b\u0002QA\u0011\u0011\u0006\r\b\u0003U9\u0002\"aK\u000f\u000e\u00031R!!L\r\u0002\rq\u0012xn\u001c;?\u0013\tyS$\u0001\u0004Qe\u0016$WMZ\u0005\u0003cI\u0012aa\u0015;sS:<'BA\u0018\u001eS\u0011\u0001!bB\u0007\u0003\r\r+g\u000e^3s'\t!1$\u0001\u0004=S:LGO\u0010\u000b\u0002qA\u0011\u0011\bB\u0007\u0002#\u0005!A*\u001a4u!\tat!D\u0001\u0005\u0005\u0011aUM\u001a;\u0014\u0007\u001dYr\b\u0005\u0002:\u0001Q\t1(\u0001\u0004DK:$XM\u001d\t\u0003y)\tQAU5hQR\u0004\"\u0001P\u0007\u0003\u000bIKw\r\u001b;\u0014\u00075Yr\bF\u0001E\u0003)1'o\\7TiJLgn\u001a\u000b\u0003\u00156\u00032\u0001H&@\u0013\taUD\u0001\u0004PaRLwN\u001c\u0005\u0006\u001d>\u0001\r\u0001K\u0001\u0002gN\u0019!bG \u0015\u0003\t\u000baBR8s[\u001aKW\r\u001c3BY&<g\u000e")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/FormFieldAlign.class */
public interface FormFieldAlign {
    static Option<FormFieldAlign> fromString(final String s) {
        return FormFieldAlign$.MODULE$.fromString(s);
    }

    static void $init$(final FormFieldAlign $this) {
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    default String toString() throws MatchError {
        if (FormFieldAlign$Left$.MODULE$.equals(this)) {
            return "left";
        }
        if (FormFieldAlign$Center$.MODULE$.equals(this)) {
            return "center";
        }
        if (FormFieldAlign$Right$.MODULE$.equals(this)) {
            return "right";
        }
        throw new MatchError(this);
    }
}
