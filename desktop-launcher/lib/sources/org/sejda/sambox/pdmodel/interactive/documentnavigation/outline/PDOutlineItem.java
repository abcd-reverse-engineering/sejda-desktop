package org.sejda.sambox.pdmodel.interactive.documentnavigation.outline;

import java.awt.Color;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSFloat;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.documentinterchange.logicalstructure.PDStructureElement;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.pdmodel.graphics.color.PDDeviceRGB;
import org.sejda.sambox.pdmodel.interactive.action.PDAction;
import org.sejda.sambox.pdmodel.interactive.action.PDActionFactory;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.PDDestination;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.PDPageXYZDestination;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.WithActionOrDestination;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/documentnavigation/outline/PDOutlineItem.class */
public final class PDOutlineItem extends PDOutlineNode implements WithActionOrDestination {
    private static final int ITALIC_FLAG = 1;
    private static final int BOLD_FLAG = 2;

    public PDOutlineItem() {
    }

    public PDOutlineItem(COSDictionary dic) {
        super(dic);
    }

    public void insertSiblingAfter(PDOutlineItem newSibling) {
        requireSingleNode(newSibling);
        PDOutlineNode parent = getParent();
        newSibling.setParent(parent);
        PDOutlineItem next = getNextSibling();
        setNextSibling(newSibling);
        newSibling.setPreviousSibling(this);
        if (next != null) {
            newSibling.setNextSibling(next);
            next.setPreviousSibling(newSibling);
        } else if (parent != null) {
            getParent().setLastChild(newSibling);
        }
        updateParentOpenCountForAddedChild(newSibling);
    }

    public void insertSiblingBefore(PDOutlineItem newSibling) {
        requireSingleNode(newSibling);
        PDOutlineNode parent = getParent();
        newSibling.setParent(parent);
        PDOutlineItem previous = getPreviousSibling();
        setPreviousSibling(newSibling);
        newSibling.setNextSibling(this);
        if (previous != null) {
            previous.setNextSibling(newSibling);
            newSibling.setPreviousSibling(previous);
        } else if (parent != null) {
            getParent().setFirstChild(newSibling);
        }
        updateParentOpenCountForAddedChild(newSibling);
    }

    public PDOutlineItem getPreviousSibling() {
        return getOutlineItem(COSName.PREV);
    }

    void setPreviousSibling(PDOutlineNode outlineNode) {
        getCOSObject().setItem(COSName.PREV, outlineNode);
    }

    public PDOutlineItem getNextSibling() {
        return getOutlineItem(COSName.NEXT);
    }

    void setNextSibling(PDOutlineNode outlineNode) {
        getCOSObject().setItem(COSName.NEXT, outlineNode);
    }

    public String getTitle() {
        return getCOSObject().getString(COSName.TITLE);
    }

    public void setTitle(String title) {
        getCOSObject().setString(COSName.TITLE, title);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.WithActionOrDestination
    public PDDestination getDestination() throws IOException {
        return PDDestination.create(getCOSObject().getDictionaryObject(COSName.DEST));
    }

    public void setDestination(PDDestination dest) {
        getCOSObject().setItem(COSName.DEST, dest);
    }

    public void setDestination(PDPage page) {
        PDPageXYZDestination dest = null;
        if (page != null) {
            dest = new PDPageXYZDestination();
            dest.setPage(page);
        }
        setDestination(dest);
    }

    public PDPage findDestinationPage(PDDocument doc) throws IOException {
        PDPageDestination pageDestination = resolveToPageDestination(doc.getDocumentCatalog()).orElse(null);
        if (Objects.nonNull(pageDestination)) {
            return (PDPage) Optional.ofNullable(pageDestination.getPage()).orElseGet(() -> {
                int pageNumber = pageDestination.getPageNumber();
                if (pageNumber != -1) {
                    return doc.getPage(pageNumber);
                }
                return null;
            });
        }
        return null;
    }

    @Override // org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.WithActionOrDestination
    public PDAction getAction() {
        return PDActionFactory.createAction((COSDictionary) getCOSObject().getDictionaryObject(COSName.A, COSDictionary.class));
    }

    public void setAction(PDAction action) {
        getCOSObject().setItem(COSName.A, action);
    }

    public PDStructureElement getStructureElement() {
        PDStructureElement se = null;
        COSDictionary dic = (COSDictionary) getCOSObject().getDictionaryObject(COSName.SE, COSDictionary.class);
        if (dic != null) {
            se = new PDStructureElement(dic);
        }
        return se;
    }

    public void setStructureElement(PDStructureElement structureElement) {
        getCOSObject().setItem(COSName.SE, structureElement);
    }

    public PDColor getTextColor() {
        COSArray csValues = (COSArray) getCOSObject().getDictionaryObject(COSName.C, COSArray.class);
        if (csValues == null) {
            csValues = new COSArray();
            csValues.growToSize(3, new COSFloat(0.0f));
            getCOSObject().setItem(COSName.C, (COSBase) csValues);
        }
        return new PDColor(csValues, PDDeviceRGB.INSTANCE);
    }

    public void setTextColor(PDColor textColor) {
        getCOSObject().setItem(COSName.C, (COSBase) textColor.toComponentsCOSArray());
    }

    public void setTextColor(Color textColor) {
        COSArray array = new COSArray();
        array.add((COSBase) new COSFloat(textColor.getRed() / 255.0f));
        array.add((COSBase) new COSFloat(textColor.getGreen() / 255.0f));
        array.add((COSBase) new COSFloat(textColor.getBlue() / 255.0f));
        getCOSObject().setItem(COSName.C, (COSBase) array);
    }

    public boolean isItalic() {
        return getCOSObject().getFlag(COSName.F, 1);
    }

    public void setItalic(boolean italic) {
        getCOSObject().setFlag(COSName.F, 1, italic);
    }

    public boolean isBold() {
        return getCOSObject().getFlag(COSName.F, 2);
    }

    public void setBold(boolean bold) {
        getCOSObject().setFlag(COSName.F, 2, bold);
    }

    public void delete() {
        PDOutlineItem previousSibling = getPreviousSibling();
        PDOutlineItem nextSibling = getNextSibling();
        PDOutlineNode parent = getParent();
        if (previousSibling == null) {
            if (parent != null) {
                parent.setFirstChild(nextSibling);
            }
        } else {
            previousSibling.setNextSibling(nextSibling);
        }
        if (nextSibling == null) {
            if (parent != null) {
                parent.setLastChild(previousSibling);
            }
        } else {
            nextSibling.setPreviousSibling(previousSibling);
        }
        updateParentOpenCount(-1);
    }
}
