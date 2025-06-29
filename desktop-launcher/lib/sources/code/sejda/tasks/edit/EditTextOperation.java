package code.sejda.tasks.edit;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.Serializable;
import org.sejda.model.TopLeftRectangularBox;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple12;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: EditParameters.scala */
@ScalaSignature(bytes = "\u0006\u0005\tUf\u0001B%K\u0001NC\u0001\"\u001b\u0001\u0003\u0016\u0004%\tA\u001b\u0005\tg\u0002\u0011\t\u0012)A\u0005W\"AA\u000f\u0001BK\u0002\u0013\u0005Q\u000f\u0003\u0005z\u0001\tE\t\u0015!\u0003w\u0011!Q\bA!f\u0001\n\u0003Y\b\"CA\u0006\u0001\tE\t\u0015!\u0003}\u0011)\ti\u0001\u0001BK\u0002\u0013\u0005\u0011q\u0002\u0005\u000b\u0003/\u0001!\u0011#Q\u0001\n\u0005E\u0001BCA\r\u0001\tU\r\u0011\"\u0001\u0002\u001c!Q\u00111\u0005\u0001\u0003\u0012\u0003\u0006I!!\b\t\u0015\u0005\u0015\u0002A!f\u0001\n\u0003\tY\u0002\u0003\u0006\u0002(\u0001\u0011\t\u0012)A\u0005\u0003;A!\"!\u000b\u0001\u0005+\u0007I\u0011AA\u0016\u0011)\t)\u0004\u0001B\tB\u0003%\u0011Q\u0006\u0005\u000b\u0003o\u0001!Q3A\u0005\u0002\u0005e\u0002BCA)\u0001\tE\t\u0015!\u0003\u0002<!I\u00111\u000b\u0001\u0003\u0016\u0004%\tA\u001b\u0005\n\u0003+\u0002!\u0011#Q\u0001\n-D!\"a\u0016\u0001\u0005+\u0007I\u0011AA-\u0011)\t)\u0007\u0001B\tB\u0003%\u00111\f\u0005\u000b\u0003O\u0002!Q3A\u0005\u0002\u0005-\u0002BCA5\u0001\tE\t\u0015!\u0003\u0002.!I\u00111\u000e\u0001\u0003\u0016\u0004%\t!\u001e\u0005\n\u0003[\u0002!\u0011#Q\u0001\nYDq!a\u001c\u0001\t\u0003\t\t\bC\u0005\u0002\u0010\u0002\t\t\u0011\"\u0001\u0002\u0012\"I\u00111\u0016\u0001\u0012\u0002\u0013\u0005\u0011Q\u0016\u0005\n\u0003\u0007\u0004\u0011\u0013!C\u0001\u0003\u000bD\u0011\"!3\u0001#\u0003%\t!a3\t\u0013\u0005=\u0007!%A\u0005\u0002\u0005E\u0007\"CAk\u0001E\u0005I\u0011AAl\u0011%\tY\u000eAI\u0001\n\u0003\t9\u000eC\u0005\u0002^\u0002\t\n\u0011\"\u0001\u0002`\"I\u00111\u001d\u0001\u0012\u0002\u0013\u0005\u0011Q\u001d\u0005\n\u0003S\u0004\u0011\u0013!C\u0001\u0003[C\u0011\"a;\u0001#\u0003%\t!!<\t\u0013\u0005E\b!%A\u0005\u0002\u0005}\u0007\"CAz\u0001E\u0005I\u0011AAc\u0011%\t)\u0010AA\u0001\n\u0003\n9\u0010C\u0005\u0003\u0004\u0001\t\t\u0011\"\u0001\u0002\u0010!I!Q\u0001\u0001\u0002\u0002\u0013\u0005!q\u0001\u0005\n\u0005'\u0001\u0011\u0011!C!\u0005+A\u0011Ba\t\u0001\u0003\u0003%\tA!\n\t\u0013\t%\u0002!!A\u0005B\t-\u0002\"\u0003B\u0018\u0001\u0005\u0005I\u0011\tB\u0019\u0011%\u0011\u0019\u0004AA\u0001\n\u0003\u0012)\u0004C\u0005\u00038\u0001\t\t\u0011\"\u0011\u0003:\u001dI!Q\b&\u0002\u0002#\u0005!q\b\u0004\t\u0013*\u000b\t\u0011#\u0001\u0003B!9\u0011qN\u0019\u0005\u0002\te\u0003\"\u0003B\u001ac\u0005\u0005IQ\tB\u001b\u0011%\u0011Y&MA\u0001\n\u0003\u0013i\u0006C\u0005\u0003xE\n\n\u0011\"\u0001\u0002F\"I!\u0011P\u0019\u0012\u0002\u0013\u0005\u0011q\u001b\u0005\n\u0005w\n\u0014\u0013!C\u0001\u0003/D\u0011B! 2#\u0003%\t!a8\t\u0013\t}\u0014'%A\u0005\u0002\u0005\u0015\b\"\u0003BAcE\u0005I\u0011AAW\u0011%\u0011\u0019)MI\u0001\n\u0003\ti\u000fC\u0005\u0003\u0006F\n\n\u0011\"\u0001\u0002`\"I!qQ\u0019\u0012\u0002\u0013\u0005\u0011Q\u0019\u0005\n\u0005\u0013\u000b\u0014\u0011!CA\u0005\u0017C\u0011B!'2#\u0003%\t!!2\t\u0013\tm\u0015'%A\u0005\u0002\u0005]\u0007\"\u0003BOcE\u0005I\u0011AAl\u0011%\u0011y*MI\u0001\n\u0003\ty\u000eC\u0005\u0003\"F\n\n\u0011\"\u0001\u0002f\"I!1U\u0019\u0012\u0002\u0013\u0005\u0011Q\u0016\u0005\n\u0005K\u000b\u0014\u0013!C\u0001\u0003[D\u0011Ba*2#\u0003%\t!a8\t\u0013\t%\u0016'%A\u0005\u0002\u0005\u0015\u0007\"\u0003BVc\u0005\u0005I\u0011\u0002BW\u0005E)E-\u001b;UKb$x\n]3sCRLwN\u001c\u0006\u0003\u00172\u000bA!\u001a3ji*\u0011QJT\u0001\u0006i\u0006\u001c8n\u001d\u0006\u0003\u001fB\u000bQa]3kI\u0006T\u0011!U\u0001\u0005G>$Wm\u0001\u0001\u0014\t\u0001!&,\u0018\t\u0003+bk\u0011A\u0016\u0006\u0002/\u0006)1oY1mC&\u0011\u0011L\u0016\u0002\u0007\u0003:L(+\u001a4\u0011\u0005U[\u0016B\u0001/W\u0005\u001d\u0001&o\u001c3vGR\u0004\"A\u00184\u000f\u0005}#gB\u00011d\u001b\u0005\t'B\u00012S\u0003\u0019a$o\\8u}%\tq+\u0003\u0002f-\u00069\u0001/Y2lC\u001e,\u0017BA4i\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\t)g+\u0001\u0003uKb$X#A6\u0011\u00051\u0004hBA7o!\t\u0001g+\u0003\u0002p-\u00061\u0001K]3eK\u001aL!!\u001d:\u0003\rM#(/\u001b8h\u0015\tyg+A\u0003uKb$\b%A\u0004g_:$x\n\u001d;\u0016\u0003Y\u00042!V<l\u0013\tAhK\u0001\u0004PaRLwN\\\u0001\tM>tGo\u00149uA\u0005Y!m\\;oI&twMQ8y+\u0005a\bcA?\u0002\b5\taPC\u0002��\u0003\u0003\tQ!\\8eK2T1aTA\u0002\u0015\t\t)!A\u0002pe\u001eL1!!\u0003\u007f\u0005U!v\u000e\u001d'fMR\u0014Vm\u0019;b]\u001e,H.\u0019:C_b\fABY8v]\u0012Lgn\u001a\"pq\u0002\n!\u0002]1hK:+XNY3s+\t\t\t\u0002E\u0002V\u0003'I1!!\u0006W\u0005\rIe\u000e^\u0001\fa\u0006<WMT;nE\u0016\u0014\b%\u0001\u0003c_2$WCAA\u000f!\r)\u0016qD\u0005\u0004\u0003C1&a\u0002\"p_2,\u0017M\\\u0001\u0006E>dG\rI\u0001\u0007SR\fG.[2\u0002\u000f%$\u0018\r\\5dA\u0005Aam\u001c8u'&TX-\u0006\u0002\u0002.A!Qk^A\u0018!\r)\u0016\u0011G\u0005\u0004\u0003g1&A\u0002#pk\ndW-A\u0005g_:$8+\u001b>fA\u0005A\u0001o\\:ji&|g.\u0006\u0002\u0002<A!Qk^A\u001f!\u0011\ty$!\u0014\u000e\u0005\u0005\u0005#\u0002BA\"\u0003\u000b\nAaZ3p[*!\u0011qIA%\u0003\r\tw\u000f\u001e\u0006\u0003\u0003\u0017\nAA[1wC&!\u0011qJA!\u0005\u001d\u0001v.\u001b8ue\u0011\u000b\u0011\u0002]8tSRLwN\u001c\u0011\u0002\u0019=\u0014\u0018nZ5oC2$V\r\u001f;\u0002\u001b=\u0014\u0018nZ5oC2$V\r\u001f;!\u0003\u0015\u0019w\u000e\\8s+\t\tY\u0006\u0005\u0003Vo\u0006u\u0003\u0003BA0\u0003Cj!!!\u0012\n\t\u0005\r\u0014Q\t\u0002\u0006\u0007>dwN]\u0001\u0007G>dwN\u001d\u0011\u0002\u00151Lg.\u001a%fS\u001eDG/A\u0006mS:,\u0007*Z5hQR\u0004\u0013A\u00024p]RLE-A\u0004g_:$\u0018\n\u001a\u0011\u0002\rqJg.\u001b;?)i\t\u0019(a\u001e\u0002z\u0005m\u0014QPA@\u0003\u0003\u000b\u0019)!\"\u0002\b\u0006%\u00151RAG!\r\t)\bA\u0007\u0002\u0015\")\u0011.\u0007a\u0001W\"9A/\u0007I\u0001\u0002\u00041\b\"\u0002>\u001a\u0001\u0004a\bbBA\u00073\u0001\u0007\u0011\u0011\u0003\u0005\n\u00033I\u0002\u0013!a\u0001\u0003;A\u0011\"!\n\u001a!\u0003\u0005\r!!\b\t\u0013\u0005%\u0012\u0004%AA\u0002\u00055\u0002\"CA\u001c3A\u0005\t\u0019AA\u001e\u0011!\t\u0019&\u0007I\u0001\u0002\u0004Y\u0007\"CA,3A\u0005\t\u0019AA.\u0011%\t9'\u0007I\u0001\u0002\u0004\ti\u0003\u0003\u0005\u0002le\u0001\n\u00111\u0001w\u0003\u0011\u0019w\u000e]=\u00155\u0005M\u00141SAK\u0003/\u000bI*a'\u0002\u001e\u0006}\u0015\u0011UAR\u0003K\u000b9+!+\t\u000f%T\u0002\u0013!a\u0001W\"9AO\u0007I\u0001\u0002\u00041\bb\u0002>\u001b!\u0003\u0005\r\u0001 \u0005\n\u0003\u001bQ\u0002\u0013!a\u0001\u0003#A\u0011\"!\u0007\u001b!\u0003\u0005\r!!\b\t\u0013\u0005\u0015\"\u0004%AA\u0002\u0005u\u0001\"CA\u00155A\u0005\t\u0019AA\u0017\u0011%\t9D\u0007I\u0001\u0002\u0004\tY\u0004\u0003\u0005\u0002Ti\u0001\n\u00111\u0001l\u0011%\t9F\u0007I\u0001\u0002\u0004\tY\u0006C\u0005\u0002hi\u0001\n\u00111\u0001\u0002.!A\u00111\u000e\u000e\u0011\u0002\u0003\u0007a/\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0005\u0005=&fA6\u00022.\u0012\u00111\u0017\t\u0005\u0003k\u000by,\u0004\u0002\u00028*!\u0011\u0011XA^\u0003%)hn\u00195fG.,GMC\u0002\u0002>Z\u000b!\"\u00198o_R\fG/[8o\u0013\u0011\t\t-a.\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0016\u0005\u0005\u001d'f\u0001<\u00022\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u001aTCAAgU\ra\u0018\u0011W\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00135+\t\t\u0019N\u000b\u0003\u0002\u0012\u0005E\u0016AD2paf$C-\u001a4bk2$H%N\u000b\u0003\u00033TC!!\b\u00022\u0006q1m\u001c9zI\u0011,g-Y;mi\u00122\u0014AD2paf$C-\u001a4bk2$HeN\u000b\u0003\u0003CTC!!\f\u00022\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012BTCAAtU\u0011\tY$!-\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%s\u0005y1m\u001c9zI\u0011,g-Y;mi\u0012\n\u0004'\u0006\u0002\u0002p*\"\u00111LAY\u0003=\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE\n\u0014aD2paf$C-\u001a4bk2$H%\r\u001a\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\tI\u0010\u0005\u0003\u0002|\n\u0005QBAA\u007f\u0015\u0011\ty0!\u0013\u0002\t1\fgnZ\u0005\u0004c\u0006u\u0018\u0001\u00049s_\u0012,8\r^!sSRL\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0005\u0005\u0013\u0011y\u0001E\u0002V\u0005\u0017I1A!\u0004W\u0005\r\te.\u001f\u0005\n\u0005#I\u0013\u0011!a\u0001\u0003#\t1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XC\u0001B\f!\u0019\u0011IBa\b\u0003\n5\u0011!1\u0004\u0006\u0004\u0005;1\u0016AC2pY2,7\r^5p]&!!\u0011\u0005B\u000e\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u0005u!q\u0005\u0005\n\u0005#Y\u0013\u0011!a\u0001\u0005\u0013\t!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!\u0011\u0011 B\u0017\u0011%\u0011\t\u0002LA\u0001\u0002\u0004\t\t\"\u0001\u0005iCND7i\u001c3f)\t\t\t\"\u0001\u0005u_N#(/\u001b8h)\t\tI0\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0003;\u0011Y\u0004C\u0005\u0003\u0012=\n\t\u00111\u0001\u0003\n\u0005\tR\tZ5u)\u0016DHo\u00149fe\u0006$\u0018n\u001c8\u0011\u0007\u0005U\u0014gE\u00032\u0005\u0007\u0012y\u0005E\r\u0003F\t-3N\u001e?\u0002\u0012\u0005u\u0011QDA\u0017\u0003wY\u00171LA\u0017m\u0006MTB\u0001B$\u0015\r\u0011IEV\u0001\beVtG/[7f\u0013\u0011\u0011iEa\u0012\u0003%\u0005\u00137\u000f\u001e:bGR4UO\\2uS>t\u0017G\r\t\u0005\u0005#\u00129&\u0004\u0002\u0003T)!!QKA%\u0003\tIw.C\u0002h\u0005'\"\"Aa\u0010\u0002\u000b\u0005\u0004\b\u000f\\=\u00155\u0005M$q\fB1\u0005G\u0012)Ga\u001a\u0003j\t-$Q\u000eB8\u0005c\u0012\u0019H!\u001e\t\u000b%$\u0004\u0019A6\t\u000fQ$\u0004\u0013!a\u0001m\")!\u0010\u000ea\u0001y\"9\u0011Q\u0002\u001bA\u0002\u0005E\u0001\"CA\riA\u0005\t\u0019AA\u000f\u0011%\t)\u0003\u000eI\u0001\u0002\u0004\ti\u0002C\u0005\u0002*Q\u0002\n\u00111\u0001\u0002.!I\u0011q\u0007\u001b\u0011\u0002\u0003\u0007\u00111\b\u0005\t\u0003'\"\u0004\u0013!a\u0001W\"I\u0011q\u000b\u001b\u0011\u0002\u0003\u0007\u00111\f\u0005\n\u0003O\"\u0004\u0013!a\u0001\u0003[A\u0001\"a\u001b5!\u0003\u0005\rA^\u0001\u0010CB\u0004H.\u001f\u0013eK\u001a\fW\u000f\u001c;%e\u0005y\u0011\r\u001d9ms\u0012\"WMZ1vYR$S'A\bbaBd\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00137\u0003=\t\u0007\u000f\u001d7zI\u0011,g-Y;mi\u0012:\u0014aD1qa2LH\u0005Z3gCVdG\u000f\n\u001d\u0002\u001f\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uIe\n\u0001#\u00199qYf$C-\u001a4bk2$H%\r\u0019\u0002!\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uIE\n\u0014\u0001E1qa2LH\u0005Z3gCVdG\u000fJ\u00193\u0003\u001d)h.\u00199qYf$BA!$\u0003\u0016B!Qk\u001eBH!Y)&\u0011S6wy\u0006E\u0011QDA\u000f\u0003[\tYd[A.\u0003[1\u0018b\u0001BJ-\n9A+\u001e9mKF\u0012\u0004\"\u0003BL}\u0005\u0005\t\u0019AA:\u0003\rAH\u0005M\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000f\n\u001a\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00136\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%m\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uI]\n1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012B\u0014a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$\u0013(\u0001\u000f%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$H%\r\u0019\u00029\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00132c\u0005aB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIE\u0012\u0014\u0001D<sSR,'+\u001a9mC\u000e,GC\u0001BX!\u0011\tYP!-\n\t\tM\u0016Q \u0002\u0007\u001f\nTWm\u0019;")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/EditTextOperation.class */
public class EditTextOperation implements Product, Serializable {
    private final String text;
    private final Option<String> fontOpt;
    private final TopLeftRectangularBox boundingBox;
    private final int pageNumber;
    private final boolean bold;
    private final boolean italic;
    private final Option<Object> fontSize;
    private final Option<Point2D> position;
    private final String originalText;
    private final Option<Color> color;
    private final Option<Object> lineHeight;
    private final Option<String> fontId;

    public static Option<Tuple12<String, Option<String>, TopLeftRectangularBox, Object, Object, Object, Option<Object>, Option<Point2D>, String, Option<Color>, Option<Object>, Option<String>>> unapply(final EditTextOperation x$0) {
        return EditTextOperation$.MODULE$.unapply(x$0);
    }

    public static EditTextOperation apply(final String text, final Option<String> fontOpt, final TopLeftRectangularBox boundingBox, final int pageNumber, final boolean bold, final boolean italic, final Option<Object> fontSize, final Option<Point2D> position, final String originalText, final Option<Color> color, final Option<Object> lineHeight, final Option<String> fontId) {
        return EditTextOperation$.MODULE$.apply(text, fontOpt, boundingBox, pageNumber, bold, italic, fontSize, position, originalText, color, lineHeight, fontId);
    }

    public static Function1<Tuple12<String, Option<String>, TopLeftRectangularBox, Object, Object, Object, Option<Object>, Option<Point2D>, String, Option<Color>, Option<Object>, Option<String>>, EditTextOperation> tupled() {
        return EditTextOperation$.MODULE$.tupled();
    }

    public static Function1<String, Function1<Option<String>, Function1<TopLeftRectangularBox, Function1<Object, Function1<Object, Function1<Object, Function1<Option<Object>, Function1<Option<Point2D>, Function1<String, Function1<Option<Color>, Function1<Option<Object>, Function1<Option<String>, EditTextOperation>>>>>>>>>>>> curried() {
        return EditTextOperation$.MODULE$.curried();
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String text() {
        return this.text;
    }

    public Option<String> fontOpt() {
        return this.fontOpt;
    }

    public EditTextOperation copy(final String text, final Option<String> fontOpt, final TopLeftRectangularBox boundingBox, final int pageNumber, final boolean bold, final boolean italic, final Option<Object> fontSize, final Option<Point2D> position, final String originalText, final Option<Color> color, final Option<Object> lineHeight, final Option<String> fontId) {
        return new EditTextOperation(text, fontOpt, boundingBox, pageNumber, bold, italic, fontSize, position, originalText, color, lineHeight, fontId);
    }

    public String copy$default$1() {
        return text();
    }

    public Option<String> copy$default$2() {
        return fontOpt();
    }

    public String productPrefix() {
        return "EditTextOperation";
    }

    public int productArity() {
        return 12;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return text();
            case 1:
                return fontOpt();
            case 2:
                return boundingBox();
            case 3:
                return BoxesRunTime.boxToInteger(pageNumber());
            case 4:
                return BoxesRunTime.boxToBoolean(bold());
            case 5:
                return BoxesRunTime.boxToBoolean(italic());
            case 6:
                return fontSize();
            case 7:
                return position();
            case 8:
                return originalText();
            case 9:
                return color();
            case 10:
                return lineHeight();
            case 11:
                return fontId();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof EditTextOperation;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "text";
            case 1:
                return "fontOpt";
            case 2:
                return "boundingBox";
            case 3:
                return "pageNumber";
            case 4:
                return "bold";
            case 5:
                return "italic";
            case 6:
                return "fontSize";
            case 7:
                return "position";
            case 8:
                return "originalText";
            case 9:
                return "color";
            case 10:
                return "lineHeight";
            case 11:
                return "fontId";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return Statics.finalizeHash(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(-889275714, productPrefix().hashCode()), Statics.anyHash(text())), Statics.anyHash(fontOpt())), Statics.anyHash(boundingBox())), pageNumber()), bold() ? 1231 : 1237), italic() ? 1231 : 1237), Statics.anyHash(fontSize())), Statics.anyHash(position())), Statics.anyHash(originalText())), Statics.anyHash(color())), Statics.anyHash(lineHeight())), Statics.anyHash(fontId())), 12);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof EditTextOperation) {
                EditTextOperation editTextOperation = (EditTextOperation) x$1;
                if (pageNumber() == editTextOperation.pageNumber() && bold() == editTextOperation.bold() && italic() == editTextOperation.italic()) {
                    String strText = text();
                    String strText2 = editTextOperation.text();
                    if (strText != null ? strText.equals(strText2) : strText2 == null) {
                        Option<String> optionFontOpt = fontOpt();
                        Option<String> optionFontOpt2 = editTextOperation.fontOpt();
                        if (optionFontOpt != null ? optionFontOpt.equals(optionFontOpt2) : optionFontOpt2 == null) {
                            TopLeftRectangularBox topLeftRectangularBoxBoundingBox = boundingBox();
                            TopLeftRectangularBox topLeftRectangularBoxBoundingBox2 = editTextOperation.boundingBox();
                            if (topLeftRectangularBoxBoundingBox != null ? topLeftRectangularBoxBoundingBox.equals(topLeftRectangularBoxBoundingBox2) : topLeftRectangularBoxBoundingBox2 == null) {
                                Option<Object> optionFontSize = fontSize();
                                Option<Object> optionFontSize2 = editTextOperation.fontSize();
                                if (optionFontSize != null ? optionFontSize.equals(optionFontSize2) : optionFontSize2 == null) {
                                    Option<Point2D> optionPosition = position();
                                    Option<Point2D> optionPosition2 = editTextOperation.position();
                                    if (optionPosition != null ? optionPosition.equals(optionPosition2) : optionPosition2 == null) {
                                        String strOriginalText = originalText();
                                        String strOriginalText2 = editTextOperation.originalText();
                                        if (strOriginalText != null ? strOriginalText.equals(strOriginalText2) : strOriginalText2 == null) {
                                            Option<Color> optionColor = color();
                                            Option<Color> optionColor2 = editTextOperation.color();
                                            if (optionColor != null ? optionColor.equals(optionColor2) : optionColor2 == null) {
                                                Option<Object> optionLineHeight = lineHeight();
                                                Option<Object> optionLineHeight2 = editTextOperation.lineHeight();
                                                if (optionLineHeight != null ? optionLineHeight.equals(optionLineHeight2) : optionLineHeight2 == null) {
                                                    Option<String> optionFontId = fontId();
                                                    Option<String> optionFontId2 = editTextOperation.fontId();
                                                    if (optionFontId != null ? optionFontId.equals(optionFontId2) : optionFontId2 == null) {
                                                        if (editTextOperation.canEqual(this)) {
                                                        }
                                                    }
                                                }
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

    public EditTextOperation(final String text, final Option<String> fontOpt, final TopLeftRectangularBox boundingBox, final int pageNumber, final boolean bold, final boolean italic, final Option<Object> fontSize, final Option<Point2D> position, final String originalText, final Option<Color> color, final Option<Object> lineHeight, final Option<String> fontId) {
        this.text = text;
        this.fontOpt = fontOpt;
        this.boundingBox = boundingBox;
        this.pageNumber = pageNumber;
        this.bold = bold;
        this.italic = italic;
        this.fontSize = fontSize;
        this.position = position;
        this.originalText = originalText;
        this.color = color;
        this.lineHeight = lineHeight;
        this.fontId = fontId;
        Product.$init$(this);
    }

    public TopLeftRectangularBox boundingBox() {
        return this.boundingBox;
    }

    public int pageNumber() {
        return this.pageNumber;
    }

    public TopLeftRectangularBox copy$default$3() {
        return boundingBox();
    }

    public int copy$default$4() {
        return pageNumber();
    }

    public boolean bold() {
        return this.bold;
    }

    public boolean italic() {
        return this.italic;
    }

    public boolean copy$default$5() {
        return bold();
    }

    public boolean copy$default$6() {
        return italic();
    }

    public Option<Object> fontSize() {
        return this.fontSize;
    }

    public Option<Point2D> position() {
        return this.position;
    }

    public String originalText() {
        return this.originalText;
    }

    public Option<Object> copy$default$7() {
        return fontSize();
    }

    public Option<Point2D> copy$default$8() {
        return position();
    }

    public String copy$default$9() {
        return originalText();
    }

    public Option<Color> color() {
        return this.color;
    }

    public Option<Object> lineHeight() {
        return this.lineHeight;
    }

    public Option<String> fontId() {
        return this.fontId;
    }

    public Option<Color> copy$default$10() {
        return color();
    }

    public Option<Object> copy$default$11() {
        return lineHeight();
    }

    public Option<String> copy$default$12() {
        return fontId();
    }
}
