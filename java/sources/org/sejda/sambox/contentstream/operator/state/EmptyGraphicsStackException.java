package org.sejda.sambox.contentstream.operator.state;

import java.io.IOException;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/contentstream/operator/state/EmptyGraphicsStackException.class */
public final class EmptyGraphicsStackException extends IOException {
    private static final long serialVersionUID = 1;

    EmptyGraphicsStackException() {
        super("Cannot execute restore, the graphics stack is empty");
    }
}
