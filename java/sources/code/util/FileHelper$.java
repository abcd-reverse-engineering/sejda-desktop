package code.util;

import com.google.common.io.Files;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FilenameUtils;
import org.sejda.model.util.IOUtils;
import org.slf4j.Logger;
import scala.Predef$;

/* compiled from: FileHelper.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/FileHelper$.class */
public final class FileHelper$ implements Loggable {
    public static final FileHelper$ MODULE$ = new FileHelper$();
    private static File TEMP_DIR;
    private static transient Logger logger;
    private static volatile transient boolean bitmap$trans$0;

    static {
        Loggable.$init$(MODULE$);
        TEMP_DIR = IOUtils.createTemporaryFolder();
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

    private FileHelper$() {
    }

    public String humanReadableSize(final long bytes) {
        if (bytes < 1000) {
            return new StringBuilder(2).append(bytes).append(" B").toString();
        }
        int exp = (int) (Math.log(bytes) / Math.log(1000));
        char pre = "kMGTPE".charAt(exp - 1);
        double rounded = bytes / Math.pow(1000, exp);
        String valueStr = FormatHelpers$.MODULE$.maybeOneDecimal(Predef$.MODULE$.double2Double(rounded));
        return new StringBuilder(2).append(valueStr).append(" ").append(pre).append("B").toString();
    }

    public int write(final InputStream in, final File f) {
        FileOutputStream out = new FileOutputStream(f);
        try {
            return org.apache.commons.io.IOUtils.copy(in, out);
        } finally {
            org.apache.commons.io.IOUtils.closeQuietly(in);
            org.apache.commons.io.IOUtils.closeQuietly(out);
        }
    }

    public void write(final String s, final File f) {
        FileOutputStream out = new FileOutputStream(f);
        try {
            org.apache.commons.io.IOUtils.write(s, out, StandardCharsets.UTF_8);
        } finally {
            org.apache.commons.io.IOUtils.closeQuietly(out);
        }
    }

    public String toString(final String f) {
        return Files.toString(new File(f), StandardCharsets.UTF_8);
    }

    public String toString(final File f) {
        return Files.toString(f, StandardCharsets.UTF_8);
    }

    public String toString(final InputStream in) {
        return org.apache.commons.io.IOUtils.toString(in, StandardCharsets.UTF_8);
    }

    public void copy(final File source, final File destination) {
        copy(new FileInputStream(source), destination);
    }

    public void copy(final InputStream in, final File destination) {
        FileOutputStream out = new FileOutputStream(destination);
        copy(in, out);
    }

    public void copy(final InputStream in, final OutputStream out) {
        try {
            org.apache.commons.io.IOUtils.copy(in, out);
        } finally {
            org.apache.commons.io.IOUtils.closeQuietly(out);
            org.apache.commons.io.IOUtils.closeQuietly(in);
        }
    }

    public File TEMP_DIR() {
        return TEMP_DIR;
    }

    public void TEMP_DIR_$eq(final File x$1) {
        TEMP_DIR = x$1;
    }

    public String sanitizeFilename(final String in) {
        String name = in.replaceAll("[<>:|\\\\/*?]", "_");
        TEMP_DIR().mkdirs();
        String res = findNotTooLongFilename(name, TEMP_DIR());
        if (res != null ? !res.equals(name) : name != null) {
            logger().warn(new StringBuilder(35).append("Shortened filename too long: ").append(res).append(" was: ").append(in).toString());
        }
        return res;
    }

    public void append(final File file, final byte[] bytes, final long position) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(file, "rwd");
        raf.seek(position);
        raf.write(bytes);
        raf.close();
    }

    public void append(final File file, final String line) {
        String string;
        if (file.exists()) {
            string = toString(file);
        } else {
            string = "";
        }
        String contents = string;
        write(new StringBuilder(1).append(contents).append("\n").append(line).toString(), file);
    }

    private String findNotTooLongFilename(final String filename, final File parentDir) {
        File file = new File(parentDir, filename);
        try {
            if (file.exists()) {
                return filename;
            }
            file.createNewFile();
            if (!file.delete()) {
                Thread.sleep(500L);
                if (!file.delete()) {
                    logger().warn(new StringBuilder(36).append("Could not delete just created file: ").append(file).toString());
                }
            }
            return filename;
        } catch (Throwable th) {
            if (th instanceof IOException) {
                String message = ((IOException) th).getMessage();
                if (message != null ? message.equals("File name too long") : "File name too long" == 0) {
                    return retry$1(filename, parentDir);
                }
            }
            if (!(th instanceof FileNotFoundException) || !((FileNotFoundException) th).getMessage().contains("File name too long")) {
                throw th;
            }
            return retry$1(filename, parentDir);
        }
    }

    private final String retry$1(final String filename$1, final File parentDir$1) {
        String ext = FilenameUtils.getExtension(filename$1);
        String baseName = FilenameUtils.getBaseName(filename$1);
        int len = baseName.length();
        int quarter = len / 4;
        String shorterName = new StringBuilder(1).append(baseName.substring(0, quarter)).append(baseName.substring(len - quarter)).append(".").append(ext).toString();
        return findNotTooLongFilename(shorterName, parentDir$1);
    }
}
