package code.util.encryption;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: FileEncryption.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/encryption/KeyEncryption$.class */
public final class KeyEncryption$ extends AbstractFunction1<String, KeyEncryption> implements Serializable {
    public static final KeyEncryption$ MODULE$ = new KeyEncryption$();

    public final String toString() {
        return "KeyEncryption";
    }

    public KeyEncryption apply(final String key) {
        return new KeyEncryption(key);
    }

    public Option<String> unapply(final KeyEncryption x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(x$0.key());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(KeyEncryption$.class);
    }

    private KeyEncryption$() {
    }
}
