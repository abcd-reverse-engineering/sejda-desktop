package org.sejda.impl.sambox.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.sejda.commons.LookupTable;
import org.sejda.commons.util.RequireUtils;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationPopup;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.PDDestination;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/AnnotationsDistiller.class */
public final class AnnotationsDistiller {
    private static final Logger LOG = LoggerFactory.getLogger(AnnotationsDistiller.class);
    private PDDocument document;
    private LookupTable<PDAnnotation> annotationsLookup = new LookupTable<>();

    public AnnotationsDistiller(PDDocument document) {
        RequireUtils.requireNotNullArg(document, "Cannot process annotations for a null document");
        this.document = document;
    }

    public LookupTable<PDAnnotation> retainRelevantAnnotations(LookupTable<PDPage> relevantPages) {
        LOG.debug("Filtering annotations");
        for (PDPage page : relevantPages.keys()) {
            try {
                Set<PDAnnotation> keptAnnotations = new LinkedHashSet<>();
                for (PDAnnotation annotation : page.getAnnotations()) {
                    PDAnnotation mapped = this.annotationsLookup.lookup(annotation);
                    if (Objects.nonNull(mapped)) {
                        keptAnnotations.add(mapped);
                    } else {
                        PDPage annotationPage = annotation.getPage();
                        if (annotationPage != null && !annotationPage.equals(page)) {
                            if (COSName.SCREEN.equals(annotation.getSubtype())) {
                                annotation.setPage(page);
                            } else {
                                annotation.setPage(null);
                            }
                        }
                        if (annotation instanceof PDAnnotationLink) {
                            processLinkAnnotation(relevantPages, keptAnnotations, (PDAnnotationLink) annotation, page);
                        } else {
                            processNonLinkAnnotation(relevantPages, keptAnnotations, annotation, page);
                        }
                    }
                }
                relevantPages.lookup(page).setAnnotations(new ArrayList(keptAnnotations));
            } catch (IOException e) {
                LOG.warn("Failed to process annotations for page", e);
            }
        }
        return this.annotationsLookup;
    }

    private void processLinkAnnotation(LookupTable<PDPage> relevantPages, Set<PDAnnotation> set, PDAnnotationLink annotation, PDPage p) throws IOException {
        PDPageDestination destination = getDestinationFrom(annotation);
        if (Objects.nonNull(destination)) {
            PDPage destPage = relevantPages.lookup(destination.getPage());
            if (Objects.nonNull(destPage)) {
                PDAnnotationLink duplicate = (PDAnnotationLink) duplicate(annotation, p, relevantPages);
                duplicate.getCOSObject().removeItem(COSName.A);
                PDPageDestination newDestination = (PDPageDestination) PDDestination.create(destination.getCOSObject().duplicate());
                newDestination.setPage(destPage);
                duplicate.setDestination(newDestination);
                set.add(duplicate);
                return;
            }
            LOG.trace("Removing not relevant link annotation");
            return;
        }
        set.add(duplicate(annotation, p, relevantPages));
    }

    private void processNonLinkAnnotation(LookupTable<PDPage> relevantPages, Set<PDAnnotation> keptAnnotations, PDAnnotation annotation, PDPage p) {
        if (Objects.isNull(p) || relevantPages.hasLookupFor(p)) {
            PDAnnotation duplicate = duplicate(annotation, p, relevantPages);
            if (duplicate instanceof PDAnnotationMarkup) {
                PDAnnotationPopup popup = ((PDAnnotationMarkup) duplicate).getPopup();
                if (Objects.nonNull(popup)) {
                    COSName subtype = popup.getCOSObject().getCOSName(COSName.SUBTYPE);
                    if (COSName.POPUP.equals(subtype)) {
                        PDAnnotationPopup popupDuplicate = (PDAnnotationPopup) Optional.ofNullable((PDAnnotationPopup) this.annotationsLookup.lookup(popup)).orElseGet(() -> {
                            return (PDAnnotationPopup) duplicate(popup, p, relevantPages);
                        });
                        ((PDAnnotationMarkup) duplicate).setPopup(popupDuplicate);
                        if (Objects.nonNull(popupDuplicate.getParent())) {
                            popupDuplicate.setParent((PDAnnotationMarkup) duplicate);
                            LOG.trace("Popup parent annotation updated");
                        }
                        keptAnnotations.add(popupDuplicate);
                    } else {
                        ((PDAnnotationMarkup) duplicate).setPopup(null);
                        LOG.warn("Removed Popup annotation of unexpected subtype {}", subtype);
                    }
                }
            }
            keptAnnotations.add(duplicate);
        }
    }

    private PDAnnotation duplicate(PDAnnotation annotation, PDPage p, LookupTable<PDPage> relevantPages) {
        PDAnnotation duplicate = PDAnnotation.createAnnotation(annotation.getCOSObject().duplicate());
        if (Objects.nonNull(p)) {
            duplicate.setPage(relevantPages.lookup(p));
            LOG.trace("Updated annotation page reference with the looked up page");
        }
        this.annotationsLookup.addLookupEntry(annotation, duplicate);
        return duplicate;
    }

    private PDPageDestination getDestinationFrom(PDAnnotationLink link) {
        try {
            return link.resolveToPageDestination(this.document.getDocumentCatalog()).orElse(null);
        } catch (Exception e) {
            LOG.warn("Failed to get destination for annotation", e);
            return null;
        }
    }
}
