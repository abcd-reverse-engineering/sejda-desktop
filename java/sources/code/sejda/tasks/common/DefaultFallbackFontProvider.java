package code.sejda.tasks.common;

import code.util.Loggable;
import java.io.IOException;
import java.util.HashSet;
import org.sejda.fonts.UnicodeType0Font;
import org.sejda.impl.sambox.component.font.FallbackFontsProvider;
import org.sejda.impl.sambox.util.FontUtils;
import org.sejda.model.pdf.StandardType1Font;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.font.PDFont;
import org.sejda.sambox.pdmodel.font.PDType0Font;
import org.slf4j.Logger;
import scala.$less$colon$less$;
import scala.collection.mutable.StringBuilder;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.util.control.NonFatal$;

/* compiled from: DefaultFallbackFontProvider.scala */
@ScalaSignature(bytes = "\u0006\u0005=4Aa\u0002\u0005\u0001#!)q\u0006\u0001C\u0001a!91\u0007\u0001b\u0001\n\u0013!\u0004BB$\u0001A\u0003%Q\u0007C\u0003I\u0001\u0011\u0005\u0013\nC\u0003I\u0001\u0011\u0005!\rC\u0003k\u0001\u0011\u00053NA\u000eEK\u001a\fW\u000f\u001c;GC2d'-Y2l\r>tG\u000f\u0015:pm&$WM\u001d\u0006\u0003\u0013)\taaY8n[>t'BA\u0006\r\u0003\u0015!\u0018m]6t\u0015\tia\"A\u0003tK*$\u0017MC\u0001\u0010\u0003\u0011\u0019w\u000eZ3\u0004\u0001M!\u0001A\u0005\u000e*!\t\u0019\u0002$D\u0001\u0015\u0015\t)b#\u0001\u0003mC:<'\"A\f\u0002\t)\fg/Y\u0005\u00033Q\u0011aa\u00142kK\u000e$\bCA\u000e(\u001b\u0005a\"BA\u000f\u001f\u0003\u00111wN\u001c;\u000b\u0005}\u0001\u0013!C2p[B|g.\u001a8u\u0015\t\t#%\u0001\u0004tC6\u0014w\u000e\u001f\u0006\u0003G\u0011\nA![7qY*\u0011Q\"\n\u0006\u0002M\u0005\u0019qN]4\n\u0005!b\"!\u0006$bY2\u0014\u0017mY6G_:$8\u000f\u0015:pm&$WM\u001d\t\u0003U5j\u0011a\u000b\u0006\u0003Y9\tA!\u001e;jY&\u0011af\u000b\u0002\t\u0019><w-\u00192mK\u00061A(\u001b8jiz\"\u0012!\r\t\u0003e\u0001i\u0011\u0001C\u0001\ng\u0016,gNR8oiN,\u0012!\u000e\t\u0004maRT\"A\u001c\u000b\u000512\u0012BA\u001d8\u0005\u001dA\u0015m\u001d5TKR\u0004\"a\u000f#\u000f\u0005q\u0012\u0005CA\u001fA\u001b\u0005q$BA \u0011\u0003\u0019a$o\\8u})\t\u0011)A\u0003tG\u0006d\u0017-\u0003\u0002D\u0001\u00061\u0001K]3eK\u001aL!!\u0012$\u0003\rM#(/\u001b8h\u0015\t\u0019\u0005)\u0001\u0006tK\u0016tgi\u001c8ug\u0002\n\u0001CZ5oI\u001a\u000bG\u000e\u001c2bG.4uN\u001c;\u0015\u000b)\u0013FK\u0017/\u0011\u0005-\u0003V\"\u0001'\u000b\u0005ui%B\u0001(P\u0003\u001d\u0001H-\\8eK2T!!\t\u0013\n\u0005Ec%A\u0002)E\r>tG\u000fC\u0003T\t\u0001\u0007!*\u0001\u0007pe&<\u0017N\\1m\r>tG\u000fC\u0003V\t\u0001\u0007a+\u0001\u0005e_\u000e,X.\u001a8u!\t9\u0006,D\u0001N\u0013\tIVJ\u0001\u0006Q\t\u0012{7-^7f]RDQa\u0017\u0003A\u0002i\nA\u0001^3yi\")Q\f\u0002a\u0001=\u0006!!m\u001c7e!\ty\u0006-D\u0001A\u0013\t\t\u0007IA\u0004C_>dW-\u00198\u0015\r)\u001bWMZ4i\u0011\u0015!W\u00011\u0001;\u0003Ay'/[4j]\u0006dgi\u001c8u\u001d\u0006lW\rC\u0003V\u000b\u0001\u0007a\u000bC\u0003\\\u000b\u0001\u0007!\bC\u0003^\u000b\u0001\u0007a\fC\u0003j\u000b\u0001\u0007a,A\u0003tKJLg-A\u0006hKR\u0004&/[8sSRLH#\u00017\u0011\u0005}k\u0017B\u00018A\u0005\rIe\u000e\u001e")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/DefaultFallbackFontProvider.class */
public class DefaultFallbackFontProvider implements FallbackFontsProvider, Loggable {
    private final HashSet<String> seenFonts;
    private transient Logger logger;
    private volatile transient boolean bitmap$trans$0;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v8, types: [code.sejda.tasks.common.DefaultFallbackFontProvider] */
    private Logger logger$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (!this.bitmap$trans$0) {
                this.logger = logger();
                r0 = this;
                r0.bitmap$trans$0 = true;
            }
        }
        return this.logger;
    }

    @Override // code.util.Loggable
    public Logger logger() {
        return !this.bitmap$trans$0 ? logger$lzycompute() : this.logger;
    }

    public DefaultFallbackFontProvider() {
        Loggable.$init$(this);
        this.seenFonts = new HashSet<>();
    }

    private HashSet<String> seenFonts() {
        return this.seenFonts;
    }

    @Override // org.sejda.impl.sambox.component.font.FallbackFontsProvider
    public PDFont findFallbackFont(final PDFont originalFont, final PDDocument document, final String text, final boolean bold) {
        if (originalFont == null) {
            return null;
        }
        try {
            if (originalFont.getName() == null) {
                return null;
            }
            boolean serif = originalFont.getFontDescriptor().isSerif();
            return findFallbackFont(originalFont.getName(), document, text, bold, serif);
        } catch (Throwable th) {
            if (!NonFatal$.MODULE$.apply(th)) {
                throw th;
            }
            logger().error("Failed to provide fallback font", th);
            return null;
        }
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:6:0x001b. Please report as an issue. */
    public PDFont findFallbackFont(final String originalFontName, final PDDocument document, final String text, final boolean bold, final boolean serif) throws IOException {
        PDFont standardType1Font;
        PDFont font;
        String normalized = FontsHelper$.MODULE$.normalizeName(originalFontName);
        switch (normalized == null ? 0 : normalized.hashCode()) {
        }
        if ((normalized.contains("times") && normalized.contains("roman")) || (normalized != null ? normalized.equals("times") : "times" == 0)) {
            standardType1Font = FontUtils.getStandardType1Font(bold ? StandardType1Font.TIMES_BOLD : StandardType1Font.TIMES_ROMAN);
        } else if (normalized.contains("helvetica")) {
            standardType1Font = FontUtils.getStandardType1Font(bold ? StandardType1Font.HELVETICA_BOLD : StandardType1Font.HELVETICA);
        } else if (normalized.contains("courier")) {
            standardType1Font = FontUtils.getStandardType1Font(bold ? StandardType1Font.CURIER_BOLD : StandardType1Font.CURIER);
        } else {
            standardType1Font = null;
        }
        PDFont font2 = standardType1Font;
        if (font2 != null && FontUtils.canDisplay(text, font2)) {
            return font2;
        }
        PDFont font3 = googleFontOrNull$1(normalized, document, bold);
        if (font3 != null && FontUtils.canDisplay(text, font3)) {
            return font3;
        }
        if (serif) {
            font = googleFontOrNull$1("liberationserif", document, bold);
        } else {
            font = googleFontOrNull$1("liberationsans", document, bold);
        }
        if (font != null && FontUtils.canDisplay(text, font)) {
            return font;
        }
        if (!serif) {
            font = googleFontOrNull$1("inter", document, bold);
        }
        if (font != null && FontUtils.canDisplay(text, font)) {
            return font;
        }
        if (seenFonts().contains(originalFontName)) {
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
            return null;
        }
        StringBuilder message = new StringBuilder(new StringBuilder(64).append("No fallback font for original font: ").append(originalFontName).append(" normalized: ").append(normalized).append(" bold: ").append(bold).append(" serif: ").append(serif).toString());
        if (font == null) {
            BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
        } else {
            PDType0Font fallbackFont = PDType0Font.load(document, UnicodeType0Font.NOTO_SANS_MERGED_REGULAR.getFontStream());
            if (!FontUtils.canDisplay(text, fallbackFont)) {
                BoxedUnit boxedUnit3 = BoxedUnit.UNIT;
            } else {
                message.append(new StringBuilder(24).append(", can't display text: '").append(text).append(OperatorName.SHOW_TEXT_LINE).toString());
            }
        }
        logger().info(message.toString());
        BoxesRunTime.boxToBoolean(seenFonts().add(originalFontName));
        return null;
    }

    private static final PDFont googleFontOrNull$1(final String normalized, final PDDocument document$1, final boolean bold$1) {
        return (PDFont) GoogleFonts$.MODULE$.findByNormalized(normalized).orElse(() -> {
            return GoogleFonts$.MODULE$.findByMapping(normalized);
        }).flatMap(x$1 -> {
            return x$1.loadFont(document$1, bold$1);
        }).orNull($less$colon$less$.MODULE$.refl());
    }

    @Override // org.sejda.impl.sambox.component.font.FallbackFontsProvider
    public int getPriority() {
        return 0;
    }
}
