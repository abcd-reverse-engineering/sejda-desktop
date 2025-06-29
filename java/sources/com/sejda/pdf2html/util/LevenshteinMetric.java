package com.sejda.pdf2html.util;

import scala.Option;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;

/* compiled from: LevensteinMetric.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005=s!\u0002\u000b\u0016\u0011\u0003sb!\u0002\u0011\u0016\u0011\u0003\u000b\u0003\"B\u001c\u0002\t\u0003AT\u0001B\u001d\u0002\u0001i*A\u0001T\u0001\u0001\u001b\u0016!!+\u0001\u0001T\u000b\u0011)\u0016\u0001\u0001,\t\u000b\u0005\fA1\u00012\t\u000b5\fA\u0011\u00018\t\u000b5\fA\u0011A=\t\u000fq\f!\u0019!C\u0005{\"9\u0011\u0011A\u0001!\u0002\u0013q\b\"CA\u0002\u0003\u0005\u0005I\u0011IA\u0003\u0011%\t)\"AA\u0001\n\u0003\t9\u0002C\u0005\u0002\u001a\u0005\t\t\u0011\"\u0001\u0002\u001c!I\u0011\u0011E\u0001\u0002\u0002\u0013\u0005\u00131\u0005\u0005\n\u0003c\t\u0011\u0011!C\u0001\u0003gA\u0011\"!\u0010\u0002\u0003\u0003%\t%a\u0010\t\u0013\u0005\u0005\u0013!!A\u0005B\u0005\r\u0003\"CA#\u0003\u0005\u0005I\u0011BA$\u0003EaUM^3og\"$X-\u001b8NKR\u0014\u0018n\u0019\u0006\u0003-]\tA!\u001e;jY*\u0011\u0001$G\u0001\ta\u00124'\u0007\u001b;nY*\u0011!dG\u0001\u0006g\u0016TG-\u0019\u0006\u00029\u0005\u00191m\\7\u0004\u0001A\u0011q$A\u0007\u0002+\t\tB*\u001a<f]NDG/Z5o\u001b\u0016$(/[2\u0014\t\u0005\u0011\u0003f\u000b\t\u0003G\u0019j\u0011\u0001\n\u0006\u0002K\u0005)1oY1mC&\u0011q\u0005\n\u0002\u0007\u0003:L(+\u001a4\u0011\u0005\rJ\u0013B\u0001\u0016%\u0005\u001d\u0001&o\u001c3vGR\u0004\"\u0001\f\u001b\u000f\u00055\u0012dB\u0001\u00182\u001b\u0005y#B\u0001\u0019\u001e\u0003\u0019a$o\\8u}%\tQ%\u0003\u00024I\u00059\u0001/Y2lC\u001e,\u0017BA\u001b7\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\t\u0019D%\u0001\u0004=S:LGO\u0010\u000b\u0002=\ta1i\\7qCJ,G+\u001e9mKV\u00111h\u0011\t\u0005Gqrd(\u0003\u0002>I\t1A+\u001e9mKJ\u00022aI B\u0013\t\u0001EEA\u0003BeJ\f\u0017\u0010\u0005\u0002C\u00072\u0001A!\u0002#\u0004\u0005\u0004)%!\u0001+\u0012\u0005\u0019K\u0005CA\u0012H\u0013\tAEEA\u0004O_RD\u0017N\\4\u0011\u0005\rR\u0015BA&%\u0005\r\te.\u001f\u0002\u000b\u001b\u0006$8\r\u001b+va2,WC\u0001(R!\u0011\u0019ChT(\u0011\u0007\rz\u0004\u000b\u0005\u0002C#\u0012)A\t\u0002b\u0001\u000b\ny1\u000b\u001e:j]\u001e$&/\u00198tM>\u0014X\u000eE\u0002U\ruk\u0011!\u0001\u0002\n)J\fgn\u001d4pe6,\"aV.\u0011\t\rB&LW\u0005\u00033\u0012\u0012\u0011BR;oGRLwN\\\u0019\u0011\u0005\t[F!\u0002/\u0007\u0005\u0004)%!A!\u0011\u0007\rzd\f\u0005\u0002$?&\u0011\u0001\r\n\u0002\u0005\u0007\"\f'/A\ttiJLgn\u001a+p\u0007\"\f'/\u0011:sCf$\"!X2\t\u000b\u0011<\u0001\u0019A3\u0002\u0003M\u0004\"A\u001a6\u000f\u0005\u001dD\u0007C\u0001\u0018%\u0013\tIG%\u0001\u0004Qe\u0016$WMZ\u0005\u0003W2\u0014aa\u0015;sS:<'BA5%\u0003\u001d\u0019w.\u001c9be\u0016$2a\\;x!\r\u0019\u0003O]\u0005\u0003c\u0012\u0012aa\u00149uS>t\u0007CA\u0012t\u0013\t!HEA\u0002J]RDQA\u001e\u0005A\u0002u\u000b\u0011!\u0019\u0005\u0006q\"\u0001\r!X\u0001\u0002ER\u0019qN_>\t\u000bYL\u0001\u0019A3\t\u000baL\u0001\u0019A3\u0002\u00171,g/\u001a8tQR,\u0017N\\\u000b\u0002}B!1\u0005W@s!\r!6AX\u0001\rY\u00164XM\\:ii\u0016Lg\u000eI\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\u0005\u001d\u0001\u0003BA\u0005\u0003'i!!a\u0003\u000b\t\u00055\u0011qB\u0001\u0005Y\u0006twM\u0003\u0002\u0002\u0012\u0005!!.\u0019<b\u0013\rY\u00171B\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0002e\u0006q\u0001O]8ek\u000e$X\t\\3nK:$HcA%\u0002\u001e!A\u0011q\u0004\b\u0002\u0002\u0003\u0007!/A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0003\u0003K\u0001R!a\n\u0002.%k!!!\u000b\u000b\u0007\u0005-B%\u0001\u0006d_2dWm\u0019;j_:LA!a\f\u0002*\tA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\u0011\t)$a\u000f\u0011\u0007\r\n9$C\u0002\u0002:\u0011\u0012qAQ8pY\u0016\fg\u000e\u0003\u0005\u0002 A\t\t\u00111\u0001J\u0003!A\u0017m\u001d5D_\u0012,G#\u0001:\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!a\u0002\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\u0005%\u0003\u0003BA\u0005\u0003\u0017JA!!\u0014\u0002\f\t1qJ\u00196fGR\u0004")
/* loaded from: com.sejda.pdf2html.pdf2html-0.0.72.jar:com/sejda/pdf2html/util/LevenshteinMetric.class */
public final class LevenshteinMetric {
    public static String toString() {
        return LevenshteinMetric$.MODULE$.toString();
    }

    public static int hashCode() {
        return LevenshteinMetric$.MODULE$.hashCode();
    }

    public static boolean canEqual(final Object x$1) {
        return LevenshteinMetric$.MODULE$.canEqual(x$1);
    }

    public static Iterator<Object> productIterator() {
        return LevenshteinMetric$.MODULE$.productIterator();
    }

    public static Object productElement(final int x$1) {
        return LevenshteinMetric$.MODULE$.productElement(x$1);
    }

    public static int productArity() {
        return LevenshteinMetric$.MODULE$.productArity();
    }

    public static String productPrefix() {
        return LevenshteinMetric$.MODULE$.productPrefix();
    }

    public static Option<Object> compare(final String a, final String b) {
        return LevenshteinMetric$.MODULE$.compare(a, b);
    }

    public static Option<Object> compare(final char[] a, final char[] b) {
        return LevenshteinMetric$.MODULE$.compare(a, b);
    }

    public static char[] stringToCharArray(final String s) {
        return LevenshteinMetric$.MODULE$.stringToCharArray(s);
    }

    public static Iterator<String> productElementNames() {
        return LevenshteinMetric$.MODULE$.productElementNames();
    }

    public static String productElementName(final int n) {
        return LevenshteinMetric$.MODULE$.productElementName(n);
    }
}
