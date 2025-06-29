package code.util.encoding;

import com.google.common.io.ByteStreams;
import java.io.InputStream;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.sejda.io.SeekableSource;
import scala.None$;
import scala.Option;
import scala.Option$;
import scala.Predef$;
import scala.collection.IterableOps;
import scala.collection.JavaConverters$;
import scala.runtime.BoxesRunTime;
import scala.util.control.NonFatal$;

/* compiled from: FileContentsHelper.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/encoding/FileContentsHelper$.class */
public final class FileContentsHelper$ {
    public static final FileContentsHelper$ MODULE$ = new FileContentsHelper$();
    private static final String baseUri = "https://www.example.com/";
    private static final int maxParseSize = 1000000;

    private FileContentsHelper$() {
    }

    public String baseUri() {
        return baseUri;
    }

    public int maxParseSize() {
        return maxParseSize;
    }

    public Option<String> encodingOfHtml(final SeekableSource seekableSource) {
        try {
            InputStream limited = ByteStreams.limit(seekableSource.asNewInputStream(), maxParseSize());
            Document doc = Jsoup.parse(limited, "UTF-8", baseUri());
            return ((IterableOps) ((IterableOps) ((IterableOps) JavaConverters$.MODULE$.asScalaBufferConverter(doc.getElementsByTag("meta")).asScala()).flatMap(metaTag -> {
                return MODULE$.parseEncoding(metaTag);
            })).map(x$1 -> {
                return x$1.toLowerCase();
            })).headOption().filter(enc -> {
                return BoxesRunTime.boxToBoolean($anonfun$encodingOfHtml$3(seekableSource, enc));
            });
        } catch (Throwable th) {
            if (NonFatal$.MODULE$.apply(th)) {
                return None$.MODULE$;
            }
            throw th;
        }
    }

    public static final /* synthetic */ boolean $anonfun$encodingOfHtml$3(final SeekableSource seekableSource$1, final String enc) {
        return MODULE$.hasHtmlContents(seekableSource$1, enc);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0057  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean hasHtmlContents(final org.sejda.io.SeekableSource r5, final java.lang.String r6) {
        /*
            r4 = this;
            r0 = r5
            java.io.InputStream r0 = r0.asNewInputStream()     // Catch: java.lang.Throwable -> L5f
            r1 = r4
            int r1 = r1.maxParseSize()     // Catch: java.lang.Throwable -> L5f
            long r1 = (long) r1     // Catch: java.lang.Throwable -> L5f
            java.io.InputStream r0 = com.google.common.io.ByteStreams.limit(r0, r1)     // Catch: java.lang.Throwable -> L5f
            r8 = r0
            r0 = r8
            r1 = r6
            java.lang.String r0 = org.apache.commons.io.IOUtils.toString(r0, r1)     // Catch: java.lang.Throwable -> L5f
            java.lang.String r0 = r0.toLowerCase()     // Catch: java.lang.Throwable -> L5f
            r9 = r0
            r0 = r9
            java.lang.String r1 = "<html"
            boolean r0 = r0.contains(r1)     // Catch: java.lang.Throwable -> L5f
            if (r0 != 0) goto L57
            r0 = r9
            java.lang.String r1 = "<body"
            boolean r0 = r0.contains(r1)     // Catch: java.lang.Throwable -> L5f
            if (r0 != 0) goto L57
            r0 = r9
            java.lang.String r1 = "<script"
            boolean r0 = r0.contains(r1)     // Catch: java.lang.Throwable -> L5f
            if (r0 != 0) goto L57
            r0 = r9
            java.lang.String r1 = "<div"
            boolean r0 = r0.contains(r1)     // Catch: java.lang.Throwable -> L5f
            if (r0 != 0) goto L57
            r0 = r9
            java.lang.String r1 = "<p"
            boolean r0 = r0.contains(r1)     // Catch: java.lang.Throwable -> L5f
            if (r0 != 0) goto L57
            r0 = r9
            java.lang.String r1 = "<h"
            boolean r0 = r0.contains(r1)     // Catch: java.lang.Throwable -> L5f
            if (r0 == 0) goto L5b
        L57:
            r0 = 1
            goto L5c
        L5b:
            r0 = 0
        L5c:
            goto L7d
        L5f:
            r10 = move-exception
            r0 = r10
            r11 = r0
            scala.util.control.NonFatal$ r0 = scala.util.control.NonFatal$.MODULE$
            r1 = r11
            boolean r0 = r0.apply(r1)
            if (r0 == 0) goto L74
            r0 = 0
            goto L7a
        L74:
            goto L77
        L77:
            r0 = r10
            throw r0
        L7a:
            goto L7d
        L7d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: code.util.encoding.FileContentsHelper$.hasHtmlContents(org.sejda.io.SeekableSource, java.lang.String):boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Option<String> parseEncoding(final Element metaTag) {
        String strAttr = metaTag.attr("http-equiv");
        if (strAttr != null ? strAttr.equals("Content-Type") : "Content-Type" == 0) {
            String content = metaTag.attr("content");
            return ((Option) Predef$.MODULE$.wrapRefArray(content.split(";")).lift().apply(BoxesRunTime.boxToInteger(1))).flatMap(x$2 -> {
                return (Option) Predef$.MODULE$.wrapRefArray(x$2.split("=")).lift().apply(BoxesRunTime.boxToInteger(1));
            });
        }
        return Option$.MODULE$.apply(metaTag.attr("charset"));
    }
}
