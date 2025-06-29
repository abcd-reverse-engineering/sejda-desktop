package code.util;

import net.liftweb.json.JsonAST;
import org.joda.time.DateTime;
import scala.Function1;
import scala.Option;
import scala.collection.immutable.List;
import scala.collection.immutable.Map;
import scala.reflect.ScalaSignature;

/* compiled from: JsonExtract.scala */
@ScalaSignature(bytes = "\u0006\u0005\t\u0015s!B\u0013'\u0011\u0003Yc!B\u0017'\u0011\u0003q\u0003\"B\u001b\u0002\t\u00031\u0004\"B\u001c\u0002\t\u0003A\u0004\"\u0002+\u0002\t\u0003)\u0006\"B-\u0002\t\u0003Q\u0006\"B0\u0002\t\u0003\u0001\u0007\"B3\u0002\t\u00031\u0007\"B5\u0002\t\u0003Q\u0007\"B7\u0002\t\u0003q\u0007\"B:\u0002\t\u0003!\b\"B<\u0002\t\u0003A\b\"B>\u0002\t\u0003a\bbBA\u0007\u0003\u0011\u0005\u0011q\u0002\u0005\b\u0003+\tA\u0011AA\f\u0011\u001d\ti\"\u0001C\u0001\u0003?Aq!a\t\u0002\t\u0003\t)\u0003C\u0004\u00020\u0005!\t!!\r\t\u000f\u0005e\u0012\u0001\"\u0001\u0002<!9\u0011\u0011I\u0001\u0005\u0002\u0005\r\u0003bBA.\u0003\u0011\u0005\u0011Q\f\u0005\b\u0003G\nA\u0011AA3\u0011\u001d\tY'\u0001C\u0001\u0003[Bq!a)\u0002\t\u0003\t)\u000bC\u0004\u00028\u0006!\t!!/\t\u000f\u0005-\u0017\u0001\"\u0001\u0002N\"9\u00111[\u0001\u0005\u0002\u0005U\u0007bBAn\u0003\u0011\u0005\u0011Q\u001c\u0005\b\u0003G\fA\u0011AAs\u0011\u001d\tY/\u0001C\u0001\u0003[Dq!a=\u0002\t\u0003\t)\u0010C\u0004\u0002|\u0006!\t!!@\t\u000f\t\r\u0011\u0001\"\u0001\u0003\u0006!9!\u0011D\u0001\u0005\u0002\tm\u0001b\u0002B\u0017\u0003\u0011\u0005!q\u0006\u0005\b\u0005o\tA\u0011\u0001B\u001d\u0011\u001d\u0011y$\u0001C\u0001\u0005\u0003\n1BS:p]\u0016CHO]1di*\u0011q\u0005K\u0001\u0005kRLGNC\u0001*\u0003\u0011\u0019w\u000eZ3\u0004\u0001A\u0011A&A\u0007\u0002M\tY!j]8o\u000bb$(/Y2u'\t\tq\u0006\u0005\u00021g5\t\u0011GC\u00013\u0003\u0015\u00198-\u00197b\u0013\t!\u0014G\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003-\nQ\u0001^8J]R$\"!\u000f\u001f\u0011\u0005AR\u0014BA\u001e2\u0005\rIe\u000e\u001e\u0005\u0006{\r\u0001\rAP\u0001\u0002mB\u0011q(\u0015\b\u0003\u0001:s!!Q&\u000f\u0005\tCeBA\"G\u001b\u0005!%BA#+\u0003\u0019a$o\\8u}%\tq)A\u0002oKRL!!\u0013&\u0002\u000f1Lg\r^<fE*\tq)\u0003\u0002M\u001b\u0006!!n]8o\u0015\tI%*\u0003\u0002P!\u00069\u0001/Y2lC\u001e,'B\u0001'N\u0013\t\u00116K\u0001\u0004K-\u0006dW/\u001a\u0006\u0003\u001fB\u000bq\u0001^8J]R|%\u000fF\u0002:-^CQ!\u0010\u0003A\u0002yBQ\u0001\u0017\u0003A\u0002e\n\u0011\u0001Z\u0001\fi>Le\u000e^(qi&|g\u000e\u0006\u0002\\=B\u0019\u0001\u0007X\u001d\n\u0005u\u000b$AB(qi&|g\u000eC\u0003>\u000b\u0001\u0007a(\u0001\u0004u_2{gn\u001a\u000b\u0003C\u0012\u0004\"\u0001\r2\n\u0005\r\f$\u0001\u0002'p]\u001eDQ!\u0010\u0004A\u0002y\n\u0001\u0002^8M_:<wJ\u001d\u000b\u0004C\u001eD\u0007\"B\u001f\b\u0001\u0004q\u0004\"\u0002-\b\u0001\u0004\t\u0017\u0001\u0004;p\u0019>twm\u00149uS>tGCA6m!\r\u0001D,\u0019\u0005\u0006{!\u0001\rAP\u0001\ti>$u.\u001e2mKR\u0011qN\u001d\t\u0003aAL!!]\u0019\u0003\r\u0011{WO\u00197f\u0011\u0015i\u0014\u00021\u0001?\u0003)!x\u000eR8vE2,wJ\u001d\u000b\u0004_V4\b\"B\u001f\u000b\u0001\u0004q\u0004\"\u0002-\u000b\u0001\u0004y\u0017A\u0004;p\t>,(\r\\3PaRLwN\u001c\u000b\u0003sj\u00042\u0001\r/p\u0011\u0015i4\u00021\u0001?\u0003!!xn\u0015;sS:<GcA?\u0002\fA\u0019a0!\u0002\u000f\u0007}\f\t\u0001\u0005\u0002Dc%\u0019\u00111A\u0019\u0002\rA\u0013X\rZ3g\u0013\u0011\t9!!\u0003\u0003\rM#(/\u001b8h\u0015\r\t\u0019!\r\u0005\u0006{1\u0001\rAP\u0001\u000bi>\u001cFO]5oO>\u0013H#B?\u0002\u0012\u0005M\u0001\"B\u001f\u000e\u0001\u0004q\u0004\"\u0002-\u000e\u0001\u0004i\u0018A\u0004;p'R\u0014\u0018N\\4PaRLwN\u001c\u000b\u0005\u00033\tY\u0002E\u000219vDQ!\u0010\bA\u0002y\n\u0011\u0004^8TiJLgnZ(qi&|gNT8oK&3'\t\\1oWR!\u0011\u0011DA\u0011\u0011\u0015it\u00021\u0001?\u0003\u0019!xNQ8pYR!\u0011qEA\u0017!\r\u0001\u0014\u0011F\u0005\u0004\u0003W\t$a\u0002\"p_2,\u0017M\u001c\u0005\u0006{A\u0001\rAP\u0001\ti>\u0014un\u001c7PeR1\u0011qEA\u001a\u0003kAQ!P\tA\u0002yBq!a\u000e\u0012\u0001\u0004\t9#A\u0004eK\u001a\fW\u000f\u001c;\u0002\u0019Q|'i\\8m\u001fB$\u0018n\u001c8\u0015\t\u0005u\u0012q\b\t\u0005aq\u000b9\u0003C\u0003>%\u0001\u0007a(A\u0006u_RKW.Z:uC6\u0004H\u0003BA#\u00033\u0002B!a\u0012\u0002V5\u0011\u0011\u0011\n\u0006\u0005\u0003\u0017\ni%\u0001\u0003uS6,'\u0002BA(\u0003#\nAA[8eC*\u0011\u00111K\u0001\u0004_J<\u0017\u0002BA,\u0003\u0013\u0012\u0001\u0002R1uKRKW.\u001a\u0005\u0006{M\u0001\rAP\u0001\u000ei>$\u0016.\\3ti\u0006l\u0007o\u0014:\u0015\r\u0005\u0015\u0013qLA1\u0011\u0015iD\u00031\u0001?\u0011\u0019AF\u00031\u0001\u0002F\u0005\tBo\u001c+j[\u0016\u001cH/Y7q\u001fB$\u0018n\u001c8\u0015\t\u0005\u001d\u0014\u0011\u000e\t\u0005aq\u000b)\u0005C\u0003>+\u0001\u0007a(\u0001\u0004u_2K7\u000f^\u000b\u0005\u0003_\n)\t\u0006\u0004\u0002r\u0005]\u0015\u0011\u0014\t\u0007\u0003g\nY(!!\u000f\t\u0005U\u0014\u0011\u0010\b\u0004\u0007\u0006]\u0014\"\u0001\u001a\n\u0005=\u000b\u0014\u0002BA?\u0003\u007f\u0012A\u0001T5ti*\u0011q*\r\t\u0005\u0003\u0007\u000b)\t\u0004\u0001\u0005\u000f\u0005\u001deC1\u0001\u0002\n\n\tA+\u0005\u0003\u0002\f\u0006E\u0005c\u0001\u0019\u0002\u000e&\u0019\u0011qR\u0019\u0003\u000f9{G\u000f[5oOB\u0019\u0001'a%\n\u0007\u0005U\u0015GA\u0002B]fDQ!\u0010\fA\u0002yBq!a'\u0017\u0001\u0004\ti*\u0001\u0005ge>l'j]8o!\u0019\u0001\u0014q\u0014 \u0002\u0002&\u0019\u0011\u0011U\u0019\u0003\u0013\u0019+hn\u0019;j_:\f\u0014\u0001\u0004;p\u0019&\u001cHo\u00149uS>tW\u0003BAT\u0003_#b!!+\u00022\u0006M\u0006\u0003\u0002\u0019]\u0003W\u0003b!a\u001d\u0002|\u00055\u0006\u0003BAB\u0003_#q!a\"\u0018\u0005\u0004\tI\tC\u0003>/\u0001\u0007a\bC\u0004\u0002\u001c^\u0001\r!!.\u0011\rA\nyJPAW\u0003!!x\u000eT5ti>\u0013X\u0003BA^\u0003\u0003$\u0002\"!0\u0002D\u0006\u0015\u0017\u0011\u001a\t\u0007\u0003g\nY(a0\u0011\t\u0005\r\u0015\u0011\u0019\u0003\b\u0003\u000fC\"\u0019AAE\u0011\u0015i\u0004\u00041\u0001?\u0011\u001d\tY\n\u0007a\u0001\u0003\u000f\u0004b\u0001MAP}\u0005}\u0006bBA\u001c1\u0001\u0007\u0011QX\u0001\ri>\u001cFO]5oO2K7\u000f\u001e\u000b\u0005\u0003\u001f\f\t\u000eE\u0003\u0002t\u0005mT\u0010C\u0003>3\u0001\u0007a(A\bu_N#(/\u001b8h\u0019&\u001cHo\u00149u)\u0011\t9.!7\u0011\tAb\u0016q\u001a\u0005\u0006{i\u0001\rAP\u0001\u000fi>\u001cFO]5oO2K7\u000f^(s)\u0019\ty-a8\u0002b\")Qh\u0007a\u0001}!9\u0011qG\u000eA\u0002\u0005=\u0017!\u0003;p\u0013:$H*[:u)\u0011\t9/!;\u0011\u000b\u0005M\u00141P\u001d\t\u000bub\u0002\u0019\u0001 \u0002\u0017Q|\u0017J\u001c;Pe2K7\u000f\u001e\u000b\u0007\u0003O\fy/!=\t\u000buj\u0002\u0019\u0001 \t\r\u0005]R\u00041\u0001:\u0003)!xNQ8pY2K7\u000f\u001e\u000b\u0005\u0003o\fI\u0010\u0005\u0004\u0002t\u0005m\u0014q\u0005\u0005\u0006{y\u0001\rAP\u0001\u0010i>$\u0016.\\3ti\u0006l\u0007\u000fT5tiR!\u0011q B\u0001!\u0019\t\u0019(a\u001f\u0002F!)Qh\ba\u0001}\u0005)Ao\\'baV!!q\u0001B\t)\u0019\u0011IAa\u0005\u0003\u0016A1aPa\u0003~\u0005\u001fIAA!\u0004\u0002\n\t\u0019Q*\u00199\u0011\t\u0005\r%\u0011\u0003\u0003\b\u0003\u000f\u0003#\u0019AAE\u0011\u0015i\u0004\u00051\u0001?\u0011\u001d\tY\n\ta\u0001\u0005/\u0001b\u0001MAP}\t=\u0011a\u0003;p\u001b\u0006\u0004x\n\u001d;j_:,BA!\b\u0003&Q1!q\u0004B\u0014\u0005S\u0001B\u0001\r/\u0003\"A1aPa\u0003~\u0005G\u0001B!a!\u0003&\u00119\u0011qQ\u0011C\u0002\u0005%\u0005\"B\u001f\"\u0001\u0004q\u0004bBANC\u0001\u0007!1\u0006\t\u0007a\u0005}eHa\t\u0002\u000b\u0005\u001cX*\u00199\u0015\u0007y\u0012\t\u0004C\u0004\u00034\t\u0002\rA!\u000e\u0002\u00035\u0004RA B\u0006{v\f!\"Y:NCB|e-\u00138u)\rq$1\b\u0005\b\u0005g\u0019\u0003\u0019\u0001B\u001f!\u0015q(1B?:\u0003E\u0019\u0018MZ3D_6\u0004\u0018m\u0019;SK:$WM\u001d\u000b\u0004{\n\r\u0003\"\u0002'%\u0001\u0004q\u0004")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/JsonExtract.class */
public final class JsonExtract {
    public static String safeCompactRender(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.safeCompactRender(json);
    }

    public static JsonAST.JValue asMapOfInt(final Map<String, Object> m) {
        return JsonExtract$.MODULE$.asMapOfInt(m);
    }

    public static JsonAST.JValue asMap(final Map<String, String> m) {
        return JsonExtract$.MODULE$.asMap(m);
    }

    public static <T> Option<Map<String, T>> toMapOption(final JsonAST.JValue v, final Function1<JsonAST.JValue, T> fromJson) {
        return JsonExtract$.MODULE$.toMapOption(v, fromJson);
    }

    public static <T> Map<String, T> toMap(final JsonAST.JValue v, final Function1<JsonAST.JValue, T> fromJson) {
        return JsonExtract$.MODULE$.toMap(v, fromJson);
    }

    public static List<DateTime> toTimestampList(final JsonAST.JValue v) {
        return JsonExtract$.MODULE$.toTimestampList(v);
    }

    public static List<Object> toBoolList(final JsonAST.JValue v) {
        return JsonExtract$.MODULE$.toBoolList(v);
    }

    public static List<Object> toIntOrList(final JsonAST.JValue v, int i) {
        return JsonExtract$.MODULE$.toIntOrList(v, i);
    }

    public static List<Object> toIntList(final JsonAST.JValue v) {
        return JsonExtract$.MODULE$.toIntList(v);
    }

    public static List<String> toStringListOr(final JsonAST.JValue v, List<String> list) {
        return JsonExtract$.MODULE$.toStringListOr(v, list);
    }

    public static Option<List<String>> toStringListOpt(final JsonAST.JValue v) {
        return JsonExtract$.MODULE$.toStringListOpt(v);
    }

    public static List<String> toStringList(final JsonAST.JValue v) {
        return JsonExtract$.MODULE$.toStringList(v);
    }

    public static <T> List<T> toListOr(final JsonAST.JValue v, final Function1<JsonAST.JValue, T> fromJson, List<T> list) {
        return JsonExtract$.MODULE$.toListOr(v, fromJson, list);
    }

    public static <T> Option<List<T>> toListOption(final JsonAST.JValue v, final Function1<JsonAST.JValue, T> fromJson) {
        return JsonExtract$.MODULE$.toListOption(v, fromJson);
    }

    public static <T> List<T> toList(final JsonAST.JValue v, final Function1<JsonAST.JValue, T> fromJson) {
        return JsonExtract$.MODULE$.toList(v, fromJson);
    }

    public static Option<DateTime> toTimestampOption(final JsonAST.JValue v) {
        return JsonExtract$.MODULE$.toTimestampOption(v);
    }

    public static DateTime toTimestampOr(final JsonAST.JValue v, final DateTime d) {
        return JsonExtract$.MODULE$.toTimestampOr(v, d);
    }

    public static DateTime toTimestamp(final JsonAST.JValue v) {
        return JsonExtract$.MODULE$.toTimestamp(v);
    }

    public static Option<Object> toBoolOption(final JsonAST.JValue v) {
        return JsonExtract$.MODULE$.toBoolOption(v);
    }

    public static boolean toBoolOr(final JsonAST.JValue v, boolean z) {
        return JsonExtract$.MODULE$.toBoolOr(v, z);
    }

    public static boolean toBool(final JsonAST.JValue v) {
        return JsonExtract$.MODULE$.toBool(v);
    }

    public static Option<String> toStringOptionNoneIfBlank(final JsonAST.JValue v) {
        return JsonExtract$.MODULE$.toStringOptionNoneIfBlank(v);
    }

    public static Option<String> toStringOption(final JsonAST.JValue v) {
        return JsonExtract$.MODULE$.toStringOption(v);
    }

    public static String toStringOr(final JsonAST.JValue v, final String d) {
        return JsonExtract$.MODULE$.toStringOr(v, d);
    }

    public static String toString(final JsonAST.JValue v) {
        return JsonExtract$.MODULE$.toString(v);
    }

    public static Option<Object> toDoubleOption(final JsonAST.JValue v) {
        return JsonExtract$.MODULE$.toDoubleOption(v);
    }

    public static double toDoubleOr(final JsonAST.JValue v, final double d) {
        return JsonExtract$.MODULE$.toDoubleOr(v, d);
    }

    public static double toDouble(final JsonAST.JValue v) {
        return JsonExtract$.MODULE$.toDouble(v);
    }

    public static Option<Object> toLongOption(final JsonAST.JValue v) {
        return JsonExtract$.MODULE$.toLongOption(v);
    }

    public static long toLongOr(final JsonAST.JValue v, final long d) {
        return JsonExtract$.MODULE$.toLongOr(v, d);
    }

    public static long toLong(final JsonAST.JValue v) {
        return JsonExtract$.MODULE$.toLong(v);
    }

    public static Option<Object> toIntOption(final JsonAST.JValue v) {
        return JsonExtract$.MODULE$.toIntOption(v);
    }

    public static int toIntOr(final JsonAST.JValue v, final int d) {
        return JsonExtract$.MODULE$.toIntOr(v, d);
    }

    public static int toInt(final JsonAST.JValue v) {
        return JsonExtract$.MODULE$.toInt(v);
    }
}
