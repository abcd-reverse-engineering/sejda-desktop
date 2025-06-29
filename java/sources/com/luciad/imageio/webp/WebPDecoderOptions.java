package com.luciad.imageio.webp;

/* loaded from: org.sejda.imageio.webp-imageio-0.1.6.jar:com/luciad/imageio/webp/WebPDecoderOptions.class */
public final class WebPDecoderOptions {
    long fPointer = createDecoderOptions();

    private static native long createDecoderOptions();

    private static native void deleteDecoderOptions(long j);

    private static native int getCropHeight(long j);

    private static native void setCropHeight(long j, int i);

    private static native int getCropLeft(long j);

    private static native void setCropLeft(long j, int i);

    private static native int getCropTop(long j);

    private static native void setCropTop(long j, int i);

    private static native int getCropWidth(long j);

    private static native void setCropWidth(long j, int i);

    private static native boolean isForceRotation(long j);

    private static native void setForceRotation(long j, boolean z);

    private static native boolean isNoEnhancement(long j);

    private static native void setNoEnhancement(long j, boolean z);

    private static native boolean isNoFancyUpsampling(long j);

    private static native void setNoFancyUpsampling(long j, boolean z);

    private static native int getScaledHeight(long j);

    private static native void setScaledHeight(long j, int i);

    private static native int getScaledWidth(long j);

    private static native void setScaledWidth(long j, int i);

    private static native boolean isUseCropping(long j);

    private static native void setUseCropping(long j, boolean z);

    private static native boolean isUseScaling(long j);

    private static native void setUseScaling(long j, boolean z);

    private static native boolean isUseThreads(long j);

    private static native void setUseThreads(long j, boolean z);

    private static native boolean isBypassFiltering(long j);

    private static native void setBypassFiltering(long j, boolean z);

    static {
        WebP.loadNativeLibrary();
    }

    public WebPDecoderOptions() {
        if (this.fPointer == 0) {
            throw new OutOfMemoryError();
        }
    }

    protected void finalize() throws Throwable {
        super.finalize();
        deleteDecoderOptions(this.fPointer);
        this.fPointer = 0L;
    }

    public int getCropHeight() {
        return getCropHeight(this.fPointer);
    }

    public void setCropHeight(int aCropHeight) {
        setCropHeight(this.fPointer, aCropHeight);
    }

    public int getCropLeft() {
        return getCropLeft(this.fPointer);
    }

    public void setCropLeft(int aCropLeft) {
        setCropLeft(this.fPointer, aCropLeft);
    }

    public int getCropTop() {
        return getCropTop(this.fPointer);
    }

    public void setCropTop(int aCropTop) {
        setCropTop(this.fPointer, aCropTop);
    }

    public int getCropWidth() {
        return getCropWidth(this.fPointer);
    }

    public void setCropWidth(int aCropWidth) {
        setCropWidth(this.fPointer, aCropWidth);
    }

    public boolean isFancyUpsampling() {
        return !isNoFancyUpsampling(this.fPointer);
    }

    public void setFancyUpsampling(boolean aFancyUpsampling) {
        setNoFancyUpsampling(this.fPointer, !aFancyUpsampling);
    }

    public int getScaledHeight() {
        return getScaledHeight(this.fPointer);
    }

    public void setScaledHeight(int aScaledHeight) {
        setScaledHeight(this.fPointer, aScaledHeight);
    }

    public int getScaledWidth() {
        return getScaledWidth(this.fPointer);
    }

    public void setScaledWidth(int aScaledWidth) {
        setScaledWidth(this.fPointer, aScaledWidth);
    }

    public boolean isUseCropping() {
        return isUseCropping(this.fPointer);
    }

    public void setUseCropping(boolean aUseCropping) {
        setUseCropping(this.fPointer, aUseCropping);
    }

    public boolean isUseScaling() {
        return isUseScaling(this.fPointer);
    }

    public void setUseScaling(boolean aUseScaling) {
        setUseScaling(this.fPointer, aUseScaling);
    }

    public boolean isUseThreads() {
        return isUseThreads(this.fPointer);
    }

    public void setUseThreads(boolean aUseThreads) {
        setUseThreads(this.fPointer, aUseThreads);
    }

    public boolean isBypassFiltering() {
        return isBypassFiltering(this.fPointer);
    }

    public void setBypassFiltering(boolean aBypassFiltering) {
        setBypassFiltering(this.fPointer, aBypassFiltering);
    }
}
