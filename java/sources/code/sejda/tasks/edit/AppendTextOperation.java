package code.sejda.tasks.edit;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.Serializable;
import org.sejda.model.pdf.page.PageRange;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple9;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: EditParameters.scala */
@ScalaSignature(bytes = "\u0006\u0005\tUc\u0001\u0002\u001c8\u0001\u0002C\u0001B\u0016\u0001\u0003\u0016\u0004%\ta\u0016\u0005\tA\u0002\u0011\t\u0012)A\u00051\"A\u0011\r\u0001BK\u0002\u0013\u0005q\u000b\u0003\u0005c\u0001\tE\t\u0015!\u0003Y\u0011!\u0019\u0007A!f\u0001\n\u0003!\u0007\u0002\u00035\u0001\u0005#\u0005\u000b\u0011B3\t\u0011%\u0004!Q3A\u0005\u0002)D\u0001b\u001d\u0001\u0003\u0012\u0003\u0006Ia\u001b\u0005\ti\u0002\u0011)\u001a!C\u0001k\"AA\u0010\u0001B\tB\u0003%a\u000f\u0003\u0005~\u0001\tU\r\u0011\"\u0001\u007f\u0011%\tI\u0002\u0001B\tB\u0003%q\u0010\u0003\u0006\u0002\u001c\u0001\u0011)\u001a!C\u0001\u0003;A!\"!\n\u0001\u0005#\u0005\u000b\u0011BA\u0010\u0011)\t9\u0003\u0001BK\u0002\u0013\u0005\u0011Q\u0004\u0005\u000b\u0003S\u0001!\u0011#Q\u0001\n\u0005}\u0001BCA\u0016\u0001\tU\r\u0011\"\u0001\u0002.!Q\u0011Q\u0007\u0001\u0003\u0012\u0003\u0006I!a\f\t\u000f\u0005]\u0002\u0001\"\u0001\u0002:!I\u0011\u0011\u000b\u0001\u0002\u0002\u0013\u0005\u00111\u000b\u0005\n\u0003O\u0002\u0011\u0013!C\u0001\u0003SB\u0011\"a \u0001#\u0003%\t!!\u001b\t\u0013\u0005\u0005\u0005!%A\u0005\u0002\u0005\r\u0005\"CAD\u0001E\u0005I\u0011AAE\u0011%\ti\tAI\u0001\n\u0003\ty\tC\u0005\u0002\u0014\u0002\t\n\u0011\"\u0001\u0002\u0016\"I\u0011\u0011\u0014\u0001\u0012\u0002\u0013\u0005\u00111\u0014\u0005\n\u0003?\u0003\u0011\u0013!C\u0001\u00037C\u0011\"!)\u0001#\u0003%\t!a)\t\u0013\u0005\u001d\u0006!!A\u0005B\u0005%\u0006\"CA[\u0001\u0005\u0005I\u0011AA\\\u0011%\ty\fAA\u0001\n\u0003\t\t\rC\u0005\u0002N\u0002\t\t\u0011\"\u0011\u0002P\"I\u0011Q\u001c\u0001\u0002\u0002\u0013\u0005\u0011q\u001c\u0005\n\u0003G\u0004\u0011\u0011!C!\u0003KD\u0011\"!;\u0001\u0003\u0003%\t%a;\t\u0013\u00055\b!!A\u0005B\u0005=\b\"CAy\u0001\u0005\u0005I\u0011IAz\u000f%\t9pNA\u0001\u0012\u0003\tIP\u0002\u00057o\u0005\u0005\t\u0012AA~\u0011\u001d\t9\u0004\u000bC\u0001\u0005'A\u0011\"!<)\u0003\u0003%)%a<\t\u0013\tU\u0001&!A\u0005\u0002\n]\u0001\"\u0003B\u0016QE\u0005I\u0011AAE\u0011%\u0011i\u0003KI\u0001\n\u0003\tY\nC\u0005\u00030!\n\n\u0011\"\u0001\u0002\u001c\"I!\u0011\u0007\u0015\u0012\u0002\u0013\u0005\u00111\u0015\u0005\n\u0005gA\u0013\u0011!CA\u0005kA\u0011Ba\u0011)#\u0003%\t!!#\t\u0013\t\u0015\u0003&%A\u0005\u0002\u0005m\u0005\"\u0003B$QE\u0005I\u0011AAN\u0011%\u0011I\u0005KI\u0001\n\u0003\t\u0019\u000bC\u0005\u0003L!\n\t\u0011\"\u0003\u0003N\t\u0019\u0012\t\u001d9f]\u0012$V\r\u001f;Pa\u0016\u0014\u0018\r^5p]*\u0011\u0001(O\u0001\u0005K\u0012LGO\u0003\u0002;w\u0005)A/Y:lg*\u0011A(P\u0001\u0006g\u0016TG-\u0019\u0006\u0002}\u0005!1m\u001c3f\u0007\u0001\u0019B\u0001A!H\u0015B\u0011!)R\u0007\u0002\u0007*\tA)A\u0003tG\u0006d\u0017-\u0003\u0002G\u0007\n1\u0011I\\=SK\u001a\u0004\"A\u0011%\n\u0005%\u001b%a\u0002)s_\u0012,8\r\u001e\t\u0003\u0017Ns!\u0001T)\u000f\u00055\u0003V\"\u0001(\u000b\u0005={\u0014A\u0002\u001fs_>$h(C\u0001E\u0013\t\u00116)A\u0004qC\u000e\\\u0017mZ3\n\u0005Q+&\u0001D*fe&\fG.\u001b>bE2,'B\u0001*D\u0003\u0011!X\r\u001f;\u0016\u0003a\u0003\"!W/\u000f\u0005i[\u0006CA'D\u0013\ta6)\u0001\u0004Qe\u0016$WMZ\u0005\u0003=~\u0013aa\u0015;sS:<'B\u0001/D\u0003\u0015!X\r\u001f;!\u0003\u00111wN\u001c;\u0002\u000b\u0019|g\u000e\u001e\u0011\u0002\u0011\u0019|g\u000e^*ju\u0016,\u0012!\u001a\t\u0003\u0005\u001aL!aZ\"\u0003\r\u0011{WO\u00197f\u0003%1wN\u001c;TSj,\u0007%A\u0003d_2|'/F\u0001l!\ta\u0017/D\u0001n\u0015\tqw.A\u0002boRT\u0011\u0001]\u0001\u0005U\u00064\u0018-\u0003\u0002s[\n)1i\u001c7pe\u000611m\u001c7pe\u0002\n\u0001\u0002]8tSRLwN\\\u000b\u0002mB\u0011qO_\u0007\u0002q*\u0011\u00110\\\u0001\u0005O\u0016|W.\u0003\u0002|q\n9\u0001k\\5oiJ\"\u0015!\u00039pg&$\u0018n\u001c8!\u0003%\u0001\u0018mZ3SC:<W-F\u0001��!\u0011\t\t!!\u0006\u000e\u0005\u0005\r!\u0002BA\u0003\u0003\u000f\tA\u0001]1hK*!\u0011\u0011BA\u0006\u0003\r\u0001HM\u001a\u0006\u0005\u0003\u001b\ty!A\u0003n_\u0012,GNC\u0002=\u0003#Q!!a\u0005\u0002\u0007=\u0014x-\u0003\u0003\u0002\u0018\u0005\r!!\u0003)bO\u0016\u0014\u0016M\\4f\u0003)\u0001\u0018mZ3SC:<W\rI\u0001\u0005E>dG-\u0006\u0002\u0002 A\u0019!)!\t\n\u0007\u0005\r2IA\u0004C_>dW-\u00198\u0002\u000b\t|G\u000e\u001a\u0011\u0002\r%$\u0018\r\\5d\u0003\u001dIG/\u00197jG\u0002\n!\u0002\\5oK\"+\u0017n\u001a5u+\t\ty\u0003\u0005\u0003C\u0003c)\u0017bAA\u001a\u0007\n1q\n\u001d;j_:\f1\u0002\\5oK\"+\u0017n\u001a5uA\u00051A(\u001b8jiz\"B#a\u000f\u0002@\u0005\u0005\u00131IA#\u0003\u000f\nI%a\u0013\u0002N\u0005=\u0003cAA\u001f\u00015\tq\u0007C\u0003W'\u0001\u0007\u0001\fC\u0003b'\u0001\u0007\u0001\fC\u0003d'\u0001\u0007Q\rC\u0004j'A\u0005\t\u0019A6\t\u000bQ\u001c\u0002\u0019\u0001<\t\u000bu\u001c\u0002\u0019A@\t\u0013\u0005m1\u0003%AA\u0002\u0005}\u0001\"CA\u0014'A\u0005\t\u0019AA\u0010\u0011%\tYc\u0005I\u0001\u0002\u0004\ty#\u0001\u0003d_BLH\u0003FA\u001e\u0003+\n9&!\u0017\u0002\\\u0005u\u0013qLA1\u0003G\n)\u0007C\u0004W)A\u0005\t\u0019\u0001-\t\u000f\u0005$\u0002\u0013!a\u00011\"91\r\u0006I\u0001\u0002\u0004)\u0007bB5\u0015!\u0003\u0005\ra\u001b\u0005\biR\u0001\n\u00111\u0001w\u0011\u001diH\u0003%AA\u0002}D\u0011\"a\u0007\u0015!\u0003\u0005\r!a\b\t\u0013\u0005\u001dB\u0003%AA\u0002\u0005}\u0001\"CA\u0016)A\u0005\t\u0019AA\u0018\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\"!a\u001b+\u0007a\u000big\u000b\u0002\u0002pA!\u0011\u0011OA>\u001b\t\t\u0019H\u0003\u0003\u0002v\u0005]\u0014!C;oG\",7m[3e\u0015\r\tIhQ\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BA?\u0003g\u0012\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII\nabY8qs\u0012\"WMZ1vYR$3'\u0006\u0002\u0002\u0006*\u001aQ-!\u001c\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%iU\u0011\u00111\u0012\u0016\u0004W\u00065\u0014AD2paf$C-\u001a4bk2$H%N\u000b\u0003\u0003#S3A^A7\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIY*\"!a&+\u0007}\fi'\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001c\u0016\u0005\u0005u%\u0006BA\u0010\u0003[\nabY8qs\u0012\"WMZ1vYR$\u0003(\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001d\u0016\u0005\u0005\u0015&\u0006BA\u0018\u0003[\nQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DXCAAV!\u0011\ti+a-\u000e\u0005\u0005=&bAAY_\u0006!A.\u00198h\u0013\rq\u0016qV\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0003s\u00032AQA^\u0013\r\til\u0011\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0005\u0003\u0007\fI\rE\u0002C\u0003\u000bL1!a2D\u0005\r\te.\u001f\u0005\n\u0003\u0017\u0004\u0013\u0011!a\u0001\u0003s\u000b1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XCAAi!\u0019\t\u0019.!7\u0002D6\u0011\u0011Q\u001b\u0006\u0004\u0003/\u001c\u0015AC2pY2,7\r^5p]&!\u00111\\Ak\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u0005}\u0011\u0011\u001d\u0005\n\u0003\u0017\u0014\u0013\u0011!a\u0001\u0003\u0007\f!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!\u00111VAt\u0011%\tYmIA\u0001\u0002\u0004\tI,\u0001\u0005iCND7i\u001c3f)\t\tI,\u0001\u0005u_N#(/\u001b8h)\t\tY+\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0003?\t)\u0010C\u0005\u0002L\u001a\n\t\u00111\u0001\u0002D\u0006\u0019\u0012\t\u001d9f]\u0012$V\r\u001f;Pa\u0016\u0014\u0018\r^5p]B\u0019\u0011Q\b\u0015\u0014\u000b!\niP!\u0003\u0011%\u0005}(Q\u0001-YK.4x0a\b\u0002 \u0005=\u00121H\u0007\u0003\u0005\u0003Q1Aa\u0001D\u0003\u001d\u0011XO\u001c;j[\u0016LAAa\u0002\u0003\u0002\t\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\\\u001d\u0011\t\t-!\u0011C\u0007\u0003\u0005\u001bQ1Aa\u0004p\u0003\tIw.C\u0002U\u0005\u001b!\"!!?\u0002\u000b\u0005\u0004\b\u000f\\=\u0015)\u0005m\"\u0011\u0004B\u000e\u0005;\u0011yB!\t\u0003$\t\u0015\"q\u0005B\u0015\u0011\u001516\u00061\u0001Y\u0011\u0015\t7\u00061\u0001Y\u0011\u0015\u00197\u00061\u0001f\u0011\u001dI7\u0006%AA\u0002-DQ\u0001^\u0016A\u0002YDQ!`\u0016A\u0002}D\u0011\"a\u0007,!\u0003\u0005\r!a\b\t\u0013\u0005\u001d2\u0006%AA\u0002\u0005}\u0001\"CA\u0016WA\u0005\t\u0019AA\u0018\u0003=\t\u0007\u000f\u001d7zI\u0011,g-Y;mi\u0012\"\u0014aD1qa2LH\u0005Z3gCVdG\u000fJ\u001c\u0002\u001f\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uIa\nq\"\u00199qYf$C-\u001a4bk2$H%O\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\u00119Da\u0010\u0011\u000b\t\u000b\tD!\u000f\u0011\u001f\t\u0013Y\u0004\u0017-fWZ|\u0018qDA\u0010\u0003_I1A!\u0010D\u0005\u0019!V\u000f\u001d7fs!I!\u0011\t\u0019\u0002\u0002\u0003\u0007\u00111H\u0001\u0004q\u0012\u0002\u0014a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$C'A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$HeN\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000f\n\u001d\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u0013:\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\u0011y\u0005\u0005\u0003\u0002.\nE\u0013\u0002\u0002B*\u0003_\u0013aa\u00142kK\u000e$\b")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/AppendTextOperation.class */
public class AppendTextOperation implements Product, Serializable {
    private final String text;
    private final String font;
    private final double fontSize;
    private final Color color;
    private final Point2D position;
    private final PageRange pageRange;
    private final boolean bold;
    private final boolean italic;
    private final Option<Object> lineHeight;

    public static Option<Tuple9<String, String, Object, Color, Point2D, PageRange, Object, Object, Option<Object>>> unapply(final AppendTextOperation x$0) {
        return AppendTextOperation$.MODULE$.unapply(x$0);
    }

    public static AppendTextOperation apply(final String text, final String font, final double fontSize, final Color color, final Point2D position, final PageRange pageRange, final boolean bold, final boolean italic, final Option<Object> lineHeight) {
        return AppendTextOperation$.MODULE$.apply(text, font, fontSize, color, position, pageRange, bold, italic, lineHeight);
    }

    public static Function1<Tuple9<String, String, Object, Color, Point2D, PageRange, Object, Object, Option<Object>>, AppendTextOperation> tupled() {
        return AppendTextOperation$.MODULE$.tupled();
    }

    public static Function1<String, Function1<String, Function1<Object, Function1<Color, Function1<Point2D, Function1<PageRange, Function1<Object, Function1<Object, Function1<Option<Object>, AppendTextOperation>>>>>>>>> curried() {
        return AppendTextOperation$.MODULE$.curried();
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String text() {
        return this.text;
    }

    public String font() {
        return this.font;
    }

    public double fontSize() {
        return this.fontSize;
    }

    public AppendTextOperation copy(final String text, final String font, final double fontSize, final Color color, final Point2D position, final PageRange pageRange, final boolean bold, final boolean italic, final Option<Object> lineHeight) {
        return new AppendTextOperation(text, font, fontSize, color, position, pageRange, bold, italic, lineHeight);
    }

    public String copy$default$1() {
        return text();
    }

    public String copy$default$2() {
        return font();
    }

    public double copy$default$3() {
        return fontSize();
    }

    public String productPrefix() {
        return "AppendTextOperation";
    }

    public int productArity() {
        return 9;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return text();
            case 1:
                return font();
            case 2:
                return BoxesRunTime.boxToDouble(fontSize());
            case 3:
                return color();
            case 4:
                return position();
            case 5:
                return pageRange();
            case 6:
                return BoxesRunTime.boxToBoolean(bold());
            case 7:
                return BoxesRunTime.boxToBoolean(italic());
            case 8:
                return lineHeight();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof AppendTextOperation;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "text";
            case 1:
                return "font";
            case 2:
                return "fontSize";
            case 3:
                return "color";
            case 4:
                return "position";
            case 5:
                return "pageRange";
            case 6:
                return "bold";
            case 7:
                return "italic";
            case 8:
                return "lineHeight";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return Statics.finalizeHash(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(-889275714, productPrefix().hashCode()), Statics.anyHash(text())), Statics.anyHash(font())), Statics.doubleHash(fontSize())), Statics.anyHash(color())), Statics.anyHash(position())), Statics.anyHash(pageRange())), bold() ? 1231 : 1237), italic() ? 1231 : 1237), Statics.anyHash(lineHeight())), 9);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof AppendTextOperation) {
                AppendTextOperation appendTextOperation = (AppendTextOperation) x$1;
                if (fontSize() == appendTextOperation.fontSize() && bold() == appendTextOperation.bold() && italic() == appendTextOperation.italic()) {
                    String strText = text();
                    String strText2 = appendTextOperation.text();
                    if (strText != null ? strText.equals(strText2) : strText2 == null) {
                        String strFont = font();
                        String strFont2 = appendTextOperation.font();
                        if (strFont != null ? strFont.equals(strFont2) : strFont2 == null) {
                            Color color = color();
                            Color color2 = appendTextOperation.color();
                            if (color != null ? color.equals(color2) : color2 == null) {
                                Point2D point2DPosition = position();
                                Point2D point2DPosition2 = appendTextOperation.position();
                                if (point2DPosition != null ? point2DPosition.equals(point2DPosition2) : point2DPosition2 == null) {
                                    PageRange pageRange = pageRange();
                                    PageRange pageRange2 = appendTextOperation.pageRange();
                                    if (pageRange != null ? pageRange.equals(pageRange2) : pageRange2 == null) {
                                        Option<Object> optionLineHeight = lineHeight();
                                        Option<Object> optionLineHeight2 = appendTextOperation.lineHeight();
                                        if (optionLineHeight != null ? optionLineHeight.equals(optionLineHeight2) : optionLineHeight2 == null) {
                                            if (appendTextOperation.canEqual(this)) {
                                            }
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

    public AppendTextOperation(final String text, final String font, final double fontSize, final Color color, final Point2D position, final PageRange pageRange, final boolean bold, final boolean italic, final Option<Object> lineHeight) {
        this.text = text;
        this.font = font;
        this.fontSize = fontSize;
        this.color = color;
        this.position = position;
        this.pageRange = pageRange;
        this.bold = bold;
        this.italic = italic;
        this.lineHeight = lineHeight;
        Product.$init$(this);
    }

    public Color color() {
        return this.color;
    }

    public Point2D position() {
        return this.position;
    }

    public PageRange pageRange() {
        return this.pageRange;
    }

    public Color copy$default$4() {
        return color();
    }

    public Point2D copy$default$5() {
        return position();
    }

    public PageRange copy$default$6() {
        return pageRange();
    }

    public boolean bold() {
        return this.bold;
    }

    public boolean italic() {
        return this.italic;
    }

    public Option<Object> lineHeight() {
        return this.lineHeight;
    }

    public boolean copy$default$7() {
        return bold();
    }

    public boolean copy$default$8() {
        return italic();
    }

    public Option<Object> copy$default$9() {
        return lineHeight();
    }
}
