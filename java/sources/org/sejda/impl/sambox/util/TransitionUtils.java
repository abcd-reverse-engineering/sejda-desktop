package org.sejda.impl.sambox.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.sejda.model.pdf.transition.PdfPageTransition;
import org.sejda.model.pdf.transition.PdfPageTransitionStyle;
import org.sejda.sambox.filter.TIFFExtension;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.pdmodel.interactive.pagenavigation.PDTransition;
import org.sejda.sambox.pdmodel.interactive.pagenavigation.PDTransitionDimension;
import org.sejda.sambox.pdmodel.interactive.pagenavigation.PDTransitionDirection;
import org.sejda.sambox.pdmodel.interactive.pagenavigation.PDTransitionMotion;
import org.sejda.sambox.pdmodel.interactive.pagenavigation.PDTransitionStyle;
import org.sejda.sambox.util.CharUtils;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/util/TransitionUtils.class */
public final class TransitionUtils {
    private static final Map<PdfPageTransitionStyle, PDTransitionStyle> TRANSITIONS_STYLES;

    static {
        Map<PdfPageTransitionStyle, PDTransitionStyle> transitionsStyles = new HashMap<>();
        transitionsStyles.put(PdfPageTransitionStyle.BLINDS_HORIZONTAL, PDTransitionStyle.Blinds);
        transitionsStyles.put(PdfPageTransitionStyle.BLINDS_VERTICAL, PDTransitionStyle.Blinds);
        transitionsStyles.put(PdfPageTransitionStyle.SPLIT_HORIZONTAL_INWARD, PDTransitionStyle.Split);
        transitionsStyles.put(PdfPageTransitionStyle.SPLIT_HORIZONTAL_OUTWARD, PDTransitionStyle.Split);
        transitionsStyles.put(PdfPageTransitionStyle.SPLIT_VERTICAL_INWARD, PDTransitionStyle.Split);
        transitionsStyles.put(PdfPageTransitionStyle.SPLIT_VERTICAL_OUTWARD, PDTransitionStyle.Split);
        transitionsStyles.put(PdfPageTransitionStyle.BOX_INWARD, PDTransitionStyle.Box);
        transitionsStyles.put(PdfPageTransitionStyle.BOX_OUTWARD, PDTransitionStyle.Box);
        transitionsStyles.put(PdfPageTransitionStyle.WIPE_BOTTOM_TO_TOP, PDTransitionStyle.Wipe);
        transitionsStyles.put(PdfPageTransitionStyle.WIPE_LEFT_TO_RIGHT, PDTransitionStyle.Wipe);
        transitionsStyles.put(PdfPageTransitionStyle.WIPE_RIGHT_TO_LEFT, PDTransitionStyle.Wipe);
        transitionsStyles.put(PdfPageTransitionStyle.WIPE_TOP_TO_BOTTOM, PDTransitionStyle.Wipe);
        transitionsStyles.put(PdfPageTransitionStyle.DISSOLVE, PDTransitionStyle.Dissolve);
        transitionsStyles.put(PdfPageTransitionStyle.GLITTER_DIAGONAL, PDTransitionStyle.Glitter);
        transitionsStyles.put(PdfPageTransitionStyle.GLITTER_LEFT_TO_RIGHT, PDTransitionStyle.Glitter);
        transitionsStyles.put(PdfPageTransitionStyle.GLITTER_TOP_TO_BOTTOM, PDTransitionStyle.Glitter);
        transitionsStyles.put(PdfPageTransitionStyle.REPLACE, PDTransitionStyle.R);
        transitionsStyles.put(PdfPageTransitionStyle.FLY_TOP_TO_BOTTOM, PDTransitionStyle.Fly);
        transitionsStyles.put(PdfPageTransitionStyle.FLY_LEFT_TO_RIGHT, PDTransitionStyle.Fly);
        transitionsStyles.put(PdfPageTransitionStyle.FADE, PDTransitionStyle.Fade);
        transitionsStyles.put(PdfPageTransitionStyle.COVER_LEFT_TO_RIGHT, PDTransitionStyle.Cover);
        transitionsStyles.put(PdfPageTransitionStyle.COVER_TOP_TO_BOTTOM, PDTransitionStyle.Cover);
        transitionsStyles.put(PdfPageTransitionStyle.UNCOVER_LEFT_TO_RIGHT, PDTransitionStyle.Uncover);
        transitionsStyles.put(PdfPageTransitionStyle.UNCOVER_TOP_TO_BOTTOM, PDTransitionStyle.Uncover);
        transitionsStyles.put(PdfPageTransitionStyle.PUSH_LEFT_TO_RIGHT, PDTransitionStyle.Push);
        transitionsStyles.put(PdfPageTransitionStyle.PUSH_TOP_TO_BOTTOM, PDTransitionStyle.Push);
        TRANSITIONS_STYLES = Collections.unmodifiableMap(transitionsStyles);
    }

    private TransitionUtils() {
    }

    public static PDTransitionStyle getTransition(PdfPageTransitionStyle transition) {
        return TRANSITIONS_STYLES.get(transition);
    }

    public static void initTransitionDimension(PdfPageTransition from, PDTransition to) {
        switch (from.getStyle()) {
            case BLINDS_HORIZONTAL:
            case SPLIT_HORIZONTAL_INWARD:
            case SPLIT_HORIZONTAL_OUTWARD:
                to.setDimension(PDTransitionDimension.H);
                break;
            case BLINDS_VERTICAL:
            case SPLIT_VERTICAL_INWARD:
            case SPLIT_VERTICAL_OUTWARD:
                to.setDimension(PDTransitionDimension.V);
                break;
        }
    }

    public static void initTransitionMotion(PdfPageTransition from, PDTransition to) {
        switch (from.getStyle()) {
            case SPLIT_HORIZONTAL_INWARD:
            case SPLIT_VERTICAL_INWARD:
            case BOX_INWARD:
                to.setMotion(PDTransitionMotion.I);
                break;
            case SPLIT_HORIZONTAL_OUTWARD:
            case SPLIT_VERTICAL_OUTWARD:
            case BOX_OUTWARD:
                to.setMotion(PDTransitionMotion.O);
                break;
        }
    }

    public static void initTransitionDirection(PdfPageTransition from, PDTransition to) {
        switch (AnonymousClass1.$SwitchMap$org$sejda$model$pdf$transition$PdfPageTransitionStyle[from.getStyle().ordinal()]) {
            case 9:
                to.setDirection(PDTransitionDirection.BOTTOM_TO_TOP);
                break;
            case 10:
            case 11:
            case 12:
            case CharUtils.ASCII_CARRIAGE_RETURN /* 13 */:
            case TIFFExtension.JPEG_PROC_LOSSLESS /* 14 */:
            case 15:
                to.setDirection(PDTransitionDirection.TOP_TO_BOTTOM);
                break;
            case PDAnnotation.FLAG_NO_ROTATE /* 16 */:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
                to.setDirection(PDTransitionDirection.LEFT_TO_RIGHT);
                break;
            case 22:
                to.setDirection(PDTransitionDirection.RIGHT_TO_LEFT);
                break;
            case 23:
                to.setDirection(PDTransitionDirection.TOP_LEFT_TO_BOTTOM_RIGHT);
                break;
        }
    }
}
