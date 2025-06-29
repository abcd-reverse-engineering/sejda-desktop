package org.sejda.sambox.pdmodel.interactive.annotation;

import java.util.Calendar;
import java.util.Objects;
import java.util.Optional;
import org.sejda.commons.util.RequireUtils;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSInteger;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSNumber;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.common.PDDictionaryWrapper;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.documentinterchange.markedcontent.PDPropertyList;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.pdmodel.graphics.color.PDDeviceCMYK;
import org.sejda.sambox.pdmodel.graphics.color.PDDeviceGray;
import org.sejda.sambox.pdmodel.graphics.color.PDDeviceRGB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/annotation/PDAnnotation.class */
public abstract class PDAnnotation extends PDDictionaryWrapper {
    private static final Logger LOG = LoggerFactory.getLogger(PDAnnotation.class);
    public static final int FLAG_INVISIBLE = 1;
    public static final int FLAG_HIDDEN = 2;
    public static final int FLAG_PRINTED = 4;
    public static final int FLAG_NO_ZOOM = 8;
    public static final int FLAG_NO_ROTATE = 16;
    public static final int FLAG_NO_VIEW = 32;
    public static final int FLAG_READ_ONLY = 64;
    public static final int FLAG_LOCKED = 128;
    public static final int FLAG_TOGGLE_NO_VIEW = 256;
    private static final int FLAG_LOCKED_CONTENTS = 512;

    public static <T extends PDAnnotation> T createAnnotation(COSDictionary dictionary, Class<T> expectedType) {
        PDAnnotation annotation = createAnnotation(dictionary);
        if (Objects.nonNull(annotation)) {
            Optional optionalOf = Optional.of(annotation);
            Objects.requireNonNull(expectedType);
            Optional optionalFilter = optionalOf.filter((v1) -> {
                return r1.isInstance(v1);
            });
            Objects.requireNonNull(expectedType);
            return (T) optionalFilter.map((v1) -> {
                return r1.cast(v1);
            }).orElseGet(() -> {
                LOG.warn("Expected annotation type {} but got {}", expectedType, annotation.getClass());
                return null;
            });
        }
        return null;
    }

    public static PDAnnotation createAnnotation(COSBase base) {
        RequireUtils.requireArg(base instanceof COSDictionary, "Illegal annotation type " + base);
        COSDictionary annotDic = (COSDictionary) base;
        String subtype = annotDic.getNameAsString(COSName.SUBTYPE);
        if (PDAnnotationFileAttachment.SUB_TYPE.equals(subtype)) {
            return new PDAnnotationFileAttachment(annotDic);
        }
        if (PDAnnotationLine.SUB_TYPE.equals(subtype)) {
            return new PDAnnotationLine(annotDic);
        }
        if ("Link".equals(subtype)) {
            return new PDAnnotationLink(annotDic);
        }
        if (PDAnnotationPopup.SUB_TYPE.equals(subtype)) {
            return new PDAnnotationPopup(annotDic);
        }
        if (PDAnnotationRubberStamp.SUB_TYPE.equals(subtype)) {
            return new PDAnnotationRubberStamp(annotDic);
        }
        if ("Square".equals(subtype) || "Circle".equals(subtype)) {
            return new PDAnnotationSquareCircle(annotDic);
        }
        if (PDAnnotationText.SUB_TYPE.equals(subtype)) {
            return new PDAnnotationText(annotDic);
        }
        if (PDAnnotationTextMarkup.SUB_TYPE_HIGHLIGHT.equals(subtype) || "Underline".equals(subtype) || PDAnnotationTextMarkup.SUB_TYPE_SQUIGGLY.equals(subtype) || PDAnnotationTextMarkup.SUB_TYPE_STRIKEOUT.equals(subtype)) {
            return new PDAnnotationTextMarkup(annotDic);
        }
        if (COSName.WIDGET.getName().equals(subtype)) {
            return new PDAnnotationWidget(annotDic);
        }
        if ("FreeText".equals(subtype) || PDAnnotationMarkup.SUB_TYPE_POLYGON.equals(subtype) || PDAnnotationMarkup.SUB_TYPE_POLYLINE.equals(subtype) || PDAnnotationMarkup.SUB_TYPE_CARET.equals(subtype) || PDAnnotationMarkup.SUB_TYPE_INK.equals(subtype) || "Sound".equals(subtype)) {
            return new PDAnnotationMarkup(annotDic);
        }
        LOG.warn("Unsupported annotation subtype " + subtype);
        return new PDAnnotationUnknown(annotDic);
    }

    public PDAnnotation() {
        getCOSObject().setItem(COSName.TYPE, (COSBase) COSName.ANNOT);
    }

    public PDAnnotation(COSDictionary dictionary) {
        super(dictionary);
        COSBase type = dictionary.getDictionaryObject(COSName.TYPE);
        if (type == null) {
            getCOSObject().setItem(COSName.TYPE, (COSBase) COSName.ANNOT);
        } else if (!COSName.ANNOT.equals(type)) {
            LOG.warn("Wrong annotation type, expected {} but was {}", COSName.ANNOT.getName(), type);
        }
    }

    public PDRectangle getRectangle() {
        COSArray rectArray = (COSArray) getCOSObject().getDictionaryObject(COSName.RECT, COSArray.class);
        if (rectArray != null) {
            if (rectArray.size() == 4 && (rectArray.getObject(0) instanceof COSNumber) && (rectArray.getObject(1) instanceof COSNumber) && (rectArray.getObject(2) instanceof COSNumber) && (rectArray.getObject(3) instanceof COSNumber)) {
                return new PDRectangle(rectArray);
            }
            LOG.warn(rectArray + " is not a rectangle array, returning null");
            return null;
        }
        return null;
    }

    public void setRectangle(PDRectangle rectangle) {
        getCOSObject().setItem(COSName.RECT, (COSBase) rectangle.getCOSObject());
    }

    public int getAnnotationFlags() {
        return getCOSObject().getInt(COSName.F, 0);
    }

    public void setAnnotationFlags(int flags) {
        getCOSObject().setInt(COSName.F, flags);
    }

    public COSName getAppearanceState() {
        return (COSName) getCOSObject().getDictionaryObject(COSName.AS, COSName.class);
    }

    public void setAppearanceState(String as) {
        getCOSObject().setName(COSName.AS, as);
    }

    public PDAppearanceDictionary getAppearance() {
        return (PDAppearanceDictionary) Optional.ofNullable((COSDictionary) getCOSObject().getDictionaryObject(COSName.AP, COSDictionary.class)).map(PDAppearanceDictionary::new).orElse(null);
    }

    public void setAppearance(PDAppearanceDictionary appearance) {
        getCOSObject().setItem(COSName.AP, appearance);
    }

    public PDAppearanceStream getNormalAppearanceStream() {
        PDAppearanceEntry normalAppearance;
        PDAppearanceDictionary appearanceDict = getAppearance();
        if (appearanceDict == null || (normalAppearance = appearanceDict.getNormalAppearance()) == null) {
            return null;
        }
        if (normalAppearance.isSubDictionary()) {
            COSName state = getAppearanceState();
            return normalAppearance.getSubDictionary().get(state);
        }
        if (normalAppearance.isStream()) {
            return normalAppearance.getAppearanceStream();
        }
        return null;
    }

    public boolean isInvisible() {
        return getCOSObject().getFlag(COSName.F, 1);
    }

    public void setInvisible(boolean invisible) {
        getCOSObject().setFlag(COSName.F, 1, invisible);
    }

    public boolean isHidden() {
        return getCOSObject().getFlag(COSName.F, 2);
    }

    public void setHidden(boolean hidden) {
        getCOSObject().setFlag(COSName.F, 2, hidden);
    }

    public boolean isPrinted() {
        return getCOSObject().getFlag(COSName.F, 4);
    }

    public void setPrinted(boolean printed) {
        getCOSObject().setFlag(COSName.F, 4, printed);
    }

    public boolean isNoZoom() {
        return getCOSObject().getFlag(COSName.F, 8);
    }

    public void setNoZoom(boolean noZoom) {
        getCOSObject().setFlag(COSName.F, 8, noZoom);
    }

    public boolean isNoRotate() {
        return getCOSObject().getFlag(COSName.F, 16);
    }

    public void setNoRotate(boolean noRotate) {
        getCOSObject().setFlag(COSName.F, 16, noRotate);
    }

    public boolean isNoView() {
        return getCOSObject().getFlag(COSName.F, 32);
    }

    public void setNoView(boolean noView) {
        getCOSObject().setFlag(COSName.F, 32, noView);
    }

    public boolean isReadOnly() {
        return getCOSObject().getFlag(COSName.F, 64);
    }

    public void setReadOnly(boolean readOnly) {
        getCOSObject().setFlag(COSName.F, 64, readOnly);
    }

    public boolean isLocked() {
        return getCOSObject().getFlag(COSName.F, FLAG_LOCKED);
    }

    public void setLocked(boolean locked) {
        getCOSObject().setFlag(COSName.F, FLAG_LOCKED, locked);
    }

    public boolean isToggleNoView() {
        return getCOSObject().getFlag(COSName.F, FLAG_TOGGLE_NO_VIEW);
    }

    public void setToggleNoView(boolean toggleNoView) {
        getCOSObject().setFlag(COSName.F, FLAG_TOGGLE_NO_VIEW, toggleNoView);
    }

    public boolean isLockedContents() {
        return getCOSObject().getFlag(COSName.F, FLAG_LOCKED_CONTENTS);
    }

    public void setLockedContents(boolean lockedContents) {
        getCOSObject().setFlag(COSName.F, FLAG_LOCKED_CONTENTS, lockedContents);
    }

    public String getContents() {
        return getCOSObject().getString(COSName.CONTENTS);
    }

    public void setContents(String value) {
        getCOSObject().setString(COSName.CONTENTS, value);
    }

    public String getModifiedDate() {
        return getCOSObject().getString(COSName.M);
    }

    public void setModifiedDate(String m) {
        getCOSObject().setString(COSName.M, m);
    }

    public void setModifiedDate(Calendar c) {
        getCOSObject().setDate(COSName.M, c);
    }

    public String getAnnotationName() {
        return getCOSObject().getString(COSName.NM);
    }

    public void setAnnotationName(String nm) {
        getCOSObject().setString(COSName.NM, nm);
    }

    public int getStructParent() {
        return getCOSObject().getInt(COSName.STRUCT_PARENT);
    }

    public void setStructParent(int structParent) {
        getCOSObject().setInt(COSName.STRUCT_PARENT, structParent);
    }

    public PDPropertyList getOptionalContent() {
        COSDictionary base = (COSDictionary) getCOSObject().getDictionaryObject(COSName.OC, COSDictionary.class);
        if (Objects.nonNull(base)) {
            return PDPropertyList.create(base);
        }
        return null;
    }

    public void setOptionalContent(PDPropertyList oc) {
        getCOSObject().setItem(COSName.OC, oc);
    }

    public COSArray getBorder() {
        COSArray border = (COSArray) getCOSObject().getDictionaryObject(COSName.BORDER, COSArray.class);
        if (Objects.isNull(border)) {
            return new COSArray(COSInteger.ZERO, COSInteger.ZERO, COSInteger.ONE);
        }
        border.growToSize(3, COSInteger.ZERO);
        return border;
    }

    public void setBorder(COSArray borderArray) {
        getCOSObject().setItem(COSName.BORDER, (COSBase) borderArray);
    }

    public void setColor(PDColor c) {
        getCOSObject().setItem(COSName.C, (COSBase) c.toComponentsCOSArray());
    }

    public PDColor getColor() {
        return getColor(COSName.C);
    }

    public PDColor getColor(COSName itemName) {
        COSArray color = (COSArray) getCOSObject().getDictionaryObject(itemName, COSArray.class);
        if (Objects.nonNull(color) && color.size() > 0) {
            switch (color.size()) {
                case 1:
                    return new PDColor(color, PDDeviceGray.INSTANCE);
                case 2:
                default:
                    return null;
                case 3:
                    return new PDColor(color, PDDeviceRGB.INSTANCE);
                case 4:
                    return new PDColor(color, PDDeviceCMYK.INSTANCE);
            }
        }
        return null;
    }

    public String getSubtype() {
        return getCOSObject().getNameAsString(COSName.SUBTYPE);
    }

    public void setPage(PDPage page) {
        getCOSObject().setItem(COSName.P, page);
    }

    public PDPage getPage() {
        COSDictionary p = (COSDictionary) getCOSObject().getDictionaryObject(COSName.P, COSDictionary.class);
        if (Objects.nonNull(p)) {
            return new PDPage(p);
        }
        return null;
    }

    public void constructAppearances() {
    }
}
