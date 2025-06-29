package org.sejda.sambox.pdmodel.interactive.form;

import java.io.IOException;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSNumber;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.cos.COSString;
import org.sejda.sambox.pdmodel.PDResources;
import org.sejda.sambox.pdmodel.font.PDFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/form/PDVariableText.class */
public abstract class PDVariableText extends PDTerminalField {
    public static final int QUADDING_LEFT = 0;
    public static final int QUADDING_CENTERED = 1;
    public static final int QUADDING_RIGHT = 2;
    private static final Logger LOG = LoggerFactory.getLogger(PDVariableText.class);
    private PDFont appearanceOverrideFont;

    PDVariableText(PDAcroForm acroForm) {
        super(acroForm);
        this.appearanceOverrideFont = null;
    }

    PDVariableText(PDAcroForm acroForm, COSDictionary field, PDNonTerminalField parent) {
        super(acroForm, field, parent);
        this.appearanceOverrideFont = null;
    }

    public String getDefaultAppearance() {
        try {
            COSString da = DefaultAppearanceHelper.getDefaultAppearance(this);
            if (da != null) {
                return da.getString();
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    PDDefaultAppearanceString getDefaultAppearanceString() throws IOException {
        try {
            COSString da = DefaultAppearanceHelper.getDefaultAppearance(this);
            PDResources dr = getAcroForm().getDefaultResources();
            return new PDDefaultAppearanceString(da, dr);
        } catch (IOException e) {
            return new PDDefaultAppearanceString();
        }
    }

    public void setDefaultAppearance(String daValue) {
        getCOSObject().setString(COSName.DA, daValue);
    }

    public String getDefaultStyleString() {
        COSString defaultStyleString = (COSString) getCOSObject().getDictionaryObject(COSName.DS);
        return defaultStyleString.getString();
    }

    public void setDefaultStyleString(String defaultStyleString) {
        if (defaultStyleString != null) {
            getCOSObject().setItem(COSName.DS, (COSBase) COSString.parseLiteral(defaultStyleString));
        } else {
            getCOSObject().removeItem(COSName.DS);
        }
    }

    public int getQ() {
        int retval = 0;
        try {
            COSNumber number = (COSNumber) getInheritableAttribute(COSName.Q);
            if (number != null) {
                retval = number.intValue();
            }
        } catch (Exception e) {
            LOG.warn("Error parsing Q attribute, using default");
        }
        return retval;
    }

    public void setQ(int q) {
        getCOSObject().setInt(COSName.Q, q);
    }

    public String getRichTextValue() {
        return getStringOrStream(getInheritableAttribute(COSName.RV));
    }

    public void setRichTextValue(String richTextValue) {
        if (richTextValue != null) {
            getCOSObject().setItem(COSName.RV, (COSBase) COSString.parseLiteral(richTextValue));
        } else {
            getCOSObject().removeItem(COSName.RV);
        }
    }

    protected final String getStringOrStream(COSBase base) {
        if (base instanceof COSString) {
            return ((COSString) base).getString();
        }
        if (base instanceof COSStream) {
            return ((COSStream) base).asTextString();
        }
        return "";
    }

    public PDFont getAppearanceFont() {
        try {
            if (this.appearanceOverrideFont != null) {
                return this.appearanceOverrideFont;
            }
            return getDefaultAppearanceString().getFont();
        } catch (IOException e) {
            return null;
        }
    }

    public PDFont getAppearanceOverrideFont() {
        return this.appearanceOverrideFont;
    }

    public void setAppearanceOverrideFont(PDFont appearanceOverrideFont) {
        this.appearanceOverrideFont = appearanceOverrideFont;
    }
}
