package com.sejda.pdf2html.sambox;

import com.sejda.pdf2html.LocalPDFTextStripper;
import java.io.Serializable;
import java.util.List;
import org.sejda.sambox.text.TextPosition;
import scala.Option;
import scala.Product;
import scala.collection.IterableOnceOps;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.JavaConverters$;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.Try$;

/* compiled from: HeaderFooterRemover.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005Ee\u0001B\u000f\u001f\u0001\u001eB\u0001\"\u0010\u0001\u0003\u0016\u0004%\tA\u0010\u0005\t#\u0002\u0011\t\u0012)A\u0005\u007f!)!\u000b\u0001C\u0001'\"9q\u000b\u0001b\u0001\n\u0003A\u0006B\u0002/\u0001A\u0003%\u0011\fC\u0004^\u0001\t\u0007I\u0011\u00010\t\r\u0015\u0004\u0001\u0015!\u0003`\u0011\u00151\u0007\u0001\"\u0001h\u0011\u0015Q\u0007\u0001\"\u0001l\u0011\u0015q\u0007\u0001\"\u0001p\u0011\u001dA\b!!A\u0005\u0002eDqa\u001f\u0001\u0012\u0002\u0013\u0005A\u0010C\u0005\u0002\u0010\u0001\t\t\u0011\"\u0011\u0002\u0012!I\u0011Q\u0004\u0001\u0002\u0002\u0013\u0005\u0011q\u0004\u0005\n\u0003O\u0001\u0011\u0011!C\u0001\u0003SA\u0011\"!\u000e\u0001\u0003\u0003%\t%a\u000e\t\u0013\u0005\u0015\u0003!!A\u0005\u0002\u0005\u001d\u0003\"CA&\u0001\u0005\u0005I\u0011IA'\u0011%\t\t\u0006AA\u0001\n\u0003\n\u0019\u0006C\u0005\u0002V\u0001\t\t\u0011\"\u0011\u0002X!I\u0011\u0011\f\u0001\u0002\u0002\u0013\u0005\u00131L\u0004\b\u0003?r\u0002\u0012AA1\r\u0019ib\u0004#\u0001\u0002d!1!k\u0006C\u0001\u0003_Bq!!\u001d\u0018\t\u0007\t\u0019\bC\u0005\u0002x]\t\t\u0011\"!\u0002z!I\u0011QP\f\u0002\u0002\u0013\u0005\u0015q\u0010\u0005\n\u0003\u000f;\u0012\u0011!C\u0005\u0003\u0013\u0013q\u0001\u00153g\u0019&tWM\u0003\u0002 A\u000511/Y7c_bT!!\t\u0012\u0002\u0011A$gM\r5u[2T!a\t\u0013\u0002\u000bM,'\u000eZ1\u000b\u0003\u0015\n1aY8n\u0007\u0001\u0019B\u0001\u0001\u0015/cA\u0011\u0011\u0006L\u0007\u0002U)\t1&A\u0003tG\u0006d\u0017-\u0003\u0002.U\t1\u0011I\\=SK\u001a\u0004\"!K\u0018\n\u0005AR#a\u0002)s_\u0012,8\r\u001e\t\u0003eir!a\r\u001d\u000f\u0005Q:T\"A\u001b\u000b\u0005Y2\u0013A\u0002\u001fs_>$h(C\u0001,\u0013\tI$&A\u0004qC\u000e\\\u0017mZ3\n\u0005mb$\u0001D*fe&\fG.\u001b>bE2,'BA\u001d+\u0003\u001da\u0017N\\3TKF,\u0012a\u0010\t\u0004\u0001\u0016;U\"A!\u000b\u0005\t\u001b\u0015\u0001B;uS2T\u0011\u0001R\u0001\u0005U\u00064\u0018-\u0003\u0002G\u0003\n!A*[:u!\tAu*D\u0001J\u0015\tQ5*\u0001\u0003uKb$(BA\u0010M\u0015\t\u0019SJC\u0001O\u0003\ry'oZ\u0005\u0003!&\u0013A\u0002V3yiB{7/\u001b;j_:\f\u0001\u0002\\5oKN+\u0017\u000fI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005Q3\u0006CA+\u0001\u001b\u0005q\u0002\"B\u001f\u0004\u0001\u0004y\u0014!B3naRLX#A-\u0011\u0005%R\u0016BA.+\u0005\u001d\u0011un\u001c7fC:\fa!Z7qif\u0004\u0013!\u00038vY2\u001c\u0016MZ3Z+\u0005y\u0006cA\u0015aE&\u0011\u0011M\u000b\u0002\u0007\u001fB$\u0018n\u001c8\u0011\u0005%\u001a\u0017B\u00013+\u0005\u00151En\\1u\u0003)qW\u000f\u001c7TC\u001a,\u0017\fI\u0001\rS:DU-\u00193fe\u0006\u0013X-\u0019\u000b\u00033\"DQ!\u001b\u0005A\u0002\t\fq\u0001[3bI\u0016\u0014\u0018,\u0001\u0007j]\u001a{w\u000e^3s\u0003J,\u0017\r\u0006\u0002ZY\")Q.\u0003a\u0001E\u00069am\\8uKJL\u0016\u0001C1t'R\u0014\u0018N\\4\u0016\u0003A\u0004\"!];\u000f\u0005I\u001c\bC\u0001\u001b+\u0013\t!(&\u0001\u0004Qe\u0016$WMZ\u0005\u0003m^\u0014aa\u0015;sS:<'B\u0001;+\u0003\u0011\u0019w\u000e]=\u0015\u0005QS\bbB\u001f\f!\u0003\u0005\raP\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\u0005i(FA \u007fW\u0005y\b\u0003BA\u0001\u0003\u0017i!!a\u0001\u000b\t\u0005\u0015\u0011qA\u0001\nk:\u001c\u0007.Z2lK\u0012T1!!\u0003+\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003\u001b\t\u0019AA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DXCAA\n!\u0011\t)\"a\u0007\u000e\u0005\u0005]!bAA\r\u0007\u0006!A.\u00198h\u0013\r1\u0018qC\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0003C\u00012!KA\u0012\u0013\r\t)C\u000b\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0005\u0003W\t\t\u0004E\u0002*\u0003[I1!a\f+\u0005\r\te.\u001f\u0005\n\u0003gy\u0011\u0011!a\u0001\u0003C\t1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XCAA\u001d!\u0019\tY$!\u0011\u0002,5\u0011\u0011Q\b\u0006\u0004\u0003\u007fQ\u0013AC2pY2,7\r^5p]&!\u00111IA\u001f\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\u0007e\u000bI\u0005C\u0005\u00024E\t\t\u00111\u0001\u0002,\u0005\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\u0011\t\u0019\"a\u0014\t\u0013\u0005M\"#!AA\u0002\u0005\u0005\u0012\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0005\u0005\u0005\u0012\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\u0005M\u0011AB3rk\u0006d7\u000fF\u0002Z\u0003;B\u0011\"a\r\u0016\u0003\u0003\u0005\r!a\u000b\u0002\u000fA#g\rT5oKB\u0011QkF\n\u0005/!\n)\u0007\u0005\u0003\u0002h\u00055TBAA5\u0015\r\tYgQ\u0001\u0003S>L1aOA5)\t\t\t'A\u0007mS:,Gk\u001c)eM2Kg.\u001a\u000b\u0004)\u0006U\u0004\"B\u001f\u001a\u0001\u0004y\u0014!B1qa2LHc\u0001+\u0002|!)QH\u0007a\u0001\u007f\u00059QO\\1qa2LH\u0003BAA\u0003\u0007\u00032!\u000b1@\u0011!\t)iGA\u0001\u0002\u0004!\u0016a\u0001=%a\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u00111\u0012\t\u0005\u0003+\ti)\u0003\u0003\u0002\u0010\u0006]!AB(cU\u0016\u001cG\u000f")
/* loaded from: com.sejda.pdf2html.pdf2html-0.0.72.jar:com/sejda/pdf2html/sambox/PdfLine.class */
public class PdfLine implements Product, Serializable {
    private final List<TextPosition> lineSeq;
    private final boolean empty;
    private final Option<Object> nullSafeY;

    public static Option<List<TextPosition>> unapply(final PdfLine x$0) {
        return PdfLine$.MODULE$.unapply(x$0);
    }

    public static PdfLine apply(final List<TextPosition> lineSeq) {
        return PdfLine$.MODULE$.apply(lineSeq);
    }

    public static PdfLine lineToPdfLine(final List<TextPosition> lineSeq) {
        return PdfLine$.MODULE$.lineToPdfLine(lineSeq);
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public List<TextPosition> lineSeq() {
        return this.lineSeq;
    }

    public PdfLine copy(final List<TextPosition> lineSeq) {
        return new PdfLine(lineSeq);
    }

    public List<TextPosition> copy$default$1() {
        return lineSeq();
    }

    public String productPrefix() {
        return "PdfLine";
    }

    public int productArity() {
        return 1;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return lineSeq();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof PdfLine;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "lineSeq";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof PdfLine) {
                PdfLine pdfLine = (PdfLine) x$1;
                List<TextPosition> listLineSeq = lineSeq();
                List<TextPosition> listLineSeq2 = pdfLine.lineSeq();
                if (listLineSeq != null ? listLineSeq.equals(listLineSeq2) : listLineSeq2 == null) {
                    if (pdfLine.canEqual(this)) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    public PdfLine(final List<TextPosition> lineSeq) {
        this.lineSeq = lineSeq;
        Product.$init$(this);
        this.empty = lineSeq == null || lineSeq.size() == 0;
        this.nullSafeY = Try$.MODULE$.apply(() -> {
            return this.lineSeq().get(0).getY();
        }).toOption();
    }

    public boolean empty() {
        return this.empty;
    }

    public Option<Object> nullSafeY() {
        return this.nullSafeY;
    }

    public boolean inHeaderArea(final float headerY) {
        return nullSafeY().exists(x$2 -> {
            return x$2 > headerY;
        });
    }

    public boolean inFooterArea(final float footerY) {
        return nullSafeY().exists(x$3 -> {
            return x$3 < footerY;
        });
    }

    public String asString() {
        return ((IterableOnceOps) ((IterableOps) JavaConverters$.MODULE$.asScalaBufferConverter(lineSeq()).asScala()).map(x0$1 -> {
            return x0$1 instanceof LocalPDFTextStripper.WordSeparator ? " " : x0$1.getUnicode();
        })).mkString("");
    }
}
