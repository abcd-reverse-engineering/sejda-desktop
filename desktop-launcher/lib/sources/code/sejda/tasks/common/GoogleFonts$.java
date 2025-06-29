package code.sejda.tasks.common;

import code.sejda.tasks.common.GoogleFonts;
import scala.Option;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Tuple2;
import scala.collection.IterableOps;
import scala.collection.immutable.Map;
import scala.collection.immutable.Set;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;

/* compiled from: GoogleFonts.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/GoogleFonts$.class */
public final class GoogleFonts$ {
    private static Set<GoogleFonts.GoogleFont> fonts;
    private static volatile boolean bitmap$0;
    public static final GoogleFonts$ MODULE$ = new GoogleFonts$();
    private static final Map<String, String> mappings = (Map) Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray(new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("arial"), "liberationsans"), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("arialunicodems"), "liberationsans"), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("arialnarrow"), "liberationsans"), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("arialmt"), "liberationsans"), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("calibri"), "carlito"), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("cambria"), "caladea"), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("georgia"), "tinos"), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("helvetica"), "inter"), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("myriadpro"), "ptsans"), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("proximanova"), "opensans"), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("segoeui"), "notosans"), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("trebuchetms"), "firasans"), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("tahoma"), "dejavusans"), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("ubermove"), "didactgothic"), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("verdana"), "dejavusans")}));

    private GoogleFonts$() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v11 */
    private Set<GoogleFonts.GoogleFont> fonts$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (!bitmap$0) {
                fonts = (Set) ((IterableOps) Predef$.MODULE$.Set().apply(ScalaRunTime$.MODULE$.wrapRefArray(new String[]{"Arimo", "Carlito", "Caladea", "DejaVu Sans", "Didact Gothic", "Droid Serif", "EB Garamond", "Fira Sans", "Inter", "Lato", "Liberation Sans", "Liberation Serif", "Liberation Mono", "Noto Sans", "Noto Serif", "Open Sans", "Open Sans Condensed", "Oranienbaum", "PT Sans", "PT Sans Caption", "PT Sans Narrow", "PT Serif", "PT Serif Caption", "Roboto", "Tinos"}))).map(GoogleFonts$GoogleFont$.MODULE$);
                r0 = 1;
                bitmap$0 = true;
            }
        }
        return fonts;
    }

    public Set<GoogleFonts.GoogleFont> fonts() {
        return !bitmap$0 ? fonts$lzycompute() : fonts;
    }

    public Option<GoogleFonts.GoogleFont> find(final String name) {
        return fonts().find(x$2 -> {
            return BoxesRunTime.boxToBoolean($anonfun$find$1(name, x$2));
        });
    }

    public static final /* synthetic */ boolean $anonfun$find$1(final String name$1, final GoogleFonts.GoogleFont x$2) {
        return x$2.name().equalsIgnoreCase(name$1);
    }

    public GoogleFonts.GoogleFont get(final String name) {
        return (GoogleFonts.GoogleFont) find(name).getOrElse(() -> {
            throw new RuntimeException(new StringBuilder(19).append("No font with name: ").append(name).toString());
        });
    }

    public boolean exists(final String name) {
        return find(name).isDefined();
    }

    public Option<GoogleFonts.GoogleFont> findByNormalized(final String normalizedName) {
        return fonts().find(f -> {
            return BoxesRunTime.boxToBoolean($anonfun$findByNormalized$1(normalizedName, f));
        });
    }

    public static final /* synthetic */ boolean $anonfun$findByNormalized$1(final String normalizedName$1, final GoogleFonts.GoogleFont f) {
        String strNormalizeName = FontsHelper$.MODULE$.normalizeName(f.name());
        return strNormalizeName != null ? strNormalizeName.equals(normalizedName$1) : normalizedName$1 == null;
    }

    public Map<String, String> mappings() {
        return mappings;
    }

    public Option<GoogleFonts.GoogleFont> findByMapping(final String normalizedName) {
        return mappings().get(normalizedName).flatMap(normalizedName2 -> {
            return MODULE$.findByNormalized(normalizedName2);
        });
    }
}
