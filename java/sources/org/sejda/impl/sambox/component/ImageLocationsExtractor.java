package org.sejda.impl.sambox.component;

import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.sejda.sambox.contentstream.PDFStreamEngine;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.contentstream.operator.OperatorProcessor;
import org.sejda.sambox.contentstream.operator.state.Concatenate;
import org.sejda.sambox.contentstream.operator.state.Restore;
import org.sejda.sambox.contentstream.operator.state.Save;
import org.sejda.sambox.contentstream.operator.state.SetGraphicsStateParameters;
import org.sejda.sambox.contentstream.operator.state.SetMatrix;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.graphics.PDXObject;
import org.sejda.sambox.pdmodel.graphics.form.PDFormXObject;
import org.sejda.sambox.pdmodel.graphics.image.PDImageXObject;
import org.sejda.sambox.util.Matrix;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/ImageLocationsExtractor.class */
public class ImageLocationsExtractor extends PDFStreamEngine {
    private Map<PDPage, List<Rectangle>> imageLocations = new HashMap();

    public ImageLocationsExtractor() {
        addOperator(new Concatenate());
        addOperator(new SetGraphicsStateParameters());
        addOperator(new Save());
        addOperator(new Restore());
        addOperator(new SetMatrix());
        addOperator(new XObjectOperator());
    }

    public void process(PDDocument document) throws IOException {
        this.imageLocations.clear();
        Iterator<PDPage> it = document.getPages().iterator();
        while (it.hasNext()) {
            PDPage page = it.next();
            processPage(page);
        }
    }

    /* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/ImageLocationsExtractor$XObjectOperator.class */
    private class XObjectOperator extends OperatorProcessor {
        private XObjectOperator() {
        }

        @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
        public void process(Operator operator, List<COSBase> operands) throws IOException {
            COSName objectName = (COSName) operands.get(0);
            PDXObject xobject = ImageLocationsExtractor.this.getResources().getXObject(objectName);
            if (!(xobject instanceof PDImageXObject)) {
                if (xobject instanceof PDFormXObject) {
                    PDFormXObject form = (PDFormXObject) xobject;
                    ImageLocationsExtractor.this.showForm(form);
                    return;
                }
                return;
            }
            Matrix ctmNew = ImageLocationsExtractor.this.getGraphicsState().getCurrentTransformationMatrix();
            float imageXScale = ctmNew.getScalingFactorX();
            float imageYScale = ctmNew.getScalingFactorY();
            if (!ImageLocationsExtractor.this.imageLocations.containsKey(ImageLocationsExtractor.this.getCurrentPage())) {
                ImageLocationsExtractor.this.imageLocations.put(ImageLocationsExtractor.this.getCurrentPage(), new ArrayList());
            }
            ImageLocationsExtractor.this.imageLocations.get(ImageLocationsExtractor.this.getCurrentPage()).add(new Rectangle((int) ctmNew.getTranslateX(), (int) ctmNew.getTranslateY(), (int) imageXScale, (int) imageYScale));
        }

        @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
        public String getName() {
            return OperatorName.DRAW_OBJECT;
        }
    }

    public Map<PDPage, List<Rectangle>> getImageLocations() {
        return this.imageLocations;
    }
}
