package org.sejda.sambox.contentstream.operator.graphics;

import org.sejda.sambox.contentstream.PDFGraphicsStreamEngine;
import org.sejda.sambox.contentstream.operator.OperatorProcessor;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/operator/graphics/GraphicsOperatorProcessor.class */
public abstract class GraphicsOperatorProcessor extends OperatorProcessor {
    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public PDFGraphicsStreamEngine getContext() {
        return (PDFGraphicsStreamEngine) super.getContext();
    }
}
