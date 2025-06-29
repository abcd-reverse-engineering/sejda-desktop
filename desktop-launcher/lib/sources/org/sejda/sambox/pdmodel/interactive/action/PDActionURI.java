package org.sejda.sambox.pdmodel.interactive.action;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSString;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/action/PDActionURI.class */
public class PDActionURI extends PDAction {
    public static final String SUB_TYPE = "URI";

    public PDActionURI() {
        setSubType(SUB_TYPE);
    }

    public PDActionURI(COSDictionary a) {
        super(a);
    }

    @Deprecated
    public String getS() {
        return this.action.getNameAsString(COSName.S);
    }

    @Deprecated
    public void setS(String s) {
        this.action.setName(COSName.S, s);
    }

    public String getURI() {
        COSString base = (COSString) this.action.getDictionaryObject(COSName.URI, COSString.class);
        if (Objects.nonNull(base)) {
            byte[] bytes = base.getBytes();
            if (bytes.length >= 2) {
                if ((bytes[0] & 255) == 254 && (bytes[1] & 255) == 255) {
                    return this.action.getString(COSName.URI);
                }
                if ((bytes[0] & 255) == 255 && (bytes[1] & 255) == 254) {
                    return this.action.getString(COSName.URI);
                }
            }
            return new String(bytes, StandardCharsets.UTF_8);
        }
        return null;
    }

    public void setURI(String uri) {
        this.action.setString(COSName.URI, uri);
    }

    public boolean shouldTrackMousePosition() {
        return this.action.getBoolean("IsMap", false);
    }

    public void setTrackMousePosition(boolean value) {
        this.action.setBoolean("IsMap", value);
    }
}
