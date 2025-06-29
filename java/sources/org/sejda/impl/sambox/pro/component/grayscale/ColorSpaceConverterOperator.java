package org.sejda.impl.sambox.pro.component.grayscale;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.sejda.impl.sambox.pro.component.ContentStreamWriterStack;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorProcessor;
import org.sejda.sambox.contentstream.operator.OperatorProcessorDecorator;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.graphics.color.PDDeviceGray;
import org.sejda.sambox.pdmodel.graphics.color.PDPattern;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/grayscale/ColorSpaceConverterOperator.class */
public class ColorSpaceConverterOperator extends OperatorProcessorDecorator {
    private ContentStreamWriterStack writers;

    public ColorSpaceConverterOperator(ContentStreamWriterStack writers, OperatorProcessor delegate) {
        super(delegate);
        this.writers = writers;
        setConsumer((operator, operands) -> {
            if (operands.contains(COSName.PATTERN)) {
                writeOperator(operands, operator);
                return;
            }
            COSName name = (COSName) operands.get(0);
            COSBase colorSpaceResurce = (COSBase) Optional.ofNullable(getContext().getResources()).map((v0) -> {
                return v0.getCOSObject();
            }).map(r -> {
                return (COSDictionary) r.getDictionaryObject(COSName.COLORSPACE, COSDictionary.class);
            }).map(d -> {
                return d.getDictionaryObject(name);
            }).orElse(null);
            if (COSName.PATTERN.equals(colorSpaceResurce)) {
                writeOperator(operands, operator);
                return;
            }
            if (colorSpaceResurce instanceof COSArray) {
                COSArray csArray = (COSArray) colorSpaceResurce;
                COSName csName = (COSName) csArray.getObject(0);
                if (COSName.PATTERN.equals(csName)) {
                    if (csArray.size() == 1) {
                        writeOperator(operands, operator);
                        return;
                    } else {
                        writeOperator(Collections.singletonList(getContext().getResources().add(new PDPattern(getContext().getResources(), PDDeviceGray.INSTANCE))), operator);
                        return;
                    }
                }
                writeOperator(List.of(COSName.DEVICEGRAY), operator);
                return;
            }
            writeOperator(List.of(COSName.DEVICEGRAY), operator);
        });
    }

    private void writeOperator(List<COSBase> operands, Operator operator) throws IOException {
        this.writers.writeOperator(operands, operator);
    }
}
