package org.sejda.core.notification.dsl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;
import org.sejda.core.notification.context.GlobalNotificationContext;
import org.sejda.core.notification.context.ThreadLocalNotificationContext;
import org.sejda.model.notification.event.AbstractNotificationEvent;
import org.sejda.model.notification.event.PercentageOfWorkDoneChangedEvent;
import org.sejda.model.notification.event.TaskExecutionCompletedEvent;
import org.sejda.model.notification.event.TaskExecutionFailedEvent;
import org.sejda.model.notification.event.TaskExecutionStartedEvent;
import org.sejda.model.notification.event.TaskExecutionWarningEvent;
import org.sejda.model.task.NotifiableTaskMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/notification/dsl/ApplicationEventsNotifier.class */
public final class ApplicationEventsNotifier implements Notifier, OngoingNotification {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationEventsNotifier.class);
    private NotifiableTaskMetadata taskMetadata;
    private BigDecimal percentage = BigDecimal.ZERO;
    private Set<String> warnings = new HashSet();

    private ApplicationEventsNotifier(NotifiableTaskMetadata taskMetadata) {
        this.taskMetadata = taskMetadata;
    }

    public static Notifier notifyEvent(NotifiableTaskMetadata taskMetadata) {
        return new ApplicationEventsNotifier(taskMetadata);
    }

    @Override // org.sejda.core.notification.dsl.Notifier
    public void taskFailed(Exception e) {
        notifyListeners(new TaskExecutionFailedEvent(e, this.taskMetadata));
    }

    @Override // org.sejda.core.notification.dsl.Notifier
    public void taskCompleted(long executionTime) {
        notifyListeners(new TaskExecutionCompletedEvent(executionTime, this.taskMetadata));
    }

    @Override // org.sejda.core.notification.dsl.Notifier
    public void taskStarted() {
        notifyListeners(new TaskExecutionStartedEvent(this.taskMetadata));
    }

    @Override // org.sejda.core.notification.dsl.Notifier
    public void taskWarning(String warning) {
        LOG.warn(warning);
        notifyListeners(new TaskExecutionWarningEvent(warning, this.taskMetadata));
        this.warnings.add(warning);
    }

    @Override // org.sejda.core.notification.dsl.Notifier
    public void taskWarningOnce(String warning) {
        if (this.warnings.contains(warning)) {
            return;
        }
        taskWarning(warning);
    }

    @Override // org.sejda.core.notification.dsl.Notifier
    public void taskWarning(String warning, Exception e) {
        LOG.warn(warning, e);
        notifyListeners(new TaskExecutionWarningEvent(warning, this.taskMetadata));
    }

    @Override // org.sejda.core.notification.dsl.Notifier
    public void progressUndetermined() {
        notifyListeners(new PercentageOfWorkDoneChangedEvent(PercentageOfWorkDoneChangedEvent.UNDETERMINED, this.taskMetadata));
    }

    @Override // org.sejda.core.notification.dsl.Notifier
    public OngoingNotification stepsCompleted(int completed) {
        this.percentage = new BigDecimal(completed);
        return this;
    }

    @Override // org.sejda.core.notification.dsl.Notifier
    public OngoingNotification stepsCompleted(BigDecimal completed) {
        this.percentage = completed;
        return this;
    }

    @Override // org.sejda.core.notification.dsl.OngoingNotification
    public void outOf(int total) {
        outOf(new BigDecimal(total));
    }

    @Override // org.sejda.core.notification.dsl.OngoingNotification
    public void outOf(BigDecimal total) {
        notifyListeners(new PercentageOfWorkDoneChangedEvent(this.percentage.multiply(PercentageOfWorkDoneChangedEvent.MAX_PERGENTAGE).divide(total, RoundingMode.HALF_DOWN), this.taskMetadata));
    }

    private void notifyListeners(AbstractNotificationEvent event) {
        LOG.trace("Notifying event {}", event);
        GlobalNotificationContext.getContext().notifyListeners(event);
        ThreadLocalNotificationContext.getContext().notifyListeners(event);
    }
}
