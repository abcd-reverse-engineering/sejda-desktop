package org.sejda.core.support.prefix.processor;

import org.sejda.core.support.prefix.model.PrefixTransformationContext;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/support/prefix/processor/StrictBookmarkPrefixProcessor.class */
public class StrictBookmarkPrefixProcessor extends BaseBookmarkPrefixProcessor {
    private static final String BOOKMARK_NAME_REPLACE_REGX = "\\[BOOKMARK_NAME_STRICT]";
    private static final String INVALID_WIN_FILENAME_CHARS_REGEXP = "(?i)[^A-Z0-9_ ]";

    @Override // org.sejda.core.support.prefix.processor.BaseBookmarkPrefixProcessor
    public /* bridge */ /* synthetic */ void accept(PrefixTransformationContext prefixTransformationContext) {
        super.accept(prefixTransformationContext);
    }

    public StrictBookmarkPrefixProcessor() {
        super(BOOKMARK_NAME_REPLACE_REGX, INVALID_WIN_FILENAME_CHARS_REGEXP);
    }
}
