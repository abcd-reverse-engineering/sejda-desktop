package code.sejda.tasks.common;

import code.util.FileHelper$;
import code.util.Loggable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.sejda.sambox.pdmodel.font.FileSystemFontProvider;
import org.slf4j.Logger;
import scala.Predef$;
import scala.collection.ArrayOps$;
import scala.collection.immutable.Seq;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;

/* compiled from: BaseFontProvider.scala */
@ScalaSignature(bytes = "\u0006\u0005Y3QAC\u0006\u0002\u0002QAQ\u0001\u000b\u0001\u0005\u0002%Bq\u0001\f\u0001C\u0002\u0013\u0005Q\u0006\u0003\u00047\u0001\u0001\u0006IA\f\u0005\bo\u0001\u0011\r\u0011\"\u0001.\u0011\u0019A\u0004\u0001)A\u0005]!A\u0011\b\u0001EC\u0002\u0013\u0005!\b\u0003\u0005L\u0001!\u0015\r\u0011\"\u0001M\u0011\u0015q\u0005\u0001\"\u0003P\u0011\u0015\u0001\u0006\u0001\"\u0001R\u0005A\u0011\u0015m]3G_:$\bK]8wS\u0012,'O\u0003\u0002\r\u001b\u000511m\\7n_:T!AD\b\u0002\u000bQ\f7o[:\u000b\u0005A\t\u0012!B:fU\u0012\f'\"\u0001\n\u0002\t\r|G-Z\u0002\u0001'\r\u0001QC\t\t\u0003-\u0001j\u0011a\u0006\u0006\u00031e\tAAZ8oi*\u0011!dG\u0001\ba\u0012lw\u000eZ3m\u0015\taR$\u0001\u0004tC6\u0014w\u000e\u001f\u0006\u0003!yQ\u0011aH\u0001\u0004_J<\u0017BA\u0011\u0018\u0005Y1\u0015\u000e\\3TsN$X-\u001c$p]R\u0004&o\u001c<jI\u0016\u0014\bCA\u0012'\u001b\u0005!#BA\u0013\u0012\u0003\u0011)H/\u001b7\n\u0005\u001d\"#\u0001\u0003'pO\u001e\f'\r\\3\u0002\rqJg.\u001b;?)\u0005Q\u0003CA\u0016\u0001\u001b\u0005Y\u0011a\u0001;naV\ta\u0006\u0005\u00020i5\t\u0001G\u0003\u00022e\u0005\u0011\u0011n\u001c\u0006\u0002g\u0005!!.\u0019<b\u0013\t)\u0004G\u0001\u0003GS2,\u0017\u0001\u0002;na\u0002\nAAY1tK\u0006)!-Y:fA\u0005!\"-\u001e8eY\u0016$gi\u001c8u%\u0016\u001cx.\u001e:dKN,\u0012a\u000f\t\u0004y\r+U\"A\u001f\u000b\u0005yz\u0014!C5n[V$\u0018M\u00197f\u0015\t\u0001\u0015)\u0001\u0006d_2dWm\u0019;j_:T\u0011AQ\u0001\u0006g\u000e\fG.Y\u0005\u0003\tv\u00121aU3r!\t1\u0015*D\u0001H\u0015\tA%'\u0001\u0003mC:<\u0017B\u0001&H\u0005\u0019\u0019FO]5oO\u0006\u0001\"-\u001e8eY\u0016$gi\u001c8u\r&dWm]\u000b\u0002\u001bB\u0019Ah\u0011\u0018\u0002\u0015%t\u0017\u000e^5bY&TX\rF\u0001N\u0003I\u0019G.Z1s\u0003:$\u0017J\\5uS\u0006d\u0017N_3\u0015\u0003I\u0003\"a\u0015+\u000e\u0003\u0005K!!V!\u0003\u000f\t{w\u000e\\3b]\u0002")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/BaseFontProvider.class */
public abstract class BaseFontProvider extends FileSystemFontProvider implements Loggable {
    private Seq<String> bundledFontResources;
    private Seq<File> bundledFontFiles;
    private final File tmp;
    private final File base;
    private transient Logger logger;
    private volatile byte bitmap$0;
    private volatile transient boolean bitmap$trans$0;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v8, types: [code.sejda.tasks.common.BaseFontProvider] */
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

    public BaseFontProvider() {
        Loggable.$init$(this);
        this.tmp = new File(System.getProperty("java.io.tmpdir"));
        this.base = new File(tmp(), "sejda-fonts");
        base().mkdirs();
        logger().info(new StringBuilder(23).append("Using ").append(getClass().getSimpleName()).append(" as font provider").toString());
    }

    public File tmp() {
        return this.tmp;
    }

    public File base() {
        return this.base;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v10, types: [code.sejda.tasks.common.BaseFontProvider] */
    private Seq<String> bundledFontResources$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (((byte) (this.bitmap$0 & 1)) == 0) {
                this.bundledFontResources = package$.MODULE$.Seq().apply(ScalaRunTime$.MODULE$.wrapRefArray(new String[]{"/fonts/Liberation_Mono/LiberationMono-Bold.ttf", "/fonts/Liberation_Mono/LiberationMono-BoldItalic.ttf", "/fonts/Liberation_Mono/LiberationMono-Italic.ttf", "/fonts/Liberation_Mono/LiberationMono-Regular.ttf", "/fonts/Liberation_Sans/LiberationSans-Bold.ttf", "/fonts/Liberation_Sans/LiberationSans-BoldItalic.ttf", "/fonts/Liberation_Sans/LiberationSans-Italic.ttf", "/fonts/Liberation_Sans/LiberationSans-Regular.ttf", "/fonts/Liberation_Serif/LiberationSerif-Bold.ttf", "/fonts/Liberation_Serif/LiberationSerif-BoldItalic.ttf", "/fonts/Liberation_Serif/LiberationSerif-Italic.ttf", "/fonts/Liberation_Serif/LiberationSerif-Regular.ttf", "/fonts/Foxit/FoxitSymbol.ttf", "/fonts/Foxit/FoxitDingbats.ttf"}));
                r0 = this;
                r0.bitmap$0 = (byte) (this.bitmap$0 | 1);
            }
        }
        return this.bundledFontResources;
    }

    public Seq<String> bundledFontResources() {
        return ((byte) (this.bitmap$0 & 1)) == 0 ? bundledFontResources$lzycompute() : this.bundledFontResources;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v10, types: [code.sejda.tasks.common.BaseFontProvider] */
    private Seq<File> bundledFontFiles$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (((byte) (this.bitmap$0 & 2)) == 0) {
                this.bundledFontFiles = initialize();
                r0 = this;
                r0.bitmap$0 = (byte) (this.bitmap$0 | 2);
            }
        }
        return this.bundledFontFiles;
    }

    public Seq<File> bundledFontFiles() {
        return ((byte) (this.bitmap$0 & 2)) == 0 ? bundledFontFiles$lzycompute() : this.bundledFontFiles;
    }

    private Seq<File> initialize() {
        return (Seq) bundledFontResources().map(res -> {
            String name = (String) ArrayOps$.MODULE$.last$extension(Predef$.MODULE$.refArrayOps(res.split("/")));
            File dest = new File(this.base(), name);
            if (dest.exists() && dest.isFile()) {
                BoxedUnit boxedUnit = BoxedUnit.UNIT;
            } else {
                InputStream in = this.getClass().getResourceAsStream(res);
                try {
                    BoxesRunTime.boxToInteger(FileHelper$.MODULE$.write(in, dest));
                } catch (IOException ex) {
                    this.logger().error("Could no extract font files to temp storage, will exit", ex);
                    System.exit(99);
                    BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
                }
            }
            return dest;
        });
    }

    public boolean clearAndInitialize() {
        ArrayOps$.MODULE$.foreach$extension(Predef$.MODULE$.refArrayOps(base().listFiles()), f -> {
            return BoxesRunTime.boxToBoolean(f.delete());
        });
        initialize();
        return getDiskCacheFile().delete();
    }
}
