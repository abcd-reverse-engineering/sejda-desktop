package org.sejda.impl.sambox.util;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.font.PDFont;
import org.sejda.sambox.pdmodel.interactive.form.PDAcroForm;
import org.sejda.sambox.pdmodel.interactive.form.PDVariableText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/util/AcroFormUtils.class */
public final class AcroFormUtils {
    private static final Logger LOG = LoggerFactory.getLogger(AcroFormUtils.class);

    private AcroFormUtils() {
    }

    public static void mergeDefaults(PDAcroForm mergeThis, PDAcroForm intoThis) {
        if (!intoThis.isNeedAppearances() && mergeThis.isNeedAppearances()) {
            intoThis.setNeedAppearances(true);
        }
        String da = mergeThis.getDefaultAppearance();
        if (StringUtils.isBlank(intoThis.getDefaultAppearance()) && !StringUtils.isBlank(da)) {
            intoThis.setDefaultAppearance(da);
        }
        int quadding = mergeThis.getCOSObject().getInt(COSName.Q);
        if (quadding >= 0 && quadding <= 2 && !intoThis.getCOSObject().containsKey(COSName.Q)) {
            intoThis.setQuadding(quadding);
        }
        COSDictionary formResources = (COSDictionary) Optional.ofNullable((COSDictionary) intoThis.getCOSObject().getDictionaryObject(COSName.DR, COSDictionary.class)).orElseGet(COSDictionary::new);
        Optional.ofNullable((COSDictionary) mergeThis.getCOSObject().getDictionaryObject(COSName.DR, COSDictionary.class)).ifPresent(dr -> {
            for (COSName currentKey : dr.keySet()) {
                Optional.ofNullable(dr.getDictionaryObject(currentKey)).ifPresent(value -> {
                    if (value instanceof COSDictionary) {
                        mergeResourceDictionaryValue(formResources, (COSDictionary) value, currentKey);
                    } else if (value instanceof COSArray) {
                        mergeResourceArrayValue(formResources, (COSArray) value, currentKey);
                    } else {
                        LOG.warn("Unsupported resource dictionary type {}", value);
                    }
                });
            }
        });
        intoThis.getCOSObject().setItem(COSName.DR, (COSBase) formResources);
        LOG.debug("Merged AcroForm dictionary");
    }

    private static void mergeResourceArrayValue(COSDictionary formResources, COSArray value, COSName currentKey) {
        COSArray currentItem = (COSArray) Optional.ofNullable((COSArray) formResources.getDictionaryObject(currentKey, COSArray.class)).orElseGet(COSArray::new);
        Stream streamFilter = value.stream().filter(i -> {
            return !currentItem.contains(i);
        });
        Objects.requireNonNull(currentItem);
        streamFilter.forEach(currentItem::add);
        formResources.setItem(currentKey, (COSBase) currentItem);
    }

    private static void mergeResourceDictionaryValue(COSDictionary formResources, COSDictionary value, COSName currentKey) {
        COSDictionary currentItem = (COSDictionary) Optional.ofNullable((COSDictionary) formResources.getDictionaryObject(currentKey, COSDictionary.class)).orElseGet(COSDictionary::new);
        currentItem.mergeWithoutOverwriting(value);
        formResources.setItem(currentKey, (COSBase) currentItem);
    }

    public static void ensureValueCanBeDisplayed(PDVariableText field, PDDocument document) throws IOException {
        String value = field.getValueAsString();
        PDFont originalFont = field.getAppearanceFont();
        if (!FontUtils.canDisplay(value, originalFont)) {
            PDFont fallbackFont = FontUtils.findFontFor(document, value, false, originalFont);
            field.setAppearanceOverrideFont(fallbackFont);
            field.applyChange();
            LOG.debug("Form field can't render (in appearances) it's value '{}', will use font {} for better support", value, fallbackFont);
        }
    }
}
