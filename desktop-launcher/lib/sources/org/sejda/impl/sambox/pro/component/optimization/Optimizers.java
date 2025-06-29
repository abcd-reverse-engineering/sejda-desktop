package org.sejda.impl.sambox.pro.component.optimization;

import java.util.function.Consumer;
import org.sejda.impl.sambox.component.optimization.ResourceDictionaryCleaner;
import org.sejda.model.pro.optimization.Optimization;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/optimization/Optimizers.class */
final class Optimizers {
    private Optimizers() {
    }

    static Consumer<PDDocument> documentOptimizer(Optimization optimization) {
        switch (optimization) {
            case DISCARD_METADATA:
                return d -> {
                    d.getDocumentCatalog().getCOSObject().removeItem(COSName.METADATA);
                };
            case DISCARD_THREADS:
                return d2 -> {
                    d2.getDocumentCatalog().getCOSObject().removeItem(COSName.THREADS);
                };
            case DISCARD_OUTLINE:
                return d3 -> {
                    d3.getDocumentCatalog().getCOSObject().removeItem(COSName.OUTLINES);
                };
            case DISCARD_SPIDER_INFO:
                return d4 -> {
                    d4.getDocumentCatalog().getCOSObject().removeItem(COSName.getPDFName("SpiderInfo"));
                };
            case DISCARD_PIECE_INFO:
                return d5 -> {
                    d5.getDocumentCatalog().getCOSObject().removeItem(COSName.PIECE_INFO);
                };
            case DISCARD_STRUCTURE_TREE:
                return d6 -> {
                    d6.getDocumentCatalog().getCOSObject().removeItem(COSName.STRUCT_TREE_ROOT);
                };
            case DISCARD_UNUSED_RESOURCES:
                return new ResourceDictionaryCleaner();
            default:
                return null;
        }
    }

    static Consumer<PDPage> pageOptimizer(Optimization optimization) {
        switch (optimization) {
            case DISCARD_PIECE_INFO:
                return p -> {
                    p.getCOSObject().removeItem(COSName.PIECE_INFO);
                };
            case DISCARD_STRUCTURE_TREE:
            case DISCARD_UNUSED_RESOURCES:
            default:
                return null;
            case DISCARD_THUMBNAILS:
                return p2 -> {
                    p2.getCOSObject().removeItem(COSName.getPDFName("Thumb"));
                };
            case DISCARD_MC_PROPERTIES:
                return p3 -> {
                    p3.getResources().getCOSObject().removeItem(COSName.PROPERTIES);
                };
            case DISCARD_MULTIMEDIA:
                return new PageAnnotationsOptimizer();
        }
    }
}
