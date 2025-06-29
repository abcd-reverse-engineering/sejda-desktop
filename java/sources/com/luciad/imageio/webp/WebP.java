package com.luciad.imageio.webp;

import java.io.IOException;
import java.nio.ByteOrder;

/* loaded from: org.sejda.imageio.webp-imageio-0.1.6.jar:com/luciad/imageio/webp/WebP.class */
final class WebP {
    private static boolean NATIVE_LIBRARY_LOADED = false;

    private static native int[] decode(long j, byte[] bArr, int i, int i2, int[] iArr, boolean z);

    private static native int getInfo(byte[] bArr, int i, int i2, int[] iArr);

    private static native byte[] encodeRGBA(long j, byte[] bArr, int i, int i2, int i3);

    private static native byte[] encodeRGB(long j, byte[] bArr, int i, int i2, int i3);

    static {
        loadNativeLibrary();
    }

    static synchronized void loadNativeLibrary() throws IOException {
        if (!NATIVE_LIBRARY_LOADED) {
            NativeLibraryUtils.loadFromJar();
            NATIVE_LIBRARY_LOADED = true;
        }
    }

    private WebP() {
    }

    public static int[] decode(WebPDecoderOptions aOptions, byte[] aData, int aOffset, int aLength, int[] aOut) throws IOException {
        if (aOptions == null) {
            throw new NullPointerException("Decoder options may not be null");
        }
        if (aData == null) {
            throw new NullPointerException("Input data may not be null");
        }
        if (aOffset + aLength > aData.length) {
            throw new IllegalArgumentException("Offset/length exceeds array size");
        }
        int[] pixels = decode(aOptions.fPointer, aData, aOffset, aLength, aOut, ByteOrder.nativeOrder().equals(ByteOrder.BIG_ENDIAN));
        VP8StatusCode status = VP8StatusCode.getStatusCode(aOut[0]);
        switch (status) {
            case VP8_STATUS_OK:
                return pixels;
            case VP8_STATUS_OUT_OF_MEMORY:
                throw new OutOfMemoryError();
            default:
                throw new IOException("Decode returned code " + status);
        }
    }

    public static int[] getInfo(byte[] aData, int aOffset, int aLength) throws IOException {
        int[] out = new int[2];
        int result = getInfo(aData, aOffset, aLength, out);
        if (result == 0) {
            throw new IOException("Invalid WebP data");
        }
        return out;
    }

    public static byte[] encodeRGBA(WebPEncoderOptions aOptions, byte[] aRgbaData, int aWidth, int aHeight, int aStride) {
        return encodeRGBA(aOptions.fPointer, aRgbaData, aWidth, aHeight, aStride);
    }

    public static byte[] encodeRGB(WebPEncoderOptions aOptions, byte[] aRgbaData, int aWidth, int aHeight, int aStride) {
        return encodeRGB(aOptions.fPointer, aRgbaData, aWidth, aHeight, aStride);
    }
}
