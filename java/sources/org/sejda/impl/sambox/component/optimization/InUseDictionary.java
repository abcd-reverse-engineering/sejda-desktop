package org.sejda.impl.sambox.component.optimization;

import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.IndirectCOSObjectIdentifier;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/optimization/InUseDictionary.class */
public class InUseDictionary extends COSDictionary {
    private final COSDictionary wrapped;

    public InUseDictionary(COSDictionary wrapped) {
        super(wrapped);
        this.wrapped = wrapped;
    }

    @Override // org.sejda.sambox.cos.COSBase
    public IndirectCOSObjectIdentifier id() {
        return this.wrapped.id();
    }

    @Override // org.sejda.sambox.cos.COSBase
    public void idIfAbsent(IndirectCOSObjectIdentifier id) {
        this.wrapped.idIfAbsent(id);
    }
}
