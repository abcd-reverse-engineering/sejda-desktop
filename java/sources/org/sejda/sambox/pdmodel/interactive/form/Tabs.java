package org.sejda.sambox.pdmodel.interactive.form;

import org.sejda.sambox.pdmodel.interactive.annotation.PDBorderEffectDictionary;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/form/Tabs.class */
public enum Tabs {
    ROW_ORDER("R"),
    COLUMN_ORDER(PDBorderEffectDictionary.STYLE_CLOUDY),
    STRUCTURE_ORDER("S");

    private final String value;

    public static Tabs fromString(String value) {
        if (value == null) {
            return null;
        }
        for (Tabs instance : values()) {
            if (instance.value.equals(value)) {
                return instance;
            }
        }
        return null;
    }

    Tabs(String value) {
        this.value = value;
    }

    public String stringValue() {
        return this.value;
    }
}
