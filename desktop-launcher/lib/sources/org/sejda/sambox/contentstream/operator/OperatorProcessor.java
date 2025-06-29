package org.sejda.sambox.contentstream.operator;

import java.io.IOException;
import java.util.List;
import org.sejda.sambox.contentstream.PDFStreamEngine;
import org.sejda.sambox.cos.COSBase;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/operator/OperatorProcessor.class */
public abstract class OperatorProcessor {
    private PDFStreamEngine context;

    public abstract void process(Operator operator, List<COSBase> list) throws IOException;

    public abstract String getName();

    protected OperatorProcessor() {
    }

    public PDFStreamEngine getContext() {
        return this.context;
    }

    public void setContext(PDFStreamEngine context) {
        this.context = context;
    }

    public static boolean checkArrayTypesClass(List<COSBase> operands, Class<?> clazz) {
        for (COSBase base : operands) {
            if (!clazz.isInstance(base)) {
                return false;
            }
        }
        return true;
    }
}
