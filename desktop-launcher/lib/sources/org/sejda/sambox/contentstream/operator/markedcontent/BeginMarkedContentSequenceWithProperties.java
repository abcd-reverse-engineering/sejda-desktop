package org.sejda.sambox.contentstream.operator.markedcontent;

import java.util.List;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.contentstream.operator.OperatorProcessor;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/operator/markedcontent/BeginMarkedContentSequenceWithProperties.class */
public class BeginMarkedContentSequenceWithProperties extends OperatorProcessor {
    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public void process(Operator operator, List<COSBase> arguments) {
        COSName tag = null;
        COSDictionary properties = null;
        for (COSBase argument : arguments) {
            if (argument instanceof COSName) {
                tag = (COSName) argument;
            } else if (argument instanceof COSDictionary) {
                properties = (COSDictionary) argument;
            }
        }
        getContext().beginMarkedContentSequence(tag, properties);
    }

    @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
    public String getName() {
        return OperatorName.BEGIN_MARKED_CONTENT_SEQ;
    }
}
