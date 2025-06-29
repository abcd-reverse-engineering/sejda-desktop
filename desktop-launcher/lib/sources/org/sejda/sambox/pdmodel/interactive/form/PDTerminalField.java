package org.sejda.sambox.pdmodel.interactive.form;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSArrayList;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSInteger;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.interactive.action.PDFormFieldAdditionalActions;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationWidget;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/form/PDTerminalField.class */
public abstract class PDTerminalField extends PDField {
    abstract void constructAppearances() throws IOException;

    protected PDTerminalField(PDAcroForm acroForm) {
        super(acroForm);
    }

    PDTerminalField(PDAcroForm acroForm, COSDictionary field, PDNonTerminalField parent) {
        super(acroForm, field, parent);
    }

    public void setActions(PDFormFieldAdditionalActions actions) {
        getCOSObject().setItem(COSName.AA, actions);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.form.PDField
    public int getFieldFlags() {
        int retval = 0;
        COSInteger ff = (COSInteger) getCOSObject().getDictionaryObject(COSName.FF);
        if (ff != null) {
            retval = ff.intValue();
        } else if (getParent() != null) {
            retval = getParent().getFieldFlags();
        }
        return retval;
    }

    @Override // org.sejda.sambox.pdmodel.interactive.form.PDField
    public String getFieldType() {
        String fieldType = getCOSObject().getNameAsString(COSName.FT);
        if (fieldType == null && getParent() != null) {
            fieldType = getParent().getFieldType();
        }
        return fieldType;
    }

    @Override // org.sejda.sambox.pdmodel.interactive.form.PDField
    public List<PDAnnotationWidget> getWidgets() {
        COSArray kids = (COSArray) getCOSObject().getDictionaryObject(COSName.KIDS, COSArray.class);
        List<PDAnnotationWidget> result = new ArrayList<>();
        if (Objects.isNull(kids)) {
            result.add(new PDAnnotationWidget(getCOSObject()));
            return result;
        }
        if (kids.size() > 0) {
            return (List) kids.stream().filter((v0) -> {
                return Objects.nonNull(v0);
            }).map((v0) -> {
                return v0.getCOSObject();
            }).filter(k -> {
                return k instanceof COSDictionary;
            }).map(k2 -> {
                return new PDAnnotationWidget((COSDictionary) k2);
            }).collect(Collectors.toList());
        }
        return result;
    }

    public boolean removeWidget(PDAnnotationWidget widget) {
        List<PDAnnotationWidget> widgets = getWidgets();
        boolean removed = widgets.remove(widget);
        if (removed) {
            setWidgets(widgets);
        }
        return removed;
    }

    public void addWidgetIfMissing(PDAnnotationWidget widget) {
        if (Objects.nonNull(widget)) {
            COSArray kids = (COSArray) getCOSObject().getDictionaryObject(COSName.KIDS);
            if (kids == null) {
                COSArray kids2 = new COSArray(widget.getCOSObject());
                widget.getCOSObject().setItem(COSName.PARENT, this);
                getCOSObject().setItem(COSName.KIDS, (COSBase) kids2);
            } else if (!kids.contains(widget.getCOSObject())) {
                kids.add((COSBase) widget.getCOSObject());
                widget.getCOSObject().setItem(COSName.PARENT, this);
            }
        }
    }

    public void setWidgets(List<PDAnnotationWidget> children) {
        getCOSObject().setItem(COSName.KIDS, (COSBase) COSArrayList.converterToCOSArray(children));
        for (PDAnnotationWidget widget : children) {
            widget.getCOSObject().setItem(COSName.PARENT, this);
        }
    }

    public final void applyChange() throws IOException {
        constructAppearances();
    }

    @Override // org.sejda.sambox.pdmodel.interactive.form.PDField
    public boolean isTerminal() {
        return true;
    }
}
