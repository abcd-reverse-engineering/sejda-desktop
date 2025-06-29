package org.sejda.core.support.prefix.processor;

import org.sejda.core.support.prefix.model.PrefixTransformationContext;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/support/prefix/processor/BookmarkPrefixProcessor.class */
public class BookmarkPrefixProcessor extends BaseBookmarkPrefixProcessor {
    private static final String BOOKMARK_NAME_REPLACE_REGX = "\\[BOOKMARK_NAME]";
    private static final String INVALID_WIN_FILENAME_CHARS_REGEXP = "[\\\\/:*?\\\"<>|]";

    @Override // org.sejda.core.support.prefix.processor.BaseBookmarkPrefixProcessor
    public /* bridge */ /* synthetic */ void accept(PrefixTransformationContext prefixTransformationContext) {
        super.accept(prefixTransformationContext);
    }

    public BookmarkPrefixProcessor() {
        super(BOOKMARK_NAME_REPLACE_REGX, INVALID_WIN_FILENAME_CHARS_REGEXP);
    }
}
