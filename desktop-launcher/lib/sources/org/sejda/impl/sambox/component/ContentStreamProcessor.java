package org.sejda.impl.sambox.component;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import org.sejda.sambox.contentstream.PDFStreamEngine;
import org.sejda.sambox.cos.COSNull;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAppearanceEntry;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAppearanceStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/ContentStreamProcessor.class */
public class ContentStreamProcessor extends PDFStreamEngine implements Consumer<PDPage> {
    private static final Logger LOG = LoggerFactory.getLogger(ContentStreamProcessor.class);

    private void processAnnotation(PDAnnotation annotation) throws IOException {
        List<PDAppearanceEntry> appreaceEntries = ((Collection) Optional.ofNullable(annotation.getAppearance()).map(d -> {
            return d.getCOSObject().getValues();
        }).filter(obj -> {
            return true;
        }).orElse(Collections.emptyList())).stream().map((v0) -> {
            return v0.getCOSObject();
        }).filter(a -> {
            return !(a instanceof COSNull);
        }).map(PDAppearanceEntry::new).toList();
        for (PDAppearanceEntry entry : appreaceEntries) {
            if (entry.isStream()) {
                processStream(entry.getAppearanceStream());
            } else {
                for (PDAppearanceStream stream : entry.getSubDictionary().values()) {
                    if (stream != null) {
                        processStream(stream);
                    }
                }
            }
        }
    }

    @Override // java.util.function.Consumer
    public void accept(PDPage page) {
        try {
            processPage(page);
            for (PDAnnotation annotation : page.getAnnotations()) {
                processAnnotation(annotation);
            }
        } catch (IOException e) {
            LOG.warn("Failed parse page, skipping and continuing with next.", e);
        }
    }
}
