package org.sejda.sambox.input;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.sejda.commons.util.IOUtils;
import org.sejda.commons.util.RequireUtils;
import org.sejda.io.CountingWritableByteChannel;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSNull;
import org.sejda.sambox.cos.COSObjectKey;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.cos.COSString;
import org.sejda.sambox.cos.DirectCOSObject;
import org.sejda.sambox.cos.IndirectCOSObjectIdentifier;
import org.sejda.sambox.cos.IndirectCOSObjectReference;
import org.sejda.sambox.output.IncrementablePDDocumentWriter;
import org.sejda.sambox.output.WriteOption;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.util.SpecVersionUtils;
import org.sejda.sambox.xref.FileTrailer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/input/IncrementablePDDocument.class */
public class IncrementablePDDocument implements Closeable {
    private static final Logger LOG = LoggerFactory.getLogger(IncrementablePDDocument.class);
    private Map<IndirectCOSObjectIdentifier, COSBase> replacements = new HashMap();
    private Set<COSBase> newIndirects = new HashSet();
    private PDDocument incremented;
    public final COSParser parser;

    IncrementablePDDocument(PDDocument incremented, COSParser parser) {
        RequireUtils.requireNotNullArg(incremented, "Incremented document cannot be null");
        RequireUtils.requireNotNullArg(parser, "COSParser cannot be null");
        this.incremented = incremented;
        this.parser = parser;
    }

    public PDDocument incremented() {
        return this.incremented;
    }

    public FileTrailer trailer() {
        return this.incremented.getDocument().getTrailer();
    }

    public InputStream incrementedAsStream() throws IOException {
        this.parser.source().position(0L);
        return this.parser.source().asInputStream();
    }

    public COSObjectKey highestExistingReference() {
        return this.parser.provider().highestKey();
    }

    public void replace(IndirectCOSObjectIdentifier toReplace, COSObjectable replacement) {
        RequireUtils.requireNotNullArg(toReplace, "Missing id of the object to be replaced");
        this.replacements.put(toReplace, (COSBase) Optional.ofNullable(replacement).map((v0) -> {
            return v0.getCOSObject();
        }).orElse(COSNull.NULL));
    }

    public boolean modified(COSObjectable modified) {
        RequireUtils.requireNotNullArg(modified, "Missing modified object");
        if (modified.getCOSObject().hasId()) {
            this.replacements.put(modified.getCOSObject().id(), modified.getCOSObject());
            return true;
        }
        return false;
    }

    public void newIndirect(COSObjectable newObject) {
        RequireUtils.requireNotNullArg(newObject, "Missing new object object");
        this.newIndirects.add(newObject.getCOSObject());
    }

    public List<IndirectCOSObjectReference> replacements() {
        return (List) this.replacements.entrySet().stream().map(e -> {
            return new IndirectCOSObjectReference(((IndirectCOSObjectIdentifier) e.getKey()).objectIdentifier.objectNumber(), ((IndirectCOSObjectIdentifier) e.getKey()).objectIdentifier.generation(), ((COSBase) e.getValue()).getCOSObject());
        }).collect(Collectors.toList());
    }

    public Set<COSBase> newIndirects() {
        return Collections.unmodifiableSet(this.newIndirects);
    }

    public COSDictionary encryptionDictionary() {
        return this.incremented.getDocument().getEncryptionDictionary();
    }

    public byte[] encryptionKey() {
        return (byte[]) Optional.ofNullable(this.incremented.getSecurityHandler()).map((v0) -> {
            return v0.getEncryptionKey();
        }).orElse(null);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.incremented.close();
        IOUtils.close(this.parser.provider());
        IOUtils.close(this.parser);
    }

    public void writeTo(File file, WriteOption... options) throws IOException {
        writeTo(CountingWritableByteChannel.from(file), options);
    }

    public void writeTo(String filename, WriteOption... options) throws IOException {
        writeTo(CountingWritableByteChannel.from(filename), options);
    }

    public void writeTo(WritableByteChannel channel, WriteOption... options) throws IOException {
        writeTo(CountingWritableByteChannel.from(channel), options);
    }

    public void writeTo(OutputStream out, WriteOption... options) throws IOException {
        writeTo(CountingWritableByteChannel.from(out), options);
    }

    private void writeTo(CountingWritableByteChannel output, WriteOption... options) throws IOException {
        RequireUtils.requireState(this.incremented.isOpen(), "The document is closed");
        RequireUtils.requireState(!this.replacements.isEmpty(), "No update to be incrementally written");
        updateId(output.toString().getBytes(StandardCharsets.ISO_8859_1));
        try {
            IncrementablePDDocumentWriter writer = new IncrementablePDDocumentWriter(output, options);
            try {
                writer.write(this);
                writer.close();
            } finally {
            }
        } finally {
            IOUtils.close(this);
        }
    }

    private void updateId(byte[] bytes) {
        DirectCOSObject id = DirectCOSObject.asDirectObject(this.incremented.generateFileIdentifier(bytes));
        COSArray existingId = (COSArray) this.incremented.getDocument().getTrailer().getCOSObject().getDictionaryObject(COSName.ID, COSArray.class);
        if (Objects.nonNull(existingId) && existingId.size() == 2) {
            ((COSString) existingId.get(0).getCOSObject()).encryptable(false);
            existingId.set(1, (COSBase) id);
        } else {
            this.incremented.getDocument().getTrailer().getCOSObject().setItem(COSName.ID, (COSBase) DirectCOSObject.asDirectObject(new COSArray(id, id)));
        }
    }

    public void requireMinVersion(String version) {
        if (!SpecVersionUtils.isAtLeast(this.incremented.getVersion(), version)) {
            LOG.debug("Minimum spec version required is {}", version);
            setVersion(version);
        }
    }

    public void setVersion(String newVersion) {
        RequireUtils.requireState(this.incremented.isOpen(), "The document is closed");
        RequireUtils.requireNotBlank(newVersion, "Spec version cannot be blank");
        int compare = this.incremented.getVersion().compareTo(newVersion);
        if (compare > 0) {
            LOG.info("Spec version downgrade not allowed");
            return;
        }
        if (compare < 0) {
            if (SpecVersionUtils.isAtLeast(newVersion, SpecVersionUtils.V1_4)) {
                COSDictionary catalog = this.incremented.getDocument().getCatalog();
                catalog.setName(COSName.VERSION, newVersion);
                modified(catalog);
                return;
            }
            LOG.warn("Sepc version must be at least 1.4 to be set as catalog entry in an incremental update");
        }
    }
}
