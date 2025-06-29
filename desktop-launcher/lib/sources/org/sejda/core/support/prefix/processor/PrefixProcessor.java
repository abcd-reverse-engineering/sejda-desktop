package org.sejda.core.support.prefix.processor;

import java.util.function.Consumer;
import org.sejda.core.support.prefix.model.PrefixTransformationContext;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/support/prefix/processor/PrefixProcessor.class */
public interface PrefixProcessor extends Consumer<PrefixTransformationContext> {
    default int order() {
        return 0;
    }
}
