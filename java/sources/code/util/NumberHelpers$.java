package code.util;

import scala.math.BigDecimal$RoundingMode$;
import scala.package$;

/* compiled from: NumberHelpers.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/NumberHelpers$.class */
public final class NumberHelpers$ {
    public static final NumberHelpers$ MODULE$ = new NumberHelpers$();

    private NumberHelpers$() {
    }

    public int roundUp$default$2() {
        return 2;
    }

    public double roundUp(final double d, final int decimals) {
        return package$.MODULE$.BigDecimal().apply(d).setScale(decimals, BigDecimal$RoundingMode$.MODULE$.HALF_UP()).toDouble();
    }
}
