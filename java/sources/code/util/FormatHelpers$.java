package code.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/* compiled from: FormatHelpers.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/FormatHelpers$.class */
public final class FormatHelpers$ {
    public static final FormatHelpers$ MODULE$ = new FormatHelpers$();

    private FormatHelpers$() {
    }

    public String alwaysTwoDecimals(final Number n) {
        DecimalFormat df = new DecimalFormat("#####.##", new DecimalFormatSymbols(Locale.US));
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        return df.format(n);
    }

    public String twoDecimalsOrNone(final Number n) {
        if (maybeOneDecimal(n).contains(".")) {
            return decimals(n, 2);
        }
        return decimals(n, 0);
    }

    public String maybeTwoDecimals(final Number n) {
        return maybeDecimals(n, 2);
    }

    public String maybeOneDecimal(final Number n) {
        return maybeDecimals(n, 1);
    }

    private String maybeDecimals(final Number n, final int decimals) {
        DecimalFormat df = new DecimalFormat("####.##", new DecimalFormatSymbols(Locale.US));
        df.setMaximumFractionDigits(decimals);
        df.setMinimumFractionDigits(0);
        return df.format(n);
    }

    private String decimals(final Number n, final int decimals) {
        DecimalFormat df = new DecimalFormat("####.##", new DecimalFormatSymbols(Locale.US));
        df.setMaximumFractionDigits(decimals);
        df.setMinimumFractionDigits(decimals);
        return df.format(n);
    }
}
