package code.util.pdf;

import code.util.Loggable;
import java.util.Locale;
import javax.imageio.ImageIO;
import javax.imageio.spi.IIORegistry;
import org.sejda.core.Sejda;
import org.sejda.model.parameter.SetMetadataParameters;
import org.sejda.model.parameter.base.TaskParameters;
import org.sejda.model.pro.parameter.DecryptParameters;
import org.sejda.sambox.SAMBox;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.util.Version;
import org.slf4j.Logger;
import scala.runtime.BoxedUnit;

/* compiled from: PdfLibraryUtils.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/pdf/PdfLibraryUtils$.class */
public final class PdfLibraryUtils$ implements Loggable {
    public static final PdfLibraryUtils$ MODULE$ = new PdfLibraryUtils$();
    private static BoxedUnit configure_once;
    private static boolean isSejdaDesktop;
    private static boolean isTesting;
    private static transient Logger logger;
    private static volatile boolean bitmap$0;
    private static volatile transient boolean bitmap$trans$0;

    static {
        Loggable.$init$(MODULE$);
        isSejdaDesktop = false;
        isTesting = false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v7 */
    private Logger logger$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (!bitmap$trans$0) {
                logger = logger();
                r0 = 1;
                bitmap$trans$0 = true;
            }
        }
        return logger;
    }

    @Override // code.util.Loggable
    public Logger logger() {
        return !bitmap$trans$0 ? logger$lzycompute() : logger;
    }

    private PdfLibraryUtils$() {
    }

    public boolean isSejdaDesktop() {
        return isSejdaDesktop;
    }

    public void isSejdaDesktop_$eq(final boolean x$1) {
        isSejdaDesktop = x$1;
    }

    public boolean isTesting() {
        return isTesting;
    }

    public void isTesting_$eq(final boolean x$1) {
        isTesting = x$1;
    }

    public void configure() {
        configure_once();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v7 */
    private void configure_once$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (!bitmap$0) {
                configureSejda();
                configureImageIO();
                configureJdk();
                r0 = 1;
                bitmap$0 = true;
            }
        }
    }

    public void configure_once() {
        if (bitmap$0) {
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
        } else {
            configure_once$lzycompute();
            BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
        }
    }

    public void configurePerTask(final TaskParameters params) {
        if (params instanceof DecryptParameters) {
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
        } else if (params instanceof SetMetadataParameters) {
            BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
        } else {
            System.setProperty(Sejda.UNETHICAL_READ_PROPERTY_NAME, "true");
            BoxedUnit boxedUnit3 = BoxedUnit.UNIT;
        }
    }

    private void configureSejda() {
        String str;
        System.setProperty(Sejda.PERFORM_MEMORY_OPTIMIZATIONS_PROPERTY_NAME, "true");
        System.setProperty(Sejda.PERFORM_EAGER_ASSERTIONS_PROPERTY_NAME, "true");
        System.clearProperty("org.sejda.sambox.rendering.UsePureJavaCMYKConversion");
        System.clearProperty("javax.accessibility.assistive_technologies");
        String desktopMarker = isSejdaDesktop() ? OperatorName.SET_LINE_DASHPATTERN : "";
        SAMBox.PRODUCER = new StringBuilder(4).append(Version.getVersion()).append(" (").append(Sejda.VERSION).append(") ").append(desktopMarker).toString();
        Sejda.CREATOR = "";
        if (isSejdaDesktop()) {
            str = "code.sejda.tasks.common.SejdaBundledPlusFileSystemFontProvider";
        } else {
            str = "code.sejda.tasks.common.SejdaBundledOnlyFontProvider";
        }
        String fontProvider = str;
        System.setProperty(SAMBox.FONT_PROVIDER_PROPERTY, fontProvider);
    }

    private void configureImageIO() {
        ImageIO.scanForPlugins();
        IIORegistry registry = IIORegistry.getDefaultInstance();
        deregister$1("com.github.jaiimageio.impl.plugins.tiff.TIFFImageReaderSpi", registry);
    }

    private static final void deregister$1(final String s, final IIORegistry registry$1) {
        Object spi = registry$1.getServiceProviderByClass(Class.forName(s));
        registry$1.deregisterServiceProvider(spi);
    }

    private void configureJdk() {
        Locale.setDefault(Locale.US);
    }
}
