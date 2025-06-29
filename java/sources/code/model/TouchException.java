package code.model;

import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: exceptions.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005Mc\u0001\u0002\r\u001a\u0001zA\u0001\u0002\u000e\u0001\u0003\u0016\u0004%\t!\u000e\u0005\t}\u0001\u0011\t\u0012)A\u0005m!Aq\b\u0001BK\u0002\u0013\u0005\u0001\t\u0003\u0005E\u0001\tE\t\u0015!\u0003B\u0011\u0015)\u0005\u0001\"\u0001G\u0011\u001dY\u0005!!A\u0005\u00021Cqa\u0014\u0001\u0012\u0002\u0013\u0005\u0001\u000bC\u0004\\\u0001E\u0005I\u0011\u0001/\t\u000fy\u0003\u0011\u0011!C!?\"9q\rAA\u0001\n\u0003\u0001\u0005b\u00025\u0001\u0003\u0003%\t!\u001b\u0005\b_\u0002\t\t\u0011\"\u0011q\u0011\u001d9\b!!A\u0005\u0002aDq! \u0001\u0002\u0002\u0013\u0005c\u0010C\u0005\u0002\u0002\u0001\t\t\u0011\"\u0011\u0002\u0004!I\u0011Q\u0001\u0001\u0002\u0002\u0013\u0005\u0013qA\u0004\n\u0003\u0017I\u0012\u0011!E\u0001\u0003\u001b1\u0001\u0002G\r\u0002\u0002#\u0005\u0011q\u0002\u0005\u0007\u000bJ!\t!a\n\t\u0013\u0005%\"#!A\u0005F\u0005-\u0002\"CA\u0017%\u0005\u0005I\u0011QA\u0018\u0011%\t)DEA\u0001\n\u0003\u000b9\u0004C\u0005\u0002JI\t\t\u0011\"\u0003\u0002L\tqAk\\;dQ\u0016C8-\u001a9uS>t'B\u0001\u000e\u001c\u0003\u0015iw\u000eZ3m\u0015\u0005a\u0012\u0001B2pI\u0016\u001c\u0001a\u0005\u0003\u0001?5\n\u0004C\u0001\u0011+\u001d\t\tsE\u0004\u0002#K5\t1E\u0003\u0002%;\u00051AH]8pizJ\u0011AJ\u0001\u0006g\u000e\fG.Y\u0005\u0003Q%\nq\u0001]1dW\u0006<WMC\u0001'\u0013\tYCF\u0001\tSk:$\u0018.\\3Fq\u000e,\u0007\u000f^5p]*\u0011\u0001&\u000b\t\u0003]=j\u0011!K\u0005\u0003a%\u0012q\u0001\u0015:pIV\u001cG\u000f\u0005\u0002!e%\u00111\u0007\f\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.Z\u0001\u0004kJdW#\u0001\u001c\u0011\u0005]ZdB\u0001\u001d:!\t\u0011\u0013&\u0003\u0002;S\u00051\u0001K]3eK\u001aL!\u0001P\u001f\u0003\rM#(/\u001b8h\u0015\tQ\u0014&\u0001\u0003ve2\u0004\u0013AC:uCR,8oQ8eKV\t\u0011\t\u0005\u0002/\u0005&\u00111)\u000b\u0002\u0004\u0013:$\u0018aC:uCR,8oQ8eK\u0002\na\u0001P5oSRtDcA$J\u0015B\u0011\u0001\nA\u0007\u00023!)A'\u0002a\u0001m!)q(\u0002a\u0001\u0003\u0006!1m\u001c9z)\r9UJ\u0014\u0005\bi\u0019\u0001\n\u00111\u00017\u0011\u001dyd\u0001%AA\u0002\u0005\u000babY8qs\u0012\"WMZ1vYR$\u0013'F\u0001RU\t1$kK\u0001T!\t!\u0016,D\u0001V\u0015\t1v+A\u0005v]\u000eDWmY6fI*\u0011\u0001,K\u0001\u000bC:tw\u000e^1uS>t\u0017B\u0001.V\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00133+\u0005i&FA!S\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\t\u0001\r\u0005\u0002bM6\t!M\u0003\u0002dI\u0006!A.\u00198h\u0015\u0005)\u0017\u0001\u00026bm\u0006L!\u0001\u00102\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0011!.\u001c\t\u0003]-L!\u0001\\\u0015\u0003\u0007\u0005s\u0017\u0010C\u0004o\u0017\u0005\u0005\t\u0019A!\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\u0005\t\bc\u0001:vU6\t1O\u0003\u0002uS\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u0005Y\u001c(\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$\"!\u001f?\u0011\u00059R\u0018BA>*\u0005\u001d\u0011un\u001c7fC:DqA\\\u0007\u0002\u0002\u0003\u0007!.\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,GC\u00011��\u0011\u001dqg\"!AA\u0002\u0005\u000b\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002\u0003\u00061Q-];bYN$2!_A\u0005\u0011\u001dq\u0007#!AA\u0002)\fa\u0002V8vG\",\u0005pY3qi&|g\u000e\u0005\u0002I%M)!#!\u0005\u0002\u001eA9\u00111CA\rm\u0005;UBAA\u000b\u0015\r\t9\"K\u0001\beVtG/[7f\u0013\u0011\tY\"!\u0006\u0003#\u0005\u00137\u000f\u001e:bGR4UO\\2uS>t'\u0007\u0005\u0003\u0002 \u0005\u0015RBAA\u0011\u0015\r\t\u0019\u0003Z\u0001\u0003S>L1aMA\u0011)\t\ti!\u0001\u0005u_N#(/\u001b8h)\u0005\u0001\u0017!B1qa2LH#B$\u00022\u0005M\u0002\"\u0002\u001b\u0016\u0001\u00041\u0004\"B \u0016\u0001\u0004\t\u0015aB;oCB\u0004H.\u001f\u000b\u0005\u0003s\t)\u0005E\u0003/\u0003w\ty$C\u0002\u0002>%\u0012aa\u00149uS>t\u0007#\u0002\u0018\u0002BY\n\u0015bAA\"S\t1A+\u001e9mKJB\u0001\"a\u0012\u0017\u0003\u0003\u0005\raR\u0001\u0004q\u0012\u0002\u0014\u0001D<sSR,'+\u001a9mC\u000e,GCAA'!\r\t\u0017qJ\u0005\u0004\u0003#\u0012'AB(cU\u0016\u001cG\u000f")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/model/TouchException.class */
public class TouchException extends RuntimeException implements Product {
    private final String url;
    private final int statusCode;

    public static Option<Tuple2<String, Object>> unapply(final TouchException x$0) {
        return TouchException$.MODULE$.unapply(x$0);
    }

    public static TouchException apply(final String url, final int statusCode) {
        return TouchException$.MODULE$.apply(url, statusCode);
    }

    public static Function1<Tuple2<String, Object>, TouchException> tupled() {
        return TouchException$.MODULE$.tupled();
    }

    public static Function1<String, Function1<Object, TouchException>> curried() {
        return TouchException$.MODULE$.curried();
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String url() {
        return this.url;
    }

    public int statusCode() {
        return this.statusCode;
    }

    public TouchException copy(final String url, final int statusCode) {
        return new TouchException(url, statusCode);
    }

    public String copy$default$1() {
        return url();
    }

    public int copy$default$2() {
        return statusCode();
    }

    public String productPrefix() {
        return "TouchException";
    }

    public int productArity() {
        return 2;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return url();
            case 1:
                return BoxesRunTime.boxToInteger(statusCode());
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof TouchException;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "url";
            case 1:
                return "statusCode";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return Statics.finalizeHash(Statics.mix(Statics.mix(Statics.mix(-889275714, productPrefix().hashCode()), Statics.anyHash(url())), statusCode()), 2);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof TouchException) {
                TouchException touchException = (TouchException) x$1;
                if (statusCode() == touchException.statusCode()) {
                    String strUrl = url();
                    String strUrl2 = touchException.url();
                    if (strUrl != null ? strUrl.equals(strUrl2) : strUrl2 == null) {
                        if (touchException.canEqual(this)) {
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TouchException(final String url, final int statusCode) {
        super(new StringBuilder(37).append("Failed to touch url: ").append(url).append(" (status code: ").append(statusCode).append(")").toString());
        this.url = url;
        this.statusCode = statusCode;
        Product.$init$(this);
    }
}
