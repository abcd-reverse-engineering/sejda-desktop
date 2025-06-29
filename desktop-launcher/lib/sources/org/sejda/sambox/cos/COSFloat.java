package org.sejda.sambox.cos;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.regex.Pattern;
import org.sejda.commons.util.RequireUtils;
import org.sejda.sambox.pdmodel.documentinterchange.taggedpdf.PDLayoutAttributeObject;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/cos/COSFloat.class */
public class COSFloat extends COSNumber {
    private static final Pattern DOTS = Pattern.compile("\\.");
    private static final Pattern EXP_END = Pattern.compile("[e|E]$");
    private static final Pattern NUM1 = Pattern.compile("^(-)([-|+]+)\\d+\\.\\d+");
    private static final Pattern NUM2 = Pattern.compile("^(-)([\\-|+]+)");
    private static final Pattern NUM3 = Pattern.compile("^0\\.0*-\\d+");
    private static final Pattern ZERO = Pattern.compile("^0-(\\.|\\d+)*");
    private static final Pattern MINUS = Pattern.compile("-");
    private float value;
    private String stringValue;

    public COSFloat(float value) {
        this.value = coerce(value);
        this.stringValue = formatString();
    }

    public COSFloat(String aFloat) throws NumberFormatException, IOException {
        try {
            float parsedValue = Float.parseFloat(aFloat);
            this.value = coerce(parsedValue);
            if (parsedValue == this.value) {
                this.stringValue = aFloat;
            }
        } catch (NumberFormatException e) {
            try {
                int dot = aFloat.indexOf(46);
                aFloat = EXP_END.matcher(dot != aFloat.lastIndexOf(46) ? aFloat.substring(0, dot + 1) + DOTS.matcher(aFloat.substring(dot + 1)).replaceAll(PDLayoutAttributeObject.GLYPH_ORIENTATION_VERTICAL_ZERO_DEGREES) : aFloat).replaceAll("");
                this.value = coerce(Float.parseFloat(aFloat));
            } catch (NumberFormatException e2) {
                try {
                    if (NUM1.matcher(aFloat).matches()) {
                        this.value = coerce(Float.parseFloat(NUM2.matcher(aFloat).replaceFirst("-")));
                    } else if (ZERO.matcher(aFloat).matches()) {
                        this.value = 0.0f;
                    } else {
                        RequireUtils.requireIOCondition(NUM3.matcher(aFloat).matches(), "Expected floating point number but found '" + aFloat + "'");
                        this.value = coerce(Float.parseFloat("-" + MINUS.matcher(aFloat).replaceFirst("")));
                    }
                } catch (NumberFormatException e22) {
                    throw new IOException("Error expected floating point number actual='" + aFloat + "'", e22);
                }
            }
        }
        if (Objects.isNull(this.stringValue)) {
            this.stringValue = formatString();
        }
    }

    private float coerce(float floatValue) {
        if (floatValue == Float.POSITIVE_INFINITY) {
            return Float.MAX_VALUE;
        }
        if (floatValue == Float.NEGATIVE_INFINITY) {
            return -3.4028235E38f;
        }
        if (Math.abs(floatValue) < Float.MIN_NORMAL) {
            return 0.0f;
        }
        return floatValue;
    }

    private String formatString() {
        String s = this.value == ((float) ((long) this.value)) ? String.valueOf((long) this.value) : String.valueOf(this.value);
        if (s.indexOf(69) < 0) {
            return s;
        }
        return new BigDecimal(s).stripTrailingZeros().toPlainString();
    }

    @Override // org.sejda.sambox.cos.COSNumber
    public float floatValue() {
        return this.value;
    }

    @Override // org.sejda.sambox.cos.COSNumber
    public double doubleValue() {
        return this.value;
    }

    @Override // org.sejda.sambox.cos.COSNumber
    public long longValue() {
        return (long) this.value;
    }

    @Override // org.sejda.sambox.cos.COSNumber
    public int intValue() {
        return (int) this.value;
    }

    public boolean equals(Object o) {
        if (o instanceof COSFloat) {
            COSFloat cosfloat = (COSFloat) o;
            if (Float.floatToIntBits(cosfloat.value) == Float.floatToIntBits(this.value)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Float.hashCode(this.value);
    }

    public String toString() {
        return this.stringValue;
    }

    @Override // org.sejda.sambox.cos.COSBase
    public void accept(COSVisitor visitor) throws IOException {
        visitor.visit(this);
    }
}
