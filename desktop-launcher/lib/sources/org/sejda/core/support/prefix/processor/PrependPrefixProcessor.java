package org.sejda.core.support.prefix.processor;

import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.sejda.core.support.prefix.model.PrefixTransformationContext;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/support/prefix/processor/PrependPrefixProcessor.class */
public class PrependPrefixProcessor implements PrefixProcessor {
    @Override // java.util.function.Consumer
    public void accept(PrefixTransformationContext context) {
        if (context.noTransformationApplied()) {
            Optional map = Optional.ofNullable(context.request()).map((v0) -> {
                return v0.getOriginalName();
            }).filter((v0) -> {
                return StringUtils.isNotBlank(v0);
            }).map(e -> {
                return String.format("%s%s", context.currentPrefix(), e);
            });
            Objects.requireNonNull(context);
            map.ifPresent(context::currentPrefix);
        }
    }

    @Override // org.sejda.core.support.prefix.processor.PrefixProcessor
    public int order() {
        return 100;
    }
}
