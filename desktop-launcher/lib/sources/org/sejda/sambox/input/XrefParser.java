package org.sejda.sambox.input;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.sejda.commons.util.RequireUtils;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.input.XrefFullScanner;
import org.sejda.sambox.xref.FileTrailer;
import org.sejda.sambox.xref.XrefEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/input/XrefParser.class */
class XrefParser {
    private static final Logger LOG = LoggerFactory.getLogger(XrefParser.class);
    private static final int DEFAULT_TRAIL_BYTECOUNT = 2048;
    private static final String STARTXREF = "startxref";
    private AbstractXrefStreamParser xrefStreamParser;
    private AbstractXrefTableParser xrefTableParser;
    private COSParser parser;
    private FileTrailer trailer = new FileTrailer();
    private HashSet<Long> catalogSeenOffsets = new HashSet<>();

    public XrefParser(COSParser parser) {
        this.parser = parser;
        this.xrefStreamParser = new AbstractXrefStreamParser(parser) { // from class: org.sejda.sambox.input.XrefParser.1
            @Override // org.sejda.sambox.input.AbstractXrefStreamParser
            void onTrailerFound(COSDictionary found) {
                XrefParser.this.trailer.getCOSObject().mergeWithoutOverwriting(found);
            }

            @Override // org.sejda.sambox.input.AbstractXrefStreamParser
            void onEntryFound(XrefEntry entry) {
                parser().provider().addEntryIfAbsent(entry);
            }
        };
        this.xrefTableParser = new AbstractXrefTableParser(parser) { // from class: org.sejda.sambox.input.XrefParser.2
            @Override // org.sejda.sambox.input.AbstractXrefTableParser
            void onTrailerFound(COSDictionary found) {
                XrefParser.this.trailer.getCOSObject().mergeWithoutOverwriting(found);
            }

            @Override // org.sejda.sambox.input.AbstractXrefTableParser
            void onEntryFound(XrefEntry entry) {
                if (entry.getByteOffset() < -1) {
                    throw new IllegalArgumentException("XrefEntry with negative byteOffset: " + entry.getByteOffset());
                }
                parser().provider().addEntryIfAbsent(entry);
            }
        };
    }

    public void parse() throws IOException {
        long xrefOffset = findXrefOffset();
        if (xrefOffset <= 0 || !parseXref(xrefOffset)) {
            XrefFullScanner fallbackFullScanner = new XrefFullScanner(this.parser);
            XrefFullScanner.XrefScanOutcome xrefScanStatus = fallbackFullScanner.scan();
            if (xrefScanStatus != XrefFullScanner.XrefScanOutcome.NOT_FOUND) {
                this.trailer = fallbackFullScanner.trailer();
            }
            if (xrefScanStatus != XrefFullScanner.XrefScanOutcome.FOUND) {
                LOG.warn("Xref full scan encountered some errors, now performing objects full scan");
                ObjectsFullScanner objectsFullScanner = new ObjectsFullScanner(this.parser) { // from class: org.sejda.sambox.input.XrefParser.3
                    private long lastObjectOffset = 0;

                    @Override // org.sejda.sambox.input.ObjectsFullScanner
                    protected void onNonObjectDefinitionLine(long offset, String line) throws IOException {
                        if (Objects.nonNull(line)) {
                            if (line.startsWith("trailer")) {
                                XrefParser.LOG.debug("Parsing trailer at {}", Long.valueOf(offset));
                                XrefParser.this.parser.position(offset);
                                XrefParser.this.parser.skipExpected("trailer");
                                XrefParser.this.parser.skipSpaces();
                                XrefParser.this.trailer.getCOSObject().merge(XrefParser.this.parser.nextDictionary());
                                XrefParser.this.parser.skipSpaces();
                                return;
                            }
                            if (line.contains(COSName.CATALOG.getName())) {
                                long position = XrefParser.this.parser.position();
                                try {
                                    if (XrefParser.this.catalogSeenOffsets.contains(Long.valueOf(this.lastObjectOffset))) {
                                        XrefParser.LOG.debug("Already parsed potential Catalog at {}, skipping", Long.valueOf(this.lastObjectOffset));
                                    } else {
                                        XrefParser.this.catalogSeenOffsets.add(Long.valueOf(this.lastObjectOffset));
                                        XrefParser.LOG.debug("Parsing potential Catalog at {}", Long.valueOf(this.lastObjectOffset));
                                        onPotentialCatalog();
                                    }
                                    return;
                                } catch (IOException e) {
                                    XrefParser.LOG.warn("Unable to parse potential Catalog", e);
                                    XrefParser.this.parser.position(position);
                                    return;
                                }
                            }
                            if (line.startsWith("xref")) {
                                XrefParser.LOG.debug("Found xref at {}", Long.valueOf(offset));
                                XrefParser.this.trailer.xrefOffset(offset);
                            }
                        }
                    }

                    @Override // org.sejda.sambox.input.ObjectsFullScanner
                    protected void onObjectDefinitionLine(long offset, String line) throws IOException {
                        this.lastObjectOffset = offset;
                        if (line.contains(COSName.CATALOG.getName())) {
                            long position = XrefParser.this.parser.position();
                            try {
                                try {
                                    XrefParser.LOG.debug("Parsing potential Catalog at {}", Long.valueOf(this.lastObjectOffset));
                                    onPotentialCatalog();
                                    XrefParser.this.parser.position(position);
                                } catch (IOException e) {
                                    XrefParser.LOG.warn("Unable to parse potential Catalog", e);
                                    XrefParser.this.parser.position(position);
                                }
                            } catch (Throwable th) {
                                XrefParser.this.parser.position(position);
                                throw th;
                            }
                        }
                    }

                    private void onPotentialCatalog() throws IOException {
                        XrefParser.this.parser.position(this.lastObjectOffset);
                        XrefParser.this.parser.skipIndirectObjectDefinition();
                        XrefParser.this.parser.skipSpaces();
                        COSDictionary possibleCatalog = XrefParser.this.parser.nextDictionary();
                        if (COSName.CATALOG.equals(possibleCatalog.getCOSName(COSName.TYPE))) {
                            XrefParser.this.trailer.getCOSObject().putIfAbsent(COSName.ROOT, (COSBase) possibleCatalog);
                        }
                        XrefParser.this.parser.skipSpaces();
                    }
                };
                Collection<XrefEntry> collectionValues = objectsFullScanner.entries().values();
                IndirectObjectsProvider indirectObjectsProviderProvider = this.parser.provider();
                Objects.requireNonNull(indirectObjectsProviderProvider);
                collectionValues.forEach(indirectObjectsProviderProvider::addEntry);
            }
            this.trailer.setFallbackScanStatus(xrefScanStatus.name());
        }
        this.catalogSeenOffsets.clear();
    }

    private long findXrefOffset() throws IOException {
        int chunkSize = (int) Math.min(this.parser.length(), 2048L);
        long startPosition = this.parser.length() - chunkSize;
        this.parser.position(startPosition);
        byte[] buffer = new byte[chunkSize];
        this.parser.source().read(ByteBuffer.wrap(buffer));
        int relativeIndex = new String(buffer, StandardCharsets.ISO_8859_1).lastIndexOf(STARTXREF);
        if (relativeIndex < 0) {
            LOG.warn("Unable to find 'startxref' keyword");
            return -1L;
        }
        try {
            this.parser.position(startPosition + relativeIndex + STARTXREF.length());
            this.parser.skipSpaces();
            long xrefOffset = this.parser.readLong();
            LOG.debug("Found xref offset at {}", Long.valueOf(xrefOffset));
            return xrefOffset;
        } catch (IOException e) {
            LOG.warn("An error occurred while parsing the xref offset", e);
            return -1L;
        }
    }

    private boolean parseXref(long xrefOffset) {
        try {
            return doParseXref(xrefOffset);
        } catch (IOException e) {
            LOG.warn("An error occurred while parsing the xref, applying fallback strategy", e);
            return false;
        }
    }

    private boolean doParseXref(long xrefOffset) throws IOException {
        RequireUtils.requireIOCondition(isValidXrefOffset(xrefOffset), "Offset '" + xrefOffset + "' doesn't point to an xref table or stream");
        Set<Long> parsedOffsets = new HashSet<>();
        long j = xrefOffset;
        while (true) {
            long currentOffset = j;
            if (currentOffset > -1) {
                RequireUtils.requireIOCondition(!parsedOffsets.contains(Long.valueOf(currentOffset)), "/Prev loop detected");
                RequireUtils.requireIOCondition(isValidXrefOffset(currentOffset), "Offset '" + currentOffset + "' doesn't point to an xref table or stream");
                this.parser.position(currentOffset);
                this.parser.skipSpaces();
                if (this.parser.isNextToken("xref")) {
                    COSDictionary trailer = this.xrefTableParser.parse(currentOffset);
                    parsedOffsets.add(Long.valueOf(currentOffset));
                    long streamOffset = trailer.getLong(COSName.XREF_STM);
                    if (streamOffset > 0) {
                        RequireUtils.requireIOCondition(isValidXrefStreamOffset(streamOffset), "Offset '" + streamOffset + "' doesn't point to an xref stream");
                        this.xrefStreamParser.parse(streamOffset);
                    }
                    j = trailer.getLong(COSName.PREV);
                } else {
                    COSDictionary streamDictionary = this.xrefStreamParser.parse(currentOffset);
                    parsedOffsets.add(Long.valueOf(currentOffset));
                    j = streamDictionary.getLong(COSName.PREV);
                }
            } else {
                this.trailer.xrefOffset(xrefOffset);
                return true;
            }
        }
    }

    private boolean isValidXrefOffset(long xrefOffset) throws IOException {
        if (isValidXrefStreamOffset(xrefOffset)) {
            return true;
        }
        this.parser.position(xrefOffset);
        return this.parser.isNextToken("xref");
    }

    private boolean isValidXrefStreamOffset(long xrefStreamOffset) throws IOException {
        this.parser.position(xrefStreamOffset);
        try {
            this.parser.skipIndirectObjectDefinition();
            this.parser.skipSpaces();
            COSDictionary xrefStreamDictionary = this.parser.nextDictionary();
            this.parser.position(xrefStreamOffset);
            return COSName.XREF.equals(xrefStreamDictionary.getCOSName(COSName.TYPE));
        } catch (IOException e) {
            return false;
        }
    }

    public FileTrailer trailer() {
        return this.trailer;
    }
}
