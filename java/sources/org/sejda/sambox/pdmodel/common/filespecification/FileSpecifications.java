package org.sejda.sambox.pdmodel.common.filespecification;

import java.util.Objects;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/common/filespecification/FileSpecifications.class */
public final class FileSpecifications {
    private static final Logger LOG = LoggerFactory.getLogger(PDFileSpecification.class);

    private FileSpecifications() {
    }

    public static PDFileSpecification fileSpecificationFor(COSBase base) {
        if (Objects.nonNull(base)) {
            if (base instanceof COSString) {
                return new PDSimpleFileSpecification((COSString) base);
            }
            if (base instanceof COSDictionary) {
                return new PDComplexFileSpecification((COSDictionary) base);
            }
            LOG.warn("Invalid file specification type {}", base.getClass());
            return null;
        }
        return null;
    }
}
