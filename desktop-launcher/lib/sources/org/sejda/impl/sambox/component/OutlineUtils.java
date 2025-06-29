package org.sejda.impl.sambox.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.apache.commons.lang3.StringUtils;
import org.sejda.commons.util.RequireUtils;
import org.sejda.model.rotation.Rotation;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDDocumentCatalog;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.PageNotFoundException;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.PDDestination;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.PDPageXYZDestination;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.outline.PDOutlineTreeIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/OutlineUtils.class */
public final class OutlineUtils {
    private static final Logger LOG = LoggerFactory.getLogger(OutlineUtils.class);

    private OutlineUtils() {
    }

    public static Set<Integer> getOutlineLevelsWithPageDestination(PDDocument document) {
        return (Set) getFlatOutline(document).stream().map(i -> {
            return Integer.valueOf(i.level);
        }).collect(Collectors.toSet());
    }

    public static Optional<PDPageDestination> toPageDestination(PDOutlineItem current, PDDocumentCatalog catalog) {
        try {
            return current.resolveToPageDestination(catalog);
        } catch (IOException e) {
            LOG.warn("Unable to get outline item destination ", e);
            return Optional.empty();
        }
    }

    public static PDPageXYZDestination pageDestinationFor(PDPage page) {
        RequireUtils.requireNotNullArg(page, "Cannot create a destination to a null page");
        PDPageXYZDestination pageDest = new PDPageXYZDestination();
        pageDest.setPage(page);
        PDRectangle cropBox = page.getCropBox();
        switch (Rotation.getRotation(page.getRotation())) {
            case DEGREES_180:
                pageDest.setLeft((int) cropBox.getUpperRightX());
                pageDest.setTop((int) cropBox.getLowerLeftY());
                break;
            case DEGREES_270:
                pageDest.setLeft((int) cropBox.getUpperRightX());
                pageDest.setTop((int) cropBox.getUpperRightY());
                break;
            case DEGREES_90:
                pageDest.setLeft((int) cropBox.getLowerLeftX());
                pageDest.setTop((int) cropBox.getLowerLeftY());
                break;
            default:
                pageDest.setLeft((int) cropBox.getLowerLeftX());
                pageDest.setTop((int) cropBox.getUpperRightY());
                break;
        }
        return pageDest;
    }

    public static PDPageDestination clonePageDestination(PDPageDestination dest, PDPage destPage) {
        RequireUtils.requireNotNullArg(dest, "Cannot clone a null destination");
        try {
            PDDestination clonedDestination = PDDestination.create(dest.getCOSObject().duplicate());
            if (clonedDestination instanceof PDPageDestination) {
                ((PDPageDestination) clonedDestination).setPage(destPage);
                return (PDPageDestination) clonedDestination;
            }
        } catch (IOException e) {
            LOG.warn("Unable to clone page destination", e);
        }
        PDPageXYZDestination ret = new PDPageXYZDestination();
        ret.setPage(destPage);
        return ret;
    }

    public static PDPage resolvePageDestination(PDPageDestination destination, PDDocument document) {
        PDPage page = destination.getPage();
        if (Objects.isNull(page) && destination.getPageNumber() >= 0) {
            try {
                LOG.debug("Found page number in page destination, expected a page reference");
                return document.getPage(destination.getPageNumber());
            } catch (PageNotFoundException e) {
                LOG.warn("Unable to resolve page destination pointing to page {} (a page reference was expected, a number was found)", Integer.valueOf(destination.getPageNumber()));
            }
        }
        return page;
    }

    public static void copyOutlineDictionary(PDOutlineItem from, PDOutlineItem to) {
        to.setTitle(StringUtils.defaultString(from.getTitle()));
        if (Objects.nonNull(from.getCOSObject().getDictionaryObject(COSName.C, COSArray.class))) {
            to.setTextColor(from.getTextColor());
        }
        to.setBold(from.isBold());
        to.setItalic(from.isItalic());
        if (from.isNodeOpen()) {
            to.openNode();
        } else {
            to.closeNode();
        }
    }

    public static Map<PDPage, Set<PDPageDestination>> pageGroupedOutlinePageDestinations(PDDocument document) {
        Map<PDPage, Set<PDPageDestination>> destinations = new HashMap<>();
        PDDocumentCatalog catalog = document.getDocumentCatalog();
        Optional.ofNullable(catalog.getDocumentOutline()).ifPresent(outline -> {
            StreamSupport.stream(Spliterators.spliteratorUnknownSize(new PDOutlineTreeIterator(outline), 272), false).forEach(i -> {
                toPageDestination(i, document.getDocumentCatalog()).ifPresent(d -> {
                    PDPage page = resolvePageDestination(d, document);
                    if (Objects.nonNull(page)) {
                        ((Set) destinations.computeIfAbsent(page, p -> {
                            return new HashSet();
                        })).add(d);
                    }
                });
            });
        });
        return destinations;
    }

    public static List<OutlineItem> getFlatOutline(PDDocument document) {
        return (List) ((List) Optional.ofNullable(document.getDocumentCatalog().getDocumentOutline()).map((v0) -> {
            return v0.children();
        }).map(c -> {
            return recurseFlatOutline(document, c, 1);
        }).orElseGet(ArrayList::new)).stream().sorted(Comparator.comparingInt(i -> {
            return i.page;
        })).filter(i2 -> {
            return i2.page > 0;
        }).collect(Collectors.toList());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List<OutlineItem> recurseFlatOutline(PDDocument document, Iterable<PDOutlineItem> items, int level) {
        List<OutlineItem> result = new ArrayList<>();
        for (PDOutlineItem item : items) {
            toPageDestination(item, document.getDocumentCatalog()).ifPresent(d -> {
                int pageNumber = ((Integer) Optional.ofNullable(d.getPage()).map(p -> {
                    return Integer.valueOf(document.getPages().indexOf(p) + 1);
                }).orElseGet(() -> {
                    return Integer.valueOf(d.getPageNumber() + 1);
                })).intValue();
                boolean specificLocationInPage = false;
                if (d instanceof PDPageXYZDestination) {
                    PDPageXYZDestination xyzPageDest = (PDPageXYZDestination) d;
                    if (xyzPageDest.getPage() != null) {
                        specificLocationInPage = xyzPageDest.getTop() != ((int) xyzPageDest.getPage().getCropBox().getHeight());
                    }
                }
                result.add(new OutlineItem(item.getTitle(), pageNumber, level, specificLocationInPage));
            });
            result.addAll(recurseFlatOutline(document, item.children(), level + 1));
        }
        return result;
    }

    public static void printOutline(PDDocument document) {
        Optional.ofNullable(document.getDocumentCatalog().getDocumentOutline()).ifPresent(outline -> {
            int childCounter = 0;
            for (PDOutlineItem child : outline.children()) {
                childCounter++;
                printNode(child, 0, childCounter);
            }
        });
    }

    private static void printNode(PDOutlineItem node, int level, int childCounter) {
        StringBuilder sb = new StringBuilder();
        sb.append("-".repeat(Math.max(0, level)));
        sb.append(" ").append(node.getTitle()).append(" (").append(childCounter).append(")");
        System.out.println(sb);
        if (node.hasChildren()) {
            int childrenCount = 0;
            for (PDOutlineItem current : node.children()) {
                childrenCount++;
                printNode(current, level + 1, childrenCount);
            }
        }
    }
}
