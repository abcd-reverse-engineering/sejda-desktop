package code.model;

import code.limits.QuotaUpdate;
import code.limits.Upgrade;
import java.io.File;
import java.io.Serializable;
import net.liftweb.json.JsonAST;
import net.liftweb.json.JsonDSL$;
import scala.Enumeration;
import scala.Option;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Product;
import scala.Tuple12;
import scala.collection.Iterator;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: Task.scala */
@ScalaSignature(bytes = "\u0006\u0005\r%e\u0001B/_\u0001\u000eD\u0001\"\u001f\u0001\u0003\u0016\u0004%\tA\u001f\u0005\n\u0003\u001b\u0001!\u0011#Q\u0001\nmD!\"a\u0004\u0001\u0005+\u0007I\u0011AA\t\u0011)\tI\u0003\u0001B\tB\u0003%\u00111\u0003\u0005\u000b\u0003W\u0001!Q3A\u0005\u0002\u00055\u0002BCA\u001b\u0001\tE\t\u0015!\u0003\u00020!Q\u0011q\u0007\u0001\u0003\u0016\u0004%\t!!\u000f\t\u0015\u00055\u0003A!E!\u0002\u0013\tY\u0004\u0003\u0006\u0002P\u0001\u0011)\u001a!C\u0001\u0003sA!\"!\u0015\u0001\u0005#\u0005\u000b\u0011BA\u001e\u0011)\t\u0019\u0006\u0001BK\u0002\u0013\u0005\u0011\u0011\b\u0005\u000b\u0003+\u0002!\u0011#Q\u0001\n\u0005m\u0002BCA,\u0001\tU\r\u0011\"\u0001\u0002Z!Q\u0011\u0011\r\u0001\u0003\u0012\u0003\u0006I!a\u0017\t\u0015\u0005\r\u0004A!f\u0001\n\u0003\t)\u0007\u0003\u0006\u0002j\u0001\u0011\t\u0012)A\u0005\u0003OB!\"a\u001b\u0001\u0005+\u0007I\u0011AA\u001d\u0011)\ti\u0007\u0001B\tB\u0003%\u00111\b\u0005\u000b\u0003_\u0002!Q3A\u0005\u0002\u0005E\u0004BCAA\u0001\tE\t\u0015!\u0003\u0002t!Q\u00111\u0011\u0001\u0003\u0016\u0004%\t!!\u000f\t\u0015\u0005\u0015\u0005A!E!\u0002\u0013\tY\u0004\u0003\u0006\u0002\b\u0002\u0011\t\u001a!C\u0001\u0003\u0013C!\"a%\u0001\u0005\u0003\u0007I\u0011AAK\u0011)\t\t\u000b\u0001B\tB\u0003&\u00111\u0012\u0005\b\u0003G\u0003A\u0011AAS\u0011\u001d\t\u0019\r\u0001C\u0001\u0003\u000bDq!!<\u0001\t\u0003\t)\rC\u0005\u0002p\u0002\t\t\u0011\"\u0001\u0002r\"I!1\u0002\u0001\u0012\u0002\u0013\u0005!Q\u0002\u0005\n\u0005G\u0001\u0011\u0013!C\u0001\u0005KA\u0011B!\u000b\u0001#\u0003%\tAa\u000b\t\u0013\t=\u0002!%A\u0005\u0002\tE\u0002\"\u0003B\u001b\u0001E\u0005I\u0011\u0001B\u0019\u0011%\u00119\u0004AI\u0001\n\u0003\u0011\t\u0004C\u0005\u0003:\u0001\t\n\u0011\"\u0001\u0003<!I!q\b\u0001\u0012\u0002\u0013\u0005!\u0011\t\u0005\n\u0005\u000b\u0002\u0011\u0013!C\u0001\u0005cA\u0011Ba\u0012\u0001#\u0003%\tA!\u0013\t\u0013\t5\u0003!%A\u0005\u0002\tE\u0002\"\u0003B(\u0001E\u0005I\u0011\u0001B)\u0011%\u0011)\u0006AA\u0001\n\u0003\u00129\u0006C\u0005\u0003d\u0001\t\t\u0011\"\u0001\u0003f!I!Q\u000e\u0001\u0002\u0002\u0013\u0005!q\u000e\u0005\n\u0005s\u0002\u0011\u0011!C!\u0005wB\u0011B!#\u0001\u0003\u0003%\tAa#\t\u0013\t=\u0005!!A\u0005B\tE\u0005\"\u0003BK\u0001\u0005\u0005I\u0011\tBL\u0011%\u0011I\nAA\u0001\n\u0003\u0012Y\nC\u0005\u0003\u001e\u0002\t\t\u0011\"\u0011\u0003 \u001e9!1\u00150\t\u0002\t\u0015fAB/_\u0011\u0003\u00119\u000bC\u0004\u0002$R\"\tA!/\t\u000f\tmF\u0007\"\u0001\u0003>\"9!Q\u001a\u001b\u0005\u0002\t=\u0007\"\u0003BmiE\u0005I\u0011\u0001B\u0019\u0011%\u0011Y\u000eNI\u0001\n\u0003\u0011i\u000eC\u0004\u0003bR\"\tAa9\t\u0013\t-H'%A\u0005\u0002\t5\bb\u0002Byi\u0011\u0005!1\u001f\u0005\b\u0005k$D\u0011\u0001B|\u0011\u001d\u0011Y\u0010\u000eC\u0001\u0005{Dqa!\u00035\t\u0003\u0011\u0019\u0010C\u0004\u0004\fQ\"\tAa=\t\u000f\r5A\u0007\"\u0001\u0004\u0010!911\u0003\u001b\u0005\u0002\rU\u0001bBB\u0010i\u0011\u00051\u0011\u0005\u0005\n\u0007O!\u0014\u0011!CA\u0007SA\u0011ba\u00115#\u0003%\tA!\n\t\u0013\r\u0015C'%A\u0005\u0002\t-\u0002\"CB$iE\u0005I\u0011\u0001B\u0019\u0011%\u0019I\u0005NI\u0001\n\u0003\u0011\t\u0004C\u0005\u0004LQ\n\n\u0011\"\u0001\u00032!I1Q\n\u001b\u0012\u0002\u0013\u0005!1\b\u0005\n\u0007\u001f\"\u0014\u0013!C\u0001\u0005\u0003B\u0011b!\u00155#\u0003%\tA!\r\t\u0013\rMC'%A\u0005\u0002\t%\u0003\"CB+iE\u0005I\u0011\u0001B\u0019\u0011%\u00199\u0006NI\u0001\n\u0003\u0011\t\u0006C\u0005\u0004ZQ\n\t\u0011\"!\u0004\\!I1\u0011\u000e\u001b\u0012\u0002\u0013\u0005!Q\u0005\u0005\n\u0007W\"\u0014\u0013!C\u0001\u0005WA\u0011b!\u001c5#\u0003%\tA!\r\t\u0013\r=D'%A\u0005\u0002\tE\u0002\"CB9iE\u0005I\u0011\u0001B\u0019\u0011%\u0019\u0019\bNI\u0001\n\u0003\u0011Y\u0004C\u0005\u0004vQ\n\n\u0011\"\u0001\u0003B!I1q\u000f\u001b\u0012\u0002\u0013\u0005!\u0011\u0007\u0005\n\u0007s\"\u0014\u0013!C\u0001\u0005\u0013B\u0011ba\u001f5#\u0003%\tA!\r\t\u0013\ruD'%A\u0005\u0002\tE\u0003\"CB@i\u0005\u0005I\u0011BBA\u0005)!\u0016m]6SKN,H\u000e\u001e\u0006\u0003?\u0002\fQ!\\8eK2T\u0011!Y\u0001\u0005G>$Wm\u0001\u0001\u0014\t\u0001!'.\u001c\t\u0003K\"l\u0011A\u001a\u0006\u0002O\u0006)1oY1mC&\u0011\u0011N\u001a\u0002\u0007\u0003:L(+\u001a4\u0011\u0005\u0015\\\u0017B\u00017g\u0005\u001d\u0001&o\u001c3vGR\u0004\"A\u001c<\u000f\u0005=$hB\u00019t\u001b\u0005\t(B\u0001:c\u0003\u0019a$o\\8u}%\tq-\u0003\u0002vM\u00069\u0001/Y2lC\u001e,\u0017BA<y\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\t)h-\u0001\u0004ti\u0006$Xo]\u000b\u0002wB\u0019A0a\u0002\u000f\u0007u\f\u0019AD\u0002\u007f\u0003\u0003q!\u0001]@\n\u0003\u0005L!a\u00181\n\u0007\u0005\u0015a,\u0001\u0006UCN\\7\u000b^1ukNLA!!\u0003\u0002\f\tQA+Y:l'R\fG/^:\u000b\u0007\u0005\u0015a,A\u0004ti\u0006$Xo\u001d\u0011\u0002\rI,7/\u001e7u+\t\t\u0019\u0002E\u0003f\u0003+\tI\"C\u0002\u0002\u0018\u0019\u0014aa\u00149uS>t\u0007\u0003BA\u000e\u0003Ki!!!\b\u000b\t\u0005}\u0011\u0011E\u0001\u0003S>T!!a\t\u0002\t)\fg/Y\u0005\u0005\u0003O\tiB\u0001\u0003GS2,\u0017a\u0002:fgVdG\u000fI\u0001\fe\u0016\u001cX\u000f\u001c;Ji\u0016l7/\u0006\u0002\u00020A)a.!\r\u0002\u001a%\u0019\u00111\u0007=\u0003\u0007M+\u0017/\u0001\u0007sKN,H\u000e^%uK6\u001c\b%A\u0007gC&dWO]3SK\u0006\u001cxN\\\u000b\u0003\u0003w\u0001R!ZA\u000b\u0003{\u0001B!a\u0010\u0002H9!\u0011\u0011IA\"!\t\u0001h-C\u0002\u0002F\u0019\fa\u0001\u0015:fI\u00164\u0017\u0002BA%\u0003\u0017\u0012aa\u0015;sS:<'bAA#M\u0006qa-Y5mkJ,'+Z1t_:\u0004\u0013!\u00054bS2,(/\u001a*fCN|gnQ8eK\u0006\u0011b-Y5mkJ,'+Z1t_:\u001cu\u000eZ3!\u000311\u0017-\u001b7ve\u0016\u001cF/Y2l\u000351\u0017-\u001b7ve\u0016\u001cF/Y2lA\u0005AA/[7fI>+H/\u0006\u0002\u0002\\A\u0019Q-!\u0018\n\u0007\u0005}cMA\u0004C_>dW-\u00198\u0002\u0013QLW.\u001a3PkR\u0004\u0013\u0001C<be:LgnZ:\u0016\u0005\u0005\u001d\u0004#\u00028\u00022\u0005u\u0012!C<be:LgnZ:!\u000311\u0017-\u001b7ve\u0016\u001c6m\u001c9f\u000351\u0017-\u001b7ve\u0016\u001c6m\u001c9fA\u00059Q\u000f]4sC\u0012,WCAA:!\u0015)\u0017QCA;!\u0011\t9(! \u000e\u0005\u0005e$bAA>A\u00061A.[7jiNLA!a \u0002z\t9Q\u000b]4sC\u0012,\u0017\u0001C;qOJ\fG-\u001a\u0011\u0002\rI,W.\u0019:l\u0003\u001d\u0011X-\\1sW\u0002\n1\"];pi\u0006,\u0006\u000fZ1uKV\u0011\u00111\u0012\t\u0006K\u0006U\u0011Q\u0012\t\u0005\u0003o\ny)\u0003\u0003\u0002\u0012\u0006e$aC)v_R\fW\u000b\u001d3bi\u0016\fq\"];pi\u0006,\u0006\u000fZ1uK~#S-\u001d\u000b\u0005\u0003/\u000bi\nE\u0002f\u00033K1!a'g\u0005\u0011)f.\u001b;\t\u0013\u0005}\u0005$!AA\u0002\u0005-\u0015a\u0001=%c\u0005a\u0011/^8uCV\u0003H-\u0019;fA\u00051A(\u001b8jiz\"\"$a*\u0002,\u00065\u0016qVAY\u0003g\u000b),a.\u0002:\u0006m\u0016QXA`\u0003\u0003\u00042!!+\u0001\u001b\u0005q\u0006\"B=\u001b\u0001\u0004Y\b\"CA\b5A\u0005\t\u0019AA\n\u0011%\tYC\u0007I\u0001\u0002\u0004\ty\u0003C\u0005\u00028i\u0001\n\u00111\u0001\u0002<!I\u0011q\n\u000e\u0011\u0002\u0003\u0007\u00111\b\u0005\n\u0003'R\u0002\u0013!a\u0001\u0003wA\u0011\"a\u0016\u001b!\u0003\u0005\r!a\u0017\t\u0013\u0005\r$\u0004%AA\u0002\u0005\u001d\u0004\"CA65A\u0005\t\u0019AA\u001e\u0011%\tyG\u0007I\u0001\u0002\u0004\t\u0019\bC\u0005\u0002\u0004j\u0001\n\u00111\u0001\u0002<!I\u0011q\u0011\u000e\u0011\u0002\u0003\u0007\u00111R\u0001\u0007i>T5o\u001c8\u0015\u0005\u0005\u001d\u0007\u0003BAe\u0003OtA!a3\u0002b:!\u0011QZAn\u001d\u0011\ty-!6\u000f\u0007A\f\t.\u0003\u0002\u0002T\u0006\u0019a.\u001a;\n\t\u0005]\u0017\u0011\\\u0001\bY&4Go^3c\u0015\t\t\u0019.\u0003\u0003\u0002^\u0006}\u0017\u0001\u00026t_:TA!a6\u0002Z&!\u00111]As\u0003\u001dQ5o\u001c8B'RSA!!8\u0002`&!\u0011\u0011^Av\u0005\u0019Qe+\u00197vK*!\u00111]As\u0003%!xNS:p]\u0016CH/\u0001\u0003d_BLHCGAT\u0003g\f)0a>\u0002z\u0006m\u0018Q`A��\u0005\u0003\u0011\u0019A!\u0002\u0003\b\t%\u0001bB=\u001e!\u0003\u0005\ra\u001f\u0005\n\u0003\u001fi\u0002\u0013!a\u0001\u0003'A\u0011\"a\u000b\u001e!\u0003\u0005\r!a\f\t\u0013\u0005]R\u0004%AA\u0002\u0005m\u0002\"CA(;A\u0005\t\u0019AA\u001e\u0011%\t\u0019&\bI\u0001\u0002\u0004\tY\u0004C\u0005\u0002Xu\u0001\n\u00111\u0001\u0002\\!I\u00111M\u000f\u0011\u0002\u0003\u0007\u0011q\r\u0005\n\u0003Wj\u0002\u0013!a\u0001\u0003wA\u0011\"a\u001c\u001e!\u0003\u0005\r!a\u001d\t\u0013\u0005\rU\u0004%AA\u0002\u0005m\u0002\"CAD;A\u0005\t\u0019AAF\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\"Aa\u0004+\u0007m\u0014\tb\u000b\u0002\u0003\u0014A!!Q\u0003B\u0010\u001b\t\u00119B\u0003\u0003\u0003\u001a\tm\u0011!C;oG\",7m[3e\u0015\r\u0011iBZ\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002\u0002B\u0011\u0005/\u0011\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII*\"Aa\n+\t\u0005M!\u0011C\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00134+\t\u0011iC\u000b\u0003\u00020\tE\u0011AD2paf$C-\u001a4bk2$H\u0005N\u000b\u0003\u0005gQC!a\u000f\u0003\u0012\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012*\u0014AD2paf$C-\u001a4bk2$HEN\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00138+\t\u0011iD\u000b\u0003\u0002\\\tE\u0011AD2paf$C-\u001a4bk2$H\u0005O\u000b\u0003\u0005\u0007RC!a\u001a\u0003\u0012\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012J\u0014aD2paf$C-\u001a4bk2$H%\r\u0019\u0016\u0005\t-#\u0006BA:\u0005#\tqbY8qs\u0012\"WMZ1vYR$\u0013'M\u0001\u0010G>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132eU\u0011!1\u000b\u0016\u0005\u0003\u0017\u0013\t\"A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0003\u00053\u0002BAa\u0017\u0003b5\u0011!Q\f\u0006\u0005\u0005?\n\t#\u0001\u0003mC:<\u0017\u0002BA%\u0005;\nA\u0002\u001d:pIV\u001cG/\u0011:jif,\"Aa\u001a\u0011\u0007\u0015\u0014I'C\u0002\u0003l\u0019\u00141!\u00138u\u00039\u0001(o\u001c3vGR,E.Z7f]R$BA!\u001d\u0003xA\u0019QMa\u001d\n\u0007\tUdMA\u0002B]fD\u0011\"a(-\u0003\u0003\u0005\rAa\u001a\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\"A! \u0011\r\t}$Q\u0011B9\u001b\t\u0011\tIC\u0002\u0003\u0004\u001a\f!bY8mY\u0016\u001cG/[8o\u0013\u0011\u00119I!!\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u00037\u0012i\tC\u0005\u0002 :\n\t\u00111\u0001\u0003r\u0005\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\u0011\u0011IFa%\t\u0013\u0005}u&!AA\u0002\t\u001d\u0014\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0005\t\u001d\u0014\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\te\u0013AB3rk\u0006d7\u000f\u0006\u0003\u0002\\\t\u0005\u0006\"CAPe\u0005\u0005\t\u0019\u0001B9\u0003)!\u0016m]6SKN,H\u000e\u001e\t\u0004\u0003S#4C\u0002\u001be\u0005S\u0013)\f\u0005\u0003\u0003,\nEVB\u0001BW\u0015\r\u0011y\u000bY\u0001\u0005kRLG.\u0003\u0003\u00034\n5&\u0001\u0003'pO\u001e\f'\r\\3\u0011\t\u0005m!qW\u0005\u0004o\u0006uAC\u0001BS\u0003u1\u0017-\u001b7ve\u0016\\en\\<o%\u0016\f7o\u001c8EKN\\Go\u001c9P]2LHCBAT\u0005\u007f\u0013\u0019\rC\u0004\u0003BZ\u0002\r!!\u0010\u0002\rI,\u0017m]8o\u0011\u001d\u0011)M\u000ea\u0001\u0005\u000f\fQaY1vg\u0016\u00042A\u001cBe\u0013\r\u0011Y\r\u001f\u0002\n)\"\u0014xn^1cY\u0016\f!CZ1jYV\u0014Xm\u00138po:\u0014V-Y:p]RQ\u0011q\u0015Bi\u0005'\u0014)Na6\t\u000f\t\u0005w\u00071\u0001\u0002>!9!QY\u001cA\u0002\t\u001d\u0007\"CA6oA\u0005\t\u0019AA\u001e\u0011!\tw\u0007%AA\u0002\u0005u\u0012\u0001\b4bS2,(/Z&o_^t'+Z1t_:$C-\u001a4bk2$HeM\u0001\u001dM\u0006LG.\u001e:f\u0017:|wO\u001c*fCN|g\u000e\n3fM\u0006,H\u000e\u001e\u00135+\t\u0011yN\u000b\u0003\u0002>\tE\u0011a\u00044bS2,(/\u001a+j[\u0016$w*\u001e;\u0015\t\u0005\u001d&Q\u001d\u0005\n\u0005OT\u0004\u0013!a\u0001\u0005S\f\u0001bY1vg\u0016|\u0005\u000f\u001e\t\u0006K\u0006U!qY\u0001\u001aM\u0006LG.\u001e:f)&lW\rZ(vi\u0012\"WMZ1vYR$\u0013'\u0006\u0002\u0003p*\"!\u0011\u001eB\t\u0003A1\u0017-\u001b7ve\u0016\u001c\u0015M\\2fY2,G\r\u0006\u0002\u0002(\u00069a-Y5mkJ,G\u0003BAT\u0005sDqA!2>\u0001\u0004\u00119-A\u0004tk\u000e\u001cWm]:\u0015\u0019\u0005\u001d&q`B\u0001\u0007\u0007\u0019)aa\u0002\t\u000f\u0005=a\b1\u0001\u0002\u001a!9\u00111\r A\u0002\u0005\u001d\u0004bBA8}\u0001\u0007\u00111\u000f\u0005\b\u0003\u0007s\u0004\u0019AA\u001e\u0011\u001d\tYC\u0010a\u0001\u0003_\t!\u0002\u001d:pG\u0016\u001c8/\u001b8h\u0003\u0019\tX/Z;fI\u0006AaM]8n\u0015N|g\u000e\u0006\u0003\u0002(\u000eE\u0001bBAo\u0003\u0002\u0007\u0011qY\u0001\u0014W:|wO\u001c$bS2,(/\u001a*fCN|gn\u001d\u000b\u0007\u0003O\u001b9ba\u0007\t\u000f\re!\t1\u0001\u0003H\u0006\u0011Q\r\u001f\u0005\b\u0007;\u0011\u0005\u0019\u0001B4\u0003-!\u0018m]6D_:$X\r\u001f;\u0002)}[gn\\<o\r\u0006LG.\u001e:f%\u0016\f7o\u001c8t)\u0019\t9ka\t\u0004&!91\u0011D\"A\u0002\t\u001d\u0007bBB\u000f\u0007\u0002\u0007!qM\u0001\u0006CB\u0004H.\u001f\u000b\u001b\u0003O\u001bYc!\f\u00040\rE21GB\u001b\u0007o\u0019Ida\u000f\u0004>\r}2\u0011\t\u0005\u0006s\u0012\u0003\ra\u001f\u0005\n\u0003\u001f!\u0005\u0013!a\u0001\u0003'A\u0011\"a\u000bE!\u0003\u0005\r!a\f\t\u0013\u0005]B\t%AA\u0002\u0005m\u0002\"CA(\tB\u0005\t\u0019AA\u001e\u0011%\t\u0019\u0006\u0012I\u0001\u0002\u0004\tY\u0004C\u0005\u0002X\u0011\u0003\n\u00111\u0001\u0002\\!I\u00111\r#\u0011\u0002\u0003\u0007\u0011q\r\u0005\n\u0003W\"\u0005\u0013!a\u0001\u0003wA\u0011\"a\u001cE!\u0003\u0005\r!a\u001d\t\u0013\u0005\rE\t%AA\u0002\u0005m\u0002\"CAD\tB\u0005\t\u0019AAF\u0003=\t\u0007\u000f\u001d7zI\u0011,g-Y;mi\u0012\u0012\u0014aD1qa2LH\u0005Z3gCVdG\u000fJ\u001a\u0002\u001f\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uIQ\nq\"\u00199qYf$C-\u001a4bk2$H%N\u0001\u0010CB\u0004H.\u001f\u0013eK\u001a\fW\u000f\u001c;%m\u0005y\u0011\r\u001d9ms\u0012\"WMZ1vYR$s'A\bbaBd\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00139\u0003=\t\u0007\u000f\u001d7zI\u0011,g-Y;mi\u0012J\u0014\u0001E1qa2LH\u0005Z3gCVdG\u000fJ\u00191\u0003A\t\u0007\u000f\u001d7zI\u0011,g-Y;mi\u0012\n\u0014'\u0001\tbaBd\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00132e\u00059QO\\1qa2LH\u0003BB/\u0007K\u0002R!ZA\u000b\u0007?\u0002\"$ZB1w\u0006M\u0011qFA\u001e\u0003w\tY$a\u0017\u0002h\u0005m\u00121OA\u001e\u0003\u0017K1aa\u0019g\u0005\u001d!V\u000f\u001d7fcIB\u0011ba\u001aQ\u0003\u0003\u0005\r!a*\u0002\u0007a$\u0003'A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$HEM\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000fJ\u001a\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00135\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%k\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIY\n1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012:\u0014a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$\u0003(A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$H%O\u0001\u001dI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000fJ\u00191\u0003q!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%cE\nA\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\n$'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0004\u0004B!!1LBC\u0013\u0011\u00199I!\u0018\u0003\r=\u0013'.Z2u\u0001")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/model/TaskResult.class */
public class TaskResult implements Product, Serializable {
    private final Enumeration.Value status;
    private final Option<File> result;
    private final Seq<File> resultItems;
    private final Option<String> failureReason;
    private final Option<String> failureReasonCode;
    private final Option<String> failureStack;
    private final boolean timedOut;
    private final Seq<String> warnings;
    private final Option<String> failureScope;
    private final Option<Upgrade> upgrade;
    private final Option<String> remark;
    private Option<QuotaUpdate> quotaUpdate;

    public static Option<Tuple12<Enumeration.Value, Option<File>, Seq<File>, Option<String>, Option<String>, Option<String>, Object, Seq<String>, Option<String>, Option<Upgrade>, Option<String>, Option<QuotaUpdate>>> unapply(final TaskResult x$0) {
        return TaskResult$.MODULE$.unapply(x$0);
    }

    public static TaskResult apply(final Enumeration.Value status, final Option<File> result, final Seq<File> resultItems, final Option<String> failureReason, final Option<String> failureReasonCode, final Option<String> failureStack, final boolean timedOut, final Seq<String> warnings, final Option<String> failureScope, final Option<Upgrade> upgrade, final Option<String> remark, final Option<QuotaUpdate> quotaUpdate) {
        return TaskResult$.MODULE$.apply(status, result, resultItems, failureReason, failureReasonCode, failureStack, timedOut, warnings, failureScope, upgrade, remark, quotaUpdate);
    }

    public static TaskResult _knownFailureReasons(final Throwable ex, final int taskContext) {
        return TaskResult$.MODULE$._knownFailureReasons(ex, taskContext);
    }

    public static TaskResult knownFailureReasons(final Throwable ex, final int taskContext) {
        return TaskResult$.MODULE$.knownFailureReasons(ex, taskContext);
    }

    public static TaskResult fromJson(final JsonAST.JValue json) {
        return TaskResult$.MODULE$.fromJson(json);
    }

    public static TaskResult queued() {
        return TaskResult$.MODULE$.queued();
    }

    public static TaskResult processing() {
        return TaskResult$.MODULE$.processing();
    }

    public static TaskResult success(final File result, final Seq<String> warnings, final Option<Upgrade> upgrade, final Option<String> remark, final Seq<File> resultItems) {
        return TaskResult$.MODULE$.success(result, warnings, upgrade, remark, resultItems);
    }

    public static TaskResult failure(final Throwable cause) {
        return TaskResult$.MODULE$.failure(cause);
    }

    public static TaskResult failureCancelled() {
        return TaskResult$.MODULE$.failureCancelled();
    }

    public static TaskResult failureTimedOut(final Option<Throwable> causeOpt) {
        return TaskResult$.MODULE$.failureTimedOut(causeOpt);
    }

    public static TaskResult failureKnownReason(final String reason, final Throwable cause, final Option<String> failureScope, final String code2) {
        return TaskResult$.MODULE$.failureKnownReason(reason, cause, failureScope, code2);
    }

    public static TaskResult failureKnownReasonDesktopOnly(final String reason, final Throwable cause) {
        return TaskResult$.MODULE$.failureKnownReasonDesktopOnly(reason, cause);
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Enumeration.Value status() {
        return this.status;
    }

    public TaskResult copy(final Enumeration.Value status, final Option<File> result, final Seq<File> resultItems, final Option<String> failureReason, final Option<String> failureReasonCode, final Option<String> failureStack, final boolean timedOut, final Seq<String> warnings, final Option<String> failureScope, final Option<Upgrade> upgrade, final Option<String> remark, final Option<QuotaUpdate> quotaUpdate) {
        return new TaskResult(status, result, resultItems, failureReason, failureReasonCode, failureStack, timedOut, warnings, failureScope, upgrade, remark, quotaUpdate);
    }

    public Enumeration.Value copy$default$1() {
        return status();
    }

    public String productPrefix() {
        return "TaskResult";
    }

    public int productArity() {
        return 12;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return status();
            case 1:
                return result();
            case 2:
                return resultItems();
            case 3:
                return failureReason();
            case 4:
                return failureReasonCode();
            case 5:
                return failureStack();
            case 6:
                return BoxesRunTime.boxToBoolean(timedOut());
            case 7:
                return warnings();
            case 8:
                return failureScope();
            case 9:
                return upgrade();
            case 10:
                return remark();
            case 11:
                return quotaUpdate();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof TaskResult;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "status";
            case 1:
                return "result";
            case 2:
                return "resultItems";
            case 3:
                return "failureReason";
            case 4:
                return "failureReasonCode";
            case 5:
                return "failureStack";
            case 6:
                return "timedOut";
            case 7:
                return "warnings";
            case 8:
                return "failureScope";
            case 9:
                return "upgrade";
            case 10:
                return "remark";
            case 11:
                return "quotaUpdate";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return Statics.finalizeHash(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(-889275714, productPrefix().hashCode()), Statics.anyHash(status())), Statics.anyHash(result())), Statics.anyHash(resultItems())), Statics.anyHash(failureReason())), Statics.anyHash(failureReasonCode())), Statics.anyHash(failureStack())), timedOut() ? 1231 : 1237), Statics.anyHash(warnings())), Statics.anyHash(failureScope())), Statics.anyHash(upgrade())), Statics.anyHash(remark())), Statics.anyHash(quotaUpdate())), 12);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof TaskResult) {
                TaskResult taskResult = (TaskResult) x$1;
                if (timedOut() == taskResult.timedOut()) {
                    Enumeration.Value valueStatus = status();
                    Enumeration.Value valueStatus2 = taskResult.status();
                    if (valueStatus != null ? valueStatus.equals(valueStatus2) : valueStatus2 == null) {
                        Option<File> optionResult = result();
                        Option<File> optionResult2 = taskResult.result();
                        if (optionResult != null ? optionResult.equals(optionResult2) : optionResult2 == null) {
                            Seq<File> seqResultItems = resultItems();
                            Seq<File> seqResultItems2 = taskResult.resultItems();
                            if (seqResultItems != null ? seqResultItems.equals(seqResultItems2) : seqResultItems2 == null) {
                                Option<String> optionFailureReason = failureReason();
                                Option<String> optionFailureReason2 = taskResult.failureReason();
                                if (optionFailureReason != null ? optionFailureReason.equals(optionFailureReason2) : optionFailureReason2 == null) {
                                    Option<String> optionFailureReasonCode = failureReasonCode();
                                    Option<String> optionFailureReasonCode2 = taskResult.failureReasonCode();
                                    if (optionFailureReasonCode != null ? optionFailureReasonCode.equals(optionFailureReasonCode2) : optionFailureReasonCode2 == null) {
                                        Option<String> optionFailureStack = failureStack();
                                        Option<String> optionFailureStack2 = taskResult.failureStack();
                                        if (optionFailureStack != null ? optionFailureStack.equals(optionFailureStack2) : optionFailureStack2 == null) {
                                            Seq<String> seqWarnings = warnings();
                                            Seq<String> seqWarnings2 = taskResult.warnings();
                                            if (seqWarnings != null ? seqWarnings.equals(seqWarnings2) : seqWarnings2 == null) {
                                                Option<String> optionFailureScope = failureScope();
                                                Option<String> optionFailureScope2 = taskResult.failureScope();
                                                if (optionFailureScope != null ? optionFailureScope.equals(optionFailureScope2) : optionFailureScope2 == null) {
                                                    Option<Upgrade> optionUpgrade = upgrade();
                                                    Option<Upgrade> optionUpgrade2 = taskResult.upgrade();
                                                    if (optionUpgrade != null ? optionUpgrade.equals(optionUpgrade2) : optionUpgrade2 == null) {
                                                        Option<String> optionRemark = remark();
                                                        Option<String> optionRemark2 = taskResult.remark();
                                                        if (optionRemark != null ? optionRemark.equals(optionRemark2) : optionRemark2 == null) {
                                                            Option<QuotaUpdate> optionQuotaUpdate = quotaUpdate();
                                                            Option<QuotaUpdate> optionQuotaUpdate2 = taskResult.quotaUpdate();
                                                            if (optionQuotaUpdate != null ? optionQuotaUpdate.equals(optionQuotaUpdate2) : optionQuotaUpdate2 == null) {
                                                                if (taskResult.canEqual(this)) {
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
                }
            }
            return false;
        }
        return true;
    }

    public TaskResult(final Enumeration.Value status, final Option<File> result, final Seq<File> resultItems, final Option<String> failureReason, final Option<String> failureReasonCode, final Option<String> failureStack, final boolean timedOut, final Seq<String> warnings, final Option<String> failureScope, final Option<Upgrade> upgrade, final Option<String> remark, final Option<QuotaUpdate> quotaUpdate) {
        this.status = status;
        this.result = result;
        this.resultItems = resultItems;
        this.failureReason = failureReason;
        this.failureReasonCode = failureReasonCode;
        this.failureStack = failureStack;
        this.timedOut = timedOut;
        this.warnings = warnings;
        this.failureScope = failureScope;
        this.upgrade = upgrade;
        this.remark = remark;
        this.quotaUpdate = quotaUpdate;
        Product.$init$(this);
    }

    public Option<File> result() {
        return this.result;
    }

    public Option<File> copy$default$2() {
        return result();
    }

    public Seq<File> resultItems() {
        return this.resultItems;
    }

    public Seq<File> copy$default$3() {
        return resultItems();
    }

    public Option<String> failureReason() {
        return this.failureReason;
    }

    public Option<String> copy$default$4() {
        return failureReason();
    }

    public Option<String> failureReasonCode() {
        return this.failureReasonCode;
    }

    public Option<String> copy$default$5() {
        return failureReasonCode();
    }

    public Option<String> failureStack() {
        return this.failureStack;
    }

    public Option<String> copy$default$6() {
        return failureStack();
    }

    public boolean timedOut() {
        return this.timedOut;
    }

    public boolean copy$default$7() {
        return timedOut();
    }

    public Seq<String> warnings() {
        return this.warnings;
    }

    public Seq<String> copy$default$8() {
        return warnings();
    }

    public Option<String> failureScope() {
        return this.failureScope;
    }

    public Option<String> copy$default$9() {
        return failureScope();
    }

    public Option<Upgrade> upgrade() {
        return this.upgrade;
    }

    public Option<Upgrade> copy$default$10() {
        return upgrade();
    }

    public Option<String> remark() {
        return this.remark;
    }

    public Option<String> copy$default$11() {
        return remark();
    }

    public Option<QuotaUpdate> quotaUpdate() {
        return this.quotaUpdate;
    }

    public void quotaUpdate_$eq(final Option<QuotaUpdate> x$1) {
        this.quotaUpdate = x$1;
    }

    public Option<QuotaUpdate> copy$default$12() {
        return quotaUpdate();
    }

    public static final /* synthetic */ JsonAST.JBool $anonfun$toJson$24(final boolean x) {
        return JsonDSL$.MODULE$.boolean2jvalue(x);
    }

    public JsonAST.JValue toJson() {
        return JsonDSL$.MODULE$.jobject2assoc(JsonDSL$.MODULE$.jobject2assoc(JsonDSL$.MODULE$.jobject2assoc(JsonDSL$.MODULE$.jobject2assoc(JsonDSL$.MODULE$.jobject2assoc(JsonDSL$.MODULE$.jobject2assoc(JsonDSL$.MODULE$.jobject2assoc(JsonDSL$.MODULE$.jobject2assoc(JsonDSL$.MODULE$.jobject2assoc(JsonDSL$.MODULE$.pair2Assoc(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("status"), status().toString()), x -> {
            return JsonDSL$.MODULE$.string2jvalue(x);
        }).$tilde(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("failureStack"), failureStack()), opt -> {
            return JsonDSL$.MODULE$.option2jvalue(opt, x2 -> {
                return JsonDSL$.MODULE$.string2jvalue(x2);
            });
        })).$tilde(JsonDSL$.MODULE$.pair2jvalue(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("failureReason"), failureReason()), opt2 -> {
            return JsonDSL$.MODULE$.option2jvalue(opt2, x2 -> {
                return JsonDSL$.MODULE$.string2jvalue(x2);
            });
        }))).$tilde(JsonDSL$.MODULE$.pair2jvalue(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("failureReasonCode"), failureReasonCode()), opt3 -> {
            return JsonDSL$.MODULE$.option2jvalue(opt3, x2 -> {
                return JsonDSL$.MODULE$.string2jvalue(x2);
            });
        }))).$tilde(JsonDSL$.MODULE$.pair2jvalue(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("timedOut"), BoxesRunTime.boxToBoolean(timedOut())), x2 -> {
            return $anonfun$toJson$24(BoxesRunTime.unboxToBoolean(x2));
        }))).$tilde(JsonDSL$.MODULE$.pair2jvalue(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("result"), result().map(x$4 -> {
            return x$4.getAbsolutePath();
        })), opt4 -> {
            return JsonDSL$.MODULE$.option2jvalue(opt4, x3 -> {
                return JsonDSL$.MODULE$.string2jvalue(x3);
            });
        }))).$tilde(JsonDSL$.MODULE$.pair2jvalue(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("warnings"), warnings()), s -> {
            return JsonDSL$.MODULE$.seq2jvalue(s, x3 -> {
                return JsonDSL$.MODULE$.string2jvalue(x3);
            });
        }))).$tilde(JsonDSL$.MODULE$.pair2jvalue(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("failureScope"), failureScope()), opt5 -> {
            return JsonDSL$.MODULE$.option2jvalue(opt5, x3 -> {
                return JsonDSL$.MODULE$.string2jvalue(x3);
            });
        }))).$tilde(JsonDSL$.MODULE$.pair2jvalue(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("upgrade"), upgrade().map(x$5 -> {
            return x$5.toJson();
        })), opt6 -> {
            return JsonDSL$.MODULE$.option2jvalue(opt6, Predef$.MODULE$.$conforms());
        }))).$tilde(JsonDSL$.MODULE$.pair2jvalue(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("remark"), remark()), opt7 -> {
            return JsonDSL$.MODULE$.option2jvalue(opt7, x3 -> {
                return JsonDSL$.MODULE$.string2jvalue(x3);
            });
        }))).$tilde(JsonDSL$.MODULE$.pair2jvalue(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("quotaUpdate"), quotaUpdate().map(x$6 -> {
            return x$6.toJson();
        })), opt8 -> {
            return JsonDSL$.MODULE$.option2jvalue(opt8, Predef$.MODULE$.$conforms());
        }));
    }

    public JsonAST.JValue toJsonExt() {
        return JsonDSL$.MODULE$.pair2jvalue(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("resultItems"), resultItems().map(x$7 -> {
            return x$7.getAbsolutePath();
        })), s -> {
            return JsonDSL$.MODULE$.seq2jvalue(s, x -> {
                return JsonDSL$.MODULE$.string2jvalue(x);
            });
        });
    }
}
