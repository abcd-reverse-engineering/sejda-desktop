package code.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import scala.reflect.ScalaSignature;

/* compiled from: FileHelper.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005mq!B\n\u0015\u0011\u0003Ib!B\u000e\u0015\u0011\u0003a\u0002\"\u0002\u0014\u0002\t\u00039\u0003\"\u0002\u0015\u0002\t\u0003I\u0003\"\u0002\u001e\u0002\t\u0003Y\u0004\"\u0002\u001e\u0002\t\u0003q\u0005\"B+\u0002\t\u00031\u0006\"B+\u0002\t\u0003A\u0006\"B+\u0002\t\u0003Q\u0006\"\u0002/\u0002\t\u0003i\u0006\"\u0002/\u0002\t\u0003\u0011\u0007\"\u0002/\u0002\t\u0003)\u0007b\u00027\u0002\u0001\u0004%\t!\u001c\u0005\b]\u0006\u0001\r\u0011\"\u0001p\u0011\u0019\u0011\u0018\u0001)Q\u0005\u0017\")1/\u0001C\u0001i\")a/\u0001C\u0001o\"1a/\u0001C\u0001\u0003\u000fAq!a\u0004\u0002\t\u0013\t\t\"\u0001\u0006GS2,\u0007*\u001a7qKJT!!\u0006\f\u0002\tU$\u0018\u000e\u001c\u0006\u0002/\u0005!1m\u001c3f\u0007\u0001\u0001\"AG\u0001\u000e\u0003Q\u0011!BR5mK\"+G\u000e]3s'\r\tQd\t\t\u0003=\u0005j\u0011a\b\u0006\u0002A\u0005)1oY1mC&\u0011!e\b\u0002\u0007\u0003:L(+\u001a4\u0011\u0005i!\u0013BA\u0013\u0015\u0005!aunZ4bE2,\u0017A\u0002\u001fj]&$h\bF\u0001\u001a\u0003EAW/\\1o%\u0016\fG-\u00192mKNK'0\u001a\u000b\u0003UU\u0002\"a\u000b\u001a\u000f\u00051\u0002\u0004CA\u0017 \u001b\u0005q#BA\u0018\u0019\u0003\u0019a$o\\8u}%\u0011\u0011gH\u0001\u0007!J,G-\u001a4\n\u0005M\"$AB*ue&twM\u0003\u00022?!)ag\u0001a\u0001o\u0005)!-\u001f;fgB\u0011a\u0004O\u0005\u0003s}\u0011A\u0001T8oO\u0006)qO]5uKR\u0019AhP%\u0011\u0005yi\u0014B\u0001  \u0005\rIe\u000e\u001e\u0005\u0006\u0001\u0012\u0001\r!Q\u0001\u0003S:\u0004\"AQ$\u000e\u0003\rS!\u0001R#\u0002\u0005%|'\"\u0001$\u0002\t)\fg/Y\u0005\u0003\u0011\u000e\u00131\"\u00138qkR\u001cFO]3b[\")!\n\u0002a\u0001\u0017\u0006\ta\r\u0005\u0002C\u0019&\u0011Qj\u0011\u0002\u0005\r&dW\rF\u0002P%R\u0003\"A\b)\n\u0005E{\"\u0001B+oSRDQaU\u0003A\u0002)\n\u0011a\u001d\u0005\u0006\u0015\u0016\u0001\raS\u0001\ti>\u001cFO]5oOR\u0011!f\u0016\u0005\u0006\u0015\u001a\u0001\rA\u000b\u000b\u0003UeCQAS\u0004A\u0002-#\"AK.\t\u000b\u0001C\u0001\u0019A!\u0002\t\r|\u0007/\u001f\u000b\u0004\u001fz\u0003\u0007\"B0\n\u0001\u0004Y\u0015AB:pkJ\u001cW\rC\u0003b\u0013\u0001\u00071*A\u0006eKN$\u0018N\\1uS>tGcA(dI\")\u0001I\u0003a\u0001\u0003\")\u0011M\u0003a\u0001\u0017R\u0019qJZ4\t\u000b\u0001[\u0001\u0019A!\t\u000b!\\\u0001\u0019A5\u0002\u0007=,H\u000f\u0005\u0002CU&\u00111n\u0011\u0002\r\u001fV$\b/\u001e;TiJ,\u0017-\\\u0001\t)\u0016k\u0005k\u0018#J%V\t1*\u0001\u0007U\u000b6\u0003v\fR%S?\u0012*\u0017\u000f\u0006\u0002Pa\"9\u0011/DA\u0001\u0002\u0004Y\u0015a\u0001=%c\u0005IA+R'Q?\u0012K%\u000bI\u0001\u0011g\u0006t\u0017\u000e^5{K\u001aKG.\u001a8b[\u0016$\"AK;\t\u000b\u0001{\u0001\u0019\u0001\u0016\u0002\r\u0005\u0004\b/\u001a8e)\u0015y\u0005P_A\u0002\u0011\u0015I\b\u00031\u0001L\u0003\u00111\u0017\u000e\\3\t\u000bY\u0002\u0002\u0019A>\u0011\u0007yah0\u0003\u0002~?\t)\u0011I\u001d:bsB\u0011ad`\u0005\u0004\u0003\u0003y\"\u0001\u0002\"zi\u0016Da!!\u0002\u0011\u0001\u00049\u0014\u0001\u00039pg&$\u0018n\u001c8\u0015\u000b=\u000bI!a\u0003\t\u000be\f\u0002\u0019A&\t\r\u00055\u0011\u00031\u0001+\u0003\u0011a\u0017N\\3\u0002-\u0019Lg\u000e\u001a(piR{w\u000eT8oO\u001aKG.\u001a8b[\u0016$RAKA\n\u0003/Aa!!\u0006\u0013\u0001\u0004Q\u0013\u0001\u00034jY\u0016t\u0017-\\3\t\r\u0005e!\u00031\u0001L\u0003%\u0001\u0018M]3oi\u0012K'\u000f")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/FileHelper.class */
public final class FileHelper {
    public static void append(final File file, final String line) {
        FileHelper$.MODULE$.append(file, line);
    }

    public static void append(final File file, final byte[] bytes, final long position) throws IOException {
        FileHelper$.MODULE$.append(file, bytes, position);
    }

    public static String sanitizeFilename(final String in) {
        return FileHelper$.MODULE$.sanitizeFilename(in);
    }

    public static File TEMP_DIR() {
        return FileHelper$.MODULE$.TEMP_DIR();
    }

    public static void copy(final InputStream in, final OutputStream out) {
        FileHelper$.MODULE$.copy(in, out);
    }

    public static void copy(final InputStream in, final File destination) {
        FileHelper$.MODULE$.copy(in, destination);
    }

    public static void copy(final File source, final File destination) {
        FileHelper$.MODULE$.copy(source, destination);
    }

    public static String toString(final InputStream in) {
        return FileHelper$.MODULE$.toString(in);
    }

    public static String toString(final File f) {
        return FileHelper$.MODULE$.toString(f);
    }

    public static String toString(final String f) {
        return FileHelper$.MODULE$.toString(f);
    }

    public static void write(final String s, final File f) {
        FileHelper$.MODULE$.write(s, f);
    }

    public static int write(final InputStream in, final File f) {
        return FileHelper$.MODULE$.write(in, f);
    }

    public static String humanReadableSize(final long bytes) {
        return FileHelper$.MODULE$.humanReadableSize(bytes);
    }
}
