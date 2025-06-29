package code.sejda.tasks.deskew;

import code.util.Loggable;
import code.util.imageio.ImageIOUtils$;
import java.awt.image.BufferedImage;
import org.slf4j.Logger;
import scala.Array$;
import scala.Predef$;
import scala.collection.ArrayOps$;
import scala.math.Ordering$DeprecatedDoubleOrdering$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.DoubleRef;
import scala.runtime.IntRef;
import scala.runtime.ObjectRef;
import scala.runtime.RichInt$;

/* compiled from: ImageDeskew.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005Ue\u0001B\u001b7\u0001}B\u0001\u0002\u0014\u0001\u0003\u0002\u0003\u0006I!\u0014\u0005\u0006-\u0002!\ta\u0016\u0004\u00057\u0002\u0001A\fC\u0003W\u0007\u0011\u0005Q\fC\u0004a\u0007\u0001\u0007I\u0011A1\t\u000f\u0015\u001c\u0001\u0019!C\u0001M\"1An\u0001Q!\n\tDq!\\\u0002A\u0002\u0013\u0005\u0011\rC\u0004o\u0007\u0001\u0007I\u0011A8\t\rE\u001c\u0001\u0015)\u0003c\u0011\u001d\u00118\u00011A\u0005\u0002MDqa^\u0002A\u0002\u0013\u0005\u0001\u0010\u0003\u0004{\u0007\u0001\u0006K\u0001\u001e\u0005\bw\u000e\u0001\r\u0011\"\u0001t\u0011\u001da8\u00011A\u0005\u0002uDaa`\u0002!B\u0013!\b\"CA\u0001\u0001\u0001\u0007I\u0011BA\u0002\u0011%\t)\u0001\u0001a\u0001\n\u0013\t9\u0001C\u0004\u0002\f\u0001\u0001\u000b\u0015B'\t\u0011\u00055\u0001\u00011A\u0005\nMD\u0011\"a\u0004\u0001\u0001\u0004%I!!\u0005\t\u000f\u0005U\u0001\u0001)Q\u0005i\"A\u0011q\u0003\u0001A\u0002\u0013%1\u000fC\u0005\u0002\u001a\u0001\u0001\r\u0011\"\u0003\u0002\u001c!9\u0011q\u0004\u0001!B\u0013!\b\u0002CA\u0011\u0001\u0001\u0007I\u0011B1\t\u0013\u0005\r\u0002\u00011A\u0005\n\u0005\u0015\u0002bBA\u0015\u0001\u0001\u0006KA\u0019\u0005\f\u0003W\u0001\u0001\u0019!a\u0001\n\u0013\ti\u0003C\u0006\u00026\u0001\u0001\r\u00111A\u0005\n\u0005]\u0002bCA\u001e\u0001\u0001\u0007\t\u0011)Q\u0005\u0003_A1\"!\u0010\u0001\u0001\u0004\u0005\r\u0011\"\u0003\u0002.!Y\u0011q\b\u0001A\u0002\u0003\u0007I\u0011BA!\u0011-\t)\u0005\u0001a\u0001\u0002\u0003\u0006K!a\f\t\u0015\u0005\u001d\u0003\u00011AA\u0002\u0013%1\u000fC\u0006\u0002J\u0001\u0001\r\u00111A\u0005\n\u0005-\u0003BCA(\u0001\u0001\u0007\t\u0011)Q\u0005i\"A\u0011\u0011\u000b\u0001A\u0002\u0013%1\u000fC\u0005\u0002T\u0001\u0001\r\u0011\"\u0003\u0002V!9\u0011\u0011\f\u0001!B\u0013!\bBCA.\u0001\u0001\u0007\t\u0019!C\u0005C\"Y\u0011Q\f\u0001A\u0002\u0003\u0007I\u0011BA0\u0011)\t\u0019\u0007\u0001a\u0001\u0002\u0003\u0006KA\u0019\u0005\f\u0003K\u0002\u0001\u0019!a\u0001\n\u0013\t9\u0007C\u0006\u0002l\u0001\u0001\r\u00111A\u0005\n\u00055\u0004bCA9\u0001\u0001\u0007\t\u0011)Q\u0005\u0003SBq!a\u001d\u0001\t\u0003\t)\bC\u0004\u0002x\u0001!I!!\u001f\t\u000f\u0005}\u0004\u0001\"\u0003\u0002\u0002\"9\u0011q\u0010\u0001\u0005\n\u0005\r\u0005bBAG\u0001\u0011%\u0011\u0011\u0011\u0005\b\u0003\u001f\u0003A\u0011AAI\u0005-IU.Y4f\t\u0016\u001c8.Z<\u000b\u0005]B\u0014A\u00023fg.,wO\u0003\u0002:u\u0005)A/Y:lg*\u00111\bP\u0001\u0006g\u0016TG-\u0019\u0006\u0002{\u0005!1m\u001c3f\u0007\u0001\u00192\u0001\u0001!G!\t\tE)D\u0001C\u0015\u0005\u0019\u0015!B:dC2\f\u0017BA#C\u0005\u0019\te.\u001f*fMB\u0011qIS\u0007\u0002\u0011*\u0011\u0011\nP\u0001\u0005kRLG.\u0003\u0002L\u0011\nAAj\\4hC\ndW-A\u0003j[\u0006<W\r\u0005\u0002O)6\tqJ\u0003\u0002M!*\u0011\u0011KU\u0001\u0004C^$(\"A*\u0002\t)\fg/Y\u0005\u0003+>\u0013QBQ;gM\u0016\u0014X\rZ%nC\u001e,\u0017A\u0002\u001fj]&$h\b\u0006\u0002Y5B\u0011\u0011\fA\u0007\u0002m!)AJ\u0001a\u0001\u001b\nI\u0001j\\;hQ2Kg.Z\n\u0003\u0007\u0001#\u0012A\u0018\t\u0003?\u000ei\u0011\u0001A\u0001\u0006G>,h\u000e^\u000b\u0002EB\u0011\u0011iY\u0005\u0003I\n\u00131!\u00138u\u0003%\u0019w.\u001e8u?\u0012*\u0017\u000f\u0006\u0002hUB\u0011\u0011\t[\u0005\u0003S\n\u0013A!\u00168ji\"91NBA\u0001\u0002\u0004\u0011\u0017a\u0001=%c\u000511m\\;oi\u0002\nQ!\u001b8eKb\f\u0011\"\u001b8eKb|F%Z9\u0015\u0005\u001d\u0004\bbB6\n\u0003\u0003\u0005\rAY\u0001\u0007S:$W\r\u001f\u0011\u0002\u000b\u0005d\u0007\u000f[1\u0016\u0003Q\u0004\"!Q;\n\u0005Y\u0014%A\u0002#pk\ndW-A\u0005bYBD\u0017m\u0018\u0013fcR\u0011q-\u001f\u0005\bW2\t\t\u00111\u0001u\u0003\u0019\tG\u000e\u001d5bA\u0005\tA-A\u0003e?\u0012*\u0017\u000f\u0006\u0002h}\"91nDA\u0001\u0002\u0004!\u0018A\u00013!\u0003\u0019\u0019\u0017*\\1hKV\tQ*\u0001\u0006d\u00136\fw-Z0%KF$2aZA\u0005\u0011\u001dY'#!AA\u00025\u000bqaY%nC\u001e,\u0007%A\u0006d\u00032\u0004\b.Y*uCJ$\u0018aD2BYBD\u0017m\u0015;beR|F%Z9\u0015\u0007\u001d\f\u0019\u0002C\u0004l+\u0005\u0005\t\u0019\u0001;\u0002\u0019\r\fE\u000e\u001d5b'R\f'\u000f\u001e\u0011\u0002\u0015\r\fE\u000e\u001d5b'R,\u0007/\u0001\bd\u00032\u0004\b.Y*uKB|F%Z9\u0015\u0007\u001d\fi\u0002C\u0004l1\u0005\u0005\t\u0019\u0001;\u0002\u0017\r\fE\u000e\u001d5b'R,\u0007\u000fI\u0001\u0007GN#X\r]:\u0002\u0015\r\u001cF/\u001a9t?\u0012*\u0017\u000fF\u0002h\u0003OAqa[\u000e\u0002\u0002\u0003\u0007!-A\u0004d'R,\u0007o\u001d\u0011\u0002\u000b\r\u001c\u0016N\\!\u0016\u0005\u0005=\u0002\u0003B!\u00022QL1!a\rC\u0005\u0015\t%O]1z\u0003%\u00197+\u001b8B?\u0012*\u0017\u000fF\u0002h\u0003sA\u0001b\u001b\u0010\u0002\u0002\u0003\u0007\u0011qF\u0001\u0007GNKg.\u0011\u0011\u0002\u000b\r\u001cun]!\u0002\u0013\r\u001cun]!`I\u0015\fHcA4\u0002D!A1.IA\u0001\u0002\u0004\ty#\u0001\u0004d\u0007>\u001c\u0018\tI\u0001\u0006G\u0012k\u0015N\\\u0001\nG\u0012k\u0015N\\0%KF$2aZA'\u0011\u001dYG%!AA\u0002Q\faa\u0019#NS:\u0004\u0013AB2E'R,\u0007/\u0001\u0006d\tN#X\r]0%KF$2aZA,\u0011\u001dYw%!AA\u0002Q\fqa\u0019#Ti\u0016\u0004\b%A\u0004d\t\u000e{WO\u001c;\u0002\u0017\r$5i\\;oi~#S-\u001d\u000b\u0004O\u0006\u0005\u0004bB6+\u0003\u0003\u0005\rAY\u0001\tG\u0012\u001bu.\u001e8uA\u0005A1\rS'biJL\u00070\u0006\u0002\u0002jA!\u0011)!\rc\u00031\u0019\u0007*T1ue&Dx\fJ3r)\r9\u0017q\u000e\u0005\tW6\n\t\u00111\u0001\u0002j\u0005I1\rS'biJL\u0007\u0010I\u0001\rO\u0016$8k[3x\u0003:<G.\u001a\u000b\u0002i\u00061q-\u001a;U_B$B!a\u001f\u0002~A!\u0011)!\r_\u0011\u0015\u0001\u0007\u00071\u0001c\u0003\u0011\u0019\u0017\r\\2\u0015\u0003\u001d$RaZAC\u0003\u0013Ca!a\"3\u0001\u0004\u0011\u0017!\u0001=\t\r\u0005-%\u00071\u0001c\u0003\u0005I\u0018\u0001B5oSR\f\u0001bZ3u\u00032\u0004\b.\u0019\u000b\u0004i\u0006M\u0005\"B75\u0001\u0004\u0011\u0007")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/deskew/ImageDeskew.class */
public class ImageDeskew implements Loggable {
    private BufferedImage cImage;
    private double cAlphaStart;
    private double cAlphaStep;
    private int cSteps;
    private double[] cSinA;
    private double[] cCosA;
    private double cDMin;
    private double cDStep;
    private int cDCount;
    private int[] cHMatrix;
    private transient Logger logger;
    private volatile transient boolean bitmap$trans$0;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v8, types: [code.sejda.tasks.deskew.ImageDeskew] */
    private Logger logger$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (!this.bitmap$trans$0) {
                this.logger = logger();
                r0 = this;
                r0.bitmap$trans$0 = true;
            }
        }
        return this.logger;
    }

    @Override // code.util.Loggable
    public Logger logger() {
        return !this.bitmap$trans$0 ? logger$lzycompute() : this.logger;
    }

    public ImageDeskew(final BufferedImage image) {
        Loggable.$init$(this);
        this.cImage = image;
        this.cAlphaStart = -20.0d;
        this.cAlphaStep = 0.2d;
        this.cSteps = 200;
        this.cDStep = 1.0d;
    }

    /* compiled from: ImageDeskew.scala */
    /* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/deskew/ImageDeskew$HoughLine.class */
    public class HoughLine {
        private int count;
        private int index;
        private double alpha;
        private double d;
        public final /* synthetic */ ImageDeskew $outer;

        public /* synthetic */ ImageDeskew code$sejda$tasks$deskew$ImageDeskew$HoughLine$$$outer() {
            return this.$outer;
        }

        public HoughLine(final ImageDeskew $outer) {
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
            this.count = 0;
            this.index = 0;
            this.alpha = 0.0d;
            this.d = 0.0d;
        }

        public int count() {
            return this.count;
        }

        public void count_$eq(final int x$1) {
            this.count = x$1;
        }

        public int index() {
            return this.index;
        }

        public void index_$eq(final int x$1) {
            this.index = x$1;
        }

        public double alpha() {
            return this.alpha;
        }

        public void alpha_$eq(final double x$1) {
            this.alpha = x$1;
        }

        public double d() {
            return this.d;
        }

        public void d_$eq(final double x$1) {
            this.d = x$1;
        }
    }

    private BufferedImage cImage() {
        return this.cImage;
    }

    private void cImage_$eq(final BufferedImage x$1) {
        this.cImage = x$1;
    }

    private double cAlphaStart() {
        return this.cAlphaStart;
    }

    private void cAlphaStart_$eq(final double x$1) {
        this.cAlphaStart = x$1;
    }

    private double cAlphaStep() {
        return this.cAlphaStep;
    }

    private void cAlphaStep_$eq(final double x$1) {
        this.cAlphaStep = x$1;
    }

    private int cSteps() {
        return this.cSteps;
    }

    private void cSteps_$eq(final int x$1) {
        this.cSteps = x$1;
    }

    private double[] cSinA() {
        return this.cSinA;
    }

    private void cSinA_$eq(final double[] x$1) {
        this.cSinA = x$1;
    }

    private double[] cCosA() {
        return this.cCosA;
    }

    private void cCosA_$eq(final double[] x$1) {
        this.cCosA = x$1;
    }

    private double cDMin() {
        return this.cDMin;
    }

    private void cDMin_$eq(final double x$1) {
        this.cDMin = x$1;
    }

    private double cDStep() {
        return this.cDStep;
    }

    private void cDStep_$eq(final double x$1) {
        this.cDStep = x$1;
    }

    private int cDCount() {
        return this.cDCount;
    }

    private void cDCount_$eq(final int x$1) {
        this.cDCount = x$1;
    }

    private int[] cHMatrix() {
        return this.cHMatrix;
    }

    private void cHMatrix_$eq(final int[] x$1) {
        this.cHMatrix = x$1;
    }

    public double getSkewAngle() {
        ObjectRef hl = ObjectRef.create((Object) null);
        DoubleRef sum = DoubleRef.create(0.0d);
        IntRef count = IntRef.create(0);
        calc();
        hl.elem = getTop(20);
        if (((HoughLine[]) hl.elem).length >= 20) {
            double minAlpha = BoxesRunTime.unboxToDouble(Predef$.MODULE$.wrapDoubleArray((double[]) ArrayOps$.MODULE$.map$extension(Predef$.MODULE$.refArrayOps((HoughLine[]) hl.elem), x$6 -> {
                return BoxesRunTime.boxToDouble(x$6.alpha());
            }, ClassTag$.MODULE$.Double())).min(Ordering$DeprecatedDoubleOrdering$.MODULE$));
            double maxAlpha = BoxesRunTime.unboxToDouble(Predef$.MODULE$.wrapDoubleArray((double[]) ArrayOps$.MODULE$.map$extension(Predef$.MODULE$.refArrayOps((HoughLine[]) hl.elem), x$7 -> {
                return BoxesRunTime.boxToDouble(x$7.alpha());
            }, ClassTag$.MODULE$.Double())).max(Ordering$DeprecatedDoubleOrdering$.MODULE$));
            double variance = Math.abs(maxAlpha - minAlpha);
            logger().debug(new StringBuilder(16).append("Alpha variance: ").append(variance).toString());
            if (variance > 8.9d) {
                logger().debug(new StringBuilder(59).append("Alpha variance too high: ").append(variance).append(", cannot trust skew angle detected").toString());
                return 0.0d;
            }
            RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(0), 19).foreach$mVc$sp(i -> {
                sum.elem += ((HoughLine[]) hl.elem)[i].alpha();
                count.elem++;
            });
            return sum.elem / count.elem;
        }
        return 0.0d;
    }

    private HoughLine[] getTop(final int count) {
        HoughLine[] hl = (HoughLine[]) Array$.MODULE$.ofDim(count, ClassTag$.MODULE$.apply(HoughLine.class));
        RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(0), count).foreach$mVc$sp(i -> {
            hl[i] = new HoughLine(this);
        });
        ObjectRef tmp = ObjectRef.create((Object) null);
        RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(0), cHMatrix().length - 1).withFilter(i2 -> {
            return this.cHMatrix()[i2] > hl[count - 1].count();
        }).foreach(i3 -> {
            hl[count - 1].count_$eq(this.cHMatrix()[i3]);
            hl[count - 1].index_$eq(i3);
            int j = count - 1;
            while (j > 0 && hl[j].count() > hl[j - 1].count()) {
                tmp.elem = hl[j];
                hl[j] = hl[j - 1];
                hl[j - 1] = (HoughLine) tmp.elem;
                j--;
                int i3 = j + 1;
            }
        });
        IntRef alphaIndex = IntRef.create(0);
        IntRef dIndex = IntRef.create(0);
        RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(0), count).foreach$mVc$sp(i4 -> {
            dIndex.elem = hl[i4].index() / this.cSteps();
            alphaIndex.elem = hl[i4].index() - (dIndex.elem * this.cSteps());
            hl[i4].alpha_$eq(this.getAlpha(alphaIndex.elem));
            hl[i4].d_$eq(dIndex.elem + this.cDMin());
        });
        return hl;
    }

    private void calc() {
        int hMin = (int) (cImage().getHeight() / 4.0d);
        int hMax = (int) ((cImage().getHeight() * 3.0d) / 4.0d);
        init();
        RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(hMin), hMax).foreach$mVc$sp(y -> {
            RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(1), this.cImage().getWidth() - 2).withFilter(x -> {
                return ImageIOUtils$.MODULE$.isBlack(this.cImage(), x, y);
            }).withFilter(x2 -> {
                return !ImageIOUtils$.MODULE$.isBlack(this.cImage(), x2, y + 1);
            }).foreach(x3 -> {
                this.calc(x3, y);
            });
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void calc(final int x, final int y) {
        DoubleRef d = DoubleRef.create(0.0d);
        IntRef dIndex = IntRef.create(0);
        IntRef index = IntRef.create(0);
        RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(0), cSteps() - 1).foreach$mVc$sp(alpha -> {
            d.elem = (y * this.cCosA()[alpha]) - (x * this.cSinA()[alpha]);
            dIndex.elem = (int) (d.elem - this.cDMin());
            index.elem = (dIndex.elem * this.cSteps()) + alpha;
            try {
                int[] iArrCHMatrix = this.cHMatrix();
                int i = index.elem;
                iArrCHMatrix[i] = iArrCHMatrix[i] + 1;
            } catch (Exception e) {
                this.logger().warn("", e);
            }
        });
    }

    private void init() {
        DoubleRef angle = DoubleRef.create(0.0d);
        cSinA_$eq((double[]) Array$.MODULE$.ofDim(cSteps() - 1, ClassTag$.MODULE$.Double()));
        cCosA_$eq((double[]) Array$.MODULE$.ofDim(cSteps() - 1, ClassTag$.MODULE$.Double()));
        RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(0), cSteps() - 1).foreach$mVc$sp(i -> {
            angle.elem = (this.getAlpha(i) * 3.141592653589793d) / 180.0d;
            this.cSinA()[i] = Math.sin(angle.elem);
            this.cCosA()[i] = Math.cos(angle.elem);
        });
        cDMin_$eq(-cImage().getWidth());
        cDCount_$eq((int) ((2.0d * (cImage().getWidth() + cImage().getHeight())) / cDStep()));
        cHMatrix_$eq((int[]) Array$.MODULE$.ofDim(cDCount() * cSteps(), ClassTag$.MODULE$.Int()));
    }

    public double getAlpha(final int index) {
        return cAlphaStart() + (index * cAlphaStep());
    }
}
