package org.sejda.sambox.cos;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;
import org.sejda.commons.FastByteArrayOutputStream;
import org.sejda.sambox.util.Hex;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/cos/COSString.class */
public final class COSString extends COSBase implements Encryptable {
    private byte[] bytes;
    private boolean forceHexForm;
    private boolean encryptable = true;

    public COSString(byte[] bytes) {
        this.bytes = bytes;
    }

    public void setValue(byte[] value) {
        this.bytes = Arrays.copyOf(value, value.length);
    }

    public void setForceHexForm(boolean value) {
        this.forceHexForm = value;
    }

    public boolean isForceHexForm() {
        return this.forceHexForm || !isAscii();
    }

    private boolean isAscii() {
        for (byte b : this.bytes) {
            if (b < 0 || b == 13 || b == 10) {
                return false;
            }
        }
        return true;
    }

    public String getString() {
        if (this.bytes.length >= 2) {
            if ((this.bytes[0] & 255) == 254 && (this.bytes[1] & 255) == 255) {
                return new String(this.bytes, 2, this.bytes.length - 2, StandardCharsets.UTF_16BE);
            }
            if ((this.bytes[0] & 255) == 255 && (this.bytes[1] & 255) == 254) {
                return new String(this.bytes, 2, this.bytes.length - 2, StandardCharsets.UTF_16LE);
            }
        }
        return PDFDocEncoding.toString(this.bytes);
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public String toHexString() {
        return Hex.getString(this.bytes);
    }

    @Override // org.sejda.sambox.cos.Encryptable
    public boolean encryptable() {
        return this.encryptable;
    }

    @Override // org.sejda.sambox.cos.Encryptable
    public void encryptable(boolean encryptable) {
        this.encryptable = encryptable;
    }

    @Override // org.sejda.sambox.cos.COSBase
    public void accept(COSVisitor visitor) throws IOException {
        visitor.visit(this);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof COSString)) {
            return false;
        }
        COSString strObj = (COSString) obj;
        return getString().equals(strObj.getString()) && this.forceHexForm == strObj.forceHexForm;
    }

    public int hashCode() {
        return Arrays.hashCode(this.bytes) + (this.forceHexForm ? 17 : 0);
    }

    public String toString() {
        return "COSString{" + getString() + "}";
    }

    public static COSString newInstance(byte[] value) {
        return new COSString(value);
    }

    public static COSString parseLiteral(String literal) {
        return (COSString) Optional.ofNullable(PDFDocEncoding.getBytes(literal)).map(COSString::new).orElseGet(() -> {
            byte[] data = literal.getBytes(StandardCharsets.UTF_16BE);
            byte[] bytes = new byte[data.length + 2];
            bytes[0] = -2;
            bytes[1] = -1;
            System.arraycopy(data, 0, bytes, 2, data.length);
            return new COSString(bytes);
        });
    }

    public static COSString parseHex(String hex) throws IOException {
        StringBuilder hexBuffer = new StringBuilder(hex.trim());
        if (hexBuffer.length() % 2 != 0) {
            hexBuffer.append('0');
        }
        FastByteArrayOutputStream bytes = new FastByteArrayOutputStream();
        for (int i = 0; i < hexBuffer.length(); i += 2) {
            try {
                try {
                    bytes.write(Integer.parseInt(hexBuffer.substring(i, i + 2), 16));
                } catch (NumberFormatException e) {
                    throw new IOException("Invalid hex string: " + hex, e);
                }
            } catch (Throwable th) {
                try {
                    bytes.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        COSString retVal = new COSString(bytes.toByteArray());
        retVal.setForceHexForm(true);
        bytes.close();
        return retVal;
    }
}
