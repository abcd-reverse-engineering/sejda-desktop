package org.sejda.sambox.input;

import java.io.Closeable;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSObjectKey;
import org.sejda.sambox.pdmodel.encryption.SecurityHandler;
import org.sejda.sambox.xref.XrefEntry;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/input/IndirectObjectsProvider.class */
interface IndirectObjectsProvider extends Closeable {
    COSBase get(COSObjectKey cOSObjectKey);

    void release(COSObjectKey cOSObjectKey);

    XrefEntry addEntryIfAbsent(XrefEntry xrefEntry);

    XrefEntry addEntry(XrefEntry xrefEntry);

    IndirectObjectsProvider initializeWith(COSParser cOSParser);

    IndirectObjectsProvider initializeWith(SecurityHandler securityHandler);

    COSObjectKey highestKey();

    String id();
}
