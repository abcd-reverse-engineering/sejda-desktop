package org.sejda.sambox.output;

import java.util.concurrent.atomic.AtomicLong;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.IndirectCOSObjectReference;
import org.sejda.sambox.cos.NonStorableInObjectStreams;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/output/IndirectReferenceProvider.class */
class IndirectReferenceProvider {
    final AtomicLong referencesCounter;

    IndirectReferenceProvider() {
        this(0L);
    }

    IndirectReferenceProvider(long highestAlreadyExisting) {
        this.referencesCounter = new AtomicLong(highestAlreadyExisting);
    }

    IndirectCOSObjectReference nextReferenceFor(COSBase baseObject) {
        return new IndirectCOSObjectReference(this.referencesCounter.incrementAndGet(), 0, baseObject);
    }

    IndirectCOSObjectReference nextNonStorableInObjectStreamsReferenceFor(COSBase baseObject) {
        return new NonStorableInObjectStreams(this.referencesCounter.incrementAndGet(), 0, baseObject);
    }

    IndirectCOSObjectReference nextNonStorableInObjectStreamsReference() {
        return new NonStorableInObjectStreams(this.referencesCounter.incrementAndGet(), 0, null);
    }
}
