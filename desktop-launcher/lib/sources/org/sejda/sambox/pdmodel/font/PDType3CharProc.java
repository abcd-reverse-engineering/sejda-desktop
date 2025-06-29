package org.sejda.sambox.pdmodel.font;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.sejda.sambox.contentstream.PDContentStream;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSNumber;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.input.ContentStreamParser;
import org.sejda.sambox.pdmodel.PDResources;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.common.PDStream;
import org.sejda.sambox.util.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/PDType3CharProc.class */
public final class PDType3CharProc implements COSObjectable, PDContentStream {
    private static final Logger LOG = LoggerFactory.getLogger(PDType3CharProc.class);
    private final PDType3Font font;
    private final COSStream charStream;

    public PDType3CharProc(PDType3Font font, COSStream charStream) {
        this.font = font;
        this.charStream = charStream;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSStream getCOSObject() {
        return this.charStream;
    }

    public PDType3Font getFont() {
        return this.font;
    }

    public PDStream getContentStream() {
        return new PDStream(this.charStream);
    }

    @Override // org.sejda.sambox.contentstream.PDContentStream
    public InputStream getContents() throws IOException {
        return this.charStream.getUnfilteredStream();
    }

    @Override // org.sejda.sambox.contentstream.PDContentStream
    public PDResources getResources() {
        COSDictionary resources = (COSDictionary) this.charStream.getDictionaryObject(COSName.RESOURCES, COSDictionary.class);
        if (Objects.nonNull(resources)) {
            LOG.warn("Using resources dictionary found in charproc entry");
            LOG.warn("This should have been in the font or in the page dictionary");
            return new PDResources(resources);
        }
        return this.font.getResources();
    }

    @Override // org.sejda.sambox.contentstream.PDContentStream
    public PDRectangle getBBox() {
        return this.font.getFontBBox();
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x003a, code lost:
    
        if (r0.size() != 6) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x003d, code lost:
    
        r12 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0044, code lost:
    
        if (r12 >= 6) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0052, code lost:
    
        if ((r0.get(r12) instanceof org.sejda.sambox.cos.COSNumber) != false) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0055, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0057, code lost:
    
        r12 = r12 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x005d, code lost:
    
        r0 = ((org.sejda.sambox.cos.COSNumber) r0.get(2)).floatValue();
        r0 = ((org.sejda.sambox.cos.COSNumber) r0.get(3)).floatValue();
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00a6, code lost:
    
        return new org.sejda.sambox.pdmodel.common.PDRectangle(r0, r0, ((org.sejda.sambox.cos.COSNumber) r0.get(4)).floatValue() - r0, ((org.sejda.sambox.cos.COSNumber) r0.get(5)).floatValue() - r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00a7, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:?, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x002f, code lost:
    
        if (((org.sejda.sambox.contentstream.operator.Operator) r0).getName().equals(org.sejda.sambox.contentstream.operator.OperatorName.TYPE3_D1) == false) goto L22;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.sejda.sambox.pdmodel.common.PDRectangle getGlyphBBox() {
        /*
            r8 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r1 = r0
            r1.<init>()
            r9 = r0
            org.sejda.sambox.input.ContentStreamParser r0 = new org.sejda.sambox.input.ContentStreamParser     // Catch: java.io.IOException -> Lba
            r1 = r0
            r2 = r8
            r1.<init>(r2)     // Catch: java.io.IOException -> Lba
            r10 = r0
            r0 = 0
            r11 = r0
        L13:
            r0 = r10
            java.lang.Object r0 = r0.nextParsedToken()     // Catch: java.io.IOException -> Lba
            r1 = r0
            r11 = r1
            if (r0 == 0) goto Lb7
            r0 = r11
            boolean r0 = r0 instanceof org.sejda.sambox.contentstream.operator.Operator     // Catch: java.io.IOException -> Lba
            if (r0 == 0) goto La9
            r0 = r11
            org.sejda.sambox.contentstream.operator.Operator r0 = (org.sejda.sambox.contentstream.operator.Operator) r0     // Catch: java.io.IOException -> Lba
            java.lang.String r0 = r0.getName()     // Catch: java.io.IOException -> Lba
            java.lang.String r1 = "d1"
            boolean r0 = r0.equals(r1)     // Catch: java.io.IOException -> Lba
            if (r0 == 0) goto La7
            r0 = r9
            int r0 = r0.size()     // Catch: java.io.IOException -> Lba
            r1 = 6
            if (r0 != r1) goto La7
            r0 = 0
            r12 = r0
        L40:
            r0 = r12
            r1 = 6
            if (r0 >= r1) goto L5d
            r0 = r9
            r1 = r12
            java.lang.Object r0 = r0.get(r1)     // Catch: java.io.IOException -> Lba
            boolean r0 = r0 instanceof org.sejda.sambox.cos.COSNumber     // Catch: java.io.IOException -> Lba
            if (r0 != 0) goto L57
            r0 = 0
            return r0
        L57:
            int r12 = r12 + 1
            goto L40
        L5d:
            r0 = r9
            r1 = 2
            java.lang.Object r0 = r0.get(r1)     // Catch: java.io.IOException -> Lba
            org.sejda.sambox.cos.COSNumber r0 = (org.sejda.sambox.cos.COSNumber) r0     // Catch: java.io.IOException -> Lba
            float r0 = r0.floatValue()     // Catch: java.io.IOException -> Lba
            r12 = r0
            r0 = r9
            r1 = 3
            java.lang.Object r0 = r0.get(r1)     // Catch: java.io.IOException -> Lba
            org.sejda.sambox.cos.COSNumber r0 = (org.sejda.sambox.cos.COSNumber) r0     // Catch: java.io.IOException -> Lba
            float r0 = r0.floatValue()     // Catch: java.io.IOException -> Lba
            r13 = r0
            org.sejda.sambox.pdmodel.common.PDRectangle r0 = new org.sejda.sambox.pdmodel.common.PDRectangle     // Catch: java.io.IOException -> Lba
            r1 = r0
            r2 = r12
            r3 = r13
            r4 = r9
            r5 = 4
            java.lang.Object r4 = r4.get(r5)     // Catch: java.io.IOException -> Lba
            org.sejda.sambox.cos.COSNumber r4 = (org.sejda.sambox.cos.COSNumber) r4     // Catch: java.io.IOException -> Lba
            float r4 = r4.floatValue()     // Catch: java.io.IOException -> Lba
            r5 = r12
            float r4 = r4 - r5
            r5 = r9
            r6 = 5
            java.lang.Object r5 = r5.get(r6)     // Catch: java.io.IOException -> Lba
            org.sejda.sambox.cos.COSNumber r5 = (org.sejda.sambox.cos.COSNumber) r5     // Catch: java.io.IOException -> Lba
            float r5 = r5.floatValue()     // Catch: java.io.IOException -> Lba
            r6 = r13
            float r5 = r5 - r6
            r1.<init>(r2, r3, r4, r5)     // Catch: java.io.IOException -> Lba
            return r0
        La7:
            r0 = 0
            return r0
        La9:
            r0 = r9
            r1 = r11
            org.sejda.sambox.cos.COSBase r1 = (org.sejda.sambox.cos.COSBase) r1     // Catch: java.io.IOException -> Lba
            boolean r0 = r0.add(r1)     // Catch: java.io.IOException -> Lba
            goto L13
        Lb7:
            goto Lc6
        Lba:
            r10 = move-exception
            org.slf4j.Logger r0 = org.sejda.sambox.pdmodel.font.PDType3CharProc.LOG
            java.lang.String r1 = "An error occured while calculating the glyph bbox"
            r2 = r10
            r0.warn(r1, r2)
        Lc6:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.sejda.sambox.pdmodel.font.PDType3CharProc.getGlyphBBox():org.sejda.sambox.pdmodel.common.PDRectangle");
    }

    @Override // org.sejda.sambox.contentstream.PDContentStream
    public Matrix getMatrix() {
        return this.font.getFontMatrix();
    }

    public float getWidth() throws IOException {
        List<COSBase> arguments = new ArrayList<>();
        ContentStreamParser parser = new ContentStreamParser(this);
        while (true) {
            Object token = parser.nextParsedToken();
            if (token != null) {
                if (token instanceof Operator) {
                    return parseWidth((Operator) token, arguments);
                }
                arguments.add(((COSBase) token).getCOSObject());
            } else {
                throw new IOException("Unexpected end of stream");
            }
        }
    }

    private float parseWidth(Operator operator, List arguments) throws IOException {
        if (operator.getName().equals(OperatorName.TYPE3_D0) || operator.getName().equals(OperatorName.TYPE3_D1)) {
            Object obj = arguments.get(0);
            if (obj instanceof Number) {
                return ((Number) obj).floatValue();
            }
            if (obj instanceof COSNumber) {
                return ((COSNumber) obj).floatValue();
            }
            throw new IOException("Unexpected argument type: " + obj.getClass().getName());
        }
        throw new IOException("First operator must be d0 or d1");
    }
}
