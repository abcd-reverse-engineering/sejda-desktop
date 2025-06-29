package org.sejda.sambox.pdmodel.common.function;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.pdmodel.common.PDRange;
import org.sejda.sambox.pdmodel.common.function.type4.ExecutionContext;
import org.sejda.sambox.pdmodel.common.function.type4.InstructionSequence;
import org.sejda.sambox.pdmodel.common.function.type4.InstructionSequenceBuilder;
import org.sejda.sambox.pdmodel.common.function.type4.Operators;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/common/function/PDFunctionType4.class */
public class PDFunctionType4 extends PDFunction {
    private static final Operators OPERATORS = new Operators();
    private final InstructionSequence instructions;

    public PDFunctionType4(COSBase functionStream) throws IOException {
        super(functionStream);
        byte[] bytes = getPDStream().toByteArray();
        String string = new String(bytes, StandardCharsets.ISO_8859_1);
        this.instructions = InstructionSequenceBuilder.parse(string);
    }

    @Override // org.sejda.sambox.pdmodel.common.function.PDFunction
    public int getFunctionType() {
        return 4;
    }

    @Override // org.sejda.sambox.pdmodel.common.function.PDFunction
    public float[] eval(float[] input) {
        ExecutionContext context = new ExecutionContext(OPERATORS);
        for (int i = 0; i < input.length; i++) {
            PDRange domain = getDomainForInput(i);
            float value = clipToRange(input[i], domain.getMin(), domain.getMax());
            context.getStack().push(Float.valueOf(value));
        }
        this.instructions.execute(context);
        int numberOfOutputValues = getNumberOfOutputParameters();
        int numberOfActualOutputValues = context.getStack().size();
        if (numberOfActualOutputValues < numberOfOutputValues) {
            throw new IllegalStateException("The type 4 function returned " + numberOfActualOutputValues + " values but the Range entry indicates that " + numberOfOutputValues + " values be returned.");
        }
        float[] outputValues = new float[numberOfOutputValues];
        for (int i2 = numberOfOutputValues - 1; i2 >= 0; i2--) {
            PDRange range = getRangeForOutput(i2);
            outputValues[i2] = context.popReal();
            outputValues[i2] = clipToRange(outputValues[i2], range.getMin(), range.getMax());
        }
        return outputValues;
    }
}
