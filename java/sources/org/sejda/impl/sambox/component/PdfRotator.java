package org.sejda.impl.sambox.component;

import org.sejda.model.rotation.Rotation;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/PdfRotator.class */
public final class PdfRotator {
    private static final Logger LOG = LoggerFactory.getLogger(PdfRotator.class);
    private PDDocument document;

    public PdfRotator(PDDocument document) {
        this.document = document;
    }

    public void rotate(int pageNumber, Rotation rotation) {
        LOG.debug("Applying rotation of {} degrees to page {}", Integer.valueOf(rotation.getDegrees()), Integer.valueOf(pageNumber));
        PDPage page = this.document.getPage(pageNumber - 1);
        rotate(page, rotation);
    }

    public static void rotate(PDPage page, Rotation rotation) {
        page.setRotation(rotation.addRotation(Rotation.getRotation(page.getRotation())).getDegrees());
    }
}
