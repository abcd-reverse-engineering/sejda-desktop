package org.sejda.sambox.input;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import org.sejda.commons.util.IOUtils;
import org.sejda.commons.util.RequireUtils;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSNull;
import org.sejda.sambox.cos.COSObjectKey;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.pdmodel.encryption.SecurityHandler;
import org.sejda.sambox.xref.CompressedXrefEntry;
import org.sejda.sambox.xref.Xref;
import org.sejda.sambox.xref.XrefEntry;
import org.sejda.sambox.xref.XrefType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/input/LazyIndirectObjectsProvider.class */
class LazyIndirectObjectsProvider implements IndirectObjectsProvider {
    private static final Logger LOG = LoggerFactory.getLogger(LazyIndirectObjectsProvider.class);
    private ObjectsFullScanner scanner;
    private COSParser parser;
    private final Xref xref = new Xref();
    private final Map<COSObjectKey, COSBase> store = new ConcurrentHashMap();
    private final Set<COSObjectKey> currentlyParsing = ConcurrentHashMap.newKeySet();
    private SecurityHandler securityHandler = null;

    LazyIndirectObjectsProvider() {
    }

    @Override // org.sejda.sambox.input.IndirectObjectsProvider
    public COSBase get(COSObjectKey key) {
        return (COSBase) Optional.ofNullable(this.store.get(key)).orElseGet(() -> {
            parseObject(key);
            return this.store.get(key);
        });
    }

    @Override // org.sejda.sambox.input.IndirectObjectsProvider
    public void release(COSObjectKey key) {
        this.store.remove(key);
    }

    @Override // org.sejda.sambox.input.IndirectObjectsProvider
    public XrefEntry addEntryIfAbsent(XrefEntry entry) {
        XrefEntry retVal = this.xref.addIfAbsent(entry);
        if (retVal == null) {
            LOG.trace("Added xref entry {}", entry);
        }
        return retVal;
    }

    @Override // org.sejda.sambox.input.IndirectObjectsProvider
    public XrefEntry addEntry(XrefEntry entry) {
        LOG.trace("Added xref entry {}", entry);
        return this.xref.add(entry);
    }

    @Override // org.sejda.sambox.input.IndirectObjectsProvider
    public COSObjectKey highestKey() {
        return this.xref.highestKey();
    }

    @Override // org.sejda.sambox.input.IndirectObjectsProvider
    public LazyIndirectObjectsProvider initializeWith(COSParser parser) {
        Objects.requireNonNull(parser);
        this.parser = parser;
        this.scanner = new ObjectsFullScanner(parser);
        return this;
    }

    @Override // org.sejda.sambox.input.IndirectObjectsProvider
    public LazyIndirectObjectsProvider initializeWith(SecurityHandler handler) {
        this.securityHandler = handler;
        return this;
    }

    private synchronized void parseObject(COSObjectKey key) {
        XrefEntry xrefEntry = this.xref.get(key);
        if (Objects.nonNull(xrefEntry)) {
            try {
                doParse(xrefEntry);
                return;
            } catch (IOException e) {
                LOG.warn("An error occurred while parsing " + xrefEntry, e);
                doParseFallbackObject(key);
                return;
            }
        }
        LOG.warn("Unable to find xref data for {}", key);
        doParseFallbackObject(key);
    }

    private void doParseFallbackObject(COSObjectKey key) {
        LOG.info("Trying fallback strategy for {}", key);
        XrefEntry xrefEntry = this.scanner.entries().get(key);
        if (Objects.nonNull(xrefEntry)) {
            try {
                doParse(xrefEntry);
                return;
            } catch (IOException e) {
                LOG.warn("Unable to find fallback xref entry for " + key, e);
                return;
            }
        }
        LOG.warn("Unable to find fallback xref entry for {}", key);
    }

    private void doParse(XrefEntry xrefEntry) throws IOException {
        LOG.trace("Parsing indirect object {}", xrefEntry);
        if (xrefEntry.getType() == XrefType.IN_USE) {
            parseInUseEntry(xrefEntry);
        }
        if (xrefEntry.getType() == XrefType.COMPRESSED) {
            parseCompressedEntry(xrefEntry);
        }
        LOG.trace("Parsing done");
    }

    private void parseInUseEntry(XrefEntry xrefEntry) throws IOException {
        try {
            if (!this.currentlyParsing.contains(xrefEntry.key())) {
                this.currentlyParsing.add(xrefEntry.key());
                this.parser.position(xrefEntry.getByteOffset());
                this.parser.skipExpectedIndirectObjectDefinition(xrefEntry.key());
                this.parser.skipSpaces();
                COSBase found = this.parser.nextParsedToken();
                this.parser.skipSpaces();
                if (this.parser.isNextToken(BaseCOSParser.STREAM)) {
                    RequireUtils.requireIOCondition(found instanceof COSDictionary, "Found stream with missing dictionary");
                    found = this.parser.nextStream((COSDictionary) found);
                    if (this.parser.skipTokenIfValue(BaseCOSParser.ENDSTREAM)) {
                        LOG.warn("Found double 'endstream' token for {}", xrefEntry);
                    }
                }
                if (this.securityHandler != null) {
                    LOG.trace("Decrypting entry {}", xrefEntry);
                    this.securityHandler.decrypt(found, xrefEntry.getObjectNumber(), xrefEntry.getGenerationNumber());
                }
                if (!this.parser.skipTokenIfValue(BaseCOSParser.ENDOBJ)) {
                    LOG.warn("Missing 'endobj' token for {}", xrefEntry);
                }
                if (found instanceof ExistingIndirectCOSObject) {
                    ExistingIndirectCOSObject existingIndirectCOSObject = (ExistingIndirectCOSObject) found;
                    if (existingIndirectCOSObject.id().objectIdentifier.equals(xrefEntry.key())) {
                        LOG.warn("Found indirect object definition pointing to itself, for {}", xrefEntry);
                        found = COSNull.NULL;
                    }
                }
                this.store.put(xrefEntry.key(), (COSBase) Optional.ofNullable(found).orElse(COSNull.NULL));
            } else {
                LOG.warn("Found a loop while parsing object definition {}", xrefEntry);
            }
        } finally {
            this.currentlyParsing.remove(xrefEntry.key());
        }
    }

    private void parseCompressedEntry(XrefEntry xrefEntry) throws IOException {
        XrefEntry containingStreamEntry = this.xref.get(new COSObjectKey(((CompressedXrefEntry) xrefEntry).getObjectStreamNumber(), 0));
        RequireUtils.requireIOCondition(Objects.nonNull(containingStreamEntry) && containingStreamEntry.getType() != XrefType.COMPRESSED, "Expected an uncompressed indirect object reference for the ObjectStream");
        parseObject(containingStreamEntry.key());
        COSBase stream = (COSBase) Optional.ofNullable(this.store.get(containingStreamEntry.key())).map((v0) -> {
            return v0.getCOSObject();
        }).orElseThrow(() -> {
            return new IOException("Unable to find ObjectStream " + containingStreamEntry);
        });
        if (!(stream instanceof COSStream)) {
            throw new IOException("Expected an object stream instance for " + containingStreamEntry);
        }
        parseObjectStream(containingStreamEntry, (COSStream) stream);
    }

    private void parseObjectStream(XrefEntry containingStreamEntry, COSStream stream) throws IOException {
        COSParser streamParser = new COSParser(stream.getUnfilteredSource(), this);
        try {
            RequireUtils.requireIOCondition(!isIndirectContainedIn(stream.getItem(COSName.N), containingStreamEntry), "Objects stream size cannot be store as indirect reference in the ObjStm itself");
            int numberOfObjects = stream.getInt(COSName.N);
            RequireUtils.requireIOCondition(numberOfObjects >= 0, "Missing or negative required objects stream size");
            RequireUtils.requireIOCondition(!isIndirectContainedIn(stream.getItem(COSName.FIRST), containingStreamEntry), "Objects stream first offset cannot be store as indirect reference in the ObjStm itself");
            long firstOffset = stream.getLong(COSName.FIRST);
            RequireUtils.requireIOCondition(firstOffset >= 0, "Missing or negative required bytes offset of the fist object in the objects stream");
            Map<Long, Long> entries = new TreeMap<>();
            for (int i = 0; i < numberOfObjects; i++) {
                long number = streamParser.readObjectNumber();
                long offset = firstOffset + streamParser.readLong();
                entries.put(Long.valueOf(offset), Long.valueOf(number));
            }
            LOG.trace("Found {} entries in object stream of size {}", Integer.valueOf(entries.size()), Long.valueOf(streamParser.source().size()));
            for (Map.Entry<Long, Long> entry : entries.entrySet()) {
                LOG.trace("Parsing compressed object {} at offset {}", entry.getValue(), entry.getKey());
                streamParser.position(entry.getKey().longValue());
                if (streamParser.skipTokenIfValue(SourceReader.OBJ)) {
                    LOG.warn("Unexptected 'obj' token in objects stream");
                }
                COSBase object = streamParser.nextParsedToken();
                if (object != null) {
                    COSObjectKey key = new COSObjectKey(entry.getValue().longValue(), 0);
                    if (containingStreamEntry.owns(this.xref.get(key))) {
                        LOG.trace("Parsed compressed object {} {}", key, object.getClass());
                        this.store.put(key, object);
                    }
                }
                if (streamParser.skipTokenIfValue(BaseCOSParser.ENDOBJ)) {
                    LOG.warn("Unexptected 'endobj' token in objects stream");
                }
            }
            streamParser.close();
            IOUtils.close(stream);
        } catch (Throwable th) {
            try {
                streamParser.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private boolean isIndirectContainedIn(COSBase item, XrefEntry containingStreamEntry) {
        if (item instanceof ExistingIndirectCOSObject) {
            Optional map = Optional.ofNullable(item.id()).map(i -> {
                return i.objectIdentifier;
            });
            Xref xref = this.xref;
            Objects.requireNonNull(xref);
            return ((Boolean) map.map(xref::get).filter(e -> {
                return e instanceof CompressedXrefEntry;
            }).map(e2 -> {
                return (CompressedXrefEntry) e2;
            }).map(e3 -> {
                return Boolean.valueOf(containingStreamEntry.key().objectNumber() == e3.getObjectStreamNumber());
            }).orElse(Boolean.FALSE)).booleanValue();
        }
        return false;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.store.values().stream().filter(o -> {
            return o instanceof Closeable;
        }).map(cOSBase -> {
            return (Closeable) cOSBase;
        }).forEach(IOUtils::closeQuietly);
        this.store.clear();
    }

    @Override // org.sejda.sambox.input.IndirectObjectsProvider
    public String id() {
        return this.parser.source().id();
    }
}
