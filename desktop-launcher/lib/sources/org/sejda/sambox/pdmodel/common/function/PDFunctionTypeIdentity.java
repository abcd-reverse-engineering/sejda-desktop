package org.sejda.sambox.pdmodel.common.function;

import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/common/function/PDFunctionTypeIdentity.class */
public class PDFunctionTypeIdentity extends PDFunction {
    public PDFunctionTypeIdentity(COSBase function) {
        super(null);
    }

    @Override // org.sejda.sambox.pdmodel.common.function.PDFunction
    public int getFunctionType() {
        throw new UnsupportedOperationException();
    }

    @Override // org.sejda.sambox.pdmodel.common.function.PDFunction
    public float[] eval(float[] input) {
        return input;
    }

    @Override // org.sejda.sambox.pdmodel.common.function.PDFunction
    protected COSArray getRangeValues() {
        return null;
    }

    @Override // org.sejda.sambox.pdmodel.common.function.PDFunction
    public String toString() {
        return "FunctionTypeIdentity";
    }
}
