package org.sejda.model.outline;

import org.sejda.model.FriendlyNamed;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/outline/OutlinePolicy.class */
public enum OutlinePolicy implements FriendlyNamed {
    DISCARD("discard"),
    RETAIN("retain"),
    ONE_ENTRY_EACH_DOC("one_entry_each_doc"),
    RETAIN_AS_ONE_ENTRY("retain_as_one_entry");

    private final String displayName;

    OutlinePolicy(String displayName) {
        this.displayName = displayName;
    }

    @Override // org.sejda.model.FriendlyNamed
    public String getFriendlyName() {
        return this.displayName;
    }
}
