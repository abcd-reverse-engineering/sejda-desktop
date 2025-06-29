package org.sejda.core.support.prefix.processor;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.sejda.core.support.prefix.model.PrefixTransformationContext;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/support/prefix/processor/BasenamePrefixProcessor.class */
public class BasenamePrefixProcessor implements PrefixProcessor {
    private final Pattern pattern = Pattern.compile("\\[BASENAME]");

    @Override // java.util.function.Consumer
    public void accept(PrefixTransformationContext context) {
        if (this.pattern.matcher(context.currentPrefix()).find()) {
            Optional map = Optional.ofNullable(context.request()).map((v0) -> {
                return v0.getOriginalName();
            }).filter((v0) -> {
                return StringUtils.isNotBlank(v0);
            }).map(o -> {
                return context.currentPrefix().replace("[BASENAME]", o);
            });
            Objects.requireNonNull(context);
            map.ifPresent(context::currentPrefix);
        }
    }
}
