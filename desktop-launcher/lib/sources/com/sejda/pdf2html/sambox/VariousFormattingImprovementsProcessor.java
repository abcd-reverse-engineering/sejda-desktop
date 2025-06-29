package com.sejda.pdf2html.sambox;

import java.io.File;
import scala.reflect.ScalaSignature;

/* compiled from: VariousFormattingImprovementsProcessor.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0015;Q\u0001C\u0005\t\u0002I1Q\u0001F\u0005\t\u0002UAQ\u0001H\u0001\u0005\u0002uAQAH\u0001\u0005\u0002}AQ!L\u0001\u0005\u00029BQ\u0001P\u0001\u0005\u0002uBQaP\u0001\u0005\u0002\u0001CQAQ\u0001\u0005\u0002\r\u000baEV1sS>,8OR8s[\u0006$H/\u001b8h\u00136\u0004(o\u001c<f[\u0016tGo\u001d)s_\u000e,7o]8s\u0015\tQ1\"\u0001\u0004tC6\u0014w\u000e\u001f\u0006\u0003\u00195\t\u0001\u0002\u001d3ge!$X\u000e\u001c\u0006\u0003\u001d=\tQa]3kI\u0006T\u0011\u0001E\u0001\u0004G>l7\u0001\u0001\t\u0003'\u0005i\u0011!\u0003\u0002'-\u0006\u0014\u0018n\\;t\r>\u0014X.\u0019;uS:<\u0017*\u001c9s_Z,W.\u001a8ugB\u0013xnY3tg>\u00148CA\u0001\u0017!\t9\"$D\u0001\u0019\u0015\u0005I\u0012!B:dC2\f\u0017BA\u000e\u0019\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\u0012AE\u0001\baJ|7-Z:t)\t\u00013\u0005\u0005\u0002\u0018C%\u0011!\u0005\u0007\u0002\u0005+:LG\u000fC\u0003%\u0007\u0001\u0007Q%\u0001\u0003gS2,\u0007C\u0001\u0014,\u001b\u00059#B\u0001\u0015*\u0003\tIwNC\u0001+\u0003\u0011Q\u0017M^1\n\u00051:#\u0001\u0002$jY\u0016\f\u0011\u0002Z8Qe>\u001cWm]:\u0015\u0005=R\u0004C\u0001\u00198\u001d\t\tT\u0007\u0005\u0002315\t1G\u0003\u00025#\u00051AH]8pizJ!A\u000e\r\u0002\rA\u0013X\rZ3g\u0013\tA\u0014H\u0001\u0004TiJLgn\u001a\u0006\u0003maAQa\u000f\u0003A\u0002=\nqaY8oi\u0016tG/A\u0006ck2dW\r\u001e'jgR\u001cHCA\u0018?\u0011\u0015YT\u00011\u00010\u0003a!\u0018M\u00197f\u001f\u001a\u001cuN\u001c;f]R\u001cHi\u001c;E_R$u\u000e\u001e\u000b\u0003_\u0005CQa\u000f\u0004A\u0002=\n\u0001\u0005^1cY\u0016|emQ8oi\u0016tGo],ji\"|W\u000f\u001e'j]\u0016\u0014%/Z1lgR\u0011q\u0006\u0012\u0005\u0006w\u001d\u0001\ra\f")
/* loaded from: com.sejda.pdf2html.pdf2html-0.0.72.jar:com/sejda/pdf2html/sambox/VariousFormattingImprovementsProcessor.class */
public final class VariousFormattingImprovementsProcessor {
    public static String tableOfContentsWithoutLineBreaks(final String content) {
        return VariousFormattingImprovementsProcessor$.MODULE$.tableOfContentsWithoutLineBreaks(content);
    }

    public static String tableOfContentsDotDotDot(final String content) {
        return VariousFormattingImprovementsProcessor$.MODULE$.tableOfContentsDotDotDot(content);
    }

    public static String bulletLists(final String content) {
        return VariousFormattingImprovementsProcessor$.MODULE$.bulletLists(content);
    }

    public static String doProcess(final String content) {
        return VariousFormattingImprovementsProcessor$.MODULE$.doProcess(content);
    }

    public static void process(final File file) {
        VariousFormattingImprovementsProcessor$.MODULE$.process(file);
    }
}
