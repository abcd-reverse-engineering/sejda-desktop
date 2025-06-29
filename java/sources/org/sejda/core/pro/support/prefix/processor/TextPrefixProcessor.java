package org.sejda.core.pro.support.prefix.processor;

import org.sejda.core.support.prefix.model.NameGenerationRequest;

/* loaded from: org.sejda.sejda-core-pro-5.1.10.1.jar:org/sejda/core/pro/support/prefix/processor/TextPrefixProcessor.class */
public class TextPrefixProcessor extends BaseTextPrefixProcessor {
    public TextPrefixProcessor() {
        super("TEXT");
    }

    @Override // org.sejda.core.pro.support.prefix.processor.BaseTextPrefixProcessor
    protected String getReplacement(NameGenerationRequest request) {
        return request.getText();
    }
}
