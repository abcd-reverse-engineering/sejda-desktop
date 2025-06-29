package org.sejda.sambox.pdmodel.encryption;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/encryption/RC4Cipher.class */
class RC4Cipher {
    private final int[] salt = new int[PDAnnotation.FLAG_TOGGLE_NO_VIEW];
    private int b;
    private int c;

    RC4Cipher() {
    }

    public void setKey(byte[] key) {
        this.b = 0;
        this.c = 0;
        if (key.length < 1 || key.length > 32) {
            throw new IllegalArgumentException("number of bytes must be between 1 and 32");
        }
        for (int i = 0; i < this.salt.length; i++) {
            this.salt[i] = i;
        }
        int keyIndex = 0;
        int saltIndex = 0;
        for (int i2 = 0; i2 < this.salt.length; i2++) {
            saltIndex = ((fixByte(key[keyIndex]) + this.salt[i2]) + saltIndex) % PDAnnotation.FLAG_TOGGLE_NO_VIEW;
            swap(this.salt, i2, saltIndex);
            keyIndex = (keyIndex + 1) % key.length;
        }
    }

    private static int fixByte(byte aByte) {
        return aByte < 0 ? PDAnnotation.FLAG_TOGGLE_NO_VIEW + aByte : aByte;
    }

    private static void swap(int[] data, int firstIndex, int secondIndex) {
        int tmp = data[firstIndex];
        data[firstIndex] = data[secondIndex];
        data[secondIndex] = tmp;
    }

    public void write(byte aByte, OutputStream output) throws IOException {
        this.b = (this.b + 1) % PDAnnotation.FLAG_TOGGLE_NO_VIEW;
        this.c = (this.salt[this.b] + this.c) % PDAnnotation.FLAG_TOGGLE_NO_VIEW;
        swap(this.salt, this.b, this.c);
        int saltIndex = (this.salt[this.b] + this.salt[this.c]) % PDAnnotation.FLAG_TOGGLE_NO_VIEW;
        output.write(aByte ^ ((byte) this.salt[saltIndex]));
    }

    public void write(byte[] data, OutputStream output) throws IOException {
        for (byte b : data) {
            write(b, output);
        }
    }

    public void write(InputStream data, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024];
        while (true) {
            int amountRead = data.read(buffer);
            if (amountRead != -1) {
                write(buffer, 0, amountRead, output);
            } else {
                return;
            }
        }
    }

    public void write(byte[] data, int offset, int len, OutputStream output) throws IOException {
        for (int i = offset; i < offset + len; i++) {
            write(data[i], output);
        }
    }
}
