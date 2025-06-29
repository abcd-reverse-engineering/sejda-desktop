package org.sejda.io;

import java.util.function.Supplier;
import org.sejda.io.SeekableSource;

@FunctionalInterface
@Deprecated
/* loaded from: org.sejda.sejda-io-4.0.0.M4.jar:org/sejda/io/SeekableSourceSupplier.class */
public interface SeekableSourceSupplier<T extends SeekableSource> extends Supplier<T> {
}
