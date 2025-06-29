package org.sejda.impl.sambox.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.sejda.model.exception.TaskException;
import org.sejda.model.pdf.viewerpreference.PdfBooleanPreference;
import org.sejda.model.pdf.viewerpreference.PdfDirection;
import org.sejda.model.pdf.viewerpreference.PdfDuplex;
import org.sejda.model.pdf.viewerpreference.PdfNonFullScreenPageMode;
import org.sejda.model.pdf.viewerpreference.PdfPageLayout;
import org.sejda.model.pdf.viewerpreference.PdfPageMode;
import org.sejda.model.pdf.viewerpreference.PdfPrintScaling;
import org.sejda.sambox.pdmodel.PageLayout;
import org.sejda.sambox.pdmodel.PageMode;
import org.sejda.sambox.pdmodel.interactive.viewerpreferences.PDViewerPreferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/util/ViewerPreferencesUtils.class */
public final class ViewerPreferencesUtils {
    private static final Logger LOG = LoggerFactory.getLogger(ViewerPreferencesUtils.class);
    private static final Map<PdfNonFullScreenPageMode, PDViewerPreferences.NON_FULL_SCREEN_PAGE_MODE> NFS_MODE_CACHE;
    private static final Map<PdfPageLayout, PageLayout> LAYOUT_CACHE;
    private static final Map<PdfPageMode, PageMode> PAGE_MODE_CACHE;
    private static final Map<PdfDuplex, PDViewerPreferences.DUPLEX> DUPLEX_CACHE;

    static {
        Map<PdfNonFullScreenPageMode, PDViewerPreferences.NON_FULL_SCREEN_PAGE_MODE> nfsModeCache = new HashMap<>();
        nfsModeCache.put(PdfNonFullScreenPageMode.USE_NONE, PDViewerPreferences.NON_FULL_SCREEN_PAGE_MODE.UseNone);
        nfsModeCache.put(PdfNonFullScreenPageMode.USE_OC, PDViewerPreferences.NON_FULL_SCREEN_PAGE_MODE.UseOC);
        nfsModeCache.put(PdfNonFullScreenPageMode.USE_OUTLINES, PDViewerPreferences.NON_FULL_SCREEN_PAGE_MODE.UseOutlines);
        nfsModeCache.put(PdfNonFullScreenPageMode.USE_THUMNS, PDViewerPreferences.NON_FULL_SCREEN_PAGE_MODE.UseThumbs);
        NFS_MODE_CACHE = Collections.unmodifiableMap(nfsModeCache);
        Map<PdfPageLayout, PageLayout> layoutCache = new HashMap<>();
        layoutCache.put(PdfPageLayout.SINGLE_PAGE, PageLayout.SINGLE_PAGE);
        layoutCache.put(PdfPageLayout.ONE_COLUMN, PageLayout.ONE_COLUMN);
        layoutCache.put(PdfPageLayout.TWO_COLUMN_LEFT, PageLayout.TWO_COLUMN_LEFT);
        layoutCache.put(PdfPageLayout.TWO_COLUMN_RIGHT, PageLayout.TWO_COLUMN_RIGHT);
        layoutCache.put(PdfPageLayout.TWO_PAGE_LEFT, PageLayout.TWO_PAGE_LEFT);
        layoutCache.put(PdfPageLayout.TWO_PAGE_RIGHT, PageLayout.TWO_PAGE_RIGHT);
        LAYOUT_CACHE = Collections.unmodifiableMap(layoutCache);
        Map<PdfPageMode, PageMode> pageModeCache = new HashMap<>();
        pageModeCache.put(PdfPageMode.USE_NONE, PageMode.USE_NONE);
        pageModeCache.put(PdfPageMode.USE_THUMBS, PageMode.USE_THUMBS);
        pageModeCache.put(PdfPageMode.USE_OUTLINES, PageMode.USE_OUTLINES);
        pageModeCache.put(PdfPageMode.FULLSCREEN, PageMode.FULL_SCREEN);
        pageModeCache.put(PdfPageMode.USE_OC, PageMode.USE_OPTIONAL_CONTENT);
        pageModeCache.put(PdfPageMode.USE_ATTACHMENTS, PageMode.USE_ATTACHMENTS);
        PAGE_MODE_CACHE = Collections.unmodifiableMap(pageModeCache);
        Map<PdfDuplex, PDViewerPreferences.DUPLEX> duplexCache = new HashMap<>();
        duplexCache.put(PdfDuplex.SIMPLEX, PDViewerPreferences.DUPLEX.Simplex);
        duplexCache.put(PdfDuplex.DUPLEX_FLIP_LONG_EDGE, PDViewerPreferences.DUPLEX.DuplexFlipLongEdge);
        duplexCache.put(PdfDuplex.DUPLEX_FLIP_SHORT_EDGE, PDViewerPreferences.DUPLEX.DuplexFlipShortEdge);
        DUPLEX_CACHE = Collections.unmodifiableMap(duplexCache);
    }

    private ViewerPreferencesUtils() {
    }

    public static PDViewerPreferences.NON_FULL_SCREEN_PAGE_MODE getNFSMode(PdfNonFullScreenPageMode nfsMode) {
        return NFS_MODE_CACHE.get(nfsMode);
    }

    public static PageMode getPageMode(PdfPageMode mode) {
        return PAGE_MODE_CACHE.get(mode);
    }

    public static PageLayout getPageLayout(PdfPageLayout layout) {
        return LAYOUT_CACHE.get(layout);
    }

    public static PDViewerPreferences.READING_DIRECTION getDirection(PdfDirection direction) {
        if (PdfDirection.RIGHT_TO_LEFT.equals(direction)) {
            return PDViewerPreferences.READING_DIRECTION.R2L;
        }
        return PDViewerPreferences.READING_DIRECTION.L2R;
    }

    public static PDViewerPreferences.DUPLEX getDuplex(PdfDuplex duplex) {
        return DUPLEX_CACHE.get(duplex);
    }

    public static PDViewerPreferences.PRINT_SCALING getPrintScaling(PdfPrintScaling scaling) {
        if (PdfPrintScaling.NONE.equals(scaling)) {
            return PDViewerPreferences.PRINT_SCALING.None;
        }
        return PDViewerPreferences.PRINT_SCALING.AppDefault;
    }

    public static void setBooleanPreferences(PDViewerPreferences preferences, Set<PdfBooleanPreference> enabled) throws TaskException {
        if (preferences == null) {
            throw new TaskException("Unable to set preferences on a null instance.");
        }
        for (PdfBooleanPreference current : PdfBooleanPreference.values()) {
            if (enabled.contains(current)) {
                PDFBoxActivableBooleanPreference.valueFromPdfBooleanPreference(current).enable(preferences);
                LOG.trace("{} = enabled.", current);
            } else {
                PDFBoxActivableBooleanPreference.valueFromPdfBooleanPreference(current).disable(preferences);
                LOG.trace("{} = disabled.", current);
            }
        }
    }

    /* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/util/ViewerPreferencesUtils$PDFBoxActivableBooleanPreference.class */
    private enum PDFBoxActivableBooleanPreference {
        HIDE_TOOLBAR(PdfBooleanPreference.HIDE_TOOLBAR) { // from class: org.sejda.impl.sambox.util.ViewerPreferencesUtils.PDFBoxActivableBooleanPreference.1
            @Override // org.sejda.impl.sambox.util.ViewerPreferencesUtils.PDFBoxActivableBooleanPreference
            void enable(PDViewerPreferences preferences) {
                preferences.setHideToolbar(true);
            }

            @Override // org.sejda.impl.sambox.util.ViewerPreferencesUtils.PDFBoxActivableBooleanPreference
            void disable(PDViewerPreferences preferences) {
                preferences.setHideToolbar(false);
            }
        },
        HIDE_MENUBAR(PdfBooleanPreference.HIDE_MENUBAR) { // from class: org.sejda.impl.sambox.util.ViewerPreferencesUtils.PDFBoxActivableBooleanPreference.2
            @Override // org.sejda.impl.sambox.util.ViewerPreferencesUtils.PDFBoxActivableBooleanPreference
            void enable(PDViewerPreferences preferences) {
                preferences.setHideMenubar(true);
            }

            @Override // org.sejda.impl.sambox.util.ViewerPreferencesUtils.PDFBoxActivableBooleanPreference
            void disable(PDViewerPreferences preferences) {
                preferences.setHideMenubar(false);
            }
        },
        HIDE_WINDOW_UI(PdfBooleanPreference.HIDE_WINDOW_UI) { // from class: org.sejda.impl.sambox.util.ViewerPreferencesUtils.PDFBoxActivableBooleanPreference.3
            @Override // org.sejda.impl.sambox.util.ViewerPreferencesUtils.PDFBoxActivableBooleanPreference
            void enable(PDViewerPreferences preferences) {
                preferences.setHideWindowUI(true);
            }

            @Override // org.sejda.impl.sambox.util.ViewerPreferencesUtils.PDFBoxActivableBooleanPreference
            void disable(PDViewerPreferences preferences) {
                preferences.setHideWindowUI(false);
            }
        },
        FIT_WINDOW(PdfBooleanPreference.FIT_WINDOW) { // from class: org.sejda.impl.sambox.util.ViewerPreferencesUtils.PDFBoxActivableBooleanPreference.4
            @Override // org.sejda.impl.sambox.util.ViewerPreferencesUtils.PDFBoxActivableBooleanPreference
            void enable(PDViewerPreferences preferences) {
                preferences.setFitWindow(true);
            }

            @Override // org.sejda.impl.sambox.util.ViewerPreferencesUtils.PDFBoxActivableBooleanPreference
            void disable(PDViewerPreferences preferences) {
                preferences.setFitWindow(false);
            }
        },
        CENTER_WINDOW(PdfBooleanPreference.CENTER_WINDOW) { // from class: org.sejda.impl.sambox.util.ViewerPreferencesUtils.PDFBoxActivableBooleanPreference.5
            @Override // org.sejda.impl.sambox.util.ViewerPreferencesUtils.PDFBoxActivableBooleanPreference
            void enable(PDViewerPreferences preferences) {
                preferences.setCenterWindow(true);
            }

            @Override // org.sejda.impl.sambox.util.ViewerPreferencesUtils.PDFBoxActivableBooleanPreference
            void disable(PDViewerPreferences preferences) {
                preferences.setCenterWindow(false);
            }
        },
        DISPLAY_DOC_TITLE(PdfBooleanPreference.DISPLAY_DOC_TITLE) { // from class: org.sejda.impl.sambox.util.ViewerPreferencesUtils.PDFBoxActivableBooleanPreference.6
            @Override // org.sejda.impl.sambox.util.ViewerPreferencesUtils.PDFBoxActivableBooleanPreference
            void enable(PDViewerPreferences preferences) {
                preferences.setDisplayDocTitle(true);
            }

            @Override // org.sejda.impl.sambox.util.ViewerPreferencesUtils.PDFBoxActivableBooleanPreference
            void disable(PDViewerPreferences preferences) {
                preferences.setDisplayDocTitle(false);
            }
        };

        private final PdfBooleanPreference preference;

        abstract void enable(PDViewerPreferences pDViewerPreferences);

        abstract void disable(PDViewerPreferences pDViewerPreferences);

        PDFBoxActivableBooleanPreference(PdfBooleanPreference preference) {
            this.preference = preference;
        }

        static PDFBoxActivableBooleanPreference valueFromPdfBooleanPreference(PdfBooleanPreference pref) {
            for (PDFBoxActivableBooleanPreference current : values()) {
                if (current.preference == pref) {
                    return current;
                }
            }
            throw new IllegalArgumentException(String.format("No activable preference found for %s", pref));
        }
    }
}
