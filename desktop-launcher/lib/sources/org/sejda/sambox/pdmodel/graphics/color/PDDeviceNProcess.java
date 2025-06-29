package org.sejda.sambox.pdmodel.graphics.color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/color/PDDeviceNProcess.class */
public class PDDeviceNProcess {
    private final COSDictionary dictionary;

    public PDDeviceNProcess() {
        this.dictionary = new COSDictionary();
    }

    public PDDeviceNProcess(COSDictionary attributes) {
        this.dictionary = attributes;
    }

    public COSDictionary getCOSDictionary() {
        return this.dictionary;
    }

    public PDColorSpace getColorSpace() throws IOException {
        COSBase cosColorSpace = this.dictionary.getDictionaryObject(COSName.COLORSPACE);
        if (cosColorSpace == null) {
            return null;
        }
        return PDColorSpace.create(cosColorSpace);
    }

    public List<String> getComponents() {
        List<String> components = new ArrayList<>();
        COSArray cosComponents = (COSArray) this.dictionary.getDictionaryObject(COSName.COMPONENTS, COSArray.class);
        if (cosComponents == null) {
            return components;
        }
        Iterator<COSBase> it = cosComponents.iterator();
        while (it.hasNext()) {
            COSBase name = it.next();
            components.add(((COSName) name).getName());
        }
        return components;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Process{");
        try {
            sb.append(getColorSpace());
            for (String component : getComponents()) {
                sb.append(" \"");
                sb.append(component);
                sb.append('\"');
            }
        } catch (IOException e) {
            sb.append("ERROR");
        }
        sb.append('}');
        return sb.toString();
    }
}
