package org.sejda.core.support.prefix.processor;

import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.sejda.core.support.prefix.model.PrefixTransformationContext;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/support/prefix/processor/FileNumberPrefixProcessor.class */
public class FileNumberPrefixProcessor extends NumberPrefixProcessor implements PrefixProcessor {
    public FileNumberPrefixProcessor() {
        super("FILENUMBER");
    }

    @Override // java.util.function.Consumer
    public void accept(PrefixTransformationContext context) {
        if (this.pattern.matcher(context.currentPrefix()).find()) {
            Optional.ofNullable(context.request()).map((v0) -> {
                return v0.getFileNumber();
            }).map(p -> {
                return findAndReplace(context.currentPrefix(), p);
            }).filter((v0) -> {
                return StringUtils.isNotBlank(v0);
            }).ifPresent(s -> {
                context.uniqueNames(true);
                context.currentPrefix(s);
            });
        }
    }
}
