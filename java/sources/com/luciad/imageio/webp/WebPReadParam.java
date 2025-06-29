package com.luciad.imageio.webp;

import javax.imageio.ImageReadParam;

/* loaded from: org.sejda.imageio.webp-imageio-0.1.6.jar:com/luciad/imageio/webp/WebPReadParam.class */
public final class WebPReadParam extends ImageReadParam {
    private WebPDecoderOptions fOptions = new WebPDecoderOptions();

    public void setScaledHeight(int aScaledHeight) {
        this.fOptions.setScaledHeight(aScaledHeight);
    }

    public void setUseScaling(boolean aUseScaling) {
        this.fOptions.setUseScaling(aUseScaling);
    }

    public void setUseThreads(boolean aUseThreads) {
        this.fOptions.setUseThreads(aUseThreads);
    }

    public int getCropHeight() {
        return this.fOptions.getCropHeight();
    }

    public int getScaledWidth() {
        return this.fOptions.getScaledWidth();
    }

    public boolean isUseCropping() {
        return this.fOptions.isUseCropping();
    }

    public void setCropWidth(int aCropWidth) {
        this.fOptions.setCropWidth(aCropWidth);
    }

    public boolean isBypassFiltering() {
        return this.fOptions.isBypassFiltering();
    }

    public int getCropLeft() {
        return this.fOptions.getCropLeft();
    }

    public int getCropWidth() {
        return this.fOptions.getCropWidth();
    }

    public int getScaledHeight() {
        return this.fOptions.getScaledHeight();
    }

    public void setBypassFiltering(boolean aBypassFiltering) {
        this.fOptions.setBypassFiltering(aBypassFiltering);
    }

    public void setUseCropping(boolean aUseCropping) {
        this.fOptions.setUseCropping(aUseCropping);
    }

    public void setCropHeight(int aCropHeight) {
        this.fOptions.setCropHeight(aCropHeight);
    }

    public void setFancyUpsampling(boolean aFancyUpsampling) {
        this.fOptions.setFancyUpsampling(aFancyUpsampling);
    }

    public boolean isUseThreads() {
        return this.fOptions.isUseThreads();
    }

    public boolean isFancyUpsampling() {
        return this.fOptions.isFancyUpsampling();
    }

    public boolean isUseScaling() {
        return this.fOptions.isUseScaling();
    }

    public void setCropLeft(int aCropLeft) {
        this.fOptions.setCropLeft(aCropLeft);
    }

    public int getCropTop() {
        return this.fOptions.getCropTop();
    }

    public void setScaledWidth(int aScaledWidth) {
        this.fOptions.setScaledWidth(aScaledWidth);
    }

    public void setCropTop(int aCropTop) {
        this.fOptions.setCropTop(aCropTop);
    }

    WebPDecoderOptions getDecoderOptions() {
        return this.fOptions;
    }
}
