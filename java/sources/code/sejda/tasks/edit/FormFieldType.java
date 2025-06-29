package code.sejda.tasks.edit;

import scala.MatchError;
import scala.Option;
import scala.reflect.ScalaSignature;

/* compiled from: EditParameters.scala */
@ScalaSignature(bytes = "\u0006\u0005\t4qAF\f\u0011\u0002\u0007\u0005\u0002\u0005C\u0003(\u0001\u0011\u0005\u0001\u0006C\u0003-\u0001\u0011\u0005SfB\u0003b/!\u0005aHB\u0003\u0017/!\u00051\bC\u0003=\t\u0011\u0005QhB\u0003A\t!\u0005\u0011IB\u0003D\t!\u0005A\tC\u0003=\u000f\u0011\u0005aiB\u0003H\t!\u0005\u0001JB\u0003J\t!\u0005!\nC\u0003=\u0015\u0011\u00051jB\u0003M\t!\u0005QJB\u0003;\t!\u0005q\fC\u0003=\u001b\u0011\u0005\u0001mB\u0003O\t!\u0005qJB\u0003Q\t!\u0005\u0011\u000bC\u0003=!\u0011\u0005!kB\u0003T\t!\u0005AKB\u0003V\t!\u0005a\u000bC\u0003='\u0011\u0005q\u000bC\u0003Y\t\u0011\u0005\u0011LA\u0007G_Jlg)[3mIRK\b/\u001a\u0006\u00031e\tA!\u001a3ji*\u0011!dG\u0001\u0006i\u0006\u001c8n\u001d\u0006\u00039u\tQa]3kI\u0006T\u0011AH\u0001\u0005G>$Wm\u0001\u0001\u0014\u0005\u0001\t\u0003C\u0001\u0012&\u001b\u0005\u0019#\"\u0001\u0013\u0002\u000bM\u001c\u0017\r\\1\n\u0005\u0019\u001a#AB!osJ+g-\u0001\u0004%S:LG\u000f\n\u000b\u0002SA\u0011!EK\u0005\u0003W\r\u0012A!\u00168ji\u0006AAo\\*ue&tw\rF\u0001/!\tycG\u0004\u00021iA\u0011\u0011gI\u0007\u0002e)\u00111gH\u0001\u0007yI|w\u000e\u001e \n\u0005U\u001a\u0013A\u0002)sK\u0012,g-\u0003\u00028q\t11\u000b\u001e:j]\u001eT!!N\u0012*\r\u0001i\u0001CC\n\b\u0005!\u0019\u0005.Z2lE>D8C\u0001\u0003\"\u0003\u0019a\u0014N\\5u}Q\ta\b\u0005\u0002@\t5\tq#\u0001\u0003UKb$\bC\u0001\"\b\u001b\u0005!!\u0001\u0002+fqR\u001c2aB\u0011F!\ty\u0004\u0001F\u0001B\u0003\u0015\u0011\u0016\rZ5p!\t\u0011%BA\u0003SC\u0012LwnE\u0002\u000bC\u0015#\u0012\u0001S\u0001\t\u0007\",7m\u001b2pqB\u0011!)D\u0001\t\tJ|\u0007\u000fZ8x]B\u0011!\t\u0005\u0002\t\tJ|\u0007\u000fZ8x]N\u0019\u0001#I#\u0015\u0003=\u000bAbU5h]\u0006$XO]3C_b\u0004\"AQ\n\u0003\u0019MKwM\\1ukJ,'i\u001c=\u0014\u0007M\tS\tF\u0001U\u0003)1'o\\7TiJLgn\u001a\u000b\u00035v\u00032AI.F\u0013\ta6E\u0001\u0004PaRLwN\u001c\u0005\u0006=V\u0001\rAL\u0001\u0002gN\u0019Q\"I#\u0015\u00035\u000bQBR8s[\u001aKW\r\u001c3UsB,\u0007")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/FormFieldType.class */
public interface FormFieldType {
    static Option<FormFieldType> fromString(final String s) {
        return FormFieldType$.MODULE$.fromString(s);
    }

    static void $init$(final FormFieldType $this) {
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    default String toString() throws MatchError {
        if (FormFieldType$Text$.MODULE$.equals(this)) {
            return "text";
        }
        if (FormFieldType$Checkbox$.MODULE$.equals(this)) {
            return "checkbox";
        }
        if (FormFieldType$Radio$.MODULE$.equals(this)) {
            return "radio";
        }
        if (FormFieldType$Dropdown$.MODULE$.equals(this)) {
            return "dropdown";
        }
        if (FormFieldType$SignatureBox$.MODULE$.equals(this)) {
            return "signature";
        }
        throw new MatchError(this);
    }
}
