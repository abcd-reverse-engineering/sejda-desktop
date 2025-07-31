package com.sejda.pdf2html.pojo;

import java.io.File;
import java.util.Optional;

/* loaded from: com.sejda.pdf2html.pdf2html-0.0.72.jar:com/sejda/pdf2html/pojo/LazyImageProps.class */
public class LazyImageProps {
    public final Optional<File> src;
    public final int width;
    public final int height;

    public LazyImageProps(Optional<File> src, int width, int height) {
        this.src = src;
        this.width = width;
        this.height = height;
    }
}
