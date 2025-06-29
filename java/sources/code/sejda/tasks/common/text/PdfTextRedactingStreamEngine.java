package code.sejda.tasks.common.text;

import code.sejda.tasks.common.OperatorAndOperands;
import code.sejda.tasks.common.RedactedStream;
import code.util.Loggable;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.sejda.impl.sambox.pro.component.contentstream.operator.text.SetFontAndSizeLenient;
import org.sejda.model.TopLeftRectangularBox;
import org.sejda.sambox.contentstream.operator.color.SetNonStrokingColor;
import org.sejda.sambox.contentstream.operator.color.SetNonStrokingColorN;
import org.sejda.sambox.contentstream.operator.color.SetNonStrokingColorSpace;
import org.sejda.sambox.contentstream.operator.color.SetNonStrokingDeviceCMYKColor;
import org.sejda.sambox.contentstream.operator.color.SetNonStrokingDeviceGrayColor;
import org.sejda.sambox.contentstream.operator.color.SetNonStrokingDeviceRGBColor;
import org.sejda.sambox.contentstream.operator.color.SetStrokingColor;
import org.sejda.sambox.contentstream.operator.color.SetStrokingColorSpace;
import org.sejda.sambox.contentstream.operator.color.SetStrokingDeviceCMYKColor;
import org.sejda.sambox.contentstream.operator.color.SetStrokingDeviceGrayColor;
import org.sejda.sambox.contentstream.operator.color.SetStrokingDeviceRGBColor;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSFloat;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSNumber;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.cos.COSString;
import org.sejda.sambox.cos.IndirectCOSObjectIdentifier;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.PDResources;
import org.sejda.sambox.pdmodel.font.PDFont;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.pdmodel.graphics.form.PDFormXObject;
import org.sejda.sambox.pdmodel.graphics.form.PDTransparencyGroup;
import org.sejda.sambox.pdmodel.graphics.state.PDTextState;
import org.sejda.sambox.pdmodel.graphics.state.RenderingMode;
import org.sejda.sambox.text.PDFTextStreamEngine;
import org.sejda.sambox.text.TextPosition;
import org.slf4j.Logger;
import scala.Option$;
import scala.Predef$;
import scala.collection.ArrayOps$;
import scala.collection.IterableOnceOps;
import scala.collection.IterableOps;
import scala.collection.JavaConverters$;
import scala.collection.mutable.StringBuilder;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;

/* compiled from: PdfTextRedactingStreamEngine.scala */
@ScalaSignature(bytes = "\u0006\u0005\reg\u0001B(Q\u0001mC\u0001\u0002\u001c\u0001\u0003\u0006\u0004%\t!\u001c\u0005\ti\u0002\u0011\t\u0011)A\u0005]\")Q\u000f\u0001C\u0001m\"9!\u0010\u0001a\u0001\n\u0003Y\b\"CA\u000b\u0001\u0001\u0007I\u0011AA\f\u0011\u001d\t)\u0003\u0001Q!\nqD\u0011\"a\n\u0001\u0001\u0004%\t!!\u000b\t\u0013\u0005m\u0002\u00011A\u0005\u0002\u0005u\u0002\u0002CA!\u0001\u0001\u0006K!a\u000b\t\u0013\u0005\r\u0003\u00011A\u0005\u0002\u0005\u0015\u0003\"CA'\u0001\u0001\u0007I\u0011AA(\u0011!\t\u0019\u0006\u0001Q!\n\u0005\u001d\u0003\"CA+\u0001\u0001\u0007I\u0011AA,\u0011%\tI\u0007\u0001a\u0001\n\u0003\tY\u0007\u0003\u0005\u0002p\u0001\u0001\u000b\u0015BA-\u0011%\t\t\b\u0001a\u0001\n\u0003\t\u0019\bC\u0005\u0002\u0006\u0002\u0001\r\u0011\"\u0001\u0002\b\"A\u00111\u0012\u0001!B\u0013\t)\bC\u0005\u0002\u000e\u0002\u0001\r\u0011\"\u0001\u0002\u0010\"I\u0011Q\u0014\u0001A\u0002\u0013\u0005\u0011q\u0014\u0005\t\u0003G\u0003\u0001\u0015)\u0003\u0002\u0012\"I\u0011Q\u0015\u0001C\u0002\u0013%\u0011q\u0015\u0005\t\u0003\u007f\u0003\u0001\u0015!\u0003\u0002*\"I\u0011\u0011\u0019\u0001A\u0002\u0013%\u00111\u0019\u0005\n\u0003'\u0004\u0001\u0019!C\u0005\u0003+D\u0001\"!7\u0001A\u0003&\u0011Q\u0019\u0005\n\u00037\u0004\u0001\u0019!C\u0005\u0003\u000bB\u0011\"!8\u0001\u0001\u0004%I!a8\t\u0011\u0005\r\b\u0001)Q\u0005\u0003\u000fB\u0011\"!:\u0001\u0001\u0004%I!a:\t\u0013\u0005U\b\u00011A\u0005\n\u0005]\b\u0002CA~\u0001\u0001\u0006K!!;\t\u0013\u0005u\b\u00011A\u0005\n\u0005\r\u0007\"CA��\u0001\u0001\u0007I\u0011\u0002B\u0001\u0011!\u0011)\u0001\u0001Q!\n\u0005\u0015\u0007\"\u0003B\u0004\u0001\u0001\u0007I\u0011BAb\u0011%\u0011I\u0001\u0001a\u0001\n\u0013\u0011Y\u0001\u0003\u0005\u0003\u0010\u0001\u0001\u000b\u0015BAc\u0011%\u0011\t\u0002\u0001a\u0001\n\u0013\t)\u0005C\u0005\u0003\u0014\u0001\u0001\r\u0011\"\u0003\u0003\u0016!A!\u0011\u0004\u0001!B\u0013\t9\u0005C\u0005\u0003\u001c\u0001\u0001\r\u0011\"\u0003\u0002F!I!Q\u0004\u0001A\u0002\u0013%!q\u0004\u0005\t\u0005G\u0001\u0001\u0015)\u0003\u0002H!I!Q\u0005\u0001A\u0002\u0013%!q\u0005\u0005\n\u0005_\u0001\u0001\u0019!C\u0005\u0005cA\u0001B!\u000e\u0001A\u0003&!\u0011\u0006\u0005\n\u0005o\u0001\u0001\u0019!C\u0005\u0005OA\u0011B!\u000f\u0001\u0001\u0004%IAa\u000f\t\u0011\t}\u0002\u0001)Q\u0005\u0005SA\u0011B!\u0011\u0001\u0001\u0004%IAa\n\t\u0013\t\r\u0003\u00011A\u0005\n\t\u0015\u0003\u0002\u0003B%\u0001\u0001\u0006KA!\u000b\t\u0013\t-\u0003\u00011A\u0005\n\t5\u0003\"\u0003B,\u0001\u0001\u0007I\u0011\u0002B-\u0011!\u0011i\u0006\u0001Q!\n\t=\u0003\"\u0003B0\u0001\u0001\u0007I\u0011\u0002B1\u0011%\u0011Y\u0007\u0001a\u0001\n\u0013\u0011i\u0007\u0003\u0005\u0003r\u0001\u0001\u000b\u0015\u0002B2\u0011%\u0011\u0019\b\u0001b\u0001\n\u0013\u0011)\b\u0003\u0005\u0003\u0004\u0002\u0001\u000b\u0011\u0002B<\u0011%\u0011)\t\u0001b\u0001\n\u0013\u00119\t\u0003\u0005\u0003\u0012\u0002\u0001\u000b\u0011\u0002BE\u0011%\u0011\u0019\n\u0001b\u0001\n\u0013\u00119\t\u0003\u0005\u0003\u0016\u0002\u0001\u000b\u0011\u0002BE\u0011\u001d\u00119\n\u0001C!\u00053CqAa?\u0001\t\u0003\u0012i\u0010C\u0004\u0004\u001a\u0001!\tea\u0007\t\u000f\rE\u0002\u0001\"\u0003\u00044!911\t\u0001\u0005B\r\u0015\u0003bBB/\u0001\u0011%1q\f\u0005\b\u0007W\u0002A\u0011KB7\u0011\u001d\u00199\n\u0001C)\u00073Cqaa)\u0001\t\u0003\u0019)\u000bC\u0004\u0004.\u0002!\taa,\t\u000f\rM\u0006\u0001\"\u0001\u00040\"a1Q\u0017\u0001\u0011\u0002\u0003\u0005\t\u0011\"\u0001\u00048\"a11\u0019\u0001\u0011\u0002\u0003\u0005\t\u0011\"\u0001\u0004F\na\u0002\u000b\u001a4UKb$(+\u001a3bGRLgnZ*ue\u0016\fW.\u00128hS:,'BA)S\u0003\u0011!X\r\u001f;\u000b\u0005M#\u0016AB2p[6|gN\u0003\u0002V-\u0006)A/Y:lg*\u0011q\u000bW\u0001\u0006g\u0016TG-\u0019\u0006\u00023\u0006!1m\u001c3f\u0007\u0001\u00192\u0001\u0001/g!\tiF-D\u0001_\u0015\t\tvL\u0003\u0002aC\u000611/Y7c_bT!a\u00162\u000b\u0003\r\f1a\u001c:h\u0013\t)gLA\nQ\t\u001a#V\r\u001f;TiJ,\u0017-\\#oO&tW\r\u0005\u0002hU6\t\u0001N\u0003\u0002j1\u0006!Q\u000f^5m\u0013\tY\u0007N\u0001\u0005M_\u001e<\u0017M\u00197f\u0003\r\u0011w\u000e_\u000b\u0002]B\u0011qN]\u0007\u0002a*\u0011\u0011/Y\u0001\u0006[>$W\r\\\u0005\u0003gB\u0014Q\u0003V8q\u0019\u00164GOU3di\u0006tw-\u001e7be\n{\u00070\u0001\u0003c_b\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0002xsB\u0011\u0001\u0010A\u0007\u0002!\")An\u0001a\u0001]\u0006q!/\u001a3bGR,Gm\u0015;sS:<W#\u0001?\u0011\u0007u\fyAD\u0002\u007f\u0003\u0013q1a`A\u0003\u001b\t\t\tAC\u0002\u0002\u0004i\u000ba\u0001\u0010:p_Rt\u0014BAA\u0004\u0003\u0015\u00198-\u00197b\u0013\u0011\tY!!\u0004\u0002\u000fA\f7m[1hK*\u0011\u0011qA\u0005\u0005\u0003#\t\u0019BA\u0007TiJLgn\u001a\"vS2$WM\u001d\u0006\u0005\u0003\u0017\ti!\u0001\nsK\u0012\f7\r^3e'R\u0014\u0018N\\4`I\u0015\fH\u0003BA\r\u0003C\u0001B!a\u0007\u0002\u001e5\u0011\u0011QB\u0005\u0005\u0003?\tiA\u0001\u0003V]&$\b\u0002CA\u0012\u000b\u0005\u0005\t\u0019\u0001?\u0002\u0007a$\u0013'A\bsK\u0012\f7\r^3e'R\u0014\u0018N\\4!\u00031\u0011X\rZ1di\u0016$gi\u001c8u+\t\tY\u0003\u0005\u0003\u0002.\u0005]RBAA\u0018\u0015\u0011\t\t$a\r\u0002\t\u0019|g\u000e\u001e\u0006\u0004\u0003ky\u0016a\u00029e[>$W\r\\\u0005\u0005\u0003s\tyC\u0001\u0004Q\t\u001a{g\u000e^\u0001\u0011e\u0016$\u0017m\u0019;fI\u001a{g\u000e^0%KF$B!!\u0007\u0002@!I\u00111\u0005\u0005\u0002\u0002\u0003\u0007\u00111F\u0001\u000ee\u0016$\u0017m\u0019;fI\u001a{g\u000e\u001e\u0011\u0002!I,G-Y2uK\u00124uN\u001c;TSj,WCAA$!\u0011\tY\"!\u0013\n\t\u0005-\u0013Q\u0002\u0002\u0006\r2|\u0017\r^\u0001\u0015e\u0016$\u0017m\u0019;fI\u001a{g\u000e^*ju\u0016|F%Z9\u0015\t\u0005e\u0011\u0011\u000b\u0005\n\u0003GY\u0011\u0011!a\u0001\u0003\u000f\n\u0011C]3eC\u000e$X\r\u001a$p]R\u001c\u0016N_3!\u0003E\u0011X\rZ1di\u0016$gi\u001c8u\u0007>dwN]\u000b\u0003\u00033\u0002B!a\u0017\u0002f5\u0011\u0011Q\f\u0006\u0005\u0003?\n\t'A\u0003d_2|'O\u0003\u0003\u0002d\u0005M\u0012\u0001C4sCBD\u0017nY:\n\t\u0005\u001d\u0014Q\f\u0002\b!\u0012\u001bu\u000e\\8s\u0003U\u0011X\rZ1di\u0016$gi\u001c8u\u0007>dwN]0%KF$B!!\u0007\u0002n!I\u00111\u0005\b\u0002\u0002\u0003\u0007\u0011\u0011L\u0001\u0013e\u0016$\u0017m\u0019;fI\u001a{g\u000e^\"pY>\u0014\b%\u0001\u000bsK\u0012\f7\r^3e)\u0016DH\u000fU8tSRLwN\\\u000b\u0003\u0003k\u0002B!a\u001e\u0002\u00026\u0011\u0011\u0011\u0010\u0006\u0005\u0003w\ni(A\u0002boRT!!a \u0002\t)\fg/Y\u0005\u0005\u0003\u0007\u000bIHA\u0003Q_&tG/\u0001\rsK\u0012\f7\r^3e)\u0016DH\u000fU8tSRLwN\\0%KF$B!!\u0007\u0002\n\"I\u00111E\t\u0002\u0002\u0003\u0007\u0011QO\u0001\u0016e\u0016$\u0017m\u0019;fIR+\u0007\u0010\u001e)pg&$\u0018n\u001c8!\u0003e\u0011X\rZ1di\u0016$G+\u001a=u%\u0016tG-\u001a:j]\u001elu\u000eZ3\u0016\u0005\u0005E\u0005\u0003BAJ\u00033k!!!&\u000b\t\u0005]\u0015\u0011M\u0001\u0006gR\fG/Z\u0005\u0005\u00037\u000b)JA\u0007SK:$WM]5oO6{G-Z\u0001\u001ee\u0016$\u0017m\u0019;fIR+\u0007\u0010\u001e*f]\u0012,'/\u001b8h\u001b>$Wm\u0018\u0013fcR!\u0011\u0011DAQ\u0011%\t\u0019\u0003FA\u0001\u0002\u0004\t\t*\u0001\u000esK\u0012\f7\r^3e)\u0016DHOU3oI\u0016\u0014\u0018N\\4N_\u0012,\u0007%A\nee\u0006<xJ\u00196fGRt\u0015-\\3Ti\u0006\u001c7.\u0006\u0002\u0002*B1\u00111VAX\u0003gk!!!,\u000b\u0007%\fi(\u0003\u0003\u00022\u00065&AC!se\u0006LH)Z9vKB!\u0011QWA^\u001b\t\t9LC\u0002\u0002:~\u000b1aY8t\u0013\u0011\ti,a.\u0003\u000f\r{5KT1nK\u0006!BM]1x\u001f\nTWm\u0019;OC6,7\u000b^1dW\u0002\nq\u0002\\1tiNCwn\u001e8TiJLgnZ\u000b\u0003\u0003\u000b\u0004B!a2\u0002R6\u0011\u0011\u0011\u001a\u0006\u0005\u0003\u0017\fi-A\u0004nkR\f'\r\\3\u000b\t\u0005=\u0017QB\u0001\u000bG>dG.Z2uS>t\u0017\u0002BA\t\u0003\u0013\f1\u0003\\1tiNCwn\u001e8TiJLgnZ0%KF$B!!\u0007\u0002X\"I\u00111E\r\u0002\u0002\u0003\u0007\u0011QY\u0001\u0011Y\u0006\u001cHo\u00155po:\u001cFO]5oO\u0002\nQ\u0003\\1tiNCwn\u001e8XS\u0012$\bn\u00144Ta\u0006\u001cW-A\rmCN$8\u000b[8x]^KG\r\u001e5PMN\u0003\u0018mY3`I\u0015\fH\u0003BA\r\u0003CD\u0011\"a\t\u001d\u0003\u0003\u0005\r!a\u0012\u0002-1\f7\u000f^*i_^tw+\u001b3uQ>37\u000b]1dK\u0002\n\u0011\u0006\\1tiNCwn\u001e8TiJLgn\u001a(pi6\u000bGo\u00195j]\u001e\u0014V\rZ1di&|gNR5mi\u0016\u0014XCAAu!\u0019\tY+a;\u0002p&!\u0011Q^AW\u0005%\t%O]1z\u0019&\u001cH\u000f\u0005\u0003\u0002\u001c\u0005E\u0018\u0002BAz\u0003\u001b\u00111!\u00138u\u00035b\u0017m\u001d;TQ><hn\u0015;sS:<gj\u001c;NCR\u001c\u0007.\u001b8h%\u0016$\u0017m\u0019;j_:4\u0015\u000e\u001c;fe~#S-\u001d\u000b\u0005\u00033\tI\u0010C\u0005\u0002$}\t\t\u00111\u0001\u0002j\u0006QC.Y:u'\"|wO\\*ue&twMT8u\u001b\u0006$8\r[5oOJ+G-Y2uS>tg)\u001b7uKJ\u0004\u0013a\f7bgR\u001c\u0006n\\<o'R\u0014\u0018N\\4CK\u001a|'/\u001a(pi6\u000bGo\u00195j]\u001e\u0014V\rZ1di&|gNR5mi\u0016\u0014\u0018a\r7bgR\u001c\u0006n\\<o'R\u0014\u0018N\\4CK\u001a|'/\u001a(pi6\u000bGo\u00195j]\u001e\u0014V\rZ1di&|gNR5mi\u0016\u0014x\fJ3r)\u0011\tIBa\u0001\t\u0013\u0005\r\"%!AA\u0002\u0005\u0015\u0017\u0001\r7bgR\u001c\u0006n\\<o'R\u0014\u0018N\\4CK\u001a|'/\u001a(pi6\u000bGo\u00195j]\u001e\u0014V\rZ1di&|gNR5mi\u0016\u0014\b%\u0001\u0018mCN$8\u000b[8x]N#(/\u001b8h\u0003\u001a$XM\u001d(pi6\u000bGo\u00195j]\u001e\u0014V\rZ1di&|gNR5mi\u0016\u0014\u0018A\r7bgR\u001c\u0006n\\<o'R\u0014\u0018N\\4BMR,'OT8u\u001b\u0006$8\r[5oOJ+G-Y2uS>tg)\u001b7uKJ|F%Z9\u0015\t\u0005e!Q\u0002\u0005\n\u0003G)\u0013\u0011!a\u0001\u0003\u000b\fq\u0006\\1tiNCwn\u001e8TiJLgnZ!gi\u0016\u0014hj\u001c;NCR\u001c\u0007.\u001b8h%\u0016$\u0017m\u0019;j_:4\u0015\u000e\u001c;fe\u0002\nQ\u0003\\1tiNCwn\u001e8TiJLgn\u001a$jeN$\b,A\rmCN$8\u000b[8x]N#(/\u001b8h\r&\u00148\u000f\u001e-`I\u0015\fH\u0003BA\r\u0005/A\u0011\"a\t)\u0003\u0003\u0005\r!a\u0012\u0002-1\f7\u000f^*i_^t7\u000b\u001e:j]\u001e4\u0015N]:u1\u0002\nA\u0003\\1tiNCwn\u001e8TiJLgn\u001a'bgRD\u0016\u0001\u00077bgR\u001c\u0006n\\<o'R\u0014\u0018N\\4MCN$\bl\u0018\u0013fcR!\u0011\u0011\u0004B\u0011\u0011%\t\u0019cKA\u0001\u0002\u0004\t9%A\u000bmCN$8\u000b[8x]N#(/\u001b8h\u0019\u0006\u001cH\u000f\u0017\u0011\u0002C!\f7\u000fV3yi:{G/T1uG\"Lgn\u001a*fI\u0006\u001cG/[8o\r&dG/\u001a:\u0016\u0005\t%\u0002\u0003BA\u000e\u0005WIAA!\f\u0002\u000e\t9!i\\8mK\u0006t\u0017!\n5bgR+\u0007\u0010\u001e(pi6\u000bGo\u00195j]\u001e\u0014V\rZ1di&|gNR5mi\u0016\u0014x\fJ3r)\u0011\tIBa\r\t\u0013\u0005\rb&!AA\u0002\t%\u0012A\t5bgR+\u0007\u0010\u001e(pi6\u000bGo\u00195j]\u001e\u0014V\rZ1di&|gNR5mi\u0016\u0014\b%\u0001\fnCR\u001c\u0007.Z:SK\u0012\f7\r^5p]\u001aKG\u000e^3s\u0003ii\u0017\r^2iKN\u0014V\rZ1di&|gNR5mi\u0016\u0014x\fJ3r)\u0011\tIB!\u0010\t\u0013\u0005\r\u0012'!AA\u0002\t%\u0012aF7bi\u000eDWm\u001d*fI\u0006\u001cG/[8o\r&dG/\u001a:!\u0003}i\u0017\r^2iKN\u0014V\rZ1di&|gNR5mi\u0016\u0014\b+\u0019:uS\u0006dG._\u0001$[\u0006$8\r[3t%\u0016$\u0017m\u0019;j_:4\u0015\u000e\u001c;feB\u000b'\u000f^5bY2Lx\fJ3r)\u0011\tIBa\u0012\t\u0013\u0005\rB'!AA\u0002\t%\u0012\u0001I7bi\u000eDWm\u001d*fI\u0006\u001cG/[8o\r&dG/\u001a:QCJ$\u0018.\u00197ms\u0002\n\u0001CZ5mi\u0016\u0014X\rZ(qKJ\fg\u000eZ:\u0016\u0005\t=\u0003CBAV\u0003W\u0014\t\u0006\u0005\u0003\u00026\nM\u0013\u0002\u0002B+\u0003o\u0013qaQ(T\u0005\u0006\u001cX-\u0001\u000bgS2$XM]3e\u001fB,'/\u00198eg~#S-\u001d\u000b\u0005\u00033\u0011Y\u0006C\u0005\u0002$]\n\t\u00111\u0001\u0003P\u0005\tb-\u001b7uKJ,Gm\u00149fe\u0006tGm\u001d\u0011\u0002\u001d\u0019LG\u000e^3sK\u0012\u001cFO]3b[V\u0011!1\r\t\u0005\u0005K\u00129'D\u0001S\u0013\r\u0011IG\u0015\u0002\u000f%\u0016$\u0017m\u0019;fIN#(/Z1n\u0003I1\u0017\u000e\u001c;fe\u0016$7\u000b\u001e:fC6|F%Z9\u0015\t\u0005e!q\u000e\u0005\n\u0003GQ\u0014\u0011!a\u0001\u0005G\nqBZ5mi\u0016\u0014X\rZ*ue\u0016\fW\u000eI\u0001\u0017e\u0016$\u0017m\u0019;fI\u001a{'/\u001c-PE*,7\r^%egV\u0011!q\u000f\t\u0007\u0003W\u0013IH! \n\t\tm\u0014Q\u0016\u0002\b\u0011\u0006\u001c\bnU3u!\u0011\t)La \n\t\t\u0005\u0015q\u0017\u0002\u001c\u0013:$\u0017N]3di\u000e{5k\u00142kK\u000e$\u0018\nZ3oi&4\u0017.\u001a:\u0002/I,G-Y2uK\u00124uN]7Y\u001f\nTWm\u0019;JIN\u0004\u0013!\u0005:fI\u0006\u001cG/\u001a3Pa\u0016\u0014\u0018\r^8sgV\u0011!\u0011\u0012\t\u0007\u0003W\u000bYOa#\u0011\t\t\u0015$QR\u0005\u0004\u0005\u001f\u0013&aE(qKJ\fGo\u001c:B]\u0012|\u0005/\u001a:b]\u0012\u001c\u0018A\u0005:fI\u0006\u001cG/\u001a3Pa\u0016\u0014\u0018\r^8sg\u0002\n!\u0004]1si&\fG\u000e\\=SK\u0012\f7\r^3e\u001fB,'/\u0019;peN\f1\u0004]1si&\fG\u000e\\=SK\u0012\f7\r^3e\u001fB,'/\u0019;peN\u0004\u0013a\u00039s_\u000e,7o\u001d)bO\u0016$B!!\u0007\u0003\u001c\"9!Q\u0014\"A\u0002\t}\u0015\u0001\u00029bO\u0016\u0004BA!)\u0003$6\u0011\u00111G\u0005\u0005\u0005K\u000b\u0019D\u0001\u0004Q\tB\u000bw-\u001a\u0015\u0006\u0005\n%&1\u0018\t\u0007\u00037\u0011YKa,\n\t\t5\u0016Q\u0002\u0002\u0007i\"\u0014xn^:\u0011\t\tE&qW\u0007\u0003\u0005gSAA!.\u0002~\u0005\u0011\u0011n\\\u0005\u0005\u0005s\u0013\u0019LA\u0006J\u001f\u0016C8-\u001a9uS>t\u0017g\u0002\u0010\u0003>\n5'\u0011 \t\u0005\u0005\u007f\u00139M\u0004\u0003\u0003B\n\r\u0007cA@\u0002\u000e%!!QYA\u0007\u0003\u0019\u0001&/\u001a3fM&!!\u0011\u001aBf\u0005\u0019\u0019FO]5oO*!!QYA\u0007c%\u0019#q\u001aBl\u0005_\u0014I.\u0006\u0003\u0003R\nMWC\u0001B_\t\u001d\u0011)N\u0017b\u0001\u0005?\u0014\u0011\u0001V\u0005\u0005\u00053\u0014Y.A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$H%\r\u0006\u0005\u0005;\fi!\u0001\u0004uQJ|wo]\t\u0005\u0005C\u00149\u000f\u0005\u0003\u0002\u001c\t\r\u0018\u0002\u0002Bs\u0003\u001b\u0011qAT8uQ&tw\r\u0005\u0003\u0003j\n-h\u0002BA\u000e\u0003\u0013IAA!<\u0002\u0014\tIA\u000b\u001b:po\u0006\u0014G.Z\u0019\nG\tE(1\u001fB{\u0005;tA!a\u0007\u0003t&!!Q\\A\u0007c\u001d\u0011\u00131DA\u0007\u0005o\u0014Qa]2bY\u0006\f4A\nBX\u0003!\u0019\bn\\<G_JlG\u0003BA\r\u0005\u007fDqa!\u0001D\u0001\u0004\u0019\u0019!\u0001\u0003g_Jl\u0007\u0003BB\u0003\u0007\u0013i!aa\u0002\u000b\t\r\u0005\u0011\u0011M\u0005\u0005\u0007\u0017\u00199AA\u0007Q\t\u001a{'/\u001c-PE*,7\r\u001e\u0015\u0006\u0007\n%6qB\u0019\b=\tu6\u0011CB\fc%\u0019#q\u001aBl\u0007'\u0011I.M\u0005$\u0005c\u0014\u0019p!\u0006\u0003^F:!%a\u0007\u0002\u000e\t]\u0018g\u0001\u0014\u00030\u0006)2\u000f[8x)J\fgn\u001d9be\u0016t7-_$s_V\u0004H\u0003BA\r\u0007;Aqa!\u0001E\u0001\u0004\u0019y\u0002\u0005\u0003\u0004\u0006\r\u0005\u0012\u0002BB\u0012\u0007\u000f\u00111\u0003\u0015#Ue\u0006t7\u000f]1sK:\u001c\u0017p\u0012:pkBDS\u0001\u0012BU\u0007O\ttA\bB_\u0007S\u0019y#M\u0005$\u0005\u001f\u00149na\u000b\u0003ZFJ1E!=\u0003t\u000e5\"Q\\\u0019\bE\u0005m\u0011Q\u0002B|c\r1#qV\u0001\u000be\u0016$\u0017m\u0019;G_JlG\u0003BA\r\u0007kAqa!\u0001F\u0001\u0004\u0019\u0019\u0001K\u0003F\u0005S\u001bI$M\u0004\u001f\u0005{\u001bYd!\u00112\u0013\r\u0012yMa6\u0004>\te\u0017'C\u0012\u0003r\nM8q\bBoc\u001d\u0011\u00131DA\u0007\u0005o\f4A\nBX\u0003=\u0019\bn\\<UKb$8\u000b\u001e:j]\u001e\u001cH\u0003BA\r\u0007\u000fBqa!\u0013G\u0001\u0004\u0019Y%A\u0003beJ\f\u0017\u0010\u0005\u0003\u00026\u000e5\u0013\u0002BB(\u0003o\u0013\u0001bQ(T\u0003J\u0014\u0018-\u001f\u0015\u0006\r\n%61K\u0019\b=\tu6QKB.c%\u0019#q\u001aBl\u0007/\u0012I.M\u0005$\u0005c\u0014\u0019p!\u0017\u0003^F:!%a\u0007\u0002\u000e\t]\u0018g\u0001\u0014\u00030\u0006QAo\u001c#fEV<7\u000b\u001e:\u0015\t\tu6\u0011\r\u0005\b\u0007G:\u0005\u0019AB3\u0003\ry'M\u001b\t\u0005\u00037\u00199'\u0003\u0003\u0004j\u00055!AB!osJ+g-A\bqe>\u001cWm]:Pa\u0016\u0014\u0018\r^8s)\u0019\tIba\u001c\u0004\u0002\"91\u0011\u000f%A\u0002\rM\u0014\u0001C8qKJ\fGo\u001c:\u0011\t\rU4QP\u0007\u0003\u0007oRAa!\u001d\u0004z)\u001911P0\u0002\u001b\r|g\u000e^3oiN$(/Z1n\u0013\u0011\u0019yha\u001e\u0003\u0011=\u0003XM]1u_JDqaa!I\u0001\u0004\u0019))\u0001\u0005pa\u0016\u0014\u0018M\u001c3t!\u0019\tYka\"\u0003R%!1\u0011RAW\u0005\u0011a\u0015n\u001d;)\u000b!\u0013Ik!$2\u000fy\u0011ila$\u0004\u0016FJ1Ea4\u0003X\u000eE%\u0011\\\u0019\nG\tE(1_BJ\u0005;\ftAIA\u000e\u0003\u001b\u001190M\u0002'\u0005_\u000b1\u0003\u001d:pG\u0016\u001c8\u000fV3yiB{7/\u001b;j_:$B!!\u0007\u0004\u001c\"1\u0011+\u0013a\u0001\u0007;\u00032!XBP\u0013\r\u0019\tK\u0018\u0002\r)\u0016DH\u000fU8tSRLwN\\\u0001\u001aO\u0016$(+\u001a3bGR,GMR8s[b{%M[3di&#7/\u0006\u0002\u0004(B1\u00111VBU\u0005{JAaa+\u0002.\n\u00191+\u001a;\u0002)\u001d,GOU3eC\u000e$X\rZ(qKJ\fGo\u001c:t+\t\u0019\t\f\u0005\u0004\u0002,\u000e\u001d%1R\u0001\u001eO\u0016$\b+\u0019:uS\u0006dG.\u001f*fI\u0006\u001cG/\u001a3Pa\u0016\u0014\u0018\r^8sg\u0006i\u0002O]8uK\u000e$X\r\u001a\u0013baBd\u0017\u0010V3yi\u0006#'.^:u[\u0016tG\u000f\u0006\u0003\u0004:\u000e\u0005GCBA\r\u0007w\u001bi\fC\u0005\u0002$5\u000b\t\u00111\u0001\u0002H!I1qX'\u0002\u0002\u0003\u0007\u0011qI\u0001\u0004q\u0012\u0012\u0004\u0002CA\u0012\u001b\u0006\u0005\t\u0019A<\u0002%A\u0014x\u000e^3di\u0016$Ge\u001d5poR+\u0007\u0010\u001e\u000b\u0005\u0007\u000f\u001c9\u000e\u0006\u0003\u0002\u001a\r%\u0007\"CA\u0012\u001d\u0006\u0005\t\u0019ABf!\u0019\tYb!4\u0004R&!1qZA\u0007\u0005\u0015\t%O]1z!\u0011\tYba5\n\t\rU\u0017Q\u0002\u0002\u0005\u0005f$X\r\u0003\u0005\u0002$9\u000b\t\u00111\u0001x\u0001")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/text/PdfTextRedactingStreamEngine.class */
public class PdfTextRedactingStreamEngine extends PDFTextStreamEngine implements Loggable {
    private final TopLeftRectangularBox box;
    private StringBuilder redactedString;
    private PDFont redactedFont;
    private float redactedFontSize;
    private PDColor redactedFontColor;
    private Point redactedTextPosition;
    private RenderingMode redactedTextRenderingMode;
    private final ArrayDeque<COSName> drawObjectNameStack;
    private StringBuilder lastShownString;
    private float lastShownWidthOfSpace;
    private ArrayList<Object> lastShownStringNotMatchingRedactionFilter;
    private StringBuilder lastShownStringBeforeNotMatchingRedactionFilter;
    private StringBuilder lastShownStringAfterNotMatchingRedactionFilter;
    private float lastShownStringFirstX;
    private float lastShownStringLastX;
    private boolean hasTextNotMatchingRedactionFilter;
    private boolean matchesRedactionFilter;
    private boolean matchesRedactionFilterPartially;
    private ArrayList<COSBase> filteredOperands;
    private RedactedStream filteredStream;
    private final HashSet<IndirectCOSObjectIdentifier> redactedFormXObjectIds;
    private final ArrayList<OperatorAndOperands> redactedOperators;
    private final ArrayList<OperatorAndOperands> partiallyRedactedOperators;
    private transient Logger logger;
    private volatile transient boolean bitmap$trans$0;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v8, types: [code.sejda.tasks.common.text.PdfTextRedactingStreamEngine] */
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

    public TopLeftRectangularBox box() {
        return this.box;
    }

    public PdfTextRedactingStreamEngine(final TopLeftRectangularBox box) {
        this.box = box;
        Loggable.$init$(this);
        addOperator(new SetStrokingColorSpace());
        addOperator(new SetStrokingColor());
        addOperator(new SetStrokingDeviceCMYKColor());
        addOperator(new SetStrokingDeviceGrayColor());
        addOperator(new SetStrokingDeviceRGBColor());
        addOperator(new SetNonStrokingColor());
        addOperator(new SetNonStrokingColorN());
        addOperator(new SetNonStrokingColorSpace());
        addOperator(new SetNonStrokingDeviceCMYKColor());
        addOperator(new SetNonStrokingDeviceGrayColor());
        addOperator(new SetNonStrokingDeviceRGBColor());
        addOperator(new SetFontAndSizeLenient());
        this.redactedString = new StringBuilder();
        this.redactedFont = null;
        this.redactedFontSize = 0.0f;
        this.redactedFontColor = null;
        this.redactedTextPosition = null;
        this.redactedTextRenderingMode = null;
        this.drawObjectNameStack = new ArrayDeque<>();
        this.lastShownString = new StringBuilder();
        this.lastShownWidthOfSpace = 0.0f;
        this.lastShownStringNotMatchingRedactionFilter = new ArrayList<>();
        this.lastShownStringBeforeNotMatchingRedactionFilter = new StringBuilder();
        this.lastShownStringAfterNotMatchingRedactionFilter = new StringBuilder();
        this.lastShownStringFirstX = 0.0f;
        this.lastShownStringLastX = 0.0f;
        this.hasTextNotMatchingRedactionFilter = false;
        this.matchesRedactionFilter = false;
        this.matchesRedactionFilterPartially = false;
        this.filteredOperands = new ArrayList<>();
        this.filteredStream = new RedactedStream();
        this.redactedFormXObjectIds = new HashSet<>();
        this.redactedOperators = new ArrayList<>();
        this.partiallyRedactedOperators = new ArrayList<>();
    }

    public StringBuilder redactedString() {
        return this.redactedString;
    }

    public void redactedString_$eq(final StringBuilder x$1) {
        this.redactedString = x$1;
    }

    public PDFont redactedFont() {
        return this.redactedFont;
    }

    public void redactedFont_$eq(final PDFont x$1) {
        this.redactedFont = x$1;
    }

    public float redactedFontSize() {
        return this.redactedFontSize;
    }

    public void redactedFontSize_$eq(final float x$1) {
        this.redactedFontSize = x$1;
    }

    public PDColor redactedFontColor() {
        return this.redactedFontColor;
    }

    public void redactedFontColor_$eq(final PDColor x$1) {
        this.redactedFontColor = x$1;
    }

    public Point redactedTextPosition() {
        return this.redactedTextPosition;
    }

    public void redactedTextPosition_$eq(final Point x$1) {
        this.redactedTextPosition = x$1;
    }

    public RenderingMode redactedTextRenderingMode() {
        return this.redactedTextRenderingMode;
    }

    public void redactedTextRenderingMode_$eq(final RenderingMode x$1) {
        this.redactedTextRenderingMode = x$1;
    }

    private ArrayDeque<COSName> drawObjectNameStack() {
        return this.drawObjectNameStack;
    }

    private StringBuilder lastShownString() {
        return this.lastShownString;
    }

    private void lastShownString_$eq(final StringBuilder x$1) {
        this.lastShownString = x$1;
    }

    private float lastShownWidthOfSpace() {
        return this.lastShownWidthOfSpace;
    }

    private void lastShownWidthOfSpace_$eq(final float x$1) {
        this.lastShownWidthOfSpace = x$1;
    }

    private ArrayList<Object> lastShownStringNotMatchingRedactionFilter() {
        return this.lastShownStringNotMatchingRedactionFilter;
    }

    private void lastShownStringNotMatchingRedactionFilter_$eq(final ArrayList<Object> x$1) {
        this.lastShownStringNotMatchingRedactionFilter = x$1;
    }

    private StringBuilder lastShownStringBeforeNotMatchingRedactionFilter() {
        return this.lastShownStringBeforeNotMatchingRedactionFilter;
    }

    private void lastShownStringBeforeNotMatchingRedactionFilter_$eq(final StringBuilder x$1) {
        this.lastShownStringBeforeNotMatchingRedactionFilter = x$1;
    }

    private StringBuilder lastShownStringAfterNotMatchingRedactionFilter() {
        return this.lastShownStringAfterNotMatchingRedactionFilter;
    }

    private void lastShownStringAfterNotMatchingRedactionFilter_$eq(final StringBuilder x$1) {
        this.lastShownStringAfterNotMatchingRedactionFilter = x$1;
    }

    private float lastShownStringFirstX() {
        return this.lastShownStringFirstX;
    }

    private void lastShownStringFirstX_$eq(final float x$1) {
        this.lastShownStringFirstX = x$1;
    }

    private float lastShownStringLastX() {
        return this.lastShownStringLastX;
    }

    private void lastShownStringLastX_$eq(final float x$1) {
        this.lastShownStringLastX = x$1;
    }

    private boolean hasTextNotMatchingRedactionFilter() {
        return this.hasTextNotMatchingRedactionFilter;
    }

    private void hasTextNotMatchingRedactionFilter_$eq(final boolean x$1) {
        this.hasTextNotMatchingRedactionFilter = x$1;
    }

    private boolean matchesRedactionFilter() {
        return this.matchesRedactionFilter;
    }

    private void matchesRedactionFilter_$eq(final boolean x$1) {
        this.matchesRedactionFilter = x$1;
    }

    private boolean matchesRedactionFilterPartially() {
        return this.matchesRedactionFilterPartially;
    }

    private void matchesRedactionFilterPartially_$eq(final boolean x$1) {
        this.matchesRedactionFilterPartially = x$1;
    }

    private ArrayList<COSBase> filteredOperands() {
        return this.filteredOperands;
    }

    private void filteredOperands_$eq(final ArrayList<COSBase> x$1) {
        this.filteredOperands = x$1;
    }

    private RedactedStream filteredStream() {
        return this.filteredStream;
    }

    private void filteredStream_$eq(final RedactedStream x$1) {
        this.filteredStream = x$1;
    }

    private HashSet<IndirectCOSObjectIdentifier> redactedFormXObjectIds() {
        return this.redactedFormXObjectIds;
    }

    private ArrayList<OperatorAndOperands> redactedOperators() {
        return this.redactedOperators;
    }

    private ArrayList<OperatorAndOperands> partiallyRedactedOperators() {
        return this.partiallyRedactedOperators;
    }

    @Override // org.sejda.sambox.text.PDFTextStreamEngine, org.sejda.sambox.contentstream.PDFStreamEngine
    public void processPage(final PDPage page) throws IOException {
        super.processPage(page);
        filteredStream().close();
        COSArray array = new COSArray();
        array.add((COSObjectable) filteredStream().getStream());
        page.getCOSObject().setItem(COSName.CONTENTS, (COSBase) array);
    }

    @Override // org.sejda.sambox.contentstream.PDFStreamEngine
    public void showForm(final PDFormXObject form) throws IOException {
        redactForm(form);
    }

    @Override // org.sejda.sambox.contentstream.PDFStreamEngine
    public void showTransparencyGroup(final PDTransparencyGroup form) throws IOException {
        redactForm(form);
    }

    private void redactForm(final PDFormXObject form) throws IOException {
        boolean z;
        PDFormXObject pDFormXObject;
        RedactedStream tmpStream = filteredStream();
        String tmpRedactedString = redactedString().toString();
        try {
            filteredStream_$eq(new RedactedStream());
            PDResources resources = getResources();
            COSName formName = drawObjectNameStack().peek();
            if (!resources.isFormXObject(formName)) {
                resources = getCurrentPage().getResources();
            }
            if (!resources.isFormXObject(formName)) {
                logger().warn(new StringBuilder(35).append("Could not find form in resources: ").append(formName).append(" ").append(form).toString());
                throw new RuntimeException("Could not find form in page resources");
            }
            logger().trace(new StringBuilder(23).append("Processing FormXObject ").append(formName).toString());
            super.showForm(form);
            filteredStream().close();
            String string = redactedString().toString();
            if (tmpRedactedString == null) {
                z = string != null;
            } else if (!tmpRedactedString.equals(string)) {
            }
            boolean formWasRedacted = z;
            if (!formWasRedacted) {
                BoxedUnit boxedUnit = BoxedUnit.UNIT;
            } else {
                logger().trace(new StringBuilder(34).append("Redaction occurred in FormXObject ").append(formName).toString());
                if (form instanceof PDTransparencyGroup) {
                    PDTransparencyGroup newForm = new PDTransparencyGroup(filteredStream().getStream());
                    newForm.setGroup(((PDTransparencyGroup) form).getGroup());
                    pDFormXObject = newForm;
                } else {
                    pDFormXObject = new PDFormXObject(filteredStream().getStream().getCOSObject());
                }
                PDFormXObject newForm2 = pDFormXObject;
                newForm2.setMatrix(form.getMatrix().createAffineTransform());
                newForm2.setFormType(form.getFormType());
                newForm2.setBBox(form.getBBox());
                newForm2.setResources(form.getResources());
                newForm2.setStructParents(form.getStructParents());
                logger().trace(new StringBuilder(42).append("Updating FormXObject ").append(formName).append(" with redacted stream").toString());
                resources.put(formName, newForm2);
                BoxesRunTime.boxToBoolean(redactedFormXObjectIds().add(form.getCOSObject().getCOSObject().id()));
            }
            logger().trace(new StringBuilder(28).append("Done processing FormXObject ").append(formName).toString());
        } finally {
            filteredStream_$eq(tmpStream);
        }
    }

    @Override // org.sejda.sambox.contentstream.PDFStreamEngine
    public void showTextStrings(final COSArray array) throws IOException {
        PDTextState textState = getGraphicsState().getTextState();
        float fontSize = textState.getFontSize();
        float horizontalScaling = textState.getHorizontalScaling() / 100.0f;
        PDFont font = textState.getFont();
        boolean isVertical = BoxesRunTime.unboxToBoolean(Option$.MODULE$.apply(font).map(x$1 -> {
            return BoxesRunTime.boxToBoolean(x$1.isVertical());
        }).getOrElse(() -> {
            return false;
        }));
        ArrayList filteredParams = new ArrayList();
        matchesRedactionFilterPartially_$eq(false);
        ((IterableOnceOps) JavaConverters$.MODULE$.asScalaBufferConverter(array).asScala()).foreach(obj -> {
            float tx;
            float ty;
            if (obj instanceof COSNumber) {
                float tj = ((COSNumber) obj).floatValue();
                if (isVertical) {
                    tx = 0.0f;
                    ty = ((-tj) / 1000) * fontSize;
                } else {
                    tx = ((-tj) / 1000) * fontSize * horizontalScaling;
                    ty = 0.0f;
                }
                this.protected$applyTextAdjustment(this, tx, ty);
                return BoxesRunTime.boxToBoolean(filteredParams.add(obj));
            }
            if (obj instanceof COSString) {
                this.hasTextNotMatchingRedactionFilter_$eq(false);
                this.matchesRedactionFilter_$eq(false);
                this.lastShownString_$eq(new StringBuilder());
                this.lastShownWidthOfSpace_$eq(0.0f);
                this.lastShownStringNotMatchingRedactionFilter_$eq(new ArrayList<>());
                this.lastShownStringBeforeNotMatchingRedactionFilter_$eq(new StringBuilder());
                this.lastShownStringAfterNotMatchingRedactionFilter_$eq(new StringBuilder());
                byte[] string = ((COSString) obj).getBytes();
                this.protected$showText(this, string);
                if (this.matchesRedactionFilter()) {
                    this.matchesRedactionFilterPartially_$eq(true);
                    float redactedStringWidth = 0.0f;
                    if (font != null) {
                        String lastShownString = this.lastShownString().toString();
                        String lastShownStringNoSpaces = lastShownString.replaceAll(" ", "");
                        int spacesCount = lastShownString.length() - lastShownStringNoSpaces.length();
                        float spacesWidth = this.lastShownWidthOfSpace() * spacesCount;
                        try {
                            redactedStringWidth = font.getStringWidth(lastShownStringNoSpaces) + spacesWidth;
                        } catch (Throwable th) {
                            boolean z = (th instanceof IllegalArgumentException) || (th instanceof UnsupportedOperationException) || (th instanceof NullPointerException);
                            if (!z) {
                                throw th;
                            }
                            try {
                                redactedStringWidth = font.getStringWidthLeniently(lastShownStringNoSpaces) + spacesWidth;
                                BoxedUnit boxedUnit = BoxedUnit.UNIT;
                            } catch (Throwable th2) {
                                boolean z2 = (th2 instanceof IllegalArgumentException) || (th2 instanceof UnsupportedOperationException) || (th2 instanceof NullPointerException);
                                if (!z2) {
                                    throw th2;
                                }
                                BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
                                BoxedUnit boxedUnit3 = BoxedUnit.UNIT;
                            }
                        }
                    }
                    if (redactedStringWidth == 0) {
                        redactedStringWidth = this.lastShownStringLastX() - this.lastShownStringFirstX();
                    }
                    if (this.lastShownStringNotMatchingRedactionFilter().size() > 0 && filteredParams.size() == 0 && this.lastShownStringBeforeNotMatchingRedactionFilter().nonEmpty() && this.lastShownStringAfterNotMatchingRedactionFilter().toString().trim().isBlank()) {
                        redactedStringWidth = 0.0f;
                    }
                    float filteredTj = -(redactedStringWidth / horizontalScaling);
                    filteredParams.add(new COSFloat(filteredTj));
                    if (!this.hasTextNotMatchingRedactionFilter()) {
                        return BoxedUnit.UNIT;
                    }
                    if (this.lastShownStringNotMatchingRedactionFilter().size() <= 0) {
                        return BoxedUnit.UNIT;
                    }
                    byte[] bytes = (byte[]) ArrayOps$.MODULE$.map$extension(Predef$.MODULE$.refArrayOps(this.lastShownStringNotMatchingRedactionFilter().toArray()), i -> {
                        return BoxesRunTime.boxToByte($anonfun$showTextStrings$4(i));
                    }, ClassTag$.MODULE$.Byte());
                    return BoxesRunTime.boxToBoolean(filteredParams.add(new COSString(bytes)));
                }
                return BoxesRunTime.boxToBoolean(filteredParams.add(obj));
            }
            throw new IOException(new StringBuilder(39).append("Unknown type in array for TJ operation:").append(obj).toString());
        });
        if (matchesRedactionFilterPartially()) {
            matchesRedactionFilter_$eq(true);
            filteredOperands_$eq(new ArrayList<>());
            COSArray cosArray = new COSArray();
            cosArray.addAll(filteredParams);
            filteredOperands().add(cosArray);
        }
    }

    public /* synthetic */ void protected$applyTextAdjustment(final PdfTextRedactingStreamEngine x$1, final float x$12, final float x$2) {
        x$1.applyTextAdjustment(x$12, x$2);
    }

    public /* synthetic */ void protected$showText(final PdfTextRedactingStreamEngine x$1, final byte[] x$12) {
        x$1.showText(x$12);
    }

    public static final /* synthetic */ byte $anonfun$showTextStrings$4(final Object i) {
        return (byte) BoxesRunTime.unboxToInt(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String toDebugStr(final Object obj) {
        while (true) {
            Object obj2 = obj;
            if (obj2 instanceof List) {
                return ((IterableOnceOps) ((IterableOps) JavaConverters$.MODULE$.asScalaBufferConverter((List) obj2).asScala()).map(x$2 -> {
                    return this.toDebugStr(x$2);
                })).mkString(", ");
            }
            if (!(obj2 instanceof COSArray)) {
                if (!(obj2 instanceof COSString)) {
                    return obj2.toString();
                }
                return new StringBuilder(12).append("COSString{").append(Predef$.MODULE$.wrapRefArray((Object[]) ArrayOps$.MODULE$.map$extension(Predef$.MODULE$.byteArrayOps(((COSString) obj2).getBytes()), b -> {
                    return $anonfun$toDebugStr$2(BoxesRunTime.unboxToByte(b));
                }, ClassTag$.MODULE$.apply(String.class))).mkString()).append(" }").toString();
            }
            obj = (COSArray) obj2;
        }
    }

    public static final /* synthetic */ String $anonfun$toDebugStr$2(final byte b) {
        return new StringBuilder(1).append("\\").append(Integer.toOctalString(b & 255)).toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00ea  */
    @Override // org.sejda.sambox.contentstream.PDFStreamEngine
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void processOperator(final org.sejda.sambox.contentstream.operator.Operator r6, final java.util.List<org.sejda.sambox.cos.COSBase> r7) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 467
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: code.sejda.tasks.common.text.PdfTextRedactingStreamEngine.processOperator(org.sejda.sambox.contentstream.operator.Operator, java.util.List):void");
    }

    @Override // org.sejda.sambox.text.PDFTextStreamEngine
    public void processTextPosition(final TextPosition text) {
        float x = text.getX();
        float y = text.getY();
        boolean inBoundingBox = box().containsPoint(x, y) && box().containsPoint(x, y - text.getHeight()) && box().containsPoint(x + text.getWidth(), y);
        if (logger().isTraceEnabled()) {
            logger().trace(new StringBuilder(49).append("Text: '").append(text.getUnicode()).append("' pos:").append(x).append(",").append(y).append(" hPos:").append(x).append(",").append(y - text.getHeight()).append(" wPos:").append(x + text.getWidth()).append(",").append(y).append(", bBox: ").append(inBoundingBox).append(" (").append(box().containsPoint(x, y)).append(", ").append(box().containsPoint(x, y - text.getHeight())).append(", ").append(box().containsPoint(x + text.getWidth(), y)).append(") box: ").append(box()).toString());
        }
        if (inBoundingBox) {
            matchesRedactionFilter_$eq(true);
            redactedString().append(text.getUnicode());
            redactedFont_$eq(text.getFont());
            if (lastShownString().isEmpty()) {
                lastShownStringFirstX_$eq(x);
                lastShownWidthOfSpace_$eq(text.getWidthOfSpace());
            }
            lastShownString().append(text.getUnicode());
            lastShownStringLastX_$eq(x + text.getWidthDirAdj());
            redactedFontSize_$eq(text.getYScale());
            redactedFontColor_$eq(getGraphicsState().getNonStrokingColor());
            if (redactedTextPosition() == null) {
                logger().trace(new StringBuilder(25).append("Redacted text position: ").append(text.getX()).append(",").append(text.getY()).toString());
                redactedTextPosition_$eq(new Point((int) x, (int) y));
                redactedTextRenderingMode_$eq(getGraphicsState().getTextState().getRenderingMode());
            } else if (redactedTextPosition().getX() > x) {
                logger().trace(new StringBuilder(34).append("Found more to the left position: ").append(text.getX()).append(",").append(text.getY()).toString());
                redactedTextPosition_$eq(new Point((int) x, (int) y));
                redactedTextRenderingMode_$eq(getGraphicsState().getTextState().getRenderingMode());
            }
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
        } else {
            hasTextNotMatchingRedactionFilter_$eq(true);
            String textUnicode = text.getUnicode();
            ArrayOps$.MODULE$.foreach$extension(Predef$.MODULE$.intArrayOps(text.getCharacterCodes()), code2 -> {
                return this.lastShownStringNotMatchingRedactionFilter().add(BoxesRunTime.boxToInteger(code2));
            });
            if (redactedTextPosition() == null) {
                lastShownStringBeforeNotMatchingRedactionFilter().append(textUnicode);
            } else {
                lastShownStringAfterNotMatchingRedactionFilter().append(textUnicode);
            }
        }
        if (matchesRedactionFilter() && hasTextNotMatchingRedactionFilter()) {
            matchesRedactionFilterPartially_$eq(true);
        }
    }

    public Set<IndirectCOSObjectIdentifier> getRedactedFormXObjectIds() {
        return redactedFormXObjectIds();
    }

    public List<OperatorAndOperands> getRedactedOperators() {
        return redactedOperators();
    }

    public List<OperatorAndOperands> getPartiallyRedactedOperators() {
        return partiallyRedactedOperators();
    }
}
