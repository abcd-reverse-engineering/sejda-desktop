package org.sejda.core.support.prefix.model;

import java.util.Optional;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/support/prefix/model/PrefixTransformationContext.class */
public class PrefixTransformationContext {
    private String currentPrefix;
    private final String originalPrefix;
    private final NameGenerationRequest request;
    private boolean uniqueNames = false;

    public PrefixTransformationContext(String prefix, NameGenerationRequest request) {
        this.currentPrefix = prefix;
        this.originalPrefix = prefix;
        this.request = (NameGenerationRequest) Optional.ofNullable(request).orElseGet(NameGenerationRequest::nameRequest);
    }

    public String currentPrefix() {
        return this.currentPrefix;
    }

    public void currentPrefix(String prefix) {
        this.currentPrefix = prefix;
    }

    public NameGenerationRequest request() {
        return this.request;
    }

    public boolean uniqueNames() {
        return this.uniqueNames;
    }

    public void uniqueNames(boolean uniqueNames) {
        this.uniqueNames = uniqueNames;
    }

    public boolean noTransformationApplied() {
        return this.originalPrefix.equals(this.currentPrefix);
    }
}
