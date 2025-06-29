package org.sejda.core.notification.dsl;

import java.math.BigDecimal;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/notification/dsl/OngoingNotification.class */
public interface OngoingNotification {
    void outOf(int i);

    void outOf(BigDecimal bigDecimal);
}
