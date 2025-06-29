package com.sejda.pdf2html.util;

import java.io.Serializable;
import scala.Array$;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Product;
import scala.Some;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.math.package$;
import scala.reflect.ClassTag$;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: LevensteinMetric.scala */
/* loaded from: com.sejda.pdf2html.pdf2html-0.0.72.jar:com/sejda/pdf2html/util/LevenshteinMetric$.class */
public final class LevenshteinMetric$ implements Product, Serializable {
    public static final LevenshteinMetric$ MODULE$ = new LevenshteinMetric$();
    private static final Function1<Tuple2<char[], char[]>, Object> levenshtein;

    static {
        Product.$init$(MODULE$);
        levenshtein = ct -> {
            return BoxesRunTime.boxToInteger($anonfun$levenshtein$1(ct));
        };
    }

    public String productElementName(final int n) {
        return Product.productElementName$(this, n);
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String productPrefix() {
        return "LevenshteinMetric";
    }

    public int productArity() {
        return 0;
    }

    public Object productElement(final int x$1) {
        return Statics.ioobe(x$1);
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof LevenshteinMetric$;
    }

    public int hashCode() {
        return -161777887;
    }

    public String toString() {
        return "LevenshteinMetric";
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(LevenshteinMetric$.class);
    }

    private LevenshteinMetric$() {
    }

    public char[] stringToCharArray(final String s) {
        return s.toCharArray();
    }

    public Option<Object> compare(final char[] a, final char[] b) {
        return (a.length == 0 || b.length == 0) ? None$.MODULE$ : Predef$.MODULE$.wrapCharArray(a).sameElements(Predef$.MODULE$.wrapCharArray(b)) ? new Some(BoxesRunTime.boxToInteger(0)) : new Some(levenshtein().apply(new Tuple2(a, b)));
    }

    public Option<Object> compare(final String a, final String b) {
        return compare(a.toCharArray(), b.toCharArray());
    }

    private Function1<Tuple2<char[], char[]>, Object> levenshtein() {
        return levenshtein;
    }

    public static final /* synthetic */ int $anonfun$levenshtein$1(final Tuple2 ct) {
        int[][] m = (int[][]) Array$.MODULE$.fill(((char[]) ct._1()).length + 1, ((char[]) ct._2()).length + 1, () -> {
            return -1;
        }, ClassTag$.MODULE$.Int());
        return distance$1(new Tuple2.mcII.sp(((char[]) ct._1()).length, ((char[]) ct._2()).length), m, ct);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    private static final int distance$1(final Tuple2 t, final int[][] m$1, final Tuple2 ct$1) throws MatchError {
        if (t != null) {
            int r = t._1$mcI$sp();
            if (0 == t._2$mcI$sp()) {
                return r;
            }
        }
        if (t != null) {
            int i_1$mcI$sp = t._1$mcI$sp();
            int c = t._2$mcI$sp();
            if (0 == i_1$mcI$sp) {
                return c;
            }
        }
        if (t != null) {
            int r2 = t._1$mcI$sp();
            int c2 = t._2$mcI$sp();
            if (m$1[r2][c2] != -1) {
                return m$1[r2][c2];
            }
        }
        if (t != null) {
            int r3 = t._1$mcI$sp();
            int c3 = t._2$mcI$sp();
            int min = ((char[]) ct$1._1())[r3 - 1] == ((char[]) ct$1._2())[c3 - 1] ? distance$1(new Tuple2.mcII.sp(r3 - 1, c3 - 1), m$1, ct$1) : package$.MODULE$.min(package$.MODULE$.min(distance$1(new Tuple2.mcII.sp(r3 - 1, c3), m$1, ct$1) + 1, distance$1(new Tuple2.mcII.sp(r3, c3 - 1), m$1, ct$1) + 1), distance$1(new Tuple2.mcII.sp(r3 - 1, c3 - 1), m$1, ct$1) + 1);
            m$1[r3][c3] = min;
            return min;
        }
        throw new MatchError(t);
    }
}
