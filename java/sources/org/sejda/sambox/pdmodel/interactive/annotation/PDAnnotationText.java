package org.sejda.sambox.pdmodel.interactive.annotation;

import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDAppearanceHandler;
import org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDTextAppearanceHandler;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/annotation/PDAnnotationText.class */
public class PDAnnotationText extends PDAnnotationMarkup {
    private PDAppearanceHandler customAppearanceHandler;
    public static final String NAME_COMMENT = "Comment";
    public static final String NAME_KEY = "Key";
    public static final String NAME_NOTE = "Note";
    public static final String NAME_HELP = "Help";
    public static final String NAME_NEW_PARAGRAPH = "NewParagraph";
    public static final String NAME_PARAGRAPH = "Paragraph";
    public static final String NAME_INSERT = "Insert";
    public static final String NAME_CIRCLE = "Circle";
    public static final String NAME_CROSS = "Cross";
    public static final String NAME_STAR = "Star";
    public static final String NAME_CHECK = "Check";
    public static final String NAME_RIGHT_ARROW = "RightArrow";
    public static final String NAME_RIGHT_POINTER = "RightPointer";
    public static final String NAME_UP_ARROW = "UpArrow";
    public static final String NAME_UP_LEFT_ARROW = "UpLeftArrow";
    public static final String NAME_CROSS_HAIRS = "CrossHairs";
    public static final String SUB_TYPE = "Text";

    public PDAnnotationText() {
        getCOSObject().setName(COSName.SUBTYPE, SUB_TYPE);
    }

    public PDAnnotationText(COSDictionary field) {
        super(field);
    }

    public void setOpen(boolean open) {
        getCOSObject().setBoolean(COSName.getPDFName("Open"), open);
    }

    public boolean getOpen() {
        return getCOSObject().getBoolean(COSName.getPDFName("Open"), false);
    }

    public void setName(String name) {
        getCOSObject().setName(COSName.NAME, name);
    }

    public String getName() {
        return getCOSObject().getNameAsString(COSName.NAME, "Note");
    }

    public String getState() {
        return getCOSObject().getString(COSName.STATE);
    }

    public void setState(String state) {
        getCOSObject().setString(COSName.STATE, state);
    }

    public String getStateModel() {
        return getCOSObject().getString(COSName.STATE_MODEL);
    }

    public void setStateModel(String stateModel) {
        getCOSObject().setString(COSName.STATE_MODEL, stateModel);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup
    public void setCustomAppearanceHandler(PDAppearanceHandler appearanceHandler) {
        this.customAppearanceHandler = appearanceHandler;
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup, org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation
    public void constructAppearances() {
        if (this.customAppearanceHandler == null) {
            PDTextAppearanceHandler appearanceHandler = new PDTextAppearanceHandler(this);
            appearanceHandler.generateAppearanceStreams();
        } else {
            this.customAppearanceHandler.generateAppearanceStreams();
        }
    }
}
