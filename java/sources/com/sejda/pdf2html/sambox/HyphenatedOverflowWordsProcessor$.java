package com.sejda.pdf2html.sambox;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import java.io.File;

/* compiled from: HyphenatedOverflowWordsProcessor.scala */
/* loaded from: com.sejda.pdf2html.pdf2html-0.0.72.jar:com/sejda/pdf2html/sambox/HyphenatedOverflowWordsProcessor$.class */
public final class HyphenatedOverflowWordsProcessor$ {
    public static final HyphenatedOverflowWordsProcessor$ MODULE$ = new HyphenatedOverflowWordsProcessor$();

    private HyphenatedOverflowWordsProcessor$() {
    }

    public void process(final File file) {
        String content = Files.toString(file, Charsets.UTF_8);
        Files.write(doProcess(content), file, Charsets.UTF_8);
    }

    public String doProcess(final String content) {
        return content.replaceAll("(?=[a-zA-Z]*)\\-\\n", "");
    }
}
