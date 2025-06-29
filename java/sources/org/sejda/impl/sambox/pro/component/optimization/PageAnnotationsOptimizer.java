package org.sejda.impl.sambox.pro.component.optimization;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/optimization/PageAnnotationsOptimizer.class */
public class PageAnnotationsOptimizer implements Consumer<PDPage> {
    private static final Logger LOG = LoggerFactory.getLogger(PageAnnotationsOptimizer.class);

    @Override // java.util.function.Consumer
    public void accept(PDPage pdPage) {
        List<PDAnnotation> annotations = pdPage.getAnnotations();
        int originalSize = annotations.size();
        for (int i = originalSize - 1; i >= 0; i--) {
            if (isScreenRendition(annotations.get(i))) {
                LOG.debug("Removed screen rendition annotation");
                annotations.remove(i);
            }
        }
        if (originalSize != annotations.size()) {
            pdPage.setAnnotations(annotations);
        }
    }

    private boolean isScreenRendition(PDAnnotation annotation) {
        String subtype = annotation.getSubtype();
        if (COSName.SCREEN.getName().equals(subtype)) {
            COSDictionary actions = (COSDictionary) annotation.getCOSObject().getDictionaryObject(COSName.A, COSDictionary.class);
            if (Objects.nonNull(actions)) {
                return Objects.nonNull(actions.getDictionaryObject(COSName.R));
            }
            return false;
        }
        return false;
    }
}
