package code.sejda.tasks.edit;

import java.awt.Color;
import java.io.Serializable;
import org.sejda.model.RectangularBox;
import org.sejda.sambox.filter.TIFFExtension;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.util.CharUtils;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple17;
import scala.collection.Iterator;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: EditParameters.scala */
@ScalaSignature(bytes = "\u0006\u0005\rUc\u0001B1c\u0001.D!\"a\u0001\u0001\u0005+\u0007I\u0011AA\u0003\u0011)\ty\u0001\u0001B\tB\u0003%\u0011q\u0001\u0005\u000b\u0003#\u0001!\u00113A\u0005\u0002\u0005M\u0001BCA\u0013\u0001\t\u0005\r\u0011\"\u0001\u0002(!Q\u00111\u0007\u0001\u0003\u0012\u0003\u0006K!!\u0006\t\u0015\u0005U\u0002A!f\u0001\n\u0003\t9\u0004\u0003\u0006\u0002@\u0001\u0011\t\u0012)A\u0005\u0003sA!\"!\u0011\u0001\u0005+\u0007I\u0011AA\"\u0011)\t9\u0006\u0001B\tB\u0003%\u0011Q\t\u0005\u000b\u00033\u0002!Q3A\u0005\u0002\u0005m\u0003BCA2\u0001\tE\t\u0015!\u0003\u0002^!Q\u0011Q\r\u0001\u0003\u0016\u0004%\t!a\u0017\t\u0015\u0005\u001d\u0004A!E!\u0002\u0013\ti\u0006\u0003\u0006\u0002j\u0001\u0011)\u001a!C\u0001\u0003WB!\"a\u001d\u0001\u0005#\u0005\u000b\u0011BA7\u0011)\t)\b\u0001BK\u0002\u0013\u0005\u0011q\u000f\u0005\u000b\u0003\u007f\u0002!\u0011#Q\u0001\n\u0005e\u0004BCAA\u0001\tU\r\u0011\"\u0001\u0002\\!Q\u00111\u0011\u0001\u0003\u0012\u0003\u0006I!!\u0018\t\u0015\u0005\u0015\u0005A!f\u0001\n\u0003\t9\t\u0003\u0006\u0002\u001c\u0002\u0011\t\u0012)A\u0005\u0003\u0013C!\"!(\u0001\u0005+\u0007I\u0011AAD\u0011)\ty\n\u0001B\tB\u0003%\u0011\u0011\u0012\u0005\u000b\u0003C\u0003!Q3A\u0005\u0002\u0005m\u0003BCAR\u0001\tE\t\u0015!\u0003\u0002^!Q\u0011Q\u0015\u0001\u0003\u0016\u0004%\t!a*\t\u0015\u0005E\u0006A!E!\u0002\u0013\tI\u000b\u0003\u0006\u00024\u0002\u0011)\u001a!C\u0001\u0003kC!\"!0\u0001\u0005#\u0005\u000b\u0011BA\\\u0011)\ty\f\u0001BK\u0002\u0013\u0005\u00111\f\u0005\u000b\u0003\u0003\u0004!\u0011#Q\u0001\n\u0005u\u0003BCAb\u0001\tU\r\u0011\"\u0001\u0002\u0014!Q\u0011Q\u0019\u0001\u0003\u0012\u0003\u0006I!!\u0006\t\u0015\u0005\u001d\u0007A!f\u0001\n\u0003\tI\r\u0003\u0006\u0002N\u0002\u0011\t\u0012)A\u0005\u0003\u0017Dq!a4\u0001\t\u0003\t\t\u000eC\u0005\u0002x\u0002\t\t\u0011\"\u0001\u0002z\"I!Q\u0004\u0001\u0012\u0002\u0013\u0005!q\u0004\u0005\n\u0005k\u0001\u0011\u0013!C\u0001\u0005oA\u0011Ba\u000f\u0001#\u0003%\tA!\u0010\t\u0013\t\u0005\u0003!%A\u0005\u0002\t\r\u0003\"\u0003B$\u0001E\u0005I\u0011\u0001B%\u0011%\u0011i\u0005AI\u0001\n\u0003\u0011I\u0005C\u0005\u0003P\u0001\t\n\u0011\"\u0001\u0003R!I!Q\u000b\u0001\u0012\u0002\u0013\u0005!q\u000b\u0005\n\u00057\u0002\u0011\u0013!C\u0001\u0005\u0013B\u0011B!\u0018\u0001#\u0003%\tAa\u0018\t\u0013\t\r\u0004!%A\u0005\u0002\t}\u0003\"\u0003B3\u0001E\u0005I\u0011\u0001B%\u0011%\u00119\u0007AI\u0001\n\u0003\u0011I\u0007C\u0005\u0003n\u0001\t\n\u0011\"\u0001\u0003p!I!1\u000f\u0001\u0012\u0002\u0013\u0005!\u0011\n\u0005\n\u0005k\u0002\u0011\u0013!C\u0001\u0005oA\u0011Ba\u001e\u0001#\u0003%\tA!\u001f\t\u0013\tu\u0004!!A\u0005B\t}\u0004\"\u0003BF\u0001\u0005\u0005I\u0011AA\u001c\u0011%\u0011i\tAA\u0001\n\u0003\u0011y\tC\u0005\u0003\u001a\u0002\t\t\u0011\"\u0011\u0003\u001c\"I!\u0011\u0016\u0001\u0002\u0002\u0013\u0005!1\u0016\u0005\n\u0005_\u0003\u0011\u0011!C!\u0005cC\u0011B!.\u0001\u0003\u0003%\tEa.\t\u0013\te\u0006!!A\u0005B\tm\u0006\"\u0003B_\u0001\u0005\u0005I\u0011\tB`\u000f%\u0011\u0019MYA\u0001\u0012\u0003\u0011)M\u0002\u0005bE\u0006\u0005\t\u0012\u0001Bd\u0011\u001d\ty-\u0011C\u0001\u0005?D\u0011B!/B\u0003\u0003%)Ea/\t\u0013\t\u0005\u0018)!A\u0005\u0002\n\r\b\"CB\u0004\u0003F\u0005I\u0011\u0001B%\u0011%\u0019I!QI\u0001\n\u0003\u0011I\u0005C\u0005\u0004\f\u0005\u000b\n\u0011\"\u0001\u0003R!I1QB!\u0012\u0002\u0013\u0005!q\u000b\u0005\n\u0007\u001f\t\u0015\u0013!C\u0001\u0005\u0013B\u0011b!\u0005B#\u0003%\tAa\u0018\t\u0013\rM\u0011)%A\u0005\u0002\t}\u0003\"CB\u000b\u0003F\u0005I\u0011\u0001B%\u0011%\u00199\"QI\u0001\n\u0003\u0011I\u0007C\u0005\u0004\u001a\u0005\u000b\n\u0011\"\u0001\u0003p!I11D!\u0012\u0002\u0013\u0005!\u0011\n\u0005\n\u0007;\t\u0015\u0013!C\u0001\u0005oA\u0011ba\bB#\u0003%\tA!\u001f\t\u0013\r\u0005\u0012)!A\u0005\u0002\u000e\r\u0002\"CB\u0019\u0003F\u0005I\u0011\u0001B%\u0011%\u0019\u0019$QI\u0001\n\u0003\u0011I\u0005C\u0005\u00046\u0005\u000b\n\u0011\"\u0001\u0003R!I1qG!\u0012\u0002\u0013\u0005!q\u000b\u0005\n\u0007s\t\u0015\u0013!C\u0001\u0005\u0013B\u0011ba\u000fB#\u0003%\tAa\u0018\t\u0013\ru\u0012)%A\u0005\u0002\t}\u0003\"CB \u0003F\u0005I\u0011\u0001B%\u0011%\u0019\t%QI\u0001\n\u0003\u0011I\u0007C\u0005\u0004D\u0005\u000b\n\u0011\"\u0001\u0003p!I1QI!\u0012\u0002\u0013\u0005!\u0011\n\u0005\n\u0007\u000f\n\u0015\u0013!C\u0001\u0005oA\u0011b!\u0013B#\u0003%\tA!\u001f\t\u0013\r-\u0013)!A\u0005\n\r5#\u0001G!qa\u0016tGMR8s[\u001aKW\r\u001c3Pa\u0016\u0014\u0018\r^5p]*\u00111\rZ\u0001\u0005K\u0012LGO\u0003\u0002fM\u0006)A/Y:lg*\u0011q\r[\u0001\u0006g\u0016TG-\u0019\u0006\u0002S\u0006!1m\u001c3f\u0007\u0001\u0019B\u0001\u00017skB\u0011Q\u000e]\u0007\u0002]*\tq.A\u0003tG\u0006d\u0017-\u0003\u0002r]\n1\u0011I\\=SK\u001a\u0004\"!\\:\n\u0005Qt'a\u0002)s_\u0012,8\r\u001e\t\u0003mzt!a\u001e?\u000f\u0005a\\X\"A=\u000b\u0005iT\u0017A\u0002\u001fs_>$h(C\u0001p\u0013\tih.A\u0004qC\u000e\\\u0017mZ3\n\u0007}\f\tA\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u0002~]\u0006!1.\u001b8e+\t\t9\u0001\u0005\u0003\u0002\n\u0005-Q\"\u00012\n\u0007\u00055!MA\u0007G_Jlg)[3mIRK\b/Z\u0001\u0006W&tG\rI\u0001\u0005]\u0006lW-\u0006\u0002\u0002\u0016A!\u0011qCA\u0010\u001d\u0011\tI\"a\u0007\u0011\u0005at\u0017bAA\u000f]\u00061\u0001K]3eK\u001aLA!!\t\u0002$\t11\u000b\u001e:j]\u001eT1!!\bo\u0003!q\u0017-\\3`I\u0015\fH\u0003BA\u0015\u0003_\u00012!\\A\u0016\u0013\r\tiC\u001c\u0002\u0005+:LG\u000fC\u0005\u00022\u0011\t\t\u00111\u0001\u0002\u0016\u0005\u0019\u0001\u0010J\u0019\u0002\u000b9\fW.\u001a\u0011\u0002\u0015A\fw-\u001a(v[\n,'/\u0006\u0002\u0002:A\u0019Q.a\u000f\n\u0007\u0005ubNA\u0002J]R\f1\u0002]1hK:+XNY3sA\u0005Y!m\\;oI&twMQ8y+\t\t)\u0005\u0005\u0003\u0002H\u0005MSBAA%\u0015\u0011\tY%!\u0014\u0002\u000b5|G-\u001a7\u000b\u0007\u001d\fyE\u0003\u0002\u0002R\u0005\u0019qN]4\n\t\u0005U\u0013\u0011\n\u0002\u000f%\u0016\u001cG/\u00198hk2\f'OQ8y\u00031\u0011w.\u001e8eS:<'i\u001c=!\u0003%iW\u000f\u001c;jY&tW-\u0006\u0002\u0002^A\u0019Q.a\u0018\n\u0007\u0005\u0005dNA\u0004C_>dW-\u00198\u0002\u00155,H\u000e^5mS:,\u0007%\u0001\u0005tK2,7\r^3e\u0003%\u0019X\r\\3di\u0016$\u0007%\u0001\u0005wC2,Xm\u00149u+\t\ti\u0007E\u0003n\u0003_\n)\"C\u0002\u0002r9\u0014aa\u00149uS>t\u0017!\u0003<bYV,w\n\u001d;!\u0003\u001dy\u0007\u000f^5p]N,\"!!\u001f\u0011\u000bY\fY(!\u0006\n\t\u0005u\u0014\u0011\u0001\u0002\u0004'\u0016\f\u0018\u0001C8qi&|gn\u001d\u0011\u0002\u00175,H\u000e^5tK2,7\r^\u0001\r[VdG/[:fY\u0016\u001cG\u000fI\u0001\fE>\u0014H-\u001a:D_2|'/\u0006\u0002\u0002\nB)Q.a\u001c\u0002\fB!\u0011QRAL\u001b\t\tyI\u0003\u0003\u0002\u0012\u0006M\u0015aA1xi*\u0011\u0011QS\u0001\u0005U\u00064\u0018-\u0003\u0003\u0002\u001a\u0006=%!B\"pY>\u0014\u0018\u0001\u00042pe\u0012,'oQ8m_J\u0004\u0013!B2pY>\u0014\u0018AB2pY>\u0014\b%A\u0005iCN\u0014uN\u001d3fe\u0006Q\u0001.Y:C_J$WM\u001d\u0011\u0002\u0017\u0019|g\u000e^*ju\u0016|\u0005\u000f^\u000b\u0003\u0003S\u0003R!\\A8\u0003W\u00032!\\AW\u0013\r\tyK\u001c\u0002\u0007\t>,(\r\\3\u0002\u0019\u0019|g\u000e^*ju\u0016|\u0005\u000f\u001e\u0011\u0002\u000b\u0005d\u0017n\u001a8\u0016\u0005\u0005]\u0006\u0003BA\u0005\u0003sK1!a/c\u000591uN]7GS\u0016dG-\u00117jO:\fa!\u00197jO:\u0004\u0013\u0001\u0003:fcVL'/\u001a3\u0002\u0013I,\u0017/^5sK\u0012\u0004\u0013AA5e\u0003\rIG\rI\u0001\nG>l'm\u00115beN,\"!a3\u0011\u000b5\fy'!\u000f\u0002\u0015\r|WNY\"iCJ\u001c\b%\u0001\u0004=S:LGO\u0010\u000b%\u0003'\f).a6\u0002Z\u0006m\u0017Q\\Ap\u0003C\f\u0019/!:\u0002h\u0006%\u00181^Aw\u0003_\f\t0a=\u0002vB\u0019\u0011\u0011\u0002\u0001\t\u000f\u0005\rA\u00051\u0001\u0002\b!9\u0011\u0011\u0003\u0013A\u0002\u0005U\u0001bBA\u001bI\u0001\u0007\u0011\u0011\b\u0005\b\u0003\u0003\"\u0003\u0019AA#\u0011%\tI\u0006\nI\u0001\u0002\u0004\ti\u0006C\u0005\u0002f\u0011\u0002\n\u00111\u0001\u0002^!I\u0011\u0011\u000e\u0013\u0011\u0002\u0003\u0007\u0011Q\u000e\u0005\n\u0003k\"\u0003\u0013!a\u0001\u0003sB\u0011\"!!%!\u0003\u0005\r!!\u0018\t\u0013\u0005\u0015E\u0005%AA\u0002\u0005%\u0005\"CAOIA\u0005\t\u0019AAE\u0011%\t\t\u000b\nI\u0001\u0002\u0004\ti\u0006C\u0005\u0002&\u0012\u0002\n\u00111\u0001\u0002*\"I\u00111\u0017\u0013\u0011\u0002\u0003\u0007\u0011q\u0017\u0005\n\u0003\u007f#\u0003\u0013!a\u0001\u0003;B\u0011\"a1%!\u0003\u0005\r!!\u0006\t\u0013\u0005\u001dG\u0005%AA\u0002\u0005-\u0017\u0001B2paf$B%a5\u0002|\u0006u\u0018q B\u0001\u0005\u0007\u0011)Aa\u0002\u0003\n\t-!Q\u0002B\b\u0005#\u0011\u0019B!\u0006\u0003\u0018\te!1\u0004\u0005\n\u0003\u0007)\u0003\u0013!a\u0001\u0003\u000fA\u0011\"!\u0005&!\u0003\u0005\r!!\u0006\t\u0013\u0005UR\u0005%AA\u0002\u0005e\u0002\"CA!KA\u0005\t\u0019AA#\u0011%\tI&\nI\u0001\u0002\u0004\ti\u0006C\u0005\u0002f\u0015\u0002\n\u00111\u0001\u0002^!I\u0011\u0011N\u0013\u0011\u0002\u0003\u0007\u0011Q\u000e\u0005\n\u0003k*\u0003\u0013!a\u0001\u0003sB\u0011\"!!&!\u0003\u0005\r!!\u0018\t\u0013\u0005\u0015U\u0005%AA\u0002\u0005%\u0005\"CAOKA\u0005\t\u0019AAE\u0011%\t\t+\nI\u0001\u0002\u0004\ti\u0006C\u0005\u0002&\u0016\u0002\n\u00111\u0001\u0002*\"I\u00111W\u0013\u0011\u0002\u0003\u0007\u0011q\u0017\u0005\n\u0003\u007f+\u0003\u0013!a\u0001\u0003;B\u0011\"a1&!\u0003\u0005\r!!\u0006\t\u0013\u0005\u001dW\u0005%AA\u0002\u0005-\u0017AD2paf$C-\u001a4bk2$H%M\u000b\u0003\u0005CQC!a\u0002\u0003$-\u0012!Q\u0005\t\u0005\u0005O\u0011\t$\u0004\u0002\u0003*)!!1\u0006B\u0017\u0003%)hn\u00195fG.,GMC\u0002\u000309\f!\"\u00198o_R\fG/[8o\u0013\u0011\u0011\u0019D!\u000b\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0016\u0005\te\"\u0006BA\u000b\u0005G\tabY8qs\u0012\"WMZ1vYR$3'\u0006\u0002\u0003@)\"\u0011\u0011\bB\u0012\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIQ*\"A!\u0012+\t\u0005\u0015#1E\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00136+\t\u0011YE\u000b\u0003\u0002^\t\r\u0012AD2paf$C-\u001a4bk2$HEN\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00138+\t\u0011\u0019F\u000b\u0003\u0002n\t\r\u0012AD2paf$C-\u001a4bk2$H\u0005O\u000b\u0003\u00053RC!!\u001f\u0003$\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012J\u0014aD2paf$C-\u001a4bk2$H%\r\u0019\u0016\u0005\t\u0005$\u0006BAE\u0005G\tqbY8qs\u0012\"WMZ1vYR$\u0013'M\u0001\u0010G>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132e\u0005y1m\u001c9zI\u0011,g-Y;mi\u0012\n4'\u0006\u0002\u0003l)\"\u0011\u0011\u0016B\u0012\u0003=\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE\"TC\u0001B9U\u0011\t9La\t\u0002\u001f\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\nqbY8qs\u0012\"WMZ1vYR$\u0013GN\u0001\u0010G>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132oU\u0011!1\u0010\u0016\u0005\u0003\u0017\u0014\u0019#A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0003\u0005\u0003\u0003BAa!\u0003\n6\u0011!Q\u0011\u0006\u0005\u0005\u000f\u000b\u0019*\u0001\u0003mC:<\u0017\u0002BA\u0011\u0005\u000b\u000bA\u0002\u001d:pIV\u001cG/\u0011:jif\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0003\u0003\u0012\n]\u0005cA7\u0003\u0014&\u0019!Q\u00138\u0003\u0007\u0005s\u0017\u0010C\u0005\u00022e\n\t\u00111\u0001\u0002:\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0003\u001eB1!q\u0014BS\u0005#k!A!)\u000b\u0007\t\rf.\u0001\u0006d_2dWm\u0019;j_:LAAa*\u0003\"\nA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\u0011\tiF!,\t\u0013\u0005E2(!AA\u0002\tE\u0015A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$BA!!\u00034\"I\u0011\u0011\u0007\u001f\u0002\u0002\u0003\u0007\u0011\u0011H\u0001\tQ\u0006\u001c\bnQ8eKR\u0011\u0011\u0011H\u0001\ti>\u001cFO]5oOR\u0011!\u0011Q\u0001\u0007KF,\u0018\r\\:\u0015\t\u0005u#\u0011\u0019\u0005\n\u0003cy\u0014\u0011!a\u0001\u0005#\u000b\u0001$\u00119qK:$gi\u001c:n\r&,G\u000eZ(qKJ\fG/[8o!\r\tI!Q\n\u0006\u0003\n%'Q\u001b\t)\u0005\u0017\u0014\t.a\u0002\u0002\u0016\u0005e\u0012QIA/\u0003;\ni'!\u001f\u0002^\u0005%\u0015\u0011RA/\u0003S\u000b9,!\u0018\u0002\u0016\u0005-\u00171[\u0007\u0003\u0005\u001bT1Aa4o\u0003\u001d\u0011XO\u001c;j[\u0016LAAa5\u0003N\n\u0011\u0012IY:ue\u0006\u001cGOR;oGRLwN\\\u00198!\u0011\u00119N!8\u000e\u0005\te'\u0002\u0002Bn\u0003'\u000b!![8\n\u0007}\u0014I\u000e\u0006\u0002\u0003F\u0006)\u0011\r\u001d9msR!\u00131\u001bBs\u0005O\u0014IOa;\u0003n\n=(\u0011\u001fBz\u0005k\u00149P!?\u0003|\nu(q`B\u0001\u0007\u0007\u0019)\u0001C\u0004\u0002\u0004\u0011\u0003\r!a\u0002\t\u000f\u0005EA\t1\u0001\u0002\u0016!9\u0011Q\u0007#A\u0002\u0005e\u0002bBA!\t\u0002\u0007\u0011Q\t\u0005\n\u00033\"\u0005\u0013!a\u0001\u0003;B\u0011\"!\u001aE!\u0003\u0005\r!!\u0018\t\u0013\u0005%D\t%AA\u0002\u00055\u0004\"CA;\tB\u0005\t\u0019AA=\u0011%\t\t\t\u0012I\u0001\u0002\u0004\ti\u0006C\u0005\u0002\u0006\u0012\u0003\n\u00111\u0001\u0002\n\"I\u0011Q\u0014#\u0011\u0002\u0003\u0007\u0011\u0011\u0012\u0005\n\u0003C#\u0005\u0013!a\u0001\u0003;B\u0011\"!*E!\u0003\u0005\r!!+\t\u0013\u0005MF\t%AA\u0002\u0005]\u0006\"CA`\tB\u0005\t\u0019AA/\u0011%\t\u0019\r\u0012I\u0001\u0002\u0004\t)\u0002C\u0005\u0002H\u0012\u0003\n\u00111\u0001\u0002L\u0006y\u0011\r\u001d9ms\u0012\"WMZ1vYR$S'A\bbaBd\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00137\u0003=\t\u0007\u000f\u001d7zI\u0011,g-Y;mi\u0012:\u0014aD1qa2LH\u0005Z3gCVdG\u000f\n\u001d\u0002\u001f\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uIe\n\u0001#\u00199qYf$C-\u001a4bk2$H%\r\u0019\u0002!\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uIE\n\u0014\u0001E1qa2LH\u0005Z3gCVdG\u000fJ\u00193\u0003A\t\u0007\u000f\u001d7zI\u0011,g-Y;mi\u0012\n4'\u0001\tbaBd\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00132i\u0005\u0001\u0012\r\u001d9ms\u0012\"WMZ1vYR$\u0013'N\u0001\u0011CB\u0004H.\u001f\u0013eK\u001a\fW\u000f\u001c;%cY\n\u0001#\u00199qYf$C-\u001a4bk2$H%M\u001c\u0002\u000fUt\u0017\r\u001d9msR!1QEB\u0017!\u0015i\u0017qNB\u0014!\u0015j7\u0011FA\u0004\u0003+\tI$!\u0012\u0002^\u0005u\u0013QNA=\u0003;\nI)!#\u0002^\u0005%\u0016qWA/\u0003+\tY-C\u0002\u0004,9\u0014q\u0001V;qY\u0016\ft\u0007C\u0005\u00040I\u000b\t\u00111\u0001\u0002T\u0006\u0019\u0001\u0010\n\u0019\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00136\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%m\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uI]\n1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012B\u0014a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$\u0013(\u0001\u000f%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$H%\r\u0019\u00029\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00132c\u0005aB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIE\u0012\u0014\u0001\b\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$\u0013gM\u0001\u001dI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000fJ\u00195\u0003q!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%cU\nA\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\nd'\u0001\u000f%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$H%M\u001c\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\r=\u0003\u0003\u0002BB\u0007#JAaa\u0015\u0003\u0006\n1qJ\u00196fGR\u0004")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/AppendFormFieldOperation.class */
public class AppendFormFieldOperation implements Product, Serializable {
    private final FormFieldType kind;
    private String name;
    private final int pageNumber;
    private final RectangularBox boundingBox;
    private final boolean multiline;
    private final boolean selected;
    private final Option<String> valueOpt;
    private final Seq<String> options;
    private final boolean multiselect;
    private final Option<Color> borderColor;
    private final Option<Color> color;
    private final boolean hasBorder;
    private final Option<Object> fontSizeOpt;
    private final FormFieldAlign align;
    private final boolean required;
    private final String id;
    private final Option<Object> combChars;

    public static Option<Tuple17<FormFieldType, String, Object, RectangularBox, Object, Object, Option<String>, Seq<String>, Object, Option<Color>, Option<Color>, Object, Option<Object>, FormFieldAlign, Object, String, Option<Object>>> unapply(final AppendFormFieldOperation x$0) {
        return AppendFormFieldOperation$.MODULE$.unapply(x$0);
    }

    public static AppendFormFieldOperation apply(final FormFieldType kind, final String name, final int pageNumber, final RectangularBox boundingBox, final boolean multiline, final boolean selected, final Option<String> valueOpt, final Seq<String> options, final boolean multiselect, final Option<Color> borderColor, final Option<Color> color, final boolean hasBorder, final Option<Object> fontSizeOpt, final FormFieldAlign align, final boolean required, final String id, final Option<Object> combChars) {
        return AppendFormFieldOperation$.MODULE$.apply(kind, name, pageNumber, boundingBox, multiline, selected, valueOpt, options, multiselect, borderColor, color, hasBorder, fontSizeOpt, align, required, id, combChars);
    }

    public static Function1<Tuple17<FormFieldType, String, Object, RectangularBox, Object, Object, Option<String>, Seq<String>, Object, Option<Color>, Option<Color>, Object, Option<Object>, FormFieldAlign, Object, String, Option<Object>>, AppendFormFieldOperation> tupled() {
        return AppendFormFieldOperation$.MODULE$.tupled();
    }

    public static Function1<FormFieldType, Function1<String, Function1<Object, Function1<RectangularBox, Function1<Object, Function1<Object, Function1<Option<String>, Function1<Seq<String>, Function1<Object, Function1<Option<Color>, Function1<Option<Color>, Function1<Object, Function1<Option<Object>, Function1<FormFieldAlign, Function1<Object, Function1<String, Function1<Option<Object>, AppendFormFieldOperation>>>>>>>>>>>>>>>>> curried() {
        return AppendFormFieldOperation$.MODULE$.curried();
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public FormFieldType kind() {
        return this.kind;
    }

    public AppendFormFieldOperation copy(final FormFieldType kind, final String name, final int pageNumber, final RectangularBox boundingBox, final boolean multiline, final boolean selected, final Option<String> valueOpt, final Seq<String> options, final boolean multiselect, final Option<Color> borderColor, final Option<Color> color, final boolean hasBorder, final Option<Object> fontSizeOpt, final FormFieldAlign align, final boolean required, final String id, final Option<Object> combChars) {
        return new AppendFormFieldOperation(kind, name, pageNumber, boundingBox, multiline, selected, valueOpt, options, multiselect, borderColor, color, hasBorder, fontSizeOpt, align, required, id, combChars);
    }

    public FormFieldType copy$default$1() {
        return kind();
    }

    public String productPrefix() {
        return "AppendFormFieldOperation";
    }

    public int productArity() {
        return 17;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return kind();
            case 1:
                return name();
            case 2:
                return BoxesRunTime.boxToInteger(pageNumber());
            case 3:
                return boundingBox();
            case 4:
                return BoxesRunTime.boxToBoolean(multiline());
            case 5:
                return BoxesRunTime.boxToBoolean(selected());
            case 6:
                return valueOpt();
            case 7:
                return options();
            case 8:
                return BoxesRunTime.boxToBoolean(multiselect());
            case 9:
                return borderColor();
            case 10:
                return color();
            case 11:
                return BoxesRunTime.boxToBoolean(hasBorder());
            case 12:
                return fontSizeOpt();
            case CharUtils.ASCII_CARRIAGE_RETURN /* 13 */:
                return align();
            case TIFFExtension.JPEG_PROC_LOSSLESS /* 14 */:
                return BoxesRunTime.boxToBoolean(required());
            case 15:
                return id();
            case PDAnnotation.FLAG_NO_ROTATE /* 16 */:
                return combChars();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof AppendFormFieldOperation;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "kind";
            case 1:
                return "name";
            case 2:
                return "pageNumber";
            case 3:
                return "boundingBox";
            case 4:
                return "multiline";
            case 5:
                return "selected";
            case 6:
                return "valueOpt";
            case 7:
                return "options";
            case 8:
                return "multiselect";
            case 9:
                return "borderColor";
            case 10:
                return "color";
            case 11:
                return "hasBorder";
            case 12:
                return "fontSizeOpt";
            case CharUtils.ASCII_CARRIAGE_RETURN /* 13 */:
                return "align";
            case TIFFExtension.JPEG_PROC_LOSSLESS /* 14 */:
                return "required";
            case 15:
                return "id";
            case PDAnnotation.FLAG_NO_ROTATE /* 16 */:
                return "combChars";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return Statics.finalizeHash(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(Statics.mix(-889275714, productPrefix().hashCode()), Statics.anyHash(kind())), Statics.anyHash(name())), pageNumber()), Statics.anyHash(boundingBox())), multiline() ? 1231 : 1237), selected() ? 1231 : 1237), Statics.anyHash(valueOpt())), Statics.anyHash(options())), multiselect() ? 1231 : 1237), Statics.anyHash(borderColor())), Statics.anyHash(color())), hasBorder() ? 1231 : 1237), Statics.anyHash(fontSizeOpt())), Statics.anyHash(align())), required() ? 1231 : 1237), Statics.anyHash(id())), Statics.anyHash(combChars())), 17);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof AppendFormFieldOperation) {
                AppendFormFieldOperation appendFormFieldOperation = (AppendFormFieldOperation) x$1;
                if (pageNumber() == appendFormFieldOperation.pageNumber() && multiline() == appendFormFieldOperation.multiline() && selected() == appendFormFieldOperation.selected() && multiselect() == appendFormFieldOperation.multiselect() && hasBorder() == appendFormFieldOperation.hasBorder() && required() == appendFormFieldOperation.required()) {
                    FormFieldType formFieldTypeKind = kind();
                    FormFieldType formFieldTypeKind2 = appendFormFieldOperation.kind();
                    if (formFieldTypeKind != null ? formFieldTypeKind.equals(formFieldTypeKind2) : formFieldTypeKind2 == null) {
                        String strName = name();
                        String strName2 = appendFormFieldOperation.name();
                        if (strName != null ? strName.equals(strName2) : strName2 == null) {
                            RectangularBox rectangularBoxBoundingBox = boundingBox();
                            RectangularBox rectangularBoxBoundingBox2 = appendFormFieldOperation.boundingBox();
                            if (rectangularBoxBoundingBox != null ? rectangularBoxBoundingBox.equals(rectangularBoxBoundingBox2) : rectangularBoxBoundingBox2 == null) {
                                Option<String> optionValueOpt = valueOpt();
                                Option<String> optionValueOpt2 = appendFormFieldOperation.valueOpt();
                                if (optionValueOpt != null ? optionValueOpt.equals(optionValueOpt2) : optionValueOpt2 == null) {
                                    Seq<String> seqOptions = options();
                                    Seq<String> seqOptions2 = appendFormFieldOperation.options();
                                    if (seqOptions != null ? seqOptions.equals(seqOptions2) : seqOptions2 == null) {
                                        Option<Color> optionBorderColor = borderColor();
                                        Option<Color> optionBorderColor2 = appendFormFieldOperation.borderColor();
                                        if (optionBorderColor != null ? optionBorderColor.equals(optionBorderColor2) : optionBorderColor2 == null) {
                                            Option<Color> optionColor = color();
                                            Option<Color> optionColor2 = appendFormFieldOperation.color();
                                            if (optionColor != null ? optionColor.equals(optionColor2) : optionColor2 == null) {
                                                Option<Object> optionFontSizeOpt = fontSizeOpt();
                                                Option<Object> optionFontSizeOpt2 = appendFormFieldOperation.fontSizeOpt();
                                                if (optionFontSizeOpt != null ? optionFontSizeOpt.equals(optionFontSizeOpt2) : optionFontSizeOpt2 == null) {
                                                    FormFieldAlign formFieldAlignAlign = align();
                                                    FormFieldAlign formFieldAlignAlign2 = appendFormFieldOperation.align();
                                                    if (formFieldAlignAlign != null ? formFieldAlignAlign.equals(formFieldAlignAlign2) : formFieldAlignAlign2 == null) {
                                                        String strId = id();
                                                        String strId2 = appendFormFieldOperation.id();
                                                        if (strId != null ? strId.equals(strId2) : strId2 == null) {
                                                            Option<Object> optionCombChars = combChars();
                                                            Option<Object> optionCombChars2 = appendFormFieldOperation.combChars();
                                                            if (optionCombChars != null ? optionCombChars.equals(optionCombChars2) : optionCombChars2 == null) {
                                                                if (appendFormFieldOperation.canEqual(this)) {
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

    public AppendFormFieldOperation(final FormFieldType kind, final String name, final int pageNumber, final RectangularBox boundingBox, final boolean multiline, final boolean selected, final Option<String> valueOpt, final Seq<String> options, final boolean multiselect, final Option<Color> borderColor, final Option<Color> color, final boolean hasBorder, final Option<Object> fontSizeOpt, final FormFieldAlign align, final boolean required, final String id, final Option<Object> combChars) {
        this.kind = kind;
        this.name = name;
        this.pageNumber = pageNumber;
        this.boundingBox = boundingBox;
        this.multiline = multiline;
        this.selected = selected;
        this.valueOpt = valueOpt;
        this.options = options;
        this.multiselect = multiselect;
        this.borderColor = borderColor;
        this.color = color;
        this.hasBorder = hasBorder;
        this.fontSizeOpt = fontSizeOpt;
        this.align = align;
        this.required = required;
        this.id = id;
        this.combChars = combChars;
        Product.$init$(this);
    }

    public String name() {
        return this.name;
    }

    public void name_$eq(final String x$1) {
        this.name = x$1;
    }

    public String copy$default$2() {
        return name();
    }

    public int pageNumber() {
        return this.pageNumber;
    }

    public int copy$default$3() {
        return pageNumber();
    }

    public RectangularBox boundingBox() {
        return this.boundingBox;
    }

    public RectangularBox copy$default$4() {
        return boundingBox();
    }

    public boolean multiline() {
        return this.multiline;
    }

    public boolean copy$default$5() {
        return multiline();
    }

    public boolean selected() {
        return this.selected;
    }

    public boolean copy$default$6() {
        return selected();
    }

    public Option<String> valueOpt() {
        return this.valueOpt;
    }

    public Option<String> copy$default$7() {
        return valueOpt();
    }

    public Seq<String> options() {
        return this.options;
    }

    public Seq<String> copy$default$8() {
        return options();
    }

    public boolean multiselect() {
        return this.multiselect;
    }

    public boolean copy$default$9() {
        return multiselect();
    }

    public Option<Color> borderColor() {
        return this.borderColor;
    }

    public Option<Color> copy$default$10() {
        return borderColor();
    }

    public Option<Color> color() {
        return this.color;
    }

    public Option<Color> copy$default$11() {
        return color();
    }

    public boolean hasBorder() {
        return this.hasBorder;
    }

    public boolean copy$default$12() {
        return hasBorder();
    }

    public Option<Object> fontSizeOpt() {
        return this.fontSizeOpt;
    }

    public Option<Object> copy$default$13() {
        return fontSizeOpt();
    }

    public FormFieldAlign align() {
        return this.align;
    }

    public FormFieldAlign copy$default$14() {
        return align();
    }

    public boolean required() {
        return this.required;
    }

    public boolean copy$default$15() {
        return required();
    }

    public String id() {
        return this.id;
    }

    public String copy$default$16() {
        return id();
    }

    public Option<Object> combChars() {
        return this.combChars;
    }

    public Option<Object> copy$default$17() {
        return combChars();
    }
}
