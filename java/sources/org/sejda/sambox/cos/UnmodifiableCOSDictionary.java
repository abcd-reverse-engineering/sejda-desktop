package org.sejda.sambox.cos;

import java.util.Collections;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/cos/UnmodifiableCOSDictionary.class */
final class UnmodifiableCOSDictionary extends COSDictionary {
    UnmodifiableCOSDictionary(COSDictionary dict) {
        this.items = Collections.unmodifiableMap(dict.items);
    }
}
