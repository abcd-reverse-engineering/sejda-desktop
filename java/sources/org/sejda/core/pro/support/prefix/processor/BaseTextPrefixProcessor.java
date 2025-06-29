package org.sejda.core.pro.support.prefix.processor;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.sejda.commons.util.RequireUtils;
import org.sejda.core.support.prefix.model.NameGenerationRequest;
import org.sejda.core.support.prefix.model.PrefixTransformationContext;
import org.sejda.core.support.prefix.processor.PrefixProcessor;
import org.sejda.model.util.IOUtils;

/* loaded from: org.sejda.sejda-core-pro-5.1.10.1.jar:org/sejda/core/pro/support/prefix/processor/BaseTextPrefixProcessor.class */
public abstract class BaseTextPrefixProcessor implements PrefixProcessor {
    private final Pattern pattern;

    protected abstract String getReplacement(NameGenerationRequest nameGenerationRequest);

    public BaseTextPrefixProcessor(String placeholder) {
        RequireUtils.requireNotBlank(placeholder, "Placeholder cannot be blank");
        this.pattern = Pattern.compile("\\[" + placeholder + "((\\s?\\|\\s?(nospaces))?(\\s?\\|\\s?(upper|lower|capitalize))?(\\s?\\|\\s?replace\\('([^']+)'\\s?,\\s?'([^']+)'\\))?)?]");
    }

    @Override // java.util.function.Consumer
    public void accept(PrefixTransformationContext context) {
        Matcher matcher = this.pattern.matcher(context.currentPrefix());
        if (matcher.find()) {
            Optional optionalFilter = Optional.ofNullable(context.request()).map(this::getReplacement).filter((v0) -> {
                return StringUtils.isNotBlank(v0);
            }).map(o -> {
                return (String) Optional.ofNullable(matcher.group(2)).map((v0) -> {
                    return v0.toLowerCase();
                }).map(c -> {
                    if (c.contains("nospaces")) {
                        return StringUtils.deleteWhitespace(o);
                    }
                    return o;
                }).orElse(o);
            }).map(o2 -> {
                return (String) Optional.ofNullable(matcher.group(4)).map((v0) -> {
                    return v0.toLowerCase();
                }).map(c -> {
                    if (c.contains("upper")) {
                        return o2.toUpperCase();
                    }
                    if (c.contains("lower")) {
                        return o2.toLowerCase();
                    }
                    if (c.contains("capitalize")) {
                        return StringUtils.capitalize(o2);
                    }
                    return o2;
                }).orElse(o2);
            }).map(text -> {
                return (String) Optional.ofNullable(matcher.group(7)).map(t -> {
                    return text.replace(t, StringUtils.defaultString(matcher.group(8)));
                }).orElse(text);
            }).map(IOUtils::toSafeFilename).filter((v0) -> {
                return StringUtils.isNotBlank(v0);
            });
            Objects.requireNonNull(matcher);
            optionalFilter.map(matcher::replaceFirst).ifPresent(t -> {
                context.currentPrefix(t);
                context.uniqueNames(true);
            });
        }
    }
}
