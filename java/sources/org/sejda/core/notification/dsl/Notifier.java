package org.sejda.core.notification.dsl;

import java.math.BigDecimal;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/notification/dsl/Notifier.class */
public interface Notifier {
    void taskFailed(Exception exc);

    void taskCompleted(long j);

    void taskStarted();

    void taskWarning(String str);

    void taskWarningOnce(String str);

    void taskWarning(String str, Exception exc);

    void progressUndetermined();

    OngoingNotification stepsCompleted(int i);

    OngoingNotification stepsCompleted(BigDecimal bigDecimal);
}
