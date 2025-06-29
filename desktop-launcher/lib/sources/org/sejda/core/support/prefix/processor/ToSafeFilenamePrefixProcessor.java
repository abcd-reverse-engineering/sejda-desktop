package org.sejda.core.support.prefix.processor;

import org.sejda.core.support.prefix.model.PrefixTransformationContext;
import org.sejda.model.util.IOUtils;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/support/prefix/processor/ToSafeFilenamePrefixProcessor.class */
public class ToSafeFilenamePrefixProcessor implements PrefixProcessor {
    @Override // java.util.function.Consumer
    public void accept(PrefixTransformationContext context) {
        context.currentPrefix(IOUtils.toSafeFilename(context.currentPrefix()));
    }

    @Override // org.sejda.core.support.prefix.processor.PrefixProcessor
    public int order() {
        return Integer.MAX_VALUE;
    }
}
