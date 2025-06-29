package org.sejda.impl.sambox.pro.component.optimization;

import java.util.HashMap;
import java.util.Map;
import org.sejda.impl.sambox.component.ReadOnlyFilteredCOSStream;
import org.sejda.impl.sambox.pro.component.optimization.font.FontSubsettingContext;
import org.sejda.model.pro.parameter.OptimizeParameters;
import org.sejda.sambox.cos.IndirectCOSObjectIdentifier;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/optimization/ContentStreamOptimizationContext.class */
public class ContentStreamOptimizationContext {
    private final OptimizeParameters parameters;
    private final Map<String, ReadOnlyFilteredCOSStream> optimizedImagesByHash = new HashMap();
    private final Map<IndirectCOSObjectIdentifier, ReadOnlyFilteredCOSStream> optimizedImagesById = new HashMap();
    private final FontSubsettingContext fontSubsettingContext = new FontSubsettingContext();

    public ContentStreamOptimizationContext(OptimizeParameters parameters) {
        this.parameters = parameters;
    }

    public OptimizeParameters parameters() {
        return this.parameters;
    }

    public Map<String, ReadOnlyFilteredCOSStream> optimizedImagesByHash() {
        return this.optimizedImagesByHash;
    }

    public Map<IndirectCOSObjectIdentifier, ReadOnlyFilteredCOSStream> optimizedImagesById() {
        return this.optimizedImagesById;
    }

    public FontSubsettingContext fontSubsettingContext() {
        return this.fontSubsettingContext;
    }
}
