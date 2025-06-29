package code.util;

import net.liftweb.json.JsonAST;
import net.liftweb.json.JsonAST$JNothing$;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import scala.$less$colon$less$;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Some;
import scala.Tuple2;
import scala.collection.IterableOnceOps;
import scala.collection.StringOps$;
import scala.collection.immutable.List;
import scala.collection.immutable.Map;
import scala.math.BigInt;
import scala.math.BigInt$;
import scala.package$;
import scala.runtime.BoxesRunTime;
import scala.util.Try$;

/* compiled from: JsonExtract.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/JsonExtract$.class */
public final class JsonExtract$ {
    public static final JsonExtract$ MODULE$ = new JsonExtract$();

    private JsonExtract$() {
    }

    public int toInt(final JsonAST.JValue v) {
        return BoxesRunTime.unboxToInt(toIntOption(v).get());
    }

    public int toIntOr(final JsonAST.JValue v, final int d) {
        return BoxesRunTime.unboxToInt(toIntOption(v).getOrElse(() -> {
            return d;
        }));
    }

    public Option<Object> toIntOption(final JsonAST.JValue v) {
        if (v instanceof JsonAST.JInt) {
            BigInt i = ((JsonAST.JInt) v).num();
            return new Some(BoxesRunTime.boxToInteger(i.toInt()));
        }
        if (v instanceof JsonAST.JDouble) {
            double d = ((JsonAST.JDouble) v).num();
            return new Some(BoxesRunTime.boxToInteger((int) d));
        }
        if (v instanceof JsonAST.JString) {
            String s = ((JsonAST.JString) v).s();
            return Try$.MODULE$.apply(() -> {
                return (int) StringOps$.MODULE$.toDouble$extension(Predef$.MODULE$.augmentString(s));
            }).toOption();
        }
        return None$.MODULE$;
    }

    public long toLong(final JsonAST.JValue v) {
        return BoxesRunTime.unboxToLong(toLongOption(v).get());
    }

    public long toLongOr(final JsonAST.JValue v, final long d) {
        return BoxesRunTime.unboxToLong(toLongOption(v).getOrElse(() -> {
            return d;
        }));
    }

    public Option<Object> toLongOption(final JsonAST.JValue v) {
        if (v instanceof JsonAST.JInt) {
            BigInt i = ((JsonAST.JInt) v).num();
            return new Some(BoxesRunTime.boxToLong(i.toLong()));
        }
        if (v instanceof JsonAST.JString) {
            String s = ((JsonAST.JString) v).s();
            return Try$.MODULE$.apply(() -> {
                return StringOps$.MODULE$.toLong$extension(Predef$.MODULE$.augmentString(s));
            }).toOption();
        }
        return None$.MODULE$;
    }

    public double toDouble(final JsonAST.JValue v) {
        return BoxesRunTime.unboxToDouble(toDoubleOption(v).get());
    }

    public double toDoubleOr(final JsonAST.JValue v, final double d) {
        return BoxesRunTime.unboxToDouble(toDoubleOption(v).getOrElse(() -> {
            return d;
        }));
    }

    public Option<Object> toDoubleOption(final JsonAST.JValue v) {
        if (v instanceof JsonAST.JDouble) {
            double i = ((JsonAST.JDouble) v).num();
            return new Some(BoxesRunTime.boxToDouble(i));
        }
        if (v instanceof JsonAST.JInt) {
            BigInt i2 = ((JsonAST.JInt) v).num();
            return new Some(BoxesRunTime.boxToDouble(i2.toDouble()));
        }
        if (v instanceof JsonAST.JString) {
            String s = ((JsonAST.JString) v).s();
            return Try$.MODULE$.apply(() -> {
                return StringOps$.MODULE$.toDouble$extension(Predef$.MODULE$.augmentString(s));
            }).toOption();
        }
        return None$.MODULE$;
    }

    public String toString(final JsonAST.JValue v) {
        return (String) toStringOption(v).get();
    }

    public String toStringOr(final JsonAST.JValue v, final String d) {
        return (String) toStringOption(v).getOrElse(() -> {
            return d;
        });
    }

    public Option<String> toStringOption(final JsonAST.JValue v) {
        if (v instanceof JsonAST.JString) {
            String s = ((JsonAST.JString) v).s();
            return new Some(s);
        }
        if (v instanceof JsonAST.JInt) {
            BigInt i = ((JsonAST.JInt) v).num();
            return new Some(i.toString());
        }
        if (v instanceof JsonAST.JDouble) {
            double d = ((JsonAST.JDouble) v).num();
            return new Some(Double.toString(d));
        }
        if (v instanceof JsonAST.JBool) {
            boolean b = ((JsonAST.JBool) v).value();
            return new Some(Boolean.toString(b));
        }
        return None$.MODULE$;
    }

    public Option<String> toStringOptionNoneIfBlank(final JsonAST.JValue v) {
        return toStringOption(v).flatMap(s -> {
            return s.trim().isEmpty() ? None$.MODULE$ : new Some(s);
        });
    }

    public boolean toBool(final JsonAST.JValue v) {
        return BoxesRunTime.unboxToBoolean(toBoolOption(v).get());
    }

    public boolean toBoolOr(final JsonAST.JValue v, boolean z) {
        return BoxesRunTime.unboxToBoolean(toBoolOption(v).getOrElse(() -> {
            return z;
        }));
    }

    public Option<Object> toBoolOption(final JsonAST.JValue v) {
        if (v instanceof JsonAST.JBool) {
            return new Some(BoxesRunTime.boxToBoolean(((JsonAST.JBool) v).value()));
        }
        if (v instanceof JsonAST.JString) {
            String b = ((JsonAST.JString) v).s();
            if (b != null ? !b.equals("true") : "true" != 0) {
                if (b != null) {
                }
            }
            return new Some(BoxesRunTime.boxToBoolean(b != null ? b.equals("true") : "true" == 0));
        }
        return None$.MODULE$;
    }

    public DateTime toTimestamp(final JsonAST.JValue v) {
        return (DateTime) toTimestampOption(v).get();
    }

    public DateTime toTimestampOr(final JsonAST.JValue v, final DateTime d) {
        return (DateTime) toTimestampOption(v).getOrElse(() -> {
            return d;
        });
    }

    public Option<DateTime> toTimestampOption(final JsonAST.JValue v) {
        if (v instanceof JsonAST.JString) {
            String s = ((JsonAST.JString) v).s();
            return new Some(new DateTime(s, DateTimeZone.UTC));
        }
        return None$.MODULE$;
    }

    public <T> List<T> toList(final JsonAST.JValue v, final Function1<JsonAST.JValue, T> fromJson) {
        return (List) toListOption(v, fromJson).get();
    }

    public <T> Option<List<T>> toListOption(final JsonAST.JValue v, final Function1<JsonAST.JValue, T> fromJson) {
        if (v instanceof JsonAST.JArray) {
            List l = ((JsonAST.JArray) v).arr();
            return new Some(l.map(x$1 -> {
                return fromJson.apply(x$1);
            }));
        }
        return None$.MODULE$;
    }

    public <T> List<T> toListOr(final JsonAST.JValue v, final Function1<JsonAST.JValue, T> fromJson, List<T> list) {
        return (List) toListOption(v, fromJson).getOrElse(() -> {
            return list;
        });
    }

    public List<String> toStringList(final JsonAST.JValue v) {
        return toList(v, v2 -> {
            return MODULE$.toString(v2);
        });
    }

    public Option<List<String>> toStringListOpt(final JsonAST.JValue v) {
        return toListOption(v, v2 -> {
            return MODULE$.toString(v2);
        });
    }

    public List<String> toStringListOr(final JsonAST.JValue v, List<String> list) {
        return (List) toListOption(v, v2 -> {
            return MODULE$.toString(v2);
        }).getOrElse(() -> {
            return list;
        });
    }

    public List<Object> toIntList(final JsonAST.JValue v) {
        return toList(v, v2 -> {
            return BoxesRunTime.boxToInteger($anonfun$toIntList$1(v2));
        });
    }

    public static final /* synthetic */ int $anonfun$toIntList$1(final JsonAST.JValue v) {
        return MODULE$.toInt(v);
    }

    public List<Object> toIntOrList(final JsonAST.JValue v, int i) {
        return toList(v, json -> {
            return BoxesRunTime.boxToInteger($anonfun$toIntOrList$1(i, json));
        });
    }

    public static final /* synthetic */ int $anonfun$toIntOrList$1(final int default$4, final JsonAST.JValue json) {
        return MODULE$.toIntOr(json, default$4);
    }

    public List<Object> toBoolList(final JsonAST.JValue v) {
        return (List) toListOption(v, v2 -> {
            return BoxesRunTime.boxToBoolean($anonfun$toBoolList$1(v2));
        }).getOrElse(() -> {
            return package$.MODULE$.List().empty();
        });
    }

    public static final /* synthetic */ boolean $anonfun$toBoolList$1(final JsonAST.JValue v) {
        return MODULE$.toBool(v);
    }

    public List<DateTime> toTimestampList(final JsonAST.JValue v) {
        return toList(v, v2 -> {
            return MODULE$.toTimestamp(v2);
        });
    }

    public <T> Map<String, T> toMap(final JsonAST.JValue v, final Function1<JsonAST.JValue, T> fromJson) {
        return (Map) toMapOption(v, fromJson).get();
    }

    public <T> Option<Map<String, T>> toMapOption(final JsonAST.JValue v, final Function1<JsonAST.JValue, T> fromJson) {
        if (v instanceof JsonAST.JObject) {
            List fields = ((JsonAST.JObject) v).obj();
            return new Some(fields.map(f -> {
                return new Tuple2(f.name(), fromJson.apply(f.value()));
            }).toMap($less$colon$less$.MODULE$.refl()));
        }
        return None$.MODULE$;
    }

    public JsonAST.JValue asMap(final Map<String, String> m) {
        return new JsonAST.JObject(((IterableOnceOps) m.map(x0$1 -> {
            if (x0$1 != null) {
                String k = (String) x0$1._1();
                String v = (String) x0$1._2();
                return new JsonAST.JField(k, new JsonAST.JString(v));
            }
            throw new MatchError(x0$1);
        })).toList());
    }

    public JsonAST.JValue asMapOfInt(final Map<String, Object> m) {
        return new JsonAST.JObject(((IterableOnceOps) m.map(x0$1 -> {
            if (x0$1 != null) {
                String k = (String) x0$1._1();
                int v = x0$1._2$mcI$sp();
                return new JsonAST.JField(k, new JsonAST.JInt(BigInt$.MODULE$.int2bigInt(v)));
            }
            throw new MatchError(x0$1);
        })).toList());
    }

    public String safeCompactRender(final JsonAST.JValue json) {
        JsonAST$JNothing$ jsonAST$JNothing$JNothing = net.liftweb.json.package$.MODULE$.JNothing();
        if (jsonAST$JNothing$JNothing == null) {
            if (json == null) {
                return "";
            }
        } else if (jsonAST$JNothing$JNothing.equals(json)) {
            return "";
        }
        return net.liftweb.json.package$.MODULE$.compactRender(json);
    }
}
