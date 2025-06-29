package com.luciad.imageio.webp;

/* loaded from: org.sejda.imageio.webp-imageio-0.1.6.jar:com/luciad/imageio/webp/WebPEncoderOptions.class */
public class WebPEncoderOptions {
    long fPointer = createConfig();

    private static native long createConfig();

    private static native void deleteConfig(long j);

    private static native float getQuality(long j);

    private static native void setQuality(long j, float f);

    private static native int getTargetSize(long j);

    private static native void setTargetSize(long j, int i);

    private static native float getTargetPSNR(long j);

    private static native void setTargetPSNR(long j, float f);

    private static native int getMethod(long j);

    private static native void setMethod(long j, int i);

    private static native int getSegments(long j);

    private static native void setSegments(long j, int i);

    private static native int getSnsStrength(long j);

    private static native void setSnsStrength(long j, int i);

    private static native int getFilterStrength(long j);

    private static native void setFilterStrength(long j, int i);

    private static native int getFilterSharpness(long j);

    private static native void setFilterSharpness(long j, int i);

    private static native int getFilterType(long j);

    private static native void setFilterType(long j, int i);

    private static native int getAutofilter(long j);

    private static native void setAutofilter(long j, int i);

    private static native int getPass(long j);

    private static native void setPass(long j, int i);

    private static native int getShowCompressed(long j);

    private static native void setShowCompressed(long j, int i);

    private static native int getPreprocessing(long j);

    private static native void setPreprocessing(long j, int i);

    private static native int getPartitions(long j);

    private static native void setPartitions(long j, int i);

    private static native int getPartitionLimit(long j);

    private static native void setPartitionLimit(long j, int i);

    private static native int getAlphaCompression(long j);

    private static native void setAlphaCompression(long j, int i);

    private static native int getAlphaFiltering(long j);

    private static native void setAlphaFiltering(long j, int i);

    private static native int getAlphaQuality(long j);

    private static native void setAlphaQuality(long j, int i);

    private static native int getLossless(long j);

    private static native void setLossless(long j, int i);

    private static native int getEmulateJpegSize(long j);

    private static native void setEmulateJpegSize(long j, int i);

    private static native int getThreadLevel(long j);

    private static native void setThreadLevel(long j, int i);

    private static native int getLowMemory(long j);

    private static native void setLowMemory(long j, int i);

    static {
        WebP.loadNativeLibrary();
    }

    public WebPEncoderOptions() {
        if (this.fPointer == 0) {
            throw new OutOfMemoryError();
        }
    }

    protected void finalize() throws Throwable {
        super.finalize();
        deleteConfig(this.fPointer);
        this.fPointer = 0L;
    }

    long getPointer() {
        return this.fPointer;
    }

    public float getCompressionQuality() {
        return getQuality(this.fPointer);
    }

    public void setCompressionQuality(float quality) {
        setQuality(this.fPointer, quality);
    }

    public boolean isLossless() {
        return getLossless(this.fPointer) != 0;
    }

    public void setLossless(boolean aLossless) {
        setLossless(this.fPointer, aLossless ? 1 : 0);
    }

    public int getTargetSize() {
        return getTargetSize(this.fPointer);
    }

    public void setTargetSize(int aTargetSize) {
        setTargetSize(this.fPointer, aTargetSize);
    }

    public float getTargetPSNR() {
        return getTargetPSNR(this.fPointer);
    }

    public void setTargetPSNR(float aTargetPSNR) {
        setTargetPSNR(this.fPointer, aTargetPSNR);
    }

    public int getMethod() {
        return getMethod(this.fPointer);
    }

    public void setMethod(int aMethod) {
        setMethod(this.fPointer, aMethod);
    }

    public int getSegments() {
        return getSegments(this.fPointer);
    }

    public void setSegments(int aSegments) {
        setSegments(this.fPointer, aSegments);
    }

    public int getSnsStrength() {
        return getSnsStrength(this.fPointer);
    }

    public void setSnsStrength(int aSnsStrength) {
        setSnsStrength(this.fPointer, aSnsStrength);
    }

    public int getFilterStrength() {
        return getFilterStrength(this.fPointer);
    }

    public void setFilterStrength(int aFilterStrength) {
        setFilterStrength(this.fPointer, aFilterStrength);
    }

    public int getFilterSharpness() {
        return getFilterSharpness(this.fPointer);
    }

    public void setFilterSharpness(int aFilterSharpness) {
        setFilterSharpness(this.fPointer, aFilterSharpness);
    }

    public int getFilterType() {
        return getFilterType(this.fPointer);
    }

    public void setFilterType(int aFilterType) {
        setFilterType(this.fPointer, aFilterType);
    }

    public boolean isAutoAdjustFilterStrength() {
        return getAutofilter(this.fPointer) != 0;
    }

    public void setAutoAdjustFilterStrength(boolean aAutofilter) {
        setAutofilter(this.fPointer, aAutofilter ? 1 : 0);
    }

    public int getEntropyAnalysisPassCount() {
        return getPass(this.fPointer);
    }

    public void setEntropyAnalysisPassCount(int aPass) {
        setPass(this.fPointer, aPass);
    }

    public boolean isShowCompressed() {
        return getShowCompressed(this.fPointer) != 0;
    }

    public void setShowCompressed(boolean aShowCompressed) {
        setShowCompressed(this.fPointer, aShowCompressed ? 1 : 0);
    }

    public int getPreprocessing() {
        return getPreprocessing(this.fPointer);
    }

    public void setPreprocessing(int aPreprocessing) {
        setPreprocessing(this.fPointer, aPreprocessing);
    }

    public int getPartitions() {
        return getPartitions(this.fPointer);
    }

    public void setPartitions(int aPartitions) {
        setPartitions(this.fPointer, aPartitions);
    }

    public int getPartitionLimit() {
        return getPartitionLimit(this.fPointer);
    }

    public void setPartitionLimit(int aPartitionLimit) {
        setPartitionLimit(this.fPointer, aPartitionLimit);
    }

    public int getAlphaCompression() {
        return getAlphaCompression(this.fPointer);
    }

    public void setAlphaCompression(int aAlphaCompression) {
        setAlphaCompression(this.fPointer, aAlphaCompression);
    }

    public int getAlphaFiltering() {
        return getAlphaFiltering(this.fPointer);
    }

    public void setAlphaFiltering(int aAlphaFiltering) {
        setAlphaFiltering(this.fPointer, aAlphaFiltering);
    }

    public int getAlphaQuality() {
        return getAlphaQuality(this.fPointer);
    }

    public void setAlphaQuality(int aAlphaQuality) {
        setAlphaQuality(this.fPointer, aAlphaQuality);
    }

    public boolean isEmulateJpegSize() {
        return getEmulateJpegSize(this.fPointer) != 0;
    }

    public void setEmulateJpegSize(boolean aEmulateJpegSize) {
        setEmulateJpegSize(this.fPointer, aEmulateJpegSize ? 1 : 0);
    }

    public int getThreadLevel() {
        return getThreadLevel(this.fPointer);
    }

    public void setThreadLevel(int aThreadLevel) {
        setThreadLevel(this.fPointer, aThreadLevel);
    }

    public boolean isReduceMemoryUsage() {
        return getLowMemory(this.fPointer) != 0;
    }

    public void setReduceMemoryUsage(boolean aLowMemory) {
        setLowMemory(this.fPointer, aLowMemory ? 1 : 0);
    }
}
