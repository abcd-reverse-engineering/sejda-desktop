package code.sejda.tasks.html;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import org.sejda.model.PageOrientation;
import scala.None$;
import scala.Option;

/* compiled from: HtmlToPdfParameters.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/html/HtmlToPdfParameters$.class */
public final class HtmlToPdfParameters$ {
    public static final HtmlToPdfParameters$ MODULE$ = new HtmlToPdfParameters$();

    public Option<String> $lessinit$greater$default$2() {
        return None$.MODULE$;
    }

    public Option<String> $lessinit$greater$default$3() {
        return None$.MODULE$;
    }

    public PageOrientation $lessinit$greater$default$4() {
        return PageOrientation.AUTO;
    }

    public Option<String> $lessinit$greater$default$5() {
        return None$.MODULE$;
    }

    public Option<Object> $lessinit$greater$default$6() {
        return None$.MODULE$;
    }

    public Option<Object> $lessinit$greater$default$7() {
        return None$.MODULE$;
    }

    public Option<Object> $lessinit$greater$default$8() {
        return None$.MODULE$;
    }

    public int $lessinit$greater$default$9() {
        return 0;
    }

    public Option<Object> $lessinit$greater$default$10() {
        return None$.MODULE$;
    }

    public Option<String> $lessinit$greater$default$11() {
        return None$.MODULE$;
    }

    public Option<String> $lessinit$greater$default$12() {
        return None$.MODULE$;
    }

    public Option<Object> $lessinit$greater$default$13() {
        return None$.MODULE$;
    }

    public Option<String> $lessinit$greater$default$14() {
        return None$.MODULE$;
    }

    public Option<String> $lessinit$greater$default$15() {
        return None$.MODULE$;
    }

    public Option<String> $lessinit$greater$default$16() {
        return None$.MODULE$;
    }

    public Option<Object> $lessinit$greater$default$17() {
        return None$.MODULE$;
    }

    public Option<String> $lessinit$greater$default$18() {
        return None$.MODULE$;
    }

    public Option<String> $lessinit$greater$default$19() {
        return None$.MODULE$;
    }

    private HtmlToPdfParameters$() {
    }

    public int sizeInMbOf(final String s) {
        return (s.getBytes(StandardCharsets.UTF_8).length / 1024) / 1024;
    }

    public String hashOf(final String s) {
        return Hashing.md5().hashString(s, StandardCharsets.UTF_8).toString();
    }

    public String redactedHtmlCode(final String s) {
        int sizeInMb = sizeInMbOf(s);
        String hash = hashOf(s);
        return new StringBuilder(6).append("<").append(hash).append("_").append(sizeInMb).append("MB/>").toString();
    }
}
