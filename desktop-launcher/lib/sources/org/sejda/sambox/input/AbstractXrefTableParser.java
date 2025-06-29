package org.sejda.sambox.input;

import java.io.IOException;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.util.CharUtils;
import org.sejda.sambox.xref.XrefEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/input/AbstractXrefTableParser.class */
abstract class AbstractXrefTableParser {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractXrefTableParser.class);
    static final String TRAILER = "trailer";
    static final String XREF = "xref";
    private final COSParser parser;

    abstract void onTrailerFound(COSDictionary cOSDictionary);

    abstract void onEntryFound(XrefEntry xrefEntry);

    AbstractXrefTableParser(COSParser parser) {
        this.parser = parser;
    }

    public COSDictionary parse(long tableOffset) throws IOException {
        parseXrefTable(tableOffset);
        this.parser.skipSpaces();
        while (this.parser.source().peek() != 116) {
            LOG.warn("Expected trailer object at position " + this.parser.position() + ", skipping line.");
            this.parser.readLine();
        }
        return parseTrailer();
    }

    private void parseXrefTable(long tableOffset) throws IOException {
        int next;
        this.parser.position(tableOffset);
        this.parser.skipExpected(XREF);
        if (this.parser.isNextToken(TRAILER)) {
            LOG.warn("Skipping empty xref table at offset " + tableOffset);
            return;
        }
        do {
            long currentObjectNumber = this.parser.readObjectNumber();
            long numberOfEntries = this.parser.readLong();
            this.parser.skipSpaces();
            for (int i = 0; i < numberOfEntries && (next = this.parser.source().peek()) != 116 && !CharUtils.isEndOfName(next) && !CharUtils.isEOF(next); i++) {
                String currentLine = this.parser.readLine();
                String[] splitString = currentLine.split("\\s");
                if (splitString.length < 3) {
                    throw new IOException("Corrupted xref table entry. Invalid xref line: " + currentLine);
                }
                String entryType = splitString[splitString.length - 1];
                if (OperatorName.ENDPATH.equals(entryType)) {
                    try {
                        onEntryFound(XrefEntry.inUseEntry(currentObjectNumber, Long.parseLong(splitString[0]), Integer.parseInt(splitString[1])));
                    } catch (IllegalArgumentException e) {
                        throw new IOException("Corrupted xref table entry. Invalid xref line: " + currentLine, e);
                    }
                } else if (!OperatorName.FILL_NON_ZERO.equals(entryType)) {
                    throw new IOException("Corrupted xref table entry. Expected 'f' but was " + entryType);
                }
                currentObjectNumber++;
                this.parser.skipSpaces();
            }
            this.parser.skipSpaces();
        } while (CharUtils.isDigit(this.parser.source().peek()));
    }

    private COSDictionary parseTrailer() throws IOException {
        long offset = this.parser.position();
        LOG.debug("Parsing trailer at " + offset);
        this.parser.skipExpected(TRAILER);
        this.parser.skipSpaces();
        COSDictionary dictionary = this.parser.nextDictionary();
        onTrailerFound(dictionary);
        this.parser.skipSpaces();
        return dictionary;
    }

    COSParser parser() {
        return this.parser;
    }
}
