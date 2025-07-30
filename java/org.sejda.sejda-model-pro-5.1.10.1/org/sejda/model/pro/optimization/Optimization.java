package org.sejda.model.pro.optimization;

import org.sejda.model.FriendlyNamed;

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

   private Optimization(String displayName) {
      this.displayName = displayName;
   }

   public String getFriendlyName() {
      return this.displayName;
   }

   // $FF: synthetic method
   private static Optimization[] $values() {
      return new Optimization[]{DISCARD_METADATA, DISCARD_OUTLINE, DISCARD_THREADS, DISCARD_SPIDER_INFO, DISCARD_PIECE_INFO, DISCARD_MC_PROPERTIES, DISCARD_ALTERNATE_IMAGES, COMPRESS_IMAGES, DISCARD_UNUSED_RESOURCES, DISCARD_STRUCTURE_TREE, DISCARD_THUMBNAILS, GRAYSCALE_IMAGES, DISCARD_MULTIMEDIA, SUBSET_FONTS};
   }
}
