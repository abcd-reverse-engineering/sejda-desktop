package org.sejda.model.toc;

import org.sejda.model.FriendlyNamed;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/toc/ToCPolicy.class */
public enum ToCPolicy implements FriendlyNamed {
    NONE("none"),
    FILE_NAMES("file_names"),
    DOC_TITLES("doc_titles");

    private final String displayName;

    ToCPolicy(String displayName) {
        this.displayName = displayName;
    }

    @Override // org.sejda.model.FriendlyNamed
    public String getFriendlyName() {
        return this.displayName;
    }
}
