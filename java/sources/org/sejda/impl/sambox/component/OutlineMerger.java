package org.sejda.impl.sambox.component;

import java.util.Optional;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.sejda.commons.LookupTable;
import org.sejda.model.outline.OutlinePolicy;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.PDPageXYZDestination;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/OutlineMerger.class */
public class OutlineMerger {
    private static final Logger LOG = LoggerFactory.getLogger(OutlineMerger.class);
    private OutlinePolicy policy;
    private PDDocumentOutline outline = new PDDocumentOutline();

    public OutlineMerger(OutlinePolicy policy) {
        this.policy = policy;
    }

    public void updateOutline(PDDocument document, String sourceName, LookupTable<PDPage> pagesLookup) {
        if (!pagesLookup.isEmpty()) {
            LOG.debug("Updating outline with policy {}", this.policy);
            switch (this.policy) {
                case ONE_ENTRY_EACH_DOC:
                    updateOneEntryPerDoc(sourceName, pagesLookup);
                    break;
                case RETAIN:
                    new OutlineDistiller(document).appendRelevantOutlineTo(this.outline, pagesLookup);
                    break;
                case RETAIN_AS_ONE_ENTRY:
                    Optional.ofNullable(updateOneEntryPerDoc(sourceName, pagesLookup)).ifPresent(item -> {
                        new OutlineDistiller(document).appendRelevantOutlineTo(item, pagesLookup);
                    });
                    break;
                default:
                    LOG.debug("Discarding outline for {}", sourceName);
                    break;
            }
        }
        LOG.info("Skipped outline merge, no relevant page");
    }

    private PDOutlineItem updateOneEntryPerDoc(String sourceName, LookupTable<PDPage> pagesLookup) {
        if (StringUtils.isNotBlank(sourceName)) {
            LOG.debug("Adding outline entry for {}", sourceName);
            PDOutlineItem item = new PDOutlineItem();
            item.setTitle(FilenameUtils.removeExtension(sourceName));
            PDPageXYZDestination destination = OutlineUtils.pageDestinationFor(pagesLookup.first());
            item.setDestination(destination);
            this.outline.addLast(item);
            return item;
        }
        LOG.warn("Unable to create an outline item for a source with blank name");
        return null;
    }

    public boolean hasOutline() {
        return this.outline.hasChildren();
    }

    public PDDocumentOutline getOutline() {
        return this.outline;
    }
}
