package code.sejda.tasks.common;

import code.sejda.tasks.edit.AppendFormFieldOperation;
import code.sejda.tasks.edit.FormFieldAlign;
import code.sejda.tasks.edit.FormFieldAlign$Center$;
import code.sejda.tasks.edit.FormFieldAlign$Left$;
import code.sejda.tasks.edit.FormFieldAlign$Right$;
import code.sejda.tasks.edit.FormFieldType;
import code.sejda.tasks.edit.FormFieldType$Checkbox$;
import code.sejda.tasks.edit.FormFieldType$Radio$;
import code.sejda.tasks.edit.FormFieldType$Text$;
import code.util.ImplicitJavaConversions$;
import code.util.Loggable;
import code.util.StringHelpers$;
import code.util.pdf.ObjIdUtils$;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import org.sejda.commons.util.StringUtils;
import org.sejda.impl.sambox.component.PageTextWriter;
import org.sejda.impl.sambox.util.FontUtils;
import org.sejda.model.RectangularBox;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSFloat;
import org.sejda.sambox.cos.COSInteger;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.PDPageContentStream;
import org.sejda.sambox.pdmodel.PDResources;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.font.PDFont;
import org.sejda.sambox.pdmodel.font.PDType1Font;
import org.sejda.sambox.pdmodel.graphics.blend.BlendMode;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.pdmodel.graphics.color.PDDeviceRGB;
import org.sejda.sambox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAppearanceCharacteristicsDictionary;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAppearanceDictionary;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAppearanceEntry;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAppearanceStream;
import org.sejda.sambox.pdmodel.interactive.annotation.PDBorderStyleDictionary;
import org.sejda.sambox.pdmodel.interactive.form.PDAcroForm;
import org.sejda.sambox.pdmodel.interactive.form.PDButton;
import org.sejda.sambox.pdmodel.interactive.form.PDCheckBox;
import org.sejda.sambox.pdmodel.interactive.form.PDChoice;
import org.sejda.sambox.pdmodel.interactive.form.PDComboBox;
import org.sejda.sambox.pdmodel.interactive.form.PDField;
import org.sejda.sambox.pdmodel.interactive.form.PDFieldFactory;
import org.sejda.sambox.pdmodel.interactive.form.PDListBox;
import org.sejda.sambox.pdmodel.interactive.form.PDNonTerminalField;
import org.sejda.sambox.pdmodel.interactive.form.PDRadioButton;
import org.sejda.sambox.pdmodel.interactive.form.PDSignatureField;
import org.sejda.sambox.pdmodel.interactive.form.PDTerminalField;
import org.sejda.sambox.pdmodel.interactive.form.PDTextField;
import org.sejda.sambox.pdmodel.interactive.form.PDVariableText;
import org.sejda.sambox.util.Matrix;
import org.slf4j.Logger;
import scala.$less$colon$less$;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Option$;
import scala.Predef$;
import scala.Product;
import scala.Some;
import scala.Tuple2;
import scala.collection.IterableOnceOps;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.JavaConverters$;
import scala.collection.StringOps$;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Set;
import scala.collection.mutable.Buffer;
import scala.collection.mutable.ListBuffer;
import scala.collection.mutable.ListBuffer$;
import scala.reflect.ScalaSignature;
import scala.runtime.BooleanRef;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ObjectRef;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.control.NonFatal$;

/* compiled from: AcroFormsHelper.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0015me\u0001\u00021b\u0001)D\u0001b\u001e\u0001\u0003\u0002\u0003\u0006I\u0001\u001f\u0005\b\u0003\u000f\u0001A\u0011AA\u0005\u0011%\t\t\u0002\u0001a\u0001\n\u0003\t\u0019\u0002C\u0005\u0002&\u0001\u0001\r\u0011\"\u0001\u0002(!A\u00111\u0007\u0001!B\u0013\t)\u0002C\u0005\u00026\u0001\u0011\r\u0011\"\u0001\u00028!A\u0011\u0011\n\u0001!\u0002\u0013\tID\u0002\u0004\u0002L\u0001\u0001\u0015Q\n\u0005\u000b\u0003[B!Q3A\u0005\u0002\u0005=\u0004BCA<\u0011\tE\t\u0015!\u0003\u0002r!9\u0011q\u0001\u0005\u0005\u0002\u0005e\u0004bBAA\u0011\u0011\u0005\u00111\u0011\u0005\n\u0003GC\u0011\u0011!C\u0001\u0003KC\u0011\"!+\t#\u0003%\t!a+\t\u0013\u0005\u0005\u0007\"!A\u0005B\u0005\r\u0007\"CAk\u0011\u0005\u0005I\u0011AAl\u0011%\ty\u000eCA\u0001\n\u0003\t\t\u000fC\u0005\u0002l\"\t\t\u0011\"\u0011\u0002n\"I\u00111 \u0005\u0002\u0002\u0013\u0005\u0011Q \u0005\n\u0005\u000fA\u0011\u0011!C!\u0005\u0013A\u0011B!\u0004\t\u0003\u0003%\tEa\u0004\t\u0013\tE\u0001\"!A\u0005B\tM\u0001\"\u0003B\u000b\u0011\u0005\u0005I\u0011\tB\f\u000f%\u0011Y\u0002AA\u0001\u0012\u0003\u0011iBB\u0005\u0002L\u0001\t\t\u0011#\u0001\u0003 !9\u0011qA\r\u0005\u0002\t]\u0002\"\u0003B\t3\u0005\u0005IQ\tB\n\u0011%\u0011I$GA\u0001\n\u0003\u0013Y\u0004C\u0005\u0003@e\t\t\u0011\"!\u0003B!9!Q\n\u0001\u0005\f\t=\u0003b\u0002B*\u0001\u0011-!Q\u000b\u0005\b\u0005?\u0002A\u0011\u0002B1\u0011%\u0011\t\nAI\u0001\n\u0013\u0011\u0019\nC\u0004\u0003\u0018\u0002!IA!'\t\u000f\t=\u0006\u0001\"\u0003\u00032\"9!1\u0018\u0001\u0005\n\tu\u0006b\u0002Bb\u0001\u0011-!Q\u0019\u0005\u000b\u0005_\u0004\u0001R1A\u0005\u0002\tE\bB\u0003B{\u0001!\u0015\r\u0011\"\u0001\u0003x\"91\u0011\u0001\u0001\u0005\u0002\r\r\u0001bBB\u0003\u0001\u0011\u000511\u0001\u0005\b\u0007\u000f\u0001A\u0011BB\u0005\u0011\u001d\u0019\u0019\u0002\u0001C\u0001\u0007+Aqa!\b\u0001\t\u0003\u0019y\u0002C\u0004\u0004$\u0001!\ta!\n\t\u000f\r\u001d\u0002\u0001\"\u0001\u0004*!91q\u0006\u0001\u0005\u0002\rE\u0002bBB\u001d\u0001\u0011\u000511\b\u0005\b\u00077\u0002A\u0011AB/\u0011\u001d\u0019\t\u0007\u0001C\u0005\u0007GBqaa\u001e\u0001\t\u0013\u0019I\bC\u0005\u0004��\u0001\u0011\r\u0011\"\u0001\u0004\u0002\"A1q\u0012\u0001!\u0002\u0013\u0019\u0019\tC\u0004\u0004\u0012\u0002!Iaa%\t\u000f\r]\u0005\u0001\"\u0001\u0004\u001a\"91Q\u0015\u0001\u0005\u0002\r\u001d\u0006bBBY\u0001\u0011\u000511\u0017\u0005\b\u0007{\u0003A\u0011AB`\u0011\u001d\u0019I\r\u0001C\u0001\u0007\u0017Dqa!6\u0001\t\u0003\u00199\u000eC\u0004\u0004b\u0002!\taa9\t\u000f\u0011U\u0001\u0001\"\u0001\u0005\u0018!9A\u0011\u0006\u0001\u0005\n\u0011-\u0002\"\u0003C.\u0001E\u0005I\u0011\u0002C/\u0011\u001d!\t\u0007\u0001C\u0005\tGBq\u0001\"!\u0001\t\u0013!\u0019\tC\u0004\u0005\u0016\u0002!I\u0001b&\t\u0013\u0011\u0015\u0006!%A\u0005\n\u0011\u001d\u0006b\u0002CV\u0001\u0011%AQ\u0016\u0005\b\to\u0003A\u0011\u0002C]\u0011%!I\rAI\u0001\n\u0013!9\u000bC\u0004\u0005L\u0002!I\u0001\"4\t\u000f\u0011e\u0007\u0001\"\u0003\u0005\\\"9Aq\u001d\u0001\u0005\n\u0011%\bb\u0002Cz\u0001\u0011%AQ\u001f\u0005\n\t\u007f\u0004!\u0019!C\u0005\u000b\u0003A\u0001\"\"\u0003\u0001A\u0003%Q1\u0001\u0005\b\u000b\u0017\u0001A\u0011BC\u0007\u0011\u001d)\t\u0002\u0001C\u0001\u000b'Aq!b\u0006\u0001\t\u0013)I\u0002C\u0005\u00066\u0001\t\n\u0011\"\u0003\u00068!9Q1\b\u0001\u0005\u0002\u0015u\u0002bBC \u0001\u0011\u000511\u0001\u0005\b\u000b\u0003\u0002A\u0011BC\"\u0011\u001d)I\u0005\u0001C\u0001\u000b\u0017Bq!\"\u0014\u0001\t\u0003)y\u0005C\u0004\u0006R\u0001!\t!b\u0015\t\u000f\u0015]\u0003\u0001\"\u0001\u0006Z!IQ\u0011\r\u0001\u0012\u0002\u0013\u0005Q1\r\u0005\b\u000bO\u0002A\u0011AC5\u0011\u001d)\t\b\u0001C\u0001\u000bg:q!\"\"b\u0011\u0003)9I\u0002\u0004aC\"\u0005Q\u0011\u0012\u0005\b\u0003\u000fiF\u0011ACF\u0011\u001d)i)\u0018C\u0001\u000b\u001f\u0013q\"Q2s_\u001a{'/\\:IK2\u0004XM\u001d\u0006\u0003E\u000e\faaY8n[>t'B\u00013f\u0003\u0015!\u0018m]6t\u0015\t1w-A\u0003tK*$\u0017MC\u0001i\u0003\u0011\u0019w\u000eZ3\u0004\u0001M\u0019\u0001a[9\u0011\u00051|W\"A7\u000b\u00039\fQa]2bY\u0006L!\u0001]7\u0003\r\u0005s\u0017PU3g!\t\u0011X/D\u0001t\u0015\t!x-\u0001\u0003vi&d\u0017B\u0001<t\u0005!aunZ4bE2,\u0017a\u00013pGB\u0019\u00110a\u0001\u000e\u0003iT!a\u001f?\u0002\u000fA$Wn\u001c3fY*\u0011QP`\u0001\u0007g\u0006l'm\u001c=\u000b\u0005\u0019|(BAA\u0001\u0003\ry'oZ\u0005\u0004\u0003\u000bQ(A\u0003)E\t>\u001cW/\\3oi\u00061A(\u001b8jiz\"B!a\u0003\u0002\u0010A\u0019\u0011Q\u0002\u0001\u000e\u0003\u0005DQa\u001e\u0002A\u0002a\f\u0001\"Y2s_\u001a{'/\\\u000b\u0003\u0003+\u0001B!a\u0006\u0002\"5\u0011\u0011\u0011\u0004\u0006\u0005\u00037\ti\"\u0001\u0003g_Jl'bAA\u0010u\u0006Y\u0011N\u001c;fe\u0006\u001cG/\u001b<f\u0013\u0011\t\u0019#!\u0007\u0003\u0015A#\u0015i\u0019:p\r>\u0014X.\u0001\u0007bGJ|gi\u001c:n?\u0012*\u0017\u000f\u0006\u0003\u0002*\u0005=\u0002c\u00017\u0002,%\u0019\u0011QF7\u0003\tUs\u0017\u000e\u001e\u0005\n\u0003c!\u0011\u0011!a\u0001\u0003+\t1\u0001\u001f\u00132\u0003%\t7M]8G_Jl\u0007%\u0001\bQ\t~\u001bu\nT(S?\nc\u0015iQ&\u0016\u0005\u0005e\u0002\u0003BA\u001e\u0003\u000bj!!!\u0010\u000b\t\u0005}\u0012\u0011I\u0001\u0006G>dwN\u001d\u0006\u0004\u0003\u0007R\u0018\u0001C4sCBD\u0017nY:\n\t\u0005\u001d\u0013Q\b\u0002\b!\u0012\u001bu\u000e\\8s\u0003=\u0001FiX\"P\u0019>\u0013vL\u0011'B\u0007.\u0003#!\u0007)E!\u0006<WmQ8oi\u0016tGo\u0015;sK\u0006l\u0007+[7qK\u0012\u001cb\u0001C6\u0002P\u0005U\u0003c\u00017\u0002R%\u0019\u00111K7\u0003\u000fA\u0013x\u000eZ;diB!\u0011qKA4\u001d\u0011\tI&a\u0019\u000f\t\u0005m\u0013\u0011M\u0007\u0003\u0003;R1!a\u0018j\u0003\u0019a$o\\8u}%\ta.C\u0002\u0002f5\fq\u0001]1dW\u0006<W-\u0003\u0003\u0002j\u0005-$\u0001D*fe&\fG.\u001b>bE2,'bAA3[\u0006\u00111m]\u000b\u0003\u0003c\u00022!_A:\u0013\r\t)H\u001f\u0002\u0014!\u0012\u0003\u0016mZ3D_:$XM\u001c;TiJ,\u0017-\\\u0001\u0004GN\u0004C\u0003BA>\u0003\u007f\u00022!! \t\u001b\u0005\u0001\u0001bBA7\u0017\u0001\u0007\u0011\u0011O\u0001\niJ\fgn\u001d4pe6$b\"!\u000b\u0002\u0006\u0006=\u00151SAL\u00037\u000by\nC\u0004\u0002\b2\u0001\r!!#\u0002\u0003\u0005\u00042\u0001\\AF\u0013\r\ti)\u001c\u0002\u0007\t>,(\r\\3\t\u000f\u0005EE\u00021\u0001\u0002\n\u0006\t!\rC\u0004\u0002\u00162\u0001\r!!#\u0002\u0003\rDq!!'\r\u0001\u0004\tI)A\u0001e\u0011\u001d\ti\n\u0004a\u0001\u0003\u0013\u000b\u0011!\u001a\u0005\b\u0003Cc\u0001\u0019AAE\u0003\u00051\u0017\u0001B2paf$B!a\u001f\u0002(\"I\u0011QN\u0007\u0011\u0002\u0003\u0007\u0011\u0011O\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\t\tiK\u000b\u0003\u0002r\u0005=6FAAY!\u0011\t\u0019,!0\u000e\u0005\u0005U&\u0002BA\\\u0003s\u000b\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0007\u0005mV.\u0001\u0006b]:|G/\u0019;j_:LA!a0\u00026\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\t)\r\u0005\u0003\u0002H\u0006EWBAAe\u0015\u0011\tY-!4\u0002\t1\fgn\u001a\u0006\u0003\u0003\u001f\fAA[1wC&!\u00111[Ae\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5usV\u0011\u0011\u0011\u001c\t\u0004Y\u0006m\u0017bAAo[\n\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR!\u00111]Au!\ra\u0017Q]\u0005\u0004\u0003Ol'aA!os\"I\u0011\u0011G\t\u0002\u0002\u0003\u0007\u0011\u0011\\\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u0011q\u001e\t\u0007\u0003c\f90a9\u000e\u0005\u0005M(bAA{[\u0006Q1m\u001c7mK\u000e$\u0018n\u001c8\n\t\u0005e\u00181\u001f\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0003\u0002��\n\u0015\u0001c\u00017\u0003\u0002%\u0019!1A7\u0003\u000f\t{w\u000e\\3b]\"I\u0011\u0011G\n\u0002\u0002\u0003\u0007\u00111]\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\r\u0006\u0003\u0002F\n-\u0001\"CA\u0019)\u0005\u0005\t\u0019AAm\u0003!A\u0017m\u001d5D_\u0012,GCAAm\u0003!!xn\u0015;sS:<GCAAc\u0003\u0019)\u0017/^1mgR!\u0011q B\r\u0011%\t\tdFA\u0001\u0002\u0004\t\u0019/A\rQ\tB\u000bw-Z\"p]R,g\u000e^*ue\u0016\fW\u000eU5na\u0016$\u0007cAA?3M)\u0011D!\t\u0003.AA!1\u0005B\u0015\u0003c\nY(\u0004\u0002\u0003&)\u0019!qE7\u0002\u000fI,h\u000e^5nK&!!1\u0006B\u0013\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|g.\r\t\u0005\u0005_\u0011)$\u0004\u0002\u00032)!!1GAg\u0003\tIw.\u0003\u0003\u0002j\tEBC\u0001B\u000f\u0003\u0015\t\u0007\u000f\u001d7z)\u0011\tYH!\u0010\t\u000f\u00055D\u00041\u0001\u0002r\u00059QO\\1qa2LH\u0003\u0002B\"\u0005\u0013\u0002R\u0001\u001cB#\u0003cJ1Aa\u0012n\u0005\u0019y\u0005\u000f^5p]\"I!1J\u000f\u0002\u0002\u0003\u0007\u00111P\u0001\u0004q\u0012\u0002\u0014a\u00059j[B,GmQ8oi\u0016tGo\u0015;sK\u0006lG\u0003BA>\u0005#Bq!!\u001c\u001f\u0001\u0004\t\t(A\u0007e_V\u0014G.\u001a+p\r2|\u0017\r\u001e\u000b\u0005\u0005/\u0012i\u0006E\u0002m\u00053J1Aa\u0017n\u0005\u00151En\\1u\u0011\u001d\tIj\ba\u0001\u0003\u0013\u000b1c\u0018;p\u001d>tG+\u001a:nS:\fGNR5fY\u0012$bAa\u0019\u0003j\te\u0004\u0003BA\f\u0005KJAAa\u001a\u0002\u001a\t\u0011\u0002\u000b\u0012(p]R+'/\\5oC24\u0015.\u001a7e\u0011\u001d\u0011Y\u0007\ta\u0001\u0005[\nAAY1tKB!!q\u000eB;\u001b\t\u0011\tHC\u0002\u0003tq\f1aY8t\u0013\u0011\u00119H!\u001d\u0003\u000f\r{5KQ1tK\"I!1\u0010\u0011\u0011\u0002\u0003\u0007!QP\u0001\u000em&\u001c\u0018\u000e^3e\u001f\nT\u0017\nZ:\u0011\r\t}$q\u0011BG\u001d\u0011\u0011\tIa!\u0011\u0007\u0005mS.C\u0002\u0003\u00066\fa\u0001\u0015:fI\u00164\u0017\u0002\u0002BE\u0005\u0017\u00131aU3u\u0015\r\u0011))\u001c\t\u0005\u0005\u007f\u0012y)\u0003\u0003\u0002T\n-\u0015!H0u_:{g\u000eV3s[&t\u0017\r\u001c$jK2$G\u0005Z3gCVdG\u000f\n\u001a\u0016\u0005\tU%\u0006\u0002B?\u0003_\u000bq\u0001^8GS\u0016dG\r\u0006\u0003\u0003\u001c\n\u0005\u0006\u0003BA\f\u0005;KAAa(\u0002\u001a\t9\u0001\u000b\u0012$jK2$\u0007b\u0002BRE\u0001\u0007!QU\u0001\u0002oB!!q\u0015BV\u001b\t\u0011IK\u0003\u0003\u0002<\u0006u\u0011\u0002\u0002BW\u0005S\u0013!\u0003\u0015#B]:|G/\u0019;j_:<\u0016\u000eZ4fi\u0006Y1M]3bi\u00164\u0015.\u001a7e)\u0011\u0011YJa-\t\u000f\u0005e5\u00051\u0001\u00036B!!q\u000eB\\\u0013\u0011\u0011IL!\u001d\u0003\u001b\r{5\u000bR5di&|g.\u0019:z\u0003-!x\u000eU1sK:$x\n\u001d;\u0015\t\t}&\u0011\u0019\t\u0006Y\n\u0015#Q\u0017\u0005\b\u00033#\u0003\u0019\u0001B[\u00039\u0019X-\u001d\u001ankR\f'\r\\3TKF,BAa2\u0003ZR!!\u0011\u001aBs!\u0019\u0011YM!5\u0003V6\u0011!Q\u001a\u0006\u0005\u0005\u001f\f\u00190A\u0004nkR\f'\r\\3\n\t\tM'Q\u001a\u0002\u000b\u0019&\u001cHOQ;gM\u0016\u0014\b\u0003\u0002Bl\u00053d\u0001\u0001B\u0004\u0003\\\u0016\u0012\rA!8\u0003\u0003Q\u000bBAa8\u0002dB\u0019AN!9\n\u0007\t\rXNA\u0004O_RD\u0017N\\4\t\u000f\t\u001dX\u00051\u0001\u0003j\u0006\t1\u000f\u0005\u0004\u0002X\t-(Q[\u0005\u0005\u0005[\fYGA\u0002TKF\fqa^5eO\u0016$8/\u0006\u0002\u0003tB1!1\u001aBi\u0005K\u000baAZ5fY\u0012\u001cXC\u0001B}!\u0019\u0011YM!5\u0003|B9AN!@\u0003\u000e\nm\u0015b\u0001B��[\n1A+\u001e9mKJ\n1\u0002\u001d:j]R4\u0015.\u001a7egR\u0011\u0011\u0011F\u0001\fe\u0016\u001cX\r\u001e$jK2$7/\u0001\u0006jg\u0016#\u0017\u000e^1cY\u0016$B!a@\u0004\f!9\u0011\u0011\u0015\u0016A\u0002\r5\u0001\u0003BA\f\u0007\u001fIAa!\u0005\u0002\u001a\ty\u0001\u000b\u0012+fe6Lg.\u00197GS\u0016dG-\u0001\rgS:$W\tZ5uC\ndWMR5fY\u0012\u001c()\u001f(b[\u0016$Baa\u0006\u0004\u001aA1\u0011q\u000bBv\u0007\u001bAqaa\u0007,\u0001\u0004\u0011i)\u0001\u0003oC6,\u0017\u0001\u00054j]\u00124\u0015.\u001a7eg\nKh*Y7f)\u0011\u00199b!\t\t\u000f\rmA\u00061\u0001\u0003\u000e\u0006I\u0011\r\u001c7GS\u0016dGm\u001d\u000b\u0003\u0007/\t\u0011cZ3u/&$w-\u001a;t\u0005f|%M[%e)\u0011\u0011\u0019pa\u000b\t\u000f\r5b\u00061\u0001\u0003\u000e\u0006)qN\u00196JI\u0006\tb-\u001b8e\r&,G\u000e\u001a\"z/&$w-\u001a;\u0015\t\rM2Q\u0007\t\u0006Y\n\u0015#1\u0014\u0005\b\u0007oy\u0003\u0019\u0001BS\u0003\u00199\u0018\u000eZ4fi\u0006I\u0011\r\u001a3XS\u0012<W\r\u001e\u000b\t\u0005K\u001bid!\u0011\u0004R!91q\b\u0019A\u0002\r5\u0011!\u00024jK2$\u0007bBB\"a\u0001\u00071QI\u0001\n_B,'/\u0019;j_:\u0004Baa\u0012\u0004N5\u00111\u0011\n\u0006\u0004\u0007\u0017\u001a\u0017\u0001B3eSRLAaa\u0014\u0004J\tA\u0012\t\u001d9f]\u00124uN]7GS\u0016dGm\u00149fe\u0006$\u0018n\u001c8\t\u000f\rM\u0003\u00071\u0001\u0004V\u0005!\u0001/Y4f!\rI8qK\u0005\u0004\u00073R(A\u0002)E!\u0006<W-\u0001\u0007sK6|g/Z,jI\u001e,G\u000f\u0006\u0003\u0003t\u000e}\u0003bBB\u001cc\u0001\u0007!QU\u0001\u0014g\u0016$H+\u001a=u\u00032LwM\\(o\r&,G\u000e\u001a\u000b\u0007\u0003S\u0019)g!\u001c\t\u000f\r}\"\u00071\u0001\u0004hA!\u0011qCB5\u0013\u0011\u0019Y'!\u0007\u0003\u001dA#e+\u0019:jC\ndW\rV3yi\"91q\u000e\u001aA\u0002\rE\u0014!B1mS\u001et\u0007\u0003BB$\u0007gJAa!\u001e\u0004J\tqai\u001c:n\r&,G\u000eZ!mS\u001et\u0017\u0001F:fiR+\u0007\u0010^!mS\u001etwJ\\,jI\u001e,G\u000f\u0006\u0004\u0002*\rm4Q\u0010\u0005\b\u0007o\u0019\u0004\u0019\u0001BS\u0011\u001d\u0019yg\ra\u0001\u0007c\nAB\\;nE\u0016\u0014hi\u001c:nCR,\"aa!\u0011\t\r\u001551R\u0007\u0003\u0007\u000fSAa!#\u0002N\u0006!A/\u001a=u\u0013\u0011\u0019iia\"\u0003\u00199+XNY3s\r>\u0014X.\u0019;\u0002\u001b9,XNY3s\r>\u0014X.\u0019;!\u0003\r1W\u000e\u001e\u000b\u0005\u0003\u000b\u001c)\nC\u0004\u0002\"Z\u0002\rAa\u0016\u0002\u0011\r\u0014X-\u0019;f\t\u0006#\u0002\"!2\u0004\u001c\u000e}51\u0015\u0005\b\u0007;;\u0004\u0019\u0001BG\u0003!1wN\u001c;OC6,\u0007bBBQo\u0001\u0007\u0011\u0011R\u0001\tM>tGoU5{K\"9\u0011qH\u001cA\u0002\u0005e\u0012\u0001D1eIR+\u0007\u0010\u001e$jK2$G\u0003BBU\u0007_\u0003B!a\u0006\u0004,&!1QVA\r\u0005-\u0001F\tV3yi\u001aKW\r\u001c3\t\u000f\r\r\u0003\b1\u0001\u0004F\u0005\u0001\u0012\r\u001a3Ee>\u0004Hm\\<o\r&,G\u000e\u001a\u000b\u0005\u0007k\u001bY\f\u0005\u0003\u0002\u0018\r]\u0016\u0002BB]\u00033\u0011\u0001\u0002\u0015#DQ>L7-\u001a\u0005\b\u0007\u0007J\u0004\u0019AB#\u00035\tG\r\u001a*bI&|g)[3mIR!1\u0011YBd!\u0011\t9ba1\n\t\r\u0015\u0017\u0011\u0004\u0002\u000e!\u0012\u0013\u0016\rZ5p\u0005V$Ho\u001c8\t\u000f\r\r#\b1\u0001\u0004F\u0005\t\u0012\r\u001a3TS\u001et\u0017\r^;sK\u001aKW\r\u001c3\u0015\t\r571\u001b\t\u0005\u0003/\u0019y-\u0003\u0003\u0004R\u0006e!\u0001\u0005)E'&<g.\u0019;ve\u00164\u0015.\u001a7e\u0011\u001d\u0019\u0019e\u000fa\u0001\u0007\u000b\n\u0001#\u00193e\u0007\",7m\u001b2pq\u001aKW\r\u001c3\u0015\t\re7q\u001c\t\u0005\u0003/\u0019Y.\u0003\u0003\u0004^\u0006e!A\u0003)E\u0007\",7m\u001b\"pq\"911\t\u001fA\u0002\r\u0015\u0013\u0001D1eI&s7.T1sWV\u0004HCDA��\u0007K\u001c)pa>\u0004~\u0012\rA\u0011\u0003\u0005\b\u0007Ol\u0004\u0019ABu\u0003-\u0011w.\u001e8eS:<'i\u001c=\u0011\t\r-8\u0011_\u0007\u0003\u0007[T1aa<\u007f\u0003\u0015iw\u000eZ3m\u0013\u0011\u0019\u0019p!<\u0003\u001dI+7\r^1oOVd\u0017M\u001d\"pq\"911K\u001fA\u0002\rU\u0003bBB}{\u0001\u000711`\u0001\bS:\\G.[:u!\u0019\t9Fa;\u0002\n\"91q`\u001fA\u0002\u0011\u0005\u0011aC1qa\u0016\f'/\u00198dKN\u0004b!a\u0016\u0003l\u000em\bbBA {\u0001\u0007AQ\u0001\t\u0005\t\u000f!i!\u0004\u0002\u0005\n)!A1BAg\u0003\r\tw\u000f^\u0005\u0005\t\u001f!IAA\u0003D_2|'\u000fC\u0004\u0005\u0014u\u0002\rAa\u0016\u0002\u00111Lg.Z*ju\u0016\f\u0011#\u00193e\u0019&tW-\u00118o_R\fG/[8o)9\ty\u0010\"\u0007\u0005\u001c\u0011uA\u0011\u0005C\u0012\tKAqaa:?\u0001\u0004\u0019I\u000fC\u0004\u0004Ty\u0002\ra!\u0016\t\u000f\u0011}a\b1\u0001\u0004|\u00061\u0001o\\5oiNDq!a\u0010?\u0001\u0004!)\u0001C\u0004\u0005\u0014y\u0002\rAa\u0016\t\u000f\u0011\u001db\b1\u0001\u0003\u000e\u0006!1.\u001b8e\u0003A\t\u0007\u000f]3be\u0006t7-Z*ue\u0016\fW\u000e\u0006\u0006\u0005.\u0011}B1\tC$\t\u0017\"B\u0001b\f\u00056A!!q\u0015C\u0019\u0013\u0011!\u0019D!+\u0003%A#\u0015\t\u001d9fCJ\fgnY3TiJ,\u0017-\u001c\u0005\b\toy\u0004\u0019\u0001C\u001d\u0003\t1g\u000eE\u0004m\tw\t\t(!\u000b\n\u0007\u0011uRNA\u0005Gk:\u001cG/[8oc!9A\u0011I A\u0002\t]\u0013!B<jIRD\u0007b\u0002C#\u007f\u0001\u0007!qK\u0001\u0007Q\u0016Lw\r\u001b;\t\u000f\u0011%s\b1\u0001\u0002Z\u0006A!o\u001c;bi&|g\u000eC\u0005\u0005N}\u0002\n\u00111\u0001\u0005P\u0005I!\r\\3oI6{G-\u001a\t\u0005\t#\"9&\u0004\u0002\u0005T)!AQKA!\u0003\u0015\u0011G.\u001a8e\u0013\u0011!I\u0006b\u0015\u0003\u0013\tcWM\u001c3N_\u0012,\u0017AG1qa\u0016\f'/\u00198dKN#(/Z1nI\u0011,g-Y;mi\u0012\"TC\u0001C0U\u0011!y%a,\u0002\u001f\r\fGnY;mCR,W*\u0019;sSb$b\u0001\"\u001a\u0005r\u0011}\u0004\u0003\u0002C4\t[j!\u0001\"\u001b\u000b\t\u0011-D\u0011B\u0001\u0005O\u0016|W.\u0003\u0003\u0005p\u0011%$aD!gM&tW\r\u0016:b]N4wN]7\t\u000f\u0011M\u0014\t1\u0001\u0005v\u0005!!MY8y!\u0011!9\bb\u001f\u000e\u0005\u0011e$B\u00012{\u0013\u0011!i\b\"\u001f\u0003\u0017A#%+Z2uC:<G.\u001a\u0005\b\t\u0013\n\u0005\u0019AAm\u0003M!'/Y<SC\u0012Lw.\u00168tK2,7\r^3e))\tI\u0003\"\"\u0005\b\u0012-Eq\u0012\u0005\b\u0003[\u0012\u0005\u0019AA9\u0011\u001d!II\u0011a\u0001\u00033\f\u0011\u0001\u001b\u0005\b\t\u001b\u0013\u0005\u0019\u0001B,\u0003-\u0011wN\u001d3fe^KG\r\u001e5\t\u000f\u0011E%\t1\u0001\u0005\u0014\u0006q!m\u001c:eKJ\u001cu\u000e\\8s\u001fB$\b#\u00027\u0003F\u0005e\u0012!\u00053sC^\u0014\u0016\rZ5p'\u0016dWm\u0019;fIRa\u0011\u0011\u0006CM\t7#i\nb(\u0005\"\"9\u0011QN\"A\u0002\u0005E\u0004b\u0002CE\u0007\u0002\u0007\u0011\u0011\u001c\u0005\b\t\u001b\u001b\u0005\u0019\u0001B,\u0011\u001d!\tj\u0011a\u0001\t'C\u0011\u0002b)D!\u0003\u0005\r\u0001b%\u0002\u0019Q,\u0007\u0010^\"pY>\u0014x\n\u001d;\u00027\u0011\u0014\u0018m\u001e*bI&|7+\u001a7fGR,G\r\n3fM\u0006,H\u000e\u001e\u00136+\t!IK\u000b\u0003\u0005\u0014\u0006=\u0016A\u00063sC^\u001c\u0005.Z2lE>DXK\\:fY\u0016\u001cG/\u001a3\u0015\u0015\u0005%Bq\u0016CY\tg#)\fC\u0004\u0002n\u0015\u0003\r!!\u001d\t\u000f\u0011%U\t1\u0001\u0002Z\"9AQR#A\u0002\t]\u0003b\u0002CI\u000b\u0002\u0007A1S\u0001\u0015IJ\fwo\u00115fG.\u0014w\u000e_*fY\u0016\u001cG/\u001a3\u0015\u0019\u0005%B1\u0018C_\t\u007f#\t\r\"2\t\u000f\u00055d\t1\u0001\u0002r!9A\u0011\u0012$A\u0002\u0005e\u0007b\u0002CG\r\u0002\u0007!q\u000b\u0005\b\t\u00074\u0005\u0019\u0001CJ\u0003-\u0011wN\u001d3fe\u000e{Gn\u001c:\t\u0013\u0011\u001dg\t%AA\u0002\u0011M\u0015!\u0003;fqR\u001cu\u000e\\8s\u0003y!'/Y<DQ\u0016\u001c7NY8y'\u0016dWm\u0019;fI\u0012\"WMZ1vYR$S'A\u0006ee\u0006<H+\u001a=u\u0005>DH\u0003DA\u0015\t\u001f$\t\u000eb5\u0005V\u0012]\u0007bBA7\u0011\u0002\u0007\u0011\u0011\u000f\u0005\b\u0005GC\u0005\u0019AAm\u0011\u001d!I\t\u0013a\u0001\u00033Dq\u0001\"$I\u0001\u0004\u00119\u0006C\u0004\u0005\u0012\"\u0003\r\u0001b%\u0002\u0011\u0011\u0014\u0018m\u001e'j]\u0016$\"\"!\u000b\u0005^\u0012}G1\u001dCs\u0011\u001d\ti'\u0013a\u0001\u0003cBq\u0001\"9J\u0001\u0004\u0019Y0\u0001\u0003mS:,\u0007bBA \u0013\u0002\u0007AQ\u0001\u0005\b\t'I\u0005\u0019\u0001B,\u0003%!'/Y<BeJ|w\u000f\u0006\u0006\u0002*\u0011-HQ\u001eCx\tcDq!!\u001cK\u0001\u0004\t\t\bC\u0004\u0005b*\u0003\raa?\t\u000f\u0005}\"\n1\u0001\u0005\u0006!9A1\u0003&A\u0002\t]\u0013A\u00043sC^Len\u001b)pYf<wN\u001c\u000b\u000b\u0003S!9\u0010\"?\u0005|\u0012u\bbBA7\u0017\u0002\u0007\u0011\u0011\u000f\u0005\b\u0007\u007f\\\u0005\u0019\u0001C\u0001\u0011\u001d\tyd\u0013a\u0001\t\u000bAq\u0001b\u0005L\u0001\u0004\u00119&A\u0007D\u001fNs\u0015-\\3`'\u0016SE)Q\u000b\u0003\u000b\u0007\u0001BAa\u001c\u0006\u0006%!Qq\u0001B9\u0005\u001d\u0019uj\u0015(b[\u0016\fabQ(T\u001d\u0006lWmX*F\u0015\u0012\u000b\u0005%A\rbI\u0012lU\r^1eCR\f7I]3bi\u0016$')_*fU\u0012\fG\u0003BA\u0015\u000b\u001fAqaa\u0010O\u0001\u0004\u0011Y*\u0001\tjg\u000e\u0013X-\u0019;fI\nK8+\u001a6eCR!\u0011q`C\u000b\u0011\u001d\u0019yd\u0014a\u0001\u00057\u000b\u0011#\u00193e\r>tGOU3t_V\u00148-Z%g)!\tI#b\u0007\u0006\u001e\u0015-\u0002bBB\u000e!\u0002\u0007!Q\u0012\u0005\b\u000b?\u0001\u0006\u0019AC\u0011\u0003\u00111wN\u001c;\u0011\t\u0015\rRqE\u0007\u0003\u000bKQ1!b\b{\u0013\u0011)I#\"\n\u0003\u0017A#E+\u001f9fc\u0019{g\u000e\u001e\u0005\n\u000b[\u0001\u0006\u0013!a\u0001\u000b_\t\u0011B]3t_V\u00148-Z:\u0011\u0007e,\t$C\u0002\u00064i\u00141\u0002\u0015#SKN|WO]2fg\u0006Y\u0012\r\u001a3G_:$(+Z:pkJ\u001cW-\u00134%I\u00164\u0017-\u001e7uIM*\"!\"\u000f+\t\u0015=\u0012qV\u0001\u0007Q\u0006\u001c\bLZ1\u0015\u0005\u0005}\u0018!\u0003:f[>4X\r\u00174b\u0003e)gn];sKZ\u000bG.^3DC:\u0014U\rR5ta2\f\u00170\u001a3\u0015\t\u0015\u0015Sq\t\t\u0007\u0003/\u0012YO!$\t\u000f\r}B\u000b1\u0001\u0004h\u00059a\r\\1ui\u0016tGCAC#\u00031Ah-Y%t\tft\u0017-\\5d+\t\ty0A\u000fhK:,'/\u0019;f\u0003B\u0004X-\u0019:b]\u000e,7/\u00134SKF,\u0018N]3e)\u0011\tI#\"\u0016\t\u000f\r}r\u000b1\u0001\u0003\u001c\u0006\u0011BO];oG\u0006$XMR8s\t&\u001c\b\u000f\\1z)\u0019\u0011i)b\u0017\u0006^!9!q\u001d-A\u0002\t5\u0005\"CC01B\u0005\t\u0019AAm\u0003\raWM\\\u0001\u001diJ,hnY1uK\u001a{'\u000fR5ta2\f\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00133+\t))G\u000b\u0003\u0002Z\u0006=\u0016aF2iC:<W\rR3gCVdG/\u00119qK\u0006\u0014\u0018M\\2f)\u0019\tI#b\u001b\u0006p!9QQ\u000e.A\u0002\u0005%\u0015a\u00038fo\u001a{g\u000e^*ju\u0016Dqaa\u0010[\u0001\u0004\u00199'\u0001\u0013q_N$\bK]8dKN\u001cx+\u001b3hKR\fe\u000e\u001a$jK2$w\u000b[3sK>sWmS5e)\u0011\tI#\"\u001e\t\u000f\tU8\f1\u0001\u0006xAA!1ZC=\u000b{\u001ai!\u0003\u0003\u0006|\t5'aA'baB9AN!@\u0003\u000e\u0016}\u0004\u0003BB$\u000b\u0003KA!b!\u0004J\tiai\u001c:n\r&,G\u000e\u001a+za\u0016\fq\"Q2s_\u001a{'/\\:IK2\u0004XM\u001d\t\u0004\u0003\u001bi6CA/l)\t)9)\u0001\u0005hKR|%M[%e)\u0011\u0011i)\"%\t\u000f\u0015Mu\f1\u0001\u0006\u0016\u0006\tq\u000e\u0005\u0003\u0003p\u0015]\u0015\u0002BCM\u0005c\u0012QbQ(T\u001f\nTWm\u0019;bE2,\u0007")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/AcroFormsHelper.class */
public class AcroFormsHelper implements Loggable {
    private volatile AcroFormsHelper$PDPageContentStreamPimped$ PDPageContentStreamPimped$module;
    private ListBuffer<PDAnnotationWidget> widgets;
    private ListBuffer<Tuple2<String, PDField>> fields;
    private final PDDocument doc;
    private PDAcroForm acroForm;
    private final PDColor PD_COLOR_BLACK;
    private final NumberFormat numberFormat;
    private final COSName COSName_SEJDA;
    private transient Logger logger;
    private volatile byte bitmap$0;
    private volatile transient boolean bitmap$trans$0;

    public static String getObjId(final COSObjectable o) {
        return AcroFormsHelper$.MODULE$.getObjId(o);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v8, types: [code.sejda.tasks.common.AcroFormsHelper] */
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

    public AcroFormsHelper(final PDDocument doc) {
        this.doc = doc;
        Loggable.$init$(this);
        this.acroForm = doc.getDocumentCatalog().getAcroForm();
        if (acroForm() == null) {
            acroForm_$eq(new PDAcroForm(doc));
            doc.getDocumentCatalog().setAcroForm(acroForm());
        }
        if (acroForm().getDefaultResources() == null) {
            acroForm().setDefaultResources(new PDResources());
        }
        this.PD_COLOR_BLACK = new PDColor(new float[]{0.0f, 0.0f, 0.0f}, PDDeviceRGB.INSTANCE);
        this.numberFormat = NumberFormat.getNumberInstance(Locale.US);
        this.COSName_SEJDA = COSName.getPDFName("Sejda");
    }

    public PDAcroForm acroForm() {
        return this.acroForm;
    }

    public void acroForm_$eq(final PDAcroForm x$1) {
        this.acroForm = x$1;
    }

    public PDColor PD_COLOR_BLACK() {
        return this.PD_COLOR_BLACK;
    }

    /* compiled from: AcroFormsHelper.scala */
    /* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/AcroFormsHelper$PDPageContentStreamPimped.class */
    public class PDPageContentStreamPimped implements Product, Serializable {
        private final PDPageContentStream cs;
        public final /* synthetic */ AcroFormsHelper $outer;

        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public PDPageContentStream cs() {
            return this.cs;
        }

        public PDPageContentStreamPimped copy(final PDPageContentStream cs) {
            return new PDPageContentStreamPimped(code$sejda$tasks$common$AcroFormsHelper$PDPageContentStreamPimped$$$outer(), cs);
        }

        public PDPageContentStream copy$default$1() {
            return cs();
        }

        public String productPrefix() {
            return "PDPageContentStreamPimped";
        }

        public int productArity() {
            return 1;
        }

        public Object productElement(final int x$1) {
            switch (x$1) {
                case 0:
                    return cs();
                default:
                    return Statics.ioobe(x$1);
            }
        }

        public Iterator<Object> productIterator() {
            return ScalaRunTime$.MODULE$.typedProductIterator(this);
        }

        public boolean canEqual(final Object x$1) {
            return x$1 instanceof PDPageContentStreamPimped;
        }

        public String productElementName(final int x$1) {
            switch (x$1) {
                case 0:
                    return OperatorName.NON_STROKING_COLORSPACE;
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
                if ((x$1 instanceof PDPageContentStreamPimped) && ((PDPageContentStreamPimped) x$1).code$sejda$tasks$common$AcroFormsHelper$PDPageContentStreamPimped$$$outer() == code$sejda$tasks$common$AcroFormsHelper$PDPageContentStreamPimped$$$outer()) {
                    PDPageContentStreamPimped pDPageContentStreamPimped = (PDPageContentStreamPimped) x$1;
                    PDPageContentStream pDPageContentStreamCs = cs();
                    PDPageContentStream pDPageContentStreamCs2 = pDPageContentStreamPimped.cs();
                    if (pDPageContentStreamCs != null ? pDPageContentStreamCs.equals(pDPageContentStreamCs2) : pDPageContentStreamCs2 == null) {
                        if (pDPageContentStreamPimped.canEqual(this)) {
                        }
                    }
                }
                return false;
            }
            return true;
        }

        public /* synthetic */ AcroFormsHelper code$sejda$tasks$common$AcroFormsHelper$PDPageContentStreamPimped$$$outer() {
            return this.$outer;
        }

        public PDPageContentStreamPimped(final AcroFormsHelper $outer, final PDPageContentStream cs) {
            this.cs = cs;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
            Product.$init$(this);
        }

        public void transform(final double a, final double b, final double c, final double d, final double e, final double f) throws IOException {
            cs().transform(new Matrix(new AffineTransform(a, b, c, d, e, f)));
        }
    }

    public AcroFormsHelper$PDPageContentStreamPimped$ PDPageContentStreamPimped() {
        if (this.PDPageContentStreamPimped$module == null) {
            PDPageContentStreamPimped$lzycompute$1();
        }
        return this.PDPageContentStreamPimped$module;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v5, types: [code.sejda.tasks.common.AcroFormsHelper] */
    private final void PDPageContentStreamPimped$lzycompute$1() {
        ?? r0 = this;
        synchronized (r0) {
            if (this.PDPageContentStreamPimped$module == null) {
                r0 = this;
                r0.PDPageContentStreamPimped$module = new AcroFormsHelper$PDPageContentStreamPimped$(this);
            }
        }
    }

    private PDPageContentStreamPimped pimpedContentStream(final PDPageContentStream cs) {
        return new PDPageContentStreamPimped(this, cs);
    }

    private float doubleToFloat(final double d) {
        return (float) d;
    }

    private Set<String> _toNonTerminalField$default$2() {
        return Predef$.MODULE$.Set().empty();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public PDNonTerminalField _toNonTerminalField(final COSBase base, final Set<String> visitedObjIds) {
        String objId = ObjIdUtils$.MODULE$.objIdOfOrEmpty(base);
        if ((!StringOps$.MODULE$.nonEmpty$extension(Predef$.MODULE$.augmentString(objId)) || !visitedObjIds.contains(objId)) && (base instanceof COSDictionary)) {
            COSDictionary cOSDictionary = (COSDictionary) base;
            PDNonTerminalField parentField = (PDNonTerminalField) toParentOpt(cOSDictionary).map(parent -> {
                return this._toNonTerminalField(parent, (Set) visitedObjIds.$plus(objId));
            }).orNull($less$colon$less$.MODULE$.refl());
            return PDFieldFactory.createNonTerminalField(acroForm(), cOSDictionary, parentField);
        }
        return null;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    private PDField toField(final PDAnnotationWidget w) throws MatchError {
        COSDictionary dictionary = w.getCOSObject();
        Option fieldTypeOpt = Option$.MODULE$.apply(dictionary.getNameAsString(COSName.FT));
        if (fieldTypeOpt instanceof Some) {
            return createField(dictionary);
        }
        if (!None$.MODULE$.equals(fieldTypeOpt)) {
            throw new MatchError(fieldTypeOpt);
        }
        Some parentOpt = toParentOpt(dictionary);
        if (!(parentOpt instanceof Some)) {
            return createField(dictionary);
        }
        COSDictionary parent = (COSDictionary) parentOpt.value();
        return createField(parent);
    }

    private PDField createField(final COSDictionary d) {
        COSDictionary parent = (COSDictionary) toParentOpt(d).orNull($less$colon$less$.MODULE$.refl());
        return PDFieldFactory.createField(acroForm(), d, _toNonTerminalField(parent, _toNonTerminalField$default$2()));
    }

    private Option<COSDictionary> toParentOpt(final COSDictionary d) {
        return Option$.MODULE$.apply(d.getDictionaryObject(COSName.PARENT)).flatMap(x0$1 -> {
            if (x0$1 instanceof COSDictionary) {
                COSDictionary cOSDictionary = (COSDictionary) x0$1;
                if (!ObjIdUtils$.MODULE$.haveSameId(cOSDictionary, d)) {
                    return new Some(cOSDictionary);
                }
            }
            return None$.MODULE$;
        });
    }

    private <T> ListBuffer<T> seq2mutableSeq(final Seq<T> s) {
        return (ListBuffer) ListBuffer$.MODULE$.apply(s);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v10, types: [code.sejda.tasks.common.AcroFormsHelper] */
    private ListBuffer<PDAnnotationWidget> widgets$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (((byte) (this.bitmap$0 & 1)) == 0) {
                this.widgets = seq2mutableSeq(((IterableOnceOps) ((IterableOps) ((IterableOps) ImplicitJavaConversions$.MODULE$.javaIterableToScalaIterable(this.doc.getPages()).flatMap(page -> {
                    return ImplicitJavaConversions$.MODULE$.javaListToScalaSeq(page.getAnnotations());
                })).filter(x$1 -> {
                    return BoxesRunTime.boxToBoolean($anonfun$widgets$2(x$1));
                })).map(x$2 -> {
                    return (PDAnnotationWidget) x$2;
                })).toSeq());
                r0 = this;
                r0.bitmap$0 = (byte) (this.bitmap$0 | 1);
            }
        }
        return this.widgets;
    }

    public ListBuffer<PDAnnotationWidget> widgets() {
        return ((byte) (this.bitmap$0 & 1)) == 0 ? widgets$lzycompute() : this.widgets;
    }

    public static final /* synthetic */ boolean $anonfun$widgets$2(final PDAnnotation x$1) {
        return x$1 instanceof PDAnnotationWidget;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v10, types: [code.sejda.tasks.common.AcroFormsHelper] */
    private ListBuffer<Tuple2<String, PDField>> fields$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (((byte) (this.bitmap$0 & 2)) == 0) {
                this.fields = (ListBuffer) widgets().flatMap(widget -> {
                    None$ some;
                    try {
                        PDField field = this.toField(widget);
                        if (field instanceof PDNonTerminalField) {
                            this.logger().warn(new StringBuilder(47).append("Mapped non terminal field for widget ").append(ObjIdUtils$.MODULE$.objIdOfOrEmpty(widget)).append(", skipping").toString());
                            some = None$.MODULE$;
                        } else {
                            if (!(field instanceof PDTerminalField)) {
                                throw new MatchError(field);
                            }
                            PDTerminalField pDTerminalField = (PDTerminalField) field;
                            Buffer fieldWidgets = (Buffer) JavaConverters$.MODULE$.asScalaBufferConverter(pDTerminalField.getWidgets()).asScala();
                            Buffer fieldWidgetIds = (Buffer) fieldWidgets.map(o -> {
                                return ObjIdUtils$.MODULE$.objIdOf(o);
                            });
                            String widgetId = ObjIdUtils$.MODULE$.objIdOf(widget);
                            if (!fieldWidgetIds.contains(widgetId)) {
                                this.logger().warn(new StringBuilder(100).append("Found field: ").append(pDTerminalField.getFullyQualifiedName()).append(" based on widget: ").append(widgetId).append(", but field widgets: ").append(fieldWidgetIds).append(" did not contain this original widget, adding it").toString());
                                pDTerminalField.addWidgetIfMissing(widget);
                                Seq newFieldWidgetIds = (Seq) ImplicitJavaConversions$.MODULE$.javaListToScalaSeq(pDTerminalField.getWidgets()).map(o2 -> {
                                    return ObjIdUtils$.MODULE$.objIdOf(o2);
                                });
                                fieldWidgets.foreach(w -> {
                                    $anonfun$fields$4(this, newFieldWidgetIds, fieldWidgetIds, pDTerminalField, w);
                                    return BoxedUnit.UNIT;
                                });
                            }
                            some = new Some(new Tuple2(pDTerminalField.getFullyQualifiedName(), pDTerminalField));
                        }
                        return some;
                    } catch (Throwable th) {
                        if (!NonFatal$.MODULE$.apply(th)) {
                            throw th;
                        }
                        this.logger().warn(new StringBuilder(43).append("Could not map widget: ").append(ObjIdUtils$.MODULE$.objIdOfOrEmpty(widget)).append(" to a field, skipping").toString(), th);
                        return None$.MODULE$;
                    }
                });
                r0 = this;
                r0.bitmap$0 = (byte) (this.bitmap$0 | 2);
            }
        }
        return this.fields;
    }

    public ListBuffer<Tuple2<String, PDField>> fields() {
        return ((byte) (this.bitmap$0 & 2)) == 0 ? fields$lzycompute() : this.fields;
    }

    public static final /* synthetic */ void $anonfun$fields$4(final AcroFormsHelper $this, final Seq newFieldWidgetIds$1, final Buffer fieldWidgetIds$1, final PDTerminalField x3$1, final PDAnnotationWidget w) {
        String wId = ObjIdUtils$.MODULE$.objIdOf(w);
        if (newFieldWidgetIds$1.contains(wId)) {
            return;
        }
        $this.logger().warn(new StringBuilder(65).append("Widget ").append(wId).append(" disappeared from field after /KIDS update, adding it back").toString());
        if (fieldWidgetIds$1.size() != 1) {
            throw new RuntimeException(new StringBuilder(58).append("Expected field to have one widget, field itself, but was: ").append(fieldWidgetIds$1.size()).toString());
        }
        x3$1.addWidgetIfMissing(w);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String toString$1(final PDField f) {
        return new StringBuilder(5).append(ObjIdUtils$.MODULE$.objIdOf(f)).append(" ").append(f.getFullyQualifiedName()).append(" [").append(f.getWidgets().size()).append("] ").append(f.getClass().getSimpleName()).toString();
    }

    public void printFields() {
        Predef$.MODULE$.println(((IterableOnceOps) allFields().map(f -> {
            return toString$1(f);
        })).mkString("\n"));
    }

    public void resetFields() {
        allFields().foreach(x0$1 -> {
            $anonfun$resetFields$1(x0$1);
            return BoxedUnit.UNIT;
        });
    }

    public static final /* synthetic */ void $anonfun$resetFields$1(final PDTerminalField x0$1) {
        if (x0$1 instanceof PDVariableText) {
            ((PDVariableText) x0$1).setValue("");
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
        } else {
            BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isEditable(final PDTerminalField f) {
        return !(f instanceof PDSignatureField);
    }

    public Seq<PDTerminalField> findEditableFieldsByName(final String name) {
        return (Seq) findFieldsByName(name).filter(f -> {
            return BoxesRunTime.boxToBoolean(this.isEditable(f));
        });
    }

    public Seq<PDTerminalField> findFieldsByName(final String name) {
        return (Seq) allFields().filter(x$3 -> {
            return BoxesRunTime.boxToBoolean($anonfun$findFieldsByName$1(name, x$3));
        });
    }

    public static final /* synthetic */ boolean $anonfun$findFieldsByName$1(final String name$1, final PDTerminalField x$3) {
        String fullyQualifiedName = x$3.getFullyQualifiedName();
        return fullyQualifiedName != null ? fullyQualifiedName.equals(name$1) : name$1 == null;
    }

    public Seq<PDTerminalField> allFields() {
        Seq fieldsOutsideAcroForm = ((IterableOnceOps) ((IterableOps) fields().map(x$4 -> {
            return (PDField) x$4._2();
        })).groupBy(f -> {
            return ObjIdUtils$.MODULE$.objIdOf(f);
        }).map(x$5 -> {
            return (PDField) ((IterableOps) x$5._2()).head();
        })).toSeq();
        Set fieldIds = ((IterableOnceOps) ((IterableOps) fieldsOutsideAcroForm.filter(f2 -> {
            return BoxesRunTime.boxToBoolean($anonfun$allFields$4(f2));
        })).map(f3 -> {
            return ObjIdUtils$.MODULE$.objIdOf(f3);
        })).toSet();
        List acroFormFields = ImplicitJavaConversions$.MODULE$.javaIterableToScalaIterable(acroForm().getFieldTree()).toList().filter(f4 -> {
            return BoxesRunTime.boxToBoolean($anonfun$allFields$6(fieldIds, f4));
        });
        return ((List) acroFormFields.$plus$plus(fieldsOutsideAcroForm)).filter(f5 -> {
            return BoxesRunTime.boxToBoolean($anonfun$allFields$7(f5));
        }).map(f6 -> {
            return (PDTerminalField) f6;
        }).filter(f7 -> {
            return BoxesRunTime.boxToBoolean($anonfun$allFields$9(f7));
        });
    }

    public static final /* synthetic */ boolean $anonfun$allFields$4(final PDField f) {
        return f.getCOSObject().id() != null;
    }

    public static final /* synthetic */ boolean $anonfun$allFields$6(final Set fieldIds$1, final PDField f) {
        String objId = ObjIdUtils$.MODULE$.objIdOfOrEmpty(f);
        return objId.isEmpty() || !fieldIds$1.contains(objId);
    }

    public static final /* synthetic */ boolean $anonfun$allFields$7(final PDField f) {
        return f instanceof PDTerminalField;
    }

    public static final /* synthetic */ boolean $anonfun$allFields$9(final PDTerminalField f) {
        return ImplicitJavaConversions$.MODULE$.javaListToScalaSeq(f.getWidgets()).nonEmpty();
    }

    public ListBuffer<PDAnnotationWidget> getWidgetsByObjId(final String objId) {
        return (ListBuffer) widgets().filter(w -> {
            return BoxesRunTime.boxToBoolean($anonfun$getWidgetsByObjId$1(objId, w));
        });
    }

    public static final /* synthetic */ boolean $anonfun$getWidgetsByObjId$1(final String objId$2, final PDAnnotationWidget w) {
        String strObjIdOf = ObjIdUtils$.MODULE$.objIdOf(w);
        return strObjIdOf != null ? strObjIdOf.equals(objId$2) : objId$2 == null;
    }

    public Option<PDField> findFieldByWidget(final PDAnnotationWidget widget) {
        String widgetObjId = ObjIdUtils$.MODULE$.objIdOf(widget);
        return fields().find(x0$1 -> {
            return BoxesRunTime.boxToBoolean($anonfun$findFieldByWidget$1(widgetObjId, x0$1));
        }).map(x$6 -> {
            return (PDField) x$6._2();
        });
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    public static final /* synthetic */ boolean $anonfun$findFieldByWidget$1(final String widgetObjId$1, final Tuple2 x0$1) throws MatchError {
        if (x0$1 != null) {
            PDField field = (PDField) x0$1._2();
            return ((IterableOnceOps) JavaConverters$.MODULE$.asScalaBufferConverter(field.getWidgets()).asScala()).exists(w -> {
                return BoxesRunTime.boxToBoolean($anonfun$findFieldByWidget$2(widgetObjId$1, w));
            });
        }
        throw new MatchError(x0$1);
    }

    public static final /* synthetic */ boolean $anonfun$findFieldByWidget$2(final String widgetObjId$1, final PDAnnotationWidget w) {
        String strObjIdOf = ObjIdUtils$.MODULE$.objIdOf(w);
        return strObjIdOf != null ? strObjIdOf.equals(widgetObjId$1) : widgetObjId$1 == null;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    public PDAnnotationWidget addWidget(final PDTerminalField field, final AppendFormFieldOperation operation, final PDPage page) throws MatchError {
        Tuple2 tuple2;
        PDAnnotationWidget widget = new PDAnnotationWidget();
        field.addWidgetIfMissing(widget);
        int height = operation.boundingBox().getTop() - operation.boundingBox().getBottom();
        int width = operation.boundingBox().getRight() - operation.boundingBox().getLeft();
        Option textColorOpt = operation.color().map(x$1 -> {
            return PageTextWriter.toPDColor(x$1);
        });
        Some someBorderColor = operation.borderColor();
        if (someBorderColor instanceof Some) {
            Color color = (Color) someBorderColor.value();
            tuple2 = new Tuple2(BoxesRunTime.boxToFloat(0.72f), new Some(PageTextWriter.toPDColor(color)));
        } else {
            tuple2 = new Tuple2(BoxesRunTime.boxToFloat(0.0f), None$.MODULE$);
        }
        Tuple2 tuple22 = tuple2;
        if (tuple22 != null) {
            float borderWidth = BoxesRunTime.unboxToFloat(tuple22._1());
            Option borderColorOpt = (Option) tuple22._2();
            Tuple2 tuple23 = new Tuple2(BoxesRunTime.boxToFloat(borderWidth), borderColorOpt);
            float borderWidth2 = BoxesRunTime.unboxToFloat(tuple23._1());
            Some some = (Option) tuple23._2();
            int rotation = page.getRotation();
            FormFieldType formFieldTypeKind = operation.kind();
            if (FormFieldType$Radio$.MODULE$.equals(formFieldTypeKind)) {
                String value = (String) operation.valueOpt().getOrElse(() -> {
                    return StringHelpers$.MODULE$.randomString(5);
                });
                COSDictionary normalAppearances = new COSDictionary();
                normalAppearances.setItem(value, appearanceStream(height, height, rotation, appearanceStream$default$4(), cs -> {
                    this.drawRadioSelected(cs, height, borderWidth2, some, textColorOpt);
                    return BoxedUnit.UNIT;
                }));
                normalAppearances.setItem(COSName.Off, appearanceStream(height, height, rotation, appearanceStream$default$4(), cs2 -> {
                    this.drawRadioUnselected(cs2, height, borderWidth2, some);
                    return BoxedUnit.UNIT;
                }));
                PDAppearanceDictionary appearanceDict = new PDAppearanceDictionary();
                appearanceDict.setNormalAppearance(new PDAppearanceEntry(normalAppearances));
                appearanceDict.setDownAppearance(new PDAppearanceEntry(normalAppearances));
                widget.setAppearance(appearanceDict);
                if (operation.selected()) {
                    PDRadioButton radio = (PDRadioButton) field;
                    radio.setIgnoreExportOptions(true);
                    radio.setValue(value);
                    widget.setAppearanceState(value);
                    BoxedUnit boxedUnit = BoxedUnit.UNIT;
                } else {
                    widget.setAppearanceState("Off");
                    BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
                }
            } else if (FormFieldType$Checkbox$.MODULE$.equals(formFieldTypeKind)) {
                COSDictionary normalAppearances2 = new COSDictionary();
                String value2 = (String) operation.valueOpt().getOrElse(() -> {
                    return "Yes";
                });
                COSName cosValue = COSName.getPDFName(value2);
                normalAppearances2.setItem(cosValue, appearanceStream(height, height, rotation, appearanceStream$default$4(), cs3 -> {
                    this.drawCheckboxSelected(cs3, height, borderWidth2, some, textColorOpt);
                    return BoxedUnit.UNIT;
                }));
                normalAppearances2.setItem(COSName.Off, appearanceStream(height, height, rotation, appearanceStream$default$4(), cs4 -> {
                    this.drawCheckboxUnselected(cs4, height, borderWidth2, some);
                    return BoxedUnit.UNIT;
                }));
                PDAppearanceDictionary appearanceDict2 = new PDAppearanceDictionary();
                appearanceDict2.setNormalAppearance(new PDAppearanceEntry(normalAppearances2));
                appearanceDict2.setDownAppearance(new PDAppearanceEntry(normalAppearances2));
                widget.setAppearance(appearanceDict2);
                if (operation.selected()) {
                    field.setValue(value2);
                    widget.setAppearanceState(value2);
                    BoxedUnit boxedUnit3 = BoxedUnit.UNIT;
                } else {
                    widget.setAppearanceState("Off");
                    BoxedUnit boxedUnit4 = BoxedUnit.UNIT;
                }
            } else if (FormFieldType$Text$.MODULE$.equals(formFieldTypeKind)) {
                PDAppearanceDictionary appearanceDict3 = new PDAppearanceDictionary();
                appearanceDict3.setNormalAppearance(appearanceStream(width, height, rotation, appearanceStream$default$4(), cs5 -> {
                    this.drawTextBox(cs5, width, height, borderWidth2, some);
                    return BoxedUnit.UNIT;
                }));
                widget.setAppearance(appearanceDict3);
                BoxedUnit boxedUnit5 = BoxedUnit.UNIT;
            } else {
                BoxedUnit boxedUnit6 = BoxedUnit.UNIT;
            }
            RectangularBox boundingBox = AnnotationsHelper$.MODULE$.boundingBoxAdjustedToPage(operation.boundingBox(), page);
            PDRectangle rect = AnnotationsHelper$.MODULE$.toPDRectangle(boundingBox);
            widget.setRectangle(rect);
            widget.setPrinted(true);
            widget.setBorder(new COSArray(COSInteger.ZERO, COSInteger.ZERO, new COSFloat(borderWidth2)));
            PDBorderStyleDictionary bs = new PDBorderStyleDictionary();
            bs.setWidth(borderWidth2);
            widget.setBorderStyle(bs);
            PDAppearanceCharacteristicsDictionary fieldAppearance = new PDAppearanceCharacteristicsDictionary(new COSDictionary());
            if (some instanceof Some) {
                PDColor borderColor = (PDColor) some.value();
                fieldAppearance.setBorderColour(borderColor);
                BoxedUnit boxedUnit7 = BoxedUnit.UNIT;
            } else {
                BoxedUnit boxedUnit8 = BoxedUnit.UNIT;
            }
            fieldAppearance.setRotation(rotation);
            widget.setAppearanceCharacteristics(fieldAppearance);
            setTextAlignOnWidget(widget, operation.align());
            widget.getCOSObject().setString(COSName.DA, field.getCOSObject().getString(COSName.DA));
            return widget;
        }
        throw new MatchError(tuple22);
    }

    public ListBuffer<PDAnnotationWidget> removeWidget(final PDAnnotationWidget widget) {
        String objId = ObjIdUtils$.MODULE$.objIdOf(widget);
        findFieldByWidget(widget).filter(x$8 -> {
            return BoxesRunTime.boxToBoolean($anonfun$removeWidget$1(x$8));
        }).map(x$9 -> {
            return (PDTerminalField) x$9;
        }).foreach(field -> {
            java.util.List fieldWidgets = field.getWidgets();
            if (fieldWidgets.remove(widget)) {
                this.logger().info(new StringBuilder(28).append("Removed widget ").append(objId).append(" from field: ").append(field.getFullyQualifiedName()).toString());
                field.setWidgets(fieldWidgets);
            }
            if (!fieldWidgets.isEmpty()) {
                return BoxedUnit.UNIT;
            }
            this.logger().info(new StringBuilder(53).append("Deleted field: ").append(field.getFullyQualifiedName()).append(", as it has no other remaining widgets").toString());
            if (this.acroForm().removeField(field) == null) {
                this.logger().warn(new StringBuilder(38).append("Could not remove field from acroform: ").append(field.getFullyQualifiedName()).toString());
            }
            return this.fields().$minus$eq(new Tuple2(field.getFullyQualifiedName(), field));
        });
        return widgets().$minus$eq(widget);
    }

    public static final /* synthetic */ boolean $anonfun$removeWidget$1(final PDField x$8) {
        return x$8 instanceof PDTerminalField;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    private void setTextAlignOnField(final PDVariableText field, final FormFieldAlign align) throws MatchError {
        if (FormFieldAlign$Left$.MODULE$.equals(align)) {
            field.setQ(0);
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
        } else if (FormFieldAlign$Center$.MODULE$.equals(align)) {
            field.setQ(1);
            BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
        } else {
            if (!FormFieldAlign$Right$.MODULE$.equals(align)) {
                throw new MatchError(align);
            }
            field.setQ(2);
            BoxedUnit boxedUnit3 = BoxedUnit.UNIT;
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    private void setTextAlignOnWidget(final PDAnnotationWidget widget, final FormFieldAlign align) throws MatchError {
        int i;
        if (FormFieldAlign$Left$.MODULE$.equals(align)) {
            i = 0;
        } else if (FormFieldAlign$Center$.MODULE$.equals(align)) {
            i = 1;
        } else {
            if (!FormFieldAlign$Right$.MODULE$.equals(align)) {
                throw new MatchError(align);
            }
            i = 2;
        }
        int value = i;
        widget.getCOSObject().setInt(COSName.Q, value);
    }

    public NumberFormat numberFormat() {
        return this.numberFormat;
    }

    private String fmt(final float f) {
        return numberFormat().format(f);
    }

    public String createDA(final String fontName, final double fontSize, final PDColor color) {
        float[] colorComp = color.getComponents();
        return new StringBuilder(11).append("/").append(fontName).append(" ").append(fmt(doubleToFloat(fontSize))).append(" Tf ").append(fmt(colorComp[0])).append(" ").append(fmt(colorComp[1])).append(" ").append(fmt(colorComp[2])).append(" rg").toString();
    }

    public PDTextField addTextField(final AppendFormFieldOperation operation) throws MatchError {
        PDTextField textBox = new PDTextField(acroForm());
        textBox.setPartialName(operation.name());
        textBox.setMultiline(operation.multiline());
        textBox.setRequired(operation.required());
        double fontSize = BoxesRunTime.unboxToDouble(operation.fontSizeOpt().getOrElse(() -> {
            return 0.0d;
        }));
        PDColor fontColor = PageTextWriter.toPDColor((Color) operation.color().getOrElse(() -> {
            return Color.BLACK;
        }));
        fontColor.getComponents();
        String fontName = "Helv";
        PDType1Font font = PDType1Font.HELVETICA();
        Some someCombChars = operation.combChars();
        if (someCombChars instanceof Some) {
            int combCharsMaxLen = BoxesRunTime.unboxToInt(someCombChars.value());
            fontName = "Courier";
            font = PDType1Font.COURIER();
            textBox.setComb(true);
            textBox.setMaxLen(combCharsMaxLen);
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
        } else {
            BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
        }
        textBox.setDefaultAppearance(createDA(fontName, fontSize, fontColor));
        addFontResourceIf(fontName, font, addFontResourceIf$default$3());
        addMetadataCreatedBySejda(textBox);
        setTextAlignOnField(textBox, operation.align());
        acroForm().addFields(Collections.singletonList(textBox));
        return textBox;
    }

    public PDChoice addDropdownField(final AppendFormFieldOperation operation) throws MatchError {
        PDChoice pDComboBox;
        if (operation.multiselect()) {
            pDComboBox = new PDListBox(acroForm());
        } else {
            pDComboBox = new PDComboBox(acroForm());
        }
        PDChoice box = pDComboBox;
        box.setPartialName(operation.name());
        box.setOptions(ImplicitJavaConversions$.MODULE$.scalaSeqToJavaList(operation.options()));
        box.setMultiSelect(operation.multiselect());
        box.setRequired(operation.required());
        double fontSize = BoxesRunTime.unboxToDouble(operation.fontSizeOpt().getOrElse(() -> {
            return 0.0d;
        }));
        PDColor fontColor = PageTextWriter.toPDColor((Color) operation.color().getOrElse(() -> {
            return Color.BLACK;
        }));
        box.setDefaultAppearance(createDA("Helv", fontSize, fontColor));
        addFontResourceIf("Helv", PDType1Font.HELVETICA(), addFontResourceIf$default$3());
        addMetadataCreatedBySejda(box);
        setTextAlignOnField(box, operation.align());
        acroForm().addFields(Collections.singletonList(box));
        return box;
    }

    public PDRadioButton addRadioField(final AppendFormFieldOperation operation) {
        PDRadioButton radioButton = new PDRadioButton(acroForm());
        radioButton.setPartialName(operation.name());
        radioButton.setRequired(operation.required());
        radioButton.setRadiosInUnison(true);
        addMetadataCreatedBySejda(radioButton);
        acroForm().addFields(Collections.singletonList(radioButton));
        return radioButton;
    }

    public PDSignatureField addSignatureField(final AppendFormFieldOperation operation) {
        PDSignatureField signatureBox = new PDSignatureField(acroForm());
        signatureBox.setPartialName(operation.name());
        signatureBox.setRequired(operation.required());
        addMetadataCreatedBySejda(signatureBox);
        acroForm().addFields(Collections.singletonList(signatureBox));
        return signatureBox;
    }

    public PDCheckBox addCheckboxField(final AppendFormFieldOperation operation) {
        PDCheckBox checkBox = new PDCheckBox(acroForm());
        checkBox.setPartialName(operation.name());
        checkBox.setDefaultValue("Off");
        checkBox.setRequired(operation.required());
        addMetadataCreatedBySejda(checkBox);
        acroForm().addFields(Collections.singletonList(checkBox));
        return checkBox;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    public boolean addInkMarkup(final RectangularBox boundingBox, final PDPage page, final Seq<Object> inklist, final Seq<Seq<Object>> appearances, final Color color, final float lineSize) throws MatchError, IOException {
        Tuple2 tuple2;
        COSArray inkListArray = new COSArray();
        inklist.foreach(i -> {
            return inkListArray.add((COSBase) new COSFloat((float) i));
        });
        int rotation = page.getRotation();
        COSArray inkList = new COSArray();
        inkList.add((COSBase) inkListArray);
        COSDictionary dict = new COSDictionary();
        dict.setName(COSName.SUBTYPE, PDAnnotationMarkup.SUB_TYPE_INK);
        dict.setItem(COSName.INKLIST, (COSBase) inkList);
        PDAnnotationMarkup markup = new PDAnnotationMarkup(dict);
        PDRectangle rect = AnnotationsHelper$.MODULE$.toPDRectangle(AnnotationsHelper$.MODULE$.boundingBoxAdjustedToPage(boundingBox, page));
        markup.setRectangle(rect);
        markup.setColor(PageTextWriter.toPDColor(color));
        PDAppearanceDictionary appearanceDict = new PDAppearanceDictionary();
        switch (rotation) {
            case 90:
            case 270:
                tuple2 = new Tuple2(BoxesRunTime.boxToFloat(rect.getHeight()), BoxesRunTime.boxToFloat(rect.getWidth()));
                break;
            default:
                tuple2 = new Tuple2(BoxesRunTime.boxToFloat(rect.getWidth()), BoxesRunTime.boxToFloat(rect.getHeight()));
                break;
        }
        Tuple2 tuple22 = tuple2;
        if (tuple22 == null) {
            throw new MatchError(tuple22);
        }
        float bboxWidth = BoxesRunTime.unboxToFloat(tuple22._1());
        float bboxHeight = BoxesRunTime.unboxToFloat(tuple22._2());
        Tuple2 tuple23 = new Tuple2(BoxesRunTime.boxToFloat(bboxWidth), BoxesRunTime.boxToFloat(bboxHeight));
        float bboxWidth2 = BoxesRunTime.unboxToFloat(tuple23._1());
        float bboxHeight2 = BoxesRunTime.unboxToFloat(tuple23._2());
        PDAppearanceStream stream = appearanceStream(bboxWidth2, bboxHeight2, rotation, BlendMode.MULTIPLY, cs -> {
            this.drawInkPolygon(cs, appearances, color, lineSize);
            return BoxedUnit.UNIT;
        });
        appearanceDict.setNormalAppearance(new PDAppearanceEntry(stream.getCOSObject()));
        markup.setAppearance(appearanceDict);
        PDBorderStyleDictionary bs = new PDBorderStyleDictionary();
        bs.setWidth(lineSize);
        markup.setBorderStyle(bs);
        markup.setPrinted(true);
        markup.setCreationDate(Calendar.getInstance());
        markup.setModifiedDate(Calendar.getInstance());
        markup.setAnnotationName(UUID.randomUUID().toString());
        return page.getAnnotations().add(markup);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0158  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0172  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0193  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x01be  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean addLineAnnotation(final org.sejda.model.RectangularBox r12, final org.sejda.sambox.pdmodel.PDPage r13, final scala.collection.immutable.Seq<java.lang.Object> r14, final java.awt.Color r15, final float r16, final java.lang.String r17) throws scala.MatchError, java.io.IOException {
        /*
            Method dump skipped, instructions count: 599
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: code.sejda.tasks.common.AcroFormsHelper.addLineAnnotation(org.sejda.model.RectangularBox, org.sejda.sambox.pdmodel.PDPage, scala.collection.immutable.Seq, java.awt.Color, float, java.lang.String):boolean");
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    public static final /* synthetic */ void $anonfun$addLineAnnotation$2(final AcroFormsHelper $this, final String kind$1, final Seq points$1, final Color color$2, final float lineSize$2, final PDPageContentStream cs) throws MatchError, IOException {
        switch (kind$1 == null ? 0 : kind$1.hashCode()) {
            case 3321844:
                if ("line".equals(kind$1)) {
                    $this.drawLine(cs, points$1, color$2, lineSize$2);
                    return;
                }
                break;
            case 93090825:
                if ("arrow".equals(kind$1)) {
                    $this.drawArrow(cs, points$1, color$2, lineSize$2);
                    return;
                }
                break;
        }
        throw new MatchError(kind$1);
    }

    private BlendMode appearanceStream$default$4() {
        return BlendMode.NORMAL;
    }

    private PDAppearanceStream appearanceStream(final float width, final float height, final int rotation, final BlendMode blendMode, final Function1<PDPageContentStream, BoxedUnit> fn) throws IOException {
        PDAppearanceStream appearance = new PDAppearanceStream();
        appearance.setResources(new PDResources());
        PDRectangle bbox = new PDRectangle(width, height);
        PDPageContentStream content = new PDPageContentStream(this.doc, appearance);
        if (0 != 0) {
            try {
                content.setStrokingColor(Color.BLUE);
                content.setLineWidth(0.5f);
                content.addRect(0.0f, 0.0f, bbox.getWidth(), bbox.getHeight());
                content.stroke();
                content.moveTo(0.0f, 0.0f);
                content.lineTo(bbox.getWidth(), bbox.getHeight());
                content.moveTo(0.0f, bbox.getHeight());
                content.lineTo(bbox.getWidth(), 0.0f);
                content.stroke();
                content.setStrokingColor(Color.RED);
                content.moveTo(0.0f, 0.0f);
                content.lineTo(20.0f, 0.0f);
                content.stroke();
                content.setStrokingColor(Color.GREEN);
                content.moveTo(0.0f, 0.0f);
                content.lineTo(0.0f, 20.0f);
                content.stroke();
            } catch (Throwable th) {
                content.close();
                throw th;
            }
        }
        PDExtendedGraphicsState gs = new PDExtendedGraphicsState();
        gs.setBlendMode(blendMode);
        content.setGraphicsStateParameters(gs);
        fn.apply(content);
        content.close();
        appearance.setBBox(bbox);
        AffineTransform at = calculateMatrix(bbox, rotation);
        if (!at.isIdentity()) {
            appearance.setMatrix(at);
        }
        return appearance;
    }

    private AffineTransform calculateMatrix(final PDRectangle bbox, final int rotation) {
        if (rotation == 0) {
            return new AffineTransform();
        }
        float tx = 0.0f;
        float ty = 0.0f;
        switch (rotation) {
            case 90:
                tx = bbox.getUpperRightY();
                break;
            case 180:
                tx = bbox.getUpperRightY();
                ty = bbox.getUpperRightX();
                break;
            case 270:
                ty = bbox.getUpperRightX();
                break;
        }
        Matrix matrix = Matrix.getRotateInstance(Math.toRadians(rotation), tx, ty);
        return matrix.createAffineTransform();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void drawRadioUnselected(final PDPageContentStream cs, final int h, final float borderWidth, final Option<PDColor> borderColorOpt) throws IOException {
        cs.saveGraphicsState();
        if (borderColorOpt instanceof Some) {
            PDColor borderColor = (PDColor) ((Some) borderColorOpt).value();
            cs.setStrokingColor(borderColor);
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
        } else {
            PDExtendedGraphicsState extGs = new PDExtendedGraphicsState();
            extGs.setStrokingAlphaConstant(Predef$.MODULE$.float2Float(0.0f));
            cs.setGraphicsStateParameters(extGs);
            BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
        }
        cs.setLineWidth(borderWidth);
        pimpedContentStream(cs).transform(1.0d, 0.0d, 0.0d, 1.0d, h / 2, h / 2);
        double a = (h / 2) - 0.5d;
        double b = h / 3.7460198539d;
        cs.moveTo(doubleToFloat(a), 0.0f);
        cs.curveTo(doubleToFloat(a), doubleToFloat(b), doubleToFloat(b), doubleToFloat(a), 0.0f, doubleToFloat(a));
        cs.curveTo(doubleToFloat(-b), doubleToFloat(a), doubleToFloat(-a), doubleToFloat(b), doubleToFloat(-a), 0.0f);
        cs.curveTo(doubleToFloat(-a), doubleToFloat(-b), doubleToFloat(-b), doubleToFloat(-a), 0.0f, doubleToFloat(-a));
        cs.curveTo(doubleToFloat(b), doubleToFloat(-a), doubleToFloat(a), doubleToFloat(-b), doubleToFloat(a), 0.0f);
        cs.closeAndStroke();
        cs.restoreGraphicsState();
    }

    private Option<PDColor> drawRadioSelected$default$5() {
        return None$.MODULE$;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void drawRadioSelected(final PDPageContentStream cs, final int h, final float borderWidth, final Option<PDColor> borderColorOpt, final Option<PDColor> textColorOpt) throws IOException {
        drawRadioUnselected(cs, h, borderWidth, borderColorOpt);
        cs.saveGraphicsState();
        pimpedContentStream(cs).transform(1.0d, 0.0d, 0.0d, 1.0d, h / 2, h / 2);
        int a = ((h / 2) - 1) / 2;
        double b = a / 1.8106101756d;
        cs.moveTo(a, 0.0f);
        cs.curveTo(a, doubleToFloat(b), doubleToFloat(b), a, 0.0f, a);
        cs.curveTo(doubleToFloat(-b), a, -a, doubleToFloat(b), -a, 0.0f);
        cs.curveTo(-a, doubleToFloat(-b), doubleToFloat(-b), -a, 0.0f, -a);
        cs.curveTo(doubleToFloat(b), -a, a, doubleToFloat(-b), a, 0.0f);
        cs.setStrokingColor(0);
        cs.setNonStrokingColor((PDColor) textColorOpt.getOrElse(() -> {
            return this.PD_COLOR_BLACK();
        }));
        cs.fill();
        cs.restoreGraphicsState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void drawCheckboxUnselected(final PDPageContentStream cs, final int h, final float borderWidth, final Option<PDColor> borderColorOpt) throws IOException {
        cs.setLineWidth(borderWidth);
        cs.saveGraphicsState();
        if (borderColorOpt instanceof Some) {
            PDColor borderColor = (PDColor) ((Some) borderColorOpt).value();
            cs.setStrokingColor(borderColor);
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
        } else {
            PDExtendedGraphicsState extGs = new PDExtendedGraphicsState();
            extGs.setStrokingAlphaConstant(Predef$.MODULE$.float2Float(0.0f));
            cs.setGraphicsStateParameters(extGs);
            BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
        }
        cs.addRect(doubleToFloat(0.5d), doubleToFloat(0.5d), h - 1, h - 1);
        cs.closeAndStroke();
        cs.restoreGraphicsState();
    }

    private Option<PDColor> drawCheckboxSelected$default$5() {
        return None$.MODULE$;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void drawCheckboxSelected(final PDPageContentStream cs, final int h, final float borderWidth, final Option<PDColor> borderColor, final Option<PDColor> textColor) throws IOException {
        drawCheckboxUnselected(cs, h, borderWidth, borderColor);
        cs.setStrokingColor(0);
        cs.setNonStrokingColor((PDColor) textColor.getOrElse(() -> {
            return this.PD_COLOR_BLACK();
        }));
        cs.saveGraphicsState();
        cs.addRect(1.0f, 1.0f, h - 2, h - 2);
        cs.clip();
        cs.beginText();
        cs.setFont(PDType1Font.ZAPF_DINGBATS(), 2 * h);
        cs.newLineAtOffset(0.0f, doubleToFloat(h * 0.1d));
        cs.setLeading(2 * h);
        cs.showText("✓");
        cs.endText();
        cs.restoreGraphicsState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void drawTextBox(final PDPageContentStream cs, final int w, final int h, final float borderWidth, final Option<PDColor> borderColorOpt) throws IOException {
        cs.saveGraphicsState();
        if (borderColorOpt instanceof Some) {
            PDColor borderColor = (PDColor) ((Some) borderColorOpt).value();
            cs.setStrokingColor(borderColor);
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
        } else {
            PDExtendedGraphicsState extGs = new PDExtendedGraphicsState();
            extGs.setStrokingAlphaConstant(Predef$.MODULE$.float2Float(0.0f));
            cs.setGraphicsStateParameters(extGs);
            BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
        }
        cs.setLineWidth(borderWidth);
        cs.setMiterLimit(10.0f);
        cs.addRect(borderWidth / 2, borderWidth / 2, w - borderWidth, h - borderWidth);
        cs.closeAndStroke();
        cs.saveGraphicsState();
        cs.addRect(borderWidth, borderWidth, w - (2 * borderWidth), h - (2 * borderWidth));
        cs.clip();
        cs.restoreGraphicsState();
        cs.restoreGraphicsState();
    }

    private void drawLine(final PDPageContentStream cs, final Seq<Object> line, final Color color, final float lineSize) throws IOException {
        cs.setStrokingColor(color);
        cs.setLineWidth(lineSize);
        cs.setLineDashPattern(new float[0], 0.0f);
        cs.moveTo(doubleToFloat(BoxesRunTime.unboxToDouble(line.apply(0))), doubleToFloat(BoxesRunTime.unboxToDouble(line.apply(1))));
        cs.lineTo(doubleToFloat(BoxesRunTime.unboxToDouble(line.apply(2))), doubleToFloat(BoxesRunTime.unboxToDouble(line.apply(3))));
        cs.stroke();
    }

    private void drawArrow(final PDPageContentStream cs, final Seq<Object> line, final Color color, final float lineSize) throws IOException {
        cs.setStrokingColor(color.getRed(), color.getGreen(), color.getBlue());
        cs.setLineWidth(lineSize);
        cs.setLineJoinStyle(0);
        cs.setLineDashPattern(new float[0], 0.0f);
        double x1 = BoxesRunTime.unboxToDouble(line.apply(0));
        double y1 = BoxesRunTime.unboxToDouble(line.apply(1));
        double x2 = BoxesRunTime.unboxToDouble(line.apply(2));
        double y2 = BoxesRunTime.unboxToDouble(line.apply(3));
        cs.moveTo(doubleToFloat(x1), doubleToFloat(y1));
        cs.lineTo(doubleToFloat(x2), doubleToFloat(y2));
        double dx = x2 - x1;
        double dy = y2 - y1;
        double len = Math.sqrt((dx * dx) + (dy * dy));
        double headSize = Math.min(15.0d, Math.max(6.0d, len * 0.2d));
        double unitDx = (dx / len) * headSize;
        double unitDy = (dy / len) * headSize;
        double px1 = (x2 - unitDx) - unitDy;
        double py1 = (y2 - unitDy) + unitDx;
        double px2 = (x2 - unitDx) + unitDy;
        double py2 = (y2 - unitDy) - unitDx;
        cs.moveTo(doubleToFloat(px1), doubleToFloat(py1));
        cs.lineTo(doubleToFloat(x2), doubleToFloat(y2));
        cs.lineTo(doubleToFloat(px2), doubleToFloat(py2));
        cs.stroke();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void drawInkPolygon(final PDPageContentStream cs, final Seq<Seq<Object>> appearances, final Color color, final float lineSize) throws IOException {
        cs.setStrokingColor(color.getRed(), color.getGreen(), color.getBlue());
        cs.setLineWidth(lineSize);
        cs.setLineCapStyle(1);
        appearances.foreach(line -> {
            $anonfun$drawInkPolygon$1(this, cs, line);
            return BoxedUnit.UNIT;
        });
        cs.stroke();
    }

    public static final /* synthetic */ void $anonfun$drawInkPolygon$1(final AcroFormsHelper $this, final PDPageContentStream cs$1, final Seq line) throws IOException {
        cs$1.moveTo($this.doubleToFloat(BoxesRunTime.unboxToDouble(line.apply(0))), $this.doubleToFloat(BoxesRunTime.unboxToDouble(line.apply(1))));
        cs$1.curveTo($this.doubleToFloat(BoxesRunTime.unboxToDouble(line.apply(2))), $this.doubleToFloat(BoxesRunTime.unboxToDouble(line.apply(3))), $this.doubleToFloat(BoxesRunTime.unboxToDouble(line.apply(4))), $this.doubleToFloat(BoxesRunTime.unboxToDouble(line.apply(5))), $this.doubleToFloat(BoxesRunTime.unboxToDouble(line.apply(6))), $this.doubleToFloat(BoxesRunTime.unboxToDouble(line.apply(7))));
    }

    private COSName COSName_SEJDA() {
        return this.COSName_SEJDA;
    }

    private void addMetadataCreatedBySejda(final PDField field) {
        field.getCOSObject().putIfAbsent(COSName_SEJDA(), (COSBase) new COSDictionary());
    }

    public boolean isCreatedBySejda(final PDField field) {
        return field.getCOSObject().containsKey(COSName_SEJDA());
    }

    private PDResources addFontResourceIf$default$3() {
        return acroForm().getDefaultResources();
    }

    private void addFontResourceIf(final String name, final PDType1Font font, final PDResources resources) {
        COSName Name = COSName.getPDFName(name);
        if (ImplicitJavaConversions$.MODULE$.javaIterableToScalaIterable(resources.getFontNames()).toList().contains(Name)) {
            return;
        }
        resources.put(Name, font);
    }

    public boolean hasXfa() {
        return acroForm().hasXFA();
    }

    public void removeXfa() {
        acroForm().setXFA(null);
    }

    private Seq<String> ensureValueCanBeDisplayed(final PDVariableText field) throws IOException {
        String originalText = field.getValueAsString().trim();
        Seq warnings = Nil$.MODULE$;
        String fieldName = field.getFullyQualifiedName();
        if (FontUtils.canDisplay(originalText, field.getAppearanceFont())) {
            return warnings;
        }
        String textNoControlChars = originalText.replaceAll("��", "").replaceAll("\u0002", "").replaceAll("\u0003", "").replaceAll("\u0007", "").replaceAll("\n", "").replaceAll("\u000b", " ").replaceAll("\f", "").replaceAll("\t", " ").replaceAll("\u0016", " ").replaceAll(" ", " ").replaceAll("\u000f", "").replaceAll("\u007f", "").replaceAll("\u0013", "").replaceAll("\u0015", "").replaceAll("\u2002", " ").replaceAll("\u2028", "").replaceAll("\u2029", "").replaceAll(" ", " ").replaceAll("\u200b", "").replaceAll("\ufeff", "").replaceAll("️", "");
        if (!(field instanceof PDTextField) || ((PDTextField) field).isMultiline()) {
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
        } else {
            textNoControlChars = textNoControlChars.replaceAll("\r", "");
            BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
        }
        Object obj = textNoControlChars;
        if (obj != null ? !obj.equals(originalText) : originalText != null) {
            logger().debug(new StringBuilder(35).append("Removing control chars from field: ").append(fieldName).toString());
            warnings = ImplicitJavaConversions$.MODULE$.listBufferToImmutableSeq(seq2mutableSeq(warnings).$plus$eq("Form fields containing non printable characters were detected, characters were removed"));
            try {
                field.setValue(textNoControlChars);
            } catch (Throwable th) {
                if (!NonFatal$.MODULE$.apply(th)) {
                    throw th;
                }
                BoxedUnit boxedUnit3 = BoxedUnit.UNIT;
            }
        }
        String textNoNewlines = ((field instanceof PDTextField) && ((PDTextField) field).isMultiline()) ? StringOps$.MODULE$.replaceAllLiterally$extension(Predef$.MODULE$.augmentString(StringOps$.MODULE$.replaceAllLiterally$extension(Predef$.MODULE$.augmentString(textNoControlChars), "\r", "")), "\n", "") : textNoControlChars;
        PDFont originalAppearanceFont = field.getAppearanceFont();
        if (!FontUtils.canDisplay(textNoNewlines, originalAppearanceFont)) {
            PDFont fallbackFont = FontUtils.findFontFor(acroForm().getDocument(), textNoNewlines, false, originalAppearanceFont);
            if (fallbackFont == null) {
                String supportedText = FontUtils.removeUnsupportedCharacters(textNoNewlines, this.doc);
                if (supportedText != null ? !supportedText.equals(textNoNewlines) : textNoNewlines != null) {
                    Set unsupportedChars = Predef$.MODULE$.wrapCharArray(textNoNewlines.toCharArray()).toSet().diff(Predef$.MODULE$.wrapCharArray(supportedText.toCharArray()).toSet());
                    if (unsupportedChars.nonEmpty()) {
                        String displayUnsupportedChars = ((IterableOnceOps) unsupportedChars.map(c -> {
                            return $anonfun$ensureValueCanBeDisplayed$1(BoxesRunTime.unboxToChar(c));
                        })).mkString(", ");
                        throw new RuntimeException(new StringBuilder(46).append("Unsupported characters (").append(displayUnsupportedChars).append(") detected: '").append(truncateForDisplay(textNoNewlines, truncateForDisplay$default$2())).append("' field: ").append(field.getFullyQualifiedName()).toString());
                    }
                }
                throw new RuntimeException(new StringBuilder(40).append("Unsupported characters detected, field: ").append(field.getFullyQualifiedName()).toString());
            }
            field.setAppearanceOverrideFont(fallbackFont);
            field.applyChange();
            logger().debug(new StringBuilder(67).append("Field: '").append(field.getFullyQualifiedName()).append("' can't render its value, will use font ").append(fallbackFont.getName()).append(" for better support").toString());
            warnings = ImplicitJavaConversions$.MODULE$.listBufferToImmutableSeq(seq2mutableSeq(warnings).$plus$eq("Form fields containing unsupported characters were detected, the font was changed"));
        }
        return warnings;
    }

    public static final /* synthetic */ String $anonfun$ensureValueCanBeDisplayed$1(final char c) {
        return StringUtils.asUnicodes(Character.toString(c));
    }

    public Seq<String> flatten() throws IOException {
        List fields = ((IterableOnceOps) allFields().map(x$13 -> {
            return x$13;
        })).toList();
        ObjectRef warnings = ObjectRef.create(Nil$.MODULE$);
        fields.foreach(x0$1 -> {
            $anonfun$flatten$2(this, warnings, x0$1);
            return BoxedUnit.UNIT;
        });
        fields.foreach(field -> {
            this.generateAppearancesIfRequired(field);
            return BoxedUnit.UNIT;
        });
        List fieldsToFlatten = fields.filter(x0$2 -> {
            return BoxesRunTime.boxToBoolean($anonfun$flatten$4(this, x0$2));
        });
        acroForm().flatten((java.util.List) JavaConverters$.MODULE$.seqAsJavaListConverter(fieldsToFlatten).asJava(), acroForm().isNeedAppearances());
        return (Seq) warnings.elem;
    }

    public static final /* synthetic */ void $anonfun$flatten$2(final AcroFormsHelper $this, final ObjectRef warnings$1, final PDField x0$1) throws IOException {
        if (x0$1 instanceof PDVariableText) {
            Seq w = $this.ensureValueCanBeDisplayed((PDVariableText) x0$1);
            warnings$1.elem = ImplicitJavaConversions$.MODULE$.listBufferToImmutableSeq($this.seq2mutableSeq((Seq) warnings$1.elem).$plus$plus$eq(w));
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
            return;
        }
        BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
    }

    public static final /* synthetic */ boolean $anonfun$flatten$4(final AcroFormsHelper $this, final PDField x0$2) {
        return ((x0$2 instanceof PDSignatureField) && $this.acroForm().isNeedAppearances()) ? false : true;
    }

    public boolean xfaIsDynamic() {
        return acroForm().xfaIsDynamic();
    }

    public void generateAppearancesIfRequired(final PDField field) throws IOException {
        boolean z = (field instanceof PDCheckBox) || (field instanceof PDRadioButton);
        if (z) {
            BooleanRef shouldApplyChanges = BooleanRef.create(false);
            ImplicitJavaConversions$.MODULE$.javaListToScalaSeq(field.getWidgets()).foreach(widget -> {
                $anonfun$generateAppearancesIfRequired$1(this, field, shouldApplyChanges, widget);
                return BoxedUnit.UNIT;
            });
            if (!shouldApplyChanges.elem) {
                BoxedUnit boxedUnit = BoxedUnit.UNIT;
                return;
            }
            ((PDButton) field).setIgnoreExportOptions(true);
            ((PDTerminalField) field).applyChange();
            BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
            return;
        }
        BoxedUnit boxedUnit3 = BoxedUnit.UNIT;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00c1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final /* synthetic */ void $anonfun$generateAppearancesIfRequired$1(final code.sejda.tasks.common.AcroFormsHelper r13, final org.sejda.sambox.pdmodel.interactive.form.PDField r14, final scala.runtime.BooleanRef r15, final org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationWidget r16) throws scala.MatchError {
        /*
            Method dump skipped, instructions count: 761
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: code.sejda.tasks.common.AcroFormsHelper.$anonfun$generateAppearancesIfRequired$1(code.sejda.tasks.common.AcroFormsHelper, org.sejda.sambox.pdmodel.interactive.form.PDField, scala.runtime.BooleanRef, org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationWidget):void");
    }

    public static final /* synthetic */ boolean $anonfun$generateAppearancesIfRequired$8(final Map.Entry x$19) {
        return x$19.getValue() != null;
    }

    private static final void putIfAbsentOrNull$1(final COSDictionary d, final COSName key, final COSObjectable value, final Set normalApSubDictKeys$1) {
        if (!normalApSubDictKeys$1.contains(key)) {
            d.removeItem(key);
        }
        d.putIfAbsent(key, value);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    public static final /* synthetic */ void $anonfun$generateAppearancesIfRequired$17(final AcroFormsHelper $this, final PDField field$1, final int height$2, final int borderWidth$2, final Some borderColorOpt$2, final PDPageContentStream cs) throws MatchError, IOException {
        if (field$1 instanceof PDCheckBox) {
            $this.drawCheckboxSelected(cs, height$2, borderWidth$2, borderColorOpt$2, $this.drawCheckboxSelected$default$5());
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
        } else {
            if (!(field$1 instanceof PDRadioButton)) {
                throw new MatchError(field$1);
            }
            $this.drawRadioSelected(cs, height$2, borderWidth$2, borderColorOpt$2, $this.drawRadioSelected$default$5());
            BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    public static final /* synthetic */ void $anonfun$generateAppearancesIfRequired$18(final AcroFormsHelper $this, final PDField field$1, final int height$2, final int borderWidth$2, final Some borderColorOpt$2, final PDPageContentStream cs) throws MatchError, IOException {
        if (field$1 instanceof PDCheckBox) {
            $this.drawCheckboxUnselected(cs, height$2, borderWidth$2, borderColorOpt$2);
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
        } else {
            if (!(field$1 instanceof PDRadioButton)) {
                throw new MatchError(field$1);
            }
            $this.drawRadioUnselected(cs, height$2, borderWidth$2, borderColorOpt$2);
            BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
        }
    }

    public int truncateForDisplay$default$2() {
        return 20;
    }

    public String truncateForDisplay(final String s, final int len) {
        return s.length() <= len ? s : new StringBuilder(3).append(StringOps$.MODULE$.take$extension(Predef$.MODULE$.augmentString(s), len).trim()).append("...").toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x00b0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void changeDefaultAppearance(final double r8, final org.sejda.sambox.pdmodel.interactive.form.PDVariableText r10) {
        /*
            Method dump skipped, instructions count: 265
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: code.sejda.tasks.common.AcroFormsHelper.changeDefaultAppearance(double, org.sejda.sambox.pdmodel.interactive.form.PDVariableText):void");
    }

    public void postProcessWidgetAndFieldWhereOneKid(final scala.collection.mutable.Map<Tuple2<String, FormFieldType>, PDTerminalField> fields) {
        fields.foreach(entry -> {
            $anonfun$postProcessWidgetAndFieldWhereOneKid$1(entry);
            return BoxedUnit.UNIT;
        });
    }

    public static final /* synthetic */ void $anonfun$postProcessWidgetAndFieldWhereOneKid$1(final Tuple2 entry) {
        PDTerminalField field = (PDTerminalField) entry._2();
        if (field.getWidgets().size() == 1) {
            PDAnnotationWidget widget = field.getWidgets().get(0);
            field.getCOSObject().removeItem(COSName.TYPE);
            field.getCOSObject().removeItem(COSName.SUBTYPE);
            widget.getCOSObject().setItem(COSName.Q, field.getCOSObject().getItem(COSName.Q));
        }
    }
}
