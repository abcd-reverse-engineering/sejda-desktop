package code.sejda.tasks.edit;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.Serializable;
import org.sejda.model.parameter.edit.Shape;
import org.sejda.model.pdf.page.PageRange;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple8;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: EditParameters.scala */
@ScalaSignature(bytes = "\u0006\u0005\t-b\u0001\u0002\u00192\u0001jB\u0001\u0002\u0015\u0001\u0003\u0016\u0004%\t!\u0015\u0005\t=\u0002\u0011\t\u0012)A\u0005%\"Aq\f\u0001BK\u0002\u0013\u0005\u0001\r\u0003\u0005e\u0001\tE\t\u0015!\u0003b\u0011!)\u0007A!f\u0001\n\u0003\u0001\u0007\u0002\u00034\u0001\u0005#\u0005\u000b\u0011B1\t\u0011\u001d\u0004!Q3A\u0005\u0002!D\u0001b\u001d\u0001\u0003\u0012\u0003\u0006I!\u001b\u0005\ti\u0002\u0011)\u001a!C\u0001k\"Aa\u0010\u0001B\tB\u0003%a\u000f\u0003\u0005��\u0001\tU\r\u0011\"\u0001a\u0011%\t\t\u0001\u0001B\tB\u0003%\u0011\r\u0003\u0006\u0002\u0004\u0001\u0011)\u001a!C\u0001\u0003\u000bA!\"a\u0004\u0001\u0005#\u0005\u000b\u0011BA\u0004\u0011)\t\t\u0002\u0001BK\u0002\u0013\u0005\u0011Q\u0001\u0005\u000b\u0003'\u0001!\u0011#Q\u0001\n\u0005\u001d\u0001bBA\u000b\u0001\u0011\u0005\u0011q\u0003\u0005\b\u0003[\u0001A\u0011AA\u0018\u0011%\t9\u0004AA\u0001\n\u0003\tI\u0004C\u0005\u0002L\u0001\t\n\u0011\"\u0001\u0002N!I\u00111\r\u0001\u0012\u0002\u0013\u0005\u0011Q\r\u0005\n\u0003S\u0002\u0011\u0013!C\u0001\u0003KB\u0011\"a\u001b\u0001#\u0003%\t!!\u001c\t\u0013\u0005E\u0004!%A\u0005\u0002\u0005M\u0004\"CA<\u0001E\u0005I\u0011AA3\u0011%\tI\bAI\u0001\n\u0003\tY\bC\u0005\u0002��\u0001\t\n\u0011\"\u0001\u0002|!I\u0011\u0011\u0011\u0001\u0002\u0002\u0013\u0005\u00131\u0011\u0005\n\u0003#\u0003\u0011\u0011!C\u0001\u0003'C\u0011\"a'\u0001\u0003\u0003%\t!!(\t\u0013\u0005%\u0006!!A\u0005B\u0005-\u0006\"CA]\u0001\u0005\u0005I\u0011AA^\u0011%\ty\fAA\u0001\n\u0003\n\t\rC\u0005\u0002F\u0002\t\t\u0011\"\u0011\u0002H\"I\u0011\u0011\u001a\u0001\u0002\u0002\u0013\u0005\u00131\u001a\u0005\n\u0003\u001b\u0004\u0011\u0011!C!\u0003\u001f<\u0011\"a52\u0003\u0003E\t!!6\u0007\u0011A\n\u0014\u0011!E\u0001\u0003/Dq!!\u0006'\t\u0003\ty\u000fC\u0005\u0002J\u001a\n\t\u0011\"\u0012\u0002L\"I\u0011\u0011\u001f\u0014\u0002\u0002\u0013\u0005\u00151\u001f\u0005\n\u0005\u000b1\u0013\u0013!C\u0001\u0003KB\u0011Ba\u0002'#\u0003%\t!a\u001f\t\u0013\t%a%!A\u0005\u0002\n-\u0001\"\u0003B\u000fME\u0005I\u0011AA3\u0011%\u0011yBJI\u0001\n\u0003\tY\bC\u0005\u0003\"\u0019\n\t\u0011\"\u0003\u0003$\t\t\u0012\t\u001a3TQ\u0006\u0004Xm\u00149fe\u0006$\u0018n\u001c8\u000b\u0005I\u001a\u0014\u0001B3eSRT!\u0001N\u001b\u0002\u000bQ\f7o[:\u000b\u0005Y:\u0014!B:fU\u0012\f'\"\u0001\u001d\u0002\t\r|G-Z\u0002\u0001'\u0011\u00011(\u0011#\u0011\u0005qzT\"A\u001f\u000b\u0003y\nQa]2bY\u0006L!\u0001Q\u001f\u0003\r\u0005s\u0017PU3g!\ta$)\u0003\u0002D{\t9\u0001K]8ek\u000e$\bCA#N\u001d\t15J\u0004\u0002H\u00156\t\u0001J\u0003\u0002Js\u00051AH]8pizJ\u0011AP\u0005\u0003\u0019v\nq\u0001]1dW\u0006<W-\u0003\u0002O\u001f\na1+\u001a:jC2L'0\u00192mK*\u0011A*P\u0001\u0006g\"\f\u0007/Z\u000b\u0002%B\u00111\u000bX\u0007\u0002)*\u0011!'\u0016\u0006\u0003-^\u000b\u0011\u0002]1sC6,G/\u001a:\u000b\u0005aK\u0016!B7pI\u0016d'B\u0001\u001c[\u0015\u0005Y\u0016aA8sO&\u0011Q\f\u0016\u0002\u0006'\"\f\u0007/Z\u0001\u0007g\"\f\u0007/\u001a\u0011\u0002\u000b]LG\r\u001e5\u0016\u0003\u0005\u0004\"\u0001\u00102\n\u0005\rl$!\u0002$m_\u0006$\u0018AB<jIRD\u0007%\u0001\u0004iK&<\u0007\u000e^\u0001\bQ\u0016Lw\r\u001b;!\u0003!\u0001xn]5uS>tW#A5\u0011\u0005)\fX\"A6\u000b\u00051l\u0017\u0001B4f_6T!A\\8\u0002\u0007\u0005<HOC\u0001q\u0003\u0011Q\u0017M^1\n\u0005I\\'a\u0002)pS:$(\u0007R\u0001\na>\u001c\u0018\u000e^5p]\u0002\n\u0011\u0002]1hKJ\u000bgnZ3\u0016\u0003Y\u0004\"a\u001e?\u000e\u0003aT!!\u001f>\u0002\tA\fw-\u001a\u0006\u0003w^\u000b1\u0001\u001d3g\u0013\ti\bPA\u0005QC\u001e,'+\u00198hK\u0006Q\u0001/Y4f%\u0006tw-\u001a\u0011\u0002\u0017\t|'\u000fZ3s/&$G\u000f[\u0001\rE>\u0014H-\u001a:XS\u0012$\b\u000eI\u0001\fE>\u0014H-\u001a:D_2|'/\u0006\u0002\u0002\bA!\u0011\u0011BA\u0006\u001b\u0005i\u0017bAA\u0007[\n)1i\u001c7pe\u0006a!m\u001c:eKJ\u001cu\u000e\\8sA\u0005y!-Y2lOJ|WO\u001c3D_2|'/\u0001\tcC\u000e\\wM]8v]\u0012\u001cu\u000e\\8sA\u00051A(\u001b8jiz\"\"#!\u0007\u0002\u001e\u0005}\u0011\u0011EA\u0012\u0003K\t9#!\u000b\u0002,A\u0019\u00111\u0004\u0001\u000e\u0003EBQ\u0001U\tA\u0002ICQaX\tA\u0002\u0005DQ!Z\tA\u0002\u0005DQaZ\tA\u0002%DQ\u0001^\tA\u0002YDqa`\t\u0011\u0002\u0003\u0007\u0011\rC\u0005\u0002\u0004E\u0001\n\u00111\u0001\u0002\b!9\u0011\u0011C\tA\u0002\u0005\u001d\u0011\u0001C<iSR,w.\u001e;\u0016\u0005\u0005E\u0002c\u0001\u001f\u00024%\u0019\u0011QG\u001f\u0003\u000f\t{w\u000e\\3b]\u0006!1m\u001c9z)I\tI\"a\u000f\u0002>\u0005}\u0012\u0011IA\"\u0003\u000b\n9%!\u0013\t\u000fA\u001b\u0002\u0013!a\u0001%\"9ql\u0005I\u0001\u0002\u0004\t\u0007bB3\u0014!\u0003\u0005\r!\u0019\u0005\bON\u0001\n\u00111\u0001j\u0011\u001d!8\u0003%AA\u0002YDqa`\n\u0011\u0002\u0003\u0007\u0011\rC\u0005\u0002\u0004M\u0001\n\u00111\u0001\u0002\b!I\u0011\u0011C\n\u0011\u0002\u0003\u0007\u0011qA\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\t\tyEK\u0002S\u0003#Z#!a\u0015\u0011\t\u0005U\u0013qL\u0007\u0003\u0003/RA!!\u0017\u0002\\\u0005IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u0003;j\u0014AC1o]>$\u0018\r^5p]&!\u0011\u0011MA,\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00133+\t\t9GK\u0002b\u0003#\nabY8qs\u0012\"WMZ1vYR$3'\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001b\u0016\u0005\u0005=$fA5\u0002R\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012*TCAA;U\r1\u0018\u0011K\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00137\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uI]*\"!! +\t\u0005\u001d\u0011\u0011K\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00139\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\u0011\u0011Q\u0011\t\u0005\u0003\u000f\u000bi)\u0004\u0002\u0002\n*\u0019\u00111R8\u0002\t1\fgnZ\u0005\u0005\u0003\u001f\u000bII\u0001\u0004TiJLgnZ\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0003+\u00032\u0001PAL\u0013\r\tI*\u0010\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0005\u0003?\u000b)\u000bE\u0002=\u0003CK1!a)>\u0005\r\te.\u001f\u0005\n\u0003Os\u0012\u0011!a\u0001\u0003+\u000b1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XCAAW!\u0019\ty+!.\u0002 6\u0011\u0011\u0011\u0017\u0006\u0004\u0003gk\u0014AC2pY2,7\r^5p]&!\u0011qWAY\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u0005E\u0012Q\u0018\u0005\n\u0003O\u0003\u0013\u0011!a\u0001\u0003?\u000b!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!\u0011QQAb\u0011%\t9+IA\u0001\u0002\u0004\t)*\u0001\u0005iCND7i\u001c3f)\t\t)*\u0001\u0005u_N#(/\u001b8h)\t\t))\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0003c\t\t\u000eC\u0005\u0002(\u0012\n\t\u00111\u0001\u0002 \u0006\t\u0012\t\u001a3TQ\u0006\u0004Xm\u00149fe\u0006$\u0018n\u001c8\u0011\u0007\u0005maeE\u0003'\u00033\f)\u000f\u0005\t\u0002\\\u0006\u0005(+Y1jm\u0006\f9!a\u0002\u0002\u001a5\u0011\u0011Q\u001c\u0006\u0004\u0003?l\u0014a\u0002:v]RLW.Z\u0005\u0005\u0003G\fiNA\tBEN$(/Y2u\rVt7\r^5p]b\u0002B!a:\u0002n6\u0011\u0011\u0011\u001e\u0006\u0004\u0003W|\u0017AA5p\u0013\rq\u0015\u0011\u001e\u000b\u0003\u0003+\fQ!\u00199qYf$\"#!\u0007\u0002v\u0006]\u0018\u0011`A~\u0003{\fyP!\u0001\u0003\u0004!)\u0001+\u000ba\u0001%\")q,\u000ba\u0001C\")Q-\u000ba\u0001C\")q-\u000ba\u0001S\")A/\u000ba\u0001m\"9q0\u000bI\u0001\u0002\u0004\t\u0007\"CA\u0002SA\u0005\t\u0019AA\u0004\u0011\u001d\t\t\"\u000ba\u0001\u0003\u000f\tq\"\u00199qYf$C-\u001a4bk2$HEN\u0001\u0010CB\u0004H.\u001f\u0013eK\u001a\fW\u000f\u001c;%o\u00059QO\\1qa2LH\u0003\u0002B\u0007\u00053\u0001R\u0001\u0010B\b\u0005'I1A!\u0005>\u0005\u0019y\u0005\u000f^5p]BiAH!\u0006SC\u0006Lg/YA\u0004\u0003\u000fI1Aa\u0006>\u0005\u0019!V\u000f\u001d7fq!I!1\u0004\u0017\u0002\u0002\u0003\u0007\u0011\u0011D\u0001\u0004q\u0012\u0002\u0014a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$c'A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$HeN\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0005K\u0001B!a\"\u0003(%!!\u0011FAE\u0005\u0019y%M[3di\u0002")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/AddShapeOperation.class */
public class AddShapeOperation implements Product, Serializable {
    private final Shape shape;
    private final float width;
    private final float height;
    private final Point2D position;
    private final PageRange pageRange;
    private final float borderWidth;
    private final Color borderColor;
    private final Color backgroundColor;

    public static Option<Tuple8<Shape, Object, Object, Point2D, PageRange, Object, Color, Color>> unapply(final AddShapeOperation x$0) {
        return AddShapeOperation$.MODULE$.unapply(x$0);
    }

    public static AddShapeOperation apply(final Shape shape, final float width, final float height, final Point2D position, final PageRange pageRange, final float borderWidth, final Color borderColor, final Color backgroundColor) {
        return AddShapeOperation$.MODULE$.apply(shape, width, height, position, pageRange, borderWidth, borderColor, backgroundColor);
    }

    public static Function1<Tuple8<Shape, Object, Object, Point2D, PageRange, Object, Color, Color>, AddShapeOperation> tupled() {
        return AddShapeOperation$.MODULE$.tupled();
    }

    public static Function1<Shape, Function1<Object, Function1<Object, Function1<Point2D, Function1<PageRange, Function1<Object, Function1<Color, Function1<Color, AddShapeOperation>>>>>>>> curried() {
        return AddShapeOperation$.MODULE$.curried();
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Shape shape() {
        return this.shape;
    }

    public AddShapeOperation copy(final Shape shape, final float width, final float height, final Point2D position, final PageRange pageRange, final float borderWidth, final Color borderColor, final Color backgroundColor) {
        return new AddShapeOperation(shape, width, height, position, pageRange, borderWidth, borderColor, backgroundColor);
    }

    public Shape copy$default$1() {
        return shape();
    }

    public String productPrefix() {
        return "AddShapeOperation";
    }

    public int productArity() {
        return 8;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return shape();
            case 1:
                return BoxesRunTime.boxToFloat(width());
            case 2:
                return BoxesRunTime.boxToFloat(height());
            case 3:
                return position();
            case 4:
                return pageRange();
            case 5:
                return BoxesRunTime.boxToFloat(borderWidth());
            case 6:
                return borderColor();
            case 7:
                return backgroundColor();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof AddShapeOperation;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "shape";
            case 1:
                return "width";
            case 2:
                return "height";
            case 3:
                return "position";
            case 4:
                return "pageRange";
            case 5:
                return "borderWidth";
            case 6:
                return "borderColor";
            case 7:
                return "backgroundColor";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return Statics.finalizeHash(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(-889275714, productPrefix().hashCode()), Statics.anyHash(shape())), Statics.floatHash(width())), Statics.floatHash(height())), Statics.anyHash(position())), Statics.anyHash(pageRange())), Statics.floatHash(borderWidth())), Statics.anyHash(borderColor())), Statics.anyHash(backgroundColor())), 8);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof AddShapeOperation) {
                AddShapeOperation addShapeOperation = (AddShapeOperation) x$1;
                if (width() == addShapeOperation.width() && height() == addShapeOperation.height() && borderWidth() == addShapeOperation.borderWidth()) {
                    Shape shape = shape();
                    Shape shape2 = addShapeOperation.shape();
                    if (shape != null ? shape.equals(shape2) : shape2 == null) {
                        Point2D point2DPosition = position();
                        Point2D point2DPosition2 = addShapeOperation.position();
                        if (point2DPosition != null ? point2DPosition.equals(point2DPosition2) : point2DPosition2 == null) {
                            PageRange pageRange = pageRange();
                            PageRange pageRange2 = addShapeOperation.pageRange();
                            if (pageRange != null ? pageRange.equals(pageRange2) : pageRange2 == null) {
                                Color colorBorderColor = borderColor();
                                Color colorBorderColor2 = addShapeOperation.borderColor();
                                if (colorBorderColor != null ? colorBorderColor.equals(colorBorderColor2) : colorBorderColor2 == null) {
                                    Color colorBackgroundColor = backgroundColor();
                                    Color colorBackgroundColor2 = addShapeOperation.backgroundColor();
                                    if (colorBackgroundColor != null ? colorBackgroundColor.equals(colorBackgroundColor2) : colorBackgroundColor2 == null) {
                                        if (addShapeOperation.canEqual(this)) {
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    public AddShapeOperation(final Shape shape, final float width, final float height, final Point2D position, final PageRange pageRange, final float borderWidth, final Color borderColor, final Color backgroundColor) {
        this.shape = shape;
        this.width = width;
        this.height = height;
        this.position = position;
        this.pageRange = pageRange;
        this.borderWidth = borderWidth;
        this.borderColor = borderColor;
        this.backgroundColor = backgroundColor;
        Product.$init$(this);
    }

    public float width() {
        return this.width;
    }

    public float copy$default$2() {
        return width();
    }

    public float height() {
        return this.height;
    }

    public float copy$default$3() {
        return height();
    }

    public Point2D position() {
        return this.position;
    }

    public Point2D copy$default$4() {
        return position();
    }

    public PageRange pageRange() {
        return this.pageRange;
    }

    public PageRange copy$default$5() {
        return pageRange();
    }

    public float borderWidth() {
        return this.borderWidth;
    }

    public float copy$default$6() {
        return borderWidth();
    }

    public Color borderColor() {
        return this.borderColor;
    }

    public Color copy$default$7() {
        return borderColor();
    }

    public Color backgroundColor() {
        return this.backgroundColor;
    }

    public Color copy$default$8() {
        return backgroundColor();
    }

    public boolean whiteout() {
        Shape shape = shape();
        Shape shape2 = Shape.RECTANGLE;
        if (shape != null ? shape.equals(shape2) : shape2 == null) {
            Color colorBorderColor = borderColor();
            Color color = Color.WHITE;
            if (colorBorderColor != null ? colorBorderColor.equals(color) : color == null) {
                Color colorBackgroundColor = backgroundColor();
                Color color2 = Color.WHITE;
                if (colorBackgroundColor != null ? colorBackgroundColor.equals(color2) : color2 == null) {
                    return true;
                }
            }
        }
        return false;
    }
}
