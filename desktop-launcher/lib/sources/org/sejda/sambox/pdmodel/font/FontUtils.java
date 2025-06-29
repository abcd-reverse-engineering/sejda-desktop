package org.sejda.sambox.pdmodel.font;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import org.apache.fontbox.ttf.TrueTypeFont;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/FontUtils.class */
public final class FontUtils {
    private static final String BASE25 = "BCDEFGHIJKLMNOPQRSTUVWXYZ";

    private FontUtils() {
    }

    public static boolean isEmbeddingPermitted(TrueTypeFont ttf) throws IOException {
        if (ttf.getOS2Windows() != null) {
            int fsType = ttf.getOS2Windows().getFsType();
            int maskedFsType = fsType & 15;
            return (maskedFsType == 2 || (fsType & 512) == 512) ? false : true;
        }
        return true;
    }

    public static boolean isSubsettingPermitted(TrueTypeFont ttf) throws IOException {
        if (ttf.getOS2Windows() != null) {
            int fsType = ttf.getOS2Windows().getFsType();
            return (fsType & PDAnnotation.FLAG_TOGGLE_NO_VIEW) != 256;
        }
        return true;
    }

    public static String getTag(Map<Integer, Integer> gidToCid) {
        long num = gidToCid.hashCode();
        StringBuilder sb = new StringBuilder();
        do {
            long div = num / 25;
            int mod = (int) (num % 25);
            sb.append(BASE25.charAt(mod));
            num = div;
            if (num == 0) {
                break;
            }
        } while (sb.length() < 6);
        while (sb.length() < 6) {
            sb.insert(0, 'A');
        }
        return sb.append('+').toString();
    }

    public static String getTag() {
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < 6; k++) {
            sb.append((char) ((Math.random() * 26.0d) + 65.0d));
        }
        return sb.append('+').toString();
    }

    public static boolean isSubsetFontName(String name) {
        String[] nameFragments = (String[]) Optional.ofNullable(name).map((v0) -> {
            return v0.trim();
        }).map(s -> {
            return s.split("\\+");
        }).orElseGet(() -> {
            return new String[0];
        });
        return nameFragments.length == 2 && nameFragments[0].length() == 6;
    }
}
