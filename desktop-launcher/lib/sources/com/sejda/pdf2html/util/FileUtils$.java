package com.sejda.pdf2html.util;

import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import java.io.File;
import java.nio.charset.StandardCharsets;

/* compiled from: FileUtils.scala */
/* loaded from: com.sejda.pdf2html.pdf2html-0.0.72.jar:com/sejda/pdf2html/util/FileUtils$.class */
public final class FileUtils$ {
    public static final FileUtils$ MODULE$ = new FileUtils$();

    private FileUtils$() {
    }

    public void write(final File file, final String contents) {
        Files.asCharSink(file, StandardCharsets.UTF_8, new FileWriteMode[0]).write(contents);
    }
}
