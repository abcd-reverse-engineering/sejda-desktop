package org.sejda.sambox.input;

import java.io.IOException;
import java.util.regex.Pattern;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.xref.FileTrailer;
import org.sejda.sambox.xref.XrefEntry;
import org.sejda.sambox.xref.XrefType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/input/XrefFullScanner.class */
class XrefFullScanner {
    private static final Logger LOG = LoggerFactory.getLogger(XrefFullScanner.class);
    private AbstractXrefStreamParser xrefStreamParser;
    private AbstractXrefTableParser xrefTableParser;
    private COSParser parser;
    private FileTrailer trailer = new FileTrailer();
    private Pattern objectDefPatter = Pattern.compile("^(\\d+)[\\s](\\d+)[\\s]obj");
    private XrefScanOutcome outcome = XrefScanOutcome.NOT_FOUND;

    XrefFullScanner(COSParser parser) {
        this.parser = parser;
        this.xrefStreamParser = new AbstractXrefStreamParser(parser) { // from class: org.sejda.sambox.input.XrefFullScanner.1
            @Override // org.sejda.sambox.input.AbstractXrefStreamParser
            void onTrailerFound(COSDictionary found) {
                XrefFullScanner.this.trailer.getCOSObject().merge(found);
            }

            @Override // org.sejda.sambox.input.AbstractXrefStreamParser
            void onEntryFound(XrefEntry entry) {
                XrefFullScanner.this.addEntryIfValid(entry);
            }
        };
        this.xrefTableParser = new AbstractXrefTableParser(parser) { // from class: org.sejda.sambox.input.XrefFullScanner.2
            @Override // org.sejda.sambox.input.AbstractXrefTableParser
            void onTrailerFound(COSDictionary found) {
                XrefFullScanner.this.trailer.getCOSObject().merge(found);
            }

            @Override // org.sejda.sambox.input.AbstractXrefTableParser
            void onEntryFound(XrefEntry entry) {
                XrefFullScanner.this.addEntryIfValid(entry);
            }
        };
    }

    private void addEntryIfValid(XrefEntry entry) {
        if (isValidEntry(entry)) {
            this.parser.provider().addEntry(entry);
        } else {
            this.outcome = this.outcome.moveTo(XrefScanOutcome.WITH_ERRORS);
        }
    }

    XrefScanOutcome scan() {
        try {
            doScan();
        } catch (Exception e) {
            this.outcome = this.outcome.moveTo(XrefScanOutcome.WITH_ERRORS);
            LOG.warn("An error occurred while performing full scan looking for xrefs", e);
        }
        return this.outcome;
    }

    private void doScan() throws IOException {
        LOG.info("Performing full scan looking for xrefs");
        long savedPos = this.parser.position();
        this.parser.position(0L);
        this.parser.skipSpaces();
        while (this.parser.source().peek() != -1) {
            long offset = this.parser.position();
            String line = this.parser.readLine();
            if (line.startsWith("xref")) {
                this.outcome = this.outcome.moveTo(XrefScanOutcome.FOUND);
                parseFoundXrefTable(offset);
            }
            if (this.objectDefPatter.matcher(line).find()) {
                parseFoundObject(offset);
            }
            this.parser.skipSpaces();
        }
        this.parser.position(savedPos);
    }

    private void parseFoundXrefTable(long offset) throws IOException {
        LOG.debug("Found xref table at {}", Long.valueOf(offset));
        this.trailer.xrefOffset(offset);
        this.xrefTableParser.parse(offset);
    }

    FileTrailer trailer() {
        return this.trailer;
    }

    private void parseFoundObject(long offset) throws IOException {
        this.parser.position(offset);
        this.parser.skipIndirectObjectDefinition();
        this.parser.skipSpaces();
        COSBase found = this.parser.nextParsedToken();
        if ((found instanceof COSDictionary) && COSName.XREF.equals(((COSDictionary) found).getItem(COSName.TYPE))) {
            LOG.debug("Found xref stream at {}", Long.valueOf(offset));
            this.trailer.xrefOffset(offset);
            parseFoundXrefStream((COSDictionary) found);
        }
    }

    private void parseFoundXrefStream(COSDictionary trailer) throws IOException {
        this.outcome = this.outcome.moveTo(XrefScanOutcome.FOUND);
        COSStream xrefStream = this.parser.nextStream(trailer);
        try {
            this.xrefStreamParser.onTrailerFound(trailer);
            this.xrefStreamParser.parseStream(xrefStream);
            if (xrefStream != null) {
                xrefStream.close();
            }
            LOG.debug("Done parsing xref stream");
        } catch (Throwable th) {
            if (xrefStream != null) {
                try {
                    xrefStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private boolean isValidEntry(XrefEntry entry) {
        if (entry.getType() != XrefType.IN_USE) {
            return true;
        }
        if (entry.getByteOffset() < 0) {
            return false;
        }
        try {
            long origin = this.parser.position();
            try {
                try {
                    this.parser.position(entry.getByteOffset());
                    this.parser.skipIndirectObjectDefinition();
                    this.parser.position(origin);
                    return true;
                } catch (IOException e) {
                    LOG.warn("Xref entry points to an invalid object definition {}", entry);
                    this.parser.position(origin);
                    return false;
                }
            } catch (Throwable th) {
                this.parser.position(origin);
                throw th;
            }
        } catch (IOException e2) {
            LOG.error("Unable to change source position", e2);
            return false;
        }
    }

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/input/XrefFullScanner$XrefScanOutcome.class */
    public enum XrefScanOutcome {
        NOT_FOUND,
        FOUND,
        WITH_ERRORS { // from class: org.sejda.sambox.input.XrefFullScanner.XrefScanOutcome.1
            @Override // org.sejda.sambox.input.XrefFullScanner.XrefScanOutcome
            XrefScanOutcome moveTo(XrefScanOutcome newState) {
                return this;
            }
        };

        XrefScanOutcome moveTo(XrefScanOutcome newState) {
            if (newState != NOT_FOUND) {
                return newState;
            }
            return this;
        }
    }
}
