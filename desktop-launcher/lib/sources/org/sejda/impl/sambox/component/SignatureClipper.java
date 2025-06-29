package org.sejda.impl.sambox.component;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.pdmodel.interactive.form.PDField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/SignatureClipper.class */
public final class SignatureClipper {
    private static final Logger LOG = LoggerFactory.getLogger(SignatureClipper.class);

    private SignatureClipper() {
    }

    public static void clipSignatures(Collection<PDAnnotation> annotations) {
        if (Objects.nonNull(annotations)) {
            for (PDAnnotation annotation : annotations) {
                if (COSName.WIDGET.getName().equals(annotation.getSubtype()) && COSName.SIG.equals(annotation.getCOSObject().getCOSName(COSName.FT))) {
                    clipSignature(annotation.getCOSObject());
                }
            }
        }
    }

    public static void clipSignatures(PDDocument doc) {
        Iterator<PDPage> it = doc.getPages().iterator();
        while (it.hasNext()) {
            PDPage page = it.next();
            clipSignatures(page.getAnnotations());
        }
    }

    public static boolean clipSignature(PDField field) {
        if (Objects.nonNull(field) && COSName.SIG.getName().equals(field.getFieldType())) {
            clipSignature(field.getCOSObject());
            return true;
        }
        return false;
    }

    private static void clipSignature(COSDictionary item) {
        LOG.info("Removing signature value from the field if any");
        item.removeItem(COSName.V);
        item.removeItem(COSName.SV);
        item.removeItem(COSName.LOCK);
    }
}
