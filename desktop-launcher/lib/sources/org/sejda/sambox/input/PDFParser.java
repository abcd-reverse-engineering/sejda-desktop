package org.sejda.sambox.input;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import org.sejda.commons.util.IOUtils;
import org.sejda.commons.util.RequireUtils;
import org.sejda.io.SeekableSource;
import org.sejda.sambox.cos.COSDocument;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.encryption.DecryptionMaterial;
import org.sejda.sambox.pdmodel.encryption.PDEncryption;
import org.sejda.sambox.pdmodel.encryption.SecurityHandler;
import org.sejda.sambox.pdmodel.encryption.StandardDecryptionMaterial;
import org.sejda.sambox.util.SpecVersionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/input/PDFParser.class */
public class PDFParser {
    private static final Logger LOG = LoggerFactory.getLogger(PDFParser.class);

    public static PDDocument parse(SeekableSource source) throws IOException {
        return parse(source, (String) null);
    }

    public static IncrementablePDDocument parseToIncrement(SeekableSource source) throws IOException {
        return parseToIncrement(source, (String) null);
    }

    public static PDDocument parse(SeekableSource source, String password) throws IOException {
        return parse(source, (DecryptionMaterial) Optional.ofNullable(password).map(StandardDecryptionMaterial::new).orElse(null));
    }

    public static IncrementablePDDocument parseToIncrement(SeekableSource source, String password) throws IOException {
        return parseToIncrement(source, (DecryptionMaterial) Optional.ofNullable(password).map(StandardDecryptionMaterial::new).orElse(null));
    }

    public static PDDocument parse(SeekableSource source, DecryptionMaterial decryptionMaterial) throws IllegalStateException, IOException {
        Objects.requireNonNull(source);
        COSParser parser = new COSParser(source);
        PDDocument document = doParse(decryptionMaterial, parser);
        document.addOnCloseAction(() -> {
            IOUtils.close(parser.provider());
            IOUtils.close(parser);
        });
        return document;
    }

    public static IncrementablePDDocument parseToIncrement(SeekableSource source, DecryptionMaterial decryptionMaterial) throws IOException {
        Objects.requireNonNull(source);
        COSParser parser = new COSParser(source);
        return new IncrementablePDDocument(doParse(decryptionMaterial, parser), parser);
    }

    private static PDDocument doParse(DecryptionMaterial decryptionMaterial, COSParser parser) throws IOException {
        String headerVersion = readHeader(parser);
        LOG.trace("Parsed header version: " + headerVersion);
        XrefParser xrefParser = new XrefParser(parser);
        xrefParser.parse();
        COSDocument document = new COSDocument(xrefParser.trailer(), headerVersion);
        if (document.isEncrypted()) {
            LOG.debug("Preparing for document decryption");
            PDEncryption encryption = new PDEncryption(document.getEncryptionDictionary());
            SecurityHandler securityHandler = encryption.getSecurityHandler();
            securityHandler.prepareForDecryption(encryption, document.getDocumentID(), (DecryptionMaterial) Optional.ofNullable(decryptionMaterial).orElse(new StandardDecryptionMaterial("")));
            parser.provider().initializeWith(securityHandler);
            return new PDDocument(document, securityHandler);
        }
        return new PDDocument(document);
    }

    private static String readHeader(COSParser parser) throws IOException {
        int headerIndex;
        parser.position(0L);
        String header = parser.readLine();
        long headerOffset = 0;
        while (true) {
            headerIndex = header.indexOf(SpecVersionUtils.PDF_HEADER);
            if (headerIndex >= 0) {
                break;
            }
            RequireUtils.requireIOCondition(parser.position() <= 1024, "Unable to find expected file header");
            headerOffset = parser.position();
            header = parser.readLine();
        }
        long headerOffset2 = headerOffset + headerIndex;
        if (headerOffset2 > 0) {
            LOG.debug("Adding source offset of {} bytes", Long.valueOf(headerOffset2));
            parser.offset(headerOffset2);
        }
        String trimmedLeftHeader = header.substring(headerIndex).replaceAll("\\s", "");
        LOG.debug("Found header {}", trimmedLeftHeader);
        return SpecVersionUtils.parseHeaderString(trimmedLeftHeader);
    }
}
