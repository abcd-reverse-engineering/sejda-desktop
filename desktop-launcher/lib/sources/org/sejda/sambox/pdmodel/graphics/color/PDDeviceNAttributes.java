package org.sejda.sambox.pdmodel.graphics.color;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.common.COSDictionaryMap;
import org.sejda.sambox.pdmodel.common.PDDictionaryWrapper;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/color/PDDeviceNAttributes.class */
public final class PDDeviceNAttributes extends PDDictionaryWrapper {
    public PDDeviceNAttributes() {
    }

    public PDDeviceNAttributes(COSDictionary attributes) {
        super(attributes);
    }

    public Map<String, PDSeparation> getColorants() throws IOException {
        Map<String, PDSeparation> actuals = new HashMap<>();
        COSDictionary colorants = (COSDictionary) getCOSObject().getDictionaryObject(COSName.COLORANTS, COSDictionary.class);
        if (colorants == null) {
            colorants = new COSDictionary();
            getCOSObject().setItem(COSName.COLORANTS, (COSBase) colorants);
        } else {
            for (COSName name : colorants.keySet()) {
                COSBase value = colorants.getDictionaryObject(name);
                actuals.put(name.getName(), (PDSeparation) PDColorSpace.create(value));
            }
        }
        return new COSDictionaryMap(actuals, colorants);
    }

    public PDDeviceNProcess getProcess() {
        COSDictionary process = (COSDictionary) getCOSObject().getDictionaryObject(COSName.PROCESS, COSDictionary.class);
        if (process == null) {
            return null;
        }
        return new PDDeviceNProcess(process);
    }

    public boolean isNChannel() {
        return "NChannel".equals(getCOSObject().getNameAsString(COSName.SUBTYPE));
    }

    public void setColorants(Map<String, PDColorSpace> colorants) {
        COSDictionary colorantDict = null;
        if (colorants != null) {
            colorantDict = COSDictionaryMap.convert(colorants);
        }
        getCOSObject().setItem(COSName.COLORANTS, (COSBase) colorantDict);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(getCOSObject().getNameAsString(COSName.SUBTYPE));
        sb.append('{');
        PDDeviceNProcess process = getProcess();
        if (process != null) {
            sb.append(process);
            sb.append(' ');
        }
        try {
            Map<String, PDSeparation> colorants = getColorants();
            sb.append("Colorants{");
            for (Map.Entry<String, PDSeparation> col : colorants.entrySet()) {
                sb.append('\"');
                sb.append(col.getKey());
                sb.append("\": ");
                sb.append(col.getValue());
                sb.append(' ');
            }
            sb.append('}');
        } catch (IOException e) {
            sb.append("ERROR");
        }
        sb.append('}');
        return sb.toString();
    }
}
