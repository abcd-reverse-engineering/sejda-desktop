package org.sejda.sambox.output;

import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.sejda.commons.util.IOUtils;
import org.sejda.commons.util.RequireUtils;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSInteger;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.DirectCOSObject;
import org.sejda.sambox.cos.IndirectCOSObjectReference;
import org.sejda.sambox.util.SpecVersionUtils;
import org.sejda.sambox.xref.XrefEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/output/DefaultPDFWriter.class */
class DefaultPDFWriter implements Closeable {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultPDFWriter.class);
    byte COMMENT = 37;
    byte[] GARBAGE = {-89, -29, -15, -15};
    private final IndirectObjectsWriter writer;

    public DefaultPDFWriter(IndirectObjectsWriter writer) {
        RequireUtils.requireNotNullArg(writer, "Cannot write to a null COSWriter");
        this.writer = writer;
    }

    public void writeHeader(String version) throws IOException {
        LOG.debug("Writing header " + version);
        writer().write(SpecVersionUtils.PDF_HEADER);
        writer().write(version);
        writer().writeEOL();
        writer().write(this.COMMENT);
        writer().write(this.GARBAGE);
        writer().writeEOL();
    }

    public long writeXrefTable() throws IOException {
        long startxref = writer().offset();
        LOG.debug("Writing xref table at offset " + startxref);
        if (Objects.nonNull(this.writer.context().addWritten(XrefEntry.DEFAULT_FREE_ENTRY))) {
            LOG.warn("Reserved object number 0 has been overwritten with the expected free entry");
        }
        writer().write("xref");
        writer().writeEOL();
        for (List<Long> continuos : this.writer.context().getWrittenContiguousGroups()) {
            writer().write(continuos.get(0).toString() + " " + continuos.size());
            writer().writeEOL();
            Iterator<Long> it = continuos.iterator();
            while (it.hasNext()) {
                long key = it.next().longValue();
                writer().write(((XrefEntry) Optional.ofNullable(this.writer.context().getWritten(Long.valueOf(key))).orElse(XrefEntry.DEFAULT_FREE_ENTRY)).toXrefTableEntry());
            }
        }
        return startxref;
    }

    public void writeTrailer(COSDictionary trailer, long startxref) throws IOException {
        writeTrailer(trailer, startxref, -1L);
    }

    public void writeTrailer(COSDictionary trailer, long startxref, long prev) throws IOException {
        LOG.trace("Writing trailer");
        sanitizeTrailer(trailer, prev);
        trailer.setItem(COSName.SIZE, (COSBase) DirectCOSObject.asDirectObject(COSInteger.get(this.writer.context().highestObjectNumber() + 1)));
        this.writer.write("trailer".getBytes(StandardCharsets.US_ASCII));
        this.writer.writeEOL();
        trailer.getCOSObject().accept(this.writer.writer());
        writeXrefFooter(startxref);
    }

    public void writeXrefStream(COSDictionary trailer) throws IOException {
        if (Objects.nonNull(this.writer.context().addWritten(XrefEntry.DEFAULT_FREE_ENTRY))) {
            LOG.warn("Reserved object number 0 has been overwritten with the expected free entry");
        }
        writeXrefStream(trailer, -1L);
    }

    public void writeXrefStream(COSDictionary trailer, long prev) throws IOException {
        long startxref = writer().offset();
        LOG.debug("Writing xref stream at offset " + startxref);
        sanitizeTrailer(trailer, prev);
        XrefEntry entry = XrefEntry.inUseEntry(this.writer.context().highestObjectNumber() + 1, startxref, 0);
        this.writer.context().addWritten(entry);
        this.writer.writeObject(new IndirectCOSObjectReference(entry.getObjectNumber(), entry.getGenerationNumber(), new XrefStream(trailer, this.writer.context())));
        writeXrefFooter(startxref);
    }

    private static void sanitizeTrailer(COSDictionary trailer, long prev) {
        trailer.removeItem(COSName.PREV);
        trailer.removeItem(COSName.XREF_STM);
        trailer.removeItem(COSName.DOC_CHECKSUM);
        trailer.removeItem(COSName.DECODE_PARMS);
        trailer.removeItem(COSName.FILTER);
        trailer.removeItem(COSName.F_DECODE_PARMS);
        trailer.removeItem(COSName.F_FILTER);
        trailer.removeItem(COSName.F);
        trailer.removeItem(COSName.LENGTH);
        trailer.removeItem(COSName.W);
        trailer.removeItem(COSName.DL);
        trailer.removeItem(COSName.TYPE);
        trailer.removeItem(COSName.INDEX);
        if (prev != -1) {
            trailer.setLong(COSName.PREV, prev);
        }
    }

    private void writeXrefFooter(long startxref) throws IOException {
        this.writer.write("startxref".getBytes(StandardCharsets.US_ASCII));
        this.writer.writeEOL();
        this.writer.write(Long.toString(startxref));
        this.writer.writeEOL();
        this.writer.write("%%EOF".getBytes(StandardCharsets.US_ASCII));
        this.writer.writeEOL();
    }

    IndirectObjectsWriter writer() {
        return this.writer;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        IOUtils.close(this.writer);
    }
}
