package org.sejda.sambox.pdmodel.graphics;

import java.io.IOException;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSFloat;
import org.sejda.sambox.cos.COSNumber;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.pdmodel.font.PDFont;
import org.sejda.sambox.pdmodel.font.PDFontFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/PDFontSetting.class */
public class PDFontSetting implements COSObjectable {
    private COSArray fontSetting;

    public PDFontSetting() {
        this.fontSetting = null;
        this.fontSetting = new COSArray();
        this.fontSetting.add((COSBase) null);
        this.fontSetting.add((COSBase) new COSFloat(1.0f));
    }

    public PDFontSetting(COSArray fs) {
        this.fontSetting = null;
        this.fontSetting = fs;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSBase getCOSObject() {
        return this.fontSetting;
    }

    public PDFont getFont() throws IOException {
        PDFont retval = null;
        COSBase font = this.fontSetting.getObject(0);
        if (font instanceof COSDictionary) {
            retval = PDFontFactory.createFont((COSDictionary) font);
        }
        return retval;
    }

    public void setFont(PDFont font) {
        this.fontSetting.set(0, (COSObjectable) font);
    }

    public float getFontSize() {
        COSNumber size = (COSNumber) this.fontSetting.get(1);
        return size.floatValue();
    }

    public void setFontSize(float size) {
        this.fontSetting.set(1, (COSBase) new COSFloat(size));
    }
}
