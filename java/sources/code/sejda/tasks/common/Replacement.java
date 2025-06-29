package code.sejda.tasks.common;

import java.awt.geom.Point2D;
import java.io.Serializable;
import org.sejda.sambox.pdmodel.font.PDFont;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple7;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: PageTextRedactor.scala */
@ScalaSignature(bytes = "\u0006\u0005\tUa\u0001\u0002\u0015*\u0001JB\u0001\u0002\u0013\u0001\u0003\u0016\u0004%\t!\u0013\u0005\t%\u0002\u0011\t\u0012)A\u0005\u0015\"A1\u000b\u0001BK\u0002\u0013\u0005A\u000b\u0003\u0005e\u0001\tE\t\u0015!\u0003V\u0011!)\u0007A!f\u0001\n\u00031\u0007\u0002C6\u0001\u0005#\u0005\u000b\u0011B4\t\u00111\u0004!Q3A\u0005\u00025D\u0001\"\u001f\u0001\u0003\u0012\u0003\u0006IA\u001c\u0005\tu\u0002\u0011)\u001a!C\u0001w\"I\u0011\u0011\u0002\u0001\u0003\u0012\u0003\u0006I\u0001 \u0005\u000b\u0003\u0017\u0001!Q3A\u0005\u0002\u00055\u0001BCA\u000b\u0001\tE\t\u0015!\u0003\u0002\u0010!I\u0011q\u0003\u0001\u0003\u0016\u0004%\tA\u001a\u0005\n\u00033\u0001!\u0011#Q\u0001\n\u001dDq!a\u0007\u0001\t\u0003\ti\u0002C\u0005\u00022\u0001\t\t\u0011\"\u0001\u00024!I\u00111\t\u0001\u0012\u0002\u0013\u0005\u0011Q\t\u0005\n\u00037\u0002\u0011\u0013!C\u0001\u0003;B\u0011\"!\u0019\u0001#\u0003%\t!a\u0019\t\u0013\u0005\u001d\u0004!%A\u0005\u0002\u0005%\u0004\"CA7\u0001E\u0005I\u0011AA8\u0011%\t\u0019\bAI\u0001\n\u0003\t)\bC\u0005\u0002z\u0001\t\n\u0011\"\u0001\u0002d!I\u00111\u0010\u0001\u0002\u0002\u0013\u0005\u0013Q\u0010\u0005\n\u0003\u0013\u0003\u0011\u0011!C\u0001\u0003\u0017C\u0011\"a%\u0001\u0003\u0003%\t!!&\t\u0013\u0005\u0005\u0006!!A\u0005B\u0005\r\u0006\"CAY\u0001\u0005\u0005I\u0011AAZ\u0011%\t9\fAA\u0001\n\u0003\nI\fC\u0005\u0002>\u0002\t\t\u0011\"\u0011\u0002@\"I\u0011\u0011\u0019\u0001\u0002\u0002\u0013\u0005\u00131\u0019\u0005\n\u0003\u000b\u0004\u0011\u0011!C!\u0003\u000f<\u0011\"a3*\u0003\u0003E\t!!4\u0007\u0011!J\u0013\u0011!E\u0001\u0003\u001fDq!a\u0007#\t\u0003\t9\u000fC\u0005\u0002B\n\n\t\u0011\"\u0012\u0002D\"I\u0011\u0011\u001e\u0012\u0002\u0002\u0013\u0005\u00151\u001e\u0005\n\u0003w\u0014\u0013\u0011!CA\u0003{D\u0011Ba\u0003#\u0003\u0003%IA!\u0004\u0003\u0017I+\u0007\u000f\\1dK6,g\u000e\u001e\u0006\u0003U-\naaY8n[>t'B\u0001\u0017.\u0003\u0015!\u0018m]6t\u0015\tqs&A\u0003tK*$\u0017MC\u00011\u0003\u0011\u0019w\u000eZ3\u0004\u0001M!\u0001aM\u001d=!\t!t'D\u00016\u0015\u00051\u0014!B:dC2\f\u0017B\u0001\u001d6\u0005\u0019\te.\u001f*fMB\u0011AGO\u0005\u0003wU\u0012q\u0001\u0015:pIV\u001cG\u000f\u0005\u0002>\u000b:\u0011ah\u0011\b\u0003\u007f\tk\u0011\u0001\u0011\u0006\u0003\u0003F\na\u0001\u0010:p_Rt\u0014\"\u0001\u001c\n\u0005\u0011+\u0014a\u00029bG.\fw-Z\u0005\u0003\r\u001e\u0013AbU3sS\u0006d\u0017N_1cY\u0016T!\u0001R\u001b\u0002\tQ,\u0007\u0010^\u000b\u0002\u0015B\u00111j\u0014\b\u0003\u00196\u0003\"aP\u001b\n\u00059+\u0014A\u0002)sK\u0012,g-\u0003\u0002Q#\n11\u000b\u001e:j]\u001eT!AT\u001b\u0002\u000bQ,\u0007\u0010\u001e\u0011\u0002\t\u0019|g\u000e^\u000b\u0002+B\u0019AG\u0016-\n\u0005]+$AB(qi&|g\u000e\u0005\u0002ZE6\t!L\u0003\u0002T7*\u0011A,X\u0001\ba\u0012lw\u000eZ3m\u0015\tqv,\u0001\u0004tC6\u0014w\u000e\u001f\u0006\u0003]\u0001T\u0011!Y\u0001\u0004_J<\u0017BA2[\u0005\u0019\u0001FIR8oi\u0006)am\u001c8uA\u0005Aam\u001c8u'&TX-F\u0001h!\r!d\u000b\u001b\t\u0003i%L!A[\u001b\u0003\r\u0011{WO\u00197f\u0003%1wN\u001c;TSj,\u0007%\u0001\u0005q_NLG/[8o+\u0005q\u0007c\u0001\u001bW_B\u0011\u0001o^\u0007\u0002c*\u0011!o]\u0001\u0005O\u0016|WN\u0003\u0002uk\u0006\u0019\u0011m\u001e;\u000b\u0003Y\fAA[1wC&\u0011\u00010\u001d\u0002\b!>Lg\u000e\u001e\u001aE\u0003%\u0001xn]5uS>t\u0007%A\u0003d_2|'/F\u0001}!\r!d+ \t\u0004}\u0006\u0015Q\"A@\u000b\u0007i\f\tAC\u0002\u0002\u0004m\u000b\u0001b\u001a:ba\"L7m]\u0005\u0004\u0003\u000fy(a\u0002)E\u0007>dwN]\u0001\u0007G>dwN\u001d\u0011\u0002\u0015\u0019\fW\u000f_%uC2L7-\u0006\u0002\u0002\u0010A\u0019A'!\u0005\n\u0007\u0005MQGA\u0004C_>dW-\u00198\u0002\u0017\u0019\fW\u000f_%uC2L7\rI\u0001\u000bY&tW\rS3jO\"$\u0018a\u00037j]\u0016DU-[4ii\u0002\na\u0001P5oSRtD\u0003EA\u0010\u0003G\t)#a\n\u0002*\u0005-\u0012QFA\u0018!\r\t\t\u0003A\u0007\u0002S!)\u0001j\u0004a\u0001\u0015\")1k\u0004a\u0001+\")Qm\u0004a\u0001O\")An\u0004a\u0001]\")!p\u0004a\u0001y\"9\u00111B\bA\u0002\u0005=\u0001BBA\f\u001f\u0001\u0007q-\u0001\u0003d_BLH\u0003EA\u0010\u0003k\t9$!\u000f\u0002<\u0005u\u0012qHA!\u0011\u001dA\u0005\u0003%AA\u0002)Cqa\u0015\t\u0011\u0002\u0003\u0007Q\u000bC\u0004f!A\u0005\t\u0019A4\t\u000f1\u0004\u0002\u0013!a\u0001]\"9!\u0010\u0005I\u0001\u0002\u0004a\b\"CA\u0006!A\u0005\t\u0019AA\b\u0011!\t9\u0002\u0005I\u0001\u0002\u00049\u0017AD2paf$C-\u001a4bk2$H%M\u000b\u0003\u0003\u000fR3ASA%W\t\tY\u0005\u0005\u0003\u0002N\u0005]SBAA(\u0015\u0011\t\t&a\u0015\u0002\u0013Ut7\r[3dW\u0016$'bAA+k\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005e\u0013q\n\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017AD2paf$C-\u001a4bk2$HEM\u000b\u0003\u0003?R3!VA%\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIM*\"!!\u001a+\u0007\u001d\fI%\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001b\u0016\u0005\u0005-$f\u00018\u0002J\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012*TCAA9U\ra\u0018\u0011J\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00137+\t\t9H\u000b\u0003\u0002\u0010\u0005%\u0013AD2paf$C-\u001a4bk2$HeN\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\u0005}\u0004\u0003BAA\u0003\u000fk!!a!\u000b\u0007\u0005\u0015U/\u0001\u0003mC:<\u0017b\u0001)\u0002\u0004\u0006a\u0001O]8ek\u000e$\u0018I]5usV\u0011\u0011Q\u0012\t\u0004i\u0005=\u0015bAAIk\t\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR!\u0011qSAO!\r!\u0014\u0011T\u0005\u0004\u00037+$aA!os\"I\u0011q\u0014\u000e\u0002\u0002\u0003\u0007\u0011QR\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0005\u0005\u0015\u0006CBAT\u0003[\u000b9*\u0004\u0002\u0002**\u0019\u00111V\u001b\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u00020\u0006%&\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$B!a\u0004\u00026\"I\u0011q\u0014\u000f\u0002\u0002\u0003\u0007\u0011qS\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\r\u0006\u0003\u0002��\u0005m\u0006\"CAP;\u0005\u0005\t\u0019AAG\u0003!A\u0017m\u001d5D_\u0012,GCAAG\u0003!!xn\u0015;sS:<GCAA@\u0003\u0019)\u0017/^1mgR!\u0011qBAe\u0011%\ty\nIA\u0001\u0002\u0004\t9*A\u0006SKBd\u0017mY3nK:$\bcAA\u0011EM)!%!5\u0002^Bq\u00111[Am\u0015V;g\u000e`A\bO\u0006}QBAAk\u0015\r\t9.N\u0001\beVtG/[7f\u0013\u0011\tY.!6\u0003#\u0005\u00137\u000f\u001e:bGR4UO\\2uS>tw\u0007\u0005\u0003\u0002`\u0006\u0015XBAAq\u0015\r\t\u0019/^\u0001\u0003S>L1ARAq)\t\ti-A\u0003baBd\u0017\u0010\u0006\t\u0002 \u00055\u0018q^Ay\u0003g\f)0a>\u0002z\")\u0001*\na\u0001\u0015\")1+\na\u0001+\")Q-\na\u0001O\")A.\na\u0001]\")!0\na\u0001y\"9\u00111B\u0013A\u0002\u0005=\u0001BBA\fK\u0001\u0007q-A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\u0005}(q\u0001\t\u0005iY\u0013\t\u0001E\u00065\u0005\u0007QUk\u001a8}\u0003\u001f9\u0017b\u0001B\u0003k\t1A+\u001e9mK^B\u0011B!\u0003'\u0003\u0003\u0005\r!a\b\u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0003\u0010A!\u0011\u0011\u0011B\t\u0013\u0011\u0011\u0019\"a!\u0003\r=\u0013'.Z2u\u0001")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/Replacement.class */
public class Replacement implements Product, Serializable {
    private final String text;
    private final Option<PDFont> font;
    private final Option<Object> fontSize;
    private final Option<Point2D> position;
    private final Option<PDColor> color;
    private final boolean fauxItalic;
    private final Option<Object> lineHeight;

    public static Option<Tuple7<String, Option<PDFont>, Option<Object>, Option<Point2D>, Option<PDColor>, Object, Option<Object>>> unapply(final Replacement x$0) {
        return Replacement$.MODULE$.unapply(x$0);
    }

    public static Replacement apply(final String text, final Option<PDFont> font, final Option<Object> fontSize, final Option<Point2D> position, final Option<PDColor> color, final boolean fauxItalic, final Option<Object> lineHeight) {
        return Replacement$.MODULE$.apply(text, font, fontSize, position, color, fauxItalic, lineHeight);
    }

    public static Function1<Tuple7<String, Option<PDFont>, Option<Object>, Option<Point2D>, Option<PDColor>, Object, Option<Object>>, Replacement> tupled() {
        return Replacement$.MODULE$.tupled();
    }

    public static Function1<String, Function1<Option<PDFont>, Function1<Option<Object>, Function1<Option<Point2D>, Function1<Option<PDColor>, Function1<Object, Function1<Option<Object>, Replacement>>>>>>> curried() {
        return Replacement$.MODULE$.curried();
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String text() {
        return this.text;
    }

    public Replacement copy(final String text, final Option<PDFont> font, final Option<Object> fontSize, final Option<Point2D> position, final Option<PDColor> color, final boolean fauxItalic, final Option<Object> lineHeight) {
        return new Replacement(text, font, fontSize, position, color, fauxItalic, lineHeight);
    }

    public String copy$default$1() {
        return text();
    }

    public String productPrefix() {
        return "Replacement";
    }

    public int productArity() {
        return 7;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return text();
            case 1:
                return font();
            case 2:
                return fontSize();
            case 3:
                return position();
            case 4:
                return color();
            case 5:
                return BoxesRunTime.boxToBoolean(fauxItalic());
            case 6:
                return lineHeight();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof Replacement;
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
                return "position";
            case 4:
                return "color";
            case 5:
                return "fauxItalic";
            case 6:
                return "lineHeight";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return Statics.finalizeHash(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(-889275714, productPrefix().hashCode()), Statics.anyHash(text())), Statics.anyHash(font())), Statics.anyHash(fontSize())), Statics.anyHash(position())), Statics.anyHash(color())), fauxItalic() ? 1231 : 1237), Statics.anyHash(lineHeight())), 7);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof Replacement) {
                Replacement replacement = (Replacement) x$1;
                if (fauxItalic() == replacement.fauxItalic()) {
                    String strText = text();
                    String strText2 = replacement.text();
                    if (strText != null ? strText.equals(strText2) : strText2 == null) {
                        Option<PDFont> optionFont = font();
                        Option<PDFont> optionFont2 = replacement.font();
                        if (optionFont != null ? optionFont.equals(optionFont2) : optionFont2 == null) {
                            Option<Object> optionFontSize = fontSize();
                            Option<Object> optionFontSize2 = replacement.fontSize();
                            if (optionFontSize != null ? optionFontSize.equals(optionFontSize2) : optionFontSize2 == null) {
                                Option<Point2D> optionPosition = position();
                                Option<Point2D> optionPosition2 = replacement.position();
                                if (optionPosition != null ? optionPosition.equals(optionPosition2) : optionPosition2 == null) {
                                    Option<PDColor> optionColor = color();
                                    Option<PDColor> optionColor2 = replacement.color();
                                    if (optionColor != null ? optionColor.equals(optionColor2) : optionColor2 == null) {
                                        Option<Object> optionLineHeight = lineHeight();
                                        Option<Object> optionLineHeight2 = replacement.lineHeight();
                                        if (optionLineHeight != null ? optionLineHeight.equals(optionLineHeight2) : optionLineHeight2 == null) {
                                            if (replacement.canEqual(this)) {
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

    public Replacement(final String text, final Option<PDFont> font, final Option<Object> fontSize, final Option<Point2D> position, final Option<PDColor> color, final boolean fauxItalic, final Option<Object> lineHeight) {
        this.text = text;
        this.font = font;
        this.fontSize = fontSize;
        this.position = position;
        this.color = color;
        this.fauxItalic = fauxItalic;
        this.lineHeight = lineHeight;
        Product.$init$(this);
    }

    public Option<PDFont> font() {
        return this.font;
    }

    public Option<PDFont> copy$default$2() {
        return font();
    }

    public Option<Object> fontSize() {
        return this.fontSize;
    }

    public Option<Object> copy$default$3() {
        return fontSize();
    }

    public Option<Point2D> position() {
        return this.position;
    }

    public Option<Point2D> copy$default$4() {
        return position();
    }

    public Option<PDColor> color() {
        return this.color;
    }

    public Option<PDColor> copy$default$5() {
        return color();
    }

    public boolean fauxItalic() {
        return this.fauxItalic;
    }

    public boolean copy$default$6() {
        return fauxItalic();
    }

    public Option<Object> lineHeight() {
        return this.lineHeight;
    }

    public Option<Object> copy$default$7() {
        return lineHeight();
    }
}
