package org.sejda.sambox.pdmodel.interactive.form;

import java.io.IOException;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/form/PDTextField.class */
public final class PDTextField extends PDVariableText {
    private static final int FLAG_MULTILINE = 4096;
    private static final int FLAG_PASSWORD = 8192;
    private static final int FLAG_FILE_SELECT = 1048576;
    private static final int FLAG_DO_NOT_SPELL_CHECK = 4194304;
    private static final int FLAG_DO_NOT_SCROLL = 8388608;
    private static final int FLAG_COMB = 16777216;
    private static final int FLAG_RICH_TEXT = 33554432;

    public PDTextField(PDAcroForm acroForm) {
        super(acroForm);
        getCOSObject().setItem(COSName.FT, (COSBase) COSName.TX);
    }

    PDTextField(PDAcroForm acroForm, COSDictionary field, PDNonTerminalField parent) {
        super(acroForm, field, parent);
    }

    public boolean isMultiline() {
        return getCOSObject().getFlag(COSName.FF, FLAG_MULTILINE);
    }

    public void setMultiline(boolean multiline) {
        getCOSObject().setFlag(COSName.FF, FLAG_MULTILINE, multiline);
    }

    public boolean isPassword() {
        return getCOSObject().getFlag(COSName.FF, FLAG_PASSWORD);
    }

    public void setPassword(boolean password) {
        getCOSObject().setFlag(COSName.FF, FLAG_PASSWORD, password);
    }

    public boolean isFileSelect() {
        return getCOSObject().getFlag(COSName.FF, FLAG_FILE_SELECT);
    }

    public void setFileSelect(boolean fileSelect) {
        getCOSObject().setFlag(COSName.FF, FLAG_FILE_SELECT, fileSelect);
    }

    public boolean doNotSpellCheck() {
        return getCOSObject().getFlag(COSName.FF, FLAG_DO_NOT_SPELL_CHECK);
    }

    public void setDoNotSpellCheck(boolean doNotSpellCheck) {
        getCOSObject().setFlag(COSName.FF, FLAG_DO_NOT_SPELL_CHECK, doNotSpellCheck);
    }

    public boolean doNotScroll() {
        return getCOSObject().getFlag(COSName.FF, FLAG_DO_NOT_SCROLL);
    }

    public void setDoNotScroll(boolean doNotScroll) {
        getCOSObject().setFlag(COSName.FF, FLAG_DO_NOT_SCROLL, doNotScroll);
    }

    public boolean isComb() {
        return getCOSObject().getFlag(COSName.FF, FLAG_COMB);
    }

    public void setComb(boolean comb) {
        getCOSObject().setFlag(COSName.FF, FLAG_COMB, comb);
    }

    public boolean isRichText() {
        return getCOSObject().getFlag(COSName.FF, FLAG_RICH_TEXT);
    }

    public void setRichText(boolean richText) {
        getCOSObject().setFlag(COSName.FF, FLAG_RICH_TEXT, richText);
    }

    public int getMaxLen() {
        return getCOSObject().getInt(COSName.MAX_LEN);
    }

    public void setMaxLen(int maxLen) {
        getCOSObject().setInt(COSName.MAX_LEN, maxLen);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.form.PDField
    public void setValue(String value) throws IOException {
        getCOSObject().setString(COSName.V, value);
        applyChange();
    }

    public void setDefaultValue(String value) throws IOException {
        getCOSObject().setString(COSName.DV, value);
    }

    public String getValue() {
        return getStringOrStream(getInheritableAttribute(COSName.V));
    }

    public String getDefaultValue() {
        return getStringOrStream(getInheritableAttribute(COSName.DV));
    }

    @Override // org.sejda.sambox.pdmodel.interactive.form.PDField
    public String getValueAsString() {
        return getValue();
    }

    @Override // org.sejda.sambox.pdmodel.interactive.form.PDTerminalField
    void constructAppearances() throws IOException {
        AppearanceGeneratorHelper apHelper = new AppearanceGeneratorHelper(this);
        apHelper.setAppearanceValue(getValue());
    }
}
