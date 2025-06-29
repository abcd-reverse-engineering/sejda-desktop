package org.sejda.sambox.output;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.SortedMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Function;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSObjectKey;
import org.sejda.sambox.cos.IndirectCOSObjectIdentifier;
import org.sejda.sambox.cos.IndirectCOSObjectReference;
import org.sejda.sambox.encryption.GeneralEncryptionAlgorithm;
import org.sejda.sambox.input.ExistingIndirectCOSObject;
import org.sejda.sambox.xref.XrefEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/output/PDFWriteContext.class */
class PDFWriteContext {
    private static final Logger LOG = LoggerFactory.getLogger(PDFWriteContext.class);
    private final String contextId;
    private final IndirectReferenceProvider referencesProvider;
    private final Map<IndirectCOSObjectIdentifier, IndirectCOSObjectReference> lookupNewRef;
    private final List<WriteOption> opts;
    private final SortedMap<Long, XrefEntry> written;
    private final GeneralEncryptionAlgorithm encryptor;

    PDFWriteContext(GeneralEncryptionAlgorithm encryptor, WriteOption... options) {
        this(0L, encryptor, options);
    }

    PDFWriteContext(long highestExistingReferenceNumber, GeneralEncryptionAlgorithm encryptor, WriteOption... options) {
        this.contextId = UUID.randomUUID().toString();
        this.lookupNewRef = new ConcurrentHashMap();
        this.written = new ConcurrentSkipListMap();
        this.encryptor = encryptor;
        this.opts = Arrays.asList(options);
        this.referencesProvider = new IndirectReferenceProvider(highestExistingReferenceNumber);
        LOG.debug("PDFWriteContext created with highest object reference number {} and encryptor {}", Long.valueOf(highestExistingReferenceNumber), encryptor);
    }

    IndirectCOSObjectReference createIndirectReferenceFor(COSBase item) {
        IndirectReferenceProvider indirectReferenceProvider = this.referencesProvider;
        Objects.requireNonNull(indirectReferenceProvider);
        return createNewReference(item, indirectReferenceProvider::nextReferenceFor);
    }

    IndirectCOSObjectReference createNonStorableInObjectStreamIndirectReferenceFor(COSBase item) {
        IndirectReferenceProvider indirectReferenceProvider = this.referencesProvider;
        Objects.requireNonNull(indirectReferenceProvider);
        return createNewReference(item, indirectReferenceProvider::nextNonStorableInObjectStreamsReferenceFor);
    }

    IndirectCOSObjectReference createNonStorableInObjectStreamIndirectReference() {
        return this.referencesProvider.nextNonStorableInObjectStreamsReference();
    }

    private IndirectCOSObjectReference createNewReference(COSBase item, Function<COSBase, IndirectCOSObjectReference> supplier) {
        IndirectCOSObjectReference newRef = supplier.apply(item);
        LOG.trace("Created new indirect reference {} for {}", newRef, item.id());
        if (!(item instanceof ExistingIndirectCOSObject)) {
            item.idIfAbsent(new IndirectCOSObjectIdentifier(newRef.xrefEntry().key(), this.contextId));
        }
        if (item.hasId()) {
            this.lookupNewRef.put(item.id(), newRef);
        } else {
            LOG.warn("Unexpected indirect reference for {}", item);
        }
        return newRef;
    }

    IndirectCOSObjectReference getOrCreateIndirectReferenceFor(COSBase item) {
        return (IndirectCOSObjectReference) Optional.ofNullable(getIndirectReferenceFor(item)).orElseGet(() -> {
            return createIndirectReferenceFor(item);
        });
    }

    IndirectCOSObjectReference getIndirectReferenceFor(COSBase item) {
        if (item.hasId()) {
            return this.lookupNewRef.get(item.id());
        }
        return null;
    }

    void addExistingReference(ExistingIndirectCOSObject existing) {
        this.lookupNewRef.put(existing.id(), new IndirectCOSObjectReference(existing.id().objectIdentifier.objectNumber(), existing.id().objectIdentifier.generation(), null));
    }

    boolean hasIndirectReferenceFor(COSBase item) {
        return item.hasId() && this.lookupNewRef.containsKey(item.id());
    }

    boolean hasWriteOption(WriteOption opt) {
        return this.opts.contains(opt);
    }

    int written() {
        return this.written.size();
    }

    boolean hasWritten(XrefEntry entry) {
        return this.written.containsKey(Long.valueOf(entry.getObjectNumber()));
    }

    XrefEntry addWritten(XrefEntry entry) {
        return this.written.put(Long.valueOf(entry.getObjectNumber()), entry);
    }

    XrefEntry highestWritten() {
        return this.written.get(this.written.lastKey());
    }

    long highestObjectNumber() {
        if (this.written.isEmpty()) {
            return this.referencesProvider.referencesCounter.get();
        }
        return Math.max(this.written.lastKey().longValue(), this.referencesProvider.referencesCounter.get());
    }

    XrefEntry getWritten(Long objectNumber) {
        return this.written.get(objectNumber);
    }

    List<List<Long>> getWrittenContiguousGroups() {
        List<List<Long>> contiguous = new ArrayList<>();
        if (!this.written.isEmpty()) {
            LinkedList<Long> group = new LinkedList<>();
            contiguous.add(group);
            for (Long current : this.written.keySet()) {
                if (group.isEmpty() || current.longValue() == group.getLast().longValue() + 1) {
                    group.addLast(current);
                } else {
                    group = new LinkedList<>(List.of(current));
                    contiguous.add(group);
                }
            }
        }
        return contiguous;
    }

    void writing(COSObjectKey key) {
        if (Objects.nonNull(this.encryptor)) {
            this.encryptor.setCurrentCOSObjectKey(key);
        }
    }

    GeneralEncryptionAlgorithm encryptor() {
        return this.encryptor;
    }
}
