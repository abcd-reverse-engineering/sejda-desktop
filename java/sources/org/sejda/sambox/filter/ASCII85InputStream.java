package org.sejda.sambox.filter;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/filter/ASCII85InputStream.class */
final class ASCII85InputStream extends FilterInputStream {
    private int index;
    private int n;
    private boolean eof;
    private byte[] ascii;
    private byte[] b;
    private static final char TERMINATOR = '~';
    private static final char OFFSET = '!';
    private static final char NEWLINE = '\n';
    private static final char RETURN = '\r';
    private static final char SPACE = ' ';
    private static final char PADDING_U = 'u';
    private static final char Z = 'z';

    ASCII85InputStream(InputStream is) {
        super(is);
        this.index = 0;
        this.n = 0;
        this.eof = false;
        this.ascii = new byte[5];
        this.b = new byte[4];
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x00c1, code lost:
    
        r11.ascii[r12] = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00cb, code lost:
    
        if (r0 != org.sejda.sambox.filter.ASCII85InputStream.TERMINATOR) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00ce, code lost:
    
        r11.ascii[r12] = org.sejda.sambox.filter.ASCII85InputStream.PADDING_U;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00df, code lost:
    
        r11.n = r12 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00ea, code lost:
    
        if (r11.n != 0) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00ed, code lost:
    
        r11.eof = true;
        r11.ascii = null;
        r11.b = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00fd, code lost:
    
        return -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0100, code lost:
    
        if (r12 >= 5) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0106, code lost:
    
        r12 = r12 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0108, code lost:
    
        if (r12 >= 5) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x010b, code lost:
    
        r11.ascii[r12] = org.sejda.sambox.filter.ASCII85InputStream.PADDING_U;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0119, code lost:
    
        r11.eof = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x011e, code lost:
    
        r14 = 0;
        r12 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0124, code lost:
    
        if (r12 >= 5) goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0127, code lost:
    
        r0 = (byte) (r11.ascii[r12] - org.sejda.sambox.filter.ASCII85InputStream.OFFSET);
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0133, code lost:
    
        if (r0 < 0) goto L89;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0139, code lost:
    
        if (r0 <= 93) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x013c, code lost:
    
        r11.n = 0;
        r11.eof = true;
        r11.ascii = null;
        r11.b = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0159, code lost:
    
        throw new java.io.IOException("Invalid data in Ascii85 stream");
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x015a, code lost:
    
        r14 = (r14 * 85) + r0;
        r12 = r12 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0169, code lost:
    
        r12 = 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x016c, code lost:
    
        if (r12 < 0) goto L91;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x016f, code lost:
    
        r11.b[r12] = (byte) (r14 & 255);
        r14 = r14 >>> 8;
        r12 = r12 - 1;
     */
    @Override // java.io.FilterInputStream, java.io.InputStream
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int read() throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 412
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.sejda.sambox.filter.ASCII85InputStream.read():int");
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] data, int offset, int len) throws IOException {
        if (this.eof && this.index >= this.n) {
            return -1;
        }
        for (int i = 0; i < len; i++) {
            if (this.index < this.n) {
                byte[] bArr = this.b;
                int i2 = this.index;
                this.index = i2 + 1;
                data[i + offset] = bArr[i2];
            } else {
                int t = read();
                if (t == -1) {
                    return i;
                }
                data[i + offset] = (byte) t;
            }
        }
        return len;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.ascii = null;
        this.eof = true;
        this.b = null;
        super.close();
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public boolean markSupported() {
        return false;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public long skip(long nValue) {
        return 0L;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int available() {
        return 0;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public synchronized void mark(int readlimit) {
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public synchronized void reset() throws IOException {
        throw new IOException("Reset is not supported");
    }
}
