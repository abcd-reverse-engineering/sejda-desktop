package code.util;

import java.util.List;
import java.util.Map;
import scala.$less$colon$less$;
import scala.collection.Iterable;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Buffer;
import scala.jdk.CollectionConverters$;

/* compiled from: ImplicitJavaConversions.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/ImplicitJavaConversions$.class */
public final class ImplicitJavaConversions$ {
    public static final ImplicitJavaConversions$ MODULE$ = new ImplicitJavaConversions$();

    private ImplicitJavaConversions$() {
    }

    public <K, V> Map<K, V> scalaMapToJavaMap(final scala.collection.immutable.Map<K, V> scalaMap) {
        return CollectionConverters$.MODULE$.MapHasAsJava(scalaMap).asJava();
    }

    public <K> List<K> scalaSeqToJavaList(final Seq<K> scalaSeq) {
        return CollectionConverters$.MODULE$.SeqHasAsJava(scalaSeq).asJava();
    }

    public <K, V> scala.collection.immutable.Map<K, V> javaMapToScalaMap(final Map<K, V> javaMap) {
        return CollectionConverters$.MODULE$.MapHasAsScala(javaMap).asScala().toMap($less$colon$less$.MODULE$.refl());
    }

    public <T> Seq<T> javaListToScalaSeq(final List<T> javaList) {
        return CollectionConverters$.MODULE$.ListHasAsScala(javaList).asScala().toSeq();
    }

    public <T> Iterable<T> javaIterableToScalaIterable(final Iterable<T> javaIterable) {
        return CollectionConverters$.MODULE$.IterableHasAsScala(javaIterable).asScala();
    }

    public <T> Seq<T> listBufferToImmutableSeq(final Buffer<T> buffer) {
        return buffer.toSeq();
    }
}
