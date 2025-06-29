package org.sejda.sambox.pdmodel.interactive.annotation;

import java.io.IOException;
import java.util.Optional;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.interactive.action.PDAction;
import org.sejda.sambox.pdmodel.interactive.action.PDActionFactory;
import org.sejda.sambox.pdmodel.interactive.action.PDActionURI;
import org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDAppearanceHandler;
import org.sejda.sambox.pdmodel.interactive.annotation.handlers.PDLinkAppearanceHandler;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.PDDestination;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.WithActionOrDestination;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/annotation/PDAnnotationLink.class */
public class PDAnnotationLink extends PDAnnotation implements WithActionOrDestination {
    private PDAppearanceHandler customAppearanceHandler;
    public static final String HIGHLIGHT_MODE_NONE = "N";
    public static final String HIGHLIGHT_MODE_INVERT = "I";
    public static final String HIGHLIGHT_MODE_OUTLINE = "O";
    public static final String HIGHLIGHT_MODE_PUSH = "P";
    public static final String SUB_TYPE = "Link";

    public PDAnnotationLink() {
        getCOSObject().setName(COSName.SUBTYPE, "Link");
    }

    public PDAnnotationLink(COSDictionary field) {
        super(field);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.WithActionOrDestination
    public PDAction getAction() {
        COSDictionary action = (COSDictionary) getCOSObject().getDictionaryObject(COSName.A, COSDictionary.class);
        return PDActionFactory.createAction(action);
    }

    public void setAction(PDAction action) {
        getCOSObject().setItem(COSName.A, action);
    }

    public void setBorderStyle(PDBorderStyleDictionary bs) {
        getCOSObject().setItem(COSName.BS, bs);
    }

    public PDBorderStyleDictionary getBorderStyle() {
        COSBase bs = getCOSObject().getDictionaryObject(COSName.BS);
        if (bs instanceof COSDictionary) {
            return new PDBorderStyleDictionary((COSDictionary) bs);
        }
        return null;
    }

    @Override // org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.WithActionOrDestination
    public PDDestination getDestination() throws IOException {
        return PDDestination.create(getCOSObject().getDictionaryObject(COSName.DEST));
    }

    public void setDestination(PDDestination dest) {
        getCOSObject().setItem(COSName.DEST, dest);
    }

    public String getHighlightMode() {
        return getCOSObject().getNameAsString(COSName.H, "I");
    }

    public void setHighlightMode(String mode) {
        getCOSObject().setName(COSName.H, mode);
    }

    public void setPreviousURI(PDActionURI pa) {
        getCOSObject().setItem("PA", pa);
    }

    public PDActionURI getPreviousURI() {
        COSDictionary pa = (COSDictionary) getCOSObject().getDictionaryObject("PA", COSDictionary.class);
        if (pa != null) {
            return new PDActionURI(pa);
        }
        return null;
    }

    public void setQuadPoints(float[] quadPoints) {
        COSArray newQuadPoints = new COSArray();
        newQuadPoints.setFloatArray(quadPoints);
        getCOSObject().setItem(COSName.QUADPOINTS, (COSBase) newQuadPoints);
    }

    public float[] getQuadPoints() {
        return (float[]) Optional.ofNullable((COSArray) getCOSObject().getDictionaryObject(COSName.QUADPOINTS, COSArray.class)).map((v0) -> {
            return v0.toFloatArray();
        }).orElse(null);
    }

    public void setCustomAppearanceHandler(PDAppearanceHandler appearanceHandler) {
        this.customAppearanceHandler = appearanceHandler;
    }

    @Override // org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation
    public void constructAppearances() {
        if (this.customAppearanceHandler == null) {
            PDLinkAppearanceHandler appearanceHandler = new PDLinkAppearanceHandler(this);
            appearanceHandler.generateAppearanceStreams();
        } else {
            this.customAppearanceHandler.generateAppearanceStreams();
        }
    }
}
