package code.util;

import java.security.SecureRandom;
import java.util.regex.Pattern;
import scala.Option;
import scala.collection.immutable.Seq;

/* compiled from: StringHelpers.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/StringHelpers$.class */
public final class StringHelpers$ implements StringHelpers {
    public static final StringHelpers$ MODULE$ = new StringHelpers$();
    private static SecureRandom code$util$StringHelpers$$_random;
    private static Seq<String> unicodeWhitespaceChars;
    private static Pattern code$util$StringHelpers$$RTRIM;
    private static volatile boolean bitmap$0;

    static {
        StringHelpers.$init$(MODULE$);
    }

    @Override // code.util.StringHelpers
    public String randomString(final int size) {
        return randomString(size);
    }

    @Override // code.util.StringHelpers
    public Option<String> asOpt(final String s) {
        return asOpt(s);
    }

    @Override // code.util.StringHelpers
    public Option<String> noneIfBlank(final Option<String> s) {
        return noneIfBlank(s);
    }

    @Override // code.util.StringHelpers
    public String onlyAlphanumeric(final String s) {
        return onlyAlphanumeric(s);
    }

    @Override // code.util.StringHelpers
    public String blankIfEmpty(final String s) {
        return blankIfEmpty(s);
    }

    @Override // code.util.StringHelpers
    public String halfIfDuplicated(final String s) {
        return halfIfDuplicated(s);
    }

    @Override // code.util.StringHelpers
    public String truncateMiddle(final String str, final int len) {
        return truncateMiddle(str, len);
    }

    @Override // code.util.StringHelpers
    public int bytesSize(final String str) {
        return bytesSize(str);
    }

    @Override // code.util.StringHelpers
    public String rightTrim(final String str) {
        return rightTrim(str);
    }

    @Override // code.util.StringHelpers
    public String leftTrimNewlines(final String str) {
        return leftTrimNewlines(str);
    }

    @Override // code.util.StringHelpers
    public String quoted(final String s) {
        return quoted(s);
    }

    @Override // code.util.StringHelpers
    public String stripQuotes(final String str) {
        return stripQuotes(str);
    }

    @Override // code.util.StringHelpers
    public String stripNonNumeric(final String s) {
        return stripNonNumeric(s);
    }

    @Override // code.util.StringHelpers
    public String htmlEscape(final String s) {
        return htmlEscape(s);
    }

    @Override // code.util.StringHelpers
    public String asWindows1252(final String s) {
        return asWindows1252(s);
    }

    @Override // code.util.StringHelpers
    public String asUnicodes(final String in) {
        return asUnicodes(in);
    }

    @Override // code.util.StringHelpers
    public boolean containsWhitespace(final String s) {
        return containsWhitespace(s);
    }

    @Override // code.util.StringHelpers
    public String removeInvalidXMLChars(final String input) {
        return removeInvalidXMLChars(input);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v7 */
    private SecureRandom code$util$StringHelpers$$_random$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (!bitmap$0) {
                code$util$StringHelpers$$_random = code$util$StringHelpers$$_random();
                r0 = 1;
                bitmap$0 = true;
            }
        }
        return code$util$StringHelpers$$_random;
    }

    @Override // code.util.StringHelpers
    public SecureRandom code$util$StringHelpers$$_random() {
        return !bitmap$0 ? code$util$StringHelpers$$_random$lzycompute() : code$util$StringHelpers$$_random;
    }

    @Override // code.util.StringHelpers
    public Seq<String> unicodeWhitespaceChars() {
        return unicodeWhitespaceChars;
    }

    @Override // code.util.StringHelpers
    public Pattern code$util$StringHelpers$$RTRIM() {
        return code$util$StringHelpers$$RTRIM;
    }

    @Override // code.util.StringHelpers
    public void code$util$StringHelpers$_setter_$unicodeWhitespaceChars_$eq(final Seq<String> x$1) {
        unicodeWhitespaceChars = x$1;
    }

    @Override // code.util.StringHelpers
    public final void code$util$StringHelpers$_setter_$code$util$StringHelpers$$RTRIM_$eq(final Pattern x$1) {
        code$util$StringHelpers$$RTRIM = x$1;
    }

    private StringHelpers$() {
    }
}
