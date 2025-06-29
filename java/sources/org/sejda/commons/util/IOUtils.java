package org.sejda.commons.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import org.sejda.commons.FastByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-commons-3.0.0.jar:org/sejda/commons/util/IOUtils.class */
public final class IOUtils {
    private static final Logger LOG = LoggerFactory.getLogger(IOUtils.class);

    private IOUtils() {
    }

    public static void close(Closeable closeable) throws IOException {
        if (Objects.nonNull(closeable)) {
            closeable.close();
        }
    }

    public static void closeQuietly(Closeable closeable) {
        closeQuietly(closeable, null);
    }

    public static void closeQuietly(Closeable closeable, Consumer<IOException> consumer) {
        try {
            close(closeable);
        } catch (IOException ioe) {
            Optional.ofNullable(consumer).ifPresentOrElse(c -> {
                c.accept(ioe);
            }, () -> {
                LOG.warn("An error occurred while closing a Closeable resource", ioe);
            });
        }
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        FastByteArrayOutputStream output = new FastByteArrayOutputStream();
        try {
            input.transferTo(output);
            byte[] byteArray = output.toByteArray();
            output.close();
            return byteArray;
        } catch (Throwable th) {
            try {
                output.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static void copy(InputStream input, OutputStream output) throws IOException {
        RequireUtils.requireNotNullArg(input, "Cannot copy a null input");
        RequireUtils.requireNotNullArg(output, "Cannot copy to a null output");
        input.transferTo(output);
    }
}
