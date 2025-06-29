package org.sejda.sambox.pdmodel.interactive.form;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSArrayList;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSInteger;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/form/PDNonTerminalField.class */
public class PDNonTerminalField extends PDField {
    private static final Logger LOG = LoggerFactory.getLogger(PDNonTerminalField.class);

    public PDNonTerminalField(PDAcroForm acroForm) {
        super(acroForm);
    }

    PDNonTerminalField(PDAcroForm acroForm, COSDictionary field, PDNonTerminalField parent) {
        super(acroForm, field, parent);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.form.PDField
    public int getFieldFlags() {
        int retval = 0;
        COSInteger ff = (COSInteger) getCOSObject().getDictionaryObject(COSName.FF);
        if (ff != null) {
            retval = ff.intValue();
        }
        return retval;
    }

    public List<PDField> getChildren() {
        List<PDField> children = new ArrayList<>();
        COSArray kids = (COSArray) getCOSObject().getDictionaryObject(COSName.KIDS, COSArray.class);
        if (kids != null) {
            Iterator<COSBase> it = kids.iterator();
            while (it.hasNext()) {
                COSBase kid = it.next();
                if (Objects.nonNull(kid) && (kid.getCOSObject() instanceof COSDictionary)) {
                    if (kid.getCOSObject() == getCOSObject()) {
                        LOG.warn("Child field is same object as parent");
                    } else {
                        children.add(PDField.fromDictionary(getAcroForm(), (COSDictionary) kid.getCOSObject(), this));
                    }
                }
            }
        }
        return children;
    }

    public boolean hasChildren() {
        return getChildren().size() > 0;
    }

    public void setChildren(List<PDField> children) {
        getCOSObject().setItem(COSName.KIDS, (COSBase) COSArrayList.converterToCOSArray(children));
    }

    public void addChild(PDField field) {
        COSArray kids = (COSArray) getCOSObject().getDictionaryObject(COSName.KIDS);
        if (kids == null) {
            kids = new COSArray();
        }
        if (!kids.contains(field)) {
            kids.add((COSObjectable) field);
            field.getCOSObject().setItem(COSName.PARENT, this);
            getCOSObject().setItem(COSName.KIDS, (COSBase) kids);
        }
    }

    public COSBase removeChild(PDField field) {
        int removeIdx;
        COSArray kids = (COSArray) getCOSObject().getDictionaryObject(COSName.KIDS, COSArray.class);
        if (Objects.nonNull(kids) && (removeIdx = kids.indexOfObject(field.getCOSObject())) >= 0) {
            return kids.remove(removeIdx);
        }
        return null;
    }

    @Override // org.sejda.sambox.pdmodel.interactive.form.PDField
    public String getFieldType() {
        return getCOSObject().getNameAsString(COSName.FT);
    }

    public COSBase getValue() {
        return getCOSObject().getDictionaryObject(COSName.V);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.form.PDField
    public String getValueAsString() {
        return getCOSObject().getDictionaryObject(COSName.V).toString();
    }

    public void setValue(COSBase object) {
        getCOSObject().setItem(COSName.V, object);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.form.PDField
    public void setValue(String value) {
        getCOSObject().setString(COSName.V, value);
    }

    public COSBase getDefaultValue() {
        return getCOSObject().getDictionaryObject(COSName.DV);
    }

    public void setDefaultValue(COSBase value) {
        getCOSObject().setItem(COSName.V, value);
    }

    @Override // org.sejda.sambox.pdmodel.interactive.form.PDField
    public List<PDAnnotationWidget> getWidgets() {
        return Collections.emptyList();
    }

    @Override // org.sejda.sambox.pdmodel.interactive.form.PDField
    public boolean isTerminal() {
        return false;
    }
}
