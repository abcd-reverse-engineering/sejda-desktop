package com.sejda.pdf2html.sambox;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import java.io.File;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import scala.Function1;
import scala.Predef$;
import scala.collection.IterableOnceOps;
import scala.collection.JavaConverters$;
import scala.collection.StringOps$;
import scala.collection.immutable.$colon;
import scala.collection.immutable.Nil$;
import scala.runtime.BoxedUnit;
import scala.runtime.ObjectRef;

/* compiled from: VariousFormattingImprovementsProcessor.scala */
/* loaded from: com.sejda.pdf2html.pdf2html-0.0.72.jar:com/sejda/pdf2html/sambox/VariousFormattingImprovementsProcessor$.class */
public final class VariousFormattingImprovementsProcessor$ {
    public static final VariousFormattingImprovementsProcessor$ MODULE$ = new VariousFormattingImprovementsProcessor$();

    private VariousFormattingImprovementsProcessor$() {
    }

    public void process(final File file) {
        String content = Files.toString(file, Charsets.UTF_8);
        Files.write(doProcess(content), file, Charsets.UTF_8);
    }

    public String doProcess(final String content) {
        ObjectRef result = ObjectRef.create(content);
        new $colon.colon(content2 -> {
            return MODULE$.bulletLists(content2);
        }, new $colon.colon(content3 -> {
            return MODULE$.tableOfContentsDotDotDot(content3);
        }, new $colon.colon(content4 -> {
            return MODULE$.tableOfContentsWithoutLineBreaks(content4);
        }, Nil$.MODULE$))).foreach(fn -> {
            $anonfun$doProcess$4(result, fn);
            return BoxedUnit.UNIT;
        });
        return (String) result.elem;
    }

    public static final /* synthetic */ void $anonfun$doProcess$4(final ObjectRef result$1, final Function1 fn) {
        result$1.elem = (String) fn.apply((String) result$1.elem);
    }

    public String bulletLists(final String content) {
        return StringOps$.MODULE$.replaceAllLiterally$extension(Predef$.MODULE$.augmentString(StringOps$.MODULE$.replaceAllLiterally$extension(Predef$.MODULE$.augmentString(StringOps$.MODULE$.replaceAllLiterally$extension(Predef$.MODULE$.augmentString(content), "&#8226;", "<br/>&#8226;")), "•", "<br/>•")), "&#183;", "<br/>&#183;");
    }

    public String tableOfContentsDotDotDot(final String content) {
        return content.replaceAll("(\\. ){4,}", ". . . ").replaceAll("(\\.){4,}", "... ");
    }

    public String tableOfContentsWithoutLineBreaks(final String content) {
        Document doc = Jsoup.parse(content);
        ((IterableOnceOps) JavaConverters$.MODULE$.asScalaBufferConverter(doc.select("span")).asScala()).foreach(elem -> {
            if (!elem.text().matches("^(\\d+)(.*)... (\\d+)(\\s*)$")) {
                return BoxedUnit.UNIT;
            }
            return elem.append("<br/>");
        });
        return doc.html();
    }
}
