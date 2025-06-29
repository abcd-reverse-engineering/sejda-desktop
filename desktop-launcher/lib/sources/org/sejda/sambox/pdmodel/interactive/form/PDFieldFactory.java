package org.sejda.sambox.pdmodel.interactive.form;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.util.ObjectIdUtils;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/form/PDFieldFactory.class */
public final class PDFieldFactory {
    private static final String FIELD_TYPE_TEXT = "Tx";
    private static final String FIELD_TYPE_BUTTON = "Btn";
    private static final String FIELD_TYPE_CHOICE = "Ch";
    private static final String FIELD_TYPE_SIGNATURE = "Sig";

    private PDFieldFactory() {
    }

    public static PDField createFieldAddingChildToParent(PDAcroForm form, COSDictionary field, PDNonTerminalField parent) {
        PDField retField = createField(form, field, parent);
        Optional.ofNullable(retField.getParent()).ifPresent(p -> {
            p.addChild(retField);
        });
        return retField;
    }

    public static PDField createField(PDAcroForm form, COSDictionary field, PDNonTerminalField parent) {
        String fieldType = findFieldType(field);
        if (FIELD_TYPE_CHOICE.equals(fieldType)) {
            return createChoiceSubType(form, field, parent);
        }
        if (FIELD_TYPE_TEXT.equals(fieldType)) {
            return new PDTextField(form, field, parent);
        }
        if (FIELD_TYPE_SIGNATURE.equals(fieldType)) {
            return new PDSignatureField(form, field, parent);
        }
        if (FIELD_TYPE_BUTTON.equals(fieldType)) {
            return createButtonSubType(form, field, parent);
        }
        return new PDNonTerminalField(form, field, parent);
    }

    public static PDNonTerminalField createNonTerminalField(PDAcroForm form, COSDictionary field, PDNonTerminalField parent) {
        return new PDNonTerminalField(form, field, parent);
    }

    private static PDField createChoiceSubType(PDAcroForm form, COSDictionary field, PDNonTerminalField parent) {
        int flags = field.getInt(COSName.FF, 0);
        if ((flags & 131072) != 0) {
            return new PDComboBox(form, field, parent);
        }
        return new PDListBox(form, field, parent);
    }

    private static PDField createButtonSubType(PDAcroForm form, COSDictionary field, PDNonTerminalField parent) {
        int flags = field.getInt(COSName.FF, 0);
        if ((flags & 32768) != 0) {
            return new PDRadioButton(form, field, parent);
        }
        if ((flags & 65536) != 0) {
            return new PDPushButton(form, field, parent);
        }
        return new PDCheckBox(form, field, parent);
    }

    private static String findFieldType(COSDictionary dic) {
        return findFieldType(dic, new HashSet());
    }

    private static String findFieldType(COSDictionary dic, Set<String> visitedObjectIds) {
        COSDictionary parent;
        String retval = dic.getNameAsString(COSName.FT);
        if (retval == null && (parent = (COSDictionary) dic.getDictionaryObject(COSName.PARENT, COSName.P, COSDictionary.class)) != null && parent != dic) {
            String objId = ObjectIdUtils.getObjectIdOf(dic);
            if (!objId.isBlank() && visitedObjectIds.contains(objId)) {
                return null;
            }
            visitedObjectIds.add(objId);
            retval = findFieldType(parent, visitedObjectIds);
        }
        return retval;
    }
}
