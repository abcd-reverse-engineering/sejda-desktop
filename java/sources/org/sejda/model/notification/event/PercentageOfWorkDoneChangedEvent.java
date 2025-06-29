package org.sejda.model.notification.event;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.sejda.model.task.NotifiableTaskMetadata;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/notification/event/PercentageOfWorkDoneChangedEvent.class */
public class PercentageOfWorkDoneChangedEvent extends AbstractNotificationEvent {
    private static final long serialVersionUID = -9123790950056705713L;
    public static final BigDecimal UNDETERMINED = new BigDecimal("-1");
    public static final BigDecimal MAX_PERGENTAGE = new BigDecimal("100");
    private BigDecimal percentage;

    public PercentageOfWorkDoneChangedEvent(NotifiableTaskMetadata taskMetadata) {
        super(taskMetadata);
        this.percentage = BigDecimal.ZERO;
    }

    public PercentageOfWorkDoneChangedEvent(BigDecimal percentage, NotifiableTaskMetadata taskMetadata) {
        super(taskMetadata);
        this.percentage = BigDecimal.ZERO;
        setPercentage(percentage);
    }

    public BigDecimal getPercentage() {
        return this.percentage;
    }

    public final void setPercentage(BigDecimal percentage) {
        if (BigDecimal.ZERO.compareTo(percentage) > 0) {
            this.percentage = UNDETERMINED;
        } else {
            this.percentage = MAX_PERGENTAGE.min(percentage);
        }
    }

    public boolean isUndetermined() {
        return BigDecimal.ZERO.compareTo(this.percentage) > 0;
    }

    @Override // org.sejda.model.notification.event.AbstractNotificationEvent
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("percentage", this.percentage).toString();
    }
}
