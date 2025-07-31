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

    /* renamed from: org.sejda.impl.sambox.pro.component.optimization.Optimizers$1, reason: invalid class name */
    /* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/optimization/Optimizers$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$sejda$model$pro$optimization$Optimization = new int[Optimization.values().length];

        static {
            try {
                $SwitchMap$org$sejda$model$pro$optimization$Optimization[Optimization.DISCARD_METADATA.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$sejda$model$pro$optimization$Optimization[Optimization.DISCARD_THREADS.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$sejda$model$pro$optimization$Optimization[Optimization.DISCARD_OUTLINE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$org$sejda$model$pro$optimization$Optimization[Optimization.DISCARD_SPIDER_INFO.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$org$sejda$model$pro$optimization$Optimization[Optimization.DISCARD_PIECE_INFO.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$org$sejda$model$pro$optimization$Optimization[Optimization.DISCARD_STRUCTURE_TREE.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$org$sejda$model$pro$optimization$Optimization[Optimization.DISCARD_UNUSED_RESOURCES.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$org$sejda$model$pro$optimization$Optimization[Optimization.DISCARD_THUMBNAILS.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$org$sejda$model$pro$optimization$Optimization[Optimization.DISCARD_MC_PROPERTIES.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$org$sejda$model$pro$optimization$Optimization[Optimization.DISCARD_MULTIMEDIA.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
        }
    }

    static Consumer<PDDocument> documentOptimizer(Optimization optimization) {
        switch (AnonymousClass1.$SwitchMap$org$sejda$model$pro$optimization$Optimization[optimization.ordinal()]) {
            case 1:
                return d -> {
                    d.getDocumentCatalog().getCOSObject().removeItem(COSName.METADATA);
                };
            case 2:
                return d2 -> {
                    d2.getDocumentCatalog().getCOSObject().removeItem(COSName.THREADS);
                };
            case 3:
                return d3 -> {
                    d3.getDocumentCatalog().getCOSObject().removeItem(COSName.OUTLINES);
                };
            case 4:
                return d4 -> {
                    d4.getDocumentCatalog().getCOSObject().removeItem(COSName.getPDFName("SpiderInfo"));
                };
            case 5:
                return d5 -> {
                    d5.getDocumentCatalog().getCOSObject().removeItem(COSName.PIECE_INFO);
                };
            case 6:
                return d6 -> {
                    d6.getDocumentCatalog().getCOSObject().removeItem(COSName.STRUCT_TREE_ROOT);
                };
            case 7:
                return new ResourceDictionaryCleaner();
            default:
                return null;
        }
    }

    static Consumer<PDPage> pageOptimizer(Optimization optimization) {
        switch (AnonymousClass1.$SwitchMap$org$sejda$model$pro$optimization$Optimization[optimization.ordinal()]) {
            case 5:
                return p -> {
                    p.getCOSObject().removeItem(COSName.PIECE_INFO);
                };
            case 6:
            case 7:
            default:
                return null;
            case 8:
                return p2 -> {
                    p2.getCOSObject().removeItem(COSName.getPDFName("Thumb"));
                };
            case 9:
                return p3 -> {
                    p3.getResources().getCOSObject().removeItem(COSName.PROPERTIES);
                };
            case 10:
                return new PageAnnotationsOptimizer();
        }
    }
}
