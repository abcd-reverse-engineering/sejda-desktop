package org.sejda.model.pdf.transition;

import org.sejda.model.FriendlyNamed;
import org.sejda.model.pdf.MinRequiredVersion;
import org.sejda.model.pdf.PdfVersion;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/pdf/transition/PdfPageTransitionStyle.class */
public enum PdfPageTransitionStyle implements MinRequiredVersion, FriendlyNamed {
    SPLIT_HORIZONTAL_INWARD("split_horizontal_inward", PdfVersion.VERSION_1_1),
    SPLIT_HORIZONTAL_OUTWARD("split_horizontal_outward", PdfVersion.VERSION_1_1),
    SPLIT_VERTICAL_INWARD("split_vertical_inward", PdfVersion.VERSION_1_1),
    SPLIT_VERTICAL_OUTWARD("split_vertical_outward", PdfVersion.VERSION_1_1),
    BLINDS_HORIZONTAL("blinds_horizontal", PdfVersion.VERSION_1_1),
    BLINDS_VERTICAL("blinds_vertical", PdfVersion.VERSION_1_1),
    BOX_INWARD("box_inward", PdfVersion.VERSION_1_1),
    BOX_OUTWARD("box_outward", PdfVersion.VERSION_1_1),
    DISSOLVE("dissolve", PdfVersion.VERSION_1_1),
    WIPE_LEFT_TO_RIGHT("wipe_left_to_right", PdfVersion.VERSION_1_1),
    WIPE_RIGHT_TO_LEFT("wipe_right_to_left", PdfVersion.VERSION_1_1),
    WIPE_TOP_TO_BOTTOM("wipe_top_to_bottom", PdfVersion.VERSION_1_1),
    WIPE_BOTTOM_TO_TOP("wipe_bottom_to_top", PdfVersion.VERSION_1_1),
    GLITTER_LEFT_TO_RIGHT("glitter_left_to_right", PdfVersion.VERSION_1_1),
    GLITTER_TOP_TO_BOTTOM("glitter_top_to_bottom", PdfVersion.VERSION_1_1),
    GLITTER_DIAGONAL("glitter_diagonal", PdfVersion.VERSION_1_1),
    REPLACE("replace", PdfVersion.VERSION_1_1),
    PUSH_LEFT_TO_RIGHT("push_left_to_right", PdfVersion.VERSION_1_5),
    PUSH_TOP_TO_BOTTOM("push_top_to_bottom", PdfVersion.VERSION_1_5),
    COVER_LEFT_TO_RIGHT("cover_left_to_right", PdfVersion.VERSION_1_5),
    COVER_TOP_TO_BOTTOM("cover_top_to_bottom", PdfVersion.VERSION_1_5),
    UNCOVER_LEFT_TO_RIGHT("uncover_left_to_right", PdfVersion.VERSION_1_5),
    UNCOVER_TOP_TO_BOTTOM("uncover_top_to_bottom", PdfVersion.VERSION_1_5),
    FADE("fade", PdfVersion.VERSION_1_5),
    FLY_LEFT_TO_RIGHT("fly_left_to_right", PdfVersion.VERSION_1_5),
    FLY_TOP_TO_BOTTOM("fly_top_to_bottom", PdfVersion.VERSION_1_5);

    private final PdfVersion minVersion;
    private final String displayName;

    PdfPageTransitionStyle(String displayName, PdfVersion minVersion) {
        this.displayName = displayName;
        this.minVersion = minVersion;
    }

    @Override // org.sejda.model.FriendlyNamed
    public String getFriendlyName() {
        return this.displayName;
    }

    @Override // org.sejda.model.pdf.MinRequiredVersion
    public PdfVersion getMinVersion() {
        return this.minVersion;
    }
}
