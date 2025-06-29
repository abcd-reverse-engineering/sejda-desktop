package org.sejda.sambox.pdmodel;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/PageNotFoundException.class */
public class PageNotFoundException extends RuntimeException {
    private int page;
    private String sourcePath;

    public PageNotFoundException(String message, int page, String sourcePath) {
        super(message);
        this.page = page;
        this.sourcePath = sourcePath;
    }

    public int getPage() {
        return this.page;
    }

    public String getSourcePath() {
        return this.sourcePath;
    }
}
