package org.sejda.core.support.prefix.processor;

import java.util.Objects;
import java.util.Optional;
import org.sejda.core.support.prefix.model.PrefixTransformationContext;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/support/prefix/processor/PrependPageNumberPrefixProcessor.class */
public class PrependPageNumberPrefixProcessor implements PrefixProcessor {
    @Override // java.util.function.Consumer
    public void accept(PrefixTransformationContext context) {
        if (!context.uniqueNames()) {
            Optional map = Optional.ofNullable(context.request()).map((v0) -> {
                return v0.getPage();
            }).map(p -> {
                return String.format("%d_%s", p, context.currentPrefix());
            });
            Objects.requireNonNull(context);
            map.ifPresent(context::currentPrefix);
        }
    }

    @Override // org.sejda.core.support.prefix.processor.PrefixProcessor
    public int order() {
        return 200;
    }
}
