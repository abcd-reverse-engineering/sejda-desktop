package org.sejda.sambox.pdmodel.interactive.annotation;

import java.util.Objects;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/annotation/PDAppearanceDictionary.class */
public class PDAppearanceDictionary implements COSObjectable {
    private final COSDictionary dictionary;

    public PDAppearanceDictionary() {
        this.dictionary = new COSDictionary();
        this.dictionary.setItem(COSName.N, (COSBase) new COSDictionary());
    }

    public PDAppearanceDictionary(COSDictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSDictionary getCOSObject() {
        return this.dictionary;
    }

    public PDAppearanceEntry getNormalAppearance() {
        COSBase entry = this.dictionary.getDictionaryObject(COSName.N);
        if (Objects.nonNull(entry)) {
            return new PDAppearanceEntry(entry);
        }
        return null;
    }

    public void setNormalAppearance(PDAppearanceEntry entry) {
        this.dictionary.setItem(COSName.N, entry);
    }

    public void setNormalAppearance(PDAppearanceStream ap) {
        this.dictionary.setItem(COSName.N, ap);
    }

    public PDAppearanceEntry getRolloverAppearance() {
        COSBase entry = this.dictionary.getDictionaryObject(COSName.R);
        if (Objects.nonNull(entry)) {
            return new PDAppearanceEntry(entry);
        }
        return getNormalAppearance();
    }

    public void setRolloverAppearance(PDAppearanceEntry entry) {
        this.dictionary.setItem(COSName.R, entry);
    }

    public void setRolloverAppearance(PDAppearanceStream ap) {
        this.dictionary.setItem(COSName.R, ap);
    }

    public PDAppearanceEntry getDownAppearance() {
        COSBase entry = this.dictionary.getDictionaryObject(COSName.D);
        if (Objects.nonNull(entry)) {
            return new PDAppearanceEntry(entry);
        }
        return getNormalAppearance();
    }

    public void setDownAppearance(PDAppearanceEntry entry) {
        this.dictionary.setItem(COSName.D, entry);
    }

    public void setDownAppearance(PDAppearanceStream ap) {
        this.dictionary.setItem(COSName.D, ap);
    }
}
