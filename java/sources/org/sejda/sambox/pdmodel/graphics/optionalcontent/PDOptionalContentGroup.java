package org.sejda.sambox.pdmodel.graphics.optionalcontent;

import java.util.Optional;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.documentinterchange.markedcontent.PDPropertyList;
import org.sejda.sambox.rendering.RenderDestination;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/optionalcontent/PDOptionalContentGroup.class */
public class PDOptionalContentGroup extends PDPropertyList {
    public PDOptionalContentGroup(String name) {
        this.dict.setItem(COSName.TYPE, (COSBase) COSName.OCG);
        setName(name);
    }

    public PDOptionalContentGroup(COSDictionary dict) {
        super(dict);
        if (!dict.getItem(COSName.TYPE).equals(COSName.OCG)) {
            throw new IllegalArgumentException("Provided dictionary is not of type '" + COSName.OCG + "'");
        }
    }

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/optionalcontent/PDOptionalContentGroup$RenderState.class */
    public enum RenderState {
        ON(COSName.ON),
        OFF(COSName.OFF);

        private final COSName name;

        RenderState(COSName value) {
            this.name = value;
        }

        public static RenderState valueOf(COSName state) {
            if (state == null) {
                return null;
            }
            return valueOf(state.getName().toUpperCase());
        }

        public COSName getName() {
            return this.name;
        }
    }

    public String getName() {
        return this.dict.getString(COSName.NAME);
    }

    public void setName(String name) {
        this.dict.setString(COSName.NAME, name);
    }

    public RenderState getRenderState(RenderDestination destination) {
        COSName state = null;
        COSDictionary usage = (COSDictionary) this.dict.getDictionaryObject("Usage", COSDictionary.class);
        if (usage != null) {
            if (RenderDestination.PRINT.equals(destination)) {
                state = (COSName) Optional.ofNullable((COSDictionary) usage.getDictionaryObject("Print", COSDictionary.class)).map(p -> {
                    return (COSName) p.getDictionaryObject("PrintState", COSName.class);
                }).orElse(null);
            } else if (RenderDestination.VIEW.equals(destination)) {
                state = (COSName) Optional.ofNullable((COSDictionary) usage.getDictionaryObject("View", COSDictionary.class)).map(v -> {
                    return (COSName) v.getDictionaryObject("ViewState", COSName.class);
                }).orElse(null);
            }
            if (state == null) {
                state = (COSName) Optional.ofNullable((COSDictionary) usage.getDictionaryObject("Export", COSDictionary.class)).map(e -> {
                    return (COSName) e.getDictionaryObject("ExportState", COSName.class);
                }).orElse(null);
            }
        }
        return (RenderState) Optional.ofNullable(state).map(RenderState::valueOf).orElse(null);
    }

    public String toString() {
        return super.toString() + " (" + getName() + ")";
    }
}
