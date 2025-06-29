package org.sejda.impl.sambox.component;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.sejda.commons.LookupTable;
import org.sejda.commons.util.RequireUtils;
import org.sejda.sambox.cos.COSObjectKey;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.outline.PDOutlineNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/OutlineDistiller.class */
public class OutlineDistiller {
    private static final Logger LOG = LoggerFactory.getLogger(OutlineDistiller.class);
    private PDDocument document;

    public OutlineDistiller(PDDocument document) {
        RequireUtils.requireNotNullArg(document, "Unable to retrieve bookmarks from a null document.");
        this.document = document;
    }

    public void appendRelevantOutlineTo(PDOutlineNode to, LookupTable<PDPage> pagesLookup) {
        Objects.requireNonNull(to, "Unable to merge relevant outline items to a null outline.");
        if (!pagesLookup.isEmpty()) {
            Optional.ofNullable(this.document.getDocumentCatalog().getDocumentOutline()).ifPresent(outline -> {
                cloneOutline(outline, to, pagesLookup);
                LOG.debug("Appended relevant outline items");
            });
        }
    }

    private String objIdOf(COSObjectable o) {
        try {
            COSObjectKey ident = o.getCOSObject().id().objectIdentifier;
            String str = ident.generation() == 0 ? "" : ident.generation();
            long jObjectNumber = ident.objectNumber();
            return jObjectNumber + "R" + jObjectNumber;
        } catch (Exception e) {
            return "";
        }
    }

    private void cloneOutline(PDDocumentOutline from, PDOutlineNode to, LookupTable<PDPage> pagesLookup) {
        Set<PDOutlineItem> alreadyVisited = new HashSet<>();
        for (PDOutlineItem child : from.children()) {
            Optional<PDOutlineItem> optionalCloneNode = cloneNode(child, pagesLookup, alreadyVisited);
            Objects.requireNonNull(to);
            optionalCloneNode.ifPresent(to::addLast);
        }
    }

    private Optional<PDOutlineItem> cloneNode(PDOutlineItem node, LookupTable<PDPage> pagesLookup, Set<PDOutlineItem> alreadyVisited) {
        String nodeObjId = objIdOf(node);
        LOG.debug("Cloning node: " + nodeObjId + " " + node.getTitle() + " #" + node.hashCode());
        if (alreadyVisited.contains(node)) {
            LOG.warn("Detected already visited node: " + nodeObjId + " " + node.getTitle() + " #" + node.hashCode() + ", skipping at cloning to avoid infinite loop");
            return Optional.empty();
        }
        alreadyVisited.add(node);
        if (node.hasChildren()) {
            PDOutlineItem clone = new PDOutlineItem();
            for (PDOutlineItem current : node.children()) {
                if (current.equals(node)) {
                    LOG.warn("Outline item has a child pointing to the parent, skipping at cloning");
                } else {
                    Optional<PDOutlineItem> optionalCloneNode = cloneNode(current, pagesLookup, alreadyVisited);
                    Objects.requireNonNull(clone);
                    optionalCloneNode.ifPresent(clone::addLast);
                }
            }
            Optional<PDPageDestination> pageDestination = OutlineUtils.toPageDestination(node, this.document.getDocumentCatalog());
            Optional<U> map = pageDestination.map(d -> {
                return OutlineUtils.resolvePageDestination(d, this.document);
            });
            Objects.requireNonNull(pagesLookup);
            Optional<PDPage> destinationPage = map.map((v1) -> {
                return r1.lookup(v1);
            });
            if (clone.hasChildren() || destinationPage.isPresent()) {
                OutlineUtils.copyOutlineDictionary(node, clone);
                destinationPage.ifPresent(p -> {
                    clone.setDestination(OutlineUtils.clonePageDestination((PDPageDestination) pageDestination.get(), p));
                });
                return Optional.of(clone);
            }
            return Optional.empty();
        }
        return cloneLeafIfNeeded(node, pagesLookup);
    }

    private Optional<PDOutlineItem> cloneLeafIfNeeded(PDOutlineItem origin, LookupTable<PDPage> pagesLookup) {
        return OutlineUtils.toPageDestination(origin, this.document.getDocumentCatalog()).flatMap(d -> {
            PDPage mapped = (PDPage) pagesLookup.lookup(OutlineUtils.resolvePageDestination(d, this.document));
            if (mapped != null) {
                PDOutlineItem retVal = new PDOutlineItem();
                OutlineUtils.copyOutlineDictionary(origin, retVal);
                retVal.setDestination(OutlineUtils.clonePageDestination(d, mapped));
                return Optional.of(retVal);
            }
            return Optional.empty();
        });
    }
}
