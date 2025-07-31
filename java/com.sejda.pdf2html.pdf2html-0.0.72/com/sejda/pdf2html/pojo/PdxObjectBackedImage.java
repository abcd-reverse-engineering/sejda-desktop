package com.sejda.pdf2html.pojo;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.Callable;

/* loaded from: com.sejda.pdf2html.pdf2html-0.0.72.jar:com/sejda/pdf2html/pojo/PdxObjectBackedImage.class */
public class PdxObjectBackedImage implements Image {
    private Optional<File> src;
    private final float x;
    private final float y;
    private final String name;
    private int width;
    private int height;
    private final Callable<LazyImageProps> imageProducer;

    public PdxObjectBackedImage(Callable<LazyImageProps> imageProducer, float x, float y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.imageProducer = imageProducer;
    }

    public synchronized void processImageIfRequired() throws Exception {
        if (this.src != null) {
            return;
        }
        try {
            LazyImageProps props = this.imageProducer.call();
            this.src = props.src;
            this.width = props.width;
            this.height = props.height;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override // com.sejda.pdf2html.pojo.Image
    public String getName() {
        return this.name;
    }

    @Override // com.sejda.pdf2html.pojo.Image
    public String getSrc() throws Exception {
        processImageIfRequired();
        return this.src.get().getAbsolutePath();
    }

    @Override // com.sejda.pdf2html.pojo.Image
    public boolean hasSrc() throws Exception {
        processImageIfRequired();
        return this.src.isPresent();
    }

    @Override // com.sejda.pdf2html.pojo.Image
    public float getX() {
        return this.x;
    }

    @Override // com.sejda.pdf2html.pojo.Image
    public float getY() {
        return this.y;
    }

    @Override // com.sejda.pdf2html.pojo.Image
    public int getWidth() throws Exception {
        processImageIfRequired();
        return this.width;
    }

    @Override // com.sejda.pdf2html.pojo.Image
    public int getHeight() throws Exception {
        processImageIfRequired();
        return this.height;
    }
}
