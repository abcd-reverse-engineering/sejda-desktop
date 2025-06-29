package org.sejda.sambox.contentstream.operator;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.sejda.sambox.contentstream.PDFStreamEngine;
import org.sejda.sambox.cos.COSBase;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/operator/OperatorProcessorDecorator.class */
public class OperatorProcessorDecorator extends OperatorProcessor {
    private final OperatorProcessor delegate;
    private OperatorConsumer consumer;

    public OperatorProcessorDecorator(OperatorProcessor delegate, OperatorConsumer consumer) {
        this.delegate = delegate;
        this.consumer = consumer;
    }

    public OperatorProcessorDecorator(OperatorProcessor delegate) {
        this.delegate = delegate;
        this.consumer = null;
    }

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public void process(Operator operator, List<COSBase> operands) throws IOException {
        this.delegate.process(operator, operands);
        ((OperatorConsumer) Optional.ofNullable(this.consumer).orElse(OperatorConsumer.NO_OP)).apply(operator, operands);
    }

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public String getName() {
        return this.delegate.getName();
    }

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public PDFStreamEngine getContext() {
        return this.delegate.getContext();
    }

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public void setContext(PDFStreamEngine context) {
        this.delegate.setContext(context);
    }

    public void setConsumer(OperatorConsumer consumer) {
        this.consumer = consumer;
    }
}
