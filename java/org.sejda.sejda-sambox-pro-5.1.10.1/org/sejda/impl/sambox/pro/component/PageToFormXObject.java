package org.sejda.impl.sambox.pro.component;

import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import org.sejda.commons.util.RequireUtils;
import org.sejda.impl.sambox.component.ReadOnlyFilteredCOSStream;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.common.PDStream;
import org.sejda.sambox.pdmodel.graphics.form.PDFormXObject;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/PageToFormXObject.class */
public class PageToFormXObject {
    public PDFormXObject apply(PDPage page) throws IOException {
        RequireUtils.requireNotNullArg(page, "Cannot convert a null page");
        PDStream stream = getStream(page);
        if (stream == null) {
            return null;
        }
        PDFormXObject form = new PDFormXObject(stream);
        form.setResources(page.getResources());
        PDRectangle mediaBox = page.getMediaBox();
        PDRectangle boundingBox = (PDRectangle) Optional.ofNullable(page.getCropBox()).orElse(mediaBox);
        AffineTransform at = form.getMatrix().createAffineTransform();
        at.translate(mediaBox.getLowerLeftX() - boundingBox.getLowerLeftX(), mediaBox.getLowerLeftY() - boundingBox.getLowerLeftY());
        switch (page.getRotation()) {
            case 90:
                at.translate(0.0d, boundingBox.getWidth());
                at.rotate(-1.5707963267948966d);
                break;
            case 180:
                at.translate(boundingBox.getWidth(), boundingBox.getHeight());
                at.rotate(-3.141592653589793d);
                break;
            case 270:
                at.translate(boundingBox.getHeight(), 0.0d);
                at.rotate(-4.71238898038469d);
                break;
        }
        at.translate(-boundingBox.getLowerLeftX(), -boundingBox.getLowerLeftY());
        if (!at.isIdentity()) {
            form.setMatrix(at);
        }
        form.setBBox(new PDRectangle(boundingBox.getLowerLeftX(), boundingBox.getLowerLeftY(), boundingBox.getUpperRightX(), boundingBox.getUpperRightY()));
        return form;
    }

    private PDStream getStream(PDPage page) throws IOException {
        COSStream dictionaryObject = page.getCOSObject().getDictionaryObject(COSName.CONTENTS);
        if (dictionaryObject instanceof COSStream) {
            COSStream existing = dictionaryObject;
            COSDictionary cOSDictionaryDuplicate = existing.duplicate();
            Objects.requireNonNull(existing);
            return new PDStream(new ReadOnlyFilteredCOSStream(cOSDictionaryDuplicate, existing::getFilteredStream, existing.getFilteredLength()));
        }
        if ((dictionaryObject instanceof COSArray) && ((COSArray) dictionaryObject).size() > 0) {
            return new PDStream(page.getContents(), COSName.FLATE_DECODE);
        }
        return null;
    }
}
