package org.sejda.core.support.prefix;

import java.util.Comparator;
import java.util.ServiceLoader;
import org.apache.commons.lang3.StringUtils;
import org.sejda.commons.util.RequireUtils;
import org.sejda.core.support.prefix.model.NameGenerationRequest;
import org.sejda.core.support.prefix.model.PrefixTransformationContext;
import org.sejda.core.support.prefix.processor.PrefixProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/support/prefix/NameGenerator.class */
public final class NameGenerator {
    private static final Logger LOG = LoggerFactory.getLogger(NameGenerator.class);
    private final String prefix;

    private NameGenerator(String prefix) {
        this.prefix = StringUtils.defaultString(prefix);
    }

    public static NameGenerator nameGenerator(String prefix) {
        return new NameGenerator(prefix);
    }

    public String generate(NameGenerationRequest request) {
        RequireUtils.requireNotNullArg(request, "Unable to generate a name for a null request");
        PrefixTransformationContext context = new PrefixTransformationContext(this.prefix, request);
        LOG.trace("Starting processing prefix: '{}'", context.currentPrefix());
        ServiceLoader.load(PrefixProcessor.class).stream().map((v0) -> {
            return v0.get();
        }).sorted(Comparator.comparingInt((v0) -> {
            return v0.order();
        })).forEachOrdered(prefixProcessor -> {
            prefixProcessor.accept(context);
            LOG.trace("Prefix processed with {}, new value: '{}'", prefixProcessor.getClass().getSimpleName(), context.currentPrefix());
        });
        return context.currentPrefix();
    }
}
