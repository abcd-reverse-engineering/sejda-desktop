package code.sejda.tasks.common;

import code.sejda.tasks.common.text.PdfTextRedactingStreamEngine;
import java.awt.Point;
import java.io.Serializable;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.font.PDFont;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.pdmodel.graphics.state.RenderingMode;
import scala.Option;
import scala.Product;
import scala.Tuple7;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: PageTextRedactor.scala */
@ScalaSignature(bytes = "\u0006\u0005\tub\u0001B\u0015+\u0001NB\u0001\"\u0013\u0001\u0003\u0016\u0004%\tA\u0013\u0005\t'\u0002\u0011\t\u0012)A\u0005\u0017\"AA\u000b\u0001BK\u0002\u0013\u0005Q\u000b\u0003\u0005b\u0001\tE\t\u0015!\u0003W\u0011!\u0011\u0007A!f\u0001\n\u0003\u0019\u0007\u0002C9\u0001\u0005#\u0005\u000b\u0011\u00023\t\u0011I\u0004!Q3A\u0005\u0002MD\u0001\u0002\u001f\u0001\u0003\u0012\u0003\u0006I\u0001\u001e\u0005\ts\u0002\u0011)\u001a!C\u0001u\"I\u0011q\u0001\u0001\u0003\u0012\u0003\u0006Ia\u001f\u0005\u000b\u0003\u0013\u0001!Q3A\u0005\u0002\u0005-\u0001BCA\u000e\u0001\tE\t\u0015!\u0003\u0002\u000e!Q\u0011Q\u0004\u0001\u0003\u0016\u0004%\t!a\b\t\u0015\u0005\u001d\u0002A!E!\u0002\u0013\t\t\u0003C\u0004\u0002*\u0001!\t!a\u000b\t\u000f\u0005}\u0002\u0001\"\u0001\u0002B!I\u0011\u0011\u000b\u0001\u0002\u0002\u0013\u0005\u00111\u000b\u0005\n\u0003G\u0002\u0011\u0013!C\u0001\u0003KB\u0011\"a\u001f\u0001#\u0003%\t!! \t\u0013\u0005\u0005\u0005!%A\u0005\u0002\u0005\r\u0005\"CAD\u0001E\u0005I\u0011AAE\u0011%\ti\tAI\u0001\n\u0003\ty\tC\u0005\u0002\u0014\u0002\t\n\u0011\"\u0001\u0002\u0016\"I\u0011\u0011\u0014\u0001\u0012\u0002\u0013\u0005\u00111\u0014\u0005\n\u0003?\u0003\u0011\u0011!C!\u0003CC\u0011\"!,\u0001\u0003\u0003%\t!a,\t\u0013\u0005]\u0006!!A\u0005\u0002\u0005e\u0006\"CAc\u0001\u0005\u0005I\u0011IAd\u0011%\t)\u000eAA\u0001\n\u0003\t9\u000eC\u0005\u0002\\\u0002\t\t\u0011\"\u0011\u0002^\"I\u0011\u0011\u001d\u0001\u0002\u0002\u0013\u0005\u00131\u001d\u0005\n\u0003K\u0004\u0011\u0011!C!\u0003OD\u0011\"!;\u0001\u0003\u0003%\t%a;\b\u000f\u0005=(\u0006#\u0001\u0002r\u001a1\u0011F\u000bE\u0001\u0003gDq!!\u000b$\t\u0003\ty\u0010C\u0004\u0003\u0002\r\"\tAa\u0001\t\u0013\t\u00051%!A\u0005\u0002\nM\u0001\"\u0003B\u0012G\u0005\u0005I\u0011\u0011B\u0013\u0011%\u0011\u0019dIA\u0001\n\u0013\u0011)DA\bSK\u0012\f7\r^5p]J+7/\u001e7u\u0015\tYC&\u0001\u0004d_6lwN\u001c\u0006\u0003[9\nQ\u0001^1tWNT!a\f\u0019\u0002\u000bM,'\u000eZ1\u000b\u0003E\nAaY8eK\u000e\u00011\u0003\u0002\u00015uu\u0002\"!\u000e\u001d\u000e\u0003YR\u0011aN\u0001\u0006g\u000e\fG.Y\u0005\u0003sY\u0012a!\u00118z%\u00164\u0007CA\u001b<\u0013\tadGA\u0004Qe>$Wo\u0019;\u0011\u0005y2eBA E\u001d\t\u00015)D\u0001B\u0015\t\u0011%'\u0001\u0004=e>|GOP\u0005\u0002o%\u0011QIN\u0001\ba\u0006\u001c7.Y4f\u0013\t9\u0005J\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u0002Fm\u0005!A/\u001a=u+\u0005Y\u0005C\u0001'Q\u001d\tie\n\u0005\u0002Am%\u0011qJN\u0001\u0007!J,G-\u001a4\n\u0005E\u0013&AB*ue&twM\u0003\u0002Pm\u0005)A/\u001a=uA\u0005A\u0001o\\:ji&|g.F\u0001W!\r)t+W\u0005\u00031Z\u0012aa\u00149uS>t\u0007C\u0001.`\u001b\u0005Y&B\u0001/^\u0003\r\tw\u000f\u001e\u0006\u0002=\u0006!!.\u0019<b\u0013\t\u00017LA\u0003Q_&tG/A\u0005q_NLG/[8oA\u0005!am\u001c8u+\u0005!\u0007cA\u001bXKB\u0011am\\\u0007\u0002O*\u0011!\r\u001b\u0006\u0003S*\fq\u0001\u001d3n_\u0012,GN\u0003\u0002lY\u000611/Y7c_bT!aL7\u000b\u00039\f1a\u001c:h\u0013\t\u0001xM\u0001\u0004Q\t\u001a{g\u000e^\u0001\u0006M>tG\u000fI\u0001\tM>tGoU5{KV\tA\u000fE\u00026/V\u0004\"!\u000e<\n\u0005]4$A\u0002#pk\ndW-A\u0005g_:$8+\u001b>fA\u0005)1m\u001c7peV\t1\u0010E\u00026/r\u00042!`A\u0002\u001b\u0005q(BA=��\u0015\r\t\t\u0001[\u0001\tOJ\f\u0007\u000f[5dg&\u0019\u0011Q\u0001@\u0003\u000fA#5i\u001c7pe\u000611m\u001c7pe\u0002\nQB]3oI\u0016\u0014\u0018N\\4N_\u0012,WCAA\u0007!\u0011)t+a\u0004\u0011\t\u0005E\u0011qC\u0007\u0003\u0003'Q1!!\u0006��\u0003\u0015\u0019H/\u0019;f\u0013\u0011\tI\"a\u0005\u0003\u001bI+g\u000eZ3sS:<Wj\u001c3f\u00039\u0011XM\u001c3fe&tw-T8eK\u0002\nA\u0003^3yi\u001a{WO\u001c3B]\u0012\u0014V\rZ1di\u0016$WCAA\u0011!\r)\u00141E\u0005\u0004\u0003K1$a\u0002\"p_2,\u0017M\\\u0001\u0016i\u0016DHOR8v]\u0012\fe\u000e\u001a*fI\u0006\u001cG/\u001a3!\u0003\u0019a\u0014N\\5u}Q\u0001\u0012QFA\u0019\u0003g\t)$a\u000e\u0002:\u0005m\u0012Q\b\t\u0004\u0003_\u0001Q\"\u0001\u0016\t\u000b%{\u0001\u0019A&\t\u000bQ{\u0001\u0019\u0001,\t\u000b\t|\u0001\u0019\u00013\t\u000bI|\u0001\u0019\u0001;\t\u000be|\u0001\u0019A>\t\u000f\u0005%q\u00021\u0001\u0002\u000e!9\u0011QD\bA\u0002\u0005\u0005\u0012a\u0003;p!\u0006<W\rU8j]R$2AVA\"\u0011\u001d\t)\u0005\u0005a\u0001\u0003\u000f\n\u0001\u0002]1hKNK'0\u001a\t\u0005\u0003\u0013\ni%\u0004\u0002\u0002L)\u00111\u0006[\u0005\u0005\u0003\u001f\nYEA\u0006Q\tJ+7\r^1oO2,\u0017\u0001B2paf$\u0002#!\f\u0002V\u0005]\u0013\u0011LA.\u0003;\ny&!\u0019\t\u000f%\u000b\u0002\u0013!a\u0001\u0017\"9A+\u0005I\u0001\u0002\u00041\u0006b\u00022\u0012!\u0003\u0005\r\u0001\u001a\u0005\beF\u0001\n\u00111\u0001u\u0011\u001dI\u0018\u0003%AA\u0002mD\u0011\"!\u0003\u0012!\u0003\u0005\r!!\u0004\t\u0013\u0005u\u0011\u0003%AA\u0002\u0005\u0005\u0012AD2paf$C-\u001a4bk2$H%M\u000b\u0003\u0003OR3aSA5W\t\tY\u0007\u0005\u0003\u0002n\u0005]TBAA8\u0015\u0011\t\t(a\u001d\u0002\u0013Ut7\r[3dW\u0016$'bAA;m\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005e\u0014q\u000e\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017AD2paf$C-\u001a4bk2$HEM\u000b\u0003\u0003\u007fR3AVA5\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIM*\"!!\"+\u0007\u0011\fI'\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001b\u0016\u0005\u0005-%f\u0001;\u0002j\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012*TCAAIU\rY\u0018\u0011N\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00137+\t\t9J\u000b\u0003\u0002\u000e\u0005%\u0014AD2paf$C-\u001a4bk2$HeN\u000b\u0003\u0003;SC!!\t\u0002j\u0005i\u0001O]8ek\u000e$\bK]3gSb,\"!a)\u0011\t\u0005\u0015\u00161V\u0007\u0003\u0003OS1!!+^\u0003\u0011a\u0017M\\4\n\u0007E\u000b9+\u0001\u0007qe>$Wo\u0019;Be&$\u00180\u0006\u0002\u00022B\u0019Q'a-\n\u0007\u0005UfGA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0003\u0002<\u0006\u0005\u0007cA\u001b\u0002>&\u0019\u0011q\u0018\u001c\u0003\u0007\u0005s\u0017\u0010C\u0005\u0002Dn\t\t\u00111\u0001\u00022\u0006\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\"!!3\u0011\r\u0005-\u0017\u0011[A^\u001b\t\tiMC\u0002\u0002PZ\n!bY8mY\u0016\u001cG/[8o\u0013\u0011\t\u0019.!4\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0003C\tI\u000eC\u0005\u0002Dv\t\t\u00111\u0001\u0002<\u0006\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\u0011\t\u0019+a8\t\u0013\u0005\rg$!AA\u0002\u0005E\u0016\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0005\u0005E\u0016\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\u0005\r\u0016AB3rk\u0006d7\u000f\u0006\u0003\u0002\"\u00055\b\"CAbC\u0005\u0005\t\u0019AA^\u0003=\u0011V\rZ1di&|gNU3tk2$\bcAA\u0018GM!1\u0005NA{!\u0011\t90!@\u000e\u0005\u0005e(bAA~;\u0006\u0011\u0011n\\\u0005\u0004\u000f\u0006eHCAAy\u0003\u0015\t\u0007\u000f\u001d7z)\u0011\tiC!\u0002\t\u000f\t\u001dQ\u00051\u0001\u0003\n\u00051QM\\4j]\u0016\u0004BAa\u0003\u0003\u00105\u0011!Q\u0002\u0006\u0003\u0013*JAA!\u0005\u0003\u000e\ta\u0002\u000b\u001a4UKb$(+\u001a3bGRLgnZ*ue\u0016\fW.\u00128hS:,G\u0003EA\u0017\u0005+\u00119B!\u0007\u0003\u001c\tu!q\u0004B\u0011\u0011\u0015Ie\u00051\u0001L\u0011\u0015!f\u00051\u0001W\u0011\u0015\u0011g\u00051\u0001e\u0011\u0015\u0011h\u00051\u0001u\u0011\u0015Ih\u00051\u0001|\u0011\u001d\tIA\na\u0001\u0003\u001bAq!!\b'\u0001\u0004\t\t#A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\t\u001d\"q\u0006\t\u0005k]\u0013I\u0003\u0005\u00076\u0005WYe\u000b\u001a;|\u0003\u001b\t\t#C\u0002\u0003.Y\u0012a\u0001V;qY\u0016<\u0004\"\u0003B\u0019O\u0005\u0005\t\u0019AA\u0017\u0003\rAH\u0005M\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0005o\u0001B!!*\u0003:%!!1HAT\u0005\u0019y%M[3di\u0002")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/RedactionResult.class */
public class RedactionResult implements Product, Serializable {
    private final String text;
    private final Option<Point> position;
    private final Option<PDFont> font;
    private final Option<Object> fontSize;
    private final Option<PDColor> color;
    private final Option<RenderingMode> renderingMode;
    private final boolean textFoundAndRedacted;

    public static Option<Tuple7<String, Option<Point>, Option<PDFont>, Option<Object>, Option<PDColor>, Option<RenderingMode>, Object>> unapply(final RedactionResult x$0) {
        return RedactionResult$.MODULE$.unapply(x$0);
    }

    public static RedactionResult apply(final String text, final Option<Point> position, final Option<PDFont> font, final Option<Object> fontSize, final Option<PDColor> color, final Option<RenderingMode> renderingMode, final boolean textFoundAndRedacted) {
        return RedactionResult$.MODULE$.apply(text, position, font, fontSize, color, renderingMode, textFoundAndRedacted);
    }

    public static RedactionResult apply(final PdfTextRedactingStreamEngine engine) {
        return RedactionResult$.MODULE$.apply(engine);
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String text() {
        return this.text;
    }

    public RedactionResult copy(final String text, final Option<Point> position, final Option<PDFont> font, final Option<Object> fontSize, final Option<PDColor> color, final Option<RenderingMode> renderingMode, final boolean textFoundAndRedacted) {
        return new RedactionResult(text, position, font, fontSize, color, renderingMode, textFoundAndRedacted);
    }

    public String copy$default$1() {
        return text();
    }

    public String productPrefix() {
        return "RedactionResult";
    }

    public int productArity() {
        return 7;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return text();
            case 1:
                return position();
            case 2:
                return font();
            case 3:
                return fontSize();
            case 4:
                return color();
            case 5:
                return renderingMode();
            case 6:
                return BoxesRunTime.boxToBoolean(textFoundAndRedacted());
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof RedactionResult;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "text";
            case 1:
                return "position";
            case 2:
                return "font";
            case 3:
                return "fontSize";
            case 4:
                return "color";
            case 5:
                return "renderingMode";
            case 6:
                return "textFoundAndRedacted";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return Statics.finalizeHash(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(-889275714, productPrefix().hashCode()), Statics.anyHash(text())), Statics.anyHash(position())), Statics.anyHash(font())), Statics.anyHash(fontSize())), Statics.anyHash(color())), Statics.anyHash(renderingMode())), textFoundAndRedacted() ? 1231 : 1237), 7);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof RedactionResult) {
                RedactionResult redactionResult = (RedactionResult) x$1;
                if (textFoundAndRedacted() == redactionResult.textFoundAndRedacted()) {
                    String strText = text();
                    String strText2 = redactionResult.text();
                    if (strText != null ? strText.equals(strText2) : strText2 == null) {
                        Option<Point> optionPosition = position();
                        Option<Point> optionPosition2 = redactionResult.position();
                        if (optionPosition != null ? optionPosition.equals(optionPosition2) : optionPosition2 == null) {
                            Option<PDFont> optionFont = font();
                            Option<PDFont> optionFont2 = redactionResult.font();
                            if (optionFont != null ? optionFont.equals(optionFont2) : optionFont2 == null) {
                                Option<Object> optionFontSize = fontSize();
                                Option<Object> optionFontSize2 = redactionResult.fontSize();
                                if (optionFontSize != null ? optionFontSize.equals(optionFontSize2) : optionFontSize2 == null) {
                                    Option<PDColor> optionColor = color();
                                    Option<PDColor> optionColor2 = redactionResult.color();
                                    if (optionColor != null ? optionColor.equals(optionColor2) : optionColor2 == null) {
                                        Option<RenderingMode> optionRenderingMode = renderingMode();
                                        Option<RenderingMode> optionRenderingMode2 = redactionResult.renderingMode();
                                        if (optionRenderingMode != null ? optionRenderingMode.equals(optionRenderingMode2) : optionRenderingMode2 == null) {
                                            if (redactionResult.canEqual(this)) {
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

    public RedactionResult(final String text, final Option<Point> position, final Option<PDFont> font, final Option<Object> fontSize, final Option<PDColor> color, final Option<RenderingMode> renderingMode, final boolean textFoundAndRedacted) {
        this.text = text;
        this.position = position;
        this.font = font;
        this.fontSize = fontSize;
        this.color = color;
        this.renderingMode = renderingMode;
        this.textFoundAndRedacted = textFoundAndRedacted;
        Product.$init$(this);
    }

    public Option<Point> position() {
        return this.position;
    }

    public Option<Point> copy$default$2() {
        return position();
    }

    public Option<PDFont> font() {
        return this.font;
    }

    public Option<PDFont> copy$default$3() {
        return font();
    }

    public Option<Object> fontSize() {
        return this.fontSize;
    }

    public Option<Object> copy$default$4() {
        return fontSize();
    }

    public Option<PDColor> color() {
        return this.color;
    }

    public Option<PDColor> copy$default$5() {
        return color();
    }

    public Option<RenderingMode> renderingMode() {
        return this.renderingMode;
    }

    public Option<RenderingMode> copy$default$6() {
        return renderingMode();
    }

    public boolean textFoundAndRedacted() {
        return this.textFoundAndRedacted;
    }

    public boolean copy$default$7() {
        return textFoundAndRedacted();
    }

    public Option<Point> toPagePoint(final PDRectangle pageSize) {
        return position().map(p -> {
            return new Point((int) p.getX(), ((int) pageSize.getHeight()) - ((int) p.getY()));
        });
    }
}
