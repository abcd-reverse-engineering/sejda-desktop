package org.sejda.core.support.prefix.processor;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.sejda.core.support.prefix.model.PrefixTransformationContext;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/support/prefix/processor/BaseBookmarkPrefixProcessor.class */
class BaseBookmarkPrefixProcessor implements PrefixProcessor {
    private final Pattern pattern;
    private final String invalidCharsRegexp;

    BaseBookmarkPrefixProcessor(String prefixNameRegex, String invalidCharsRegexp) {
        this.pattern = Pattern.compile(prefixNameRegex);
        this.invalidCharsRegexp = invalidCharsRegexp;
    }

    @Override // java.util.function.Consumer
    public void accept(PrefixTransformationContext context) {
        Matcher matcher = this.pattern.matcher(context.currentPrefix());
        if (matcher.find()) {
            Optional.ofNullable(context.request()).map((v0) -> {
                return v0.getBookmark();
            }).map(b -> {
                return b.replaceAll(this.invalidCharsRegexp, "");
            }).map(Matcher::quoteReplacement).filter((v0) -> {
                return StringUtils.isNotBlank(v0);
            }).ifPresent(r -> {
                context.uniqueNames(true);
                context.currentPrefix(matcher.replaceAll(r));
            });
        }
    }
}
