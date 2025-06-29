package com.luciad.imageio.webp;

import java.util.Locale;
import javax.imageio.ImageWriteParam;

/* loaded from: org.sejda.imageio.webp-imageio-0.1.6.jar:com/luciad/imageio/webp/WebPWriteParam.class */
public class WebPWriteParam extends ImageWriteParam {
    public static final int LOSSY_COMPRESSION = 0;
    public static final int LOSSLESS_COMPRESSION = 1;
    private final boolean fDefaultLossless;
    private WebPEncoderOptions fOptions;

    public WebPWriteParam(Locale aLocale) {
        super(aLocale);
        this.fOptions = new WebPEncoderOptions();
        this.fDefaultLossless = this.fOptions.isLossless();
        this.canWriteCompressed = true;
        this.compressionTypes = new String[]{"Lossy", "Lossless"};
        this.compressionType = this.compressionTypes[this.fDefaultLossless ? (char) 1 : (char) 0];
        this.compressionQuality = this.fOptions.getCompressionQuality() / 100.0f;
        this.compressionMode = 2;
    }

    public float getCompressionQuality() {
        return super.getCompressionQuality();
    }

    public void setCompressionQuality(float quality) {
        super.setCompressionQuality(quality);
        this.fOptions.setCompressionQuality(quality * 100.0f);
    }

    public void setCompressionType(String compressionType) {
        super.setCompressionType(compressionType);
        int i = 0;
        while (i < this.compressionTypes.length) {
            if (!this.compressionTypes[i].equals(compressionType)) {
                i++;
            } else {
                this.fOptions.setLossless(i == 1);
                return;
            }
        }
    }

    public void unsetCompression() {
        super.unsetCompression();
        this.fOptions.setLossless(this.fDefaultLossless);
    }

    public void setSnsStrength(int aSnsStrength) {
        this.fOptions.setSnsStrength(aSnsStrength);
    }

    public void setAlphaQuality(int aAlphaQuality) {
        this.fOptions.setAlphaQuality(aAlphaQuality);
    }

    public int getSegments() {
        return this.fOptions.getSegments();
    }

    public int getPreprocessing() {
        return this.fOptions.getPreprocessing();
    }

    public int getFilterStrength() {
        return this.fOptions.getFilterStrength();
    }

    public void setEmulateJpegSize(boolean aEmulateJpegSize) {
        this.fOptions.setEmulateJpegSize(aEmulateJpegSize);
    }

    public int getPartitions() {
        return this.fOptions.getPartitions();
    }

    public void setTargetPSNR(float aTargetPSNR) {
        this.fOptions.setTargetPSNR(aTargetPSNR);
    }

    public int getEntropyAnalysisPassCount() {
        return this.fOptions.getEntropyAnalysisPassCount();
    }

    public int getPartitionLimit() {
        return this.fOptions.getPartitionLimit();
    }

    public int getFilterType() {
        return this.fOptions.getFilterType();
    }

    public int getFilterSharpness() {
        return this.fOptions.getFilterSharpness();
    }

    public int getAlphaQuality() {
        return this.fOptions.getAlphaQuality();
    }

    public boolean isShowCompressed() {
        return this.fOptions.isShowCompressed();
    }

    public boolean isReduceMemoryUsage() {
        return this.fOptions.isReduceMemoryUsage();
    }

    public void setThreadLevel(int aThreadLevel) {
        this.fOptions.setThreadLevel(aThreadLevel);
    }

    public boolean isAutoAdjustFilterStrength() {
        return this.fOptions.isAutoAdjustFilterStrength();
    }

    public void setReduceMemoryUsage(boolean aLowMemory) {
        this.fOptions.setReduceMemoryUsage(aLowMemory);
    }

    public void setFilterStrength(int aFilterStrength) {
        this.fOptions.setFilterStrength(aFilterStrength);
    }

    public int getTargetSize() {
        return this.fOptions.getTargetSize();
    }

    public void setEntropyAnalysisPassCount(int aPass) {
        this.fOptions.setEntropyAnalysisPassCount(aPass);
    }

    public void setFilterSharpness(int aFilterSharpness) {
        this.fOptions.setFilterSharpness(aFilterSharpness);
    }

    public int getAlphaFiltering() {
        return this.fOptions.getAlphaFiltering();
    }

    public int getSnsStrength() {
        return this.fOptions.getSnsStrength();
    }

    public void setPartitionLimit(int aPartitionLimit) {
        this.fOptions.setPartitionLimit(aPartitionLimit);
    }

    public void setMethod(int aMethod) {
        this.fOptions.setMethod(aMethod);
    }

    public void setAlphaFiltering(int aAlphaFiltering) {
        this.fOptions.setAlphaFiltering(aAlphaFiltering);
    }

    public int getMethod() {
        return this.fOptions.getMethod();
    }

    public void setFilterType(int aFilterType) {
        this.fOptions.setFilterType(aFilterType);
    }

    public void setPartitions(int aPartitions) {
        this.fOptions.setPartitions(aPartitions);
    }

    public void setAutoAdjustFilterStrength(boolean aAutofilter) {
        this.fOptions.setAutoAdjustFilterStrength(aAutofilter);
    }

    public boolean isEmulateJpegSize() {
        return this.fOptions.isEmulateJpegSize();
    }

    public int getAlphaCompression() {
        return this.fOptions.getAlphaCompression();
    }

    public void setShowCompressed(boolean aShowCompressed) {
        this.fOptions.setShowCompressed(aShowCompressed);
    }

    public void setSegments(int aSegments) {
        this.fOptions.setSegments(aSegments);
    }

    public float getTargetPSNR() {
        return this.fOptions.getTargetPSNR();
    }

    public int getThreadLevel() {
        return this.fOptions.getThreadLevel();
    }

    public void setTargetSize(int aTargetSize) {
        this.fOptions.setTargetSize(aTargetSize);
    }

    public void setAlphaCompression(int aAlphaCompression) {
        this.fOptions.setAlphaCompression(aAlphaCompression);
    }

    public void setPreprocessing(int aPreprocessing) {
        this.fOptions.setPreprocessing(aPreprocessing);
    }

    WebPEncoderOptions getEncoderOptions() {
        return this.fOptions;
    }
}
