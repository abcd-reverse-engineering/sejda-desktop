package org.sejda.sambox.pdmodel.interactive.form;

import java.io.IOException;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSString;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/form/DefaultAppearanceHelper.class */
public class DefaultAppearanceHelper {
    public static COSString getDefaultAppearance(PDField field) throws IOException {
        return getDefaultAppearance(field.getInheritableAttribute(COSName.DA));
    }

    public static COSString getDefaultAppearance(COSBase defaultAppearance) throws IOException {
        if (defaultAppearance == null) {
            return null;
        }
        if (defaultAppearance instanceof COSString) {
            return (COSString) defaultAppearance;
        }
        if (defaultAppearance instanceof COSName) {
            String value = ((COSName) defaultAppearance).getName();
            return COSString.parseLiteral(value);
        }
        throw new IOException("Expected DA to be COSString, got: " + defaultAppearance.getClass().getSimpleName());
    }
}
