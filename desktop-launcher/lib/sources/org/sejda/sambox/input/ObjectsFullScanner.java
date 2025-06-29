package org.sejda.sambox.input;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.sejda.commons.util.RequireUtils;
import org.sejda.sambox.xref.Xref;
import org.sejda.sambox.xref.XrefEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/input/ObjectsFullScanner.class */
class ObjectsFullScanner {
    private static final Logger LOG = LoggerFactory.getLogger(ObjectsFullScanner.class);
    private SourceReader reader;
    private final Matcher matcher = Pattern.compile("(\\d+)[\\s]+(\\d+)[\\s]+obj").matcher("");
    private Xref xref = new Xref();
    private boolean scanned = false;

    ObjectsFullScanner(SourceReader reader) {
        RequireUtils.requireNotNullArg(reader, "Cannot read from a null reader");
        this.reader = reader;
    }

    private void scan() {
        LOG.info("Performing full scan to retrieve objects");
        try {
            long savedPos = this.reader.position();
            this.reader.position(0L);
            this.reader.skipSpaces();
            while (this.reader.source().peek() != -1) {
                long offset = this.reader.position();
                addEntryIfObjectDefinition(offset, this.reader.readLine());
                this.reader.skipSpaces();
            }
            this.reader.position(savedPos);
        } catch (IOException e) {
            LOG.error("An error occurred performing a full scan of the document", e);
        }
    }

    private void addEntryIfObjectDefinition(long offset, String line) throws IOException {
        boolean found = false;
        if (line.contains(SourceReader.OBJ)) {
            this.matcher.reset(line);
            while (this.matcher.find()) {
                long entryOffset = offset + this.matcher.start();
                this.xref.add(XrefEntry.inUseEntry(Long.parseUnsignedLong(this.matcher.group(1)), entryOffset, Integer.parseUnsignedInt(this.matcher.group(2))));
                onObjectDefinitionLine(entryOffset, line);
                found = true;
            }
        }
        if (!found) {
            onNonObjectDefinitionLine(offset, line);
        }
    }

    protected void onNonObjectDefinitionLine(long originalOffset, String line) throws IOException {
    }

    protected void onObjectDefinitionLine(long originalOffset, String line) throws IOException {
    }

    Xref entries() {
        if (!this.scanned) {
            this.scanned = true;
            scan();
        }
        return this.xref;
    }
}
