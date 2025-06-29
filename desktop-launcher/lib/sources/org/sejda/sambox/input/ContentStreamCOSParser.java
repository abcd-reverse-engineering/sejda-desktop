package org.sejda.sambox.input;

import java.io.IOException;
import org.sejda.io.SeekableSource;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSBoolean;
import org.sejda.sambox.cos.COSNull;
import org.sejda.sambox.pdmodel.encryption.PDEncryption;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/input/ContentStreamCOSParser.class */
class ContentStreamCOSParser extends BaseCOSParser {
    ContentStreamCOSParser(SeekableSource source) {
        super(source);
    }

    @Override // org.sejda.sambox.input.BaseCOSParser
    public COSBase nextParsedToken() throws IOException {
        skipSpaces();
        char c = (char) source().peek();
        switch (c) {
            case PDEncryption.DEFAULT_LENGTH /* 40 */:
                return nextLiteralString();
            case '+':
            case '-':
            case '.':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                return nextNumber();
            case '/':
                return nextName();
            case '<':
                source().read();
                char c2 = (char) source().peek();
                source().back();
                if (c2 == '<') {
                    return nextDictionary();
                }
                return nextHexadecimalString();
            case '[':
                return nextArray();
            case 'f':
            case 't':
                String token = readToken();
                if ("true".equals(token)) {
                    return COSBoolean.TRUE;
                }
                if ("false".equals(token)) {
                    return COSBoolean.FALSE;
                }
                return null;
            case 'n':
                if ("null".equals(readToken())) {
                    return COSNull.NULL;
                }
                return null;
            case 65535:
                return null;
            default:
                String badString = readToken();
                if (badString.length() <= 0) {
                    source().read();
                    return null;
                }
                return null;
        }
    }
}
