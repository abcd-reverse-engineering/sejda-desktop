package code.util.ratelimit;

import code.util.JsonExtract$;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import java.io.File;
import net.liftweb.json.JsonAST;
import net.liftweb.json.JsonDSL$;
import net.liftweb.json.package$;
import scala.Option;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Tuple2;
import scala.collection.StringOps$;
import scala.collection.immutable.$colon;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.NonLocalReturnControl;
import scala.util.Try$;
import scala.util.control.NonFatal$;

/* compiled from: RateLimiting.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/ratelimit/DiskPersistentMeters$.class */
public final class DiskPersistentMeters$ {
    public static final DiskPersistentMeters$ MODULE$ = new DiskPersistentMeters$();
    private static final List<String> locations = new $colon.colon(System.getenv("LOCALAPPDATA"), new $colon.colon(System.getenv("APPDATA"), new $colon.colon(System.getenv("TMPDIR"), new $colon.colon(System.getenv("TEMP"), new $colon.colon(System.getProperty("user.home"), Nil$.MODULE$)))));

    private DiskPersistentMeters$() {
    }

    public List<String> locations() {
        return locations;
    }

    public List<File> existingLocations() {
        return locations().filter(x$1 -> {
            return BoxesRunTime.boxToBoolean($anonfun$existingLocations$1(x$1));
        }).filter(x$2 -> {
            return BoxesRunTime.boxToBoolean($anonfun$existingLocations$2(x$2));
        }).map(path -> {
            return new File(path);
        }).filter(x$3 -> {
            return BoxesRunTime.boxToBoolean(x$3.exists());
        }).filter(x$4 -> {
            return BoxesRunTime.boxToBoolean(x$4.isDirectory());
        });
    }

    public static final /* synthetic */ boolean $anonfun$existingLocations$1(final String x$1) {
        return x$1 != null;
    }

    public static final /* synthetic */ boolean $anonfun$existingLocations$2(final String x$2) {
        return StringOps$.MODULE$.nonEmpty$extension(Predef$.MODULE$.augmentString(x$2.trim()));
    }

    public Option<Tuple2<Object, Object>> load(final Meter meter) {
        List values = existingLocations().flatMap(location -> {
            return Try$.MODULE$.apply(() -> {
                JsonAST.JValue json = package$.MODULE$.parse(Files.toString(new File(location, meter.name()), Charsets.UTF_8));
                int remainingReq = JsonExtract$.MODULE$.toInt(json.$bslash("rr"));
                long nextReset = JsonExtract$.MODULE$.toLong(json.$bslash("nr"));
                return new Tuple2.mcIJ.sp(remainingReq, nextReset);
            }).toOption();
        });
        return values.headOption();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.runtime.NonLocalReturnControl */
    public boolean save(final Meter meter) throws NonLocalReturnControl {
        Object obj = new Object();
        try {
            String s = package$.MODULE$.compactRender(JsonDSL$.MODULE$.pair2Assoc(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("rr"), BoxesRunTime.boxToInteger(meter.remainingRequests())), x -> {
                return $anonfun$save$1(BoxesRunTime.unboxToInt(x));
            }).$tilde(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("nr"), BoxesRunTime.boxToLong(meter.nextReset())), x2 -> {
                return $anonfun$save$2(BoxesRunTime.unboxToLong(x2));
            }));
            existingLocations().foreach(location -> {
                $anonfun$save$3(meter, s, obj, location);
                return BoxedUnit.UNIT;
            });
            return false;
        } catch (NonLocalReturnControl ex) {
            if (ex.key() == obj) {
                return ex.value$mcZ$sp();
            }
            throw ex;
        }
    }

    public static final /* synthetic */ JsonAST.JInt $anonfun$save$1(final int x) {
        return JsonDSL$.MODULE$.int2jvalue(x);
    }

    public static final /* synthetic */ JsonAST.JInt $anonfun$save$2(final long x) {
        return JsonDSL$.MODULE$.long2jvalue(x);
    }

    public static final /* synthetic */ void $anonfun$save$3(final Meter meter$2, final String s$1, final Object nonLocalReturnKey1$1, final File location) {
        try {
            File file = new File(location, meter$2.name());
            Files.write(s$1, file, Charsets.UTF_8);
            throw new NonLocalReturnControl.mcZ.sp(nonLocalReturnKey1$1, true);
        } catch (Throwable th) {
            if (!NonFatal$.MODULE$.apply(th)) {
                throw th;
            }
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
        }
    }

    public void reset(final Meter meter) {
        existingLocations().foreach(location -> {
            return BoxesRunTime.boxToBoolean($anonfun$reset$1(meter, location));
        });
        meter.remainingRequests_$eq(meter.cfg().maxRequests());
        meter.nextReset_$eq(System.currentTimeMillis() + meter.cfg().duration().toMillis());
    }

    public static final /* synthetic */ boolean $anonfun$reset$1(final Meter meter$3, final File location) {
        return new File(location, meter$3.name()).delete();
    }
}
