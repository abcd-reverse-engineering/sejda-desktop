package code.util;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.regex.Pattern;
import org.sejda.sambox.contentstream.operator.OperatorName;
import scala.None$;
import scala.Option;
import scala.Option$;
import scala.Predef$;
import scala.Some;
import scala.collection.ArrayOps$;
import scala.collection.StringOps$;
import scala.collection.immutable.Seq;
import scala.collection.mutable.StringBuilder;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.xml.Utility$;

/* compiled from: StringHelpers.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005us!B\u000e\u001d\u0011\u0003\tc!B\u0012\u001d\u0011\u0003!\u0003bBA-\u0003\u0011\u0005\u00111\f\u0004\bGq\u0001\n1!\u0001-\u0011\u0015i3\u0001\"\u0001/\u0011!\u00114\u0001#b\u0001\n\u0013\u0019\u0004b\u0002\u001f\u0004\u0005\u0004%\t!\u0010\u0005\u0006\u0019\u000e!\t!\u0014\u0005\u0006;\u000e!\tA\u0018\u0005\u0006I\u000e!\t!\u001a\u0005\u0006O\u000e!\t\u0001\u001b\u0005\u0006U\u000e!\ta\u001b\u0005\u0006[\u000e!\tA\u001c\u0005\u0006a\u000e!\t!\u001d\u0005\u0006m\u000e!\ta\u001e\u0005\bs\u000e\u0011\r\u0011\"\u0003{\u0011\u001d\t)a\u0001C\u0001\u0003\u000fAq!a\u0003\u0004\t\u0003\ti\u0001C\u0004\u0002\u0012\r!\t!a\u0005\t\u000f\u0005]1\u0001\"\u0001\u0002\u001a!9\u0011QD\u0002\u0005\u0002\u0005}\u0001bBA\u0012\u0007\u0011\u0005\u0011Q\u0005\u0005\b\u0003S\u0019A\u0011AA\u0016\u0011\u001d\tyc\u0001C\u0001\u0003cAq!a\u000e\u0004\t\u0003\tI\u0004C\u0004\u0002D\r!\t!!\u0012\t\u000f\u0005-3\u0001\"\u0003\u0002N\u0005i1\u000b\u001e:j]\u001eDU\r\u001c9feNT!!\b\u0010\u0002\tU$\u0018\u000e\u001c\u0006\u0002?\u0005!1m\u001c3f\u0007\u0001\u0001\"AI\u0001\u000e\u0003q\u0011Qb\u0015;sS:<\u0007*\u001a7qKJ\u001c8cA\u0001&WA\u0011a%K\u0007\u0002O)\t\u0001&A\u0003tG\u0006d\u0017-\u0003\u0002+O\t1\u0011I\\=SK\u001a\u0004\"AI\u0002\u0014\u0005\r)\u0013A\u0002\u0013j]&$H\u0005F\u00010!\t1\u0003'\u0003\u00022O\t!QK\\5u\u0003\u001dy&/\u00198e_6,\u0012\u0001\u000e\t\u0003kij\u0011A\u000e\u0006\u0003oa\n\u0001b]3dkJLG/\u001f\u0006\u0002s\u0005!!.\u0019<b\u0013\tYdG\u0001\u0007TK\u000e,(/\u001a*b]\u0012|W.\u0001\fv]&\u001cw\u000eZ3XQ&$Xm\u001d9bG\u0016\u001c\u0005.\u0019:t+\u0005q\u0004cA E\r6\t\u0001I\u0003\u0002B\u0005\u0006I\u0011.\\7vi\u0006\u0014G.\u001a\u0006\u0003\u0007\u001e\n!bY8mY\u0016\u001cG/[8o\u0013\t)\u0005IA\u0002TKF\u0004\"a\u0012&\u000e\u0003!S!!\u0013\u001d\u0002\t1\fgnZ\u0005\u0003\u0017\"\u0013aa\u0015;sS:<\u0017\u0001\u0004:b]\u0012|Wn\u0015;sS:<GC\u0001(Y!\tyeK\u0004\u0002Q)B\u0011\u0011kJ\u0007\u0002%*\u00111\u000bI\u0001\u0007yI|w\u000e\u001e \n\u0005U;\u0013A\u0002)sK\u0012,g-\u0003\u0002L/*\u0011Qk\n\u0005\u00063\u001e\u0001\rAW\u0001\u0005g&TX\r\u0005\u0002'7&\u0011Al\n\u0002\u0004\u0013:$\u0018!B1t\u001fB$HCA0c!\r1\u0003MT\u0005\u0003C\u001e\u0012aa\u00149uS>t\u0007\"B2\t\u0001\u0004q\u0015!A:\u0002\u00179|g.Z%g\u00052\fgn\u001b\u000b\u0003?\u001aDQaY\u0005A\u0002}\u000b\u0001c\u001c8ms\u0006c\u0007\u000f[1ok6,'/[2\u0015\u00059K\u0007\"B2\u000b\u0001\u0004q\u0015\u0001\u00042mC:\\\u0017JZ#naRLHC\u0001(m\u0011\u0015\u00197\u00021\u0001O\u0003AA\u0017\r\u001c4JM\u0012+\b\u000f\\5dCR,G\r\u0006\u0002G_\")1\r\u0004a\u0001\u001d\u0006qAO];oG\u0006$X-T5eI2,Gc\u0001(si\")1/\u0004a\u0001\u001d\u0006\u00191\u000f\u001e:\t\u000bUl\u0001\u0019\u0001.\u0002\u00071,g.A\u0005csR,7oU5{KR\u0011!\f\u001f\u0005\u0006g:\u0001\rAT\u0001\u0006%R\u0013\u0016*T\u000b\u0002wB\u0019A0!\u0001\u000e\u0003uT!A`@\u0002\u000bI,w-\u001a=\u000b\u0005uA\u0014bAA\u0002{\n9\u0001+\u0019;uKJt\u0017!\u0003:jO\"$HK]5n)\r1\u0015\u0011\u0002\u0005\u0006gB\u0001\rAT\u0001\u0011Y\u00164G\u000f\u0016:j[:+w\u000f\\5oKN$2ATA\b\u0011\u0015\u0019\u0018\u00031\u0001O\u0003\u0019\tXo\u001c;fIR\u0019a*!\u0006\t\u000b\r\u0014\u0002\u0019\u0001(\u0002\u0017M$(/\u001b9Rk>$Xm\u001d\u000b\u0004\r\u0006m\u0001\"B:\u0014\u0001\u0004q\u0015aD:ue&\u0004hj\u001c8Ok6,'/[2\u0015\u00079\u000b\t\u0003C\u0003d)\u0001\u0007a*\u0001\u0006ii6dWi]2ba\u0016$2ATA\u0014\u0011\u0015\u0019W\u00031\u0001O\u00035\t7oV5oI><8/\r\u001a6eQ\u0019a)!\f\t\u000b\r4\u0002\u0019\u0001(\u0002\u0015\u0005\u001cXK\\5d_\u0012,7\u000fF\u0002O\u0003gAa!!\u000e\u0018\u0001\u0004q\u0015AA5o\u0003I\u0019wN\u001c;bS:\u001cx\u000b[5uKN\u0004\u0018mY3\u0015\t\u0005m\u0012\u0011\t\t\u0004M\u0005u\u0012bAA O\t9!i\\8mK\u0006t\u0007\"B2\u0019\u0001\u0004q\u0015!\u0006:f[>4X-\u00138wC2LG\rW'M\u0007\"\f'o\u001d\u000b\u0004\u001d\u0006\u001d\u0003BBA%3\u0001\u0007a*A\u0003j]B,H/\u0001\bjgZ\u000bG.\u001b3Y\u001b2\u001b\u0005.\u0019:\u0015\t\u0005m\u0012q\n\u0005\b\u0003#R\u0002\u0019AA*\u0003\u0005\u0019\u0007c\u0001\u0014\u0002V%\u0019\u0011qK\u0014\u0003\t\rC\u0017M]\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003\u0005\u0002")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/StringHelpers.class */
public interface StringHelpers {
    void code$util$StringHelpers$_setter_$unicodeWhitespaceChars_$eq(final Seq<String> x$1);

    void code$util$StringHelpers$_setter_$code$util$StringHelpers$$RTRIM_$eq(final Pattern x$1);

    Seq<String> unicodeWhitespaceChars();

    Pattern code$util$StringHelpers$$RTRIM();

    default SecureRandom code$util$StringHelpers$$_random() {
        return new SecureRandom();
    }

    static void $init$(final StringHelpers $this) {
        $this.code$util$StringHelpers$_setter_$unicodeWhitespaceChars_$eq((Seq) package$.MODULE$.Seq().apply(ScalaRunTime$.MODULE$.wrapRefArray(new String[]{"\t", "\n", "\u000b", "\f", "\r", " ", "\u0085", " ", "\u1680", "\u180e", "\u2000", "\u2001", "\u2002", "\u2003", "\u2004", "\u2005", "\u2006", " ", "\u2008", "\u2009", "\u200a", "\u2028", "\u2029", " ", "\u205f", "\u3000"})));
        $this.code$util$StringHelpers$_setter_$code$util$StringHelpers$$RTRIM_$eq(Pattern.compile("\\s+$"));
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x0008  */
    /* JADX WARN: Type inference failed for: r0v11, types: [java.lang.Throwable, java.security.SecureRandom] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private default java.lang.StringBuilder addChar$1(final int r6, final int r7, final java.lang.StringBuilder r8, final int r9) {
        /*
            r5 = this;
        L0:
            r0 = r6
            r1 = r9
            if (r0 < r1) goto L8
            r0 = r8
            return r0
        L8:
            r0 = r6
            r1 = 6
            int r0 = r0 % r1
            r1 = 0
            if (r0 != r1) goto L34
            r0 = r5
            java.security.SecureRandom r0 = r0.code$util$StringHelpers$$_random()
            r1 = r0
            r12 = r1
            monitor-enter(r0)
            r0 = r5
            java.security.SecureRandom r0 = r0.code$util$StringHelpers$$_random()     // Catch: java.lang.Throwable -> L2d
            int r0 = r0.nextInt()     // Catch: java.lang.Throwable -> L2d
            r13 = r0
            r0 = r12
            monitor-exit(r0)
            r0 = r13
            goto L31
        L2d:
            r1 = move-exception
            monitor-exit(r1)
            throw r0
        L31:
            goto L35
        L34:
            r0 = r7
        L35:
            r11 = r0
            r0 = r8
            r1 = r11
            r2 = 31
            r1 = r1 & r2
            r14 = r1
            r1 = r14
            switch(r1) {
                default: goto L4c;
            }
        L4c:
            r1 = r14
            r2 = 26
            if (r1 >= r2) goto L5c
            r1 = 65
            r2 = r14
            int r1 = r1 + r2
            char r1 = (char) r1
            goto L68
        L5c:
            r1 = 48
            r2 = r14
            r3 = 26
            int r2 = r2 - r3
            int r1 = r1 + r2
            char r1 = (char) r1
            goto L68
        L68:
            java.lang.StringBuilder r0 = r0.append(r1)
            r0 = r6
            r1 = 1
            int r0 = r0 + r1
            r1 = r11
            r2 = 5
            int r1 = r1 >> r2
            r2 = r8
            r8 = r2
            r7 = r1
            r6 = r0
            goto L0
        */
        throw new UnsupportedOperationException("Method not decompiled: code.util.StringHelpers.addChar$1(int, int, java.lang.StringBuilder, int):java.lang.StringBuilder");
    }

    default String randomString(final int size) {
        return addChar$1(0, 0, new StringBuilder(size), size).toString();
    }

    default Option<String> asOpt(final String s) {
        return (s == null || s.trim().isEmpty()) ? None$.MODULE$ : new Some(s);
    }

    default Option<String> noneIfBlank(final Option<String> s) {
        if (s instanceof Some) {
            String str = (String) ((Some) s).value();
            if (str.trim().isEmpty()) {
                return None$.MODULE$;
            }
        }
        return s;
    }

    default String onlyAlphanumeric(final String s) {
        return s.replaceAll("[^a-zA-Z0-9]", "");
    }

    default String blankIfEmpty(final String s) {
        return (String) Option$.MODULE$.apply(s).getOrElse(() -> {
            return "";
        });
    }

    default String halfIfDuplicated(final String s) {
        String firstHalf = s.substring(0, s.length() / 2);
        String secondHalf = s.substring(s.length() / 2, s.length());
        if (firstHalf != null ? firstHalf.equals(secondHalf) : secondHalf == null) {
            String string = new StringBuilder(0).append(firstHalf).append(secondHalf).toString();
            if (string != null ? string.equals(s) : s == null) {
                return firstHalf;
            }
        }
        return s;
    }

    default String truncateMiddle(final String str, final int len) {
        if (str.length() <= len) {
            return str;
        }
        int frontChars = (int) Math.ceil(len / 2.0d);
        int backChars = (int) Math.floor(len / 2.0d);
        return new StringBuilder(0).append(str.substring(0, frontChars)).append(str.substring(str.length() - backChars)).toString();
    }

    default int bytesSize(final String str) {
        return ArrayOps$.MODULE$.size$extension(Predef$.MODULE$.byteArrayOps(str.getBytes("UTF-8")));
    }

    default String rightTrim(final String str) {
        return code$util$StringHelpers$$RTRIM().matcher(str).replaceAll("");
    }

    default String leftTrimNewlines(final String str) {
        return StringOps$.MODULE$.dropWhile$extension(Predef$.MODULE$.augmentString(str), c -> {
            return BoxesRunTime.boxToBoolean($anonfun$leftTrimNewlines$1(BoxesRunTime.unboxToChar(c)));
        });
    }

    static /* synthetic */ boolean $anonfun$leftTrimNewlines$1(final char c) {
        return c == '\n' || c == '\r';
    }

    default String quoted(final String s) {
        return new StringBuilder(2).append(OperatorName.SHOW_TEXT_LINE_AND_SPACE).append(s).append(OperatorName.SHOW_TEXT_LINE_AND_SPACE).toString();
    }

    default String stripQuotes(final String str) {
        if (str.startsWith(OperatorName.SHOW_TEXT_LINE_AND_SPACE) && str.endsWith(OperatorName.SHOW_TEXT_LINE_AND_SPACE)) {
            return str.substring(1, str.length() - 1);
        }
        return str;
    }

    default String stripNonNumeric(final String s) {
        return s.replaceAll("[^0-9]", "");
    }

    default String htmlEscape(final String s) {
        return Utility$.MODULE$.escape(s);
    }

    default String asWindows1252(final String s) {
        byte[] b = s.getBytes(StandardCharsets.ISO_8859_1);
        return new String(b, "Windows-1252");
    }

    default String asUnicodes(final String in) {
        if (in == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        int iCharCount = 0;
        while (true) {
            int offset = iCharCount;
            if (offset < in.length()) {
                int codepoint = in.codePointAt(offset);
                result.append("\\U+").append(Integer.toHexString(codepoint).toUpperCase());
                iCharCount = offset + Character.charCount(codepoint);
            } else {
                return result.toString();
            }
        }
    }

    default boolean containsWhitespace(final String s) {
        return unicodeWhitespaceChars().exists(x$1 -> {
            return BoxesRunTime.boxToBoolean(s.contains(x$1));
        });
    }

    default String removeInvalidXMLChars(final String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return StringOps$.MODULE$.filter$extension(Predef$.MODULE$.augmentString(input), c -> {
            return BoxesRunTime.boxToBoolean(this.isValidXMLChar(BoxesRunTime.unboxToChar(c)));
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    default boolean isValidXMLChar(final char c) {
        return c == '\t' || c == '\n' || c == '\r' || (c >= ' ' && c <= 55295) || ((c >= 57344 && c <= 65533) || (c >= 0 && c <= 65535));
    }
}
