package org.sejda.model.pro.optimization;

import org.sejda.model.FriendlyNamed;

/* loaded from: org.sejda.sejda-model-pro-5.1.10.1.jar:org/sejda/model/pro/optimization/Optimization.class */
public enum Optimization implements FriendlyNamed {
    DISCARD_METADATA("discard_metadata"),
    DISCARD_OUTLINE("discard_outline"),
    DISCARD_THREADS("discard_threads"),
    DISCARD_SPIDER_INFO("discard_spider_info"),
    DISCARD_PIECE_INFO("discard_piece_info"),
    DISCARD_MC_PROPERTIES("discard_mc_props"),
    DISCARD_ALTERNATE_IMAGES("discard_alternate_images"),
    COMPRESS_IMAGES("compress_images"),
    DISCARD_UNUSED_RESOURCES("discard_unused_resources"),
    DISCARD_STRUCTURE_TREE("discard_struct_tree"),
    DISCARD_THUMBNAILS("discard_thumbnails"),
    GRAYSCALE_IMAGES("grayscale_images"),
    DISCARD_MULTIMEDIA("discard_multimedia"),
    SUBSET_FONTS("subset_fonts");

    private final String displayName;

    Optimization(String displayName) {
        this.displayName = displayName;
    }

    @Override // org.sejda.model.FriendlyNamed
    public String getFriendlyName() {
        return this.displayName;
    }
}
