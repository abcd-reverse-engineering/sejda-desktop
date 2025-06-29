package org.sejda.core.support.prefix.processor;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.sejda.core.support.prefix.model.PrefixTransformationContext;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/support/prefix/processor/TimestampPrefixProcessor.class */
public class TimestampPrefixProcessor implements PrefixProcessor {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSS");

    @Override // java.util.function.Consumer
    public void accept(PrefixTransformationContext context) {
        if (context.currentPrefix().contains("[TIMESTAMP]")) {
            context.uniqueNames(true);
            context.currentPrefix(context.currentPrefix().replace("[TIMESTAMP]", FORMATTER.format(ZonedDateTime.now())));
        }
    }
}
