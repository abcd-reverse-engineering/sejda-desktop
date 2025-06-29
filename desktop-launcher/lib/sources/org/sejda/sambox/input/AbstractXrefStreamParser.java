package org.sejda.sambox.input;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.stream.LongStream;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSNumber;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.xref.CompressedXrefEntry;
import org.sejda.sambox.xref.XrefEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/input/AbstractXrefStreamParser.class */
abstract class AbstractXrefStreamParser {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractXrefStreamParser.class);
    private final COSParser parser;

    abstract void onTrailerFound(COSDictionary cOSDictionary);

    abstract void onEntryFound(XrefEntry xrefEntry);

    AbstractXrefStreamParser(COSParser parser) {
        this.parser = parser;
    }

    COSDictionary parse(long streamObjectOffset) throws IOException {
        LOG.debug("Parsing xref stream at offset " + streamObjectOffset);
        this.parser.position(streamObjectOffset);
        this.parser.skipIndirectObjectDefinition();
        this.parser.skipSpaces();
        COSDictionary dictionary = this.parser.nextDictionary();
        COSStream xrefStream = this.parser.nextStream(dictionary);
        try {
            onTrailerFound(dictionary);
            parseStream(xrefStream);
            if (xrefStream != null) {
                xrefStream.close();
            }
            LOG.debug("Done parsing xref stream");
            return dictionary;
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

    /* JADX WARN: Type inference failed for: r0v39, types: [java.util.PrimitiveIterator$OfLong] */
    void parseStream(COSStream xrefStream) throws IOException {
        LongStream objectNumbers;
        LongStream.empty();
        COSArray index = (COSArray) xrefStream.getDictionaryObject(COSName.INDEX, COSArray.class);
        if (index == null) {
            LOG.debug("No index found for xref stream, using default values");
            objectNumbers = LongStream.rangeClosed(0L, xrefStream.getInt(COSName.SIZE));
        } else {
            LOG.debug("Index found, now retrieving expected object numbers");
            LongStream.Builder builder = LongStream.builder();
            for (int i = 0; i < index.size(); i += 2) {
                long start = ((COSNumber) index.get(i)).longValue();
                long end = start + Math.max(((COSNumber) index.get(i + 1)).longValue() - 1, 0L);
                LOG.trace(String.format("Adding expected range from %d to %d", Long.valueOf(start), Long.valueOf(end)));
                LongStream longStreamRangeClosed = LongStream.rangeClosed(start, end);
                Objects.requireNonNull(builder);
                longStreamRangeClosed.forEach(builder::add);
            }
            objectNumbers = builder.build();
        }
        COSArray xrefFormat = (COSArray) xrefStream.getDictionaryObject(COSName.W);
        int w0 = xrefFormat.getInt(0);
        int w1 = xrefFormat.getInt(1);
        int w2 = xrefFormat.getInt(2);
        int lineSize = w0 + w1 + w2;
        InputStream stream = xrefStream.getUnfilteredStream();
        try {
            ?? it = objectNumbers.iterator();
            while (stream.available() > 0 && it.hasNext()) {
                Long objectId = it.next();
                byte[] currLine = new byte[lineSize];
                stream.read(currLine);
                int type = w0 == 0 ? 1 : 0;
                for (int i2 = 0; i2 < w0; i2++) {
                    type += (currLine[i2] & 255) << (((w0 - i2) - 1) * 8);
                }
                int field1 = 0;
                for (int i3 = 0; i3 < w1; i3++) {
                    field1 += (currLine[i3 + w0] & 255) << (((w1 - i3) - 1) * 8);
                }
                int field2 = 0;
                for (int i4 = 0; i4 < w2; i4++) {
                    field2 += (currLine[(i4 + w0) + w1] & 255) << (((w2 - i4) - 1) * 8);
                }
                switch (type) {
                    case 0:
                        onEntryFound(XrefEntry.freeEntry(objectId.longValue(), field2));
                        break;
                    case 1:
                        onEntryFound(XrefEntry.inUseEntry(objectId.longValue(), field1, field2));
                        break;
                    case 2:
                        onEntryFound(CompressedXrefEntry.compressedEntry(objectId.longValue(), field1, field2));
                        break;
                    default:
                        LOG.warn("Unknown xref entry type " + type);
                        break;
                }
            }
            if (stream != null) {
                stream.close();
            }
        } catch (Throwable th) {
            if (stream != null) {
                try {
                    stream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    COSParser parser() {
        return this.parser;
    }
}
